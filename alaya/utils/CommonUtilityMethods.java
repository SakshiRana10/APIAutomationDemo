package bright.api.alaya.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;
import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.json.simple.parser.JSONParser;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import org.testng.util.Strings;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import bright.api.alaya.utils.EnumsUtility.EnumObjectType;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.GetTopicAttributesRequest;
import software.amazon.awssdk.services.sns.model.GetTopicAttributesResponse;
import software.amazon.awssdk.services.sns.model.ListTopicsRequest;
import software.amazon.awssdk.services.sns.model.ListTopicsResponse;
import software.amazon.awssdk.services.sns.model.SnsException;
import software.amazon.awssdk.services.sns.model.Subscription;
import software.amazon.awssdk.services.ssm.SsmClient;

public class CommonUtilityMethods extends MainClassAlaya {

	private static FileWriter fileWriter;
	private static final ObjectMapper mapper = new ObjectMapper();
	static String x_api_key=null;
	public static String user,password;
	public static String res;
	public static HashMap<String, String> documentKeys = new HashMap<String,String>(){{
        put("listing", "listingKey");
        put("office","officeKey");
        put("member", "memberKey");
        put("countyrate","fileName");
        put("taxrecord","fileName");
        put("city","ctyCityKey");
        put("subdivision","loSubdivisionKey");
        put("builder_model","builderModelKey");
        put("building_name","bldgNameKey");
        put("team","teamKey");
    }};
    
    public static HashMap<String,ArrayList<String>> docListMap = new HashMap<String,ArrayList<String>>(){{
    	 put("listing", listingDocumentNames);
    	 put("office",officeDocumentNames);
         put("member",memberDocumentNames);
         put("countyrate",countyDocumentNames);
         put("taxrecord",taxDocumentNames);
         put("city",cityDocumentNames);
         put("subdivision",subDivisionDocumentNames);
         put("builder_model",builderModelDocumentNames);
         put("building_name",buildingNameDocumentNames);
         put("team",teamDocumentNames);
    	
    }};
    
    public static HashMap<String, String> graphQlMethods = new HashMap<String,String>(){{
        put("listing", "getListings");
        put("office","getOffices");
        put("member", "getMembers");
        put("countyrate","getCountyRates");
        put("taxrecord","getTaxRecords");
        put("city","getCities");
        put("subdivision","getSubdivisions");
        put("builder_model","getBuilderModels");
        put("building_name","getBuildingNames");
        put("team","getTeams");
        put("wrong","wrong");
    }};
    
	

	/**----------------------------------------------------------------------------------------------------------- **/	
	/** ALL REQUEST SPEC METHODS **/
	/**----------------------------------------------------------------------------------------------------------- **/	
	
	public static RequestSpecification requestSpec() {               //DOCUMENT STORE REQUEST SPEC
		RequestSpecBuilder builder = new RequestSpecBuilder();
		builder.setBaseUri(EnvSpecificMethods.docStoreURL());
		x_api_key= fetchXApiKey("documentstore/apikey");
		if (Strings.isNullOrEmpty(x_api_key)) {
			x_api_key = EnvSpecificMethods.docStoreAPIKey(); 

		}
		builder.addHeader("x-api-key",x_api_key);
		builder.setContentType(ContentType.JSON);
		return builder.build();
	}

	public static RequestSpecification requestSpecForDiscovery() {     //DISCOVERY LAYER REQUEST SPEC
		RequestSpecBuilder builder = new RequestSpecBuilder();
		builder.setBaseUri(EnvSpecificMethods.discoveryLayerURL());
		x_api_key= fetchXApiKey("discoveryServices");
		if (Strings.isNullOrEmpty(x_api_key)) {
			x_api_key = EnvSpecificMethods.discoveryLayerAPIKey(); 

		}
		builder.addHeader("x-api-key",x_api_key);
		builder.setContentType(ContentType.JSON);
		return builder.build();

	}
	
	public static RequestSpecification requestSpecForObjectLayer() {     //OBJECT LAYER REQUEST SPEC
		RequestSpecBuilder builder = new RequestSpecBuilder();
		builder.setBaseUri(EnvSpecificMethods.ObjectLayerURL());
		x_api_key= fetchXApiKey("objectServices");
		if (Strings.isNullOrEmpty(x_api_key)) {
			x_api_key = EnvSpecificMethods.ObjectLayerAPIKey(); 

		}
		builder.addHeader("x-api-key",x_api_key);
		builder.setContentType(ContentType.JSON);
		return builder.build();

	}
	
	public static RequestSpecification graphQlRequestSpec() {            //GRAPHQL REQUEST SPEC
		RequestSpecBuilder builder = new RequestSpecBuilder();
		builder.setBaseUri(EnvSpecificMethods.graphQlURL());
		String x_api_key = fetchXApiKey("discoveryServices");
		if(Strings.isNullOrEmpty(x_api_key)) {
			x_api_key=EnvSpecificMethods.graphQlAPIKey();
		}

		builder.addHeader("x-api-key",x_api_key);
		builder.setContentType(ContentType.JSON);
		return builder.build();
	}


	public static RequestSpecification OneadminSpec() {                  //ONE ADMIN REQUEST SPEC
		RequestSpecBuilder builder = new RequestSpecBuilder();
		builder.setBaseUri(EnvSpecificMethods.oneAdminUrl());

		return builder.build();
	}
	
	public static RequestSpecification utilityServicesRequestSpec() {            //UTILITY SERVICES LAYER REQUEST SPEC
		RequestSpecBuilder builder = new RequestSpecBuilder();
		builder.setBaseUri(EnvSpecificMethods.utilityServicesURL());
		String x_api_key = fetchXApiKey("utilityServices");
		if(Strings.isNullOrEmpty(x_api_key)) {
			x_api_key=EnvSpecificMethods.utilityLayerAPIKey();
		}

		builder.addHeader("x-api-key",x_api_key);
		builder.setContentType(ContentType.JSON);
		return builder.build();
	}
	
	public static RequestSpecification auditTrailRequestSpec() {            //AUDIT TRAIL REQUEST SPEC
		RequestSpecBuilder builder = new RequestSpecBuilder();
		builder.setBaseUri(EnvSpecificMethods.auditTrailURL());
		String x_api_key = fetchXApiKey("auditTrailServices/apiKey");
		if(Strings.isNullOrEmpty(x_api_key)) {
			x_api_key=EnvSpecificMethods.auditTrailAPIKey();
		}

		builder.addHeader("x-api-key",x_api_key);
		builder.setContentType(ContentType.JSON);
		return builder.build();
	}
	
	/**----------------------------------------------------------------------------------------------------------- **/	
	/** ALL METHODS OF FILE MANIPULATIONS AND READING **/
	/**----------------------------------------------------------------------------------------------------------- **/	
	
