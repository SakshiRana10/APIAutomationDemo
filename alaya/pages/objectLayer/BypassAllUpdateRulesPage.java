package bright.api.alaya.pages.objectLayer;

import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Assert;

import bright.api.alaya.pages.discoveryLayer.discoveryLayerPostAPIPage;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import io.restassured.specification.RequestSpecification;

public class BypassAllUpdateRulesPage extends MainClassAlaya {

	private static final String Locale = "Bright" ;
	private static final String True = "true";
	
	/** 
	 * A Method to create session key and token with bypass rule role application
	 **/ 		
	public static void createSessionWithByPassRole() {	
	try {
		String sessionid = SessionManagementPage.fetchSessionToken("brokerId", "brokerPassword");
		String code = SessionManagementPage.fetchLocationfromCode(sessionid);
		String idToken = SessionManagementPage.fetchIDToken(code);
		bypassSessionKey = SessionManagementPage.fetchSessionkeyWithByPassRole(idToken);
		bypassBearerToken = SessionManagementPage.fetchBearerToken();
		logger.info("Successfully got the bypass all rules session token for user :" + property.getProperty("UserName"));
	   } catch (InterruptedException e) {
		e.printStackTrace();
	   }
	 if(bypassSessionKey.length()==0 || bypassBearerToken.length()==0) {
		Assert.fail("Session Id was null for Application id : 550032996738, while running bypass rule role test cases");
	 }
	}
	
