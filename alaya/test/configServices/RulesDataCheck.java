package bright.api.alaya.test.configServices;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import bright.api.alaya.pages.configServices.CreateThreadPage;
import bright.api.alaya.pages.configServices.OneAdminRulesData;
import bright.api.alaya.pages.configServices.RulesDataPage;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.MainClassAlaya;
import io.restassured.specification.RequestSpecification;
public class RulesDataCheck extends MainClassAlaya {
	
	


	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify Bright Rules data with config and one admin")

	public void verigyFlagDataBright() throws IOException, ParseException, InterruptedException {
		
		
		 SoftAssert softAssert = new SoftAssert();
		CreateThreadPage thread=new CreateThreadPage();
		 thread.divideIntoThreads();
		 RulesDataPage.getRulesJson(Constants.congRulesBusinessView, Constants.BrightLocale,softAssert);
		 softAssert.assertAll();
		
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify Wirex Rules data with config and one admin")
	public void verigyFlagDataWirex() throws IOException, ParseException, InterruptedException {
		
		RequestSpecification httpRequestWirexRulesData = null;
		 SoftAssert softAssert = new SoftAssert();
		 OneAdminRulesData.getRuleUsageFileForAPICall(httpRequestWirexRulesData,Constants.WirexRuleConfigMapping,Constants.WirexLocale);
		 RulesDataPage.getRulesJson(Constants.congRulesBusinessView, Constants.WirexLocale,softAssert);
		 softAssert.assertAll();
		
	}

	
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify Bright Rules flag data with config and one admin")
	public void VerifyRulesWirex() throws IOException, ParseException {
		

		 RequestSpecification httpRequestWirexRulesData = null;
		 SoftAssert softAssert = new SoftAssert();
		RulesDataPage.mapFieldsIdsFromConfig(Constants.WirexLocale);
		RulesDataPage.fetchFieldIdsFromFile(httpRequestWirexRulesData, Constants.WirexLocale, Constants.WirexFieldIDMapping);
		
		RulesDataPage.getRulesData(Constants.congRulesBusinessView, Constants.WirexLocale,softAssert);
		 softAssert.assertAll();
		
	}
	
	

	
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify Wirex Rules flag data with config and one admin")

	public void VerifyRulesBright() throws IOException, ParseException {
		

		 RequestSpecification httpRequestWirexRulesData = null;
		 SoftAssert softAssert = new SoftAssert();
		RulesDataPage.mapFieldsIdsFromConfig(Constants.BrightLocale);
		RulesDataPage.fetchFieldIdsFromFile(httpRequestWirexRulesData, Constants.BrightLocale, Constants.BrightFieldIdMapping);
		RulesDataPage.getRulesData(Constants.congRulesBusinessView, Constants.BrightLocale,softAssert);
		 softAssert.assertAll();
		
	}

	
	
	
	
}