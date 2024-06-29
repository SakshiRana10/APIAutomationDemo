package bright.api.alaya.pages.configServices;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.util.Strings;

import bright.api.alaya.pages.configServices.pojo.BusinessViewPojo;
import bright.api.alaya.pages.configServices.pojo.FieldsDataPojo;
import bright.api.alaya.pages.configServices.pojo.FieldsPojo;
import bright.api.alaya.pages.configServices.pojo.PickListPojo;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.LookUpResourceMapping;
import bright.api.alaya.utils.MainClassAlaya;



public class CondutPage extends MainClassAlaya {

	static ArrayList<String> businessViewLookupType;
	static ArrayList<String> businessViewTableType;
	
	/*
	 * This method filters all the picklist data of condut json using lookup and
	 * resource mapping
	 */
	public static List<PickListPojo> getCondutLookUpData(String jsonFilePath) throws IOException, ParseException {
		// String jsonFilePath = Constants.testConfigData;
		List<PickListPojo> result = new ArrayList<>();

		Object obj = null ;

		try {
			obj = new JSONParser().parse(new FileReader(jsonFilePath));
		}
		
		catch(Exception e){
			e.printStackTrace();
			Assert.fail(e.toString());
		}
		JSONObject jsonFile = new JSONObject();
		jsonFile = (JSONObject) obj;
		JSONArray metaDataLookupArray = (JSONArray) ((JSONObject) jsonFile.get("RETS")).get("METADATA-LOOKUP_TYPE");

		for (Object metaDataLookupObject : metaDataLookupArray) {
			PickListPojo pickListData = new PickListPojo();

			String resource  = (String) ((JSONObject) metaDataLookupObject).get("Resource").toString();
			String lookup  = (String) ((JSONObject) metaDataLookupObject).get("Lookup").toString();
			
			List<String> picklistItemIds = new ArrayList<>();
			List<String> longValue = new ArrayList<>();
			List<String> shortValue = new ArrayList<>();

			if (((JSONObject) metaDataLookupObject).get("DATA") instanceof List) {
				List<String> dataArray = (List<String>) ((JSONObject) metaDataLookupObject).get("DATA");
				for (String data : dataArray) {
					String[] text = data.split(String.valueOf('\t'));
					picklistItemIds.add(text[0]);
					longValue.add(text[1]);
					shortValue.add(text[2]);
				}
			} else {
				String data = (String)  ((JSONObject) metaDataLookupObject).get("DATA");
					String[] text = data.split(String.valueOf('\t'));
					picklistItemIds.add(text[0]);
					longValue.add(text[1]);
					shortValue.add(text[2]);		
			}

			pickListData.setResource(resource);
			pickListData.setLookup(lookup);
			pickListData.setPickListItems(picklistItemIds);
			pickListData.setLongValue(longValue);
			pickListData.setShortValue(shortValue);
			result.add(pickListData);
		}
		return result;
	}

	/*
	 * Method to check whether the given resource and lookup mapping exist in codut
	 * or Not
	 */
	public static PickListPojo checkDataInCondut(List<PickListPojo> metaDataInfoList, String resource, String lookup) {

		PickListPojo result=null;
		for (PickListPojo entryB : metaDataInfoList) {
			if(resource.equalsIgnoreCase(entryB.getResource()) && lookup.equalsIgnoreCase(entryB.getLookup())){
				result= entryB;
			}
		}
		if(result==null) {
			logger.info("No mapping is Found for Resource: "+resource+" and lookup :"+lookup);
		}
		return result;
	}


	/*
	 * Method to check whether the given resource and classname mapping exist in codut
	 * or Not
	 */
	public static FieldsDataPojo checkFieldTableDataInCondut(List<FieldsDataPojo> metaDataInfoList, String resource, String className) {

		FieldsDataPojo result=null;
		for (FieldsDataPojo entryB : metaDataInfoList) {
			if(resource.equalsIgnoreCase(entryB.getResource()) && className.equalsIgnoreCase(entryB.getClassName())){
				result= entryB;
			}
		}
		if(result==null) {
			logger.info("No mapping is Found for Resource: "+resource+" and classname :"+className);
		}
		return result;
	}

