package bright.api.alaya.test.getConfig;

import java.io.File;
import java.io.IOException;
import org.testng.annotations.Test;
import bright.api.alaya.pages.getConfig.getConfigPage;
import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.MainClassAlaya;
import io.restassured.specification.RequestSpecification;

public class getConfigTest extends MainClassAlaya{
	
	public static String brightLocale = "bright";
	public static String wirexLocale = "wirex";
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify artifacts data with getConfig response for Bright directories")
	public void getConfigBrightDirectories() throws InterruptedException, IOException{
		File[] brightfiles = new File(Constants.gitLocalDirectoryArtifacts+"/"+brightLocale).listFiles();
		RequestSpecification httpRequest= null;
		if(brightfiles.length==0)
			logger.info("brightFiles were empty for directory function");
		getConfigPage.getFilesInsideDirectory(httpRequest,brightLocale,brightfiles);
		logger.info("Verified Files Inside Directories for "+ brightLocale);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify artifacts data with getConfig response for Bright files")
	public void getConfigBrightFiles() throws InterruptedException, IOException{
		File[] brightfiles = new File(Constants.gitLocalDirectoryArtifacts+"/"+brightLocale).listFiles();
		RequestSpecification httpRequest= null;
		if(brightfiles.length==0)
			logger.info("brightFiles were empty for files function");
		getConfigPage.getFiles(httpRequest,brightLocale,brightfiles);
		logger.info("Verified Files for "+ brightLocale);
	}
		
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify artifacts data with getConfig response for Wirex directories")
	public void getConfigWirexDirectories() throws InterruptedException, IOException{
		File[] wirexfiles = new File(Constants.gitLocalDirectoryArtifacts+"/"+wirexLocale).listFiles();
		RequestSpecification httpRequest= null;
		if(wirexfiles.length==0)
			logger.info("wirexFiles were empty for directory function");
		getConfigPage.getFilesInsideDirectory(httpRequest,wirexLocale,wirexfiles);
		logger.info("Verified Files Inside Directories for "+ wirexLocale);
	}
	
	@Test ( groups = {"test", "dev","prod"}, priority = 0, enabled = true, description = "Verify artifacts data with getConfig response for Wirex files")
	public void getConfigWirexFiles() throws InterruptedException, IOException{
		File[] wirexfiles = new File(Constants.gitLocalDirectoryArtifacts+"/"+wirexLocale).listFiles();
		RequestSpecification httpRequest= null;
		if(wirexfiles.length==0)
			logger.info("wirexFiles were empty for files function");
		getConfigPage.getFiles(httpRequest,wirexLocale,wirexfiles);
		logger.info("Verified Files for "+ wirexLocale);
	}
	

}
