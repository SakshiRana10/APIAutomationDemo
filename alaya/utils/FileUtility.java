package bright.api.alaya.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeSuite;



public class FileUtility extends MainClassAlaya {


	public static  FileInputStream input;
	protected static Properties property;
	public static List<String> businessView = new ArrayList<>();

	
	public static List<String> GetAllFilesFromDirectory(String directoryPath) {
		File folder = new File(directoryPath);
		File[] listOfFiles = folder.listFiles();
		
		return listOfFiles != null ? Arrays.stream(listOfFiles).filter(File::isFile).map(File::getAbsolutePath).collect(Collectors.toList()) : Collections.EMPTY_LIST;
	}


		public static String getValueFromConfig(String key)  {
			Properties prop = null;
			try {
				prop = readPropertiesFile(Constants.configPath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String value=prop.getProperty(key);
			return value;
		}
		public static Properties readPropertiesFile(String fileName) throws IOException {
			FileInputStream fis = null;
			Properties prop = null;
			try {
				fis = new FileInputStream(fileName);
				prop = new Properties();
				prop.load(fis);
			} catch(FileNotFoundException fnfe) {
				fnfe.printStackTrace();
			} catch(IOException ioe) {
				ioe.printStackTrace();
			} finally {
				fis.close();
			}
			return prop;
		}
		
		public static void deleteDirectory(File file)
	    {
			if(file.exists()) {
	        for (File subfile : file.listFiles()) {
	  
	            if (subfile.isDirectory()) {
	                deleteDirectory(subfile);
	            }
	            subfile.delete();
	        }
	    }
	    }
		public static String getValueFromParamStoreFile(String key) throws IOException  {
			String value = null;
			Object paramObj = new Object();
			JSONParser parser = new JSONParser();
			try {
				paramObj = parser.parse(new FileReader(Constants.parameterStore));
				org.json.simple.JSONObject paramJsonObject = (org.json.simple.JSONObject)paramObj;
				value = paramJsonObject.get(key).toString();
			} catch (FileNotFoundException e) {
				logger.info("Parameter File was not found");
			} catch (IOException e) {
				logger.info("Parameter File does not exist");
			} catch (ParseException e) {	
			}
			return value;
		}

		
	}