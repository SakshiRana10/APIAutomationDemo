package bright.api.alaya.test.graphQl;

import java.io.IOException;

import org.testng.annotations.Test;

import bright.api.alaya.pages.graphQl.GraphqlGenericPage;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import io.restassured.specification.RequestSpecification;

public class GraphqlTaxrecordsTest extends MainClassAlaya{
	
	public static String basicAttributeType = "basicTaxrecords";
	private static final String KEY = "fileName" ;
	protected static final String documentType = "taxrecord";
	protected static final String graphQLMethod = "getTaxRecords";
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify success status code for basic graphql query for TaxRecords")
	public void verifySuccessCodeForBasicGraphQLQueryTaxRecords() throws InterruptedException, IOException{
		RequestSpecification httpRequest= null;
		GraphqlGenericPage.codeStatusForBasicQueryGraphQL(httpRequest,documentType, ResponseCodes.SUCCESS, basicAttributeType,KEY,"items");			
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify data for basic graphql query for TaxRecords")
	public void verifyDataForBasicGraphQLQueryTaxRecords() throws InterruptedException, IOException{
		RequestSpecification httpRequest= null;
		GraphqlGenericPage.DataVerificationForBasicGraphQL(httpRequest,documentType, basicAttributeType,KEY,graphQLMethod,"items");		
	}

}
