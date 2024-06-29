package bright.api.alaya.pages.objectLayer;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.asserts.SoftAssert;

import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ObjectLayerMemberRulesPage extends MainClassAlaya {
	
	static String memberDataJson;
	static JSONObject pickListState;
	static JSONObject pickListCounty;
	static JSONObject lmsobj;
	static JSONObject pickListStatus;
	static ArrayList<String> AttributeNamesForES;
	static ArrayList<String> memberKey;
	static String stateReponse;
	static JSONObject items;
	static String countyRespinse;
	static JSONObject itemsCounty;
	
	public ObjectLayerMemberRulesPage(){
	RequestSpecification httpRequest = null;
    AttributeNamesForES = new ArrayList<String>(Arrays.asList("memberKey"));
	memberKey=CommonUtilityMethods.fetchGraphQLBydocKey("member",AttributeNamesForES);
	memberDataJson=CommonUtilityMethods.fetchDocumentfromDocStore(httpRequest,"member", memberKey.get(0));
	stateReponse=getConfigAPIWithPicklistIdAndName("STATES_OR_PROVINCES");
	items = new JSONObject(stateReponse).getJSONObject("item");
	pickListState=(JSONObject) items.get("pickListItems");
	countyRespinse=getConfigAPIWithPicklistIdAndName("COUNTIES_OR_REGIONS");
	itemsCounty = new JSONObject(countyRespinse).getJSONObject("item");
	pickListCounty=(JSONObject) itemsCounty.get("pickListItems");
	}

	public static void allNullValuesCheckBright(RequestSpecification httpRequest,String docName,String response,JSONObject rulesData) {
        SoftAssert softAssert = new SoftAssert();
		
		ObjectLayerPutAPIPage.verifySuccessPutCallAPIObjectLayer(httpRequest,response,docName,"member","parameterTrue");
		String getResponseAfterPut = ObjectLayerPage.getCallForObjectyLayer(httpRequest,"Bright","member",docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject getResponseAfterPutObj = new JSONObject(getResponseAfterPut);
		JSONObject result = getResponseAfterPutObj.getJSONObject("content").getJSONObject("lmsObject");
		
		String city = null;
		String county = null;
		boolean cityExist=true;
		cityExist=OfficeStaticRulePage.isCityExist(city,county);
		OfficeStaticRulePage.deleteCreatedCity(httpRequest,city,county,cityExist);

		if(!(Objects.equals(result.get("memberStateOrProvince").toString(),rulesData.get("state").toString()))) {
			softAssert.fail("Expected value of State:"+rulesData.get("state").toString()+" but state value after put Api:"+result.get("memberStateOrProvince").toString());
		}
		if(!(Objects.equals(result.get("memberCounty").toString(),rulesData.get("county").toString()))) {
			softAssert.fail("Expected value of county:"+rulesData.get("county").toString()+" but county value after put Api:"+result.get("memberCounty").toString());
		}
		if(!(Objects.equals(result.get("memberCity").toString(),rulesData.get("city").toString()))) {
			softAssert.fail("Expected value of city:"+rulesData.get("city").toString()+" but city value after put Api:"+result.get("memberCity").toString());
		}
		softAssert.assertAll();
	}


	public static void allValidValuesBright(RequestSpecification httpRequest,String docName,String response,JSONObject rulesData) {
        SoftAssert softAssert = new SoftAssert();
		
		ObjectLayerPutAPIPage.verifySuccessPutCallAPIObjectLayer(httpRequest,response,docName,"member","parameterTrue");
		String getResponseAfterPut = ObjectLayerPage.getCallForObjectyLayer(httpRequest,"Bright","member",docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject getResponseAfterPutObj = new JSONObject(getResponseAfterPut);
		JSONObject result = getResponseAfterPutObj.getJSONObject("content").getJSONObject("lmsObject");
		
		boolean cityExist=true;
		cityExist=OfficeStaticRulePage.isCityExist(rulesData.get("city").toString(),rulesData.get("county").toString());
		OfficeStaticRulePage.deleteCreatedCity(httpRequest,rulesData.get("city").toString(),rulesData.get("county").toString(),cityExist);

		if(!(result.get("memberStateOrProvince").equals(null)? null : result.get("memberStateOrProvince").toString().equals(rulesData.get("state").toString()))) {
			softAssert.fail("Expected value of State:"+rulesData.get("state").toString()+" but state value after put Api:"+result.get("memberStateOrProvince").toString());
		}

		if(!(result.get("memberCounty").equals(null)? null : result.get("memberCounty").toString().equals(rulesData.get("county").toString()))) {
			softAssert.fail("Expected value of county:"+rulesData.get("county").toString()+" but county value after put Api:"+result.get("memberCounty").toString());
		}
		if(!(result.getString("memberCity").equals(null)? null : result.getString("memberCity").equals(rulesData.get("city").toString()))) {
			softAssert.fail("Expected value of city:"+rulesData.get("city").toString()+" but city value after put Api:"+result.get("memberCity").toString());
		}
     	softAssert.assertAll();
	}


	public static void cityNotNullCountyNotNullStateNullBright(RequestSpecification httpRequest,String docName,String response,JSONObject rulesData) {
		SoftAssert softAssert = new SoftAssert();
		
		ObjectLayerPutAPIPage.verifySuccessPutCallAPIObjectLayer(httpRequest,response,docName,"member","parameterTrue");
		String getResponseAfterPut = ObjectLayerPage.getCallForObjectyLayer(httpRequest,"Bright","member",docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject getResponseAfterPutObj = new JSONObject(getResponseAfterPut);
		JSONObject result = getResponseAfterPutObj.getJSONObject("content").getJSONObject("lmsObject");
		
		boolean cityExist=true;
		cityExist=OfficeStaticRulePage.isCityExist(rulesData.get("city").toString(),rulesData.get("county").toString());
		OfficeStaticRulePage.deleteCreatedCity(httpRequest,rulesData.get("city").toString(),rulesData.get("county").toString(),cityExist);

		if(!(Objects.equals(result.get("memberStateOrProvince").toString(),rulesData.get("state").toString()))) {
			softAssert.fail("Expected value of State:"+rulesData.get("state").toString()+" but state value after put Api:"+result.get("memberStateOrProvince").toString());
		}
		if(!(result.get("memberCounty").equals(null)? null : result.get("memberCounty").toString().equals(rulesData.get("county").toString()))) {
			softAssert.fail("Expected value of county:"+rulesData.get("county").toString()+" but county value after put Api:"+result.get("memberCounty").toString());
		}
		if(!(result.getString("memberCity").equals(null)? null : result.getString("memberCity").equals(rulesData.get("city").toString()))) {
			softAssert.fail("Expected value of city:"+rulesData.get("city").toString()+" but city value after put Api:"+result.get("memberCity").toString());
		}
		softAssert.assertAll();
	}


	public static void cityNotNullCountyNullStateNullBright(RequestSpecification httpRequest,String docName,String response,JSONObject rulesData) {
		SoftAssert softAssert = new SoftAssert();
			
		ObjectLayerPutAPIPage.verifySuccessPutCallAPIObjectLayer(httpRequest,response,docName,"member","parameterTrue");
		String getResponseAfterPut = ObjectLayerPage.getCallForObjectyLayer(httpRequest,"Bright","member",docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject getResponseAfterPutObj = new JSONObject(getResponseAfterPut);
		JSONObject result = getResponseAfterPutObj.getJSONObject("content").getJSONObject("lmsObject");
		
		String county = null;
		boolean cityExist=true;
		cityExist=OfficeStaticRulePage.isCityExist(rulesData.get("city").toString(),county);
		OfficeStaticRulePage.deleteCreatedCity(httpRequest,rulesData.get("city").toString(),county,cityExist);

		if(!(Objects.equals(result.get("memberStateOrProvince").toString(),rulesData.get("state").toString()))) {
			softAssert.fail("Expected value of State:"+rulesData.get("state").toString()+" but state value after put Api:"+result.get("memberStateOrProvince").toString());
		}
		if(!(Objects.equals(result.get("memberCounty").toString(),rulesData.get("county").toString()))) {
			softAssert.fail("Expected value of county:"+rulesData.get("county").toString()+" but county value after put Api:"+result.get("memberCounty").toString());
		}
		if(!((result.get("memberCity").equals(null)? null : result.get("memberCity").toString()).equals(rulesData.get("city").toString()))) {
			softAssert.fail("Expected value of city:"+rulesData.get("city").toString()+" but city value after put Api:"+result.get("memberCity").toString());
		}
		softAssert.assertAll();
	}

	public static void citNullCountyNotNullStateNotNullBright(RequestSpecification httpRequest,String docName,String response,JSONObject rulesData) {
		SoftAssert softAssert = new SoftAssert();	
		
		ObjectLayerPutAPIPage.verifySuccessPutCallAPIObjectLayer(httpRequest,response,docName,"member","parameterTrue");
		String getResponseAfterPut = ObjectLayerPage.getCallForObjectyLayer(httpRequest,"Bright","member",docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject getResponseAfterPutObj = new JSONObject(getResponseAfterPut);
		JSONObject result = getResponseAfterPutObj.getJSONObject("content").getJSONObject("lmsObject");
		
		String city = null;
		boolean cityExist=true;
		cityExist=OfficeStaticRulePage.isCityExist(city,rulesData.get("county").toString());
		OfficeStaticRulePage.deleteCreatedCity(httpRequest,city,rulesData.get("county").toString(),cityExist);
		
		if(!(result.get("memberStateOrProvince").equals(null)? null : result.get("memberStateOrProvince").toString().equals(rulesData.get("state").toString()))) {
			softAssert.fail("Expected value of State:"+rulesData.get("state").toString()+" but state value after put Api:"+result.get("memberStateOrProvince").toString());
		}
		if(!(result.get("memberCounty").equals(null)? null : result.get("memberCounty").toString().equals(rulesData.get("county").toString()))) {
			softAssert.fail("Expected value of county:"+rulesData.get("county").toString()+" but county value after put Api:"+result.get("memberCounty").toString());
		}
		if(!(Objects.equals(result.get("memberCity").toString(),rulesData.get("city").toString()))) {
			softAssert.fail("Expected value of city:"+rulesData.get("city").toString()+" but city value after put Api:"+result.get("memberCity").toString());
		}
		softAssert.assertAll();
	}

	public static void cityNullCountyNotNullStateNullBright(RequestSpecification httpRequest,String docName,String response,JSONObject rulesData) {
	    SoftAssert softAssert = new SoftAssert();
		
		ObjectLayerPutAPIPage.verifySuccessPutCallAPIObjectLayer(httpRequest,response,docName,"member","parameterTrue");
		String getResponseAfterPut = ObjectLayerPage.getCallForObjectyLayer(httpRequest,"Bright","member",docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject getResponseAfterPutObj = new JSONObject(getResponseAfterPut);
		JSONObject result = getResponseAfterPutObj.getJSONObject("content").getJSONObject("lmsObject");
		
		String city = null;
		boolean cityExist=true;
		cityExist=OfficeStaticRulePage.isCityExist(city,rulesData.get("county").toString());
		OfficeStaticRulePage.deleteCreatedCity(httpRequest,city,rulesData.get("county").toString(),cityExist);

		if(!(Objects.equals(result.get("memberStateOrProvince").toString(),rulesData.get("state").toString()))) {
			softAssert.fail("Expected value of State:"+ rulesData.get("state").toString() +" but state value after put Api:"+result.get("memberStateOrProvince").toString());
		}

		if(!((result.get("memberCounty").equals(null)? null : result.get("memberCounty").toString()).equals(rulesData.get("county").toString()))) {
			softAssert.fail("Expected value of county:"+ rulesData.get("county").toString() +" but county value after put Api:"+result.get("memberCounty").toString());
		}
		if(!(Objects.equals(result.get("memberCity").toString(),rulesData.get("city").toString()))) {
			softAssert.fail("Expected value of city:"+rulesData.get("city").toString()+" but city value after put Api:"+result.get("memberCity").toString());
		}
		softAssert.assertAll();
	}


	public static void cityNullCountyNullStateNotNullBright(RequestSpecification httpRequest,String docName,String response,JSONObject rulesData) {
		SoftAssert softAssert = new SoftAssert();
			
		ObjectLayerPutAPIPage.verifySuccessPutCallAPIObjectLayer(httpRequest,response,docName,"member","parameterTrue");
		String getResponseAfterPut = ObjectLayerPage.getCallForObjectyLayer(httpRequest,"Bright","member",docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject getResponseAfterPutObj = new JSONObject(getResponseAfterPut);
		JSONObject result = getResponseAfterPutObj.getJSONObject("content").getJSONObject("lmsObject");
		String city = null;
		String county = null;
		boolean cityExist=true;
		cityExist=OfficeStaticRulePage.isCityExist(city,county);
		OfficeStaticRulePage.deleteCreatedCity(httpRequest,city,county,cityExist);

		if(!(result.get("memberStateOrProvince").equals(null)? null : result.get("memberStateOrProvince").toString()).equals(rulesData.get("state").toString())) {
			softAssert.fail("Expected value of State:"+rulesData.get("state").toString()+" but state value after put Api:"+result.get("memberStateOrProvince").toString());
		}
		if(!(Objects.equals(result.get("memberCounty").toString(),rulesData.get("county").toString()))) {
			softAssert.fail("Expected value of county:"+rulesData.get("county").toString()+" but county value after put Api:"+result.get("memberCounty").toString());
		}
		if(!(Objects.equals(result.get("memberCity").toString(),rulesData.get("city").toString()))) {
			softAssert.fail("Expected value of city:"+rulesData.get("city").toString()+" but city value after put Api:"+result.get("memberCity").toString());
		}
		softAssert.assertAll();
	}

	public static void cityInValidCountyNullStateNotNullBright(RequestSpecification httpRequest,String docName, String response, JSONObject rulesData) {
		SoftAssert softAssert = new SoftAssert();
		
		ObjectLayerPutAPIPage.verifySuccessPutCallAPIObjectLayer(httpRequest,response,docName,"member","parameterTrue");
		String getResponseAfterPut = ObjectLayerPage.getCallForObjectyLayer(httpRequest,"Bright","member",docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject getResponseAfterPutObj = new JSONObject(getResponseAfterPut);
		JSONObject result = getResponseAfterPutObj.getJSONObject("content").getJSONObject("lmsObject");
		
		String county = null;
		boolean cityExist=true;
		cityExist=OfficeStaticRulePage.isCityExist(rulesData.get("city").toString(),county);
		OfficeStaticRulePage.deleteCreatedCity(httpRequest,rulesData.get("city").toString(),county,cityExist);

		String expectedCounty = null;

		try {

			JSONObject stateObject=ValidateAPIPage.getValueOfKeyInPicklist(pickListState, rulesData.get("state").toString(), "picklistItemId");
			String ExpectedCountyName="UNKNOWN-"+stateObject.getString("itemValue");
			logger.info(pickListCounty);
			expectedCounty =ValidateAPIPage.getParentKeyForItemId(pickListCounty, ExpectedCountyName, "itemValue");
			logger.info("Expected county is:"+expectedCounty);
		} catch (JSONException e) {

		} catch (ParseException e) {

		}

		if(!(result.get("memberStateOrProvince").equals(null)? null : result.get("memberStateOrProvince").toString().equals(rulesData.get("state").toString()))) {
			softAssert.fail("Expected value of State:"+ rulesData.get("state").toString() +" but state value after put Api:"+result.get("memberStateOrProvince").toString());
		}
		if(!(Objects.equals(result.get("memberCounty").toString(),expectedCounty.toString()))) {
			softAssert.fail("Expected value of county:"+expectedCounty +" but county value after put Api:"+result.get("memberCounty").toString());
		}
		if(!(result.getString("memberCity").equals(null)? null : result.getString("memberCity").equals(rulesData.get("city").toString()))) {
			softAssert.fail("Expected value of city:"+ rulesData.get("city").toString() +" but city value after put Api:"+result.get("memberCity").toString());
		}
		softAssert.assertAll();
	}


	/**

	 * A method to call get config api with picklist id and name

	 */

	public static String getConfigAPIWithPicklistIdAndName(String picklistName) {

		RequestSpecification requestSpecification;

		requestSpecification = CommonUtilityMethods.requestSpecForObjectLayer();

		RequestSpecification httpRequest = RestAssured.given();

		requestSpecification.basePath("config/bright/businessViews/brightMember/picklists/{picklistName}");

		requestSpecification.pathParam("picklistName",  picklistName);

		httpRequest.spec(requestSpecification);

		Response  response = httpRequest.get();

		return response.asString();

	}
	
//	public static void allNullValuesCheckNonBright(RequestSpecification httpRequest,String docName,String response,JSONObject rulesData) {
//	SoftAssert softAssert = new SoftAssert();
//	
//	ObjectLayerPutAPIPage.verifySuccessPutCallAPIObjectLayer(httpRequest,response,docName,"member","parameterTrue");
//	String getResponseAfterPut = ObjectLayerPage.getCallForObjectyLayer(httpRequest,"Bright","member",docName,ResponseCodes.SUCCESS).getBody().asString();
//	JSONObject getResponseAfterPutObj = new JSONObject(getResponseAfterPut);
//	JSONObject result = getResponseAfterPutObj.getJSONObject("content").getJSONObject("lmsObject");
//	
//	String city = null;
//	String county = null;
//	boolean cityExist=true;
//	cityExist=OfficeStaticRulePage.isCityExist(city,county);
//	OfficeStaticRulePage.deleteCreatedCity(httpRequest,city,county,cityExist);
//
//	String ExpectedState = null;
//	String expectedCity="OTHER";
//	String ExpectedCounty=null;
//
//	try {
//		ExpectedState=ValidateAPIPage.getParentKeyForItemId(pickListState, "OTHER", "displayValueLong");
//		ExpectedCounty=ValidateAPIPage.getParentKeyForItemId(pickListCounty, "OTHER", "displayValue");
//		logger.info("Expected State is:"+ExpectedState);
//	} catch (JSONException e) {
//		logger.info("Caught into JSON exception catch block : "+e.toString());
//	} catch (ParseException e) {
//		logger.info("Caught into Parse exception catch block : "+e.toString());
//	}
//
//	if(!(Objects.equals(result.get("memberStateOrProvince").toString(),ExpectedState.toString()))) {
//		softAssert.fail("Expected value of State:"+ExpectedState +" but state value after put Api:"+result.get("memberStateOrProvince").toString());
//	}
//	if(!(Objects.equals(result.get("memberCounty").toString(),ExpectedCounty.toString()))) {
//		softAssert.fail("Expected value of county:"+ExpectedCounty +" but county value after put Api:"+result.get("memberCounty").toString());
//	}
//	if(!(Objects.equals(result.get("memberCity").toString(),expectedCity.toString()))) {
//		softAssert.fail("Expected value of city:"+expectedCity +" but city value after put Api:"+result.get("memberCity").toString());
//	}
//	softAssert.assertAll();
//}
//
//
//public static void allValidValuesNonBright(RequestSpecification httpRequest,String docName, String response) throws NumberFormatException, JSONException, ParseException {
//
//	SoftAssert softAssert = new SoftAssert(); 
//	
//	String state="10000003020";
//	String city="Flushing";
//	String county="10000004953";
//	String StreetNumber="136";
//	String StreetName="37";
//    String UnitDesignation="APT#";
//    String StreetSuffix="DRIVE";
//    String StreetDirPrefix="EAST";
//    String StreetDirSuffix="WEST";
//    String UnitNumber="9A";     
//	String StreetException="-33";
//	String configResponse = getConfigAPIWithPicklistIdAndName("UNIT_DESIGNATIONS");
//	JSONObject items = new JSONObject(configResponse).getJSONObject("item");
//	JSONObject json = null;
//	try {
//		json = (JSONObject) items.get("pickListItems");
//	} catch (JSONException  e) {
//		logger.info("Caught into JSON exception catch block : "+e.toString());
//	}
//	String streetDireResponse = getConfigAPIWithPicklistIdAndName("STREET_DIRECTIONS");
//	JSONObject itemsStreetDir = new JSONObject(streetDireResponse).getJSONObject("item");
//	JSONObject jsonStreetDir = null;
//	try {
//		jsonStreetDir =  (JSONObject) itemsStreetDir.get("pickListItems");
//	} catch (JSONException e ) {
//		logger.info("Caught into JSON exception catch block : "+e.toString());
//	}
//	String streetTypesResponse = getConfigAPIWithPicklistIdAndName("STREET_TYPES");
//	JSONObject itemsStreetTypes = new JSONObject(streetTypesResponse).getJSONObject("item");
//	JSONObject jsonStreetTypes= null;
//	try {
//		jsonStreetTypes =(JSONObject) itemsStreetTypes.get("pickListItems");
//	} catch (JSONException  e) {
//		logger.info("Caught into JSON exception catch block : "+e.toString());
//	}
//
//	JSONObject finalObj=new JSONObject( memberDataJson);
//	JSONObject lmsobj = new JSONObject( memberDataJson).getJSONObject("lmsObject");
//	lmsobj.put("memberBoxNumber", JSONObject.NULL);
//	lmsobj.put("memberStateOrProvince",new BigInteger(state));
//	lmsobj.put("memberCity",city);
//	lmsobj.put("memberCounty",new BigInteger(county));
//	lmsobj.put("memberStreetDirPrefix", Long.parseLong(ValidateAPIPage.getParentKeyForItemId(jsonStreetDir,StreetDirPrefix,"itemValue")));
//	lmsobj.put("memberStreetDirSuffix", Long.parseLong(ValidateAPIPage.getParentKeyForItemId(jsonStreetDir,StreetDirSuffix,"itemValue")));
//	lmsobj.put("memberBoxNumber",JSONObject.NULL);
//	lmsobj.put("memberStreetNumber",new BigInteger(StreetNumber));
//	lmsobj.put("memberStreetName",StreetName);
//	lmsobj.put("memberUnitNumber",UnitNumber);
//	lmsobj.put("memberUnitDesignation", Long.parseLong(ValidateAPIPage.getParentKeyForItemId(json,UnitDesignation,"itemValue")));
//	lmsobj.put("memberStreetSuffix", Long.parseLong(ValidateAPIPage.getParentKeyForItemId(jsonStreetTypes,StreetSuffix,"itemValue")));
//	lmsobj.put("memberSourceBusinessPartner",new BigInteger(Constants.SourceBusinessPartnerNONBright));
//	lmsobj.put("memberStreetException",StreetException);
//	JSONObject responseBodyObj = new JSONObject();
//	finalObj.put("lmsObject", lmsobj);
//	responseBodyObj.put("content", finalObj);
//	JSONObject metadata = new JSONObject(response).getJSONObject("metadata");
//	responseBodyObj.put("metadata", metadata);
//	String payload = responseBodyObj.toString();
//	boolean cityExist=OfficeStaticRulePage.isCityExist(city,county);
//	
//	ObjectLayerPutAPIPage.verifySuccessPutCallAPIObjectLayer(httpRequest,payload,docName,"member","parameterTrue");
//    String getResponseAfterPut = ObjectLayerPage.getCallForObjectyLayer(httpRequest,"Bright","member",docName,ResponseCodes.SUCCESS).getBody().asString();
//    JSONObject getResponseAfterPutObj = new JSONObject(getResponseAfterPut);
//    JSONObject result = getResponseAfterPutObj.getJSONObject("content").getJSONObject("lmsObject");
//
//	OfficeStaticRulePage.deleteCreatedCity(httpRequest,city,county,cityExist);
//
//
//	if(!(Objects.equals(result.get("memberStateOrProvince").toString(),state.toString()))) {
//		softAssert.fail("Expected value of State:"+state +" but state value after put Api:"+result.get("memberStateOrProvince").toString());
//	}
//
//	if(!(Objects.equals(result.get("memberCounty").toString(),county.toString()))) {
//		softAssert.fail("Expected value of county:"+county +" but county value after put Api:"+result.get("memberCounty").toString());
//	}
//
//	if(!(Objects.equals(result.get("memberCity").toString(),city.toString()))) {
//		softAssert.fail("Expected value of city:"+city +" but city value after put Api:"+result.get("memberCity").toString());
//	}
//
//	String ExpectedPrefix=ValidateAPIPage.getValueOfKeyInPicklist(jsonStreetDir,StreetDirPrefix,"itemValue").getString("displayValue");
//
//	String ExpectedSuffix=ValidateAPIPage.getValueOfKeyInPicklist(jsonStreetDir,StreetDirSuffix,"itemValue").getString("displayValue");
//
//	String designation=ValidateAPIPage.getValueOfKeyInPicklist(json,UnitDesignation,"itemValue").getString("displayValue");
//
//	String streetSuffix=ValidateAPIPage.getValueOfKeyInPicklist(jsonStreetTypes,StreetSuffix,"itemValue").getString("itemCode");
//
//	String expectedAddress=StreetNumber+StreetException+ " "+ExpectedPrefix+" "+StreetName+" "+streetSuffix+" "+ExpectedSuffix+" "+designation+" "+UnitNumber;
//	
//	if(!expectedAddress.equals(result.getString("memberAddress1"))) {
//		softAssert.fail("Expected value of address1:"+expectedAddress +" but address1 value after put Api:"+result.getString("memberAddress1").toString());
//	}
//	softAssert.assertAll();
//}	
	
//	public static void cityNotNullCountyNullStateNotNullBright(RequestSpecification httpRequest,String docName,String response,JSONObject rulesData) {
//
//		SoftAssert softAssert = new SoftAssert();
//
//		String state="10000003008";
//
//		String city="Rockville";
//
//		String county=null;
//
//		JSONObject result=triggerValidAPI(httpRequest,city,county,state,Constants.SourceBusinessPartnerBright);
//
//		logger.info("result is:"+result.toString());
//
//	}
	
//	public static void cityNullCountyNotNullStateNotNullNonBright(RequestSpecification httpRequest,String docName,String response,JSONObject rulesData) {
//	SoftAssert softAssert = new SoftAssert();	
//	
//	ObjectLayerPutAPIPage.verifySuccessPutCallAPIObjectLayer(httpRequest,response,docName,"member","parameterTrue");
//    String getResponseAfterPut = ObjectLayerPage.getCallForObjectyLayer(httpRequest,"Bright","member",docName,ResponseCodes.SUCCESS).getBody().asString();
//    JSONObject getResponseAfterPutObj = new JSONObject(getResponseAfterPut);
//    JSONObject result = getResponseAfterPutObj.getJSONObject("content").getJSONObject("lmsObject");
//    String city = null;
//    boolean cityExist=true;
//	cityExist=OfficeStaticRulePage.isCityExist(city,rulesData.get("county").toString());
//	OfficeStaticRulePage.deleteCreatedCity(httpRequest,city,rulesData.get("county").toString(),cityExist);
//
//	String expectedCity="OTHER";
//
//	logger.info(result.get("memberStateOrProvince").toString().equals(rulesData.get("state").toString()));
//
//	if(!(result.get("memberStateOrProvince").equals(null)? null : result.get("memberStateOrProvince").toString().equals(rulesData.get("state").toString()))) {
//		softAssert.fail("Expected value of State:"+rulesData.get("state").toString()+" but state value after put Api:"+result.get("memberStateOrProvince").toString());
//	}
//	if(!(result.get("memberCounty").equals(null)? null : result.get("memberCounty").toString().equals(rulesData.get("county").toString()))) {
// 		softAssert.fail("Expected value of county:"+rulesData.get("county").toString()+" but county value after put Api:"+result.get("memberCounty").toString());
//	}
//	if(!(Objects.equals(result.get("memberCity").toString(),expectedCity.toString()))) {
//		softAssert.fail("Expected value of city:"+expectedCity +" but city value after put Api:"+result.get("memberCity").toString());
//	}
//	softAssert.assertAll();
//}
//
//
//public static void cityInvalidCountyNotNullStateNotNullNonBright(RequestSpecification httpRequest,String docName,String response,JSONObject rulesData) {
//    SoftAssert softAssert = new SoftAssert();
//	
//	ObjectLayerPutAPIPage.verifySuccessPutCallAPIObjectLayer(httpRequest,response,docName,"member","parameterTrue");
//    String getResponseAfterPut = ObjectLayerPage.getCallForObjectyLayer(httpRequest,"Bright","member",docName,ResponseCodes.SUCCESS).getBody().asString();
//    JSONObject getResponseAfterPutObj = new JSONObject(getResponseAfterPut);
//    JSONObject result = getResponseAfterPutObj.getJSONObject("content").getJSONObject("lmsObject");
//    
//    boolean cityExist=true;
//	cityExist=OfficeStaticRulePage.isCityExist(rulesData.get("city").toString(),rulesData.get("county").toString());
//	OfficeStaticRulePage.deleteCreatedCity(httpRequest,rulesData.get("city").toString(),rulesData.get("county").toString(),cityExist);
//
//    if(!(result.get("memberStateOrProvince").equals(null)? null : result.get("memberStateOrProvince").toString().equals(rulesData.get("state").toString()))) {
//		softAssert.fail("Expected value of State:"+rulesData.get("state").toString() +" but state value after put Api:"+result.get("memberStateOrProvince").toString());
//	}
//	if(!(result.get("memberCounty").equals(null)? null : result.get("memberCounty").toString().equals(rulesData.get("county").toString()))) {
//		softAssert.fail("Expected value of county:"+rulesData.get("county").toString()+" but county value after put Api:"+result.get("memberCounty").toString());
//	}
//	if(!(result.getString("memberCity").equals(null)? null : result.getString("memberCity").equals(rulesData.get("city").toString()))) {
//		softAssert.fail("Expected value of city:"+rulesData.get("city").toString()+" but city value after put Api:"+result.get("memberCity").toString());
//	}
//	softAssert.assertAll();
//}
//
//
//public static void cityNotNullCountyNotNullStateNullNonBright(RequestSpecification httpRequest,String docName,String response,JSONObject rulesData) {
//	SoftAssert softAssert = new SoftAssert();
//	
//	ObjectLayerPutAPIPage.verifySuccessPutCallAPIObjectLayer(httpRequest,response,docName,"member","parameterTrue");
//	String getResponseAfterPut = ObjectLayerPage.getCallForObjectyLayer(httpRequest,"Bright","member",docName,ResponseCodes.SUCCESS).getBody().asString();
//	JSONObject getResponseAfterPutObj = new JSONObject(getResponseAfterPut);
//	JSONObject result = getResponseAfterPutObj.getJSONObject("content").getJSONObject("lmsObject");
//	
//	boolean cityExist=true;
//	cityExist=OfficeStaticRulePage.isCityExist(rulesData.get("city").toString(),rulesData.get("county").toString());
//	OfficeStaticRulePage.deleteCreatedCity(httpRequest,rulesData.get("city").toString(),rulesData.get("county").toString(),cityExist);
//	
//	String ExpectedState = null;
//
//	try {
//		ExpectedState=ValidateAPIPage.getParentKeyForItemId(pickListState, "OTHER", "displayValueLong");
//		logger.info("Expected State is:"+ExpectedState);
//	} catch (JSONException e) {
//		logger.info("Caught into JSON exception catch block : "+e.toString());
//	} catch (ParseException e) {
//		logger.info("Caught into Parse exception catch block : "+e.toString());
//	}
//	logger.info("Actual is:"+result.get("memberStateOrProvince"));
//
//	if(!(Objects.equals(result.get("memberStateOrProvince").toString(),ExpectedState.toString()))) {
//		softAssert.fail("Expected value of State:"+ExpectedState +" but state value After put Api:"+result.get("memberStateOrProvince").toString());
//	}
//	if(!(result.get("memberCounty").equals(null)? null : result.get("memberCounty").toString().equals(rulesData.get("county").toString()))) {
//		softAssert.fail("Expected value of county:"+rulesData.get("county").toString()+" but county value After put Api:"+result.get("memberCounty").toString());
//	}
//	if(!(result.getString("memberCity").equals(null)? null : result.getString("memberCity").equals(rulesData.get("city").toString()))) {
//  		softAssert.fail("Expected value of city:"+rulesData.get("city").toString()+" but city value  After put Api:"+result.get("memberCity").toString());
//	}
//	softAssert.assertAll();
//
//}
//
//
//public static void cityNotNullCountyNullStateNotNullNonBright(RequestSpecification httpRequest,String docName,String response,JSONObject rulesData) {
//	SoftAssert softAssert = new SoftAssert();
//	
//	ObjectLayerPutAPIPage.verifySuccessPutCallAPIObjectLayer(httpRequest,response,docName,"member","parameterTrue");
//	String getResponseAfterPut = ObjectLayerPage.getCallForObjectyLayer(httpRequest,"Bright","member",docName,ResponseCodes.SUCCESS).getBody().asString();
//	JSONObject getResponseAfterPutObj = new JSONObject(getResponseAfterPut);
//	JSONObject result = getResponseAfterPutObj.getJSONObject("content").getJSONObject("lmsObject");
//	
//	String county = null;
//	boolean cityExist=true;
//	cityExist=OfficeStaticRulePage.isCityExist(rulesData.get("city").toString(),county);
//	OfficeStaticRulePage.deleteCreatedCity(httpRequest,rulesData.get("city").toString(),county,cityExist);
//
//	String expectedCounty = null;
//
//	try {
//		JSONObject stateObject=ValidateAPIPage.getValueOfKeyInPicklist(pickListState, rulesData.get("state").toString(), "picklistItemId");
//		JSONObject otherStateFound=ValidateAPIPage.getValueOfKeyInPicklist(pickListState, "Other-"+rulesData.get("state").toString(), "picklistItemId");
//		if(otherStateFound!=null) {
//			String ExpectedCountyName="OTHER-"+stateObject.getString("itemValue");
//			expectedCounty =ValidateAPIPage.getParentKeyForItemId(pickListCounty, ExpectedCountyName, "itemValue");
//		}
//		else {
//			String ExpectedCountyName="OTHER-OT";
//			expectedCounty =ValidateAPIPage.getParentKeyForItemId(pickListCounty, ExpectedCountyName, "itemValue");
//		}
//	} catch (JSONException e) {
//		logger.info("Caught into JSON exception catch block : "+e.toString());
//	} catch (ParseException e) {
//		logger.info("Caught into Parse exception catch block : "+e.toString());
//	}
//
//	if(!(result.get("memberStateOrProvince").equals(null)? null : result.get("memberStateOrProvince").toString().equals(rulesData.get("state").toString()))) {
//		softAssert.fail("Expected value of State:"+rulesData.get("state").toString()+" but state value after put Api:"+result.get("memberStateOrProvince").toString());
//	}
//	if(!(Objects.equals(result.get("memberCounty").toString(),expectedCounty.toString()))) {
//		softAssert.fail("Expected value of county:"+expectedCounty +" but county value after put Api:"+result.get("memberCounty").toString());
//	}
//	if(!(result.getString("memberCity").equals(null)? null : result.getString("memberCity").equals(rulesData.get("city").toString()))) {
// 		softAssert.fail("Expected value of city:"+rulesData.get("city").toString()+" but city value after put Api:"+result.get("memberCity").toString());
//	}
//	softAssert.assertAll();
//}
}
