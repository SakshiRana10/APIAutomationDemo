package bright.api.alaya.test.graphQl;

import java.io.IOException;

import org.testng.annotations.Test;

import bright.api.alaya.pages.graphQl.GraphqlNegativeGenericPage;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import io.restassured.specification.RequestSpecification;

public class GraphqlNegativeTest extends MainClassAlaya {
	protected static String basicAttributeType = "basicListing";
	protected static final String KEY = "listingKey" ;
	protected static final String documentType = "listing";
	protected static final String graphQLMethod = "getListings";
	protected static final String object = "lmsObject";
	
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify forbidden for no x-api-key in graphql query Listing")
	public void verifyforbiddenCodeForGraphQLQuery() throws InterruptedException, IOException{
		RequestSpecification httpRequest= null;
		GraphqlNegativeGenericPage.forbiddenCodeStatusForQueryGraphQL(httpRequest,documentType, ResponseCodes.FORBIDDEN, basicAttributeType,KEY,object);		
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify Bad Request For Wrong FieldName in graphql query Listing")
	public void verifyBadReqForWrongFieldNameForGraphQLQuery() throws InterruptedException, IOException{
		RequestSpecification httpRequest= null;
		GraphqlNegativeGenericPage.BadReqForWrongFieldNameForGraphQLQuery(httpRequest,documentType, ResponseCodes.BAD_REQUEST, basicAttributeType,KEY,object);		
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify Bad Request For Wrong graphQL method in graphql query Listing")
	public void verifyBadReqForWrongMethodForGraphQLQuery() throws InterruptedException, IOException{
		RequestSpecification httpRequest= null;
		GraphqlNegativeGenericPage.BadReqForWrongMethodForGraphQLQuery(httpRequest,documentType, ResponseCodes.BAD_REQUEST, basicAttributeType,KEY,object);		
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify Bad Request For no return fields in graphql query Listing")
	public void verifyBadReqForNoFieldsForGraphQLQuery() throws InterruptedException, IOException{
		RequestSpecification httpRequest= null;
		GraphqlNegativeGenericPage.BadReqForNoFieldsForGraphQLQuery(httpRequest,documentType, ResponseCodes.BAD_REQUEST, basicAttributeType,KEY,object);		
	}

	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify Bad Request For not indexed return fields in graphql query Listing")
	public void verifyBadReqForNotIndexedFieldsForGraphQLQuery() throws InterruptedException, IOException{
		RequestSpecification httpRequest= null;
		GraphqlNegativeGenericPage.BadReqForNotIndexedFieldsForGraphQLQuery(httpRequest,documentType, ResponseCodes.BAD_REQUEST, basicAttributeType,KEY,object);		
	}
}
