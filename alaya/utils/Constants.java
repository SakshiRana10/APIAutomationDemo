package bright.api.alaya.utils;

public class Constants {


	public static final String gitUrlArtifacts = "https://github.com/BrightMLS/Alaya.ConfigArtifacts.git";

	public static final String gitUrlConfig = "https://github.com/BrightMLS/Alaya.Config.git";

	public static final String gitLocalDirectoryArtifacts= System.getProperty("user.dir")+"/resources/testdata/artifacts";

	public static final String gitLocalDirectoryConfig= System.getProperty("user.dir")+"/resources/testdata/config";

	public static final String lookUpMapping = System.getProperty("user.dir")+"/resources/testdata/LookUp.xlsx";

	public static final String resourceMapping = System.getProperty("user.dir")+"/resources/testdata/Resource.xlsx";

	public static final String businessViewDirectoryBright = System.getProperty("user.dir")+"/resources/testdata/artifacts/bright/businessViews";

	public static final String pickListDirectoryBright = System.getProperty("user.dir")+"/resources/testdata/artifacts/bright/picklistItems";

	public static final String businessViewDirectoryWirex = System.getProperty("user.dir")+"/resources/testdata/artifacts/wirex/businessViews";

	public static final String pickListDirectoryWirex = System.getProperty("user.dir")+"/resources/testdata/artifacts/wirex/picklistItems";

	public static final String testMetaDataBright = System.getProperty("user.dir")+"/resources/testdata/BRIGHT_Metadata_Test.json";

	public static final String testMetaDataWirex = System.getProperty("user.dir")+"/resources/testdata/WirexMetaData.json";

	public static final String devMetaDataBright = System.getProperty("user.dir")+"/resources/testdata/BrightMetaDataDev.json";

	public static final String devtMetaDataWirex = System.getProperty("user.dir")+"/resources/testdata/WirexMetaData.json";

	public static final String prodMetaDataBright = System.getProperty("user.dir")+"/resources/testdata/BrightMetaData.json";

	public static final String prodMetaDataWirex = System.getProperty("user.dir")+"/resources/testdata/WirexMetaData.json";


	public static final String configPath = System.getProperty("user.dir")+"/resources/config.properties";

	public static final String gitBranchDev = FileUtility.getValueFromConfig("GitDevBranch");

	public static final String gitBranchTest = FileUtility.getValueFromConfig("GitTestBranch");

	public static final String reportPath = System.getProperty("user.dir")+"/ReportGenerated/AutomationReport.html";

	public static final String apiEndpointsJsonPath = System.getProperty("user.dir")+"/resources/testdata/apiEndpoints.json";

	public static final String dsInitializerPayloadJsonPath = System.getProperty("user.dir")+"/resources/testdata/dsInitializerPayloads.json";

	public static final String outputValuesPath=System.getProperty("user.dir")+"/resources/output-generated/outputValues.json";

	public static final String discoverylayerPath ="/resources/testdata/discoverylayer.json";
	
	public static final String objectlayer ="/resources/testdata/objectLayer.json";
	
	
	public static final String objectlayerPatch ="/resources/testdata/objectPatchPayload.json";

	public static final String decodedDocumentsPath = System.getProperty("user.dir")+"/resources/output-generated/decoded-documents";

	public static final String configBusinessView = System.getProperty("user.dir")+"/resources/testdata/config/config/businessViews";

	public static final String congRulesBusinessView = System.getProperty("user.dir")+"/resources/testdata/config/config/ruleUsages";
	
	public static final String BrightLocale="200003943665";
	
	public static final String WirexLocale="50000969238";

	public static final String SlackIntegration="/resources/testdata/slackIntegration.json";
	
	public static final String Project="Core-Alaya";
	
	public static final String testEnvironment="Test";
	
	public static final String prodEnvironment="Prod";
	
	public static final String devEnvironment="Dev";
	
