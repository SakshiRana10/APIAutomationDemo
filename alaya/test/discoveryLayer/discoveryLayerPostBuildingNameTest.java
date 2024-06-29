package bright.api.alaya.test.discoveryLayer;

import java.util.ArrayList;

import org.json.JSONArray;
import org.testng.annotations.Test;

import bright.api.alaya.pages.discoveryLayer.discoveryLayerPostAPIPage;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.MainClassAlaya;
import io.restassured.specification.RequestSpecification;

public class discoveryLayerPostBuildingNameTest extends MainClassAlaya {

	
	
	public static String typeBuilding_Name = "building_name";
	
	public static String typeWrong = "wrong";
	
	
	
	/*Verify success for post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Success Status for Building_Name in Post API")
	public void verifySuccessStatusforPOSTDiscoveryBuilding_Name() throws InterruptedException{
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifySuccessForPost(httpRequest,typeBuilding_Name);
	}
	
	
	/*Verify attributes with graph QL after post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify attributes with graphql for Building_Name in Post API")
	public void verifyAttributesforPOSTDiscoveryBuilding_Name() throws InterruptedException{
		ArrayList<String> AttributeNamesForDocStore = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.graphqlAttributes, "basicBuildingName").get("discovery"));
	    ArrayList<String> AttributeNamesForES = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.graphqlAttributes, "basicBuildingName").get("graphQl"));	
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifyAttributesForPost(httpRequest,typeBuilding_Name,AttributeNamesForDocStore,AttributeNamesForES);
	}
	

	
    /*Verify status for invalid system locale post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify not found for wrong locale for Building_Name in Post API")
	public void verifyNotFoundForWrongLocaleForPOSTDiscoveryBuilding_Name(){
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifyNotFoundForWrongLocaleForPost(httpRequest,typeBuilding_Name);
	}
	
	
	
    /*Verify status for invalid documentType post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify not found for wrong doc type for Building_Name in Post API")
	public void verifyBadRequestForWrongDocTypeForPOSTDiscoveryBuilding_Name(){
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifyBadRequestForWrongDocTypeForPost(httpRequest,typeWrong);
	}
	
	 /*Verify version Id and history for post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify version id and history for Building_Name in Post API")
	public void verifyVersionIDForPOSTDiscoveryBuilding_Name() throws InterruptedException{
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifyVersionIDForPost(httpRequest,typeBuilding_Name);
	}
	
	
	
    /*Verify bad request status if include versions is false for post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify bad request if include versions is false for Building_Name in Post API")
	public void verifyBadRequestForFalseVersionsForPOSTDiscoveryBuilding_Name(){
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifyBadRequestForPost(httpRequest,typeBuilding_Name,"includeVersions");
	}
	
	
	/*Verify bad request status if pretty is false for post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify bad request if pretty is false for Building_Name in Post API")
	public void verifyBadRequestForFalsePrettyForPOSTDiscoveryBuilding_Name(){
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifyBadRequestForPost(httpRequest,typeBuilding_Name,"pretty");
	}
	

	
    /*Verify forbidden status for wrong API key for post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify forbidden status for wrong api key for Building_Name in Post API")
	public void verifyforbiddenForWrongAPIKeyForPOSTDiscoveryBuilding_Name(){
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifyForbiddenForPost(httpRequest,typeBuilding_Name);
	}
	
}