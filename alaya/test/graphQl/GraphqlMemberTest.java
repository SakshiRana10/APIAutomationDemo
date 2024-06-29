package bright.api.alaya.test.graphQl;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import bright.api.alaya.pages.graphQl.GraphqlGenericPage;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import io.restassured.specification.RequestSpecification;

public class GraphqlMemberTest extends MainClassAlaya{
	
	public static String basicAttributeType = "basicMember";
	private static final String KEY = "memberKey" ;
	protected static final String documentType = "member";
	protected static final String graphQLMethod = "getMembers";
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify success status code for basic graphql query for member")
	public void verifySuccessCodeForBasicGraphQLQueryMember() throws InterruptedException, IOException{
		RequestSpecification httpRequest= null;
		GraphqlGenericPage.codeStatusForBasicQueryGraphQL(httpRequest,documentType, ResponseCodes.SUCCESS, basicAttributeType,KEY,"lmsObject");		
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify data for basic graphql query for member")
	public void verifyDataForBasicGraphQLQueryMember() throws InterruptedException, IOException{
		RequestSpecification httpRequest= null;
		GraphqlGenericPage.DataVerificationForBasicGraphQL(httpRequest,documentType, basicAttributeType,KEY,graphQLMethod,"lmsObject");		
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify success status code for nested graphql query for member")
	public void verifySuccessCodeForNestedGraphQLQueryMember() throws InterruptedException, IOException{
		RequestSpecification httpRequest= null;
		GraphqlGenericPage.codeStatusForNestedQueryGraphQL(httpRequest,documentType, ResponseCodes.SUCCESS, basicAttributeType,KEY);		
	}
}
