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
		public void process(int[][] image, int[][] label) {
			
	    	int numberOfWorkers = 50;

			CyclicBarrier barrier = new CyclicBarrier(numberOfWorkers + 1);

			int sliceLength = image[0].length / numberOfWorkers;

			for (int i = 0; i < numberOfWorkers; i++) {
				int leftBound = i * sliceLength;

				int rightBound;
				rightBound = ((i + 1) * sliceLength) - 1;

				Worker worker = new Worker("Thread_" + i, image, label, leftBound,
						rightBound, barrier);
				worker.start();
			}
			
			try {
				barrier.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
