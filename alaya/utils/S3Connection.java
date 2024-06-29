package bright.api.alaya.utils;

import java.io.IOException;
import java.util.ArrayList;

import org.testng.util.Strings;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;

public class S3Connection {

	/**
	 * A method to create S3 Client
	 */

	public static S3Client createS3Client(Region region, String profile) {

		S3Client s3Client=null;
		if(!Strings.isNullOrEmpty(System.getProperty("env"))) 
		 s3Client = S3Client.builder().region(region).build();
		else
			 s3Client = S3Client.builder().region(region)
				.credentialsProvider(ProfileCredentialsProvider.create(profile)).build();
		return s3Client;
	}

	/**
	 * A method to get S3 object
	 */

	public static String getS3Object(S3Client s3, String bucketName, String keyName)  {
		GetObjectRequest objRequest = GetObjectRequest.builder().bucket(bucketName).key(keyName).build();

		ResponseInputStream<GetObjectResponse> objResponse = s3.getObject(objRequest);


		String result = new String();
		try {
			int data;
			data = objResponse.read();
			while(data !=-1) {
				result = result + (char)data;
				data = objResponse.read();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * A method to list all objects 
	 */

	public static ArrayList<String> listObjects(S3Client s3, String bucketName, String keyName, int limit){

		ArrayList<String> result = new ArrayList<String>();

		ListObjectsV2Request request = ListObjectsV2Request.builder().bucket(bucketName).maxKeys(limit).prefix(keyName)
				.build();
		ListObjectsV2Response response = s3.listObjectsV2(request);

		for(S3Object object: response.contents()) {
			result.add((object.key().split("/")[2]).split(".json")[0]);
		}


		return result;
	}

}
