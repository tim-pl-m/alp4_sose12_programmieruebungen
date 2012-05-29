package alp4.prog3;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import alp4.prog3.solution.Worker;

/* 
 This file is just a proposal, you may change it at your convenience.
 Handed in "Framework.java" files will not be considered and you also
 don't need to hand it in again. What this means is simply:
 "Don't change the given framework!"
 */

public class YourCode {

	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.out
					.println("[ERROR]: Requires exactly two arguments, the input file and the output file!");
			return;
		}

		Framework.processLabeling(args[0], args[1], new MyLabeler());
	}

	private static class MyLabeler implements ILabeler {
		@Override
		public void process(int[][] image2, int[][] label2) {
			

	    	int[][] image = {
	    			{4,4,4,4,3,3}, 
	    			{1,4,3,4,4,4}, 
	    			{1,5,2,2,2,4}, 
	    			{4,4,4,5,2,4}, 
	    			{5,5,4,4,4,4}, 
	    			{5,4,4,3,4,3}, 
	    			}; 

	    	int n = image.length; 
	    	int[][] label = new int[n][n]; 
	    	
	    	
	    	int numberOfWorkers = 3;

			CyclicBarrier barrier = new CyclicBarrier(numberOfWorkers + 1);

			int sliceLength = image[0].length / numberOfWorkers;

			for (int i = 0; i < numberOfWorkers; i++) {
				int leftBound = i * sliceLength;

				int rightBound = ((i + 1) * sliceLength) - 1;

				Worker worker = new Worker("Thread_" + i, image, label, leftBound,
						rightBound, barrier);
				worker.start();
			}
			
			try {
				barrier.await();
				
				for(int i = numberOfWorkers-2; i > -1; i--)
				{				
					int leftBound = i * sliceLength;
					int rightBound = ((i + 1) * sliceLength) - 1;
					
					for(int y = 0; y < label.length; y++)
					{
						if(image[y][rightBound+1] == image[y][rightBound])
						{
							int oldLabelValue = label[y][rightBound]; 
							int newLabelValue = label[y][rightBound+1]; 
							updateLabelValuesForSlice(label, leftBound, rightBound, oldLabelValue, newLabelValue); 
						}
					}
				}
				


		    	System.out.println("Image: ");
		    	printArray(image); 

		    	System.out.println("Label: ");
		    	printArray(label); 
		    	
		    	
		    	
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		private void updateLabelValuesForSlice(int[][] label, int leftBound, int rightBound,
				int oldLabelValue, int newLabelValue) {
			
			for(int y = 0; y < label.length; y++)
			{
				for(int x = leftBound; x <= rightBound; x++)
				{
					if(label[y][x] == oldLabelValue)
						label[y][x] = newLabelValue; 
				}
			}
		}
	}
	

    public static void printArray(int[][] arr)
    {
    	int n = arr.length; 
    	
    	for(int y = 0; y < n; y++)
    	{
    		for(int x = 0; x < n; x++)
    		{
    			System.out.print(arr[y][x] + " ");
    		}
    		System.out.println();
    	}
    }

}
