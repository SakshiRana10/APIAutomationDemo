package bright.api.alaya.pages.filterPolicyPage;

import org.testng.Assert;

import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.EnvSpecificMethods;
import bright.api.alaya.utils.MainClassAlaya;

public class VerifyBuilderFilterPolicyPage extends MainClassAlaya{
	private static final String SYSTEM_LOCALE = "bright" ;
	
	
	public static void verifyOfficeBuilderFilterPolicy() {

		String topicArn = EnvSpecificMethods.indexBuilderSNS();
		Assert.assertTrue( CommonUtilityMethods.verifyBuilderFilterPolicyforLocale(topicArn ,"office" ).contains(SYSTEM_LOCALE),"Locale Not found in Builder policy");
		Assert.assertTrue( CommonUtilityMethods.verifyBuilderFilterPolicyforDocType(topicArn ,"office" ).contains("office"),"doc type Not found in Builder policy");
		logger.info("Verified the filter policy for Index Builder Office");
	}


	public static void verifyListingBuilderFilterPolicy() {

		String topicArn = EnvSpecificMethods.indexBuilderSNS();
		Assert.assertTrue( CommonUtilityMethods.verifyBuilderFilterPolicyforLocale(topicArn ,"listing" ).contains(SYSTEM_LOCALE),"Locale Not found in Builder policy");
		Assert.assertTrue( CommonUtilityMethods.verifyBuilderFilterPolicyforDocType(topicArn ,"listing" ).contains("listing"),"doc type Not found in Builder policy");
		logger.info("Verified the filter policy for Index Builder Listing");
	}


	public static void verifyMemberBuilderFilterPolicy() {

		String topicArn = EnvSpecificMethods.indexBuilderSNS();
		Assert.assertTrue( CommonUtilityMethods.verifyBuilderFilterPolicyforLocale(topicArn ,"member" ).contains(SYSTEM_LOCALE),"Locale Not found in Builder policy");
		Assert.assertTrue( CommonUtilityMethods.verifyBuilderFilterPolicyforDocType(topicArn ,"member" ).contains("member"),"doc type Not found in Builder policy");
		logger.info("Verified the filter policy for Index Builder Member");
	}

	public static void verifyTaxRecordBuilderFilterPolicy() {

		String topicArn = EnvSpecificMethods.indexBuilderSNS();
		Assert.assertTrue( CommonUtilityMethods.verifyBuilderFilterPolicyforLocale(topicArn ,"taxrecord" ).contains(SYSTEM_LOCALE),"Locale Not found in Builder policy");
		Assert.assertTrue( CommonUtilityMethods.verifyBuilderFilterPolicyforDocType(topicArn ,"taxrecord" ).contains("taxrecord"),"doc type Not found in Builder policy");
		
		logger.info("Verified the filter policy for Index Builder Taxrecord");
	}


	public static void verifyCountyrateBuilderFilterPolicy() {

		String topicArn = EnvSpecificMethods.indexBuilderSNS();
		Assert.assertTrue( CommonUtilityMethods.verifyBuilderFilterPolicyforLocale(topicArn ,"countyrate" ).contains(SYSTEM_LOCALE),"Locale Not found in Builder policy");
		Assert.assertTrue( CommonUtilityMethods.verifyBuilderFilterPolicyforDocType(topicArn ,"countyrate" ).contains("countyrate"),"doc type Not found in Builder policy");
		logger.info("Verified the filter policy for Index Builder Countyrate");
	}
	
	public static void verifyCityBuilderFilterPolicy() {

		String topicArn = EnvSpecificMethods.indexBuilderSNS();
		Assert.assertTrue( CommonUtilityMethods.verifyBuilderFilterPolicyforLocale(topicArn ,"city" ).contains(SYSTEM_LOCALE),"Locale Not found in Builder policy");
		Assert.assertTrue( CommonUtilityMethods.verifyBuilderFilterPolicyforDocType(topicArn ,"city" ).contains("city"),"doc type Not found in Builder policy");
		logger.info("Verified the filter policy for Index Builder City");
	}


	public static void verifySubdivisionBuilderFilterPolicy() {
		String topicArn = EnvSpecificMethods.indexBuilderSNS();
		Assert.assertTrue( CommonUtilityMethods.verifyBuilderFilterPolicyforLocale(topicArn ,"subdivision" ).contains(SYSTEM_LOCALE),"Locale Not found in Builder policy");
		Assert.assertTrue( CommonUtilityMethods.verifyBuilderFilterPolicyforDocType(topicArn ,"subdivision" ).contains("subdivision"),"doc type Not found in Builder policy");
		
		logger.info("Verified the filter policy for Index Builder Subdivison");
		
	}


	public static void verifyBuilder_modelBuilderFilterPolicy() {
		String topicArn = EnvSpecificMethods.indexBuilderSNS();
		Assert.assertTrue( CommonUtilityMethods.verifyBuilderFilterPolicyforLocale(topicArn ,"builder_model" ).contains(SYSTEM_LOCALE),"Locale Not found in Builder policy");
		Assert.assertTrue( CommonUtilityMethods.verifyBuilderFilterPolicyforDocType(topicArn ,"builder_model" ).contains("builder_model"),"doc type Not found in Builder policy");
		
		logger.info("Verified the filter policy for Index Builder builder model");
		
	}


	public static void verifyBuilding_NameBuilderFilterPolicy() {
		String topicArn = EnvSpecificMethods.indexBuilderSNS();
		Assert.assertTrue( CommonUtilityMethods.verifyBuilderFilterPolicyforLocale(topicArn ,"building_name" ).contains(SYSTEM_LOCALE),"Locale Not found in Builder policy");
		Assert.assertTrue( CommonUtilityMethods.verifyBuilderFilterPolicyforDocType(topicArn ,"building_name" ).contains("building_name"),"doc type Not found in Builder policy");
		logger.info("Verified the filter policy for Index Builder building name");
		
	}
}
