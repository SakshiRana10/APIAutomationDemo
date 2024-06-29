package bright.api.alaya.test.objectLayer;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import bright.api.alaya.pages.objectLayer.ObjectLayerDeleteAPIPage;
import bright.api.alaya.pages.objectLayer.ObjectLayerPage;
import bright.api.alaya.pages.objectLayer.ObjectLayerPostAPIPage;
import bright.api.alaya.pages.objectLayer.ObjectLayerPutAPIPage;
import bright.api.alaya.pages.objectLayer.ObjectLayerOfficeRulesPage;
import bright.api.alaya.pages.objectLayer.StaticRuleTestDataPage;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import io.restassured.specification.RequestSpecification;

public class ObjectLayerPostAPIOfficeTest extends MainClassAlaya {
	
	/*Total Cases - 32*/
	
	
	public static String locale = "Bright";
	public static String office = "office";
	public static String docName = null;
	public static ArrayList<String> result = new ArrayList<String>();
	public static JSONObject payload = null;
	public static String response = null;
	public static JSONObject rulesData = new JSONObject();
	
	
	@Test (dependsOnMethods = {"verifySuccessForPOSTAPIObjectLayerWithNoRoles"}, groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Success Status for delete office")
	public void verifyDeleteSuccess() {	
		RequestSpecification httpRequest = null;	
		if(docName != null)
		ObjectLayerDeleteAPIPage.verifySuccessForDelete(httpRequest, locale, office, docName);
		else
		logger.info("No document found for deletion");
	}
	
	@Test ( groups = {"test","dev"}, priority = 0, enabled = true, description ="verify status 200 OK for POST API Object layer") 
	public void verifySuccessForPOSTAPIObjectLayerWithNoRoles() throws ParseException{
		RequestSpecification httpRequest= null;
		JSONObject postResultant;
		if(CommonUtilityMethods.getFeatureToggle(office)){
			/*POST call for object layer creating a new doc*/
        	result = ObjectLayerPostAPIPage.verifySuccessStatusforPOSTAPIObjectLayer(httpRequest,office);
        	docName = result.get(0);
        	payload = new JSONObject(result.get(1));
        	response=ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,office,docName,ResponseCodes.SUCCESS).getBody().asString();	
        	postResultant=new JSONObject(response).getJSONObject("content").getJSONObject("lmsObject");
			ObjectLayerOfficeRulesPage.verifyOfficeRoleAddition(postResultant,true);
        	ObjectLayerOfficeRulesPage.verifySystemBrightRoleAddition(postResultant);
        	ObjectLayerPostAPIPage.verifyHostnameAndSourceDataBaseName(response,office);
        	ObjectLayerPostAPIPage.verifyAddressContenation(response,office);
        	/*PUT call for object layer on the newly created doc*/
        	ObjectLayerPutAPIPage.verifySuccessPutCallAPIObjectLayer(httpRequest,response,docName,office,"parameterTrue");
		}
		else
		{
		    logger.info("Post call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(office),"System toggle is disabled for document Type - "+office);	
		}
	}
	


	@Test ( groups = {"test","dev"}, priority = 1, enabled = true, description ="verify status 200 OK for POST API Object layer") 
	public void verifyAttributesForPOSTAPIObjectLayer(){
		RequestSpecification httpRequest= null;
		ArrayList<String> AttributeNamesForDocStore = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.graphqlAttributes, "basicOffice").get("discovery"));
	    ArrayList<String> AttributeNamesForES = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.graphqlAttributes, "basicOffice").get("graphQl"));	
		if(CommonUtilityMethods.getFeatureToggle(office)){
        	ObjectLayerPostAPIPage.verifyattributesforPOSTAPIObjectLayer(httpRequest,office,docName,AttributeNamesForDocStore,AttributeNamesForES);	
		}
		else
		{
		    logger.info("Post call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(office),"System toggle is disabled for document Type - "+office);	
		}
	}
	
	@Test ( groups = {"test","dev"}, priority = 1, enabled = true, description ="verify response for wrong bearer token for POST API Object layer") 
	public void verifyResponseForWrongBearerTokenPOSTAPIObjectLayer(){
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(office)){
        	ObjectLayerPostAPIPage.verifyResponseForInvalidBearerTokenforPOSTAPIObjectLayer(httpRequest,office,docName,payload);
		}
		else
		{
		    logger.info("Post call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(office),"System toggle is disabled for document Type - "+office);	
		}
	}
	
	@Test ( groups = {"test","dev"}, priority = 1, enabled = true, description ="verify response for wrong session key for POST API Object layer") 
	public void verifyResponseForWrongSessionKeyTypePOSTAPIObjectLayer(){
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(office)){
        	ObjectLayerPostAPIPage.verifyResponseForInvalidSessionKeyforPOSTAPIObjectLayer(httpRequest,office,docName,payload);
		}
		else
		{
		    logger.info("Post call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(office),"System toggle is disabled for document Type - "+office);	
		}	
	}
	
	@Test ( groups = {"test","dev"}, priority = 1, enabled = true, description ="verify response for wrong doc type for POST API Object layer") 
	public void verifyResponseForWrongDocTypePOSTAPIObjectLayer(){
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(office)){
        	ObjectLayerPostAPIPage.verifyResponseForWrongDocType(httpRequest,office,docName,payload,"POST");
		}
		else
		{
		    logger.info("Post call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(office),"System toggle is disabled for document Type - "+office);	
		}
	}
	
	@Test ( groups = {"test","dev"}, priority = 1, enabled = true, description ="verify response for wrong locale for POST API Object layer") 
	public void verifyResponseForWrongLocalePOSTAPIObjectLayer(){
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(office)){
        	ObjectLayerPostAPIPage.verifyResponseForWrongLocale(httpRequest,office,docName,payload,"POST");
		}
		else
		{
		    logger.info("Post call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(office),"System toggle is disabled for document Type - "+office);	
		}	
	}
	
	/* ------------------------------------------------PUT test cases---------------------------------------------------------*/
	
	@Test ( groups = {"test","dev"}, priority = 2, enabled = true, description ="verify attributes for PUT API Object layer") 
	public void verifyAttributesForPutAPIObjectLayer(){
		RequestSpecification httpRequest= null;		
		ArrayList<String> AttributeNamesForDocStore = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.graphqlAttributes, "basicOffice").get("discovery"));
	    ArrayList<String> AttributeNamesForES = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.graphqlAttributes, "basicOffice").get("graphQl"));	
		if(CommonUtilityMethods.getFeatureToggle(office)){
			response=ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,office,docName,ResponseCodes.SUCCESS).getBody().asString();
			ObjectLayerPutAPIPage.verifySuccessPutCallAPIObjectLayer(httpRequest,response,docName,office,"parameterTrue");
        	ObjectLayerPutAPIPage.verifyattributesforPUTAPIObjectLayer(httpRequest,office,docName,AttributeNamesForDocStore,AttributeNamesForES);	
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(office),"System toggle is disabled for document Type - "+office);	
		}
	}
	
	@Test ( groups = {"test","dev"}, priority = 2, enabled = true, description ="verify response for wrong doc type for PUT API Object layer") 
	public void verifyResponseForWrongDocTypePUTAPIObjectLayer(){
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(office)){
			JSONObject payloadAfterPut = new JSONObject(response);
        	ObjectLayerPostAPIPage.verifyResponseForWrongDocType(httpRequest,office,docName,payloadAfterPut,"PUT");
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(office),"System toggle is disabled for document Type - "+office);	
		}
	}
	
	@Test ( groups = {"test","dev"}, priority = 2, enabled = true, description ="verify response for wrong locale for PUT API Object layer") 
	public void verifyResponseForWrongLocalePUTAPIObjectLayer(){
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(office)){
			JSONObject payloadAfterPut = new JSONObject(response);
        	ObjectLayerPostAPIPage.verifyResponseForWrongLocale(httpRequest,office,docName,payloadAfterPut,"PUT");
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(office),"System toggle is disabled for document Type - "+office);	
		}	
	}
	
	@Test ( groups = {"test","dev"}, priority = 2, enabled = true, description ="verify response for no parameters for PUT API Object layer") 
	public void verifyResponseForNoParametersPUTAPIObjectLayer(){
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(office)){
        	ObjectLayerPutAPIPage.verifySuccessPutCallAPIObjectLayer(httpRequest,response,docName,office,"noParameter");
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(office),"System toggle is disabled for document Type - "+office);	
		}	
	}
	
