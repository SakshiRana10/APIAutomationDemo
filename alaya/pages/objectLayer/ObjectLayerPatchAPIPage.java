package bright.api.alaya.pages.objectLayer;

import java.math.BigInteger;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.asserts.SoftAssert;

import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import io.restassured.specification.RequestSpecification;

public class ObjectLayerPatchAPIPage  extends MainClassAlaya{

	/*------------------------------------Member rules functions ------------------------------------*/
	
	public static void allNullValuesCheckBright(RequestSpecification httpRequest,String docName,String locale,String docType,JSONObject rulesData ) {
		SoftAssert softAssert = new SoftAssert();
		
		JSONObject cityOperation=ObjectLayerPage.createOperation("replace","memberCity",rulesData.get("city"));
		JSONObject stateOperation=ObjectLayerPage.createOperation("replace","memberStateOrProvince",rulesData.get("state"));
		JSONObject countyOperation=ObjectLayerPage.createOperation("replace","memberCounty",rulesData.get("county"));

		JSONArray combinedOperations=new JSONArray();
		combinedOperations.put(cityOperation);
		combinedOperations.put(stateOperation);
		combinedOperations.put(countyOperation);

		JSONObject payload=ObjectLayerPage.createPatchPayload(combinedOperations);

		ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"true","false",locale,docName,docType,ResponseCodes.SUCCESS,payload);
		String getResponseAfterPatch = ObjectLayerPage.getCallForObjectyLayer(httpRequest,"Bright","member",docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject getResponseAfterPatchObj = new JSONObject(getResponseAfterPatch);
		JSONObject result = getResponseAfterPatchObj.getJSONObject("content").getJSONObject("lmsObject");
		
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
	
	public static void allValidValuesBrightMember(RequestSpecification httpRequest,String docName,String locale,String docType,JSONObject rulesData ) {
		SoftAssert softAssert = new SoftAssert();
		
		JSONObject cityOperation=ObjectLayerPage.createOperation("replace","memberCity",rulesData.get("city"));
		JSONObject stateOperation=ObjectLayerPage.createOperation("replace","memberStateOrProvince",new BigInteger(rulesData.get("state").toString()));
		JSONObject countyOperation=ObjectLayerPage.createOperation("replace","memberCounty",new BigInteger(rulesData.get("county").toString()));

		JSONArray combinedOperations=new JSONArray();
		combinedOperations.put(cityOperation);
		combinedOperations.put(stateOperation);
		combinedOperations.put(countyOperation);

		JSONObject payload=ObjectLayerPage.createPatchPayload(combinedOperations);

		ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"true","false",locale,docName,docType,ResponseCodes.SUCCESS,payload);
		String getResponseAfterPatch = ObjectLayerPage.getCallForObjectyLayer(httpRequest,"Bright","member",docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject getResponseAfterPatchObj = new JSONObject(getResponseAfterPatch);
		JSONObject result = getResponseAfterPatchObj.getJSONObject("content").getJSONObject("lmsObject");
		
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
	
	public static void cityNotNullCountyNotNullStateNullBrightMember(RequestSpecification httpRequest,String docName,String locale,String docType,JSONObject rulesData ) {
		SoftAssert softAssert = new SoftAssert();
		
		JSONObject cityOperation=ObjectLayerPage.createOperation("replace","memberCity",rulesData.get("city"));
		JSONObject stateOperation=ObjectLayerPage.createOperation("replace","memberStateOrProvince",rulesData.get("state"));
		JSONObject countyOperation=ObjectLayerPage.createOperation("replace","memberCounty",new BigInteger(rulesData.get("county").toString()));

		JSONArray combinedOperations=new JSONArray();
		combinedOperations.put(cityOperation);
		combinedOperations.put(stateOperation);
		combinedOperations.put(countyOperation);

		JSONObject payload=ObjectLayerPage.createPatchPayload(combinedOperations);

		ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"true","false",locale,docName,docType,ResponseCodes.SUCCESS,payload);
		String getResponseAfterPatch = ObjectLayerPage.getCallForObjectyLayer(httpRequest,"Bright","member",docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject getResponseAfterPatchObj = new JSONObject(getResponseAfterPatch);
		JSONObject result = getResponseAfterPatchObj.getJSONObject("content").getJSONObject("lmsObject");
				
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
	
	
	public static void citNullCountyNotNullStateNotNullBright(RequestSpecification httpRequest,String docName,String locale,String docType,JSONObject rulesData ) {
		SoftAssert softAssert = new SoftAssert();
		
		JSONObject cityOperation=ObjectLayerPage.createOperation("replace","memberCity",rulesData.get("city"));
		JSONObject stateOperation=ObjectLayerPage.createOperation("replace","memberStateOrProvince",new BigInteger(rulesData.get("state").toString()));
		JSONObject countyOperation=ObjectLayerPage.createOperation("replace","memberCounty",new BigInteger(rulesData.get("county").toString()));

		JSONArray combinedOperations=new JSONArray();
		combinedOperations.put(cityOperation);
		combinedOperations.put(stateOperation);
		combinedOperations.put(countyOperation);

		JSONObject payload=ObjectLayerPage.createPatchPayload(combinedOperations);

		ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"true","false",locale,docName,docType,ResponseCodes.SUCCESS,payload);
		String getResponseAfterPatch = ObjectLayerPage.getCallForObjectyLayer(httpRequest,"Bright","member",docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject getResponseAfterPatchObj = new JSONObject(getResponseAfterPatch);
		JSONObject result = getResponseAfterPatchObj.getJSONObject("content").getJSONObject("lmsObject");
			
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
	
	public static void cityNullCountyNotNullStateNullBright(RequestSpecification httpRequest,String docName,String locale,String docType,JSONObject rulesData ) {
		SoftAssert softAssert = new SoftAssert();
		
		JSONObject cityOperation=ObjectLayerPage.createOperation("replace","memberCity",rulesData.get("city"));
		JSONObject stateOperation=ObjectLayerPage.createOperation("replace","memberStateOrProvince",rulesData.get("state"));
		JSONObject countyOperation=ObjectLayerPage.createOperation("replace","memberCounty",new BigInteger(rulesData.get("county").toString()));

		JSONArray combinedOperations=new JSONArray();
		combinedOperations.put(cityOperation);
		combinedOperations.put(stateOperation);
		combinedOperations.put(countyOperation);

		JSONObject payload=ObjectLayerPage.createPatchPayload(combinedOperations);

		ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"true","false",locale,docName,docType,ResponseCodes.SUCCESS,payload);
		String getResponseAfterPatch = ObjectLayerPage.getCallForObjectyLayer(httpRequest,"Bright","member",docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject getResponseAfterPatchObj = new JSONObject(getResponseAfterPatch);
		JSONObject result = getResponseAfterPatchObj.getJSONObject("content").getJSONObject("lmsObject");		

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
	
	public static void cityNullCountyNullStateNotNullBright(RequestSpecification httpRequest,String docName,String locale,String docType,JSONObject rulesData ) {
		SoftAssert softAssert = new SoftAssert();
		
		JSONObject cityOperation=ObjectLayerPage.createOperation("replace","memberCity",rulesData.get("city"));
		JSONObject stateOperation=ObjectLayerPage.createOperation("replace","memberStateOrProvince",new BigInteger(rulesData.get("state").toString()));
		JSONObject countyOperation=ObjectLayerPage.createOperation("replace","memberCounty",rulesData.get("county"));

		JSONArray combinedOperations=new JSONArray();
		combinedOperations.put(cityOperation);
		combinedOperations.put(stateOperation);
		combinedOperations.put(countyOperation);

		JSONObject payload=ObjectLayerPage.createPatchPayload(combinedOperations);

		ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"true","false",locale,docName,docType,ResponseCodes.SUCCESS,payload);
		String getResponseAfterPatch = ObjectLayerPage.getCallForObjectyLayer(httpRequest,"Bright","member",docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject getResponseAfterPatchObj = new JSONObject(getResponseAfterPatch);
		JSONObject result = getResponseAfterPatchObj.getJSONObject("content").getJSONObject("lmsObject");	
		
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
	
	public static void cityInValidCountyNullStateNotNullBright(RequestSpecification httpRequest,String docName,String locale,String docType,JSONObject rulesData ) {
		SoftAssert softAssert = new SoftAssert();
		
		String countyResponse;
		JSONObject itemsCounty;
		JSONObject pickListCounty;
		String stateResponse;
		JSONObject items;
		JSONObject pickListState;
		
		countyResponse=ObjectLayerMemberRulesPage.getConfigAPIWithPicklistIdAndName("COUNTIES_OR_REGIONS");
		itemsCounty = new JSONObject(countyResponse).getJSONObject("item");
		pickListCounty=(JSONObject) itemsCounty.get("pickListItems");
		stateResponse = ObjectLayerMemberRulesPage.getConfigAPIWithPicklistIdAndName("STATES_OR_PROVINCES");
		items = new JSONObject(stateResponse).getJSONObject("item");
		pickListState=(JSONObject) items.get("pickListItems");
		
		
		JSONObject cityOperation=ObjectLayerPage.createOperation("replace","memberCity",rulesData.get("city"));
		JSONObject stateOperation=ObjectLayerPage.createOperation("replace","memberStateOrProvince",new BigInteger(rulesData.get("state").toString()));
		JSONObject countyOperation=ObjectLayerPage.createOperation("replace","memberCounty",rulesData.get("county"));

		JSONArray combinedOperations=new JSONArray();
		combinedOperations.put(cityOperation);
		combinedOperations.put(stateOperation);
		combinedOperations.put(countyOperation);

		JSONObject payload=ObjectLayerPage.createPatchPayload(combinedOperations);

		ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"true","false",locale,docName,docType,ResponseCodes.SUCCESS,payload);
		String getResponseAfterPatch = ObjectLayerPage.getCallForObjectyLayer(httpRequest,"Bright","member",docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject getResponseAfterPatchObj = new JSONObject(getResponseAfterPatch);
		JSONObject result = getResponseAfterPatchObj.getJSONObject("content").getJSONObject("lmsObject");
			
		
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
	
	
	
    /*------------------------------------Office rules functions ------------------------------------*/
	
	public static void cityNotNullCountyNotNullStateNullBright(RequestSpecification httpRequest,String docName,String locale,String docType,JSONObject rulesData ) {
		SoftAssert softAssert = new SoftAssert();

		JSONObject cityOperation=ObjectLayerPage.createOperation("replace","officeCity",rulesData.get("city"));
		JSONObject stateOperation=ObjectLayerPage.createOperation("replace","officeStateOrProvince",rulesData.get("state"));
		JSONObject countyOperation=ObjectLayerPage.createOperation("replace","officeCounty",new BigInteger(rulesData.get("county").toString()));

		JSONArray combinedOperations=new JSONArray();
		combinedOperations.put(cityOperation);
		combinedOperations.put(stateOperation);
		combinedOperations.put(countyOperation);

		JSONObject payload=ObjectLayerPage.createPatchPayload(combinedOperations);

		ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"true","false",locale,docName,docType,ResponseCodes.SUCCESS,payload);
		String getResponseAfterPatch = ObjectLayerPage.getCallForObjectyLayer(httpRequest,"Bright","office",docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject getResponseAfterPutObj = new JSONObject(getResponseAfterPatch);
		JSONObject result = getResponseAfterPutObj.getJSONObject("content").getJSONObject("lmsObject");

		boolean cityExist=true;
		cityExist=OfficeStaticRulePage.isCityExist(rulesData.get("city").toString(),rulesData.get("county").toString());
		OfficeStaticRulePage.deleteCreatedCity(httpRequest,rulesData.get("city").toString(),rulesData.get("county").toString(),cityExist);

		if(!(Objects.equals(result.get("officeStateOrProvince").toString(),rulesData.get("state").toString()))) {
			softAssert.fail("Expected value of State:"+rulesData.get("state").toString()+" but state value after put Api:"+result.get("officeStateOrProvince").toString());
		}
		if(!(result.get("officeCounty").equals(null)? null : result.get("officeCounty").toString().equals(rulesData.get("county").toString()))) {
			softAssert.fail("Expected value of county:"+rulesData.get("county").toString()+" but county value after put Api:"+result.get("officeCounty").toString());
		}
		if(!(result.getString("officeCity").equals(null)? null : result.getString("officeCity").equals(rulesData.get("city").toString()))) {
			softAssert.fail("Expected value of city:"+rulesData.get("city").toString()+" but city value after put Api:"+result.get("officeCity").toString());
		}
		softAssert.assertAll();
	}



	public static void allValidValuesBright(RequestSpecification httpRequest,String docName,String locale,String docType,JSONObject rulesData) {
		SoftAssert softAssert = new SoftAssert();


		JSONObject cityOperation=ObjectLayerPage.createOperation("replace","officeCity",rulesData.get("city"));
		JSONObject stateOperation=ObjectLayerPage.createOperation("replace","officeStateOrProvince",new BigInteger(rulesData.get("state").toString()));
		JSONObject countyOperation=ObjectLayerPage.createOperation("replace","officeCounty",new BigInteger(rulesData.get("county").toString()));

		JSONArray combinedOperations=new JSONArray();
		combinedOperations.put(cityOperation);
		combinedOperations.put(stateOperation);
		combinedOperations.put(countyOperation);

		JSONObject payload=ObjectLayerPage.createPatchPayload(combinedOperations);

		ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"true","true",locale,docName,docType,ResponseCodes.SUCCESS,payload);
		String getResponseAfterPatch = ObjectLayerPage.getCallForObjectyLayer(httpRequest,"Bright","office",docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject getResponseAfterPutObj = new JSONObject(getResponseAfterPatch);
		JSONObject result = getResponseAfterPutObj.getJSONObject("content").getJSONObject("lmsObject");

		boolean cityExist=true;
		cityExist=OfficeStaticRulePage.isCityExist(rulesData.get("city").toString(),rulesData.get("county").toString());
		OfficeStaticRulePage.deleteCreatedCity(httpRequest,rulesData.get("city").toString(),rulesData.get("county").toString(),cityExist);

		if(!(Objects.equals(result.get("officeStateOrProvince").toString(),rulesData.get("state").toString()))) {
			softAssert.fail("Expected value of State:"+rulesData.get("state").toString()+" but state value after put Api:"+result.get("officeStateOrProvince").toString());
		}
		if(!(result.get("officeCounty").equals(null)? null : result.get("officeCounty").toString().equals(rulesData.get("county").toString()))) {
			softAssert.fail("Expected value of county:"+rulesData.get("county").toString()+" but county value after put Api:"+result.get("officeCounty").toString());
		}
		if(!(result.getString("officeCity").equals(null)? null : result.getString("officeCity").equals(rulesData.get("city").toString()))) {
			softAssert.fail("Expected value of city:"+rulesData.get("city").toString()+" but city value after put Api:"+result.get("officeCity").toString());
		}
		softAssert.assertAll();
	}



	public static void verifycitNullCountyNotNullStateNotNullBright(RequestSpecification httpRequest, String docName,
			String locale, String docType, JSONObject rulesData) {

		SoftAssert softAssert = new SoftAssert();
		 String city=null;

         city=city!=null?rulesData.get("city").toString() : null  ;
	JSONObject cityOperation=ObjectLayerPage.createOperation("replace","officeCity",rulesData.get("city"));
		JSONObject stateOperation=ObjectLayerPage.createOperation("replace","officeStateOrProvince",new BigInteger(rulesData.get("state").toString()));
		JSONObject countyOperation=ObjectLayerPage.createOperation("replace","officeCounty",new BigInteger(rulesData.get("county").toString()));

		JSONArray combinedOperations=new JSONArray();
		combinedOperations.put(cityOperation);
		combinedOperations.put(stateOperation);
		combinedOperations.put(countyOperation);

		JSONObject payload=ObjectLayerPage.createPatchPayload(combinedOperations);

		ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"false","true",locale,docName,docType,ResponseCodes.SUCCESS_ACCEPTED,payload);
		String getResponseAfterPatch = ObjectLayerPage.getCallForObjectyLayer(httpRequest,"Bright","office",docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject getResponseAfterPutObj = new JSONObject(getResponseAfterPatch);
		JSONObject result = getResponseAfterPutObj.getJSONObject("content").getJSONObject("lmsObject");
		boolean cityExist=true;
		
		cityExist=OfficeStaticRulePage.isCityExist(city,rulesData.get("county").toString());
		OfficeStaticRulePage.deleteCreatedCity(httpRequest,city,rulesData.get("county").toString(),cityExist);

		if(!(Objects.equals(result.get("officeStateOrProvince").toString(),rulesData.get("state").toString()))) {
			softAssert.fail("Expected value of State:"+rulesData.get("state").toString()+" but state value after put Api:"+result.get("officeStateOrProvince").toString());
		}
		if(!(result.get("officeCounty").equals(null)? null : result.get("officeCounty").toString().equals(rulesData.get("county").toString()))) {
			softAssert.fail("Expected value of county:"+rulesData.get("county").toString()+" but county value after put Api:"+result.get("officeCounty").toString());
		}
		if(!(Objects.equals(result.get("officeCity").toString(),rulesData.get("city").toString()))) {
			softAssert.fail("Expected value of city:"+rulesData.get("city").toString()+" but city value after put Api:"+result.get("officeCity").toString());
		}
		softAssert.assertAll();

	}







	public static void verifycityNullCountyNullStateNotNullBright(RequestSpecification httpRequest, String docName,
			String locale, String docType, JSONObject rulesData) {


		SoftAssert softAssert = new SoftAssert();

		String city=null;

        city=city!=null ?  rulesData.get("city").toString(): null;
		JSONObject cityOperation=ObjectLayerPage.createOperation("replace","officeCity",rulesData.get("city"));
		JSONObject stateOperation=ObjectLayerPage.createOperation("replace","officeStateOrProvince",new BigInteger(rulesData.get("state").toString()));
		JSONObject countyOperation=ObjectLayerPage.createOperation("replace","officeCounty",rulesData.get("county"));

		JSONArray combinedOperations=new JSONArray();
		combinedOperations.put(cityOperation);
		combinedOperations.put(stateOperation);
		combinedOperations.put(countyOperation);

		JSONObject payload=ObjectLayerPage.createPatchPayload(combinedOperations);

		ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"true","true",locale,docName,docType,ResponseCodes.SUCCESS,payload);
		String getResponseAfterPatch = ObjectLayerPage.getCallForObjectyLayer(httpRequest,"Bright","office",docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject getResponseAfterPutObj = new JSONObject(getResponseAfterPatch);
		JSONObject result = getResponseAfterPutObj.getJSONObject("content").getJSONObject("lmsObject");
		boolean cityExist=true;
		cityExist=OfficeStaticRulePage.isCityExist(city,rulesData.get("county").toString());
		OfficeStaticRulePage.deleteCreatedCity(httpRequest,city,rulesData.get("county").toString(),cityExist);

		if(!(result.get("officeStateOrProvince").equals(null)? null : result.get("officeStateOrProvince").toString()).equals(rulesData.get("state").toString())) {
			softAssert.fail("Expected value of State:"+rulesData.get("state").toString()+" but state value after put Api:"+result.get("officeStateOrProvince").toString());
		}
		if(!(Objects.equals(result.get("officeCounty").toString(),rulesData.get("county").toString()))) {
			softAssert.fail("Expected value of county:"+rulesData.get("county").toString()+" but county value after put Api:"+result.get("officeCounty").toString());
		}
		if(!(Objects.equals(result.get("officeCity").toString(),rulesData.get("city").toString()))) {
			softAssert.fail("Expected value of city:"+rulesData.get("city").toString()+" but city value after put Api:"+result.get("officeCity").toString());
		}

	}



	public static void verifycityNullCountyNotNullStateNullBright(RequestSpecification httpRequest, String docName,
			String locale, String docType, JSONObject rulesData) {

		SoftAssert softAssert = new SoftAssert();

		String city=null;

        city=city!=null?  rulesData.get("city").toString(): null ;
		JSONObject cityOperation=ObjectLayerPage.createOperation("replace","officeCity",rulesData.get("city"));
		JSONObject stateOperation=ObjectLayerPage.createOperation("replace","officeStateOrProvince",rulesData.get("state"));
		JSONObject countyOperation=ObjectLayerPage.createOperation("replace","officeCounty",new BigInteger(rulesData.get("county").toString()));

		JSONArray combinedOperations=new JSONArray();
		combinedOperations.put(cityOperation);
		combinedOperations.put(stateOperation);
		combinedOperations.put(countyOperation);

		JSONObject payload=ObjectLayerPage.createPatchPayload(combinedOperations);

		ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"true","false",locale,docName,docType,ResponseCodes.SUCCESS,payload);
		String getResponseAfterPatch = ObjectLayerPage.getCallForObjectyLayer(httpRequest,"Bright","office",docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject getResponseAfterPutObj = new JSONObject(getResponseAfterPatch);
		JSONObject result = getResponseAfterPutObj.getJSONObject("content").getJSONObject("lmsObject");
		boolean cityExist=true;
		cityExist=OfficeStaticRulePage.isCityExist(city,rulesData.get("county").toString());
		OfficeStaticRulePage.deleteCreatedCity(httpRequest,city,rulesData.get("county").toString(),cityExist);
		if(!(Objects.equals(result.get("officeStateOrProvince").toString(),rulesData.get("state").toString()))) {
			softAssert.fail("Expected value of State:"+ rulesData.get("state").toString() +" but state value after put Api:"+result.get("officeStateOrProvince").toString());
		}

		if(!((result.get("officeCounty").equals(null)? null : result.get("officeCounty").toString()).equals(rulesData.get("county").toString()))) {
			softAssert.fail("Expected value of county:"+ rulesData.get("county").toString() +" but county value after put Api:"+result.get("officeCounty").toString());
		}
		if(!(Objects.equals(result.get("officeCity").toString(),rulesData.get("city").toString()))) {
			softAssert.fail("Expected value of city:"+rulesData.get("city").toString()+" but city value after put Api:"+result.get("officeCity").toString());
		}

	}



	public static void verifycityInValidCountyNullStateNotNullBright(RequestSpecification httpRequest, String docName,
			String locale, String docType, JSONObject rulesData) {
		SoftAssert softAssert = new SoftAssert();

		String city=null;

        city=city!=null?rulesData.get("city").toString() : null ;
		JSONObject cityOperation=ObjectLayerPage.createOperation("replace","officeCity",rulesData.get("city"));
		JSONObject stateOperation=ObjectLayerPage.createOperation("replace","officeStateOrProvince",new BigInteger(rulesData.get("state").toString()));
		JSONObject countyOperation=ObjectLayerPage.createOperation("replace","officeCounty",rulesData.get("county"));

		JSONArray combinedOperations=new JSONArray();
		combinedOperations.put(cityOperation);
		combinedOperations.put(stateOperation);
		combinedOperations.put(countyOperation);

		JSONObject payload=ObjectLayerPage.createPatchPayload(combinedOperations);

		ObjectLayerPage.PatchCallAPIObjectLayer(httpRequest,"true","false",locale,docName,docType,ResponseCodes.SUCCESS,payload);
		String getResponseAfterPatch = ObjectLayerPage.getCallForObjectyLayer(httpRequest,"Bright","office",docName,ResponseCodes.SUCCESS).getBody().asString();
		JSONObject getResponseAfterPutObj = new JSONObject(getResponseAfterPatch);
		JSONObject result = getResponseAfterPutObj.getJSONObject("content").getJSONObject("lmsObject");
		boolean cityExist=true;
		cityExist=OfficeStaticRulePage.isCityExist(city,rulesData.get("county").toString());
		OfficeStaticRulePage.deleteCreatedCity(httpRequest,city,rulesData.get("county").toString(),cityExist);
		if(!(Objects.equals(result.get("officeStateOrProvince").toString(),rulesData.get("state").toString()))) {
			softAssert.fail("Expected value of State:"+ rulesData.get("state").toString() +" but state value after put Api:"+result.get("officeStateOrProvince").toString());
		}

		if(!((result.get("officeCounty").equals(null)? null : result.get("officeCounty").toString()).equals(rulesData.get("county").toString()))) {
			softAssert.fail("Expected value of county:"+ rulesData.get("county").toString() +" but county value after put Api:"+result.get("officeCounty").toString());
		}
		if(!(Objects.equals(result.get("officeCity").toString(),rulesData.get("city").toString()))) {
			softAssert.fail("Expected value of city:"+rulesData.get("city").toString()+" but city value after put Api:"+result.get("officeCity").toString());
		}

	}


}
