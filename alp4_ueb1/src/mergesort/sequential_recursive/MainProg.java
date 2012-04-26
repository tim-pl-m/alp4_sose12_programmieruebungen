package mergesort.sequential_recursive;

import java.util.Arrays;

public class MainProg {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//define methods
		Sort mergeSeq = new MergeSortSeqRec();
		Sort mergeParallel = new MergeSortParallelRec();
		
//		int n = 40000000;
		int n = 40000;
		for (int c=1; c<=16; c++){
			//iterate over array
			System.out.println(c+" Threads: ");
			
			int x=10;
			//number of runs
			int[][] values = new int[2][x];
			for (int j=0;j<x;j++){
				// do runs

				values[0][j]=(int) mergeSeq.sort(generateRandomIntArray(n));
				values[1][j]=(int) mergeParallel.sortWithThreads(generateRandomIntArray(n),c);
			}
			int sS=values[0][0];
			int hS=sS;
			int sumS=0;
			int sP=values[1][0];
			int hP=sP;
			int sumP=0;
			for (int j=0;j<x;j++){
				//find the shortest and the longest time and give the average value
				if(values[0][j] < sS)
					sS=values[0][j];
				if(values[0][j] > hS)
					hS=values[0][j];
				if(values[1][j] < sP)
					sP=values[1][j];
				if(values[1][j] > hP)
					hP=values[1][j];
				sumS=sumS+values[0][j];
				sumP=sumP+values[1][j];

			}

			double avgS=(sumS-sS-hS)/(x-2);
			double avgP=(sumP-sP-hP)/(x-2);
			double SprintUp=((avgS/avgP)-1)*100;
			System.out.println("("+avgS+", "+avgP+", "+SprintUp+")");
		}

//		int [] valueArray = {4000};
		int [] valueArray = {4000,40000,400000,4000000,40000000};

		System.out.println("(avg.time for sequential sorting, avg.time for parallel sorting, speed-up)");
		for (int n1 : valueArray){
			//iterate over array
			System.out.println("n= "+n1+":");
			
			int x=10;
			//number of runs
			int[][] values = new int[2][x];
			for (int j=0;j<x;j++){
				// do runs

				values[0][j]=(int) mergeSeq.sort(generateRandomIntArray(n1));
				values[1][j]=(int) mergeParallel.sort(generateRandomIntArray(n1));
			}
			int sS=values[0][0];
			int hS=sS;
			int sumS=0;
			int sP=values[1][0];
			int hP=sP;
			int sumP=0;
			for (int j=0;j<x;j++){
				//find the shortest and the longest time and give the average value
				if(values[0][j] < sS)
					sS=values[0][j];
				if(values[0][j] > hS)
					hS=values[0][j];
				if(values[1][j] < sP)
					sP=values[1][j];
				if(values[1][j] > hP)
					hP=values[1][j];
				sumS=sumS+values[0][j];
				sumP=sumP+values[1][j];

			}

			double avgS=(sumS-sS-hS)/(x-2);
			double avgP=(sumP-sP-hP)/(x-2);
			double SprintUp=((avgS/avgP)-1)*100;
			System.out.println("("+avgS+", "+avgP+", "+SprintUp+")");
		}
		


	}
	
	static int[] generateRandomIntArray(int size)
	{
		int[] arr = new int[size]; 
		
		java.util.Random random = new java.util.Random();
		for(int i = 0; i < size; i++)
		{
			arr[i] = random.nextInt(Integer.MAX_VALUE);

		}
		
		return arr; 
	}
	
	static int[] copyArray(int[] arr)
	{
		int[] arr2 = new int[arr.length];
		
		for(int i = 0; i < arr.length; i++)
		{
			arr2[i] = arr[i]; 
		}
		return arr2; 
	}
	
}
