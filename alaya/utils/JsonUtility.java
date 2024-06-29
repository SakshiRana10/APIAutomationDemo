package bright.api.alaya.utils;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class JsonUtility {

	

    public static JSONObject GetJsonFileData(String fileName) throws IOException {
        FileReader fileReader = null;
        JSONObject res = null;
        try {
            fileReader = new FileReader(fileName);
            res = (JSONObject) new JSONParser().parse(fileReader);
        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            if (fileReader != null) {
                fileReader.close();
            }
        }
        return res;
    }
    
    
    
    public static String getValueFrom (JSONObject jsonString,String parentKey,String key) throws IOException {
     
        JSONObject res = null;
        String value = null;
        
        
        
        
		return value;
       
    }
}
