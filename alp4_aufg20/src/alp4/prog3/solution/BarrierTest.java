package alp4.prog3.solution;


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class BarrierTest {


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
//    	int[][] image = {
//    			{3,3,4}, 
//    			{5,1,2}, 
//    			{1,1,2}, 
//    			}; 

    	int[][] image = {
    			{3,3}, 
    			{5,1}, 
    			}; 

//    	int[][] image = {
//    			{3}, 
//    			}; 
    	
    	int n = image.length; 
    	int[][] label = new int[n][n]; 
    	
    	
    	BarrierTest barrierTest = new BarrierTest(); 
    	barrierTest.testCyclicBarrier(image, label); 
    	

    	System.out.println("Image: ");
    	printArray(image); 

    	System.out.println("Label: ");
    	printArray(label); 

    	
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

//    public static void main(String[] args) {
//        BarrierTest t = new BarrierTest();
//        t.testCyclicBarrier();
//    }
}