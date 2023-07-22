package dataProviders;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import utilities.LoggerLoad;



public class ExcelReader {

	private static XSSFSheet excelSheet;
	private static XSSFWorkbook excelWorkbook;
	private static XSSFCell cell;
	private static XSSFRow row;
	private static String sheetPath = ConfigReader.getProperty("test.data.path")+ConfigReader.getProperty("excel.name");
	
	

	private static void setExcelFile(String sheetName) throws IOException {
		   // LoggerLoad.logInfo("Getting sheets from the workbook.");
			System.out.println("sheetPath" + sheetPath );
			FileInputStream excelFile = new FileInputStream(new File(sheetPath).getAbsolutePath());
			excelWorkbook = new XSSFWorkbook(excelFile);
			System.out.println("sheetName" + sheetName );
			excelSheet = excelWorkbook.getSheet(sheetName);
	}

	private static int getDataRow(String dataKey, int dataColumn) {
		int rowCount = excelSheet.getLastRowNum();
		for(int row=0; row<= rowCount; row++){
			if(ExcelReader.getCellData(row, dataColumn).equalsIgnoreCase(dataKey)){
				return row;
			}
		}
		return 0;		
	}

	private static String getCellData(int rowNumb, int colNumb) {
		cell = excelSheet.getRow(rowNumb).getCell(colNumb);
		//LOG.info("Getting cell data.");
		if(cell.getCellType() == CellType.NUMERIC) {
			cell.setCellType(CellType.STRING);
		}
		String cellData = cell.getStringCellValue();
		return cellData;
	}

	public static void setCellData(String result, int rowNumb, int colNumb, String sheetPath,String sheetName) throws Exception{
		try{
			row = excelSheet.getRow(rowNumb);
			cell = row.getCell(colNumb);
			LoggerLoad.logInfo("Setting results into the excel sheet.");
			if(cell==null){
				cell = row.createCell(colNumb);
				cell.setCellValue(result);
			}
			else{
				cell.setCellValue(result);
			}

			LoggerLoad.logInfo("Creating file output stream.");
			FileOutputStream fileOut = new FileOutputStream(sheetPath + sheetName);
			excelWorkbook.write(fileOut);
			fileOut.flush();
			fileOut.close();

		}catch(Exception exp){
			LoggerLoad.logInfo("Exception occured in setCellData: "+exp);
		}
	}

	public static Map getData(String dataKey, String sheetName) throws Exception {
		Map dataMap = new HashMap<String, String>();
			setExcelFile(sheetName);
			int dataRow = getDataRow(dataKey.trim(), 0);
			//LoggerLoad.logInfo("Test Data Found in Row: "+dataRow);
			if (dataRow == 0) {
				throw new Exception("NO DATA FOUND for dataKey: "+dataKey);
			}
			int columnCount = excelSheet.getRow(dataRow).getLastCellNum();
			for(int i=0;i<columnCount;i++) {
				cell = excelSheet.getRow(dataRow).getCell(i);
				String cellData = null; 
				if (cell != null) {
					if(cell.getCellType() == CellType.NUMERIC) {
						cell.setCellType(CellType.STRING);
					}
					cellData = cell.getStringCellValue();
				}
				dataMap.put(excelSheet.getRow(0).getCell(i).getStringCellValue(), cellData);
			}
		return dataMap;
	}

	public static void main(String []args) throws Exception {
		Map<String,String> dataMap = new HashMap<String, String>();
		dataMap = getData("updateBooking21","sheet1");
		for(Map.Entry<String, String> data: dataMap.entrySet()) {
			LoggerLoad.logInfo(data.getKey()+ " ==> " + data.getValue());
		}
	}


}
