package mergesort.sequential_recursive;

public class MergeSortParallelRec implements Sort {

	int[] array; 
	
	public void sort(int[] array) {
		this.array = array; 
		
		MergeSortThread mergeSortThread = new MergeSortThread(0, array.length -1);
		
		Thread th = new Thread(mergeSortThread); 
		th.start(); 
		
		try {
			th.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		
	}

	
	private class MergeSortThread implements Runnable
	{
		
		int lower, upper; 
		
		public MergeSortThread(int lower, int upper) {
			this.lower = lower; 
			this.upper = upper; 
		}
		

		public void run() {
//			System.out.println("RUN THREAD: " + this.hashCode());
			this.mergeSort(this.lower, this.upper); 
		}
		
		
		
		private void mergeSort(int lower, int upper)
		{
			int middle = (lower + upper) / 2; 
			
			if(lower < upper)
			{
				MergeSortThread mergeSortThreadLeft = new MergeSortThread(lower, middle);
				Thread thLeft = new Thread(mergeSortThreadLeft); 
				thLeft.start(); 

				MergeSortThread mergeSortThreadRight = new MergeSortThread(middle+1, upper);
				Thread thRight = new Thread(mergeSortThreadRight); 
				thRight.start(); 

				try {
					thLeft.join();
					thRight.join(); 
					merge(lower, middle, upper); 

				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
				
			}
		}
		
		private void merge(int lower, int middle, int upper)
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
	
	

}
