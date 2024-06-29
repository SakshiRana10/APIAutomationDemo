package bright.api.alaya.test.objectLayer;

import org.json.JSONException;
import org.testng.annotations.Test;

import bright.api.alaya.pages.objectLayer.OfficeSysPartyRoleUpdateRulePage;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.MainClassAlaya;
import io.restassured.specification.RequestSpecification;

public class OfficeSysPartyRoleUpdateRulesTest extends MainClassAlaya {
	
	/*T1 - "sysPrRoleKey": 10041844141,
	 *     "sysPrPartyKey": 650083587540,
	 *     "sysPrParentPartyKey": 650098737232
	 *D1 - "sysPrRoleKey": 10041844141,
	 *     "sysPrPartyKey": 550032512408,
	 *     "sysPrParentPartyKey": 550032512424*/
	
	/*Total cases - 8 
	 *1. Validating circulare dependency
	 *2. Validating circular dependency if non mandatory field is gone
	 *3. Validating no circular dependency if RoleKey,PartyKey,ParentPartyKey is wrong
	 *4. Validating no circular dependency if RoleKey,PartyKey,ParentPartyKey is Gone*/
	
	public static String sysRoleKey = "10041844141";
	public static String environment = CommonUtilityMethods.getEnvironment();
	
	@Test ( groups = {"dev"}, priority = 0, enabled = true, description ="Verify circular dependency of 'Branch Office' roles") 
	public void verifyCircularDependencyOnRoles() throws JSONException, InterruptedException{
		RequestSpecification httpRequest= null;
		if(environment.equalsIgnoreCase("t1"))
		    OfficeSysPartyRoleUpdateRulePage.VerifyCircularDependency(httpRequest,sysRoleKey,"650083587540","650098737232","NotGone");
		else if(environment.equalsIgnoreCase("d1"))
		    OfficeSysPartyRoleUpdateRulePage.VerifyCircularDependency(httpRequest,sysRoleKey,"550032512408","550032512424","NotGone");
			
	}
	
	@Test ( groups = {"test","dev"}, priority = 0, enabled = true, description ="Verify no circular dependency of 'Branch Office' roles for wrong role key") 
	public void verifyNoCircularDependencyForWrongRoleKey()  throws JSONException, InterruptedException{
		RequestSpecification httpRequest= null;
		if(environment.equalsIgnoreCase("t1"))
		    OfficeSysPartyRoleUpdateRulePage.VerifyNoCircularDependency(httpRequest,"111","650083587540","650098737232","NotGone");
		else if(environment.equalsIgnoreCase("d1"))
			OfficeSysPartyRoleUpdateRulePage.VerifyNoCircularDependency(httpRequest,"111","550032512408","550032512424","NotGone");
	}
	
	@Test ( groups = {"test","dev"}, priority = 0, enabled = true, description ="Verify no circular dependency of 'Branch Office' roles for wrong party key") 
	public void verifyNoCircularDependencyForWrongPartyKey() throws JSONException, InterruptedException{
		RequestSpecification httpRequest= null;
		if(environment.equalsIgnoreCase("t1"))
			OfficeSysPartyRoleUpdateRulePage.VerifyNoCircularDependency(httpRequest,sysRoleKey,"111","650098737232","NotGone");
		else if(environment.equalsIgnoreCase("d1"))
			OfficeSysPartyRoleUpdateRulePage.VerifyNoCircularDependency(httpRequest,sysRoleKey,"111","550032512424","NotGone");
	}
	
	@Test ( groups = {"test","dev"}, priority = 0, enabled = true, description ="Verify no circular dependency of 'Branch Office' roles for wrong parent party key") 
	public void verifyNoCircularDependencyForWrongParentPartyKey()  throws JSONException, InterruptedException{
		RequestSpecification httpRequest= null;
		if(environment.equalsIgnoreCase("t1"))
			OfficeSysPartyRoleUpdateRulePage.VerifyNoCircularDependency(httpRequest,sysRoleKey,"650083587540","111","NotGone");
		else if(environment.equalsIgnoreCase("d1"))
			OfficeSysPartyRoleUpdateRulePage.VerifyNoCircularDependency(httpRequest,sysRoleKey,"550032512408","111","NotGone");
	}
	
	@Test ( groups = {"test","dev"}, priority = 0, enabled = true, description ="Verify no circular dependency of 'Branch Office' roles for gone Role key") 
	public void verifyNoCircularDependencyForGoneRoleKey() throws JSONException, InterruptedException{
		RequestSpecification httpRequest= null;
		if(environment.equalsIgnoreCase("t1"))
			OfficeSysPartyRoleUpdateRulePage.VerifyNoCircularDependencyForGoneKeys(httpRequest,"sysPrPartyKey","sysPrParentPartyKey","650083587540","650098737232");
		else if(environment.equalsIgnoreCase("d1"))
			OfficeSysPartyRoleUpdateRulePage.VerifyNoCircularDependencyForGoneKeys(httpRequest,"sysPrPartyKey","sysPrParentPartyKey","550032512408","550032512424");
	}
	
	@Test ( groups = {"test","dev"}, priority = 0, enabled = true, description ="Verify no circular dependency of 'Branch Office' roles for gone party key") 
	public void verifyNoCircularDependencyForGonePartyKey() throws JSONException, InterruptedException{
		RequestSpecification httpRequest= null;
		if(environment.equalsIgnoreCase("t1"))
			OfficeSysPartyRoleUpdateRulePage.VerifyNoCircularDependencyForGoneKeys(httpRequest,"sysPrRoleKey","sysPrParentPartyKey",sysRoleKey,"650098737232");
		else if(environment.equalsIgnoreCase("d1"))
			OfficeSysPartyRoleUpdateRulePage.VerifyNoCircularDependencyForGoneKeys(httpRequest,"sysPrRoleKey","sysPrParentPartyKey",sysRoleKey,"550032512424");
	}
	
	@Test ( groups = {"test","dev"}, priority = 0, enabled = true, description ="Verify no circular dependency of 'Branch Office' roles for gone parent party key") 
	public void verifyNoCircularDependencyForGoneParentPartyKey()  throws JSONException, InterruptedException{
		RequestSpecification httpRequest= null;
		if(environment.equalsIgnoreCase("t1"))
			OfficeSysPartyRoleUpdateRulePage.VerifyNoCircularDependencyForGoneKeys(httpRequest,"sysPrRoleKey","sysPrPartyKey",sysRoleKey,"650083587540");
		else if(environment.equalsIgnoreCase("d1"))
			OfficeSysPartyRoleUpdateRulePage.VerifyNoCircularDependencyForGoneKeys(httpRequest,"sysPrRoleKey","sysPrPartyKey",sysRoleKey,"550032512408");
	}
	
	@Test ( groups = {"dev"}, priority = 0, enabled = true, description ="Verify circular dependency of 'Branch Office' roles for gone non mandatory key") 
	public void verifyCircularDependencyForGoneNonMandatKey() throws JSONException, InterruptedException{
		RequestSpecification httpRequest= null;
		if(environment.equalsIgnoreCase("t1"))
			OfficeSysPartyRoleUpdateRulePage.VerifyCircularDependency(httpRequest,sysRoleKey,"650083587540","650098737232","Gone");
		else if(environment.equalsIgnoreCase("d1"))
			OfficeSysPartyRoleUpdateRulePage.VerifyCircularDependency(httpRequest,sysRoleKey,"550032512408","550032512424","Gone");
	}
	
	

}
