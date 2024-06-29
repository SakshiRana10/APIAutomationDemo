package bright.api.alaya.test.objectLayer;

import java.util.ArrayList;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import bright.api.alaya.pages.objectLayer.ObjectLayerDeleteAPIPage;
import bright.api.alaya.pages.objectLayer.ObjectLayerMemberRulesPage;
import bright.api.alaya.pages.objectLayer.ObjectLayerPage;
import bright.api.alaya.pages.objectLayer.ObjectLayerPatchAPIPage;
import bright.api.alaya.pages.objectLayer.ObjectLayerPostAPIPage;
import bright.api.alaya.pages.objectLayer.ObjectLayerPutAPIPage;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ObjectLayerPatchAPIMemberTest extends MainClassAlaya {
	
	 /*Total cases - 17
	 * Delete document 
     * Add media and verify structure
     * Replace operation and verify doc via get
     * Invalid locale
     * Invalid doc type
     * Invalid operation
     * Invalid parameter
     * UpdatedBy not given
     * Forbidden - wrong session
     * Request consistency not given
     * Rules test cases - 7
     * */
	
	public static String locale = "Bright";
	public static String member = "member";
	public static String docName = null;
	public static ArrayList<String> result = new ArrayList<String>();
	public static JSONObject payload = null;
	public static String response = null;
	public static JSONObject rulesData = new JSONObject();
	
	
	@Test ( groups = {"test", "dev"}, priority = 2, enabled = true, description = "Verify Success Status for delete member")
	public void verifyDeleteSuccess() {	
		RequestSpecification httpRequest = null;	
		if(docName != null)
		ObjectLayerDeleteAPIPage.verifySuccessForDelete(httpRequest, locale, member, docName);
		else
		logger.info("No document found for deletion");
	}
	
	@Test ( groups = {"test", "dev"}, priority = 1, enabled = true, description ="Verify media add and delete for member") 
	public void addMedia() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(member)){		
			result = ObjectLayerPostAPIPage.verifySuccessStatusforPOSTAPIObjectLayer(httpRequest,member);
        	docName = result.get(0);
			JSONObject payload = CommonUtilityMethods.readJsonFile(property.getProperty("objectPatchPayloadJsonPath"),"memberMediaPayload_"+CommonUtilityMethods.getEnvironment());		
			Response patchResponse = ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"true","true",locale,docName,member,ResponseCodes.SUCCESS,payload);		
			ObjectLayerPage.verifyPatchResponseStructure(patchResponse,docName,member,"bright");
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(member),"System toggle is disabled for document Type - "+member);	
		}	
	}
	
	@Test ( groups = {"test", "dev"}, priority = 1, enabled = true, description ="Verify replace operation for member") 
	public void replaceOperationmember() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(member)){		
			JSONObject payload = CommonUtilityMethods.readJsonFile(property.getProperty("objectPatchPayloadJsonPath"),"memberReplaceOp");		
			ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"true","true",locale,docName,member,ResponseCodes.SUCCESS,payload);
			response=ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,member,docName,ResponseCodes.SUCCESS).getBody().asString();	
			ObjectLayerPage.verifyReplaceOperationAfterPatch(response,999,"memberStreetNumber");
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(member),"System toggle is disabled for document Type - "+member);	
		}	
	}
	
	@Test ( groups = {"test", "dev"}, priority = 1, enabled = true, description ="Verify Invalid locale for member") 
	public void verifyInValidLocale() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(member)){		
			JSONObject payload = CommonUtilityMethods.readJsonFile(property.getProperty("objectPatchPayloadJsonPath"),"memberMediaPayload_"+CommonUtilityMethods.getEnvironment());	
			ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"true","true","wrong",docName,"member",ResponseCodes.NOT_FOUND,payload);		
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(member),"System toggle is disabled for document Type - "+member);	
		}	
	}
	
	@Test ( groups = {"test", "dev"}, priority = 1, enabled = true, description ="Verify Invalid doc type for member") 
	public void verifyInValidDocType() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(member)){		
			JSONObject payload = CommonUtilityMethods.readJsonFile(property.getProperty("objectPatchPayloadJsonPath"),"memberMediaPayload_"+CommonUtilityMethods.getEnvironment());	
			ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"true","true",locale,docName,"builder_model",ResponseCodes.NO_SUCH_KEY,payload);		
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(member),"System toggle is disabled for document Type - "+member);	
		}	
	}
	
	
	@Test ( groups = {"test", "dev"}, priority = 1, enabled = true, description ="Verify Invalid operation for member") 
	public void verifyInValidOperation() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(member)){		
			JSONObject payload = CommonUtilityMethods.readJsonFile(property.getProperty("objectPatchPayloadJsonPath"),"inValidPatchOperation");		
			ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"true","true",locale,docName,"member",ResponseCodes.BAD_REQUEST,payload);		
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(member),"System toggle is disabled for document Type - "+member);	
		}	
	}
	
	
	@Test ( groups = {"test", "dev"}, priority = 1, enabled = true, description ="Verify Invalid parameter for member") 
	public void verifyInValidParamter() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(member)){		
			JSONObject payload = CommonUtilityMethods.readJsonFile(property.getProperty("objectPatchPayloadJsonPath"),"inValidPatchParameter");			
			ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"true","true",locale,docName,"member",ResponseCodes.BAD_REQUEST,payload);			
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(member),"System toggle is disabled for document Type - "+member);	
		}	
	}
	
	@Test ( groups = {"test", "dev"}, priority = 1, enabled = true, description ="Verify not updated by for member") 
	public void verifyNotUpdatedBy() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(member)){		
			JSONObject payload = CommonUtilityMethods.readJsonFile(property.getProperty("objectPatchPayloadJsonPath"),"memberNoLastUpdated");			
			ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"true","true",locale,docName,"member",ResponseCodes.BAD_REQUEST,payload);			
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(member),"System toggle is disabled for document Type - "+member);	
		}	
	}
	
	@Test ( groups = {"test", "dev"}, priority = 1, enabled = true, description ="Verify forbidden status for member") 
	public void verifyForbiddenPatch() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(member)){		
			JSONObject payload = CommonUtilityMethods.readJsonFile(property.getProperty("objectPatchPayloadJsonPath"),"memberReplaceOp");			
			ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"true","true",locale,docName,"member",ResponseCodes.FORBIDDEN,payload);			
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(member),"System toggle is disabled for document Type - "+member);	
		}	
	}
	
	@Test ( groups = {"test", "dev"}, priority = 1, enabled = true, description ="Verify status if no request consistency is given for member") 
	public void verifyNoRequestConsistency() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(member)){
			JSONObject payload = CommonUtilityMethods.readJsonFile(property.getProperty("objectPatchPayloadJsonPath"),"memberReplaceOp");			
			ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"false","true",locale,docName,"member",ResponseCodes.SUCCESS_ACCEPTED,payload);			
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(member),"System toggle is disabled for document Type - "+member);	
		}	
	}
	
    /* ------------------------------------------------RULES test cases---------------------------------------------------------*/
	
	@Test ( groups = {"test", "dev"}, priority = 2, enabled = true, description ="Verify all null values when business partner is Bright") 
	public void verifyallNullValuesCheckBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(member)){		
			rulesData = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"allNullBright").getJSONObject("rulesData");
			ObjectLayerPatchAPIPage.allNullValuesCheckBright(httpRequest,docName,locale,member,rulesData);
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(member),"System toggle is disabled for document Type - "+member);	
		}
	}
	
	@Test ( groups = {"test", "dev"}, priority = 2, enabled = true, description ="Verify all valid values when business partner is Bright") 
	public void verifyallValidValuesBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(member)){
			rulesData = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"allValidValuesBright").getJSONObject("rulesData");
			ObjectLayerPatchAPIPage.allValidValuesBrightMember(httpRequest,docName,locale,member,rulesData);
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(member),"System toggle is disabled for document Type - "+member);	
		}
	}
	
	@Test ( groups = {"test", "dev"}, priority = 2, enabled = true, description ="Verify valid county as not null , state as null and valid city when business partner is Bright") 
	public void verifycityNotNullCountyNotNullStateNullBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(member)){
			rulesData = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"StateNullBright").getJSONObject("rulesData");
			ObjectLayerPatchAPIPage.cityNotNullCountyNotNullStateNullBrightMember(httpRequest,docName,locale,member,rulesData);
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(member),"System toggle is disabled for document Type - "+member);	
		}	
	}
	
	@Test ( groups = {"test", "dev"}, priority = 2, enabled = true, description ="Verify city as null , valid county as null and  valid state  when business partner is Bright") 
	public void verifycitNullCountyNotNullStateNotNullBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(member)){
			rulesData = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"cityNullBright").getJSONObject("rulesData");
			ObjectLayerPatchAPIPage.citNullCountyNotNullStateNotNullBright(httpRequest,docName,locale,member,rulesData);
		}
		else
		   {
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(member),"System toggle is disabled for document Type - "+member);	
		   }
	}
	
	@Test ( groups = {"test", "dev"}, priority = 2, enabled = true, description ="Verify city as null , valid county as null and  state null  when business partner is Bright") 
	public void verifycityNullCountyNotNullStateNullBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(member)){
			rulesData = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"cityNullStateNullBright").getJSONObject("rulesData");
			ObjectLayerPatchAPIPage.cityNullCountyNotNullStateNullBright(httpRequest,docName,locale,member,rulesData);
		}
		else
		   {
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(member),"System toggle is disabled for document Type - "+member);	
		   }
	}
		
	@Test ( groups = {"test", "dev"}, priority = 2, enabled = true, description ="Verify city as null , county as null and  valid state when business partner is Bright") 
	public void verifycityNullCountyNullStateNotNullBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(member)){
			rulesData = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"cityNullCountyNullBright").getJSONObject("rulesData");
			ObjectLayerPatchAPIPage.cityNullCountyNullStateNotNullBright(httpRequest,docName,locale,member,rulesData);
		}
		else
		   {
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(member),"System toggle is disabled for document Type - "+member);	
		   }
	}
	
	@Test ( groups = {"test", "dev"}, priority = 2, enabled = true, description ="Verify invalid city  , county as null and  valid state when business partner is Bright") 
	public void verifycityInValidCountyNullStateNotNullBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(member)){
			rulesData = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"cityInvalidCountyNullBright").getJSONObject("rulesData");
			ObjectLayerPatchAPIPage.cityInValidCountyNullStateNotNullBright(httpRequest,docName,locale,member,rulesData);
		}
		else
		   {
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(member),"System toggle is disabled for document Type - "+member);	
		   }
	}

}