/* ------------------------------------------------RULES test cases---------------------------------------------------------*/
	
	
	@Test ( groups = {"test","dev"}, priority = 3, enabled = true, description ="Verify all null values when business partner is Bright") 
	public void verifyallNullValuesCheckBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(office)){
		ObjectLayerOfficeRulesPage ruleObject=new ObjectLayerOfficeRulesPage();
		rulesData = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"allNullBright").getJSONObject("rulesData");
		response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,office,docName,ResponseCodes.SUCCESS).getBody().asString();
		response = ObjectLayerPutAPIPage.modifyPayloadForOfficeRules(response,rulesData);
		ObjectLayerOfficeRulesPage.allNullValuesCheckBright(httpRequest,docName,response,rulesData);
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(office),"System toggle is disabled for document Type - "+office);	
		}	
	}
	
	@Test ( groups = {"test","dev"}, priority = 3, enabled = true, description ="Verify all valid values when business partner is Bright") 
	public void verifyallValidValuesBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(office)){
		ObjectLayerOfficeRulesPage ruleObject=new ObjectLayerOfficeRulesPage();
		rulesData = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"allValidValuesBright").getJSONObject("rulesData");
		response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,office,docName,ResponseCodes.SUCCESS).getBody().asString();
		response = ObjectLayerPutAPIPage.modifyPayloadForOfficeRules(response,rulesData);
		ObjectLayerOfficeRulesPage.allValidValuesBright(httpRequest,docName,response,rulesData);
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(office),"System toggle is disabled for document Type - "+office);	
		}	
	}
	
	@Test ( groups = {"test","dev"}, priority = 3, enabled = true, description ="Verify city null when business partner is Bright") 
	public void verifycitNullCountyNotNullStateNotNullBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(office)){
		ObjectLayerOfficeRulesPage ruleObject=new ObjectLayerOfficeRulesPage();
		rulesData = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"cityNullBright").getJSONObject("rulesData");
		response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,office,docName,ResponseCodes.SUCCESS).getBody().asString();
		response = ObjectLayerPutAPIPage.modifyPayloadForOfficeRules(response,rulesData);
		ObjectLayerOfficeRulesPage.citNullCountyNotNullStateNotNullBright(httpRequest,docName,response,rulesData);
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(office),"System toggle is disabled for document Type - "+office);	
		}	
	}
	
	@Test ( groups = {"test","dev"}, priority = 3, enabled = true, description ="Verify city and state as null when business partner is Bright") 
	public void verifycityNullCountyNotNullStateNullBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(office)){
		ObjectLayerOfficeRulesPage ruleObject=new ObjectLayerOfficeRulesPage();
		rulesData = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"cityNullStateNullBright").getJSONObject("rulesData");
		response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,office,docName,ResponseCodes.SUCCESS).getBody().asString();
		response = ObjectLayerPutAPIPage.modifyPayloadForOfficeRules(response,rulesData);
		ObjectLayerOfficeRulesPage.cityNullCountyNotNullStateNullBright(httpRequest,docName,response,rulesData);
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(office),"System toggle is disabled for document Type - "+office);	
		}	
	}
	
	
	@Test ( groups = {"test","dev"}, priority = 3, enabled = true, description ="Verify city and county as null when business partner is Bright") 
	public void verifycityNullCountyNullStateNotNullBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(office)){
		ObjectLayerOfficeRulesPage ruleObject=new ObjectLayerOfficeRulesPage();
		rulesData = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"cityNullCountyNullBright").getJSONObject("rulesData");
		response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,office,docName,ResponseCodes.SUCCESS).getBody().asString();
		response = ObjectLayerPutAPIPage.modifyPayloadForOfficeRules(response,rulesData);
		ObjectLayerOfficeRulesPage.cityNullCountyNullStateNotNullBright(httpRequest,docName,response,rulesData);
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(office),"System toggle is disabled for document Type - "+office);	
		}	
	}
	
	
	@Test ( groups = {"test","dev"}, priority = 3, enabled = true, description ="verify for invalid city when business partner is null") 
	public void verifycityInValidCountyNullStateNotNullBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(office)){
		ObjectLayerOfficeRulesPage ruleObject=new ObjectLayerOfficeRulesPage();
		rulesData = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"cityInvalidCountyNullBright").getJSONObject("rulesData");
		response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,office,docName,ResponseCodes.SUCCESS).getBody().asString();
		response = ObjectLayerPutAPIPage.modifyPayloadForOfficeRules(response,rulesData);
		ObjectLayerOfficeRulesPage.cityInValidCountyNullStateNotNullBright(httpRequest,docName,response,rulesData);
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(office),"System toggle is disabled for document Type - "+office);	
		}	
	}
	
	/*------------------------------------------------------------------------------------------------------------------*/
	
	@Test ( groups = {"test","dev"}, priority = 4, enabled = true, description ="verify status is active and SysPrCurrent is true") 
	public void verifyActivePrCurrentOffice() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException {
		RequestSpecification httpRequest= null;
		ObjectLayerOfficeRulesPage ruleObject=new ObjectLayerOfficeRulesPage();
		response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,office,docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject role=ObjectLayerOfficeRulesPage.addRoles(true,false,StaticRuleTestDataPage.sysPrKeyOfficeRole,response);
		ObjectLayerOfficeRulesPage.verifySetDefaultValuesActiveRules(httpRequest,role,response,docName,true,false,3);	
	}
	
	
	@Test ( groups = {"test","dev"}, priority = 4, enabled = true, description ="verify status is active and SysPrLastCurrent is true") 
	public void verifyActiveLastCurrentOffice() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException {
		RequestSpecification httpRequest= null;
		ObjectLayerOfficeRulesPage ruleObject=new ObjectLayerOfficeRulesPage();
		response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,office,docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject role=ObjectLayerOfficeRulesPage.addRoles(false,true,StaticRuleTestDataPage.sysPrKeyOfficeRole,response);
		ObjectLayerOfficeRulesPage.verifySetDefaultValuesActiveRules(httpRequest,role,response,docName,false,true,3);	
	}
	
	
	@Test ( groups = {"test","dev"}, priority = 4, enabled = true, description ="verify status is inactive and SysPrCurrent is true") 
	public void verifyInActivePrCurrentOffice() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException {
		RequestSpecification httpRequest= null;
		ObjectLayerOfficeRulesPage ruleObject=new ObjectLayerOfficeRulesPage();
		response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,office,docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject role=ObjectLayerOfficeRulesPage.addRoles(true,false,StaticRuleTestDataPage.sysPrKeyOfficeRole,response);
		ObjectLayerOfficeRulesPage.verifySetDefaultValuesInactiveRules(httpRequest,role,response,docName,true,false,3);
	}
	
	
	@Test ( groups = {"test","dev"}, priority = 4, enabled = true, description ="verify status is Inactive and SysPrLastCurrent is true") 
	public void verifyInActiveLastCurrentOffice() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException {
		RequestSpecification httpRequest= null;
		ObjectLayerOfficeRulesPage ruleObject=new ObjectLayerOfficeRulesPage();
		response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,office,docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject role=ObjectLayerOfficeRulesPage.addRoles(false,true,StaticRuleTestDataPage.sysPrKeyOfficeRole,response);
		ObjectLayerOfficeRulesPage.verifySetDefaultValuesInactiveRules(httpRequest,role,response,docName,false,true,2);	
	}
	
	
	@Test ( groups = {"test","dev"}, priority = 4, enabled = true, description ="verify status is active and SysPrCurrent and SysPrLastCurrent both are true") 
	public void verifyActivePrCurrentLastCurrentOffice() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException {
		RequestSpecification httpRequest= null;
		ObjectLayerOfficeRulesPage ruleObject=new ObjectLayerOfficeRulesPage();
		response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,office,docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject role=ObjectLayerOfficeRulesPage.addRoles(true,true,StaticRuleTestDataPage.sysPrKeyOfficeRole,response);
		ObjectLayerOfficeRulesPage.verifySetDefaultValuesActiveRules(httpRequest,role,response,docName,true,true,2);	
	}
	
	@Test ( groups = {"test","dev"}, priority = 4, enabled = true, description ="verify status is Inactive and SysPrCurrent and SysPrLastCurrent both are true") 
	public void verifyInActivePrCurrentLastCurrentOffice() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException {
		RequestSpecification httpRequest= null;
		ObjectLayerOfficeRulesPage ruleObject=new ObjectLayerOfficeRulesPage();
		response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,office,docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject role=ObjectLayerOfficeRulesPage.addRoles(true,true,StaticRuleTestDataPage.sysPrKeyOfficeRole,response);
		ObjectLayerOfficeRulesPage.verifySetDefaultValuesInactiveRules(httpRequest,role,response,docName,true,true,2);
	}

	
	@Test ( groups = {"test","dev"}, priority = 4, enabled = true, description ="verify status is active and SysPrCurrent and SysPrLastCurrent both are false") 
	public void verifyActiveNoPrCurrentnAndLastCurrentOffice() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException {
		RequestSpecification httpRequest= null;
		ObjectLayerOfficeRulesPage ruleObject=new ObjectLayerOfficeRulesPage();
		response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,office,docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject role=ObjectLayerOfficeRulesPage.addRoles(false,false,StaticRuleTestDataPage.sysPrKeyOfficeRole,response);
		ObjectLayerOfficeRulesPage.verifySetDefaultValuesActiveRules(httpRequest,role,response,docName,false,false,3);		
	}
	
	@Test ( groups = {"test","dev"}, priority = 4, enabled = true, description ="verify status is Inactive and SysPrCurrent and SysPrLastCurrent both are false") 
	public void verifyInActiveNoPrCurrentnAndLastCurrentOffice() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException {
		RequestSpecification httpRequest= null;
		ObjectLayerOfficeRulesPage ruleObject=new ObjectLayerOfficeRulesPage();
		response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,office,docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject role=ObjectLayerOfficeRulesPage.addRoles(false,false,StaticRuleTestDataPage.sysPrKeyOfficeRole,response);
		ObjectLayerOfficeRulesPage.verifySetDefaultValuesInactiveRules(httpRequest,role,response,docName,false,false,3);	
	}

	
