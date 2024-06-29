package bright.api.alaya.test.discoveryLayer;

import org.testng.annotations.Test;

import bright.api.alaya.pages.discoveryLayer.discoveryLayerPutAPIPage;
import bright.api.alaya.utils.MainClassAlaya;

public class discoveryLayerPutAPIMemberTest extends MainClassAlaya {
	
	 public static String member = "member";
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Success Status for member")
	public void verifySuccessStatusforPUTApimemberDiscoveryMethod(){
		discoveryLayerPutAPIPage.verifySuccessStatusforPutApi(member);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Response If IncludeVersions Is False")
	public void verifyResponseIfIncludeVersionsIsFalseDiscoveryMethod(){
		discoveryLayerPutAPIPage.verifyResponseIfIncludeVersionsIsFalse(member);
	}
	
//	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Response If IncludeTags Is False")
//	public void verifyResponseIfIncludeTagsIsFalseDiscoveryMethod() {
//		discoveryLayerPutAPIPage.verifyResponseIfIncludeTagsIsFalse(member);
//	}
	//commented out due to blockage in release
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Response If IncludePretty Is False")
	public void verifyResponseIfIncludePrettyIsFalseDiscoveryMethod() {
		discoveryLayerPutAPIPage.verifyResponseIfIncludePrettyIsFalse(member);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Response If last modified format Is wrong")
	public void verifyResponseIfLastModifiedIsWrongFormatDiscoveryMethod() {
		discoveryLayerPutAPIPage.verifyResponseIfLastModifiedIsWrongFormat(member);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Response If last modified value Is wrong")
	public void verifyResponseIfLastModifiedIsWrongValueDiscoveryMethod() {
		discoveryLayerPutAPIPage.verifyResponseIfLastModifiedIsWrongValue(member);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Response For Unchecked IncludeVersions")
	public void verifyResponseForUncheckedIncludeVersionsDiscoveryMethod() {
		discoveryLayerPutAPIPage.verifyResponseForUncheckedIncludeVersions(member);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Response For Unchecked last modified")
	public void verifyResponseForUncheckedLastModifiedDiscoveryMethod() {
		discoveryLayerPutAPIPage.verifyResponseForUncheckedLastModified(member);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Response For Wrong System Locale")
	public void verifyResponseForWrongSystemLocaleDiscoveryMethod() {
		discoveryLayerPutAPIPage.verifyResponseForWrongSystemLocale(member);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Response For Wrong document type")
	public void verifyResponseForWrongDocumentTypeDiscoveryMethod() {
		discoveryLayerPutAPIPage.verifyResponseForWrongDocType(member);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Response For Wrong document name")
	public void verifyResponseForWrongDocumentDiscoveryMethod() {
		discoveryLayerPutAPIPage.verifyResponseForWrongDocument(member);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify forbidden request if wrong api key is sent")
	public void verifyForbiddenRequestDiscoveryMethod() {
		discoveryLayerPutAPIPage.verifyWrongAPIKeyForPUT(member);
	}
}
