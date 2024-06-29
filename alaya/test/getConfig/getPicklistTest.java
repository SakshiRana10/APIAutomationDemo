package bright.api.alaya.test.getConfig;

import java.io.IOException;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;
import bright.api.alaya.pages.getConfig.getPicklistPage;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import io.restassured.specification.RequestSpecification;


public class getPicklistTest extends MainClassAlaya {
 
	protected static String picklistParameter = "picklists";
	protected static String brightLocale = "bright";
	protected static String wirexLocale = "wirex";

	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify picklistItems from api response to repo file for bright locale")
	public void matchItemsBright() throws InterruptedException, IOException, ParseException{
		RequestSpecification httpRequest= null;
		getPicklistPage.matchItems(httpRequest,brightLocale,picklistParameter);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify picklistItems from api response to repo file for wirex locale")
	public void matchItemsWirex() throws InterruptedException, IOException, ParseException{
		RequestSpecification httpRequest= null;
		getPicklistPage.matchItems(httpRequest,wirexLocale,picklistParameter);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify success status for get picklist for bright")
	public void getPicklistBright() throws InterruptedException, IOException{
		RequestSpecification httpRequest= null;
		getPicklistPage.getConfigForPicklist(httpRequest,brightLocale,ResponseCodes.SUCCESS,picklistParameter);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify success status for get picklist for wirex")
	public void getPicklistWirex() throws InterruptedException, IOException{
		RequestSpecification httpRequest= null;
		getPicklistPage.getConfigForPicklist(httpRequest,wirexLocale,ResponseCodes.SUCCESS,picklistParameter);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify not found status for get picklist for wrong name for bright")
	public void getPicklistWrongNameBright() throws InterruptedException, IOException{
		RequestSpecification httpRequest= null;
		getPicklistPage.getConfigForPicklist(httpRequest,brightLocale,ResponseCodes.NOT_FOUND,"picklist");
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify not found status for get picklist for wrong name for wirex")
	public void getPicklistWrongNameWirex() throws InterruptedException, IOException{
		RequestSpecification httpRequest= null;
		getPicklistPage.getConfigForPicklist(httpRequest,wirexLocale,ResponseCodes.NOT_FOUND,"picklist");
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify response body structure for get picklist for bright")
	public void verifyResponseBodyBright() throws InterruptedException{  
		RequestSpecification httpRequest= null;
		getPicklistPage.verifyResponseBody(httpRequest,brightLocale,ResponseCodes.SUCCESS,picklistParameter);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify response body structure for get picklist for wirex")
	public void verifyResponseBodyWirex() throws InterruptedException{  
		RequestSpecification httpRequest= null;
		getPicklistPage.verifyResponseBody(httpRequest,wirexLocale,ResponseCodes.SUCCESS,picklistParameter);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify response with picklist ID for bright")
	public void verifyResponseWithPickListIDBright() throws InterruptedException{  
		RequestSpecification httpRequest= null;
		getPicklistPage.verifyResponseWithPickListIDOrName(httpRequest,brightLocale,"pickListId",ResponseCodes.SUCCESS,picklistParameter);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify response with picklist name for bright")
	public void verifyResponseWithPickListNameBright() throws InterruptedException{  
		RequestSpecification httpRequest= null;
		getPicklistPage.verifyResponseWithPickListIDOrName(httpRequest,brightLocale,"pickListSystemName",ResponseCodes.SUCCESS,picklistParameter);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify response with picklist ID for wirex")
	public void verifyResponseWithPickListIDWirex() throws InterruptedException{  
		RequestSpecification httpRequest= null;
		getPicklistPage.verifyResponseWithPickListIDOrName(httpRequest,wirexLocale,"pickListId",ResponseCodes.SUCCESS,picklistParameter);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify response with picklist name for wirex")
	public void verifyResponseWithPickListNameWirex() throws InterruptedException{  
		RequestSpecification httpRequest= null;
		getPicklistPage.verifyResponseWithPickListIDOrName(httpRequest,wirexLocale,"pickListSystemName",ResponseCodes.SUCCESS,picklistParameter);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify response with picklist item id for bright")
	public void verifyResponseWithPickListItemIDBright() throws InterruptedException{  
		RequestSpecification httpRequest= null;
		getPicklistPage.verifyResponseWithPickListitemID(httpRequest,brightLocale,"picklistItemId",ResponseCodes.SUCCESS,picklistParameter);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify response with picklist item id for wirex")
	public void verifyResponseWithPickListItemIDWirex() throws InterruptedException{ 
		RequestSpecification httpRequest= null;
		getPicklistPage.verifyResponseWithPickListitemID(httpRequest,wirexLocale,"picklistItemId",ResponseCodes.SUCCESS,picklistParameter);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify response with wrong picklist item id for bright")
	public void verifyResponseWithWrongPickListItemBright() throws InterruptedException{  
		RequestSpecification httpRequest= null;
		getPicklistPage.verifyResponseWithWrongPickListitemID(httpRequest,brightLocale,ResponseCodes.NOT_FOUND,picklistParameter);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify response with wrong picklist item id for wirex")
	public void verifyResponseWithWrongPickListItemWirex() throws InterruptedException{  
		RequestSpecification httpRequest= null;
		getPicklistPage.verifyResponseWithWrongPickListitemID(httpRequest,wirexLocale,ResponseCodes.NOT_FOUND,picklistParameter);
	}
	
}
