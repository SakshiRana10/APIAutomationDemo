package bright.api.alaya.pages.dynamicRules;

import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONObject;

import bright.api.alaya.pages.objectLayer.ValidateAPIPage;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.MainClassAlaya;
import io.restassured.specification.RequestSpecification;

public class DynamicMandatoryRulesPage extends MainClassAlaya{
	
	ArrayList<String> AttributeNamesForES = new ArrayList<String>(Arrays.asList("listingKey"));
	
	
	public void getJsonContentForMandatoryFields(String Status, String PropertyType) {
		RequestSpecification httpRequest = null;
		ArrayList<String> listingkeys=CommonUtilityMethods.fetchGraphQLByStatusAndPropertyType("listing",AttributeNamesForES,Status,PropertyType);
		String listingDataJson=CommonUtilityMethods.fetchDocumentfromDocStore(httpRequest,"listing", listingkeys.get(0));
		JSONObject finalObj=new JSONObject( listingDataJson);
		JSONObject lmsobj = new JSONObject( listingDataJson).getJSONObject("lmsObject");
		lmsobj.put("StoryList", "200008636059");
		lmsobj.put("schoolDistrictName", JSONObject.NULL.toString());
		lmsobj.put("HeatingFuel", JSONObject.NULL.toString());
		lmsobj.put("ShortSale", JSONObject.NULL.toString());
		lmsobj.put("AssociationYN", JSONObject.NULL.toString());
		lmsobj.put("listOfficeBrokerOfRecordKey", "230025611757");
		System.out.println("content  is:"+lmsobj.toString());
		JSONObject responseBodyObj = new JSONObject();
		finalObj.put("lmsObject", lmsobj);
		responseBodyObj.put("content", finalObj);
		String payload = responseBodyObj.toString();
		
		
	}
}
