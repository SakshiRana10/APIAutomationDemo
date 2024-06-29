package bright.api.alaya.pages.discoveryLayer;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.asserts.SoftAssert;

import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class discoveryLayerUtilityPage extends MainClassAlaya{

	/**
	 * A method to call get API of discovery layer
	 */

	public static Response getCallForDiscoveryLayer(RequestSpecification httpRequest, String systemLoc, String versionValue, String prettyValue, String doctype, String docName) {
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpecForDiscovery();
		httpRequest = RestAssured.given();
		String systemLocale = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"systemLocale").getString(systemLoc);
		String includeVersions = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"IncludeVersions").getString(versionValue);
		String pretty = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"Pretty").getString(prettyValue);
		String docType = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"doctype").getString(doctype);	
		String docuName = docName;		
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"discoveryLayer").getString("getDocument").toString());	
		requestSpecification.pathParam("systemLocale", systemLocale);
		requestSpecification.pathParam("docType", docType);
		requestSpecification.pathParam("docName", docuName);
		requestSpecification.queryParam("includeVersions", includeVersions);
		requestSpecification.queryParam("pretty", pretty);
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.get();
		return response;
	}

	/**
	 * A method to call get API of discovery layer when include versions is not given
	 */

	public static Response getCallForDiscoveryLayerIncludeVersions(RequestSpecification httpRequest,String systemLoc, String prettyValue, String doctype, String docName){
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpecForDiscovery();
		httpRequest = RestAssured.given();
		String systemLocale = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"systemLocale").getString(systemLoc);
		String pretty = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"Pretty").getString(prettyValue);
		String docType = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"doctype").getString(doctype);
		String docuName = docName;	
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"discoveryLayer").getString("getDocumentIncludeVersion").toString());	
		requestSpecification.pathParam("systemLocale", systemLocale);
		requestSpecification.pathParam("docType", docType);
		requestSpecification.pathParam("docName", docuName);
		requestSpecification.queryParam("pretty", pretty);	
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.get();
		logger.info("Response from get call Include versions of discovery layer for document name "+ docuName +" is - "+response.getBody().asString());
		return response;
	}


	/**
	 * A method to verify the Forbidden error for GetAPI for discovery Layer
	 */

	public static Response verifyForbiddenForGetAPIDiscovery(RequestSpecification httpRequest, String systemLoc, String versionValue, String prettyValue, String doctype, String docName) {
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpecForDiscovery();
		httpRequest = RestAssured.given();
		String systemLocale = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"systemLocale").getString(systemLoc);
		String includeVersions = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"IncludeVersions").getString(versionValue);
		String pretty = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"Pretty").getString(prettyValue);
		String docType = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"doctype").getString(doctype);	
		String docuName = docName;		
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"discoveryLayer").getString("getDocument").toString());
		requestSpecification.pathParam("systemLocale", systemLocale);
		requestSpecification.pathParam("docType", docType);
		requestSpecification.pathParam("docName", docuName);
		requestSpecification.queryParam("includeVersions", includeVersions);
		requestSpecification.queryParam("pretty", pretty);
		requestSpecification.headers("x-api-key","check");                 //passing invalid value for x-api-key
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.get();
		logger.info("Response from forbidden get call of discovery layer for document name "+ docuName +" is - "+response.getBody().asString());
		return response;
	}

	/**
	 * A method to call put API of discovery layer
	 */

	public static Response putCallForDiscoveryLayer(RequestSpecification httpRequest,String consistency, String versionValue, String prettyValue, String systemLoc, String doctype, String docName, String lastModified, String payload){
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpecForDiscovery();
		httpRequest = RestAssured.given();
		String consistencyValue = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"consistency").getString(consistency);
		String includeVersions = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"IncludeVersions").getString(versionValue);
		String pretty = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"Pretty").getString(prettyValue);
		String systemLocale = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"systemLocale").getString(systemLoc);
		String docType = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"doctype").getString(doctype);	
		String docuName = docName;	
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"discoveryLayer").getString("putDocument").toString());	
		requestSpecification.pathParam("systemLocale", systemLocale);
		requestSpecification.pathParam("docType", docType);
		requestSpecification.pathParam("docName", docuName);
		requestSpecification.queryParam("requestConsistency", consistencyValue);
		requestSpecification.queryParam("includeVersions", includeVersions);
		requestSpecification.queryParam("pretty", pretty);
		requestSpecification.queryParam("lastModifiedSeen", lastModified);
		requestSpecification.body(payload);	
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.put();
		return response;
	}

	/**
	 * A method to call put API of discovery layer when include versions is not given
	 */

	public static Response putCallForDiscoveryLayerIncludeVersion(RequestSpecification httpRequest, String consistency, String prettyValue, String systemLoc, String doctype, String docName, String lastModified, String payload){
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpecForDiscovery();
		httpRequest = RestAssured.given();
		String consistencyValue = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"consistency").getString(consistency);
		String pretty = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"Pretty").getString(prettyValue);
		String systemLocale = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"systemLocale").getString(systemLoc);
		String docType = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"doctype").getString(doctype);	
		String docuName = docName;		
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"discoveryLayer").getString("putDocumentIncludeVersion").toString());	
		requestSpecification.pathParam("systemLocale", systemLocale);
		requestSpecification.pathParam("docType", docType);
		requestSpecification.pathParam("docName", docuName);
		requestSpecification.queryParam("requestConsistency", consistencyValue);
		requestSpecification.queryParam("pretty", pretty);
		requestSpecification.queryParam("lastModifiedSeen", lastModified);
		requestSpecification.body(payload);	
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.put();
		return response;

	}

	/**
	 * A method to call put API of discovery layer when include tags is not given
	 */

	public static Response putCallForDiscoveryLayerIncludeTags(RequestSpecification httpRequest,String consistency, String versionValue, String prettyValue, String systemLoc, String doctype, String docName, String lastModified, String payload){
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpecForDiscovery();
		httpRequest = RestAssured.given();
		String consistencyValue = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"consistency").getString(consistency);
		String pretty = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"Pretty").getString(prettyValue);
		String systemLocale = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"systemLocale").getString(systemLoc);
		String docType = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"doctype").getString(doctype);	
		String docuName = docName;	
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"discoveryLayer").getString("putDocumentIncludeTags").toString());	
		requestSpecification.pathParam("systemLocale", systemLocale);
		requestSpecification.pathParam("docType", docType);
		requestSpecification.pathParam("docName", docuName);
		requestSpecification.queryParam("requestConsistency", consistencyValue);
		requestSpecification.queryParam("pretty", pretty);
		requestSpecification.queryParam("lastModifiedSeen", lastModified);
		requestSpecification.body(payload);
		httpRequest.spec(requestSpecification);
		Response  response =httpRequest.put();
		logger.info("Response from put call include tags of discovery layer for document name "+ docuName +" is - "+response.getBody().asString());
		return response;

	}

	/**
	 * A method to call put API of discovery layer when last modified is not given
	 */

	public static Response putCallForDiscoveryLayerLastModified(RequestSpecification httpRequest, String consistency, String versionValue, String prettyValue, String systemLoc, String doctype, String docName, String payload){
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpecForDiscovery();
		httpRequest = RestAssured.given();
		String consistencyValue = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"consistency").getString(consistency);
		String includeVersions = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"IncludeVersions").getString(versionValue);
		String pretty = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"Pretty").getString(prettyValue);
		String systemLocale = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"systemLocale").getString(systemLoc);
		String docType = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"doctype").getString(doctype);	
		String docuName = docName;
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"discoveryLayer").getString("putDocumentLastModified").toString());	
		requestSpecification.pathParam("systemLocale", systemLocale);
		requestSpecification.pathParam("docType", docType);
		requestSpecification.pathParam("docName", docuName);
		requestSpecification.queryParam("requestConsistency", consistencyValue);
		requestSpecification.queryParam("includeVersions", includeVersions);
		requestSpecification.queryParam("pretty", pretty);
		requestSpecification.body(payload);
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.put();
		logger.info("Response from put call last modified of discovery layer for document name "+ docuName +" is - "+response.getBody().asString());
		return response;	
	}
	
	/*A method to call post API of discovery layer*/
	
	public static Response callPostOfDiscoveryLayer(RequestSpecification httpRequest,String docuType,String docName,String consistency,String versionValue,String prettyValue, String systemLoc, int expectedCode, JSONObject payloadOBJ) {
		SoftAssert softAssert = new SoftAssert();
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpecForDiscovery();
		httpRequest = RestAssured.given();
		String consistencyValue = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"consistency").getString(consistency);
		String includeVersions = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"IncludeVersions").getString(versionValue);
		String pretty = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"Pretty").getString(prettyValue);
		String systemLocale = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"systemLocale").getString(systemLoc);
		String docType = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"doctype").getString(docuType);	
		String docuName = docName;	
		String payloadForPost = payloadOBJ.toString();		
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"discoveryLayer").getString("postDocument").toString());	
		requestSpecification.body(payloadForPost);
		requestSpecification.pathParam("systemLocale", systemLocale);
		requestSpecification.pathParam("docType", docType);
		requestSpecification.queryParam("requestConsistency", consistencyValue);
		requestSpecification.queryParam("includeVersions", includeVersions);
		requestSpecification.queryParam("pretty", pretty);
		requestSpecification.queryParam("preserveDocNameCase", true);
		httpRequest.spec(requestSpecification);
		Response  responseFromPost = httpRequest.post();
		logger.info("Response from post call of discovery layer for document name "+ docuName +" is - "+responseFromPost.getBody().asString());
		softAssert.assertEquals(expectedCode, responseFromPost.getStatusCode(), "Success code was not received for POST api of discovery for doc type - "+docType+" Code receieved was - "+responseFromPost.getStatusCode());

		softAssert.assertAll();
		return responseFromPost;
	}

	/**
	 * A method to verify the Forbidden error for Put API for discovery Layer
	 */

	public static Response verifyForbiddenForPutAPIDiscovery(RequestSpecification httpRequest, String consistency, String versionValue, String prettyValue, String systemLoc, String doctype, String docName, String lastModified, String payload) {
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpecForDiscovery();
		httpRequest = RestAssured.given();
		String consistencyValue = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"consistency").getString(consistency);
		String includeVersions = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"IncludeVersions").getString(versionValue);
		String pretty = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"Pretty").getString(prettyValue);
		String systemLocale = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"systemLocale").getString(systemLoc);
		String docType = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"doctype").getString(doctype);	
		String docuName = docName;	
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"discoveryLayer").getString("putDocument").toString());	
		requestSpecification.pathParam("systemLocale", systemLocale);
		requestSpecification.pathParam("docType", docType);
		requestSpecification.pathParam("docName", docuName);
		requestSpecification.queryParam("requestConsistency", consistencyValue);
		requestSpecification.queryParam("includeVersions", includeVersions);
		requestSpecification.queryParam("pretty", pretty);
		requestSpecification.queryParam("lastModifiedSeen", lastModified);
		requestSpecification.headers("x-api-key","check");                 //passing invalid value for x-api-key
		requestSpecification.body(payload);	
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.put();
		logger.info("Response from forbidden put call of discovery layer for document name "+ docuName +" is - "+response.getBody().asString());
		return response;
	}
	
	
    /*A method to call forbidden post API of discovery layer*/
	
	public static Response callForbiddenPostOfDiscoveryLayer(RequestSpecification httpRequest,String docuType,String docName,String consistency,String versionValue,String prettyValue, String systemLoc, int expectedCode, JSONObject payloadOBJ) {
		SoftAssert softAssert = new SoftAssert();
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpecForDiscovery();
		httpRequest = RestAssured.given();	
		String consistencyValue = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"consistency").getString(consistency);
		String includeVersions = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"IncludeVersions").getString(versionValue);
		String pretty = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"Pretty").getString(prettyValue);
		String systemLocale = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"systemLocale").getString(systemLoc);
		String docType = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"doctype").getString(docuType);	
		String docuName = docName;	
		String payloadForPost = payloadOBJ.toString();		
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"discoveryLayer").getString("postDocument").toString());	
		requestSpecification.body(payloadForPost);
		requestSpecification.pathParam("systemLocale", systemLocale);
		requestSpecification.pathParam("docType", docType);
		requestSpecification.queryParam("requestConsistency", consistencyValue);
		requestSpecification.queryParam("includeVersions", includeVersions);
		requestSpecification.queryParam("pretty", pretty);
		requestSpecification.headers("x-api-key","check");                 //passing invalid value for x-API-key
		httpRequest.spec(requestSpecification);		
		/*Calling post API and verifying status code*/
		Response  responseFromPost = httpRequest.post();
		logger.info("Response from forbidden post call include tags of discovery layer for document name "+ docuName +" is - "+responseFromPost.getBody().asString());
		softAssert.assertEquals(expectedCode, responseFromPost.getStatusCode(), "Success code was not received for POST api of discovery for doc type - "+docType+" Code receieved was - "+responseFromPost.getStatusCode());
		softAssert.assertAll();
		return responseFromPost;
	}
	
	/**
	 * A method to get wrong document for discovery layer
	 */
	public static String getWrongDocForDiscoveryLayer() {
		String wrongDoc = null;
		String env = CommonUtilityMethods.getEnvironment();
		if(env.equals("t1")) {
		wrongDoc = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"docName").getString("tstwrong");
		}
		if(env.equals("d1")) {
		wrongDoc = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"docName").getString("devwrong");
		}
		return wrongDoc;
	}
	
	/**
	 * A method to get deleted document for discovery layer
	 */
	public static String getDeletedDocForDiscoveryLayer(String docType) {
		String deletedDoc = null;
		String env = CommonUtilityMethods.getEnvironment();
		if(docType.equals("listing")){
		if(env.equals("t1")) {
		deletedDoc = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"docName").getString("deletedListing");
		}
		if(env.equals("d1")) {
		deletedDoc = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"docName").getString("deletedListingDev");
		}
		}
		if(docType.equals("office")){
		if(env.equals("t1")) {
		deletedDoc = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"docName").getString("deletedOffice");
		}
		if(env.equals("d1")) {
		deletedDoc = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"docName").getString("deletedOfficeDev");
		}
		}
		if(docType.equals("member")){
		if(env.equals("t1")) {
		deletedDoc = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"docName").getString("deletedMember");
		}
		if(env.equals("d1")) {
		deletedDoc = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"docName").getString("deletedMemberDev");
		}
		}
		if(docType.equals("countyrate")){
		if(env.equals("t1")) {
		deletedDoc = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"docName").getString("deletedCountyrate");
		}
		if(env.equals("d1")) {
		deletedDoc = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"docName").getString("deletedCountyrateDev");
		}
		}
		if(docType.equals("taxrecord")){
		if(env.equals("t1")) {
		deletedDoc = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"docName").getString("deletedTaxrecord");
		}
		if(env.equals("d1")) {
		deletedDoc = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"docName").getString("deletedTaxrecordDev");
		}
		}
		if(docType.equals("building_name")){
		if(env.equals("t1")) {
		deletedDoc = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"docName").getString("deletedbuildingName");
		}
		if(env.equals("d1")) {
		deletedDoc = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"docName").getString("deletedbuildingNameDev");
		}
		}
		if(docType.equals("builder_model")){
		if(env.equals("t1")) {
		deletedDoc = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"docName").getString("deletedbuilderModel");
		}
		if(env.equals("d1")) {
		deletedDoc = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"docName").getString("deletedbuilderModelDev");
		}
		}
		
		if(docType.equals("subdivision")){
			if(env.equals("t1")) {
			deletedDoc = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"docName").getString("deletedSubdivision");
			}
			if(env.equals("d1")) {
			deletedDoc = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"docName").getString("deletedSubdivisionDev");
			}
			}
		
		if(docType.equals("city")){
			if(env.equals("t1")) {
			deletedDoc = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"docName").getString("deletedCity");
			}
			if(env.equals("d1")) {
			deletedDoc = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"docName").getString("deletedCityDev");
			}
			}
		
		
		return deletedDoc;
		
	}
	
	public static String getTagValueFromResponse(String response) {

		String versionId=null;
		JSONObject result=new JSONObject(response);
		JSONArray jsonArray=result.getJSONArray("tags");
		JSONObject tag=(JSONObject) jsonArray.get(0);
		String tagName=tag.getString("Key");
		String tagValue=tag.getString("Value");
		
		return tagValue;
	}
	

	public static String getVersionIdFromPutAPI(String response) {

		String versionId=null;
		JSONObject result=new JSONObject(response);
		versionId=result.getString("versionId");	
		return versionId;
	}
	
	
	public static String getLastModified(String response) {

		String lastModified=null;
		JSONObject result=new JSONObject(response);
		lastModified=result.getString("lastModified");	
		return lastModified;
	}


	public static String getVersionIdFromGetAPI(String response) {

		String versionId=null;
		JSONObject result=new JSONObject(response);
		JSONObject metaData = result.getJSONObject("metadata");
		versionId=metaData.getString("versionId");
		return versionId;

	}
	
	
	
	public static ArrayList<String> getlatestVersionIDs(String response) {

		ArrayList<String> versionIds = new ArrayList<>();
		JSONObject result=new JSONObject(response);
		JSONObject versions = (JSONObject) result.get("versions");
		JSONArray recs = versions.getJSONArray("versionHistory");
		//Fetching 2 version ids
		for (int i = 0; i < 2; i++) {
		    JSONObject rec = recs.getJSONObject(i);
		    logger.info("version Arry: "+i+rec.toString());
		    String version = rec.getString("versionId");
		    versionIds.add(version);
		    logger.info("test");
		}
		return versionIds;
	}
	
	public static ArrayList<String> getVersionIDForPost(String response) {
		ArrayList<String> versionIds = new ArrayList<>();
		JSONObject result=new JSONObject(response);
		JSONObject versions = (JSONObject) result.get("versions");
		JSONArray recs = versions.getJSONArray("versionHistory");
		for (int i = 0; i < 1; i++) {
		    JSONObject rec = recs.getJSONObject(i);
		    String version = rec.getString("versionId");
		    versionIds.add(version);
		}
		return versionIds;
	}
	
	
	
	public static boolean versionIdHistoryMatch(ArrayList<String> versionIds, String getVersionId,String putVersionID) {
		
		boolean flag = false;
		if(versionIds.contains(getVersionId) && versionIds.contains(putVersionID)) {
			logger.info("Version ids exist in Version History.");
			flag=true;
		}
		else
			logger.info("Version ids does not match woth version history");
		return flag;
	}

	
	
}