package bright.api.alaya.test.discoveryLayer;

import java.util.ArrayList;

import org.json.JSONArray;
import org.testng.annotations.Test;

import bright.api.alaya.pages.discoveryLayer.discoveryLayerPostAPIPage;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.MainClassAlaya;
import io.restassured.specification.RequestSpecification;

public class discoveryLayerPostAPISubdivisionTest extends MainClassAlaya {

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

	public static String typeSubdivision = "subdivision";
	public static String typeWrong = "wrong";
	
	/*Verify success for post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Success Status for Subdivision in Post API")
	public void verifySuccessStatusforPOSTDiscoverySubdivision() throws InterruptedException{
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifySuccessForPost(httpRequest,typeSubdivision);
	}
	
	
	/*Verify attributes with graph QL after post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify attributes with graphql for Subdivision in Post API")
	public void verifyAttributesforPOSTDiscoverySubdivision() throws InterruptedException{
		ArrayList<String> AttributeNamesForDocStore = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.graphqlAttributes, "basicSubdivision").get("discovery"));
	    ArrayList<String> AttributeNamesForES = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.graphqlAttributes, "basicSubdivision").get("graphQl"));	
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifyAttributesForPost(httpRequest,typeSubdivision,AttributeNamesForDocStore,AttributeNamesForES);
	}
	

	
    /*Verify status for invalid system locale post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify not found for wrong locale for Subdivision in Post API")
	public void verifyNotFoundForWrongLocaleForPOSTDiscoverySubdivision(){
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifyNotFoundForWrongLocaleForPost(httpRequest,typeSubdivision);
	}
	
	
	
    /*Verify status for invalid documentType post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify not found for wrong doc type for Subdivision in Post API")
	public void verifyBadRequestForWrongDocTypeForPOSTDiscoverySubdivision(){
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifyBadRequestForWrongDocTypeForPost(httpRequest,typeWrong);
	}
	
	 /*Verify version Id and history for post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify version id and history for Subdivision in Post API")
	public void verifyVersionIDForPOSTDiscoverySubdivision() throws InterruptedException{
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifyVersionIDForPost(httpRequest,typeSubdivision);
	}
	
	
	
    /*Verify bad request status if include versions is false for post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify bad request if include versions is false for Subdivision in Post API")
	public void verifyBadRequestForFalseVersionsForPOSTDiscoverySubdivision(){
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifyBadRequestForPost(httpRequest,typeSubdivision,"includeVersions");
	}
	
	
	/*Verify bad request status if pretty is false for post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify bad request if pretty is false for Subdivision in Post API")
	public void verifyBadRequestForFalsePrettyForPOSTDiscoverySubdivision(){
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifyBadRequestForPost(httpRequest,typeSubdivision,"pretty");
	}
	

	
    /*Verify forbidden status for wrong API key for post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify forbidden status for wrong api key for Subdivision in Post API")
	public void verifyforbiddenForWrongAPIKeyForPOSTDiscoverySubdivision(){
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifyForbiddenForPost(httpRequest,typeSubdivision);
	}
	
	
	
}
