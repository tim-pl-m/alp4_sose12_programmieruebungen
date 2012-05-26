package alp4.prog3.solution;

import java.util.LinkedList;
import java.util.List;
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

    public static class IntTupel
    {
    	final public int a; 
    	final public int b; 
    	
    	public IntTupel(int a, int b)
    	{
    		this.a = a; 
    		this.b = b; 
    	}
    }
    
    public void work()
    {

    	boolean[][] doneMarker = new boolean[leftBound+1][image.length]; 
    	
    	for(int y = 0; y <= leftBound; y++)
    	{

        	for(int x = 0; x <= leftBound; x++)
        	{
        		if(doneMarker[y][x] == true)
        			continue; 
        		
        		int value = this.image[y][x]; 
        		List<IntTupel> foundList = new LinkedList<IntTupel>(); 
        		
        		
        	}	
    	}
    	
    	/*
    	 * bool[][] doneMarker; 
    	 * // int[][] label
    	 * foreach y,x
    	 *   
    	 *   value = image[y][x]
    	 *   
    	 *   if(doneMarker[y][x]) 
    	 *     continue; 

    	 *   List<y,x> foundList 
    	 *     
    	 *   Stack stack; 
    	 *   stack.push((y,x))
    	 *   
    	 *   while((current = stack.pop) != null)
    	 *   
    	 *     if(doneMarker[current.y][current.x] == false)
    	 *     
    	 *       foundList.add((y,x)); 
    	 *       
    	 *       check(y-1, x, value)
    	 *       check(y, x-1, value)
    	 *       check(y, x+1, value)
    	 *       check(y+1, x, value)
    	 *         
    	 *       doneMarker[y][x] = true
    	 *   
    	 *   // kopiere an alle stellen aus foundList in label jeweils das maximum 
    	 *   for((y,x): foundList)
    	 *     label[y][x] = maximum; 
    	 * 
    	 * 
    	 * 
    	 * check(y, x, value)
    	 *   if(image[y][x] == value)
    	 *     stack.push((y), x)
    	 *     if((y,x) > max)
    	 *       max = (y,x)
    	 * 
    	 */
    	
    }
    
    public void run() {
    	
        System.out.println("WAITING says:" + name);
        try {
            barrier.await();
            this.work(); 
            System.out.println("WORKING HARD NOW says:"+ name);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println("FINISHED says: "+ name);
    }
}
