package bright.api.alaya.utils;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.junit.Assert;
import org.testng.util.Strings;

import freemarker.log.Logger;

public class DatabaseUtility extends MainClassAlaya {
	public static Connection conn = null;

	public static Connection CreateConnection() {

		String hostname,port,Sid,password,username;
		String dbCredentials = null;
		dbCredentials = CommonUtilityMethods.fetchXApiKey("cornerstoneCredentials");
		
		if(Strings.isNullOrEmpty(dbCredentials)) {
			logger.info("DB credentials Not Found in ParamStore. Checking in config");
			hostname = FileUtility.getValueFromConfig("hostname");
			username=FileUtility.getValueFromConfig("username");
			password=FileUtility.getValueFromConfig("password");
			port=FileUtility.getValueFromConfig("port");
			Sid=FileUtility.getValueFromConfig("sid");
		}

		else {
			logger.info("DB credentials Found in ParamStore");
			JSONObject jsonObject = new JSONObject(dbCredentials);
			hostname = jsonObject.getString("hostname");
			port=Integer.toString(jsonObject.getInt("port"));
			Sid=jsonObject.getString("sid");
			username=jsonObject.getString("user");
			password=jsonObject.getString("password");
		}


		try {

			Class.forName("oracle.jdbc.OracleDriver");
			String dbURL = "jdbc:oracle:thin:"+username+"/"+password+"@"+hostname+":"+port+":"+Sid;
			conn = DriverManager.getConnection(dbURL);
			if (conn != null) {
				logger.info("DB connection done Successfully");
			}

		} catch (Exception e) {

			Assert.fail("Not able to Make Database Connection");
			e.printStackTrace();
		}

		finally {

		}
		return conn;

	}


	public  static void WriteToResourceFile(Connection conn) {

		Statement statement = null;
		ResultSet ResultSet = null;


		try {
			statement = conn.createStatement();
			ResultSet = statement.executeQuery("SELECT lov.lov_id,lov.name AS businessViewName,lo.name AS resourceName FROM LMS_CFG.logical_object_views lov\n"
					+ "JOIN LMS_CFG.LOGICAL_OBJECTS lo ON LOV.lo_id = lo.LO_ID \n"
					+ "join LMS_CFG.locale_logical_object_views llov on llov.lov_id=lov.lov_id\n"
					+ "join LMS_CFG.locales l on l.loc_id=llov.loc_id\n"
					+ "where llov.loc_id in (50000969238, 200003943665,10000065721) \n"

				+ " ");


			writeDataToResourceFile(ResultSet);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public static void WriteLookUpFile(Connection conn) {

		Statement statement = null;
		ResultSet ResultSet = null;

		try {
			statement = conn.createStatement();


			ResultSet = statement.executeQuery("select DISTINCT lov_pl.lov_id AS BusinessViewId,lov.name AS BusinessViewName,lov_pl.name AS LOVPicklistName,lov_pl.PCK_ID AS PicklistId,p.NAME AS PicklistName from \n"
					+ "LMS_CFG.LOV_PICKLISTS lov_pl \n"
					+ "JOIN LMS_CFG.picklists p ON p.PCK_ID = LOV_Pl.pck_id  \n"
					+ "JOIN LMS_CFG.LOGICAL_OBJECT_VIEWS lov ON lov.lov_id = lov_pl.LOV_ID \n"
					+ "join LMS_CFG.locale_logical_object_views llov on llov.lov_id=lov.lov_id\n"
					+ "join LMS_CFG.locales l on l.loc_id=llov.loc_id\n"
					+ "where llov.loc_id in (50000969238, 200003943665,10000065721)\n"
					+"");

			writeDataToLookUpFile(ResultSet);


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}



	private static void writeDataToLookUpFile(ResultSet result) throws SQLException {
		int rowCount = 1;

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Lookup");
		Row headerRow = sheet.createRow(0);

		Cell headerCell = headerRow.createCell(0);
		headerCell.setCellValue("BUSINESSVIEWID");

		headerCell = headerRow.createCell(1);
		headerCell.setCellValue("BUSINESSVIEWNAME");

		headerCell = headerRow.createCell(2);
		headerCell.setCellValue("LOVPICKLISTNAME");

		headerCell = headerRow.createCell(3);
		headerCell.setCellValue("PICKLISTID");

		headerCell = headerRow.createCell(4);
		headerCell.setCellValue("PICKLISTNAMe");

		while (result.next()) {
			long businessViewID = result.getLong("BUSINESSVIEWID");
			String businessViewName = result.getString("BUSINESSVIEWNAME");
			String lovPickListname = result.getString("LOVPICKLISTNAME");
			long picklistID = result.getLong("PICKLISTID");
			String pickListName = result.getString("PICKLISTNAME");

			Row row = sheet.createRow(rowCount++);

			int columnCount = 0;
			Cell cell = row.createCell(columnCount++);
			cell.setCellValue(businessViewID);

			cell = row.createCell(columnCount++);
			cell.setCellValue(businessViewName);

			cell = row.createCell(columnCount++);
			cell.setCellValue(lovPickListname);

			cell = row.createCell(columnCount++);
			cell.setCellValue(picklistID);

			cell = row.createCell(columnCount);
			cell.setCellValue(pickListName);

		}
		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(Constants.lookUpMapping);
			workbook.write(outputStream);
			workbook.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	} 

	private static void writeDataToResourceFile(ResultSet result) throws SQLException {
		int rowCount = 1;

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("ResourceMapping");
		Row headerRow = sheet.createRow(0);

		Cell headerCell = headerRow.createCell(0);
		headerCell.setCellValue("LOV_ID");

		headerCell = headerRow.createCell(1);
		headerCell.setCellValue("RESOURCENAME");


		while (result.next()) {
			String lovId = result.getString("LOV_ID");
			String resourceName = result.getString("RESOURCENAME");


			Row row = sheet.createRow(rowCount++);

			int columnCount = 0;
			Cell cell = row.createCell(columnCount++);
			cell.setCellValue(lovId);

			cell = row.createCell(columnCount++);
			cell.setCellValue(resourceName);


		}
		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(Constants.resourceMapping);
			workbook.write(outputStream);
			workbook.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	} 
}