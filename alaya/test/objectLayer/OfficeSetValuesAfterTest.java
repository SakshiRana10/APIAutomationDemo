package bright.api.alaya.test.objectLayer;

import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import bright.api.alaya.pages.objectLayer.OfficeSetDefaultValuesPage;
import bright.api.alaya.pages.objectLayer.OfficeStaticRulePage;
import bright.api.alaya.utils.MainClassAlaya;
import io.restassured.specification.RequestSpecification;

public class OfficeSetValuesAfterTest extends MainClassAlaya{

	
	@Test ( groups = {"test"}, priority = 0, enabled = true, description ="verify status is active and SysPrCurrent is true") 
	public void verifyActivePrCurrentOffice() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException {
		RequestSpecification httpRequest= null;
		OfficeSetDefaultValuesPage setValue=new OfficeSetDefaultValuesPage();
		OfficeSetDefaultValuesPage.verifyActivePrCurrentOffice(httpRequest);
		
		
	}
	
	
	@Test ( groups = {"test"}, priority = 0, enabled = true, description ="verify status is inactive and SysPrCurrent is true") 
	public void verifyInActivePrCurrentOffice() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException {
		RequestSpecification httpRequest= null;
		OfficeSetDefaultValuesPage setValue=new OfficeSetDefaultValuesPage();
		
		OfficeSetDefaultValuesPage.verifyInActivePrCurrentOffice(httpRequest);
	
	}
	
	
	@Test ( groups = {"test"}, priority = 0, enabled = true, description ="verify status is active and SysPrCurrent and SysPrLastCurrent both are true") 
	public void verifyActivePrCurrentLastCurrentOffice() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException {
		RequestSpecification httpRequest= null;
		OfficeSetDefaultValuesPage setValue=new OfficeSetDefaultValuesPage();
		
		OfficeSetDefaultValuesPage.verifyActivePrCurrentLastCurrentOffice(httpRequest);
		
		
	}
	
	@Test ( groups = {"test"}, priority = 0, enabled = true, description ="verify status is Inactive and SysPrCurrent and SysPrLastCurrent both are true") 
	public void verifyInActivePrCurrentLastCurrentOffice() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException {
		RequestSpecification httpRequest= null;
		OfficeSetDefaultValuesPage setValue=new OfficeSetDefaultValuesPage();
		
		OfficeSetDefaultValuesPage.verifyActivePrCurrentOffice(httpRequest);
		
	}
	
	
	
	@Test ( groups = {"test"}, priority = 0, enabled = true, description ="verify status is active and SysPrLastCurrent is true") 
	public void verifyActiveLastCurrentOffice() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException {
		RequestSpecification httpRequest= null;
		OfficeSetDefaultValuesPage setValue=new OfficeSetDefaultValuesPage();
		OfficeSetDefaultValuesPage.verifyActiveLastCurrentOffice(httpRequest);
		
	}
	
	@Test ( groups = {"test"}, priority = 0, enabled = true, description ="verify status is Inactive and SysPrLastCurrent is true") 
	public void verifyInActiveLastCurrentOffice() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException {
		RequestSpecification httpRequest= null;
		OfficeSetDefaultValuesPage setValue=new OfficeSetDefaultValuesPage();
		OfficeSetDefaultValuesPage.verifyInActiveLastCurrentOffice(httpRequest);
		
		
		
	}
	
	@Test ( groups = {"test"}, priority = 0, enabled = true, description ="verify status is active and SysPrCurrent and SysPrLastCurrent both are false") 
	public void verifyActiveNoPrCurrentnAndLastCurrentOffice() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException {
		RequestSpecification httpRequest= null;
		OfficeSetDefaultValuesPage setValue=new OfficeSetDefaultValuesPage();
		OfficeSetDefaultValuesPage.verifyActiveNoPrCurrentnAndLastCurrentOffice(httpRequest);
		
	}
	
	@Test ( groups = {"test"}, priority = 0, enabled = true, description ="verify status is Inactive and SysPrCurrent and SysPrLastCurrent both are false") 
	public void verifyInActiveNoPrCurrentnAndLastCurrentOffice() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException {
		RequestSpecification httpRequest= null;
		OfficeSetDefaultValuesPage setValue=new OfficeSetDefaultValuesPage();
		OfficeSetDefaultValuesPage.verifyInActiveNoPrCurrentnAndLastCurrentOffice(httpRequest);
		
		
		
	}
	
	@Test ( groups = {"test"}, priority = 0, enabled = true, description ="verify status is active and SysPrCurrent is true") 
	public void verifyActivePrCurrentSystemBright() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException {
		RequestSpecification httpRequest= null;
		OfficeSetDefaultValuesPage setValue=new OfficeSetDefaultValuesPage();
		OfficeSetDefaultValuesPage.verifyActivePrCurrentSystemBright(httpRequest);
		
		
		
	}
	
