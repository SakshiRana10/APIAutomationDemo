package bright.api.alaya.pages.objectLayer;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Assert;

import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.EnvSpecificMethods;
import bright.api.alaya.utils.MainClassAlaya;
import io.restassured.specification.RequestSpecification;

public class OfficeSetDefaultValuesPage  extends MainClassAlaya{
	static String listingDataJson;
	static JSONObject lmsobj;
	static JSONObject pickListStatus;
	static ArrayList<String> AttributeNamesForES;
	static ArrayList<String> officeKey;
	static String statusReponse;
	static JSONObject items;
	private static final String env = CommonUtilityMethods.getEnvironment();
    private static final String region = CommonUtilityMethods.getRegionString();

	public OfficeSetDefaultValuesPage(){
		RequestSpecification httpRequest = null;    
		AttributeNamesForES = new ArrayList<String>(Arrays.asList("officeKey"));
		
		listingDataJson=CommonUtilityMethods.fetchDocumentfromDocStore(httpRequest,"office",officeDocumentNames.get(0));	
		 statusReponse=OfficeStaticRulePage.getConfigAPIWithPicklistIdAndName("OFFICE_STATUSES");
		 items = new JSONObject(statusReponse).getJSONObject("item");
		pickListStatus=(JSONObject) items.get("pickListItems");

	}

