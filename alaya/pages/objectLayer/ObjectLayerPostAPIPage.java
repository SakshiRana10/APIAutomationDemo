package bright.api.alaya.pages.objectLayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import org.testng.util.Strings;

import bright.api.alaya.pages.discoveryLayer.discoveryLayerPostAPIPage;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ObjectLayerPostAPIPage extends MainClassAlaya {

	private static final String Locale = "Bright" ;
	private static final String True = "true" ;
	private static final String region = CommonUtilityMethods.getRegionString();
	
	/**
	 * A method to get office data based on member MLS id
	 **/
	 public static JSONArray getofficeDetsForMemberMLSId(String memberMlsId,ArrayList<String> returnFields) {
		 JSONArray officeData = CommonUtilityMethods.fetchGraphQlOfficeResponseUsingMemberMlsID("member",memberMlsId,"getMembers",returnFields);
	     return officeData;
	 }
	
	/** 
	 * A Method to change listOfficeBrokerOfRecordKey,listOffice key, MLS id, name and phone for post call for object layer in listing 
	 **/


	public static JSONObject putlistOfficeBrokerKey(String documentName,JSONObject lmsObj) 
	{
		ArrayList<String> returnField = new ArrayList<String>(Arrays.asList("memberKey"));
		ArrayList<String> returnFieldForOfficeData = new ArrayList<String>(Arrays.asList("officeKey","officeMlsId","officeName","memberOfficePhone"));



		String listOfficeBrokerKey = CommonUtilityMethods.fetchGraphQlResponseUsingMemberMlsID("member",property.getProperty("UserName"),"getMembers",returnField);
		JSONArray officeData = getofficeDetsForMemberMLSId(property.getProperty("UserName"),returnFieldForOfficeData);
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

	public static JSONObject payloadForMember(String docName,JSONObject lmsObj) {
		RequestSpecification httpRequest = null;
		ArrayList<String> returnFieldForOfficeData = new ArrayList<String>(Arrays.asList("officeKey","officeMlsId","officeName","memberOfficePhone"));
		ArrayList<String> roleList = new ArrayList<String>(Arrays.asList("Status Active","Realtor/Non Shareholder","System-BRIGHT","Agent"));
		ArrayList<String> roleListKey = new ArrayList<String>(Arrays.asList("10331882130","10347331840","200004083353","10000065692"));

		JSONArray officeData = getofficeDetsForMemberMLSId(property.getProperty("UserName"),returnFieldForOfficeData);

		JSONObject officeDataObj = officeData.getJSONObject(0);
		String officeKeyValue = officeDataObj.get("officeKey").toString();
		String officeMlsId = officeDataObj.get("officeMlsId").toString();
		String officeName = officeDataObj.get("officeName").toString();
		String memberEmail="CoreAlayaAutomation"+docName+"@yopmail.com";
		String memberMlsId="5567891";
		String firstName=docName+"FirstName";
		String lastName=docName+"LastName";
		long officeKey = Long.parseLong(officeKeyValue);


		lmsObj.putOpt("memberLoginId",memberMlsId );
		lmsObj.putOpt("memberMlsId",memberMlsId );
		lmsObj.putOpt("memberEmail", memberEmail);
		lmsObj.putOpt("memberFirstName",firstName );
		lmsObj.putOpt("memberLastName",lastName );
		lmsObj.putOpt("memberNickname",firstName );
		lmsObj.putOpt("memberPreferredFirstName",firstName );
		lmsObj.putOpt("memberPreferredLastName", lastName);
		lmsObj.putOpt("memberPrivateEmail",memberEmail );
		lmsObj.putOpt("officeMlsId", officeMlsId);
		lmsObj.putOpt("officeName",officeName );
		lmsObj.putOpt("memberFullName",firstName+ " "+ lastName);
		lmsObj.putOpt("officeKey",officeKey);

		JSONObject roleObj=lmsObj.getJSONObject("roles");


		String roleKeyStatusActive = discoveryLayerPostAPIPage.getIdGenerator(httpRequest);
		JSONObject  statusActive=createRoleJson(firstName,roleKeyStatusActive,lastName,officeName,officeKey,docName,roleListKey.get(0),roleList.get(0),null);
		roleObj.put(roleKeyStatusActive, statusActive);

		String roleKeyAgent = discoveryLayerPostAPIPage.getIdGenerator(httpRequest);
		JSONObject  agentRole=createRoleJson(firstName,roleKeyStatusActive,lastName,officeName,officeKey,docName,roleListKey.get(3),roleList.get(3),memberMlsId);
		roleObj.put(roleKeyAgent, agentRole);

		String roleRealtor = discoveryLayerPostAPIPage.getIdGenerator(httpRequest);
		JSONObject  realtor=createRoleJson(firstName,roleKeyStatusActive,lastName,officeName,officeKey,docName,roleListKey.get(1),roleList.get(1),null);
		roleObj.put(roleRealtor, realtor);

		String roleSystemBright = discoveryLayerPostAPIPage.getIdGenerator(httpRequest);
		JSONObject  systemBright=createRoleJson(firstName,roleKeyStatusActive,lastName,officeName,officeKey,docName,roleListKey.get(2),roleList.get(2),memberMlsId);
		roleObj.put(roleSystemBright, systemBright);

		lmsObj.putOpt("roles",roleObj);
		
		return lmsObj;

	}

	public static JSONObject createRoleJson(String sysPrFirstName,String sysPrKey,String sysPrLastName,String sysPrParentID,long sysPrParentPartyKey,String sysPrPartyKey,String sysPrRoleKey,String sysPrRoleName,String sysPrRoleID)
	{


		String rolePayload = CommonUtilityMethods.readJsonFile(property.getProperty("objectPostPayloadJsonPath"),"roles").toString();
		JSONObject rolePayloadOBJ = new JSONObject(rolePayload);
		rolePayloadOBJ.put("sysPrFirstName", sysPrFirstName);
		rolePayloadOBJ.put("sysPrKey", Long.parseLong(sysPrKey));
		rolePayloadOBJ.put("sysPrLastName", sysPrLastName);
		rolePayloadOBJ.put("sysPrParentID", sysPrParentID);
		rolePayloadOBJ.put("sysPrParentPartyKey", sysPrParentPartyKey);
		rolePayloadOBJ.put("sysPrPartyKey", Long.parseLong(sysPrPartyKey));
		rolePayloadOBJ.put("sysPrRoleKey", Long.parseLong(sysPrRoleKey));
		rolePayloadOBJ.put("sysPrRoleName", sysPrRoleName);
		rolePayloadOBJ.put("sysPrRoleID", sysPrRoleID);
		rolePayloadOBJ.put("primaryKey", Long.parseLong(sysPrKey));


		return rolePayloadOBJ;


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
			lmsObj = payloadForMember(documentName,lmsObj);				
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
	
	


	/**
	 * A method to verify response structure when POST API is successfully sent
	 */
        public static void verifyResponseStructureForPostObject(Response response,String documentName,String docType,String locale) {
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
			softAssert.assertTrue(CollectionUtils.isEqualCollection(apiInfoFields, ExpectedApiInfoFields),"Fields inside API INFO mismatched for POST API of object layer. Expected were "+ExpectedApiInfoFields+" Found were "+apiInfoFields);
		}
		else 
		softAssert.fail("API info object not found in response body for POST call object layer for document name - "+documentName+" and document type - "+docType);
		
		/*verifying document name here*/
		if(responseObj.has("documentName")) {
			String docName = responseObj.get("documentName").toString();
			softAssert.assertEquals(docName, documentName , "Document name mismatch found in response. Expected was "+documentName+" but found was "+docName);
		}
		else
		softAssert.fail("Document name object not found in response body for POST API object layer for document name - "+documentName+" and document type - "+docType);
		
		/*verifying location Object here*/
		if(responseObj.has("location")) {
		JSONObject location = responseObj.getJSONObject("location");
		Iterator<String> Keys = location.keys();
		while (Keys.hasNext()) 
		{
		  String fieldValue = (String)Keys.next();		  
		  locationFields.add(fieldValue);
		}
		
		softAssert.assertTrue(CollectionUtils.isEqualCollection(locationFields, ExpectedlocationFields),"Fields inside location mismatched for POST API of object layer. Expected were "+ExpectedApiInfoFields+" Found were "+locationFields);
		String locationDocType = location.get("documentType").toString();
		String locationlocale = location.get("systemLocale").toString();
		softAssert.assertEquals(locationDocType,docType,"Document type in location mismatch. Expected was - "+docType+" found was - "+locationDocType);
		softAssert.assertEquals(locationlocale,"bright","System locale in location mismatch. Expected was - bright found was - "+locationlocale);
		
		}
	   else 
	   softAssert.fail("Location object not found in response body for POST call object layer for document name - "+documentName+" and document type - "+docType);
		
	   /*verifying size Object here*/
	   if(responseObj.has("size")) {	
			String size = responseObj.get("size").toString();
			if(size.length()== 0)
			softAssert.fail("size object was found empty");
	   }
	   else
	   softAssert.fail("size object not found in response body for POST call object layer for document name - "+documentName+" and document type - "+docType);
		
	   /*verifying last modified,tags,tag count, version id and versions Object here*/
	   if(responseObj.has("lastModified") && responseObj.has("tagCount") && responseObj.has("tags") && responseObj.has("versionId") && responseObj.has("versions")) {	
		logger.info("last modified,tags,tag count, version id and versions Object found in POST API call");
		JSONArray tags = responseObj.getJSONArray("tags");
		if(tags.length()==0) {
			softAssert.fail("Tags array was empty even after tags parameter was true");
		}
		JSONObject versions = responseObj.getJSONObject("versions");
		JSONObject versionHistory = (JSONObject) versions.getJSONArray("versionHistory").get(0);
		String versionIdInside = versionHistory.get("versionId").toString();
		String versionIdOutside = responseObj.get("versionId").toString();
	    softAssert.assertEquals(versionIdInside, versionIdOutside,"Versions ids from response and version history did not match");
	    softAssert.assertEquals(versionHistory.get("isFirst"), true,"Version created via Post call is not the first version");
	    softAssert.assertEquals(versionHistory.get("isLatest"), true,"Version created via Post call is not the latest version");	    
	   }
	   else
	   softAssert.fail("last modified,tags,tag count, version id and versions Object not found in response body for POST call object layer for document name - "+documentName+" and document type - "+docType);
	  	
		softAssert.assertAll();	
    }

	/**
	 * A method to verify 200 status code when POST API is successfully sent
	 */

	public static ArrayList<String> verifySuccessStatusforPOSTAPIObjectLayer(RequestSpecification httpRequest,String docType) {
		ArrayList<String> result = new ArrayList<String>();
		String docName = discoveryLayerPostAPIPage.getIdGenerator(httpRequest);
		logger.info("Object layer Id generator generated document - "+ docName);
		if(Strings.isNullOrEmpty(docName)) {
			Assert.fail("No valid document found for document type - "+ docType);
		}
		JSONObject payload = createPayloadForPostObjectLayer(docName,docType);
		Response  response = ObjectLayerPage.postCallForObjectyLayer(httpRequest,True,True,True,True,Locale,docType,docName,ResponseCodes.CREATED,payload);
		logger.info("Verified the Success Status for POST API of object layer for document type - "+docType);
		verifyResponseStructureForPostObject(response,docName,docType,Locale);
		result.add(docName);
		result.add(payload.toString());
		return result;
	}

	
	/**
	 * A method to verify 200 status code when POST API is successfully sent for bypass rules application
	 */

	public static ArrayList<String> verifySuccessStatusforPOSTAPIByPassRules(RequestSpecification httpRequest,String docType) {
		ArrayList<String> result = new ArrayList<String>();
		JSONObject rulesData = new JSONObject();
		String docName = discoveryLayerPostAPIPage.getIdGenerator(httpRequest);
		logger.info("Object layer Id generator generated document for bypass rule test case In Post API - "+ docName);
		if(Strings.isNullOrEmpty(docName)) {
			Assert.fail("No valid document found for document type - "+ docType);
		}
		JSONObject payload = createPayloadForPostObjectLayer(docName,docType);
		//modify payload with rules validation values
		rulesData = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"allValidValuesBright").getJSONObject("rulesData");
		ObjectLayerPutAPIPage.modifyPayloadForMemberRules(payload.toString(),rulesData);
		Response  response = ObjectLayerPage.postCallForObjectyLayerForByPassRules(httpRequest,True,True,True,True,Locale,docType,docName,ResponseCodes.CREATED,payload);
		logger.info("Verified the Success Status for POST API of object layer for document type - "+docType);
		verifyResponseStructureForPostObject(response,docName,docType,Locale);
		result.add(docName);
		result.add(payload.toString());
		return result;
	}


	/**
	 * A method to verify 200 status code when POST API is successfully sent
	 */

	public static void verifyHostnameAndSourceDataBaseName(String lmsObj,String docType) {

		String databaseName=new JSONObject(lmsObj).getJSONObject("content").getJSONObject("lmsObject").getString("sourceDatabaseName");
		String exepectedDataBaseName="bright/"+docType;
		Assert.assertEquals(databaseName,exepectedDataBaseName,"databaseName not matched");
		logger.info(new JSONObject(lmsObj).getJSONObject("content").getJSONObject("lmsObject").getString("sourceDatabaseName"));

		String hostname=new JSONObject(lmsObj).getJSONObject("content").getJSONObject("lmsObject").getString("sourceHostName");

		String expectedHostname=String.format("au%s%sz1s3documentstoreinfra-docstore", region, CommonUtilityMethods.getEnvironment());
		Assert.assertEquals(hostname, expectedHostname);

	}

	/**
	 * A method to verify 200 status code when POST API is successfully sent
	 * @throws ParseException 
	 * @throws NumberFormatException 
	 */

	public static void verifyAddressContenation(String resultant,String docType) throws NumberFormatException, ParseException {


		String payload = CommonUtilityMethods.readJsonFile(property.getProperty("objectPostPayloadJsonPath"),docType).toString();

		JSONObject officeData=new JSONObject(payload).getJSONObject("content").getJSONObject("lmsObject");

		String responseStreetNumber=officeData.get(docType+"StreetNumber").toString();
		String responseStreetName=officeData.get(docType+"StreetName").toString();
		String responseStreetDirprefix=officeData.get(docType+"StreetDirPrefix").toString();
		String responseStreetSuffix=officeData.get(docType+"StreetSuffix").toString();
		String responseAddress1=new JSONObject(resultant).getJSONObject("content").getJSONObject("lmsObject").getString(docType+"Address1");


		String streetDireResponse = OfficeStaticRulePage.getConfigAPIWithPicklistIdAndName("STREET_DIRECTIONS");
		JSONObject itemsStreetDir = new JSONObject(streetDireResponse).getJSONObject("item");
		JSONObject jsonStreetDir = null;
		try {
			jsonStreetDir =  (JSONObject) itemsStreetDir.get("pickListItems");
		} catch (JSONException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		String officeStreetDirPrefixValue= ValidateAPIPage.getValueOfKeyInPicklist(jsonStreetDir,responseStreetDirprefix,"picklistItemId").getString("displayValue");;



		String streetTypesResponse = OfficeStaticRulePage.getConfigAPIWithPicklistIdAndName("STREET_TYPES");
		JSONObject itemsStreetTypes = new JSONObject(streetTypesResponse).getJSONObject("item");
		JSONObject jsonStreetTypes= null;
		try {
			jsonStreetTypes =(JSONObject) itemsStreetTypes.get("pickListItems");
		} catch (JSONException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String officeStreetSuffixValue= ValidateAPIPage.getValueOfKeyInPicklist(jsonStreetTypes,responseStreetSuffix,"picklistItemId").getString("itemCode");;

		String expectedAddress=responseStreetNumber+" "+officeStreetDirPrefixValue+" "+responseStreetName+" "+officeStreetSuffixValue;
		Assert.assertEquals(responseAddress1, expectedAddress);
	}



	/**
	 * A method to verify attributes matching when POST API is successfully sent
	 */
	public static void verifyattributesforPOSTAPIObjectLayer(RequestSpecification httpRequest,String docType, String docName,ArrayList<String> AttributeNamesForDocStore, ArrayList<String> AttributeNamesForES) {
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
		Assert.assertEquals(AttributesFromDocStore.equals(AttributesFromES), true,"Object layer Post Test case : Doc store attributes did not match with ES attributes for document type and doc name - "+docType+"/"+docName+ "Doc store attributes are - "+AttributesFromDocStore+"ES attributes are -"+AttributesFromES);
		logger.info("Object layer Post Test case : Verified attributes from doc store are matching elastic search in POST API of object layer for document type - "+docType);
	}

	/**
	 * A method to verify bad request code when invalid bearer token is sent in POST API
	 */	
	public static void verifyResponseForInvalidBearerTokenforPOSTAPIObjectLayer(RequestSpecification httpRequest,String docType,String docName,JSONObject payload) {
		logger.info("Object layer Working on document - "+ docName);
		if(Strings.isNullOrEmpty(docName)) {
			Assert.fail("No valid document found for document type - "+ docType);
		}
		String  response = ObjectLayerPage.verifyForbiddenInvalidBearerTokenForPOSTAPIObject(httpRequest,Locale,docType,payload);
		logger.info("Verified the forbidden Status of invalid bearer token for POST API of object layer for document type - "+docType);
	}

	/**
	 * A method to verify bad request code when invalid session key is sent in POST API
	 */	
	public static void verifyResponseForInvalidSessionKeyforPOSTAPIObjectLayer(RequestSpecification httpRequest,String docType,String docName,JSONObject payload) {
		logger.info("Object layer Working on document - "+ docName);
		if(Strings.isNullOrEmpty(docName)) {
			Assert.fail("No valid document found for document type - "+ docType);
		}
		String  response = ObjectLayerPage.verifyForbiddenInvalidSessionKeyForPOSTAPIObject(httpRequest,Locale,docType,payload);
		logger.info("Verified the forbidden Status of invalid session key for POST API of object layer for document type - "+docType);
	}

	/**
	 * A method to verify 404 error if wrong document Type is given for POST API
	 */

	public static void verifyResponseForWrongDocType(RequestSpecification httpRequest,String docType, String docName,JSONObject payload,String condition) {
		logger.info("Object layer Working on document - "+ docName);
		if(Strings.isNullOrEmpty(docName)) {
			Assert.fail("No valid document found for document type - "+docType);
		}
		logger.info("Object payload is : "+payload);
		if(condition.equalsIgnoreCase("POST")) {
		     ObjectLayerPage.postCallForObjectyLayer(httpRequest,True,True,True,True,Locale,"wrong",docName,ResponseCodes.BAD_REQUEST,payload);
		}
		else if(condition.equalsIgnoreCase("PUT")){
			JSONObject contentObj = payload.getJSONObject("content");
			JSONObject payloadObj = new JSONObject();
			payloadObj.putOpt("content", contentObj);
			payloadObj.putOpt("lastUpdatedBy", "core Alaya qa automation for object layer PUT call");	
			String getResponse = ObjectLayerPage.getCallForObjectyLayer(httpRequest,Locale,docType,docName,ResponseCodes.SUCCESS).getBody().asString();
			String lastModified = new JSONObject(getResponse).getJSONObject("metadata").get("lastModified").toString();
			ObjectLayerPage.PutCallAPIObjectLayer(httpRequest,True,True,True,True,Locale,docName,"wrong",ResponseCodes.BAD_REQUEST,payloadObj,lastModified);
		}
		logger.info("Verified the 404 not found Status for wrong document type for POST API of object layer for document type - "+docType);
	}

	/**
	 * A method to verify 404 error if wrong document Type is given for POST API
	 */

	public static void verifyResponseForWrongLocale(RequestSpecification httpRequest,String docType, String docName,JSONObject payload,String condition) {
		logger.info("Object layer Working on document - "+ docName);
		if(Strings.isNullOrEmpty(docName)) {
			Assert.fail("No valid document found for document type - "+docType);
		}
		logger.info("Object payload is : "+payload);
		if(condition.equalsIgnoreCase("POST")) {
		    ObjectLayerPage.postCallForObjectyLayer(httpRequest,True,True,True,True,"wrong",docType,docName,ResponseCodes.BAD_REQUEST,payload);
		}
		else if(condition.equalsIgnoreCase("PUT")) {
			JSONObject contentObj = payload.getJSONObject("content");
			JSONObject payloadObj = new JSONObject();
			payloadObj.putOpt("content", contentObj);
			payloadObj.putOpt("lastUpdatedBy", "core Alaya qa automation for object layer PUT call");	
			String getResponse = ObjectLayerPage.getCallForObjectyLayer(httpRequest,Locale,docType,docName,ResponseCodes.SUCCESS).getBody().asString();
			String lastModified = new JSONObject(getResponse).getJSONObject("metadata").get("lastModified").toString();
			ObjectLayerPage.PutCallAPIObjectLayer(httpRequest,True,True,True,True,"wrong",docName,docType,ResponseCodes.BAD_REQUEST,payloadObj,lastModified);
		}
		logger.info("Verified the 404 not found Status for wrong locale for POST API of object layer for document type - "+docType);
	}



}
