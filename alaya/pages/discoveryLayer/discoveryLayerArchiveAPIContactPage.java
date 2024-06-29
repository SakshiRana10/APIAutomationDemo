package bright.api.alaya.pages.discoveryLayer;

import org.apache.commons.lang.RandomStringUtils;
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

public class discoveryLayerArchiveAPIContactPage extends MainClassAlaya {


	public static Response archiveCallForDiscovery(RequestSpecification httpRequest, String systemLoc,  String doctype,String docName, String lastModified ,boolean includeTags) {


		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpecForDiscovery();
		httpRequest = RestAssured.given();
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"discoveryLayer").getString("deleteDocument").toString());	
		requestSpecification.pathParam("locale", systemLoc);
		requestSpecification.pathParam("docType", doctype);
		requestSpecification.pathParam("docName", docName);
		requestSpecification.queryParam("preserveDocNameCase", true);
		requestSpecification.queryParam("lastModifiedSeen",lastModified);
		

		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.delete();
		logger.info("Archive Call for Discovery Respose - "+response.asString());
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.SUCCESS);
		SoftAssert softAssert = new SoftAssert();
		JSONObject result=new JSONObject(response.asString());
		String docNameResponse=result.getString("documentName");
		softAssert.assertEquals(docNameResponse, docName,"Document names mismatch found. Expected name was - "+docNameResponse+" Found name was - "+docName);
		JSONObject location=result.getJSONObject("location");
		String docTypeResponse=location.getString("documentType");
		softAssert.assertEquals(docTypeResponse, doctype,"Document Types mismatch found. Expected type was - "+docTypeResponse+" Found name was - "+doctype);
		return response;
	}



	public static void wrongDocType(RequestSpecification httpRequest, String systemLoc,  String doctype, String docName,String lastModified) {
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpecForDiscovery();
		httpRequest = RestAssured.given();

		String docuName = docName;		
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"discoveryLayer").getString("deleteDocument").toString());	
		requestSpecification.pathParam("locale", systemLoc);
		requestSpecification.pathParam("docType", doctype);
		requestSpecification.pathParam("docName", docuName);
		requestSpecification.queryParam("preserveDocNameCase", true);
		requestSpecification.queryParam("lastModifiedSeen",lastModified);
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.delete();
		logger.info("Response for wrong document type in archive call for discovery layer - "+response.asString());
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.BAD_REQUEST,"Response code mismatch found. Expected code was - "+ ResponseCodes.BAD_REQUEST + " Found code was - "+response.getStatusCode());
	}


	public static void verifyGoneDoc(RequestSpecification httpRequest, String systemLoc,  String doctype,String docName,String lastModified) {
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpecForDiscovery();
		httpRequest = RestAssured.given();

			
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"discoveryLayer").getString("deleteDocument").toString());	
		requestSpecification.pathParam("locale", systemLoc);
		requestSpecification.pathParam("docType", doctype);
		requestSpecification.pathParam("docName", docName);
		requestSpecification.queryParam("preserveDocNameCase", true);
		requestSpecification.queryParam("lastModifiedSeen",lastModified);
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.delete();
		logger.info("Response for gone document in archive call for discovery layer - "+response.asString());
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.GONE,"Response code mismatch found. Expected code was - "+ ResponseCodes.GONE + " Found code was - "+response.getStatusCode());



	}

	public static void verifyWrongLocale(RequestSpecification httpRequest, String systemLoc,  String doctype, String docName,String lastModified) {
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpecForDiscovery();
		httpRequest = RestAssured.given();

		String docuName = docName;		
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"discoveryLayer").getString("deleteDocument").toString());	
		requestSpecification.pathParam("locale", systemLoc);
		requestSpecification.pathParam("docType", doctype);
		requestSpecification.pathParam("docName", docuName);
		requestSpecification.queryParam("preserveDocNameCase", true);
		requestSpecification.queryParam("lastModifiedSeen",lastModified);
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.delete();
		logger.info("Response for wrong locale in archive call for discovery layer - "+response.asString());
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.NOT_FOUND,"Response code mismatch found. Expected code was - "+ ResponseCodes.NOT_FOUND + " Found code was - "+response.getStatusCode());



	}

	public static void verifyNotExistDoc(RequestSpecification httpRequest, String systemLoc, String doctype,String docName,String lastModified) {
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpecForDiscovery();
		httpRequest = RestAssured.given();

		String docuName = docName;		
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"discoveryLayer").getString("deleteDocument").toString());	
		requestSpecification.pathParam("locale", systemLoc);
		requestSpecification.pathParam("docType", doctype);
		requestSpecification.pathParam("docName", docuName);
		requestSpecification.queryParam("preserveDocNameCase", true);
		requestSpecification.queryParam("lastModifiedSeen",lastModified);
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.delete();
		logger.info("Response for non existing document in archive call for discovery layer - "+response.asString());
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.NO_SUCH_KEY,"Response code mismatch found. Expected code was - "+ ResponseCodes.NO_SUCH_KEY + " Found code was - "+response.getStatusCode());



	}
	
	
	
	/**
	 * A method to verify the Forbidden error for Put API for discovery Layer
	 */

	public static Response verifyForbiddenForArchiveAPIDiscovery(RequestSpecification httpRequest, String systemLoc, String doctype, String docName) {
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpecForDiscovery();
		httpRequest = RestAssured.given();
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"discoveryLayer").getString("deleteDocument").toString());	
		requestSpecification.pathParam("locale", systemLoc);
		requestSpecification.pathParam("docType", doctype);
		requestSpecification.pathParam("docName", docName);
		requestSpecification.queryParam("preserveDocNameCase", true);
		requestSpecification.headers("x-api-key","check"); 
		
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.delete();
		logger.info("Response for forbidden archive call for discovery layer - "+response.asString());
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.FORBIDDEN,"Response code mismatch found. Expected code was - "+ ResponseCodes.FORBIDDEN + " Found code was - "+response.getStatusCode());
		return response;
	}
	
	
	
	/**
	 * A method to verify the Forbidden error for Put API for discovery Layer
	 */

	public static Response verifyPreserveDocNameFalseForArchiveAPIDiscovery(RequestSpecification httpRequest,  String systemLoc, String doctype, String docName,String lastModified) {
		
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpecForDiscovery();
		httpRequest = RestAssured.given();
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"discoveryLayer").getString("deleteDocument").toString());	
		requestSpecification.pathParam("locale", systemLoc);
		requestSpecification.pathParam("docType", doctype);
		requestSpecification.pathParam("docName", docName);
		requestSpecification.queryParam("preserveDocNameCase", false);

		requestSpecification.queryParam("lastModifiedSeen",lastModified);

		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.delete();
		logger.info("Response for preserve doc name in archive call for discovery layer - "+response.asString());
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.NO_SUCH_KEY,"Response code mismatch found. Expected code was - "+ ResponseCodes.NO_SUCH_KEY + " Found code was - "+response.getStatusCode());
		return response;
	}

	public static String randomString()
	{ 
		  int length = 18;
		    boolean useLetters = true;
		    boolean useNumbers = true;
		    String generatedString = RandomStringUtils.random(length, useNumbers,useLetters);
            logger.info("Generated Random String for archive API is - "+generatedString);
			return generatedString;
	}

}
