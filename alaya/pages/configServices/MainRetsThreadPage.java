package bright.api.alaya.pages.configServices;

import java.io.FileWriter;
import java.io.IOException;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.MainClassAlaya;
import io.restassured.specification.RequestSpecification;


public class MainRetsThreadPage extends MainClassAlaya implements Runnable {
	
	private String Type;
	private String Locale;
	
	 public MainRetsThreadPage(String type, String locale)
	    {
		 Locale = locale;
	     Type = type;  
	     
	    }
	  
	    public void run(){
	    	RequestSpecification httpRequestSessionID = null;
	    	RequestSpecification httpRequestMetaData = null;
        try {
		RetsAPIPage.getMetaData(httpRequestSessionID,httpRequestMetaData,this.Locale,this.Type,CommonUtilityMethods.getEnvironment());
			} 
        catch (InterruptedException | IOException e) 
        {
		e.printStackTrace();
			}
	    }
}
