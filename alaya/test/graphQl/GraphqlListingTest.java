package bright.api.alaya.test.graphQl;

import java.io.IOException;
import org.testng.annotations.Test;
import bright.api.alaya.pages.graphQl.GraphqlGenericPage;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import io.restassured.specification.RequestSpecification;

public class GraphqlListingTest extends MainClassAlaya {
	
	protected static String basicAttributeType = "basicListing";
	protected static final String KEY = "listingKey" ;
	protected static final String documentType = "listing";
	protected static final String graphQLMethod = "getListings";
	

	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify success status code for basic graphql query for listing")
	public void verifySuccessCodeForBasicGraphQLQueryListing() throws InterruptedException, IOException{
		RequestSpecification httpRequest= null;
		GraphqlGenericPage.codeStatusForBasicQueryGraphQL(httpRequest,documentType, ResponseCodes.SUCCESS, basicAttributeType,KEY,"lmsObject");		
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify data for basic graphql query for listing")
	public void verifyDataForBasicGraphQLQueryListing() throws InterruptedException, IOException{
		RequestSpecification httpRequest= null;
		GraphqlGenericPage.DataVerificationForBasicGraphQL(httpRequest,documentType, basicAttributeType,KEY,graphQLMethod,"lmsObject");		
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify success status code for Range graphql query for listing")
	public void verifySuccessCodeForRangeGraphQLQueryListing() throws InterruptedException, IOException{
		RequestSpecification httpRequest= null;
		GraphqlGenericPage.codeStatusForRangeQueryGraphQL(httpRequest,documentType, ResponseCodes.SUCCESS, basicAttributeType,"1000004058","MDAL100020",KEY);		
	}
	
	
}
