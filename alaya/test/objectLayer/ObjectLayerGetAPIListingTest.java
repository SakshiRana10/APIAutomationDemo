package bright.api.alaya.test.objectLayer;

import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.util.Strings;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import bright.api.alaya.pages.discoveryLayer.discoveryLayerUtilityPage;
import bright.api.alaya.pages.objectLayer.ObjectLayerGetAPIPage;
import bright.api.alaya.pages.objectLayer.OfficeSetDefaultValuesPage;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ObjectLayerGetAPIListingTest extends MainClassAlaya {
	
	/*Total Cases - 8
	 * Success for GET call 
	 * Verifying attributes for GET call
	 * Invalid system locale
	 * Invalid doc type
	 * Wrong document name
	 * Deleted document name
	 * wrong session ID
	 * invalid bearer token 
	 * */

	public static String listing = "listing";
	
	@Test ( groups = {"test","dev","prod"}, priority = 0, enabled = true, description ="verify status 200 OK for Get API Object layer") 
	public void verifySuccessForGetAPIObjectLayer() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException {
		RequestSpecification httpRequest= null;
		ObjectLayerGetAPIPage.verifySuccessStatusforGETAPIObjectLayer(httpRequest,listing);	
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify Response For Wrong System Locale")
	public void verifyResponseForWrongSystemLocaleObjectMethod() {
		RequestSpecification httpRequest= null;
		ObjectLayerGetAPIPage.verifyResponseForWrongSystemLocale(httpRequest,listing);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify Response For Wrong document type")
	public void verifyResponseForWrongDocumentTypeObjectMethod() {
		RequestSpecification httpRequest= null;
		ObjectLayerGetAPIPage.verifyResponseForWrongDocType(httpRequest,listing);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Response For Wrong document name")
	public void verifyResponseForWrongDocumentObjectMethod() {
		RequestSpecification httpRequest= null;
		ObjectLayerGetAPIPage.verifyResponseForWrongDocument(httpRequest,listing);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Response For deleted document")
	public void verifyResponseForDeletedDocumentObjectMethod() {
		RequestSpecification httpRequest= null;
		ObjectLayerGetAPIPage.verifyResponseForDeletedDocument(httpRequest,listing);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify attributes matching from doc store to ES")
	public void verifyAttributesObjectMethod() {
		RequestSpecification httpRequest= null;
		ArrayList<String> AttributeNamesForDocStore = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.graphqlAttributes, "basicListing").get("discovery"));
	    ArrayList<String> AttributeNamesForES = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.graphqlAttributes, "basicListing").get("graphQl"));	
		ObjectLayerGetAPIPage.verifyAttributesForObjectLayer(httpRequest,listing,AttributeNamesForDocStore,AttributeNamesForES);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify forbidden request if wrong api key is sent")
	public void verifyForbiddenRequestForInvalisSessionObjectMethod() {
		RequestSpecification httpRequest= null;
		ObjectLayerGetAPIPage.verifyWrongSessionKeyForGETAPIObjectLayer(httpRequest,listing);
	}
	
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify forbidden request if wrong api key is sent")
	public void verifyForbiddenRequestForInvalidBearerTokenObjectMethod() {
		RequestSpecification httpRequest= null;
		ObjectLayerGetAPIPage.verifyWrongBearerTokenForGETAPIObjectLayer(httpRequest,listing);
	}
	
}
