import java.util.Scanner;

public class ThreadParSqrt
{
	public static void main(String[] args) throws InterruptedException
	{
		Scanner sc = new Scanner(System.in); 
		System.out.println("Enter the problem size:");
		int pbSize = sc.nextInt();
		System.out.println("Enter the number of threads:");
		int nbThreads = sc.nextInt();
		
		//If the nb of threads is 0 or less, nbThreads take the value of the available number of processors
		if(nbThreads <= 0){
			nbThreads = Runtime.getRuntime().availableProcessors();
		}
		
		//Initialisation
		int slice=pbSize/nbThreads;
		int reste=pbSize%nbThreads;
		double[] a = new double[pbSize];
		for(int i = 0; i < pbSize; i++)
			a[i] = i; 

		// Creation of the threads
		SqrtThread threads[] = new SqrtThread[nbThreads];
		
		// Give each threads a part of the job
		for(int i = 0; i < nbThreads; i++) 
		{
			int ind=slice*i;
			
			if(i==nbThreads-1){
				threads[i] = new SqrtThread(a,ind,ind+slice+reste);
			}else{
				threads[i] = new SqrtThread(a,ind,ind+slice);
			}	
			threads[i].start();
		}
    
    //Wait for all the threads to end
		for(int i = 0; i < nbThreads; i++) 
		{
			threads[i].join();
		}
		
		// Print results
		for(int i = 0; i < pbSize; i++) 
		{
			System.out.println(a[i]);
		}
	}
}
