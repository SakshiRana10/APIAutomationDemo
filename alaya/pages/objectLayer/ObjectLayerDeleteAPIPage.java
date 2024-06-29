package bright.api.alaya.pages.objectLayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.commons.collections4.CollectionUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.EnumsUtility;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import bright.api.alaya.utils.EnumsUtility.EnumObjectType;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ObjectLayerDeleteAPIPage extends MainClassAlaya {

	/**
	 * A method to call get API of discovery layer
	 */

	public static Response deleteCallForObjectyLayer(RequestSpecification httpRequest, String systemLoc, String doctype, String docName) {

		if(CommonUtilityMethods.getFeatureToggle(doctype)){
			RequestSpecification requestSpecification;
			requestSpecification = CommonUtilityMethods.requestSpecForObjectLayer();
			requestSpecification.headers("x-session-key",sessionKey);  
			requestSpecification.headers("Authorization", "Bearer " + bearerToken);
			httpRequest = RestAssured.given();
			String systemLocale = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"systemLocale").getString(systemLoc);
			String docType = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"doctype").getString(doctype);	
			String docuName = docName;		
			requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"objectLayer").getString("getDocument").toString());	
			requestSpecification.pathParam("systemLocale", systemLocale);
			requestSpecification.pathParam("docType", docType);
			requestSpecification.pathParam("docName", docuName);
			requestSpecification.queryParam("requestConsistency", "true");
			httpRequest.spec(requestSpecification);
			Response  response = httpRequest.delete();
			logger.info(response.asString());
			return response;
		}

		else
		{
			logger.info("Toggle is Currently OFF for:"+ doctype);
			return null;
		}

	}
	
	/* A Method to verify delete API response structure */
	
	public static void verifyResponseForDeleteObjectAPI(String response, String docType, String docName) {
		SoftAssert softAssert = new SoftAssert();
        ArrayList<String> apiInfoFields = new ArrayList<String>();
		ArrayList<String> ExpectedApiInfoFields = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.objectlayer, "apiInfo").get("fields"));	
		ArrayList<String> locationFields = new ArrayList<String>();	
		ArrayList<String> ExpectedlocationFields = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.objectlayer, "location").get("fields"));
		
		
	    JSONObject responseObj = new JSONObject(response);	
	    
		/*verifying API info here*/
		if(responseObj.has("apiInfo")) {
			JSONObject apiInfo = responseObj.getJSONObject("apiInfo");
			Iterator<String> Keys = apiInfo.keys();
			while (Keys.hasNext()) 
			{
			  String fieldValue = (String)Keys.next();
			  apiInfoFields.add(fieldValue);
			}
			softAssert.assertTrue(CollectionUtils.isEqualCollection(apiInfoFields, ExpectedApiInfoFields),"Fields inside API INFO mismatched. Expected were "+ExpectedApiInfoFields+" Found were "+apiInfoFields+ " for doctype - "+docType);
		}
		else 
		Assert.fail("API info object not found in response body for delete API object layer response for "+docType);
			
		/*verifying document name here*/
		if(responseObj.has("documentName")) {
			String documentName = responseObj.get("documentName").toString();
			softAssert.assertEquals(documentName, docName , "Document name mismatch found in response. Expected was "+docName+" but found was "+documentName);
		}
		else
		softAssert.fail("Document name object not found in response body  for delete API object layer for document name - "+docName+" and document type - "+docType);
		
		/*verifying location Object here*/
		if(responseObj.has("location")) {
		JSONObject location = responseObj.getJSONObject("location");
		Iterator<String> Keys = location.keys();
		while (Keys.hasNext()) 
		{
		  String fieldValue = (String)Keys.next();		  
		  locationFields.add(fieldValue);
		}
		
		softAssert.assertTrue(CollectionUtils.isEqualCollection(locationFields, ExpectedlocationFields),"Fields inside location mismatched for Delete API of object layer. Expected were "+ExpectedApiInfoFields+" Found were "+locationFields);
		String locationDocType = location.get("documentType").toString();
		String locationlocale = location.get("systemLocale").toString();
		softAssert.assertEquals(locationDocType,docType,"Document type in location mismatch. Expected was - "+docType+" found was - "+locationDocType);
		softAssert.assertEquals(locationlocale,"bright","System locale in location mismatch. Expected was - bright found was - "+locationlocale);
		
		}
	   else 
	   softAssert.fail("Location object not found in response body for Delete API call object layer for document name - "+docName+" and document type - "+docType);
		
	   /*verifying size Object here*/
	   if(responseObj.has("size")) {	
			String size = responseObj.get("size").toString();
			if(size.length()== 0)
			softAssert.fail("size object was found empty");
	   }
	   else
	   softAssert.fail("size object not found in response body for Delete API call object layer for document name - "+docName+" and document type - "+docType);
		
	   /*verifying last modified,tags,tag count, version id here*/
	   if(responseObj.has("lastModified") && responseObj.has("tagCount") && responseObj.has("tags") && responseObj.has("versionId") && responseObj.has("versions")) {	
		logger.info("last modified,tags,tag count, version id and versions Object found in Delete API call");    
	   }
	   else
	   softAssert.fail("last modified,tags,tag count, version id not found in response body for Delete API call object layer for document name - "+docName+" and document type - "+docType);
	  	
		softAssert.assertAll();	
	}

	/* A Method to verify success status for delete API  */
	
	public static void verifySuccessForDelete(RequestSpecification httpRequest, String locale, String docType, String docName) {
		Response response= deleteCallForObjectyLayer(httpRequest,locale,docType,docName);
		if(response!=null) {
			Assert.assertEquals(response.getStatusCode(), ResponseCodes.SUCCESS,"Status code mismatch found. Expected code was - "+ResponseCodes.SUCCESS+"Received code was -"+response.getStatusCode());
			logger.info("Verified the Success Status for Delete API of object layer for document type - "+docType);
			//verifying response structure of delete api 
			verifyResponseForDeleteObjectAPI(response.getBody().asString(),docType,docName);		
			String attributeResponse = ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,docType,docName,ResponseCodes.SUCCESS).getBody().asString();
			String updatedByPTYID=new JSONObject(attributeResponse).getJSONObject("content").get("updatedByPtyId").toString();
			JSONObject deletionInfo=new JSONObject(attributeResponse).getJSONObject("content").getJSONObject("lmsObject").getJSONObject("documentDeletionInformation");
			String deletionPtyId=deletionInfo.get("ptyId").toString();
			String deletionTime=deletionInfo.get("deletedTms").toString();
			ArrayList<String> returnField = new ArrayList<String>(Arrays.asList("memberKey"));
			String listOfficeBrokerKey = CommonUtilityMethods.fetchGraphQlResponseUsingMemberMlsID("member",property.getProperty("UserName"),"getMembers",returnField);
			Assert.assertEquals(deletionPtyId, listOfficeBrokerKey,"pty id in doc deletion info does not match with member key");
			Assert.assertNotEquals(deletionTime, null,"deletedTms in doc deletion info is null");
			Assert.assertEquals(updatedByPTYID, listOfficeBrokerKey,"pty id in doc deletion info does not match with member key");
			
		}

		else
		{
			logger.info("Toggle Off so we are not able to do a delete call");
		}


	}
	public static void verifyGoneDoc(RequestSpecification httpRequest, String locale, String docType, String docName) {
		Response response= deleteCallForObjectyLayer(httpRequest,locale,docType,docName);
		if(response!=null) {
			logger.info("Response for get API success of object layer "+response.getBody().asString());
			Assert.assertEquals(response.getStatusCode(), ResponseCodes.GONE,"Status code mismatch found. Expected code was - "+ResponseCodes.SUCCESS+"Received code was -"+response.getStatusCode());
			logger.info("Verified the Success Status for Delete API of object layer for document type - "+docType);

		}
		else
		{
			logger.info("Toggle Off so we are not able to do a delete call");
		}
	}

	public static void verifyWrongDocType(RequestSpecification httpRequest, String locale, String docType,String docName) {

		Response response= verifyWrongDocTypeRequest(httpRequest,locale,docType,docName);
		if(response!=null) {
			logger.info("Response for get API success of object layer "+response.getBody().asString());
			Assert.assertEquals(response.getStatusCode(), ResponseCodes.BAD_REQUEST,"Status code mismatch found. Expected code was - "+ResponseCodes.SUCCESS+"Received code was -"+response.getStatusCode());
			logger.info("Verified the Success Status for Delete API of object layer for wrong doc type document type ");

		}
		else
		{
			logger.info("Toggle Off so we are not able to do a delete call");
		}

	}

	public static void verifyNotExistDoc(RequestSpecification httpRequest, String locale, String docType,String nonExistingDoc) {
		nonExistingDoc = ObjectLayerPage.getDeletedDocForObjectLayer("subdivision");
		Response response= deleteCallForObjectyLayer(httpRequest,locale,docType,nonExistingDoc);
		if(response!=null) {
			logger.info("Response for get API success of object layer "+response.getBody().asString());
			Assert.assertEquals(response.getStatusCode(), ResponseCodes.NO_SUCH_KEY,"Status code mismatch found. Expected code was - "+ResponseCodes.SUCCESS+"Received code was -"+response.getStatusCode());
			logger.info("Verified the Success Status for Delete API of object layer for non existing doc in document type - "+docType);

		}
		else
		{
			logger.info("Toggle Off so we are not able to do a delete call");
		}


	}

	public static void verifyWrongLocale(RequestSpecification httpRequest, String wrongLocale, String docType,
			String docName) {


		Response response= wrongLocaleType(httpRequest,wrongLocale,docType,docName);

		logger.info("Response for get API success of object layer "+response.getBody().asString());
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.BAD_REQUEST,"Status code mismatch found. Expected code was - "+ResponseCodes.SUCCESS+"Received code was -"+response.getStatusCode());
		logger.info("Verified the Success Status for Delete API of object layer for non existing doc in document type - "+wrongLocale);



	}

	public static void verifyForbiddenForDeleteAPIDiscovery(RequestSpecification httpRequest, String locale,
			String docType, String docName) {
		Response response= forbiddenCallForObjectyLayer(httpRequest,docType,docType,docName);

		logger.info("Response for get API success of object layer "+response.getBody().asString());
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.BAD_REQUEST,"Status code mismatch found. Expected code was - "+ResponseCodes.SUCCESS+"Received code was -"+response.getStatusCode());
		logger.info("Verified the Success Status for Delete API of object layer for non existing doc in document type - "+locale);



	}

	public static Response forbiddenCallForObjectyLayer(RequestSpecification httpRequest, String systemLoc, String doctype, String docName) {

		if(CommonUtilityMethods.getFeatureToggle(doctype)){
			RequestSpecification requestSpecification;
			requestSpecification = CommonUtilityMethods.requestSpecForObjectLayer();
			requestSpecification.headers("x-session-key","AUTH-6d85fa23-fcdb-4ffb-b3d7-6948e4eeeedb");  
			requestSpecification.headers("Authorization", "Bearer " + bearerToken);
			httpRequest = RestAssured.given();
			String systemLocale = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"systemLocale").getString(systemLoc);
			String docType = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"doctype").getString(doctype);	
			docName = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"docName").getString("testDoc");	
			requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"objectLayer").getString("getDocument").toString());	
			requestSpecification.pathParam("systemLocale", systemLocale);
			requestSpecification.pathParam("docType", docType);
			requestSpecification.pathParam("docName", docName);
			httpRequest.spec(requestSpecification);
			Response  response = httpRequest.delete();
			return response;
		}

		else
		{
			logger.info("Toggle is Currently OFF for:"+ doctype);
			return null;
		}

	}


	public static Response verifyWrongDocTypeRequest(RequestSpecification httpRequest, String systemLoc, String doctype, String docName) {


		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpecForObjectLayer();
		requestSpecification.headers("x-session-key",sessionKey);  
		requestSpecification.headers("Authorization", "Bearer " + bearerToken);
		httpRequest = RestAssured.given();
		String systemLocale = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"systemLocale").getString(systemLoc);
		String docType = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"doctype").getString(doctype);	
		docName = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"docName").getString("testDoc");	
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"objectLayer").getString("getDocument").toString());	
		requestSpecification.pathParam("systemLocale", systemLocale);
		requestSpecification.pathParam("docType", "wrong");
		requestSpecification.pathParam("docName", docName);
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.delete();
		logger.info(response.asString());
		return response;


	}

	public static Response wrongLocaleType(RequestSpecification httpRequest, String systemLoc, String doctype, String docName) {


		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpecForObjectLayer();
		requestSpecification.headers("x-session-key",sessionKey);  
		requestSpecification.headers("Authorization", "Bearer " + bearerToken);
		httpRequest = RestAssured.given();
		String systemLocale = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"systemLocale").getString(systemLoc);
		String docType = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"doctype").getString(doctype);	
		 docName = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"docName").getString("testDoc");	
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"objectLayer").getString("getDocument").toString());	
		requestSpecification.pathParam("systemLocale", "wrong");
		requestSpecification.pathParam("docType", docType);
		requestSpecification.pathParam("docName", docName);
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.delete();
		logger.info(response.asString());
		return response;


	}



}
