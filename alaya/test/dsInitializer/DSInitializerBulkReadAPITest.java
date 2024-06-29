package bright.api.alaya.test.dsInitializer;

import org.testng.annotations.Test;
import bright.api.alaya.pages.dsInitializer.DSInitializerBulkReadAPIPage;
import bright.api.alaya.utils.MainClassAlaya;
import io.restassured.specification.RequestSpecification;

public class DSInitializerBulkReadAPITest extends MainClassAlaya {

	/* TOTAL CASES - 45
	 * Bulk read Post call flow of ds initializer 
	 * Forbidden call of bulk read ds initializer
	 * Not found call for non existing docs of bulk read ds initializer
	 * Not found call for wrong doc type of bulk read ds initializer
	 * Not found call for wrong locale of bulk read ds initializer
	 * */
	
	public static String typeOffice = "office";
	public static String typeListing = "listing";
	public static String typeMember = "member";
	public static String typeTax = "taxrecord";
	public static String typeCounty = "countyrate";
	public static String typeBuilderModel = "builder_model";
	public static String typeCity = "city";
	public static String typeSubdivision = "subdivision";
	public static String typeBuildingName = "building_name";

	
	/*-- BULK READ POST CALL FOR DS INITIALIZER --*/
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Send Post Call to Bulk Read Listing DS Initializer")
	public void sendPostToListingBulkReadDsInitializerCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.sendPostToBulkReadDsInitializer(httpRequest,typeListing);
	}

	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Send Post Call to Bulk Read Office DS Initializer")
	public void sendPostToOfficeBulkReadDsInitializerCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.sendPostToBulkReadDsInitializer(httpRequest,typeOffice);
	}

	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Send Post Call to Bulk Read Member DS Initializer")
	public void sendPostToMemberBulkReadDsInitializerCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.sendPostToBulkReadDsInitializer(httpRequest,typeMember);
	}

	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Send Post Call to Bulk Read Taxrecord DS Initializer")
	public void sendPostToTaxrecordBulkReadDsInitializerCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.sendPostToBulkReadDsInitializer(httpRequest,typeTax);
	}

	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Send Post Call to Bulk Read Countyrate DS Initializer")
	public void sendPostToCountyrateBulkReadDsInitializerCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.sendPostToBulkReadDsInitializer(httpRequest,typeCounty);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Send Post Call to Bulk Read Builder model DS Initializer")
	public void sendPostToBuilderModelBulkReadDsInitializerCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.sendPostToBulkReadDsInitializer(httpRequest,typeBuilderModel);
	}

	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Send Post Call to Bulk Read Builder model DS Initializer")
	public void sendPostToCityBulkReadDsInitializerCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.sendPostToBulkReadDsInitializer(httpRequest,typeCity);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Send Post Call to Bulk Read Builder model DS Initializer")
	public void sendPostToSubDivisionBulkReadDsInitializerCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.sendPostToBulkReadDsInitializer(httpRequest,typeSubdivision);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Send Post Call to Bulk Read Building Name DS Initializer")
	public void sendPostToBuildingNameBulkReadDsInitializerCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.sendPostToBulkReadDsInitializer(httpRequest,typeBuildingName);
	}	
	
	/*-- FORBIDDEN CALL FOR BULK READ DS INITIALIZER --*/

	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verified the Forbidden error for Bulk Read Office DS Initializer")
	public void verifyForbiddenForOfficeBulkReadDsInitializerCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.verifyForbiddenForBulkReadDsInitializer(httpRequest,typeOffice);
	}

	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verified the Forbidden error for Bulk Read Member DS Initializer")
	public void verifyForbiddenForMemberBulkReadDsInitializerCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.verifyForbiddenForBulkReadDsInitializer(httpRequest,typeMember);
	}

	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verified the Forbidden error for Bulk Read Taxrecord DS Initializer")
	public void verifyForbiddenForTaxrecordBulkReadDsInitializerCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.verifyForbiddenForBulkReadDsInitializer(httpRequest,typeTax);
	}

	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verified the Forbidden error for Bulk Read Countyrate DS Initializer")
	public void verifyForbiddenForCountyrateBulkReadDsInitializerCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.verifyForbiddenForBulkReadDsInitializer(httpRequest,typeCounty);
	}

	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verified the Forbidden error for Bulk Read Listing DS Initializer")
	public void verifyForbiddenForListingBulkReadDsInitializerCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.verifyForbiddenForBulkReadDsInitializer(httpRequest,typeListing);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verified the Forbidden error for Bulk Read Builder Model DS Initializer")
	public void verifyForbiddenForBuilderModelBulkReadDsInitializerCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.verifyForbiddenForBulkReadDsInitializer(httpRequest,typeBuilderModel);
	}
	

	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verified the Forbidden error for Bulk Read city DS Initializer")
	public void verifyForbiddenForCityBulkReadDsInitializerCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.verifyForbiddenForBulkReadDsInitializer(httpRequest,typeCity);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verified the Forbidden error for Bulk Read subdivision DS Initializer")
	public void verifyForbiddenForSubdivisionBulkReadDsInitializerCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.verifyForbiddenForBulkReadDsInitializer(httpRequest,typeSubdivision);
		
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verified the Forbidden error for Bulk Read Builder Model DS Initializer")
	public void verifyForbiddenForBuildingNameBulkReadDsInitializerCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.verifyForbiddenForBulkReadDsInitializer(httpRequest,typeBuildingName);

	}
	
	
	/*-- NOT FOUND CALL FOR BULK READ DS INITIALIZER --*/

	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verified the not found documents for Bulk Read Listing DS Initializer Post Call")
	public void verifyNotFoundDocumentForListingBulkReadDsInitializerPostCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.verifyNotFoundDocumentForBulkReadDsInitializerPost(httpRequest,typeListing);
	}

	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verified the not found documents for Bulk Read Countyrate DS Initializer Post Call")
	public void verifyNotFoundDocumentForCountyrateBulkReadDsInitializerPostCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.verifyNotFoundDocumentForBulkReadDsInitializerPost(httpRequest,typeCounty);
	}

	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verified the not found documents for Bulk Read Office DS Initializer Post Call")
	public void verifyNotFoundDocumentForOfficeBulkReadDsInitializerPostCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.verifyNotFoundDocumentForBulkReadDsInitializerPost(httpRequest,typeOffice);
	}

	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verified the not found documents for Bulk Read Taxrecord DS Initializer Post Call")
	public void verifyNotFoundDocumentForTaxrecordBulkReadDsInitializerPostCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.verifyNotFoundDocumentForBulkReadDsInitializerPost(httpRequest,typeTax);
	}

	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verified the not found documents for Bulk Read Member DS Initializer Post Call")
	public void verifyNotFoundDocumentForMemberBulkReadDsInitializerPostCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.verifyNotFoundDocumentForBulkReadDsInitializerPost(httpRequest,typeMember);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verified the not found documents for Bulk Read BuilderModel DS Initializer Post Call")
	public void verifyNotFoundDocumentForBuilderModelBulkReadDsInitializerPostCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.verifyNotFoundDocumentForBulkReadDsInitializerPost(httpRequest,typeBuilderModel);
	}
	

	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verified the not found documents for Bulk Read city DS Initializer Post Call")
	public void verifyNotFoundDocumentForCityBulkReadDsInitializerPostCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.verifyNotFoundDocumentForBulkReadDsInitializerPost(httpRequest,typeCity);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verified the not found documents for Bulk Read subdivision DS Initializer Post Call")
	public void verifyNotFoundDocumentForSubDivisionBulkReadDsInitializerPostCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.verifyNotFoundDocumentForBulkReadDsInitializerPost(httpRequest,typeSubdivision);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verified the not found documents for Bulk Read BuilderModel DS Initializer Post Call")
	public void verifyNotFoundDocumentForBuilingNameBulkReadDsInitializerPostCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.verifyNotFoundDocumentForBulkReadDsInitializerPost(httpRequest,typeBuildingName);
	}
	
	
	/*-- NOT FOUND CALL OF INVALID DOC TYPE FOR BULK READ DS INITIALIZER --*/
	
	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verified invalid document type for Bulk Read Member DS Initializer Post Call")
	public void verifyInvalidDocumentTypeForMemberBulkReadDsInitializerPostCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.verifyInvalidDocumentTypeForBulkReadDsInitializerPost(httpRequest,typeMember);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verified invalid document type for Bulk Read listing DS Initializer Post Call")
	public void verifyInvalidDocumentTypeForListingBulkReadDsInitializerPostCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.verifyInvalidDocumentTypeForBulkReadDsInitializerPost(httpRequest,typeListing);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verified invalid document type for Bulk Read office DS Initializer Post Call")
	public void verifyInvalidDocumentTypeForOfficeBulkReadDsInitializerPostCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.verifyInvalidDocumentTypeForBulkReadDsInitializerPost(httpRequest,typeOffice);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verified invalid document type for Bulk Read tax record DS Initializer Post Call")
	public void verifyInvalidDocumentTypeFortaxBulkReadDsInitializerPostCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.verifyInvalidDocumentTypeForBulkReadDsInitializerPost(httpRequest,typeTax);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verified invalid document type for Bulk Read county rate DS Initializer Post Call")
	public void verifyInvalidDocumentTypeForCountyBulkReadDsInitializerPostCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.verifyInvalidDocumentTypeForBulkReadDsInitializerPost(httpRequest,typeCounty);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verified invalid document type for Bulk Read builder model DS Initializer Post Call")
	public void verifyInvalidDocumentTypeForBuilderModelBulkReadDsInitializerPostCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.verifyInvalidDocumentTypeForBulkReadDsInitializerPost(httpRequest,typeBuilderModel);
	}

	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verified invalid document type for Bulk Read City DS Initializer Post Call")
	public void verifyInvalidDocumentTypeForCityBulkReadDsInitializerPostCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.verifyInvalidDocumentTypeForBulkReadDsInitializerPost(httpRequest,typeCity);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verified invalid document type for Bulk Read subdivision DS Initializer Post Call")
	public void verifyInvalidDocumentTypeForSubDivisionBulkReadDsInitializerPostCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.verifyInvalidDocumentTypeForBulkReadDsInitializerPost(httpRequest,typeSubdivision);
	} 
	
	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verified invalid document type for Bulk Read builder model DS Initializer Post Call")
	public void verifyInvalidDocumentTypeForBuildingNameBulkReadDsInitializerPostCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.verifyInvalidDocumentTypeForBulkReadDsInitializerPost(httpRequest,typeBuildingName);

	}
	
	
