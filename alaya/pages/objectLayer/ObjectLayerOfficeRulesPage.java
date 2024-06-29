package bright.api.alaya.pages.objectLayer;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import bright.api.alaya.pages.discoveryLayer.discoveryLayerPostAPIPage;
import bright.api.alaya.pages.discoveryLayer.discoveryLayerUtilityPage;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ObjectLayerOfficeRulesPage extends MainClassAlaya {
	static String listingDataJson;
	static JSONObject pickListState;
	static JSONObject pickListCounty;
	static JSONObject pickListStatus;
	static String statusReponse;
	static JSONObject itemsStatus;
	private static final String env = CommonUtilityMethods.getEnvironment();
    private static final String region = CommonUtilityMethods.getRegionString();



	public ObjectLayerOfficeRulesPage(){
		RequestSpecification httpRequest = null;
		ArrayList<String> AttributeNamesForES = new ArrayList<String>(Arrays.asList("officeKey"));

		listingDataJson=CommonUtilityMethods.fetchDocumentfromDocStore(httpRequest,"office",officeDocumentNames.get(0));	
		String stateReponse=getConfigAPIWithPicklistIdAndName("STATES_OR_PROVINCES");
		JSONObject items = new JSONObject(stateReponse).getJSONObject("item");
		pickListState=(JSONObject) items.get("pickListItems");
		String countyRespinse=getConfigAPIWithPicklistIdAndName("COUNTIES_OR_REGIONS");
		JSONObject itemsCounty = new JSONObject(countyRespinse).getJSONObject("item");
		pickListCounty=(JSONObject) itemsCounty.get("pickListItems");
		
		statusReponse=OfficeStaticRulePage.getConfigAPIWithPicklistIdAndName("OFFICE_STATUSES");
		 itemsStatus = new JSONObject(statusReponse).getJSONObject("item");
		pickListStatus=(JSONObject) itemsStatus.get("pickListItems");

	}

	public static void allNullValuesCheckBright(RequestSpecification httpRequest,String docName,String response,JSONObject rulesData) {
        SoftAssert softAssert = new SoftAssert();
			
		ObjectLayerPutAPIPage.verifySuccessPutCallAPIObjectLayer(httpRequest,response,docName,"office","parameterTrue");
		String getResponseAfterPut = ObjectLayerPage.getCallForObjectyLayer(httpRequest,"Bright","office",docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject getResponseAfterPutObj = new JSONObject(getResponseAfterPut);
		JSONObject result = getResponseAfterPutObj.getJSONObject("content").getJSONObject("lmsObject");
			
		String city = null;
		String county = null;
		boolean cityExist=true;
		cityExist=OfficeStaticRulePage.isCityExist(city,county);
		OfficeStaticRulePage.deleteCreatedCity(httpRequest,city,county,cityExist);

		if(!(Objects.equals(result.get("officeStateOrProvince").toString(),rulesData.get("state").toString()))) {
			softAssert.fail("Expected vaue of State:"+rulesData.get("state").toString()+" but state value from validate Api:"+result.get("officeStateOrProvince").toString());
		}
		if(!(Objects.equals(result.get("officeCounty").toString(),rulesData.get("county").toString()))) {
			softAssert.fail("Expected vaue of county:"+rulesData.get("county").toString()+" but county value from validate Api:"+result.get("officeCounty").toString());
		}
		if(!(Objects.equals(result.get("officeCity").toString(),rulesData.get("city").toString()))) {
			softAssert.fail("Expected vaue of city:"+rulesData.get("city").toString()+" but city value from validate Api:"+result.get("officeCity").toString());
		}
		softAssert.assertAll();
	}

	public static void allValidValuesBright(RequestSpecification httpRequest,String docName,String response,JSONObject rulesData) {
        SoftAssert softAssert = new SoftAssert();
		
		ObjectLayerPutAPIPage.verifySuccessPutCallAPIObjectLayer(httpRequest,response,docName,"office","parameterTrue");
		String getResponseAfterPut = ObjectLayerPage.getCallForObjectyLayer(httpRequest,"Bright","office",docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject getResponseAfterPutObj = new JSONObject(getResponseAfterPut);
		JSONObject result = getResponseAfterPutObj.getJSONObject("content").getJSONObject("lmsObject");
		
		boolean cityExist=true;
		cityExist=OfficeStaticRulePage.isCityExist(rulesData.get("city").toString(),rulesData.get("county").toString());
		OfficeStaticRulePage.deleteCreatedCity(httpRequest,rulesData.get("city").toString(),rulesData.get("county").toString(),cityExist);

		if(!(result.get("officeStateOrProvince").equals(null)? null : result.get("officeStateOrProvince").toString().equals(rulesData.get("state").toString()))) {
			softAssert.fail("Expected vaue of State:"+ rulesData.get("state").toString() +" but state value from validate Api:"+result.get("officeStateOrProvince").toString());
		}
		if(!(result.get("officeCounty").equals(null)? null : result.get("officeCounty").toString().equals(rulesData.get("county").toString()))) {
			softAssert.fail("Expected vaue of county:"+ rulesData.get("county").toString() +" but county value from validate Api:"+result.get("officeCounty").toString());
		}
		if(!(result.getString("officeCity").equals(null)? null : result.getString("officeCity").equals(rulesData.get("city").toString()))) {
			softAssert.fail("Expected vaue of city:"+rulesData.get("city").toString()+" but city value from validate Api:"+result.get("officeCity").toString());
		}
		softAssert.assertAll();
	}

	public static void citNullCountyNotNullStateNotNullBright(RequestSpecification httpRequest,String docName,String response,JSONObject rulesData) {
		SoftAssert softAssert = new SoftAssert();	
		
		ObjectLayerPutAPIPage.verifySuccessPutCallAPIObjectLayer(httpRequest,response,docName,"office","parameterTrue");
		String getResponseAfterPut = ObjectLayerPage.getCallForObjectyLayer(httpRequest,"Bright","office",docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject getResponseAfterPutObj = new JSONObject(getResponseAfterPut);
		JSONObject result = getResponseAfterPutObj.getJSONObject("content").getJSONObject("lmsObject");
		
		String city = null;
		boolean cityExist=true;
		cityExist=OfficeStaticRulePage.isCityExist(city,rulesData.get("county").toString());
		OfficeStaticRulePage.deleteCreatedCity(httpRequest,city,rulesData.get("county").toString(),cityExist);

		if(!(result.get("officeStateOrProvince").equals(null)? null : result.get("officeStateOrProvince").toString().equals(rulesData.get("state").toString()))) {
			softAssert.fail("Expected vaue of State:"+rulesData.get("state").toString()+" but state value from validate Api:"+result.get("officeStateOrProvince").toString());
		}
		if(!(result.get("officeCounty").equals(null)? null : result.get("officeCounty").toString().equals(rulesData.get("county").toString()))) {
			softAssert.fail("Expected vaue of county:"+rulesData.get("county").toString()+" but county value from validate Api:"+result.get("officeCounty").toString());
		}
		if(!(Objects.equals(result.get("officeCity").toString(),rulesData.get("city").toString()))) {
			softAssert.fail("Expected vaue of city:"+rulesData.get("city").toString()+" but city value from validate Api:"+result.get("officeCity").toString());
		}
		softAssert.assertAll();
	}
	
	public static void cityNullCountyNotNullStateNullBright(RequestSpecification httpRequest,String docName,String response,JSONObject rulesData) {
	    SoftAssert softAssert = new SoftAssert();
		
		ObjectLayerPutAPIPage.verifySuccessPutCallAPIObjectLayer(httpRequest,response,docName,"office","parameterTrue");
		String getResponseAfterPut = ObjectLayerPage.getCallForObjectyLayer(httpRequest,"Bright","office",docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject getResponseAfterPutObj = new JSONObject(getResponseAfterPut);
		JSONObject result = getResponseAfterPutObj.getJSONObject("content").getJSONObject("lmsObject");
		
		String city = null;
		boolean cityExist=true;
		cityExist=OfficeStaticRulePage.isCityExist(city,rulesData.get("county").toString());
		OfficeStaticRulePage.deleteCreatedCity(httpRequest,city,rulesData.get("county").toString(),cityExist);

		if(!(Objects.equals(result.get("officeStateOrProvince").toString(),rulesData.get("state").toString()))) {
			softAssert.fail("Expected vaue of State:"+rulesData.get("state").toString()+" but state value from validate Api:"+result.get("officeStateOrProvince").toString());
		}
		if(!((result.get("officeCounty").equals(null)? null : result.get("officeCounty").toString()).equals(rulesData.get("county").toString()))) {
			softAssert.fail("Expected vaue of county:"+rulesData.get("county").toString()+" but county value from validate Api:"+result.get("officeCounty").toString());
		}
		if(!(Objects.equals(result.get("officeCity").toString(),rulesData.get("city").toString()))) {
			softAssert.fail("Expected vaue of city:"+rulesData.get("city").toString()+" but city value from validate Api:"+result.get("officeCity").toString());
		}
		softAssert.assertAll();
	}


	public static void cityNullCountyNullStateNotNullBright(RequestSpecification httpRequest,String docName,String response,JSONObject rulesData) {
		SoftAssert softAssert = new SoftAssert();
			
		ObjectLayerPutAPIPage.verifySuccessPutCallAPIObjectLayer(httpRequest,response,docName,"office","parameterTrue");
		String getResponseAfterPut = ObjectLayerPage.getCallForObjectyLayer(httpRequest,"Bright","office",docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject getResponseAfterPutObj = new JSONObject(getResponseAfterPut);
		JSONObject result = getResponseAfterPutObj.getJSONObject("content").getJSONObject("lmsObject");
		String city = null;
		String county = null;
		boolean cityExist=true;
		cityExist=OfficeStaticRulePage.isCityExist(city,county);
		OfficeStaticRulePage.deleteCreatedCity(httpRequest,city,county,cityExist);

		if(!((result.get("officeStateOrProvince").equals(null)? null : result.get("officeStateOrProvince").toString()).equals(rulesData.get("state").toString()))) {
			softAssert.fail("Expected vaue of State:"+ rulesData.get("state").toString() +" but state value from validate Api:"+result.get("officeStateOrProvince").toString());
		}
		if(!(Objects.equals(result.get("officeCounty").toString(),rulesData.get("county").toString()))) {
			softAssert.fail("Expected vaue of county:"+ rulesData.get("county").toString() +" but county value from validate Api:"+result.get("officeCounty").toString());
		}
		if(!(Objects.equals(result.get("officeCity").toString(),rulesData.get("city").toString()))) {
			softAssert.fail("Expected vaue of city:"+ rulesData.get("city").toString() +" but city value from validate Api:"+result.get("officeCity").toString());
		}
		softAssert.assertAll();
	}


	public static void cityInValidCountyNullStateNotNullBright(RequestSpecification httpRequest,String docName, String response, JSONObject rulesData) {
		SoftAssert softAssert = new SoftAssert();
		
		ObjectLayerPutAPIPage.verifySuccessPutCallAPIObjectLayer(httpRequest,response,docName,"office","parameterTrue");
		String getResponseAfterPut = ObjectLayerPage.getCallForObjectyLayer(httpRequest,"Bright","office",docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject getResponseAfterPutObj = new JSONObject(getResponseAfterPut);
		JSONObject result = getResponseAfterPutObj.getJSONObject("content").getJSONObject("lmsObject");
		
		String county = null;
		boolean cityExist=true;
		cityExist=OfficeStaticRulePage.isCityExist(rulesData.get("city").toString(),county);
		OfficeStaticRulePage.deleteCreatedCity(httpRequest,rulesData.get("city").toString(),county,cityExist);

		String expectedCounty = null;

		try {
			JSONObject stateObject=ValidateAPIPage.getValueOfKeyInPicklist(pickListState, rulesData.get("state").toString(), "picklistItemId");
			String ExpectedCountyName="UNKNOWN-"+stateObject.getString("itemValue");
			expectedCounty =ValidateAPIPage.getParentKeyForItemId(pickListCounty, ExpectedCountyName, "itemValue");
			logger.info("Expected county is:"+expectedCounty);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(!(result.get("officeStateOrProvince").equals(null)? null : result.get("officeStateOrProvince").toString().equals(rulesData.get("state").toString()))) {
			softAssert.fail("Expected vaue of State:"+rulesData.get("state").toString()+" but state value from validate Api:"+result.get("officeStateOrProvince").toString());
		}
		if(!(Objects.equals(result.get("officeCounty").toString(),expectedCounty.toString()))) {
			softAssert.fail("Expected vaue of county:"+expectedCounty +" but county value from validate Api:"+result.get("officeCounty").toString());
		}
		if(!(result.getString("officeCity").equals(null)? null : result.getString("officeCity").equals(rulesData.get("city").toString()))) {
			softAssert.fail("Expected vaue of city:"+rulesData.get("city").toString()+" but city value from validate Api:"+result.get("officeCity").toString());
		}
		softAssert.assertAll();
	}



	/**
	 * A method to call get config api with picklist id and name
	 */
	public static String getConfigAPIWithPicklistIdAndName(String picklistName) {


		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpecForObjectLayer();
		RequestSpecification httpRequest = RestAssured.given();
		requestSpecification.basePath("config/bright/businessViews/brightOffice/picklists/{picklistName}");
		requestSpecification.pathParam("picklistName",  picklistName);
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.get();
		return response.asString();
	}

	public static boolean isCityExist(String city , String county) {

		city=city!=null ? city.toUpperCase():city;
		ArrayList<String> graphQlQuery = null;
		if(city!=null && county!=null) {
			graphQlQuery = CommonUtilityMethods.fetchCityKeyUsingCounty("city", city, county);

			if(graphQlQuery!=null) {
				logger.info("city exist ");


			}
			else {
				logger.info("creating new city doc");
				return false;
			}
		}

		return true;
	}


	public static void deleteCreatedCity(RequestSpecification httpRequest,String city , String county, boolean cityExist) {

		ArrayList<String> graphQlQueryAfterCityCreation = null;
		JSONObject responseBodyObj = new JSONObject();
		city=city!=null ? city.toUpperCase():city;
		if(city!=null && county!=null) {
			graphQlQueryAfterCityCreation = CommonUtilityMethods.fetchCityKeyUsingCounty("city", city, county);
			logger.info("Response of graphQL:"+graphQlQueryAfterCityCreation.toString());
			logger.info("after:"+graphQlQueryAfterCityCreation.size());
		}


		if(!cityExist ) {
			logger.info("New city doc got Triggered");

			JSONObject lmsObj= new JSONObject();
			String lastModified=null;
			String docType="city";
			String documentName=graphQlQueryAfterCityCreation.get(0);

			String payloadResponse = discoveryLayerUtilityPage.getCallForDiscoveryLayer(httpRequest,"Bright","true","true","city",documentName).getBody().asString();

			JSONObject result=new JSONObject(payloadResponse);
			JSONObject metaData = result.getJSONObject("metadata");
			lastModified=metaData.getString("lastModified");

			JSONObject payloadOBJ = new JSONObject(payloadResponse);
			JSONObject contentObj = new JSONObject(payloadResponse).getJSONObject("content");
			lmsObj = contentObj.getJSONObject("lmsObject");

			String docDeletionInfo = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath, "documentDeletionInfo")
					.toString();
			lmsObj.put("documentDeletionInformation", docDeletionInfo);
			contentObj.put("lmsObject", lmsObj);

			responseBodyObj.put("lastUpdatedBy", "AlayaAutomation");
			responseBodyObj.put("content", contentObj);
			payloadOBJ.put("content", contentObj);
			String payloadForPut = responseBodyObj.toString();
			Response responseFromPut = discoveryLayerUtilityPage.putCallForDiscoveryLayer(httpRequest, "true", "true", "true","Bright", docType, documentName, lastModified, payloadForPut);
			logger.info("Response from put - "+responseFromPut.getBody().asString());


			// Calling delete API of document store to complete the flow
			discoveryLayerPostAPIPage.deleteApiDocStore(httpRequest, docType, documentName);
		}

		else {
			logger.info("No new city created");
		}
	}
	
	
	public static void verifySetDefaultValuesActiveRules(RequestSpecification httpRequest,JSONObject role,String response,String docName,boolean sysPrCurrent, boolean sysPrLastCurrent,int rolesSize ) throws ParseException {
		String status = null;
		try {
			status = ValidateAPIPage.getParentKeyForItemId(pickListStatus, "Active", "displayValueLong");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		JSONObject resultant = createPayloadAndCallPutObject(role,status,httpRequest,response,docName);
		Assert.assertEquals(rolesSize, ValidateAPIPage.getAllRolesKey(resultant).size());
		if((sysPrCurrent == true || sysPrCurrent == false) && sysPrLastCurrent == false )
		{
		verifyOfficeRoleAddition(resultant,true);
		verifySystemBrightRoleAddition(resultant);	
		}
		
		else if(sysPrCurrent == true && sysPrLastCurrent == true )
		{
		verifySystemBrightRoleAddition(resultant);	
		}
		
		else if(sysPrCurrent == false && sysPrLastCurrent == true)
		{
		verifyOfficeRoleAddition(resultant,true);	
		}
	}
	
	public static void verifySetDefaultValuesInactiveRules(RequestSpecification httpRequest,JSONObject role,String response,String docName,boolean sysPrCurrent, boolean sysPrLastCurrent,int rolesSize ) throws ParseException {
		String status = null;
		try {
			status = ValidateAPIPage.getParentKeyForItemId(pickListStatus, "Inactive", "displayValueLong");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		JSONObject resultant = createPayloadAndCallPutObject(role,status,httpRequest,response,docName);
		Assert.assertEquals(rolesSize, ValidateAPIPage.getAllRolesKey(resultant).size());
		if((sysPrCurrent == true || sysPrCurrent == false) && sysPrLastCurrent == false)
		{
		verifyOfficeRoleAddition(resultant,false);
		verifySystemBrightRoleAddition(resultant);
		}
		else if(sysPrCurrent == true && sysPrLastCurrent == true)
		{
		verifySystemBrightRoleAddition(resultant);	
		}
		else if(sysPrCurrent == false && sysPrLastCurrent == true)
		{
		verifySystemBrightRoleAddition(resultant);		
		}
	}
	
	
	public static void verifySetDefaultValuesActiveRulesSystemBright(RequestSpecification httpRequest,JSONObject role,String response,String docName,boolean sysPrCurrent, boolean sysPrLastCurrent,int rolesSize ) throws ParseException {
		String status = null;
		try {
			status = ValidateAPIPage.getParentKeyForItemId(pickListStatus, "Active", "displayValueLong");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		JSONObject resultant = createPayloadAndCallPutObject(role,status,httpRequest,response,docName);
		Assert.assertEquals(rolesSize, ValidateAPIPage.getAllRolesKey(resultant).size());
		if((sysPrCurrent == true || sysPrCurrent == false) && sysPrLastCurrent == false )
		{
		verifyOfficeRoleAddition(resultant,true);
		verifySystemBrightRoleAddition(resultant);	
		}
		
		else if(sysPrCurrent == true && sysPrLastCurrent == true )
		{
		verifyOfficeRoleAddition(resultant,true);
		}
		
		else if(sysPrCurrent == false && sysPrLastCurrent == true)
		{
		verifyOfficeRoleAddition(resultant,true);	
		}
	}
	
	public static void verifySetDefaultValuesInactiveRulesSystemBright(RequestSpecification httpRequest,JSONObject role,String response,String docName,boolean sysPrCurrent, boolean sysPrLastCurrent,int rolesSize ) throws ParseException {
		String status = null;
		try {
			status = ValidateAPIPage.getParentKeyForItemId(pickListStatus, "Inactive", "displayValueLong");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		JSONObject resultant = createPayloadAndCallPutObject(role,status,httpRequest,response,docName);
		Assert.assertEquals(rolesSize, ValidateAPIPage.getAllRolesKey(resultant).size());
		if((sysPrCurrent == true || sysPrCurrent == false) && sysPrLastCurrent == false)
		{
		verifyOfficeRoleAddition(resultant,false);
		verifySystemBrightRoleAddition(resultant);
		}
		else if(sysPrCurrent == true && sysPrLastCurrent == true)
		{
		verifyOfficeRoleAddition(resultant,false);	
		}
		else if(sysPrCurrent == false && sysPrLastCurrent == true)
		{
		verifyOfficeRoleAddition(resultant,false);	
		}
	}
	
	


	public static void verifyNoRoleActive(RequestSpecification httpRequest,String response,String docName) throws ParseException {

		JSONObject newRole=new JSONObject();

		JSONObject responseObj = new JSONObject(response);
		JSONObject contentObj = responseObj.getJSONObject("content");
		JSONObject lmsobj = contentObj.getJSONObject("lmsObject");	
	
		JSONObject rolesJson = lmsobj.getJSONObject("roles");
		List<String> keysForRemoval = rolesJson.keySet()
				.stream()
				.collect(Collectors.toList());

		keysForRemoval.forEach(rolesJson::remove);
		
		String status = null;
		try {
			status = ValidateAPIPage.getParentKeyForItemId(pickListStatus, "Active", "displayValueLong");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		JSONObject resultant = createPayloadAndCallPutObject(rolesJson,status,httpRequest,response,docName);
		Assert.assertEquals(2, ValidateAPIPage.getAllRolesKey(resultant).size());
		verifyOfficeRoleAddition(resultant,true);
		verifySystemBrightRoleAddition(resultant);
		
	}
	
	
	public static void verifyNoRoleInActive(RequestSpecification httpRequest,String response,String docName) throws ParseException {
		JSONObject newRole=new JSONObject();

		JSONObject responseObj = new JSONObject(response);
		JSONObject contentObj = responseObj.getJSONObject("content");
		JSONObject lmsobj = contentObj.getJSONObject("lmsObject");	
	
		JSONObject rolesJson = lmsobj.getJSONObject("roles");
		List<String> keysForRemoval = rolesJson.keySet()
				.stream()
				.collect(Collectors.toList());

		keysForRemoval.forEach(rolesJson::remove);
		
		String status = null;
		try {
			status = ValidateAPIPage.getParentKeyForItemId(pickListStatus, "InActive", "displayValueLong");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		JSONObject resultant = createPayloadAndCallPutObject(rolesJson,status,httpRequest,response,docName);	
		Assert.assertEquals(2, ValidateAPIPage.getAllRolesKey(resultant).size());
		verifyOfficeRoleAddition(resultant,false);
		verifySystemBrightRoleAddition(resultant);
		
	}

	public static void verifyFlag(JSONObject resultant,boolean expectedSysPrCurrent) throws ParseException {
		
		JSONObject rolesJson = resultant.getJSONObject("roles");
		JSONObject rolesObject=ValidateAPIPage.getRoleJsonUsingAnyKey(rolesJson,StaticRuleTestDataPage.roleBrightKey,"sysPrKey");
		
		boolean sysPrCurrent=rolesObject.getBoolean("sysPrCurrent");
		Assert.assertEquals(sysPrCurrent, expectedSysPrCurrent);
		
	}
	
	public static void verifyOfficeRoleAddition(JSONObject resultantObj,boolean currentFlag) throws ParseException {

		JSONObject rolesJson = resultantObj.getJSONObject("roles");
		
		String roleId=ValidateAPIPage.getParentKeyForItemId(rolesJson,"Office","sysPrRoleName");
		if(roleId!=null) {
			JSONObject addedRole=ValidateAPIPage.getRoleJsonUsingAnyKey(rolesJson,"Office","sysPrRoleName");
			
			String databaseName=addedRole.getString("sourceDatabaseName");
			
		Assert.assertEquals(databaseName,"bright/office","databaseName not matched");
			String hostname=addedRole.getString("sourceHostName");
			
			String expectedHostname=String.format("au%s%sz1s3documentstoreinfra-docstore", region, CommonUtilityMethods.getEnvironment());
			Assert.assertEquals(hostname, expectedHostname);
			
			boolean sysPrCurrent=addedRole.getBoolean("sysPrCurrent");
			Assert.assertEquals(sysPrCurrent, currentFlag);
			boolean sysPrLastCurrent=addedRole.getBoolean("sysPrLastCurrent");
			Assert.assertEquals(sysPrLastCurrent, true);
		}
		else
		{
			Assert.fail("Office Role not added successfully");
		}
		

	}
	
	
	public static void verifySystemBrightRoleAddition(JSONObject resultantObj) throws ParseException {

		JSONObject rolesJson = resultantObj.getJSONObject("roles");
		
		String roleId=ValidateAPIPage.getParentKeyForItemId(rolesJson,"System-BRIGHT","sysPrRoleName");
		if(roleId!=null) {
		JSONObject addedRole=ValidateAPIPage.getRoleJsonUsingAnyKey(rolesJson,"System-BRIGHT","sysPrRoleName");
		
		String startDate=addedRole.getString("sysPrStartDate");
		if(startDate==null) {
			Assert.fail("Sart date is not added");
		}
		
		String databaseName=addedRole.getString("sourceDatabaseName");
		
		Assert.assertEquals(databaseName,"bright/office","databaseName not matched");
		String hostname=addedRole.getString("sourceHostName");
		
		String expectedHostname=String.format("au%s%sz1s3documentstoreinfra-docstore", region, CommonUtilityMethods.getEnvironment());
		Assert.assertEquals(hostname, expectedHostname);
		boolean sysPrCurrent=addedRole.getBoolean("sysPrCurrent");
		Assert.assertEquals(sysPrCurrent, true);
		boolean sysPrLastCurrent=addedRole.getBoolean("sysPrLastCurrent");
		Assert.assertEquals(sysPrLastCurrent, true);
		
		}
		
		else
		{
			Assert.fail("System-Bright Role not added successfully");
		}
	}

	@SuppressWarnings("null")
	public static JSONObject addRoles(boolean sysPrCurrent,boolean sysPrLastCurrent,String roleKey,String response){
        String timestamp = getCurrentTimeStamp(true);
		JSONObject newRole=new JSONObject();

		JSONObject responseObj = new JSONObject(response);
		JSONObject contentObj = responseObj.getJSONObject("content");
		JSONObject lmsobj = contentObj.getJSONObject("lmsObject");	
	
		JSONObject rolesJson = lmsobj.getJSONObject("roles");
		List<String> keysForRemoval = rolesJson.keySet()
				.stream()
				.collect(Collectors.toList());

		keysForRemoval.forEach(rolesJson::remove);
		lmsobj.put("roles", rolesJson);

		JSONObject roleValue=new JSONObject(StaticRuleTestDataPage.roleBrightValue);
		roleValue.put("sysPrCurrent", sysPrCurrent);
		roleValue.put("sysPrLastCurrent", sysPrLastCurrent);
		roleValue.put("sysPrStartDate", timestamp);
		roleValue.put("sysPrRoleKey", new BigInteger(roleKey));
		newRole.put(StaticRuleTestDataPage.roleBrightKey, roleValue);
		return newRole;

	}
	
	public static JSONObject createPayloadAndCallPutObject(JSONObject roles,String oficeStatus,RequestSpecification httpRequest,String response,String docName) {
		JSONObject responseObj = new JSONObject(response);
		JSONObject contentObj = responseObj.getJSONObject("content");
		JSONObject lmsObj = contentObj.getJSONObject("lmsObject");	
		lmsObj.put("roles", roles);
		long status=Long.parseLong(oficeStatus);
		lmsObj.put("officeStatus", status);
		contentObj.put("lmsObject", lmsObj);
		responseObj.put("content", contentObj);
		String payload = responseObj.toString();
		ObjectLayerPutAPIPage.verifySuccessPutCallAPIObjectLayer(httpRequest,payload,docName,"office","parameterTrue");
		String getResponseAfterPut = ObjectLayerPage.getCallForObjectyLayer(httpRequest,"Bright","office",docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject getResponseAfterPutObj = new JSONObject(getResponseAfterPut);
		JSONObject result = getResponseAfterPutObj.getJSONObject("content").getJSONObject("lmsObject");
		return result;
	}
	
	
	public static void checkPhoneNumberOther(RequestSpecification httpRequest,String response,String docName) {

		JSONObject responseObj = new JSONObject(response);
		JSONObject contentObj = responseObj.getJSONObject("content");
		JSONObject lmsobj = contentObj.getJSONObject("lmsObject");	
		
		lmsobj.put("officePhoneOther", StaticRuleTestDataPage.officePhoneOther);
		contentObj.put("lmsObject", lmsobj);
		responseObj.put("content", contentObj);
		String payload = responseObj.toString();

		ObjectLayerPutAPIPage.verifySuccessPutCallAPIObjectLayer(httpRequest,payload,docName,"office","parameterTrue");
		String getResponseAfterPut = ObjectLayerPage.getCallForObjectyLayer(httpRequest,"Bright","office",docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject getResponseAfterPutObj = new JSONObject(getResponseAfterPut);
		JSONObject result = getResponseAfterPutObj.getJSONObject("content").getJSONObject("lmsObject");
		String actualPhoneNo=result.getString("officePhoneOther");
		if(actualPhoneNo.contains("-")) {
			Assert.fail("Phone Nuber check failed ."+"Response is:"+actualPhoneNo);
		}
		

	}
	
	public static String getCurrentTimeStamp(boolean flag) {
		String str;
		if(flag) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			str = LocalDateTime.now().format(formatter);
		}
		
		else {
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			str = LocalDateTime.now().format(formatter);
		}
		

		return str;

	}
}
