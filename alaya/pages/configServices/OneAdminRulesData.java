package bright.api.alaya.pages.configServices;


import java.io.BufferedWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.asserts.SoftAssert;


import com.google.gson.Gson;


import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.EnvSpecificMethods;
import bright.api.alaya.utils.FileUtility;
import bright.api.alaya.utils.JsonUtility;
import bright.api.alaya.utils.MainClassAlaya;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class OneAdminRulesData extends MainClassAlaya {
	protected static RequestSpecification requestSpecification = CommonUtilityMethods.OneadminSpec();
	public static HashMap<Object, Object> map=new HashMap<Object, Object>(); 
	public static String jsessionId =null;
	
	public static String user,password;
	public static String path=null;

	public static List<String> comparisonSummary = new ArrayList<>();

	enum rules {
		Name,
		CallName,
		businessViewId,
		localeId,
		executeOnlyIfChangedFlag,
		eventType,
		ruleUsageId,
		conditionalCallName,
		callName,
		ruleUsageType,
		packageName,
		executeOnClientFlag,
		executeOnServerFlag,
		executeOnValidateFlag,
		itemNumber,
		description,
		deletedFlag,
		ruleId,
		noOfArguments,
		fieldIds,
		Values;
	}

	

	public static void getRuleUsageFileForAPICall(RequestSpecification httpRequest,String path, String locale) throws IOException, ParseException {

		
		try{
			Scanner s = new Scanner(new File(path)) ;
			  while (s.hasNext()) {
				  String ruleUsage=(s.next()).replaceAll(",", "").replaceAll("]", "").replaceAll("\\[", "");

				  getFlagsFromAPI(httpRequest,ruleUsage,locale);

			  }
			}
			 catch (FileNotFoundException e) {
			  e.printStackTrace();
			}
	}
	


	public static void getFlagsFromAPI(RequestSpecification httpRequest,String ruleUsageId,String locale) throws IOException, ParseException {

		Response res;
		FileWriter fileBright = null,fileWirex = null;
	
		if(locale.equalsIgnoreCase(Constants.BrightLocale)) {

			fileBright = new FileWriter(Constants.BrightRulesFlagData+ruleUsageId+".json");
		}
		else
		{
			fileWirex = new FileWriter(Constants.WirexRulesFlagData+ruleUsageId+".json");
		}
		
		if(jsessionId==null) {
			httpRequest = RestAssured.given().auth().basic(CommonUtilityMethods.user,CommonUtilityMethods.password); 

			httpRequest.baseUri(EnvSpecificMethods.oneAdminUrlRuleFlags());
			String filter="ruId eq " +ruleUsageId+"L";
			httpRequest.queryParam("$filter",filter);
			httpRequest.queryParam("$select",Constants.oneAdminSelectQuery);
			httpRequest.queryParam("$format","json");
			res = httpRequest.get();
			jsessionId = res.getCookie("CSADM-SESSION-ID");
		}
		
		String cookie=String.format("CSADM-SESSION-ID=%s",jsessionId);

		httpRequest = RestAssured.given().auth().basic(CommonUtilityMethods.user,CommonUtilityMethods.password); 

		httpRequest.baseUri(EnvSpecificMethods.oneAdminUrlRuleFlags());
		String filter="ruId eq " +ruleUsageId+"L";
		httpRequest.queryParam("$filter",filter);
		httpRequest.queryParam("$select",Constants.oneAdminSelectQuery);
		httpRequest.queryParam("$format","json");
		httpRequest.cookie(cookie);
		Response resp = httpRequest.get();
		

		JSONObject json = new JSONObject();
		
		
		if(locale.equalsIgnoreCase(Constants.BrightLocale)) {
			fileBright.write(resp.asString());
			fileBright.close();
		}
		else
		{
			fileWirex.write(resp.asString());
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
			String  description= jsonObject.get("description") != null ? jsonObject.get("description").toString(): "" ;
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
		//softAssert.assertAll();
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
		            
		            
		            else
			        {
			        	
			        }
		        }  
		       
		        
		    } catch (NullPointerException np) { 
		    
		    	softAssert.fail("Null Pointer for locale: "+localeId +" field ID: "+fieldId + " and ruleUsageId: "+ruleUsageid +System.lineSeparator());
		    	logger.info("Null Pointer for locale: "+localeId +" field ID: "+fieldId + " and ruleUsageId: "+ruleUsageid +System.lineSeparator());
		    } 
	   
		return softAssert;
	} 

}