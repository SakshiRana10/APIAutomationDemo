package bright.api.alaya.test.discoveryLayer;

import java.util.ArrayList;

import org.json.JSONArray;
import org.testng.annotations.Test;

import bright.api.alaya.pages.discoveryLayer.discoveryLayerPostAPIPage;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.MainClassAlaya;
import io.restassured.specification.RequestSpecification;

public class discoveryLayerPostCityTest extends MainClassAlaya {
	
	
	/*Total Cases - 8
	 * Success for post call 
	 * Verifying attributes for post call
	 * Invalid system locale
	 * Invalid doc type
	 * Verifying version id
	 * Bad request if include versions is false
	 * Bad request if pretty is false
	 * Forbidden request for wrong API key
	 * */
	
	public static String typeCity = "city";
	public static String typeWrong = "wrong";
	
	/*Verify success for post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Success Status for City in Post API")
	public void verifySuccessStatusforPOSTDiscoveryCity() throws InterruptedException{
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifySuccessForPost(httpRequest,typeCity);
	}
	
	
	/*Verify attributes with graph QL after post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify attributes with graphql for City in Post API")
	public void verifyAttributesforPOSTDiscoveryCity() throws InterruptedException{
		ArrayList<String> AttributeNamesForDocStore = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.graphqlAttributes, "basicCity").get("discovery"));
	    ArrayList<String> AttributeNamesForES = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.graphqlAttributes, "basicCity").get("graphQl"));	
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifyAttributesForPost(httpRequest,typeCity,AttributeNamesForDocStore,AttributeNamesForES);
	}
	

	
    /*Verify status for invalid system locale post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify not found for wrong locale for City in Post API")
	public void verifyNotFoundForWrongLocaleForPOSTDiscoveryCity(){
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifyNotFoundForWrongLocaleForPost(httpRequest,typeCity);
	}
	
	
	
    /*Verify status for invalid documentType post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify not found for wrong doc type for City in Post API")
	public void verifyBadRequestForWrongDocTypeForPOSTDiscoveryCity(){
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifyBadRequestForWrongDocTypeForPost(httpRequest,typeWrong);
	}
	
	 /*Verify version Id and history for post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify version id and history for City in Post API")
	public void verifyVersionIDForPOSTDiscoveryCity() throws InterruptedException{
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifyVersionIDForPost(httpRequest,typeCity);
	}
	
	
	
    /*Verify bad request status if include versions is false for post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify bad request if include versions is false for City in Post API")
	public void verifyBadRequestForFalseVersionsForPOSTDiscoveryCity(){
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifyBadRequestForPost(httpRequest,typeCity,"includeVersions");
	}
	
	
	/*Verify bad request status if pretty is false for post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify bad request if pretty is false for City in Post API")
	public void verifyBadRequestForFalsePrettyForPOSTDiscoveryCity(){
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifyBadRequestForPost(httpRequest,typeCity,"pretty");
	}
	

	
    /*Verify forbidden status for wrong API key for post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify forbidden status for wrong api key for City in Post API")
	public void verifyforbiddenForWrongAPIKeyForPOSTDiscoveryCity(){
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifyForbiddenForPost(httpRequest,typeCity);
	}
	
}