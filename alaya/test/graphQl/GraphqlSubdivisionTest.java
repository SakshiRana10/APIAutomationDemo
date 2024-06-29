package bright.api.alaya.test.graphQl;

import java.io.IOException;

import org.testng.annotations.Test;

import bright.api.alaya.pages.graphQl.GraphqlGenericPage;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import io.restassured.specification.RequestSpecification;

public class GraphqlSubdivisionTest extends MainClassAlaya{
	
	public static String basicAttributeType = "basicSubdivision";
	private static final String KEY = "loSubdivisionKey" ;
	protected static final String documentType = "subdivision";
	protected static final String graphQLMethod = "getSubdivisions";
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify success status code for basic graphql query for subdivision")
	public void verifySuccessCodeForBasicGraphQLQuerySubdivision() throws InterruptedException, IOException{
		RequestSpecification httpRequest= null;
		GraphqlGenericPage.codeStatusForBasicQueryGraphQL(httpRequest,documentType, ResponseCodes.SUCCESS, basicAttributeType,KEY,"lmsObject");			
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify data for basic graphql query for subdivision")
	public void verifyDataForBasicGraphQLQuerySubdivision() throws InterruptedException, IOException{
		RequestSpecification httpRequest= null;
		GraphqlGenericPage.DataVerificationForBasicGraphQL(httpRequest,documentType, basicAttributeType,KEY,graphQLMethod,"lmsObject");		
	}

}