//	@Test ( groups = {"test","dev"}, priority = 4, enabled = true, description ="verify status is active and SysPrCurrent is true") 
//	public void verifyInActivePrCurrentSystemBright() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException {
//		RequestSpecification httpRequest= null;
//		ObjectLayerOfficeRulesPage ruleObject=new ObjectLayerOfficeRulesPage();
//		response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,office,docName,ResponseCodes.SUCCESS).getBody().asString();
//		ObjectLayerOfficeRulesPage.verifyActivePrCurrentOffice(httpRequest);	
//	}
//	
//	@Test ( groups = {"test","dev"}, priority = 4, enabled = true, description ="verify status is active and SysPrCurrent is true") 
//	public void verifyActivePrCurrentLastCurrentSystemBright() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException {
//		RequestSpecification httpRequest= null;
//		ObjectLayerOfficeRulesPage ruleObject=new ObjectLayerOfficeRulesPage();
//		response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,office,docName,ResponseCodes.SUCCESS).getBody().asString();
//		ObjectLayerOfficeRulesPage.verifyActivePrCurrentOffice(httpRequest);
//	}
//	
//	@Test ( groups = {"test","dev"}, priority = 4, enabled = true, description ="verify status is active and SysPrCurrent is true") 
//	public void verifyInActivePrCurrentLastCurrentSystemBright() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException {
//		RequestSpecification httpRequest= null;
//		ObjectLayerOfficeRulesPage ruleObject=new ObjectLayerOfficeRulesPage();
//		response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,office,docName,ResponseCodes.SUCCESS).getBody().asString();
//		ObjectLayerOfficeRulesPage.verifyActivePrCurrentOffice(httpRequest);	
//	}
	
	@Test ( groups = {"test","dev"}, priority = 4, enabled = true, description ="verify status is active and SysPrCurrent is true") 
	public void verifyActivePrCurrentSystemBright() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException {
		RequestSpecification httpRequest= null;
		ObjectLayerOfficeRulesPage ruleObject=new ObjectLayerOfficeRulesPage();
		response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,office,docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject role=ObjectLayerOfficeRulesPage.addRoles(true,false,StaticRuleTestDataPage.sysPrKeySystemBrightRole,response);
		ObjectLayerOfficeRulesPage.verifySetDefaultValuesActiveRulesSystemBright(httpRequest,role,response,docName,true,false,3);	
	
	}
	
	@Test ( groups = {"test","dev"}, priority = 4, enabled = true, description ="verify status is active and SysPrLastCurrent is true") 
	public void verifyActiveLastCurrentOfficeSystemBright() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException {
		RequestSpecification httpRequest= null;
		ObjectLayerOfficeRulesPage ruleObject=new ObjectLayerOfficeRulesPage();
		response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,office,docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject role=ObjectLayerOfficeRulesPage.addRoles(false,true,StaticRuleTestDataPage.sysPrKeySystemBrightRole,response);
		ObjectLayerOfficeRulesPage.verifySetDefaultValuesActiveRulesSystemBright(httpRequest,role,response,docName,false,true,2);	
	}
	
	
	@Test ( groups = {"test","dev"}, priority = 4, enabled = true, description ="verify status is Inactive and SysPrLastCurrent is true") 
	public void verifyInActiveLastCurrentOfficeSystemBright() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException {
		RequestSpecification httpRequest= null;
		ObjectLayerOfficeRulesPage ruleObject=new ObjectLayerOfficeRulesPage();
		response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,office,docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject role=ObjectLayerOfficeRulesPage.addRoles(false,true,StaticRuleTestDataPage.sysPrKeySystemBrightRole,response);
		ObjectLayerOfficeRulesPage.verifySetDefaultValuesInactiveRulesSystemBright(httpRequest,role,response,docName,false,true,2);	
	}
	
	@Test ( groups = {"test","dev"}, priority = 4, enabled = true, description ="verify status is active and SysPrCurrent and sysPrLastCurrent is false") 
	public void verifyActiveNoPrCurrentnAndLastCurrentSystemBright() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException {
		RequestSpecification httpRequest= null;
		ObjectLayerOfficeRulesPage ruleObject=new ObjectLayerOfficeRulesPage();
		response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,office,docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject role=ObjectLayerOfficeRulesPage.addRoles(false,false,StaticRuleTestDataPage.sysPrKeySystemBrightRole,response);
		ObjectLayerOfficeRulesPage.verifySetDefaultValuesActiveRulesSystemBright(httpRequest,role,response,docName,false,false,3);	
	}
	
	@Test ( groups = {"test","dev"}, priority = 4, enabled = true, description ="verify status is Inactive and SysPrCurrent and sysPrLastCurrent is false") 
	public void verifyInActiveNoPrCurrentnAndLastCurrentSystemBright() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException {
		RequestSpecification httpRequest= null;
		ObjectLayerOfficeRulesPage ruleObject=new ObjectLayerOfficeRulesPage();
		response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,office,docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject role=ObjectLayerOfficeRulesPage.addRoles(false,false,StaticRuleTestDataPage.sysPrKeySystemBrightRole,response);
		ObjectLayerOfficeRulesPage.verifySetDefaultValuesInactiveRulesSystemBright(httpRequest,role,response,docName,false,false,3);	
	}
	
	@Test ( groups = {"test","dev"}, priority = 4, enabled = true, description ="verify status is active and SysPrCurrent is true") 
	public void verifyNoRoleActive() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException {
		RequestSpecification httpRequest= null;
		ObjectLayerOfficeRulesPage ruleObject=new ObjectLayerOfficeRulesPage();
		response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,office,docName,ResponseCodes.SUCCESS).getBody().asString();
		ObjectLayerOfficeRulesPage.verifyNoRoleActive(httpRequest,response,docName);	
	}
	
	@Test ( groups = {"test","dev"}, priority = 4, enabled = true, description ="verify status is active and SysPrCurrent is true") 
	public void verifyNoRoleInActive() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException {
		RequestSpecification httpRequest= null;
		ObjectLayerOfficeRulesPage ruleObject=new ObjectLayerOfficeRulesPage();
		response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,office,docName,ResponseCodes.SUCCESS).getBody().asString();
		ObjectLayerOfficeRulesPage.verifyNoRoleInActive(httpRequest,response,docName);
	}
	
	@Test ( groups = {"test","dev"}, priority = 4, enabled = true, description ="verify status is active and SysPrCurrent is true") 
	public void verifyPhoneNumberOther() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException {
		RequestSpecification httpRequest= null;
		ObjectLayerOfficeRulesPage ruleObject=new ObjectLayerOfficeRulesPage();
		response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,office,docName,ResponseCodes.SUCCESS).getBody().asString();
		ObjectLayerOfficeRulesPage.checkPhoneNumberOther(httpRequest,response,docName);	
	}
	
	/* --------------------------RULES test cases For non bright are not to be done yet-----------------------------------*/	
	
