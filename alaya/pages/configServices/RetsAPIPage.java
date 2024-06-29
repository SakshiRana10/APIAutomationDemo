package bright.api.alaya.pages.configServices;

import org.json.JSONObject;
import org.testng.Assert;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.EnvSpecificMethods;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.RequestSpecification;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.*;  

public class RetsAPIPage extends MainClassAlaya {
	
	public static void getMetaData(RequestSpecification httpRequestForSessionID, RequestSpecification httpRequestForMetaData, String locale, String type, String env) throws InterruptedException, IOException {
		
		FileWriter classFile = null,lookupFile = null, searchFile = null, tableFile = null;
		
		if(type.equalsIgnoreCase("METADATA-CLASS")) {
			classFile = new FileWriter(Constants.RetsMetaData+"Class_"+"_"+locale+"_"+env+".json");	
			logger.info("file written for type-"+type);
		}	
		else if(type.equalsIgnoreCase("METADATA-LOOKUP_TYPE")) {
			lookupFile = new FileWriter(Constants.RetsMetaData+"Lookup_"+"_"+locale+"_"+env+".json");
			logger.info("file written for type-"+type);
		}	
		else if(type.equalsIgnoreCase("METADATA-SEARCH_HELP")) {
			searchFile = new FileWriter(Constants.RetsMetaData+"SearchHelp_"+"_"+locale+"_"+env+".json");	
			logger.info("file written for type-"+type);
		}	
		else if(type.equalsIgnoreCase("METADATA-TABLE")) {
			tableFile = new FileWriter(Constants.RetsMetaData+"Table_"+"_"+locale+"_"+env+".json");	
			logger.info("file written for type-"+type);
		}	
		
		httpRequestForSessionID = (RequestSpecification) RestAssured.given();
		httpRequestForSessionID.baseUri(EnvSpecificMethods.retsGetSessionId());
		httpRequestForSessionID.header("User-Agent","CMMS/1.0").header("RETS-Version","RETS/1.8");
		httpRequestForSessionID.contentType(ContentType.JSON);
		String user = CommonUtilityMethods.getRetsCredentials("user",locale,env);
		logger.info("successfully getting the user:"+user);
		String pass = CommonUtilityMethods.getRetsCredentials("password",locale,env);
		httpRequestForSessionID.auth().digest(user, pass);
		Response  responseforSessionID = httpRequestForSessionID.get();
		
		Assert.assertEquals(responseforSessionID.getStatusCode(), ResponseCodes.SUCCESS);		
		logger.info("--Getting session ID--");
		String sessionID = responseforSessionID.getCookie("RETS-Session-ID").toString();
		
      
		httpRequestForMetaData = (RequestSpecification) RestAssured.given();	
		httpRequestForMetaData.baseUri(EnvSpecificMethods.retsGetMetaData());	
		String ID = CommonUtilityMethods.readJsonFile(Constants.retsPath,"getMetaData").getString("ID");
		String Type = type;
		String Format = CommonUtilityMethods.readJsonFile(Constants.retsPath,"getMetaData").getString("Format");
		httpRequestForMetaData.header("User-Agent","CMMS/1.0").header("RETS-Version","RETS/1.8");
		httpRequestForMetaData.queryParam("ID", ID);
		httpRequestForMetaData.queryParam("Type", Type);
		httpRequestForMetaData.queryParam("Format", Format);
		httpRequestForMetaData.cookie("RETS-Session-ID",sessionID);
		String Metauser = CommonUtilityMethods.getRetsCredentials("user",locale,env);
		logger.info("successfully getting the user for metadata:"+user);
		String Metapass = CommonUtilityMethods.getRetsCredentials("password",locale,env);
		httpRequestForMetaData.auth().digest(Metauser, Metapass);
		Response  responseForMetaData = httpRequestForMetaData.post();
		Assert.assertEquals(responseForMetaData.getStatusCode(), ResponseCodes.SUCCESS,"Meatadata sucess code check");	
		String xml = responseForMetaData.getBody().asString();
		
		
		//converting xml into json 
		JSONObject json = new JSONObject();
		json = CommonUtilityMethods.convertJSON(xml);
		
	    //writing back into files according to types	 
		if(type.equalsIgnoreCase("METADATA-CLASS")) {
			classFile.write(json.toString());
			classFile.close();
		}	
		else if(type.equalsIgnoreCase("METADATA-LOOKUP_TYPE")) {
			lookupFile.write(json.toString());	
			lookupFile.close();
		}	
		else if(type.equalsIgnoreCase("METADATA-SEARCH_HELP")) {
			searchFile.write(json.toString());
			searchFile.close();
		}	
		else if(type.equalsIgnoreCase("METADATA-TABLE")) {
			tableFile.write(json.toString());
			tableFile.close();
		}	
	
	}
	
}


