package bright.api.alaya.pages.objectLayer;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.util.Strings;

import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.EnumsUtility.EnumObjectType;
import bright.api.alaya.utils.EnvSpecificMethods;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import bright.api.alaya.utils.SSMConnection;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.RedirectConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ssm.SsmClient;

public class SessionManagementPage extends MainClassAlaya {
	
	private static String apiEndpointsPath = property.getProperty("apiEndpointsJsonPath");
	
	private static String testDataPath = property.getProperty("sessionManagementTestDataJsonPath");
	
	public static HashMap<String,String> clientIDMap = new HashMap<String,String>(){{
    	 put("d1", CommonUtilityMethods.readJsonFile(testDataPath, "oidcData").getString("clientIdDev"));
    	 put("t1",CommonUtilityMethods.readJsonFile(testDataPath, "oidcData").getString("clientIdTst"));
         put("p1",CommonUtilityMethods.readJsonFile(testDataPath, "oidcData").getString("clientIdProd"));    	
    }};
    
    public static HashMap<String,String> AuthorizationMap = new HashMap<String,String>(){{
   	 put("d1", CommonUtilityMethods.readJsonFile(testDataPath, "bearerTokenData").getString("AuthorizationDev"));
   	 put("t1",CommonUtilityMethods.readJsonFile(testDataPath, "bearerTokenData").getString("AuthorizationTst"));
     put("p1",CommonUtilityMethods.readJsonFile(testDataPath, "bearerTokenData").getString("AuthorizationProd"));    	
   }};
	
	/* 
	 * A method to fetch tst users from parameter store
	 */
	
	public static String fetchTestUsers() {
		return CommonUtilityMethods.fetchXApiKey("qa_users");
	}
	
	/**
	 * A method to set request specifications for fetching the id token from OIDC
	 * flow
	 */
	public static RequestSpecification requestSpecToken() {
		RequestSpecBuilder builder = new RequestSpecBuilder();
		builder.setBaseUri(EnvSpecificMethods.oktaBaseURL());
		builder.setContentType(ContentType.JSON);
		return builder.build();
	}

	/**
	 * A method to set request specifications for fetching the access token from S2S
	 * flow
	 */
	public static RequestSpecification s2sToken() {
		RequestSpecBuilder builder = new RequestSpecBuilder();
		builder.setBaseUri(EnvSpecificMethods.s2sURL());
		builder.setContentType(ContentType.JSON);
		return builder.build();
	}

	/* A method to fetch tst users from parameter store */
	public static void getUserDetails(String userkey, String passwordKey) {
		
		String user = fetchTestUsers();
		String Username;
		String Password;
		if(!Strings.isNullOrEmpty(user)) {
			JSONObject json = new JSONObject(user); 
			 Username = (String)json.get(userkey); 
			 Password = (String) json.get(passwordKey);
					  
		}
		else {
			 Username=EnvSpecificMethods.sessionUserName();
			 Password=EnvSpecificMethods.sessionPassword();
		}
		  
		 
		property.setProperty("UserName", Username);
		property.setProperty("Password", Password);
	}
	
