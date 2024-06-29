package bright.api.alaya.test.configServices;

import org.testng.annotations.Test;
import bright.api.alaya.pages.configServices.FieldsPage;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.MainClassAlaya;


public class WirexFieldCheck extends MainClassAlaya{


@Test( groups = { "dev","prod","test"}, priority = 0, enabled = true, description = "Verify Fields data for Wirex locale")
		public void verifyFieldsDataWirex() {
	String locale = "Wirex";
    String env = CommonUtilityMethods.getEnvironment();

	try {
        
		FieldsPage fields=new FieldsPage();
		fields.verifyFields(Constants.businessViewDirectoryWirex,locale,env);
	} 
	catch (Exception e) {

		e.printStackTrace();
	} 

}
}