	/*
	 * This method filters all the picklist data of condut json using lookup and
	 * resource mapping
	 */
	public static List<FieldsDataPojo> getCondutFieldTableData(String jsonFilePathTable, String jsonFilePathSearchHelp) throws IOException, ParseException {
		// String jsonFilePath = Constants.testConfigData;
		List<FieldsDataPojo> result = new ArrayList<>();
		Object tableObj = null ;

		try {
			 tableObj = new JSONParser().parse(new FileReader(jsonFilePathTable));
		}
		
		catch(Exception e){
			e.printStackTrace();
			Assert.fail(e.toString());
		}
		
		JSONObject tableJsonFile = (JSONObject) tableObj;
		
		Object searchObj = new JSONParser().parse(new FileReader(jsonFilePathSearchHelp));
		JSONObject searchJsonFile = (JSONObject) searchObj;
		
		
		JSONArray metaDataTableArray = (JSONArray) ((JSONObject) tableJsonFile.get("RETS")).get("METADATA-TABLE");

		JSONArray metaDataSearchArray = (JSONArray) ((JSONObject) searchJsonFile.get("RETS")).get("METADATA-SEARCH_HELP");

		for (Object metaDataTableObject : metaDataTableArray) {
			FieldsDataPojo fieldsData = new FieldsDataPojo();

			String resource  = (String) ((JSONObject) metaDataTableObject).get("Resource").toString();
			String className  = (String) ((JSONObject) metaDataTableObject).get("Class").toString();
			

			List<String> longValue = new ArrayList<>();
			List<String> shortValue = new ArrayList<>();
			List<String> pickListSystemName = new ArrayList<>();
			List<String> dataPrecision = new ArrayList<>();
			List<String> pickListMaxInput = new ArrayList<>();
			List<Long> fieldId = new ArrayList<>();
			List<String> standardName = new ArrayList<>();
			List<String> name = new ArrayList<>();
			List<String> maximumValue = new ArrayList<>();
			List<String> minimumValue = new ArrayList<>();
			List<String> upperCaseFlag = new ArrayList<>();
			List<String> dbValue = new ArrayList<>();
			List<String> alignment = new ArrayList<>();
			List<String> caseValue = new ArrayList<>();
			List<String> useSeparator = new ArrayList<>();
			List<String> DefaultSearchOrder  = new ArrayList<>();
			List<Long> searchHelpId = new ArrayList<>();
			List<String> helpText=new ArrayList<>();

			if (((JSONObject) metaDataTableObject).get("DATA") instanceof List) {
				List<String> dataArray = (List<String>) ((JSONObject) metaDataTableObject).get("DATA");
				for (String data : dataArray) {
					String[] text = data.split(String.valueOf('\t'));
					String helptextValue ;
					Long helpId ;
					String idStr = text[0];
					Long id = Long.parseLong(idStr);
					if(Strings.isNullOrEmpty(text[22])) {
						helptextValue=null;
						helpId=null;
					}
					else {
						helpId=Long.parseLong(text[22]);
						helptextValue=getCondutHelpData(metaDataSearchArray,resource,helpId);
					}



				    fieldId.add(id);
					name.add(text[1]);
					standardName.add(text[2]);
					longValue.add(text[3]);
					shortValue.add(text[5]);
					dataPrecision.add(text[6]);
					pickListSystemName.add(text[14]);
					pickListMaxInput.add(text[15]);
					maximumValue.add(text[19]);
					minimumValue.add(text[18]);
					dbValue.add(text[4]);
					alignment.add(text[11]);
					useSeparator.add(text[12]);
					searchHelpId.add(helpId);
					helpText.add(helptextValue);
					caseValue.add(text[30]);
					DefaultSearchOrder.add(text[29]);

				}
			} else {
				String dataArray = (String) ((JSONObject) metaDataTableObject).get("DATA");
					String[] text = dataArray.split(String.valueOf('\t'));
					String helptextValue;
					Long helpId ;
					Long id=Long.parseLong(text[0]);
					if(Strings.isNullOrEmpty(text[22])) {
						helptextValue=null;
						helpId=null;
					}
					else {
						helpId=Long.parseLong(text[23]);
						helptextValue=getCondutHelpData(metaDataSearchArray,resource,helpId);
					

					fieldId.add(id);
					name.add(text[1]);
					standardName.add(text[2]);
					longValue.add(text[3]);
					shortValue.add(text[5]);
					dataPrecision.add(text[6]);
					pickListSystemName.add(text[14]);
					pickListMaxInput.add(text[15]);
					maximumValue.add(text[19]);
					minimumValue.add(text[18]);
					dbValue.add(text[4]);
					alignment.add(text[11]);
					useSeparator.add(text[12]);
					searchHelpId.add(helpId);
					helpText.add(helptextValue);
					caseValue.add(text[30]);
					DefaultSearchOrder.add(text[29]);
				}

			}

			fieldsData.setClassName(className);
			fieldsData.setResource(resource);
			fieldsData.setFieldId(fieldId);
			fieldsData.setName(name);
			fieldsData.setShortValue(shortValue);
			fieldsData.setLongValue(longValue);
			fieldsData.setMaximumValue(maximumValue);
			fieldsData.setMinimumValue(minimumValue);
			fieldsData.setPickListMaxInput(pickListMaxInput);
			fieldsData.setPickListSystemName(pickListSystemName);
			fieldsData.setStandardNamevalue(standardName);
			fieldsData.setDataPrecision(dataPrecision);
			fieldsData.setUpperCaseFlag(upperCaseFlag);
			fieldsData.setAlignment(alignment);
			fieldsData.setDatabaseName(dbValue);
			fieldsData.setUseSeparator(useSeparator);
			fieldsData.setHelpIds(searchHelpId);
			fieldsData.setHelpText(helpText);
			fieldsData.setCaseName(caseValue);
			fieldsData.setDefaultSearchOrder(DefaultSearchOrder);
			result.add(fieldsData);

		}
		return result;
	}


