package lab3;

public class ThreadPI extends Thread{

	private int indexS;
	private int indexE;
	private int myNum;
	private int myPlace;
	private int i;
	private double x;
	private double h;
	private double sum;

	public ThreadPI(double h, int indS, int indE)
	{
	  this.h= h;
		indexS = indS;
		indexE = indE;
	}

	// Job of the thread - each thread calculate a part of the sum
	public void run()
	{
		for (i = indexS; i < indexE; i++){
			x = (i + 0.5) * h;
			sum += 4.0 / (1.0 + x * x);
		}	
	}
	
	public double getSum() {
		return sum;
	}
	
}
