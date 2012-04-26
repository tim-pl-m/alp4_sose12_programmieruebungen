package mergesort.sequential_recursive;

// the following code is based on our sequential solution, modified on a few places for the implementation of the multi-threading

public class MergeSortParallelRec implements Sort {

	// defining the static, global variables for the maximum of used threads and the counter for the currently created threads
	int MAX_THREAD_NUMBER; 
	Integer used_thread_number = 0; 
	
	// thread-safe accessor-method for the counter
	synchronized int incUsedThreadNumber()
	{
		return used_thread_number++; 
	}
	
	
	
	int[] array; 
	
	public long sort(int[] array) {
		this.array = array; 
		
		// time measurement code
		long time=System.currentTimeMillis();
		
		MergeSortThread mergeSortThread = new MergeSortThread(0, array.length -1);
		
		// the first root-thread is also incrementing the number of used threads
		incUsedThreadNumber(); 

		// create and start the root-thread
		Thread th = new Thread(mergeSortThread); 
		th.start(); 
		
		try {
			th.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		return (System.currentTimeMillis()-time);
	}
	
	public long sortWithThreads(int[] array, int c) {
		this.MAX_THREAD_NUMBER = c;
		this.array = array; 
		long time=System.currentTimeMillis();
		
		MergeSortThread mergeSortThread = new MergeSortThread(0, array.length -1);
		
		incUsedThreadNumber(); 
		Thread th = new Thread(mergeSortThread); 
		th.start(); 
		
		try {
			th.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		return (System.currentTimeMillis()-time);
	}
	
	
	// moving the sort and merging code into a threadable class, so it can be run concurrently
	private class MergeSortThread implements Runnable
	{
		
		int lower, upper; 
		
		public MergeSortThread(int lower, int upper) {
			this.lower = lower; 
			this.upper = upper; 
		}
		

		public void run() {
			System.out.println("RUN THREAD: " + this.hashCode());
			this.mergeSort(this.lower, this.upper); 
		}
		
		
		
		private void mergeSort(int lower, int upper)
		{
			int middle = (lower + upper) / 2; 
			
			if(lower < upper)
			{
				Thread thLeft = null;  

				// check that we are still allowed to create new threads
				if(incUsedThreadNumber() < MAX_THREAD_NUMBER)
				{
					MergeSortThread mergeSortThreadLeft = new MergeSortThread(lower, middle);
					thLeft = new Thread(mergeSortThreadLeft); 
					thLeft.start(); 
				}
				
				// otherwise: use the sequential, non-threaded version (with direct call of mergeSort on the same object without creating and starting a new thread-object)  
				else
				{
					this.mergeSort(lower, middle); 
				}
				
				
				// AGAIN FOR THE OTHER recursive mergeSort-call:
				// check that we are still allowed to create new threads
				Thread thRight = null;  
				if(incUsedThreadNumber() < MAX_THREAD_NUMBER)
				{
					MergeSortThread mergeSortThreadRight = new MergeSortThread(middle+1, upper);
					thRight = new Thread(mergeSortThreadRight); 
					thRight.start(); 
				}

				// AGAIN FOR THE OTHER recursive mergeSort-call: 
				// otherwise: use the sequential, non-threaded version (with direct call of mergeSort on the same object without creating and starting a new thread-object)  
				else
				{
					this.mergeSort(middle+1, upper); 
				}
				
				

				// if there are threads: waiting for their termination
				try {
					if(thLeft != null)
						thLeft.join();
					
					if(thRight != null)
						thRight.join(); 
					
					merge(lower, middle, upper); 

				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
				
			}
		}
		
		// the merge-method is not modified, because it is not running concurrently
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
