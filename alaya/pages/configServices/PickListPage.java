package bright.api.alaya.pages.configServices;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.asserts.SoftAssert;

import bright.api.alaya.pages.configServices.pojo.PickListItemsPojo;
import bright.api.alaya.pages.configServices.pojo.PickListPojo;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.FileUtility;

import bright.api.alaya.utils.JsonUtility;
import bright.api.alaya.utils.MainClassAlaya;
import joptsimple.internal.Strings;



public class PickListPage extends MainClassAlaya {

	SoftAssert softAssert = new SoftAssert();
	
	
	/*
	 * This method will compare short name and long value of picklist data with
	 * condut data
	 */
public void compareLongNameShortName(String businessViewdirectory, String pickListDirectory, String type,String locale, String env) throws IOException, ParseException, NullPointerException {

		

		List<String> businessViewFiles = FileUtility.GetAllFilesFromDirectory(businessViewdirectory);
		List<PickListPojo> metaDataInfoList = null;

		if(businessViewdirectory.contains("wirex") || businessViewdirectory.contains("bright"))
		    metaDataInfoList = CondutPage.getCondutLookUpData(Constants.RetsMetaData+"Lookup_"+"_"+locale+"_"+env+".json");
		else
			logger.info("Please check the metaData configuration amd files ");


		BusinessViewPage businessViewPage = new BusinessViewPage();
		List<String> comparisonSummary = new ArrayList<>();
	
		
		for (String businessViewFile : businessViewFiles) {
			if(businessViewFile.contains("DS_Store")) {
				continue;
			}
			//checking systemName for each class and verifying if it has a corresponding lms named file then only execution will happen
			boolean flag = checkSystemNameLogic(businessViewFile,businessViewFiles);
			if(!flag) {
			  logger.info("Skipped file in PickList Page - "+businessViewFile);
			  continue;
			}
			
			List<PickListPojo> businessViewsDataList = businessViewPage.getBusinessViewsData(businessViewFile);
			for (PickListPojo pickListData : businessViewsDataList) {
				PickListPojo result = null;
				
				if(pickListData.getResource().contains("Lms")) {	
				continue;
				}
				if(!pickListData.getExternalVisibleFlag()) {				
				continue;
				}
				else if(!pickListData.getIsRetsEnabled()) {
				continue;
				}
				else if(Objects.equals(pickListData.getBusinessViewId(), 10065735983L) && businessViewdirectory.contains("wirex") ) {
				continue;
				}
				else
				{
					 result=CondutPage.checkDataInCondut(metaDataInfoList,pickListData.getResource(),pickListData.getLookup());
				}
				
				if(result==null) {
					softAssert.fail("No Mapping found for Business view : "+pickListData.getBusinessViewId() +" and PickList ID: "+pickListData.getPickListId()+" in condut for Resource: "+pickListData.getResource()+" and Lookup "+pickListData.getLookup()+System.lineSeparator());
					continue;
				}
				for (String itemId : pickListData.getPickListItems()) {
					PickListItemsPojo pickListItemsData = getDataFromLocalDirectory(pickListDirectory,itemId, pickListData.getBusinessViewId());

					
					int i = -1;
					
					
						if (result.getPickListItems().contains(itemId)) {
							i = result.getPickListItems().indexOf(itemId);
							
						}
					
					if (i == -1) {
					softAssert.fail("No picklist item data found in condut for business view :"+pickListData.getBusinessViewId()+" and  Resource: "+pickListData.getResource()+"  and Lookup "+pickListData.getLookup()+"  Picklisdt item id: "+itemId+System.lineSeparator());
					continue;
					}

					String itemIdSummary = Strings.EMPTY;
					String condutLongValue=result.getLongValue().get(i);
					String condutShortValue=result.getShortValue().get(i);
					String picklistDisplayValue=pickListItemsData.getDisplayValue();
					String picklistItemValue=pickListItemsData.getItemValue();
					
					
					if (!condutLongValue.equalsIgnoreCase(picklistItemValue)) {
						itemIdSummary = " Business Id :["+pickListData.getBusinessViewId()+"]"+" PickListItem Id: [" + itemId + "], " +
								" long Value in condut: [" + condutLongValue + "], " +
								" item/displayLong Value of picklist [" + picklistItemValue+ "]"+System.lineSeparator();
						

					}
					if (!condutShortValue.equalsIgnoreCase(picklistDisplayValue)) {
						if (itemIdSummary.equals(Strings.EMPTY)) {
							itemIdSummary = " Business Id :["+pickListData.getBusinessViewId()+"]"+" PickListItem Id: [" + itemId + "], " +
									" short Value in condut: [" + condutShortValue + "], " +
									" display Value on picklist: [" + picklistDisplayValue + "]"+System.lineSeparator();
							

						} else {
							itemIdSummary += " Business Id :["+pickListData.getBusinessViewId()+"]"+" PickListItem Id: [" + itemId + "], " +
									" short Value in condut: [" + condutShortValue + "], " +
									" display Value on picklist: [" + picklistDisplayValue + "]"+System.lineSeparator();
							
							


						}
					}
					if (!itemIdSummary.equals(Strings.EMPTY)) {
						comparisonSummary.add(itemIdSummary);
					}
				}
				if (comparisonSummary.size() > 0) {

					for (String item : comparisonSummary) 
					{
						
						softAssert.fail("items are: "+item);
					}
				}
				comparisonSummary.clear();
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

/*
 * This method will identify whether user have to consider business view display
 * array value for names or have to consider name from the picklist display value.
 * Also set the values of names accordingly in picklist items Pojo
 */
public PickListItemsPojo getDataFromLocalDirectory(String pickListDirectory,String id, long businessViewId) throws NullPointerException, ParseException, IOException {

	String filePath = pickListDirectory + "/"+id + ".json";
	String displayValue = null, displayValueLong = null;
	JSONObject jsonObject = JsonUtility.GetJsonFileData(filePath);
	PickListItemsPojo pickListItemsDTO = new PickListItemsPojo();
	if (jsonObject.get("businessViewsDisplayValues") != null) {
		JSONArray arr = new JSONArray();
		JSONObject targetJson = null;
		arr.add(jsonObject.get("businessViewsDisplayValues"));
		for (Object item : ((JSONArray) arr.get(0))) {
			if (((JSONObject) item).containsKey(String.valueOf(businessViewId))) {
				targetJson = (JSONObject) ((JSONObject) item).get(String.valueOf(businessViewId));
			}
		}
		if (targetJson != null) {

			displayValue = targetJson.get("displayValue") != null ? targetJson.get("displayValue").toString() : "null";
			displayValueLong = targetJson.get("displayValueLong") != null ? targetJson.get("displayValueLong").toString() : "null";

		}
	}
	if (Strings.isNullOrEmpty(displayValue)) {
		displayValue = jsonObject.get("displayValue") != null ? jsonObject.get("displayValue").toString() : "null";

	}
	if (Strings.isNullOrEmpty(displayValueLong)) {
		
		if(jsonObject.get("isLocalizedDisplayValues").toString().equals("true")) 
			displayValueLong = jsonObject.get("displayValueLong") != null ? jsonObject.get("displayValueLong").toString() : "null";
		
		else
		displayValueLong = jsonObject.get("itemValue") != null ? jsonObject.get("itemValue").toString() : "null";
	}
	pickListItemsDTO.setDisplayValue(displayValue);
	pickListItemsDTO.setItemValue(displayValueLong);
	return pickListItemsDTO;
}

}



