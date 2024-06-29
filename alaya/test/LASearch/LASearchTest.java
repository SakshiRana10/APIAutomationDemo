package bright.api.alaya.test.LASearch;

import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import io.restassured.specification.RequestSpecification;

import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import bright.api.alaya.pages.LASearch.LASearchPage;

public class LASearchTest extends MainClassAlaya {
	
	/*Total Cases - 21*/

	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Send Post Call for LA search api")
	public void verifySuccessPostCallForLASearch() {
		RequestSpecification httpRequest= null;
		LASearchPage.verifyLASearch(httpRequest,"success",ResponseCodes.SUCCESS);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Send Post Call for LA search api with no search Text")
	public void verifyBadRequestWithoutSearchTextForLASearch() {
		RequestSpecification httpRequest= null;
		LASearchPage.verifyLASearch(httpRequest,"noSearchText",ResponseCodes.BAD_REQUEST);
	}
	

	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Send Post Call for LA search api with no body")
	public void verifyBadRequestWithoutBodyForLASearch() {
		RequestSpecification httpRequest= null;
		LASearchPage.verifyLASearch(httpRequest,"noBody",ResponseCodes.BAD_REQUEST);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Send Post Call for LA search api with wrong resource name")
	public void verifyBadRequestForWrongResourceForLASearch() {
		RequestSpecification httpRequest= null;
		LASearchPage.verifyLASearch(httpRequest,"wrongResource",ResponseCodes.BAD_REQUEST);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Send Post Call for LA search api with wrong search components")
	public void verifyBadRequestForWrongSearchComponentsForLASearch() {
		RequestSpecification httpRequest= null;
		LASearchPage.verifyLASearch(httpRequest,"wrongSearchComponents",ResponseCodes.BAD_REQUEST);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Send Post Call for LA search api with wrong search text format")
	public void verifyBadRequestForWrongSearchTextFormatForLASearch() {
		RequestSpecification httpRequest= null;
		LASearchPage.verifyLASearch(httpRequest,"wrongSearchText",ResponseCodes.BAD_REQUEST);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Send Post Call for LA search api with wrong location format")
	public void verifyBadRequestForWronglocationFormatForLASearch() {
		RequestSpecification httpRequest= null;
		LASearchPage.verifyLASearch(httpRequest,"wrongLocation",ResponseCodes.BAD_REQUEST);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Send Post Call for LA search api with only search text")
	public void verifySuccessStatusWithOnlySearchTextForLASearch() {
		RequestSpecification httpRequest= null;
		LASearchPage.verifyLASearch(httpRequest,"onlySearchText",ResponseCodes.SUCCESS);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Send Post Call for LA search api with no x api key")
	public void verifyForbiddenStatusForLASearch() {
		RequestSpecification httpRequest= null;
		LASearchPage.verifyForbiddenStatusForLASearch(httpRequest);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Send Post Call for LA search api to verify search limit")
	public void verifySearchLimitForLASearch() {
		RequestSpecification httpRequest= null;
		LASearchPage.verifySearchLimitForLASearch(httpRequest,"searchLimit");
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Send Post Call for LA search api to verify default search limit")
	public void verifyDefaultSearchLimitForLASearch() {
		RequestSpecification httpRequest= null;
		LASearchPage.verifySearchLimitForLASearch(httpRequest,"DefaultsearchLimit");
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Send Post Call for LA search api to verify search resources for listing")
	public void verifySearchResourcesWithListingForLASearch() {
		RequestSpecification httpRequest= null;
		LASearchPage.verifySearchResourcesWithListingForLASearch(httpRequest);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Send Post Call for LA search api to verify search resources for tax")
	public void verifySearchResourcesWithTaxForLASearch() {
		RequestSpecification httpRequest= null;
		LASearchPage.verifySearchResourcesWithTaxForLASearch(httpRequest);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify no difference in responses for with and without location latitude and longitude for LA search api")
	public void verifyNoDifferenceForLASearch() {
		RequestSpecification httpRequest= null;
		LASearchPage.verifyDifferenceForLASearch(httpRequest);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify fields in response for LA search api in listing")
	public void verifyFieldsForLASearchListing() throws JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		LASearchPage.verifyFieldsForLASearch(httpRequest,"listings","searchResourcesForListing");
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify fields in response for LA search api in taxrecord")
	public void verifyFieldsForLASearchTax() throws JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		LASearchPage.verifyFieldsForLASearch(httpRequest,"taxRecord","searchResourcesForTax");
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify data for LA search api")
	public void verifyDataForLASearchDiscoveryMethod() {
		RequestSpecification httpRequest= null;
		LASearchPage.verifyDataForLASearch(httpRequest);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify same response for different states for LA search api")
	public void verifyResponseForDifferentStatesLASearchDiscoveryMethod() {
		RequestSpecification httpRequest= null;
		LASearchPage.verifySameResponseForDifferentAddresses(httpRequest);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify city and state in response for different addresses for LA search api")
	public void verifyCityAndStateLASearchDiscoveryMethod() {
		RequestSpecification httpRequest= null;
		LASearchPage.verifyCityAndStateForDifferentAddresses(httpRequest);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify null keyword for null values for LA search api")
	public void verifyUnknownKeywordForLASearchDiscoveryMethod() {
		RequestSpecification httpRequest= null;
		LASearchPage.verifyNullKeyWord(httpRequest);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify no listings if office exclusive is true for LA search api")
	public void verifyOfficeExclusiveForLASearchDiscoveryMethod() {
		RequestSpecification httpRequest= null;
		LASearchPage.verifyOfficeExclusive(httpRequest);
		//This test case is only on test and dev (not prod) because office exclusive YN is not true in any listings in Prod env
	}
	
	
}
