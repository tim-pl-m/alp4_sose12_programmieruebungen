package mergesort.sequential_recursive;


// the following code is a nearly one-to-one transformation of the provided pseudo-code to the corresponding java-version

public class MergeSortSeqRec implements Sort {

	public long sort(int[] array) {
		long time=System.currentTimeMillis();
		mergeSort(array, 0, array.length - 1);
		return (System.currentTimeMillis()-time);
	}

	private void mergeSort(int[] array, int lower, int upper)
	{
		int middle = (lower + upper) / 2; 
		
		if(lower < upper)
		{
			mergeSort(array, lower, middle); 
			mergeSort(array, middle+1, upper); 
			merge(array, lower, middle, upper); 
		}
	}
	
	private void merge(int[] array, int lower, int middle, int upper)
	{
		// the tmpArray is created separately with every new call of merge
		int[] tmpArray = new int[array.length]; 
		// also, all of the following variables are independent 
		int left = lower; 
		int right = middle+1;
		int i = lower; 

		
		while(true)
		{
			
			if(left <= middle && ((right <= upper && array[left] < array[right]) || right > upper ))
			{
				tmpArray[i] = array[left];
				i++;
				left++; 
			}
			
			else if(right <= upper && ((left <= middle && array[right] <= array[left]) || left > middle))
			{
				tmpArray[i] = array[right]; 
				i++; 
				right++; 
			}
			
			else
			{
				break; 
			}
		}
		
		for(int j = lower; j <= upper; j++)
		{
			array[j] = tmpArray[j]; 
		}
		
	}

	public long sortWithThreads(int[] array, int c) {
		// TODO Auto-generated method stub
		return 0;
	}

}
