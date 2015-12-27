class SqrtThread extends Thread
{
	private double [] table;
	private int indexS;
	private int indexE;

	public SqrtThread(double [] array, int indS, int indE)
	{
		table = array;
		indexS = indS;
		indexE = indE;
	}

	//The job of the thread
	public void run()
	{
		for(int i=indexS; i<indexE ;i++ ){
			table[i] = Math.sqrt(table[i]);
		}
	}
}
