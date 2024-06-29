package bright.api.alaya.pages.dsInitializer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class DSInitializerBulkReadAPIPage extends MainClassAlaya {

	
	public static HashMap<String, ArrayList<String>> docNamesMap = new HashMap<String,ArrayList<String>>(){{
        put("listing", listingDocumentNames);
        put("office",officeDocumentNames);
        put("member",memberDocumentNames);
        put("countyrate",countyDocumentNames);
        put("taxrecord",taxDocumentNames);
        put("builder_model",builderModelDocumentNames);
        put("city",cityDocumentNames);
        put("subdivision",subDivisionDocumentNames);
        put("building_name",buildingNameDocumentNames);
    }};
	private static final ArrayList<String> nonExistingDocuments = new ArrayList<String>(Arrays.asList("234567", "1239865 ","3456896","128648"));

	/**
	 * A method to send post call to Bulk read DS Initilaizer
	 */

	public static void sendPostToBulkReadDsInitializer(RequestSpecification httpRequest,String docType) {
		SoftAssert softAssert = new SoftAssert();
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpec();
		httpRequest = RestAssured.given();
		JSONObject payload = CommonUtilityMethods.readJsonFile(property.getProperty("dsInitializerPayloadJsonPath"),"dsInitializerBulkRead"+docType);
		ArrayList<String> documentNames = docNamesMap.get(docType);
		payload.put("documentNames", documentNames);
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"bulkReadDSInitializer").getString(docType).toString());
		requestSpecification.body(payload.toString());
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.post();
		String responseBody = response.getBody().asString();
		
		/*Verifying Status code*/
		softAssert.assertEquals(response.getStatusCode(), ResponseCodes.SUCCESS,"Expected status code was 200 OK but it was "+response.getStatusCode());
		logger.info("Verified the Post call status code for Bulk Read DS Initializer for document type "+docType);
		
		/*Validating encoded document with documentstore call response*/
		String encodedDocument = CommonUtilityMethods.fetchStringValueFromApiResponse(responseBody,"documentsEncoded");
		String decodedDocument = CommonUtilityMethods.decodeBase64String(encodedDocument);
		CommonUtilityMethods.saveDecodedDocument(decodedDocument, docType);
		softAssert.assertEquals(CommonUtilityMethods.validateDocument(httpRequest,decodedDocument, payload.toString()), true,"Decoded document did not match with document store call");
		logger.info("Verified the validation of encoded document recieved in the Post Bulk Read API Response for document type "+docType);
	
		/*Verifying document type in encoded document with path param doc type*/
		String documentTypeInPayload = docType;
		ArrayList<String> documentTypesInEncode = new ArrayList<String>();
		JSONArray documentsTypes = CommonUtilityMethods.convertJSONArray(new JSONObject(decodedDocument));
		for(int i=0;i<documentsTypes.length();i++) {
			JSONObject doc = documentsTypes.getJSONObject(i);
			String docuType = null;
			if(docType.equalsIgnoreCase("countyrate") || docType.equalsIgnoreCase("taxrecord"))
			 docuType = doc.getJSONObject("content").getString("documentType").toString().toLowerCase().replace("bright", "");
			else if(docType.equalsIgnoreCase("builder_model")) {
			 docuType = doc.getJSONObject("content").getString("lmsDocumentType").toString().toLowerCase().replace("bright", "");
			 docuType = docuType.substring(0, docuType.indexOf("r")+1)+"_"+docuType.substring(docuType.indexOf("r")+1);
			}
			else 
		     docuType = doc.getJSONObject("content").getString("lmsDocumentType").toString().toLowerCase().replace("bright", "");
			documentTypesInEncode.add(docuType);
			
		}
		
		if(docType.equalsIgnoreCase("building_name"))
		{
			
			softAssert.assertEquals(documentTypeInPayload.replaceAll("_",""), documentTypesInEncode.get(0).toString(),"Expected document type was "+documentTypeInPayload.replaceAll("_","")+" but it was found "+documentTypesInEncode.get(0).toString());
		}
		else
			
	    softAssert.assertEquals(documentTypeInPayload, documentTypesInEncode.get(0),"Expected document type was "+documentTypeInPayload+" but it was found "+documentTypesInEncode.get(0));
	 
		logger.info("Verified the document type from encoded document with payload in the Post Bulk Read API for document type "+docType);
	    
	    /*Verifying document name in encoded document with path param doc type*/
		ArrayList<String> documentNamesInEncode = new ArrayList<String>();
		JSONArray documents = CommonUtilityMethods.convertJSONArray(new JSONObject(decodedDocument));
		for(int i=0;i<documents.length();i++) {
			JSONObject doc = documents.getJSONObject(i);
			String docName = doc.getJSONObject("metadata").getString("documentName");
			documentNamesInEncode.add(docName);
		}
		Collections.sort(documentNames);
		Collections.sort(documentNamesInEncode);
	    softAssert.assertEquals(documentNames, documentNamesInEncode,"Expected document names were "+documentNames+" but it was found "+documentNamesInEncode);
	    logger.info("Verified the document names from encoded document with payload in the Post Bulk Read API for document type "+docType);
	    
	    softAssert.assertAll();
	    logger.info("Bulk Read api call flow verified for DS initializer for document type "+docType);
	}

	/**
	 * A method to verify the Forbidden error for Bulk Read DS Initializer
	 */

	public static void verifyForbiddenForBulkReadDsInitializer(RequestSpecification httpRequest,String docType) {
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpec();
		httpRequest = RestAssured.given();
		JSONObject payload = CommonUtilityMethods.readJsonFile(property.getProperty("dsInitializerPayloadJsonPath"),"dsInitializerBulkRead"+docType);
		ArrayList<String> documentNames = docNamesMap.get(docType);
		payload.put("documentNames", documentNames);
		requestSpecification.headers("x-api-key","check");                 //passing invalid value for x-api-key
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"bulkReadDSInitializer").getString(docType).toString());
		requestSpecification.body(payload.toString());
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.post();
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.FORBIDDEN,"Expected status code was 403 FORBIDDEN but it was "+response.getStatusCode());
		logger.info("Verified the Forbidden error for Bulk Read DS Initializer for document type "+docType);
	}

	/**
	 * A method to verify the not found documents for Bulk Read DS Initializer Post Call
	 */

	public static void verifyNotFoundDocumentForBulkReadDsInitializerPost(RequestSpecification httpRequest,String docType) {
		SoftAssert softAssert = new SoftAssert();
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpec();
		httpRequest = RestAssured.given();
		JSONObject payload = CommonUtilityMethods.readJsonFile(property.getProperty("dsInitializerPayloadJsonPath"),"dsInitializerBulkRead"+docType);
		ArrayList<String> documentNames = nonExistingDocuments;
		payload.put("documentNames", documentNames);
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"bulkReadDSInitializer").getString(docType).toString());
		requestSpecification.body(payload.toString());
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.post();
		String responseBody = response.getBody().asString();
		softAssert.assertEquals(response.getStatusCode(), ResponseCodes.SUCCESS,"Expected status code was 200 OK but it was "+response.getStatusCode());
		logger.info("Verified the Post call status code for Bulk Read DS Initializer for document type "+docType);
		ArrayList<String> notFoundDocs = CommonUtilityMethods.convertJArrayToListString(CommonUtilityMethods.fetchJArrayFromApiResponse(responseBody, "notFoundDocumentNames"));
		softAssert.assertEquals(notFoundDocs, nonExistingDocuments,"Not found documents were "+nonExistingDocuments+" which did not match with response body docs "+notFoundDocs);
		logger.info("Verified the Not found status for wrong doc type for Bulk Read DS Initializer for document type "+docType);
		
		softAssert.assertAll();
		logger.info("Verified the not found documents for non existing documents for Bulk Read DS Initializer Post Call for document type "+docType);
	}
	
	/**
	 * A method to verify the not found of invalid doc type for Bulk Read DS Initializer Post Call
	 */
	
	public static void verifyInvalidDocumentTypeForBulkReadDsInitializerPost(RequestSpecification httpRequest,String docType) {
		SoftAssert softAssert = new SoftAssert();
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpec();
		httpRequest = RestAssured.given();
		JSONObject payload = CommonUtilityMethods.readJsonFile(property.getProperty("dsInitializerPayloadJsonPath"),"dsInitializerBulkRead"+docType);
		ArrayList<String> documentNames = docNamesMap.get(docType);
		payload.put("documentNames", documentNames);
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"bulkReadDSInitializer").getString("wrongDocType").toString());
		requestSpecification.body(payload.toString());
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.post();
		String responseBody = response.getBody().asString();
		softAssert.assertEquals(response.getStatusCode(), ResponseCodes.NOT_FOUND,"Expected status code was 404 NOT FOUND but it was "+response.getStatusCode());
		logger.info("Verified the Not found status for wrong doc type for Bulk Read DS Initializer for document type "+docType);
		String subError = new JSONObject(responseBody).getJSONArray("subErrors").get(0).toString();
		softAssert.assertEquals(subError, "Invalid document type","Expected sub error was Invalid document type but it was "+subError);
		logger.info("Verified the Not found sub error for wrong doc type for Bulk Read DS Initializer for document type "+docType);
		
		softAssert.assertAll();
		logger.info("Verified the not found status and message for wrong doc type for Bulk Read DS Initializer Post Call for document type "+docType);
	}
	
	/**
	 * A method to verify the not found of invalid locale for Bulk Read DS Initializer Post Call
	 */

	public static void verifyInvalidLocaleForBulkReadDsInitializerPost(RequestSpecification httpRequest,String docType) {
		SoftAssert softAssert = new SoftAssert();
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpec();
		httpRequest = RestAssured.given();
		JSONObject payload = CommonUtilityMethods.readJsonFile(property.getProperty("dsInitializerPayloadJsonPath"),"dsInitializerBulkRead"+docType);
		ArrayList<String> documentNames = docNamesMap.get(docType);
		payload.put("documentNames", documentNames);
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"bulkReadDSInitializer").getString("wrongLocale"+docType).toString());
		requestSpecification.body(payload.toString());
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.post();
		String responseBody = response.getBody().asString();
		softAssert.assertEquals(response.getStatusCode(), ResponseCodes.NOT_FOUND,"Expected status code was 404 NOT FOUND but it was "+response.getStatusCode());
		logger.info("Verified the Not found status for wrong locale for Bulk Read DS Initializer for document type "+docType);
		String subError = new JSONObject(responseBody).getJSONArray("subErrors").get(0).toString();
		softAssert.assertEquals(subError, "Invalid system locale","Expected sub error was Invalid system locale but it was "+subError);
		logger.info("Verified the Not found sub error for wrong locale for Bulk Read DS Initializer for document type "+docType);
		
		softAssert.assertAll();
		logger.info("Verified the not found status and message for wrong locale for Bulk Read DS Initializer Post Call for document type "+docType);
	}


}
