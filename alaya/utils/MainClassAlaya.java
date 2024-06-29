/** This is the MainClass and is being inherited in all the logic pages. */
package bright.api.alaya.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import bright.api.alaya.pages.configServices.CreateThreadLogicalAttributeMappingPage;
import bright.api.alaya.pages.objectLayer.SessionManagementPage;
import bright.api.alaya.pages.configServices.CreateRetsThreadPage;

import io.restassured.specification.RequestSpecification;
import software.amazon.awssdk.services.ssm.SsmClient;

public class MainClassAlaya {
	protected static RequestSpecification requestSpecification;
	protected static Properties property;
	protected static String workingDir = System.getProperty("user.dir");
	public static Logger logger = Logger.getLogger(MainClassAlaya.class);
	private FileInputStream input;
	protected static JSONObject data;
	public static JSONObject json = new JSONObject();
	protected static ArrayList<String> listingDocumentNames = new ArrayList<String>();
	protected static ArrayList<String> memberDocumentNames = new ArrayList<String>();
	protected static ArrayList<String> officeDocumentNames = new ArrayList<String>();
	protected static ArrayList<String> taxDocumentNames = new ArrayList<String>();
	protected static ArrayList<String> cityDocumentNames = new ArrayList<String>();
	protected static ArrayList<String> countyDocumentNames = new ArrayList<String>();
	protected static ArrayList<String> subDivisionDocumentNames = new ArrayList<String>();
	protected static ArrayList<String> builderModelDocumentNames = new ArrayList<String>();
	protected static ArrayList<String> buildingNameDocumentNames = new ArrayList<String>();
	protected static ArrayList<String> teamDocumentNames = new ArrayList<String>();
	public static String sessionKey;
	public static String bearerToken;
	public static String bypassSessionKey;
	public static String bypassBearerToken;
	
	@BeforeSuite(alwaysRun = true)
	public void cloneRepo() throws IOException, ParseException {

		/** --------Properties object is created--------- **/
		PropertyConfigurator.configure(workingDir + "/resources/log4j.properties");
		try {
			input = new FileInputStream(workingDir + "/resources/config.properties");
			property = new Properties();
			try {
				property.load(input);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException fileException) {
			logger.info("File not found" + fileException);
		}

//		/** --------Database connection is created--------- **/
//		logger.info("Selected env is:" + CommonUtilityMethods.getEnvironment());
//
//
//		Connection connection = DatabaseUtility.CreateConnection();
//		DatabaseUtility.conn = connection;
//		DatabaseUtility.WriteLookUpFile(connection);
//		DatabaseUtility.WriteToResourceFile(connection);
//
//		try {
//
//			GitUtility.cloneRepo(Constants.gitUrlConfig,
//					Constants.gitLocalDirectoryConfig);
//			GitUtility.cloneRepo(Constants.gitUrlArtifacts,
//					Constants.gitLocalDirectoryArtifacts);
//
//
//		} catch (Exception e) {
//
//			e.printStackTrace(); }
//
//
//		/** --------Threads for rets are created--------- **/
//
//
//		CreateRetsThreadPage threadBright = new
//				CreateRetsThreadPage(); try {
//					threadBright.divideIntoThreads("Bright"); } catch
//				(FileNotFoundException | InterruptedException e)
//				{
//
//						e.printStackTrace(); } CreateRetsThreadPage
//				threadWirex = new CreateRetsThreadPage(); try {
//					threadWirex.divideIntoThreads("Wirex"); } catch
//				(FileNotFoundException | InterruptedException e)
//				{
//
//						e.printStackTrace(); }


//				/**
//				 * --------Creating docStore document names file to be used in various
//				 * classes---------
//				 */
//				CreateDocNamesThreads lmsObjThread = new CreateDocNamesThreads();
//				try {
//					lmsObjThread.divideIntoThreads("lmsObject");
//				} catch (FileNotFoundException | InterruptedException e) {
//					e.printStackTrace();
//				}
//				CreateDocNamesThreads itemsThread = new CreateDocNamesThreads();
//				try {
//					itemsThread.divideIntoThreads("items");
//				} catch (FileNotFoundException | InterruptedException e) {
//					e.printStackTrace();
//				}
//
//				
					try {
						String sessionid = SessionManagementPage.fetchSessionToken("brokerId", "brokerPassword");
						String code = SessionManagementPage.fetchLocationfromCode(sessionid);
						String idToken = SessionManagementPage.fetchIDToken(code);
						sessionKey = SessionManagementPage.fetchSessionkey(idToken);
						bearerToken = SessionManagementPage.fetchBearerToken();
						logger.info("Successfully got the session token token for user :" + property.getProperty("UserName"));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				

	}

	@AfterSuite(alwaysRun = true)
	public void deleteArtifacts() throws IOException, ParseException {

		/** --------Deleting various directories Created for Rules testing--------- **/
		File localRepoDirConfig = new File(Constants.gitLocalDirectoryConfig);
		File localRepoDirArtifacts = new File(Constants.gitLocalDirectoryArtifacts);
		File brightRulesTestData = new File(Constants.BrightRulesTestDataPath);
		File wirexRulesTestData = new File(Constants.WirexRulesTestDataPath);
		FileUtility.deleteDirectory(localRepoDirConfig);
		FileUtility.deleteDirectory(localRepoDirArtifacts);
		FileUtility.deleteDirectory(brightRulesTestData);
		FileUtility.deleteDirectory(wirexRulesTestData);
		logger.info("Test Data files deleted successfully");
	}

	/**
	 * A method to get the setup ready
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@BeforeClass(alwaysRun = true)
	public void setUpMethod() throws IOException, InterruptedException {

		if (this.getClass().getName().contains("RulesDataCheck")) {

			CommonUtilityMethods.createTestDataDir();
			CommonUtilityMethods.getUserCredentails();
			List<String> brightRules = new ArrayList();
			List<String> WireRules = new ArrayList();
			List<String> lovId = new ArrayList();
			FileWriter brightFile = new FileWriter(Constants.BrightRuleConfigMapping, false);
			FileWriter wirexFile = new FileWriter(Constants.WirexRuleConfigMapping, false);

			List<String> rulesData = FileUtility.GetAllFilesFromDirectory(Constants.congRulesBusinessView);
			for (String rulesFile : rulesData) {

				if (rulesFile.contains("DS_Store")) {
					continue;
				}
				JSONObject jsonObject = JsonUtility.GetJsonFileData(rulesFile);

				if (jsonObject.get("localeId").toString().equalsIgnoreCase(Constants.BrightLocale)) {
					brightRules.add(jsonObject.get("ruleUsageId").toString());
				} else

					WireRules.add(jsonObject.get("ruleUsageId").toString());
			}
			brightFile.write(brightRules.toString());
			wirexFile.write(WireRules.toString());

			brightFile.close();
			wirexFile.close();

			CreateThreadLogicalAttributeMappingPage thread = new CreateThreadLogicalAttributeMappingPage();
			thread.divideIntoThreads();
		}
	}

	@BeforeMethod(alwaysRun = true)
	public void setup(Method method) {
		logger.info("Test Started => " + method.getName());
		requestSpecification = CommonUtilityMethods.requestSpec();
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown(Method method) {
		logger.info("Test Ended => " + method.getName());
		logger.info("========================================\n");
	}

}