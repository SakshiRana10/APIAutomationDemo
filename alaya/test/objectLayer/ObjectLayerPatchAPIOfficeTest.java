package bright.api.alaya.test.objectLayer;

import java.util.ArrayList;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import bright.api.alaya.pages.objectLayer.ObjectLayerDeleteAPIPage;
import bright.api.alaya.pages.objectLayer.ObjectLayerPage;
import bright.api.alaya.pages.objectLayer.ObjectLayerPatchAPIPage;
import bright.api.alaya.pages.objectLayer.ObjectLayerPostAPIPage;
import bright.api.alaya.pages.objectLayer.ObjectLayerPutAPIPage;
import bright.api.alaya.pages.objectLayer.ObjectLayerOfficeRulesPage;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import io.restassured.specification.RequestSpecification;

public class ObjectLayerPatchAPIOfficeTest extends MainClassAlaya {


	public static String locale = "Bright";
	public static String office = "office";
	public static String docName = null;
	public static ArrayList<String> result = new ArrayList<String>();
	public static JSONObject payload = null;
	public static String response = null;
	public static JSONObject rulesData = new JSONObject();

	@Test  (dependsOnMethods = {"verifycityNotNullCountyNotNullStateNullBright"}, groups = {"test", "dev"}, priority = 3, enabled = true, description = "Verify Success Status for delete office")
	public void verifyDeleteSuccess() {	
		RequestSpecification httpRequest = null;	
		if(docName != null)
			ObjectLayerDeleteAPIPage.verifySuccessForDelete(httpRequest, locale, office, docName);
		else
			logger.info("No document found for deletion");
	}