	//get help text
	public static String getCondutHelpData(JSONArray metaDataSearchArray, String Resource, long helpId) throws IOException, ParseException {
		// String jsonFilePath = Constants.testConfigData;
		List<FieldsDataPojo> result = new ArrayList<>();
		Boolean isFound=false;

		String helpText = null ;

		for (Object metaDataSearchObject : metaDataSearchArray) {
			FieldsDataPojo fieldsData = new FieldsDataPojo();

			String resourceName  = (String) ((JSONObject) metaDataSearchObject).get("Resource").toString();
			if(!resourceName.equalsIgnoreCase(Resource)) {
				isFound=false;
			}
			else
			{

				if (((JSONObject) metaDataSearchObject).get("DATA") instanceof List) {
					List<String> dataArray = (List<String>) ((JSONObject) metaDataSearchObject).get("DATA");
					for (String data : dataArray) {

						String[] text = data.split(String.valueOf('\t'));
						Long id=Long.parseLong(text[0]);
						if(id.equals(helpId)) {
							isFound=true;
							helpText=text[2];
							break;
						}


					}
				} else {
					String dataArray = (String) ((JSONObject) metaDataSearchObject).get("DATA");
					String[] text = dataArray.split(String.valueOf('\t'));
						Long id=Long.parseLong(text[0]);
						if(id.equals(helpId)) {
							helpText=text[2];
							break;
						}

				}

			}
			if(isFound)
				break;
		}
		return helpText;
	}



	public static List<BusinessViewPojo> getCondutClassData(String jsonFilePath) throws IOException, ParseException {
		// String jsonFilePath = Constants.testConfigData;
		List<String> className = new ArrayList<>();
		List<String> visibleName = new ArrayList<>();
		List<String> description = new ArrayList<>();
		List<String> resourceName = new ArrayList<>();
		List<BusinessViewPojo> result = new ArrayList<>();

		//Object obj = new JSONParser().parse(new FileReader(jsonFilePath));
		Object obj = null ;

		try {
			obj = new JSONParser().parse(new FileReader(jsonFilePath));
		}
		
		catch(Exception e){
			e.printStackTrace();
			Assert.fail(e.toString());
		}
		
		JSONObject jsonFile = (JSONObject) obj;
		JSONArray metaDataClassArray = (JSONArray) ((JSONObject) jsonFile.get("RETS")).get("METADATA-CLASS");

		for (Object metaDataClassObject : metaDataClassArray) {
					
			String resource = (String) ((JSONObject) metaDataClassObject).get("Resource").toString();
			if (((JSONObject) metaDataClassObject).get("DATA") instanceof List) {
				List<String> dataArray = (List<String>) ((JSONObject) metaDataClassObject).get("DATA");

				if(dataArray!=null)
				{
					for (String data : dataArray) {
						String[] text = data.split(String.valueOf('\t'));
						String ClassName=text[0];
						String VisbleName=text[2];
						String Description=text[3];
						className.add(ClassName);
						visibleName.add(VisbleName);
						description.add(Description);
						resourceName.add(resource);

					}
				}
			} else {
				String dataArray = (String) ((JSONObject) metaDataClassObject).get("DATA");
				if(dataArray!=null) {

						String[] text = dataArray.split(String.valueOf('\t'));
						String ClassName=text[0];
						String VisbleName=text[2];
						String Description=text[3];
						className.add(ClassName);
						visibleName.add(VisbleName);
						description.add(Description);
						resourceName.add(resource);

				}

			}
		}
		for(int i = 0; i < className.size(); i++) {
			BusinessViewPojo businessView = new BusinessViewPojo();
			businessView.setDisplayName(visibleName.get(i));
			businessView.setShortDescription(className.get(i));
			businessView.setDescription(description.get(i));
			businessView.setResource(resourceName.get(i));
			result.add(businessView);
			
		}
		    
return result;
		
	}
}