/*-- NOT FOUND CALL OF INVALID LOCALE FOR BULK READ DS INITIALIZER --*/
	
	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verified invalid locale for Bulk Read Member DS Initializer Post Call")
	public void verifyInvalidLocaleForMemberBulkReadDsInitializerPostCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.verifyInvalidLocaleForBulkReadDsInitializerPost(httpRequest,typeMember);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verified invalid locale for Bulk Read listing DS Initializer Post Call")
	public void verifyInvalidLocaleForListingBulkReadDsInitializerPostCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.verifyInvalidLocaleForBulkReadDsInitializerPost(httpRequest,typeListing);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verified invalid locale for Bulk Read office DS Initializer Post Call")
	public void verifyInvalidLocaleForOfficeBulkReadDsInitializerPostCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.verifyInvalidLocaleForBulkReadDsInitializerPost(httpRequest,typeOffice);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verified invalid locale for Bulk Read tax record DS Initializer Post Call")
	public void verifyInvalidLocaleFortaxBulkReadDsInitializerPostCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.verifyInvalidLocaleForBulkReadDsInitializerPost(httpRequest,typeTax);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verified invalid locale for Bulk Read Countyrate DS Initializer Post Call")
	public void verifyInvalidLocaleTypeForCountyBulkReadDsInitializerPostCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.verifyInvalidLocaleForBulkReadDsInitializerPost(httpRequest,typeCounty);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verified invalid locale for Bulk Read BuilderModel DS Initializer Post Call")
	public void verifyInvalidLocaleTypeForBuilderModelBulkReadDsInitializerPostCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.verifyInvalidLocaleForBulkReadDsInitializerPost(httpRequest,typeBuilderModel);
	}

	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verified invalid locale for Bulk Read City DS Initializer Post Call")
	public void verifyInvalidLocaleTypeForCityBulkReadDsInitializerPostCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.verifyInvalidLocaleForBulkReadDsInitializerPost(httpRequest,typeCity);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verified invalid locale for Bulk Read subdivision DS Initializer Post Call")
	public void verifyInvalidLocaleTypeForSubDivisionBulkReadDsInitializerPostCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.verifyInvalidLocaleForBulkReadDsInitializerPost(httpRequest,typeSubdivision);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verified invalid locale for Bulk Read BuilderModel DS Initializer Post Call")
	public void verifyInvalidLocaleTypeForBuildingNameBulkReadDsInitializerPostCall() {
		RequestSpecification httpRequest = null;
		DSInitializerBulkReadAPIPage.verifyInvalidLocaleForBulkReadDsInitializerPost(httpRequest,typeBuildingName);

	}
}
