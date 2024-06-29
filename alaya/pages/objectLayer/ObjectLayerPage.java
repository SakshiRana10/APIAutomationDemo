package bright.api.alaya.pages.objectLayer;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.collections4.CollectionUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ObjectLayerPage extends MainClassAlaya {

	
	/**
	 * A method to call get API of object layer
	 */

	public static Response getCallForObjectyLayer(RequestSpecification httpRequest, String systemLoc, String doctype, String docName,int expectedStatusCode) {
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
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.get();
		Assert.assertEquals(expectedStatusCode, response.getStatusCode(),"Status Code mismatch found for get call in object layer. Expcected was - "+expectedStatusCode+" Received was - "+response.getStatusCode()+" Object layer Response body was - "+response.getBody().asString());		
		return response;
	}
	
	/**
	 * A method to call Post API of object layer
	 */

	public static Response postCallForObjectyLayer(RequestSpecification httpRequest,String consistency,String versionValue, String tagValue,String prettyValue, String systemLoc, String doctype, String docName,int expectedStatusCode,JSONObject payloadOBJ) {
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpecForObjectLayer();
		requestSpecification.headers("x-session-key",sessionKey);  
		requestSpecification.headers("Authorization", "Bearer " + bearerToken);
		httpRequest = RestAssured.given();
		String consistencyValue = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"consistency").getString(consistency);
		String includeVersions = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"IncludeVersions").getString(versionValue);
		String includeTags = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"IncludeTags").getString(tagValue);
		String pretty = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"Pretty").getString(prettyValue);
		String systemLocale = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"systemLocale").getString(systemLoc);
		String docType = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"doctype").getString(doctype);		
		
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"objectLayer").getString("postDocument").toString());	
		requestSpecification.body(payloadOBJ.toString());
		requestSpecification.pathParam("systemLocale", systemLocale);
		requestSpecification.pathParam("docType", docType);
		requestSpecification.queryParam("requestConsistency", consistencyValue);
		requestSpecification.queryParam("includeVersions", includeVersions);
		requestSpecification.queryParam("includeTags", includeTags);
		requestSpecification.queryParam("pretty", pretty);
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.post();
		Assert.assertEquals(expectedStatusCode, response.getStatusCode(),"Status Code mismatch found for post call in object layer. Expcected was - "+expectedStatusCode+" Received was - "+response.getStatusCode()+" Object layer Response body was - "+response.getBody().asString());		
		return response;
	}
	
	/**
	 * A method to call Post API of object layer ForByPassRules
	 */

	public static Response postCallForObjectyLayerForByPassRules(RequestSpecification httpRequest,String consistency,String versionValue, String tagValue,String prettyValue, String systemLoc, String doctype, String docName,int expectedStatusCode,JSONObject payloadOBJ) {
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpecForObjectLayer();
		requestSpecification.headers("x-session-key",bypassSessionKey);  
		requestSpecification.headers("Authorization", "Bearer " + bypassBearerToken);
		httpRequest = RestAssured.given();
		String consistencyValue = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"consistency").getString(consistency);
		String includeVersions = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"IncludeVersions").getString(versionValue);
		String includeTags = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"IncludeTags").getString(tagValue);
		String pretty = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"Pretty").getString(prettyValue);
		String systemLocale = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"systemLocale").getString(systemLoc);
		String docType = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"doctype").getString(doctype);		
		
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"objectLayer").getString("postDocument").toString());	
		requestSpecification.body(payloadOBJ.toString());
		requestSpecification.pathParam("systemLocale", systemLocale);
		requestSpecification.pathParam("docType", docType);
		requestSpecification.queryParam("requestConsistency", consistencyValue);
		requestSpecification.queryParam("includeVersions", includeVersions);
		requestSpecification.queryParam("includeTags", includeTags);
		requestSpecification.queryParam("pretty", pretty);
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.post();
		Assert.assertEquals(expectedStatusCode, response.getStatusCode(),"Status Code mismatch found for post call in object layer. Expcected was - "+expectedStatusCode+" Received was - "+response.getStatusCode()+" Object layer Response body was - "+response.getBody().asString());		
		return response;
	}
	
	/** 
	 * A Method to put call for object layer
	 **/

	public static Response PutCallAPIObjectLayer(RequestSpecification httpRequest,String consistency,String versionValue, String tagValue,String prettyValue,String systemLoc, String docName,String doctype,int expectedStatusCode, JSONObject payload,String lastModified) 
	{
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpecForObjectLayer();
		requestSpecification.headers("x-session-key",sessionKey);  
		requestSpecification.headers("Authorization", "Bearer " + bearerToken);
		httpRequest = RestAssured.given();
		String consistencyValue = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"consistency").getString(consistency);
		String includeVersions = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"IncludeVersions").getString(versionValue);
		String includeTags = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"IncludeTags").getString(tagValue);
		String pretty = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"Pretty").getString(prettyValue);
		String systemLocale = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"systemLocale").getString(systemLoc);
		String docType = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"doctype").getString(doctype);	
		
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"objectLayer").getString("putDocument").toString());	
		requestSpecification.body(payload.toString());
		requestSpecification.pathParam("systemLocale", systemLocale);
		requestSpecification.pathParam("docType", docType);
		requestSpecification.pathParam("docName", docName);
		requestSpecification.queryParam("requestConsistency", consistencyValue);
		requestSpecification.queryParam("includeVersions", includeVersions);
		requestSpecification.queryParam("includeTags", includeTags);
		requestSpecification.queryParam("pretty", pretty);
		requestSpecification.queryParam("lastModifiedSeen", lastModified);
		requestSpecification.body(payload.toString());	
		logger.info("Payload for put call of object layer with parameters - "+payload);
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.put();
		Assert.assertEquals(expectedStatusCode, response.getStatusCode(),"Status Code mismatch found for put call in object layer. Expcected was - "+expectedStatusCode+" Received was - "+response.getStatusCode()+" Object layer Response body was - "+response.getBody().asString());		
		return response;
	}
	
	/**
	 * A method to call Put API of object layer ForByPassRules
	 */
	
	public static Response PutCallAPIObjectLayerForByPassRules (RequestSpecification httpRequest,String consistency,String versionValue, String tagValue,String prettyValue,String systemLoc, String docName,String doctype,int expectedStatusCode, JSONObject payload,String lastModified) 
	{
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpecForObjectLayer();
		requestSpecification.headers("x-session-key",bypassSessionKey);  
		requestSpecification.headers("Authorization", "Bearer " + bypassBearerToken);
		httpRequest = RestAssured.given();
		String consistencyValue = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"consistency").getString(consistency);
		String includeVersions = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"IncludeVersions").getString(versionValue);
		String includeTags = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"IncludeTags").getString(tagValue);
		String pretty = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"Pretty").getString(prettyValue);
		String systemLocale = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"systemLocale").getString(systemLoc);
		String docType = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"doctype").getString(doctype);	
		
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"objectLayer").getString("putDocument").toString());	
		requestSpecification.body(payload.toString());
		requestSpecification.pathParam("systemLocale", systemLocale);
		requestSpecification.pathParam("docType", docType);
		requestSpecification.pathParam("docName", docName);
		requestSpecification.queryParam("requestConsistency", consistencyValue);
		requestSpecification.queryParam("includeVersions", includeVersions);
		requestSpecification.queryParam("includeTags", includeTags);
		requestSpecification.queryParam("pretty", pretty);
		requestSpecification.queryParam("lastModifiedSeen", lastModified);
		requestSpecification.body(payload.toString());	
		logger.info("Payload for put call of object layer with parameters - "+payload);
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.put();
		Assert.assertEquals(expectedStatusCode, response.getStatusCode(),"Status Code mismatch found for put call in object layer while running bypass Rules role test cases. Expcected was - "+expectedStatusCode+" Received was - "+response.getStatusCode()+" Object layer Response body was - "+response.getBody().asString());		
		return response;
	}
	
	/** 
	 * A Method to put call for object layer without parameters
	 **/

	public static Response PutCallAPIWithOutParamsObjectLayer(RequestSpecification httpRequest,String systemLoc, String docName,String doctype,int expectedStatusCode, JSONObject payload,String lastModified) 
	{
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpecForObjectLayer();
		requestSpecification.headers("x-session-key",sessionKey);  
		requestSpecification.headers("Authorization", "Bearer " + bearerToken);
		httpRequest = RestAssured.given();
		String systemLocale = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"systemLocale").getString(systemLoc);
		String docType = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"doctype").getString(doctype);	
		
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"objectLayer").getString("putDocumentWithoutParam").toString());	
		requestSpecification.body(payload.toString());
		requestSpecification.pathParam("systemLocale", systemLocale);
		requestSpecification.pathParam("docType", docType);
		requestSpecification.pathParam("docName", docName);
		requestSpecification.queryParam("lastModifiedSeen", lastModified);
		requestSpecification.queryParam("requestConsistency", "true");
		requestSpecification.body(payload.toString());	
		logger.info("Payload for put call of object layer without parameters - "+payload);
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.put();
		Assert.assertEquals(expectedStatusCode, response.getStatusCode(),"Status Code mismatch found for put call in object layer. Expcected was - "+expectedStatusCode+" Received was - "+response.getStatusCode()+" Object layer Response body was - "+response.getBody().asString());		
		return response;
	}
	
	
	public static Response PatchCallAPIObjectLayer(RequestSpecification httpRequest,String consistency,String preserveDocNameCase,String systemLoc, String docName,String doctype,int expectedStatusCode, JSONObject payload) 
	{
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpecForObjectLayer();
		if(expectedStatusCode==403) {
		requestSpecification.headers("x-session-key","wrong"); 	
		}
		else {
		requestSpecification.headers("x-session-key",sessionKey);  
		}
		requestSpecification.headers("Authorization", "Bearer " + bearerToken);
		httpRequest = RestAssured.given();
		String consistencyValue = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"consistency").getString(consistency);	
		String systemLocale = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"systemLocale").getString(systemLoc);
		String docType = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"doctype").getString(doctype);	
		
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"objectLayer").getString("patchDocument").toString());	
		requestSpecification.body(payload.toString());
		requestSpecification.pathParam("systemLocale", systemLocale);
		requestSpecification.pathParam("docType", docType);
		requestSpecification.pathParam("docName", docName);
		requestSpecification.queryParam("requestConsistency", consistencyValue);
		requestSpecification.body(payload.toString());	
		logger.info("Payload for patch call of object layer with parameters - "+payload);
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.patch();
		Assert.assertEquals(expectedStatusCode, response.getStatusCode(),"Status Code mismatch found for patch call in object layer. Expcected was - "+expectedStatusCode+" Received was - "+response.getStatusCode()+" Object layer Response body was - "+response.getBody().asString());		
		return response;
	}
	
	public static JSONObject createPayloadForPatchCallAPIObjectLayer(String parameter) 
	{
		JSONObject payload=null;
		
		if(parameter.equals("media")) {
			payload = CommonUtilityMethods.readJsonFile(Constants.objectlayerPatch,"mediaPayload"+CommonUtilityMethods.getEnvironment());
		}
		else if(parameter.equals("partyPermissionDefaults")) {
			payload = CommonUtilityMethods.readJsonFile(Constants.objectlayerPatch,"mediaPayload"+CommonUtilityMethods.getEnvironment());
		}
		
		else if (parameter.equals("rules"))
			payload=CommonUtilityMethods.readJsonFile(Constants.objectlayerPatch,"replacePayload"+CommonUtilityMethods.getEnvironment());
			
			else {
				logger.info("No valid paramter detected");
			}
		
		return payload;
	}
	
	
	public static JSONObject createOperation(String operationName,String parameter,Object value){
		
		JSONObject operation=new JSONObject();
		
		operation.put("op", operationName);
		operation.put("path", "/lmsObject/"+parameter);
		operation.put("value", value);
		return operation;
	}
	
	
	public static JSONObject createPatchPayload(JSONArray jsonToBeAdded ){
		
		JSONObject json=new JSONObject();
	
		json.put("lastUpdatedBy", "Patch API AUtomation Core alaya");
		json.put("patchDocument",jsonToBeAdded);
		
		
		return json;
	}
	
