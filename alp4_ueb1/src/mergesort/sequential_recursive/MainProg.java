package mergesort.sequential_recursive;

public class MainProg {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int[] arr = generateRandomIntArray(4000); 
		
		int[] arr2 = copyArray(arr); 
		
		for(int val: arr)
		{
			System.out.println(val);
		}

		
		System.out.println("ANFANG AUSGABE DER SEQUENTIELL SORTIERTEN MENGE");
		Sort mergeSortSeqRec = new MergeSortSeqRec();
		mergeSortSeqRec.sort(arr); 

		System.out.println("SORTIERT SEQUENTIELL:");
		for(int val: arr)
		{
			System.out.println(val);
		}
		System.out.println("ENDE AUSGABE DER SEQUENTIELL SORTIERTEN MENGE");


		Sort mergeSortParallelRec = new MergeSortParallelRec();
		mergeSortParallelRec.sort(arr2); 

		System.out.println("ANFANG AUSGABE DER PARALLEL SORTIERTEN MENGE");
		System.out.println("SORTIERT PARALLEL:");
		for(int val: arr2)
		{
			System.out.println(val);
		}
		System.out.println("ENDE AUSGABE DER PARALLEL SORTIERTEN MENGE");
		
		
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
