package bright.api.alaya.test.discoveryLayer;

import java.util.ArrayList;
import java.util.Arrays;

import org.testng.annotations.Test;

import bright.api.alaya.pages.discoveryLayer.discoveryLayerGetAPIPage;
import bright.api.alaya.utils.MainClassAlaya;
import io.restassured.specification.RequestSpecification;

public class discoveryLayerGetAPIBuilderModelTest extends MainClassAlaya {
	
	public static String buildermodel = "builder_model";

	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify Success Status for BuilderModel")
	public void verifySuccessStatusforGETApiBuilderModelDiscoveryMethod() {
		RequestSpecification httpRequest = null;
		discoveryLayerGetAPIPage.verifySuccessStatusforGETApi(httpRequest,buildermodel);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify Response If IncludeVersions Is False")
	public void verifyResponseIfIncludeVersionsIsFalseDiscoveryMethod() {
		RequestSpecification httpRequest = null;
		discoveryLayerGetAPIPage.verifyResponseIfIncludeVersionsIsFalse(httpRequest,buildermodel);
	}
	
//	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify Response If IncludeTags Is False")
//	public void verifyResponseIfIncludeTagsIsFalseDiscoveryMethod() {
//		discoveryLayerGetAPIMainPage.verifyResponseIfIncludeTagsIsFalse(httpRequest,buildermodel);
//	}
	//commented out due to blockage in release
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify Response If IncludePretty Is False")
	public void verifyResponseIfIncludePrettyIsFalseDiscoveryMethod() {
		RequestSpecification httpRequest = null;
		discoveryLayerGetAPIPage.verifyResponseIfIncludePrettyIsFalse(httpRequest,buildermodel);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify Response For Unchecked IncludeVersions")
	public void verifyResponseForUncheckedIncludeVersionsDiscoveryMethod() {
		RequestSpecification httpRequest = null;
		discoveryLayerGetAPIPage.verifyResponseForUncheckedIncludeVersions(httpRequest,buildermodel);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify Response For Wrong System Locale")
	public void verifyResponseForWrongSystemLocaleDiscoveryMethod() {
		RequestSpecification httpRequest = null;
		discoveryLayerGetAPIPage.verifyResponseForWrongSystemLocale(httpRequest,buildermodel);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify Response For Wrong document type")
	public void verifyResponseForWrongDocumentTypeDiscoveryMethod() {
		RequestSpecification httpRequest = null;
		discoveryLayerGetAPIPage.verifyResponseForWrongDocType(httpRequest,buildermodel);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Response For Wrong document name")
	public void verifyResponseForWrongDocumentDiscoveryMethod() {
		RequestSpecification httpRequest = null;
		discoveryLayerGetAPIPage.verifyResponseForWrongDocument(httpRequest,buildermodel);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Response For deleted document")
	public void verifyResponseForDeletedDocumentDiscoveryMethod() {
		RequestSpecification httpRequest = null;
		discoveryLayerGetAPIPage.verifyResponseForDeletedDocument(httpRequest,buildermodel);
	}
		
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify attributes matching from doc store to ES")
	public void verifyAttributesDiscoveryMethod() {
		RequestSpecification httpRequest = null;
		ArrayList<String> AttributeNamesForDocStore = new ArrayList<String>(Arrays.asList("builderModelKey", "builderModelName","lastModified","builderModelCounty","builderModelStatus"));
		ArrayList<String> AttributeNamesForES = new ArrayList<String>(Arrays.asList("builderModelKey", "builderModelName","dsLastModifiedTime","builderModelCounty{key}","builderModelStatus{key}"));
		discoveryLayerGetAPIPage.verifyAttributesForDiscoveryLayer(httpRequest,buildermodel,AttributeNamesForDocStore,AttributeNamesForES);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify forbidden request if wrong api key is sent")
	public void verifyForbiddenRequestDiscoveryMethod() {
		RequestSpecification httpRequest = null;
		discoveryLayerGetAPIPage.verifyWrongAPIKeyForGET(httpRequest,buildermodel);
	}
}
