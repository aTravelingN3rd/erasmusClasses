import java.util.Random;
import java.util.Scanner;

public class RankSort
{
	public static void main(String[] args) throws InterruptedException
	{
		Scanner sc = new Scanner(System.in); 
		System.out.println("Enter the problem size:");
		int pbSize = sc.nextInt();
		System.out.println("Enter the number of threads:");
		int nbThreads = sc.nextInt();
		
		if(nbThreads <= 0){
			nbThreads = Runtime.getRuntime().availableProcessors();
		}
		
		//Initialisation
		int slice=pbSize/nbThreads;
		int reste=pbSize%nbThreads;
		int[] x = new int[pbSize];
		int[] xy= new int[pbSize]; //transition table
		int[] y = new int[pbSize];
		Random rand = new Random();
		
		//we put random number in the tab to increase the difficulty
		for(int i = 0; i < pbSize; i++)
			x[i] = rand.nextInt(1000); 
		
		//We use this line to get the total time of the calculation for our performance experiments
		long start = System.currentTimeMillis();
		
		//We create the threads
		RankSortThread threads[] = new RankSortThread[nbThreads];

		//We give each thread a part of the job
		for(int i = 0; i < nbThreads; i++) 
		{
			int ind=slice*i;
			
			if(i==nbThreads-1){
				threads[i] = new RankSortThread(x,xy,ind,ind+slice+reste);
			}else{
				threads[i] = new RankSortThread(x,xy,ind,ind+slice);
			}	
			threads[i].start();
		}

    //We wait all the threads ends
		for(int i = 0; i < nbThreads; i++) 
		{
			threads[i].join();
		}
		
		//Now the parallel part of this program is finish, we sort the x tab in the xy tab
		//But we have occurence problem, we don't treat this problem in the parallel part 
		//because it would take too much time to synchronized the threads each time we find an occurence.
		//here is my solution: we can see the occurences in the xy tab when there is a '0'
		//so unless it's the first number, each time we find a '0' we know it correspond to the number before
		//This way we can fill the y tab properly
		
		for(int i = 0; i < pbSize; i++) 
		{	
			if(i==0){
				y[i]=xy[i];
			}else{
				if(xy[i]==0){
					xy[i]=xy[i-1];
					y[i]=xy[i-1];
				}else{
					y[i]=xy[i];
				}			
			}
		}
		
		// Get elapsed time in milliseconds
		long elapsedTimeMillis = System.currentTimeMillis()-start;
		// Get elapsed time in seconds
		float elapsedTimeSec = elapsedTimeMillis/1000F; 
		
		for(int i = 0; i < pbSize; i++) 
		{	
			System.out.println(y[i]);
		}
	}
}
