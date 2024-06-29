package bright.api.alaya.test.objectLayer;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import bright.api.alaya.pages.objectLayer.MemberSetDefaultValuesPage;
import bright.api.alaya.pages.objectLayer.ObjectLayerDeleteAPIPage;
import bright.api.alaya.pages.objectLayer.ObjectLayerPage;
import bright.api.alaya.pages.objectLayer.ObjectLayerPostAPIPage;
import bright.api.alaya.pages.objectLayer.ObjectLayerPutAPIPage;
import bright.api.alaya.pages.objectLayer.ObjectLayerMemberRulesPage;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import io.restassured.specification.RequestSpecification;

public class ObjectLayerPostAPIMemberTest  extends MainClassAlaya{

	
	/*Total Cases - 26
	 * delete call of object layer
	 * verify success status for post and put
	 * verify attributes after post
	 * wrong bearer token for post
	 * wrong session key for post
	 * wrong system locale for post
	 * wrong doc type for post
	 * verify attributes after put
	 * wrong system locale for put
	 * wrong doc type for put
	 * verify response for no parameters in put
	 * Rules test cases - 15
	 * */
	
	public static String locale = "Bright";
	public static String member = "member";
	public static String docName = null;
	public static ArrayList<String> result = new ArrayList<String>();
	public static JSONObject payload = null;
	public static String response = null;
	public static JSONObject rulesData = new JSONObject();
	
	
	@Test (dependsOnMethods = {"verifySuccessForPOSTAPIObjectLayerWithNoRoles"}, groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Success Status for delete member")
	public void verifyDeleteSuccess() {	
		RequestSpecification httpRequest = null;	
		if(docName != null)
		ObjectLayerDeleteAPIPage.verifySuccessForDelete(httpRequest, locale, member, docName);
		else
		logger.info("No document found for deletion");
	}
	
	@Test ( groups = {"test","dev"}, priority = 0, enabled = true, description ="verify status 200 OK for POST API Object layer") 
	public void verifySuccessForPOSTAPIObjectLayerWithNoRoles() throws ParseException{
		RequestSpecification httpRequest= null;
		JSONObject postResultant;
		if(CommonUtilityMethods.getFeatureToggle(member)){
        	result = ObjectLayerPostAPIPage.verifySuccessStatusforPOSTAPIObjectLayer(httpRequest,member);
        	docName = result.get(0);
        	payload = new JSONObject(result.get(1));
        	response=ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,member,docName,ResponseCodes.SUCCESS).getBody().asString();	
        	postResultant=new JSONObject(response).getJSONObject("content").getJSONObject("lmsObject");
			
        	ObjectLayerPostAPIPage.verifyHostnameAndSourceDataBaseName(response,member);
        	ObjectLayerPostAPIPage.verifyAddressContenation(response,member);
        	/*PUT call for object layer on the newly created doc*/
        	ObjectLayerPutAPIPage.verifySuccessPutCallAPIObjectLayer(httpRequest,response,docName,member,"parameterTrue");
		}
		else
		{
		    logger.info("Post call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(member),"System toggle is disabled for document Type - "+member);	
		}
	}
	
	

	@Test ( groups = {"test","dev"}, priority = 1, enabled = true, description ="verify status 200 OK for POST API Object layer") 
	public void verifyAttributesForPOSTAPIObjectLayer(){
		RequestSpecification httpRequest= null;
		ArrayList<String> AttributeNamesForDocStore = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.graphqlAttributes, "basicMember").get("discovery"));
	    ArrayList<String> AttributeNamesForES = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.graphqlAttributes, "basicMember").get("graphQl"));	
		if(CommonUtilityMethods.getFeatureToggle(member)){
        	ObjectLayerPostAPIPage.verifyattributesforPOSTAPIObjectLayer(httpRequest,member,docName,AttributeNamesForDocStore,AttributeNamesForES);	
		}
		else
		{
		    logger.info("Post call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(member),"System toggle is disabled for document Type - "+member);	
		}
	}
	
	@Test ( groups = {"test","dev"}, priority = 1, enabled = true, description ="verify response for wrong bearer token for POST API Object layer") 
	public void verifyResponseForWrongBearerTokenPOSTAPIObjectLayer(){
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(member)){
        	ObjectLayerPostAPIPage.verifyResponseForInvalidBearerTokenforPOSTAPIObjectLayer(httpRequest,member,docName,payload);
		}
		else
		{
		    logger.info("Post call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(member),"System toggle is disabled for document Type - "+member);	
		}
	}
	
	@Test ( groups = {"test","dev"}, priority = 1, enabled = true, description ="verify response for wrong session key for POST API Object layer") 
	public void verifyResponseForWrongSessionKeyTypePOSTAPIObjectLayer(){
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(member)){
        	ObjectLayerPostAPIPage.verifyResponseForInvalidSessionKeyforPOSTAPIObjectLayer(httpRequest,member,docName,payload);
		}
		else
		{
		    logger.info("Post call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(member),"System toggle is disabled for document Type - "+member);	
		}	
	}
	
	@Test ( groups = {"test","dev"}, priority = 1, enabled = true, description ="verify response for wrong doc type for POST API Object layer") 
	public void verifyResponseForWrongDocTypePOSTAPIObjectLayer(){
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(member)){
        	ObjectLayerPostAPIPage.verifyResponseForWrongDocType(httpRequest,member,docName,payload,"POST");
		}
		else
		{
		    logger.info("Post call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(member),"System toggle is disabled for document Type - "+member);	
		}
	}
	
	@Test ( groups = {"test","dev"}, priority = 1, enabled = true, description ="verify response for wrong locale for POST API Object layer") 
	public void verifyResponseForWrongLocalePOSTAPIObjectLayer(){
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(member)){
        	ObjectLayerPostAPIPage.verifyResponseForWrongLocale(httpRequest,member,docName,payload,"POST");
		}
		else
		{
		    logger.info("Post call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(member),"System toggle is disabled for document Type - "+member);	
		}	
	}
	
	
	/* ------------------------------------------------PUT test cases---------------------------------------------------------*/
	
	@Test ( groups = {"test","dev"}, priority = 2, enabled = true, description ="verify attributes for PUT API Object layer") 
	public void verifyAttributesForPutAPIObjectLayer(){
		RequestSpecification httpRequest= null;
		ArrayList<String> AttributeNamesForDocStore = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.graphqlAttributes, "basicMember").get("discovery"));
	    ArrayList<String> AttributeNamesForES = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.graphqlAttributes, "basicMember").get("graphQl"));	
		if(CommonUtilityMethods.getFeatureToggle(member)){
			response=ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,member,docName,ResponseCodes.SUCCESS).getBody().asString();
			ObjectLayerPutAPIPage.verifySuccessPutCallAPIObjectLayer(httpRequest,response,docName,member,"parameterTrue");
        	ObjectLayerPutAPIPage.verifyattributesforPUTAPIObjectLayer(httpRequest,member,docName,AttributeNamesForDocStore,AttributeNamesForES);	
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(member),"System toggle is disabled for document Type - "+member);	
		}
	}
	
	@Test ( groups = {"test","dev"}, priority = 2, enabled = true, description ="verify response for wrong doc type for PUT API Object layer") 
	public void verifyResponseForWrongDocTypePUTAPIObjectLayer(){
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(member)){
			JSONObject payloadAfterPut = new JSONObject(response);
        	ObjectLayerPostAPIPage.verifyResponseForWrongDocType(httpRequest,member,docName,payloadAfterPut,"PUT");
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(member),"System toggle is disabled for document Type - "+member);	
		}
	}
	
	@Test ( groups = {"test","dev"}, priority = 2, enabled = true, description ="verify response for wrong locale for PUT API Object layer") 
	public void verifyResponseForWrongLocalePUTAPIObjectLayer(){
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(member)){
			JSONObject payloadAfterPut = new JSONObject(response);
        	ObjectLayerPostAPIPage.verifyResponseForWrongLocale(httpRequest,member,docName,payloadAfterPut,"PUT");
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(member),"System toggle is disabled for document Type - "+member);	
		}	
	}
	
	@Test ( groups = {"test","dev"}, priority = 2, enabled = true, description ="verify response for no parameters for PUT API Object layer") 
	public void verifyResponseForNoParametersPUTAPIObjectLayer(){
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(member)){
        	ObjectLayerPutAPIPage.verifySuccessPutCallAPIObjectLayer(httpRequest,response,docName,member,"noParameter");
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(member),"System toggle is disabled for document Type - "+member);	
		}	
	}
	
	/* ------------------------------------------------RULES test cases---------------------------------------------------------*/
	
	@Test ( groups = {"test", "dev"}, priority = 3, enabled = true, description ="Verify all null values when business partner is Bright") 
	public void verifyallNullValuesCheckBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(member)){
			ObjectLayerMemberRulesPage ruleObject =new ObjectLayerMemberRulesPage();
			rulesData = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"allNullBright").getJSONObject("rulesData");
			response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,member,docName,ResponseCodes.SUCCESS).getBody().asString();
			response = ObjectLayerPutAPIPage.modifyPayloadForMemberRules(response,rulesData);
			ObjectLayerMemberRulesPage.allNullValuesCheckBright(httpRequest,docName,response,rulesData);
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(member),"System toggle is disabled for document Type - "+member);	
		}
	}
	
	@Test ( groups = {"test", "dev"}, priority = 3, enabled = true, description ="Verify all valid values when business partner is Bright") 
	public void verifyallValidValuesBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(member)){
			ObjectLayerMemberRulesPage ruleObject =new ObjectLayerMemberRulesPage();
			rulesData = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"allValidValuesBright").getJSONObject("rulesData");
			response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,member,docName,ResponseCodes.SUCCESS).getBody().asString();
			response = ObjectLayerPutAPIPage.modifyPayloadForMemberRules(response,rulesData);
			ObjectLayerMemberRulesPage.allValidValuesBright(httpRequest,docName,response,rulesData);
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(member),"System toggle is disabled for document Type - "+member);	
		}
	}
	
	
	
	@Test ( groups = {"test", "dev"}, priority = 3, enabled = true, description ="Verify valid county as not null , state as null and valid city when business partner is Bright") 
	public void verifycityNotNullCountyNotNullStateNullBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(member)){
			ObjectLayerMemberRulesPage ruleObject =new ObjectLayerMemberRulesPage();
			rulesData = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"StateNullBright").getJSONObject("rulesData");
			response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,member,docName,ResponseCodes.SUCCESS).getBody().asString();
			response = ObjectLayerPutAPIPage.modifyPayloadForMemberRules(response,rulesData);
			ObjectLayerMemberRulesPage.cityNotNullCountyNotNullStateNullBright(httpRequest,docName,response,rulesData);
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(member),"System toggle is disabled for document Type - "+member);	
		}	
	}
	
	@Test ( groups = {"test", "dev"}, priority = 3, enabled = true, description ="Verify city as null , valid county as null and  valid state  when business partner is Bright") 
	public void verifycitNullCountyNotNullStateNotNullBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(member)){
			ObjectLayerMemberRulesPage ruleObject =new ObjectLayerMemberRulesPage();
			rulesData = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"cityNullBright").getJSONObject("rulesData");
			response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,member,docName,ResponseCodes.SUCCESS).getBody().asString();
			response = ObjectLayerPutAPIPage.modifyPayloadForMemberRules(response,rulesData);
			ObjectLayerMemberRulesPage.citNullCountyNotNullStateNotNullBright(httpRequest,docName,response,rulesData);
		}
		else
		   {
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(member),"System toggle is disabled for document Type - "+member);	
		   }
	}
	
	@Test ( groups = {"test", "dev"}, priority = 3, enabled = true, description ="Verify city as null , valid county as null and  state null  when business partner is Bright") 
	public void verifycityNullCountyNotNullStateNullBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(member)){
			ObjectLayerMemberRulesPage ruleObject =new ObjectLayerMemberRulesPage();
			rulesData = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"cityNullStateNullBright").getJSONObject("rulesData");
			response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,member,docName,ResponseCodes.SUCCESS).getBody().asString();
			response = ObjectLayerPutAPIPage.modifyPayloadForMemberRules(response,rulesData);
			ObjectLayerMemberRulesPage.cityNullCountyNotNullStateNullBright(httpRequest,docName,response,rulesData);
		}
		else
		   {
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(member),"System toggle is disabled for document Type - "+member);	
		   }
	}
	
	
	@Test ( groups = {"test", "dev"}, priority = 3, enabled = true, description ="Verify city as null , county as null and  valid state when business partner is Bright") 
	public void verifycityNullCountyNullStateNotNullBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(member)){
			ObjectLayerMemberRulesPage ruleObject =new ObjectLayerMemberRulesPage();
			rulesData = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"cityNullCountyNullBright").getJSONObject("rulesData");
			response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,member,docName,ResponseCodes.SUCCESS).getBody().asString();
			response = ObjectLayerPutAPIPage.modifyPayloadForMemberRules(response,rulesData);
			ObjectLayerMemberRulesPage.cityNullCountyNullStateNotNullBright(httpRequest,docName,response,rulesData);
		}
		else
		   {
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(member),"System toggle is disabled for document Type - "+member);	
		   }
	}
	
	
	@Test ( groups = {"test", "dev"}, priority = 3, enabled = true, description ="Verify invalid city  , county as null and  valid state when business partner is Bright") 
	public void verifycityInValidCountyNullStateNotNullBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(member)){
			ObjectLayerMemberRulesPage ruleObject =new ObjectLayerMemberRulesPage();
			rulesData = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"cityInvalidCountyNullBright").getJSONObject("rulesData");
			response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,member,docName,ResponseCodes.SUCCESS).getBody().asString();
			response = ObjectLayerPutAPIPage.modifyPayloadForMemberRules(response,rulesData);
			ObjectLayerMemberRulesPage.cityInValidCountyNullStateNotNullBright(httpRequest,docName,response,rulesData);
		}
		else
		   {
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(member),"System toggle is disabled for document Type - "+member);	
		   }
	}
	
	/* --------------------------RULES test cases For non bright are not to be done yet-----------------------------------*/
	
