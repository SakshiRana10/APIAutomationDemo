package bright.api.alaya.pages.configServices;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.http.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import bright.api.alaya.pages.configServices.OneAdminRulesData.rules;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.EnvSpecificMethods;
import bright.api.alaya.utils.FileUtility;
import bright.api.alaya.utils.JsonUtility;
import bright.api.alaya.utils.MainClassAlaya;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;



public class RulesDataPage extends MainClassAlaya {



	static List<String> fieldIds;

	protected static RequestSpecification requestSpecification = CommonUtilityMethods.OneadminSpec();
	public static HashMap<Object, Object> map=new HashMap<Object, Object>(); 
	public static String jsessionId =null;
	public static String  RulesData =System.lineSeparator();
	static SoftAssert softAssert = new SoftAssert();
	public static String user,password;


	public static void mapFieldsIdsFromConfig(String locale) throws IOException, ParseException {

		List<String> businessViewFiles = FileUtility.GetAllFilesFromDirectory(Constants.configBusinessView);
		ArrayList<String> fields=new ArrayList();
		String fileName=null;
		if(locale.equalsIgnoreCase(Constants.BrightLocale))
			fileName=Constants.BrightFieldIdMapping;

		else
			fileName=Constants.WirexFieldIDMapping;

		FileWriter file = new FileWriter(fileName,false);
		HashMap<Object,Object> map=new HashMap<Object,Object>(); 
		HashMap<Object,Object> mapWirex=new HashMap<Object,Object>(); 
		JSONObject result = new JSONObject();
		for (String businessViewFile : businessViewFiles) {
			if(businessViewFile.contains("DS_Store")) {

				continue;
			}
			JSONObject jsonObject = JsonUtility.GetJsonFileData(businessViewFile);
			JSONObject Fields=getFieldsJson(locale,jsonObject);
			if(Fields==null)
			{
				continue;

			}
			@SuppressWarnings("unchecked")

			Set<String> keys=Fields.keySet();
			Iterator<String> value = keys.iterator();
			while (value.hasNext()) {

				String fieldId=value.next();
				JSONObject fieldsJson= (JSONObject) Fields.get(fieldId);
				ArrayList<Long> ruleUsageIds=(ArrayList<Long>) fieldsJson.get("ruleUsageIds");
				if(ruleUsageIds!=null && ruleUsageIds.size()>0) {
					data = new JSONObject();
					fields.add(fieldId);
					if(map.containsKey(fieldId)) {
						ArrayList<Long> oldValues=(ArrayList<Long>) map.get(fieldId);
						ruleUsageIds.addAll(oldValues);
					}
					map.put(fieldId,ruleUsageIds);
				}
			}
		}
		file.write(new JSONObject(map).toString());

		file.close();
	}


	@SuppressWarnings("unchecked")

	public static JSONObject getFieldsJson(String Locale,JSONObject jsonObject) {

		JSONObject targetJson = null;
		JSONArray arr = new JSONArray();
		arr.add(jsonObject.get("locales"));
		for (int i = 0; i < arr.size(); i++)
		{
			for (Object item : ((JSONArray) arr.get(i))) {

				if (((JSONObject) item).containsKey(String.valueOf(Locale))) {
					targetJson = (JSONObject) ((JSONObject) item).get(String.valueOf(Locale));
					break;
				}
			}
			if (targetJson != null) {
				return targetJson;
			}
		}
		return targetJson;
	}


