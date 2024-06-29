package bright.api.alaya.pages.objectLayer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import bright.api.alaya.pages.getConfig.getPicklistPage;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.MainClassAlaya;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ValidateAPIPage extends MainClassAlaya {
	
	

	
	
	public static String postValidateAPI(String locale , String docType,String payload, RequestSpecification httpRequest) {
		
		
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpecForObjectLayer();
		 httpRequest = RestAssured.given();
		requestSpecification.basePath("/object/validate/{systemLocale}/{documentType}");
		requestSpecification.pathParam("systemLocale", locale);
		requestSpecification.pathParam("documentType", docType);
		requestSpecification.headers("x-session-key",sessionKey);   
		requestSpecification.body(payload);
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.post();
		String responseBody = response.getBody().asString();
		logger.info("Response from validate api - "+responseBody);
		return responseBody.toString();

	}
	
      public static String postValidateAPIForByPassingRules(String locale , String docType,String payload, RequestSpecification httpRequest) {
		
		
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpecForObjectLayer();
		 httpRequest = RestAssured.given();
		requestSpecification.basePath("/object/validate/{systemLocale}/{documentType}");
		requestSpecification.pathParam("systemLocale", locale);
		requestSpecification.pathParam("documentType", docType);
		requestSpecification.headers("x-session-key",bypassSessionKey);   
		requestSpecification.body(payload);
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.post();
		String responseBody = response.getBody().asString();
		logger.info("Response from validate api - "+responseBody);
		return responseBody.toString();

	}


	public static JSONObject getValueOfKeyInPicklist(JSONObject json,String keyValue,String keyName) throws ParseException {

		JSONObject value=null;
		JSONObject result = null;
		Iterator<?> iterator = json.keys();
		while (iterator.hasNext()) {
			Object key = iterator.next();
			 value = (JSONObject) json.get(key.toString());
			
			
			String keyValueResult=value.get(keyName).toString();
			
			if(keyValueResult.equalsIgnoreCase(keyValue)) {
				result=value;
				break;
			}
				
			else
				result=null;

		}

		return result;
	}
	
	public static String getParentKeyForItemId(JSONObject json,String keyValue,String keyName) throws ParseException {

		
		JSONObject value=null;
		String parentKey=null;
		Iterator<?> iterator = json.keys();
		while (iterator.hasNext()) {
			Object key = iterator.next();
			 value = (JSONObject) json.get(key.toString());
			String keyValueResult=value.getString(keyName);
		
			if(keyValueResult.equalsIgnoreCase(keyValue)) {
				parentKey=key.toString();
				break;
			}
		}
		logger.info("Parent key - "+parentKey);
		return parentKey;
	}
	
	public static JSONObject findFirstValue(JSONObject json) throws ParseException {

		JSONObject value=null;
		Iterator<?> iterator = json.keys();
		while (iterator.hasNext()) {
			Object key = iterator.next();
			 value = (JSONObject) json.get(key.toString());
			
				break;

		}
		logger.info("First value is - "+value);
		return value;
	}
	
	
	public static List getAllRolesKey(JSONObject json) throws ParseException {
		
		
		List<String> namesList = new ArrayList<>();
	    try {
	    	JSONObject posts = (JSONObject) json.get("roles");
	        Iterator<String> stringIterator = posts.keys();
	        while (stringIterator.hasNext()) {
	            namesList.add(stringIterator.next());
	        }
	    } catch (JSONException e) {
	        e.printStackTrace();
	    }
		logger.info("Roles key list is - "+namesList);
	    return namesList;
	
	}
	
	
public static JSONObject getRoleJsonUsingAnyKey(JSONObject json,String value,String keyName) throws ParseException {
		
	JSONObject objects = null;
	JSONObject resultJson=null;
	Iterator<String> keys = json.keys();
	while(keys.hasNext()) {
	    String key = keys.next();
	    
	    	 objects=(JSONObject) json.get(key);
	    		 
	    		if(objects.get(keyName).toString().equalsIgnoreCase(value)) {
	    			resultJson=objects;
	    		}
	    }
	logger.info("Resultant json is - "+resultJson);
	   
	return resultJson;
	
	}



}
