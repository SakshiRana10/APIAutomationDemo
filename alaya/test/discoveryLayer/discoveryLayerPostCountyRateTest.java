package bright.api.alaya.test.discoveryLayer;

import java.util.ArrayList;

import org.json.JSONArray;
import org.testng.annotations.Test;

import bright.api.alaya.pages.discoveryLayer.discoveryLayerPostAPIPage;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.MainClassAlaya;
import io.restassured.specification.RequestSpecification;

public class discoveryLayerPostCountyRateTest extends MainClassAlaya{
	
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
	
	public static String typeCounty = "countyrate";
	public static String typeWrong = "wrong";
	
    /*Verify success for post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Success Status for County Rate in Post API")
	public void verifySuccessStatusforPOSTDiscoveryCountyRate() throws InterruptedException{
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifySuccessForPost(httpRequest,typeCounty);
	}
	
	
	/*Verify attributes with graph QL after post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify attributes with graphql for County Rate in Post API")
	public void verifyAttributesforPOSTDiscoveryCountyRate() throws InterruptedException{
		ArrayList<String> AttributeNamesForDocStore = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.graphqlAttributes, "basicCountyRate").get("discovery"));
	    ArrayList<String> AttributeNamesForES = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.graphqlAttributes, "basicCountyRate").get("graphQl"));	
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifyAttributesForPost(httpRequest,typeCounty,AttributeNamesForDocStore,AttributeNamesForES);
	}
	
	
    /*Verify status for invalid system locale post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify not found for wrong locale for County Rate in Post API")
	public void verifyNotFoundForWrongLocaleForPOSTDiscoveryCountyRate(){
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifyNotFoundForWrongLocaleForPost(httpRequest,typeCounty);
	}
	
    /*Verify status for invalid documentType post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify not found for wrong doc type for County Rate in Post API")
	public void verifyBadRequestForWrongDocTypeForPOSTDiscoveryCountyRate(){
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifyBadRequestForWrongDocTypeForPost(httpRequest,typeWrong);
	}
	
	 /*Verify version Id and history for post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify version id and history for County Rate in Post API")
	public void verifyVersionIDForPOSTDiscoveryCountyRate() throws InterruptedException{
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifyVersionIDForPost(httpRequest,typeCounty);
	}

	
    /*Verify bad request status if include versions is false for post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify bad request if include versions is false for County Rate in Post API")
	public void verifyBadRequestForFalseVersionsForPOSTDiscoveryCountyRate(){
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifyBadRequestForPost(httpRequest,typeCounty,"includeVersions");
	}

	
	/*Verify bad request status if pretty is false for post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify bad request if pretty is false for County Rate in Post API")
	public void verifyBadRequestForFalsePrettyForPOSTDiscoveryCountyRate(){
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifyBadRequestForPost(httpRequest,typeCounty,"pretty");
	}

	
    /*Verify forbidden status for wrong API key for post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify forbidden status for wrong api key for County Rate in Post API")
	public void verifyforbiddenForWrongAPIKeyForPOSTDiscoveryCountyRate(){
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifyForbiddenForPost(httpRequest,typeCounty);
	}

}
