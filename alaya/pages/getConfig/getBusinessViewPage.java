package bright.api.alaya.pages.getConfig;

import static org.testng.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
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

public class getBusinessViewPage extends MainClassAlaya{
	
	/**
	 * A method to check api response body structure
	 */
	public static void verifyResponseBodyForBusinessView(String locale,String basePathParam,String response,String businessVFilePath) {
		SoftAssert softAssert = new SoftAssert();
		ArrayList<String> apiInfoFields = new ArrayList<String>();
		ArrayList<String> ExpectedApiInfoFields = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.configAPI, "apiInfo").get("fields"));	
		JSONObject responseObj = new JSONObject(response);	
		
		/*verifying API info here*/
		if(responseObj.has("apiInfo")) {
			JSONObject apiInfo = responseObj.getJSONObject("apiInfo");
			Iterator<String> Keys = apiInfo.keys();
			while (Keys.hasNext()) 
			{
			  String fieldValue = (String)Keys.next();
			  apiInfoFields.add(fieldValue);
			}
			softAssert.assertTrue(CollectionUtils.isEqualCollection(apiInfoFields, ExpectedApiInfoFields),"Fields inside API INFO mismatched. Expected were "+ExpectedApiInfoFields+" Found were "+apiInfoFields+ " for file - "+businessVFilePath);
		}
		else 
		softAssert.fail("API info object not found in response body for businessViews in file -"+businessVFilePath);
		
		/*verifying Message success here*/
		if(responseObj.has("message")) {
			String msg = responseObj.getString("message");
			softAssert.assertEquals(msg, "Success" , "Success message mismatch found. Expected was Success but found was "+msg);
		}
		else
		softAssert.fail("Message object not found in response body for businessViews in file -"+businessVFilePath);
		
		/*verifying items Object here*/
		if(responseObj.has("item")) {
			JSONObject itemOBJ = responseObj.getJSONObject("item");
			if(itemOBJ.length()== 0)
				softAssert.fail("Item object was found empty");
		}
		else
		softAssert.fail("Item Object not found in response body for businessViews in file -"+businessVFilePath);
		
		softAssert.assertAll();	
	}
	
	
	
	
	/**
	 * A method to  get random field name
	 * @throws IOException 
	 */
	public static String getFieldName(String locale, String businessviewName) throws IOException {
		String BusinessVfile = Constants.gitLocalDirectoryArtifacts+"/"+locale+"/"+"businessViews"+"/"+businessviewName+".json";
		Path p = Path.of(BusinessVfile);
  	    String str = Files.readString(p);
  	    JSONObject fieldsObj = CommonUtilityMethods.convertStrToObj(str).getJSONObject("fields");
  	    JSONArray fieldsObjarr = CommonUtilityMethods.convertJSONArray(fieldsObj);
  	    String field = fieldsObjarr.get(CommonUtilityMethods.generateRandomNo(0,fieldsObjarr.length()-1 )).toString();
  	    String fieldName = CommonUtilityMethods.convertStrToObj(field).get("name").toString();
		return fieldName;
	}
	
	/**
	 * A method to call get config api
	 * @throws IOException 
	 */
	public static ArrayList<String> getConfigBusinessView(RequestSpecification httpRequest,String locale, String basePathParam, int expectedCode) throws IOException {
		String fieldName = null;
		ArrayList<String> resultant = new ArrayList<String>(); //resultant have three values - responseCode,businessViewName,responseBody
		RequestSpecification requestSpec;
		requestSpec = CommonUtilityMethods.requestSpecForObjectLayer();
		httpRequest = RestAssured.given();
		requestSpec.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"getConfig").getString(basePathParam).toString());	
		String businessviewName = getPicklistPage.getBusinessViewName(locale);
		requestSpec.pathParam("systemLocale", locale);
		requestSpec.pathParam("businessViewName", businessviewName);	
		if(basePathParam.equalsIgnoreCase("getFieldsName")) {
			fieldName = getFieldName(locale,businessviewName);
			requestSpec.pathParam("fieldName", fieldName);
		}
		httpRequest.spec(requestSpec);
		Response  response = httpRequest.get();
		int code = response.getStatusCode();
		Assert.assertEquals(code, expectedCode, "Status Code mismatch found. Expected was - "+expectedCode+" found was - "+code);
		String codeStr = String.valueOf(code);
		resultant.add(codeStr);
		resultant.add(businessviewName);
		resultant.add(response.getBody().asString());
		resultant.add(fieldName);
		return resultant;
		
	}
	
	/**
	 * A method to verify get config api code status
	 * @throws IOException 
	 */
	public static void verifySuccessForBusinessView(RequestSpecification httpRequest, String locale, int code, String basePathParam) throws IOException {
        ArrayList<String> result = new ArrayList<String>(); 
        SoftAssert softAssert = new SoftAssert();
		result = getConfigBusinessView(httpRequest,locale,basePathParam,code);   //result have three elements - responseCode,businessViewname,responseBody
		if(Integer.parseInt(result.get(0))!= code )
			softAssert.fail("GetConfig API response for BusinessView is "+Integer.parseInt(result.get(0))+" But Expected is "+ code + " for locale "+ locale +" and businessView " + result.get(1)); 	    
		else
		    logger.info("Verified " + code + " for get config api" + " for locale "+ locale + " and businessView " + result.get(1));
		
		softAssert.assertAll();	
		
	}
	
	/**
	 * A method to verify data for get config api 
	 */
	public static void MatchDataForBusinessView(RequestSpecification httpRequest, String locale, String ObjectToMatch, String basePathParam) throws IOException {
        ArrayList<String> result = new ArrayList<String>();  
        SoftAssert softAssert = new SoftAssert();
        JSONObject fields = new JSONObject(); 
        JSONObject fieldsObj = new JSONObject();
        for(int i=0;i<5;i++) {
		result = getConfigBusinessView(httpRequest, locale,basePathParam,ResponseCodes.SUCCESS);   //result have 4 elements - responseCode,businessViewname,responseBody,fieldName
		fields = new JSONObject(result.get(2)).getJSONObject(ObjectToMatch);
		String fieldsStr = fields.toString();
		String BusinessVfile = Constants.gitLocalDirectoryArtifacts+"/"+locale+"/"+"businessViews"+"/"+result.get(1)+".json";
		Path p = Path.of(BusinessVfile);
  	    String str = Files.readString(p);
  	    if(basePathParam.equalsIgnoreCase("getFields"))
  	      fieldsObj = CommonUtilityMethods.convertStrToObj(str).getJSONObject("fields");
  	    else if(basePathParam.equalsIgnoreCase("getFieldsName"))
  	      fieldsObj = CommonUtilityMethods.convertStrToObj(str).getJSONObject("fields").getJSONObject(result.get(3));
  	    else
  	      fieldsObj = CommonUtilityMethods.convertStrToObj(str);
  	    String fieldsObjStr = fieldsObj.toString();

  	    
        String responseForBusinessViews = result.get(2);
        verifyResponseBodyForBusinessView(locale,basePathParam,responseForBusinessViews,BusinessVfile);
        
  	    if(CommonUtilityMethods.jsonComparer(fieldsStr, fieldsObjStr) != null) 
  	    {
  	    	String mismathches=CommonUtilityMethods.jsonComparer(fieldsStr, fieldsObjStr);
  	    	softAssert.fail("API Response for fields Did not match with Repo data and mismatches are:"+mismathches);
  	    }
  	    		    	

  	    else
  	    	logger.info("API Response for fields matched with Repo data");
        }
        softAssert.assertAll();
	}
	
	/**
	 * A method to verify 404 not found for get config api for businessViews
	 * @throws IOException 
	 */
	public static void verifyNotFound(RequestSpecification httpRequest, String locale, int code, String basePathParam, String wrongValue) throws IOException {
		   String businessviewName = null;
		   String fieldName = null;
		   SoftAssert softAssert = new SoftAssert();
		   
		   RequestSpecification requestSpec;
		   requestSpec = CommonUtilityMethods.requestSpecForObjectLayer();
		   httpRequest = RestAssured.given();
		   requestSpec.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"getConfig").getString(basePathParam).toString());	
		   if(wrongValue.equalsIgnoreCase("businessViewName"))
			   businessviewName = "wrong";
		   else
			   businessviewName = getPicklistPage.getBusinessViewName(locale);
		   
		   requestSpec.pathParam("systemLocale", locale);
		   requestSpec.pathParam("businessViewName", businessviewName);	
		   
		   if(basePathParam.equalsIgnoreCase("getFieldsName")) {
			   if(wrongValue.equalsIgnoreCase("fieldsName")) {
				   fieldName = "wrong";
				   requestSpec.pathParam("fieldName", fieldName);
			   }
			   else {
				fieldName = getFieldName(locale,businessviewName);
				requestSpec.pathParam("fieldName", fieldName);
			   }
			}
		   
		    httpRequest.spec(requestSpec);
			Response  response = httpRequest.get();
			int coderes = response.getStatusCode();
			String codeStr = String.valueOf(coderes);
			   
		   if(Integer.parseInt(codeStr)!= code )
				softAssert.fail("GetConfig API response for BusinessView is "+Integer.parseInt(codeStr)+" But Expected is "+ code + " for locale "+ locale +" with Wrong values "); 	    
			else
				logger.info("Verified " + code + " for get config api" + " for locale "+ locale + " with Wrong values");
			softAssert.assertAll();
	}

	
}