	/**
	 * A method to fetch String Value from API Response
	 */
	public static String fetchStringValueFromApiResponse(String responseBody, String key, String obj) {
		JSONObject jObject = null;
		try {
			jObject = new JSONObject(responseBody);
			if (obj != "") {
				jObject = jObject.getJSONObject(obj);
			}
		} catch (Exception jsonException) {
			jsonException.printStackTrace();
		}
		return jObject.getString(key);
	}
	/**
	 * A method to fetch the ID token through OIDC Flow
	 */
	public static String fetchSessionToken(String userkey, String passwordKey) throws InterruptedException {
		RequestSpecification requestSpecification = requestSpecToken();
		String sessiontoken;
		RequestSpecification httpRequest = RestAssured.given();
		JSONObject option = new JSONObject();
		option.put("warnBeforePasswordExpired", false);
		JSONObject body = new JSONObject();
		
			getUserDetails(userkey, passwordKey);
			body.put("username", property.getProperty("UserName"));
			body.put("password", property.getProperty("Password"));
		
		body.put("options", option);
		String apiEndpoint = CommonUtilityMethods.readJsonFile(apiEndpointsPath, "oidc").getString("postauthn")
				.toString();
		requestSpecification.basePath(apiEndpoint);
		requestSpecification.body(body.toString());
		httpRequest.spec(requestSpecification);
		Response response = httpRequest.post();
		String responseBody = response.getBody().asString();
		logger.info("Response body from first call of OIDC flow - "+responseBody);
		int statusCode = response.getStatusCode();
		if (statusCode != ResponseCodes.SUCCESS) {
			String statusMessage = response.getStatusLine();
			logger.info("Failed to get the session token. Status Code: " + statusCode + ", Status Message: "
					+ statusMessage);
			throw new RuntimeException("Failed to get the session token.");
		} else {
			sessiontoken = fetchStringValueFromApiResponse(responseBody, "sessionToken", "");
			logger.info("Got the session token from the first call of OIDC.");
		}
		logger.info("Session token from first call of OIDC is - "+sessiontoken);
		return sessiontoken;
		}
	
	
	public static String fetchLocationfromCode(String sessiontoken) throws InterruptedException {
		RequestSpecification requestSpecification = requestSpecToken();
		RestAssuredConfig config = new RestAssuredConfig().redirect(new RedirectConfig().followRedirects(false));
		RequestSpecification httpRequestCode = RestAssured.given();
		Response response = null;
		httpRequestCode.config(config);
		String locationCodeApiEndPoint = CommonUtilityMethods.readJsonFile(apiEndpointsPath, "oidc").getString("getsessionid").toString();
		String clientId = clientIDMap.get(CommonUtilityMethods.getEnvironment());
		String scopes = CommonUtilityMethods.readJsonFile(testDataPath, "oidcData").getString("scope");
		String redirectURI = CommonUtilityMethods.readJsonFile(testDataPath, "oidcData").getString("redirectUri");
		String state = CommonUtilityMethods.readJsonFile(testDataPath, "oidcData").getString("state");
		String guid = CommonUtilityMethods.readJsonFile(testDataPath, "oidcData").getString("guid");
		String codeChallenge = CommonUtilityMethods.readJsonFile(testDataPath, "oidcData").getString("codechallenge");
			
		requestSpecification.basePath(locationCodeApiEndPoint).config(config).queryParam("client_id", clientId)
				.queryParam("response_type", "code").queryParam("response_mode", "query").queryParam("scope", scopes)
				.queryParams("redirect_uri", redirectURI).queryParam("state", state).queryParam("nonce", guid)
				.queryParam("code_challenge_method", "S256").queryParam("code_challenge", codeChallenge)
				.queryParam("sessionToken", sessiontoken);
		httpRequestCode.spec(requestSpecification);
		httpRequestCode.spec(requestSpecification).urlEncodingEnabled(false);
		
		Response responseCode = httpRequestCode.get();
		logger.info("Response from second call of OIDC flow"+responseCode.getBody().asString());
		Assert.assertEquals(responseCode.getStatusCode(), ResponseCodes.REDIRECT, "Status code mismatch in second call of OIDC flow");
		String responseBodyCode = responseCode.getHeaders().getValue("location");
		if (responseBodyCode == null) {
			logger.info("Location header not found in the response.");
			throw new RuntimeException("Location header not found in the response.");
		}
		String[] texts = responseBodyCode.split("code=");
		if (texts.length < 2) {
			logger.info("Failed to get the code from location header. Location Header: " + responseBodyCode);
			throw new RuntimeException("Failed to get the code from location header.");
		}
		String code = texts[1].split("&")[0];
		if (responseCode.getStatusCode() != ResponseCodes.REDIRECT) {
			String statusMessage = response.getStatusLine();
			logger.info("Failed to get the code from location. Status Code: " + responseCode.getStatusCode()
					+ ", Status Message: " + statusMessage);
			throw new RuntimeException("Failed to get the code from location.");
		}
		logger.info("Got the code from location header from the second call of the OIDC flow - "+code);
		return code;
	}
	
	
	public static String fetchIDToken(String code) throws InterruptedException {
		String id_token;
		requestSpecification = requestSpecToken();
		RestAssuredConfig config = new RestAssuredConfig().redirect(new RedirectConfig().followRedirects(false));
		RequestSpecification httpRequestToken = RestAssured.given();
		httpRequestToken.config(config);

		String clientId = clientIDMap.get(CommonUtilityMethods.getEnvironment());
		String redirectURI = CommonUtilityMethods.readJsonFile(testDataPath, "oidcData").getString("redirectUri");
		String apiEndpointThird = CommonUtilityMethods.readJsonFile(apiEndpointsPath, "oidc").getString("posttoken").toString();
		String codeVerifier = CommonUtilityMethods.readJsonFile(testDataPath, "oidcData").getString("codeverifier");
		
		requestSpecification.basePath(apiEndpointThird);
		requestSpecification.contentType("application/x-www-form-urlencoded; charset=utf-8")
				.formParam("grant_type", "authorization_code").formParam("redirect_uri", redirectURI)
				.formParam("code", code).formParam("client_id", clientId).formParam("code_verifier", codeVerifier);
		httpRequestToken.spec(requestSpecification);
		Response responseToken = httpRequestToken.post();
		String responseBodyToken = responseToken.getBody().asString();
		logger.info("Response from fetch id Token api - "+responseBodyToken);
		Assert.assertEquals(responseToken.getStatusCode(), ResponseCodes.SUCCESS);
		try {
			 id_token = fetchStringValueFromApiResponse(responseBodyToken, "id_token", "");
			property.setProperty("idToken", id_token);
			logger.info("Got the Id token from the third call of the OIDC flow and set the id token in config");
		} catch (JSONException e) {
			logger.error("Error: " + e.getMessage());
			throw e;
		}
		logger.info("ID token received is - "+id_token);
		return id_token;
	}

