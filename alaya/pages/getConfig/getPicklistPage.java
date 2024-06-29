package bright.api.alaya.pages.getConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.apache.http.util.TextUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class getPicklistPage extends MainClassAlaya{




	/**
	 * A method to match picklistItems with picklistID or name 
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public static void matchItems(RequestSpecification httpRequest,String locale, String picklistParameter) throws IOException, ParseException {
		ArrayList<String> result = new ArrayList<String>();  

		JSONObject item = new JSONObject();
		SoftAssert softAssert = new SoftAssert();

		File[] files = new File(Constants.gitLocalDirectoryArtifacts+"/"+locale+"/"+"businessViews").listFiles();

		for(File f : files) {
			result = getConfigAPI(httpRequest,picklistParameter,locale);   //result have three elements - responseCode,businessViewname,responseBody	
			if(new JSONObject(result.get(2)).has("item")) {
				item = new JSONObject(result.get(2)).getJSONObject("item");
				if(item.length()>0)
				{
					break;
				}
				else
				{
					logger.info("items is blank  in selected business view .Retrying with another ");
					
				}

			}  
			else {
				logger.info("Items object was not there in BusinessView - "+ result.get(1) + "\n"+ " Retrying with another Random BusinessView ");			
			}
		}
		String BusinessVfile = Constants.gitLocalDirectoryArtifacts+"/"+locale+"/"+"businessViews"+"/"+result.get(1)+".json";
		logger.info("Matching is Happening in file - "+ BusinessVfile);
		JSONArray itemArray = CommonUtilityMethods.convertJSONArray(item);
		JSONArray itemsInVFile = null;
		for(int i = 0; i<itemArray.length(); i++) {
			JSONArray pickListItemIdArr = new JSONArray();
			String picklistID = itemArray.getJSONObject(i).get("pickListId").toString();
			logger.info("PicklistId from API Response is - " + picklistID);
			if(picklistID!=null) {
				JSONObject pickListobj = itemArray.getJSONObject(i).getJSONObject("pickListItems");
				
				JSONArray pickListarr = CommonUtilityMethods.convertJSONArray(pickListobj);
				for (Object o : pickListarr) {
					JSONObject oJson = (JSONObject) o;
					String plItem = oJson.get("picklistItemId").toString();
					long plItemID = Long.parseLong(plItem);
					pickListItemIdArr.put(plItemID);
				} 

				Path p = Path.of(BusinessVfile);
				String str = Files.readString(p);
				JSONObject fieldsObj = CommonUtilityMethods.convertStrToObj(str).getJSONObject("fields");
				JSONArray fieldsObjarr = CommonUtilityMethods.convertJSONArray(fieldsObj);
				
				for (Object key : fieldsObjarr) {
					JSONObject keyJson = (JSONObject) key;
					String plId = keyJson.get("pickListId").toString();
					if(plId.equalsIgnoreCase(picklistID)) {
						logger.info("Matched PicklistId from Artifacts Repo is - " + plId);
					
						if(keyJson.length()>0 && !keyJson.get("pickListItems").equals(null)) {
						
							itemsInVFile = (JSONArray) keyJson.get("pickListItems");		
							break;
						}
						else 
							logger.error("No Picklist items Found");

					}
				}
			}
			
			if(CommonUtilityMethods.compareArrays(pickListItemIdArr, itemsInVFile)==0) 
				logger.info("Item in API response for picklist ID - "+picklistID+" matched with Repo file");  	    
			else 
				softAssert.fail("Items in API response were - "+pickListItemIdArr+ " Whereas Items in Repo File were - "+ itemsInVFile);	    	
		}
		softAssert.assertAll();
	}


	/**
	 * A method to get random businessview name
	 */
	public static String getBusinessViewName(String locale) {
		int index;
		File[] files = new File(Constants.gitLocalDirectoryArtifacts+"/"+locale+"/"+"businessViews").listFiles();
		logger.info("files length is - "+files.length);
		Random rand = new Random();
		index = CommonUtilityMethods.generateRandomNo(1,(files.length)-1);
		logger.info("Calculated Index is - "+index);
		while(index<0) {
			index = CommonUtilityMethods.generateRandomNo(1,(files.length)-1);
			logger.info("Calculated Index found to be negative .. calculating again and now index is - "+index);
		}
		File file = files[index];
		String fileName = file.getName().replace(".json", "");	
		logger.info("File name is -"+fileName);
		return fileName;		
	}

	/**
	 * A method to call get config api
	 */
	public static ArrayList<String> getConfigAPI(RequestSpecification httpRequest,String parameter,String locale) {
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpecForObjectLayer();
		httpRequest = RestAssured.given();	
		ArrayList<String> resultant = new ArrayList<String>(); //resultant have three values - responseCode,businessViewName,responseBody
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"getConfig").getString("getConfig").toString());	
		String businessviewName = getBusinessViewName(locale);
		requestSpecification.pathParam("systemLocale", locale);
		requestSpecification.pathParam("businessViewName", businessviewName);
		requestSpecification.pathParam("parameter", parameter);	
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.get();
		int code = response.getStatusCode();
		String codeStr = String.valueOf(code);
		resultant.add(codeStr);
		resultant.add(businessviewName);
		resultant.add(response.getBody().asString());
		return resultant;
	}


	/**
	 * A method to call get config api with picklist id and name
	 */
	public static Response getConfigAPIWithPicklistIdAndName(RequestSpecification httpRequest,String parameter, String pickListIDAndName,String locale,String businessView) {
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpecForObjectLayer();
		httpRequest = RestAssured.given();
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"getConfig").getString("getConfigPicklistIdAndName").toString());	
		requestSpecification.pathParam("systemLocale", locale);
		requestSpecification.pathParam("businessViewName", businessView);
		requestSpecification.pathParam("parameter", parameter);
		requestSpecification.pathParam("picklistIdAndName", pickListIDAndName);
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.get();
		return response;
	}



	/**
	 * A method to verify get picklist success status
	 * @throws InterruptedException 
	 * @throws IOException 
	 */

	public static void getConfigForPicklist(RequestSpecification httpRequest,String locale,int code,String picklistParameter) throws InterruptedException, IOException{
		ArrayList<String> result = new ArrayList<String>();  

		SoftAssert softAssert = new SoftAssert();

		result = getConfigAPI(httpRequest,picklistParameter,locale);   //result have three elements - responseCode,businessViewname,responseBody

		if(Integer.parseInt(result.get(0))!= code )
			softAssert.fail("GetConfig API response for picklists is "+Integer.parseInt(result.get(0))+" But Expected is "+ code + " for locale "+ locale +" and businessView " + result.get(1)); 	    
		else
			logger.info("Verified " + code + " for get config api" + " for locale "+ locale + " and businessView " + result.get(1));
		softAssert.assertAll();
	}


	/**
	 * A method to verify get Picklist reponse body structure
	 * @throws InterruptedException 
	 */

	public static void verifyResponseBody(RequestSpecification httpRequest,String locale,int code,String picklistParameter) throws InterruptedException{
		ArrayList<String> result = new ArrayList<String>();  
		JSONObject item = new JSONObject();
		File[] files = new File(Constants.gitLocalDirectoryArtifacts+"/"+locale+"/"+"businessViews").listFiles();

		SoftAssert softAssert = new SoftAssert();

		for(File f : files) {
			result = getConfigAPI(httpRequest,picklistParameter,locale);   //result have three elements - responseCode,businessViewname,responseBody	
			if(new JSONObject(result.get(2)).has("item")) {
				item = new JSONObject(result.get(2)).getJSONObject("item");
				break;
			}  
			else {
				logger.info("Items object was not there in BusinessView - "+ result.get(1) + "\n"+ " Retrying with another Random BusinessView ");			
			}
		}

		result = getConfigAPI(httpRequest,picklistParameter,locale);   //result have three elements - responseCode,businessViewname,responseBody

		if(Integer.parseInt(result.get(0))!= code )
			softAssert.fail("GetConfig API response for picklists is "+Integer.parseInt(result.get(0))+" But Expected is "+ code + " for locale "+ locale +" and businessView " + result.get(1)); 	    
		else
			logger.info("Verified " + code + " for get config api " + " for locale "+ locale + " and businessView " + result.get(1));

		if(new JSONObject(result.get(2)).has("apiInfo"))
			logger.info("Verified API Info object in response body");
		else
			softAssert.fail("Api Info object is not found in " + result.get(1));

		if(new JSONObject(result.get(2)).has("item"))
			logger.info("Verified Item object in response body");
		else
			softAssert.fail("Item object is not found in " + result.get(1));

		softAssert.assertAll();
	}

	/**
	 * A method to verify get Picklist reponse with picklist id or name
	 * @throws InterruptedException 
	 */

	public static void verifyResponseWithPickListIDOrName(RequestSpecification httpRequest,String locale,String nameOrId,int code,String picklistParameter) throws InterruptedException{
		ArrayList<String> result = new ArrayList<String>();  
		JSONArray itemArray = new JSONArray();	
		JSONObject item = new JSONObject();
		boolean havePickLists=true;
		String NoPicklistBusinessView;
		File[] files = new File(Constants.gitLocalDirectoryArtifacts+"/"+locale+"/"+"businessViews").listFiles();

		SoftAssert softAssert = new SoftAssert();

		for(File f : files) {
			result = getConfigAPI(httpRequest,picklistParameter,locale);  
			logger.info("result is:"+result);
			//result have three elements - responseCode,businessViewname,responseBody	
			if(new JSONObject(result.get(2)).has("item")) {
				item = new JSONObject(result.get(2)).getJSONObject("item");
				if(item.length()>0)
				{
					break;
				}
				else
				{
					logger.info("No Picklist found in selected business view .Retrying with another ");
					havePickLists=false;
					NoPicklistBusinessView=result.get(1);
				}
			}  
			else {
				logger.info("Items object was not there in BusinessView - "+ result.get(1) + "\n"+ " Retrying with another Random BusinessView ");			
			}
		}
		itemArray = CommonUtilityMethods.convertJSONArray(item);

		logger.info("items array is :"+itemArray);
		
		
		
		int arraySize=itemArray.length()-1;

		String pickListIdOrName = itemArray.getJSONObject(CommonUtilityMethods.generateRandomNo(0,arraySize)).get(nameOrId).toString();  	
		logger.info("picklistItems is :"+pickListIdOrName);
		Response response2 = getConfigAPIWithPicklistIdAndName(httpRequest,picklistParameter,pickListIdOrName,locale,result.get(1));
		if(response2.getStatusCode() == code )
			logger.info("Verified " + code + " for get config api " + " for locale "+ locale + " , businessView " + result.get(1) + " And picklist ID/Name "+ pickListIdOrName);	
		else
			softAssert.fail("GetConfig API response for picklists is "+response2.getStatusCode()+" But Expected is "+ code + " for locale "+ locale +" and businessView " + result.get(1)+ " And picklist ID/Name "+ pickListIdOrName); 	    

		softAssert.assertAll();
	}


	/**
	 * A method to verify get Picklist reponse with picklist item id
	 * @throws InterruptedException 
	 */

	public static void verifyResponseWithPickListitemID(RequestSpecification httpRequest,String locale,String nameOrId,int code,String picklistParameter) throws InterruptedException{
		ArrayList<String> result = new ArrayList<String>();  
		JSONArray itemArray = new JSONArray();
		JSONArray picklistItemsArray = new JSONArray();
		JSONObject item = new JSONObject();
		boolean havePickLists=true;
		String NoPicklistBusinessView;
		File[] files = new File(Constants.gitLocalDirectoryArtifacts+"/"+locale+"/"+"businessViews").listFiles();

		SoftAssert softAssert = new SoftAssert();

		for(File f : files) {
			result = getConfigAPI(httpRequest,picklistParameter,locale);
			logger.info("result is:"+result);
			//result have three elements - responseCode,businessViewname,responseBody	
			if(new JSONObject(result.get(2)).has("item")) {
				item = new JSONObject(result.get(2)).getJSONObject("item");
				if(item.length()>0)
				{
					break;
				}
				else
				{
					logger.info("No Picklist found in selected business view .Retrying with another ");
					havePickLists=false;
					NoPicklistBusinessView=result.get(1);
				}

			}  
			else {
				logger.info("Items object was not there in BusinessView - "+ result.get(1) + "\n"+ " Retrying with another Random BusinessView ");			
			}
		}
		
		if(!havePickLists) {
			//will check with artifacts
			
		}
		itemArray = CommonUtilityMethods.convertJSONArray(item);
		int arraySize=itemArray.length()-1;
        logger.info("items array is :"+itemArray);
		JSONObject picklistItems = new JSONObject();
		//getPicjlist check should be added
		picklistItems = itemArray.getJSONObject(CommonUtilityMethods.generateRandomNo(1,arraySize)).getJSONObject("pickListItems");
		logger.info("picklistItems is :"+picklistItems);
		picklistItemsArray = CommonUtilityMethods.convertJSONArray(picklistItems);
		int pickListArraySize=picklistItemsArray.length()-1;
		String pickListItemID = picklistItemsArray.getJSONObject(CommonUtilityMethods.generateRandomNo(1,pickListArraySize)).get(nameOrId).toString();  	

		Response response2 = getConfigAPIWithPicklistIdAndName(httpRequest,"picklistItems",pickListItemID,locale,result.get(1));
		if(response2.getStatusCode() == code )
			logger.info("Verified " + code + " for get config api " + " for locale "+ locale + " , businessView " + result.get(1) + " And picklistItem ID "+ pickListItemID);	
		else
			softAssert.fail("GetConfig API response for picklists is "+Integer.parseInt(result.get(0))+" But Expected is "+ code + " for locale "+ locale +" and businessView " + result.get(1)+ " And picklistItem ID "+ pickListItemID); 	    

		softAssert.assertAll();
	}

	/**
	 * A method to verify reponse with wrong picklist id or name
	 * @throws InterruptedException 
	 */

	public static void verifyResponseWithWrongPickListitemID(RequestSpecification httpRequest,String locale,int code,String picklistParameter) {
		ArrayList<String> result = new ArrayList<String>();  

		SoftAssert softAssert = new SoftAssert();

		result = getConfigAPI(httpRequest,picklistParameter,locale);   //result have three elements - responseCode,businessViewname,responseBody	

		String pickListItemID = "wrong";                   //giving wrong picklistitem ID 
		Response response2 = getConfigAPIWithPicklistIdAndName(httpRequest,"picklistItems",pickListItemID,locale,result.get(1));
		if(response2.getStatusCode() == code )
			logger.info("Verified " + code + " for get config api " + " for locale "+ locale + " , businessView " + result.get(1) + " And picklistItem ID "+ pickListItemID);	
		else
			softAssert.fail("GetConfig API response for picklists is "+Integer.parseInt(result.get(0))+" But Expected is "+ code + " for locale "+ locale +" and businessView " + result.get(1)+ " And picklistItem ID "+ pickListItemID); 	    

		softAssert.assertAll();
	}


}

