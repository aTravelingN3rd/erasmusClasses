package lab3;
import java.util.Scanner;

public class ParallelPI {

	public static void main(String[] args) throws InterruptedException
	{
		Scanner sc = new Scanner(System.in); 	
		
		//For this program, instead of a problem size we have to choose a number of points to calculate pi
		//More there is points, more longer will be the calculation but it will be more precise
		System.out.println("Enter the number of point:");
		int nbPoint = sc.nextInt();
		System.out.println("Enter the number of threads:");
		int nbThreads = sc.nextInt();
		
		if(nbThreads <= 0){
			nbThreads = Runtime.getRuntime().availableProcessors();
		}
		
		int i;
		double h,pi,sum;
		int slice=nbPoint/nbThreads;
		int reste=nbPoint%nbThreads;
		h = 1/(double)nbPoint; //h is the distance from one point to an other
		pi=0;
		sum=0;
		
		ThreadPI threads[] = new ThreadPI[nbThreads];
		
		long start = System.currentTimeMillis();
		
		for(i = 0; i < nbThreads; i++) 
		{
			int ind=slice*i;
			
			if(i==nbThreads-1){
				threads[i] = new ThreadPI(h,ind,ind+slice+reste);
			}else{
				threads[i] = new ThreadPI(h,ind,ind+slice);
			}	
			threads[i].start();
		}

		for(i = 0; i < nbThreads; i++) 
		{
			threads[i].join();
			sum += threads[i].getSum();
		}
		
		pi=h*sum;
		
		System.out.println("Pi:"+pi);
		
		// Get elapsed time in milliseconds
		long elapsedTimeMillis = System.currentTimeMillis()-start;
		// Get elapsed time in seconds
		float elapsedTimeSec = elapsedTimeMillis/1000F;	
		System.out.println(elapsedTimeSec+"s");
		
		
	}
}
