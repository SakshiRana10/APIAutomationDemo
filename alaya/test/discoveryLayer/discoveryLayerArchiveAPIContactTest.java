package bright.api.alaya.test.discoveryLayer;

import org.json.JSONObject;
import org.testng.annotations.Test;

import bright.api.alaya.pages.discoveryLayer.discoveryLayerArchiveAPIContactPage;
import bright.api.alaya.pages.discoveryLayer.discoveryLayerUtilityPage;
import bright.api.alaya.pages.discoveryLayer.discoveryLayerPostAPIPage;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class discoveryLayerArchiveAPIContactTest extends MainClassAlaya {

	
	
	static String docName;
	static String lastModified;
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Success Status for delete sf_account")
	public void verifyArchiveSuccess() {
		RequestSpecification httpRequest = null;
	String docType=CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"archiveTestData").getString("docType");
	String locale=CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"archiveTestData").getString("locale");
	docName=discoveryLayerArchiveAPIContactPage.randomString();
	JSONObject payload=discoveryLayerPostAPIPage.createPayloadForPostRawObject(docName,docType);
	
	Response responseFromPost = discoveryLayerUtilityPage.callPostOfDiscoveryLayer(httpRequest, docType, docName,"true", "true", "true", "Bright", ResponseCodes.CREATED,payload);
	logger.info(responseFromPost.asString());
	 lastModified = new JSONObject(responseFromPost.getBody().asString()).getString("lastModified");
	discoveryLayerArchiveAPIContactPage.archiveCallForDiscovery(httpRequest,locale, docType,docName,lastModified,false);
	
	}
	@Test ( groups = {"test", "dev"}, priority = 1, enabled = true, description = "Verify Gone doc Status for delete sf_account")
	public void verifyGoneDocuments() {
		RequestSpecification httpRequest = null;
		String docType=CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"archiveTestData").getString("docType");
		String locale=CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"archiveTestData").getString("locale");
		discoveryLayerArchiveAPIContactPage.verifyGoneDoc(httpRequest,locale, docType,docName,lastModified);
	}
	
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Invalid Doc type  for delete sf_account")
	public void verifyInvalidDocType() {
		RequestSpecification httpRequest = null;
		String wrongDocType=CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"archiveTestData").getString("wrongDocType");
		String locale=CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"archiveTestData").getString("locale");
		discoveryLayerArchiveAPIContactPage.wrongDocType(httpRequest,locale, wrongDocType,docName,lastModified);
	}
	
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Non existing  doc for delete sf_account")
	public void verifyNonExistingDoc() {
		RequestSpecification httpRequest = null;
		String docType=CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"archiveTestData").getString("docType");
		String locale=CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"archiveTestData").getString("locale");
		String nonExistingdoc=CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"archiveTestData").getString("nonExistingdoc");
		discoveryLayerArchiveAPIContactPage.verifyNotExistDoc(httpRequest,locale, docType,nonExistingdoc,lastModified);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Wrong locale for delete sf_account")
	public void verifyWrongLocale() {
		RequestSpecification httpRequest = null;
		String docType=CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"archiveTestData").getString("docType");
		String wrongLocale=CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"archiveTestData").getString("wrongLocale");
		discoveryLayerArchiveAPIContactPage.verifyWrongLocale(httpRequest,wrongLocale, docType, docName,lastModified);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Success Status for delete sf_account")
	public void verifyForbiddenForArchiveDiscoveryLayer() {
		RequestSpecification httpRequest = null;
		String docType=CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"archiveTestData").getString("docType");
		String locale=CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"archiveTestData").getString("locale");
		discoveryLayerArchiveAPIContactPage.verifyForbiddenForArchiveAPIDiscovery(httpRequest,locale, docType, docName);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Success Status for delete sf_account")
	public void verifyPreserveDocNameFalse() {
		
		RequestSpecification httpRequest = null;
		String docType=CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"archiveTestData").getString("docType");
		String locale=CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath,"archiveTestData").getString("locale");
		docName=discoveryLayerArchiveAPIContactPage.randomString();
		JSONObject payload=discoveryLayerPostAPIPage.createPayloadForPostRawObject(docName,docType);
		Response responseFromPost = discoveryLayerUtilityPage.callPostOfDiscoveryLayer(httpRequest, docType, docName,"true", "true", "true", "Bright", ResponseCodes.CREATED,payload);
		String lastModified = new JSONObject(responseFromPost.getBody().asString()).getString("lastModified");
		discoveryLayerArchiveAPIContactPage.verifyPreserveDocNameFalseForArchiveAPIDiscovery(requestSpecification,locale, docType, docName,lastModified);
		 lastModified = new JSONObject(responseFromPost.getBody().asString()).getString("lastModified");
		discoveryLayerArchiveAPIContactPage.archiveCallForDiscovery(requestSpecification,locale, docType,docName,lastModified,true);
	}
	
	
}
