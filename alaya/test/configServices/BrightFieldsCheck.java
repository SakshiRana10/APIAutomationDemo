package bright.api.alaya.test.configServices;

import org.testng.annotations.Test;

import bright.api.alaya.pages.configServices.FieldsPage;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.ExcelManager;
import bright.api.alaya.utils.MainClassAlaya;

public class BrightFieldsCheck extends MainClassAlaya {



	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verify Bright fields with artifacts and condut")
		public void verifyFieldsDataBright() {
		 String locale = "Bright";
	     String env = CommonUtilityMethods.getEnvironment();
			try {

				FieldsPage fields=new FieldsPage();
				fields.verifyFields(Constants.businessViewDirectoryBright,locale,env);
			} 
			catch (Exception e) {

				e.printStackTrace();
			} 

		}


	
}