	/** 
	 * A Method to create payload and put call for object layer using by pass role
	 **/	
	public static JSONObject createPayloadAndCallPutObject(JSONObject roles,String oficeStatus,RequestSpecification httpRequest,String response,String docName,String docType) {
		JSONObject payload = new JSONObject();
		JSONObject responseObj = new JSONObject(response);
		JSONObject contentObj = responseObj.getJSONObject("content");
		JSONObject lmsObj = contentObj.getJSONObject("lmsObject");	
		lmsObj.put("roles", roles);
		long status=Long.parseLong(oficeStatus);
		lmsObj.put("officeStatus", status);
		contentObj.put("lmsObject", lmsObj);
		payload.put("content", contentObj);	
		payload.putOpt("lastUpdatedBy", "core Alaya qa automation for testing rule bypass role");	
		String lastModified = new JSONObject(response).getJSONObject("metadata").get("lastModified").toString();
		//verify success for put call for object layer using by pass role
		ObjectLayerPage.PutCallAPIObjectLayerForByPassRules(httpRequest,True,True,True,True,Locale,docName,docType,ResponseCodes.SUCCESS,payload,lastModified);
		String getResponseAfterPut = ObjectLayerPage.getCallForObjectyLayer(httpRequest,"Bright",docType,docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject getResponseAfterPutObj = new JSONObject(getResponseAfterPut);
		JSONObject result = getResponseAfterPutObj.getJSONObject("content").getJSONObject("lmsObject");
		return result;
	}
	
	/** 
	 * A Method to verify no default roles were added in office document when bypass role was used - PUT API
	 **/ 
	public static void verifySetDefaultValuesActiveRulesBypassedInPut(RequestSpecification httpRequest,JSONObject role,String response,String docName,boolean sysPrCurrent, boolean sysPrLastCurrent,int rolesSize, String docType) throws ParseException {
		String status = null;
		String statusReponse=OfficeStaticRulePage.getConfigAPIWithPicklistIdAndName("OFFICE_STATUSES");
		JSONObject itemsStatus = new JSONObject(statusReponse).getJSONObject("item");
		JSONObject pickListStatus=(JSONObject) itemsStatus.get("pickListItems");
		
		try {
			status = ValidateAPIPage.getParentKeyForItemId(pickListStatus, "Active", "displayValueLong");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		JSONObject resultant = createPayloadAndCallPutObject(role,status,httpRequest,response,docName,docType);
		//verifying new roles did not got added because rules were bypassed 
		Assert.assertEquals(rolesSize, ValidateAPIPage.getAllRolesKey(resultant).size(),"Role size was not as expected, It was - "+ValidateAPIPage.getAllRolesKey(resultant).size());    
		//verifying the only role document have is the one given by us 
		String roleName = null;
		JSONObject rolesObj = (JSONObject) resultant.getJSONObject("roles");
        Iterator<String> keys = rolesObj.keys();
        while (keys.hasNext()) {
        	String key = keys.next();
            if (rolesObj.get(key) instanceof JSONObject) {
               roleName =  new JSONObject(rolesObj.get(key).toString()).get("sysPrRoleName").toString();
            }
        }
		Assert.assertEquals(roleName, "Login","Role name was not 'Login', It was - "+roleName);
		//Reverting back the doc to original state using response
		JSONObject responseObj = new JSONObject(response);
		JSONObject contentObj = responseObj.getJSONObject("content");
		JSONObject payload = new JSONObject();
		payload.putOpt("content", contentObj);
		payload.putOpt("lastUpdatedBy", "core Alaya qa automation for bypass rule role testing Reverting back to original");
		String getResponseForReverting = ObjectLayerPage.getCallForObjectyLayer(httpRequest,"Bright",docType,docName,ResponseCodes.SUCCESS).getBody().asString();
		String lastModified = new JSONObject(getResponseForReverting).getJSONObject("metadata").get("lastModified").toString();
		ObjectLayerPage.PutCallAPIObjectLayer(httpRequest,True,True,True,True,Locale,docName,docType,ResponseCodes.SUCCESS,payload,lastModified);	
	}
	
	/** 
	 * A Method to verify no rules were executed in member document when bypass role was used - POST API
	 **/ 
	public static void verifyAllValidValuesRulesBypassedInPost(RequestSpecification httpRequest,JSONObject rulesData,String docType) {
	    ArrayList<String> result = ObjectLayerPostAPIPage.verifySuccessStatusforPOSTAPIByPassRules(httpRequest,docType);
	    String docName = result.get(0);
	    String originalPayload = result.get(1);
	    String getResponseAfterPost = ObjectLayerPage.getCallForObjectyLayer(httpRequest,"Bright",docType,docName,ResponseCodes.SUCCESS).getBody().asString();
	    JSONObject lmsObj = new JSONObject(getResponseAfterPost).getJSONObject("content").getJSONObject("lmsObject");
	    String memberAdd1 = lmsObj.get("memberAddress1").toString();
	    JSONObject originalLmsObj = new JSONObject(originalPayload).getJSONObject("content").getJSONObject("lmsObject");
	    String originalMemberAdd1 = originalLmsObj.get("memberAddress1").toString();
	    //verifying the address rule did not got executed and it is same as the original one
	    Assert.assertEquals(memberAdd1, originalMemberAdd1,"Member address was changed for bypass rules appication, Original value was - "+originalMemberAdd1+" New value is - "+memberAdd1);	    
	    //Deleting the above created document for member
	    ObjectLayerDeleteAPIPage.verifySuccessForDelete(httpRequest, "Bright", docType, docName);
	}
	
	/** 
	 * A Method to verify no rules were executed in office document when bypass role was used - VALIDATE API
	 **/ 
	public static void verifyActivePrCurrentOfficeRuleByPassedInValidate(RequestSpecification httpRequest) throws ParseException {
		JSONObject role=OfficeSetDefaultValuesPage.addRoles(true,false,OfficeSetDefaultValuesPage.getCurrentTimeStamp(true),StaticRuleTestDataPage.sysPrKeyOfficeRole);
		String status = null;
		String statusReponse=OfficeStaticRulePage.getConfigAPIWithPicklistIdAndName("OFFICE_STATUSES");
		JSONObject itemsStatus = new JSONObject(statusReponse).getJSONObject("item");
		JSONObject pickListStatus=(JSONObject) itemsStatus.get("pickListItems");
		try {
			status = ValidateAPIPage.getParentKeyForItemId(pickListStatus, "Active", "displayValueLong");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		JSONObject resultant=OfficeSetDefaultValuesPage.triggerValidateAPIForByPassingRules(role,status,httpRequest);
		//verifying new roles did not got added because rules were bypassed 
		Assert.assertEquals(1, ValidateAPIPage.getAllRolesKey(resultant).size(),"Role size was not as expected, It was - "+ValidateAPIPage.getAllRolesKey(resultant).size());
		//verifying the only role document have is the one given by us 
				String roleName = null;
				JSONObject rolesObj = (JSONObject) resultant.getJSONObject("roles");
		        Iterator<String> keys = rolesObj.keys();
		        while (keys.hasNext()) {
		        	String key = keys.next();
		            if (rolesObj.get(key) instanceof JSONObject) {
		               roleName =  new JSONObject(rolesObj.get(key).toString()).get("sysPrRoleName").toString();
		            }
		        }
				Assert.assertEquals(roleName, "Login","Role name was not 'Login', It was - "+roleName);		
	}	
}
