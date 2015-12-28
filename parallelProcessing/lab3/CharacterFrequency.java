package lab3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;

class CharacterFrequency
{
	
	public static int[] freqs = new int[26];
	public static File filename;
	
	public static void main(String[] args) throws IOException
	{
		// Choose your file
        JFileChooser dialogue = new JFileChooser();    
        dialogue.showOpenDialog(null);
        filename=dialogue.getSelectedFile();
		int nbThreads = Runtime.getRuntime().availableProcessors();
		BufferedReader in = new BufferedReader(new FileReader(filename));	
		int nbLine = 0;
		String line;
		
		while((line = in.readLine()) != null){
			nbLine++;
		}
		
		int slice=nbLine/nbThreads;
		int reste=nbLine%nbThreads;
		
		ThreadSum threads[] = new ThreadSum[nbThreads];
		
		System.out.println(nbLine);
		// Give each thread a part
		for(int i = 0; i < nbThreads; i++){
			int ind=slice*i;
			
			if(i==nbThreads-1){
				threads[i] = new ThreadSum(ind,ind+slice+reste);
			}else{
				threads[i] = new ThreadSum(ind,ind+slice);
			}	
			threads[i].start();
		}

		// Join
		for (int i = 0; i < threads.length; i++) {
			try {
				threads[i].join();
			}
			catch (InterruptedException e) {
				System.err.println("this should not happen");
			}
		}
		
		System.out.println("The frequency of the characters are ");			
		for(int i =0;i<26;i++){
			System.out.println((char)('A'+i)+" : "+ freqs[i]);
		}
	}
	
	private static class ThreadSum extends Thread
	{
		int[] partial_freqs=new int[26];
		int indexS;
		int indexE;
		String line;
		BufferedReader buffer;
	
		// Constructor
		public ThreadSum(int indS, int indE) throws FileNotFoundException
		{
			buffer = new BufferedReader(new FileReader(filename));
			indexS = indS;
			indexE = indE;
		}

		//Job of the thread, count the number of characters in the line assigned to the thread
		//and stock it in the tab partial_freqs
		public void run()
		{
			for(int i = indexS; i < indexE ; i++){
				try {
					line=buffer.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}	

				for(char ch:line.toUpperCase().toCharArray()){
					if(Character.isLetter(ch)){
						partial_freqs[ch - 'A']++;
					}
				}
			}

			//Sync of the results of all the threads
			synchronized(this)
			{
				for(int i =0;i<26;i++){
					freqs[i] = freqs[i] + partial_freqs[i];
				}			
			}
		}
	}
}
		
