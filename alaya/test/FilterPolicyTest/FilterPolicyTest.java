package bright.api.alaya.test.FilterPolicyTest;

import org.testng.Assert;
import org.testng.annotations.Test;

import bright.api.alaya.pages.filterPolicyPage.VerifyBuilderFilterPolicyPage;
import bright.api.alaya.pages.filterPolicyPage.VerifyInitializerFilterPolicyPage;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.EnvSpecificMethods;
import bright.api.alaya.utils.MainClassAlaya;

public class FilterPolicyTest extends MainClassAlaya{




	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verify filter policy")
	public static void verifyTaxRecordInitializerFilterPolicy() {

		VerifyInitializerFilterPolicyPage.verifyTaxRecordInitializerFilterPolicy();
	}

	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verify filter policy")
	public  void verifyListingBuilderFilterPolicy() {

		VerifyBuilderFilterPolicyPage.verifyListingBuilderFilterPolicy();
	}


	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verify filter policy")

	public void verifyOfficeBuilderFilterPolicy() {

		VerifyBuilderFilterPolicyPage.verifyOfficeBuilderFilterPolicy();
	}


	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verify filter policy")
	public static void verifyMemberBuilderFilterPolicy() {

		VerifyBuilderFilterPolicyPage.verifyMemberBuilderFilterPolicy();
	}

	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verify filter policy")
	public static void verifyTaxRecordBuilderFilterPolicy() {

		VerifyBuilderFilterPolicyPage.verifyTaxRecordBuilderFilterPolicy();
	}

	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verify filter policy")
	public static void verifyCountyrateBuilderFilterPolicy() {

		VerifyBuilderFilterPolicyPage.verifyCountyrateBuilderFilterPolicy();
	}
	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verify filter policy")
	public static void verifyCityBuilderFilterPolicy() {

		VerifyBuilderFilterPolicyPage.verifyCityBuilderFilterPolicy();
	}


	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verify filter policy")
	public static void verifySubdivisionBuilderFilterPolicy() {

		VerifyBuilderFilterPolicyPage.verifySubdivisionBuilderFilterPolicy();
	}

	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verify filter policy")
	public static void verifyBuilder_modelBuilderFilterPolicy() {

		VerifyBuilderFilterPolicyPage.verifyBuilder_modelBuilderFilterPolicy();
	}

	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verify filter policy")
	public static void verifyBuilding_NameBuilderFilterPolicy() {

		VerifyBuilderFilterPolicyPage.verifyBuilding_NameBuilderFilterPolicy();
	}



	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verify filter policy")
	public static void verifyOfficeInitializerFilterPolicy() {

		VerifyInitializerFilterPolicyPage.verifyOfficeInitializerFilterPolicy();
	}

	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verify filter policy")
	public static void verifyListingInitializerFilterPolicy() {

		VerifyInitializerFilterPolicyPage.verifyListingInitializerFilterPolicy();
	}

	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verify filter policy")
	public static void verifyMemberInitializerFilterPolicy() {

		VerifyInitializerFilterPolicyPage.verifyMemberInitializerFilterPolicy();
	}


	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verify filter policy")
	public static void verifyCountyrateInitializerFilterPolicy() {

		VerifyInitializerFilterPolicyPage.verifyCountyrateInitializerFilterPolicy();
	}

	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verify filter policy")
	public static void verifyBuildingNameInitializerFilterPolicy() {

		VerifyInitializerFilterPolicyPage.verifyBuildingNameInitializerFilterPolicy();
	}


	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verify filter policy")
	public static void verifyBuilderModelInitializerFilterPolicy() {

		VerifyInitializerFilterPolicyPage.verifyBuilderModelInitializerFilterPolicy();
	}

	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verify filter policy")
	public static void verifySubdivisionInitializerFilterPolicy() {

		VerifyInitializerFilterPolicyPage.verifySubdivisionInitializerFilterPolicy();
	}

	@Test ( groups = {"test", "dev","prod"}, priority = 1, enabled = true, description = "Verify filter policy")
	public static void verifyCityInitializerFilterPolicy() {

		VerifyInitializerFilterPolicyPage.verifyCityInitializerFilterPolicy();
	}
}
