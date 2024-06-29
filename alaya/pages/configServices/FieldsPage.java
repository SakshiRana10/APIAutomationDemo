package bright.api.alaya.pages.configServices;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Map.Entry;

import org.json.simple.parser.ParseException;
import org.testng.asserts.SoftAssert;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import bright.api.alaya.pages.configServices.pojo.BusinessViewPojo;
import bright.api.alaya.pages.configServices.pojo.FieldsDataPojo;
import bright.api.alaya.pages.configServices.pojo.FieldsPojo;
import bright.api.alaya.pages.configServices.pojo.PickListItemsPojo;
import bright.api.alaya.pages.configServices.pojo.PickListPojo;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.FileUtility;
import bright.api.alaya.utils.LookUpResourceMapping;
import bright.api.alaya.utils.MainClassAlaya;
import joptsimple.internal.Strings;

public class FieldsPage extends MainClassAlaya {


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
	 * This method Creates a Fields Pojo object using resource and class name
	 * mappings from database
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	public static List<FieldsPojo> getBusinessViewsData(String jsonFilePath) throws IOException {
		BusinessViewPojo buinessViewData = readBusinessViewJsonFile(jsonFilePath);
		if (buinessViewData == null) {
			logger.info("Exception in Parsing file: " + jsonFilePath);
			return Collections.EMPTY_LIST;
		}

		List<FieldsPojo> fields = new ArrayList<>();

		for (Entry<String, FieldsPojo> entry : buinessViewData.getFields().entrySet()) {

			FieldsPojo data = entry.getValue();

			FieldsPojo fieldsPojo = new FieldsPojo();
			
			if(data.getmInternalFieldFlag())
				continue;

			else
			{
			fieldsPojo.setBusinessViewId(buinessViewData.getId());
			fieldsPojo.setExternalVisibleFlag(buinessViewData.getExternalVisibleFlag());
			fieldsPojo.setClassName(buinessViewData.getShortDescription());
			String resource=LookUpResourceMapping.readResourceValue(buinessViewData.getId());
			fieldsPojo.setResource(resource);
			fieldsPojo.setmName(data.getmName());
			fieldsPojo.setmLongName(data.getmLongName());
			fieldsPojo.setmShortName(data.getmShortName());
			fieldsPojo.setmFieldId(data.getmFieldId());
			fieldsPojo.setmFieldSystemName(data.getmFieldSystemName());
			fieldsPojo.setmUpperCaseFlag(data.getmUpperCaseFlag());
			fieldsPojo.setmStandardName(data.getmStandardName());
			fieldsPojo.setmPickListMaxInput(data.getmPickListMaxInput());
			fieldsPojo.setmMaximumValue(data.getmMaximumValue());
			fieldsPojo.setmMinimumValue(data.getmMinimumValue());
			fieldsPojo.setmDataPrecision(data.getmDataPrecision());
			fieldsPojo.setmPickListSystemName(data.getmPickListSystemName());
			fieldsPojo.setmIsDeprecated(data.getmIsDeprecated());
			fieldsPojo.setmJsonDataType(data.getmJsonDataType());
			fieldsPojo.setmUpperCaseFlag(data.getmUpperCaseFlag());
			fieldsPojo.setmDataTypeId(data.getmDataTypeId());
			fieldsPojo.setmHelpText(data.getmHelpText());
			fields.add(fieldsPojo);
			}
		}

		return fields;
	}

