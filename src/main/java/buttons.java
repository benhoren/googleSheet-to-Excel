import java.util.Date;


public class buttons extends Funcs{






	public static void exportAll_start(String[][] matrix){

		matrix = expandMat(matrix);
		matrix = fixDates(matrix);		

		matrix[2][2] = "נכון לתאריך";
		matrix[2][3] = Funcs.todayString();

		String name = matrix[1][1]+" "+todayString();

		boolean toRed[][] = new boolean[matrix.length][matrix[0].length];
		for(int i=0; i<toRed.length; i++){
			for (int j = 0; j < toRed.length; j++) {
				toRed[i][j] = false;
			}
		}

		Funcs.writeMatrix(matrix, toRed, exportType.exportAll, name);
	}




	public static void exportAll_end(String[][] matrix){

		matrix = expandMat(matrix);
		matrix = fixDates(matrix);

		matrix = expiredDates(matrix);
		
		boolean[][] toRed = ToRed(matrix);


		matrix[2][2] = "נכון לתאריך";
		matrix[2][3] = Funcs.todayString();

		String name = matrix[1][1]+" "+Funcs.todayString();

		Funcs.writeMatrix(matrix, toRed, exportType.exportAll, name);
	}




	public static void exportTable(String[][] matrix){

		matrix = expandMat(matrix);
		matrix = fixDates(matrix);

		String[][] exMatrix = expiredDates(matrix);


		String[][] Table = new String[matrix.length*matrix[0].length][4];

		int k=0;
		for(int i=5; i<matrix.length; i++){
			for(int j=2; j<matrix[i].length; j++){

				if(matrix[i][j]!=null && !matrix[i][j].isEmpty()){
					Table[k][0] = matrix[i][1];
					Table[k][1] = matrix[4][j];
					Table[k][2] = matrix[i][j];
					if(stringToDate(exMatrix[i][j])!=null)
						Table[k][3] = exMatrix[i][j];
					k++;
				}
			}
		}

		int rows=0;
		for (int i = 0; i < Table.length; i++) {
			System.out.println(Table[i][0]);
			if(Table[i][0]!=null && !Table[i][0].isEmpty())
				rows++;
		}

		
		System.err.println(rows);


		

		String[][] allTable = cleanMatrix(rows+5, Table[0].length+2);

		int count=0;
		for(int i=0; i<Table.length; i++){
			if(Table[i][0]==null || Table[i][0].isEmpty()){
				count++;
				continue;
			}
				
			for(int j=0; j<Table[0].length; j++){
				if(Table[i][j]!=null)
					allTable[i+5-count][j+2] = Table[i][j];
			}
		}

		
		
		int n = allTable.length;
		boolean ok = true;
		Date d1, d2;
		while(ok){
			ok = false;
			for(int i=5; i<n-1; i++){
//				if(allTable[i][5] == null || allTable[i][5].isEmpty())
//					continue;

				
				d1 = stringToDate(allTable[i-1][5]);
				d2 = stringToDate(allTable[i][5]);

				System.out.println(d1+" "+d2);
				
				
				if(d1==null)
					continue;

				if(d2 == null || d1.before(d2)){
					
					String[] tmp = allTable[i-1];
					allTable[i-1]=allTable[i];
					allTable[i]=tmp;
					
					ok=true;
				}
			}
		}	
		
		
		
		
		
		
		
		
		
		
		
		
		boolean[][] toRed = ToRed(allTable);

		for(int i=0; i<allTable.length; i++){
			if(toRed[i][5]){
				toRed[i][4] = true;
				toRed[i][3] = true;
				toRed[i][2] = true;	
			}
		}

		System.out.println(allTable.length+" "+allTable[0].length);
		System.err.println(toRed.length+" "+toRed[0].length);

		allTable[3][3] = Funcs.todayString();

		String name = matrix[1][1]+" "+Funcs.todayString();
		Funcs.writeMatrix(allTable, toRed, exportType.exportTable, name);

	}





}
