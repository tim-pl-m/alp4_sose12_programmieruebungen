package alp4.prog3.solution;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

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
