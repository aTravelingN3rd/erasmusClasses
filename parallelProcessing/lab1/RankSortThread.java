class RankSortThread extends Thread
{
	private int[] table;
	private int[] table2;
	private int indexS;
	private int indexE;
	private int myNum;
	private int myPlace;

	// Constructor
	public SqrtThread(int[] array, int[] array2, int indS, int indE)
	{
		table = array;
		table2 = array2;
		indexS = indS;
		indexE = indE;
	}

	// Job of the thread - sort a part of x tab in the right order
	public void run()
	{
		for(int i=indexS; i<indexE ;i++){
			myNum=table[i];
			myPlace=0;
			for(int j=0; j<table.length;j++){
				if(myNum > table[j]){
					myPlace++;
				}
			}
			table2[myPlace]=myNum;	
		}
	}
}