	public static JSONObject readJsonFile(String JSONPath, String property) {   //READING JSONOBJECT FROM A FILE
		String loc = new String(workingDir + JSONPath);
		File file = new File(loc);
		JSONObject json = null;
		try {
			String content = new String(Files.readAllBytes(Paths.get(file.toURI())));
			json = property.length()==0 ? new JSONObject(content) : new JSONObject(content).getJSONObject(property);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	public static org.json.simple.JSONObject readSimpleJSONFile(String JSONPath, String property) {     //READING SIMPLE JSONOBJECT FROM A FILE
		FileReader fileReader = null;
		String loc = new String(workingDir + JSONPath);
		File file = new File(loc);
		org.json.simple.JSONObject json = null;
		try {
			Object object = null;
			JSONParser jsonParser = new JSONParser();
			try {
				fileReader = new FileReader(loc);
			} catch (FileNotFoundException exception) {
				logger.info("Could not find the JSON file from the provided path");
			}
			try {
				object = jsonParser.parse(fileReader);

			} catch (IOException readException) {
				logger.info("Could not read the JSON file" + readException);
			}
			org.json.simple.JSONObject jsonObj = (org.json.simple.JSONObject) object;
			json = (org.json.simple.JSONObject) jsonObj.get(property);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	public static void writeOutputValue(String value, String attribute) {       //WRITING OUTPUT TO OUTPUTVALUE FILE
		JSONObject jobj = new JSONObject();
		jobj.put("Value", value);
		try {
			JSONObject temp = CommonUtilityMethods.readJsonFile(property.getProperty("outputValuesPath"),"");
			if(temp.has(attribute)) temp.remove(attribute);
			temp.put(attribute, jobj);
			fileWriter = new FileWriter(workingDir + "/resources/output-generated/outputValues.json");
			fileWriter.write(temp.toString());
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException connectionException) {
			logger.info("Could not close the connection" + connectionException.getMessage());
		}
	}

	public static void saveDecodedDocument(String decodedDocument, String filename) {     //SAVING DECODED DOCUMENT
		FileWriter file;
		try {
			file = new FileWriter(workingDir+ String.format("/resources/output-generated/decoded-documents/%s.json", filename));
			file.write(decodedDocument);
			file.flush();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void createTestDataDir() {                     //CREATING TEST DATA DIRECTORY AND FILES WITHIN
		CommonUtilityMethods.getUserCredentails();
		File localRepoDirBright = new File(System.getProperty("user.dir")+"/resources/testdata/BrightOneadmin/RulesFlag"); 
		File localRepoDirWirex = new File(System.getProperty("user.dir")+"/resources/testdata/WirexOneadmin/RulesFlag"); 
		//Creating a folder using mkdir() method  

		File localRepoDir = new File(System.getProperty("user.dir")+"/resources/testdata/BrightOneadmin/RulesData"); 
		File localRepoDirWirexRules = new File(System.getProperty("user.dir")+"/resources/testdata/WirexOneadmin/RulesData"); 
		
		File logicalAttributeMApping = new File(System.getProperty("user.dir")+"/resources/testdata/LcLaMapping"); 
		//Creating a folder using mkdir() method  
		localRepoDir.mkdirs(); 
		localRepoDirWirex.mkdirs();
		localRepoDirBright.mkdirs();  
		localRepoDirWirexRules.mkdirs();
		logicalAttributeMApping.mkdirs();
	}
	 
	public static void appendStrToFile(String fileName,String str) {    //APPENDING STRING TO FILE
		try {
			BufferedWriter out = new BufferedWriter(
					new FileWriter(fileName, true));
			out.write(str);
			out.close();
		}
		catch (IOException e) {
			System.out.println("exception occurred" + e);
		}
	}
	
   public static String fetchXApiKey(String parameter) {         //GETTING X-API-KEY OR CREDENTIALS FROM PARAM STORE FILE FETCHED FROM AWS
	   String xApiKey = null;
	   try {
		xApiKey = FileUtility.getValueFromParamStoreFile(parameter);
	} catch (IOException e) {
		e.printStackTrace();
	}
	   return xApiKey;
   }
	
   /**----------------------------------------------------------------------------------------------------------- **/	
   /** ALL METHODS OF MISCELLANEOUS CONVERSIONS AND CALCULATIONS OF STRINGS,JSONARRAYS,JSONOBJECTS,XML **/
   /**----------------------------------------------------------------------------------------------------------- **/	
	
	public static JSONObject convertJSON(String xml) {           //CONVERTING XML TO JSON FORMAT
		JSONObject json = new JSONObject();
		try {  
			 json = XML.toJSONObject(xml);   	  
			}
		catch (JSONException e) {   
			logger.info(e.toString());  
			}
		logger.info("--Converting xml into Json--");
		return json;		
	}
	
	public static JSONArray convertJSONArray(JSONObject obj) {   //CONVERTING JSONOBJECT TO JSONARRAY
		JSONArray jsonArr = new JSONArray();
		Iterator x = obj.keys();
		while (x.hasNext()){
		    String key = (String) x.next();
		    jsonArr.put(obj.get(key));
		}	
		return jsonArr;		
	}
 
	public static int pickRandomFromJSONArray(JSONArray arr) {    //GETTING RANDOM NUMBER FROM JSONARRAY
		int random = 0 ;	
		Random generator = new Random();
	    random = generator.nextInt(arr.length());
		return random;		
	}
	
	public static JSONObject convertStrToObj(String str){        //CONVERTING STRING TO JSONOBJECT
		JSONObject jobj = new JSONObject(str);
		return jobj;		
	}
 
	public static int compareArrays(JSONArray arr1, JSONArray arr2) throws JsonMappingException, JsonProcessingException {
		int flag = 0;                                            //COMPARING TWO JSONARRAYS HAVING LONG TYPE ELEMENTS
       for(int i=0; i<arr1.length();i++) {
       	flag = 1;
       	long x = (long) arr1.get(i);
       	for(int j=0;j<arr2.length();j++) {
       		long y = (long)arr2.get(j);
       		if(x!=y)
       			continue;
       		else {
       			flag = 0;
       			break;
       		}
       	}
       }
     return flag;
	}

	public static String decode(String str) {                     //DECODING
		return new String(Base64.getMimeDecoder().decode(str));
	}
	
	public static JSONObject modifyJSONObject(JSONObject object , JSONObject putObjects , String[] removeObjects) {
		for(int index = 0; index<removeObjects.length; index++) {   //MODIFYING JSON OBJECTS
			object.remove(removeObjects[index]);
		}
		Iterator<String> keys = putObjects.keys();
		while(keys.hasNext()) {
			String key = keys.next();
			try{
				object.put(key, putObjects.getInt(key));
			}catch(Exception e ){
				object.put(key, putObjects.getString(key));
			}
		}
		return object;
	}

	public static boolean isJsonsEqual(String expectedBody , String actualBody) {  //COMPARING TWO JSONS IN STRING FORMAT
		JsonNode expectedJson = getJson(expectedBody);
		JsonNode actualJson = getJson(actualBody);
		try {
			
			if(!expectedJson.equals(actualJson))
			{
				JSONCompareResult result = 
					     JSONCompare.compareJSON(expectedBody, actualBody, JSONCompareMode.STRICT);
				logger.info("mismatch result is :"+result);
					if(result.toString().contains("sourceHostName"))
						return true;
			}
			if(expectedJson.equals(actualJson)) return true;
		} catch(Exception jsonException) {
			jsonException.printStackTrace();
		}
		
		
		return false;
	}
	
	public static String jsonComparer(String expectedBody , String actualBody) {  //COMPARING TWO JSONS IN STRING FORMAT
		JsonNode expectedJson = getJson(expectedBody);
		JsonNode actualJson = getJson(actualBody);
		String mismatches=null;
		try {
			
			if(!expectedJson.equals(actualJson))
			{
				JSONCompareResult result = 
					     JSONCompare.compareJSON(expectedBody, actualBody, JSONCompareMode.STRICT);
				logger.info("mismatch result is :"+result);
				
					if(result.toString().contains("sourceHostName"))
						mismatches=null;
					
					else
						mismatches=result.toString();
			}
			if(expectedJson.equals(actualJson)) return null;
		} catch(Exception jsonException) {
			jsonException.printStackTrace();
		}
		
		
		return mismatches;
	}

	public static JsonNode getJson(String json) {   //CREATING JSONNODE OF JSONOBJECT FOR COMPARING DIFFERENT JSONOBJECTS
		JsonNode jsonNode = null;
		try {
			jsonNode = mapper.readTree(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonNode;
	}

	public static String decodeBase64String(String base64EncodedString) {   //DECODING BASE64 STRING
		GZIPInputStream gzip;
		String result = new String();
		try {
			gzip = new GZIPInputStream(new Base64InputStream(IOUtils.toInputStream(base64EncodedString,"UTF-8")));
			BufferedReader br = new BufferedReader(new InputStreamReader(gzip));
			result = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static ArrayList<String> convertJArrayToListString(JSONArray jArray){   //CONVERTING JSONARRAY TO STRING LIST
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i<jArray.length();i++) {
			list.add(jArray.getString(i));
		}
		return list;
	}
	
	
	
	public static List<String> getUncommonElements(List<String> a, List<String> b) {  //FETCHING ALL UNCOMMON ELEMENTS FROM 2 LISTS - RESULT IN STRING LIST
		return a.stream()
				.filter(aObject -> {
					return ! b.contains(aObject);
				})
				.collect(Collectors.toList());
	}

	public static List<Long> getUncommonElementsLong(List<Long> a, List<Long> b) {    //FETCHING ALL UNCOMMON ELEMENTS FROM 2 LISTS - RESULT IN LONG LIST
		List<Long> source=new ArrayList<>(a);
		List<Long> dest=new ArrayList<>(b);
		return a.stream()
				.filter(aObject -> {
					return ! b.contains(aObject);
				})
				.collect(Collectors.toList());
	}
	
	public static Date convertToDate(String date) {           //CONVERTING STRING TO DATE
		date = String.join(" ",date.split("T")).substring(0, date.length()-1);
		Date result = new Date() ;
		return result;
	}

	public static JSONArray removeDuplicateObjects(JSONArray array, String primaryKey) {  //REMOVING DUPLICATE OBJECTS FROM JSONARRAY
		HashMap<String, String> arrayMapping = new HashMap<String,String>();
		JSONArray result = new JSONArray();
		int length = array.length();
		for(int key=0; key<length; key++) {
			String currentListingKey = array.getJSONObject(key).get(primaryKey).toString();
			String dsLastModifiedTime = array.getJSONObject(key).getString("dsLastModifiedTime");
			if(arrayMapping.containsKey(currentListingKey)) {
				Date date = convertToDate(arrayMapping.get(currentListingKey));
				Date arrayDate = convertToDate(dsLastModifiedTime);
				if(arrayDate.after(date) == true) {
					arrayMapping.remove(currentListingKey);
					arrayMapping.put(currentListingKey, dsLastModifiedTime);
				}
			}
			else {
				arrayMapping.put(currentListingKey, dsLastModifiedTime);
			}
		}
		for (Map.Entry<String,String> entry : arrayMapping.entrySet()) {
			JSONObject obj = new JSONObject();
			obj.put(primaryKey, entry.getKey());
			obj.put("dsLastModifiedTime", entry.getValue());
			result.put(obj);
		}
		return result;
	}

	public static List<String> getMissingElement(List<String> A, List<String> B) {     //GETTING UNIQUE ELEMENTS FROM LIST 2 LEAVING BEHIND LIST 1
		List<String> result = new ArrayList(B); 
		result.removeAll(new HashSet<>(A));

		return result;
	}
	
	public static JSONArray fetchJArrayFromApiResponse(String responseBody, String key) {   //FETCHING JSONARRAY FROM API RESPONSE
		JSONObject jObject = null;
		try {
			jObject = new JSONObject(responseBody);
		}
		catch(Exception jsonException) {
			jsonException.printStackTrace();
		}
		return jObject.getJSONArray(key);
	}

	public static String fetchStringValueFromApiResponse(String responseBody, String key) {   //FETCHING STRING VALUE FROM API RESPONSE
		JSONObject jObject = null;
		try {
			jObject = new JSONObject(responseBody);
		}
		catch(Exception jsonException) {
			jsonException.printStackTrace();
		}
		return jObject.getString(key);
	}
	
	public static int pickRandomFromList(ArrayList<String> list){
		int index = (int)(Math.random() * list.size());
		return index;
	}
	
	public static ArrayList<String> getRandomDocNamesList(JSONArray documents){
		Random rand = new Random();
		ArrayList<String> randomDocNamesList = new ArrayList<>(); 
		ArrayList<Integer> IntegerList = new ArrayList<>();
		try {
		for (int i = 0; i < 45; i++) { 
		int randomIndex = rand.nextInt(documents.length()-1);
		IntegerList.add(randomIndex);
		while(IntegerList.contains(randomIndex)){  
		randomIndex = rand.nextInt(documents.length()-1);
		}
		String docName = documents.getJSONObject(randomIndex).getJSONObject("metadata").get("documentName").toString();
		randomDocNamesList.add(docName);
		}
	}
		catch(Exception e) {
			logger.info("Exception caught under generating random doc names process "+e);
		}
		return randomDocNamesList;
		}
		
		
	/**----------------------------------------------------------------------------------------------------------- **/	
	/** ALL CLASS SPECIFIC METHODS LIKE DOCUMENT STORE,DS INITIALIZER, BULK READ, FILTER POLICY, VALIDATING DOCUMENT **/
	/**----------------------------------------------------------------------------------------------------------- **/	

	public static boolean validateDocument(RequestSpecification httpRequest,String document, String payload ) {        //VALIDATING DOCUMENT
		JSONObject doc = new JSONObject(document);
		Iterator<String> keys = doc.keys();
		JSONObject requestBody = new JSONObject(payload);

		ArrayList<String> documentNames = new ArrayList<String>();
		JSONArray jArray = requestBody.getJSONArray("documentNames");
		for (int i = 0; i<jArray.length();i++) {
			documentNames.add(jArray.getString(i));
		}

		while(keys.hasNext()) {
			String key = keys.next();
			if(!documentNames.contains(key)) return false;
			JSONObject location = doc.getJSONObject(key).getJSONObject("metadata").getJSONObject("location");
			if(!location.getString("documentType").equals(requestBody.getString("documentType")) 
					|| !location.getString("systemLocale").equals(requestBody.getString("systemLocale"))) return false;

			String docStoreDocument = fetchDocumentfromDocStore(httpRequest,location.getString("documentType"), key);

			if(!isJsonsEqual(doc.getJSONObject(key).getJSONObject("content").toString(),docStoreDocument)) return false;
		}

		return true;
	}

	public static String fetchSubscriptionArn(SnsClient snsClient , String topicArn, String documentType) {  //FETCHING SUBSCRIPTION ARN OF SNS TOPIC
		List<Subscription> subscriptions = SNSConnection.listSNSSubscriptions(snsClient, topicArn);
		String subscriptionArn = null;
		for(int i = 0;i<subscriptions.size();i++) {
			Subscription subscription = subscriptions.get(i);
			if((documentType.equals("city") || documentType.equals("builder_model") || documentType.equals("building_name") || documentType.equals("subdivision") ) && topicArn.contains("documentstoreservices-subscription") ) {
                if(subscription.endpoint().contains("alayadiscoveryservices-indexbuilderqueue")) {
                    subscriptionArn = subscription.subscriptionArn();
                    break;
                }
            }
			else if(documentType.equals("builder_model") || documentType.equals("building_name") || documentType.equals("subdivision") || documentType.equals("city")) {
				 if(subscription.endpoint().contains("alayadiscovery-es-indexinitializerqueue")) {
	                    subscriptionArn = subscription.subscriptionArn();
	                    break;
	                }
			}
			
			else if(subscription.endpoint().contains("alayadiscoveryservices") &&subscription.endpoint().contains(documentType) && !subscription.endpoint().contains("hub")) {
				subscriptionArn = subscription.subscriptionArn();
				break;
			}
			else if(subscription.endpoint().contains("alayadiscovery-es") &&subscription.endpoint().contains(documentType) && !subscription.endpoint().contains("hub")) {
				subscriptionArn = subscription.subscriptionArn();
				break;
			}
			
		}
		return subscriptionArn;
	}


	/**
	 * A method to verify initializer filter policy of SNS
	 */ 

	public static boolean verifyInitializerFilterPolicy( String topicArn, String documentType ) {

		SnsClient snsClient = SNSConnection.createSnsClient(CommonUtilityMethods.getAWSRegion(), EnvSpecificMethods.awsProfile());
		String subscriptionArn = fetchSubscriptionArn(snsClient, topicArn , documentType);
		String filterPolicy = SNSConnection.fetchFilterPolicy(snsClient, subscriptionArn);
		logger.info("filetr policy is:"+filterPolicy);
		String docType = new JSONObject(filterPolicy).getJSONArray("documentType").toString();
		logger.info(docType);
		if(!docType.contains(documentType)) return false;
		String subName = new JSONObject(filterPolicy).getJSONArray("subscriberName").toString();
		logger.info(subName);
		if(!subName.contains(String.format("%s-indexer", documentType))) return false;
		return true;
	}


	
	/**
	 * A method to verify index builder filter policy of SNS
	 */ 

	public static String verifyBuilderFilterPolicyforDocType( String topicArn, String documentType ) {

		SnsClient snsClient = SNSConnection.createSnsClient(CommonUtilityMethods.getAWSRegion(), EnvSpecificMethods.awsProfile());
		String subscriptionArn = fetchSubscriptionArn(snsClient, topicArn , documentType);
		String filterPolicy = SNSConnection.fetchFilterPolicy(snsClient, subscriptionArn);
		
		String docType = new JSONObject(filterPolicy).getJSONArray("documentType").toString();

		return docType;
	}

	public static String verifyBuilderFilterPolicyforLocale(String topicArn,String documentType ) {   //VERIFYING BUILDER FILTER POLICY FOR LOCALE
		SnsClient snsClient = SNSConnection.createSnsClient(CommonUtilityMethods.getAWSRegion(), EnvSpecificMethods.awsProfile());
		String subscriptionArn = fetchSubscriptionArn(snsClient, topicArn , documentType);
		logger.info(subscriptionArn);
		String filterPolicy = SNSConnection.fetchFilterPolicy(snsClient, subscriptionArn);
		logger.info("Filter policy is: "+filterPolicy);
		String sysLocale = new JSONObject(filterPolicy).getJSONArray("systemLocale").toString();

		return sysLocale;
	}

	public static boolean isTopicExists(String topicArn) {     //VERIFYING IF TOPIC EXISTS IN SNS
		SnsClient snsClient = SNSConnection.createSnsClient(CommonUtilityMethods.getAWSRegion(), EnvSpecificMethods.awsProfile());
		try {
			ListTopicsRequest request1 = ListTopicsRequest.builder()
					.build();

			ListTopicsResponse result1 = snsClient.listTopics(request1);
			logger.info("Status was " + result1.sdkHttpResponse().statusCode() + "\n\nTopics\n\n" + result1.topics());
			GetTopicAttributesRequest request = GetTopicAttributesRequest.builder().topicArn(topicArn).build();
			GetTopicAttributesResponse result = snsClient.getTopicAttributes(request);
			logger.info("result is:"+ result);
			if(result.sdkHttpResponse().statusCode()!=200) return false;
		} catch (SnsException e) {
			System.err.println(e.awsErrorDetails().errorMessage());
			return false;
		}
		return true;
	}
	
	public static ArrayList<String> ValidateDocumentsInbulkReadPost(RequestSpecification httpRequest, ArrayList<String> randomDocumentNames,String docType,int limit,String contentObj,String key) {
		ArrayList<String> result= new ArrayList<String>();                 //VALIDATING DOCUMENTS THROUGH BULK READ
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpec();
		httpRequest = RestAssured.given();
		JSONObject payload = CommonUtilityMethods.readJsonFile(property.getProperty("dsInitializerPayloadJsonPath"),"dsInitializerBulkRead"+docType);
		payload.put("documentNames", randomDocumentNames);
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"bulkReadDSInitializer").getString(docType).toString());
		requestSpecification.body(payload.toString());
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.post();
		String responseBody = response.getBody().asString();
		String docName = null;
		try {
		String encodedDocument = CommonUtilityMethods.fetchStringValueFromApiResponse(responseBody,"documentsEncoded");
		String decodedDocument = CommonUtilityMethods.decodeBase64String(encodedDocument);
		JSONArray docsAfterDecode = convertJSONArray(new JSONObject(decodedDocument));
		for(int i=docsAfterDecode.length()-1; i>=0 && limit > 0 ; i--) {
			docName = docsAfterDecode.getJSONObject(i).getJSONObject("metadata").getString("documentName");
			JSONObject responseContent = docsAfterDecode.getJSONObject(i).getJSONObject("content");
			if(!responseContent.getJSONObject(contentObj).has(key)) {
				break;
			}
		String uniqueKey = responseContent.getJSONObject(contentObj).get(key).toString();
			if(docName.equals(uniqueKey)) {
				if(responseContent.getJSONObject(contentObj).get("documentDeletionInformation").equals(JSONObject.NULL)) {
				result.add(docName);
				limit--;
				}
				else {
				logger.info("Document found had document deletion Information");	
				}
			}
		}
	}
		catch(Exception e) {
			logger.info("Exception caught under bulk read document validating method for document name and type - "+docName+docType+e);
		}
		return result;
	}
	
	public static void fetchDocuments(RequestSpecification httpRequest, String documentType , int limit, String contentObj) {
		ArrayList<String> result= new ArrayList<String>();      //FETCHING DOCUMENT NAMES FROM DOCSTORE USING BULK READ API
		result=fetchDocumentsFromBulkRead(httpRequest,documentType,limit,contentObj);
		
		String DocListName = documentType;
		docListMap.get(DocListName).addAll(result);
		if(result.size()==0 || result.equals(null)) {
			logger.info("No valid documents found/fetched for document type - "+documentType);
			logger.info("trying again for documents for "+documentType);
			result=fetchDocumentsFromBulkRead(httpRequest,documentType,limit,contentObj);
			 DocListName = documentType;
			docListMap.get(DocListName).addAll(result);
			if(result.size()==0) {
				logger.info("No valid documents found/fetched for document type in another try- "+documentType);
			}
			
		}
			   
	}

	
	public static ArrayList<String> fetchDocumentsFromBulkRead(RequestSpecification httpRequest, String documentType , int limit, String contentObj) {
		ArrayList<String> result= new ArrayList<String>();      //FETCHING DOCUMENT NAMES FROM DOCSTORE USING BULK READ API
		String key = documentKeys.get(documentType);
		RequestSpecification requestSpecification;
		requestSpecification = requestSpec();
		httpRequest = RestAssured.given();
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"documentStore").getString("getEnumDocument").toString());	
		requestSpecification.pathParam("docType", documentType);
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.get();
		String responseBody = response.getBody().asString();
		try {
		JSONArray documents = new JSONObject(responseBody).getJSONArray("documents");
		ArrayList<String> randomDocNames = getRandomDocNamesList(documents);
		logger.info(" finding Random document lists for document type - "+documentType+" is - "+randomDocNames);
		result = ValidateDocumentsInbulkReadPost(httpRequest,randomDocNames,documentType,limit,contentObj,key);
		
		logger.info("Resultant list of fetched documents for doctype  "+documentType+" is  - "+result);
		}
		catch(Exception e) {
			logger.info("Entered into catch of fetch document names through bulk read method"+e);
			return null;
		}
		
		return result;
	}
	
	public static ArrayList<String> fetchDocumentNamesfromDocStore(RequestSpecification httpRequest, String documentType , int limit, String contentObj) throws InterruptedException{
		ArrayList<String> result= new ArrayList<String>();      //FETCHING DOCUMENT NAMES FROM DOCSTORE
		String key = documentKeys.get(documentType);
		RequestSpecification requestSpecification;
		requestSpecification = requestSpec();
		httpRequest = RestAssured.given();
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"documentStore").getString("getEnumDocument").toString());	
		requestSpecification.pathParam("docType", documentType);
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.get();
		String responseBody = response.getBody().asString();
		try {
			JSONArray documents = new JSONObject(responseBody).getJSONArray("documents");
			for(int i=documents.length()-1; i>=0 && limit > 0 ; i--) {
				int randomCount = (int)(Math.random() * 1000);
				JSONObject doc = documents.getJSONObject(randomCount);
				String docName = doc.getJSONObject("metadata").getString("documentName");
				String responseDoc = CommonUtilityMethods.fetchDocumentfromDocStore(httpRequest,documentType, docName);
				JSONObject responseObj = new JSONObject(responseDoc);
				String uniqueKey = responseObj.getJSONObject(contentObj).get(key).toString();
				if(docName.equals(uniqueKey)) {
					result.add(docName);
					limit--;
				}
			}
		}
		catch(Exception e) {
			logger.info("Entered into catch of fetch document names method"+e);
		}
		String DocListName = documentType;
		docListMap.get(DocListName).addAll(result);
		return result;
	}


	public static String fetchDocumentfromDocStore(RequestSpecification httpRequest,String documentType , String documentName){
		RequestSpecification requestSpecification;                  //FETCHING DOCUMENTS FROM DOCSTORE
		requestSpecification = requestSpec();
		httpRequest = RestAssured.given();
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"documentStore").getString("getDocument").toString());
		requestSpecification.pathParam("docType", documentType);
		requestSpecification.pathParam("docName", documentName);
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.get();
		String responseBody = response.getBody().asString();
		JSONObject doc = new JSONObject(responseBody).getJSONObject("content");
		String documents = doc.toString();
		return documents;

	}

	public static String responseFromDocStore(String docName,String docType,String baseProperty) {
		RequestSpecification requestSpecification;                   //FETCHING WHOLE RESPONSE FROM DOCSTORE BY DOC-NAME
		requestSpecification = requestSpec();
		RequestSpecification httpRequest = RestAssured.given();
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"documentStore").getString(baseProperty).toString());
		requestSpecification.pathParam("docType", docType);
		requestSpecification.pathParam("docName", docName);
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.get();
		String responseBody = response.getBody().asString();
		return responseBody;
	}

	public static ArrayList<String> fetchAttributesFromDocStore(String docName, ArrayList<String> attributeNames,String docType) {
		ArrayList<String> result = new ArrayList<String>();          //FETCHING ATTRIBUTES RESPONSE FROM DOCSTORE BY DOC-NAME
		JSONObject attributeOBJ = new JSONObject();
		String responseBody = CommonUtilityMethods.responseFromDocStore(docName, docType, "getDocument");
		  if(docType.equalsIgnoreCase("taxrecord") || docType.equalsIgnoreCase("countyrate"))
			{attributeOBJ= new JSONObject(responseBody).getJSONObject("content").getJSONObject("items");}
			else
			{attributeOBJ= new JSONObject(responseBody).getJSONObject("content").getJSONObject("lmsObject");}
		JSONObject attributeOBJforTime = new JSONObject(responseBody).getJSONObject("metadata");
		for(int i=0;i<attributeNames.size();i++) {
			String attribute = attributeNames.get(i);
			String attributeToAdd = null ;
			if(attributeOBJ.has(attribute)) {
				attributeToAdd = attributeOBJ.get(attributeNames.get(i)).toString();
			}
			else {
				attributeToAdd = attributeOBJforTime.get(attributeNames.get(i)).toString();
			}
			result.add(attributeToAdd);	
		}
		return result;
	}

	public static String putDocumentInDocStore(String documentType , String documentName , String payload, String attribute){
		RequestSpecification requestSpecification;                   //PUTTING DOCUMENT IN DOC STORE AND GETTING SINGLE ATTRIBUTE RESPONSE
		requestSpecification = requestSpec();
		RequestSpecification httpRequest = RestAssured.given();
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"documentStore").getString("putDocument").toString());
		requestSpecification.pathParam("docType", documentType);
		requestSpecification.pathParam("docName", documentName);
		requestSpecification.body(payload);
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.put();
		String responseBody = response.getBody().asString();
		String responseAttribute = new JSONObject(responseBody).getString(attribute);
		return responseAttribute;
	}
	
	public static String putDocumentInDocStoreToGetResponse(String documentType , String documentName , String payload){
		RequestSpecification requestSpecification;                    //PUTTING DOCUMENT IN DOC STORE AND GETTING WHOLE RESPONSE
		requestSpecification = requestSpec();
		RequestSpecification httpRequest = RestAssured.given();
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"documentStore").getString("putDocument").toString());
		requestSpecification.pathParam("docType", documentType);
		requestSpecification.pathParam("docName", documentName);
		requestSpecification.body(payload);
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.put();
		String responseBody = response.getBody().asString();
		return responseBody;
	}

	public static JSONArray putBulkReadDocumentsInDocStore(String document, String documentType ,String primaryKey,String attribute, String attributeForPut) {
		JSONObject doc = new JSONObject(document);                     //PUTTING BULK READ DOCUMENT IN DOCSTORE
		Iterator<String> keys = doc.keys();
		String docUniqueKey = new String();
		JSONArray modificationTimestamps = new JSONArray();
		while(keys.hasNext()) {
			String key = keys.next();
			JSONObject content = doc.getJSONObject(key).getJSONObject("content");
			String objKey = content.has("lmsObject") ? "lmsObject" : "items";
			docUniqueKey = content.getJSONObject(objKey).get(primaryKey).toString();
			JSONObject requestBody = new JSONObject();
			requestBody.put("lastUpdatedBy", "AlayaAutomation");
			requestBody.put("content", content);
			String responseAttri =  putDocumentInDocStore(documentType , key , requestBody.toString(),attributeForPut);
			JSONObject keyModificationTimestamp = new JSONObject();
			keyModificationTimestamp.put(primaryKey, docUniqueKey);
			keyModificationTimestamp.put(attribute, responseAttri);
			
			modificationTimestamps.put(keyModificationTimestamp);
		}
		return modificationTimestamps;
	}
	
	/**----------------------------------------------------------------------------------------------------------- **/	
	/** ALL GRAPH QL METHODS **/	
	/**----------------------------------------------------------------------------------------------------------- **/	

	public static JSONArray executeGraphQLQuery(String graphQlQuery , String graphQlMethod) {  //EXECUTING GRAPHQL QUERY
		RequestSpecification requestSpecification;
		requestSpecification = graphQlRequestSpec();
		RequestSpecification httpRequest = RestAssured.given();
		
		requestSpecification.body(graphQlQuery);
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.post();
		String responseBody = response.getBody().asString();
		logger.info("graph QL :"+responseBody);
		JSONArray data = new JSONObject(responseBody).getJSONObject("data").getJSONObject(graphQlMethod).getJSONArray("returnFields");
		return data;
	}

	public static JSONArray fetchGraphQlResponse(JSONArray modificationTimestamps, String documentType, String graphQlMethod, String primaryKey, String returnField) {
		JSONArray graphQlReturnValue = new JSONArray();                                         //FETCHING WHOLE GRAPHQL RESPONSE
		ArrayList<String> returnFields = new ArrayList<String>(){{add(returnField); add(primaryKey);}};
		for (int key=0 ; key<modificationTimestamps.length() ; key++) {
			String value = modificationTimestamps.getJSONObject(key).get(primaryKey).toString();
			String graphQlQuery = GraphQlQueries.graphQlQuery(primaryKey, value, returnFields, documentType);
			JSONArray graphQlResponse = CommonUtilityMethods.executeGraphQLQuery(graphQlQuery, graphQlMethod);
			for (int obj=0; obj<graphQlResponse.length(); obj++) {
				graphQlReturnValue.put(graphQlResponse.getJSONObject(obj));
			}
		}
		return graphQlReturnValue;
	}

	public static String fetchGraphQlResponseForSingleField(String docType, String docName, String graphQlMethod, String primaryKey, ArrayList<String> returnField) {
		ArrayList<String> resultattributes = new ArrayList<String>();                          //FETCHING SINGLE FIELD GRAPHQL RESPONSE
		String graphQlQuery = GraphQlQueries.graphQlQuery(primaryKey, docName, returnField, docType);
		logger.info("graph Ql Query:"+graphQlQuery);
		JSONArray graphQlResponse = CommonUtilityMethods.executeGraphQLQuery(graphQlQuery, graphQlMethod);
		logger.info("graph QL Response: "+graphQlResponse);
		JSONObject graphQlResponseOBJ = new JSONObject();
		for(int j=0;j<graphQlResponse.length();j++) {
			graphQlResponseOBJ =  (JSONObject) graphQlResponse.get(j); 
		}
		logger.info("graph QL response matched:"+graphQlResponseOBJ);
		String result = graphQlResponseOBJ.getString(returnField.get(0));	
		resultattributes.add(result);
		return result;
	}
	
	public static JSONArray fetchGraphQlOfficeResponseUsingMemberMlsID(String docType, String docName, String graphQlMethod,ArrayList<String> returnField) {
		String graphQlQuery = GraphQlQueries.getMemberKeyUsingMlsId(docName, returnField, docType);   //FETCHING  GRAPHQL RESPONSE FOR OFFICE DATA
		logger.info("graph Ql Query:"+graphQlQuery);
		JSONArray graphQlResponse = CommonUtilityMethods.executeGraphQLQuery(graphQlQuery, graphQlMethod);
		logger.info("graph QL Response: "+graphQlResponse);
		return graphQlResponse;
	}
	
	public static JSONArray fetchGraphQlTrueOfficeExclusiveListings(String docType,String graphQlMethod) {
		ArrayList<String> returnFields = new ArrayList<String>(Arrays.asList("listingKey","fullStreetAddress","officeExclusiveYN"));
		String graphQlQuery = GraphQlQueries. getListingsForTrueOfficeExclusiveYN(returnFields, docType);   //FETCHING  GRAPHQL RESPONSE FOR TRUE OFFICE EXCLUSIVE YN LISTINGS
		logger.info("graph Ql Query:"+graphQlQuery);
		JSONArray graphQlResponse = CommonUtilityMethods.executeGraphQLQuery(graphQlQuery, graphQlMethod);
		logger.info("graph QL Response: "+graphQlResponse);
		return graphQlResponse;
	}
	
	
 
 
	public static ArrayList<String> fetchGraphQlResponseByDocName(String docType, String docName, String graphQlMethod, String primaryKey, ArrayList<String> returnFields){
		ArrayList<String> resultattributes = new ArrayList<String>();                         //FETCHING GRAPHQL RESPONSE BY DOCNAME
		String graphQlQuery = GraphQlQueries.graphQlQuery(primaryKey, docName, returnFields, docType);
		JSONArray graphQlResponse = CommonUtilityMethods.executeGraphQLQuery(graphQlQuery, graphQlMethod);
		JSONObject graphQlResponseOBJ = new JSONObject();
		for(int j=0;j<graphQlResponse.length();j++) {
			graphQlResponseOBJ =  (JSONObject) graphQlResponse.get(j); 
		}
		logger.info("Graphql response Obj is - "+graphQlResponseOBJ);
		for(int i=0; i<returnFields.size(); i++) {
			if(returnFields.get(i).contains("{key}")) {
				String fieldWithoutKey = returnFields.get(i).replace("{key}", "");
				Object propKey = graphQlResponseOBJ.get(fieldWithoutKey);
				String propKeystr = ((JSONObject) propKey).get("key").toString();
				resultattributes.add(propKeystr);
			}
			else if(returnFields.get(i).contains("{value}")){
				String fieldWithoutValue = returnFields.get(i).replace("{value}", "");
				Object propValue = graphQlResponseOBJ.get(fieldWithoutValue);
				String propValuestr = ((JSONObject) propValue).get("value").toString();
				resultattributes.add(propValuestr);
			}
			else {
				if(!graphQlResponseOBJ.has(returnFields.get(i))){
					Assert.fail(returnFields.get(i) +" was not found in the graphql response object for this graphql query - "+graphQlQuery);
				}
				else {
				String result = graphQlResponseOBJ.get(returnFields.get(i)).toString();	
				resultattributes.add(result);
				}
			}
		}
		return resultattributes;
	}
	
	public static ArrayList<String> fetchGraphQlResponseByDocNameAndSecondAttributeForLAE(String docType, String docName, String graphQlMethod, String primaryKey, String nestedKey,ArrayList<String> returnFields,String secondAttribute){
		ArrayList<String> resultattributes = new ArrayList<String>();                         //FETCHING GRAPHQL RESPONSE BY DOCNAME
		String graphQlQuery = GraphQlQueries.nestedGraphQlQueryForLAE(primaryKey,nestedKey, docName,secondAttribute, returnFields, docType);
		JSONArray graphQlResponse = CommonUtilityMethods.executeGraphQLQuery(graphQlQuery, graphQlMethod);
		JSONObject graphQlResponseOBJ = new JSONObject();
		for(int j=0;j<graphQlResponse.length();j++) {
			graphQlResponseOBJ =  (JSONObject) graphQlResponse.get(j); 
		}
		for(int i=0; i<returnFields.size(); i++) {
			if(returnFields.get(i).contains("{key}")) {
				String fieldWithoutKey = returnFields.get(i).replace("{key}", "");
				Object propKey = graphQlResponseOBJ.get(fieldWithoutKey);
				String propKeystr = ((JSONObject) propKey).get("key").toString();
				resultattributes.add(propKeystr);
			}
			else if(returnFields.get(i).contains("{value}")){
				String fieldWithoutValue = returnFields.get(i).replace("{value}", "");
				Object propValue = graphQlResponseOBJ.get(fieldWithoutValue);
				String propValuestr = ((JSONObject) propValue).get("value").toString();
				resultattributes.add(propValuestr);
			}
			else if(returnFields.get(i).contains("{displayValueLong}")){
				String fieldWithoutlongValue = returnFields.get(i).replace("{displayValueLong}", "");
				Object proplongValue = graphQlResponseOBJ.get(fieldWithoutlongValue);
				String proplongValuestr = ((JSONObject) proplongValue).get("displayValueLong").toString();
				resultattributes.add(proplongValuestr);
			}
			else {
				String result = graphQlResponseOBJ.get(returnFields.get(i)).toString();	
				resultattributes.add(result);
			}
		}
		return resultattributes;
	}
	
	public static ArrayList<String> fetchGraphQLByStatusAndPropertyType(String docType, ArrayList<String> returnFields,String status,String propertyType){
		ArrayList<String> resultattributes = new ArrayList<String>();                          //FETCHING GRAPHQL RESPONSE BY STATUS AND PROPERTYTYPE
		String graphQlQuery = GraphQlQueries.getListingKeyFromStatusAndPropertyType( returnFields, docType,status,propertyType);
		
		JSONArray graphQlResponse = CommonUtilityMethods.executeGraphQLQuery(graphQlQuery, GraphQlQueries.graphQlMethods.get(docType));
		JSONObject graphQlResponseOBJ = new JSONObject();
		
		//fetching the first listingKey
			graphQlResponseOBJ =  (JSONObject) graphQlResponse.get(0); 
		
		for(int i=0; i<returnFields.size(); i++) {
			
				String result = graphQlResponseOBJ.get(returnFields.get(i)).toString();	
				resultattributes.add(result);
			
		}
		return resultattributes;
	}
	 
	public static ArrayList<String> fetchGraphQLBydocKey(String docType, ArrayList<String> returnFields){
		ArrayList<String> resultattributes = new ArrayList<String>();                          //FETCHING GRAPHQL RESPONSE BY DOC-KEY
		String graphQlQuery = GraphQlQueries.getDocTypeKeys( returnFields, docType);
		
		JSONArray graphQlResponse = CommonUtilityMethods.executeGraphQLQuery(graphQlQuery, GraphQlQueries.graphQlMethods.get(docType));
		JSONObject graphQlResponseOBJ = new JSONObject();
		
		//fetching the first key
			graphQlResponseOBJ =  (JSONObject) graphQlResponse.get(0); 
		
		for(int i=0; i<returnFields.size(); i++) {
			
				String result = graphQlResponseOBJ.get(returnFields.get(i)).toString();	
				resultattributes.add(result);
			
		}
		return resultattributes;
	}
	
	public static String fetchGraphQlResponseUsingMemberMlsID(String docType, String docName, String graphQlMethod,ArrayList<String> returnField) {
		ArrayList<String> resultattributes = new ArrayList<String>();                          //FETCHING SINGLE FIELD GRAPHQL RESPONSE
		String graphQlQuery = GraphQlQueries.getMemberKeyUsingMlsId(docName, returnField, docType);
		logger.info("graph Ql Query:"+graphQlQuery);
		JSONArray graphQlResponse = CommonUtilityMethods.executeGraphQLQuery(graphQlQuery, graphQlMethod);
		logger.info("graph QL Response: "+graphQlResponse);
		JSONObject graphQlResponseOBJ = new JSONObject();
		for(int j=0;j<graphQlResponse.length();j++) {
			graphQlResponseOBJ =  (JSONObject) graphQlResponse.get(j); 
		}
		logger.info("graph QL response matched:"+graphQlResponseOBJ);
		String result = graphQlResponseOBJ.get(returnField.get(0)).toString();	
		resultattributes.add(result);
		return result;
	}
	
	/**----------------------------------------------------------------------------------------------------------- **/
	/** ALL METHODS FOR GETTING CREDENTIALS, ENVIRONMENT AND REGION**/	
	/**----------------------------------------------------------------------------------------------------------- **/	
	
	public static String getEnvironment() {      //GETTING ENVIRONMENT FROM SYSTEM, IF NOT FOUND, FROM CONFIG 
		String env=null;
		if(System.getProperty("env")!=null) {
			if(System.getProperty("env").equalsIgnoreCase("dev"))
				env="d1";

			if(System.getProperty("env").equalsIgnoreCase("test"))
				env="t1";

			if(System.getProperty("env").equalsIgnoreCase("prod"))
				env="p1";
		}
		else {
			env = FileUtility.getValueFromConfig("env");
		}
		return env;
	}

	public static String getRegionString() {      //GETTING REGION FROM SYSTEM, IF NOT FOUND, FROM CONFIG 
		String region=null;
		String regionName = System.getProperty("region")!=null ? System.getProperty("region") : FileUtility.getValueFromConfig("region");

		if(regionName.contains("east"))
			region="e1";

		if(regionName.contains("west"))
			region="w2";

		return region;
	}

	public static Region getAWSRegion() {         //GETTING AWS REGION FROM SYSTEM, IF NOT FOUND, FROM CONFIG 
		Region region = null;
		String regionName;

		regionName = System.getProperty("region")!=null ? System.getProperty("region") : FileUtility.getValueFromConfig("region");

		if(regionName.contains("east"))
			region=Region.US_EAST_1;

		if(regionName.contains("west"))
			region=Region.US_WEST_2;

		return region;
	}

	public static void getUserCredentails() {     //GETTING CSADMIN USER CREDENTIALS
		String credentails = null;
		credentails = fetchXApiKey("csAdmSvcUserCredentials");
		if(credentails==null) {
			user = FileUtility.getValueFromConfig("CsAdmSVCuser");
			password=FileUtility.getValueFromConfig("CsAdmSVCPassword");
		}
		else {
			JSONObject jsonObject = new JSONObject(credentails);
			user = jsonObject.getString("user");
			password=jsonObject.getString("password");
		}
	}	
	
	public static String getRetsCredentials(String condition, String locale,String env) {
		String credentials = null;                  //GETTING RETS USER CREDENTIALS
		credentials = fetchXApiKey("retsCredentials");
		
		if(credentials==null) {
			if(condition.equals("user")) {
				res = FileUtility.getValueFromConfig(env+"retsUser"+locale);		
	         }
			else if(condition.equals("password")) {
				res = FileUtility.getValueFromConfig(env+"retsPass"+locale);
			}		
		}
		else {
			JSONObject jsonObject = new JSONObject(credentials);
			if(locale.equalsIgnoreCase("Bright")) {
			if(condition.equals("user")) {
			res = jsonObject.getString("brightUser");
			}
			else if(condition.equals("password")){
			res =jsonObject.getString("brightPass");
			}
		}
			else if(locale.equalsIgnoreCase("Wirex")) {
			if(condition.equals("user")) {
			res = jsonObject.getString("wirexUser");
			}
			else if(condition.equals("password")){
			res =jsonObject.getString("wirexPass");
			}	
		}
	}
	return res;
}
	
	public static int generateRandomNo(int max, int min) {
		
	     return min + (int) (Math.random() * (max - min + 1));
	
}
	public static ArrayList<String> fetchCityKeyUsingCounty(String docType,String city,String county){
		
		ArrayList<String> cityAttribute = new ArrayList<String>(Arrays.asList("ctyCityKey"));
		ArrayList<String> resultattributes = new ArrayList<String>();                         
		String graphQlQuery = GraphQlQueries.getCitykeyUsingCountyValue( cityAttribute, docType,city,county);
		
		JSONArray graphQlResponse = CommonUtilityMethods.executeGraphQLQuery(graphQlQuery, GraphQlQueries.graphQlMethods.get(docType));
		JSONObject graphQlResponseOBJ = new JSONObject();
		
		if(graphQlResponse.length()>0) {
			//fetching the first listingKey
			graphQlResponseOBJ =  (JSONObject) graphQlResponse.get(0); 
		
		for(int i=0; i<cityAttribute.size(); i++) {
			
				String result = graphQlResponseOBJ.get(cityAttribute.get(i)).toString();	
				resultattributes.add(result);
			
		}
		return resultattributes;
		}
		
		else
			return null;
		
	}
	public static Timestamp convertUTC(String time) {
		Timestamp ts=null;
		 String output=Instant.parse( time )                
          	     .atOffset( ZoneOffset.UTC )                             
          	     .format(                                                   
          	         DateTimeFormatter.ofPattern( "uuuu-MM-dd HH:mm:ss" )  );;
          	        ts = Timestamp.valueOf(output);  
		return ts;
		
	}
	/**
	 * A method to get system toggle config
	 */
	public static boolean getFeatureToggle(String docType) {
		
		String systemToggleVal = null;
		
		
			String config = CommonUtilityMethods.fetchXApiKey("systemToggleConfig");
			JSONObject obj = new JSONObject(config);
			logger.info("values from param store is:"+config);
			logger.info("values for "+docType+"doctype  is:"+obj.getJSONObject("bright").getBoolean(docType));
			return obj.getJSONObject("bright").getBoolean(docType);

	}
}