//	@Test ( groups = {"test","dev"}, priority = 0, enabled = true, description ="test") 
//	public void verifySetToOtherForNonBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
//		RequestSpecification httpRequest= null;
//		if(CommonUtilityMethods.getFeatureToggle(office)){
//		ObjectLayerOfficeRulesPage ruleObject=new ObjectLayerOfficeRulesPage();
//		rulesData = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"StateNullNonBrightOffice").getJSONObject("rulesData");
//		response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,office,docName,ResponseCodes.SUCCESS).getBody().asString();
//		response = ObjectLayerPutAPIPage.modifyPayloadForOfficeRules(response,rulesData);
//		ObjectLayerOfficeRulesPage.cityNotNullCountyNotNullStateNullNonBright(httpRequest,docName,response,rulesData);
//		}
//		else
//		{
//		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
//			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(office),"System toggle is disabled for document Type - "+office);	
//		}	
//	}
	
//	@Test ( groups = {"test","dev"}, priority = 0, enabled = true, description ="test") 
//	public void verifyallNullValuesCheckNonBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
//		RequestSpecification httpRequest= null;
//		if(CommonUtilityMethods.getFeatureToggle(office)){
//		ObjectLayerOfficeRulesPage ruleObject=new ObjectLayerOfficeRulesPage();
//		ObjectLayerOfficeRulesPage.allNullValuesCheckNonBright(httpRequest);
//		}
//		else
//		{
//		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
//			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(office),"System toggle is disabled for document Type - "+office);	
//		}	
//	}
	
