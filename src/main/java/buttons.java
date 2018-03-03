import java.util.Date;


public class buttons extends Funcs{






	public static void exportAll_start(String[][] matrix){

		matrix = expandMat(matrix);
		matrix = fixDates(matrix);		

		matrix[2][2] = "נכון לתאריך";
		matrix[2][3] = Funcs.todayString();

		String name = "תוקף "+matrix[1][1]+" "+todayString();

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

		String name = "סיום תוקף "+matrix[1][1]+" "+todayString();

		Funcs.writeMatrix(matrix, toRed, exportType.exportAllend, name);
	}




	public static void exportTable(String[][] matrix){

		matrix = expandMat(matrix);
		matrix = fixDates(matrix);

		String[][] exMatrix = expiredDates(matrix);


		String[][] Table = new String[matrix.length*matrix[0].length][4];

		int k=0;
		for(int i=5; i<matrix.length; i++){
			for(int j=2; j<matrix[i].length; j++){

				if(matrix[i][j]!=null && !matrix[i][j].isEmpty() && !matrix[i][j].equals("לא רלוונטי")){
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
			if(Table[i][0]!=null && !Table[i][0].isEmpty())
				rows++;
		}






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

				if(allTable[i-1][5] == null || allTable[i][5] == null){
					if(allTable[i-1][5]==null)
						allTable[i-1][5] = allTable[i-1][4];

					if(allTable[i][5]==null)
						allTable[i][5] = allTable[i][4];
				}
				
				if(allTable[i-1][5] == null || allTable[i][5] == null)
					continue;
				
				
				if(allTable[i-1][5].isEmpty() || allTable[i][5].isEmpty()){
					if(allTable[i-1][5].isEmpty())
						allTable[i-1][5] = allTable[i-1][4];

					if(allTable[i][5].isEmpty())
						allTable[i][5] = allTable[i][4];
				}


				if(allTable[i-1][5].isEmpty() || allTable[i][5].isEmpty())
					continue;

				String s1 = allTable[i-1][5];
				String s2 = allTable[i][5];
				d1 = stringToDate(s1);
				d2 = stringToDate(s2);

				if(d1 != null && d2 != null){
					if(d1.before(d2)){
						swap(allTable, i, i-1);
						ok = true;
						continue;
					}
				}


				if(d1 != null && d2 == null){
					swap(allTable, i, i-1);
					ok = true;
					continue;
				}


				if(d1 == null && d2 == null){
					if((s1.equals("חסר")|| s1.equals("לא תקין")) && (!s2.equals("חסר")&&!s2.equals("לא תקיו"))){

						swap(allTable, i, i-1);
						ok = true;
						continue;
					}
				}

			}
		}	

		
		for(int i=5; i<allTable.length; i++){
			if(stringToDate(allTable[i][4])==null)
				allTable[i][5] = "";	
		}

		boolean[][] toRed = ToRed(allTable);

		for(int i=0; i<allTable.length; i++){

			if(toRed[i][5]){
				toRed[i][4] = true;
			}
			if(toRed[i][4]){
				toRed[i][3] = true;
				toRed[i][2] = true;	
			}

		}

 
		allTable[3][3] = Funcs.todayString();

		String name = "סיום תוקף "+matrix[1][1]+" "+todayString();

		Funcs.writeMatrix(allTable, toRed, exportType.exportTable, name);

	}







	public static void exportInst(String[][] matrix, int row){
		row--;

		matrix = expandMat(matrix);
		matrix = fixDates(matrix);

		String[][] exMtr = expiredDates(matrix);
		String[][] Table = new String[matrix[0].length][4];

		int i =0 ;
		for(int j=2; j<matrix[0].length; j++){
			if(matrix[row][j] == null || matrix[row][j].isEmpty() || matrix[row][j].equals("לא רלוונטי"))
				continue;

			Table[i][0] = matrix[4][j];
			Table[i][1] = matrix[3][j];
			Table[i][2] = matrix[row][j];
			Table[i][3] = exMtr[row][j];
			i++;
		}




		int n = Table.length;
		boolean ok = true;
		Date d1, d2;
		while(ok){
			ok = false;
			for(i=1; i<n-1; i++){

				if(Table[i-1][3] == null || Table[i][3] == null)
					continue;

				if(Table[i-1][3].isEmpty() || Table[i][3].isEmpty())
					continue;

				String s1 = Table[i-1][3];
				String s2 = Table[i][3];
				d1 = stringToDate(s1);
				d2 = stringToDate(s2);

				if(d1 != null && d2 != null){
					if(d1.before(d2)){
						swap(Table, i, i-1);
						ok = true;
						continue;
					}
				}


				if(d1 != null && d2 == null){
					swap(Table, i, i-1);
					ok = true;
					continue;
				}


				if(d1 == null && d2 == null){
					if((s1.equals("חסר")|| s1.equals("לא תקין")) && (!s2.equals("חסר")&&!s2.equals("לא תקיו"))){

						swap(Table, i, i-1);
						ok = true;
						continue;
					}
				}
			}
		}
		
		for(i=0; i<Table.length; i++){
			if(stringToDate(Table[i][2])==null)
				Table[i][3] = "";	
		}



		String[][] allTable = cleanMatrix(Table.length+7, Table[0].length+4);

		int count =0;
		for(i=0; i<Table.length; i++){
			if(Table[i][0] == null || Table[i][0].isEmpty()){
				count++;
				continue;
			}


			allTable[i+7-count][4] = Table[i][0];
			allTable[i+7-count][5] = Table[i][1];
			allTable[i+7-count][6] = Table[i][2];
			allTable[i+7-count][7] = Table[i][3];	
		}

		boolean[][] toRed = ToRed(allTable);

		for(i=0 ;i<toRed.length; i++){

			if(toRed[i][7])
				toRed[i][6] = true;

			if(toRed[i][6]){
				toRed[i][5] = true;
				toRed[i][4] = true;
			}
		}

		allTable[5][4] = matrix[row][1];
		allTable[3][4] = todayString();


		String name = "תוקף "+matrix[row][1]+" "+todayString();

		Funcs.writeMatrix(allTable, toRed, exportType.exportInstitution, name);
	}




	private static void swap(String[][] table, int i, int i2) {
		String[] tmp = table[i2];
		table[i2]=table[i];
		table[i]=tmp;

	}






}
