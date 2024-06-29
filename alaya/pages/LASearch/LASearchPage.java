package bright.api.alaya.pages.LASearch;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import org.apache.commons.collections4.CollectionUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import org.testng.util.Strings;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.EnvSpecificMethods;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.RequestSpecification;

public class LASearchPage extends MainClassAlaya {
	
	protected static RequestSpecification requestSpecification = null;
	protected static final String base_uri = EnvSpecificMethods.discoveryLayerURL();
	
	public static HashMap<String, String> resourceKeys = new HashMap<String,String>(){{
        put("listings", "listing");
        put("taxRecord","taxrecord");
    }};
    
    public static HashMap<String, String> docNameKeyMap= new HashMap<String,String>(){{
        put("listings", "listingKey");
        put("taxRecord","taxId");
    }};
    
    public static HashMap<String, String> resourceGraphqlKeys = new HashMap<String,String>(){{
        put("listings", "listingKey");
        put("taxRecord","taxId__keyword");
    }};
    
    public static HashMap<String, String> nestedKeys = new HashMap<String,String>(){{
        put("listings", "listingId");
        put("taxRecord","parcelId");
    }};
	
	
	/**
	 * A method to get fields values from lae api response
	 */	
	
	public static ArrayList<String> getfieldValuesForLAE(String type,JSONObject laeResponseObj) {
	    ArrayList<String> result = new ArrayList<String>();
	    ArrayList<String> laSearchFieldsList = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.laSearchAttributesPath, type+"Fields").get("laSearch"));
	    
	    for(int i = 0 ; i < laSearchFieldsList.size() ; i++ ) {
	    	String field = laSearchFieldsList.get(i);
	    	String attributeToAdd = null ;
	    	if(laeResponseObj.has(field)) {
		    	attributeToAdd = laeResponseObj.get(field).toString();
		    }
	    	else {
	    		logger.info(field+" was not found in la search response Object for resource "+type);
	    	}
	    	result.add(attributeToAdd);
	    }
        return result;
	}
	
	
	/**
	 * A method to get fields values from graphql api response
	 */	
	
	public static ArrayList<String> getFieldValuesForGraphql(String type,JSONObject laeResponseObj,ArrayList<String> laeFieldList){
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<String> graphQlAttributesList = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.laSearchAttributesPath, type+"Fields").get("graphQl"));
	    String docType = resourceKeys.get(type);
	    String key = resourceGraphqlKeys.get(type);
	    String docNameKey = docNameKeyMap.get(type);
		String docName = laeResponseObj.get(docNameKey).toString();
		String graphqlMethod = CommonUtilityMethods.graphQlMethods.get(docType);
		String nestedKey = nestedKeys.get(type);
		String nestedQueryAttribute = laeFieldList.get(0).toString();
	    result = CommonUtilityMethods.fetchGraphQlResponseByDocNameAndSecondAttributeForLAE(docType,docName,graphqlMethod,key,nestedKey,graphQlAttributesList,nestedQueryAttribute);
		return result;
	}
	

	/**
	 * A method to send put call for LA search api
	 */	
	public static Response putLaSearch(RequestSpecification httpRequest,String condition) {
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpecForDiscovery();
		httpRequest = RestAssured.given();
		String payload = CommonUtilityMethods.readJsonFile(Constants.laSearchPath, condition).toString();
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"laSearch").getString("postLaSearch").toString());	
		requestSpecification.body(payload);
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.post();
		logger.info("LAS POST Response status code is : "+ response.getStatusCode());
		logger.info("LAS POST Response status line is : "+ response.getStatusLine());
		return response;
	}
	
	
	/**
	 * A method to send post call for LA search api and verify various status codes
	 */

	public static void verifyLASearch(RequestSpecification httpRequest,String condition, int responseCode) {
		Response response = putLaSearch(httpRequest,condition);
		Assert.assertEquals(response.getStatusCode(), responseCode, "Status code mismatch found. Expected was - "+responseCode+"Actual was - "+response.getStatusCode());
		logger.info("Verified the Post call status code "+response.getStatusCode()+"of LA Search API for condition - "+condition);
	}
	
	
	/**
	 * A method to verify forbidden status if no x api key is given
	 */
	
	public static void verifyForbiddenStatusForLASearch(RequestSpecification httpRequest) {
		requestSpecification = (RequestSpecification) RestAssured.given();
		String payload = CommonUtilityMethods.readJsonFile(Constants.laSearchPath, "success").toString();
		requestSpecification.baseUri(base_uri);
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"laSearch").getString("postLaSearch").toString());	
		requestSpecification.headers("x-api-key","check");                 //passing invalid value for x-api-key
		requestSpecification.body(payload);
		Response  response = requestSpecification.post();
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.FORBIDDEN, "Status code mismatch found. Expected was - "+ResponseCodes.FORBIDDEN+"Actual was - "+response.getStatusCode());
		logger.info("Verified the forbidden status code for LA Search API for wrong x api key");
	}
	
	/**
	 * A method to verify  search limit with items in the response
	 */
	
	public static void verifySearchLimitForLASearch(RequestSpecification httpRequest,String condition) {
		int searchLimit = 0;
		if(condition.equalsIgnoreCase("searchLimit"))
			searchLimit = (int) new JSONObject(CommonUtilityMethods.readJsonFile(Constants.laSearchPath, "searchLimit").toString()).get("searchLimit");		
		else if(condition.equalsIgnoreCase("DefaultsearchLimit"))
			searchLimit = 10;
		Response response = putLaSearch(httpRequest,condition);
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.SUCCESS, "Status code mismatch found. Expected was - "+ResponseCodes.SUCCESS+"Actual was - "+response.getStatusCode());
		String responseBody = response.getBody().asString();
		int responseData = new JSONObject(responseBody).getJSONArray("items").length();
		logger.info("Search limit in payload is : "+ searchLimit);
		logger.info("Search limit in response is : "+ responseData);
		Assert.assertEquals(searchLimit, responseData,"Search Limit mismatch found. Expected limit was - "+searchLimit+"Actual limit was -"+responseData);
		logger.info("Verified the search limit for LA Search API from response");
	}
	
	/**
	 * A method to verify search resources for listing in the response
	 */
	
	public static void verifySearchResourcesWithListingForLASearch(RequestSpecification httpRequest) {
		Response response = putLaSearch(httpRequest,"searchResourcesForListing");
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.SUCCESS, "Status code mismatch found. Expected was - "+ResponseCodes.SUCCESS+"Actual was - "+response.getStatusCode());
		String responseBody = response.getBody().asString();
		JSONArray items = new JSONObject(responseBody).getJSONArray("items");
		for(int i=0; i<items.length() ; i++) {
			int validate = 0;
			JSONObject itemObj = (JSONObject)items.get(i);
			if(itemObj.has("listings") && itemObj.has("taxRecord")) {
				validate = 1;
			}
			if(!(itemObj.has("listings")) && itemObj.has("taxRecord")) {
				validate = 1;
			}
			JSONArray listing = itemObj.getJSONArray("listings");
			if(listing.length()==0) {
				validate = 1;
			}
			Assert.assertEquals(validate, 0, "Search Resource Mismatch found. Listings were not found.");
		}
	}
	
	/**
	 * A method to verify search resources for taxrecord in the response
	 */
	
	public static void verifySearchResourcesWithTaxForLASearch(RequestSpecification httpRequest) {
		Response response = putLaSearch(httpRequest,"searchResourcesForTax");
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.SUCCESS, "Status code mismatch found. Expected was - "+ResponseCodes.SUCCESS+"Actual was - "+response.getStatusCode());
		String responseBody = response.getBody().asString();
		JSONArray items = new JSONObject(responseBody).getJSONArray("items");
		for(int i=0; i<items.length() ; i++) {
			int validate = 0;
			JSONObject itemObj = (JSONObject)items.get(i);
			if(itemObj.has("listings") && itemObj.has("taxRecord")) {
				validate = 1;
			}
			if(!(itemObj.has("taxRecord")) && itemObj.has("listings")) {
				validate = 1;
			}
			JSONObject tax = itemObj.getJSONObject("taxRecord");
			if(tax.length()==0) {
				validate = 1;
			}
			Assert.assertEquals(validate, 0, "Search Resource Mismatch found. Taxrecords were not found.");
		}
	}
	
	/**
	 * A method to verify difference in responses for with and without location latitude and longitude for LA search api
	 */
	
	public static void verifyDifferenceForLASearch(RequestSpecification httpRequest) {
		Response withResponse = putLaSearch(httpRequest,"success");
		Assert.assertEquals(withResponse.getStatusCode(), ResponseCodes.SUCCESS, "Status code mismatch found. Expected was - "+ResponseCodes.SUCCESS+"Actual was - "+withResponse.getStatusCode());
		String withlongAndLat = withResponse.getBody().asString();
		Response withoutResponse = putLaSearch(httpRequest, "withoutLangAndLat");
		Assert.assertEquals(withoutResponse.getStatusCode(), ResponseCodes.SUCCESS, "Status code mismatch found. Expected was - "+ResponseCodes.SUCCESS+"Actual was - "+withoutResponse.getStatusCode());
		String withoutlongAndLat = withoutResponse.getBody().asString();
		Assert.assertNotEquals(withlongAndLat, withoutlongAndLat, "Response mismatch found. The Two Responses were same. Response with latitude and longitude was - "+withlongAndLat+" Response without latitude and longitude was - "+withoutlongAndLat);
	}
	
	/**
	 * A method to verify  fields in response for LA search api
	 * @throws JsonProcessingException 
	 * @throws JsonMappingException 
	 */
	
	public static void verifyFieldsForLASearch(RequestSpecification httpRequest, String resourceType, String payload) throws JsonMappingException, JsonProcessingException {
		
		JSONArray FieldsArray = (JSONArray) CommonUtilityMethods.readJsonFile(Constants.laSearchAttributesPath,resourceType).get("fields");	
		JSONArray ResponseArray = new JSONArray();	
		JSONArray FieldsArr = new JSONArray();	
		JSONObject FieldsObj = new JSONObject();
		
		Response Response = putLaSearch(httpRequest,payload);
		Assert.assertEquals(Response.getStatusCode(), ResponseCodes.SUCCESS, "Status code mismatch found. Expected was - "+ResponseCodes.SUCCESS+"Actual was - "+Response.getStatusCode());
		JSONArray items = new JSONObject(Response.getBody().asString()).getJSONArray("items");
		JSONObject itemObj = (JSONObject)items.get(0);
       
		String arrayCheckString = itemObj.get(resourceType).toString();		
		String firstChar = String.valueOf(arrayCheckString.charAt(0));
		if (firstChar.equalsIgnoreCase("[")) {
			FieldsArr = itemObj.getJSONArray(resourceType);
		    FieldsObj = (JSONObject)FieldsArr.get(0);
		}
		else
			FieldsObj = (JSONObject)itemObj.get(resourceType);
		
		Iterator<String> Keys = FieldsObj.keys();
		while (Keys.hasNext()) 
		{
		  String fieldValue = (String)Keys.next();
		  ResponseArray.put(fieldValue);
		}
		
		ArrayList<String> expectedList = CommonUtilityMethods.convertJArrayToListString(FieldsArray);
		ArrayList<String> responseList = CommonUtilityMethods.convertJArrayToListString(ResponseArray);
		Assert.assertTrue(CollectionUtils.isEqualCollection(expectedList, responseList),"Fields mismatch found for "+resourceType+" Expected array was - "+FieldsArray+ " Actual array was - "+ResponseArray);		
	}
	
	/**
	 * A method to verify data in the response for LAS with ES
	 */
	
	public static void verifyDataForLASearch(RequestSpecification httpRequest) {
		ArrayList<String> laeFieldsList = new ArrayList<String>();
		ArrayList<String> esFieldsList = new ArrayList<String>();
		
		ArrayList<String> addList = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.laSearchAttributesPath, "addresses").get("address"));
		String address = null;
		address = addList.get(CommonUtilityMethods.pickRandomFromList(addList));
	
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpecForDiscovery();
		httpRequest = RestAssured.given();
		JSONObject payloadOBJ = CommonUtilityMethods.readJsonFile(Constants.laSearchPath, "verifyData");
		payloadOBJ.put("searchText", address);
		String payload = payloadOBJ.toString();
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"laSearch").getString("postLaSearch").toString());	
		requestSpecification.body(payload);
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.post();
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.SUCCESS, "Status code mismatch found. Expected was - "+ResponseCodes.SUCCESS+"Actual was - "+response.getStatusCode());
		JSONArray items = new JSONObject(response.getBody().asString()).getJSONArray("items");
		
		for(int i = 0 ; i<items.length(); i++) {
			JSONObject itemObj = items.getJSONObject(i);
			
			if(itemObj.has("listings")) {
			JSONArray listingArray = itemObj.getJSONArray("listings");
			JSONObject listingArrayObj = listingArray.getJSONObject(0);
		    laeFieldsList = getfieldValuesForLAE("listings",listingArrayObj);
		    esFieldsList = getFieldValuesForGraphql("listings",listingArrayObj,laeFieldsList);
		    Assert.assertTrue(CollectionUtils.isEqualCollection(laeFieldsList, esFieldsList),"Data mismatch found for listings object . Expected list from ES was - "+esFieldsList+ " Actual list from LAE was - "+laeFieldsList);		
			}
			
			else if(itemObj.has("taxRecord")) {
			JSONObject taxObject = itemObj.getJSONObject("taxRecord");
			laeFieldsList = getfieldValuesForLAE("taxRecord",taxObject);
			esFieldsList = getFieldValuesForGraphql("taxRecord",taxObject,laeFieldsList);
			Assert.assertTrue(CollectionUtils.isEqualCollection(laeFieldsList, esFieldsList),"Data mismatch found for taxrecord object . Expected list from ES was - "+esFieldsList+ " Actual list from LAE was - "+laeFieldsList);		
			}
	        		
		}
		logger.info("Verified data for listing and tax resources for LA Search API");
	}
	
	/**
	 * A method to verify same response for two different states
	 */
	
	public static void verifySameResponseForDifferentAddresses(RequestSpecification httpRequest) {
		
		Response response = putLaSearch(httpRequest,"differentStatesOne");
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.SUCCESS, "Status code mismatch found. Expected was - "+ResponseCodes.SUCCESS+"Actual was - "+response.getStatusCode());
		String responseBody = response.getBody().asString();
		JSONArray itemsFromStateOne = new JSONObject(responseBody).getJSONArray("items");
		
		Response responseForChangedAdd = putLaSearch(httpRequest,"differentStatesTwo");
		String responseBodyForChangedAdd = responseForChangedAdd.getBody().asString();
		JSONArray itemsFromStateTwo = new JSONObject(responseBodyForChangedAdd).getJSONArray("items");
		
		for(int i = 0;i<itemsFromStateOne.length();i++) {
			JSONObject addressFromItem = (JSONObject) itemsFromStateOne.get(i);
			JSONObject secondAddressFromItem = (JSONObject) itemsFromStateTwo.get(i);
			if(addressFromItem.get("address").equals(secondAddressFromItem.get("address"))) {
				if(addressFromItem.has("listings") && secondAddressFromItem.has("listings")) {
				JSONArray listone = (JSONArray) addressFromItem.get("listings");
				JSONArray listTwo = (JSONArray) secondAddressFromItem.get("listings");
				Assert.assertEquals(listone.toString(),listTwo.toString(), "Addresses matched for two responses, but listings array did not match, First listing was - "+listone+" second listing was - "+listTwo);
				}
				if(addressFromItem.has("taxRecord") && secondAddressFromItem.has("taxRecord")) {
				JSONObject taxone = (JSONObject) addressFromItem.get("taxRecord");
				JSONObject taxTwo = (JSONObject) secondAddressFromItem.get("taxRecord");
				Assert.assertEquals(taxone.toString(),taxTwo.toString(), "Addresses matched for two responses, but taxrecord objects did not match, First tax record was - "+ taxone +" second taxrecord was - "+taxTwo);
				}
			}
		}
	}
	
	/**
	 * A method to verify city and state with addresses in the response 
	 */
	
	public static void verifyCityAndStateForDifferentAddresses(RequestSpecification httpRequest) {		
		int flag = 0;
		ArrayList<String> addList = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.laSearchAttributesPath, "addresses").get("address"));
		String address = null;
		address = addList.get(CommonUtilityMethods.pickRandomFromList(addList));
	
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpecForDiscovery();
		httpRequest = RestAssured.given();
		JSONObject payloadOBJ = CommonUtilityMethods.readJsonFile(Constants.laSearchPath, "DifferentAddresses");
		payloadOBJ.put("searchText", address);
		String payload = payloadOBJ.toString();
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"laSearch").getString("postLaSearch").toString());	
		requestSpecification.body(payload);
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.post();
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.SUCCESS, "Status code mismatch found. Expected was - "+ResponseCodes.SUCCESS+"Actual was - "+response.getStatusCode());
		JSONArray items = new JSONObject(response.getBody().asString()).getJSONArray("items");
		for(int i = 0 ; i<items.length(); i++) {
		JSONObject itemObj = items.getJSONObject(i);
		String responseState = itemObj.get("state").toString().toLowerCase();
		if(address.toLowerCase().contains(responseState)) {
			flag = flag+1;
		   }
		}
		if(flag==0) {
		Assert.fail("State was not found in LA search response for the address - "+address);
		}
	    logger.info("Verified correct cities and states For LA Search API");
	}
	
	/**
	 * A method to verify city and state with addresses in the response 
	 */
	
	public static void verifyNullKeyWord(RequestSpecification httpRequest) {
		Response response = putLaSearch(httpRequest,"searchLimit");
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.SUCCESS, "Status code mismatch found. Expected was - "+ResponseCodes.SUCCESS+"Actual was - "+response.getStatusCode());
		String responseBody = response.getBody().asString();
		JSONArray items = new JSONObject(responseBody).getJSONArray("items");
		JSONObject itemOBJ = items.getJSONObject(0);
		if(itemOBJ.has("listings")) {
			Assert.assertTrue((itemOBJ.getJSONArray("listings").getJSONObject(0).get("listAgentTeamKey")).equals(null), "Null keyword mismatch Found. List agent team key string found was not null");	
		}
	}
	
	/**
	 * A method to verify no listings in the response if office exclusive is true
	 */
	public static void verifyOfficeExclusive(RequestSpecification httpRequest){
		SoftAssert softAssert = new SoftAssert();
		RequestSpecification requestSpecification;
		requestSpecification = CommonUtilityMethods.requestSpecForDiscovery();
		httpRequest = RestAssured.given();
		String address = null;

		JSONArray listingsWithTrueOfficeEYN = CommonUtilityMethods.fetchGraphQlTrueOfficeExclusiveListings("listing","getListings");
		int randomIndex = (int) (Math.random() * listingsWithTrueOfficeEYN.length());
		JSONObject randomListingObj =  listingsWithTrueOfficeEYN.getJSONObject(randomIndex); 
		String officeExclusiveYN = randomListingObj.get("officeExclusiveYN").toString();
		if(officeExclusiveYN.equalsIgnoreCase("true")) {
			address = randomListingObj.get("fullStreetAddress").toString();
		}
		
		String payload = CommonUtilityMethods.readJsonFile(Constants.laSearchPath, "OfficeExclusive").toString();
		JSONObject payloadObj = new JSONObject(payload);
		payloadObj.put("searchText", address);
		requestSpecification.basePath(CommonUtilityMethods.readJsonFile(property.getProperty("apiEndpointsJsonPath"),"laSearch").getString("postLaSearch").toString());	
		requestSpecification.body(payloadObj.toString());
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.post();
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.SUCCESS, "Status code mismatch found. Expected was - "+ResponseCodes.SUCCESS+"Actual was - "+response.getStatusCode());
		
		String responseBody = response.getBody().asString();
		JSONArray items = new JSONObject(responseBody).getJSONArray("items");
		for(int i = 0 ; i<items.length(); i++) {
			JSONObject itemObj = items.getJSONObject(i);
			if(itemObj.has("listings")) {
				softAssert.fail("OfficeExclusiveYN was true for the address used in LA search request, but listings were found in LA search response");
			}
		}      
	}


}


	

	
