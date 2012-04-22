package mergesort.sequential_recursive;

public class MergeSortSeqRec implements Sort {

	public void sort(int[] array) {
		mergeSort(array, 0, array.length - 1); 
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

}
