package bright.api.alaya.pages.discoveryLayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import org.apache.commons.collections4.CollectionUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import bright.api.alaya.pages.objectLayer.ObjectLayerPage;
import bright.api.alaya.pages.objectLayer.ObjectLayerPostAPIPage;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class discoveryLayerPostAPIPage extends MainClassAlaya {

	private static ArrayList<Integer> PostCodes = new ArrayList<Integer>(
			Arrays.asList(ResponseCodes.SUCCESS, ResponseCodes.SUCCESS_ACCEPTED));

	/*
	 * A method to call Get for getting ID to be used in post call of discovery
	 * layer
	 */

	public static String getIdGenerator(RequestSpecification httpRequest) {
		SoftAssert softAssert = new SoftAssert();
		String documentName = null;
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.utilityServicesRequestSpec();
		httpRequest = RestAssured.given();
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"), "utilityLayer").getString("IdGenerator").toString());
		requestSpecification.queryParam("seqName", "UNIVERSAL_IDENTIFIERS");
		httpRequest.spec(requestSpecification);
		Response response = httpRequest.get();
		String responseBody = response.getBody().asString();
		JSONObject resBodyObj = new JSONObject(responseBody).getJSONObject("content");
		documentName = resBodyObj.get("id").toString();
		softAssert.assertFalse(documentName.isEmpty(), "Document name was not retireved from id generator api");
		softAssert.assertAll();
		return documentName;
	}

	/* A method to call Delete API of document store */

	public static void deleteApiDocStore(RequestSpecification httpRequest, String docType, String docName) {
		SoftAssert softAssert = new SoftAssert();
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpec();
		httpRequest = RestAssured.given();
		requestSpecification.basePath(
				CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"), "documentStore")
						.getString("deleteDocument").toString());
		requestSpecification.pathParam("docType", docType);
		requestSpecification.pathParam("docName", docName);
		httpRequest.spec(requestSpecification);
		Response response = httpRequest.delete();
		softAssert.assertEquals(ResponseCodes.SUCCESS, response.getStatusCode(),
				"Success code was not received for DELETE api of discovery for doc type - " + docType
						+ " Code receieved was - " + response.getStatusCode());
		softAssert.assertAll();
	}

	/*
	 * A method to Add document deletion information and call put API to get the
	 * document deleted from ES
	 */

	public static void addDocumentDeletionInfo(RequestSpecification httpRequest, String docType, String documentName,
			Response responseFromPost,JSONObject payloadObject) {
		JSONObject lmsObj= new JSONObject();
		SoftAssert softAssert = new SoftAssert();
		String payload = payloadObject.toString();
		JSONObject payloadOBJ = new JSONObject(payload);
		JSONObject contentObj = new JSONObject(payload).getJSONObject("content");
		if(docType.equalsIgnoreCase("taxrecord") || docType.equalsIgnoreCase("countyrate"))
		{ lmsObj = contentObj.getJSONObject("items"); }
		else
		{ lmsObj = contentObj.getJSONObject("lmsObject"); }

		JSONObject docDeletionInfo = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath, "documentDeletionInfo");
		lmsObj.put("documentDeletionInformation", docDeletionInfo);
		if(docType.equalsIgnoreCase("taxrecord") || docType.equalsIgnoreCase("countyrate"))
		{ contentObj.put("items", lmsObj); }
		else 
		{ contentObj.put("lmsObject", lmsObj); }
		payloadOBJ.putOpt("content", contentObj);
		String payloadForPut = payloadOBJ.toString();
		JSONObject responseFromPostOBJ = new JSONObject(responseFromPost.getBody().asString());
		String lastModified = responseFromPostOBJ.get("lastModified").toString();
		Response responseFromPut = discoveryLayerUtilityPage.putCallForDiscoveryLayer(httpRequest, "true", "true", "true","Bright", docType, documentName, lastModified, payloadForPut);
		logger.info("Response from post - "+responseFromPut.getBody().asString());
		softAssert.assertEquals(PostCodes.contains(responseFromPut.getStatusCode()), true,"Success code was not received for PUT api of discovery for doc type - " + docType + " Code received was - " + responseFromPut.getStatusCode());
		softAssert.assertAll();

	}
	
	/* A Method to create payload for post call for discovery layer */
	
	public static JSONObject createPayloadForPost(String documentName, String docType) {
		long docName = Long.parseLong(documentName);
		RequestSpecification httpRequest= null;
		JSONObject lmsObj= new JSONObject();
		String payload = CommonUtilityMethods.readJsonFile(property.getProperty("discoveryPutPayloadJsonPath"),docType).toString();
		JSONObject payloadOBJ = new JSONObject(payload);
		JSONObject contentObj = new JSONObject(payload).getJSONObject("content");
		
		
		
		
		if(docType.equalsIgnoreCase("taxrecord") || docType.equalsIgnoreCase("countyrate"))
		{ 
		lmsObj = contentObj.getJSONObject("items"); }
		
		
		else 
		{ 
			lmsObj = contentObj.getJSONObject("lmsObject");
        }
		
		String key = CommonUtilityMethods.documentKeys.get(docType);	
		/*Document name is changed*/
		payloadOBJ.putOpt("documentName", docName);
		
		/*ListingKey,memberKey,officeKey,Team key is changed*/
		lmsObj.putOpt(key, docName);
		
		/*Primary key is changed*/
		lmsObj.putOpt("primaryKey", docName);
		
		/*listOfficeBrokerOfRecordKey,listOffice key, MLS id, name and phone is changed for listing*/
		if(docType.equalsIgnoreCase("listing")) {
			lmsObj = putlistOfficeBrokerKey(documentName,lmsObj);
			String listingId = "100"+ObjectLayerPage.getListingId(httpRequest);
			lmsObj.putOpt("listingId", listingId);
		}
		
		/*Office MLS id, name, and email is changed for office*/
		if(docType.equalsIgnoreCase("office")) {
			lmsObj.putOpt("officeMlsId", "CoreAlayaAutomation"+docName);
			lmsObj.putOpt("officeName", "CoreAlayaAutomationOffice"+docName);
			lmsObj.putOpt("officeEmail", "CoreAlayaAutomation"+docName+"@yopmail.com");		
		}
		
		/*Office MLS id, name, key, memberEmail and memberPrivateEmail is changed for member*/
		if(docType.equalsIgnoreCase("member")) {
			lmsObj = ObjectLayerPostAPIPage.payloadForMember(documentName,lmsObj);				
		}
		
		/*teamExternalSystemID is changed for team*/
		if(docType.equalsIgnoreCase("team")) {
			Random random = new Random();
			String teamSysId = "T-01"+String.format("%04d", random.nextInt(10000));
			lmsObj.putOpt("teamExternalSystemID", teamSysId);
		}

		if(docType.equalsIgnoreCase("taxrecord") || docType.equalsIgnoreCase("countyrate"))
		{ contentObj.put("items", lmsObj); }
		else 
		{ contentObj.put("lmsObject", lmsObj); }
		
		payloadOBJ.putOpt("content", contentObj);
		return payloadOBJ;		
	}

	
	public static JSONObject createPayloadForPostRawObject(String docName, String docType) {
		String payloadForPost;
		String payload = CommonUtilityMethods.readJsonFile(property.getProperty("discoveryPutPayloadJsonPath"),docType).toString();
		logger.info(payload);
		JSONObject payloadOBJ = new JSONObject(payload);
		JSONObject contentObj = new JSONObject(payload).getJSONObject("content");
		payloadOBJ.putOpt("documentName", docName);
		contentObj.putOpt("Id", docName);
		payloadOBJ.putOpt("content", contentObj);
		payloadForPost = payloadOBJ.toString();
		return payloadOBJ;		
		}
	
	/* A method to verify post call response structure in discovery layer */
	
	public static void verifyPostDiscoveryResponseStructure(Response response, String docType, String docName) {
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
			softAssert.assertTrue(CollectionUtils.isEqualCollection(apiInfoFields, ExpectedApiInfoFields),"Fields inside API INFO mismatched for post call of discovery layer. Expected were "+ExpectedApiInfoFields+" Found were "+apiInfoFields+ " for doctype/docName - "+docType+"/"+docName);
		}
		else 
			softAssert.fail("API info object not found in response body for post discovery layer response for "+"doctype/docName - "+docType+"/"+docName);

        /*verifying document name here*/
		if(responseObj.has("documentName")) {
			softAssert.assertEquals(responseObj.get("documentName").toString(), docName);
		}
		else
			softAssert.fail("Document name is not found in response body for post discovery layer for "+"doctype/docName - "+docType+"/"+docName);
		
		/*verifying location Object here*/
		if(responseObj.has("location")) {
		JSONObject location = responseObj.getJSONObject("location");
		Iterator<String> Keys = location.keys();
		while (Keys.hasNext()) 
		{
		  String fieldValue = (String)Keys.next();		  
		  locationFields.add(fieldValue);
		}
		
		softAssert.assertTrue(CollectionUtils.isEqualCollection(locationFields, ExpectedlocationFields),"Fields inside location mismatched for POST API of discovery layer. Expected were "+ExpectedApiInfoFields+" Found were "+locationFields);
		String locationDocType = location.get("documentType").toString();
		String locationlocale = location.get("systemLocale").toString();
		softAssert.assertEquals(locationDocType,docType,"Document type in location mismatch. Expected was - "+docType+" found was - "+locationDocType+" for document name - "+docName);
		softAssert.assertEquals(locationlocale,"bright","System locale in location mismatch. Expected was - bright found was - "+locationlocale+" for document name - "+docName);	
		}
	   else 
	   softAssert.fail("Location object not found in response body for POST call discovery layer for doctype/documentname - "+docType+"/"+docName);
		
	   /*verifying size Object here*/
	   if(responseObj.has("size")) {	
			String size = responseObj.get("size").toString();
			if(size.length()== 0)
			softAssert.fail("size object was found empty in post call of discovery layer for doctype/docName - "+docType+"/"+docName);
	   }
	   else
	   softAssert.fail("size object not found in response body for POST call discovery layer for doctype/docName - "+docType+"/"+docName);
		
	   /*verifying last modified,tags,tag count, version id and versions Object here*/
	   if(responseObj.has("lastModified") && responseObj.has("tagCount") && responseObj.has("tags") && responseObj.has("versionId") && responseObj.has("versions")) {	
		logger.info("last modified,tags,tag count, version id and versions Object found in POST API call for discovery layer");
		JSONObject versions = responseObj.getJSONObject("versions");
		JSONObject versionHistory = (JSONObject) versions.getJSONArray("versionHistory").get(0);
		String versionIdInside = versionHistory.get("versionId").toString();
		String versionIdOutside = responseObj.get("versionId").toString();
	    softAssert.assertEquals(versionIdInside, versionIdOutside,"Versions ids from response and version history did not match in discovery layer for doctype/docName - "+docType+"/"+docName);
	    softAssert.assertEquals(versionHistory.get("isFirst"), true,"Version created via Post call is not the first version in discovery layer for doctype/docName - "+docType+"/"+docName);
	    softAssert.assertEquals(versionHistory.get("isLatest"), true,"Version created via Post call is not the latest version in discovery layer for doctype/docName - "+docType+"/"+docName);	    
	   }
	   else
	   softAssert.fail("last modified,tags,tag count, version id and versions Object not found in response body for POST call discovery layer for doctype/docName - "+docType+"/"+docName);
	  	
		softAssert.assertAll();	
	}
	
	/* A Method to verify success status of post call for discovery layer */

	public static void verifySuccessForPost(RequestSpecification httpRequest, String docType) throws InterruptedException {
		// Calling id generator API and getting document name
		String documentName = discoveryLayerPostAPIPage.getIdGenerator(httpRequest);
		logger.info("Discovery post call created document name for doctype: - "+docType+" is "+documentName);
		JSONObject payloadOBJ = createPayloadForPost(documentName,docType);
		// Call Post call for discovery layer
		Response responseFromPost = discoveryLayerUtilityPage.callPostOfDiscoveryLayer(httpRequest, docType, documentName,"true", "true", "true", "Bright", ResponseCodes.CREATED,payloadOBJ);
		//verifying response structure
		verifyPostDiscoveryResponseStructure(responseFromPost, docType, documentName);
		if(docType.equalsIgnoreCase("office") || docType.equalsIgnoreCase("member"))
		  Thread.sleep(30000);
		// Call Put call for discovery layer to add document deletion information
		addDocumentDeletionInfo(httpRequest, docType, documentName, responseFromPost,payloadOBJ);

	}

	/*
	 * A Method to verify attributes of post call with graph QL for discovery layer
	 */

	public static void verifyAttributesForPost(RequestSpecification httpRequest, String docType,ArrayList<String> AttributeNamesForDocStore, ArrayList<String> AttributeNamesForES) throws InterruptedException {
		JSONObject attributeResponseBody = new JSONObject();
		SoftAssert softAssert = new SoftAssert();
		// Calling id generator API and getting document name
		String documentName = discoveryLayerPostAPIPage.getIdGenerator(httpRequest);
		logger.info("Discovery post call created document - "+documentName);
		JSONObject payloadOBJ = createPayloadForPost(documentName,docType);
		// Call Post call for discovery layer
		Response responseFromPost = discoveryLayerUtilityPage.callPostOfDiscoveryLayer(httpRequest, docType, documentName,"true", "true", "true", "Bright", ResponseCodes.CREATED,payloadOBJ);
		if(docType.equalsIgnoreCase("office") || docType.equalsIgnoreCase("member"))
			  Thread.sleep(30000);
		// Call get to discovery layer , then graph QL call, finally matching both call responses
		Response responseFromGet = discoveryLayerUtilityPage.getCallForDiscoveryLayer(httpRequest, "Bright", "true", "true",docType, documentName);
		String responseFromGetString = responseFromGet.getBody().asString();
		softAssert.assertEquals(responseFromGet.getStatusCode(), ResponseCodes.SUCCESS);
		if(docType.equalsIgnoreCase("taxrecord") || docType.equalsIgnoreCase("countyrate"))
		{attributeResponseBody = new JSONObject(responseFromGetString).getJSONObject("content").getJSONObject("items");}
		else
		{attributeResponseBody = new JSONObject(responseFromGetString).getJSONObject("content").getJSONObject("lmsObject");}
		JSONObject attributeOBJforTime = new JSONObject(responseFromGetString).getJSONObject("metadata");
		ArrayList<String> AttributesFromDocStore = new ArrayList<String>();
		for (int i = 0; i < AttributeNamesForDocStore.size(); i++) {
			String attribute = AttributeNamesForDocStore.get(i);
			String attributeToAdd = null;
			if (attributeResponseBody.has(attribute)) {
				attributeToAdd = attributeResponseBody.get(AttributeNamesForDocStore.get(i)).toString();
			} else {
				attributeToAdd = attributeOBJforTime.get(AttributeNamesForDocStore.get(i)).toString();
			}
			AttributesFromDocStore.add(attributeToAdd);
		}
		String key = CommonUtilityMethods.documentKeys.get(docType);
		String method = CommonUtilityMethods.graphQlMethods.get(docType);
		ArrayList<String> AttributesFromES = CommonUtilityMethods.fetchGraphQlResponseByDocName(docType, documentName,method, key, AttributeNamesForES);
		Collections.sort(AttributesFromDocStore);
		Collections.sort(AttributesFromES);
		softAssert.assertEquals(AttributesFromDocStore.equals(AttributesFromES), true,"Discovery layer Post Test case : Doc store attributes did not match with ES attributes for document type and doc name - "+docType+"/"+documentName+ " Doc store attributes are - "+AttributesFromDocStore+ " ES attributes are - "+AttributesFromES);

		// Call Put call for discovery layer to add document deletion information
		if(docType.equalsIgnoreCase("office") || docType.equalsIgnoreCase("member"))
			  Thread.sleep(15000);
		addDocumentDeletionInfo(httpRequest, docType, documentName, responseFromPost,payloadOBJ);
		
		// Calling delete API of document store to complete the flow
		//deleteApiDocStore(httpRequest, docType, documentName);
		softAssert.assertAll();

	}

	/*
	 * A Method to verify 404 not found if wrong locale is given in post call for
	 * discovery layer
	 */

	public static void verifyNotFoundForWrongLocaleForPost(RequestSpecification httpRequest, String docType) {
		// Calling id generator API and getting document name
		String documentName = discoveryLayerPostAPIPage.getIdGenerator(httpRequest);
		logger.info("Discovery post call created document - "+documentName);
		JSONObject payloadOBJ = createPayloadForPost(documentName,docType);
		// Call Post call for discovery layer with wrong locale name
		discoveryLayerUtilityPage.callPostOfDiscoveryLayer(httpRequest, docType, documentName, "true", "true", "true","wrong", ResponseCodes.NOT_FOUND,payloadOBJ);
	}

	/*
	 * A Method to verify 404 not found if wrong docType is given in post call for
	 * discovery layer
	 */

	public static void verifyBadRequestForWrongDocTypeForPost(RequestSpecification httpRequest, String docType) {
		// Calling id generator API and getting document name
		String documentName = discoveryLayerPostAPIPage.getIdGenerator(httpRequest);
		logger.info("Discovery post call created document - "+documentName);
		JSONObject payloadOBJ = createPayloadForPost(documentName,docType);
		// Call Post call for discovery layer with wrong doc type
		discoveryLayerUtilityPage.callPostOfDiscoveryLayer(httpRequest, docType, documentName, "true", "true", "true","Bright", ResponseCodes.NOT_FOUND,payloadOBJ);

	}

	/*
	 * A Method to verify version id in post call for discovery layer with get call
	 * of discovery
	 */

	public static void verifyVersionIDForPost(RequestSpecification httpRequest, String docType) throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		// Calling id generator API and getting document name
		String documentName = discoveryLayerPostAPIPage.getIdGenerator(httpRequest);
		logger.info("Discovery post call created document - "+documentName);
		JSONObject payloadOBJ = createPayloadForPost(documentName,docType);
		// Call Post call for discovery layer 
		Response responseFromPost = discoveryLayerUtilityPage.callPostOfDiscoveryLayer(httpRequest, docType, documentName,"true", "true", "true", "Bright", ResponseCodes.CREATED,payloadOBJ);
		if(docType.equalsIgnoreCase("office") || docType.equalsIgnoreCase("member"))
			  Thread.sleep(30000);
		// Getting version ID and version history from post response
		String VersionIdPost = discoveryLayerUtilityPage.getVersionIdFromPutAPI(responseFromPost.asString());
		ArrayList<String> VersionHistory = discoveryLayerUtilityPage.getVersionIDForPost(responseFromPost.asString());
		// Calling get call of discovery layer and getting version id from it
		Response responseFromGet = discoveryLayerUtilityPage.getCallForDiscoveryLayer(httpRequest, "Bright", "true", "true",docType, documentName);
		String VersionIdGet = discoveryLayerUtilityPage.getVersionIdFromGetAPI(responseFromGet.asString());
		Boolean isMatched = discoveryLayerUtilityPage.versionIdHistoryMatch(VersionHistory, VersionIdPost, VersionIdGet);
		softAssert.assertTrue(isMatched,"New Version from version history Matched succesfully for Post call of discovery layer for docType - "+ docType);

		// Call Put call for discovery layer to add document deletion information
		addDocumentDeletionInfo(httpRequest, docType, documentName, responseFromPost,payloadOBJ);
		if(docType.equalsIgnoreCase("office") || docType.equalsIgnoreCase("member"))
			  Thread.sleep(15000);
		// Calling delete API of document store to complete the flow
		//deleteApiDocStore(httpRequest, docType, documentName);
		softAssert.assertAll();
	}

	/* A Method to verify bad request in post call for discovery layer */

	public static void verifyBadRequestForPost(RequestSpecification httpRequest, String docType, String parameterToFalse) {
		// Calling id generator API and getting document name
		String documentName = discoveryLayerPostAPIPage.getIdGenerator(httpRequest);
		logger.info("Discovery post call created document - "+documentName);
		JSONObject payloadOBJ = createPayloadForPost(documentName,docType);
		if(parameterToFalse.equalsIgnoreCase("includeVersions"))
		   // Call Post call for discovery layer with false include versions
		   discoveryLayerUtilityPage.callPostOfDiscoveryLayer(httpRequest, docType, documentName,"true", "false", "true", "Bright", ResponseCodes.BAD_REQUEST,payloadOBJ);
		else if(parameterToFalse.equalsIgnoreCase("pretty"))
		   // Call Post call for discovery layer with false pretty
		   discoveryLayerUtilityPage.callPostOfDiscoveryLayer(httpRequest, docType, documentName,"true", "true", "false", "Bright", ResponseCodes.BAD_REQUEST,payloadOBJ);
	}
	
	/* A Method to verify forbidden status in post call for discovery layer when API key is wrong*/
	
	public static void verifyForbiddenForPost(RequestSpecification httpRequest, String docType) {
		// Calling id generator API and getting document name
		String documentName = discoveryLayerPostAPIPage.getIdGenerator(httpRequest);
		logger.info("Discovery post call created document - "+documentName);
		JSONObject payloadOBJ = createPayloadForPost(documentName,docType);
		// Call Post call for discovery layer with wrong API key
		discoveryLayerUtilityPage.callForbiddenPostOfDiscoveryLayer(httpRequest, docType, documentName,"true", "true", "true", "Bright", ResponseCodes.FORBIDDEN,payloadOBJ);	
	}
	
	/** 
	 * A Method to create pay load for post call for object layer
	 **/

	public static JSONObject createPayloadForPostObjectLayer(String documentName, String docType) {
		RequestSpecification httpRequest= null;
		long docName = Long.parseLong(documentName);
		JSONObject lmsObj= new JSONObject();
		String payload = CommonUtilityMethods.readJsonFile(property.getProperty("objectPostPayloadJsonPath"),docType).toString();
		JSONObject payloadOBJ = new JSONObject(payload);
		JSONObject contentObj = new JSONObject(payload).getJSONObject("content");

		if(docType.equalsIgnoreCase("taxrecord") || docType.equalsIgnoreCase("countyrate"))
		{ lmsObj = contentObj.getJSONObject("items"); }
		else 
		{ lmsObj = contentObj.getJSONObject("lmsObject"); }
		
		String key = CommonUtilityMethods.documentKeys.get(docType);	
		/*Document name is changed*/
		payloadOBJ.putOpt("documentName", docName);
		
		/*ListingKey,memberKey,officeKey,Team key is changed*/
		lmsObj.putOpt(key, docName);
		
		/*Primary key is changed*/
		lmsObj.putOpt("primaryKey", docName);
		
		/*listOfficeBrokerOfRecordKey,listOffice key, MLS id, name and phone is changed for listing*/
		if(docType.equalsIgnoreCase("listing")) {
			lmsObj = putlistOfficeBrokerKey(documentName,lmsObj);
			String listingId = "100"+ObjectLayerPage.getListingId(httpRequest);
			lmsObj.putOpt("listingId", listingId);
		}
		
		/*Office MLS id, name, and email is changed for office*/
		if(docType.equalsIgnoreCase("office")) {
			lmsObj.putOpt("officeMlsId", "CoreAlayaAutomation"+docName);
			lmsObj.putOpt("officeName", "CoreAlayaAutomationOffice"+docName);
			lmsObj.putOpt("officeEmail", "CoreAlayaAutomation"+docName+"@yopmail.com");		
		}
		
		/*Office MLS id, name, key, memberEmail and memberPrivateEmail is changed for member*/
		if(docType.equalsIgnoreCase("member")) {
			lmsObj = ObjectLayerPostAPIPage.payloadForMember(documentName,lmsObj);				
		}
		
		/*teamExternalSystemID is changed for team*/
		if(docType.equalsIgnoreCase("team")) {
			Random random = new Random();
			String teamSysId = "T-01"+String.format("%04d", random.nextInt(10000));
			lmsObj.putOpt("teamExternalSystemID", teamSysId);
		}

		if(docType.equalsIgnoreCase("taxrecord") || docType.equalsIgnoreCase("countyrate"))
		{ contentObj.put("items", lmsObj); }
		else 
		{ contentObj.put("lmsObject", lmsObj); }
		
		payloadOBJ.putOpt("content", contentObj);
		return payloadOBJ;		
	}
	public static JSONObject putlistOfficeBrokerKey(String documentName,JSONObject lmsObj) 
	{
		ArrayList<String> returnField = new ArrayList<String>(Arrays.asList("memberKey"));
		ArrayList<String> returnFieldForOfficeData = new ArrayList<String>(Arrays.asList("officeKey","officeMlsId","officeName","memberOfficePhone"));

		String listOfficeBrokerKey = CommonUtilityMethods.fetchGraphQlResponseUsingMemberMlsID("member",property.getProperty("UserName"),"getMembers",returnField);
		JSONArray officeData =ObjectLayerPostAPIPage.getofficeDetsForMemberMLSId(property.getProperty("UserName"),returnFieldForOfficeData);
		JSONObject officeDataObj = officeData.getJSONObject(0);
		String listOfficeKey = officeDataObj.get("officeKey").toString();
		String listOfficeMlsId = officeDataObj.get("officeMlsId").toString();
		String listOfficeName = officeDataObj.get("officeName").toString();
		String listOfficePhone = officeDataObj.get("memberOfficePhone").toString();

		long listOfficeBrokerKeyLong = Long.parseLong(listOfficeBrokerKey);
		long listOfficeKeyLong = Long.parseLong(listOfficeKey);
		property.setProperty("memberkey", listOfficeBrokerKey);

		lmsObj.putOpt("listOfficeBrokerOfRecordKey", listOfficeBrokerKeyLong);
		lmsObj.putOpt("listOfficeKey", listOfficeKeyLong);
		lmsObj.putOpt("listOfficeMlsId", listOfficeMlsId);
		lmsObj.putOpt("listOfficeName", listOfficeName);
		lmsObj.putOpt("listOfficePhone", listOfficePhone);

		return lmsObj;
	}

	
	

}