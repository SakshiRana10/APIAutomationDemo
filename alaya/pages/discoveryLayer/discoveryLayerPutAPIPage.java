package bright.api.alaya.pages.discoveryLayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.commons.collections4.CollectionUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import org.testng.util.Strings;

import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class discoveryLayerPutAPIPage extends MainClassAlaya {

	

	public static String getRandomDocName(String docType) {
		ArrayList<String> docNames = CommonUtilityMethods.docListMap.get(docType);
		String docName = docNames.get(CommonUtilityMethods.pickRandomFromList(docNames));
		return docName;
	}
	
	/* A method to verify response structure of put call of discovery layer */
	
	public static void verifyPutDiscoveryResponseStructure(Response response, String docType, String docName) {
        SoftAssert softAssert = new SoftAssert();
		
		ArrayList<String> apiInfoFields = new ArrayList<String>();
		ArrayList<String> ExpectedApiInfoFields = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath, "getResponseAPIInfo").get("fields"));	
		ArrayList<String> locationFields = new ArrayList<String>();	
		ArrayList<String> ExpectedlocationFields = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath, "location").get("fields"));
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
			softAssert.assertTrue(CollectionUtils.isEqualCollection(apiInfoFields, ExpectedApiInfoFields),"Fields inside API INFO mismatched for PUT API of discovery layer. Expected were "+ExpectedApiInfoFields+" Found were "+apiInfoFields+" for doctype/docName - "+docType+"/"+docName);
		}
		else 
		softAssert.fail("API info object not found in response body for PUT call discovery layer for document name - "+docName+" and document type - "+docType);
		
		/*verifying document name here*/
		if(responseObj.has("documentName")) {
			String documntName = responseObj.get("documentName").toString();
			softAssert.assertEquals(docName, documntName , "Document name mismatch found in response for put call of discovery layer. Expected was "+docName+" but found was "+documntName+" for doctype/docName - "+docType+"/"+docName);
		}
		else
		softAssert.fail("Document name object not found in response body for PUT API discovery layer for document name - "+docName+" and document type - "+docType);
		
		/*verifying location Object here*/
		if(responseObj.has("location")) {
		JSONObject location = responseObj.getJSONObject("location");
		Iterator<String> Keys = location.keys();
		while (Keys.hasNext()) 
		{
		  String fieldValue = (String)Keys.next();		  
		  locationFields.add(fieldValue);
		}
		
		softAssert.assertTrue(CollectionUtils.isEqualCollection(locationFields, ExpectedlocationFields),"Fields inside location mismatched for PUT API of discovery layer. Expected were "+ExpectedApiInfoFields+" Found were "+locationFields+ " for doctype/docName - "+docType+"/"+docName);
		String locationDocType = location.get("documentType").toString();
		String locationlocale = location.get("systemLocale").toString();
		softAssert.assertEquals(locationDocType,docType,"Document type in location mismatch for put discovery call. Expected was - "+docType+" found was - "+locationDocType+" for doctype/docName - "+docType+"/"+docName);
		softAssert.assertEquals(locationlocale,"bright","System locale in location mismatch for put discovery call. Expected was - bright found was - "+locationlocale+" for doctype/docName - "+docType+"/"+docName);
		
		}
	   else 
	   softAssert.fail("Location object not found in response body for PUT call discovery layer for document name - "+docName+" and document type - "+docType);
		
	   /*verifying size Object here*/
	   if(responseObj.has("size")) {	
			String size = responseObj.get("size").toString();
			if(size.length()== 0)
			softAssert.fail("size object was found empty");
	   }
	   else
	   softAssert.fail("size object not found in response body for PUT call discovery layer for document name - "+docName+" and document type - "+docType);
		
	   
	   /*verifying last modified,tags,tag count, version id and versions Object */   
	   if(responseObj.has("lastModified") && responseObj.has("tagCount") && responseObj.has("tags") && responseObj.has("versionId") && responseObj.has("versions")) {	
		logger.info("last modified,tags,tag count, version id and versions Object found in PUT API discovery call");
		JSONObject versions = responseObj.getJSONObject("versions");
		JSONObject versionHistory = (JSONObject) versions.getJSONArray("versionHistory").get(0);
		String versionIdInside = versionHistory.get("versionId").toString();
		String versionIdOutside = responseObj.get("versionId").toString();
	    softAssert.assertEquals(versionIdInside, versionIdOutside,"Versions ids from response and version history did not match for put discovery call "+" for doctype/docName - "+docType+"/"+docName);
	    softAssert.assertEquals(versionHistory.get("isFirst"), false,"Version created via Put call is the first version for put discovery call "+" for doctype/docName - "+docType+"/"+docName);
	    softAssert.assertEquals(versionHistory.get("isLatest"), true,"Version created via Put call is not the latest version for put discovery call "+" for doctype/docName - "+docType+"/"+docName);	    
	   }
	   else
	   softAssert.fail("last modified,tags,tag count, version id and versions Object not found in response body for PUT call discovery layer for document name - "+docName+" and document type - "+docType);
	  	
	   softAssert.assertAll();	
	}
	
	/**
	 * A method to verify 200 status code when get api is successfully sent
	 */

	public static void verifySuccessStatusforPutApi(String docType){
		RequestSpecification httpRequest= null;
		String docName =  getRandomDocName(docType);
		if(Strings.isNullOrEmpty(docName)) {
			Assert.fail("No valid document found");
		}
		Response payloadResponse = discoveryLayerUtilityPage.getCallForDiscoveryLayer(httpRequest,"Bright","true","true",docType,docName);
		JSONObject contentObj = new JSONObject( payloadResponse.getBody().asString()).getJSONObject("content");
		JSONObject responseBodyObj = new JSONObject();
		responseBodyObj.put("lastUpdatedBy", "Core Alaya Automation for discovery layer  for discovery layer");
		responseBodyObj.put("content", contentObj);
		String payload = responseBodyObj.toString();
		String lastModified = new JSONObject(payloadResponse.getBody().asString()).getJSONObject("metadata").getString("lastModified");
		Response  response = discoveryLayerUtilityPage.putCallForDiscoveryLayer(httpRequest,"true","true","true","Bright",docType,docName,lastModified,payload);
		
		//verifying response structure of put call of discovery layer
		verifyPutDiscoveryResponseStructure(response, docType, docName);
		
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.SUCCESS);
		logger.info("Verified the Success Status for put api in doc type - "+docType);
		String VersionIdPut=discoveryLayerUtilityPage.getVersionIdFromPutAPI(response.asString());
		ArrayList<String> VersionHistory=discoveryLayerUtilityPage.getlatestVersionIDs(response.asString());
		String VersionIdGet=discoveryLayerUtilityPage.getVersionIdFromGetAPI(payloadResponse.asString());
		Boolean isMatched=discoveryLayerUtilityPage.versionIdHistoryMatch(VersionHistory,VersionIdPut,VersionIdGet);
		Assert.assertTrue(isMatched, "New Version from version history Matched succesfully");
		logger.info("Verified the Success Status for put api in doc type - "+docType);
		
		String lastmodifiedFromPut=discoveryLayerUtilityPage.getLastModified(response.asString());
		ArrayList<String> dsLastModifiedTime = new ArrayList<String>(Arrays.asList("dsLastModifiedTime"));
		logger.info("Waiting for document to update on Elastic Search");
		String key = CommonUtilityMethods.documentKeys.get(docType);
		String method = CommonUtilityMethods.graphQlMethods.get(docType);
	   	
		String ESLastModified =  CommonUtilityMethods.fetchGraphQlResponseForSingleField(docType, docName,method,key,dsLastModifiedTime);  