	/**
	 * This method verifies the count of fields in business view , compare the fields data with condut data 
	 * @param directory
	 * @throws IOException
	 * @throws ParseException
	 */
	public void verifyFields(String directory, String locale, String env) throws IOException, ParseException {

		List<String> comparisonSummary = new ArrayList<>();
		String itemIdSummary=Strings.EMPTY;

		// read metaData from condut
		List<FieldsDataPojo> metaDataInfoList=null ;
		
		if(directory.contains("wirex")|| directory.contains("bright"))
			metaDataInfoList = CondutPage.getCondutFieldTableData(Constants.RetsMetaData+"Table_"+"_"+locale+"_"+env+".json",Constants.RetsMetaData+"SearchHelp_"+"_"+locale+"_"+env+".json");
		else
			logger.info("Please check the metaData configuration amd files ");

		SoftAssert softAssert = new SoftAssert();
		// Read all files paths
		List<String> businessViewFiles = FileUtility.GetAllFilesFromDirectory(directory);
		if(businessViewFiles.size()> 0)
		{

		for (String businessViewFile : businessViewFiles) {
			logger.info("Now Picked file: " + businessViewFile);

			if(businessViewFile.contains("DS_Store")) {
				continue;
			}

			List<FieldsPojo> businessViewsDataList = getBusinessViewsData(businessViewFile);
			int businessFieldsCount=businessViewsDataList.size();
if(!businessViewsDataList.get(1).isRetsEnabled()  ) {
				
				continue;
			}
			if(!businessViewsDataList.get(1).isExternalVisibleFlag()) {
				
				continue;
			}
			List<Long> res;
			FieldsDataPojo metadata=CondutPage.checkFieldTableDataInCondut(metaDataInfoList,businessViewsDataList.get(1).getResource(),businessViewsDataList.get(1).getClassName());
			if(metadata!=null) {
				res=metadata.getFieldId();
			}
			else {
				softAssert.fail("No Data Exist in conduit for this Class: "+businessViewsDataList.get(1).getClassName()+" and Resource :"+businessViewsDataList.get(1).getResource()+System.lineSeparator());
				continue;
				
				

			}

			if(businessFieldsCount==res.size()) 
			{
				
				List<Long> missingEntryInBusinessViews = CommonUtilityMethods.getUncommonElementsLong(metadata.getFieldId(), getFieldIDs(businessViewFile));
				List<Long> missingEntryInCondut = CommonUtilityMethods.getUncommonElementsLong(getFieldIDs(businessViewFile),metadata.getFieldId());
				if (missingEntryInCondut.size() > 0) {

					softAssert.fail("Error:Records not exists in conduit  : " + missingEntryInCondut +" For Business View : "+businessViewsDataList.get(1).getBusinessViewId()+" and Resource: "+ businessViewsDataList.get(1).getResource()+ " and class: "+businessViewsDataList.get(1).getClassName()+" and fields are :"+missingEntryInCondut+System.lineSeparator());

				}
				else {

					
				}
				if (missingEntryInBusinessViews.size() > 0) {

					softAssert.fail("Error:Records not exists in business view : " + missingEntryInBusinessViews  +" For Business View : "+businessViewsDataList.get(1).getBusinessViewId()+" and Resource: "+ businessViewsDataList.get(1).getResource()+ " and className: "+businessViewsDataList.get(1).getClassName()+" and fields are :"+missingEntryInBusinessViews+System.lineSeparator());

				}
				else {
					

					for (FieldsPojo entryA : businessViewsDataList) {


						if(Objects.equals(entryA.getBusinessViewId(), 10065735983L) && directory.contains("wirex") ) {
							logger.info("Record Skip as Res Mapping Found for Business view : 10065735983");
							continue;
						}
						else
							metadata=CondutPage.checkFieldTableDataInCondut(metaDataInfoList,entryA.getResource(),entryA.getClassName());


						if(metadata==null) {
							softAssert.fail("Error:No Mapping found for Business view : "+entryA.getBusinessViewId() +" and class name ID: "+entryA.getClassName()+" in condut for Resource: "+entryA.getResource()+System.lineSeparator());
							continue;
						}



						int i = -1;
						res=metadata.getFieldId();
						Long businessField=entryA.getmFieldId();

						i=res.indexOf(businessField);

						if (i == -1) {
							softAssert.fail("Error:No fields item data found in condut for Resource: "+entryA.getResource()+"  and class name "+entryA.getClassName()+"and Business viw ID :"+entryA.getBusinessViewId()+" and FieldID :"+entryA.getmFieldId()+System.lineSeparator());
							continue;
						}

						else {
						String helpValueBusiness=entryA.getmHelpText();
						String condutHelpValue=metadata.getHelpText().get(i);
						
						
						String condutLongname=metadata.getLongValue().get(i);
						String condutShortName=metadata.getShortValue().get(i);

						String businessLongName=entryA.getmLongName();
						String businessShortName=entryA.getmShortName();

						String pickListSystemName=metadata.getPickListSystemName().get(i);
						String businessSystemName=entryA.getmPickListSystemName();

						String fieldName=metadata.getName().get(i);
						String businessFieldName=entryA.getmName();


						String standardName=metadata.getStandardNamevalue().get(i);
						String businessStandardName=entryA.getmStandardName();



						Integer dataPrecisionBusiness=entryA.getmDataPrecision();
						String dataPrecisonCondut=metadata.getDataPrecision().get(i);

						String JsonDataType=entryA.getmJsonDataType();
						String alignmentCondut=metadata.getAlignment().get(i);
						String calculatedAlignment;

						String calculatedUseSeperator;

						if (JsonDataType.equalsIgnoreCase("number")) {
							calculatedUseSeperator="1";
						}

						else
							calculatedUseSeperator="0";

						String useSeperatorCondut=metadata.getUseSeparator().get(i);
						/* Will add once we have dataTypes in artifacts
						 * if(!calculatedUseSeperator.equalsIgnoreCase(useSeperatorCondut)) {
						 * softAssert.fail("Error: Business Id :["+entryA.getBusinessViewId()+
						 * "]"+" FieldID : [" + entryA.getmFieldId() + "], " +
						 * " use seperator Value in condut: [" + useSeperatorCondut + "], " +
						 * " calculated Value of use seperator from business file [" +
						 * calculatedUseSeperator+ "]"+System.lineSeparator()); }
						 */
						boolean upperCaseFlag=entryA.getmUpperCaseFlag();
						String calculatedUpperCaseFlag;
						String caseNameCondut=metadata.getCaseName().get(i);

						if(upperCaseFlag) {
							calculatedUpperCaseFlag="UPPER";
						}

						else
							calculatedUpperCaseFlag="EXACT";

						if(!calculatedUpperCaseFlag.equalsIgnoreCase(caseNameCondut)) {
							softAssert.fail("Error: Business Id :["+entryA.getBusinessViewId()+"]"+" FieldID : [" + entryA.getmFieldId() + "], " +
									" case Value in condut: [" + caseNameCondut + "], " +
									" calculated Value of case from business file [" + calculatedUpperCaseFlag+ "]"+System.lineSeparator());
						}
						
						if(helpValueBusiness==null)
							helpValueBusiness="";
						
						if(condutHelpValue==null)
							condutHelpValue="";

						if(!helpValueBusiness.equalsIgnoreCase(condutHelpValue)) {
							
							/*
							 * softAssert.fail("Error: Business Id :["+entryA.getBusinessViewId()+
							 * "]"+" FieldID : [" + entryA.getmFieldId() + "], " +
							 * " help text Value in condut: [" + condutHelpValue + "], " +
							 * " help text from business file [" + helpValueBusiness+
							 * "]"+System.lineSeparator());
							 */
						}
						
					

						/* This code will be used when we have data Type in config artifacts 
						 * if(JsonDataType.equalsIgnoreCase("number") || (entryA.getPickListId()!=null))
						 * {
						 * 
						 * calculatedAlignment = "Right"; } else calculatedAlignment="Left";
						 * 
						 * if(!alignmentCondut.equalsIgnoreCase(calculatedAlignment)) { itemIdSummary +=
						 * " Business Id :["+entryA.getBusinessViewId()+"]"+" FieldID : [" +
						 * entryA.getmFieldId() + "], " + " alignment Value in condut: [" +
						 * alignmentCondut + "], " +
						 * " calculated Value of Alignment from business file [" + calculatedAlignment+
						 * "]"+System.lineSeparator(); }
						 */

						long dbValue=entryA.getmFieldId()% 1000000000L;
						String dbValueCalculated="X"+dbValue;
						String condutDBValue=metadata.getDatabaseName().get(i);

						if(!dbValueCalculated.equalsIgnoreCase(condutDBValue)) {
							softAssert.fail("Error: Business Id :["+entryA.getBusinessViewId()+"]"+" FieldID : [" + entryA.getmFieldId() + "], " +
									" DB Value in condut: [" + condutDBValue + "], " +
									" calculated Value of DB from business file [" + dbValueCalculated+ "]"+System.lineSeparator());
						}

						/* This code will be used when we have data Type in config artifacts 
						 * String dataPrecisionValuebusiness = null;
						 * if(dataPrecisionBusiness.equals(null)) dataPrecisionValuebusiness=""; else
						 * dataPrecisionValuebusiness=String.valueOf(dataPrecisionBusiness);
						 * 
						 * if(!(dataPrecisonCondut.equals(dataPrecisionValuebusiness))) { itemIdSummary
						 * = " Business Id :["+entryA.getBusinessViewId()+"]"+" FieldID : [" +
						 * entryA.getmFieldId() + "], " + " dataprecision Value in condut: [" +
						 * dataPrecisonCondut + "], " + " data precision Value of business file [" +
						 * dataPrecisionBusiness+ "]"+System.lineSeparator();
						 * 
						 * }
						 */

						if (!condutLongname.equalsIgnoreCase(businessLongName)) {

							softAssert.fail("Error: Business Id :["+entryA.getBusinessViewId()+"]"+" FieldID : [" + entryA.getmFieldId() + "], " +
									" long Name in condut: [" + condutLongname + "], " +
									" long Name of business file [" + businessLongName+ "]"+System.lineSeparator());


						}

						if (!condutShortName.equalsIgnoreCase(businessShortName)) {
							softAssert.fail("Error: Business Id :["+entryA.getBusinessViewId()+"]"+" FieldID : [" + entryA.getmFieldId() + "], " +
									" short Name in condut: [" + condutShortName + "], " +
									" short Name of business file [" + businessShortName+ "]"+System.lineSeparator());



						}

						if(businessSystemName==null)
							businessSystemName="";

						/*
						 * if (!pickListSystemName.equalsIgnoreCase(businessSystemName)) { itemIdSummary
						 * += " Business Id :["+entryA.getBusinessViewId()+"]"+" FieldID : [" +
						 * entryA.getmFieldId() + "], " + " pickListSystemName in condut: [" +
						 * pickListSystemName + "], " + " pickListSystemName of business file [" +
						 * businessSystemName+ "]"+System.lineSeparator();
						 * 
						 * 
						 * }
						 */


						if (!fieldName.equalsIgnoreCase(businessFieldName)) {
							softAssert.fail("Error: Business Id :["+entryA.getBusinessViewId()+"]"+" FieldID : [" + entryA.getmFieldId() + "], " +
									" fieldName in condut: [" + fieldName + "], " +
									" businessFieldName of business file [" + businessFieldName+ "]"+System.lineSeparator());


						}

						if(businessStandardName==null)
							businessStandardName="";


						if (!standardName.equalsIgnoreCase(businessStandardName)) {
							softAssert.fail("Error: Business Id :["+entryA.getBusinessViewId()+"]"+" FieldID : [" + entryA.getmFieldId() + "], " +
									" standardName in condut: [" + standardName + "], " +
									" businessStandardName of business file [" + businessStandardName+ "]"+System.lineSeparator());

						}

						String maxValueCondut=metadata.getMaximumValue().get(i);
						String businessMaxValue=entryA.getmMaximumValue();


						String minValueCondut=metadata.getMinimumValue().get(i);
						String businessMinvalue=entryA.getmMinimumValue();

						if(minValueCondut.contains(".")) {
							minValueCondut=minValueCondut.substring(0,minValueCondut.indexOf("."));
						}

						if(maxValueCondut.contains(".")) {
							maxValueCondut=maxValueCondut.substring(0,maxValueCondut.indexOf("."));
						}

						if(businessMaxValue==null)
							businessMaxValue="";

						if(businessMinvalue==null)
							businessMinvalue="";
						if(maxValueCondut.equalsIgnoreCase("9223372036854775807"))
						{
							softAssert.assertEquals(businessMaxValue, "9007199254740991");


						}
						else {


							if (!maxValueCondut.equalsIgnoreCase(businessMaxValue)) {
								softAssert.fail("Error: Business Id :["+entryA.getBusinessViewId()+"]"+" FieldID : [" + entryA.getmFieldId() + "], " +
										" maxValue in condut: [" + maxValueCondut + "], " +
										" businessFieldName of business file [" + businessMaxValue+ "]"+System.lineSeparator());

							}
						}
						if(minValueCondut.equalsIgnoreCase("-9223372036854775808"))
						{

							softAssert.assertEquals(businessMinvalue, "-9007199254740991");

						}

						else
						{

							if (!minValueCondut.equalsIgnoreCase(businessMinvalue)) {
								softAssert.fail("Error: Business Id :["+entryA.getBusinessViewId()+"]"+" FieldID : [" + entryA.getmFieldId() + "], " +
										" minValue in condut: [" + minValueCondut + "], " +
										" businessMinvalue of business file [" + businessMinvalue+ "]"+System.lineSeparator());

							}
						}


						}

						if (!itemIdSummary.equals(Strings.EMPTY)) {
							comparisonSummary.add(itemIdSummary);
						}
					}
				}

			}

			else {
				
				List<Long> missingEntryInCondut = CommonUtilityMethods.getUncommonElementsLong(metadata.getFieldId(), getFieldIDs(businessViewFile));
				List<Long> missingEntryInBusinessViews = CommonUtilityMethods.getUncommonElementsLong(getFieldIDs(businessViewFile),metadata.getFieldId());
				if(missingEntryInBusinessViews.size() >0) {
					softAssert.fail("Error:"+missingEntryInBusinessViews.size()+ " fields missing in conduit view :"+businessViewsDataList.get(1).getBusinessViewId()+" and Resource: "+ businessViewsDataList.get(1).getResource()+ " and classname: "+businessViewsDataList.get(1).getClassName()+" fields missing are :"+missingEntryInBusinessViews+System.lineSeparator());
				}

				if(missingEntryInCondut.size() >0) {
					softAssert.fail("Error:"+missingEntryInCondut.size()+ " fields missing in business view :"+businessViewsDataList.get(1).getBusinessViewId()+" and Resource: "+ businessViewsDataList.get(1).getResource()+ " and classname: "+businessViewsDataList.get(1).getClassName()+" fields missing are :"+missingEntryInCondut+System.lineSeparator());
				}

			}

		}
					
		}
		
		else {
			softAssert.fail("Error:Artifacts file is empty");
		}
		softAssert.assertAll();
	}

	/**
	 * This method returns the list of fields Ids in a given specific view 
	 * @param businessViewFile
	 * @return
	 * @throws IOException
	 */
	public List<Long> getFieldIDs(String businessViewFile ) throws IOException{

		List<Long> fieldId = new ArrayList<>();
		List<FieldsPojo> businessViewsDataList = getBusinessViewsData(businessViewFile);
		for (FieldsPojo entryA : businessViewsDataList) {

			fieldId.add(entryA.getmFieldId());


		}
		return fieldId;
	}

}
