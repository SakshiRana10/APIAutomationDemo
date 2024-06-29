package bright.api.alaya.test.configServices;


import org.json.JSONObject;
import org.testng.annotations.Test;

import bright.api.alaya.pages.configServices.BusinessViewPage;
import bright.api.alaya.pages.configServices.PickListPage;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.MainClassAlaya;


public class BrightPickListCheck extends MainClassAlaya {

	public static JSONObject json = new JSONObject();
   @Test ( groups = {"test", "dev", "prod"}, priority = 0, enabled = true, description = "Verify Count of Picklist items in bright locale")
	public void verifyPickListCountBright() {	   	   
	 String type = "METADATA-LOOKUP_TYPE", locale = "Bright";
     String env = CommonUtilityMethods.getEnvironment();
     
		try {
			BusinessViewPage businessView=new BusinessViewPage();
			businessView.verifyPickListItems(Constants.businessViewDirectoryBright,locale,type, env);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
      }
    
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "verify Short name and long name of picklist items in bright locale")
	public void VerifyLongNameShortNameBright() {
		String type = "METADATA-LOOKUP_TYPE", locale = "Bright";
	    String env = CommonUtilityMethods.getEnvironment();
	  
		try {
			PickListPage pickList=new PickListPage();	
			pickList.compareLongNameShortName(Constants.businessViewDirectoryBright,Constants.pickListDirectoryBright, type,locale, env );

		} 
		catch (Exception e) {

			e.printStackTrace();
		} 
	}
}
