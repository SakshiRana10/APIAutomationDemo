package bright.api.alaya.pages.indexInitializer;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import org.testng.util.Strings;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class IndexInitializerPage extends MainClassAlaya{
	
	public static String getRandomDocName(String docType) {
		String docName = null;
		ArrayList<String> docNames = CommonUtilityMethods.docListMap.get(docType);
		docName = docNames.get(CommonUtilityMethods.pickRandomFromList(docNames));
		return docName;
	}
	
	/**
	 * A method to call post for index initializer for all doc Types
	 */
	
	public static Response dsInitializerPostCall(RequestSpecification httpRequest,String docName, String payloadProperty,String baseProperty) {
		SoftAssert softAssert = new SoftAssert();
		RequestSpecification requestSpecification;               //POST CALLING OF DS INITIALIZER      
		requestSpecification =  CommonUtilityMethods.requestSpec();
		httpRequest = RestAssured.given();
		JSONObject payloadOBJ = CommonUtilityMethods.readJsonFile(property.getProperty("dsInitializerPayloadJsonPath"),payloadProperty);
		payloadOBJ.putOpt("objectNamePrefix", docName);
		String payload = payloadOBJ.toString();
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"dsInitializer").getString(baseProperty).toString());
		requestSpecification.body(payload);
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.post();
		logger.info("Response for ds initializer post call - "+response.getBody().asString());
		softAssert.assertEquals(ResponseCodes.SUCCESS_ACCEPTED, response.getStatusCode(),
				"Success code was not received for DS initializer for doc type - " + baseProperty
						+ " Code receieved was - " + response.getStatusCode());
		softAssert.assertAll();
		return response;
	}
	
	/**
	 * A method to verify bad request for index Initializer for all doc Types
	 */
	
	public static Response verifyBadRequestForIndexInitializerPostCall(RequestSpecification httpRequest,String payloadProperty, String baseProperty) {
		RequestSpecification requestSpecification;               //POST CALLING OF DS INITIALIZER TO GET BAD REQUEST STATUS     
		requestSpecification = CommonUtilityMethods.requestSpec();
		httpRequest = RestAssured.given();
		String payload = CommonUtilityMethods.readJsonFile(property.getProperty("dsInitializerPayloadJsonPath"),payloadProperty).toString();
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"dsInitializer").getString(baseProperty).toString());
		requestSpecification.body(payload);
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.post();
		logger.info("Response for ds initializer bad request post call - "+response.getBody().asString());
		return response;

	}
	
	/**
	 * A method to verify forbidden request for index Initializer for all doc Types
	 */

	public static Response verifyForbiddenForIndexInitializerPostCall(RequestSpecification httpRequest,String docType) {
		RequestSpecification requestSpecification;    //POST CALLING OF DS INITIALIZER TO GET FORBIDDEN STATUS FOR WRONG X-API-KEY                   
		requestSpecification = CommonUtilityMethods.requestSpec();
		httpRequest = RestAssured.given();
		String payload = CommonUtilityMethods.readJsonFile(property.getProperty("dsInitializerPayloadJsonPath"),docType).toString();
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"dsInitializer").getString(docType).toString());
		requestSpecification.headers("x-api-key","check");                 
		requestSpecification.body(payload);
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.post();
		logger.info("Response for ds initializer forbidden post call - "+response.getBody().asString());
		return response;
	}
	
	/**
	 * A method to verify index Initializer for all doc Types
	 * @throws InterruptedException 
	 */

	public static void postIndexInitializer(RequestSpecification httpRequest,String docType,ArrayList<String> AttributeNamesForDocStore, ArrayList<String> AttributeNamesForES) throws InterruptedException, JsonMappingException, JsonProcessingException {
		String docName = getRandomDocName(docType);
	    if(Strings.isNullOrEmpty(docName)) {
			Assert.fail("No valid document found for "+docType+ " while executing Index initializer test case ");
		}
	    String payloadstr = "indexInitializer"+docType+"Payload";
	    Response response = dsInitializerPostCall(httpRequest,docName,payloadstr, docType);
		String responseBody = response.getBody().asString();
		logger.info("Response for ds initializer verify attributes post call - "+responseBody);
		logger.info("DS Initialiser post call completed for doctype - " + docType + " and docName - "+ docName);	 
		
		ArrayList<String> documentIndexTime = new ArrayList<String>(Arrays.asList("documentIndexTime"));
		String creationTime = CommonUtilityMethods.fetchStringValueFromApiResponse(responseBody,"creationTime");
		
		String key = CommonUtilityMethods.documentKeys.get(docType);
		String method = CommonUtilityMethods.graphQlMethods.get(docType);
	    ArrayList<String> AttributesFromDocStore = CommonUtilityMethods.fetchAttributesFromDocStore(docName, AttributeNamesForDocStore, docType);	
		ArrayList<String> AttributesFromES = CommonUtilityMethods.fetchGraphQlResponseByDocName(docType,docName,method,key,AttributeNamesForES);    
		Assert.assertEquals(AttributesFromDocStore.equals(AttributesFromES), true,"Attributes from document store and graphql did not match. Attributes from doc Store were - "+AttributesFromDocStore+" Attributes from ES were -"+AttributesFromES);
		logger.info("Index Initializer verified for doctype - "+ docType + " and docName - "+ docName);
		
		Thread.sleep(40000);
		
		String docIndexTime =  CommonUtilityMethods.fetchGraphQlResponseForSingleField(docType, docName,method,key,documentIndexTime);
		Assert.assertTrue(convertUTC(docIndexTime).after(convertUTC(creationTime)),"Document index time and creation time did not match. Document index time was - "+docIndexTime+" Creation time was - "+creationTime);	
		logger.info("DS Initialiser current time verified with Elastic search documentIndexTime for document " + docName);	
	    logger.info("--Index Initializer verified for "+ docType +" type documents--");
	}

	
	/**
	 * A method to verify bad request for post call of index initializers for all doc types
	 */
	
	public static void verifyBadRequestForIndexInitializerPostCallMethod(RequestSpecification httpRequest,String docType, String wrongDocType) {
		Response responseListing = verifyBadRequestForIndexInitializerPostCall(httpRequest,docType,wrongDocType);
		Assert.assertEquals(responseListing.getStatusCode(), ResponseCodes.BAD_REQUEST," Status code mismatch found. Expected was - "+ResponseCodes.BAD_REQUEST+ " Found was - "+ responseListing.getStatusCode());
		logger.info("Verified the bad request for index Initializer Post Call for doc type - "+docType);
	}
	
	
	/**
	 * A method to verify the Forbidden error for Post call of index Initializers for all doc types
	 */
	
	public static void verifyForbiddenForIndexInitializerPostCallMethod(RequestSpecification httpRequest,String docType) {
		Response responseListing = verifyForbiddenForIndexInitializerPostCall(httpRequest,docType);
		Assert.assertEquals(responseListing.getStatusCode(), ResponseCodes.FORBIDDEN," Status code mismatch found. Expected was - "+ResponseCodes.FORBIDDEN+ " Found was - "+ responseListing.getStatusCode());
		logger.info("Verified the Forbidden error for Post Call of index Initializer for doc type - "+docType);
	}
	
	
	public static Timestamp convertUTC(String time) {
		Timestamp ts=null;
		 String output=Instant.parse( time )                
          	     .atOffset( ZoneOffset.UTC )                             
          	     .format(                                                   
          	         DateTimeFormatter.ofPattern( "uuuu-MM-dd HH:mm:ss" )  );;
          	        ts = Timestamp.valueOf(output);  
		return ts;
		
	}
}