public static JSONArray addOperation(JSONObject json, String parameter,Object value ){
		
		JSONArray patch=json.getJSONArray("patchDocument");
		JSONObject itemArr=null;
		
		for (int i=0; i < patch.length(); i++){
		     itemArr = (JSONObject)patch.get(i);
		    if(itemArr.get("path").toString().equals(parameter)){
		        itemArr.put("value", value);
		    }
		}
		return patch;
	}
	
	/**
	 * A method to get wrong document for object layer
	 */
	public static String getWrongDocForObjectLayer() {
		String wrongDoc = null;
		String env = CommonUtilityMethods.getEnvironment();
		if(env.equals("t1")) {
		wrongDoc = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"docName").getString("tstwrong");
		}
		if(env.equals("d1")) {
		wrongDoc = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"docName").getString("devwrong");
		}
		return wrongDoc;
	}



	public static String verifyForbiddenInvalidSessionForGetAPIObject(RequestSpecification httpRequest, String systemLoc, String doctype, String docName)  {
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpecForObjectLayer();
		requestSpecification.headers("x-session-key","test-sessionkey");  
		requestSpecification.headers("Authorization", "Bearer " + bearerToken);
		httpRequest = RestAssured.given();
		String systemLocale = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"systemLocale").getString(systemLoc);
		String docType = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"doctype").getString(doctype);	
		String docuName = docName;		
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"objectLayer").getString("getDocument").toString());	
		requestSpecification.pathParam("systemLocale", systemLocale);
		requestSpecification.pathParam("docType", docType);
		requestSpecification.pathParam("docName", docuName);
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.get();
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.FORBIDDEN,"Status code mismatch found for get call in object layer. Expected was Forbidden, Found - "+response.getStatusCode());
		return response.asString();
	}
	
	

	public static String verifyForbiddenInvalidBearerTokenForGetAPIObject(RequestSpecification httpRequest, String systemLoc, String doctype, String docName)  {
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpecForObjectLayer();
		requestSpecification.headers("x-session-key",sessionKey);  
		String invalidToken=bearerToken.replace("e", "r");
		requestSpecification.headers("Authorization", invalidToken);
		httpRequest = RestAssured.given();
		String systemLocale = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"systemLocale").getString(systemLoc);
		String docType = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"doctype").getString(doctype);	
		String docuName = docName;		
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"objectLayer").getString("getDocument").toString());	
		requestSpecification.pathParam("systemLocale", systemLocale);
		requestSpecification.pathParam("docType", docType);
		requestSpecification.pathParam("docName", docuName);
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.get();
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.FORBIDDEN,"Status code mismatch found for get call in object layer. Expected was Forbidden, Found - "+response.getStatusCode());
		return response.asString();
	}
	

	public static String verifyForbiddenInvalidBearerTokenForPOSTAPIObject(RequestSpecification httpRequest, String systemLoc, String doctype,JSONObject payloadOBJ)  {
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpecForObjectLayer();
		requestSpecification.headers("x-session-key",sessionKey);  
		String invalidToken=bearerToken.replace("e", "r");
		requestSpecification.headers("Authorization", invalidToken);
		httpRequest = RestAssured.given();
		String systemLocale = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"systemLocale").getString(systemLoc);
		String docType = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"doctype").getString(doctype);		
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"objectLayer").getString("postDocument").toString());	
		requestSpecification.body(payloadOBJ.toString());
		requestSpecification.pathParam("systemLocale", systemLocale);
		requestSpecification.pathParam("docType", docType);
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.post();
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.FORBIDDEN,"Status code mismatch found for post call in object layer. Expected was Forbidden, Found - "+response.getStatusCode());
		return response.asString();
	}
	
	public static String verifyForbiddenInvalidSessionKeyForPOSTAPIObject(RequestSpecification httpRequest, String systemLoc, String doctype,JSONObject payloadOBJ)  {
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpecForObjectLayer();
		requestSpecification.headers("x-session-key","test-sessionkey");  
		requestSpecification.headers("Authorization", "Bearer " + bearerToken);
		httpRequest = RestAssured.given();
		String systemLocale = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"systemLocale").getString(systemLoc);
		String docType = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"doctype").getString(doctype);		
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"objectLayer").getString("postDocument").toString());	
		requestSpecification.body(payloadOBJ.toString());
		requestSpecification.pathParam("systemLocale", systemLocale);
		requestSpecification.pathParam("docType", docType);
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.post();
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.FORBIDDEN,"Status code mismatch found for post call in object layer. Expected was Forbidden, Found - "+response.getStatusCode());
		return response.asString();
	}
	
	
	/**
	 * A method to verify Replace Operation After Patch
	 */
	public static void verifyReplaceOperationAfterPatch(String getResponse, int changedValue, String changedField) {
		JSONObject getResponseObj = new JSONObject(getResponse);
		JSONObject result = getResponseObj.getJSONObject("content").getJSONObject("lmsObject");
		int field = (int) result.get(changedField);
		Assert.assertEquals(field, changedValue,"Replace operation on field - "+ field +" After replace operation in patch field did not change. Get call have value - "+field+" Patch changed value is - "+changedValue);	
	}
	
	
	/**
	 * A method to verify Replace Operation After Patch
	 */
	public static void verifyReplaceOperationAfterPatchTeams(String getResponse, String beforeTeamName) {
		JSONObject getResponseObj = new JSONObject(getResponse);
		JSONObject result = getResponseObj.getJSONObject("content").getJSONObject("lmsObject");
		String teamsName =result.getString("teamName");
		Assert.assertEquals(teamsName, beforeTeamName,"Team name after replace operation in patch did not change. Get call have Team Name - "+teamsName+" Patch changed Team Name is - "+beforeTeamName);	
	}
	
	
	/**
	 * A method to verify patch response structure
	 */
	public static void verifyPatchResponseStructure(Response patchResponse,String docName,String docType,String locale) {
		SoftAssert softAssert = new SoftAssert();
		ArrayList<String> apiInfoFields = new ArrayList<String>();
		ArrayList<String> locationFields = new ArrayList<String>();	
		ArrayList<String> ExpectedApiInfoFields = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.objectlayer, "apiInfo").get("fields"));
		ArrayList<String> ExpectedlocationFields = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.objectlayer, "location").get("fields"));
		JSONObject responseObj = new JSONObject(patchResponse.getBody().asString());	
		
		/*verifying API info here*/
		if(responseObj.has("apiInfo")) {
			JSONObject apiInfo = responseObj.getJSONObject("apiInfo");
			Iterator<String> Keys = apiInfo.keys();
			while (Keys.hasNext()) 
			{
			  String fieldValue = (String)Keys.next();
			  apiInfoFields.add(fieldValue);
			}
			softAssert.assertTrue(CollectionUtils.isEqualCollection(apiInfoFields, ExpectedApiInfoFields),"Fields inside API INFO mismatched for Patch API of object layer. Expected were "+ExpectedApiInfoFields+" Found were "+apiInfoFields);
		}
		else 
		softAssert.fail("API info object not found in response body for PUT call object layer for document name - "+docName+" and document type - "+docType);
		
		/*verifying document name here*/
		if(responseObj.has("documentName")) {
			String documntName = responseObj.get("documentName").toString();
			softAssert.assertEquals(docName, documntName , "Document name mismatch found in response. Expected was "+docName+" but found was "+docName);
		}
		else
		softAssert.fail("Document name object not found in response body for Patch API object layer for document name - "+docName+" and document type - "+docType);
		
		/*verifying location Object here*/
		if(responseObj.has("location")) {
		JSONObject location = responseObj.getJSONObject("location");
		Iterator<String> Keys = location.keys();
		while (Keys.hasNext()) 
		{
		  String fieldValue = (String)Keys.next();		  
		  locationFields.add(fieldValue);
		}
		
		softAssert.assertTrue(CollectionUtils.isEqualCollection(locationFields, ExpectedlocationFields),"Fields inside location mismatched for Patch API of object layer. Expected were "+ExpectedApiInfoFields+" Found were "+locationFields);
		String locationDocType = location.get("documentType").toString();
		String locationlocale = location.get("systemLocale").toString();
		softAssert.assertEquals(locationDocType,docType,"Document type in location mismatch. Expected was - "+docType+" found was - "+locationDocType);
		softAssert.assertEquals(locationlocale,"bright","System locale in location mismatch. Expected was - bright found was - "+locationlocale);
		
		}
	   else 
	   softAssert.fail("Location object not found in response body for Patch call object layer for document name - "+docName+" and document type - "+docType);
		
	   /*verifying size Object here*/
	   if(responseObj.has("size")) {	
			String size = responseObj.get("size").toString();
			if(size.length()== 0)
			softAssert.fail("size object was found empty");
	   }
	   else
	   softAssert.fail("size object not found in response body for Patch call object layer for document name - "+docName+" and document type - "+docType);
		
	   /*verifying last modified,tags,tag count, version id and versions Object is present or not*/
	   if(responseObj.has("lastModified") && responseObj.has("tagCount") && responseObj.has("tags") && responseObj.has("versionId") && responseObj.has("versions")) {
		   logger.info("last modified,tags,tag count, version id and versions Object found in Patch API call");  
	   }
	   else
	   softAssert.fail("last modified,tags,tag count,version id and versions Object not found in Patch API call");
	}
	
	/**
	 * A method to get listingId from listingid api
	 */
	public static String getListingId(RequestSpecification httpRequest) {
		SoftAssert softAssert = new SoftAssert();
		String listingid = null;
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.utilityServicesRequestSpec();
		httpRequest = RestAssured.given();
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"), "utilityLayer").getString("listingIdGenerator").toString());
		requestSpecification.queryParam("seqName", "VARO");
		httpRequest.spec(requestSpecification);
		Response response = httpRequest.get();
		String responseBody = response.getBody().asString();
		JSONObject resBodyObj = new JSONObject(responseBody).getJSONObject("content");
		listingid = resBodyObj.get("id").toString();
		softAssert.assertFalse(listingid.isEmpty(), "Listing id was not retireved from listing id generator api");
		softAssert.assertAll();
		return listingid;
	}
	
	/**
	 * A method to get deleted document for  object layer
	 */
	public static String getDeletedDocForObjectLayer(String docType) {
		String deletedDoc = null;
		String env = CommonUtilityMethods.getEnvironment();
		if(docType.equals("listing")){
		if(env.equals("t1")) {
		deletedDoc = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"docName").getString("deletedListing");
		}
		if(env.equals("d1")) {
		deletedDoc = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"docName").getString("deletedListingDev");
		}
		}
		if(docType.equals("office")){
		if(env.equals("t1")) {
		deletedDoc = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"docName").getString("deletedOffice");
		}
		if(env.equals("d1")) {
		deletedDoc = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"docName").getString("deletedOfficeDev");
		}
		}
		if(docType.equals("member")){
		if(env.equals("t1")) {
		deletedDoc = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"docName").getString("deletedMember");
		}
		if(env.equals("d1")) {
		deletedDoc = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"docName").getString("deletedMemberDev");
		}
		}
		if(docType.equals("countyrate")){
		if(env.equals("t1")) {
		deletedDoc = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"docName").getString("deletedCountyrate");
		}
		if(env.equals("d1")) {
		deletedDoc = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"docName").getString("deletedCountyrateDev");
		}
		}
		if(docType.equals("taxRecord")){
		if(env.equals("t1")) {
		deletedDoc = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"docName").getString("deletedTaxrecord");
		}
		if(env.equals("d1")) {
		deletedDoc = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"docName").getString("deletedTaxrecordDev");
		}
		}
		if(docType.equals("building_name")){
		if(env.equals("t1")) {
		deletedDoc = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"docName").getString("deletedbuildingName");
		}
		if(env.equals("d1")) {
		deletedDoc = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"docName").getString("deletedbuildingNameDev");
		}
		}
		if(docType.equals("builder_model")){
		if(env.equals("t1")) {
		deletedDoc = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"docName").getString("deletedbuilderModel");
		}
		if(env.equals("d1")) {
		deletedDoc = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"docName").getString("deletedbuilderModelDev");
		}
		}
		
		if(docType.equals("subdivision")){
			if(env.equals("t1")) {
			deletedDoc = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"docName").getString("deletedSubdivision");
			}
			if(env.equals("d1")) {
			deletedDoc = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"docName").getString("deletedSubdivisionDev");
			}
			}
		
		if(docType.equals("city")){
			if(env.equals("t1")) {
			deletedDoc = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"docName").getString("deletedCity");
			}
			if(env.equals("d1")) {
			deletedDoc = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"docName").getString("deletedCityDev");
			}
			}
		
		if(docType.equals("team")) {
			if(env.equals("t1")) {
				deletedDoc = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"docName").getString("deletedTeam");
				}
				if(env.equals("d1")) {
				deletedDoc = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"docName").getString("deletedTeamDev");
				}	
		}
		
		
		return deletedDoc;
		
	}

}
