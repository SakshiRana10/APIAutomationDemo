package bright.api.alaya.test.indexInitializer;

import bright.api.alaya.pages.indexInitializer.IndexInitializerPage;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.MainClassAlaya;
import io.restassured.specification.RequestSpecification;

import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class IndexInitializerTest extends MainClassAlaya{
	
	/*Total Cases - 24
	 * Post call for index initializer
	 * bad request for index initializer
	 * invalid request for index initializer
	 * */
	
	public static String listing = "listing";
	public static String member = "member";
	public static String office = "office";
	public static String city = "city";
	public static String taxrecord = "taxrecord";
	public static String county = "countyrate";
	public static String builder_model = "builder_model";
	public static String building_name = "building_name";
	public static String subdivision = "subdivision";
	public static String team = "team";
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Index initializer for doc type listing")
	public void listingIndexInitializerMethod() throws InterruptedException, JsonMappingException, JsonProcessingException {
		ArrayList<String> AttributeNamesForDocStore = new ArrayList<String>(Arrays.asList("lastModified","fullStreetAddress", "expirationDate","listingId","listAgentKey","listAgentMlsId","listOfficeKey","listingKey","propertyType"));
		ArrayList<String> AttributeNamesForES = new ArrayList<String>(Arrays.asList("dsLastModifiedTime","fullStreetAddress", "expirationDate","listingId","listAgentKey","listAgentMlsId","listOfficeKey","listingKey","propertyType{key}"));
		RequestSpecification httpRequest= null;
		IndexInitializerPage.postIndexInitializer(httpRequest,listing,AttributeNamesForDocStore,AttributeNamesForES);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Index initializer for doc type member")
	public void memberIndexInitializerMethod() throws InterruptedException, JsonMappingException, JsonProcessingException {
		ArrayList<String> AttributeNamesForDocStore = new ArrayList<String>(Arrays.asList("memberMlsId", "memberKey","lastModified","officeKey","officeMlsId"));
		ArrayList<String> AttributeNamesForES = new ArrayList<String>(Arrays.asList("memberMlsId", "memberKey","dsLastModifiedTime","officeKey","officeMlsId"));
		RequestSpecification httpRequest= null;
		IndexInitializerPage.postIndexInitializer(httpRequest,member,AttributeNamesForDocStore,AttributeNamesForES);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Index initializer for doc type office")
	public void OfficeIndexInitializerMethod() throws InterruptedException, JsonMappingException, JsonProcessingException {
		ArrayList<String> AttributeNamesForDocStore = new ArrayList<String>(Arrays.asList("officeMlsId", "officeRoleList","officeNumViolations","officeName","lastModified"));
		ArrayList<String> AttributeNamesForES = new ArrayList<String>(Arrays.asList("officeMlsId", "officeRoleList","officeNumViolations","officeName","dsLastModifiedTime"));
		RequestSpecification httpRequest= null;
		IndexInitializerPage.postIndexInitializer(httpRequest,office,AttributeNamesForDocStore,AttributeNamesForES);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Index initializer for doc type city")
	public void CityIndexInitializerMethod() throws InterruptedException, JsonMappingException, JsonProcessingException {
		ArrayList<String> AttributeNamesForDocStore = new ArrayList<String>(Arrays.asList("ctyCityCounty","ctyCityName","ctyCountyState","lastModified"));
		ArrayList<String> AttributeNamesForES = new ArrayList<String>(Arrays.asList("ctyCityCounty{key}","ctyCityName","ctyCountyState{key}","dsLastModifiedTime"));
		RequestSpecification httpRequest= null;
		IndexInitializerPage.postIndexInitializer(httpRequest,city,AttributeNamesForDocStore,AttributeNamesForES);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Index initializer for doc type tax record")
	public void TaxRecordIndexInitializerMethod() throws InterruptedException, JsonMappingException, JsonProcessingException {
		ArrayList<String> AttributeNamesForDocStore = new ArrayList<String>(Arrays.asList("lastModified","fileName", "fips","municipalityId","stateId","townId"));
		ArrayList<String> AttributeNamesForES = new ArrayList<String>(Arrays.asList("dsLastModifiedTime","fileName", "fips","municipalityId","stateId","townId"));
		RequestSpecification httpRequest= null;
		IndexInitializerPage.postIndexInitializer(httpRequest,taxrecord,AttributeNamesForDocStore,AttributeNamesForES);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Index initializer for doc type county rate")
	public void CountyRateIndexInitializerMethod() throws InterruptedException, JsonMappingException, JsonProcessingException {
		ArrayList<String> AttributeNamesForDocStore = new ArrayList<String>(Arrays.asList("lastModified", "fileName", "fips", "municipalityId", "stateId", "countyId"));
		ArrayList<String> AttributeNamesForES = new ArrayList<String>(Arrays.asList("dsLastModifiedTime", "fileName", "fips", "municipalityId", "stateId", "countyId"));
		RequestSpecification httpRequest= null;
		IndexInitializerPage.postIndexInitializer(httpRequest,county,AttributeNamesForDocStore,AttributeNamesForES);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Index initializer for doc type builder model")
	public void BuilderModelIndexInitializerMethod() throws InterruptedException, JsonMappingException, JsonProcessingException {
		ArrayList<String> AttributeNamesForDocStore = new ArrayList<String>(Arrays.asList("builderModelKey", "builderModelName","lastModified","builderModelCounty","builderModelStatus"));
		ArrayList<String> AttributeNamesForES = new ArrayList<String>(Arrays.asList("builderModelKey", "builderModelName","dsLastModifiedTime","builderModelCounty{key}","builderModelStatus{key}"));
		RequestSpecification httpRequest= null;
		IndexInitializerPage.postIndexInitializer(httpRequest,builder_model,AttributeNamesForDocStore,AttributeNamesForES);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Index initializer for doc type building name")
	public void BuildingNameIndexInitializerMethod() throws InterruptedException, JsonMappingException, JsonProcessingException {
		ArrayList<String> AttributeNamesForDocStore = new ArrayList<String>(Arrays.asList("lastModified","bldgNameKey","bldgNameName","bldgNameRelatedBldgNameKey","bldgNameState","bldgNameStatus"));
		ArrayList<String> AttributeNamesForES = new ArrayList<String>(Arrays.asList("dsLastModifiedTime","bldgNameKey","bldgNameName","bldgNameRelatedBldgNameKey","bldgNameState{key}","bldgNameStatus{key}"));
		RequestSpecification httpRequest= null;
		IndexInitializerPage.postIndexInitializer(httpRequest,building_name,AttributeNamesForDocStore,AttributeNamesForES);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Index initializer for doc type teams ")
	public void TeamIndexInitializerMethod() throws InterruptedException, JsonMappingException, JsonProcessingException {
		ArrayList<String> AttributeNamesForDocStore = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.graphqlAttributes, "basicTeam").get("discovery"));
	    ArrayList<String> AttributeNamesForES = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.graphqlAttributes, "basicTeam").get("graphQl"));	
		RequestSpecification httpRequest= null;
		IndexInitializerPage.postIndexInitializer(httpRequest,team,AttributeNamesForDocStore,AttributeNamesForES);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Index initializer for doc type subdivision ")
	public void SubdivisionIndexInitializerMethod() throws InterruptedException, JsonMappingException, JsonProcessingException {
		ArrayList<String> AttributeNamesForDocStore = new ArrayList<String>(Arrays.asList("lastModified","loSubdivisionCounty", "loSubdivisionName","loSubdivisionState","loSubdivisionStatus"));
		ArrayList<String> AttributeNamesForES = new ArrayList<String>(Arrays.asList("dsLastModifiedTime","loSubdivisionCounty{key}", "loSubdivisionName","loSubdivisionState{key}","loSubdivisionStatus{key}"));
		RequestSpecification httpRequest= null;
		IndexInitializerPage.postIndexInitializer(httpRequest,subdivision,AttributeNamesForDocStore,AttributeNamesForES);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Bad request for index initializer for doc type listing")
	public void verifyBadRequestForListingIndexInitializerPost() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		IndexInitializerPage.verifyBadRequestForIndexInitializerPostCallMethod(httpRequest,listing,taxrecord);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Bad request for index initializer for doc type member")
	public void verifyBadRequestForMemberIndexInitializerPost() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		IndexInitializerPage.verifyBadRequestForIndexInitializerPostCallMethod(httpRequest,member,listing);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Bad request for index initializer for doc type office")
	public void verifyBadRequestForOfficeIndexInitializerPost() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		IndexInitializerPage.verifyBadRequestForIndexInitializerPostCallMethod(httpRequest,office,member);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Bad request for index initializer for doc type city")
	public void verifyBadRequestForCityIndexInitializerPost() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		IndexInitializerPage.verifyBadRequestForIndexInitializerPostCallMethod(httpRequest,city,office);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Bad request for index initializer for doc type taxrecord")
	public void verifyBadRequestForTaxRecordIndexInitializerPost() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		IndexInitializerPage.verifyBadRequestForIndexInitializerPostCallMethod(httpRequest,taxrecord,county);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Bad request for index initializer for doc type countyrate")
	public void verifyBadRequestForCountyIndexInitializerPost() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		IndexInitializerPage.verifyBadRequestForIndexInitializerPostCallMethod(httpRequest,county,city);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Bad request for index initializer for doc type builder model")
	public void verifyBadRequestForBuilderModelIndexInitializerPost() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		IndexInitializerPage.verifyBadRequestForIndexInitializerPostCallMethod(httpRequest,builder_model,listing);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Bad request for index initializer for doc type building name")
	public void verifyBadRequestForBuldingNameIndexInitializerPost() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		IndexInitializerPage.verifyBadRequestForIndexInitializerPostCallMethod(httpRequest,building_name,member);
	}
	
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Bad request for index initializer for doc type building name")
	public void verifyBadRequestForSubdivisionIndexInitializerPost() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		IndexInitializerPage.verifyBadRequestForIndexInitializerPostCallMethod(httpRequest,subdivision,office);
	}
	
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Bad request for index initializer for doc type building name")
	public void verifyBadRequestForTeamsIndexInitializerPost() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		IndexInitializerPage.verifyBadRequestForIndexInitializerPostCallMethod(httpRequest,team,office);
	}

	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "forbidden request for index initializer for doc type listing")
	public void verifyForbiddenForListingIndexInitializerPost() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		IndexInitializerPage.verifyForbiddenForIndexInitializerPostCallMethod(httpRequest,listing);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "forbidden request for index initializer for doc type member")
	public void verifyForbiddenForMemberIndexInitializerPost() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		IndexInitializerPage.verifyForbiddenForIndexInitializerPostCallMethod(httpRequest,member);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "forbidden request for index initializer for doc type member")
	public void verifyForbiddenForTeamsIndexInitializerPost() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		IndexInitializerPage.verifyForbiddenForIndexInitializerPostCallMethod(httpRequest,team);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "forbidden request for index initializer for doc type member")
	public void verifyForbiddenForSubdivisionIndexInitializerPost() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		IndexInitializerPage.verifyForbiddenForIndexInitializerPostCallMethod(httpRequest,subdivision);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "forbidden request for index initializer for doc type office")
	public void verifyForbiddenForOfficeIndexInitializerPost() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		IndexInitializerPage.verifyForbiddenForIndexInitializerPostCallMethod(httpRequest,office);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "forbidden request for index initializer for doc type city")
	public void verifyForbiddenForCityIndexInitializerPost() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		IndexInitializerPage.verifyForbiddenForIndexInitializerPostCallMethod(httpRequest,city);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "forbidden request for index initializer for doc type taxrecord")
	public void verifyForbiddenForTaxRecordIndexInitializerPost() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		IndexInitializerPage.verifyForbiddenForIndexInitializerPostCallMethod(httpRequest,taxrecord);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "forbidden request for index initializer for doc type countyrate")
	public void verifyForbiddenForCountyIndexInitializerPost() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		IndexInitializerPage.verifyForbiddenForIndexInitializerPostCallMethod(httpRequest,county);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "forbidden request for index initializer for doc type builder model")
	public void verifyForbiddenForBuilderModelIndexInitializerPost() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		IndexInitializerPage.verifyForbiddenForIndexInitializerPostCallMethod(httpRequest,builder_model);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "forbidden request for index initializer for doc type building name")
	public void verifyForbiddenForBuildingNameIndexInitializerPost() throws InterruptedException, JsonMappingException, JsonProcessingException {
		RequestSpecification httpRequest= null;
		IndexInitializerPage.verifyForbiddenForIndexInitializerPostCallMethod(httpRequest,building_name);
	}
	
	
	
}
