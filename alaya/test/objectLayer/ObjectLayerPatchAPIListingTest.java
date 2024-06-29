package bright.api.alaya.test.objectLayer;

import java.util.ArrayList;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import bright.api.alaya.pages.objectLayer.ObjectLayerDeleteAPIPage;
import bright.api.alaya.pages.objectLayer.ObjectLayerPage;
import bright.api.alaya.pages.objectLayer.ObjectLayerPatchAPIPage;
import bright.api.alaya.pages.objectLayer.ObjectLayerPostAPIPage;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ObjectLayerPatchAPIListingTest extends MainClassAlaya {

    /*Total cases - 9
     * Add media and verify structure
     * Replace operation and verify doc via get
     * Invalid locale
     * Invalid doc type
     * Invalid operation
     * Invalid parameter
     * UpdatedBy not given
     * Forbidden - wrong session
     * Request consistency not given
     * */
	
	public static String locale = "Bright";
	public static String listing = "listing";
	public static String docName = null;
	public static ArrayList<String> result = new ArrayList<String>();
	public static JSONObject payload = null;
	public static String response = null;
	public static JSONObject rulesData = new JSONObject();
	
	
	@Test ( groups = {"test", "dev"}, priority = 3, enabled = true, description = "Verify Success Status for delete listing")
	public void verifyDeleteSuccess() {	
		RequestSpecification httpRequest = null;	
		if(docName != null)
		ObjectLayerDeleteAPIPage.verifySuccessForDelete(httpRequest, locale, listing, docName);
		else
		logger.info("No document found for deletion");
	}
	
	@Test ( groups = {"test", "dev"}, priority = 1, enabled = true, description ="Verify media add and delete for listing") 
	public void addMedia() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(listing)){		
			result = ObjectLayerPostAPIPage.verifySuccessStatusforPOSTAPIObjectLayer(httpRequest,listing);
        	docName = result.get(0);
			JSONObject payload = CommonUtilityMethods.readJsonFile(property.getProperty("objectPatchPayloadJsonPath"),"listingMediaPayload_"+CommonUtilityMethods.getEnvironment());		
			Response patchResponse = ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"true","true",locale,docName,listing,ResponseCodes.SUCCESS,payload);		
			ObjectLayerPage.verifyPatchResponseStructure(patchResponse,docName,listing,"bright");
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(listing),"System toggle is disabled for document Type - "+listing);	
		}	
	}
	
	@Test ( groups = {"test", "dev"}, priority = 2, enabled = true, description ="Verify replace operation for listing") 
	public void replaceOperationListing() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(listing)){		
			JSONObject payload = CommonUtilityMethods.readJsonFile(property.getProperty("objectPatchPayloadJsonPath"),"replaceOp");		
			ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"true","true",locale,docName,listing,ResponseCodes.SUCCESS,payload);
			response=ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,listing,docName,ResponseCodes.SUCCESS).getBody().asString();	
			ObjectLayerPage.verifyReplaceOperationAfterPatch(response,112233,"listPrice");
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(listing),"System toggle is disabled for document Type - "+listing);	
		}	
	}
	
	@Test ( groups = {"test", "dev"}, priority = 2, enabled = true, description ="Verify Invalid locale for listing") 
	public void verifyInValidLocale() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(listing)){		
			JSONObject payload = CommonUtilityMethods.readJsonFile(property.getProperty("objectPatchPayloadJsonPath"),"listingMediaPayload_"+CommonUtilityMethods.getEnvironment());	
			ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"true","true","wrong",docName,"listing",ResponseCodes.NOT_FOUND,payload);		
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(listing),"System toggle is disabled for document Type - "+listing);	
		}	
	}
	
	@Test ( groups = {"test", "dev"}, priority = 2, enabled = true, description ="Verify Invalid doc type for listing") 
	public void verifyInValidDocType() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(listing)){		
			JSONObject payload = CommonUtilityMethods.readJsonFile(property.getProperty("objectPatchPayloadJsonPath"),"listingMediaPayload_"+CommonUtilityMethods.getEnvironment());	
			ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"true","true",locale,docName,"builder_model",ResponseCodes.NO_SUCH_KEY,payload);		
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(listing),"System toggle is disabled for document Type - "+listing);	
		}	
	}
	
	
	@Test ( groups = {"test", "dev"}, priority = 2, enabled = true, description ="Verify Invalid operation for listing") 
	public void verifyInValidOperation() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(listing)){		
			JSONObject payload = CommonUtilityMethods.readJsonFile(property.getProperty("objectPatchPayloadJsonPath"),"inValidPatchOperation");		
			ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"true","true",locale,docName,"listing",ResponseCodes.BAD_REQUEST,payload);		
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(listing),"System toggle is disabled for document Type - "+listing);	
		}	
	}
	
	
	@Test ( groups = {"test", "dev"}, priority = 2, enabled = true, description ="Verify Invalid parameter for listing") 
	public void verifyInValidParamter() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(listing)){		
			JSONObject payload = CommonUtilityMethods.readJsonFile(property.getProperty("objectPatchPayloadJsonPath"),"inValidPatchParameter");			
			ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"true","true",locale,docName,"listing",ResponseCodes.BAD_REQUEST,payload);			
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(listing),"System toggle is disabled for document Type - "+listing);	
		}	
	}
	
	@Test ( groups = {"test", "dev"}, priority = 2, enabled = true, description ="Verify not updated by for listing") 
	public void verifyNotUpdatedBy() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(listing)){		
			JSONObject payload = CommonUtilityMethods.readJsonFile(property.getProperty("objectPatchPayloadJsonPath"),"noLastUpdated");			
			ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"true","true",locale,docName,"listing",ResponseCodes.BAD_REQUEST,payload);			
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(listing),"System toggle is disabled for document Type - "+listing);	
		}	
	}
	
	@Test ( groups = {"test", "dev"}, priority = 2, enabled = true, description ="Verify forbidden status for listing") 
	public void verifyForbiddenPatch() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(listing)){		
			JSONObject payload = CommonUtilityMethods.readJsonFile(property.getProperty("objectPatchPayloadJsonPath"),"replaceOp");			
			ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"true","true",locale,docName,"listing",ResponseCodes.FORBIDDEN,payload);			
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(listing),"System toggle is disabled for document Type - "+listing);	
		}	
	}
	
	@Test ( groups = {"test", "dev"}, priority = 2, enabled = true, description ="Verify status if no request consistency is given for listing") 
	public void verifyNoRequestConsistency() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(listing)){
			JSONObject payload = CommonUtilityMethods.readJsonFile(property.getProperty("objectPatchPayloadJsonPath"),"replaceOp");			
			ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"false","true",locale,docName,"listing",ResponseCodes.SUCCESS_ACCEPTED,payload);			
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(listing),"System toggle is disabled for document Type - "+listing);	
		}	
	}
	
	

}