//		if(!CommonUtilityMethods.convertUTC(ESLastModified).after(CommonUtilityMethods.convertUTC(lastmodifiedFromPut)) || ESLastModified.equals(lastmodifiedFromPut) ){
//			
//			Assert.fail("ModificationTime stamps did not match with graphql response or last modified is greater than ES");
//		}
//		logger.info("Verified the  Index builder for doc type - "+docType);
	}
	
	/**
	 * A method to verify 400 error if includeVersions is false
	 */

	public static void verifyResponseIfIncludeVersionsIsFalse(String docType) {
		RequestSpecification httpRequest= null;
		String docName =  getRandomDocName(docType);
		if(Strings.isNullOrEmpty(docName)) {
			Assert.fail("No valid document found");
		}
		Response payloadResponse = discoveryLayerUtilityPage.getCallForDiscoveryLayer(httpRequest,"Bright","true","true",docType,docName);
		JSONObject contentObj = new JSONObject( payloadResponse.getBody().asString()).getJSONObject("content");
		JSONObject responseBodyObj = new JSONObject();
		responseBodyObj.put("lastUpdatedBy", "Core Alaya Automation for discovery layer ");
		responseBodyObj.put("content", contentObj);
		String payload = responseBodyObj.toString();
		String lastModified = new JSONObject(payloadResponse.getBody().asString()).getJSONObject("metadata").getString("lastModified");
		Response  response = discoveryLayerUtilityPage.putCallForDiscoveryLayer(httpRequest,"true","false","true","Bright",docType,docName,lastModified,payload);
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.BAD_REQUEST);
		logger.info("Verified the 400 Bad Request Status when include versions is false in doc type - "+docType);
	}
