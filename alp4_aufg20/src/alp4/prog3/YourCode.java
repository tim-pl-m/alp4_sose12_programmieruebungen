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
			System.out.println("[ERROR]: Requires exactly two arguments, the input file and the output file!");
			return;
		}

		// Framework.processLabeling(args[0], args[1], new MyLabeler());
		// ${workspace_loc}"/alp4_aufg20/pics/Spiral.png"
		// ${workspace_loc}"/alp4_aufg20/pics/Spiral.label.png"
		// debug
		String workspace = "C:/Users/tim/Desktop/git/alp4_sose12_programmieruebungen";
		Framework.processLabeling(workspace + "/alp4_aufg20/pics/8.png", workspace + "/alp4_aufg20/pics/test.label.png", new MyLabeler());
	}

	private static class MyLabeler implements ILabeler {

		public boolean isValueIn(int value, int[] array) {
			for (int i = 0; i < array.length; i++) {
				if (array[i] == value)
					return true;
			}

			return false;
		}

		@Override
		public void process(int[][] image, int[][] label) throws InterruptedException, BrokenBarrierException {

			// zum testen
			int[][] imageTest = { { 4, 4, 4, 4, 3, 3 }, { 1, 4, 3, 4, 2, 5 }, { 1, 5, 2, 2, 2, 4 }, { 4, 4, 4, 5, 2, 4 }, { 5, 5, 4, 4, 4, 4 }, { 5, 4, 4, 3, 3, 3 }, };

			image = imageTest;

			// // int[][] image = {
			// // {3,3,4},
			// // {5,1,2},
			// // {1,1,2},
			// // };

			int n = image.length;
			label = new int[n][n];

			System.out.println("array: " + image[0].length + "*" + image.length);

			// debug
			System.out.println("Image: ");
			printArray(image);

			System.out.println("start Labeling ");

			long startTime = System.currentTimeMillis();

			// int numberOfWorkers = 2;
			int numberOfWorkers = 1;

			int[] YStartPositions = new int[numberOfWorkers];
			int[] XStartPositions = new int[numberOfWorkers];
			int[] StartValues = new int[numberOfWorkers];

			TerminatedFlagWrapper terminatedFlagWrapper = new TerminatedFlagWrapper(false);

			CyclicBarrier barrier1 = new CyclicBarrier(numberOfWorkers + 1);
			CyclicBarrier barrier2 = new CyclicBarrier(numberOfWorkers + 1);

			YStartPositions[0] = 1;
			XStartPositions[0] = 1;
			StartValues[0] = 1;

			// worker anlegen
			for (int i = 0; i < numberOfWorkers; i++) {
				Worker worker = new Worker(i, image, label, YStartPositions, XStartPositions, StartValues, terminatedFlagWrapper, barrier1, barrier2);
				worker.start();
			}
			
			int counter = 0;
			while (terminatedFlagWrapper.terminated == false) {
				int StartPosition= 0;
				if (StartPosition == 0)
					terminatedFlagWrapper.terminated = true;
				
				barrier1.await();

				barrier2.await();
				

			}

			System.out.println("Number of Pixel: " + image[0].length * image.length);

			long endTime = System.currentTimeMillis();

			long totalTime = endTime - startTime;

			System.out.println("Time for " + numberOfWorkers + ": " + totalTime);

			// debug
			System.out.println("Labeling finished");
			System.out.println("Image after: ");
			printArray(image);
			System.out.println("labeledArray: ");
			printArray(label);

		}

		private void schleifen(int[][] image, int numberOfWorkers, int[] YStartPositions, int[] XStartPositions, int[] StartValues, TerminatedFlagWrapper terminatedFlagWrapper,
				CyclicBarrier barrier1, CyclicBarrier barrier2) throws InterruptedException, BrokenBarrierException {

			int counter = 0;
			while (terminatedFlagWrapper.terminated == false) {
				int foundStartPositionForThreadNumber = 0;

				for (int y = image.length - 1; y >= 0 && foundStartPositionForThreadNumber < numberOfWorkers; y--) {
					for (int x = image[0].length - 1; x >= 0 && foundStartPositionForThreadNumber < numberOfWorkers; x--) {

						counter++;

						// System.out.println();
						// System.out.println("y: " + y);
						// System.out.println("x: " + x);
						// System.out.println("foundStartPositionForThreadNumber: "
						// + foundStartPositionForThreadNumber);
						//
						// System.out.println("Image: ");
						// printArray(image);
						//
						// System.out.println("Label: ");
						// printArray(label);

						int value = image[y][x];

						// System.out.println("work on Pixel: "+counter+" with value "+value
						// );

						// if (value == -1)
						// continue;

						if (value != -1 && !isValueIn(value, StartValues)) {
							YStartPositions[foundStartPositionForThreadNumber] = y;
							XStartPositions[foundStartPositionForThreadNumber] = x;
							StartValues[foundStartPositionForThreadNumber] = value;

							foundStartPositionForThreadNumber++;
						}

					}
				}

				// if (foundStartPositionForThreadNumber == 0) {
				// terminatedFlagWrapper.terminated = true;
				// for (int y = image.length - 1; y >= 0; y--) {
				// for (int x = image[0].length - 1; x >= 0; x--) {
				// if (image[y][x] != -1)
				// terminatedFlagWrapper.terminated = false;
				// }
				// }
				// }

				if (foundStartPositionForThreadNumber == 0)
					terminatedFlagWrapper.terminated = true;

				barrier1.await();

				barrier2.await();

			}
		}

		public static void printArray(int[][] arr) {
			// int n = arr.length;

			for (int y = 0; y < arr.length; y++) {
				for (int x = 0; x < arr[0].length; x++) {
					System.out.print(arr[y][x] + " ");
				}
				System.out.println();
			}
		}

		private void updateLabelValuesForSlice(int[][] label, int leftBound, int rightBound, int oldLabelValue, int newLabelValue) {

			for (int y = 0; y < label.length; y++) {
				for (int x = leftBound; x <= rightBound; x++) {
					if (label[y][x] == oldLabelValue)
						label[y][x] = newLabelValue;
				}
			}
		}
	}

}
