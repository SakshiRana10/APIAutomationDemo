package bright.api.alaya.pages.auditTrail;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.collections4.CollectionUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import org.testng.util.Strings;

import bright.api.alaya.pages.discoveryLayer.discoveryLayerUtilityPage;
import bright.api.alaya.pages.objectLayer.ObjectLayerPage;
import bright.api.alaya.pages.objectLayer.ObjectLayerPutAPIPage;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GetHistoryAPIPage extends MainClassAlaya{
	
	/** A method to get audit trail document name */
	
	public static String getRandomDocName(String docType) {
		ArrayList<String> docNames = CommonUtilityMethods.docListMap.get(docType);
		String docName = docNames.get(CommonUtilityMethods.pickRandomFromList(docNames));
		return docName;
	}

	
	/** A method to call get history API of audit Trail*/

	public static Response getHistoryAPI (RequestSpecification httpRequest, String systemLoc, String doctype, String docName,int expectedStatusCode,int page,int perPage, String tableName) {
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.auditTrailRequestSpec();
		httpRequest = RestAssured.given();	
	
		if(tableName.contains("Bright"))
			requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"auditTrail").getString("getHistoryWithoutTableName").toString());	
		else	
		    requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"auditTrail").getString("getHistory").toString());	
		
		requestSpecification.pathParam("systemLocale", systemLoc);
		requestSpecification.pathParam("docType", doctype);
		requestSpecification.pathParam("docName", docName);
		requestSpecification.queryParam("page", page);
		requestSpecification.queryParam("perPage", perPage);
		if(!tableName.contains("Bright"))
		requestSpecification.queryParam("tableName", tableName);
				
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.get();
		Assert.assertEquals(expectedStatusCode, response.getStatusCode(),"Status Code mismatch found for get History API. Expcected was - "+expectedStatusCode+" Received was - "+response.getStatusCode()+" Get History Response body was - "+response.getBody().asString());		
		return response;
	}

	/** A method to verify 200 Ok status response structure, count and keys for getHistory API   */
	
	public static void verifyResponseForGetHistory(RequestSpecification httpRequest,String locale, String documentType,String docName, int responseCode, int page, int perPage, String tableName) {
		SoftAssert softAssert = new SoftAssert();
		if(Strings.isNullOrEmpty(docName)) {
			Assert.fail("No valid document found");  
		}	
		
		Response response = getHistoryAPI(httpRequest,locale,documentType,docName,responseCode,page,perPage,tableName);
		JSONObject responseObj = new JSONObject(response.getBody().asString());	
		
		//verifying status code 
		softAssert.assertEquals(response.getStatusCode(), responseCode, "Status code mismatch found. Expected was - "+responseCode+"Actual was - "+response.getStatusCode());
		logger.info("Verified the Get history status code "+response.getStatusCode()+" for document type - " + documentType);
		
		//verifying API info object 
		ArrayList<String> ExpectedApiInfoFields = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.auditTrailPath, "apiInfo").get("fields"));
		ArrayList<String> apiInfoFields = new ArrayList<String>();
		if(responseObj.has("apiInfo")) {
		JSONObject apiInfo = responseObj.getJSONObject("apiInfo");
		Iterator<String> Keys = apiInfo.keys();
		while (Keys.hasNext()) 
		{
		String fieldValue = (String)Keys.next();
		apiInfoFields.add(fieldValue);
		}
		softAssert.assertTrue(CollectionUtils.isEqualCollection(apiInfoFields, ExpectedApiInfoFields),"Fields inside API INFO mismatched for get history API of Audit Trail. Expected were "+ExpectedApiInfoFields+" Found were "+apiInfoFields);
		}
		else 
		softAssert.fail("API info object not found in response body for get history API for document name - "+docName+" and document type - "+documentType);
				
		//verifying count object
		int count = 0;
		if(responseObj.has("count")) {
			count = (int) responseObj.get("count");
		}
		else 
		softAssert.fail("Count object not found in response body for get history API for document name - "+docName+" and document type - "+documentType);
		
		//verifying items array 
		int itemObjCount = 0;
		if(responseObj.has("items")) {
			JSONArray items = (JSONArray) responseObj.get("items");
			itemObjCount = items.length();
		}
		else 
		softAssert.fail("Items array object not found in response body for get history API for document name - "+docName+" and document type - "+documentType);
		
		//verifying count and itemsObject have same number 
		softAssert.assertEquals(count, itemObjCount,"Count and item object count did not match for get history API for document name - "+docName+" and document type - "+documentType);
	
		//verifying items object have PropHistKey and PropHistListingKey
		JSONArray items = (JSONArray) responseObj.get("items");
		for(int i=0; i<items.length(); i++) {
			JSONObject objInsideItems = (JSONObject) items.get(i);
			if(documentType.equalsIgnoreCase("listing")) {
			if(!objInsideItems.has("PropHistKey") || !objInsideItems.has("PropHistListingKey")) {
			softAssert.fail("PropHistKey or PropHistListingKey not found in items object no - "+i+" for document name - "+docName+" In listing");
			}
			}
			else {
		    if(!objInsideItems.has("SysHistKey") || !objInsideItems.has("SysHistObjectKey")) {
			softAssert.fail("SysHistKey or SysHistObjectKey not found in items object no - "+i+" for document name - "+docName+" In Member/Office");
			}
			}
		}
		softAssert.assertAll();	
	
	}
	
	/** A method to verify status codes for getHistory API   */

	public static void verifyStatusForGetHistory(RequestSpecification httpRequest,String locale, String documentType,String docName, int responseCode, int page, int perPage, String tableName) {
		if(Strings.isNullOrEmpty(docName)) {
			Assert.fail("No valid document found");  
		}	
		Response response = getHistoryAPI(httpRequest,locale,documentType,docName,responseCode,page,perPage,tableName);
		Assert.assertEquals(response.getStatusCode(), responseCode, "Status code mismatch found. Expected was - "+responseCode+"Actual was - "+response.getStatusCode());
		logger.info("Verified the Get history status code "+response.getStatusCode()+" for document type - " + documentType);
	}
	
	/** A method to verify forbidden status code for getHistory API when x API key is wrong   */
	
	public static void verifyForbiddenForWrongKey(RequestSpecification httpRequest,String locale, String doctype,String docName, int responseCode, int page, int perPage, String tableName) {
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.auditTrailRequestSpec();
		httpRequest = RestAssured.given();	
	
		if(tableName.contains("Bright"))
			requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"auditTrail").getString("getHistoryWithoutTableName").toString());	
		else	
		    requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"auditTrail").getString("getHistory").toString());	
		
		requestSpecification.pathParam("systemLocale", locale);
		requestSpecification.pathParam("docType", doctype);
		requestSpecification.pathParam("docName", docName);
		requestSpecification.queryParam("page", page);
		requestSpecification.queryParam("perPage", perPage);
		if(!tableName.contains("Bright"))
		requestSpecification.queryParam("tableName", tableName);
				
		requestSpecification.headers("x-api-key","check");                 //passing invalid value for x-api-key
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.get();
		Assert.assertEquals(responseCode, response.getStatusCode(),"Status Code mismatch found for get History API. Expcected was - "+responseCode+" Received was - "+response.getStatusCode()+" Get History Response body was - "+response.getBody().asString());		
		logger.info("Verified the Get history forbidden status code of wrong x api key - "+response.getStatusCode()+" for document type - " + doctype);
	}
	
	/** A method to verify forbidden status code for getHistory API when calling method is wrong   */
	
	public static void verifyForbiddenForWrongMethod(RequestSpecification httpRequest,String locale, String doctype,String docName, int responseCode, int page, int perPage, String tableName) {
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.auditTrailRequestSpec();
		httpRequest = RestAssured.given();	
	
		if(tableName.contains("Bright"))
			requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"auditTrail").getString("getHistoryWithoutTableName").toString());	
		else	
		    requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"auditTrail").getString("getHistory").toString());	
		
		requestSpecification.pathParam("systemLocale", locale);
		requestSpecification.pathParam("docType", doctype);
		requestSpecification.pathParam("docName", docName);
		requestSpecification.queryParam("page", page);
		requestSpecification.queryParam("perPage", perPage);
		if(!tableName.contains("Bright"))
		requestSpecification.queryParam("tableName", tableName);
				
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.put();
		Assert.assertEquals(responseCode, response.getStatusCode(),"Status Code mismatch found for get History API. Expcected was - "+responseCode+" Received was - "+response.getStatusCode()+" Get History Response body was - "+response.getBody().asString());		
		logger.info("Verified the Get history forbidden status code of wrong method - "+response.getStatusCode()+" for document type - " + doctype);
	}
	
	/** A method to call put of discovery layer and changing data to be verified in getHistory for parent field*/
	
	public static String putDiscoveryForGetHistory(RequestSpecification httpRequest,String locale, String docType,String docName,String fieldName, String fieldValue) {
		String responseFromGet = discoveryLayerUtilityPage.getCallForDiscoveryLayer(httpRequest,"Bright","true","true",docType,docName).getBody().asString();
		JSONObject contentObj = new JSONObject(responseFromGet).getJSONObject("content");
		JSONObject lmsObj = contentObj.getJSONObject("lmsObject");
		String originalValueOfField = lmsObj.get(fieldName).toString();
		lmsObj.put(fieldName, fieldValue);
		contentObj.put("lmsObject", lmsObj);
		JSONObject responseBodyObj = new JSONObject();
		responseBodyObj.put("lastUpdatedBy", "Core Alaya Automation for GetHistory");
		responseBodyObj.put("content", contentObj);
		String payload = responseBodyObj.toString();
		String lastModified = new JSONObject(responseFromGet).getJSONObject("metadata").getString("lastModified");
		Response  responseFromPut = discoveryLayerUtilityPage.putCallForDiscoveryLayer(httpRequest,"true","true","true","Bright",docType,docName,lastModified,payload);	    
	    return originalValueOfField;
	}
	
    /** A method to call put of discovery layer and changing data to be verified in getHistory for child field*/
	
	public static String putDiscoveryInChildForGetHistory(RequestSpecification httpRequest,String locale, String docType,String docName,String childObjName,String fieldName,String childObjValue,String condition) {
		String responseFromGet = discoveryLayerUtilityPage.getCallForDiscoveryLayer(httpRequest,"Bright","true","true",docType,docName).getBody().asString();
		JSONObject contentObj = new JSONObject(responseFromGet).getJSONObject("content");
		JSONObject lmsObj = contentObj.getJSONObject("lmsObject");
		JSONObject childObj = lmsObj.getJSONObject(childObjName);	
		JSONObject childObjValueObj = new JSONObject(childObjValue);
		if(condition.equals("Adding")) {
		childObj.put(fieldName, childObjValueObj);
		lmsObj.put(childObjName,childObj);
		}
		else if(condition.equals("Reverting")) {
		lmsObj.put(childObjName, childObjValueObj);
		}
		contentObj.put("lmsObject", lmsObj);
		JSONObject responseBodyObj = new JSONObject();
		responseBodyObj.put("lastUpdatedBy", "Core Alaya Automation for GetHistory");
		responseBodyObj.put("content", contentObj);
		String payload = responseBodyObj.toString();
		String lastModified = new JSONObject(responseFromGet).getJSONObject("metadata").getString("lastModified");
		Response  responseFromPut = discoveryLayerUtilityPage.putCallForDiscoveryLayer(httpRequest,"true","true","true","Bright",docType,docName,lastModified,payload);	    
		JSONObject originalcontentObj = new JSONObject(responseFromGet).getJSONObject("content");
		JSONObject originalchildObj = originalcontentObj.getJSONObject("lmsObject").getJSONObject(childObjName);
		return originalchildObj.toString();
	}
	
	/** A method to verify data in response of getHistory API after doing a put call for parent field*/
	
	public static void verifyDataAfterPutParentFieldInGetHistory(RequestSpecification httpRequest,String locale, String documentType,String docName, int responseCode, int page, int perPage, String tableName,String fieldName, String fieldValue, ArrayList<String> verificationFields) {
		SoftAssert softAssert = new SoftAssert();
		if(Strings.isNullOrEmpty(docName)) {
			Assert.fail("No valid document found");  
		}	
		
		//put of discovery with saved values for parent fields
		String originalValueOfField = putDiscoveryForGetHistory(httpRequest,locale,documentType,docName,fieldName,fieldValue);
		
		//get to get the version id, last modified and parent field
		String responseFromGetAfterPut = discoveryLayerUtilityPage.getCallForDiscoveryLayer(httpRequest,"Bright","true","true",documentType,docName).getBody().asString();
		String FieldAfterPut = new JSONObject(responseFromGetAfterPut).getJSONObject("content").getJSONObject("lmsObject").get(fieldName).toString();
		String versionIdAfterPut = new JSONObject(responseFromGetAfterPut).getJSONObject("metadata").get("versionId").toString();
		String lastModifiedAfterPut = new JSONObject(responseFromGetAfterPut).getJSONObject("metadata").get("lastModified").toString();
			
		//call get history to find versionId, field name, field value and last modified
		String responseOfGetHistory = getHistoryAPI(httpRequest,locale,documentType,docName,responseCode,page,perPage,tableName).getBody().asString();
		JSONObject responseObjOfGetHistory = new JSONObject(responseOfGetHistory);	
		JSONArray items = (JSONArray) responseObjOfGetHistory.get("items");
		JSONObject itemsObj = (JSONObject) items.get(0);
		String FieldInGetHistory = itemsObj.get(verificationFields.get(0)).toString();
		String FieldNameInGetHistory =  itemsObj.get(verificationFields.get(1)).toString();
		String versionIdInGetHistory = itemsObj.get(verificationFields.get(2)).toString();
		String lastModifiedInGetHistory = itemsObj.get(verificationFields.get(3)).toString();
		String tableNameInGetHistory = itemsObj.get(verificationFields.get(4)).toString();
				
		//verify changed values in getHistory object
		softAssert.assertEquals(FieldAfterPut, FieldInGetHistory, "Mismatch found for Field value for field - "+fieldName+" Expected was - "+FieldAfterPut+" Actual in get History was - "+FieldInGetHistory+" Document name and type is - "+documentType+" / "+docName);
		softAssert.assertEquals(fieldName.toUpperCase(), FieldNameInGetHistory.toUpperCase(), "Mismatch found for Field Name for field - "+fieldName+" Expected was - "+fieldName+" Actual in get History was - "+FieldNameInGetHistory+" Document name and type is - "+documentType+" / "+docName);
		softAssert.assertEquals(versionIdAfterPut, versionIdInGetHistory, "Mismatch found for versionId value for field - "+fieldName+" Expected was - "+versionIdAfterPut+" Actual in get History was - "+versionIdInGetHistory+" Document name and type is - "+documentType+" / "+docName);
		softAssert.assertEquals(lastModifiedAfterPut, lastModifiedInGetHistory, "Mismatch found for last modified value for field - "+fieldName+" Expected was - "+lastModifiedAfterPut+" Actual in get History was - "+lastModifiedInGetHistory+" Document name and type is - "+documentType+" / "+docName);
		softAssert.assertEquals(tableName, tableNameInGetHistory, "Mismatch found for tableName value for field - "+fieldName+" Expected was - "+tableName+" Actual in get History was - "+tableNameInGetHistory+" Document name and type is - "+documentType+" / "+docName);	
		
		//reverting back the changed field and verifying if it got back to original
		putDiscoveryForGetHistory(httpRequest,locale,documentType,docName,fieldName,originalValueOfField);
		String responseFromGetAfterReverting = discoveryLayerUtilityPage.getCallForDiscoveryLayer(httpRequest,"Bright","true","true",documentType,docName).getBody().asString();
		String FieldAfterReverting = new JSONObject(responseFromGetAfterReverting).getJSONObject("content").getJSONObject("lmsObject").get(fieldName).toString();
		softAssert.assertEquals(originalValueOfField, FieldAfterReverting , "Mismatch found after reverting process, Original value expected was - "+originalValueOfField+" Found value after reverting was - "+FieldAfterReverting);
			
		softAssert.assertAll();	
	}
	
	
	
    /** A method to verify data in response of getHistory API after doing a put call for child field*/
	
	public static void verifyDataAfterPutChildFieldInGetHistory(RequestSpecification httpRequest,String locale, String documentType,String docName, int responseCode, int page, int perPage, String tableName,String childObjName,String fieldName,String columnName,ArrayList<String> verificationFields) {
		SoftAssert softAssert = new SoftAssert();
		if(Strings.isNullOrEmpty(docName)) {
			Assert.fail("No valid document found");  
		}	
		
		//put of discovery with saved values for child fields
		JSONObject childObjValueObj =  CommonUtilityMethods.readJsonFile(property.getProperty("auditTrailJsonPath"),"childPayload_"+documentType);	
		String originalValueOfChildField = putDiscoveryInChildForGetHistory(httpRequest,locale,documentType,docName,childObjName,fieldName,childObjValueObj.toString(),"Adding");
		
		//get to get the version id, last modified and parent field
		String responseFromGetAfterPut = discoveryLayerUtilityPage.getCallForDiscoveryLayer(httpRequest,"Bright","true","true",documentType,docName).getBody().asString();
		String FieldAfterPut = new JSONObject(responseFromGetAfterPut).getJSONObject("content").getJSONObject("lmsObject").getJSONObject(childObjName).getJSONObject(fieldName).get("primaryKey").toString();
		String versionIdAfterPut = new JSONObject(responseFromGetAfterPut).getJSONObject("metadata").get("versionId").toString();
		String lastModifiedAfterPut = new JSONObject(responseFromGetAfterPut).getJSONObject("metadata").get("lastModified").toString();
			
		//call get history to find versionId, field name, field value and last modified
		String responseOfGetHistory = getHistoryAPI(httpRequest,locale,documentType,docName,responseCode,page,perPage,tableName).getBody().asString();
		JSONObject responseObjOfGetHistory = new JSONObject(responseOfGetHistory);	
		JSONArray items = (JSONArray) responseObjOfGetHistory.get("items");
		JSONObject itemsObj = (JSONObject) items.get(0);
		String FieldInGetHistory = itemsObj.get(verificationFields.get(0)).toString();
		String FieldNameInGetHistory =  itemsObj.get(verificationFields.get(1)).toString();
		String versionIdInGetHistory = itemsObj.get(verificationFields.get(2)).toString();
		String lastModifiedInGetHistory = itemsObj.get(verificationFields.get(3)).toString();
		String tableNameInGetHistory = itemsObj.get(verificationFields.get(4)).toString();
				
		//verify changed values in getHistory object
		softAssert.assertEquals(FieldAfterPut, FieldInGetHistory, "Mismatch found for Field value for field - "+childObjName+"/"+fieldName+" Expected was - "+FieldAfterPut+" Actual in get History was - "+FieldInGetHistory+" Document name and type is - "+documentType+" / "+docName);
		softAssert.assertEquals(columnName, FieldNameInGetHistory, "Mismatch found for Field Name for field - "+childObjName+"/"+fieldName+" Expected was - "+fieldName+" Actual in get History was - "+FieldNameInGetHistory+" Document name and type is - "+documentType+" / "+docName);
		softAssert.assertEquals(versionIdAfterPut, versionIdInGetHistory, "Mismatch found for versionId value for field - "+childObjName+"/"+fieldName+" Expected was - "+versionIdAfterPut+" Actual in get History was - "+versionIdInGetHistory+" Document name and type is - "+documentType+" / "+docName);
		softAssert.assertEquals(lastModifiedAfterPut, lastModifiedInGetHistory, "Mismatch found for last modified value for field - "+childObjName+"/"+fieldName+" Expected was - "+lastModifiedAfterPut+" Actual in get History was - "+lastModifiedInGetHistory+" Document name and type is - "+documentType+" / "+docName);
		softAssert.assertEquals(tableName, tableNameInGetHistory, "Mismatch found for tableName value for field - "+fieldName+" Expected was - "+tableName+" Actual in get History was - "+tableNameInGetHistory+" Document name and type is - "+documentType+" / "+docName);	
		
		//reverting back the changed field and verifying if it got back to original
		putDiscoveryInChildForGetHistory(httpRequest,locale,documentType,docName,childObjName,fieldName,originalValueOfChildField,"Reverting");		
		String responseFromGetAfterReverting = discoveryLayerUtilityPage.getCallForDiscoveryLayer(httpRequest,"Bright","true","true",documentType,docName).getBody().asString();
		String FieldAfterReverting = new JSONObject(responseFromGetAfterReverting).getJSONObject("content").getJSONObject("lmsObject").getJSONObject(childObjName).toString();
		softAssert.assertEquals(originalValueOfChildField, FieldAfterReverting , "Mismatch found after reverting process, Original value expected was - "+originalValueOfChildField+" Found value after reverting was - "+FieldAfterReverting);
					
		softAssert.assertAll();	
	}
	
	}