	@Test ( groups = {"test"}, priority = 0, enabled = true, description ="verify status is active and SysPrCurrent is true") 
	public void verifyInActivePrCurrentSystemBright() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException {
		RequestSpecification httpRequest= null;
		OfficeSetDefaultValuesPage setValue=new OfficeSetDefaultValuesPage();
		OfficeSetDefaultValuesPage.verifyActivePrCurrentOffice(httpRequest);
		
		
		
	}
	
	@Test ( groups = {"test"}, priority = 0, enabled = true, description ="verify status is active and SysPrCurrent is true") 
	public void verifyActivePrCurrentLastCurrentSystemBright() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException {
		RequestSpecification httpRequest= null;
		OfficeSetDefaultValuesPage setValue=new OfficeSetDefaultValuesPage();
		OfficeSetDefaultValuesPage.verifyActivePrCurrentOffice(httpRequest);
		
		
		
	}
	
	@Test ( groups = {"test"}, priority = 0, enabled = true, description ="verify status is active and SysPrCurrent is true") 
	public void verifyInActivePrCurrentLastCurrentSystemBright() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException {
		RequestSpecification httpRequest= null;
		OfficeSetDefaultValuesPage setValue=new OfficeSetDefaultValuesPage();
		OfficeSetDefaultValuesPage.verifyActivePrCurrentOffice(httpRequest);
		
		
		
	}
	
	@Test ( groups = {"test"}, priority = 0, enabled = true, description ="verify status is active and SysPrCurrent is true") 
	public void verifyActiveLastCurrentOfficeSystemBright() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException {
		RequestSpecification httpRequest= null;
		OfficeSetDefaultValuesPage setValue=new OfficeSetDefaultValuesPage();
		OfficeSetDefaultValuesPage.verifyActiveLastCurrentOfficeSystemBright(httpRequest);
		
		
		
	}
	
	
	@Test ( groups = {"test"}, priority = 0, enabled = true, description ="verify status is active and SysPrCurrent is true") 
	public void verifyInActiveLastCurrentOfficeSystemBright() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException {
		RequestSpecification httpRequest= null;
		OfficeSetDefaultValuesPage setValue=new OfficeSetDefaultValuesPage();
		OfficeSetDefaultValuesPage.verifyInActiveLastCurrentSystemBright(httpRequest);
		
		
		
	}
	
	@Test ( groups = {"test"}, priority = 0, enabled = true, description ="verify status is active and SysPrCurrent is true") 
	public void verifyActiveNoPrCurrentnAndLastCurrentSystemBright() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException {
		RequestSpecification httpRequest= null;
		OfficeSetDefaultValuesPage setValue=new OfficeSetDefaultValuesPage();
		OfficeSetDefaultValuesPage.verifyActiveNoPrCurrentnAndLastCurrentSystemBright(httpRequest);
		
		
		
	}
	
	@Test ( groups = {"test"}, priority = 0, enabled = true, description ="verify status is active and SysPrCurrent is true") 
	public void verifyInActiveNoPrCurrentnAndLastCurrentSystemBright() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException {
		RequestSpecification httpRequest= null;
		OfficeSetDefaultValuesPage setValue=new OfficeSetDefaultValuesPage();
		OfficeSetDefaultValuesPage.verifyInActiveNoPrCurrentnAndLastCurrentSystemBright(httpRequest);
		
		
		
	}
	
	@Test ( groups = {"test"}, priority = 0, enabled = true, description ="verify status is active and SysPrCurrent is true") 
	public void verifyNoRoleActive() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException {
		RequestSpecification httpRequest= null;
		OfficeSetDefaultValuesPage setValue=new OfficeSetDefaultValuesPage();
		OfficeSetDefaultValuesPage.verifyNoRoleActive(httpRequest);
		
		
		
	}
	
	@Test ( groups = {"test"}, priority = 0, enabled = true, description ="verify status is active and SysPrCurrent is true") 
	public void verifyNoRoleInActive() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException {
		RequestSpecification httpRequest= null;
		OfficeSetDefaultValuesPage setValue=new OfficeSetDefaultValuesPage();
		OfficeSetDefaultValuesPage.verifyNoRoleInActive(httpRequest);
		
		
		
	}
	
	@Test ( groups = {"test"}, priority = 0, enabled = true, description ="verify status is active and SysPrCurrent is true") 
	public void verifyPhoneNumberOther() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException {
		RequestSpecification httpRequest= null;
		OfficeSetDefaultValuesPage setValue=new OfficeSetDefaultValuesPage();
		OfficeSetDefaultValuesPage.checkPhoneNumberOther(httpRequest);
		
		
		
	}
}
