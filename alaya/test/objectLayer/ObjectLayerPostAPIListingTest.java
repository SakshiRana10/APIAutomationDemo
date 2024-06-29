package bright.api.alaya.test.objectLayer;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import bright.api.alaya.pages.objectLayer.ObjectLayerDeleteAPIPage;
import bright.api.alaya.pages.objectLayer.ObjectLayerGetAPIPage;
import bright.api.alaya.pages.objectLayer.ObjectLayerPage;
import bright.api.alaya.pages.objectLayer.ObjectLayerPostAPIPage;
import bright.api.alaya.pages.objectLayer.ObjectLayerPutAPIPage;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import io.restassured.specification.RequestSpecification;

public class ObjectLayerPostAPIListingTest extends MainClassAlaya {
	
	/*Total Cases - 11
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
	 * */
	
	public static String locale = "Bright";
	public static String listing = "listing";
	public static String docName = null;
	public static ArrayList<String> result = new ArrayList<String>();
	public static JSONObject payload = null;
	public static String response = null;
	
	
	@Test (dependsOnMethods = {"verifySuccessForPOSTAPIObjectLayer"}, groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Success Status for delete listing")
	public void verifyDeleteSuccess() {	
		RequestSpecification httpRequest = null;	
		if(docName != null)
		ObjectLayerDeleteAPIPage.verifySuccessForDelete(httpRequest, locale, listing, docName);
		else
		logger.info("No document found for deletion");
	}
	
	@Test ( groups = {"test","dev"}, priority = 0, enabled = true, description ="verify status 200 OK for POST API Object layer") 
	public void verifySuccessForPOSTAPIObjectLayer(){
		RequestSpecification httpRequest= null;
		
		if(CommonUtilityMethods.getFeatureToggle(listing))
		{
			/*POST call for object layer creating a new doc*/
			result = ObjectLayerPostAPIPage.verifySuccessStatusforPOSTAPIObjectLayer(httpRequest,listing);	
        	docName = result.get(0);
        	payload = new JSONObject(result.get(1));
        	response=ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,listing,docName,ResponseCodes.SUCCESS).getBody().asString();	
        	ObjectLayerPostAPIPage.verifyHostnameAndSourceDataBaseName(response,listing);
        	/*PUT call for object layer on the newly created doc*/
        	ObjectLayerPutAPIPage.verifySuccessPutCallAPIObjectLayer(httpRequest,response,docName,listing,"parameterTrue");
		}
		else
		{
		    logger.info("Post call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(listing),"System toggle is disabled for document Type - "+listing);	
		}
	}
	@Test ( groups = {"test","dev"}, priority = 1, enabled = true, description ="verify status 200 OK for POST API Object layer") 
	public void verifyAttributesForPOSTAPIObjectLayer(){
		RequestSpecification httpRequest= null;
		ArrayList<String> AttributeNamesForDocStore = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.graphqlAttributes, "basicListing").get("discovery"));
	    ArrayList<String> AttributeNamesForES = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.graphqlAttributes, "basicListing").get("graphQl"));	
		if(CommonUtilityMethods.getFeatureToggle(listing)){
        	ObjectLayerPostAPIPage.verifyattributesforPOSTAPIObjectLayer(httpRequest,listing,docName,AttributeNamesForDocStore,AttributeNamesForES);	
		}
		else
		{
		    logger.info("Post call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(listing),"System toggle is disabled for document Type - "+listing);	
		}
	}
	
	@Test ( groups = {"test","dev"}, priority = 1, enabled = true, description ="verify response for wrong bearer token for POST API Object layer") 
	public void verifyResponseForWrongBearerTokenPOSTAPIObjectLayer(){
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(listing)){
        	ObjectLayerPostAPIPage.verifyResponseForInvalidBearerTokenforPOSTAPIObjectLayer(httpRequest,listing,docName,payload);
		}
		else
		{
		    logger.info("Post call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(listing),"System toggle is disabled for document Type - "+listing);	
		}
	}
	
	@Test ( groups = {"test","dev"}, priority = 1, enabled = true, description ="verify response for wrong session key for POST API Object layer") 
	public void verifyResponseForWrongSessionKeyTypePOSTAPIObjectLayer(){
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(listing)){
        	ObjectLayerPostAPIPage.verifyResponseForInvalidSessionKeyforPOSTAPIObjectLayer(httpRequest,listing,docName,payload);
		}
		else
		{
		    logger.info("Post call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(listing),"System toggle is disabled for document Type - "+listing);	
		}	
	}
	
	@Test ( groups = {"test","dev"}, priority = 1, enabled = true, description ="verify response for wrong doc type for POST API Object layer") 
	public void verifyResponseForWrongDocTypePOSTAPIObjectLayer(){
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(listing)){
        	ObjectLayerPostAPIPage.verifyResponseForWrongDocType(httpRequest,listing,docName,payload,"POST");
		}
		else
		{
		    logger.info("Post call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(listing),"System toggle is disabled for document Type - "+listing);	
		}
	}
	
	@Test ( groups = {"test","dev"}, priority = 1, enabled = true, description ="verify response for wrong locale for POST API Object layer") 
	public void verifyResponseForWrongLocalePOSTAPIObjectLayer(){
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(listing)){
        	ObjectLayerPostAPIPage.verifyResponseForWrongLocale(httpRequest,listing,docName,payload,"POST");
		}
		else
		{
		    logger.info("Post call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(listing),"System toggle is disabled for document Type - "+listing);	
		}	
	}
	
	/* ------------------------------------------------PUT test cases---------------------------------------------------------*/
	
	@Test ( groups = {"test","dev"}, priority = 2, enabled = true, description ="verify attributes for PUT API Object layer") 
	public void verifyAttributesForPutAPIObjectLayer(){
		RequestSpecification httpRequest= null;
		ArrayList<String> AttributeNamesForDocStore = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.graphqlAttributes, "basicListing").get("discovery"));
	    ArrayList<String> AttributeNamesForES = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.graphqlAttributes, "basicListing").get("graphQl"));	
		if(CommonUtilityMethods.getFeatureToggle(listing)){
			response=ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,listing,docName,ResponseCodes.SUCCESS).getBody().asString();
			ObjectLayerPutAPIPage.verifySuccessPutCallAPIObjectLayer(httpRequest,response,docName,listing,"parameterTrue");
        	ObjectLayerPutAPIPage.verifyattributesforPUTAPIObjectLayer(httpRequest,listing,docName,AttributeNamesForDocStore,AttributeNamesForES);	
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(listing),"System toggle is disabled for document Type - "+listing);	
		}
	}
	
	@Test ( groups = {"test","dev"}, priority = 2, enabled = true, description ="verify response for wrong doc type for PUT API Object layer") 
	public void verifyResponseForWrongDocTypePUTAPIObjectLayer(){
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(listing)){
			JSONObject payloadAfterPut = new JSONObject(response);
        	ObjectLayerPostAPIPage.verifyResponseForWrongDocType(httpRequest,listing,docName,payloadAfterPut,"PUT");
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(listing),"System toggle is disabled for document Type - "+listing);	
		}
	}
	
	@Test ( groups = {"test","dev"}, priority = 2, enabled = true, description ="verify response for wrong locale for PUT API Object layer") 
	public void verifyResponseForWrongLocalePUTAPIObjectLayer(){
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(listing)){
			JSONObject payloadAfterPut = new JSONObject(response);
        	ObjectLayerPostAPIPage.verifyResponseForWrongLocale(httpRequest,listing,docName,payloadAfterPut,"PUT");
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(listing),"System toggle is disabled for document Type - "+listing);	
		}	
	}
	
	@Test ( groups = {"test","dev"}, priority = 2, enabled = true, description ="verify response for no parameters for PUT API Object layer") 
	public void verifyResponseForNoParametersPUTAPIObjectLayer(){
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(listing)){
        	ObjectLayerPutAPIPage.verifySuccessPutCallAPIObjectLayer(httpRequest,response,docName,listing,"noParameter");
		}
		else
		{
		    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(listing),"System toggle is disabled for document Type - "+listing);	
		}	
	}
	

}