//	@Test ( groups = {"test","dev"}, priority = 0, enabled = true, description ="test") 
//	public void verifyallValidValuesNonBright() throws InterruptedException, JsonMappingException, JsonProcessingException, NumberFormatException, JSONException, ParseException {
//		RequestSpecification httpRequest= null;
//		if(CommonUtilityMethods.getFeatureToggle(office)){
//		ObjectLayerOfficeRulesPage ruleObject=new ObjectLayerOfficeRulesPage();
//		ObjectLayerOfficeRulesPage.allValidValuesNonBright(httpRequest);
//		}
//		else
//		{
//		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
//			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(office),"System toggle is disabled for document Type - "+office);	
//		}	
//	}
	
//	@Test ( groups = {"test","dev"}, priority = 0, enabled = true, description ="test") 
//	public void verifycityNullCountyNotNullStateNotNullNonBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
//		RequestSpecification httpRequest= null;
//		if(CommonUtilityMethods.getFeatureToggle(office)){
//		ObjectLayerOfficeRulesPage ruleObject=new ObjectLayerOfficeRulesPage();
//		ObjectLayerOfficeRulesPage.cityNullCountyNotNullStateNotNullNonBright(httpRequest);
//		}
//		else
//		{
//		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
//			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(office),"System toggle is disabled for document Type - "+office);	
//		}	
//	}
	
