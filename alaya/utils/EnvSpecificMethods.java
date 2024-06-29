package bright.api.alaya.utils;

import java.util.HashMap;

import bright.api.alaya.utils.EnumsUtility.EnumObjectType;
import software.amazon.awssdk.regions.Region;

public class EnvSpecificMethods extends MainClassAlaya {

    private static final String env = CommonUtilityMethods.getEnvironment();
    private static final String region = CommonUtilityMethods.getRegionString();
    private static final Region awsRegion = CommonUtilityMethods.getAWSRegion();

    private static final HashMap<String, String> ENVIRONMENTS = new HashMap<String, String>(){
        {
            put("d1", "dev");
            put("t1", "tst");
            put("p1", "prd");
        }
    };

    private static final HashMap<String, String> ACCOUNT_NUMBER = new HashMap<String, String>(){
        {
            put("d1", "714767054201");
            put("t1", "607145767122");
            put("p1", "717545809501");
        }
    };

    private static final HashMap<String, String> LOCAL_AWS_PROFILE = new HashMap<String, String>(){
        {
            put("d1", "dev_admin");
            put("t1", "tst_automation");
            put("p1", "tst_prod");
        }
    };


public static String docStoreURL() {
    String URL = String.format("https://au%s%sz1documentstoreservices.%s.brightmls.com",region, env, ENVIRONMENTS.get(env));
    
    return URL;
}

/*
 * This method is used to get the base url for OIDC flow
 */
public static String oktaBaseURL() {
	String paraName = null;
	
	
	
	  if(CommonUtilityMethods.getEnvironment().equalsIgnoreCase("d1")|| CommonUtilityMethods.getEnvironment().equalsIgnoreCase("t1")) {
			paraName = String.format("https://okta.%s.brightmls.com", ENVIRONMENTS.get(env));
		    }
		   
		    else if(CommonUtilityMethods.getEnvironment().equalsIgnoreCase("p1")) {
		    	paraName = String.format("https://okta.brightmls.com");
		    }
	  
		    else
		    {
		    	logger.info("Please select a valid env");
		    }
	
	return paraName;
}

/*
 * This method is used to get the base url for S2S flow
 */
public static String s2sURL() {
	
	String paraName = null;
	
	
	
	  if(CommonUtilityMethods.getEnvironment().equalsIgnoreCase("d1")) {
		  paraName = String.format("https://okta.dev.brightmls.com");
		    }
		   
		    else if(CommonUtilityMethods.getEnvironment().equalsIgnoreCase("t1")) {
		    	paraName = String.format("https://brightmls-test.okta.com");
		    }
	  
		    else if(CommonUtilityMethods.getEnvironment().equalsIgnoreCase("p1")) {
		    	paraName = String.format("https://okta.brightmls.com");
		    }
		    else
		    {
		    	logger.info("Please select a valid env");
		    }
	
	return paraName;
}


public static String oneAdminUrl() {
    String URL = String.format("https://lmsoneadmin.brightmls.com/CsAdmSvc/service/AUE1/LMS%s/GetAllRuleUsages", env.toUpperCase());
    return URL;
}



public static String oneAdminUrlRuleFlags() {
    String URL = String.format("https://lmsoneadmin.brightmls.com/CsAdmSvc/odata/AUE1/LMS%s/RuleUsages", env.toUpperCase());
    return URL;
}

public static String retsGetSessionId() {
	 String URL = null;
	    if(CommonUtilityMethods.getEnvironment().equalsIgnoreCase("d1")) {
	    URL = String.format("http://aue1d1lms-rets-oracle.%s.aws.brightmls.com/cornerstone/login",ENVIRONMENTS.get(env));
	    }
	    else if(CommonUtilityMethods.getEnvironment().equalsIgnoreCase("t1")) {
	    URL = String.format("http://au%st1lms-rets-oracle.%s.aws.brightmls.com/cornerstone/login",region,ENVIRONMENTS.get(env));
	    }
	    else if(CommonUtilityMethods.getEnvironment().equalsIgnoreCase("p1")) {
	    URL = String.format("http://au%sp1lms-rets-oracle.%s.aws.brightmls.com/cornerstone/login",region,ENVIRONMENTS.get(env));
	    }
	    return URL;
	    
	}