	public static void verifyActivePrCurrentOffice(RequestSpecification httpRequest) throws ParseException {


		JSONObject role=addRoles(true,false,getCurrentTimeStamp(true),StaticRuleTestDataPage.sysPrKeyOfficeRole);
		String status = null;
		try {
			status = ValidateAPIPage.getParentKeyForItemId(pickListStatus, "Active", "displayValueLong");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject resultant=triggerValidateAPI(role,status,httpRequest);
		Assert.assertEquals(3, ValidateAPIPage.getAllRolesKey(resultant).size());
		verifyOfficeRoleAddition(resultant,true);
		verifySystemBrightRoleAddition(resultant);
		
		
	}
	
	
	public static void verifyActiveLastCurrentOffice(RequestSpecification httpRequest) throws ParseException {


		JSONObject role=addRoles(false,true,getCurrentTimeStamp(true),StaticRuleTestDataPage.sysPrKeyOfficeRole);
		String status = null;
		try {
			status = ValidateAPIPage.getParentKeyForItemId(pickListStatus, "Active", "displayValueLong");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject resultant=triggerValidateAPI(role,status,httpRequest);
		Assert.assertEquals(3, ValidateAPIPage.getAllRolesKey(resultant).size());
		verifyOfficeRoleAddition(resultant,true);
	
	}
	
	public static void verifyActiveNoPrCurrentnAndLastCurrentOffice(RequestSpecification httpRequest) throws ParseException {


		JSONObject role=addRoles(false,false,getCurrentTimeStamp(true),StaticRuleTestDataPage.sysPrKeyOfficeRole);
		String status = null;
		try {
			status = ValidateAPIPage.getParentKeyForItemId(pickListStatus, "Active", "displayValueLong");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject resultant=triggerValidateAPI(role,status,httpRequest);
		Assert.assertEquals(3, ValidateAPIPage.getAllRolesKey(resultant).size());
		verifyOfficeRoleAddition(resultant,true);
		verifySystemBrightRoleAddition(resultant);
		
	}
	
	
	public static void verifyActivePrCurrentLastCurrentOffice(RequestSpecification httpRequest) throws ParseException {


		JSONObject role=addRoles(true,true,getCurrentTimeStamp(true),StaticRuleTestDataPage.sysPrKeyOfficeRole);
		String status = null;
		try {
			status = ValidateAPIPage.getParentKeyForItemId(pickListStatus, "Active", "displayValueLong");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject resultant=triggerValidateAPI(role,status,httpRequest);
		Assert.assertEquals(2, ValidateAPIPage.getAllRolesKey(resultant).size());
		verifySystemBrightRoleAddition(resultant);
		
	}
	
	
	public static void verifyInActivePrCurrentOffice(RequestSpecification httpRequest) throws ParseException {


		JSONObject role=addRoles(true,false,getCurrentTimeStamp(false),StaticRuleTestDataPage.sysPrKeyOfficeRole);
		String status = null;
		try {
			status = ValidateAPIPage.getParentKeyForItemId(pickListStatus, "Inactive", "displayValueLong");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject resultant=triggerValidateAPI(role,status,httpRequest);
		Assert.assertEquals(3, ValidateAPIPage.getAllRolesKey(resultant).size());
		verifyOfficeRoleAddition(resultant,false);
		verifySystemBrightRoleAddition(resultant);
		
	}
	

	
	

	public static void verifyInActivePrCurrentLastCurrentOffice(RequestSpecification httpRequest) throws ParseException {


		JSONObject role=addRoles(true,true,getCurrentTimeStamp(false),StaticRuleTestDataPage.sysPrKeyOfficeRole);
		String status = null;
		try {
			status = ValidateAPIPage.getParentKeyForItemId(pickListStatus, "Inactive", "displayValueLong");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject resultant=triggerValidateAPI(role,status,httpRequest);
		Assert.assertEquals(2, ValidateAPIPage.getAllRolesKey(resultant).size());
		verifySystemBrightRoleAddition(resultant);
		
	}
	
	
	
	

	public static void verifyInActiveLastCurrentOffice(RequestSpecification httpRequest) throws ParseException {


		JSONObject role=addRoles(false,true,getCurrentTimeStamp(false),StaticRuleTestDataPage.sysPrKeyOfficeRole);
		String status = null;
		try {
			status = ValidateAPIPage.getParentKeyForItemId(pickListStatus, "Inactive", "displayValueLong");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject resultant=triggerValidateAPI(role,status,httpRequest);
		Assert.assertEquals(2, ValidateAPIPage.getAllRolesKey(resultant).size());
		verifySystemBrightRoleAddition(resultant);
		
	}
	
	
	
	
	

	public static void verifyInActiveNoPrCurrentnAndLastCurrentOffice(RequestSpecification httpRequest) throws ParseException {


		JSONObject role=addRoles(false,false,getCurrentTimeStamp(false),StaticRuleTestDataPage.sysPrKeyOfficeRole);
		String status = null;
		try {
			status = ValidateAPIPage.getParentKeyForItemId(pickListStatus, "Inactive", "displayValueLong");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject resultant=triggerValidateAPI(role,status,httpRequest);
		Assert.assertEquals(3, ValidateAPIPage.getAllRolesKey(resultant).size());
		verifyOfficeRoleAddition(resultant,false);
		verifySystemBrightRoleAddition(resultant);
		
	}
	
	
	//System-Bright Roles
	
	public static void verifyActiveNoPrCurrentnAndLastCurrentSystemBright(RequestSpecification httpRequest) throws ParseException {


		JSONObject role=addRoles(false,false,getCurrentTimeStamp(true),StaticRuleTestDataPage.sysPrKeySystemBrightRole);
		String status = null;
		try {
			status = ValidateAPIPage.getParentKeyForItemId(pickListStatus, "Active", "displayValueLong");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject resultant=triggerValidateAPI(role,status,httpRequest);
		Assert.assertEquals(3, ValidateAPIPage.getAllRolesKey(resultant).size());
		verifyOfficeRoleAddition(resultant,true);
		verifySystemBrightRoleAddition(resultant);
		
	}

	

	public static void verifyActivePrCurrentSystemBright(RequestSpecification httpRequest) throws ParseException {


		JSONObject role=addRoles(true,false,getCurrentTimeStamp(true),StaticRuleTestDataPage.sysPrKeySystemBrightRole);
		String status = null;
		try {
			status = ValidateAPIPage.getParentKeyForItemId(pickListStatus, "Active", "displayValueLong");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject resultant=triggerValidateAPI(role,status,httpRequest);
		Assert.assertEquals(3, ValidateAPIPage.getAllRolesKey(resultant).size());
		verifyOfficeRoleAddition(resultant,true);
		verifySystemBrightRoleAddition(resultant);
		
	}

	public static void verifyActivePrCurrentLastCurrentSystemBright(RequestSpecification httpRequest) throws ParseException {


		JSONObject role=addRoles(true,true,getCurrentTimeStamp(true),StaticRuleTestDataPage.sysPrKeySystemBrightRole);
		String status = null;
		try {
			status = ValidateAPIPage.getParentKeyForItemId(pickListStatus, "Active", "displayValueLong");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject resultant=triggerValidateAPI(role,status,httpRequest);
		Assert.assertEquals(2, ValidateAPIPage.getAllRolesKey(resultant).size());
		verifyOfficeRoleAddition(resultant,true);
		
	}
	public static void verifyActiveLastCurrentOfficeSystemBright(RequestSpecification httpRequest) throws ParseException {


		JSONObject role=addRoles(false,true,getCurrentTimeStamp(true),StaticRuleTestDataPage.sysPrKeySystemBrightRole);
		String status = null;
		try {
			status = ValidateAPIPage.getParentKeyForItemId(pickListStatus, "Active", "displayValueLong");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject resultant=triggerValidateAPI(role,status,httpRequest);
		Assert.assertEquals(2, ValidateAPIPage.getAllRolesKey(resultant).size());
		verifyOfficeRoleAddition(resultant,true);
		
	}

	public static void verifyNoRoleActive(RequestSpecification httpRequest) throws ParseException {


		JSONObject newRole=new JSONObject();

		JSONObject lmsobj = new JSONObject( listingDataJson).getJSONObject("lmsObject");
		JSONObject rolesJson = lmsobj.getJSONObject("roles");
		List<String> keysForRemoval = rolesJson.keySet()
				.stream()
				.collect(Collectors.toList());

		keysForRemoval.forEach(rolesJson::remove);
		lmsobj.put("roles", rolesJson);
		
		String status = null;
		try {
			status = ValidateAPIPage.getParentKeyForItemId(pickListStatus, "Active", "displayValueLong");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject resultant=triggerValidateAPI(rolesJson,status,httpRequest);
		Assert.assertEquals(2, ValidateAPIPage.getAllRolesKey(resultant).size());
		verifyOfficeRoleAddition(resultant,true);
		verifySystemBrightRoleAddition(resultant);
		
	}
	
	
	
	
	public static void verifyInActiveNoPrCurrentnAndLastCurrentSystemBright(RequestSpecification httpRequest) throws ParseException {


		JSONObject role=addRoles(false,false,getCurrentTimeStamp(false),StaticRuleTestDataPage.sysPrKeySystemBrightRole);
		String status = null;
		try {
			status = ValidateAPIPage.getParentKeyForItemId(pickListStatus, "Inactive", "displayValueLong");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject resultant=triggerValidateAPI(role,status,httpRequest);
		Assert.assertEquals(3, ValidateAPIPage.getAllRolesKey(resultant).size());
		verifyOfficeRoleAddition(resultant,false);
		verifySystemBrightRoleAddition(resultant);
		
	}



	public static void verifyInActivePrCurrentSystemBright(RequestSpecification httpRequest) throws ParseException {


		JSONObject role=addRoles(true,false,getCurrentTimeStamp(false),StaticRuleTestDataPage.sysPrKeySystemBrightRole);
		String status = null;
		try {
			status = ValidateAPIPage.getParentKeyForItemId(pickListStatus, "Inactive", "displayValueLong");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject resultant=triggerValidateAPI(role,status,httpRequest);
		Assert.assertEquals(3, ValidateAPIPage.getAllRolesKey(resultant).size());
		verifyOfficeRoleAddition(resultant,false);
		verifySystemBrightRoleAddition(resultant);
		
		
	}
	

	

	public static void verifyInActivePrCurrentLastCurrentSystemBright(RequestSpecification httpRequest) throws ParseException {


		JSONObject role=addRoles(true,true,getCurrentTimeStamp(false),StaticRuleTestDataPage.sysPrKeySystemBrightRole);
		String status = null;
		try {
			status = ValidateAPIPage.getParentKeyForItemId(pickListStatus, "Inactive", "displayValueLong");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject resultant=triggerValidateAPI(role,status,httpRequest);
		Assert.assertEquals(3, ValidateAPIPage.getAllRolesKey(resultant).size());
		verifyOfficeRoleAddition(resultant,false);
		
	}
	
	
	
	public static void verifyInActiveLastCurrentSystemBright(RequestSpecification httpRequest) throws ParseException {


		JSONObject role=addRoles(false,true,getCurrentTimeStamp(false),StaticRuleTestDataPage.sysPrKeySystemBrightRole);
		String status = null;
		try {
			status = ValidateAPIPage.getParentKeyForItemId(pickListStatus, "Inactive", "displayValueLong");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject resultant=triggerValidateAPI(role,status,httpRequest);
		Assert.assertEquals(2, ValidateAPIPage.getAllRolesKey(resultant).size());
		verifyOfficeRoleAddition(resultant,false);
		
		
	}
	

	
	public static void verifyNoRoleInActive(RequestSpecification httpRequest) throws ParseException {


		

		JSONObject lmsobj = new JSONObject( listingDataJson).getJSONObject("lmsObject");
		JSONObject rolesJson = lmsobj.getJSONObject("roles");
		List<String> keysForRemoval = rolesJson.keySet()
				.stream()
				.collect(Collectors.toList());

		keysForRemoval.forEach(rolesJson::remove);
		lmsobj.put("roles", rolesJson);
		
		String status = null;
		try {
			status = ValidateAPIPage.getParentKeyForItemId(pickListStatus, "InActive", "displayValueLong");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject resultant=triggerValidateAPI(rolesJson,status,httpRequest);
		
		Assert.assertEquals(2, ValidateAPIPage.getAllRolesKey(resultant).size());
		verifyOfficeRoleAddition(resultant,false);
		verifySystemBrightRoleAddition(resultant);
		
	}

	public static void verifyFlag(JSONObject resultant,boolean expectedSysPrCurrent) throws ParseException {
		
		JSONObject rolesJson = resultant.getJSONObject("roles");
		JSONObject rolesObject=ValidateAPIPage.getRoleJsonUsingAnyKey(rolesJson,StaticRuleTestDataPage.roleBrightKey,"sysPrKey");
		
		boolean sysPrCurrent=rolesObject.getBoolean("sysPrCurrent");
		Assert.assertEquals(sysPrCurrent, expectedSysPrCurrent);
		
	}
	
	public static void verifyOfficeRoleAddition(JSONObject resultantObj,boolean currentFlag) throws ParseException {

		JSONObject rolesJson = resultantObj.getJSONObject("roles");
		
		String roleId=ValidateAPIPage.getParentKeyForItemId(rolesJson,"Office","sysPrRoleName");
		if(roleId!=null) {
			JSONObject addedRole=ValidateAPIPage.getRoleJsonUsingAnyKey(rolesJson,"Office","sysPrRoleName");
			
			String databaseName=addedRole.getString("sourceDatabaseName");
			
		Assert.assertEquals(databaseName,"bright/office","databaseName not matched");
			String hostname=addedRole.getString("sourceHostName");
			
			String expectedHostname=String.format("au%s%sz1s3documentstoreinfra-docstore", region, CommonUtilityMethods.getEnvironment());
			Assert.assertEquals(hostname, expectedHostname);
			
			boolean sysPrCurrent=addedRole.getBoolean("sysPrCurrent");
			Assert.assertEquals(sysPrCurrent, currentFlag);
			boolean sysPrLastCurrent=addedRole.getBoolean("sysPrLastCurrent");
			Assert.assertEquals(sysPrLastCurrent, true);
		}
		else
		{
			Assert.fail("Office Role not added successfully");
		}
		

	}
	
	
	public static void verifySystemBrightRoleAddition(JSONObject resultantObj) throws ParseException {

		JSONObject rolesJson = resultantObj.getJSONObject("roles");
		
		String roleId=ValidateAPIPage.getParentKeyForItemId(rolesJson,"System-BRIGHT","sysPrRoleName");
		if(roleId!=null) {
		JSONObject addedRole=ValidateAPIPage.getRoleJsonUsingAnyKey(rolesJson,"System-BRIGHT","sysPrRoleName");
		
		String startDate=addedRole.getString("sysPrStartDate");
		if(startDate==null) {
			Assert.fail("Sart date is not added");
		}
		
		String databaseName=addedRole.getString("sourceDatabaseName");
		
		Assert.assertEquals(databaseName,"bright/office","databaseName not matched");
		String hostname=addedRole.getString("sourceHostName");
		
		String expectedHostname=String.format("au%s%sz1s3documentstoreinfra-docstore", region, CommonUtilityMethods.getEnvironment());
		Assert.assertEquals(hostname, expectedHostname);
		boolean sysPrCurrent=addedRole.getBoolean("sysPrCurrent");
		Assert.assertEquals(sysPrCurrent, true);
		boolean sysPrLastCurrent=addedRole.getBoolean("sysPrLastCurrent");
		Assert.assertEquals(sysPrLastCurrent, true);
		
		}
		
		else
		{
			Assert.fail("System-Bright Role not added successfully");
		}
	}

	@SuppressWarnings("null")
	public static JSONObject addRoles(boolean sysPrCurrent,boolean sysPrLastCurrent, String timestamp,String roleKey){

		JSONObject newRole=new JSONObject();

		JSONObject lmsobj = new JSONObject( listingDataJson).getJSONObject("lmsObject");
		JSONObject rolesJson = lmsobj.getJSONObject("roles");
		List<String> keysForRemoval = rolesJson.keySet()
				.stream()
				.collect(Collectors.toList());

		keysForRemoval.forEach(rolesJson::remove);
		lmsobj.put("roles", rolesJson);

		JSONObject roleValue=new JSONObject(StaticRuleTestDataPage.roleBrightValue);
		roleValue.put("sysPrCurrent", sysPrCurrent);
		roleValue.put("sysPrLastCurrent", sysPrLastCurrent);
		roleValue.put("sysPrStartDate", timestamp);
		roleValue.put("sysPrRoleKey", new BigInteger(roleKey));
		newRole.put(StaticRuleTestDataPage.roleBrightKey, roleValue);
		return newRole;

	}

	public static JSONObject triggerValidateAPI(JSONObject roles,String oficeStatus,RequestSpecification httpRequest ) {

		JSONObject content=null;
		JSONObject finalObj=new JSONObject( listingDataJson);
		JSONObject lmsobj = new JSONObject( listingDataJson).getJSONObject("lmsObject");
		lmsobj.put("roles", roles);
		long status=Long.parseLong(oficeStatus);
		lmsobj.put("officeStatus", status);
		JSONObject responseBodyObj = new JSONObject();
		finalObj.put("lmsObject", lmsobj);
		responseBodyObj.put("content", finalObj);
		String payload = responseBodyObj.toString();
		String Json=ValidateAPIPage.postValidateAPI("bright", "office", payload,httpRequest);
		JSONObject resultant = new JSONObject( Json).getJSONObject("resultantLmsObject");

		return resultant;

	}
	
	public static JSONObject triggerValidateAPIForByPassingRules(JSONObject roles,String oficeStatus,RequestSpecification httpRequest ) {

		JSONObject content=null;
		JSONObject finalObj=new JSONObject( listingDataJson);
		JSONObject lmsobj = new JSONObject( listingDataJson).getJSONObject("lmsObject");
		lmsobj.put("roles", roles);
		long status=Long.parseLong(oficeStatus);
		lmsobj.put("officeStatus", status);
		JSONObject responseBodyObj = new JSONObject();
		finalObj.put("lmsObject", lmsobj);
		responseBodyObj.put("content", finalObj);
		String payload = responseBodyObj.toString();
		String Json=ValidateAPIPage.postValidateAPIForByPassingRules("bright", "office", payload,httpRequest);
		JSONObject resultant = new JSONObject( Json).getJSONObject("resultantLmsObject");

		return resultant;

	}
	
	
	public static void checkPhoneNumberOther(RequestSpecification httpRequest ) {

		JSONObject content=null;
		JSONObject finalObj=new JSONObject( listingDataJson);
		JSONObject lmsobj = new JSONObject( listingDataJson).getJSONObject("lmsObject");
		
		
		lmsobj.put("officePhoneOther", StaticRuleTestDataPage.officePhoneOther);
		JSONObject responseBodyObj = new JSONObject();
		finalObj.put("lmsObject", lmsobj);
		responseBodyObj.put("content", finalObj);
		String payload = responseBodyObj.toString();
		String Json=ValidateAPIPage.postValidateAPI("bright", "office", payload,httpRequest);
		JSONObject resultant = new JSONObject( Json).getJSONObject("resultantLmsObject");
		String actualPhoneNo=resultant.getString("officePhoneOther");
		if(actualPhoneNo.contains("-")) {
			Assert.fail("Phone Nuber check failed ."+"Response is:"+actualPhoneNo);
		}
		

	}
	public static String getCurrentTimeStamp(boolean flag) {
		String str;
		if(flag) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			str = LocalDateTime.now().format(formatter);
		}
		
		else {
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			str = LocalDateTime.now().format(formatter);
		}
		

		return str;

	}

}
