package bright.api.alaya.test.objectLayer;

import org.testng.annotations.Test;

import bright.api.alaya.pages.objectLayer.ObjectLayerDeleteAPIPage;
import io.restassured.specification.RequestSpecification;

public class ObjectLayerDeleteAPIMemberTest {
	static String docName;
	static String locale="Bright";
	static String docType="member";
	
	
	
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Success Status for delete member")
	public void verifyWronglocale() {
		
		RequestSpecification httpRequest = null;
		
		ObjectLayerDeleteAPIPage.verifyWrongLocale(httpRequest, locale, docType, docName);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Success Status for delete member")
	public void verifyNonExistDoc() {
		
		RequestSpecification httpRequest = null;
		
		ObjectLayerDeleteAPIPage.verifyNotExistDoc(httpRequest, locale, docType, docName);
	}
	
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Success Status for delete member")
	public void verifyWrongDocType() {
		
		RequestSpecification httpRequest = null;
		
		ObjectLayerDeleteAPIPage.verifyWrongDocType(httpRequest, locale, docType, docName);
	}
	

	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Success Status for delete member")
	public void verifyForbiddenRequest() {
		
		RequestSpecification httpRequest = null;
		
		ObjectLayerDeleteAPIPage.forbiddenCallForObjectyLayer(httpRequest, locale, docType, docName);
	}
}
