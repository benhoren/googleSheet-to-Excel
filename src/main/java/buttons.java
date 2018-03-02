
public class buttons {



	private static String[][] expandMat(String[][] matrix){
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


	public static void exportAll(String[][] matrix){

		matrix = expandMat(matrix);
		
		for(int i=0; i<matrix.length; i++){
			for(int j=0; j<matrix[i].length; j++)
				System.out.print(matrix[i][j]+",");
			System.out.println();
		}

		
		
		matrix[2][2] = "נכון לתאריך";
		matrix[2][3] = Funcs.todayString();
		
		String name = matrix[1][1]+" "+Funcs.todayString();
	
		exportType e = exportType.exportAll;
		Funcs.writeMatrix(matrix  ,e, name);
		
	}

}