//	@Test ( groups = {"test","dev"}, priority = 3, enabled = true, description ="verify Set To Other For NonBright for PUT API Object layer") 
//	public void verifySetToOtherForNonBright(){
//		RequestSpecification httpRequest= null;
//		if(CommonUtilityMethods.getFeatureToggle(member)){
//			ObjectLayerMemberRulesPage ruleObject =new ObjectLayerMemberRulesPage();
//			rulesData = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"StateNullNonBright").getJSONObject("rulesData");
//			response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,member,docName,ResponseCodes.SUCCESS).getBody().asString();
//			response = ObjectLayerPutAPIPage.modifyPayloadForMemberRules(response,rulesData);
//			ObjectLayerMemberRulesPage.cityNotNullCountyNotNullStateNullNonBright(httpRequest,docName,response,rulesData);
//		}
//		else
//		{
//		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
//			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(member),"System toggle is disabled for document Type - "+member);	
//		}	
//	}
//	
//	@Test ( groups = {"test","dev"}, priority = 3, enabled = true, description ="Verify all null values when business partner is Non Bright") 
//	public void verifyallNullValuesCheckNonBright(){
//		RequestSpecification httpRequest= null;
//		if(CommonUtilityMethods.getFeatureToggle(member)){
//			ObjectLayerMemberRulesPage ruleObject =new ObjectLayerMemberRulesPage();
//			rulesData = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"allNullNonBright").getJSONObject("rulesData");
//			response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,member,docName,ResponseCodes.SUCCESS).getBody().asString();
//			response = ObjectLayerPutAPIPage.modifyPayloadForMemberRules(response,rulesData);	
//			ObjectLayerMemberRulesPage.allNullValuesCheckNonBright(httpRequest,docName,response,rulesData);
//		}
//		else
//		{
//		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
//			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(member),"System toggle is disabled for document Type - "+member);	
//		}	
//	}
//	
//	@Test ( groups = {"test","dev"}, priority = 3, enabled = true, description ="Verify all valid values when business partner is Non Bright") 
//	public void verifyallValidValuesNonBright() throws NumberFormatException, JSONException, ParseException {
//		RequestSpecification httpRequest= null;
//		if(CommonUtilityMethods.getFeatureToggle(member)){
//			ObjectLayerMemberRulesPage ruleObject =new ObjectLayerMemberRulesPage();
//			response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,member,docName,ResponseCodes.SUCCESS).getBody().asString();
//			ObjectLayerMemberRulesPage.allValidValuesNonBright(httpRequest,docName,response);
//		}
//		else
//		{
//		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
//			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(member),"System toggle is disabled for document Type - "+member);	
//		}	
//	}
//	
//	@Test ( groups = {"test","dev"}, priority = 3, enabled = true, description ="Verify county as null and state as null when business partner is Non Bright") 
//	public void  verifycityNullCountyNotNullStateNotNullNonBright(){
//		RequestSpecification httpRequest= null;
//		if(CommonUtilityMethods.getFeatureToggle(member)){
//			ObjectLayerMemberRulesPage ruleObject =new ObjectLayerMemberRulesPage();
//			rulesData = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"cityNullNonBright").getJSONObject("rulesData");
//			response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,member,docName,ResponseCodes.SUCCESS).getBody().asString();
//			response = ObjectLayerPutAPIPage.modifyPayloadForMemberRules(response,rulesData);
//			ObjectLayerMemberRulesPage.cityNullCountyNotNullStateNotNullNonBright(httpRequest,docName,response,rulesData);
//		}
//		else
//		{
//		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
//			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(member),"System toggle is disabled for document Type - "+member);	
//		}	
//	}
//	
//	@Test ( groups = {"test","dev"}, priority = 3, enabled = true, description ="Verify county as null , state as null and city as invalid  when business partner is Non Bright") 
//	public void  verifycityInvalidCountyNotNullStateNotNullNonBright(){
//		RequestSpecification httpRequest= null;
//		if(CommonUtilityMethods.getFeatureToggle(member)){
//			ObjectLayerMemberRulesPage ruleObject =new ObjectLayerMemberRulesPage();
//			rulesData = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"cityInvalidNonBright").getJSONObject("rulesData");
//			response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,member,docName,ResponseCodes.SUCCESS).getBody().asString();
//			response = ObjectLayerPutAPIPage.modifyPayloadForMemberRules(response,rulesData);
//			ObjectLayerMemberRulesPage.cityInvalidCountyNotNullStateNotNullNonBright(httpRequest,docName,response,rulesData);
//		}
//		else
//		{
//		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
//			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(member),"System toggle is disabled for document Type - "+member);	
//		}	
//	}
//	
//	@Test ( groups = {"test", "dev"}, priority = 3, enabled = true, description ="Verify valid county as null , state as null and valid city when business partner is Non Bright") 
//	public void verifycityNotNullCountyNotNullStateNullNonBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
//		RequestSpecification httpRequest= null;
//		if(CommonUtilityMethods.getFeatureToggle(member)){
//			ObjectLayerMemberRulesPage ruleObject =new ObjectLayerMemberRulesPage();
//			rulesData = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"StateNullNonBright").getJSONObject("rulesData");
//			response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,member,docName,ResponseCodes.SUCCESS).getBody().asString();
//			response = ObjectLayerPutAPIPage.modifyPayloadForMemberRules(response,rulesData);
//			ObjectLayerMemberRulesPage.cityNotNullCountyNotNullStateNullNonBright(httpRequest,docName,response,rulesData);
//		}
//		else
//		{
//		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
//			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(member),"System toggle is disabled for document Type - "+member);	
//		}	
//	}
//	
//	@Test ( groups = {"test", "dev"}, priority = 3, enabled = true, description ="Verify valid county as null ,  valid state  and valid city when business partner is Non Bright") 
//	public void verifycityNotNullCountyNullStateNotNullNonBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
//		RequestSpecification httpRequest= null;
//		if(CommonUtilityMethods.getFeatureToggle(member)){
//			ObjectLayerMemberRulesPage ruleObject =new ObjectLayerMemberRulesPage();
//			rulesData = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"CountyNullNonBright").getJSONObject("rulesData");
//			response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,member,docName,ResponseCodes.SUCCESS).getBody().asString();
//			response = ObjectLayerPutAPIPage.modifyPayloadForMemberRules(response,rulesData);
//			ObjectLayerMemberRulesPage.cityNotNullCountyNullStateNotNullNonBright(httpRequest,docName,response,rulesData);
//		}
//		else
//		{
//		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
//			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(member),"System toggle is disabled for document Type - "+member);	
//		}	
//	}
	
}
