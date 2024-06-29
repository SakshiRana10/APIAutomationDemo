package bright.api.alaya.test.dsInitializer;

import org.testng.annotations.Test;

import bright.api.alaya.pages.dsInitializer.DSInitializerGetAPIPage;
import bright.api.alaya.pages.dsInitializer.DSInitializerPostAPIPage;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.MainClassAlaya;
import io.restassured.specification.RequestSpecification;

public class DSInitializerTest extends MainClassAlaya {
	
	/* TOTAL CASES - 27
	 * Post call flow of ds initializer 
	 * Bad request of post call 
	 * Forbidden request of post call
	 * */
	
	public static String typeOffice = "office";
	public static String typeListing = "listing";
	public static String typeMember = "member";
	public static String typeTax = "taxrecord";
	public static String typeCounty = "countyrate";
	public static String typeBuilderModel = "builder_model";
	public static String typeCity = "city";
	public static String typeSubdivision = "subdivision";
	public static String typeBuilding_name="building_name";

	/*-- POST CALL FOR DS INITIALIZER --*/
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Send Post Call to DS Initializer Office")
	public void sendPostToOfficeDsInitializerCall() throws InterruptedException {
		RequestSpecification httpRequest = null;
		DSInitializerPostAPIPage.sendPostToDsInitializer(httpRequest,typeOffice);	
	}

	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Send Post Call to DS Initializer Listing")
	public void sendPostToListingDsInitializerCall() throws InterruptedException {
		RequestSpecification httpRequest = null;
		DSInitializerPostAPIPage.sendPostToDsInitializer(httpRequest,typeListing);
	}

	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Send Post Call to DS Initializer Member")
	public void sendPostToMemberDsInitializerCall() throws InterruptedException {
		RequestSpecification httpRequest = null;
		DSInitializerPostAPIPage.sendPostToDsInitializer(httpRequest,typeMember);
	}

	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Send Post Call to DS Initializer Taxrecord")
	public void sendPostToTaxrecordDsInitializerCall() throws InterruptedException {
		RequestSpecification httpRequest = null;
		DSInitializerPostAPIPage.sendPostToDsInitializer(httpRequest,typeTax);
	}

	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Send Post Call to DS Initializer Countyrate")
	public void sendPostToCountyrateDsInitializerCall() throws InterruptedException {
		RequestSpecification httpRequest = null;
		DSInitializerPostAPIPage.sendPostToDsInitializer(httpRequest,typeCounty);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Send Post Call to DS Initializer builder model")
	public void sendPostToBuilderModelDsInitializerCall() throws InterruptedException {
		RequestSpecification httpRequest = null;
		DSInitializerPostAPIPage.sendPostToDsInitializer(httpRequest,typeBuilderModel);
	}
	

	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Send Post Call to DS Initializer city")
	public void sendPostToCityDsInitializerCall() throws InterruptedException {
		RequestSpecification httpRequest = null;
		DSInitializerPostAPIPage.sendPostToDsInitializer(httpRequest,typeCity);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Send Post Call to DS Initializer subdivison")
	public void sendPostToSubDivisionDsInitializerCall() throws InterruptedException {
		RequestSpecification httpRequest = null;
		DSInitializerPostAPIPage.sendPostToDsInitializer(httpRequest,typeSubdivision);
	}  
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Send Post Call to DS Initializer building Name")
	public void sendPostToBuildingNameDsInitializerCall() throws InterruptedException {
		RequestSpecification httpRequest = null;
		DSInitializerPostAPIPage.sendPostToDsInitializer(httpRequest,typeBuilding_name);

	}
	
	
	
	/*-- BAD REQUEST OF POST CALL FOR DS INITIALIZER--*/
	
	@Test ( groups = {"test", "dev"}, priority = 1, enabled = true, description = "Verified bad request for DS Initializer office post call ")
	public void verifyBadRequestForOfficeDsInitializerPost() {
		RequestSpecification httpRequest = null;
		DSInitializerPostAPIPage.verifyBadRequestForDsInitializerPostCall(httpRequest,typeOffice);
	}

	@Test ( groups = {"test", "dev"},priority = 1, enabled = true, description = "Verified bad request for DS Initializer listing post call ")
	public void verifyBadRequestForListingDsInitializerPost() {
		RequestSpecification httpRequest = null;
		DSInitializerPostAPIPage.verifyBadRequestForDsInitializerPostCall(httpRequest,typeListing);
	}

	@Test ( groups = {"test", "dev"}, priority = 1, enabled = true, description = "Verified bad request for DS Initializer countyrate post call ")
	public void verifyBadRequestForCountyrateDsInitializerPost() {
		RequestSpecification httpRequest = null;
		DSInitializerPostAPIPage.verifyBadRequestForDsInitializerPostCall(httpRequest,typeCounty);
	}

	@Test ( groups = {"test", "dev"}, priority = 1, enabled = true, description = "Verified bad request for DS Initializer Taxrecord post call ")
	public void verifyBadRequestForTaxrecordDsInitializerPost() {
		RequestSpecification httpRequest = null;
		DSInitializerPostAPIPage.verifyBadRequestForDsInitializerPostCall(httpRequest,typeTax);
	}

	@Test ( groups = {"test", "dev"}, priority = 1, enabled = true, description = "Verified bad request for DS Initializer Member post call ")
	public void verifyBadRequestForMemberDsInitializerPost() {
		RequestSpecification httpRequest = null;
		DSInitializerPostAPIPage.verifyBadRequestForDsInitializerPostCall(httpRequest,typeMember);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 1, enabled = true, description = "Verified bad request for DS Initializer Builder model post call ")
	public void verifyBadRequestForBuilderModelDsInitializerPost() {
		RequestSpecification httpRequest = null;
		DSInitializerPostAPIPage.verifyBadRequestForDsInitializerPostCall(httpRequest,typeBuilderModel);
	}
	

	@Test ( groups = {"test", "dev"}, priority = 1, enabled = true, description = "Verified bad request for DS Initializer city post call ")
	public void verifyBadRequestForCityDsInitializerPost() {
		RequestSpecification httpRequest = null;
		DSInitializerPostAPIPage.verifyBadRequestForDsInitializerPostCall(httpRequest,typeCity);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 1, enabled = true, description = "Verified bad request for DS Initializer subdivision post call ")
	public void verifyBadRequestForSubdivisionDsInitializerPost() {
		RequestSpecification httpRequest = null;
		DSInitializerPostAPIPage.verifyBadRequestForDsInitializerPostCall(httpRequest,typeSubdivision);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 1, enabled = true, description = "Verified bad request for DS Initializer Building Name post call ")
	public void verifyBadRequestForBuildingNameDsInitializerPost() {
		RequestSpecification httpRequest = null;
		DSInitializerPostAPIPage.verifyBadRequestForDsInitializerPostCall(httpRequest,typeBuilding_name);

	}

	/*-- FORBIDDEN REQUEST OF POST CALL FOR DS INITIALIZER--*/
	
	@Test ( groups = {"test", "dev"}, priority = 2, enabled = true, description = "Verified the Forbidden error for Post Call of Listing DS Initializer ")
	public void verifyForbiddenForListingDsInitializerPost() {
		RequestSpecification httpRequest = null;
		DSInitializerPostAPIPage.verifyForbiddenForDsInitializerPostCall(httpRequest,typeListing);
	}

	@Test ( groups = {"test", "dev"}, priority = 2, enabled = true, description = "Verified the Forbidden error for Post Call of Member DS Initializer ")
	public void verifyForbiddenForMemberDsInitializerPost() {
		RequestSpecification httpRequest = null;
		DSInitializerPostAPIPage.verifyForbiddenForDsInitializerPostCall(httpRequest,typeMember);
	}

	@Test ( groups = {"test", "dev"}, priority = 2, enabled = true, description = "Verified the Forbidden error for Post Call of Taxrecord DS Initializer ")
	public void verifyForbiddenForTaxrecordDsInitializerPost() {
		RequestSpecification httpRequest = null;
		DSInitializerPostAPIPage.verifyForbiddenForDsInitializerPostCall(httpRequest,typeTax);
	}

	@Test ( groups = {"test", "dev"}, priority = 2, enabled = true, description = "Verified the Forbidden error for Post Call of Countyrate DS Initializer ")
	public void verifyForbiddenForCountyrateDsInitializerPost() {
		RequestSpecification httpRequest = null;
		DSInitializerPostAPIPage.verifyForbiddenForDsInitializerPostCall(httpRequest,typeCounty);
	}

	@Test ( groups = {"test", "dev"}, priority = 2, enabled = true, description = "Verified the Forbidden error for Post Call of Office DS Initializer ")
	public void verifyForbiddenForOfficeDsInitializerPost() {
		RequestSpecification httpRequest = null;
		DSInitializerPostAPIPage.verifyForbiddenForDsInitializerPostCall(httpRequest,typeOffice);
	}	
	
	@Test ( groups = {"test", "dev"}, priority = 2, enabled = true, description = "Verified the Forbidden error for Post Call of builder model DS Initializer ")
	public void verifyForbiddenForBuilderModelDsInitializerPost() {
		RequestSpecification httpRequest = null;
		DSInitializerPostAPIPage.verifyForbiddenForDsInitializerPostCall(httpRequest,typeBuilderModel);
	}
	

	@Test ( groups = {"test", "dev"}, priority = 2, enabled = true, description = "Verified the Forbidden error for Post Call of city DS Initializer ")
	public void verifyForbiddenForCityDsInitializerPost() {
		RequestSpecification httpRequest = null;
		DSInitializerPostAPIPage.verifyForbiddenForDsInitializerPostCall(httpRequest,typeCity);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 2, enabled = true, description = "Verified the Forbidden error for Post Call of subdivision DS Initializer ")
	public void verifyForbiddenForSubdivisonDsInitializerPost() {
		RequestSpecification httpRequest = null;
		DSInitializerPostAPIPage.verifyForbiddenForDsInitializerPostCall(httpRequest,typeSubdivision);
	} 
	
	@Test ( groups = {"test", "dev"}, priority = 2, enabled = true, description = "Verified the Forbidden error for Post Call of building name DS Initializer ")
	public void verifyForbiddenForBuildingNameDsInitializerPost() {
		RequestSpecification httpRequest = null;
		DSInitializerPostAPIPage.verifyForbiddenForDsInitializerPostCall(httpRequest,typeBuilding_name);

	}

}
