package bright.api.alaya.pages.configServices;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.asserts.SoftAssert;
import org.testng.util.Strings;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


import bright.api.alaya.pages.configServices.pojo.BusinessViewPojo;

import bright.api.alaya.pages.configServices.pojo.FieldsPojo;
import bright.api.alaya.pages.configServices.pojo.PickListPojo;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.FileUtility;
import bright.api.alaya.utils.LookUpResourceMapping;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;


public class BusinessViewPage extends MainClassAlaya   {

	/*
	 * This method initialize business view pojo by reading json file
	 */
	public static BusinessViewPojo readBusinessViewJsonFile(String fileName) {
		Gson gson = new GsonBuilder().serializeNulls().create();
		BusinessViewPojo businessViewDTO = null;
		try (FileReader reader = new FileReader(fileName)) {
			businessViewDTO = gson.fromJson(reader, new TypeToken<BusinessViewPojo>() {
			}.getType());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return businessViewDTO;
	}

	/*
	 * This method Creates a pickList Pojo object using resource and lookup
	 * mappings from database
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	public List<PickListPojo> getBusinessViewsData(String jsonFilePath) throws IOException {

		BusinessViewPojo buinessViewData = this.readBusinessViewJsonFile(jsonFilePath);
		if (buinessViewData == null) {
			logger.info("Exception in Parsing file: " + jsonFilePath);
			return Collections.EMPTY_LIST;
		}


		List<PickListPojo> pickList = new ArrayList<>();

		for (Entry<String, FieldsPojo> entry : buinessViewData.getFields().entrySet()) {
			FieldsPojo data = entry.getValue();
			if (Strings.isNullOrEmpty(data.getPickListId()) || data.getPickListItems() == null || data.getPickListItems().size() == 0) {
				continue;
			}
			PickListPojo PickListPojo = new PickListPojo();
			PickListPojo.setBusinessViewId(buinessViewData.getId());
			PickListPojo.setExternalVisibleFlag(buinessViewData.getExternalVisibleFlag());
			PickListPojo.setPickListItems(data.getPickListItems());
			PickListPojo.setPickListId(data.getPickListId());
			PickListPojo.setSystemName(buinessViewData.getSystemName());
			PickListPojo.setIsRetsEnabled(buinessViewData.getIsRetsEnabled());


			String lookUpName = LookUpResourceMapping.readLookUpName(buinessViewData.getId(),data.getPickListId()); 
			if(Strings.isNullOrEmpty(lookUpName)) {
				lookUpName= data.getmPickListSystemName();
			}

			PickListPojo.setLookup(lookUpName); 
			String resource2=LookUpResourceMapping.readResourceValue(buinessViewData.getId());

			PickListPojo.setResource(resource2);
			pickList.add(PickListPojo);
		}

		return pickList;
	}



	public void verifyPickListItems(String directory, String locale,String type, String env) throws ParseException, IOException {

		// read metaData from condut
		List<PickListPojo> metaDataInfoList = null ;
		if(locale.contains("Wirex") || locale.contains("Bright"))
			metaDataInfoList = CondutPage.getCondutLookUpData(Constants.RetsMetaData+"Lookup_"+"_"+locale+"_"+env+".json");
		else
			logger.info("Please check the metaData configuration amd files ");
		
		SoftAssert softAssert = new SoftAssert();


		// Read all files paths
		List<String> businessViewFiles = FileUtility.GetAllFilesFromDirectory(directory);

		for (String businessViewFile : businessViewFiles) {
			if(businessViewFile.contains("DS_Store")) {
				continue;
			}
			
			//checking systemName for each class and verifying if it has a corresponding lms named file then only execution will happen
			boolean flag = checkSystemNameLogic(businessViewFile,businessViewFiles);
			if(!flag) {
			  logger.info("Skipped file in business view Page - "+businessViewFile);
			  continue;
			}
			
			List<PickListPojo> businessViewsDataList = getBusinessViewsData(businessViewFile);
			for (PickListPojo entryA : businessViewsDataList) {

				PickListPojo metadata = null;
				
				if(!entryA.getExternalVisibleFlag()) {				
					continue;
				}
				else if(!entryA.getIsRetsEnabled()) {
					continue;
				}
				else if(Objects.equals(entryA.getBusinessViewId(), 10065735983L) && directory.contains("wirex") ) {				
					continue;
				}
				else
				{
					metadata=CondutPage.checkDataInCondut(metaDataInfoList,entryA.getResource(),entryA.getLookup());
				}

				if(metadata==null) {
					softAssert.fail("No Mapping found for Business view : "+entryA.getBusinessViewId() +" and PickList ID: "+entryA.getPickListId()+" in condut for Resource: "+entryA.getResource()+" and Lookup "+entryA.getLookup()+System.lineSeparator());
					continue;
				}



				if (entryA.getPickListSize() == metadata.getPickListSize()) {
					List<String> missingEntryInCondut = CommonUtilityMethods.getUncommonElements(metadata.getPickListItems(), entryA.getPickListItems());
					List<String> missingEntryInBusinessViews = CommonUtilityMethods.getUncommonElements(entryA.getPickListItems(), metadata.getPickListItems());
					if (missingEntryInCondut.size() > 0) {

						softAssert.fail("Records not exists in Condut : " + missingEntryInCondut +" For Business View : "+entryA.getBusinessViewId()+" for Picklist: "+entryA.getPickListId()+ " and Resource: "+ entryA.getResource()+ " and lookup: "+entryA.getLookup()+" and missing elements are:"+ missingEntryInCondut+System.lineSeparator());
			}
					else {

						
					}
					if (missingEntryInBusinessViews.size() > 0) {

						softAssert.fail("Records not exists in Business view : " + missingEntryInBusinessViews  +" For Business View : "+entryA.getBusinessViewId()+" for Picklist: "+entryA.getPickListId()+ " and Resource: "+ entryA.getResource()+" and missing elements are:"+ missingEntryInBusinessViews+System.lineSeparator());

					}
					else {
						
					}


				} 
				else {

					
					List<String> missingEntryInBusinessViews = CommonUtilityMethods.getUncommonElements(metadata.getPickListItems(), entryA.getPickListItems());
					List<String> missingEntryInCondut = CommonUtilityMethods.getUncommonElements(entryA.getPickListItems(), metadata.getPickListItems());
					if(missingEntryInBusinessViews.size() >0) {
						softAssert.fail(missingEntryInBusinessViews.size()+ " count missing in business view :"+entryA.getBusinessViewId()+" for Picklist: "+entryA.getPickListId()+ " and Resource: "+ entryA.getResource()+ " and lookup: "+entryA.getLookup()+" and missing elements are:"+ missingEntryInBusinessViews+System.lineSeparator());
					}

					if(missingEntryInCondut.size() > 0) {
						softAssert.fail(missingEntryInCondut.size()+ " count missing in conduit  :"+entryA.getBusinessViewId()+" for Picklist: "+entryA.getPickListId()+ " and Resource: "+ entryA.getResource()+ " and lookup: "+entryA.getLookup()+" and missing elements are:"+ missingEntryInCondut+System.lineSeparator());
					}

				}

			}

		}
		softAssert.assertAll();
	}
	
	public boolean checkSystemNameLogic(String businessViewFile, List<String> businessViewFiles) throws FileNotFoundException, IOException, ParseException {
		boolean flag = false;
	    JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader(businessViewFile));
	    JSONObject jsonObject = (JSONObject)obj;
	    
	    String expectedLmsSystemName = (String)jsonObject.get("systemName");
	    logger.info("System name to check : "+expectedLmsSystemName);
	    for(String businessViewFile1 : businessViewFiles) {
	    	Object obj1 = parser.parse(new FileReader(businessViewFile1));
	        JSONObject jsonObject1 = (JSONObject)obj1;
	        String lmsSystemName = (String)jsonObject1.get("systemName");
	      	
	    	if(lmsSystemName.equals("LMS_"+expectedLmsSystemName)) {
	    		flag=true;
	    		break;
	    	}
	    	else {
	    		continue;
	    	}
	    }
	    return flag;
	}

	public void verifyCondutBusinessView(String directory, String locale, String env) throws IOException, ParseException {

		BusinessViewPage businessView=new BusinessViewPage();
		SoftAssert softAssert = new SoftAssert();
		List<BusinessViewPojo> businessViewData = businessView.getBusinessView(directory) ;

		List<BusinessViewPojo> conduit=null;
		if(locale.contains("Wirex") || locale.contains("Bright"))
			conduit = CondutPage.getCondutClassData(Constants.RetsMetaData+"Class_"+"_"+locale+"_"+env+".json");
		else
			logger.info("Please check the metaData configuration amd files ");
		
		
		

		for (BusinessViewPojo entryA : conduit) {

			String shortDescription=entryA.getShortDescription();
			String displayName=entryA.getDisplayName();

			boolean isMatchFound=false;

			for (BusinessViewPojo entryB : businessViewData) {
				String shortDescriptionArtifacts=entryB.getShortDescription();
				String displayNameArtifacts=entryB.getDisplayName();

				if(shortDescriptionArtifacts.equalsIgnoreCase(shortDescription) && displayNameArtifacts.equalsIgnoreCase(displayName)) {
					isMatchFound=true;
				}
				
			}
			if(!isMatchFound) {
				
				softAssert.fail(displayName+ " does not exist in artifacts for this description " + entryA.getDescription() +" and Resource: "+entryA.getResource()+System.lineSeparator());
			}
			
			else {
				
			}
		}
		softAssert.assertAll();
	
	}
	
	/*
	 * This method Creates a pickList Pojo object using resource and lookup
	 * mappings from database
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	public BusinessViewPojo getBusinessViewsInfo(String jsonFilePath) throws IOException {

		BusinessViewPojo buinessViewData = this.readBusinessViewJsonFile(jsonFilePath);
			
			BusinessViewPojo businessViewPojo = new BusinessViewPojo();
			businessViewPojo.setShortDescription(buinessViewData.getShortDescription());
		
			businessViewPojo.setDisplayName(buinessViewData.getDisplayName());

		return businessViewPojo;
	}
	
	public  List<BusinessViewPojo> getBusinessView(String directory) throws IOException, ParseException {
		// String jsonFilePath = Constants.testConfigData;
		List<String> businessViewFiles = FileUtility.GetAllFilesFromDirectory(directory);
		List<BusinessViewPojo> result = new ArrayList<>();

		for (String businessViewFile : businessViewFiles) {
			
			if(businessViewFile.contains("DS_Store")) {
				continue;
			}

			try {
				result.add(getBusinessViewsInfo(businessViewFile));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return result;
	}

}
