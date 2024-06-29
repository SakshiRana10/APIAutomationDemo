package bright.api.alaya.test.graphQl;

import java.io.IOException;

import org.testng.annotations.Test;

import bright.api.alaya.pages.graphQl.GraphqlGenericPage;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import io.restassured.specification.RequestSpecification;

public class GraphqlCountyRateTest extends MainClassAlaya{
	
	protected static String basicAttributeType = "basicCountyRate";
	protected static final String KEY = "fileName" ;
	protected static final String documentType = "countyrate";
	protected static final String graphQLMethod = "getCountyRates";
	

	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify success status code for basic graphql query for CountyRate")
	public void verifySuccessCodeForBasicGraphQLQueryCountyRate() throws InterruptedException, IOException{
		RequestSpecification httpRequest= null;
		GraphqlGenericPage.codeStatusForBasicQueryGraphQL(httpRequest,documentType, ResponseCodes.SUCCESS, basicAttributeType,KEY,"items");		
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify data for basic graphql query for CountyRate")
	public void verifyDataForBasicGraphQLQueryCountyRate() throws InterruptedException, IOException{
		RequestSpecification httpRequest= null;
		GraphqlGenericPage.DataVerificationForBasicGraphQL(httpRequest,documentType, basicAttributeType,KEY,graphQLMethod,"items");		
	}

}
