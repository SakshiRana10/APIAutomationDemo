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

import com.beust.jcommander.Strings;

import bright.api.alaya.pages.discoveryLayer.discoveryLayerUtilityPage;
import bright.api.alaya.pages.discoveryLayer.discoveryLayerPostAPIPage;
import bright.api.alaya.pages.getConfig.getPicklistPage;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.EnvSpecificMethods;
import bright.api.alaya.utils.GraphQlQueries;
import bright.api.alaya.utils.MainClassAlaya;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class OfficeStaticRulePage extends MainClassAlaya{
	static String listingDataJson;
	static JSONObject pickListState;
	static JSONObject pickListCounty;



	public OfficeStaticRulePage(){
		RequestSpecification httpRequest = null;
		ArrayList<String> AttributeNamesForES = new ArrayList<String>(Arrays.asList("officeKey"));

		listingDataJson=CommonUtilityMethods.fetchDocumentfromDocStore(httpRequest,"office",officeDocumentNames.get(0));	
		String stateReponse=getConfigAPIWithPicklistIdAndName("STATES_OR_PROVINCES");
		JSONObject items = new JSONObject(stateReponse).getJSONObject("item");
		pickListState=(JSONObject) items.get("pickListItems");
		String countyRespinse=getConfigAPIWithPicklistIdAndName("COUNTIES_OR_REGIONS");
		JSONObject itemsCounty = new JSONObject(countyRespinse).getJSONObject("item");
		pickListCounty=(JSONObject) itemsCounty.get("pickListItems");

	}



	public static void allNullValuesCheckNonBright(RequestSpecification httpRequest) {


		String state=null;
		String city=null;
		String county=null;

		JSONObject result=passStateCountyCityBusinessPartner(httpRequest,city,state,county,Constants.SourceBusinessPartnerNONBright);
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


		if(!((result.get("officeStateOrProvince").equals(null)? null : result.get("officeStateOrProvince").toString()).equals(ExpectedState))) {
			softAssert.fail("Expected vaue of State:"+ExpectedState +" but state value from validate Api:"+result.get("officeStateOrProvince").toString());
		}


		if(!((result.get("officeCounty").equals(null)? null : result.get("officeCounty").toString()).equals(ExpectedCounty))) {
			softAssert.fail("Expected vaue of county:"+ExpectedCounty +" but county value from validate Api:"+result.get("officeCounty").toString());
		}


		if(!((result.get("officeCity").equals(null)? null : result.get("officeCity")).equals(expectedCity))) {
			softAssert.fail("Expected vaue of city:"+expectedCity +" but city value from validate Api:"+result.get("officeCity").toString());
		}

		softAssert.assertAll();
	}


	public static void allValidValuesNonBright(RequestSpecification httpRequest) throws NumberFormatException, JSONException, ParseException, InterruptedException {

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
		String latitude="40.7719317";
		String longitude="-73.7515318";
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


		JSONObject finalObj=new JSONObject( listingDataJson);
		JSONObject lmsobj = new JSONObject( listingDataJson).getJSONObject("lmsObject");

		lmsobj.put("officeBoxNumber", JSONObject.NULL);
		lmsobj.put("officeStateOrProvince",new BigInteger(state));

		lmsobj.put("officeCity",city);

		lmsobj.put("officeCounty",new BigInteger(county));

		lmsobj.put("officeStreetDirPrefix", Long.parseLong(ValidateAPIPage.getParentKeyForItemId(jsonStreetDir,StreetDirPrefix,"itemValue")));
		lmsobj.put("officeStreetDirSuffix", Long.parseLong(ValidateAPIPage.getParentKeyForItemId(jsonStreetDir,StreetDirSuffix,"itemValue")));

		lmsobj.put("officeBoxNumber",JSONObject.NULL);
		lmsobj.put("officeStreetNumber",new BigInteger(StreetNumber));
		lmsobj.put("officeStreetName",StreetName);
		lmsobj.put("officeUnitNumber",UnitNumber);
		lmsobj.put("officeUnitDesignation", Long.parseLong(ValidateAPIPage.getParentKeyForItemId(json,UnitDesignation,"itemValue")));
		lmsobj.put("officeStreetSuffix", Long.parseLong(ValidateAPIPage.getParentKeyForItemId(jsonStreetTypes,StreetSuffix,"itemValue")));
		lmsobj.put("officeStreetException",StreetException);
		lmsobj.put("officeSourceBusinessPartner",new BigInteger(Constants.SourceBusinessPartnerNONBright));
		JSONObject responseBodyObj = new JSONObject();
		finalObj.put("lmsObject", lmsobj);
		responseBodyObj.put("content", finalObj);
		String payload = responseBodyObj.toString();

		boolean cityExist=true;

		cityExist=isCityExist(city,county);

		String Json=ValidateAPIPage.postValidateAPI("bright", "office", payload,httpRequest);

		deleteCreatedCity(httpRequest,city,county,cityExist);

		JSONObject result = new JSONObject( Json).getJSONObject("resultantLmsObject");

		SoftAssert softAssert = new SoftAssert();

		if(!(result.get("officeStateOrProvince").equals(null)? null : result.get("officeStateOrProvince").toString().equals(state))) {
			softAssert.fail("Expected vaue of State:"+state +" but state value from validate Api:"+result.get("officeStateOrProvince").toString());
		}


		if(!(result.get("officeCounty").equals(null)? null : result.get("officeCounty").toString().equals(county))) {
			softAssert.fail("Expected vaue of county:"+county +" but county value from validate Api:"+result.get("officeCounty").toString());
		}


		if(!(result.getString("officeCity").equals(null)? null : result.getString("officeCity").equals(city))) {
			softAssert.fail("Expected vaue of city:"+city +" but city value from validate Api:"+result.get("officeCity").toString());
		}


		String ExpectedPrefix=ValidateAPIPage.getValueOfKeyInPicklist(jsonStreetDir,StreetDirPrefix,"itemValue").getString("displayValue");

		String ExpectedSuffix=ValidateAPIPage.getValueOfKeyInPicklist(jsonStreetDir,StreetDirSuffix,"itemValue").getString("displayValue");

		String designation=ValidateAPIPage.getValueOfKeyInPicklist(json,UnitDesignation,"itemValue").getString("displayValue");

		String streetSuffix=ValidateAPIPage.getValueOfKeyInPicklist(jsonStreetTypes,StreetSuffix,"itemValue").getString("itemCode");


		String expectedAddress=StreetNumber+StreetException+" "+ExpectedPrefix+" "+StreetName+" "+streetSuffix+" "+ExpectedSuffix+" "+designation+" "+UnitNumber;
		logger.info(expectedAddress);
		logger.info(result.getString("officeAddress1"));

		if(!expectedAddress.equals(result.getString("officeAddress1"))) {
			softAssert.fail("Expected vaue of address1:"+expectedAddress +" but address1 value from validate Api:"+result.getString("officeAddress1").toString());
		}



		if(!((result.get("officeLatitude").equals(null)? null : result.get("officeLatitude").toString()).equals(latitude))) {
			softAssert.fail("Expected vaue of officeLatitude:"+latitude +" but city value from validate Api:"+result.get("officeLatitude").toString());
		}

		if(!((result.get("officeLongitude").equals(null)? null : result.get("officeLongitude").toString()).equals(longitude))) {
			softAssert.fail("Expected vaue of officeLongitude:"+longitude +" but city value from validate Api:"+result.get("officeLongitude").toString());
		}
		softAssert.assertAll();

	}



	public static void cityNullCountyNotNullStateNotNullNonBright(RequestSpecification httpRequest) {

		String state="10000003008";
		String city=null;
		String county="10000004181";
		JSONObject result=passStateCountyCityBusinessPartner(httpRequest,city,county,state,Constants.SourceBusinessPartnerNONBright);
		SoftAssert softAssert = new SoftAssert();
		String expectedCity="OTHER";

		logger.info(result.get("officeStateOrProvince").toString().equals(state));

		if(!(result.get("officeStateOrProvince").equals(null)? null : result.get("officeStateOrProvince").toString().equals(state))) {
			softAssert.fail("Expected vaue of State:"+state +" but state value from validate Api:"+result.get("officeStateOrProvince").toString());
		}


		if(!(result.get("officeCounty").equals(null)? null : result.get("officeCounty").toString().equals(county))) {
			softAssert.fail("Expected vaue of county:"+county +" but county value from validate Api:"+result.get("officeCounty").toString());
		}



		if(!(result.getString("officeCity").equals(null)? null : result.getString("officeCity").equals(expectedCity.toUpperCase()))) {
			softAssert.fail("Expected vaue of city:"+expectedCity +" but city value from validate Api:"+result.get("officeCity").toString());
		}

		softAssert.assertAll();
	}


	public static void cityInvalidCountyNotNullStateNotNullNonBright(RequestSpecification httpRequest) {

		String state="10000003008";
		String city="woodstock";
		String county="10000004181";

		JSONObject result=passStateCountyCityBusinessPartner(httpRequest,city,county,state,Constants.SourceBusinessPartnerNONBright);
		SoftAssert softAssert = new SoftAssert();

		if(!(result.get("officeStateOrProvince").equals(null)? null : result.get("officeStateOrProvince").toString().equals(state))) {
			softAssert.fail("Expected vaue of State:"+state +" but state value from validate Api:"+result.get("officeStateOrProvince").toString());
		}


		if(!(result.get("officeCounty").equals(null)? null : result.get("officeCounty").toString().equals(county))) {
			softAssert.fail("Expected vaue of county:"+county +" but county value from validate Api:"+result.get("officeCounty").toString());
		}


		if(!(result.getString("officeCity").equals(null)? null : result.getString("officeCity").equals(city.toUpperCase()))) {
			softAssert.fail("Expected vaue of city:"+city +" but city value from validate Api:"+result.get("officeCity").toString());
		}
		softAssert.assertAll();
	}


	public static void cityNotNullCountyNotNullStateNullNonBright(RequestSpecification httpRequest) {

		String state=null;
		String city="Rockville";
		String county="10000004181";
		JSONObject result=passStateCountyCityBusinessPartner(httpRequest,city,county,state,Constants.SourceBusinessPartnerNONBright);
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
		logger.info("ACtual is:"+result.get("officeStateOrProvince"));
		if(!(result.get("officeStateOrProvince").equals(null)? null : result.get("officeStateOrProvince").toString().equals(ExpectedState))) {
			softAssert.fail("Expected vaue of State:"+ExpectedState +" but state value from validate Api:"+result.get("officeStateOrProvince").toString());
		}


		if(!(result.get("officeCounty").equals(null)? null : result.get("officeCounty").toString().equals(county))) {
			softAssert.fail("Expected vaue of county:"+county +" but county value from validate Api:"+result.get("officeCounty").toString());
		}


		if(!(result.getString("officeCity").equals(null)? null : result.getString("officeCity").equals(city.toUpperCase()))) {
			softAssert.fail("Expected vaue of city:"+city +" but city value from validate Api:"+result.get("officeCity").toString());
		}
		softAssert.assertAll();
	}


	public static void cityNotNullCountyNullStateNotNullNonBright(RequestSpecification httpRequest) {

		String state="10000003008";
		String city="Rockville";
		String county=null;
		JSONObject result=passStateCountyCityBusinessPartner(httpRequest,city,county,state,Constants.SourceBusinessPartnerNONBright);
		String expectedCounty = null;


		SoftAssert softAssert = new SoftAssert();
		try {
			JSONObject stateObject=ValidateAPIPage.getValueOfKeyInPicklist(pickListState, state, "picklistItemId");
			JSONObject otherStateFound=ValidateAPIPage.getValueOfKeyInPicklist(pickListState, "Other-"+state, "picklistItemId");
			if(otherStateFound!=null) {
				String ExpectedCountyName="OTHER-"+stateObject.getString("itemValue");
				expectedCounty =ValidateAPIPage.getParentKeyForItemId(pickListCounty, ExpectedCountyName, "itemValue");
			}

			else {
				String ExpectedCountyName="OTHER-OT";
				expectedCounty =ValidateAPIPage.getParentKeyForItemId(pickListCounty, ExpectedCountyName, "itemValue");
			}


		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!(result.get("officeStateOrProvince").equals(null)? null : result.get("officeStateOrProvince").toString().equals(state))) {
			softAssert.fail("Expected vaue of State:"+state +" but state value from validate Api:"+result.get("officeStateOrProvince").toString());
		}


		if(!(result.get("officeCounty").equals(null)? null : result.get("officeCounty").toString().equals(expectedCounty))) {
			softAssert.fail("Expected vaue of county:"+expectedCounty +" but county value from validate Api:"+result.get("officeCounty").toString());
		}


		if(!(result.getString("officeCity").equals(null)? null : result.getString("officeCity").equals(city.toUpperCase()))) {
			softAssert.fail("Expected vaue of city:"+city +" but city value from validate Api:"+result.get("officeCity").toString());
		}
		softAssert.assertAll();
	}



	public static void allNullValuesCheckBright(RequestSpecification httpRequest) {



		String state=null;
		String city=null;
		String county=null;
		JSONObject result=passStateCountyCityBusinessPartner(httpRequest,city,county,state,Constants.SourceBusinessPartnerBright);

		SoftAssert softAssert = new SoftAssert();
		if(!((result.get("officeStateOrProvince").equals(null)? null : result.get("officeStateOrProvince").toString())==(state))) {
			softAssert.fail("Expected vaue of State:"+state +" but state value from validate Api:"+result.get("officeStateOrProvince").toString());
		}


		if(!((result.get("officeCounty").equals(null)? null : result.get("officeCounty").toString())==(county))) {
			softAssert.fail("Expected vaue of county:"+county +" but county value from validate Api:"+result.get("officeCounty").toString());
		}


		if(!((result.get("officeCity").equals(null)? null : result.get("officeCity"))==(city))) {
			softAssert.fail("Expected vaue of city:"+city +" but city value from validate Api:"+result.get("officeCity").toString());
		}
		softAssert.assertAll();
	}




	public static void allValidValuesBright(RequestSpecification httpRequest) {

		String state="10000003008";
		String city="Rockville";
		String county="10000004181";
		SoftAssert softAssert = new SoftAssert();
		JSONObject result=passStateCountyCityBusinessPartner(httpRequest,city,county,state,Constants.SourceBusinessPartnerBright);

		if(!(result.get("officeStateOrProvince").equals(null)? null : result.get("officeStateOrProvince").toString().equals(state))) {
			softAssert.fail("Expected vaue of State:"+state +" but state value from validate Api:"+result.get("officeStateOrProvince").toString());
		}


		if(!(result.get("officeCounty").equals(null)? null : result.get("officeCounty").toString().equals(county))) {
			softAssert.fail("Expected vaue of county:"+county +" but county value from validate Api:"+result.get("officeCounty").toString());
		}


		if(!(result.getString("officeCity").equals(null)? null : result.getString("officeCity").equals(city.toUpperCase()))) {
			softAssert.fail("Expected vaue of city:"+city +" but city value from validate Api:"+result.get("officeCity").toString());
		}
		softAssert.assertAll();
	}



	public static void cityNotNullCountyNotNullStateNullBright(RequestSpecification httpRequest) {

		String state=null;
		String city="Rockville";
		String county="10000004181";
		SoftAssert softAssert = new SoftAssert();
		JSONObject result=passStateCountyCityBusinessPartner(httpRequest,city,county,state,Constants.SourceBusinessPartnerBright);
		String value=result.get("officeStateOrProvince").equals(null) ? result.get("officeStateOrProvince").toString(): "null";

		if(!(result.get("officeStateOrProvince").equals(null)? null : result.get("officeStateOrProvince").toString().equals(state))) {
			softAssert.fail("Expected vaue of State:"+state +" but state value from validate Api:"+result.get("officeStateOrProvince").toString());
		}


		if(!(result.get("officeCounty").equals(null)? null : result.get("officeCounty").toString().equals(county))) {
			softAssert.fail("Expected vaue of county:"+county +" but county value from validate Api:"+result.get("officeCounty").toString());
		}


		if(!(result.getString("officeCity").equals(null)? null : result.getString("officeCity").equals(city.toUpperCase()))) {
			softAssert.fail("Expected vaue of city:"+city +" but city value from validate Api:"+result.get("officeCity").toString());
		}
		softAssert.assertAll();


	}


	public static void cityNotNullCountyNullStateNullBright(RequestSpecification httpRequest) {

		String state=null;
		String city="ROCKVILLE";
		String county=null;
		JSONObject result=passStateCountyCityBusinessPartner(httpRequest,city,county,state,Constants.SourceBusinessPartnerBright);
		SoftAssert softAssert = new SoftAssert();



		if(!((result.get("officeStateOrProvince").equals(null)? null : result.get("officeStateOrProvince").toString())==(state))) {
			softAssert.fail("Expected vaue of State:"+state +" but state value from validate Api:"+result.get("officeStateOrProvince").toString());
		}
		else
		{
			logger.info("Expected vaue of State:"+state +" and  state value from validate Api:"+result.get("officeStateOrProvince").toString());
		}


		if(!((result.get("officeCounty").equals(null)? null : result.get("officeCounty").toString())==(state))) {
			softAssert.fail("Expected vaue of county:"+county +" but county value from validate Api:"+result.get("officeCounty").toString());
		}

 logger.info("test:"+result.get("officeCity"));
 if(!((result.get("officeCity").equals(null)? null : result.get("officeCity").toString())==(city))) {
			softAssert.fail("Expected vaue of city:"+city +" but city value from validate Api:"+result.get("officeCity").toString());
		}
		softAssert.assertAll();
	}



	public static void cityNotNullCountyNullStateNotNullBright(RequestSpecification httpRequest) {

		SoftAssert softAssert = new SoftAssert();
		String state="10000003008";
		String city="Rockville";
		String county=null;
		JSONObject result=passStateCountyCityBusinessPartner(httpRequest,city,county,state,Constants.SourceBusinessPartnerBright);
		logger.info("result is:"+result.toString());
		//salman se why city is getting changed
	}

	public static void citNullCountyNotNullStateNotNullBright(RequestSpecification httpRequest) {

		String state="10000003008";
		String county="10000004181";
		String city=null;
		JSONObject result=passStateCountyCityBusinessPartner(httpRequest,city,county,state,Constants.SourceBusinessPartnerBright);
		SoftAssert softAssert = new SoftAssert();

		if(!(result.get("officeStateOrProvince").equals(null)? null : result.get("officeStateOrProvince").toString().equals(state))) {
			softAssert.fail("Expected vaue of State:"+state +" but state value from validate Api:"+result.get("officeStateOrProvince").toString());
		}


		if(!(result.get("officeCounty").equals(null)? null : result.get("officeCounty").toString().equals(county))) {
			softAssert.fail("Expected vaue of county:"+county +" but county value from validate Api:"+result.get("officeCounty").toString());
		}


		if(!((result.get("officeCity").equals(null)? null : result.get("officeCity").toString())==(city))) {
			softAssert.fail("Expected vaue of city:"+city +" but city value from validate Api:"+result.get("officeCity").toString());
		}
		softAssert.assertAll();

	}
	public static void cityNullCountyNotNullStateNullBright(RequestSpecification httpRequest) {

		String state=null;
		String city=null;
		String county="10000004181";
		SoftAssert softAssert = new SoftAssert();
		JSONObject result=passStateCountyCityBusinessPartner(httpRequest,city,county,state,Constants.SourceBusinessPartnerBright);
		logger.info("result is:"+result.toString());

		if(!((result.get("officeStateOrProvince").equals(null)? null : result.get("officeStateOrProvince").toString())==(state))) {
			softAssert.fail("Expected vaue of State:"+state +" but state value from validate Api:"+result.get("officeStateOrProvince").toString());
		}


		if(!((result.get("officeCounty").equals(null)? null : result.get("officeCounty").toString()).equals(county))) {
			softAssert.fail("Expected vaue of county:"+county +" but county value from validate Api:"+result.get("officeCounty").toString());
		}


		if(!((result.get("officeCity").equals(null)? null : result.get("officeCity"))==(city))) {
			softAssert.fail("Expected vaue of city:"+city +" but city value from validate Api:"+result.get("officeCity").toString());
		}
		softAssert.assertAll();

	}


	public static void cityNullCountyNullStateNotNullBright(RequestSpecification httpRequest) {

		String state="10000003008";
		String city=null;
		String county=null;
		SoftAssert softAssert = new SoftAssert();
		JSONObject result=passStateCountyCityBusinessPartner(httpRequest,city,county,state,Constants.SourceBusinessPartnerBright);
		logger.info("result is:"+result.toString());

		if(!((result.get("officeStateOrProvince").equals(null)? null : result.get("officeStateOrProvince").toString()).equals(state))) {
			softAssert.fail("Expected vaue of State:"+state +" but state value from validate Api:"+result.get("officeStateOrProvince").toString());
		}


		if(!((result.get("officeCounty").equals(null)? null : result.get("officeCounty").toString())==(county))) {
			softAssert.fail("Expected vaue of county:"+county +" but county value from validate Api:"+result.get("officeCounty").toString());
		}


		if(!((result.get("officeCity").equals(null)? null : result.getString("officeCity"))==(city))) {
			softAssert.fail("Expected vaue of city:"+city +" but city value from validate Api:"+result.get("officeCity").toString());
		}
		softAssert.assertAll();
	}


	public static void cityInValidCountyNullStateNotNullBright(RequestSpecification httpRequest) {

		String state="10000003008";
		String city="Philadelphia";
		String county=null;
		SoftAssert softAssert = new SoftAssert();
		JSONObject result=passStateCountyCityBusinessPartner(httpRequest,city,county,state,Constants.SourceBusinessPartnerBright);
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
		if(!(result.get("officeStateOrProvince").equals(null)? null : result.get("officeStateOrProvince").toString().equals(state))) {
			softAssert.fail("Expected vaue of State:"+state +" but state value from validate Api:"+result.get("officeStateOrProvince").toString());
		}


		if(!(result.get("officeCounty").equals(null)? null : result.get("officeCounty").toString().equals(expectedCounty))) {
			softAssert.fail("Expected vaue of county:"+expectedCounty +" but county value from validate Api:"+result.get("officeCounty").toString());
		}


		if(!(result.getString("officeCity").equals(null)? null : result.getString("officeCity").equals(city.toUpperCase()))) {
			softAssert.fail("Expected vaue of city:"+city +" but city value from validate Api:"+result.get("officeCity").toString());
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
		requestSpecification.basePath("config/bright/businessViews/brightOffice/picklists/{picklistName}");
		requestSpecification.pathParam("picklistName",  picklistName);
		httpRequest.spec(requestSpecification);
		Response  response = httpRequest.get();
		return response.asString();
	}


	public static JSONObject passStateCountyCityBusinessPartner(RequestSpecification httpRequest,String city , String county, String state, String businessPartner) {



		JSONObject finalObj=new JSONObject( listingDataJson);
		JSONObject lmsobj = new JSONObject( listingDataJson).getJSONObject("lmsObject");
		city=city!=null ? city.toUpperCase():city;
		if(state==null) 
			lmsobj.put("officeStateOrProvince",JSONObject.NULL);

		else
			lmsobj.put("officeStateOrProvince",new BigInteger(state));

		if(city==null) 
			lmsobj.put("officeCity",JSONObject.NULL);

		else
			lmsobj.put("officeCity",city);

		if(county==null) 
			lmsobj.put("officeCounty",JSONObject.NULL);

		else
			lmsobj.put("officeCounty",new BigInteger(county));


		lmsobj.put("officeSourceBusinessPartner",new BigInteger(businessPartner));

		JSONObject responseBodyObj = new JSONObject();
		finalObj.put("lmsObject", lmsobj);
		responseBodyObj.put("content", finalObj);
		String payload = responseBodyObj.toString();
		boolean cityExist=true;

		cityExist=isCityExist(city,county);

		String Json=ValidateAPIPage.postValidateAPI("bright", "office", payload,httpRequest);
		JSONObject validateRes = new JSONObject( Json);
		JSONArray validationRes = validateRes.getJSONArray("ruleValidationResults");
		
		if(validationRes.length()>0) {
			Assert.assertTrue(false, "Please check the validation errors:"+validationRes);
		}
		
		JSONArray schemaValidationErrors = validateRes.getJSONArray("schemaValidationErrors");
		
		if(schemaValidationErrors.length()>0) {
			Assert.assertTrue(false, "Please check the schema Validation Errors :"+schemaValidationErrors);
		}
		
		deleteCreatedCity(httpRequest,city,county,cityExist);

		JSONObject resultant = new JSONObject( Json).getJSONObject("resultantLmsObject");


		return resultant;
	}


	public static boolean isCityExist(String city , String county) {

		city=city!=null ? city.toUpperCase():city;
		ArrayList<String> graphQlQuery = null;
		if(city!=null && county!=null) {
			graphQlQuery = CommonUtilityMethods.fetchCityKeyUsingCounty("city", city, county);

			if(graphQlQuery!=null) {
				logger.info("city exist ");


			}
			else {
				logger.info("creating new city doc");
				return false;
			}
		}

		return true;
	}


	public static void deleteCreatedCity(RequestSpecification httpRequest,String city , String county, boolean cityExist) {

		ArrayList<String> graphQlQueryAfterCityCreation = null;
		JSONObject responseBodyObj = new JSONObject();
		city=city!=null ? city.toUpperCase():city;
		if(city!=null && county!=null) {
			graphQlQueryAfterCityCreation = CommonUtilityMethods.fetchCityKeyUsingCounty("city", city, county);
			logger.info("Response of graphQL:"+graphQlQueryAfterCityCreation.toString());
			logger.info("after:"+graphQlQueryAfterCityCreation.size());
		}


		if(!cityExist ) {
			logger.info("New city doc got Triggered");

			JSONObject lmsObj= new JSONObject();
			String lastModified=null;
			String docType="city";
			String documentName=graphQlQueryAfterCityCreation.get(0);

			String payloadResponse = discoveryLayerUtilityPage.getCallForDiscoveryLayer(httpRequest,"Bright","true","true","city",documentName).getBody().asString();

			JSONObject result=new JSONObject(payloadResponse);
			JSONObject metaData = result.getJSONObject("metadata");
			lastModified=metaData.getString("lastModified");

			JSONObject payloadOBJ = new JSONObject(payloadResponse);
			JSONObject contentObj = new JSONObject(payloadResponse).getJSONObject("content");
			lmsObj = contentObj.getJSONObject("lmsObject");

			String docDeletionInfo = CommonUtilityMethods.readJsonFile(Constants.discoverylayerPath, "documentDeletionInfo")
					.toString();
			lmsObj.put("documentDeletionInformation", docDeletionInfo);
			contentObj.put("lmsObject", lmsObj);

			responseBodyObj.put("lastUpdatedBy", "AlayaAutomation");
			responseBodyObj.put("content", contentObj);
			payloadOBJ.put("content", contentObj);
			String payloadForPut = responseBodyObj.toString();
			Response responseFromPut = discoveryLayerUtilityPage.putCallForDiscoveryLayer(httpRequest, "true", "true", "true","Bright", docType, documentName, lastModified, payloadForPut);
			logger.info("Response from put - "+responseFromPut.getBody().asString());


			// Calling delete API of document store to complete the flow
			discoveryLayerPostAPIPage.deleteApiDocStore(httpRequest, docType, documentName);
		}

		else {
			logger.info("No new city created");
		}
	}
}

