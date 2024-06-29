package bright.api.alaya.test.objectLayer;

import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import bright.api.alaya.pages.objectLayer.MemberSetDefaultValuesPage;
import bright.api.alaya.utils.MainClassAlaya;
import io.restassured.specification.RequestSpecification;

public class MemberSetDefaultValuesTest extends MainClassAlaya{

	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description ="Verify Other State when business partner is Non Bright") 
	public void verifySetToOtherForNonBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		MemberSetDefaultValuesPage setDefault=new MemberSetDefaultValuesPage();
		MemberSetDefaultValuesPage.cityNotNullCountyNotNullStateNullNonBright(httpRequest);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description ="Verify all null values when business partner is Non Bright") 
	public void verifyallNullValuesCheckNonBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		MemberSetDefaultValuesPage setDefault=new MemberSetDefaultValuesPage();
		MemberSetDefaultValuesPage.allNullValuesCheckNonBright(httpRequest);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description ="Verify all valid values when business partner is Non Bright") 
	public void verifyallValidValuesNonBright() throws InterruptedException, JsonMappingException, JsonProcessingException, NumberFormatException, JSONException, ParseException {
		RequestSpecification httpRequest= null;
		MemberSetDefaultValuesPage setDefault=new MemberSetDefaultValuesPage();
		MemberSetDefaultValuesPage.allValidValuesNonBright(httpRequest);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description ="Verify county as null and state as null when business partner is Non Bright") 
	public void verifycityNullCountyNotNullStateNotNullNonBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		MemberSetDefaultValuesPage setDefault=new MemberSetDefaultValuesPage();
		MemberSetDefaultValuesPage.cityNullCountyNotNullStateNotNullNonBright(httpRequest);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description ="Verify county as null , state as null and city as invalid  when business partner is Non Bright") 
	public void verifycityInvalidCountyNotNullStateNotNullNonBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		MemberSetDefaultValuesPage setDefault=new MemberSetDefaultValuesPage();
		MemberSetDefaultValuesPage.cityInvalidCountyNotNullStateNotNullNonBright(httpRequest);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description ="Verify valid county as null , state as null and valid city when business partner is Non Bright") 
	public void verifycityNotNullCountyNotNullStateNullNonBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		MemberSetDefaultValuesPage setDefault=new MemberSetDefaultValuesPage();
		MemberSetDefaultValuesPage.cityNotNullCountyNotNullStateNullNonBright(httpRequest);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description ="Verify valid county as null ,  valid state  and valid city when business partner is Non Bright") 
	public void verifycityNotNullCountyNullStateNotNullNonBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		MemberSetDefaultValuesPage setDefault=new MemberSetDefaultValuesPage();
		MemberSetDefaultValuesPage.cityNotNullCountyNullStateNotNullNonBright(httpRequest);
	}  
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description ="Verify all null values when business partner is Bright") 
	public void verifyallNullValuesCheckBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		MemberSetDefaultValuesPage setDefault=new MemberSetDefaultValuesPage();
		MemberSetDefaultValuesPage.allNullValuesCheckBright(httpRequest);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description ="Verify all valid values when business partner is Bright") 
	public void verifyallValidValuesBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		MemberSetDefaultValuesPage setDefault=new MemberSetDefaultValuesPage();
		MemberSetDefaultValuesPage.allValidValuesBright(httpRequest);
	}
	
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description ="Verify valid city , county as null and valid state  when business partner is Bright") 
	public void verifycityNotNullCountyNullStateNotNullBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		MemberSetDefaultValuesPage setDefault=new MemberSetDefaultValuesPage();
		MemberSetDefaultValuesPage.cityNotNullCountyNullStateNotNullBright(httpRequest);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description ="Verify city as null , valid county as null and  valid state  when business partner is Bright") 
	public void verifycitNullCountyNotNullStateNotNullBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		MemberSetDefaultValuesPage setDefault=new MemberSetDefaultValuesPage();
		MemberSetDefaultValuesPage.citNullCountyNotNullStateNotNullBright(httpRequest);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description ="Verify city as null , valid county as null and  state null  when business partner is Bright") 
	public void verifycityNullCountyNotNullStateNullBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		MemberSetDefaultValuesPage setDefault=new MemberSetDefaultValuesPage();
		MemberSetDefaultValuesPage.cityNullCountyNotNullStateNullBright(httpRequest);
	}
	
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description ="Verify city as null , county as null and  valid state when business partner is Bright") 
	public void verifycityNullCountyNullStateNotNullBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		MemberSetDefaultValuesPage setDefault=new MemberSetDefaultValuesPage();
		MemberSetDefaultValuesPage.cityNullCountyNullStateNotNullBright(httpRequest);
	}
	
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description ="Verify invalid city  , county as null and  valid state when business partner is Bright") 
	public void verifycityInValidCountyNullStateNotNullBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		MemberSetDefaultValuesPage setDefault=new MemberSetDefaultValuesPage();
		MemberSetDefaultValuesPage.cityInValidCountyNullStateNotNullBright(httpRequest);
	}
}
	
