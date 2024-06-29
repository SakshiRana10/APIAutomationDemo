package bright.api.alaya.test.auditTrail;

import java.util.ArrayList;

import org.json.JSONArray;
import org.testng.annotations.Test;

import bright.api.alaya.pages.auditTrail.GetHistoryAPIPage;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import io.restassured.specification.RequestSpecification;

public class GetHistoryAPITeamTest extends MainClassAlaya{
	
	public static String bright = "bright";
	public static String team = "team";
	public static String docName;
	public static ArrayList<String> historyVerificationFields = CommonUtilityMethods.convertJArrayToListString((JSONArray) CommonUtilityMethods.readJsonFile(Constants.auditTrailPath, "getHistoryVerificationFields_"+team).get("fields"));

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
	 * */

	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "200 ok Get call for team history in audit trail")
	public void verifySuccessGetHistory() {
		RequestSpecification httpRequest= null;
		docName = GetHistoryAPIPage.getRandomDocName(team);
		GetHistoryAPIPage.verifyStatusForGetHistory(httpRequest,bright,team,docName,ResponseCodes.SUCCESS,1,100,"BrightTeam");
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "404 Invalid locale Get call for team history in audit trail")
	public void verifyNotFoundForInvalidLocaleGetHistory() {
		RequestSpecification httpRequest= null;
		docName = GetHistoryAPIPage.getRandomDocName(team);
		GetHistoryAPIPage.verifyStatusForGetHistory(httpRequest,"wrong",team,docName,ResponseCodes.NOT_FOUND,1,100,"BrightTeam");
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "404 Invalid doc type Get call for team history in audit trail")
	public void verifyNotFoundForInvalidDocTypeGetHistory() {
		RequestSpecification httpRequest= null;
		docName = GetHistoryAPIPage.getRandomDocName(team);
		GetHistoryAPIPage.verifyStatusForGetHistory(httpRequest,bright,"wrong",docName,ResponseCodes.NOT_FOUND,1,100,"BrightTeam");
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "463 Invalid docName Get call for team history in audit trail")
	public void verifyNotFoundForInvalidDocNameGetHistory() {
		RequestSpecification httpRequest= null;
		GetHistoryAPIPage.verifyStatusForGetHistory(httpRequest,bright,team,"0",ResponseCodes.NO_SUCH_KEY,1,100,"BrightTeam");
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "400 Invalid Page Get call for team history in audit trail")
	public void verifyBadReqForPageGetHistory() {
		RequestSpecification httpRequest= null;
		docName = GetHistoryAPIPage.getRandomDocName(team);
		GetHistoryAPIPage.verifyStatusForGetHistory(httpRequest,bright,team,docName,ResponseCodes.BAD_REQUEST,0,100,"BrightTeam");
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "400 Invalid PerPage Get call for team history in audit trail")
	public void verifyBadReqForPerPageGetHistory() {
		RequestSpecification httpRequest= null;
		docName = GetHistoryAPIPage.getRandomDocName(team);
		GetHistoryAPIPage.verifyStatusForGetHistory(httpRequest,bright,team,docName,ResponseCodes.BAD_REQUEST,1,0,"BrightTeam");
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "403 wrong x api key Get call for team history in audit trail")
	public void verifyForbiddenForWrongKeyGetHistory() {
		RequestSpecification httpRequest= null;
		docName = GetHistoryAPIPage.getRandomDocName(team);
		GetHistoryAPIPage.verifyForbiddenForWrongKey(httpRequest,bright,team,docName,ResponseCodes.FORBIDDEN,1,100,"BrightTeam");
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "403 wrong method Get call for team history in audit trail")
	public void verifyForbiddenForWrongMethodGetHistory() {
		RequestSpecification httpRequest= null;
		docName = GetHistoryAPIPage.getRandomDocName(team);
		GetHistoryAPIPage.verifyForbiddenForWrongMethod(httpRequest,bright,team,docName,ResponseCodes.FORBIDDEN,1,100,"BrightTeam");
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "200 ok and response verification of Get call for team history in audit trail")
	public void verifyResponseGetHistory() {
		RequestSpecification httpRequest= null;
		docName = GetHistoryAPIPage.getRandomDocName(team);
		GetHistoryAPIPage.verifyResponseForGetHistory(httpRequest,bright,team,docName,ResponseCodes.SUCCESS,1,1000,"BrightTeam");
	}
	
	@Test ( groups = {"test", "dev"}, priority = 0, enabled = true, description = "Data verification of Get call for team history in audit trail after put in parent field")
	public void verifyDataAfterPutParentFieldInGetHistory() {
		RequestSpecification httpRequest= null;
		docName = GetHistoryAPIPage.getRandomDocName(team);
		GetHistoryAPIPage.verifyDataAfterPutParentFieldInGetHistory(httpRequest,bright,team,docName,ResponseCodes.SUCCESS,1,100,"BrightTeam","teamName","CORE ALAYA AUTOMATION TEAM",historyVerificationFields);
	}
}