	public static final String oneAdminSelectQuery="lovId,ruId,laId,logicalAttribute/name,name,ruleUsageArguments/logicalColumn/name,description,executeOnClientFlag,executeOnServerFlag,executeOnValidateFlag,executeOnlyIfChangedFlag,validatedFlag,deletedFlag,executionStart,executionEnd,executionInterval,itemNumber,etId,retId,rutId,ruleId,rule/name,rule/clId,rule/conditionalCallName,rule/callName,ruleUsageType/name,ruleUsageArguments/ruaId,ruleUsageArguments/charValue,ruleUsageArguments/dateValue,ruleUsageArguments/numValue,ruleUsageArguments/currentValueFlag,ruleUsageArguments/raId,ruleUsageArguments/laId,ruleUsageArguments/lcId,ruleUsageArguments/ruleArgument/name,ruleUsageArguments/ruleArgument/description,ruleUsageArguments/ruleArgument/itemNumber,ruleUsageArguments/ruleArgument/dataType/dataType,ruleUsageArguments/ruleArgument/dataType/javaDataType,ruleUsageArguments/ruleArgument/dataType/name,ruleUsageFlags/rufId,ruleUsageFlags/rfId,ruleUsageFlags/errorMessage,ruleUsageFlags/setValidationFlag";

	public static final String LOVMapping=System.getProperty("user.dir")+"/resources/testdata/LcLaMapping/";
	
	public static final String WirexRuleConfigMapping=System.getProperty("user.dir")+"/resources/testdata/WirexOneadmin/WirexRuleConfig.json";

	public static final String BrightRuleConfigMapping=System.getProperty("user.dir")+"/resources/testdata/BrightOneadmin/BrightRuleConfig.json";

	public static final String WirexFieldIDMapping=System.getProperty("user.dir")+"/resources/testdata/WirexOneadmin/Wirex.json";

	public static final String BrightFieldIdMapping=System.getProperty("user.dir")+"/resources/testdata/BrightOneadmin/Bright.json";

	public static final String WirexRulesFlagData=System.getProperty("user.dir")+"/resources/testdata/WirexOneadmin/RulesFlag/";

	public static final String BrightRulesFlagData=System.getProperty("user.dir")+"/resources/testdata/BrightOneadmin/RulesFlag/";

	public static final String WirexRulesMainData=System.getProperty("user.dir")+"/resources/testdata/WirexOneadmin/RulesData/";

	public static final String BrightRulesMainData=System.getProperty("user.dir")+"/resources/testdata/BrightOneadmin/RulesData/";
	
	public static final String BrightRulesTestDataPath=System.getProperty("user.dir")+"/resources/testdata/BrightOneadmin";
	
	public static final String WirexRulesTestDataPath=System.getProperty("user.dir")+"/resources/testdata/WirexOneadmin";
	

	public static final String laSearchPath ="/resources/testdata/laSearchPayloads.json";
	
	public static final String laSearchAttributesPath ="/resources/testdata/laSearchAttributes.json";
	
	public static final String retsPath ="/resources/testdata/rets.json";
	
	public static final String RetsMetaData = System.getProperty("user.dir")+"/resources/testdata/Rets_MetaData_";

	public static final String RetsMetaDataClass = System.getProperty("user.dir")+"/resources/testdata/Rets_MetaData_Class_";
	
	public static final String RetsMetaDataLookup = System.getProperty("user.dir")+"/resources/testdata/Rets_MetaData_Lookup_";
	
	public static final String RetsMetaDataSearchHelp = System.getProperty("user.dir")+"/resources/testdata/Rets_MetaData_SearchHelp_";
	
	public static final String RetsMetaDataTable = System.getProperty("user.dir")+"/resources/testdata/Rets_MetaData_Table_";
	
	public static final String SourceBusinessPartnerNONBright = "200004293311";
	public static final String SourceBusinessPartnerBright = "200004300675";

	public static final String graphqlAttributes ="/resources/testdata/graphQlAttributes.json";

	public static final String parameterStore = System.getProperty("user.dir")+"/resources/testdata/paramStoreFile.json";
	
	public static final String configAPI = "/resources/testdata/configAPI.json";
	
	public static final String auditTrailPath ="/resources/testdata/auditTrail.json";

}
