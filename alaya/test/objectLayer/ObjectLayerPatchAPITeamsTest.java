package bright.api.alaya.test.objectLayer;

import java.util.ArrayList;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import bright.api.alaya.pages.objectLayer.ObjectLayerDeleteAPIPage;
import bright.api.alaya.pages.objectLayer.ObjectLayerPage;
import bright.api.alaya.pages.objectLayer.ObjectLayerPostAPIPage;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ObjectLayerPatchAPITeamsTest extends MainClassAlaya {


	public static String locale = "Bright";
	public static String team = "team";
	public static String docName = null;
	public static ArrayList<String> result = new ArrayList<String>();
	public static JSONObject payload = null;
	public static String response = null;
	public static JSONObject rulesData = new JSONObject();
	
	
	@Test ( groups = {"test", "dev"}, priority = 3, enabled = true, description = "Verify Success Status for delete team")
	public void verifyDeleteSuccess() {	
		RequestSpecification httpRequest = null;	
		if(docName != null)
		ObjectLayerDeleteAPIPage.verifySuccessForDelete(httpRequest, locale, team, docName);
		else
		logger.info("No document found for deletion");
	}
	
	
	
	@Test ( groups = {"test", "dev"}, priority = 1, enabled = true, description ="Verify replace operation for team") 
	public void replaceOperationteam() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(team)){		
			result = ObjectLayerPostAPIPage.verifySuccessStatusforPOSTAPIObjectLayer(httpRequest,team);
        	docName = result.get(0);
			JSONObject payload = CommonUtilityMethods.readJsonFile(property.getProperty("objectPatchPayloadJsonPath"),"validPatchParameterTeams");		
			ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"true","true",locale,docName,team,ResponseCodes.SUCCESS,payload);
			response=ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,team,docName,ResponseCodes.SUCCESS).getBody().asString();	
			ObjectLayerPage.verifyReplaceOperationAfterPatchTeams(response,"After patch TeamName");
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(team),"System toggle is disabled for document Type - "+team);	
		}	
	}
	
	@Test ( groups = {"test", "dev"}, priority = 2, enabled = true, description ="Verify Invalid locale for team") 
	public void verifyInValidLocale() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(team)){		
			JSONObject payload = CommonUtilityMethods.readJsonFile(property.getProperty("objectPatchPayloadJsonPath"),"validPatchParameterTeams");	
			ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"true","true","wrong",docName,"team",ResponseCodes.NOT_FOUND,payload);		
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(team),"System toggle is disabled for document Type - "+team);	
		}	
	}
	
	@Test ( groups = {"test", "dev"}, priority = 2, enabled = true, description ="Verify Invalid doc type for team") 
	public void verifyInValidDocType() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(team)){		
			JSONObject payload = CommonUtilityMethods.readJsonFile(property.getProperty("objectPatchPayloadJsonPath"),"validPatchParameterTeams");	
			ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"true","true",locale,docName,"builder_model",ResponseCodes.NO_SUCH_KEY,payload);		
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(team),"System toggle is disabled for document Type - "+team);	
		}	
	}
	
	
	@Test ( groups = {"test", "dev"}, priority = 2, enabled = true, description ="Verify Invalid operation for team") 
	public void verifyInValidOperation() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(team)){		
			JSONObject payload = CommonUtilityMethods.readJsonFile(property.getProperty("objectPatchPayloadJsonPath"),"inValidPatchOperation");		
			ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"true","true",locale,docName,"team",ResponseCodes.BAD_REQUEST,payload);		
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(team),"System toggle is disabled for document Type - "+team);	
		}	
	}
	
	
	@Test ( groups = {"test", "dev"}, priority = 2, enabled = true, description ="Verify Invalid parameter for team") 
	public void verifyInValidParamter() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(team)){		
			JSONObject payload = CommonUtilityMethods.readJsonFile(property.getProperty("objectPatchPayloadJsonPath"),"inValidPatchParameterTeams");			
			ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"true","true",locale,docName,"team",ResponseCodes.BAD_REQUEST,payload);			
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(team),"System toggle is disabled for document Type - "+team);	
		}	
	}
	
	@Test ( groups = {"test", "dev"}, priority = 2, enabled = true, description ="Verify not updated by for team") 
	public void verifyNotUpdatedBy() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(team)){		
			JSONObject payload = CommonUtilityMethods.readJsonFile(property.getProperty("objectPatchPayloadJsonPath"),"noLastUpdatedTeams");			
			ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"true","true",locale,docName,"team",ResponseCodes.BAD_REQUEST,payload);			
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(team),"System toggle is disabled for document Type - "+team);	
		}	
	}
	
	@Test ( groups = {"test", "dev"}, priority = 2, enabled = true, description ="Verify forbidden status for team") 
	public void verifyForbiddenPatch() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(team)){		
			JSONObject payload = CommonUtilityMethods.readJsonFile(property.getProperty("objectPatchPayloadJsonPath"),"validPatchParameterTeams");			
			ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"true","true",locale,docName,"team",ResponseCodes.FORBIDDEN,payload);			
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(team),"System toggle is disabled for document Type - "+team);	
		}	
	}
	
	@Test ( groups = {"test", "dev"}, priority = 2, enabled = true, description ="Verify status if no request consistency is given for team") 
	public void verifyNoRequestConsistency() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(team)){
			JSONObject payload = CommonUtilityMethods.readJsonFile(property.getProperty("objectPatchPayloadJsonPath"),"validPatchParameterTeams");			
			ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"false","true",locale,docName,"team",ResponseCodes.SUCCESS_ACCEPTED,payload);			
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(team),"System toggle is disabled for document Type - "+team);	
		}	
	}
}