	public static String retsGetMetaData() {
	    String URL = null;
	    if(CommonUtilityMethods.getEnvironment().equalsIgnoreCase("d1")) {
	    URL = String.format("http://aue1d1lms-rets-oracle.%s.aws.brightmls.com/cornerstone/getmetadata",ENVIRONMENTS.get(env));
	    }
	    else if(CommonUtilityMethods.getEnvironment().equalsIgnoreCase("t1")) {
	    URL = String.format("http://au%st1lms-rets-oracle.%s.aws.brightmls.com/cornerstone/getmetadata",region,ENVIRONMENTS.get(env));
	    }
	    else if(CommonUtilityMethods.getEnvironment().equalsIgnoreCase("p1")) {
	    URL = String.format("http://au%sp1lms-rets-oracle.%s.aws.brightmls.com/cornerstone/getmetadata",region,ENVIRONMENTS.get(env));
	    }
	    return URL;
	}



public static String discoveryLayerURL() {

    String paraName = String.format("https://au%s%sz1discoveryservices.%s.brightmls.com", region,env, ENVIRONMENTS.get(env));
    return paraName;
}

/**
 * This method will get the parameter store name for env
 */
public static String userParamStore() {
	String paraName = String.format("/secure/au%s/%s/alaya/qa_users", region,env);
	return paraName;
}


public static String ObjectLayerURL() {

    String paraName = String.format("https://au%s%sz1objectlayerservices.%s.brightmls.com", region,env, ENVIRONMENTS.get(env));
    return paraName;
}


public static String graphQlURL() {
    String URL = String.format("https://au%s%sz1discoveryservices.%s.brightmls.com/search",region, env, ENVIRONMENTS.get(env));
    return URL;
}

public static String utilityServicesURL() {
    String URL = String.format("https://au%s%sz1alayautilityservices.%s.brightmls.com",region, env, ENVIRONMENTS.get(env));
    return URL;
}

public static String auditTrailURL() {
    String URL = String.format("https://au%s%sz1alayaaudittrailservices.%s.brightmls.com",region, env, ENVIRONMENTS.get(env));
    return URL;
}

public static String docStoreAPIKey() {
    String configKey = ENVIRONMENTS.get(env)+"ApiKeyDocStore";
    return property.getProperty(configKey);
}

public static String sessionUserName() {
    String configKey = ENVIRONMENTS.get(env)+"UserSession";
    return property.getProperty(configKey);
}

public static String sessionPassword() {
    String configKey = ENVIRONMENTS.get(env)+"PasswordSession";
    return property.getProperty(configKey);
}

public static String discoveryLayerAPIKey() {
    String configKey = ENVIRONMENTS.get(env)+"ApiKeyDiscovery";
    return property.getProperty(configKey);
}

public static String ObjectLayerAPIKey() {
    String configKey = ENVIRONMENTS.get(env)+"ApiKeyObject";
    return property.getProperty(configKey);
}

public static String graphQlAPIKey() {
    String configKey = ENVIRONMENTS.get(env)+"GraphQlApiKey";
    return property.getProperty(configKey);
}

public static String utilityLayerAPIKey() {
    String configKey = ENVIRONMENTS.get(env)+"UtilityLayerApiKey";
    return property.getProperty(configKey);
}

public static String auditTrailAPIKey() {
    String configKey = ENVIRONMENTS.get(env)+"AuditTrailApiKey";
    return property.getProperty(configKey);
}

public static String objectLayerAPIKey() {
    String configKey = ENVIRONMENTS.get(env)+"ApiKeyObjectLayer";
    return property.getProperty(configKey);
}

public static String dsInitializerSNS() {
    String sns ;
    if(CommonUtilityMethods.getRegionString().equalsIgnoreCase("e1"))
         sns = String.format("arn:aws:sns:us-east-1:%s:au%s%sz1snrdocumentstoreservices-brightinitializer", ACCOUNT_NUMBER.get(env),region, env);
    
    else
         sns = String.format("arn:aws:sns:us-west-2:%s:au%s%sz1snrdocumentstoreservices-brightinitializer", ACCOUNT_NUMBER.get(env),region, env);
        
    return sns;
}




public static String indexBuilderSNS() {
    String sns ;
    
    if(CommonUtilityMethods.getRegionString().equalsIgnoreCase("e1"))
     sns = String.format("arn:aws:sns:us-east-1:%s:au%s%sz1snrdocumentstoreservices-subscription", ACCOUNT_NUMBER.get(env),region, env);
    
    else
        sns = String.format("arn:aws:sns:us-west-2:%s:au%s%sz1snrdocumentstoreservices-subscription", ACCOUNT_NUMBER.get(env),region, env);
        
    return sns;
}

public static String awsProfile() {
    return LOCAL_AWS_PROFILE.get(env);
}

public static String docStoreParamStore() {
    String paraName = String.format("/secure/au%s/%s/documentstore/apikey", region,env);
    return paraName;
}

public static String oneAdminLogicalAttribute() {
    String URL = String.format("https://lmsoneadmin.brightmls.com/CsAdmSvc/service/AUE1/LMS%s/GetLogicalObjectViewAttributes", env.toUpperCase());
    return URL;
}

public static String retsCredentialsParamStore() {
    String paraName = null;        
    paraName = String.format("/secure/au%s/%s/alaya/retsCredentials",region,env);
    return paraName;
}

public static String objectLayerParamStore() {
    String paraName = String.format("/secure/au%s/%s/alaya/objectServices/apiKey", region,env);
    return paraName;
}


public static String CsAdmParamStore() {
    String paraName = String.format("/secure/au%s/%s/alaya/csAdmSvcUserCredentials", region,env);
    return paraName;
}

public static String docStoreParamStoreDiscovery() {
    String paraName = String.format("/secure/au%s/%s/alaya/discoveryServices/apiKey", region,env);
    return paraName;
}

public static String graphQlParamStore() {
    String paraName = String.format("/secure/au%s/%s/alaya/discoveryServices/apiKey", region, env);
    return paraName;
}

public static String docStoreParamStoreDB() {
    String paraName = String.format("/secure/au%s/%s/alaya/cornerstoneCredentials", region, env);
    return paraName;
}

public static String docStoreGitToken() {

    String paraName = String.format("/secure/au%s/%s/alaya/gitPT", region, env);
    return paraName;
}

public static String slackBotTokenParamStore() {
    String paraName = String.format("/secure/au%s/%s/alaya/slackBotToken", region,env);
    return paraName;
}

/**
 * This method will get the parameter store name for system Toggle
 */

public static String systemToggleParamStore() {
	String paraName = String.format("/parameter/aue1/%s/alaya/cutOver/systemToggleConfig", env);
	return paraName;
}

/**
 * This method will fetch systemToggle value from Config
 */
public static String systemToggle(EnumObjectType objectType) {
	String toggleVal = property.getProperty(objectType.toString().toLowerCase());
	return toggleVal;
}
}