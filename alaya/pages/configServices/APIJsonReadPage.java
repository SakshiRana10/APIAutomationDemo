package bright.api.alaya.pages.configServices;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import bright.api.alaya.pages.configServices.OneAdminRulesData.rules;
import bright.api.alaya.utils.CommonUtilityMethods;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.JsonUtility;
import bright.api.alaya.utils.MainClassAlaya;

public class APIJsonReadPage extends MainClassAlaya {



	public static  HashMap<Object,Object> getFlagData(String locale,String ruleUsageId) throws IOException {


		HashMap<Object,Object> map=new HashMap<Object,Object>(); 
		String directory;
		if(locale.equalsIgnoreCase(Constants.BrightLocale)) {
			directory=Constants.BrightRulesFlagData;
		}
		else
			directory=Constants.WirexRulesFlagData;


		Path fileName= Path.of(directory+ruleUsageId+".json");
		String jsonFileResponse = Files.readString(fileName);
		JSONObject json = new JSONObject(jsonFileResponse.toString());
		JSONObject d = json.getJSONObject("d");
		JSONArray result = d.getJSONArray("results");
		JSONObject values = result.getJSONObject(0);

		Boolean executeOnlyIfChangedFlag= values.get("executeOnlyIfChangedFlag").toString().contains("0") ? false : true;
		Boolean  executeOnClientFlag= values.get("executeOnClientFlag").toString().contains("0") ? false : true;
		Boolean  executeOnServerFlag= values.get("executeOnServerFlag").toString().contains("0") ? false : true;
		Boolean  executeOnValidateFlag= values.get("executeOnValidateFlag").toString().contains("0") ? false : true;
		Boolean deletedFlag= values.get("deletedFlag").toString().contains("0") ? false : true;
		String  ruleId= values.get("ruleId").toString() ;
		String  description= values.get("description").equals(null) ? "" : values.get("description").toString() ;
		String  ruId= values.get("ruId").toString() ;


		map.put(rules.executeOnlyIfChangedFlag, executeOnlyIfChangedFlag);
		map.put(rules.executeOnClientFlag, executeOnClientFlag);
		map.put(rules.executeOnServerFlag, executeOnServerFlag);
		map.put(rules.executeOnValidateFlag, executeOnValidateFlag);
		map.put(rules.deletedFlag, deletedFlag);
		map.put(rules.ruleId, ruleId);
		map.put(rules.ruleUsageId,ruId);
		map.put(rules.description, description);

		return map;

	}



	public static  HashMap<Object,Object> getMainData(String locale,String fieldId,String ruleUsageId,String lovId) throws IOException, ParseException {


		HashMap<Object,Object> map=new HashMap<Object,Object>(); 

		String directory;
		if(locale.equalsIgnoreCase(Constants.BrightLocale)) {
			directory=Constants.BrightRulesMainData;
		}
		else
			directory=Constants.WirexRulesMainData;

		JSONObject jsonObject =readJsonResponse(directory+fieldId+".json",ruleUsageId);

		if(jsonObject==null) {
			return null;
		}
		else {


			String name =  jsonObject.get("name").toString() ;
			String callName =  jsonObject.get("callName").toString();
			String eventType=jsonObject.get("eventType").toString() ;
			String conditionalCallName = jsonObject.get("conditionalCallName").equals(null) ?  "": jsonObject.get("conditionalCallName").toString();
			String  ruleUsageType= jsonObject.get("ruleUsageType").toString() ;
			String  packageName= jsonObject.get("packageName").toString() ;
			String  itemNumber= jsonObject.get("itemNumber").toString() ;
			ArrayList<String> fieldIds=new ArrayList();
			JSONArray getArray = jsonObject.getJSONArray("ruleUsageArguments");

			ArrayList<String> value=new ArrayList();
			for(int i = 0; i < getArray.length(); i++)
			{
				JSONObject objects = getArray.getJSONObject(i);
				String  numValue= objects.get("numValue")!=null ? objects.get("numValue").toString() :null ;
				if(!numValue.equalsIgnoreCase("null")) {
					
					numValue=String.valueOf(numValue);
					BigDecimal bd = null;
					if(numValue.contains("E")) {
						
						numValue=new BigDecimal(numValue).toBigInteger().toString();
					
				}
					
					else if(numValue.substring(numValue.lastIndexOf("."), numValue.length()).equalsIgnoreCase(".0")) {
					numValue=	numValue.substring(0,numValue.lastIndexOf("."));
					}
				}
				String  charValue= objects.get("charValue")!=null ? objects.get("charValue").toString() : null ;
				String  dateValue= objects.get("dateValue")!=null ? objects.get("dateValue").toString(): null ;
				
				if(!objects.get("laId").equals(null)) {
					fieldIds.add(objects.get("laId").toString());
				}
				else {
				
					if(!objects.get("lcId").equals(null)) {

						String laid=readlaIdfromLcid(Constants.LOVMapping+lovId+".json",objects.get("lcId").toString());
						
							
						fieldIds.add(laid);
					}

					else {

						fieldIds.add(null);
					}
				}
				
				if(!numValue.equalsIgnoreCase("null")) {
					value.add(numValue);
				}
				else if(!charValue.equalsIgnoreCase("null")) {
					value.add(charValue);
				}

				else
					value.add(dateValue);

			}

			map.put(rules.CallName, callName);
			map.put(rules.Name, name);
			map.put(rules.eventType, eventType);
			map.put(rules.conditionalCallName, conditionalCallName.toString());
			map.put(rules.ruleUsageType, ruleUsageType);
			map.put(rules.packageName, packageName);
			map.put(rules.itemNumber, itemNumber);
			map.put(rules.noOfArguments,getArray.length());
			map.put(rules.fieldIds, fieldIds);
			map.put(rules.Values, value.toString());
			return map;
		}


	}

	public static JSONObject readJsonResponse(String fileName,String ruleUsage)  {
		Path file= Path.of(fileName);
		JSONObject jsonObject = null;
		String str = null;
		try {
			str = Files.readString(file);

			boolean isMatched=false;

			JSONArray jArray = (JSONArray) new JSONTokener(str).nextValue();
			for (int i = 0; i < jArray.length(); ++i) {
				JSONObject rec = jArray.getJSONObject(i);
				String ruleUsageId= rec.get("ruleUsageId").toString();
				if(ruleUsageId.equalsIgnoreCase(ruleUsage))
				{
					jsonObject=rec;
					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e);
		}

		return jsonObject;
	}
	
	public static String  readlaIdfromLcid(String fileName,String lcid)  {
		Path file= Path.of(fileName);
		JSONObject jsonObject = null;
		String str = null;
		String laid=null;
		try {
			str = Files.readString(file);

			boolean isMatched=false;

			JSONArray jArray = (JSONArray) new JSONTokener(str).nextValue();
			for (int i = 0; i < jArray.length(); ++i) {
				JSONObject rec = jArray.getJSONObject(i);
				String lcidInResp= rec.get("LC_ID").toString();
				if(lcidInResp.equalsIgnoreCase(lcid))
				{
					 laid=rec.get("LA_ID").toString();
					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e);
		}

		return laid;
	}
}
