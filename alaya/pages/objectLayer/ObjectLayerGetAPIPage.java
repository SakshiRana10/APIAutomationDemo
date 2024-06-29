package bright.api.alaya.pages.objectLayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.collections4.CollectionUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.util.Strings;

import bright.api.alaya.pages.discoveryLayer.discoveryLayerUtilityPage;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ObjectLayerGetAPIPage extends MainClassAlaya {
	
	private static final String Locale = "Bright" ;
    
	public static String getRandomDocName(String docType) {
		String docName = null;
		ArrayList<String> docNames = CommonUtilityMethods.docListMap.get(docType);
		docName = docNames.get(CommonUtilityMethods.pickRandomFromList(docNames));
		return docName;
	}
		
	/**
	 * A method to verify 200 status code when get API is successfully sent
	 */

	public static void verifySuccessStatusforGETAPIObjectLayer(RequestSpecification httpRequest,String docType) {
		
		ArrayList<String> contentFields = new ArrayList<String>();
		ArrayList<String> ExpectedContentFields = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.objectlayer, "getResponseContent").get("fields"));
		ArrayList<String> apiInfoFields = new ArrayList<String>();
		ArrayList<String> ExpectedApiInfoFields = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.objectlayer, "getResponseAPIInfo").get("fields"));	
		ArrayList<String> metadataFields = new ArrayList<String>();
		ArrayList<String> ExpectedMetadataFields = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.objectlayer, "getResponseMetadata").get("fields"));	
		
		
		String docName = getRandomDocName(docType);
		if(Strings.isNullOrEmpty(docName)) {
			Assert.fail("No valid document found for document type - "+docType);
		}
		Response  response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,Locale,docType,docName,ResponseCodes.SUCCESS);
		
		logger.info("Response for get API success of object layer "+response.getBody().asString());
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.SUCCESS,"Status code mismatch found. Expected code was - "+ResponseCodes.SUCCESS+"Received code was -"+response.getStatusCode());
		logger.info("Verified the Success Status for GET API of object layer for document type - "+docType);
		
		JSONObject responseObj = new JSONObject(response.getBody().asString());	
		
		/*verifying API info here*/
		if(responseObj.has("apiInfo")) {
			JSONObject apiInfo = responseObj.getJSONObject("apiInfo");
			Iterator<String> Keys = apiInfo.keys();
			while (Keys.hasNext()) 
			{
			  String fieldValue = (String)Keys.next();
			  apiInfoFields.add(fieldValue);
			}
			Assert.assertTrue(CollectionUtils.isEqualCollection(apiInfoFields, ExpectedApiInfoFields),"Fields inside API INFO mismatched. Expected were "+ExpectedApiInfoFields+" Found were "+apiInfoFields+ " for doctype - "+docType);
		}
		else 
		Assert.fail("API info object not found in response body for get object layer response for "+docType);
		
		
		
		
		/*verifying Meta data info here*/
		
		if(responseObj.has("metadata")) {
			JSONObject metaData = responseObj.getJSONObject("metadata");
			Iterator<String> Keys = metaData.keys();
			while (Keys.hasNext()) 
			{
			  String fieldValue = (String)Keys.next();
			  metadataFields.add(fieldValue);
			}
			Assert.assertTrue(CollectionUtils.isEqualCollection(metadataFields, ExpectedMetadataFields),"Fields inside metadata mismatched. Expected were "+ExpectedMetadataFields+" Found were "+metadataFields+ " for doctype - "+docType);
		}
		else 
		Assert.fail("metadata object not found in response body for get object layer response for "+docType);
		
		
		/*verifying content info here*/
		if(responseObj.has("content")) {
			JSONObject content = responseObj.getJSONObject("content");
			Iterator<String> Keys = content.keys();
			while (Keys.hasNext()) 
			{
			  String fieldValue = (String)Keys.next();
			  if(!fieldValue.equalsIgnoreCase("updatedByPtyId"))
			  Assert.assertTrue(ExpectedContentFields.contains(fieldValue),"Fields inside content mismatched. Not found Expected field was "+fieldValue+" for doctype - "+docType+" for docname: "+docName);
			}
		}
		else 
		Assert.fail("content object not found in response body for get object layer response for "+docType);
	}


	/**
	 * A method to verify 404 error if wrong system locale is given
	 */

	public static void verifyResponseForWrongSystemLocale(RequestSpecification httpRequest,String docType) {
		String docName = getRandomDocName(docType);
		if(Strings.isNullOrEmpty(docName)) {
			Assert.fail("No valid document found for document type - "+docType);
		}
		Response  response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,"wrong",docType,docName,ResponseCodes.NOT_FOUND);
		logger.info("Response for get API wrong system locale of object layer "+response.getBody().asString());
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.NOT_FOUND,"Status code mismatch found. Expected code was - "+ResponseCodes.NOT_FOUND+"Received code was -"+response.getStatusCode());
		logger.info("Verified the 404 unknown Status for wrong system locale in GET API of object layer for document type - "+docType);
	}

	/**
	 * A method to verify 404 error if wrong document Type is given
	 */

	public static void verifyResponseForWrongDocType(RequestSpecification httpRequest,String docType) {
		String docName = getRandomDocName(docType);
		if(Strings.isNullOrEmpty(docName)) {
			Assert.fail("No valid document found for document type - "+docType);
		}
		Response  response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,Locale,"wrong",docName,ResponseCodes.NOT_FOUND);
		logger.info("Response for get API wrong document type of object layer "+response.getBody().asString());
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.NOT_FOUND,"Status code mismatch found. Expected code was - "+ResponseCodes.NOT_FOUND+"Received code was -"+response.getStatusCode());
		logger.info("Verified the 404 not found Status for wrong document type in GET API of object layer for document type - "+docType);
	}

	/**
	 * A method to verify 463 error if wrong documentName is given
	 */

	public static void verifyResponseForWrongDocument(RequestSpecification httpRequest,String docType) {
		String wrongDoc = ObjectLayerPage.getWrongDocForObjectLayer();
		Response  response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,Locale,docType,wrongDoc,ResponseCodes.NO_SUCH_KEY);
		logger.info("Response for get API wrong document of object layer "+response.getBody().asString());
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.NO_SUCH_KEY,"Status code mismatch found. Expected code was - "+ResponseCodes.NO_SUCH_KEY+"Received code was -"+response.getStatusCode());
		logger.info("Verified the 463 Not found Status for wrong document name in GET API of object layer for document type - "+docType);
	}

	/**
	 * A method to verify 410 gone error if deleted document is given
	 */

	public static void verifyResponseForDeletedDocument(RequestSpecification httpRequest,String docType) {
		String deletedDoc = ObjectLayerPage.getDeletedDocForObjectLayer(docType);
		Response  response = ObjectLayerPage.getCallForObjectyLayer(httpRequest,Locale,docType,deletedDoc,ResponseCodes.GONE);
		logger.info("Response for get API deleted document of object layer "+response.getBody().asString());
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.GONE,"Status code mismatch found. Expected code was - "+ResponseCodes.GONE+"Received code was -"+response.getStatusCode());
		logger.info("Verified the 410 Gone Status for deleted document in GET API of object layer for document type - "+docType);
	}

	/**
	 * A method to verify the Forbidden error of GetAPI for Object Layer
	 */

	public static void verifyWrongSessionKeyForGETAPIObjectLayer(RequestSpecification httpRequest,String docType) {
		String docName = getRandomDocName(docType);
		if(Strings.isNullOrEmpty(docName)) {
			Assert.fail("No valid document found for document type - "+docType);
		}
		String  response = ObjectLayerPage.verifyForbiddenInvalidSessionForGetAPIObject(httpRequest,Locale,docType,docName);
		logger.info("Response for get API wrong session key of object layer "+response);
		String resultant = new JSONObject( response).get("message").toString();
		Assert.assertTrue(resultant.toString().contains("Session ID provided is for a session that does not exist"),"Session ID was correct for invalid session ID test case for document Type - "+docType);
		logger.info("Verified the forbidden Request in GET API of object layer for document type - "+docType);
	}

	/**
	 * A method to verify the Forbidden error of GetAPI for Object Layer
	 */

	public static void verifyWrongBearerTokenForGETAPIObjectLayer(RequestSpecification httpRequest,String docType) {
		String docName = getRandomDocName(docType);
		if(Strings.isNullOrEmpty(docName)) {
			Assert.fail("No valid document found for document type - "+docType);
		}
		String  response = ObjectLayerPage.verifyForbiddenInvalidBearerTokenForGetAPIObject(httpRequest,Locale,docType,docName);
		logger.info("Response for get API wrong bearer token of object layer "+response);
		String resultant = new JSONObject( response).get("message").toString();
		Assert.assertTrue(resultant.toString().contains("invalid token"),"Token was correct for invalid token test case for document Type - "+docType);
		logger.info("Verified the forbidden Request in GET API of object layer for document type - "+docType);
	}
	
	
	/**
	 * A method to verify attributes from doc store are matching elastic search
	 */
	
	public static void verifyAttributesForObjectLayer(RequestSpecification httpRequest, String docType,ArrayList<String> AttributeNamesForDocStore, ArrayList<String> AttributeNamesForES) {	 
		JSONObject attributeResponseBody = new JSONObject();
		String docName = getRandomDocName(docType);	  
		if(Strings.isNullOrEmpty(docName)) {
			Assert.fail("No valid document found for document type - "+docType);
		}
	    String attributeResponse = ObjectLayerPage.getCallForObjectyLayer(httpRequest,Locale,docType,docName,ResponseCodes.SUCCESS).getBody().asString();
		logger.info("Response for get API verify attributes of object layer "+attributeResponse);
	    if(docType.equalsIgnoreCase("taxrecord") || docType.equalsIgnoreCase("countyrate"))
		{attributeResponseBody = new JSONObject(attributeResponse).getJSONObject("content").getJSONObject("items");}
		else
		{attributeResponseBody = new JSONObject(attributeResponse).getJSONObject("content").getJSONObject("lmsObject");}
	    JSONObject attributeOBJforTime = new JSONObject(attributeResponse).getJSONObject("metadata");
	    ArrayList<String> AttributesFromDocStore = new ArrayList<String>();
	    for(int i = 0; i<AttributeNamesForDocStore.size(); i++) {
	    	String attribute = AttributeNamesForDocStore.get(i);
			String attributeToAdd = null ;
		    if(attributeResponseBody.has(attribute)) {
		    	attributeToAdd = attributeResponseBody.get(AttributeNamesForDocStore.get(i)).toString();
		    }
		    else {
		    	attributeToAdd = attributeOBJforTime.get(AttributeNamesForDocStore.get(i)).toString();
		    }
		    AttributesFromDocStore.add(attributeToAdd);	    	
	    }	
	    String key = CommonUtilityMethods.documentKeys.get(docType);
		String method = CommonUtilityMethods.graphQlMethods.get(docType);
	    ArrayList<String> AttributesFromES = CommonUtilityMethods.fetchGraphQlResponseByDocName(docType,docName,method, key, AttributeNamesForES);	    
	    Collections.sort(AttributesFromDocStore);
		Collections.sort(AttributesFromES);
	    Assert.assertEquals(AttributesFromDocStore.equals(AttributesFromES), true,"Doc store attributes did not match with ES attributes for document type - "+docType);
	    logger.info("Verified attributes from doc store are matching elastic search in GET API of object layer for document type - "+docType);
	    }



}