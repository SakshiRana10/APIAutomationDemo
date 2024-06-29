package bright.api.alaya.test.objectLayer;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import bright.api.alaya.pages.objectLayer.ObjectLayerGetAPIPage;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.MainClassAlaya;
import io.restassured.specification.RequestSpecification;

public class ObjectLayerGetAPITeamTest extends MainClassAlaya{
	
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

	public static String team = "team";
	
	@Test ( groups = {"test","dev","prod"}, priority = 0, enabled = true, description ="verify status 200 OK for Get API Object layer") 
	public void verifySuccessForGetAPIObjectLayer() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException {
		RequestSpecification httpRequest= null;
		ObjectLayerGetAPIPage.verifySuccessStatusforGETAPIObjectLayer(httpRequest,team);	
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify Response For Wrong System Locale")
	public void verifyResponseForWrongSystemLocaleObjectMethod() {
		RequestSpecification httpRequest= null;
		ObjectLayerGetAPIPage.verifyResponseForWrongSystemLocale(httpRequest,team);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify Response For Wrong document type")
	public void verifyResponseForWrongDocumentTypeObjectMethod() {
		RequestSpecification httpRequest= null;
		ObjectLayerGetAPIPage.verifyResponseForWrongDocType(httpRequest,team);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Response For Wrong document name")
	public void verifyResponseForWrongDocumentObjectMethod() {
		RequestSpecification httpRequest= null;
		ObjectLayerGetAPIPage.verifyResponseForWrongDocument(httpRequest,team);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Response For deleted document")
	public void verifyResponseForDeletedDocumentObjectMethod() {
		RequestSpecification httpRequest= null;
		ObjectLayerGetAPIPage.verifyResponseForDeletedDocument(httpRequest,team);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify attributes matching from doc store to ES")
	public void verifyAttributesObjectMethod() {
		RequestSpecification httpRequest= null;
		ArrayList<String> AttributeNamesForDocStore = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.graphqlAttributes, "basicTeam").get("discovery"));
	    ArrayList<String> AttributeNamesForES = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.graphqlAttributes, "basicTeam").get("graphQl"));	
		ObjectLayerGetAPIPage.verifyAttributesForObjectLayer(httpRequest,team,AttributeNamesForDocStore,AttributeNamesForES);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify forbidden request if wrong api key is sent")
	public void verifyForbiddenRequestForInvalisSessionObjectMethod() {
		RequestSpecification httpRequest= null;
		ObjectLayerGetAPIPage.verifyWrongSessionKeyForGETAPIObjectLayer(httpRequest,team);
	}
	
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify forbidden request if wrong api key is sent")
	public void verifyForbiddenRequestForInvalidBearerTokenObjectMethod() {
		RequestSpecification httpRequest= null;
		ObjectLayerGetAPIPage.verifyWrongBearerTokenForGETAPIObjectLayer(httpRequest,team);
	}
}
