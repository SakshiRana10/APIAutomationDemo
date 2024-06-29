package bright.api.alaya.pages.dsInitializer;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.EnvSpecificMethods;
import bright.api.alaya.utils.MainClassAlaya;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import bright.api.alaya.utils.ResponseCodes;

public class DSInitializerPostAPIPage extends MainClassAlaya {

	static SoftAssert softAssert = new SoftAssert();
	public static HashMap<String, String> initIDMap = new HashMap<String,String>();
	public static HashMap<String, String> prefixNameMap = new HashMap<String,String>();
	public static HashMap<String, String> subscriberMap = new HashMap<String,String>();
	public static HashMap<String, String> creationDateMap = new HashMap<String,String>();
	
	
	/**
	 * A method to send post call to DS Initilaizer
	 */

	public static void sendPostToDsInitializer(RequestSpecification httpRequest,String docType) {
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpec();
		httpRequest = RestAssured.given();
		String payload = CommonUtilityMethods.readJsonFile(property.getProperty("dsInitializerPayloadJsonPath"),docType).toString();
		JSONObject payloadObj = new JSONObject(payload);
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"dsInitializer").getString(docType).toString());
		requestSpecification.body(payload);
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.post();
		String responseBody = response.getBody().asString();
		JSONObject resObj = new JSONObject(responseBody);
		
		/*Verifying Status code*/
		softAssert.assertEquals(response.getStatusCode(), ResponseCodes.SUCCESS_ACCEPTED,"Could not verify the Post call status code for DS Initializer for docType "+docType);
		String initID = resObj.get("initializationId").toString();
		initIDMap.put(docType, initID);
		String prefix = payloadObj.get("objectNamePrefix").toString();
		prefixNameMap.put(docType, prefix);
		
		/*Verifying Prefix name, document type and subscriber name*/
		HashMap<String,String> result = DSInitializerGenericPage.verifyRequestContext(docType, resObj, prefix);
		softAssert.assertEquals(prefix, result.get("prefix"),"Prefix - "+prefix+" in body did not matched with prefix name - "+result.get("prefix")+" in response");
		softAssert.assertEquals(docType, result.get("documentType"),"Document type - "+docType+" did not match with document type - "+result.get("documentType")+" in response");
		String expectedSubscriberName = docType+"-indexer";
		softAssert.assertEquals(expectedSubscriberName,result.get("subscriberName"),"Expected subscriberName was "+expectedSubscriberName+" but it was "+result.get("subscriberName")+" in the response" );
		subscriberMap.put(docType,result.get("subscriberName"));
		
		/*Verifying creation date with current date*/
		HashMap<String,String> dateResult = DSInitializerGenericPage.verifyCreationTime(docType, resObj);
		creationDateMap.put(docType, dateResult.get("creationDate"));
		softAssert.assertEquals(dateResult.get("currentDate"), dateResult.get("creationDate") , "Current Date is "+dateResult.get("currentDate")+" which did not match with creation Date that is "+dateResult.get("creationDate"));
		
		/*Verifying initialization status*/
		String status = DSInitializerGenericPage.verifyInitializationStatus(resObj);
		softAssert.assertEquals(status, "notStarted","Status was - "+status+" in response, which did not match with notStarted status for initialize API");
	    
		/*Verifying Sns messages pushed number*/
		String messagesPushed = DSInitializerGenericPage.verifyMessagesPushed(resObj);
	    softAssert.assertEquals(messagesPushed, "0","MessagesPushed were - "+messagesPushed+" in response, which did not match with 0");
	    
	    softAssert.assertAll();
	    logger.info("POST call flow verified for DS initializer for document type "+docType);
	}

	/**
	 * A method to verify bad request for post call of DS Initilaizer
	 */

	public static void verifyBadRequestForDsInitializerPostCall(RequestSpecification httpRequest,String docType) {
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpec();
		httpRequest = RestAssured.given();
		String payload = CommonUtilityMethods.readJsonFile(property.getProperty("dsInitializerPayloadJsonPath"),"wrong").toString();
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"dsInitializer").getString(docType).toString());
		requestSpecification.body(payload);
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.post();
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.BAD_REQUEST,"Expected code is "+ResponseCodes.BAD_REQUEST+" but it was "+response.getStatusCode()+" for documentType "+docType+" for post call DS initializer");
		logger.info("Verified the bad request for DS Initializer Post Call for doc type "+docType);
	}

	/**
	 * A method to verify the Forbidden error for Post call of DS Initializer
	 */

	public static void verifyForbiddenForDsInitializerPostCall(RequestSpecification httpRequest,String docType) {
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpec();
		httpRequest = RestAssured.given();
		String payload = CommonUtilityMethods.readJsonFile(property.getProperty("dsInitializerPayloadJsonPath"),docType).toString();
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"dsInitializer").getString(docType).toString());
		requestSpecification.headers("x-api-key","check");                 //passing invalid value for x-api-key
		requestSpecification.body(payload);
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.post();
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.FORBIDDEN,"Expected code is "+ResponseCodes.FORBIDDEN+" but it was "+response.getStatusCode()+" for documentType "+docType+" for post call DS initializer");
		logger.info("Verified the Forbidden error for Post Call of DS Initializer for doc type "+docType);
	}
}
