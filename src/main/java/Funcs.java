import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class Funcs {


	static final String tmpsFold = "Tamplates";
	static final String outputFold = "output";
	
	static final String tamplate1 = "exportAll.xlsx";
	static final String tamplate2 = "exportAllend.xlsx";
	static final String tamplate3 = "allForTable.xlsx";
	static final String tamplate4 = "oneInstitution.xlsx";


	public static boolean[][] ToRed(String[][] matrix){
		boolean[][] toRed = new boolean[matrix.length][matrix[0].length];
		String str;
		Date today = stringToDate(todayString());
		Date cont;
		for(int i=0; i<matrix.length; i++){
			for(int j=0; j<matrix[0].length; j++){
				str = matrix[i][j];
				if(str!=null && !str.isEmpty()){

					if(str.equals("חסר") || str.equals("לא תקין")){
						toRed[i][j]= true;
					}
					else{
						cont = stringToDate(matrix[i][j]);

						if(cont == null)
							continue;

						if(cont.after(today))
							toRed[i][j]= true;
					}


				}
				else toRed[i][j]= false;
			}
		}
		return toRed;
	}


	public static String[][] expiredDates(String[][] matrix){
		String[][] exMtr = new String[matrix.length][matrix[0].length];
		Date date;
		
		
		for(int i=0; i<matrix.length; i++){
			for(int j=0; j<matrix[0].length; j++){
				if(!matrix[i][j].isEmpty()){
					date = stringToDate(matrix[i][j]); 
					if(date!=null){
						double x=0;
						try{
							x= Double.parseDouble(matrix[3][j]);
						}catch(Exception e){e.printStackTrace();}

						exMtr[i][j] = addYears(dateToString(date), x);
					}
					else exMtr[i][j] = matrix[i][j];
				}	
			}
		}

		return exMtr;
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



	public static void writeMatrix(String[][] sheet, boolean[][] toRed,exportType ex ,String newName) {

		
		while(newName.indexOf('"') != -1){
			newName = newName.replaceAll('"'+"", "''");
		}
		while(newName.indexOf('/') != -1){
			newName = newName.replaceAll('/'+"", ".");
		}
		while(newName.indexOf('*') != -1){
			newName = newName.replaceAll('*'+"", "x");
		}
		while(newName.indexOf('?') != -1){
			newName = newName.replaceAll('?'+"", "");
		}
		
		
		File out = new File(outputFold);
		if(!out.exists())
			out.mkdir();


		String tamplate="";

		if(ex == exportType.exportAll)
			tamplate = tamplate1;
		if(ex == exportType.exportAllend)
			tamplate = tamplate2;
		if(ex == exportType.exportTable)
			tamplate = tamplate3;
		if(ex == exportType.exportInstitution)
			tamplate = tamplate4;




		try {
			FileInputStream file = new FileInputStream(new File(tmpsFold+"/"+tamplate));

			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet asheet = workbook.getSheetAt(0);


			XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();

			Font font = workbook.createFont();
			font.setFontHeight((short)(12*20));
			font.setColor(IndexedColors.RED.index);

			style.setBorderTop(BorderStyle.THIN);
			style.setBorderBottom(BorderStyle.THIN);
			style.setBorderLeft(BorderStyle.THIN);
			style.setBorderRight(BorderStyle.THIN);

			font.setFontName("Arial");

			style.setVerticalAlignment(VerticalAlignment.CENTER);

			style.setFont(font);


			for(int i=0; i<sheet.length; i++){
				for(int j=0; j<sheet[i].length; j++){
					if(sheet[i][j]== null || sheet[i][j].isEmpty())
						continue;

					Row row = asheet.getRow(i);
					if(row==null)
						asheet.createRow(i);

					row = asheet.getRow(i);
					if(row.getCell(j) == null)
						row.createCell(j);

					row.getCell(j).setCellValue(sheet[i][j]);

					if(toRed[i][j]){
						row.getCell(j).setCellStyle(style);
					}
				}
			}


			file.close();

			String newfileName =outputFold+"/"+newName;


			File f= new File(newfileName+".xlsx");
			if(f.exists()){
				boolean ok = false;
				int i=1;
				String tmp="";
				while(!ok){
					tmp = newfileName+"-"+i;
					f= new File(tmp+".xlsx");
					if(!f.exists())
						ok = true;
					//					else newfileName = newfileName.substring(0, newfileName.length()-2);
					i++;
				}
				newfileName = tmp;
			}

			FileOutputStream outFile =new FileOutputStream(new File(newfileName+".xlsx"));
			workbook.write(outFile);
			outFile.close();
			workbook.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	protected static String[][] expandMat(String[][] matrix){
		int max = 0;
		for(int i=0; i<matrix.length; i++){
			if(max < matrix[i].length)
				max = matrix[i].length;
		}

		String[][] newm = new String[matrix.length][max];

		for(int i=0; i<newm.length; i++){
			for(int j=0; j<newm[0].length; j++){
				if(j<matrix[i].length)
					newm[i][j] = matrix[i][j];
				else newm[i][j]="";
			}
		}
		return newm;	
	}


	public static String[][] cleanMatrix(int rows, int cols){
		String[][] matrix = new String[rows][cols];

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				matrix[i][j]="";
			}
		}

		return matrix;
	}

	public static String[][] fixDates(String[][] matrix) {

		String str ="";
		for(int i=0; i<matrix.length; i++){
			for(int j=0; j<matrix.length; j++){
				str= matrix[i][j];

				if(str!=null && !str.isEmpty() && str.length()>=8){

					try{
						char c = str.charAt(str.length()-5);

						String arr[] = str.split(c+"");
						if(arr.length==3){

							int day = Integer.parseInt(arr[0]);
							int month = Integer.parseInt(arr[1]);
							int year = Integer.parseInt(arr[2]);

							str = day+"."+month+"."+year;
						}

					}catch(Exception e){}

					matrix[i][j] = str;
				}

			}
		}
		return matrix;
	}



	/**
	 * 
	 * @param date String that present date with format dd.MM.yyy
	 * @return date in Date type.
	 */
	public static Date stringToDate(String date){
		if(date == null || date.isEmpty()) return null;

		Date dt=null ; 
		DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		try {
			dt = formatter.parse(date);
		} catch (ParseException e) {
		}

		return dt;
	}

	/**
	 * 
	 * @param date
	 * @return String that present the date in dd.MM.yyyy format 
	 */
	public static String dateToString(Date date){
		if(date == null) return "";

		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		String reportDate = df.format(date);

		return reportDate;
	}

	/**
	 * 
	 * @param date Date 
	 * @param days how many days to add
	 * @return update date.
	 */
	public static String addDays(String date , int days){
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(date));
		} catch (ParseException e) {e.printStackTrace();}
		c.add(Calendar.DATE, days);  // number of days to add
		date = sdf.format(c.getTime());  // dt is now the new date
		return date;
	}


	/**
	 * 
	 * @param date Date 
	 * @param days how many days to add
	 * @return update date.
	 */
	public static String addYears(String date , double years){

		if(years != (int) years){
			double half = years - (int) years;


			date = addDays(date, (int)(half*365));
		}

		int year = (int) Math.floor(years);


		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		Calendar c = Calendar.getInstance();

		try {
			c.setTime(sdf.parse(date));
		} catch (ParseException e) {e.printStackTrace();}
		c.add(Calendar.YEAR, year);  // number of days to add
		date = sdf.format(c.getTime());  // dt is now the new date
		return date;
	}




}
