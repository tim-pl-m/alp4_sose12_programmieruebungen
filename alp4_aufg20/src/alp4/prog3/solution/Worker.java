package alp4.prog3.solution;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class Worker extends Thread {

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

			for (int x = 0; x < image.length; x++) {
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

						/*
						 * checks hier ausführen
						 */

						maximum = this.checkForSameArea(y-1, x, value, stack, maximum); 
						maximum = this.checkForSameArea(y, x-1, value, stack, maximum); 
						maximum = this.checkForSameArea(y, x+1, value, stack, maximum); 
						maximum = this.checkForSameArea(y+1, x, value, stack, maximum); 

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

		/*
		 * bool[][] doneMarker; // int[][] label foreach y,x
		 * 
		 * value = image[y][x]
		 * 
		 * if(doneMarker[y][x]) continue;
		 * 
		 * List<y,x> foundList
		 * 
		 * Stack stack; stack.push((y,x))
		 * 
		 * while((current = stack.pop) != null)
		 * 
		 * if(doneMarker[current.y][current.x] == false)
		 * 
		 * foundList.add((y,x));
		 * 
		 * check(y-1, x, value) check(y, x-1, value) check(y, x+1, value)
		 * check(y+1, x, value)
		 * 
		 * doneMarker[y][x] = true
		 * 
		 * // kopiere an alle stellen aus foundList in label jeweils das maximum
		 * for((y,x): foundList) label[y][x] = maximum;
		 * 
		 * 
		 * 
		 * check(y, x, value) if(image[y][x] == value) stack.push((y), x)
		 * if((y,x) > max) max = (y,x)
		 */

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
		
		if(image[y][x] == value)
			stack.push(new IntTupel(y, x)); 
		
		int newMax = (y*this.image.length) + x; 
		return newMax > oldMaximum ? newMax : oldMaximum; 
	}

	public void run() {

		System.out.println("WAITING says:" + name);
		try {
			barrier.await();
			this.work();
			System.out.println("WORKING HARD NOW says:" + name);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
		System.out.println("FINISHED says: " + name);
	}
}
