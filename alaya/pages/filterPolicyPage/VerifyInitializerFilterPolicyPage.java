package bright.api.alaya.pages.filterPolicyPage;

import org.testng.Assert;

import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.EnvSpecificMethods;
import bright.api.alaya.utils.MainClassAlaya;

public class VerifyInitializerFilterPolicyPage extends MainClassAlaya{


	public static void verifyOfficeInitializerFilterPolicy() {

		String topicArn = EnvSpecificMethods.dsInitializerSNS();
		Assert.assertEquals(CommonUtilityMethods.verifyInitializerFilterPolicy(topicArn ,"office"), true,"Filter policy did not match");
		logger.info("Verified the filter policy for post call of  DS Initializer Office");
	}


	public static void verifyListingInitializerFilterPolicy() {

		String topicArn = EnvSpecificMethods.dsInitializerSNS();
		Assert.assertEquals(CommonUtilityMethods.verifyInitializerFilterPolicy(topicArn ,"listing"), true,"Filter policy did not match");
		logger.info("Verified the filter policy for post call of  DS Initializer Listing");
	}


	public static void verifyMemberInitializerFilterPolicy() {

		String topicArn = EnvSpecificMethods.dsInitializerSNS();
		Assert.assertEquals(CommonUtilityMethods.verifyInitializerFilterPolicy(topicArn ,"member"), true,"Filter policy did not match");
		logger.info("Verified the filter policy for post call of  DS Initializer Member");
	}

	public static void verifyTaxRecordInitializerFilterPolicy() {

		String topicArn = EnvSpecificMethods.dsInitializerSNS();
		Assert.assertEquals(CommonUtilityMethods.verifyInitializerFilterPolicy(topicArn ,"taxrecord"), true,"Filter policy did not match");
		logger.info("Verified the filter policy for post call of  DS Initializer Taxrecord");
	}


	public static void verifyCountyrateInitializerFilterPolicy() {

		String topicArn = EnvSpecificMethods.dsInitializerSNS();
		Assert.assertEquals(CommonUtilityMethods.verifyInitializerFilterPolicy(topicArn ,"countyrate"), true,"Filter policy did not match");
		logger.info("Verified the filter policy for post call of  DS Initializer Countyrate");
	}
	public static void verifyBuildingNameInitializerFilterPolicy() {

		String topicArn = EnvSpecificMethods.dsInitializerSNS();
		Assert.assertEquals(CommonUtilityMethods.verifyInitializerFilterPolicy(topicArn ,"building_name"), true,"Filter policy did not match");
		logger.info("Verified the filter policy for post call of  DS Initializer Countyrate");
	}
	
	
	public static void verifyBuilderModelInitializerFilterPolicy() {

		String topicArn = EnvSpecificMethods.dsInitializerSNS();
		Assert.assertEquals(CommonUtilityMethods.verifyInitializerFilterPolicy(topicArn ,"builder_model"), true,"Filter policy did not match");
		logger.info("Verified the filter policy for post call of  DS Initializer Countyrate");
	}
	
	public static void verifySubdivisionInitializerFilterPolicy() {

		String topicArn = EnvSpecificMethods.dsInitializerSNS();
		Assert.assertEquals(CommonUtilityMethods.verifyInitializerFilterPolicy(topicArn ,"subdivision"), true,"Filter policy did not match");
		logger.info("Verified the filter policy for post call of  DS Initializer Countyrate");
	}
	public static void verifyCityInitializerFilterPolicy() {

		String topicArn = EnvSpecificMethods.dsInitializerSNS();
		Assert.assertEquals(CommonUtilityMethods.verifyInitializerFilterPolicy(topicArn ,"city"), true,"Filter policy did not match");
		logger.info("Verified the filter policy for post call of  DS Initializer Countyrate");
	}
}


