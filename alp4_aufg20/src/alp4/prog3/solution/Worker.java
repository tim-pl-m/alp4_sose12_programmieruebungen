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
    	
    	
    	/*
    	 * 
    	 * int[][] result
    	 * foreach y,x
    	 *   
    	 *   value = image[y][x]
    	 *   stack.push((y,x))
    	 *   
    	 *   while((current = stack.pop) != null)
    	 *   
    	 *     if(image[current.y][current.x] != null)
    	 *     
    	 *       check(y-1, x, value)
    	 *       check(y, x-1, value)
    	 *       check(y, x+1, value)
    	 *       check(y+1, x, value)
    	 *         
    	 *       image[y][x] = null
    	 * 
    	 * 
    	 * 
    	 * 
    	 * check(y, x, value)
    	 *   if(image[y][x] == value)
    	 *     stack.push((y-1), x)
    	 *     if((y,x) > max)
    	 *       max = (y,x)
    	 * 
    	 */
    	
    	
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
