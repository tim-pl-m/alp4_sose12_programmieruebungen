package alp4.prog3;


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class BarrierTest {


    class Worker extends Thread {

    	String name; 
    	
        int[][] image; 
        int[][] label;
        
        CyclicBarrier barrier;

        
        public Worker(String name, int[][] image, int[][] label, CyclicBarrier cBarrier) {
        	this.name = name; 
        	this.image = image; 
        	this.label = label; 
            barrier = cBarrier;
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
    	
    	
    	
        CyclicBarrier barrier = new CyclicBarrier(2, new Runnable() {
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

        for (int i = 0; i < 2; i++) {
            Worker worker = new Worker("Thread_"+i, image, label, barrier);
            worker.start();
        }

    }


//    public static void main(String[] args) {
//        BarrierTest t = new BarrierTest();
//        t.testCyclicBarrier();
//    }
}