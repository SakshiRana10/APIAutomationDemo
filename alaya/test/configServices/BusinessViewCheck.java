package bright.api.alaya.test.configServices;

import java.io.IOException;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import bright.api.alaya.pages.configServices.BusinessViewPage;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.MainClassAlaya;


public class BusinessViewCheck extends MainClassAlaya {

	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify Bright fields count with artifacts and condut")
	public void verifyBrightBusinessViews() {
		String locale = "Bright";
	    String env = CommonUtilityMethods.getEnvironment();
		BusinessViewPage business=new BusinessViewPage();

		try {
			business.verifyCondutBusinessView(Constants.businessViewDirectoryBright,locale,env);
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify Wirex fields count with artifacts and condut")
	public void verifyWirexBusinessViews() {
		String locale = "Wirex";
	    String env = CommonUtilityMethods.getEnvironment();
		BusinessViewPage business=new BusinessViewPage();

		try {
			business.verifyCondutBusinessView(Constants.businessViewDirectoryWirex,locale,env);
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	


}
