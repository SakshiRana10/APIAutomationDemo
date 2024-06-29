package bright.api.alaya.pages.graphQl;

import static org.testng.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import org.testng.util.Strings;

import bright.api.alaya.pages.discoveryLayer.discoveryLayerUtilityPage;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.GraphQlQueries;
import bright.api.alaya.utils.MainClassAlaya;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GraphqlGenericPage extends MainClassAlaya{
	//static SoftAssert softAssert = new SoftAssert();
	
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
	
	
	/*A method to verify response structure
	 * */
	public static void verifyResponseStructure(Response response,String documentType) {
		SoftAssert soft = new SoftAssert();
		String responseBody = response.getBody().asString();
		JSONObject data = new JSONObject();
		JSONArray returnFields = new JSONArray();
		JSONObject responseObj = new JSONObject(responseBody);
		//verifying data object
		if(responseObj.has("data")) {
		data = responseObj.getJSONObject("data");
		}
		else {
		soft.fail("Data object not found in response body");
		}
		//verifying return fields array 
		if(data.has(GraphQlQueries.graphQlMethods.get(documentType))) {
		String docObjectStr = GraphQlQueries.graphQlMethods.get(documentType);
		JSONObject docObject = data.getJSONObject(docObjectStr);
		returnFields = docObject.getJSONArray("returnFields");
		}
		else {
		soft.fail("Return fields array not found in response body");
		}
		soft.assertAll();
	}
	
	/**----------------------------------------------------------------------------------------------------------- **/	
	/** ALL METHODS FOR CODE STATUS VERIFICATION FOR DIFF TYPES OF GRAPHQL QUERIES **/
	/**----------------------------------------------------------------------------------------------------------- **/
		
	public static SoftAssert genericCodeVerificationMethod(String query,RequestSpecification httpRequest,int code,String documentType, String queryType) {
		SoftAssert softAssert = new SoftAssert();
		RequestSpecification requestSpec;
		requestSpec = CommonUtilityMethods.graphQlRequestSpec();
		httpRequest = RestAssured.given();
		requestSpec.body(query);
		httpRequest.spec(requestSpec);
		Response  response = httpRequest.post();
		verifyResponseStructure(response,documentType);    //verifying response structure
		int statusCode = response.getStatusCode();
		if(statusCode!=code) {
			softAssert.fail("Expected code for graphql "+queryType+" query is "+ code +" But it was "+ statusCode + " for document type "+documentType);		
			logger.info("Error Response Body is : "+response.getBody().asString());
		}
		else
			logger.info("Verified Success status code for graphql "+queryType+" query for " + documentType);
		
		return softAssert;
	}
	
	public static void codeStatusForBasicQueryGraphQL(RequestSpecification httpRequest, String documentType, int code, String attributesType, String key,String object) {
		SoftAssert softAssert = new SoftAssert();
		JSONArray graphQlAttributesArray = (JSONArray) CommonUtilityMethods.readJsonFile(Constants.graphqlAttributes, attributesType).get("graphQl");
		ArrayList<String> GraphQlAttributesList = CommonUtilityMethods.convertJArrayToListString(graphQlAttributesArray);
		String docName = getDocName(documentType,key,object);
		if(Strings.isNullOrEmpty(docName)) {
			Assert.fail("No valid document found");
		}
		String graphQlQuery = GraphQlQueries.graphQlQuery(key, docName, GraphQlAttributesList, documentType);
		softAssert = genericCodeVerificationMethod(graphQlQuery,httpRequest,code,documentType,"Basic");	
		softAssert.assertAll();
	}//performed for all docTypes
	
	public static void codeStatusForRangeQueryGraphQL(RequestSpecification httpRequest, String documentType, int code, String attributesType, String gt, String lt, String key) {
		SoftAssert softAssert = new SoftAssert();
		ArrayList<String> GraphQlAttributesList = new ArrayList<String>(Arrays.asList(key));
		String graphQlQuery = GraphQlQueries.graphQlRangeQuery(GraphQlAttributesList, documentType, gt,lt);
		softAssert = genericCodeVerificationMethod(graphQlQuery,httpRequest,code,documentType,"Range");	
		softAssert.assertAll();
	}//performed for Listing
	
	public static void codeStatusForNestedQueryGraphQL(RequestSpecification httpRequest, String documentType, int code, String attributesType, String key) {
		SoftAssert softAssert = new SoftAssert();
		ArrayList<String> GraphQlAttributesList = new ArrayList<String>(Arrays.asList(key));
		String graphQlQuery = GraphQlQueries.graphQlNestedQuery("205001868217",GraphQlAttributesList, documentType);
		softAssert = genericCodeVerificationMethod(graphQlQuery,httpRequest,code,documentType,"Nested");	
		softAssert.assertAll();
	}//performed for member
	
	public static void codeStatusForConditionalQueryGraphQL(RequestSpecification httpRequest, String documentType, int code, String attributesType, String key) {
		SoftAssert softAssert = new SoftAssert();
		ArrayList<String> GraphQlAttributesList = new ArrayList<String>(Arrays.asList(key));
		String graphQlQuery = GraphQlQueries.graphQlConditinalQuery(GraphQlAttributesList, documentType, "RETS Acc01","RETS Acc02");
		softAssert = genericCodeVerificationMethod(graphQlQuery,httpRequest,code,documentType,"Conditional");	
		softAssert.assertAll();
	}//performed for office
	
	/**----------------------------------------------------------------------------------------------------------- **/	
	/** METHOD FOR DATA VERIFICATION**/
	/**----------------------------------------------------------------------------------------------------------- **/
	
	public static void DataVerificationForBasicGraphQL(RequestSpecification httpRequest, String documentType, String attributesType,String key,String graphQLMethod,String object) {
		//Calling discoveryLayer and retrieving attributes to be Matched
		ArrayList<String> AttributeNamesForDiscovery = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.graphqlAttributes, attributesType).get("discovery"));
		ArrayList<String> AttributesFromDiscovery = new ArrayList<String>();
		String docName = getDocName(documentType,key,object);
		if(Strings.isNullOrEmpty(docName)) {
			Assert.fail("No valid document found");
		}
		String attributeResponse = discoveryLayerUtilityPage.getCallForDiscoveryLayer(httpRequest,"Bright","true","true",documentType,docName).getBody().asString();
	    logger.info("Data verification is happening on this get response for graphql -"+ attributeResponse);
		JSONObject attributeResponseBody = new JSONObject(attributeResponse).getJSONObject("content").getJSONObject(object);
	    for(int i = 0; i<AttributeNamesForDiscovery.size(); i++) {
	    	String attribute = AttributeNamesForDiscovery.get(i);
			String attributeToAdd = null ;
		    if(attributeResponseBody.has(attribute)) {
		    	attributeToAdd = attributeResponseBody.get(AttributeNamesForDiscovery.get(i)).toString();
		    }
		    else{
		    	attributeToAdd = JSONObject.NULL.toString();
		    }
		    AttributesFromDiscovery.add(attributeToAdd);	
          }
	    
	   //Calling graphql and and retrieving attributes to be Matched with discovery Layer
	    ArrayList<String> AttributeNamesForGraphQL = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.graphqlAttributes, attributesType).get("graphQl"));
	    ArrayList<String> AttributesFromGraphQL = CommonUtilityMethods.fetchGraphQlResponseByDocName(documentType, docName,graphQLMethod,key,AttributeNamesForGraphQL);	    
		logger.info("Discovery - "+AttributesFromDiscovery);
		logger.info("Graphql - "+AttributesFromGraphQL);
	    Assert.assertEquals(AttributesFromDiscovery.equals(AttributesFromGraphQL), true,"Attributes from discovery were - "+AttributesFromDiscovery+ " Attributes from graphql were - "+AttributesFromGraphQL);
	    logger.info("Verified attributes from Discovery Layer are matching graphQL Response for "+ documentType);
	    
	}
	
}
