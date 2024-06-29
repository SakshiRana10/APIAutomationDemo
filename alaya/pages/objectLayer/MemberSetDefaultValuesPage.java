package bright.api.alaya.pages.objectLayer;



import java.math.BigInteger;

import java.util.ArrayList;

import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;

import org.json.JSONObject;

import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;



import bright.api.alaya.utils.CommonUtilityMethods;

import bright.api.alaya.utils.Constants;

import bright.api.alaya.utils.MainClassAlaya;

import io.restassured.RestAssured;

import io.restassured.response.Response;

import io.restassured.specification.RequestSpecification;



public class MemberSetDefaultValuesPage  extends MainClassAlaya{


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
	

	public MemberSetDefaultValuesPage(){
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


	

	public static void cityNullCountyNotNullStateNotNullNonBright(RequestSpecification httpRequest) {



		String state="10000003008";
		String city=null;
		String county="10000004181";
		JSONObject result=triggerValidAPI(httpRequest,city,county,state,Constants.SourceBusinessPartnerNONBright);

		SoftAssert softAssert = new SoftAssert();

		String expectedCity="OTHER";

		logger.info(result.get("memberStateOrProvince").toString().equals(state));

		if(!(result.get("memberStateOrProvince").equals(null)? null : result.get("memberStateOrProvince").toString().equals(state))) {

			softAssert.fail("Expected vaue of State:"+state +" but state value from validate Api:"+result.get("memberStateOrProvince").toString());

		}
		if(!(result.get("memberCounty").equals(null)? null : result.get("memberCounty").toString().equals(county))) {

			softAssert.fail("Expected vaue of county:"+county +" but county value from validate Api:"+result.get("memberCounty").toString());

		}
		if(!(result.getString("memberCity").equals(null)? null : result.getString("memberCity").equals(expectedCity))) {

			softAssert.fail("Expected vaue of city:"+expectedCity +" but city value from validate Api:"+result.get("memberCity").toString());

		}
		softAssert.assertAll();

	}


	public static void cityInvalidCountyNotNullStateNotNullNonBright(RequestSpecification httpRequest) {


//recheck
		String state="10000003008";
		String city="woodstock";
		String county="10000004181";
		
		JSONObject result=triggerValidAPI(httpRequest,city,county,state,Constants.SourceBusinessPartnerNONBright);

		SoftAssert softAssert = new SoftAssert();

if(!(result.get("memberStateOrProvince").equals(null)? null : result.get("memberStateOrProvince").toString().equals(state))) {

			softAssert.fail("Expected vaue of State:"+state +" but state value from validate Api:"+result.get("memberStateOrProvince").toString());

		}

		if(!(result.get("memberCounty").equals(null)? null : result.get("memberCounty").toString().equals(county))) {

			softAssert.fail("Expected vaue of county:"+county +" but county value from validate Api:"+result.get("memberCounty").toString());

		}


		if(!(result.getString("memberCity").equals(null)? null : result.getString("memberCity").equals(city))) {

			softAssert.fail("Expected vaue of city:"+city +" but city value from validate Api:"+result.get("memberCity").toString());

		}

		softAssert.assertAll();

	}


	public static void cityNotNullCountyNotNullStateNullNonBright(RequestSpecification httpRequest) {



		String state=null;
		String city="ROCKVILLE";
		String county="10000004181";
		JSONObject result=triggerValidAPI(httpRequest,city,county,state,Constants.SourceBusinessPartnerNONBright);
		String ExpectedState = null;
		SoftAssert softAssert = new SoftAssert();

		try {

			ExpectedState=ValidateAPIPage.getParentKeyForItemId(pickListState, "OTHER", "displayValueLong");
			logger.info("Expected State is:"+ExpectedState);

		} catch (JSONException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		} catch (ParseException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		logger.info("ACtual is:"+result.get("memberStateOrProvince"));

		if(!(result.get("memberStateOrProvince").equals(null)? null : result.get("memberStateOrProvince").toString().equals(ExpectedState))) {

			softAssert.fail("Expected vaue of State:"+ExpectedState +" but state value from validate Api:"+result.get("memberStateOrProvince").toString());

		}


		if(!(result.get("memberCounty").equals(null)? null : result.get("memberCounty").toString().equals(county))) {

			softAssert.fail("Expected vaue of county:"+county +" but county value from validate Api:"+result.get("memberCounty").toString());

		}


		if(!(result.getString("memberCity").equals(null)? null : result.getString("memberCity").equals(city))) {

			softAssert.fail("Expected vaue of city:"+city +" but city value from validate Api:"+result.get("memberCity").toString());

		}

		softAssert.assertAll();

	}


	public static void cityNotNullCountyNullStateNotNullNonBright(RequestSpecification httpRequest) {
//
//
//
//		String state="10000003008";
//
//		String city="ROCKVILLE";
//
//		String county=null;
//
//		JSONObject result=triggerValidAPI(httpRequest,city,county,state,Constants.SourceBusinessPartnerNONBright);
//
//		String expectedCounty = null;
//
//
//		
//
//		SoftAssert softAssert = new SoftAssert();
//
//		try {
//
//			JSONObject stateObject=ValidateAPIPage.getValueOfKeyInPicklist(pickListState, state, "picklistItemId");
//
//			JSONObject otherStateFound=ValidateAPIPage.getValueOfKeyInPicklist(pickListState, "Other-"+state, "picklistItemId");
//
//			if(otherStateFound!=null) {
//
//				String ExpectedCountyName="OTHER-"+stateObject.getString("itemValue");
//
//				expectedCounty =ValidateAPIPage.getParentKeyForItemId(pickListCounty, ExpectedCountyName, "itemValue");
//
//			}
//
//			else {
//
//				String ExpectedCountyName="OTHER-OT";
//
//				expectedCounty =ValidateAPIPage.getParentKeyForItemId(pickListCounty, ExpectedCountyName, "itemValue");
//
//			}
//
//		} catch (JSONException e) {
//
//			// TODO Auto-generated catch block
//
//			e.printStackTrace();
//
//		} catch (ParseException e) {
//
//			// TODO Auto-generated catch block
//
//			e.printStackTrace();
//
//		}
//
//		if(!(result.get("memberStateOrProvince").equals(null)? null : result.get("memberStateOrProvince").toString().equals(state))) {
//
//			softAssert.fail("Expected vaue of State:"+state +" but state value from validate Api:"+result.get("memberStateOrProvince").toString());
//
//		}
//		if(!(result.get("memberCounty").equals(null)? null : result.get("memberCounty").toString().equals(expectedCounty))) {
//
//			softAssert.fail("Expected vaue of county:"+expectedCounty +" but county value from validate Api:"+result.get("memberCounty").toString());
//
//		}
//
//		if(!(result.getString("memberCity").equals(null)? null : result.getString("memberCity").equals(city))) {
//
//			softAssert.fail("Expected vaue of city:"+city +" but city value from validate Api:"+result.get("memberCity").toString());
//
//		}
//
//		softAssert.assertAll();
//
	}  //commented due to case that county can not be null and also this is a non bright case

	public static void allNullValuesCheckBright(RequestSpecification httpRequest) {


		String state=null;

		String city=null;

		String county=null;

		JSONObject result=triggerValidAPI(httpRequest,city,county,state,Constants.SourceBusinessPartnerBright);


		SoftAssert softAssert = new SoftAssert();

		if(!((result.get("memberStateOrProvince").equals(null)? null : result.get("memberStateOrProvince").toString())==(state))) {

			softAssert.fail("Expected vaue of State:"+state +" but state value from validate Api:"+result.get("memberStateOrProvince").toString());

		}


		if(!((result.get("memberCounty").equals(null)? null : result.get("memberCounty").toString())==(county))) {

			softAssert.fail("Expected vaue of county:"+county +" but county value from validate Api:"+result.get("memberCounty").toString());

		}

		if(!((result.get("memberCity").equals(null)? null : result.get("memberCity"))==(city))) {

			softAssert.fail("Expected vaue of city:"+city +" but city value from validate Api:"+result.get("memberCity").toString());

		}

		softAssert.assertAll();

	}


	public static void allValidValuesBright(RequestSpecification httpRequest) {



		String state="10000003008";

		String city="ROCKVILLE";

		String county="10000004181";

		SoftAssert softAssert = new SoftAssert();

		JSONObject result=triggerValidAPI(httpRequest,city,county,state,Constants.SourceBusinessPartnerBright);



		if(!(result.get("memberStateOrProvince").equals(null)? null : result.get("memberStateOrProvince").toString().equals(state))) {

			softAssert.fail("Expected vaue of State:"+state +" but state value from validate Api:"+result.get("memberStateOrProvince").toString());

		}


		if(!(result.get("memberCounty").equals(null)? null : result.get("memberCounty").toString().equals(county))) {

			softAssert.fail("Expected vaue of county:"+county +" but county value from validate Api:"+result.get("memberCounty").toString());

		}


		if(!(result.getString("memberCity").equals(null)? null : result.getString("memberCity").equals(city))) {

			softAssert.fail("Expected vaue of city:"+city +" but city value from validate Api:"+result.get("memberCity").toString());

		}

		softAssert.assertAll();

	}


	public static void cityNotNullCountyNotNullStateNullBright(RequestSpecification httpRequest) {



		String state=null;

		String city="ROCKVILLE";

		String county="10000004181";

		SoftAssert softAssert = new SoftAssert();

		JSONObject result=triggerValidAPI(httpRequest,city,county,state,Constants.SourceBusinessPartnerBright);

		if(!(result.get("memberStateOrProvince").equals(null)? null : result.get("memberStateOrProvince").toString().equals(state))) {

			softAssert.fail("Expected vaue of State:"+state +" but state value from validate Api:"+result.get("memberStateOrProvince").toString());

		}

		if(!(result.get("memberCounty").equals(null)? null : result.get("memberCounty").toString().equals(county))) {

			softAssert.fail("Expected vaue of county:"+county +" but county value from validate Api:"+result.get("memberCounty").toString());

		}

		if(!(result.getString("memberCity").equals(null)? null : result.getString("memberCity").equals(city))) {

			softAssert.fail("Expected vaue of city:"+city +" but city value from validate Api:"+result.get("memberCity").toString());

		}

		softAssert.assertAll();

	}


	public static void cityNotNullCountyNullStateNullBright(RequestSpecification httpRequest) {



		String state=null;

		String city="ROCKVILLE";

		String county=null;

		JSONObject result=triggerValidAPI(httpRequest,city,county,state,Constants.SourceBusinessPartnerBright);

		SoftAssert softAssert = new SoftAssert();



		if(!((result.get("memberStateOrProvince").equals(null)? null : result.get("memberStateOrProvince").toString())==(state))) {

			softAssert.fail("Expected vaue of State:"+state +" but state value from validate Api:"+result.get("memberStateOrProvince").toString());

		}

		else

		{

			logger.info("Expected vaue of State:"+state +" and  state value from validate Api:"+result.get("memberStateOrProvince").toString());

		}


		if(!((result.get("memberCounty").equals(null)? null : result.get("memberCounty").toString())==(state))) {

			softAssert.fail("Expected vaue of county:"+county +" but county value from validate Api:"+result.get("memberCounty").toString());

		}

		if(!((result.get("memberCity").equals(null)? null : result.get("memberCity").toString())==(state))) {

			softAssert.fail("Expected vaue of city:"+city +" but city value from validate Api:"+result.get("memberCity").toString());

		}

		softAssert.assertAll();

	}



	public static void cityNotNullCountyNullStateNotNullBright(RequestSpecification httpRequest) {

		SoftAssert softAssert = new SoftAssert();

		String state="10000003008";

		String city="ROCKVILLE";

		String county=null;

		JSONObject result=triggerValidAPI(httpRequest,city,county,state,Constants.SourceBusinessPartnerBright);

		logger.info("result is:"+result.toString());

	}



	public static void citNullCountyNotNullStateNotNullBright(RequestSpecification httpRequest) {



		String state="10000003008";

		String county="10000004181";

		String city=null;

		JSONObject result=triggerValidAPI(httpRequest,city,county,state,Constants.SourceBusinessPartnerBright);

		SoftAssert softAssert = new SoftAssert();



		if(!(result.get("memberStateOrProvince").equals(null)? null : result.get("memberStateOrProvince").toString().equals(state))) {

			softAssert.fail("Expected vaue of State:"+state +" but state value from validate Api:"+result.get("memberStateOrProvince").toString());

		}


		if(!(result.get("memberCounty").equals(null)? null : result.get("memberCounty").toString().equals(county))) {

			softAssert.fail("Expected vaue of county:"+county +" but county value from validate Api:"+result.get("memberCounty").toString());

		}

		if(!((result.get("memberCity").equals(null)? null : result.get("memberCity").toString())==(city))) {

			softAssert.fail("Expected vaue of city:"+city +" but city value from validate Api:"+result.get("memberCity").toString());

		}

		softAssert.assertAll();
	}

	public static void cityNullCountyNotNullStateNullBright(RequestSpecification httpRequest) {



		String state=null;

		String city=null;

		String county="10000004181";

		SoftAssert softAssert = new SoftAssert();

		JSONObject result=triggerValidAPI(httpRequest,city,county,state,Constants.SourceBusinessPartnerBright);

		logger.info("result is:"+result.toString());


		if(!((result.get("memberStateOrProvince").equals(null)? null : result.get("memberStateOrProvince").toString())==(state))) {

			softAssert.fail("Expected vaue of State:"+state +" but state value from validate Api:"+result.get("memberStateOrProvince").toString());

		}

		if(!((result.get("memberCounty").equals(null)? null : result.get("memberCounty").toString()).equals(county))) {

			softAssert.fail("Expected vaue of county:"+county +" but county value from validate Api:"+result.get("memberCounty").toString());

		}

		if(!((result.get("memberCity").equals(null)? null : result.get("memberCity"))==(city))) {

			softAssert.fail("Expected vaue of city:"+city +" but city value from validate Api:"+result.get("memberCity").toString());

		}

		softAssert.assertAll();

	}


	public static void cityNullCountyNullStateNotNullBright(RequestSpecification httpRequest) {

		String state="10000003008";

		String city=null;

		String county=null;

		SoftAssert softAssert = new SoftAssert();

		JSONObject result=triggerValidAPI(httpRequest,city,county,state,Constants.SourceBusinessPartnerBright);

		logger.info("result is:"+result.toString());

		if(!((result.get("memberStateOrProvince").equals(null)? null : result.get("memberStateOrProvince").toString()).equals(state))) {

			softAssert.fail("Expected vaue of State:"+state +" but state value from validate Api:"+result.get("memberStateOrProvince").toString());

		}


		if(!((result.get("memberCounty").equals(null)? null : result.get("memberCounty").toString())==(county))) {

			softAssert.fail("Expected vaue of county:"+county +" but county value from validate Api:"+result.get("memberCounty").toString());

		}

		if(!((result.get("memberCity").equals(null)? null : result.getString("memberCity"))==(city))) {

			softAssert.fail("Expected vaue of city:"+city +" but city value from validate Api:"+result.get("memberCity").toString());

		}

		softAssert.assertAll();

	}

	public static void allNullValuesCheckNonBright(RequestSpecification httpRequest) {


		String state=null;
		String city=null;
		String county=null;
		JSONObject result=triggerValidAPI(httpRequest,city,state,county,Constants.SourceBusinessPartnerNONBright);

		String ExpectedState = null;

		String expectedCity="OTHER";

		String ExpectedCounty=null;

		SoftAssert softAssert = new SoftAssert();

		try {

			ExpectedState=ValidateAPIPage.getParentKeyForItemId(pickListState, "OTHER", "displayValueLong");

			ExpectedCounty=ValidateAPIPage.getParentKeyForItemId(pickListCounty, "OTHER", "displayValue");

			logger.info("Expected State is:"+ExpectedState);

		} catch (JSONException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		} catch (ParseException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		if(!((result.get("memberStateOrProvince").equals(null)? null : result.get("memberStateOrProvince").toString()).equals(ExpectedState))) {
			softAssert.fail("Expected vaue of State:"+ExpectedState +" but state value from validate Api:"+result.get("memberStateOrProvince").toString());
		}

		if(!((result.get("memberCounty").equals(null)? null : result.get("memberCounty").toString()).equals(ExpectedCounty))) {
			softAssert.fail("Expected vaue of county:"+ExpectedCounty +" but county value from validate Api:"+result.get("memberCounty").toString());
		}

		if(!((result.get("memberCity").equals(null)? null : result.get("memberCity")).equals(expectedCity))) {
			softAssert.fail("Expected vaue of city:"+expectedCity +" but city value from validate Api:"+result.get("memberCity").toString());
		}

		softAssert.assertAll();

	}


	public static void allValidValuesNonBright(RequestSpecification httpRequest) throws NumberFormatException, JSONException, ParseException {


		String state="10000003020";
		String city="Flushing";
		String county="10000004953";
		String StreetNumber="136";
		String StreetName="37";
        String UnitDesignation="APT#";
        String StreetSuffix="DRIVE";
        String StreetDirPrefix="EAST";
        String StreetDirSuffix="WEST";
        String UnitNumber="9A";
        
		String StreetException="-33";
		String configResponse = getConfigAPIWithPicklistIdAndName("UNIT_DESIGNATIONS");
		JSONObject items = new JSONObject(configResponse).getJSONObject("item");
		JSONObject json = null;
		try {

			json = (JSONObject) items.get("pickListItems");

		} catch (JSONException  e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}

		String streetDireResponse = getConfigAPIWithPicklistIdAndName("STREET_DIRECTIONS");
		JSONObject itemsStreetDir = new JSONObject(streetDireResponse).getJSONObject("item");
		JSONObject jsonStreetDir = null;
		try {

			jsonStreetDir =  (JSONObject) itemsStreetDir.get("pickListItems");

		} catch (JSONException e ) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		String streetTypesResponse = getConfigAPIWithPicklistIdAndName("STREET_TYPES");
		JSONObject itemsStreetTypes = new JSONObject(streetTypesResponse).getJSONObject("item");
		JSONObject jsonStreetTypes= null;

		try {

			jsonStreetTypes =(JSONObject) itemsStreetTypes.get("pickListItems");

		} catch (JSONException  e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		JSONObject finalObj=new JSONObject( memberDataJson);
		JSONObject lmsobj = new JSONObject( memberDataJson).getJSONObject("lmsObject");
		lmsobj.put("memberBoxNumber", JSONObject.NULL);
		lmsobj.put("memberStateOrProvince",new BigInteger(state));
		lmsobj.put("memberCity",city);
		lmsobj.put("memberCounty",new BigInteger(county));
		lmsobj.put("memberStreetDirPrefix", Long.parseLong(ValidateAPIPage.getParentKeyForItemId(jsonStreetDir,StreetDirPrefix,"itemValue")));
		lmsobj.put("memberStreetDirSuffix", Long.parseLong(ValidateAPIPage.getParentKeyForItemId(jsonStreetDir,StreetDirSuffix,"itemValue")));
		lmsobj.put("memberBoxNumber",JSONObject.NULL);
		lmsobj.put("memberStreetNumber",new BigInteger(StreetNumber));
		lmsobj.put("memberStreetName",StreetName);
		lmsobj.put("memberUnitNumber",UnitNumber);
		lmsobj.put("memberUnitDesignation", Long.parseLong(ValidateAPIPage.getParentKeyForItemId(json,UnitDesignation,"itemValue")));
		lmsobj.put("memberStreetSuffix", Long.parseLong(ValidateAPIPage.getParentKeyForItemId(jsonStreetTypes,StreetSuffix,"itemValue")));
		lmsobj.put("memberSourceBusinessPartner",new BigInteger(Constants.SourceBusinessPartnerNONBright));
		lmsobj.put("memberStreetException",StreetException);
		JSONObject responseBodyObj = new JSONObject();
		finalObj.put("lmsObject", lmsobj);
		responseBodyObj.put("content", finalObj);

		String payload = responseBodyObj.toString();

	
		boolean cityExist=OfficeStaticRulePage.isCityExist(city,county);

		String Json=ValidateAPIPage.postValidateAPI("bright", "member", payload,httpRequest);

		OfficeStaticRulePage.deleteCreatedCity(httpRequest,city,county,cityExist);
		
		

		JSONObject result = new JSONObject( Json).getJSONObject("resultantLmsObject");

		SoftAssert softAssert = new SoftAssert();

		if(!(result.get("memberStateOrProvince").equals(null)? null : result.get("memberStateOrProvince").toString().equals(state))) {

			softAssert.fail("Expected vaue of State:"+state +" but state value from validate Api:"+result.get("memberStateOrProvince").toString());

		}

		if(!(result.get("memberCounty").equals(null)? null : result.get("memberCounty").toString().equals(county))) {

			softAssert.fail("Expected vaue of county:"+county +" but county value from validate Api:"+result.get("memberCounty").toString());

		}


		if(!(result.getString("memberCity").equals(null)? null : result.getString("memberCity").equals(city))) {

			softAssert.fail("Expected vaue of city:"+city +" but city value from validate Api:"+result.get("memberCity").toString());

		}

		String ExpectedPrefix=ValidateAPIPage.getValueOfKeyInPicklist(jsonStreetDir,StreetDirPrefix,"itemValue").getString("displayValue");

		String ExpectedSuffix=ValidateAPIPage.getValueOfKeyInPicklist(jsonStreetDir,StreetDirSuffix,"itemValue").getString("displayValue");

		String designation=ValidateAPIPage.getValueOfKeyInPicklist(json,UnitDesignation,"itemValue").getString("displayValue");

		String streetSuffix=ValidateAPIPage.getValueOfKeyInPicklist(jsonStreetTypes,StreetSuffix,"itemValue").getString("itemCode");

		String expectedAddress=StreetNumber+StreetException+ " "+ExpectedPrefix+" "+StreetName+" "+streetSuffix+" "+ExpectedSuffix+" "+designation+" "+UnitNumber;

		
		if(!expectedAddress.equals(result.getString("memberAddress1"))) {

			softAssert.fail("Expected vaue of address1:"+expectedAddress +" but address1 value from validate Api:"+result.getString("memberAddress1").toString());

		}


		softAssert.assertAll();

	}

	public static void cityInValidCountyNullStateNotNullBright(RequestSpecification httpRequest) {



		String state="10000003008";

		String city="Philadelphia";

		String county=null;

		SoftAssert softAssert = new SoftAssert();

		JSONObject result=triggerValidAPI(httpRequest,city,county,state,Constants.SourceBusinessPartnerBright);

		String expectedCounty = null;

		

		try {

			JSONObject stateObject=ValidateAPIPage.getValueOfKeyInPicklist(pickListState, state, "picklistItemId");

			String ExpectedCountyName="UNKNOWN-"+stateObject.getString("itemValue");
			logger.info(pickListCounty);

			expectedCounty =ValidateAPIPage.getParentKeyForItemId(pickListCounty, ExpectedCountyName, "itemValue");
			logger.info("Expected county is:"+expectedCounty);

		} catch (JSONException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		} catch (ParseException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		if(!(result.get("memberStateOrProvince").equals(null)? null : result.get("memberStateOrProvince").toString().equals(state))) {

			softAssert.fail("Expected vaue of State:"+state +" but state value from validate Api:"+result.get("memberStateOrProvince").toString());

		}

		if(!(result.get("memberCounty").equals(null)? null : result.get("memberCounty").toString().equals(expectedCounty))) {

			softAssert.fail("Expected vaue of county:"+expectedCounty +" but county value from validate Api:"+result.get("memberCounty").toString());

		}

		if(!(result.getString("memberCity").equals(null)? null : result.getString("memberCity").equals(city))) {

			softAssert.fail("Expected vaue of city:"+city +" but city value from validate Api:"+result.get("memberCity").toString());

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


	public static JSONObject triggerValidAPI(RequestSpecification httpRequest,String city , String county, String state, String businessPartner) {

		JSONObject finalObj=new JSONObject( memberDataJson);

		JSONObject lmsobj = new JSONObject( memberDataJson).getJSONObject("lmsObject");

		if(state==null) 

			lmsobj.put("memberStateOrProvince",JSONObject.NULL);

		else

			lmsobj.put("memberStateOrProvince",new BigInteger(state));

		if(city==null) 

			lmsobj.put("memberCity",JSONObject.NULL);

		else

			lmsobj.put("memberCity",city);

		if(county==null) 

			lmsobj.put("memberCounty",JSONObject.NULL);

		else

			lmsobj.put("memberCounty",new BigInteger(county));


		lmsobj.put("memberSourceBusinessPartner",new BigInteger(businessPartner));

		JSONObject responseBodyObj = new JSONObject();

		finalObj.put("lmsObject", lmsobj);

		responseBodyObj.put("content", finalObj);

		String payload = responseBodyObj.toString();

		
		

		boolean cityExist=true;

		cityExist=OfficeStaticRulePage.isCityExist(city,county);

		String Json=ValidateAPIPage.postValidateAPI("bright", "member", payload,httpRequest);

		OfficeStaticRulePage.deleteCreatedCity(httpRequest,city,county,cityExist);
		
		JSONObject validateRes = new JSONObject( Json);
		JSONArray validationRes = validateRes.getJSONArray("ruleValidationResults");
		
		if(validationRes.length()>0) {
			Assert.assertTrue(false, "Please check the validation errors:"+validationRes);
		}
		
		JSONArray schemaValidationErrors = validateRes.getJSONArray("schemaValidationErrors");
		
		if(schemaValidationErrors.length()>0) {
			Assert.assertTrue(false, "Please check the schema Validation Errors :"+schemaValidationErrors);
		}
		
		JSONObject resultant = new JSONObject( Json).getJSONObject("resultantLmsObject");
		logger.info("result is:"+resultant);

		return resultant;

	}

}