package bright.api.alaya.utils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.util.Strings;

import com.amazonaws.util.StringUtils;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;
import software.amazon.awssdk.services.ssm.model.GetParametersByPathRequest;
import software.amazon.awssdk.services.ssm.model.GetParametersByPathResponse;
import software.amazon.awssdk.services.ssm.model.Parameter;
import software.amazon.awssdk.services.ssm.model.SsmException;

public class SSMConnection extends MainClassAlaya {
	   private static final String env = CommonUtilityMethods.getEnvironment();
	    private static final String region = CommonUtilityMethods.getRegionString();
    public static SsmClient  createSsmClient(Region region, String profile) {
    	
    	SsmClient  ssmClient = null ;
    	if(!Strings.isNullOrEmpty(System.getProperty("env"))) {
    		  ssmClient = SsmClient.builder().region(region).build();
    	}
    	else
    		
    		 ssmClient = SsmClient.builder().region(region).credentialsProvider(ProfileCredentialsProvider.create(profile)).build();
    	
        return ssmClient;
    }
    
    public static void getParaValue(SsmClient ssmClient) throws IOException, ParseException {  	
    	JSONParser parser = new JSONParser();
    	Object obj = parser.parse(new FileReader(Constants.parameterStore));
    	JSONObject paramObj = (org.json.simple.JSONObject)obj;
        ArrayList<String> parameters = new ArrayList<String>(paramObj.keySet());
  
    	  	
       	FileWriter valuesFile = new FileWriter(Constants.parameterStore);
       	JSONObject valuesObj = new JSONObject();
    	String keyValue = null;
        try {
        	GetParametersByPathRequest parameterRequest = GetParametersByPathRequest.builder().recursive(true).path(String.format("/secure/au%s/%s/alaya/", region, env)).withDecryption(true).build();
            GetParametersByPathResponse parameterResponse;
            do {
            	parameterResponse = ssmClient.getParametersByPath(parameterRequest);
            	for(int i = 0; i<parameters.size(); i++) {
            		String keyName = parameters.get(i).toString();
            		  if(keyName.equalsIgnoreCase("documentstore/apikey"))
            		  {
            			  GetParameterRequest parameterRequestDocStore = GetParameterRequest.builder().name(String.format("/secure/au%s/%s/documentstore/apikey", region, env)).withDecryption(true).build();
                          GetParameterResponse parameterResponseDocStore = ssmClient.getParameter(parameterRequestDocStore);
            		      keyValue = parameterResponseDocStore.parameter().value();
            		      valuesObj.put(keyName, keyValue);
            		  }
            		  
            		  else if(keyName.equalsIgnoreCase("systemToggleConfig"))
            		  {
            			  GetParameterRequest parameterRequestDocStore = GetParameterRequest.builder().name(String.format("/parameter/au%s/%s/alaya/cutOver/systemToggleConfig", region, env)).withDecryption(true).build();
                          GetParameterResponse parameterResponseDocStore = ssmClient.getParameter(parameterRequestDocStore);
            		      keyValue = parameterResponseDocStore.parameter().value();
            		      valuesObj.put(keyName, keyValue);
            		  }
            		  
            		  else if(keyName.equalsIgnoreCase("auditTrailServices/apiKey"))
            		  {
            			  GetParameterRequest parameterRequestDocStore = GetParameterRequest.builder().name(String.format("/secure/au%s/%s/alaya/auditTrailServices/apiKey", region, env)).withDecryption(true).build();
                          GetParameterResponse parameterResponseDocStore = ssmClient.getParameter(parameterRequestDocStore);
            		      keyValue = parameterResponseDocStore.parameter().value();
            		      valuesObj.put(keyName, keyValue);
            		  }
            		  else { 
                        for (Parameter p : parameterResponse.parameters()) {
                	    if(p.name().contains(keyName)) {
                		 keyValue = p.value().toString();
                		 valuesObj.put(keyName, keyValue);
                		 break;
                	  }
                    }
                  }
            	}
               parameterRequest = GetParametersByPathRequest.builder().recursive(true).path(String.format("/secure/au%s/%s/alaya/", region, env)).withDecryption(true).nextToken(parameterResponse.nextToken()).build(); 
              }
              while (!StringUtils.isNullOrEmpty(parameterResponse.nextToken()));
            valuesFile.write(valuesObj.toJSONString());
            valuesFile.close();

        } catch (SsmException e) {
        	logger.info(e);
            logger.info("The user is not having"
            		+ " access to perform ssm:GetParametersByPath for test and prod"
            		+ " Looking in config file for credentials ");        	
        }
       
   }
}