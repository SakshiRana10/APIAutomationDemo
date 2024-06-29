package bright.api.alaya.pages.configServices;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.json.simple.parser.ParseException;

import bright.api.alaya.utils.Constants;
import io.restassured.specification.RequestSpecification;

public class LogicalAttributeRuleThreadPage implements Runnable   
{
    private List<String> name;
    
      
    public LogicalAttributeRuleThreadPage(List<String> s)
    {
        name = s;
        
    }
     

    public void run()
    {
    	RequestSpecification httpRequestRulesBrightData = null;
		try {

			getLcidAndLaidMapping(httpRequestRulesBrightData,this.name);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public static void getLcidAndLaidMapping(RequestSpecification httpRequest,List<String> list) throws IOException, ParseException {

		try{
			for (String lovid : list)   
			{  
				String Lov=lovid;
				RulesDataPage.writeLcidMappingFromAPI(httpRequest,Lov);

			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}