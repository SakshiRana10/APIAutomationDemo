package bright.api.alaya.test.dsInitializer;

import org.testng.annotations.Test;

import bright.api.alaya.pages.dsInitializer.DSInitializerGetAPIPage;
import io.restassured.specification.RequestSpecification;

public class DSInitializerGetTest extends DSInitializerTest {
	
	/* TOTAL CASES - 36
	 * Get call flow of ds initializer
	 * Invalid id request of get call
	 * Forbidden request of get call
	 * Not found request of get call
	 * */
	
   /*-- GET CALL FOR DS INITIALIZER --*/
	
	@Test ( groups = {"test", "dev"},priority = 0, enabled = true, description = "Send Get Call to DS Initializer Office")
	public void sendGetToOfficeDsInitializerCall() throws InterruptedException {
		RequestSpecification httpRequest = null;
		DSInitializerGetAPIPage.getDSInitializer(httpRequest,typeOffice);
	}

	@Test ( groups = {"test", "dev"},priority = 0, enabled = true, description = "Send Get Call to DS Initializer Listing")
	public void sendGetToListingDsInitializerCall() throws InterruptedException {
		RequestSpecification httpRequest = null;
		DSInitializerGetAPIPage.getDSInitializer(httpRequest,typeListing);
	}

	@Test ( groups = {"test", "dev"},priority = 0, enabled = true, description = "Send Get Call to DS Initializer Member")
	public void sendGetToMemberDsInitializerCall() throws InterruptedException {
		RequestSpecification httpRequest = null;
		DSInitializerGetAPIPage.getDSInitializer(httpRequest,typeMember);
	}
	
	@Test ( groups = {"test", "dev"},priority = 0, enabled = true, description = "Send Get Call to DS Initializer Taxrecord")
	public void sendGetToTaxDsInitializerCall() throws InterruptedException {
		RequestSpecification httpRequest = null;
		DSInitializerGetAPIPage.getDSInitializer(httpRequest,typeTax);
	}
	
	@Test ( groups = {"test", "dev"},priority = 0, enabled = true, description = "Send Get Call to DS Initializer Countyrate")
	public void sendGetToCountyrateDsInitializerCall() throws InterruptedException {
		RequestSpecification httpRequest = null;
		DSInitializerGetAPIPage.getDSInitializer(httpRequest,typeCounty);
	}
	
	@Test ( groups = {"test", "dev"},priority = 0, enabled = true, description = "Send Get Call to DS Initializer BuilderModel")
	public void sendGetToBuilderModelDsInitializerCall() throws InterruptedException {
		RequestSpecification httpRequest = null;
		DSInitializerGetAPIPage.getDSInitializer(httpRequest,typeBuilderModel);
	}

	@Test ( groups = {"test", "dev"},priority = 0, enabled = true, description = "Send Get Call to DS Initializer city")
	public void sendGetToCityDsInitializerCall() throws InterruptedException {
		RequestSpecification httpRequest = null;
		DSInitializerGetAPIPage.getDSInitializer(httpRequest,typeCity);
	}
	
	@Test ( groups = {"test", "dev"},priority = 0, enabled = true, description = "Send Get Call to DS Initializer subdivision")
	public void sendGetToSubdivisonDsInitializerCall() throws InterruptedException {
		RequestSpecification httpRequest = null;
		DSInitializerGetAPIPage.getDSInitializer(httpRequest,typeSubdivision);
	}

	@Test ( groups = {"test", "dev"},priority = 0, enabled = true, description = "Send Get Call to DS Initializer Building Name")
	public void sendGetToBuildingNameDsInitializerCall() throws InterruptedException {
		RequestSpecification httpRequest = null;
		DSInitializerGetAPIPage.getDSInitializer(httpRequest,typeBuilding_name);

	}
	
/*-- INVALID ID OF GET CALL FOR DS INITIALIZER--*/
	
	@Test ( groups = {"test", "dev"},priority = 1, enabled = true, description = "Verified the Invalid Id Error for DS Initializer Office Get call")
	public void verifyInvalidIdErrorForGetOfficeInitializationStatusCall() {
		RequestSpecification httpRequest = null;
		DSInitializerGetAPIPage.verifyInvalidIdErrorForGetInitializationStatus(httpRequest,typeOffice);
	}

	@Test ( groups = {"test", "dev"},priority = 1, enabled = true, description = "Verified the Invalid Id Error for DS Initializer Countyrate Get call")
	public void verifyInvalidIdErrorForGetCountyrateInitializationStatusCall() {
		RequestSpecification httpRequest = null;
		DSInitializerGetAPIPage.verifyInvalidIdErrorForGetInitializationStatus(httpRequest,typeCounty);
	}

	@Test ( groups = {"test", "dev"},priority = 1, enabled = true, description = "Verified the Invalid Id Error for DS Initializer Member Get call")
	public void verifyInvalidIdErrorForGetMemberInitializationStatusCall() {
		RequestSpecification httpRequest = null;
		DSInitializerGetAPIPage.verifyInvalidIdErrorForGetInitializationStatus(httpRequest,typeMember);
	}

