package bright.api.alaya.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LookUpResourceMapping extends MainClassAlaya {
  
   


	/**
	 * Read Look Up name from Lookup Sheet via BusinessViewId
	 **/
    public static String readLookUpName(long businessViewID, String pickListId) throws IOException {
        File src = new File(Constants.lookUpMapping);
        FileInputStream fileInputStream = new FileInputStream(src);
        XSSFWorkbook xsf = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = xsf.getSheetAt(0);
        String name = null;
        
        
        int rowcount = sheet.getLastRowNum();
        for (int i = 1; i <= rowcount; i++) {
            long currBusinessViewId = Double.valueOf(sheet.getRow(i).getCell(0).getNumericCellValue()).longValue();
            long currPickListId = Double.valueOf(sheet.getRow(i).getCell(3).getNumericCellValue()).longValue();
            if (currBusinessViewId == businessViewID && String.valueOf(currPickListId).equalsIgnoreCase(pickListId)) {
                name = sheet.getRow(i).getCell(2).getStringCellValue();
                break;
            }
        }
        
     
       
        xsf.close();
        return name;
    }

    
   


	/**
	 * Read Resource Value from Resource Sheet using PickListID 
	 **/
	public static String readResourceValue(long pickListId) throws IOException {
		 File src = new File(Constants.resourceMapping);

	        FileInputStream fileInputStream = new FileInputStream(src);

	        XSSFWorkbook xsf = new XSSFWorkbook(fileInputStream);

	        XSSFSheet sheet = xsf.getSheetAt(0);
	        String name = null;
	        String resourcename = null;
	   
	        int rowcount = sheet.getLastRowNum();
	        for (int i = 0; i <= rowcount ; i++) {
	            if (sheet.getRow(i).getCell(0).getStringCellValue().equalsIgnoreCase(String.valueOf(pickListId))) {
	                resourcename = sheet.getRow(i).getCell(1).getStringCellValue();
	            }
	        }
	        
	     
	        xsf.close();
	        return resourcename;
	}
}

