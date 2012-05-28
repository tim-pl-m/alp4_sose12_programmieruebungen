package alp4.prog3;

import java.util.concurrent.BrokenBarrierException;

import alp4.prog3.solution.BarrierTest;

/* 
	This file is just a proposal, you may change it at your convenience.
	Handed in "Framework.java" files will not be considered and you also
	don't need to hand it in again. What this means is simply:
	"Don't change the given framework!"
*/

public class YourCode {
	
	public static void main(String[] args) throws Exception{
		if(args.length != 2)
		{
			System.out.println("[ERROR]: Requires exactly two arguments, the input file and the output file!");
			return;
		}
			
		Framework.processLabeling(args[0], args[1], new MyLabeler());
	}
	
	
	private static class MyLabeler implements ILabeler
	{
		@Override
		public void process(int[][] image, int[][] label)
		{
			
			BarrierTest barrierTest = new BarrierTest(); 
			barrierTest.testCyclicBarrier(image, label); 
			try {
				barrierTest.waitForBarrier();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
//			// TODO: implement something here...
//			for(int x = 0, i = 0; x < image.length; x++){
//				for(int y = 0; y < image[0].length; y++){
//					label[x][y] = image[x][y];
//					//label[x][y] = (x < image.length / 2) ? 59 : 63;
//				}
//			}
		}
	}
}
