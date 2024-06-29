package bright.api.alaya.test.auditTrail;

import java.util.ArrayList;

import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.Test;

import bright.api.alaya.pages.auditTrail.GetHistoryAPIPage;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import io.restassured.specification.RequestSpecification;

public class GetHistoryAPIListingTest  extends MainClassAlaya{
	
	public static String bright = "bright";
	public static String listing = "listing";
	public static String docName;
	public static ArrayList<String> historyVerificationFields = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.auditTrailPath, "getHistoryVerificationFields_"+listing).get("fields"));

	/*Total Cases - 11*/
	/* success Status code
	 * Invalid locale
	 * Invalid docType
	 * Invalid docName
	 * Invalid page
	 * Invalid perPage
	 * Invalid X API key
	 * Invalid method name
	 * response structure verification
	 * Data verification after put in parent field
	 * Data verification after put in child field
	 * */

	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "200 ok Get call for listing history in audit trail")
	public void verifySuccessGetHistory() {
		RequestSpecification httpRequest= null;
		docName = GetHistoryAPIPage.getRandomDocName(listing);
		GetHistoryAPIPage.verifyStatusForGetHistory(httpRequest,bright,listing,docName,ResponseCodes.SUCCESS,1,100,"BrightAll");
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "404 Invalid locale Get call for listing history in audit trail")
	public void verifyNotFoundForInvalidLocaleGetHistory() {
		RequestSpecification httpRequest= null;
		docName = GetHistoryAPIPage.getRandomDocName(listing);
		GetHistoryAPIPage.verifyStatusForGetHistory(httpRequest,"wrong",listing,docName,ResponseCodes.NOT_FOUND,1,100,"BrightAll");
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "404 Invalid doc type Get call for listing history in audit trail")
	public void verifyNotFoundForInvalidDocTypeGetHistory() {
		RequestSpecification httpRequest= null;
		docName = GetHistoryAPIPage.getRandomDocName(listing);
		GetHistoryAPIPage.verifyStatusForGetHistory(httpRequest,bright,"wrong",docName,ResponseCodes.NOT_FOUND,1,100,"BrightAll");
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "463 Invalid docName Get call for listing history in audit trail")
	public void verifyNotFoundForInvalidDocNameGetHistory() {
		RequestSpecification httpRequest= null;
		GetHistoryAPIPage.verifyStatusForGetHistory(httpRequest,bright,listing,"0",ResponseCodes.NO_SUCH_KEY,1,100,"BrightAll");
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "400 Invalid Page Get call for listing history in audit trail")
	public void verifyBadReqForPageGetHistory() {
		RequestSpecification httpRequest= null;
		docName = GetHistoryAPIPage.getRandomDocName(listing);
		GetHistoryAPIPage.verifyStatusForGetHistory(httpRequest,bright,listing,docName,ResponseCodes.BAD_REQUEST,0,100,"BrightAll");
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "400 Invalid PerPage Get call for listing history in audit trail")
	public void verifyBadReqForPerPageGetHistory() {
		RequestSpecification httpRequest= null;
		docName = GetHistoryAPIPage.getRandomDocName(listing);
		GetHistoryAPIPage.verifyStatusForGetHistory(httpRequest,bright,listing,docName,ResponseCodes.BAD_REQUEST,1,0,"BrightAll");
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "403 wrong x api key Get call for listing history in audit trail")
	public void verifyForbiddenForWrongKeyGetHistory() {
		RequestSpecification httpRequest= null;
		docName = GetHistoryAPIPage.getRandomDocName(listing);
		GetHistoryAPIPage.verifyForbiddenForWrongKey(httpRequest,bright,listing,docName,ResponseCodes.FORBIDDEN,1,100,"BrightAll");
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "403 wrong method Get call for listing history in audit trail")
	public void verifyForbiddenForWrongMethodGetHistory() {
		RequestSpecification httpRequest= null;
		docName = GetHistoryAPIPage.getRandomDocName(listing);
		GetHistoryAPIPage.verifyForbiddenForWrongMethod(httpRequest,bright,listing,docName,ResponseCodes.FORBIDDEN,1,100,"BrightAll");
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "200 ok and response verification of Get call for listing history in audit trail")
	public void verifyResponseGetHistory() {
		RequestSpecification httpRequest= null;
		docName = GetHistoryAPIPage.getRandomDocName(listing);
		GetHistoryAPIPage.verifyResponseForGetHistory(httpRequest,bright,listing,docName,ResponseCodes.SUCCESS,1,1000,"BrightAll");
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Data verification of Get call for listing history in audit trail after put in parent field")
	public void verifyDataAfterPutParentFieldInGetHistory() {
		RequestSpecification httpRequest= null;
		docName = GetHistoryAPIPage.getRandomDocName(listing);
		GetHistoryAPIPage.verifyDataAfterPutParentFieldInGetHistory(httpRequest,bright,listing,docName,ResponseCodes.SUCCESS,1,100,"BrightAll","fullStreetAddress","CORE ALAYA AUTOMATION STREET",historyVerificationFields);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Data verification of Get call for listing history in audit trail after put in child field")
	public void verifyDataAfterPutChildFieldInGetHistory() {
		RequestSpecification httpRequest= null;
		docName = GetHistoryAPIPage.getRandomDocName(listing);
		GetHistoryAPIPage.verifyDataAfterPutChildFieldInGetHistory(httpRequest,bright,listing,docName,ResponseCodes.SUCCESS,1,100,"BrightPropMedia","media","650148169496","MEDIAKEY",historyVerificationFields);
	}
}
