package lab2;

import java.util.Random;
import java.util.Scanner;

public class ParallelMatrixMultiply {
	
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
		
		int slice=pbSize/nbThreads;
		int reste=pbSize%nbThreads;
		
		int[][] a = new int[pbSize][pbSize];
		int[][] b = new int[pbSize][pbSize];
		int[][] c = new int[pbSize][pbSize];
		Random rand = new Random();
		int i,j,k = 0;
		
		//Initialisation
		for (i = 0; i < pbSize; i++) 
			for (j = 0; j < pbSize; j++){
				a[i][j] = rand.nextInt(100);
				b[i][j] = rand.nextInt(100);
				c[i][j] = 0;
			}
		
		ThreadMM threads[] = new ThreadMM[nbThreads];
		
		long start = System.currentTimeMillis();
		
		//Give each thread a part of the job
		for(i = 0; i < nbThreads; i++) 
		{
			int ind=slice*i;
			
			if(i==nbThreads-1){
				threads[i] = new ThreadMM(a,b,c,ind,ind+slice+reste);
			}else{
				threads[i] = new ThreadMM(a,b,c,ind,ind+slice);
			}	
			threads[i].start();
		}

		for(i = 0; i < nbThreads; i++) 
		{
			threads[i].join();
		}
		
		// Get elapsed time in milliseconds
		long elapsedTimeMillis = System.currentTimeMillis()-start;
		// Get elapsed time in seconds
		float elapsedTimeSec = elapsedTimeMillis/1000F;

		System.out.println(elapsedTimeSec);
	}
} 
