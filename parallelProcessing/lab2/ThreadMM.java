package lab2;

public class ThreadMM extends Thread{
	
	private int[][] matrix1;
	private int[][] matrix2;
	private int[][] matrix3;
	private int indexS;
	private int indexE;
	private int myNum;
	private int myPlace;
	private int i,j,k=0;

	public ThreadMM(int[][] m1, int[][] m2, int[][] m3, int indS, int indE)
	{
		matrix1 = m1;
		matrix2 = m2;
		matrix3 = m3;
		indexS = indS;
		indexE = indE;
	}

	// Job of the thread
	//We try to change the order of the loop execution (i,j,k). This is the more performant.
	public void run()
	{
		for (k=indexS; k<indexE; k++)
			for (i=0; i<matrix1.length; i++)
				for (j=0; j<matrix1.length; j++)
					matrix3[i][j] += matrix1[i][k]*matrix2[k][j];
	}
}
