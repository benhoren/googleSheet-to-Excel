import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class main {



	public static void main(String[] args) {

		//        String spreadsheetId = "1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms";
		//		  String spreadsheetId ="1MiWyc1zHj3HzQPFGTX_DCkgeTJV4LGMEnSHFiKTuXf8";
		//        String spreadsheetId ="1VKsOyAEYUtoKc9BgTjE69-bqxlfpB25MNL7LT7cqhsw";


	}

	public static String[][] init(String url){

		String[][] sheet =null;

		String[] arr = url.split("/");
		String spreadsheetId="";
		for(int i=0; i<arr.length-1; i++){
			if(arr[i].equals("d")){
				spreadsheetId = arr[i+1];
				break;
			}
		}
		if(spreadsheetId.isEmpty())
			return null;

		try {
			sheet = Quickstart.getSheet(spreadsheetId);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return sheet;
	}




	public static int findRange(String[][] sheet, String range){
		if(range == null || range.isEmpty())
			return 0;

		try{
			int x=Integer.parseInt(range);
			return x; 
		}catch(Exception e){}

		for(int i=0; i<sheet.length; i++){
			if(sheet[i]!=null){
				if(sheet[i].length>=2){
					if(sheet[i][1]!=null){
						if(sheet[i][1].equals(range))
							return i+1;
					}
				}
			}
		} 
		return 0;
	}


	public static void start(String url, String range, exportType ex){
		mainScreen.addToLog("...");

		if(url.isEmpty()){
			mainScreen.addToLog("קישור לא תקין");
			return;
		}

		String[][] sheet = init(url);

		if(sheet == null){
			mainScreen.addToLog("קישור לא תקין");
			return;
		}

		if(ex == exportType.exportAll)
			buttons.exportAll_start(sheet);

		if(ex == exportType.exportAll)
			buttons.exportAll_end(sheet);

		if(ex == exportType.exportTable)
			buttons.exportTable(sheet);

		if(ex == exportType.exportInstitution){
			int row = findRange(sheet, range);

			if(row < 6 ){
				mainScreen.addToLog("מוסד לא תקין");
				return;
			}
			buttons.exportInst(sheet, row);
		}

		mainScreen.addToLog("סיים.");



	}




}
