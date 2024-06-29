package bright.api.alaya.test.objectLayer;

import org.json.JSONObject;
import org.testng.annotations.Test;

import bright.api.alaya.pages.objectLayer.ObjectLayerDeleteAPIPage;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ObjectLayerDeleteAPIListingTest extends MainClassAlaya {

	static String docName;
	static String locale="Bright";
	static String docType="listing";
	
	
	
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Success Status for delete listing")
	public void verifyWronglocale() {
		
		RequestSpecification httpRequest = null;
		
		ObjectLayerDeleteAPIPage.verifyWrongLocale(httpRequest, locale, docType, docName);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Success Status for delete listing")
	public void verifyNonExistDoc() {
		
		RequestSpecification httpRequest = null;
		
		ObjectLayerDeleteAPIPage.verifyNotExistDoc(httpRequest, locale, docType, docName);
	}
	
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Success Status for delete listing")
	public void verifyWrongDocType() {
		
		RequestSpecification httpRequest = null;
		
		ObjectLayerDeleteAPIPage.verifyWrongDocType(httpRequest, locale, docType, docName);
	}
	

	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Success Status for delete listing")
	public void verifyForbiddenRequest() {
		
		RequestSpecification httpRequest = null;
		
		ObjectLayerDeleteAPIPage.forbiddenCallForObjectyLayer(httpRequest, locale, docType, docName);
	}
}
