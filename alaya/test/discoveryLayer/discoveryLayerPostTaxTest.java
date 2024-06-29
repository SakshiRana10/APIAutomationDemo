package bright.api.alaya.test.discoveryLayer;

import java.util.ArrayList;

import org.json.JSONArray;
import org.testng.annotations.Test;

import bright.api.alaya.pages.discoveryLayer.discoveryLayerPostAPIPage;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.MainClassAlaya;
import io.restassured.specification.RequestSpecification;

public class discoveryLayerPostTaxTest extends MainClassAlaya {

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
	
	public static String typeTax = "taxrecord";
	public static String typeWrong = "wrong";
	
    /*Verify success for post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Success Status for Tax Record in Post API")
	public void verifySuccessStatusforPOSTDiscoveryTaxRecord() throws InterruptedException{
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifySuccessForPost(httpRequest,typeTax);
	}
	
	
	/*Verify attributes with graph QL after post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify attributes with graphql for Tax Record in Post API")
	public void verifyAttributesforPOSTDiscoveryTaxRecord() throws InterruptedException{
		ArrayList<String> AttributeNamesForDocStore = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.graphqlAttributes, "basicTaxrecords").get("discovery"));
	    ArrayList<String> AttributeNamesForES = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.graphqlAttributes, "basicTaxrecords").get("graphQl"));	
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifyAttributesForPost(httpRequest,typeTax,AttributeNamesForDocStore,AttributeNamesForES);
	}
	
	
    /*Verify status for invalid system locale post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify not found for wrong locale for Tax Record in Post API")
	public void verifyNotFoundForWrongLocaleForPOSTDiscoveryTaxRecord(){
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifyNotFoundForWrongLocaleForPost(httpRequest,typeTax);
	}
	
    /*Verify status for invalid documentType post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify not found for wrong doc type for Tax Record in Post API")
	public void verifyBadRequestForWrongDocTypeForPOSTDiscoveryTaxRecord(){
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifyBadRequestForWrongDocTypeForPost(httpRequest,typeWrong);
	}
	
	 /*Verify version Id and history for post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify version id and history for Tax Record in Post API")
	public void verifyVersionIDForPOSTDiscoveryTaxRecord() throws InterruptedException{
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifyVersionIDForPost(httpRequest,typeTax);
	}

	
    /*Verify bad request status if include versions is false for post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify bad request if include versions is false for Tax Record in Post API")
	public void verifyBadRequestForFalseVersionsForPOSTDiscoveryTaxRecord(){
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifyBadRequestForPost(httpRequest,typeTax,"includeVersions");
	}

	
	/*Verify bad request status if pretty is false for post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify bad request if pretty is false for Tax Record in Post API")
	public void verifyBadRequestForFalsePrettyForPOSTDiscoveryTaxRecord(){
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifyBadRequestForPost(httpRequest,typeTax,"pretty");
	}

	
    /*Verify forbidden status for wrong API key for post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify forbidden status for wrong api key for Tax Record in Post API")
	public void verifyforbiddenForWrongAPIKeyForPOSTDiscoveryTaxRecord(){
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifyForbiddenForPost(httpRequest,typeTax);
	}
}