	@Test ( groups = {"test", "dev"}, priority = 1, enabled = true, description ="Verify valid county as not null , state as null and valid city when business partner is Bright") 
	public void verifycityNotNullCountyNotNullStateNullBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(office)){

			result = ObjectLayerPostAPIPage.verifySuccessStatusforPOSTAPIObjectLayer(httpRequest,office);
			ObjectLayerPatchAPIOfficeTest.docName = result.get(0);
			rulesData = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"StateNullBright").getJSONObject("rulesData");
			ObjectLayerPatchAPIPage.cityNotNullCountyNotNullStateNullBright(httpRequest,docName,locale,office,rulesData );

		}
		else
		{
			logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(office),"System toggle is disabled for document Type - "+office);	
		}	
	}


	@Test  (dependsOnMethods = {"verifycityNotNullCountyNotNullStateNullBright"},groups = {"test", "dev"}, priority = 3, enabled = true, description ="Verify valid county as not null , state as null and valid city when business partner is Bright") 
	public void addMedia() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(office)){

			JSONObject payload = CommonUtilityMethods.readJsonFile(property.getProperty("objectPatchPayloadJsonPath"),"mediaPayload_"+CommonUtilityMethods.getEnvironment());


			ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"true","true",locale,docName,office,ResponseCodes.SUCCESS,payload);

		}
		else
		{
			logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(office),"System toggle is disabled for document Type - "+office);	
		}	
	}


	@Test  (dependsOnMethods = {"verifycityNotNullCountyNotNullStateNullBright"}, groups = {"test", "dev"}, priority = 3, enabled = true, description ="Verify valid county as not null , state as null and valid city when business partner is Bright") 
	public void addPartyPermission() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(office)){

			JSONObject payload = CommonUtilityMethods.readJsonFile(property.getProperty("objectPatchPayloadJsonPath"),"partyProfilePayload_"+CommonUtilityMethods.getEnvironment());
			ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"false","false",locale,docName,office,ResponseCodes.SUCCESS_ACCEPTED,payload);

		}
		else
		{
			logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(office),"System toggle is disabled for document Type - "+office);	
		}	
	}




	@Test  (dependsOnMethods = {"verifycityNotNullCountyNotNullStateNullBright"}, groups = {"test", "dev"}, priority = 3, enabled = true, description ="Verify valid county as not null , state as null and valid city when business partner is Bright") 
	public void verifyAllValidValuesBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(office)){


			rulesData = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"allValidValuesBright").getJSONObject("rulesData");
			ObjectLayerPatchAPIPage.allValidValuesBright(httpRequest,docName,locale,office,rulesData);

		}
		else
		{
			logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(office),"System toggle is disabled for document Type - "+office);	
		}	
	}


	@Test  (dependsOnMethods = {"verifycityNotNullCountyNotNullStateNullBright"}, groups = {"test", "dev"}, priority = 3, enabled = true, description ="Verify valid county as not null , state as null and valid city when business partner is Bright") 
	public void verifycitNullCountyNotNullStateNotNullBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(office)){


			rulesData = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"cityNullBright").getJSONObject("rulesData");
			ObjectLayerPatchAPIPage.verifycitNullCountyNotNullStateNotNullBright(httpRequest,docName,locale,office,rulesData);

		}

		else
		{
			logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(office),"System toggle is disabled for document Type - "+office);	
		}	
	}


	@Test  (dependsOnMethods = {"verifycityNotNullCountyNotNullStateNullBright"},groups = {"test", "dev"}, priority = 3, enabled = true, description ="Verify valid county as not null , state as null and valid city when business partner is Bright") 
	public void verifycityNullCountyNotNullStateNullBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(office)){

			rulesData = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"cityNullStateNullBright").getJSONObject("rulesData");
			ObjectLayerPatchAPIPage.verifycityNullCountyNotNullStateNullBright(httpRequest,docName,locale,office,rulesData);

		}
		else
		{
			logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(office),"System toggle is disabled for document Type - "+office);	
		}	
	}



	@Test  (dependsOnMethods = {"verifycityNotNullCountyNotNullStateNullBright"}, groups = {"test", "dev"}, priority = 3, enabled = true, description ="Verify valid county as not null , state as null and valid city when business partner is Bright") 
	public void verifycityNullCountyNullStateNotNullBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(office)){


			docName = result.get(0);
			rulesData = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"cityNullCountyNullBright").getJSONObject("rulesData");
			ObjectLayerPatchAPIPage.verifycityNullCountyNullStateNotNullBright(httpRequest,docName,locale,office,rulesData);

		}
		else
		{
			logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(office),"System toggle is disabled for document Type - "+office);	
		}	
	}

	@Test  (dependsOnMethods = {"verifycityNotNullCountyNotNullStateNullBright"},groups = {"test", "dev"}, priority = 3, enabled = true, description ="Verify valid county as not null , state as null and valid city when business partner is Bright") 
	public void verifycityInValidCountyNullStateNotNullBright() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(office)){


			docName = result.get(0);
			rulesData = CommonUtilityMethods.readJsonFile(Constants.objectlayer,"cityInvalidCountyNullBright").getJSONObject("rulesData");
			ObjectLayerPatchAPIPage.verifycityInValidCountyNullStateNotNullBright(httpRequest,docName,locale,office,rulesData);

		}
		else
		{
			logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(office),"System toggle is disabled for document Type - "+office);	
		}	
	}

	@Test  (dependsOnMethods = {"verifycityNotNullCountyNotNullStateNullBright"}, groups = {"test", "dev"}, priority = 3, enabled = true, description ="Verify valid county as not null , state as null and valid city when business partner is Bright") 
	public void verifyInValidDocType() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(office)){

			JSONObject payload = CommonUtilityMethods.readJsonFile(property.getProperty("objectPatchPayloadJsonPath"),"mediaPayload_"+CommonUtilityMethods.getEnvironment());


			ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"true","true",locale,docName,"builder_model",ResponseCodes.NO_SUCH_KEY,payload);

		}
		else
		{
			logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(office),"System toggle is disabled for document Type - "+office);	
		}	
	}


	@Test  (dependsOnMethods = {"verifycityNotNullCountyNotNullStateNullBright"}, groups = {"test", "dev"}, priority = 3, enabled = true, description ="Verify valid county as not null , state as null and valid city when business partner is Bright") 
	public void verifyInValidOperation() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(office)){

			JSONObject payload = CommonUtilityMethods.readJsonFile(property.getProperty("objectPatchPayloadJsonPath"),"inValidPatchOperation");


			ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"true","true",locale,docName,"office",ResponseCodes.BAD_REQUEST,payload);

		}
		else
		{
			logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(office),"System toggle is disabled for document Type - "+office);	
		}	
	}


	@Test  (dependsOnMethods = {"verifycityNotNullCountyNotNullStateNullBright"}, groups = {"test", "dev"}, priority = 3, enabled = true, description ="Verify valid county as not null , state as null and valid city when business partner is Bright") 
	public void verifyInValidParamter() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		if(CommonUtilityMethods.getFeatureToggle(office)){

			JSONObject payload = CommonUtilityMethods.readJsonFile(property.getProperty("objectPatchPayloadJsonPath"),"inValidPatchParameter");


			ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"true","true",locale,docName,"office",ResponseCodes.BAD_REQUEST,payload);

		}
		else
		{
			logger.info("Put call was skipped because system toggle was disabled. Test passed.");
			Assert.assertFalse(CommonUtilityMethods.getFeatureToggle(office),"System toggle is disabled for document Type - "+office);	
		}	
	}





}


