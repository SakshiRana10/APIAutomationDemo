package bright.api.alaya.pages.objectLayer;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.asserts.SoftAssert;

import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.MainClassAlaya;
import io.restassured.specification.RequestSpecification;


    public class OfficeSysPartyRoleUpdateRulePage extends MainClassAlaya{
 
	
	public static final ArrayList<String> docsWithRoleKeyTest = new ArrayList<String>(Arrays.asList("240245163902","240283421645","240283421821","240282662912","240274316398","240280221723","240247479320","240245201420","240245154217","240282662404"));
	public static final ArrayList<String> docsWithRoleKeyDev = new ArrayList<String>(Arrays.asList("200009126107","200009096922","200009096916","200009096910","200009125836","200007159119","200007149324","200009177920","200007159113","200016513622"));
	
	public static HashMap<String,ArrayList<String>> docsWithRoleKeyMap = new HashMap<String,ArrayList<String>>(){{
	    	 put("t1", docsWithRoleKeyTest);
	    	 put("d1", docsWithRoleKeyDev);
	    }};
	    
	/*A method to Add role to further check in validate api*/
	
	public static JSONObject addRole(String responseBodyFromGet,String roleKey, String partyKey, String parentPartyKey,String goneState){
		JSONObject roleValue = new JSONObject();
		JSONObject newRole=new JSONObject();
		JSONObject lmsobj = new JSONObject(responseBodyFromGet).getJSONObject("lmsObject");
		JSONObject rolesJson = lmsobj.getJSONObject("roles");
		List<String> keysForRemoval = rolesJson.keySet().stream().collect(Collectors.toList());
		keysForRemoval.forEach(rolesJson::remove);
		lmsobj.put("roles", rolesJson);
		if(CommonUtilityMethods.getEnvironment().equalsIgnoreCase("t1"))
		   roleValue=new JSONObject(StaticRuleTestDataPage.officeRoleValueTst);
		else if(CommonUtilityMethods.getEnvironment().equalsIgnoreCase("d1"))
		   roleValue=new JSONObject(StaticRuleTestDataPage.officeRoleValueDev);
		roleValue.put("sysPrParentPartyKey", new BigInteger(parentPartyKey));
		roleValue.put("sysPrPartyKey", new BigInteger(partyKey));
		roleValue.put("sysPrRoleKey", new BigInteger(roleKey));
		if(goneState.equalsIgnoreCase("Gone"))     //checking for the scenario where non mandatory field is removed to verify no impact on rule validation
		 roleValue.remove("sysPrState");
		if(CommonUtilityMethods.getEnvironment().equalsIgnoreCase("t1"))
		  newRole.put(StaticRuleTestDataPage.officeRoleKeyTst, roleValue);
		else if(CommonUtilityMethods.getEnvironment().equalsIgnoreCase("d1"))
		  newRole.put(StaticRuleTestDataPage.officeRoleKeyDev, roleValue);	
		return newRole;
	}
	
	/*A method to Add role for the case when keys are gone from the role object*/
	
	public static JSONObject addRoleForGoneKeys(String responseBodyFromGet,String firstKeyName,String secondKeyName ,String firstKey, String secondKey){
		JSONObject roleValue = new JSONObject();
		JSONObject newRole=new JSONObject();
		JSONObject lmsobj = new JSONObject(responseBodyFromGet).getJSONObject("lmsObject");
		JSONObject rolesJson = lmsobj.getJSONObject("roles");
		List<String> keysForRemoval = rolesJson.keySet().stream().collect(Collectors.toList());
		keysForRemoval.forEach(rolesJson::remove);
		lmsobj.put("roles", rolesJson);
		if(CommonUtilityMethods.getEnvironment().equalsIgnoreCase("t1"))
			 roleValue=new JSONObject(StaticRuleTestDataPage.officeRoleValueTst);
		else if(CommonUtilityMethods.getEnvironment().equalsIgnoreCase("d1"))
			 roleValue=new JSONObject(StaticRuleTestDataPage.officeRoleValueDev);
		roleValue.put(firstKeyName, new BigInteger(firstKey));
		roleValue.put(secondKeyName, new BigInteger(secondKey));
		if(CommonUtilityMethods.getEnvironment().equalsIgnoreCase("t1"))
			 newRole.put(StaticRuleTestDataPage.officeRoleKeyTst, roleValue);
		else if(CommonUtilityMethods.getEnvironment().equalsIgnoreCase("d1"))
			 newRole.put(StaticRuleTestDataPage.officeRoleKeyDev, roleValue);
		return newRole;
	}
	
	/*A method to create payload to be passed in validate api*/
	
	public static String createPayLoadForValidateAPI(RequestSpecification httpRequest ,String responseBodyFromGet,JSONObject role) throws InterruptedException {
		JSONObject content=null;
		JSONObject finalObj=new JSONObject(responseBodyFromGet);
		JSONObject lmsobj = new JSONObject(responseBodyFromGet).getJSONObject("lmsObject");
		lmsobj.put("roles", role);
		JSONObject responseBodyObj = new JSONObject();
		finalObj.put("lmsObject", lmsobj);
		responseBodyObj.put("content", finalObj);
		String payload = responseBodyObj.toString();
		String Json=ValidateAPIPage.postValidateAPI("bright", "office",payload,httpRequest);
		return Json;
	}
	
	/*A method to verify if circular dependency exists or not*/
	
    public static void VerifyCircularDependency(RequestSpecification httpRequest,String roleKey, String partyKey, String parentPartyKey,String goneState) throws JSONException, InterruptedException {
    	SoftAssert softAssert = new SoftAssert();
    	String officeDocName = docsWithRoleKeyMap.get(CommonUtilityMethods.getEnvironment()).get(CommonUtilityMethods.pickRandomFromList(docsWithRoleKeyMap.get(CommonUtilityMethods.getEnvironment())));
    	logger.info("Document picked for rule validation is - "+officeDocName);
    	String responseBodyFromGet = CommonUtilityMethods.fetchDocumentfromDocStore(httpRequest, "office", officeDocName);
    	/* Placing correct values for all three parameters to generate rule validation - sysPrRoleKey, sysPrPartyKey, sysPrParentPartyKey */
    	JSONObject roleObj = addRole(responseBodyFromGet,roleKey,partyKey,parentPartyKey,goneState);
    	JSONObject resultFromValidateAPI = new JSONObject(createPayLoadForValidateAPI(httpRequest, responseBodyFromGet,roleObj));
    	JSONArray ruleValidationResultsArr = CommonUtilityMethods.fetchJArrayFromApiResponse(resultFromValidateAPI.toString(),"ruleValidationResults");
    	String msg = ruleValidationResultsArr.getJSONObject(0).get("message").toString();
    	String ExpectedMsg = "Detected incoming circular dependency of 'Branch Office' roles detected";
    	if(msg.contains(ExpectedMsg))
           logger.info("Circular dependency was detected");
    	else
    	   softAssert.fail("Circular dependency was not detected");
    	softAssert.assertAll();	
    }
    
    /*A method to verify that a circular dependency should not exist if wrong paramaters are given*/
    
    public static void VerifyNoCircularDependency(RequestSpecification httpRequest,String roleKey, String partyKey, String parentPartyKey,String goneState) throws JSONException, InterruptedException {
    	SoftAssert softAssert = new SoftAssert();
    	String officeDocName = docsWithRoleKeyMap.get(CommonUtilityMethods.getEnvironment()).get(CommonUtilityMethods.pickRandomFromList(docsWithRoleKeyMap.get(CommonUtilityMethods.getEnvironment())));
    	logger.info("Document picked for rule validation is - "+officeDocName);
    	String responseBodyFromGet = CommonUtilityMethods.fetchDocumentfromDocStore(httpRequest, "office", officeDocName);
    	/* Placing Different value combinations for all three parameters to not generate rule validation - sysPrRoleKey, sysPrPartyKey, sysPrParentPartyKey */
    	JSONObject roleObj = addRole(responseBodyFromGet,roleKey,partyKey,parentPartyKey,goneState);
    	JSONObject resultFromValidateAPI = new JSONObject(createPayLoadForValidateAPI(httpRequest, responseBodyFromGet,roleObj));
    	JSONArray ruleValidationResultsArr = CommonUtilityMethods.fetchJArrayFromApiResponse(resultFromValidateAPI.toString(),"ruleValidationResults");
    	if(ruleValidationResultsArr.length()==0)
           logger.info("Circular dependency was not detected");
    	else
    	   softAssert.fail("Circular dependency was detected even with wrong parameters");
    	softAssert.assertAll();	
    }
    
    /*A method to verify that a circular dependency should not exist if needed paramaters are gone*/
    
    public static void VerifyNoCircularDependencyForGoneKeys(RequestSpecification httpRequest,String firstKeyName, String secondKeyName, String firstKey, String secondKey) throws JSONException, InterruptedException {
    	SoftAssert softAssert = new SoftAssert();
    	String officeDocName = docsWithRoleKeyMap.get(CommonUtilityMethods.getEnvironment()).get(CommonUtilityMethods.pickRandomFromList(docsWithRoleKeyMap.get(CommonUtilityMethods.getEnvironment())));
    	logger.info("Document picked for rule validation is - "+officeDocName);
    	String responseBodyFromGet = CommonUtilityMethods.fetchDocumentfromDocStore(httpRequest, "office", officeDocName);
    	/* Placing Differentcombinations for all three parameters to not generate rule validation if they are gone - sysPrRoleKey, sysPrPartyKey, sysPrParentPartyKey */
    	JSONObject roleObj = addRoleForGoneKeys(responseBodyFromGet,firstKeyName,secondKeyName,firstKey,secondKey);
    	JSONObject resultFromValidateAPI = new JSONObject(createPayLoadForValidateAPI(httpRequest, responseBodyFromGet,roleObj));
    	JSONArray ruleValidationResultsArr = CommonUtilityMethods.fetchJArrayFromApiResponse(resultFromValidateAPI.toString(),"ruleValidationResults");
    	if(ruleValidationResultsArr.length()==0)
           logger.info("Circular dependency was not detected");
    	else
    	   softAssert.fail("Circular dependency was detected even with wrong parameters");
    	softAssert.assertAll();	
    }



}