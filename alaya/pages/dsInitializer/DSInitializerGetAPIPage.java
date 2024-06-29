package bright.api.alaya.pages.dsInitializer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class DSInitializerGetAPIPage extends MainClassAlaya {
	
	private static ArrayList<String> initializationState = new ArrayList<String>(Arrays.asList("completed", "failed ","started","notStarted"));
	
	private static ArrayList<String> MessagesPushed = new ArrayList<String>(Arrays.asList("0","1"));

	
	/**
	 * A method to send get call of DS Initializer
	 * @throws InterruptedException 
	 */

	public static void getDSInitializer(RequestSpecification httpRequest,String docType) throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpec();
		httpRequest = RestAssured.given();
		String pathInitializationId = DSInitializerPostAPIPage.initIDMap.get(docType);
		requestSpecification.pathParam("initializationID", pathInitializationId);
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"dsInitializerGet").getString(docType).toString());
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.get();
		String responseBody = response.getBody().asString();
		JSONObject resObj = new JSONObject(responseBody);
		
		/*Verifying Status code*/
		softAssert.assertEquals(response.getStatusCode(), ResponseCodes.SUCCESS_ACCEPTED,"Verified the Get call status code for DS Initialize for document type "+docType);		
		
		/*Verifying Prefix name,docType,subscriberName of getResponse with postPayload*/
		String prefix = DSInitializerPostAPIPage.prefixNameMap.get(docType);
		String expectedSubscriberName = DSInitializerPostAPIPage.subscriberMap.get(docType);
		HashMap<String,String> result = DSInitializerGenericPage.verifyRequestContext(docType, resObj, prefix);
		softAssert.assertEquals(prefix, result.get("prefix"),"Prefix - "+prefix+" in Post call did not match with prefix name - "+result.get("prefix")+" in response");
		softAssert.assertEquals(docType, result.get("documentType"),"Document type - "+docType+" did not match with document type - "+result.get("documentType")+" in response");
		softAssert.assertEquals(expectedSubscriberName,result.get("subscriberName"),"Expected subscriberName was "+expectedSubscriberName+" but it was "+result.get("subscriberName")+" in the response" );
		
		/*Verifying creation date of getcall with postcall and again with current date*/
		String postDate = DSInitializerPostAPIPage.creationDateMap.get(docType);
		HashMap<String,String> dateResult = DSInitializerGenericPage.verifyCreationTime(docType, resObj);
		String getDate = dateResult.get("creationDate");
		String currentDate = dateResult.get("currentDate");
		softAssert.assertEquals(postDate,getDate, "Post call Date is "+postDate+" which did not match with get call Date that is "+getDate);
		softAssert.assertEquals(getDate,currentDate, "Current Date is "+currentDate+" which did not match with creation Date that is "+getDate);
		
		/*Verifying initialization status is completed*/
		String status = DSInitializerGenericPage.verifyInitializationStatus(resObj);
		softAssert.assertEquals(initializationState.contains(status), true,"Status was - "+status+" in response, which did not match with completed status for get call API");
	    
		/*Verifying Sns messages pushed number is 1*/
		String messagesPushed = DSInitializerGenericPage.verifyMessagesPushed(resObj);
		softAssert.assertEquals(MessagesPushed.contains(messagesPushed), true,"MessagesPushed were - "+messagesPushed+" in response, which did not match with 1");      
	    
	    /*Verifying initialization id of getResponse is same as postReponse*/
	    String getInitID = resObj.get("initializationID").toString();
	    softAssert.assertEquals(pathInitializationId, getInitID ,"Initialization ID of post call "+pathInitializationId+" did not match with Initialization ID of get call "+getInitID);   
	    			    		
	    softAssert.assertAll();
	    logger.info("GET call flow verified for DS initializer for document type "+docType);
	}

	
	/**
	 * A method to verify the Invalid Id error for DS Initializer Get call
	 */

	public static void verifyInvalidIdErrorForGetInitializationStatus(RequestSpecification httpRequest,String docType) {
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpec();
		httpRequest = RestAssured.given();
		requestSpecification.pathParam("initializationID", UUID.randomUUID());
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"dsInitializerGet").getString(docType).toString());
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.get();
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.INVALID_ID,"Could not verify Invalid error for DS intializer get call for document type "+docType);
		logger.info("Verified the Invalid Id Error for DS Initializer Get call for document type "+docType);
	}

	/**
	 * A method to verify the Forbidden error for DS Initializer Get call
	 */

	public static void verifyForbiddenForGetInitializationStatus(RequestSpecification httpRequest,String docType) {
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpec();
		httpRequest = RestAssured.given();
		String pathInitializationId = DSInitializerPostAPIPage.initIDMap.get(docType);
		requestSpecification.pathParam("initializationID", pathInitializationId);
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"dsInitializerGet").getString(docType).toString());
		requestSpecification.headers("x-api-key","check");                 //passing invalid value for x-api-key
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.get();
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.FORBIDDEN,"Could not verify forbidden error for DS initializer get call for document Type "+docType);
		logger.info("Verified the forbidden error for DS Initializer Get call for document type "+docType);
	}

	/**
	 * A method to verify the Not found error for DS Initializer Get call
	 */

	public static void verifyNotFoundForGetInitializationStatus(RequestSpecification httpRequest,String docType) {
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpec();
		httpRequest = RestAssured.given();
		String pathInitializationId = DSInitializerPostAPIPage.initIDMap.get(docType);
		requestSpecification.pathParam("initializationID", pathInitializationId);
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"dsInitializerGet").getString("wrong").toString());
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.get();
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.NOT_FOUND,"Could not verify not found error for DS initializer get call for document Type "+docType);
		logger.info("Verified the Not found error for DS Initializer Get call for document type "+docType);
	}
}