	/**
	 * A method to fetch the Cookie through OIDC Flow
	 */
	public static String fetchSessionkey(String idToken) {
		String responseCookie = null;
		String apiEndpoint = null;
		RequestSpecification requestSpecification = CommonUtilityMethods.requestSpecForObjectLayer();
		RequestSpecification httpRequest = RestAssured.given();
		apiEndpoint = CommonUtilityMethods.readJsonFile(apiEndpointsPath, "authtoken").getString("authtokenendpoint").toString();
		String appId = CommonUtilityMethods.readJsonFile(testDataPath, "oidcData").getString("appId");
		String appVersion = CommonUtilityMethods.readJsonFile(testDataPath, "oidcData").getString("appVersion");
		requestSpecification.basePath(apiEndpoint);
		String payload = idToken;
		JSONObject body = new JSONObject();
		body.put("authorization", payload);
		body.put("appId",appId);
		body.put("appVersion", appVersion);
		requestSpecification.body(body.toString());
		httpRequest.spec(requestSpecification);
		Response response = httpRequest.post();
		logger.info("Response from fetch session key api - "+response.getBody().asString());
			responseCookie = response.getCookie("OBJECT-SESSION-ID").toString();
			property.setProperty("objectSessionId", responseCookie);
			logger.info("Fetched object session id - "+responseCookie);
		
		return responseCookie;

	}
	
	/**
	 * A method to fetch the Cookie through OIDC Flow for bypass rules application 
	 */
	public static String fetchSessionkeyWithByPassRole(String idToken) {
		String responseCookie = null;
		String apiEndpoint = null;
		RequestSpecification requestSpecification = CommonUtilityMethods.requestSpecForObjectLayer();
		RequestSpecification httpRequest = RestAssured.given();
		apiEndpoint = CommonUtilityMethods.readJsonFile(apiEndpointsPath, "authtoken").getString("authtokenendpoint").toString();
		String appId = CommonUtilityMethods.readJsonFile(testDataPath, "oidcData").getString("bypassRulesAppId");
		String appVersion = CommonUtilityMethods.readJsonFile(testDataPath, "oidcData").getString("appVersion");
		requestSpecification.basePath(apiEndpoint);
		String payload = idToken;
		JSONObject body = new JSONObject();
		body.put("authorization", payload);
		body.put("appId",appId);
		body.put("appVersion", appVersion);
		requestSpecification.body(body.toString());
		httpRequest.spec(requestSpecification);
		Response response = httpRequest.post();
		logger.info("Response from fetch session key api - "+response.getBody().asString());
			responseCookie = response.getCookie("OBJECT-SESSION-ID").toString();
			property.setProperty("objectSessionId", responseCookie);
			logger.info("Fetched object session id - "+responseCookie);
		
		return responseCookie;

	}
	
