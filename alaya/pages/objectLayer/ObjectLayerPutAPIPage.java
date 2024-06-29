package bright.api.alaya.pages.objectLayer;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

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
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ObjectLayerPutAPIPage extends MainClassAlaya {

	private static final String Locale = "Bright" ;
	private static final String True = "true" ;
	
	public static String modifyPayloadForMemberRules(String response,JSONObject rulesData) {
		JSONObject responseObj = new JSONObject(response);
		JSONObject contentObj = responseObj.getJSONObject("content");
		JSONObject lmsObj = contentObj.getJSONObject("lmsObject");

		if(rulesData.get("city").equals(null)) { 
			lmsObj.put("memberCity",JSONObject.NULL); }
		else { 
			lmsObj.put("memberCity",rulesData.get("city").toString()); }
		if(rulesData.get("state").equals(null)) { 
			lmsObj.put("memberStateOrProvince",JSONObject.NULL); }
		else {	
		    lmsObj.put("memberStateOrProvince",new BigInteger(rulesData.get("state").toString())); }
		if(rulesData.get("county").equals(null)) {
			lmsObj.put("memberCounty",JSONObject.NULL);}
		else {	
			lmsObj.put("memberCounty",new BigInteger(rulesData.get("county").toString())); }
		
		lmsObj.put("memberSourceBusinessPartner",new BigInteger(rulesData.get("SourceBusinessPartner").toString()));	
		contentObj.put("lmsObject", lmsObj);
		responseObj.put("content", contentObj);
		return responseObj.toString();
	}
	
	public static String modifyPayloadForOfficeRules(String response,JSONObject rulesData) {
		JSONObject responseObj = new JSONObject(response);
		JSONObject contentObj = responseObj.getJSONObject("content");
		JSONObject lmsObj = contentObj.getJSONObject("lmsObject");
		
		if(!rulesData.get("city").equals(null)) {
			((String) rulesData.get("city")).toUpperCase();
		}
		if(rulesData.get("state").equals(null)) 
			lmsObj.put("officeStateOrProvince",JSONObject.NULL);
		else
			lmsObj.put("officeStateOrProvince",new BigInteger(rulesData.get("state").toString()));
		if(rulesData.get("city").equals(null)) 
			lmsObj.put("officeCity",JSONObject.NULL);
		else
			lmsObj.put("officeCity",rulesData.get("city").toString());
		if(rulesData.get("county").equals(null)) 
			lmsObj.put("officeCounty",JSONObject.NULL);
		else
			lmsObj.put("officeCounty",new BigInteger(rulesData.get("county").toString()));

		lmsObj.put("officeSourceBusinessPartner",new BigInteger(rulesData.get("SourceBusinessPartner").toString()));

		lmsObj.put("memberSourceBusinessPartner",new BigInteger(rulesData.get("SourceBusinessPartner").toString()));	
		contentObj.put("lmsObject", lmsObj);
		responseObj.put("content", contentObj);
		return responseObj.toString();
	}
	
	/** 
	 * A Method to verify response structure for put call of object layer
	 **/

	public static void verifyResponsePutCallAPIObjectLayer(Response response,String docName,String docType,String parameterCondition) 
	{
		SoftAssert softAssert = new SoftAssert();
		ArrayList<String> apiInfoFields = new ArrayList<String>();
		ArrayList<String> locationFields = new ArrayList<String>();	
		ArrayList<String> ExpectedApiInfoFields = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.objectlayer, "apiInfo").get("fields"));
		ArrayList<String> ExpectedlocationFields = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.objectlayer, "location").get("fields"));
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
			softAssert.assertTrue(CollectionUtils.isEqualCollection(apiInfoFields, ExpectedApiInfoFields),"Fields inside API INFO mismatched for PUT API of object layer. Expected were "+ExpectedApiInfoFields+" Found were "+apiInfoFields);
		}
		else 
		softAssert.fail("API info object not found in response body for PUT call object layer for document name - "+docName+" and document type - "+docType);
		
		/*verifying document name here*/
		if(responseObj.has("documentName")) {
			String documntName = responseObj.get("documentName").toString();
			softAssert.assertEquals(docName, documntName , "Document name mismatch found in response. Expected was "+docName+" but found was "+docName);
		}
		else
		softAssert.fail("Document name object not found in response body for PUT API object layer for document name - "+docName+" and document type - "+docType);
		
		/*verifying location Object here*/
		if(responseObj.has("location")) {
		JSONObject location = responseObj.getJSONObject("location");
		Iterator<String> Keys = location.keys();
		while (Keys.hasNext()) 
		{
		  String fieldValue = (String)Keys.next();		  
		  locationFields.add(fieldValue);
		}
		
		softAssert.assertTrue(CollectionUtils.isEqualCollection(locationFields, ExpectedlocationFields),"Fields inside location mismatched for PUT API of object layer. Expected were "+ExpectedApiInfoFields+" Found were "+locationFields);
		String locationDocType = location.get("documentType").toString();
		String locationlocale = location.get("systemLocale").toString();
		softAssert.assertEquals(locationDocType,docType,"Document type in location mismatch. Expected was - "+docType+" found was - "+locationDocType);
		softAssert.assertEquals(locationlocale,"bright","System locale in location mismatch. Expected was - bright found was - "+locationlocale);
		
		}
	   else 
	   softAssert.fail("Location object not found in response body for PUT call object layer for document name - "+docName+" and document type - "+docType);
		
	   /*verifying size Object here*/
	   if(responseObj.has("size")) {	
			String size = responseObj.get("size").toString();
			if(size.length()== 0)
			softAssert.fail("size object was found empty");
	   }
	   else
	   softAssert.fail("size object not found in response body for PUT call object layer for document name - "+docName+" and document type - "+docType);
		
	   
	   /*verifying last modified,tags,tag count, version id and versions Object here for parameters given*/
	   if(parameterCondition.equalsIgnoreCase("parameterTrue")) {
	   if(responseObj.has("lastModified") && responseObj.has("tagCount") && responseObj.has("tags") && responseObj.has("versionId") && responseObj.has("versions")) {	
		logger.info("last modified,tags,tag count, version id and versions Object found in PUT API call");
		JSONArray tags = responseObj.getJSONArray("tags");
		if(tags.length()==0) {
			softAssert.fail("Tags array was empty even after tags parameter was true");
		}
		JSONObject versions = responseObj.getJSONObject("versions");
		JSONObject versionHistory = (JSONObject) versions.getJSONArray("versionHistory").get(0);
		String versionIdInside = versionHistory.get("versionId").toString();
		String versionIdOutside = responseObj.get("versionId").toString();
	    softAssert.assertEquals(versionIdInside, versionIdOutside,"Versions ids from response and version history did not match");
	    softAssert.assertEquals(versionHistory.get("isFirst"), false,"Version created via Put call is the first version");
	    softAssert.assertEquals(versionHistory.get("isLatest"), true,"Version created via Put call is not the latest version");	    
	   }
	   else
	   softAssert.fail("last modified,tags,tag count, version id and versions Object not found in response body for PUT call object layer for document name - "+docName+" and document type - "+docType);
	  	
	   }
	   
	   /*verifying last modified,tags,tag count, version id and versions Object here for no parameters given*/
	   if(parameterCondition.equalsIgnoreCase("noParameter")) {
	   if(responseObj.has("lastModified") && responseObj.has("tagCount") && responseObj.has("tags") && responseObj.has("versionId") && responseObj.has("versions")) {	
		logger.info("last modified,tags,tag count, version id and versions Object found in PUT API call");
	   }
	   else
	   softAssert.fail("last modified,tags,tag count, version id and versions Object not found in response body for POST call object layer for document name - "+docName+" and document type - "+docType);
	   }	
		softAssert.assertAll();	
		
	}
	
	/** 
	 * A Method to edit payload for put call for object layer
	 **/
    public static JSONObject editPayloadForPutCall(String docType, JSONObject contentObj) {
    	JSONObject lmsObj = contentObj.getJSONObject("lmsObject");
    	
    	/*Changing list price in listing to do put call*/
		if(docType.equalsIgnoreCase("listing")) {
			Random random = new Random();
			String randomListPrice = "500"+String.format("%04d", random.nextInt(10000));
			lmsObj.putOpt("listPrice", Long.parseLong(randomListPrice));
		}
		/*Changing list price in member to do put call*/
		if(docType.equalsIgnoreCase("member")) {
			Random random = new Random();
			String prefferedPhone = "234561"+String.format("%04d", random.nextInt(10000));
			lmsObj.putOpt("memberPreferredPhone", prefferedPhone);
		}
		/*Changing team name in team to do put call*/
		if(docType.equalsIgnoreCase("team")) {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
			LocalDateTime now = LocalDateTime.now();  
			String teamName = "CoreAlayaAutomationTeam_"+lmsObj.get("teamKey")+"_"+dtf.format(now);
			lmsObj.putOpt("teamName", teamName);
		}
		
		contentObj.put("lmsObject", lmsObj);
		return contentObj;
    }
	
	
	/** 
	 * A Method to verify success for put call for object layer
	 **/

	public static void verifySuccessPutCallAPIObjectLayer(RequestSpecification httpRequest, String response,String docName,String docType,String parameterCondition) 
	{
        Response responseAfterPutWithParam = null;
        Response responseAfterPutWithoutParam = null;
		JSONObject responseObj = new JSONObject(response);
		JSONObject contentObj = responseObj.getJSONObject("content");
		JSONObject payload = new JSONObject();
		payload.putOpt("content", contentObj);
		payload.putOpt("lastUpdatedBy", "core Alaya qa automation");	
		String lastModified = new JSONObject(response).getJSONObject("metadata").get("lastModified").toString();
		contentObj = editPayloadForPutCall(docType,contentObj);
		payload.put("content", contentObj);
		logger.info(payload);
		if(parameterCondition.equalsIgnoreCase("parameterTrue")) {
		responseAfterPutWithParam = ObjectLayerPage.PutCallAPIObjectLayer(httpRequest,True,True,True,True,Locale,docName,docType,ResponseCodes.SUCCESS,payload,lastModified);
		logger.info("Response from put call of object layer with parameters - "+responseAfterPutWithParam.getBody().asString());
		verifyResponsePutCallAPIObjectLayer(responseAfterPutWithParam,docName,docType,"parameterTrue");
		}
		else if(parameterCondition.equalsIgnoreCase("noParameter")) {
		String getResponseAfterPut = ObjectLayerPage.getCallForObjectyLayer(httpRequest,Locale,docType,docName,ResponseCodes.SUCCESS).getBody().asString();	
		String lastModifiedAfterPut =  new JSONObject(getResponseAfterPut).getJSONObject("metadata").get("lastModified").toString();
		responseAfterPutWithoutParam = ObjectLayerPage.PutCallAPIWithOutParamsObjectLayer(httpRequest,Locale,docName,docType,ResponseCodes.SUCCESS,payload,lastModifiedAfterPut);
		logger.info("Response from put call of object layer without parameters - "+responseAfterPutWithoutParam.getBody().asString());
		verifyResponsePutCallAPIObjectLayer(responseAfterPutWithoutParam,docName,docType,"noParameter");
		}
		logger.info("Verified the Success Status for POST API of object layer for document type - "+docType);
	
	}
	
	/**
	 * A method to verify attributes matching when PUT API is successfully sent
	 */
	public static void verifyattributesforPUTAPIObjectLayer(RequestSpecification httpRequest,String docType, String docName,ArrayList<String> AttributeNamesForDocStore, ArrayList<String> AttributeNamesForES) {
		JSONObject attributeResponseBody = new JSONObject();
		logger.info("Object layer Working on document - "+ docName);
		if(Strings.isNullOrEmpty(docName)) {
			Assert.fail("No valid document found for document type - "+ docType);
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
		Assert.assertEquals(AttributesFromDocStore.equals(AttributesFromES), true,"Object layer Put Test case : Doc store attributes did not match with ES attributes for document type and doc name - "+docType+"/"+docName+ " Doc store attributes are - "+AttributesFromDocStore+ " ES attributes are - "+AttributesFromES);
		logger.info("Object layer Put Test case : Verified attributes from doc store are matching elastic search in PUT API of object layer for document type - "+docType);
	}

}
