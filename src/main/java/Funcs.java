import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class Funcs {

	
	static final String tmpsFold = "Tamplates";
	static final String outputFold = "output";
	static final String tamplate1 = "exportAll.xlsx";
	
	
	
	/**
	 * this finction write a String arr to the last row in sheet
	 * @param arr
	 * @param sheet
	 */
	public static void StringArrToLastRow(String[] arr, XSSFSheet sheet) {
		if(arr == null) return;

		for(int i=0; i<arr.length; i++){
			if(arr[i] == null)
				arr[i]="";
		}

		Row row = sheet.getRow(sheet.getLastRowNum());
		if(row==null)
			row = sheet.createRow(sheet.getLastRowNum());
		else row = sheet.createRow(sheet.getLastRowNum()+1);


		int i=0;
		Cell cell;
		for(i=0; i<arr.length; i++){
			cell = row.createCell(i);
			try {
				cell.setCellValue(arr[i]);
			}catch(Exception e) {e.printStackTrace();}
		}
	}
	
	
	/**
	 * 
	 * @return today date in format dd.MM.yyyy
	 */
	public static String todayString(){
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}



	public static void writeMatrix(String[][] sheet,exportType ex ,String newnName) {


		File out = new File(outputFold);
		if(!out.exists())
			out.mkdir();
		
		
		String tamplate="";
		if(ex == exportType.exportAll)
			tamplate = tamplate1;
		
		try {
			FileInputStream file = new FileInputStream(new File(tmpsFold+"/"+tamplate));

			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet asheet = workbook.getSheetAt(0);
			Cell cell = null;

			for(int i=0; i<sheet.length; i++){
				for(int j=0; j<sheet[i].length; j++){

					Row row = asheet.getRow(i);
					if(row.getCell(j) == null)
						row.createCell(j);
					row.getCell(j).setCellValue(sheet[i][j]);
				}
			}


			file.close();

			FileOutputStream outFile =new FileOutputStream(new File(outputFold+"/"+newnName+".xlsx"));
			workbook.write(outFile);
			outFile.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}




}
