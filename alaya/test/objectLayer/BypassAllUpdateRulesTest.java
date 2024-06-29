package bright.api.alaya.test.objectLayer;

import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import bright.api.alaya.pages.objectLayer.BypassAllUpdateRulesPage;
import bright.api.alaya.pages.objectLayer.ObjectLayerGetAPIPage;
import bright.api.alaya.pages.objectLayer.ObjectLayerMemberRulesPage;
import bright.api.alaya.pages.objectLayer.ObjectLayerOfficeRulesPage;
import bright.api.alaya.pages.objectLayer.ObjectLayerPage;
import bright.api.alaya.pages.objectLayer.ObjectLayerPutAPIPage;
import bright.api.alaya.pages.objectLayer.OfficeSetDefaultValuesPage;
import bright.api.alaya.pages.objectLayer.StaticRuleTestDataPage;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import io.restassured.specification.RequestSpecification;

public class BypassAllUpdateRulesTest  extends MainClassAlaya{
	
	public static String locale = "Bright";
	public static String office = "office";
	public static String member = "member";
	public static JSONObject payload = null;
	public static String docName = null;
	
	
	@Test ( groups = {"test","dev"}, priority = 0, enabled = true, description ="get session id for the application that have bypass rule role") 
	public void getSessionKeyForBypassRoleApp() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException {
		BypassAllUpdateRulesPage.createSessionWithByPassRole();
  }

	
	@Test ( dependsOnMethods = {"getSessionKeyForBypassRoleApp"}, groups = {"test","dev"}, priority = 1, enabled = true, description ="verify Office Default Rule is ByPassed In Put object Call") 
	public void verifyOfficeDefaultRuleBypassInPutCall() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(office)){
		ObjectLayerOfficeRulesPage ruleObject=new ObjectLayerOfficeRulesPage();
		docName=ObjectLayerGetAPIPage.getRandomDocName(office);
		String originalResponse = ObjectLayerPage.getCallForObjectyLayer(httpRequest,locale,office,docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject role=ObjectLayerOfficeRulesPage.addRoles(true,true,StaticRuleTestDataPage.sysPrKeyOfficeRole,originalResponse);
		BypassAllUpdateRulesPage.verifySetDefaultValuesActiveRulesBypassedInPut(httpRequest,role,originalResponse,docName,true,true,1,office);	
		}
	else
	{
	    logger.info("Put call was skipped because system toggle was disabled. Test passed.");
		Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(office),"System toggle is disabled for document Type - "+office);	
	}
  }
	
	@Test ( dependsOnMethods = {"getSessionKeyForBypassRoleApp"},groups = {"test", "dev"}, priority = 1, enabled = true, description ="verify member Default address Rule is ByPassed In Post object Call") 
	public void verifyallValidValuesRuleBypassInPostCall() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(member)){
			ObjectLayerMemberRulesPage ruleObject =new ObjectLayerMemberRulesPage();
			JSONObject rulesData = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"allValidValuesBright").getJSONObject("rulesData");
			BypassAllUpdateRulesPage.verifyAllValidValuesRulesBypassedInPost(httpRequest,rulesData,member);
		}
		else
		{
		    logger.info("Post call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(member),"System toggle is disabled for document Type - "+member);	
		}
	}
	
	@Test ( dependsOnMethods = {"getSessionKeyForBypassRoleApp"},groups = {"test", "dev"}, priority = 1, enabled = true, description ="verify Office Default Rule is ByPassed In Validate object Call") 
	public void verifyActivePrCurrentOfficeRuleByPassInValidateCall() throws InterruptedException, JsonMappingException, JsonProcessingException, ParseException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(office)){
			OfficeSetDefaultValuesPage setValue=new OfficeSetDefaultValuesPage();
			BypassAllUpdateRulesPage.verifyActivePrCurrentOfficeRuleByPassedInValidate(httpRequest);
		}
		else
		{
		    logger.info("Validate call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(office),"System toggle is disabled for document Type - "+office);	
		}
	}
}
