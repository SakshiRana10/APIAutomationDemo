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

public class GetHistoryAPIMemberTest extends MainClassAlaya{
	
	public static String bright = "bright";
	public static String member = "member";
	public static String docName;
	public static ArrayList<String> historyVerificationFields = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.auditTrailPath, "getHistoryVerificationFields_"+member).get("fields"));

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

	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "200 ok Get call for member history in audit trail")
	public void verifySuccessGetHistory() {
		RequestSpecification httpRequest= null;
		docName = GetHistoryAPIPage.getRandomDocName(member);
		GetHistoryAPIPage.verifyStatusForGetHistory(httpRequest,bright,member,docName,ResponseCodes.SUCCESS,1,100,"BrightMember");
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "404 Invalid locale Get call for member history in audit trail")
	public void verifyNotFoundForInvalidLocaleGetHistory() {
		RequestSpecification httpRequest= null;
		docName = GetHistoryAPIPage.getRandomDocName(member);
		GetHistoryAPIPage.verifyStatusForGetHistory(httpRequest,"wrong",member,docName,ResponseCodes.NOT_FOUND,1,100,"PartyPermission");
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "404 Invalid doc type Get call for member history in audit trail")
	public void verifyNotFoundForInvalidDocTypeGetHistory() {
		RequestSpecification httpRequest= null;
		docName = GetHistoryAPIPage.getRandomDocName(member);
		GetHistoryAPIPage.verifyStatusForGetHistory(httpRequest,bright,"wrong",docName,ResponseCodes.NOT_FOUND,1,100,"BrightMember");
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "463 Invalid docName Get call for member history in audit trail")
	public void verifyNotFoundForInvalidDocNameGetHistory() {
		RequestSpecification httpRequest= null;
		GetHistoryAPIPage.verifyStatusForGetHistory(httpRequest,bright,member,"0",ResponseCodes.NO_SUCH_KEY,1,100,"PartyPermission");
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "400 Invalid Page Get call for member history in audit trail")
	public void verifyBadReqForPageGetHistory() {
		RequestSpecification httpRequest= null;
		docName = GetHistoryAPIPage.getRandomDocName(member);
		GetHistoryAPIPage.verifyStatusForGetHistory(httpRequest,bright,member,docName,ResponseCodes.BAD_REQUEST,0,100,"BrightMember");
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "400 Invalid PerPage Get call for member history in audit trail")
	public void verifyBadReqForPerPageGetHistory() {
		RequestSpecification httpRequest= null;
		docName = GetHistoryAPIPage.getRandomDocName(member);
		GetHistoryAPIPage.verifyStatusForGetHistory(httpRequest,bright,member,docName,ResponseCodes.BAD_REQUEST,1,0,"PartyPermission");
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "403 wrong x api key Get call for member history in audit trail")
	public void verifyForbiddenForWrongKeyGetHistory() {
		RequestSpecification httpRequest= null;
		docName = GetHistoryAPIPage.getRandomDocName(member);
		GetHistoryAPIPage.verifyForbiddenForWrongKey(httpRequest,bright,member,docName,ResponseCodes.FORBIDDEN,1,100,"BrightMember");
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "403 wrong method Get call for member history in audit trail")
	public void verifyForbiddenForWrongMethodGetHistory() {
		RequestSpecification httpRequest= null;
		docName = GetHistoryAPIPage.getRandomDocName(member);
		GetHistoryAPIPage.verifyForbiddenForWrongMethod(httpRequest,bright,member,docName,ResponseCodes.FORBIDDEN,1,100,"PartyPermission");
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "200 ok and response verification of Get call for member history in audit trail")
	public void verifyResponseGetHistory() {
		RequestSpecification httpRequest= null;
		docName = GetHistoryAPIPage.getRandomDocName(member);
		GetHistoryAPIPage.verifyResponseForGetHistory(httpRequest,bright,member,docName,ResponseCodes.SUCCESS,1,1000,"BrightMember");
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Data verification of Get call for member history in audit trail after put in parent field")
	public void verifyDataAfterPutParentFieldInGetHistory() {
		RequestSpecification httpRequest= null;
		docName = GetHistoryAPIPage.getRandomDocName(member);
		GetHistoryAPIPage.verifyDataAfterPutParentFieldInGetHistory(httpRequest,bright,member,docName,ResponseCodes.SUCCESS,1,100,"BrightMember","memberAddress1","CORE ALAYA AUTOMATION ADDRESS",historyVerificationFields);
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Data verification of Get call for member history in audit trail after put in child field")
	public void verifyDataAfterPutChildFieldInGetHistory() {
		RequestSpecification httpRequest= null;
		docName = GetHistoryAPIPage.getRandomDocName(member);
		GetHistoryAPIPage.verifyDataAfterPutChildFieldInGetHistory(httpRequest,bright,member,docName,ResponseCodes.SUCCESS,1,100,"PartyPermission","permissions","230035815111","PARTYPERMKEY",historyVerificationFields);
	}

}
