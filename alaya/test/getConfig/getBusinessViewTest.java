package bright.api.alaya.test.getConfig;

import java.io.IOException;
import org.testng.annotations.Test;
import bright.api.alaya.pages.getConfig.getBusinessViewPage;
import bright.api.alaya.utils.MainClassAlaya;
import bright.api.alaya.utils.ResponseCodes;
import io.restassured.specification.RequestSpecification;

public class getBusinessViewTest extends MainClassAlaya {
	protected static String picklistParameter = "picklists";
	protected static String brightLocale = "bright";
	protected static String wirexLocale = "wirex";
	protected static String businessViewParam = "getBusinessView";
	protected static String fieldsParam = "getFields";
	protected static String fieldsNameParam = "getFieldsName";

	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify success status for get businessView for bright")
	public void getBusinessViewBright() throws InterruptedException, IOException{
		RequestSpecification httpRequest= null;
		getBusinessViewPage.verifySuccessForBusinessView( httpRequest,brightLocale,ResponseCodes.SUCCESS,businessViewParam);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify success status for get businessView for wirex")
	public void getBusinessViewWirex() throws InterruptedException, IOException{
		RequestSpecification httpRequest= null;
		getBusinessViewPage.verifySuccessForBusinessView( httpRequest,wirexLocale,ResponseCodes.SUCCESS,businessViewParam);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify data for get businessView for bright")
	public void verifyDataForBusinessViewBright() throws InterruptedException, IOException{
		RequestSpecification httpRequest= null;
		getBusinessViewPage.MatchDataForBusinessView( httpRequest,brightLocale,"item",businessViewParam);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify data for get businessView for wirex")
	public void verifyDataForBusinessViewWirex() throws InterruptedException, IOException{
		RequestSpecification httpRequest= null;
		getBusinessViewPage.MatchDataForBusinessView( httpRequest,wirexLocale ,"item",businessViewParam);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify success status for get fields for bright")
	public void getfieldsBright() throws InterruptedException, IOException{
		RequestSpecification httpRequest= null;
		getBusinessViewPage.verifySuccessForBusinessView( httpRequest,brightLocale,ResponseCodes.SUCCESS,fieldsParam);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify success status for get fields for wirex")
	public void getfieldsWirex() throws InterruptedException, IOException{
		RequestSpecification httpRequest= null;
		getBusinessViewPage.verifySuccessForBusinessView( httpRequest,wirexLocale,ResponseCodes.SUCCESS,fieldsParam);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify data for get fields for bright")
	public void verifyDataForFieldsBright() throws InterruptedException, IOException{
		RequestSpecification httpRequest= null;
		getBusinessViewPage.MatchDataForBusinessView( httpRequest,brightLocale, "item", fieldsParam);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify data for get fields for wirex")
	public void verifyDataForFieldsWirex() throws InterruptedException, IOException{
		RequestSpecification httpRequest= null;
		getBusinessViewPage.MatchDataForBusinessView( httpRequest,wirexLocale, "item", fieldsParam);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify success status for get fields by names for bright")
	public void getfieldsByNameBright() throws InterruptedException, IOException{
		RequestSpecification httpRequest= null;
	    getBusinessViewPage.verifySuccessForBusinessView( httpRequest,brightLocale,ResponseCodes.SUCCESS,fieldsNameParam);
    }

    @Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify success status for get fields by names for wirex")
    public void getfieldsByNameWirex() throws InterruptedException, IOException{
    	RequestSpecification httpRequest= null;
	    getBusinessViewPage.verifySuccessForBusinessView( httpRequest,wirexLocale,ResponseCodes.SUCCESS,fieldsNameParam);
    }
    
    @Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify data for get fields by names for bright")
	public void verifyDataForFieldNameBright() throws InterruptedException, IOException{
    	RequestSpecification httpRequest= null;
		getBusinessViewPage.MatchDataForBusinessView( httpRequest,brightLocale, "item", fieldsNameParam);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify data for get fields by names for wirex")
	public void verifyDataForFieldNameWirex() throws InterruptedException, IOException{
		RequestSpecification httpRequest= null;
		getBusinessViewPage.MatchDataForBusinessView( httpRequest,wirexLocale, "item", fieldsNameParam);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify not found for wrong businessView name for bright")
	public void verifyNotFoundForWrongBusinessNameBright() throws InterruptedException, IOException{
		RequestSpecification httpRequest= null;
		getBusinessViewPage.verifyNotFound(httpRequest,brightLocale, ResponseCodes.NOT_FOUND, businessViewParam, "businessViewName");
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify not found for wrong businessView name for wirex")
	public void verifyNotFoundForWrongBusinessNameWirex() throws InterruptedException, IOException{
		RequestSpecification httpRequest= null;
		getBusinessViewPage.verifyNotFound(httpRequest,wirexLocale, ResponseCodes.NOT_FOUND, businessViewParam, "businessViewName");
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify not found for wrong businessView name/fields for bright")
	public void verifyNotFoundForWrongBusinessNamewithFieldsBright() throws InterruptedException, IOException{
		RequestSpecification httpRequest= null;
		getBusinessViewPage.verifyNotFound(httpRequest,brightLocale, ResponseCodes.NOT_FOUND, fieldsParam, "businessViewName");
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify not found for wrong businessView name/fields for wirex")
	public void verifyNotFoundForWrongBusinessNamewithFieldsWirex() throws InterruptedException, IOException{
		RequestSpecification httpRequest= null;
		getBusinessViewPage.verifyNotFound(httpRequest,wirexLocale, ResponseCodes.NOT_FOUND, fieldsParam, "businessViewName");
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify not found for wrong field name for bright")
	public void verifyNotFoundForWrongFieldNameBright() throws InterruptedException, IOException{
		RequestSpecification httpRequest= null;
		getBusinessViewPage.verifyNotFound(httpRequest,brightLocale, ResponseCodes.NOT_FOUND, fieldsNameParam, "fieldsName");
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify not found for wrong field name for wirex")
	public void verifyNotFoundForWrongFieldNameWirex() throws InterruptedException, IOException{
		RequestSpecification httpRequest= null;
		getBusinessViewPage.verifyNotFound(httpRequest,wirexLocale, ResponseCodes.NOT_FOUND, fieldsNameParam, "fieldsName");
	}

}
