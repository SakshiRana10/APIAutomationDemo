package bright.api.alaya.test.discoveryLayer;

import java.util.ArrayList;
import java.util.Arrays;

import org.testng.annotations.Test;
import bright.api.alaya.pages.discoveryLayer.discoveryLayerGetAPIPage;
import bright.api.alaya.utils.MainClassAlaya;
import io.restassured.specification.RequestSpecification;

public class discoveryLayerGetAPIMemberTest extends MainClassAlaya  {
	
	    public static String member = "member";
	
		@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify Success Status for member")
		public void verifySuccessStatusforGETApiMemberDiscoveryMethod() {
			RequestSpecification httpRequest = null;
			discoveryLayerGetAPIPage.verifySuccessStatusforGETApi(httpRequest,member);
		}
		
		@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify Response If IncludeVersions Is False")
		public void verifyResponseIfIncludeVersionsIsFalseDiscoveryMethod() {
			RequestSpecification httpRequest = null;
			discoveryLayerGetAPIPage.verifyResponseIfIncludeVersionsIsFalse(httpRequest,member);
		}
		
//		@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify Response If IncludeTags Is False")
//		public void verifyResponseIfIncludeTagsIsFalseDiscoveryMethod() {
//			discoveryLayerGetAPIMainPage.verifyResponseIfIncludeTagsIsFalse(httpRequest,member);
//		}
		//commented out due to blockage in release
		
		@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify Response If IncludePretty Is False")
		public void verifyResponseIfIncludePrettyIsFalseDiscoveryMethod() {
			RequestSpecification httpRequest = null;
			discoveryLayerGetAPIPage.verifyResponseIfIncludePrettyIsFalse(httpRequest,member);
		}
		
		@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify Response For Unchecked IncludeVersions")
		public void verifyResponseForUncheckedIncludeVersionsDiscoveryMethod() {
			RequestSpecification httpRequest = null;
			discoveryLayerGetAPIPage.verifyResponseForUncheckedIncludeVersions(httpRequest,member);
		}
	
		@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify Response For Wrong System Locale")
		public void verifyResponseForWrongSystemLocaleDiscoveryMethod() {
			RequestSpecification httpRequest = null;
			discoveryLayerGetAPIPage.verifyResponseForWrongSystemLocale(httpRequest,member);
		}
		
		@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify Response For Wrong document type")
		public void verifyResponseForWrongDocumentTypeDiscoveryMethod() {
			RequestSpecification httpRequest = null;
			discoveryLayerGetAPIPage.verifyResponseForWrongDocType(httpRequest,member);
		}
		
		@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Response For Wrong document name")
		public void verifyResponseForWrongDocumentDiscoveryMethod() {
			RequestSpecification httpRequest = null;
			discoveryLayerGetAPIPage.verifyResponseForWrongDocument(httpRequest,member);
		}
		
		@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Response For deleted document")
		public void verifyResponseForDeletedDocumentDiscoveryMethod() {
			RequestSpecification httpRequest = null;
			discoveryLayerGetAPIPage.verifyResponseForDeletedDocument(httpRequest,member);
		}
		
		@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify attributes matching from doc store to ES")
		public void verifyAttributesDiscoveryMethod() {
			RequestSpecification httpRequest = null;
			ArrayList<String> AttributeNamesForDocStore = new ArrayList<String>(Arrays.asList("memberMlsId", "memberKey","lastModified","officeKey","officeMlsId"));
			ArrayList<String> AttributeNamesForES = new ArrayList<String>(Arrays.asList("memberMlsId", "memberKey","dsLastModifiedTime","officeKey","officeMlsId"));
			discoveryLayerGetAPIPage.verifyAttributesForDiscoveryLayer(httpRequest,member,AttributeNamesForDocStore,AttributeNamesForES);
		}
		
		@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify forbidden request if wrong api key is sent")
		public void verifyForbiddenRequestDiscoveryMethod() {
			RequestSpecification httpRequest = null;
			discoveryLayerGetAPIPage.verifyWrongAPIKeyForGET(httpRequest,member);
		}
}
	
