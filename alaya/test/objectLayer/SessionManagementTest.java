package bright.api.alaya.test.objectLayer;

import org.testng.annotations.Test;

import bright.api.alaya.pages.objectLayer.SessionManagementPage;
import bright.api.alaya.utils.MainClassAlaya;



public class SessionManagementTest extends MainClassAlaya {

	

	@Test ( groups = {"test"}, priority = 0, enabled = true, description ="Verify session token  ") 
	public void verifySesionToken() throws InterruptedException{
		
		String sessionid=SessionManagementPage.fetchSessionToken("brokerId", "brokerPassword");
		String code=SessionManagementPage.fetchLocationfromCode(sessionid);
		String idToken=SessionManagementPage.fetchIDToken(code);
		SessionManagementPage.fetchSessionkey(idToken);
		
		SessionManagementPage.fetchBearerToken();
			
	}
}
