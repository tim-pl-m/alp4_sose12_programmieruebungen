package alp4.prog3.solution;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Worker extends Thread {

	String name;

	int[][] image;
	int[][] label;

//	int leftBound;
//	int rightBound;

	CyclicBarrier barrier;

	public Worker(String name, int[][] image, int[][] label, int leftBound,
			int rightBound, CyclicBarrier barrier) {
		this.name = name;
		this.image = image;
		this.label = label;
//		this.leftBound = leftBound;
//		this.rightBound = rightBound;
		this.barrier = barrier;
	}


	public void run() {

		try {
			this.work();
			barrier.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	public static class IntTupel {
		final public int y;
		final public int x;

		public IntTupel(int y, int x) {
			this.y = y;
			this.x = x;
		}
	}

	public void work() {

		boolean[][] doneMarker = new boolean[image.length][image.length];

		for (int y = 0; y < image.length; y++) {

			for (int x = 0; x < image[y].length; x++) {
				if (doneMarker[y][x] == true)
					continue;

				int value = this.image[y][x];
				List<IntTupel> foundList = new LinkedList<IntTupel>();
				Stack<IntTupel> stack = new Stack<Worker.IntTupel>();

				int maximum = (y*this.image.length) + x; 
				stack.push(new IntTupel(y, x));

				IntTupel current;
				while ( ! stack.isEmpty() ) {
					current = stack.pop(); 
					if (doneMarker[current.y][current.x] == false) {
						foundList.add(current);

						maximum = this.checkForSameArea(current.y-1, current.x, value, stack, maximum); 
						maximum = this.checkForSameArea(current.y, current.x-1, value, stack, maximum); 
						maximum = this.checkForSameArea(current.y, current.x+1, value, stack, maximum); 
						maximum = this.checkForSameArea(current.y+1, current.x, value, stack, maximum); 

						doneMarker[current.y][current.x] = true;

					}
					
				}

				// kopiere an alle stellen aus foundList in label jeweils das maximum
				for(IntTupel current2: foundList)
				{
					label[current2.y][current2.x] = maximum; 
				}

			}
		}

	}
	
	// returns the new maximum
	public int checkForSameArea(int y, int x, int value, Stack<IntTupel> stack, int oldMaximum)
	{
		if(y >= image.length || y < 0)
		{
			return oldMaximum; 
		}
		if(x >= image[y].length || x < 0)
		{
			return oldMaximum; 
		}
		
		if(image[y][x] != value)
			return oldMaximum; 
		
		
		stack.push(new IntTupel(y, x)); 
		
		int newMax = (y*this.image.length) + x; 
		return newMax >= oldMaximum ? newMax : oldMaximum; 
	}
}
