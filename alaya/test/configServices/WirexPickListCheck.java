package bright.api.alaya.test.configServices;

import org.testng.annotations.Test;

import bright.api.alaya.pages.configServices.BusinessViewPage;
import bright.api.alaya.pages.configServices.PickListPage;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.MainClassAlaya;

public class WirexPickListCheck extends MainClassAlaya  {
	
	 @Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify Count of Picklist items in wirex locale")
		public void verifyPickListCountWirex() {
		 String type = "METADATA-LOOKUP_TYPE", locale = "Wirex";
	     String env = CommonUtilityMethods.getEnvironment();
			try {
				BusinessViewPage businessView=new BusinessViewPage();
				businessView.verifyPickListItems(Constants.businessViewDirectoryWirex,locale,type, env);
			} 
			catch (Exception e) {				
				e.printStackTrace();
			} 

		}
		
	 @Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "verify Short name and long name of picklist items in Wirex locale")
		public void VerifyLongNameShortNameWirex() {
		 String type = "METADATA-LOOKUP_TYPE", locale = "Wirex";
		 String env = CommonUtilityMethods.getEnvironment();
			try {
				PickListPage pickList=new PickListPage();
				pickList.compareLongNameShortName(Constants.businessViewDirectoryWirex,Constants.pickListDirectoryWirex, type,locale, env );

			} 
			catch (Exception e) {

				e.printStackTrace();
			} 
		}
}