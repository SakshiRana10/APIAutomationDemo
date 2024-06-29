package bright.api.alaya.test.objectLayer;

import org.testng.annotations.Test;

import bright.api.alaya.pages.objectLayer.ObjectLayerDeleteAPIPage;
import bright.api.alaya.utils.MainClassAlaya;
import io.restassured.specification.RequestSpecification;

public class ObjectLayerDeleteAPITeamTest extends MainClassAlaya{
	static String docName;
	String locale="Bright";
	String docType="team";

	
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify 404 Status for delete team wrong locale")
	public void verifyWronglocale() {
		RequestSpecification httpRequest = null;
		ObjectLayerDeleteAPIPage.verifyWrongLocale(httpRequest, locale, docType, docName);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Status for delete team non existing doc")
	public void verifyNonExistDoc() {		
		RequestSpecification httpRequest = null;
		String docType="team";
		String locale="Bright";
		ObjectLayerDeleteAPIPage.verifyNotExistDoc(httpRequest, locale, docType, docName);
	}
	
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify 404 Status for delete team wrong document type")
	public void verifyWrongDocType() {	
		RequestSpecification httpRequest = null;
		String docType="team";
		String locale="Bright";
		ObjectLayerDeleteAPIPage.verifyWrongDocType(httpRequest, locale, docType, docName);
	}
	

	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify forbidden Status for delete team")
	public void verifyForbiddenRequest() {	
		RequestSpecification httpRequest = null;
		String docType="team";
		String locale="Bright";
		ObjectLayerDeleteAPIPage.forbiddenCallForObjectyLayer(httpRequest, locale, docType, docName);
	}
}
