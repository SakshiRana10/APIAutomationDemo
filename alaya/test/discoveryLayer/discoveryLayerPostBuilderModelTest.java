package bright.api.alaya.test.discoveryLayer;

import java.util.ArrayList;

import org.json.JSONArray;
import org.testng.annotations.Test;

import bright.api.alaya.pages.discoveryLayer.discoveryLayerPostAPIPage;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.MainClassAlaya;
import io.restassured.specification.RequestSpecification;

public class discoveryLayerPostBuilderModelTest extends MainClassAlaya {

	
	
	public static String typeBuilder_model = "builder_model";
	
	public static String typeWrong = "wrong";
	
	
	
	/*Verify success for post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Success Status for Builder_model in Post API")
	public void verifySuccessStatusforPOSTDiscoveryBuilder_model() throws InterruptedException{
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifySuccessForPost(httpRequest,typeBuilder_model);
	}
	
	
	/*Verify attributes with graph QL after post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify attributes with graphql for Builder_model in Post API")
	public void verifyAttributesforPOSTDiscoveryBuilder_model() throws InterruptedException{
		ArrayList<String> AttributeNamesForDocStore = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.graphqlAttributes, "basicBuilderModel").get("discovery"));
	    ArrayList<String> AttributeNamesForES = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.graphqlAttributes, "basicBuilderModel").get("graphQl"));	
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifyAttributesForPost(httpRequest,typeBuilder_model,AttributeNamesForDocStore,AttributeNamesForES);
	}
	

	
    /*Verify status for invalid system locale post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify not found for wrong locale for Builder_model in Post API")
	public void verifyNotFoundForWrongLocaleForPOSTDiscoveryBuilder_model(){
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifyNotFoundForWrongLocaleForPost(httpRequest,typeBuilder_model);
	}
	
	
	
    /*Verify status for invalid documentType post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify not found for wrong doc type for Builder_model in Post API")
	public void verifyBadRequestForWrongDocTypeForPOSTDiscoveryBuilder_model(){
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifyBadRequestForWrongDocTypeForPost(httpRequest,typeWrong);
	}
	
	 /*Verify version Id and history for post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify version id and history for Builder_model in Post API")
	public void verifyVersionIDForPOSTDiscoveryBuilder_model() throws InterruptedException{
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifyVersionIDForPost(httpRequest,typeBuilder_model);
	}
	
	
	
    /*Verify bad request status if include versions is false for post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify bad request if include versions is false for Builder_model in Post API")
	public void verifyBadRequestForFalseVersionsForPOSTDiscoveryBuilder_model(){
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifyBadRequestForPost(httpRequest,typeBuilder_model,"includeVersions");
	}
	
	
	/*Verify bad request status if pretty is false for post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify bad request if pretty is false for Builder_model in Post API")
	public void verifyBadRequestForFalsePrettyForPOSTDiscoveryBuilder_model(){
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifyBadRequestForPost(httpRequest,typeBuilder_model,"pretty");
	}
	

	
    /*Verify forbidden status for wrong API key for post call*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify forbidden status for wrong api key for Builder_model in Post API")
	public void verifyforbiddenForWrongAPIKeyForPOSTDiscoveryBuilder_model(){
		RequestSpecification httpRequest = null;
		discoveryLayerPostAPIPage.verifyForbiddenForPost(httpRequest,typeBuilder_model);
	}
	
}