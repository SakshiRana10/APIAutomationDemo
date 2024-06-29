package bright.api.alaya.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;


import org.apache.commons.lang3.StringUtils;
import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.request.files.FilesUploadRequest;

public class SlackIntegration extends MainClassAlaya{
	
	public static void sendTestExecutionStatusToSlack(String message, String env) {
		org.json.simple.JSONObject data = CommonUtilityMethods.readSimpleJSONFile(Constants.SlackIntegration,"slackIntegration");
		MethodsClient slack = Slack.getInstance().methods();
		if(env.equalsIgnoreCase("P1")) {
			ChatPostMessageRequest request = ChatPostMessageRequest.builder().channel(data.get("channelNameProd").toString())
					.token(CommonUtilityMethods.fetchXApiKey("slackBotToken").toString()).text(message)
					.build();
			try {
				slack.chatPostMessage(request);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
		ChatPostMessageRequest request = ChatPostMessageRequest.builder().channel(data.get("channelName").toString())
				.token(CommonUtilityMethods.fetchXApiKey("slackBotToken").toString()).text(message)
				.build();
		try {
			slack.chatPostMessage(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
	}

	public static void sendTestExecutionReportToSlack(String env, String region) {
		org.json.simple.JSONObject integrationData = CommonUtilityMethods.readSimpleJSONFile(Constants.SlackIntegration,"slackIntegration");
		MethodsClient slack = Slack.getInstance().methods();
		String filepath = Constants.reportPath;
		if(env.equalsIgnoreCase("P1")) {
		FilesUploadRequest upload = FilesUploadRequest.builder()
				.token(CommonUtilityMethods.fetchXApiKey("slackBotToken").toString())
				.channels(Arrays.asList(integrationData.get("channelIdProd").toString()))
				.initialComment("*Automation Report:* " + " _Click to download the report._" + " :point_down:")
				.file(new File(filepath)).content("html").filename("AutomationReport.html").filetype("html").build();

		try {
		
			slack.filesUpload(upload);
		} catch (Exception error) {
			
			error.printStackTrace();
		}
	}
		else {
			FilesUploadRequest upload = FilesUploadRequest.builder()
					.token(CommonUtilityMethods.fetchXApiKey("slackBotToken").toString())
					.channels(Arrays.asList(integrationData.get("channelId").toString()))
					.initialComment("*Automation Report:* " + " _Click to download the report._" + " :point_down:")
					.file(new File(filepath)).content("html").filename("AutomationReport.html").filetype("html").build();

			try {
			
				slack.filesUpload(upload);
			} catch (Exception error) {
				
				error.printStackTrace();
			}
		}
		logger.info("Execution reports sent");
	}
	
	public static void sendFailedTestMethodsToSlack(ArrayList<String> failedMethods,ArrayList<String> passedMethods) {
		int totalcases = failedMethods.size()+passedMethods.size();
		String env = CommonUtilityMethods.getEnvironment();
		String region = CommonUtilityMethods.getRegionString();
		String message = "*Script Failed:* " + " :x: " +  "  Methods Failed : "+" :point_down:  " +  "\n"    +    
		failedMethods.toString().replace(",", "  \n--").replace("[", " -- ").replace("]", " ")+  "\n"    + 
		failedMethods.size() + " *Cases failed out of :* " + totalcases;
		SlackIntegration.sendTestExecutionStatusToSlack(message,env);
	}

}