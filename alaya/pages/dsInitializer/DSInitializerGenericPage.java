package bright.api.alaya.pages.dsInitializer;

import java.util.HashMap;
import org.json.JSONObject;
import bright.api.alaya.utils.MainClassAlaya;

public class DSInitializerGenericPage extends MainClassAlaya {
	
	/**
	 * A method to verify request context
	 */
	public static HashMap<String,String> verifyRequestContext(String docType, JSONObject responseBodyObj, String prefix) {
		HashMap<String, String> result = new HashMap<String,String>();
		JSONObject requestContextObj = new JSONObject(responseBodyObj.get("requestContext").toString());
		result.put("prefix",requestContextObj.get("objectNamePrefix").toString());
		result.put("documentType",requestContextObj.get("documentType").toString());
		String snsAttributeStr = requestContextObj.get("snsMessageAttributes").toString();
		String subName = new JSONObject(snsAttributeStr).getJSONArray("subscriberName").get(0).toString();
		result.put("subscriberName", subName);
		return result;
    }
	
	/**
	 * A method to verify creation time
	 */
	public static HashMap<String,String> verifyCreationTime(String docType, JSONObject responseBodyObj) {
		HashMap<String, String> dateResult = new HashMap<String,String>();
		String creationTimeStamp =  responseBodyObj.get("creationTime").toString();
		String creationDate = creationTimeStamp.substring(0,creationTimeStamp.indexOf("T"));
		String currentDate = java.time.LocalDate.now().toString();
	    dateResult.put("currentDate", currentDate);
	    dateResult.put("creationDate", creationDate);
	    return dateResult;
    }
	
	/**
	 * A method to verify Initialization status
	 */
	public static String verifyInitializationStatus(JSONObject responseBodyObj) {
		String status = responseBodyObj.get("initializationState").toString();
		return status;
    }
	
	/**
	 * A method to verify sns messages pushed
	 */
	public static String verifyMessagesPushed(JSONObject responseBodyObj) {
		String msgsPushed = responseBodyObj.get("snsMessagesPushed").toString();
		return msgsPushed;	
    }
}
