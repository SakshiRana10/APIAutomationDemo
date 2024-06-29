package bright.api.alaya.test.discoveryLayer;

import org.testng.annotations.Test;

import bright.api.alaya.pages.discoveryLayer.discoveryLayerPutAPIPage;
import bright.api.alaya.utils.MainClassAlaya;

public class discoveryLayerPutAPISubdivisionTest extends MainClassAlaya {
	
	public static String subdivision = "subdivision";
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Success Status for Subdivision")
	public void verifySuccessStatusforPUTApiSubdivisionDiscoveryMethod(){
		discoveryLayerPutAPIPage.verifySuccessStatusforPutApi(subdivision);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Response If IncludeVersions Is False")
	public void verifyResponseIfIncludeVersionsIsFalseDiscoveryMethod(){
		discoveryLayerPutAPIPage.verifyResponseIfIncludeVersionsIsFalse(subdivision);
	}
	
//	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Response If IncludeTags Is False")
//	public void verifyResponseIfIncludeTagsIsFalseDiscoveryMethod() {
//		discoveryLayerPutAPIPage.verifyResponseIfIncludeTagsIsFalse(subdivision);
//	}
	//commented out due to blockage in release
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Response If IncludePretty Is False")
	public void verifyResponseIfIncludePrettyIsFalseDiscoveryMethod() {
		discoveryLayerPutAPIPage.verifyResponseIfIncludePrettyIsFalse(subdivision);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Response If last modified format Is wrong")
	public void verifyResponseIfLastModifiedIsWrongFormatDiscoveryMethod() {
		discoveryLayerPutAPIPage.verifyResponseIfLastModifiedIsWrongFormat(subdivision);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Response If last modified value Is wrong")
	public void verifyResponseIfLastModifiedIsWrongValueDiscoveryMethod() {
		discoveryLayerPutAPIPage.verifyResponseIfLastModifiedIsWrongValue(subdivision);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Response For Unchecked IncludeVersions")
	public void verifyResponseForUncheckedIncludeVersionsDiscoveryMethod() {
		discoveryLayerPutAPIPage.verifyResponseForUncheckedIncludeVersions(subdivision);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Response For Unchecked last modified")
	public void verifyResponseForUncheckedLastModifiedDiscoveryMethod() {
		discoveryLayerPutAPIPage.verifyResponseForUncheckedLastModified(subdivision);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Response For Wrong System Locale")
	public void verifyResponseForWrongSystemLocaleDiscoveryMethod() {
		discoveryLayerPutAPIPage.verifyResponseForWrongSystemLocale(subdivision);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Response For Wrong document type")
	public void verifyResponseForWrongDocumentTypeDiscoveryMethod() {
		discoveryLayerPutAPIPage.verifyResponseForWrongDocType(subdivision);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify Response For Wrong document name")
	public void verifyResponseForWrongDocumentDiscoveryMethod() {
		discoveryLayerPutAPIPage.verifyResponseForWrongDocument(subdivision);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Verify forbidden request if wrong api key is sent")
	public void verifyForbiddenRequestDiscoveryMethod() {
		discoveryLayerPutAPIPage.verifyWrongAPIKeyForPUT(subdivision);
	}
	
	
}
