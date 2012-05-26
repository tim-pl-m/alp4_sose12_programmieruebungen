package alp4.prog3;


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class BarrierTest {


    class Worker extends Thread {

    	String name; 
    	
        int[][] image; 
        int[][] label;
        
        int leftBound; 
        int rightBound; 
        
        CyclicBarrier barrier;

        
        public Worker(String name, int[][] image, int[][] label, int leftBound, int rightBound, CyclicBarrier barrier) {
        	this.name = name; 
        	this.image = image; 
        	this.label = label;
        	this.leftBound = leftBound; 
        	this.rightBound = rightBound; 
            this.barrier = barrier;
        }

        public void run() {
        	
        	
        	
            System.out.println("WAITING says:" + name);
            try {
                barrier.await();
                System.out.println("WORKING HARD NOW says:"+ name);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("FINISHED says: "+ name);
        }
    }

    public void testCyclicBarrier(int[][] image, int[][] label) {
    	
    	int numberOfWorkers = 1; 
    	
        CyclicBarrier barrier = new CyclicBarrier(numberOfWorkers, new Runnable() {
                public void run() {
                    try {
                        System.out.println("START Barrier Action ....");
                        Thread.sleep(1000);
                        System.out.println("STOP Barrier Action ....");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

        for (int i = 0; i < numberOfWorkers; i++) {
            Worker worker = new Worker("Thread_"+i, image, label, 0, image[0].length, barrier);
            worker.start();
            try {
				worker.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
        }

    }

    public static void main(String[] args) {
    	int[][] image = {
    			{3,3,4,5}, 
    			{5,1,2,2}, 
    			{3,2,2,3}, 
    			{3,3,4,5}
    			}; 
    	
    	int n = image.length; 
    	int[][] label = new int[n][n]; 
    	
    	
    	System.out.println("Image: ");
    	for(int x = 0; x < n; x++)
    	{
    		for(int y = 0; y < n; y++)
    		{
    			System.out.print(image[x][y] + " ");
    		}
    		System.out.println();
    	}
    	
    	BarrierTest barrierTest = new BarrierTest(); 
    	barrierTest.testCyclicBarrier(image, label); 
    	
    	
    	System.out.println("Image: ");
    	for(int x = 0; x < n; x++)
    	{
    		for(int y = 0; y < n; y++)
    		{
    			System.out.print(image[x][y] + " ");
    		}
    		System.out.println();
    	}
    	
    }


//    public static void main(String[] args) {
//        BarrierTest t = new BarrierTest();
//        t.testCyclicBarrier();
//    }
}