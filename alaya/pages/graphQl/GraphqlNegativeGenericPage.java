package bright.api.alaya.pages.graphQl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import org.testng.util.Strings;

import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.GraphQlQueries;
import bright.api.alaya.utils.MainClassAlaya;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GraphqlNegativeGenericPage extends MainClassAlaya{
	
   static SoftAssert softAssert = new SoftAssert();
	
   public static String getDocName(String documentType, String key,String object) {
		String docName = null ;	
		if(documentType.equalsIgnoreCase("listing"))
			docName = listingDocumentNames.get(CommonUtilityMethods.pickRandomFromList(listingDocumentNames));
		if(documentType.equalsIgnoreCase( "member"))
			docName = memberDocumentNames.get(CommonUtilityMethods.pickRandomFromList(memberDocumentNames));
		if(documentType.equalsIgnoreCase( "office"))
			docName = officeDocumentNames.get(CommonUtilityMethods.pickRandomFromList(officeDocumentNames));
		if(documentType.equalsIgnoreCase( "city"))
			docName = cityDocumentNames.get(CommonUtilityMethods.pickRandomFromList(cityDocumentNames));
		if(documentType.equalsIgnoreCase( "countyrate"))
			docName = countyDocumentNames.get(CommonUtilityMethods.pickRandomFromList(countyDocumentNames));
		if(documentType.equalsIgnoreCase( "taxrecord"))
			docName = taxDocumentNames.get(CommonUtilityMethods.pickRandomFromList(taxDocumentNames));
		if(documentType.equalsIgnoreCase( "subdivision"))
			docName = subDivisionDocumentNames.get(CommonUtilityMethods.pickRandomFromList(subDivisionDocumentNames));
		if(documentType.equalsIgnoreCase( "builder_model"))
			docName = builderModelDocumentNames.get(CommonUtilityMethods.pickRandomFromList(builderModelDocumentNames));
		if(documentType.equalsIgnoreCase( "building_name"))
			docName = buildingNameDocumentNames.get(CommonUtilityMethods.pickRandomFromList(buildingNameDocumentNames));		
			
		return docName;
		}
	
	/**----------------------------------------------------------------------------------------------------------- **/	
	/** ALL METHODS FOR NEGATIVE SCENARIOS**/
	/**----------------------------------------------------------------------------------------------------------- **/
	
	public static void forbiddenCodeStatusForQueryGraphQL(RequestSpecification httpRequest, String documentType, int code, String attributesType, String key, String object) {
		
		JSONArray graphQlAttributesArray = (JSONArray) CommonUtilityMethods.readJsonFile(Constants.graphqlAttributes, attributesType).get("graphQl");
		ArrayList<String> GraphQlAttributesList = CommonUtilityMethods.convertJArrayToListString(graphQlAttributesArray);
		String docName = getDocName(documentType,key,object);
		if(Strings.isNullOrEmpty(docName)) {
			Assert.fail("No valid document found");
		}
		String graphQlQuery = GraphQlQueries.graphQlQuery(key, docName, GraphQlAttributesList, documentType);
		RequestSpecification requestSpec;
		requestSpec = CommonUtilityMethods.graphQlRequestSpec();
		requestSpec.header("x-api-key","wrongKey");
		httpRequest = RestAssured.given();
		requestSpec.body(graphQlQuery);
		httpRequest.spec(requestSpec);
		Response  response = httpRequest.post();
		int statusCode = response.getStatusCode();
		if(statusCode!=code)
			softAssert.fail("Expected code for graphql query is "+ code +" But it was "+ statusCode + " for document type "+documentType+" - "+docName);
		else
			logger.info("Verified Forbidden status code when wrong x-api-key is passed for graphql query");
		String message = response.getBody().asString();
		if(!message.equalsIgnoreCase("{\"message\":\"Forbidden\"}"))
			softAssert.fail("Body was not same as expected for document type "+ documentType +" - "+ docName);
		else
			logger.info("Body error message was also verified for wrong x-api-key");
		
		softAssert.assertAll();	
	}
	
	
	public static void genericBadRequestMethod(String query,RequestSpecification httpRequest,int code,String documentType, String docName, String errorMsg) {
		RequestSpecification requestSpec;
		requestSpec = CommonUtilityMethods.graphQlRequestSpec();
		httpRequest = RestAssured.given();
		requestSpec.body(query);
		httpRequest.spec(requestSpec);
		Response  response = httpRequest.post();
		int statusCode = response.getStatusCode();
		if(statusCode!=code)
			softAssert.fail("Expected code for graphql query is "+ code +" But it was "+ statusCode + " for document type "+documentType+" - "+docName);
		else
			logger.info("Verified Bad Request status code when wrong fieldName is passed for graphql query");
		String message = response.getBody().asString();
		String error = CommonUtilityMethods.convertStrToObj(message).getJSONArray("errors").getJSONObject(0).getJSONObject("extensions").get("code").toString();
		if(!error.equalsIgnoreCase(errorMsg))
			softAssert.fail("Body was not same as expected for document type "+ documentType +" - "+ docName);
		else
			logger.info("Body error message was also verified for wrong fieldName");
		
		softAssert.assertAll();	
	}
	
	
	public static void BadReqForWrongFieldNameForGraphQLQuery(RequestSpecification httpRequest, String documentType, int code, String attributesType, String key, String object) {
		
		ArrayList<String> GraphQlAttributesList = new ArrayList<String>(Arrays.asList("listingI"));
		String docName = getDocName(documentType,key,object);
		if(Strings.isNullOrEmpty(docName)) {
			Assert.fail("No valid document found");
		}
		String graphQlQuery = GraphQlQueries.graphQlQuery(key, docName, GraphQlAttributesList, documentType);
		genericBadRequestMethod(graphQlQuery,httpRequest,code,documentType,docName,"GRAPHQL_VALIDATION_FAILED");
	}
	
	public static void BadReqForWrongMethodForGraphQLQuery(RequestSpecification httpRequest, String documentType, int code, String attributesType, String key, String object) {
		
		ArrayList<String> GraphQlAttributesList = new ArrayList<String>(Arrays.asList("listingId"));
		String docName = getDocName(documentType,key,object);
		if(Strings.isNullOrEmpty(docName)) {
			Assert.fail("No valid document found");
		}
		String graphQlQuery = GraphQlQueries.graphQlQuery(key, docName, GraphQlAttributesList,"wrong");
		genericBadRequestMethod(graphQlQuery,httpRequest,code,documentType,docName,"GRAPHQL_VALIDATION_FAILED");
	}
	
      public static void BadReqForNoFieldsForGraphQLQuery(RequestSpecification httpRequest, String documentType, int code, String attributesType, String key, String object) {
		
		ArrayList<String> GraphQlAttributesList = new ArrayList<String>();
		String docName = getDocName(documentType,key,object);
		if(Strings.isNullOrEmpty(docName)) {
			Assert.fail("No valid document found");
		}
		String graphQlQuery = GraphQlQueries.graphQlQuery(key, docName, GraphQlAttributesList,documentType);
		genericBadRequestMethod(graphQlQuery,httpRequest,code,documentType,docName,"GRAPHQL_PARSE_FAILED");
	}
      
      public static void BadReqForNotIndexedFieldsForGraphQLQuery(RequestSpecification httpRequest, String documentType, int code, String attributesType, String key, String object) {
    	  
    	ArrayList<String> GraphQlAttributesList = new ArrayList<String>(Arrays.asList("listingId"));
  		String docName = getDocName(documentType,key,object);
  		if(Strings.isNullOrEmpty(docName)) {
			Assert.fail("No valid document found");
		}
  		String graphQlQuery = GraphQlQueries.graphQlQuery("daysOnMarket", docName, GraphQlAttributesList,documentType);
  		genericBadRequestMethod(graphQlQuery,httpRequest,code,documentType,docName,"GRAPHQL_VALIDATION_FAILED");
	}

}
