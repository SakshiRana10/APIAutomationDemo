package bright.api.alaya.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.GetTopicAttributesRequest;
import software.amazon.awssdk.services.sns.model.GetTopicAttributesResponse;
import software.amazon.awssdk.services.sns.model.SnsException;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;
import software.amazon.awssdk.services.ssm.model.SsmException;

public class ExcelManager extends MainClassAlaya {

	private static Logger logger = Logger.getLogger(ExcelManager.class);
	public static String path;
	public FileInputStream fis = null;
	public FileOutputStream fileOut = null;
	private XSSFWorkbook workbook = null;
	private static Sheet sheet = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;
	static Workbook book;

	public ExcelManager(String path) {

		this.path = path;
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method will read the test data from Excel and feeds it to tests.
	 */
	public static Object[][] getTestData(String sheetName) {

		FileInputStream file = null;

		try {

			file = new FileInputStream(path);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			book = WorkbookFactory.create(file);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		sheet = book.getSheet(sheetName);

		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		System.out.println(sheet.getLastRowNum());
		System.out.println(sheet.getRow(0).getLastCellNum());

		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
				data[i][k] = sheet.getRow(i + 1).getCell(k).toString();
				System.out.println(data[i][k].toString());
			}

		}

		logger.info("Excel data is read.");
		return data;
	}
	
	
	public static SsmClient createSsmClient(Region region) {

        SsmClient  ssmClient = SsmClient.builder().region(region).build();
        logger.info("SSM client object is:"+ssmClient);
        return ssmClient;
    }
    
    public static String getParaValue(SsmClient ssmClient, String paraName) {
        String paramValue = "";
        try {
            GetParameterRequest parameterRequest = GetParameterRequest.builder()
                .name(paraName).withDecryption(true)
                .build();
            logger.info("parameterRequest is:"+parameterRequest);
            GetParameterResponse parameterResponse = ssmClient.getParameter(parameterRequest);
            paramValue = parameterResponse.parameter().value();
            logger.info("paramValue is:"+paramValue);
            

        } catch (SsmException e) {
            System.err.println(e.getMessage());
        }
        return paramValue;
   }
	
}