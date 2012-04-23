package mergesort.sequential_recursive;

public class MainProg {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int[] array1 = generateRandomIntArray(4000); 
		int[] array5 = generateRandomIntArray(40000); 
		int[] array2 = generateRandomIntArray(400000); 
		int[] array3 = generateRandomIntArray(4000000); 
		int[] array4 = generateRandomIntArray(40000000);
		
//		int[] arr2 = copyArray(array1); 
		
//		for(int val: arr)
//		{
//			System.out.println(val);
//		}

		int [] valueArray = {4,5,6};
		Sort mergeSeq = new MergeSortSeqRec();
		//for (int i=1;i<=5;i++){
		for (int i : valueArray){
			//iterate over array
			System.out.println(i);
			for (int j=1;j<=10;j++){
				//measure 10 runs
				
				//remove the shortest and the longest time and give the average value
			}
		}
			
		System.out.println("time for sequential sorting: " + mergeSeq.sort(array1));
		
		
//		System.out.println("ANFANG AUSGABE DER SEQUENTIELL SORTIERTEN MENGE");
//		for(int val: arr)
//		{
//			System.out.println(val);
//		}
//		System.out.println("ENDE AUSGABE DER SEQUENTIELL SORTIERTEN MENGE");


		
		
		Sort mergeParallel = new MergeSortParallelRec();
		
		System.out.println("time for parallel sorting: " + mergeParallel.sort(array1));
		
		
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
