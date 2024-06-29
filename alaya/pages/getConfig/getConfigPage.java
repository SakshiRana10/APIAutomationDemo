package bright.api.alaya.pages.getConfig;

import java.io.File;
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
   

public class getConfigPage extends MainClassAlaya{	
	
	 static SoftAssert softAssert = new SoftAssert();
	 
	 
    public static void verifyResponseBodyForConfigAPI(String locale,String file,String response) {
    	SoftAssert softAssert = new SoftAssert();
    	String fileName = file;
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
			softAssert.assertTrue(CollectionUtils.isEqualCollection(apiInfoFields, ExpectedApiInfoFields),"Fields inside API INFO mismatched. Expected were "+ExpectedApiInfoFields+" Found were "+apiInfoFields+ " for file - "+fileName);
		}
		else 
		softAssert.fail("API info object not found in response body for config test in file - "+locale+" / "+fileName);
		
		/*verifying Message success here*/
		if(responseObj.has("message")) {
			String msg = responseObj.getString("message");
			softAssert.assertEquals(msg, "Success" , "Success message mismatch found. Expected was Success but found was "+msg);
		}
		else
		softAssert.fail("Message object not found in response body for config test in file - "+locale+" / "+fileName);
		
		/*verifying items Object here*/
		if(responseObj.has("item")) {
			String itemOBJ = responseObj.get("item").toString();
			if(itemOBJ.length()== 0)
				softAssert.fail("Item object was found empty");
		}
		
		else if(responseObj.has("items")) {
			JSONArray itemOBJ = responseObj.getJSONArray("items");
			if(itemOBJ.length()== 0)
				softAssert.fail("Items object was found empty");
		}
		else
		softAssert.fail("Item Object not found in response body for config test in file - "+locale+" / "+fileName);
		
		softAssert.assertAll();	
    }
	
	 
	public static void getFilesInsideDirectory(RequestSpecification httpRequest, String locale,File[] rootfiles) throws IOException {
		SoftAssert sa = new SoftAssert();
		for (File filename : rootfiles) {
            if (filename.isDirectory()) {
                if((filename.getName().equalsIgnoreCase("businessViews")&&filename.getParentFile().getName().equalsIgnoreCase(locale))||filename.getName().equalsIgnoreCase("picklistItems") || (filename.getName().equalsIgnoreCase(".DS_Store"))) {
                    continue;
                }
                else {
                	verifyGetConfigAPIResponseForDirectories(httpRequest,locale,filename,sa);              	
                	getFilesInsideDirectory(httpRequest,locale,filename.listFiles());
 
                }
            }
		}
		sa.assertAll();
	}		
			
	public static void verifyGetConfigAPIResponseForDirectories(RequestSpecification httpRequest,String locale,File f,SoftAssert sa) throws IOException {
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpecForObjectLayer();
		httpRequest = RestAssured.given();
		boolean dirFlag = true;
		String item = null;
		String path = null;
		 String name = null;
		 String childName = null;
		 
		 if(!f.getParentFile().getName().equalsIgnoreCase(locale)) {
			 name = f.getParentFile().getName()+"/"+f.getName();
		 }
		 else
			 name = f.getName();
		 
		 if(!name.equalsIgnoreCase("schemaDefinitions")) {
			 ArrayList<File> childFiles = new ArrayList<File>(Arrays.asList(f.listFiles()));
			 for(File childFile : childFiles) {
				 
				 if(childFile.getParentFile().getName().equalsIgnoreCase("rules"))
					 childName = childFile.getName().replace(".js", "");
				 else
				     childName = childFile.getName().replace(".json", "");
				 break;
			 }	
        requestSpecification.basePath("/config/"+locale+"/"+name+"/"+childName);	
        httpRequest.spec(requestSpecification);
        Response  res = httpRequest.get();
        Assert.assertEquals(res.getStatusCode(), ResponseCodes.SUCCESS,"Status Code mismatch found. Expected was - "+ResponseCodes.SUCCESS+" found was - "+res.getStatusCode());
		
		
		String responseBody = res.getBody().asString();
		logger.info("Response body for config get api -"+responseBody);
		
		
	    verifyResponseBodyForConfigAPI(locale,name+"/"+childName,responseBody);
		
		if(name.equalsIgnoreCase("rules")) {
 			item = new JSONObject(responseBody).get("item").toString();
	        path = Constants.gitLocalDirectoryArtifacts+"/"+locale+"/"+name+"/"+childName+".js";
		}
		else if(name.equalsIgnoreCase("denormReferenceFields")) {
			item = new JSONObject(responseBody).getJSONArray("items").toString();
	        path = Constants.gitLocalDirectoryArtifacts+"/"+locale+"/"+name+"/"+childName+".json";
		}
		
 		else {
 			logger.info("Response:"+responseBody);
 			item = new JSONObject(responseBody).get("item").toString();
	        path = Constants.gitLocalDirectoryArtifacts+"/"+locale+"/"+name+"/"+childName+".json";
 		}
		
		
  	    Path p = Path.of(path);
  	    String str = Files.readString(p);
  	    if(name.equalsIgnoreCase("rules")) 
			dirFlag = item.equalsIgnoreCase(str); 	    
		else	
		    dirFlag = CommonUtilityMethods.isJsonsEqual(item, str);
  	    
  	    if(!dirFlag) {
  	    	String mismatches=CommonUtilityMethods.jsonComparer(item, str);
  	    	sa.fail("Mismatched found for file name - " + name+"/"+childName +" and mismatches are: "+mismatches);
  	    }
  	       
  	    else   
  	      logger.info("Verified Data for file - " + name+"/"+childName);		
		
	  
	   }
	}
	
	
	
	public static void getFiles(RequestSpecification httpRequest,String locale,File[] rootfiles) throws IOException {
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpecForObjectLayer();
		httpRequest = RestAssured.given();
		String name = null;
		boolean filesFlag = true;
			
		for (File filename : rootfiles) {
			if(filename.isFile()) {
				
				if(filename.getAbsolutePath().contains(".DS"))
					continue ;
				
				  name = filename.getName().replace(".json", "");
				  requestSpecification.basePath("/config/"+locale+"/"+name);	 
			      httpRequest.spec(requestSpecification);
			      Response  res = httpRequest.get();

			      Assert.assertEquals(res.getStatusCode(), ResponseCodes.SUCCESS,"Status Code mismatch found. Expected was - "+ResponseCodes.SUCCESS+" found was - "+res.getStatusCode());
	        	  String responseBody = res.getBody().asString();
	        	   
	        	   String item = new JSONObject(responseBody).get("item").toString();
	        	   String path = Constants.gitLocalDirectoryArtifacts+"/"+locale+"/"+name+".json";
	        	   Path f = Path.of(path);
	        	   String str = Files.readString(f);
	        	   filesFlag = CommonUtilityMethods.isJsonsEqual(item, str); 
	        	   if(!filesFlag) {
	        		   String mismatches=CommonUtilityMethods.jsonComparer(item, str);
	        		   softAssert.fail("Mismatched found for file name  " + name+" and mismatches are: "+mismatches);
	        	   }
	        		  
	        	   else   
	        	      logger.info("Verified Data for file - " + name);
			
		  }
			
		}	
		softAssert.assertAll();
	}
}
