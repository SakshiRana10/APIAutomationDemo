package bright.api.alaya.test.objectLayer;

import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import bright.api.alaya.pages.indexInitializer.IndexInitializerPage;
import bright.api.alaya.pages.objectLayer.OfficeStaticRulePage;
import bright.api.alaya.utils.MainClassAlaya;
import io.restassured.specification.RequestSpecification;

public class OfficeStaticRulesTest extends MainClassAlaya{

	
	@Test ( groups = {"test"}, priority = 0, enabled = true, description ="test") 
	public void verifySetToOtherForNonBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		OfficeStaticRulePage setValue=new OfficeStaticRulePage();
		OfficeStaticRulePage.cityNotNullCountyNotNullStateNullNonBright(httpRequest);
	}
	
	@Test ( groups = {"test"}, priority = 0, enabled = true, description ="test") 
	public void verifyallNullValuesCheckNonBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		OfficeStaticRulePage setValue=new OfficeStaticRulePage();
		OfficeStaticRulePage.allNullValuesCheckNonBright(httpRequest);
	}
	
	@Test ( groups = {"test"}, priority = 0, enabled = true, description ="test") 
	public void verifyallValidValuesNonBright() throws InterruptedException, JsonMappingException, JsonProcessingException, NumberFormatException, JSONException, ParseException {
		RequestSpecification httpRequest= null;
		OfficeStaticRulePage setValue=new OfficeStaticRulePage();
		OfficeStaticRulePage.allValidValuesNonBright(httpRequest);
	}
	
	@Test ( groups = {"test"}, priority = 0, enabled = true, description ="test") 
	public void verifycityNullCountyNotNullStateNotNullNonBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		OfficeStaticRulePage setValue=new OfficeStaticRulePage();
		OfficeStaticRulePage.cityNullCountyNotNullStateNotNullNonBright(httpRequest);
	}
	
	@Test ( groups = {"test"}, priority = 0, enabled = true, description ="test") 
	public void verifycityInvalidCountyNotNullStateNotNullNonBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		OfficeStaticRulePage setValue=new OfficeStaticRulePage();
		OfficeStaticRulePage.cityInvalidCountyNotNullStateNotNullNonBright(httpRequest);
	}
	
	@Test ( groups = {"test"}, priority = 0, enabled = true, description ="test") 
	public void verifycityNotNullCountyNotNullStateNullNonBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		OfficeStaticRulePage setValue=new OfficeStaticRulePage();
		OfficeStaticRulePage.cityNotNullCountyNotNullStateNullNonBright(httpRequest);
	}
	
//	@Test ( groups = {"test"}, priority = 0, enabled = true, description ="test") 
//	public void verifycityNotNullCountyNullStateNotNullNonBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
//		RequestSpecification httpRequest= null;
//		OfficeStaticRulePage setValue=new OfficeStaticRulePage();
//		OfficeStaticRulePage.cityNotNullCountyNullStateNotNullNonBright(httpRequest);
//	} //not a valid test case as county cant be null
	
	@Test ( groups = {"test"}, priority = 0, enabled = true, description ="test") 
	public void verifyallNullValuesCheckBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		OfficeStaticRulePage setValue=new OfficeStaticRulePage();
		OfficeStaticRulePage.allNullValuesCheckBright(httpRequest);
	}
	
	@Test ( groups = {"test"}, priority = 0, enabled = true, description ="test") 
	public void verifyallValidValuesBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		OfficeStaticRulePage setValue=new OfficeStaticRulePage();
		OfficeStaticRulePage.allValidValuesBright(httpRequest);
	}
	

//@Test ( groups = {"test"}, priority = 0, enabled = true, description ="test") 
//	public void verifycityNotNullCountyNullStateNullBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
//		RequestSpecification httpRequest= null;
//		OfficeStaticRulePage setValue=new OfficeStaticRulePage();
//		OfficeStaticRulePage.cityNotNullCountyNullStateNullBright(httpRequest);
//	}
	// not a valid test case as county cant be null
	
	@Test ( groups = {"test"}, priority = 0, enabled = true, description ="test") 
	public void verifycityNotNullCountyNullStateNotNullBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		OfficeStaticRulePage setValue=new OfficeStaticRulePage();
		OfficeStaticRulePage.cityNotNullCountyNullStateNotNullBright(httpRequest);
	}
	
	@Test ( groups = {"test"}, priority = 0, enabled = true, description ="test") 
	public void verifycitNullCountyNotNullStateNotNullBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		OfficeStaticRulePage setValue=new OfficeStaticRulePage();
		OfficeStaticRulePage.citNullCountyNotNullStateNotNullBright(httpRequest);
	}
	
	@Test ( groups = {"test"}, priority = 0, enabled = true, description ="test") 
	public void verifycityNullCountyNotNullStateNullBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		OfficeStaticRulePage setValue=new OfficeStaticRulePage();
		OfficeStaticRulePage.cityNullCountyNotNullStateNullBright(httpRequest);
	}
	
	
	@Test ( groups = {"test"}, priority = 0, enabled = true, description ="test") 
	public void verifycityNullCountyNullStateNotNullBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		OfficeStaticRulePage setValue=new OfficeStaticRulePage();
		OfficeStaticRulePage.cityNullCountyNullStateNotNullBright(httpRequest);
	}
	
	
	@Test ( groups = {"test"}, priority = 0, enabled = true, description ="verify for invalid city") 
	public void verifycityInValidCountyNullStateNotNullBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		OfficeStaticRulePage setValue=new OfficeStaticRulePage();
		OfficeStaticRulePage.cityInValidCountyNullStateNotNullBright(httpRequest);
	}
	

}