	/**
	 * A method to fetch the Cookie through OIDC Flow
	 */
	public static String fetchSessionkeyWithoutRoles(String idToken) {
		String responseCookie = null;
		String apiEndpoint = null;
		RequestSpecification requestSpecification = CommonUtilityMethods.requestSpecForObjectLayer();
		RequestSpecification httpRequest = RestAssured.given();
		apiEndpoint = CommonUtilityMethods.readJsonFile(apiEndpointsPath, "authtoken").getString("authtokenendpoint").toString();
		String appId = CommonUtilityMethods.readJsonFile(testDataPath, "oidcData").getString("appIdWithoutRoles");
		String appVersion = CommonUtilityMethods.readJsonFile(testDataPath, "oidcData").getString("appVersion");
		requestSpecification.basePath(apiEndpoint);
		String payload = idToken;
		JSONObject body = new JSONObject();
		body.put("authorization", payload);
		body.put("appId",appId);
		body.put("appVersion", appVersion);
		requestSpecification.body(body.toString());
		httpRequest.spec(requestSpecification);
		Response response = httpRequest.post();
		logger.info("Response from fetch session key api - "+response.getBody().asString());
			responseCookie = response.getCookie("OBJECT-SESSION-ID").toString();
			property.setProperty("objectSessionId", responseCookie);
			logger.info("Fetched object session id - "+responseCookie);
		
		return responseCookie;

	}


	/**
	 * A method to fetch the Access token through S2S Flow
	 */
	public static String fetchBearerToken() throws InterruptedException {
		String access_token=null;
		RequestSpecification requestSpecification = s2sToken();
		RequestSpecification httpRequest = RestAssured.given();
		String apiEndpoint = CommonUtilityMethods.readJsonFile(apiEndpointsPath, "s2s").getString("post").toString();
		requestSpecification.basePath(apiEndpoint);
		String scope = CommonUtilityMethods.readJsonFile(testDataPath, "s2sData").getString("scope");
		String grant_type = CommonUtilityMethods.readJsonFile(testDataPath, "s2sData").getString("grant_type");
		String authorization = AuthorizationMap.get(CommonUtilityMethods.getEnvironment());
		
		requestSpecification.basePath(apiEndpoint).header("Content-Type", "application/x-www-form-urlencoded")
				.queryParam("scope", scope).queryParam("grant_type", grant_type).header("Authorization",authorization)
				.header("Accept", "application/json");

		httpRequest.spec(requestSpecification);
		Response response = httpRequest.post();
		String responseBody = response.getBody().asString();
		logger.info("Response from fetch bearer token api is - "+responseBody);
		try {
			 access_token = fetchStringValueFromApiResponse(responseBody, "access_token",
					"");
			property.setProperty("accessToken", access_token);
			Assert.assertEquals(response.getStatusCode(), ResponseCodes.SUCCESS);
			logger.info("Got the Access token from the S2S Call and set the access token in config");
		} catch (JSONException e) {
			logger.error("Error: " + e.getMessage());
			throw e;
		}
		logger.info("Access token received is - "+access_token);
		return access_token;

	}

	/**
	 * A method to get system toggle config
	 */
	public static boolean getFeatureToggle(EnumObjectType objectType) {
		String systemToggleVal= CommonUtilityMethods.fetchXApiKey("qa_users");
		logger.info("System toggle value is - "+systemToggleVal);
			return Boolean.valueOf(systemToggleVal);
		}

	
	
}
