package mergesort.sequential_recursive;

import java.util.Arrays;

public class MainProg {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int[] array1 = generateRandomIntArray(4000); 
//		int[] array5 = generateRandomIntArray(40000); 
//		int[] array2 = generateRandomIntArray(400000); 
//		int[] array3 = generateRandomIntArray(4000000); 
//		int[] array4 = generateRandomIntArray(40000000);
		
//		int[] arr2 = copyArray(array1); 
		
//		for(int val: arr)
//		{
//			System.out.println(val);
//		}
		int [] valueArray = {4000};
//		int [] valueArray = {4000,40000,400000,4000000,40000000};
		//System.out.println(valueArray.length);
		Sort mergeSeq = new MergeSortSeqRec();
		Sort mergeParallel = new MergeSortParallelRec();
		//for (int i=1;i<=5;i++){
		
		System.out.println("(avg.time for sequential sorting,avg.time for parallel sorting,speed-up)");
		for (int n : valueArray){
			//iterate over array
			System.out.println("n= "+n+":");
			
			int x=10;
			//number of runs
			int[][] values = new int[2][x];
			for (int j=0;j<x;j++){
				// do runs
//				int [] array=generateRandomIntArray(n);
//				int[] arrayCopy;
//				values[0][j]=(int) mergeSeq.sort(arrayCopy=copyArray(array));
//				values[1][j]=(int) mergeParallel.sort(arrayCopy=copyArray(array));
				//ohne ArrayKopien
				values[0][j]=(int) mergeSeq.sort(generateRandomIntArray(n));
				values[1][j]=(int) mergeParallel.sort(generateRandomIntArray(n));
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
				System.out.println(sumS);
			}
			System.out.println(Arrays.deepToString(values));
			System.out.println(sS+","+hS);
			System.out.println(sP+","+hP);
			System.out.println(sumS+","+sumP);
			double avgS=(sumS-sS-hS)/(x-2);
			double avgP=(sumP-sP-hP)/(x-2);
			double SprintUp=((avgS/avgP)-1)*100;
			System.out.println("("+avgS+","+avgP+","+SprintUp+")");
		}
		
//		int n = 40000000;
		int n = 4000;
		for (int c=1; c<=16; c++){
			//iterate over array
			System.out.println(c+"Threads:");
			
			int x=10;
			//number of runs
			int[][] values = new int[2][x];
			for (int j=0;j<x;j++){
				// do runs
//				int [] array=generateRandomIntArray(n);
//				int[] arrayCopy;
//				values[0][j]=(int) mergeSeq.sort(arrayCopy=copyArray(array));
//				values[1][j]=(int) mergeParallel.sort(arrayCopy=copyArray(array));
				//ohne ArrayKopien
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
				System.out.println(sumS);
			}
			System.out.println(Arrays.deepToString(values));
			System.out.println(sS+","+hS);
			System.out.println(sP+","+hP);
			System.out.println(sumS+","+sumP);
			double avgS=(sumS-sS-hS)/(x-2);
			double avgP=(sumP-sP-hP)/(x-2);
			double SprintUp=((avgS/avgP)-1)*100;
			System.out.println("("+avgS+","+avgP+","+SprintUp+")");
		}
			
//		System.out.println("time for sequential sorting: " + mergeSeq.sort(array1));
		
		
//		System.out.println("ANFANG AUSGABE DER SEQUENTIELL SORTIERTEN MENGE");
//		for(int val: arr)
//		{
//			System.out.println(val);
//		}
//		System.out.println("ENDE AUSGABE DER SEQUENTIELL SORTIERTEN MENGE");


		
		
		
		
//		System.out.println("time for parallel sorting: " + mergeParallel.sort(array1));
		
		
//		System.out.println("ANFANG AUSGABE DER PARALLEL SORTIERTEN MENGE");
//		for(int val: arr2)
//		{
//			System.out.println(val);
//		}
//		System.out.println("ENDE AUSGABE DER PARALLEL SORTIERTEN MENGE");
		
		
//		for(int i = 0; i < arr.length; i++)
//		{
//			if(arr[i] != arr2[i])
//			{
//				System.out.println("ERGEBNISE-ARRAYS AN STELLE "+ i + " ungleich");
//			}
//
//			else
//			{
//				System.out.println("ok an stelle "+ i); 
//			}
//		}
		
	}
	
	static int[] generateRandomIntArray(int size)
	{
		int[] arr = new int[size]; 
		
		java.util.Random random = new java.util.Random();
		for(int i = 0; i < size; i++)
		{
			arr[i] = random.nextInt(Integer.MAX_VALUE);
//			arr[i] = random.nextInt(50);
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
