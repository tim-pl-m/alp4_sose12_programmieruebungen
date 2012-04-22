package mergesort.sequential_recursive;

public class MainProg {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int[] arr = generateRandomIntArray(2000); 

		for(int val: arr)
		{
			System.out.println(val);
		}
		
		Sort mergeSortSeqRec = new MergeSortParallelRec();
		mergeSortSeqRec.sort(arr); 

		System.out.println("SORTIERT:");
		for(int val: arr)
		{
			System.out.println(val);
		}
		
				
		
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
	
}