	@Test ( groups = {"test", "dev"},priority = 1, enabled = true, description = "Verified the Invalid Id Error for DS Initializer Taxrecord Get call")
	public void verifyInvalidIdErrorForGetTaxrecordInitializationStatusCall() {
		RequestSpecification httpRequest = null;
		DSInitializerGetAPIPage.verifyInvalidIdErrorForGetInitializationStatus(httpRequest,typeTax);
	}

	@Test ( groups = {"test", "dev"},priority = 1, enabled = true, description = "Verified the Invalid Id Error for DS Initializer Listing Get call")
	public void verifyInvalidIdErrorForGetListingInitializationStatusCall() {
		RequestSpecification httpRequest = null;
		DSInitializerGetAPIPage.verifyInvalidIdErrorForGetInitializationStatus(httpRequest,typeListing);
	}
	
	@Test ( groups = {"test", "dev"},priority = 1, enabled = true, description = "Verified the Invalid Id Error for DS Initializer builder Model Get call")
	public void verifyInvalidIdErrorForGetBuilderModelInitializationStatusCall() {
		RequestSpecification httpRequest = null;
		DSInitializerGetAPIPage.verifyInvalidIdErrorForGetInitializationStatus(httpRequest,typeBuilderModel);
	}

	@Test ( groups = {"test", "dev"},priority = 1, enabled = true, description = "Verified the Invalid Id Error for DS Initializer city Get call")
	public void verifyInvalidIdErrorForGetCityInitializationStatusCall() {
		RequestSpecification httpRequest = null;
		DSInitializerGetAPIPage.verifyInvalidIdErrorForGetInitializationStatus(httpRequest,typeCity);
	}
	
	@Test ( groups = {"test", "dev"},priority = 1, enabled = true, description = "Verified the Invalid Id Error for DS Initializer subdivision Get call")
	public void verifyInvalidIdErrorForGetSubDivisionInitializationStatusCall() {
		RequestSpecification httpRequest = null;
		DSInitializerGetAPIPage.verifyInvalidIdErrorForGetInitializationStatus(httpRequest,typeSubdivision);
	}
	
	@Test ( groups = {"test", "dev"},priority = 1, enabled = true, description = "Verified the Invalid Id Error for DS Initializer building Name Get call")
	public void verifyInvalidIdErrorForGetBuildingNameInitializationStatusCall() {
		RequestSpecification httpRequest = null;
		DSInitializerGetAPIPage.verifyInvalidIdErrorForGetInitializationStatus(httpRequest,typeBuilding_name);

	}
	
	/*-- FORBIDDEN REQUEST OF GET CALL FOR DS INITIALIZER--*/
	
	@Test ( groups = {"test", "dev"},priority = 2, enabled = true, description = "Verified the Forbidden Error for DS Initializer Listing Get call")
	public void verifyForbiddenForGetListingInitializationStatusCall() {
		RequestSpecification httpRequest = null;
		DSInitializerGetAPIPage.verifyForbiddenForGetInitializationStatus(httpRequest,typeListing);
	}

	@Test ( groups = {"test", "dev"},priority = 2, enabled = true, description = "Verified the Forbidden Error for DS Initializer Office Get call")
	public void verifyForbiddenForGetOfficeInitializationStatusCall() {
		RequestSpecification httpRequest = null;
		DSInitializerGetAPIPage.verifyForbiddenForGetInitializationStatus(httpRequest,typeOffice);
	}

	@Test ( groups = {"test", "dev"},priority = 2, enabled = true, description = "Verified the Forbidden Error for DS Initializer Countyrate Get call")
	public void verifyForbiddenForGetCountyrateInitializationStatusCall() {
		RequestSpecification httpRequest = null;
		DSInitializerGetAPIPage.verifyForbiddenForGetInitializationStatus(httpRequest,typeCounty);
	}

	@Test ( groups = {"test", "dev"},priority = 2, enabled = true, description = "Verified the Forbidden Error for DS Initializer Member Get call")
	public void verifyForbiddenForGetMemberInitializationStatusCall() {
		RequestSpecification httpRequest = null;
		DSInitializerGetAPIPage.verifyForbiddenForGetInitializationStatus(httpRequest,typeMember);
	}

	@Test ( groups = {"test", "dev"},priority = 2, enabled = true, description = "Verified the Forbidden Error for DS Initializer Taxrecord Get call")
	public void verifyForbiddenForGetTaxrecordInitializationStatusCall() {
		RequestSpecification httpRequest = null;
		DSInitializerGetAPIPage.verifyForbiddenForGetInitializationStatus(httpRequest,typeTax);
	}
	
	@Test ( groups = {"test", "dev"},priority = 2, enabled = true, description = "Verified the Forbidden Error for DS Initializer builder model Get call")
	public void verifyForbiddenForGetBuilderModelInitializationStatusCall() {
		RequestSpecification httpRequest = null;
		DSInitializerGetAPIPage.verifyForbiddenForGetInitializationStatus(httpRequest,typeBuilderModel);
	}
	

