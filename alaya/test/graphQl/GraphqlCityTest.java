package bright.api.alaya.test.graphQl;

import java.io.IOException;
import org.testng.annotations.Test;
import bright.api.alaya.pages.graphQl.GraphqlGenericPage;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import io.restassured.specification.RequestSpecification;

public class GraphqlCityTest extends MainClassAlaya{
	
	protected static String basicAttributeType = "basicCity";
	protected static final String KEY = "ctyCityKey" ;
	protected static final String documentType = "city";
	protected static final String graphQLMethod = "getCities";
	

	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify success status code for basic graphql query for City")
	public void verifySuccessCodeForBasicGraphQLQueryCity() throws InterruptedException, IOException{
		RequestSpecification httpRequest= null;
		GraphqlGenericPage.codeStatusForBasicQueryGraphQL(httpRequest,documentType, ResponseCodes.SUCCESS, basicAttributeType,KEY,"lmsObject");		
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify data for basic graphql query for City")
	public void verifyDataForBasicGraphQLQueryCity() throws InterruptedException, IOException{
		RequestSpecification httpRequest= null;
		GraphqlGenericPage.DataVerificationForBasicGraphQL(httpRequest,documentType, basicAttributeType,KEY,graphQLMethod,"lmsObject");		
	}

}
