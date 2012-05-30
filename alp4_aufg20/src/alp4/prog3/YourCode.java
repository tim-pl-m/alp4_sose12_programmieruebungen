package alp4.prog3;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import alp4.prog3.solution.TerminatedFlagWrapper;
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

		public boolean isValueIn(int value, int[] array)
		{
			for(int i = 0; i < array.length; i++)
			{
				if(array[i] == value)
					return true; 
			}
			
			return false; 
		}
		
		@Override
		public void process(int[][] image, int[][] label) {

//			int[][] image = {
//	    			{4,4,4,4,3,3}, 
//	    			{1,4,3,4,2,5}, 
//	    			{1,5,2,2,2,4}, 
//	    			{4,4,4,5,2,4}, 
//	    			{5,5,4,4,4,4}, 
//	    			{5,4,4,3,3,3}, 
//	    			}; 
//
////	    	int[][] image = {	
////			{3,3,4}, 
////			{5,1,2}, 
////			{1,1,2}, 
////			}; 
//			
//	    	int n = image.length; 
//	    	int[][] label = new int[n][n]; 
//	    	

//	    	System.out.println("Image: ");
//	    	printArray(image); 
	    	
	    	
	    	
			
			long startTime = System.currentTimeMillis();

			int numberOfWorkers = 2;

			int[] foundYStartPositions = new int[numberOfWorkers];
			int[] foundXStartPositions = new int[numberOfWorkers];
			int[] foundStartValues = new int[numberOfWorkers];

			TerminatedFlagWrapper terminatedFlagWrapper = new TerminatedFlagWrapper(false);
			
			
			CyclicBarrier barrier1 = new CyclicBarrier(numberOfWorkers + 1);
			CyclicBarrier barrier2 = new CyclicBarrier(numberOfWorkers + 1);
			
			
			// worker anlegen
			for(int i = 0; i < numberOfWorkers; i++)
			{
				Worker worker = new Worker(i, image, label, foundYStartPositions, foundXStartPositions, foundStartValues, terminatedFlagWrapper, barrier1, barrier2); 
				worker.start(); 
			}
			

			int counter = 0; 
			try {

				while (terminatedFlagWrapper.terminated == false) {
					int foundStartPositionForThreadNumber = 0;

					for (int y = image.length - 1; y >= 0 && foundStartPositionForThreadNumber < numberOfWorkers; y--) {
						for (int x = image[0].length - 1; x >= 0 && foundStartPositionForThreadNumber < numberOfWorkers; x--) {
							
							counter++; 
							
//							System.out.println();
//							System.out.println("y: " + y);
//							System.out.println("x: " + x);
//							System.out.println("foundStartPositionForThreadNumber: " + foundStartPositionForThreadNumber);
//
//							System.out.println("Image: ");
//					    	printArray(image); 
//					    	
//					    	System.out.println("Label: ");
//					    	printArray(label); 
							
							int value = image[y][x];

//							if (value == -1)
//								continue;

							if (value != -1 && !isValueIn(value, foundStartValues)) 
							{
								foundYStartPositions[foundStartPositionForThreadNumber] = y;
								foundXStartPositions[foundStartPositionForThreadNumber] = x;
								foundStartValues[foundStartPositionForThreadNumber] = value;
								
								foundStartPositionForThreadNumber++;
							}

						}
					}


//					if (foundStartPositionForThreadNumber == 0) {
//						terminatedFlagWrapper.terminated = true;
//						for (int y = image.length - 1; y >= 0; y--) {
//							for (int x = image[0].length - 1; x >= 0; x--) {
//								if (image[y][x] != -1)
//									terminatedFlagWrapper.terminated = false;
//							}
//						}
//					}
					
					
					if (foundStartPositionForThreadNumber == 0)
						terminatedFlagWrapper.terminated = true;


					barrier1.await(); 

					barrier2.await(); 
					

					
				}
			
			
			
			
			
			
			
			
			
			
			
//	    	int numberOfWorkers = 50;
//
//			CyclicBarrier barrier = new CyclicBarrier(numberOfWorkers + 1);
//
//			int sliceLength = image[0].length / numberOfWorkers;
//
//			for (int i = 0; i < numberOfWorkers; i++) {
//				int leftBound = i * sliceLength;
//
//				int rightBound = ((i + 1) * sliceLength) - 1;
//
//				Worker worker = new Worker("Thread_" + i, image, label, leftBound,
//						rightBound, barrier);
//				worker.start();
//			}
//			
//			try {
//				barrier.await();
//				
//				for(int i = numberOfWorkers-2; i > -1; i--)
//				{				
//					int leftBound = i * sliceLength;
//					int rightBound = ((i + 1) * sliceLength) - 1;
//					
//					for(int y = 0; y < label.length; y++)
//					{
//						if(image[y][rightBound+1] == image[y][rightBound])
//						{
//							int oldLabelValue = label[y][rightBound]; 
//							int newLabelValue = label[y][rightBound+1]; 
//							updateLabelValuesForSlice(label, leftBound, rightBound, oldLabelValue, newLabelValue); 
//						}
//					}
//				}
//		    	
//		    	
//		    	
//				
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (BrokenBarrierException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			long endTime = System.currentTimeMillis();

			long totalTime = endTime - startTime;

			System.out
					.println("Time for " + numberOfWorkers + ": " + totalTime);
			
			

//	    	System.out.println("Image after: ");
//	    	printArray(image); 
//	    	
//
//	    	System.out.println("Label: ");
//	    	printArray(label); 
	    	

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
	
}
