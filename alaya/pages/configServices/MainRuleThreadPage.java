package bright.api.alaya.pages.configServices;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.json.simple.parser.ParseException;

import bright.api.alaya.utils.Constants;
import io.restassured.specification.RequestSpecification;

public class MainRuleThreadPage implements Runnable   
{
    private List<String> name;
    
      
    public MainRuleThreadPage(List<String> s)
    {
        name = s;
        
    }
      


    public void run()
    {
    	RequestSpecification httpRequestRulesBrightData = null;
		try {

			getRuleUsageFromListForAPI(httpRequestRulesBrightData,Constants.BrightLocale,this.name);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public static void getRuleUsageFromListForAPI(RequestSpecification httpRequest, String locale,List<String> list) throws IOException, ParseException {

		try{
			for (String ruleUsage : list)   
			{  
				String ruleUsageId=ruleUsage;
				OneAdminRulesData.getFlagsFromAPI(httpRequest,ruleUsageId,locale);

			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
