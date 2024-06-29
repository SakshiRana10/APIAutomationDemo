package bright.api.alaya.test.graphQl;

import java.io.IOException;

import org.testng.annotations.Test;

import bright.api.alaya.pages.graphQl.GraphqlGenericPage;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import io.restassured.specification.RequestSpecification;

public class GraphqlBuilderModelTest extends MainClassAlaya {
	
	public static String basicAttributeType = "basicBuilderModel";
	private static final String KEY = "builderModelKey" ;
	protected static final String documentType = "builder_model";
	protected static final String graphQLMethod = "getBuilderModels";
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify success status code for basic graphql query for BuilderModel")
	public void verifySuccessCodeForBasicGraphQLQueryBuilderModel() throws InterruptedException, IOException{
		RequestSpecification httpRequest= null;
		GraphqlGenericPage.codeStatusForBasicQueryGraphQL(httpRequest,documentType, ResponseCodes.SUCCESS, basicAttributeType,KEY,"lmsObject");			
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify data for basic graphql query for BuilderModel")
	public void verifyDataForBasicGraphQLQueryBuilderModel() throws InterruptedException, IOException{
		RequestSpecification httpRequest= null;
		GraphqlGenericPage.DataVerificationForBasicGraphQL(httpRequest,documentType, basicAttributeType,KEY,graphQLMethod,"lmsObject");		
	}

}