//	@Test ( groups = {"test","dev"}, priority = 0, enabled = true, description ="test") 
//	public void verifycityInvalidCountyNotNullStateNotNullNonBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
//		RequestSpecification httpRequest= null;
//		if(CommonUtilityMethods.getFeatureToggle(office)){
//		ObjectLayerOfficeRulesPage ruleObject=new ObjectLayerOfficeRulesPage();
//		ObjectLayerOfficeRulesPage.cityInvalidCountyNotNullStateNotNullNonBright(httpRequest);
//		}
//		else
//		{
//		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
//			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(office),"System toggle is disabled for document Type - "+office);	
//		}	
//	}
//	
//	@Test ( groups = {"test","dev"}, priority = 0, enabled = true, description ="test") 
//	public void verifycityNotNullCountyNotNullStateNullNonBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
//		RequestSpecification httpRequest= null;
//		if(CommonUtilityMethods.getFeatureToggle(office)){
//		ObjectLayerOfficeRulesPage ruleObject=new ObjectLayerOfficeRulesPage();
//	//	ObjectLayerOfficeRulesPage.cityNotNullCountyNotNullStateNullNonBright(httpRequest);
//		}
//		else
//		{
//		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
//			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(office),"System toggle is disabled for document Type - "+office);	
//		}	
//	}
//	
//	@Test ( groups = {"test","dev"}, priority = 0, enabled = true, description ="test") 
//	public void verifycityNotNullCountyNullStateNotNullNonBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
//		RequestSpecification httpRequest= null;
//		if(CommonUtilityMethods.getFeatureToggle(office)){
//		ObjectLayerOfficeRulesPage ruleObject=new ObjectLayerOfficeRulesPage();
//		ObjectLayerOfficeRulesPage.cityNotNullCountyNullStateNotNullNonBright(httpRequest);
//		}
//		else
//		{
//		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
//			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(office),"System toggle is disabled for document Type - "+office);	
//		}	
//	}
}
	