	public static void fetchFieldIdsFromFile(RequestSpecification httpRequest,String locale,String path) throws ParseException {

		String jsonString;

		try {
			Path fileName= Path.of(path);
			jsonString = Files.readString(fileName);
			Map<String, Object> result = new ObjectMapper().readValue(jsonString, new TypeReference<Map<String, Object>>(){});
			for (String key: result.keySet()) {

				writeAPIDataFromAPI(httpRequest,locale,key,CommonUtilityMethods.user,CommonUtilityMethods.password);
			}

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	public static void writeAPIDataFromAPI(RequestSpecification httpRequest,String localeId, String fieldId,String username,String csADmpassword) throws IOException, ParseException {

		Response res;
		HashMap<Object,Object> map=new HashMap<Object,Object>(); 

		FileWriter fileBright = null,fileWirex = null;
		String path;

		if(localeId.equalsIgnoreCase(Constants.BrightLocale)) {
			fileBright = new FileWriter(System.getProperty("user.dir")+"/resources/testdata/BrightOneadmin/RulesData/"+fieldId+".json");
		}
		else
		{
			fileWirex = new FileWriter(System.getProperty("user.dir")+"/resources/testdata/WirexOneadmin/RulesData/"+fieldId+".json");
		}

		if(jsessionId==null) {

			httpRequest = RestAssured.given().auth().basic(username,csADmpassword); 
			httpRequest.baseUri(EnvSpecificMethods.oneAdminUrl());
			httpRequest.queryParam("localeId",localeId);
			httpRequest.queryParam("laId",fieldId);
			res = httpRequest.get();
			jsessionId = res.getCookie("CSADM-SESSION-ID");

		}
		httpRequest = RestAssured.given().auth().basic(username,csADmpassword); 
		String cookie=String.format("CSADM-SESSION-ID=%s",jsessionId);
		httpRequest.baseUri(EnvSpecificMethods.oneAdminUrl());
		httpRequest.queryParam("localeId",localeId);
		httpRequest.queryParam("laId",fieldId);
		httpRequest.cookie(cookie);
		res=  httpRequest.get();
		

		if(localeId.equalsIgnoreCase(Constants.BrightLocale)) {
			fileBright.write(res.asString());
			fileBright.close();
		}
		else
		{
			fileWirex.write(res.asString());
			fileWirex.close();
		}


	}
	
	public static SoftAssert getRulesJson(String path, String locale,SoftAssert softAssert) throws IOException, ParseException {

		List<String> rulesFiles = FileUtility.GetAllFilesFromDirectory(path);

		for (String rulesFile : rulesFiles) {

			if(rulesFile.contains("DS_Store")) {
				continue;
			}
			
			logger.info("------------Picked :" +locale +"  "+rulesFile );
			
			JSONObject jsonObject = JsonUtility.GetJsonFileData(rulesFile);
	
			String localeId= jsonObject.get("localeId").toString();
			if(!localeId.equalsIgnoreCase(locale)) {
				continue;
			}
			
			else {
			String fieldId= jsonObject.get("fieldId").toString();
			
			HashMap<Object,Object> map=new HashMap<Object,Object>(); 

			boolean executeOnlyIfChangedFlag=Boolean.parseBoolean(jsonObject.get("executeOnlyIfChangedFlag").toString()); 
			boolean executeOnClientFlag=Boolean.parseBoolean(jsonObject.get("executeOnClientFlag").toString()); 
			boolean executeOnServerFlag=Boolean.parseBoolean(jsonObject.get("executeOnServerFlag").toString()); 
			boolean executeOnValidateFlag=Boolean.parseBoolean(jsonObject.get("executeOnValidateFlag").toString()); 
			boolean deletedFlag=Boolean.parseBoolean(jsonObject.get("deletedFlag").toString()); 
			String  description= jsonObject.get("description")!=null ? jsonObject.get("description").toString(): "" ;
			String  ruleId= jsonObject.get("ruleId").toString() ;
			String  ruleUsageId= jsonObject.get("ruleUsageId").toString() ;
			
			map.put(rules.executeOnlyIfChangedFlag, executeOnlyIfChangedFlag);
			map.put(rules.executeOnClientFlag, executeOnClientFlag);
			map.put(rules.executeOnServerFlag, executeOnServerFlag);
			map.put(rules.executeOnValidateFlag, executeOnValidateFlag);
			map.put(rules.deletedFlag, deletedFlag);
			map.put(rules.ruleId, ruleId);
			map.put(rules.ruleUsageId,ruleUsageId);
			map.put(rules.description, description);
			
			HashMap<Object,Object>responseData=APIJsonReadPage.getFlagData(localeId,ruleUsageId); 
			if(responseData==null) {
				softAssert.fail("No data found in one admin for locale: "+localeId +" field ID: "+fieldId + " and ruleUsageId: "+ ruleUsageId+System.lineSeparator() );
				logger.error("No data found in one admin for locale: "+localeId +" field ID: "+fieldId + " and ruleUsageId: "+ ruleUsageId+System.lineSeparator()); 
			}
			else
				
			mapsAreEqual(map,responseData,localeId,fieldId,ruleUsageId,softAssert);
			
			
		}
		}
		return softAssert;
	}

	
	
	public static SoftAssert  getRulesData(String path, String locale,SoftAssert softAssert) throws IOException, ParseException {

		List<String> rulesFiles = FileUtility.GetAllFilesFromDirectory(path);
		for (String rulesFile : rulesFiles) {

			if(rulesFile.contains("DS_Store")) {
				continue;
			}
			
			logger.info("------------Picked :" +locale +"  "+rulesFile );
			
			JSONObject jsonObject = JsonUtility.GetJsonFileData(rulesFile);
	
			String localeId= jsonObject.get("localeId").toString();
			String lovId= jsonObject.get("businessViewId").toString();
			
			if(!localeId.equalsIgnoreCase(locale)) {
				continue;
			}
			
			else {
				
			String fieldId= jsonObject.get("fieldId").toString();
			
			HashMap<Object,Object> map=new HashMap<Object,Object>(); 
			
			
			String name =  jsonObject.get("name").toString() ;
			String callName =  jsonObject.get("callName") != null ? jsonObject.get("callName").toString(): null ;
			String eventType=jsonObject.get("eventType").toString() ;
			String conditionalCallName = jsonObject.get("conditionalCallName") != null?   jsonObject.get("conditionalCallName").toString() : "";
			String  ruleUsageType= jsonObject.get("ruleUsageType").toString() ;
			String  packageName= jsonObject.get("packageName").toString() ;
			String  itemNumber= jsonObject.get("itemNumber").toString() ;
			
			String  ruleUsageId= jsonObject.get("ruleUsageId").toString() ;
			JSONArray getArray =  (JSONArray) jsonObject.get("ruleUsageArguments");
			ArrayList<String> fieldIdList=new ArrayList();
			ArrayList<String> Value=new ArrayList();
			for(int i = 0; i < getArray.size(); i++)
			{
				JSONObject objects = (JSONObject) getArray.get(i);
				JSONArray fields=(JSONArray) objects.get("fieldIds");
				if(fields!=null)
				fieldIdList.add(fields.get(0).toString());
				
				else
					fieldIdList.add(null);

				String  value=objects.get("value")!=null ? objects.get("value").toString(): null;
				if(value!=null) {
					String roundOffCheck=value.length()>2 ? value.substring(0,2): value;
					
					if(roundOffCheck.equalsIgnoreCase(".0")) {
						logger.info("value finds to be 0.0. So Rounding it off ");
						value="0";
					
					}
					else if(value.substring(0,1).equalsIgnoreCase(".") && value.substring(1, value.length()).chars().allMatch(Character::isDigit) ) {
						value="0"+value;;
					}
						
					
					Value.add(value);
				}
				
				
				else
					Value.add(null);
				
			}
			
			
			map.put(rules.CallName, callName);
			map.put(rules.Name, name);
			map.put(rules.eventType, eventType);
			map.put(rules.conditionalCallName, conditionalCallName.toString());
			map.put(rules.ruleUsageType, ruleUsageType);
			map.put(rules.packageName, packageName);
			map.put(rules.itemNumber, itemNumber);
			
		
			map.put(rules.noOfArguments,getArray.size());
			map.put(rules.fieldIds, fieldIdList);
			
			map.put(rules.Values,Value.toString());
			
			HashMap<Object, Object> responseData;
			
				try {
					responseData = APIJsonReadPage.getMainData(localeId,fieldId,ruleUsageId,lovId);
					if(responseData==null) {
						softAssert.fail("No data found in one admin for locale: "+localeId +" field ID: "+fieldId + " and ruleUsageId: "+ ruleUsageId+System.lineSeparator() );
						logger.error("No data found in one admin for locale: "+localeId +" field ID: "+fieldId + " and ruleUsageId: "+ ruleUsageId+System.lineSeparator()); 
					}
					else
						
						 softAssert	=mapsAreEqual(map,responseData,localeId,fieldId,ruleUsageId,softAssert);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			
			
		}
	}
		return softAssert;
		
	}
	

	public static SoftAssert mapsAreEqual(Map<Object, Object> mapA, Map<Object, Object> mapB ,String localeId, String fieldId, String ruleUsageid,SoftAssert softAssert) { 
		 
		
		
		 try{ 
		        for (Object k : mapB.keySet()) 
		        { 
		        	
		            if (!mapA.get(k).equals(mapB.get(k))) { 
		            	
		            	
		            	softAssert.fail("Mismatch Found for locale: "+localeId +" field ID: "+fieldId + " and ruleUsageId: "+ruleUsageid +"---- Value mismatch for :"+ k+ " In config value is: "+ mapA.get(k)+" in api response: "+mapB.get(k)+System.lineSeparator());
		            	logger.error("Mismatch Found for locale: "+localeId +" field ID: "+fieldId + " and ruleUsageId: "+ruleUsageid +"---- Value mismatch for :"+ k+ " In config value is: "+ mapA.get(k)+" in api response: "+mapB.get(k)+System.lineSeparator());
		            } 
		            
		     
		            
		        }  
		        
		        
		    } catch (NullPointerException np) { 
		    
		    	softAssert.fail("Null Pointer for locale: "+localeId +" field ID: "+fieldId + " and ruleUsageId: "+ruleUsageid +System.lineSeparator());
		    	logger.error("Null Pointer for locale: "+localeId +" field ID: "+fieldId + " and ruleUsageId: "+ruleUsageid +System.lineSeparator());
		    } 
	   
		return softAssert;
	} 



public static void writeLcidMappingFromAPI(RequestSpecification httpRequest,String lovId) throws IOException, ParseException {

	Response res;
	HashMap<Object,Object> map=new HashMap<Object,Object>(); 

	FileWriter lcidMapping = null;
	String path;

	
	lcidMapping = new FileWriter(Constants.LOVMapping+lovId+".json",false);
	

	if(jsessionId==null) {

		httpRequest = RestAssured.given().auth().basic(CommonUtilityMethods.user,CommonUtilityMethods.password); 

		httpRequest.baseUri(EnvSpecificMethods.oneAdminLogicalAttribute());
		httpRequest.queryParam("lovId",lovId);
		res = httpRequest.get();
		jsessionId = res.getCookie("CSADM-SESSION-ID");

	}
	httpRequest = RestAssured.given().auth().basic(CommonUtilityMethods.user,CommonUtilityMethods.password); 

	String cookie=String.format("CSADM-SESSION-ID=%s",jsessionId);
	httpRequest.baseUri(EnvSpecificMethods.oneAdminLogicalAttribute());
	httpRequest.queryParam("lovId",lovId);
	httpRequest.cookie(cookie);
	res=  httpRequest.get();
	
		lcidMapping.write(res.asString());
		lcidMapping.close();
	
}

}