	@Test ( groups = {"test", "dev"},priority = 2, enabled = true, description = "Verified the Forbidden Error for DS Initializer city Get call")
	public void verifyForbiddenForGetCityInitializationStatusCall() {
		RequestSpecification httpRequest = null;
		DSInitializerGetAPIPage.verifyForbiddenForGetInitializationStatus(httpRequest,typeCity);
	}
	
	@Test ( groups = {"test", "dev"},priority = 2, enabled = true, description = "Verified the Forbidden Error for DS Initializer subdivision Get call")
	public void verifyForbiddenForGetSubdivisionInitializationStatusCall() {
		RequestSpecification httpRequest = null;
		DSInitializerGetAPIPage.verifyForbiddenForGetInitializationStatus(httpRequest,typeSubdivision);
	}
	
	@Test ( groups = {"test", "dev"},priority = 2, enabled = true, description = "Verified the Forbidden Error for DS Initializer building Name Get call")
	public void verifyForbiddenForGetBuildingNameInitializationStatusCall() {
		RequestSpecification httpRequest = null;
		DSInitializerGetAPIPage.verifyForbiddenForGetInitializationStatus(httpRequest,typeBuilding_name);

	}
	
	
	/*-- NOT FOUND REQUEST OF GET CALL FOR DS INITIALIZER--*/
	
	@Test ( groups = {"test", "dev"},priority = 3, enabled = true, description = "Verified the Not found Error for DS Initializer Taxrecord Get call")
	public void verifyNotFoundForGetTaxrecordInitializationStatusCall() {
		RequestSpecification httpRequest = null;
		DSInitializerGetAPIPage.verifyNotFoundForGetInitializationStatus(httpRequest,typeTax);
	}

	@Test ( groups = {"test", "dev"},priority = 3, enabled = true, description = "Verified the Not found Error for DS Initializer Office Get call")
	public void verifyNotFoundForGetOfficeInitializationStatusCall() {
		RequestSpecification httpRequest = null;
		DSInitializerGetAPIPage.verifyNotFoundForGetInitializationStatus(httpRequest,typeOffice);
	}

	@Test ( groups = {"test", "dev"},priority = 3, enabled = true, description = "Verified the Not found Error for DS Initializer Countyrate Get call")
	public void verifyNotFoundForGetCountyrateInitializationStatusCall() {
		RequestSpecification httpRequest = null;
		DSInitializerGetAPIPage.verifyNotFoundForGetInitializationStatus(httpRequest,typeCounty);
	}

	@Test ( groups = {"test", "dev"},priority = 3, enabled = true, description = "Verified the Not found Error for DS Initializer Member Get call")
	public void verifyNotFoundForGetMemberInitializationStatusCall() {
		RequestSpecification httpRequest = null;
		DSInitializerGetAPIPage.verifyNotFoundForGetInitializationStatus(httpRequest,typeMember);
	}

	@Test ( groups = {"test", "dev"},priority = 3, enabled = true, description = "Verified the Not found Error for DS Initializer Listing Get call")
	public void verifyNotFoundForGetListingInitializationStatusCall() {
		RequestSpecification httpRequest = null;
		DSInitializerGetAPIPage.verifyNotFoundForGetInitializationStatus(httpRequest,typeListing);
	}
	
	@Test ( groups = {"test", "dev"},priority = 3, enabled = true, description = "Verified the Not found Error for DS Initializer BuilderModel Get call")
	public void verifyNotFoundForGetBuilderModelInitializationStatusCall() {
		RequestSpecification httpRequest = null;
		DSInitializerGetAPIPage.verifyNotFoundForGetInitializationStatus(httpRequest,typeBuilderModel);
	}
	
	@Test ( groups = {"test", "dev"},priority = 3, enabled = true, description = "Verified the Not found Error for DS Initializer city Get call")
	public void verifyNotFoundForGetCityInitializationStatusCall() {
		RequestSpecification httpRequest = null;
		DSInitializerGetAPIPage.verifyNotFoundForGetInitializationStatus(httpRequest,typeCity);
	}
	
	@Test ( groups = {"test", "dev"},priority = 3, enabled = true, description = "Verified the Not found Error for DS Initializer subdivision Get call")
	public void verifyNotFoundForGetSubdivisionInitializationStatusCall() {
		RequestSpecification httpRequest = null;
		DSInitializerGetAPIPage.verifyNotFoundForGetInitializationStatus(httpRequest,typeSubdivision);
	}


	@Test ( groups = {"test", "dev"},priority = 3, enabled = true, description = "Verified the Not found Error for DS Initializer Building name Get call")
	public void verifyNotFoundForGetBuildingNameInitializationStatusCall() {
		RequestSpecification httpRequest = null;
		DSInitializerGetAPIPage.verifyNotFoundForGetInitializationStatus(httpRequest,typeBuilding_name);
	}

}