//	
//	/**
//	 * A method to verify 400 error if includeTags is false
//	 */
//
//	public static void verifyResponseIfIncludeTagsIsFalse() {
//		
//		String docName = listingDocumentNames.get(CommonUtilityMethods.pickRandomFromList(listingDocumentNames)2);
//		Response payloadResponse = CommonUtilityMethods.getCallForDiscoveryLayer(httpRequest,"Bright","true","true",docType,docName);
//		JSONObject contentObj = new JSONObject( payloadResponse.getBody().asString()).getJSONObject("content");
//		JSONObject responseBodyObj = new JSONObject();
//		responseBodyObj.put("lastUpdatedBy", "Core Alaya Automation for discovery layer ");
//		responseBodyObj.put("content", contentObj);
//		String payload = responseBodyObj.toString();
//		String lastModified = new JSONObject(payloadResponse.getBody().asString()).getJSONObject("metadata").getString("lastModified");
//		Response  response = CommonUtilityMethods.putCallForDiscoveryLayer("true","true","false","true","Bright",docType,docName,lastModified,payload);
//		Assert.assertEquals(response.getStatusCode(), ResponseCodes.BAD_REQUEST);
//		logger.info("Verified the 400 Bad Request Status when include tags is false in Listing");
//	}
	
	/**
	 * A method to verify 400 error if includePretty is false
	 */

	public static void verifyResponseIfIncludePrettyIsFalse(String docType) {
		RequestSpecification httpRequest= null;
		String docName =  getRandomDocName(docType);
		if(Strings.isNullOrEmpty(docName)) {
			Assert.fail("No valid document found");
		}
		Response payloadResponse = discoveryLayerUtilityPage.getCallForDiscoveryLayer(httpRequest,"Bright","true","true",docType,docName);
		JSONObject contentObj = new JSONObject( payloadResponse.getBody().asString()).getJSONObject("content");
		JSONObject responseBodyObj = new JSONObject();
		responseBodyObj.put("lastUpdatedBy", "Core Alaya Automation for discovery layer ");
		responseBodyObj.put("content", contentObj);
		String payload = responseBodyObj.toString();
		String lastModified = new JSONObject(payloadResponse.getBody().asString()).getJSONObject("metadata").getString("lastModified");
		Response  response = discoveryLayerUtilityPage.putCallForDiscoveryLayer(httpRequest,"true","true","false","Bright",docType,docName,lastModified,payload);
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.BAD_REQUEST);
		logger.info("Verified the 400 Bad Request Status when pretty is false in doc type - "+docType);
	}
	
	/**
	 * A method to verify 400 error if LastModified Is Wrong format
	 */

	public static void verifyResponseIfLastModifiedIsWrongFormat(String docType) {
		RequestSpecification httpRequest= null;
		String docName =  getRandomDocName(docType);
		if(Strings.isNullOrEmpty(docName)) {
			Assert.fail("No valid document found");
		}
		Response payloadResponse = discoveryLayerUtilityPage.getCallForDiscoveryLayer(httpRequest,"Bright","true","true",docType,docName);
		JSONObject contentObj = new JSONObject( payloadResponse.getBody().asString()).getJSONObject("content");
		JSONObject responseBodyObj = new JSONObject();
		responseBodyObj.put("lastUpdatedBy", "Core Alaya Automation for discovery layer ");
		responseBodyObj.put("content", contentObj);
		String payload = responseBodyObj.toString();
		Response response = discoveryLayerUtilityPage.putCallForDiscoveryLayer(httpRequest,"true","true","true","Bright",docType,docName,"wrongLastModified",payload);    //wrong formatted Last modified given
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.BAD_REQUEST);
		logger.info("Verified the 400 Bad Request Status when Last modified is wrong format in doc type - "+docType);
    }
	
	/**
	 * A method to verify 462 error if LastModified Is Wrong value
	 */

	public static void verifyResponseIfLastModifiedIsWrongValue(String docType) {
		RequestSpecification httpRequest= null;
		String docName =  getRandomDocName(docType);
		if(Strings.isNullOrEmpty(docName)) {
			Assert.fail("No valid document found");
		}
		Response payloadResponse = discoveryLayerUtilityPage.getCallForDiscoveryLayer(httpRequest,"Bright","true","true",docType,docName);
		JSONObject contentObj = new JSONObject( payloadResponse.getBody().asString()).getJSONObject("content");
		JSONObject responseBodyObj = new JSONObject();
		responseBodyObj.put("lastUpdatedBy", "Core Alaya Automation for discovery layer ");
		responseBodyObj.put("content", contentObj);
		String payload = responseBodyObj.toString();
		Response  response = discoveryLayerUtilityPage.putCallForDiscoveryLayer(httpRequest,"true","true","true","Bright",docType,docName,"2022-10-27T06:10:39.000Z",payload);    //wrong Last modified given
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.INVALID_LASTMODIFIED);
		logger.info("Verified the 462 Status when Last modified is wrong value in doc type - "+docType);
    }
	
	/**
	 * A method to verify versions are not displayed if IncludeVersions is unchecked
	 */

	public static void verifyResponseForUncheckedIncludeVersions(String docType) {
		RequestSpecification httpRequest= null;
		String docName =  getRandomDocName(docType);
		if(Strings.isNullOrEmpty(docName)) {
			Assert.fail("No valid document found");
		}
		Response payloadResponse = discoveryLayerUtilityPage.getCallForDiscoveryLayer(httpRequest,"Bright","true","true",docType,docName);
		JSONObject contentObj = new JSONObject( payloadResponse.getBody().asString()).getJSONObject("content");
		JSONObject responseBodyObj = new JSONObject();
		responseBodyObj.put("lastUpdatedBy", "Core Alaya Automation for discovery layer ");
		responseBodyObj.put("content", contentObj);
		String payload = responseBodyObj.toString();
		String lastModified = new JSONObject(payloadResponse.getBody().asString()).getJSONObject("metadata").getString("lastModified");
		Response  response = discoveryLayerUtilityPage.putCallForDiscoveryLayerIncludeVersion(httpRequest,"true","true","Bright",docType,docName,lastModified,payload);
		String body = response.getBody().asString();
		int validate = 1;           // 1 for true and 0 for false
		if(body.contains("versionHistory")) {
			validate = 0;
		}
		Assert.assertEquals(1, validate);
		logger.info("Verified versions are not present for unchecked include versions for doc type - "+docType);
	}
	
	/**
	 * A method to verify 400 error if lastmodified is unchecked
	 */

	public static void verifyResponseForUncheckedLastModified(String docType) {
		RequestSpecification httpRequest= null;
		String docName =  getRandomDocName(docType);
		if(Strings.isNullOrEmpty(docName)) {
			Assert.fail("No valid document found");
		}
		Response payloadResponse = discoveryLayerUtilityPage.getCallForDiscoveryLayer(httpRequest,"Bright","true","true",docType,docName);
		JSONObject contentObj = new JSONObject( payloadResponse.getBody().asString()).getJSONObject("content");
		JSONObject responseBodyObj = new JSONObject();
		responseBodyObj.put("lastUpdatedBy", "Core Alaya Automation for discovery layer ");
		responseBodyObj.put("content", contentObj);
		String payload = responseBodyObj.toString();
		Response  response = discoveryLayerUtilityPage.putCallForDiscoveryLayerLastModified(httpRequest,"true","true","true","Bright",docType,docName,payload);
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.BAD_REQUEST);
		logger.info("Verified the 400 error when Last modified is unchecked in doc type - "+docType);
	}
	
	/**
	 * A method to verify 404 error if wrong system locale is given
	 */

	public static void verifyResponseForWrongSystemLocale(String docType) {
		RequestSpecification httpRequest= null;
		String docName =  getRandomDocName(docType);
		if(Strings.isNullOrEmpty(docName)) {
			Assert.fail("No valid document found");
		}
		Response payloadResponse = discoveryLayerUtilityPage.getCallForDiscoveryLayer(httpRequest,"Bright","true","true",docType,docName);
		JSONObject contentObj = new JSONObject( payloadResponse.getBody().asString()).getJSONObject("content");
		JSONObject responseBodyObj = new JSONObject();
		responseBodyObj.put("lastUpdatedBy", "Core Alaya Automation for discovery layer ");
		responseBodyObj.put("content", contentObj);
		String payload = responseBodyObj.toString();
		String lastModified = new JSONObject(payloadResponse.getBody().asString()).getJSONObject("metadata").getString("lastModified");
		Response  response = discoveryLayerUtilityPage.putCallForDiscoveryLayer(httpRequest,"true","true","true","wrong",docType,docName,lastModified,payload);
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.NOT_FOUND);
		logger.info("Verified the 404 unknown Status for wrong system locale in doc type - "+docType);
	}
	
	/**
	 * A method to verify 404 error if wrong document type is given
	 */

	public static void verifyResponseForWrongDocType(String docType) {
		RequestSpecification httpRequest= null;
		String docName =  getRandomDocName(docType);
		if(Strings.isNullOrEmpty(docName)) {
			Assert.fail("No valid document found");
		}
		Response payloadResponse = discoveryLayerUtilityPage.getCallForDiscoveryLayer(httpRequest,"Bright","true","true",docType,docName);
		JSONObject contentObj = new JSONObject( payloadResponse.getBody().asString()).getJSONObject("content");
		JSONObject responseBodyObj = new JSONObject();
		responseBodyObj.put("lastUpdatedBy", "Core Alaya Automation for discovery layer ");
		responseBodyObj.put("content", contentObj);
		String payload = responseBodyObj.toString();
		String lastModified = new JSONObject(payloadResponse.getBody().asString()).getJSONObject("metadata").getString("lastModified");
		Response  response = discoveryLayerUtilityPage.putCallForDiscoveryLayer(httpRequest,"true","true","true","Bright","wrong",docName,lastModified,payload);
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.NOT_FOUND);
		logger.info("Verified the 404 not found Status for wrong document type in doc type - "+docType);
	}
	
	/**
	 * A method to verify 500 error if wrong documentName is given
	 */

	public static void verifyResponseForWrongDocument(String docType) {
		RequestSpecification httpRequest= null;
		String docName =  getRandomDocName(docType);
		if(Strings.isNullOrEmpty(docName)) {
			Assert.fail("No valid document found");
		}
		Response payloadResponse = discoveryLayerUtilityPage.getCallForDiscoveryLayer(httpRequest,"Bright","true","true",docType,docName);
		JSONObject contentObj = new JSONObject( payloadResponse.getBody().asString()).getJSONObject("content");
		JSONObject responseBodyObj = new JSONObject();
		responseBodyObj.put("lastUpdatedBy", "Core Alaya Automation for discovery layer ");
		responseBodyObj.put("content", contentObj);
		String payload = responseBodyObj.toString();
		String lastModified = new JSONObject(payloadResponse.getBody().asString()).getJSONObject("metadata").getString("lastModified");
		String wrongDoc = discoveryLayerUtilityPage.getWrongDocForDiscoveryLayer();
		Response  response = discoveryLayerUtilityPage.putCallForDiscoveryLayer(httpRequest,"true","true","true","Bright",docType,wrongDoc,lastModified,payload);
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.NO_KEY);
		logger.info("Verified the 500 No such key Status for wrong document name in doc type - "+docType);
	}
	
	/**
	 * A method to verify the Forbidden error for Post call of GetAPI for discovery Layer
	 */
	
	public static void verifyWrongAPIKeyForPUT(String docType) {
		RequestSpecification httpRequest= null;
		String docName =  getRandomDocName(docType);
		if(Strings.isNullOrEmpty(docName)) {
			Assert.fail("No valid document found");
		}
		Response payloadResponse = discoveryLayerUtilityPage.getCallForDiscoveryLayer(httpRequest,"Bright","true","true",docType,docName);
		JSONObject contentObj = new JSONObject( payloadResponse.getBody().asString()).getJSONObject("content");
		JSONObject responseBodyObj = new JSONObject();
		responseBodyObj.put("lastUpdatedBy", "Core Alaya Automation for discovery layer ");
		responseBodyObj.put("content", contentObj);
		String payload = responseBodyObj.toString();
		String lastModified = new JSONObject(payloadResponse.getBody().asString()).getJSONObject("metadata").getString("lastModified");
		Response  response = discoveryLayerUtilityPage.verifyForbiddenForPutAPIDiscovery(httpRequest,"true","true","true","Bright",docType,docName,lastModified,payload);
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.FORBIDDEN);
		logger.info("Verified the forbidden Request for put api in doc type - "+docType);
	}
}
