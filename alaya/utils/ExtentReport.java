package bright.api.alaya.utils;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import software.amazon.awssdk.services.ssm.SsmClient;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExtentReport extends MainClassAlaya implements IReporter,ITestListener,ISuiteListener {
	ExtentReports extent;
	ExtentHtmlReporter htmlReporter;
	ExtentTest test;
	public static final ArrayList<String> failedMethods = new ArrayList<String>();
	public static final ArrayList<String> passedMethods = new ArrayList<String>();
	public static final ArrayList<String> skippedMethods = new ArrayList<String>();
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		htmlReporter = new ExtentHtmlReporter(Constants.reportPath);
		htmlReporter.config().setReportName("Alaya Report");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		for (ISuite suite : suites) {
			Map<String, ISuiteResult> result = suite.getResults();

			for (ISuiteResult r : result.values()) {
				ITestContext context = r.getTestContext();

				buildTestNodes(context.getPassedTests(), Status.PASS);
				buildTestNodes(context.getFailedTests(), Status.FAIL);
				buildTestNodes(context.getSkippedTests(), Status.SKIP);
			}
			
		}
		extent.flush();
		String env = CommonUtilityMethods.getEnvironment();
		String region = CommonUtilityMethods.getRegionString();
		logger.info("Trying to send Test suite Reports");
		//SlackIntegration.sendTestExecutionReportToSlack(env,region);
		
		/** --------Deleting the paramStore file content--------- **/
		JSONParser parser = new JSONParser();
    	Object obj;
		try {
			obj = parser.parse(new FileReader(Constants.parameterStore));
			JSONObject paramObj = (JSONObject)obj;
			JSONObject newParamObj = (JSONObject)obj;
			  for (Object key : paramObj.keySet()) {
				   String keyStr = (String)key;
				   String value = paramObj.get(keyStr).toString();
				   value = value.replace(value, "");
				   newParamObj.put(keyStr, value);
			  }	
			FileWriter valuesFile = new FileWriter(Constants.parameterStore);
			valuesFile.write(newParamObj.toJSONString());
	        valuesFile.close();
		} catch (FileNotFoundException e) {
			logger.info("Parameter File not found");
		} catch (IOException e) {
			logger.info("Parameter File does not exist");
		} catch (ParseException e) {
			logger.info("Parameter File was empty..Looking into config file");
		}
	}

	private void buildTestNodes(IResultMap tests, Status status) {
		

		if (tests.size() > 0) {
			for (ITestResult result : tests.getAllResults()) {
				String feature = "Class : "+result.getMethod().getRealClass().getName().replace("bright.api.alaya.test.","") + " | " + "Method : "+result.getMethod().getMethodName();
				test = extent.createTest(feature,result.getMethod().getMethodName());

				for (String group : result.getMethod().getGroups())
					test.assignCategory(group);

				String message = "Test " + status.toString().toLowerCase() + "ed";

				if (result.getThrowable() != null)
					message = result.getThrowable().getMessage();

				test.log(status, message);
			}
		}
	}
	
	public void onStart(ISuite suite) {
		/** --------Creating the paramStore file by fetching creds from aws--------- **/
		SsmClient ssm = null;
		ssm = SSMConnection.createSsmClient(CommonUtilityMethods.getAWSRegion(), EnvSpecificMethods.awsProfile());
		try {
			SSMConnection.getParaValue(ssm);
		} catch (IOException e) {
			logger.info("Paremeter File does not exist");
		} catch (ParseException e) {
			logger.info("Parameter File was empty..Looking into config file");
		}
		logger.info("Param Store values retireved successfully");
		
		String env = CommonUtilityMethods.getEnvironment();
		String region = CommonUtilityMethods.getRegionString();
			String message = ">> *Build Started:* " + " :Hourglass: " + "\n" +
					"*Project:-* " + StringUtils.capitalize(Constants.Project) + " |  " + 
					"*Environment:-* " + StringUtils.capitalize(env) + " |  " + 
					"*Region:-*  " + StringUtils.capitalize(region);		
					//SlackIntegration.sendTestExecutionStatusToSlack(message,env);
	}
	
	public void onFinish(ISuite suite) {
		String env = CommonUtilityMethods.getEnvironment();
		String region = CommonUtilityMethods.getRegionString();
		String message = ">> *Build Completed:* " + " :Hourglass: " + "\n" +
				"*Project:-* " + StringUtils.capitalize(Constants.Project) + " |  " + 
				"*Environment:-* " + StringUtils.capitalize(env) + " |  " + 
				"*Region:-*  " + StringUtils.capitalize(region);
		//SlackIntegration.sendTestExecutionStatusToSlack(message,env);
		if(failedMethods.isEmpty() && skippedMethods.isEmpty()) {
			String passMessage = "All " + passedMethods.size() + "  Tests Passed" + " :white_check_mark: ";
			//SlackIntegration.sendTestExecutionStatusToSlack(passMessage,env);
		}
		else if(failedMethods.isEmpty() && skippedMethods.size()>0) {
			String skipMessage = "No failures Only Skipped methods detected Please check the Report";
			//SlackIntegration.sendTestExecutionStatusToSlack(skipMessage,env);
		}
		else {
		//SlackIntegration.sendFailedTestMethodsToSlack(failedMethods,passedMethods);	
		}	
	}
	
	public void onTestFailure(ITestResult result) {			
		String classname = result.getTestClass().getName().replace("bright.api.alaya.test.","");
		failedMethods.add(classname + "  ||  " + result.getMethod().getMethodName());
	}
	
	
	public void onTestSuccess(ITestResult result) {
		passedMethods.add(result.getMethod().getMethodName());
	}
	
	public void onTestSkipped(ITestResult result) {
		skippedMethods.add(result.getMethod().getMethodName());
		
	}




	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	

}