package bright.api.alaya.utils;

import java.util.List;
import java.util.Map;

import org.testng.util.Strings;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.GetSubscriptionAttributesRequest;
import software.amazon.awssdk.services.sns.model.GetSubscriptionAttributesResponse;
import software.amazon.awssdk.services.sns.model.GetTopicAttributesRequest;
import software.amazon.awssdk.services.sns.model.GetTopicAttributesResponse;
import software.amazon.awssdk.services.sns.model.ListSubscriptionsByTopicRequest;
import software.amazon.awssdk.services.sns.model.ListSubscriptionsByTopicResponse;
import software.amazon.awssdk.services.sns.model.ListTopicsRequest;
import software.amazon.awssdk.services.sns.model.ListTopicsResponse;
import software.amazon.awssdk.services.sns.model.SnsException;
import software.amazon.awssdk.services.sns.model.Subscription;

public class SNSConnection extends MainClassAlaya {

	/**
	 * A method to create SNS client
	 */

	public static SnsClient createSnsClient(Region region, String profile) {
		SnsClient snsClient=null;

		if(!Strings.isNullOrEmpty(System.getProperty("env"))) 
			snsClient = SnsClient.builder().region(region)
			.build();
		else
			snsClient = SnsClient.builder().region(region)
			.credentialsProvider(ProfileCredentialsProvider.create(profile)).build();

		return snsClient;
	}

	/**
	 * A method to get ANS topic attributes
	 */

	public static void getSNSTopicAttributes(SnsClient snsClient, String topicArn) {

		try {

			GetTopicAttributesRequest request = GetTopicAttributesRequest.builder().topicArn(topicArn).build();
			GetTopicAttributesResponse result = snsClient.getTopicAttributes(request);
			logger.info("\n\nStatus is " + result.sdkHttpResponse().statusCode() + "\n\nAttributes: \n\n"
					+ result.attributes());

		} catch (SnsException e) {
			System.err.println(e.awsErrorDetails().errorMessage());
		}
	}

	/**
	 * A method to list SNS Subscription
	 */

	public static List<Subscription> listSNSSubscriptions( SnsClient snsClient , String topicArn) {

		ListSubscriptionsByTopicResponse result= null;
		try {
			ListSubscriptionsByTopicRequest request = ListSubscriptionsByTopicRequest.builder().topicArn(topicArn)
					.build();

			result = snsClient.listSubscriptionsByTopic(request);

		} catch (SnsException e) {

			System.err.println(e.awsErrorDetails().errorMessage());
		}
		return result.subscriptions();
	}

	/**
	 * A method to fetch filter policy
	 */

	public static String fetchFilterPolicy(SnsClient snsClient, String subscriptionArn) {
		String filterPolicy = null;
		try {

			GetSubscriptionAttributesRequest request = GetSubscriptionAttributesRequest.builder()
					.subscriptionArn(subscriptionArn).build();

			GetSubscriptionAttributesResponse result = snsClient.getSubscriptionAttributes(request);

			Map<String, String> map = result.attributes();
			filterPolicy = map.get("FilterPolicy").toString();

		} catch (SnsException e) {
			System.err.println(e.awsErrorDetails().errorMessage());
		}
		return filterPolicy;
	}
}