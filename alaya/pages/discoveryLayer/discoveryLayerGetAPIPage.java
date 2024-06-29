package bright.api.alaya.pages.discoveryLayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

import org.apache.commons.collections4.CollectionUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.util.Strings;

import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class discoveryLayerGetAPIPage extends MainClassAlaya{


	public static String getRandomDocName(String docType) {
		ArrayList<String> docNames = CommonUtilityMethods.docListMap.get(docType);
		String docName = docNames.get(CommonUtilityMethods.pickRandomFromList(docNames));
		return docName;
	}

	/**
	 * A method to verify 200 status code when get api is successfully sent
	 */

	public static void verifySuccessStatusforGETApi(RequestSpecification httpRequest,String docType) {

		ArrayList<String> contentFields = new ArrayList<String>();
		ArrayList<String> ExpectedContentFields = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath, "getResponseContent").get("fields"));
		ArrayList<String> apiInfoFields = new ArrayList<String>();
		ArrayList<String> ExpectedApiInfoFields = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath, "getResponseAPIInfo").get("fields"));	
		ArrayList<String> metadataFields = new ArrayList<String>();
		ArrayList<String> ExpectedMetadataFields = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath, "getResponseMetadata").get("fields"));	
		ArrayList<String> TaxCountyExpectedContentFields =CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath, "TaxCountygetResponseContent").get("fields"));

		String docName = getRandomDocName(docType);
		
		  if(Strings.isNullOrEmpty(docName)) { Assert.fail("No valid document found");
		  }
		 
		Response  response = discoveryLayerUtilityPage.getCallForDiscoveryLayer(httpRequest,"Bright","true","true",docType,docName);
		logger.info("Response for get API of discovery layer for document type "+docType+" is - "+response.getBody().asString());
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.SUCCESS, "Status code mismatch found. Expected was - "+ResponseCodes.SUCCESS+ " Found was - "+response.getStatusCode());
		logger.info("Verified the Success Status for get api of discovery for document type - "+ docType);

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
				contentFields.add(fieldValue);
			}
			
			 if(docType.equalsIgnoreCase("countyrate") || docType.equalsIgnoreCase("taxrecord")) {
				 Assert.assertTrue(CollectionUtils.isEqualCollection(contentFields, TaxCountyExpectedContentFields),"Fields inside content mismatched. Expected were "+ExpectedContentFields+" Found were "+contentFields+ " for doctype - "+docType); }
			 else
			
			Assert.assertTrue(CollectionUtils.isEqualCollection(contentFields, ExpectedContentFields),"Fields inside content mismatched. Expected were "+ExpectedContentFields+" Found were "+contentFields+ " for doctype - "+docType);
		}
		else 
			Assert.fail("content object not found in response body for get object layer response for "+docType);
	}

	/**
	 * A method to verify 400 error if includeVersions is false
	 */

	public static void verifyResponseIfIncludeVersionsIsFalse(RequestSpecification httpRequest,String docType) {

		String docName = getRandomDocName(docType);
		if(Strings.isNullOrEmpty(docName)) {
			Assert.fail("No valid document found");
		}
		Response  response = discoveryLayerUtilityPage.getCallForDiscoveryLayer(httpRequest,"Bright","false","true",docType,docName);
		logger.info("Response for get API include versions of discovery layer for document type "+docType+" is - "+response.getBody().asString());
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.BAD_REQUEST, "Status code mismatch found. Expected was - "+ResponseCodes.BAD_REQUEST+ " Found was - "+response.getStatusCode());
		logger.info("Verified the 400 Bad Request Status when include versions is false for document type - "+ docType);
	}

	//	/**
	//	 * A method to verify 400 error if includeTags is false
	//	 */
	//
	//	public static void verifyResponseIfIncludeTagsIsFalse(RequestSpecification httpRequest,String docType) {
	//		
	//		String docName = builderModelDocumentNames.get(CommonUtilityMethods.pickRandomFromList(builderModelDocumentNames)2);
	//		Response  response = CommonUtilityMethods.getCallForDiscoveryLayer(httpRequest,"Bright","true","false","true",docType,docName);
	//		Assert.assertEquals(response.getStatusCode(), ResponseCodes.BAD_REQUEST);
	//		logger.info("Verified the 400 Bad Request Status when include tags is false in building_name");
	//	}

	/**
	 * A method to verify 400 error if includePretty is false
	 */

	public static void verifyResponseIfIncludePrettyIsFalse(RequestSpecification httpRequest,String docType) {

		String docName = getRandomDocName(docType);
		if(Strings.isNullOrEmpty(docName)) {
			Assert.fail("No valid document found");
		}
		Response  response = discoveryLayerUtilityPage.getCallForDiscoveryLayer(httpRequest,"Bright","true","false",docType,docName);
		logger.info("Response for get API include pretty false of discovery layer for document type "+docType+" is - "+response.getBody().asString());
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.BAD_REQUEST, "Status code mismatch found. Expected was - "+ResponseCodes.BAD_REQUEST+ " Found was - "+response.getStatusCode());
		logger.info("Verified the 400 Bad Request Status when include pretty is false in for document type - "+ docType);
	}

	/**
	 * A method to verify versions are not displayed if IncludeVersions is unchecked
	 */

	public static void verifyResponseForUncheckedIncludeVersions(RequestSpecification httpRequest,String docType) {

		String docName = getRandomDocName(docType);
		if(Strings.isNullOrEmpty(docName)) {
			Assert.fail("No valid document found");
		}
		Response  response = discoveryLayerUtilityPage.getCallForDiscoveryLayerIncludeVersions(httpRequest,"Bright","true",docType,docName);
		logger.info("Response for get API unchecked include versions of discovery layer for document type "+docType+" is - "+response.getBody().asString());
		String body = response.getBody().asString();
		int validate = 1;           // 1 for true and 0 for false
		if(body.contains("versionHistory")) {
			validate = 0;
		}
		Assert.assertEquals(1, validate, "Version history was found even after unchecked include versions");
		logger.info("Verified versions are not present for unchecked include versions for document type - "+ docType);
	}

	/**
	 * A method to verify 404 error if wrong system locale is given
	 */

	public static void verifyResponseForWrongSystemLocale(RequestSpecification httpRequest,String docType) {

		String docName = getRandomDocName(docType);
		if(Strings.isNullOrEmpty(docName)) {
			Assert.fail("No valid document found");
		}
		Response  response = discoveryLayerUtilityPage.getCallForDiscoveryLayer(httpRequest,"wrong","true","true",docType,docName);
		logger.info("Response for get API wrong system locale of discovery layer for document type "+docType+" is - "+response.getBody().asString());
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.NOT_FOUND, "Status code mismatch found. Expected was - "+ResponseCodes.NOT_FOUND+ " Found was - "+response.getStatusCode());
		logger.info("Verified the 404 unknown Status for wrong system locale for document type - "+ docType);
	}

	/**
	 * A method to verify 404 error if wrong documenttype is given
	 */

	public static void verifyResponseForWrongDocType(RequestSpecification httpRequest,String docType) {

		String docName = getRandomDocName(docType);
		if(Strings.isNullOrEmpty(docName)) {
			Assert.fail("No valid document found");
		}
		Response  response = discoveryLayerUtilityPage.getCallForDiscoveryLayer(httpRequest,"Bright","true","true","wrong",docName);
		logger.info("Response for get API wrong document type of discovery layer for document type "+docType+" is - "+response.getBody().asString());
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.NOT_FOUND, "Status code mismatch found. Expected was - "+ResponseCodes.NOT_FOUND+ " Found was - "+response.getStatusCode());
		logger.info("Verified the 404 not found Status for wrong document type - "+ docType);
	}

	/**
	 * A method to verify 463 error if wrong documentName is given
	 */

	public static void verifyResponseForWrongDocument(RequestSpecification httpRequest,String docType) {

		String wrongDoc = discoveryLayerUtilityPage.getWrongDocForDiscoveryLayer();
		Response  response = discoveryLayerUtilityPage.getCallForDiscoveryLayer(httpRequest,"Bright","true","true",docType,wrongDoc);
		logger.info("Response for get API wrong document name of discovery layer for document type "+docType+" is - "+response.getBody().asString());
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.NO_SUCH_KEY, "Status code mismatch found. Expected was - "+ResponseCodes.NO_SUCH_KEY+ " Found was - "+response.getStatusCode());
		logger.info("Verified the 463 Not found Status for wrong document name for document type - "+ docType);
	}

	/**
	 * A method to verify 410 gone error if deleted document is given
	 */

	public static void verifyResponseForDeletedDocument(RequestSpecification httpRequest,String docType) {

		String deletedDoc = discoveryLayerUtilityPage.getDeletedDocForDiscoveryLayer(docType);
		Response  response = discoveryLayerUtilityPage.getCallForDiscoveryLayer(httpRequest,"Bright","true","true",docType,deletedDoc);
		logger.info("Response for get API deleted document of discovery layer for document type "+docType+" is - "+response.getBody().asString());
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.GONE, "Status code mismatch found. Expected was - "+ResponseCodes.GONE+ " Found was - "+response.getStatusCode());
		logger.info("Verified the 410 Gone Status for deleted document for document type - "+ docType);
	}

	/**
	 * A method to verify attributes from doc store are matching elastic search
	 */

	public static void verifyAttributesForDiscoveryLayer(RequestSpecification httpRequest,String docType,ArrayList<String> AttributeNamesForDocStore, ArrayList<String> AttributeNamesForES) {
		JSONObject attributeResponseBody = new JSONObject();		
		String docName = getRandomDocName(docType);	
		if(Strings.isNullOrEmpty(docName)) {
			Assert.fail("No valid document found");
		}
		String attributeResponse = discoveryLayerUtilityPage.getCallForDiscoveryLayer(httpRequest,"Bright","true","true",docType,docName).getBody().asString();
		logger.info("Response for get API attribute verification of discovery layer for document type "+docType+" is - "+attributeResponse);
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
		ArrayList<String> AttributesFromES = CommonUtilityMethods.fetchGraphQlResponseByDocName(docType, docName,method,key,AttributeNamesForES);	    
		Collections.sort(AttributesFromDocStore);
		Collections.sort(AttributesFromES);
		Assert.assertEquals(AttributesFromDocStore.equals(AttributesFromES), true,"Doc store attributes did not match with ES attributes for document type - "+ docType + "Attributes from doc store were - "+ AttributesFromDocStore + "Attributes from ES were - "+AttributesFromES);
		logger.info("Verified attributes from doc store are matching elastic search ifor document type - "+ docType);
	}

	/**
	 * A method to verify the Forbidden error for Post call of GetAPI for discovery Layer
	 */

	public static void verifyWrongAPIKeyForGET(RequestSpecification httpRequest,String docType) {

		String docName = getRandomDocName(docType);
		if(Strings.isNullOrEmpty(docName)) {
			Assert.fail("No valid document found");
		}
		Response  response = discoveryLayerUtilityPage.verifyForbiddenForGetAPIDiscovery(httpRequest,"Bright","true","true",docType,docName);
		logger.info("Response for get API wrong apikey of discovery layer for document type "+docType+" is - "+response.getBody().asString());
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.FORBIDDEN,"Status code mismatch found. Expected was - "+ResponseCodes.FORBIDDEN+ " Found was - "+response.getStatusCode());
		logger.info("Verified the forbidden Request for get api for document type - "+ docType);
	}
}
