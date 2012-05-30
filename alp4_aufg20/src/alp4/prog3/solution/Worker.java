package alp4.prog3.solution;

import java.util.Stack;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/*
 * 
 * w = #worker
 * beginne von rechts unten an und wähle w felder mit verschiedenen farbwerten aus
 *   weise jedem worker ein startfeld (die koordinaten) zu
 *    jeder worker findet und markiert alle felder aus dem gebiet, in welchem sein startfeld liegt, 
 *    und setzt für jedes dieser felder den labelwert auf den positionwert seines startfeldes
 *    markiere dabei jedes besuchte feld mit -1
 *   
 *   barrier1.await
 *   
 *   wenn keine felder != -1 mehr vorhanden sind:
 *    setze terminated = true
 *    
 *   barrier2.await
 *      
 *   
 *   
 *   
 *   
 *   
 *   worker: 
 *    
 *    while(terminated == false)
 *      arbeiten
 *      
 *      barrier1.await
 *      
 *      barrier2.await
 * 
 * 
 * 
 * 
 */

public class Worker extends Thread {

	int workerId;

	int[][] image;
	int[][] label;

	int[] foundYStartPositions;
	int[] foundXStartPositions;
	int[] foundStartValues;
	
	TerminatedFlagWrapper terminatedFlagWrapper; 

	CyclicBarrier barrier1;
	CyclicBarrier barrier2;
	
	
	public Worker(int workerId, int[][] image, int[][] label, 
			int[] foundYStartPositions, int[] foundXStartPositions, int[] foundStartValues, 
			TerminatedFlagWrapper terminatedFlagWrapper, 
			CyclicBarrier barrier1, CyclicBarrier barrier2) {
		this.workerId = workerId;
		this.image = image;
		this.label = label;
		this.foundYStartPositions = foundYStartPositions; 
		this.foundXStartPositions = foundXStartPositions;
		this.foundStartValues = foundStartValues; 
		this.terminatedFlagWrapper = terminatedFlagWrapper; 
		this.barrier1 = barrier1;
		this.barrier2 = barrier2;
	}


	public void run() {

		try {
			
			while(true)
			{
				this.barrier1.await(); 
				
				if(this.terminatedFlagWrapper.terminated == true)
				{
					this.barrier2.await(); 
					break; 
				}
				
				else
				{
					this.work();
					this.barrier2.await(); 
				}
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
	}
	

	public void work() {

		
		int valueToCompare = this.foundStartValues[this.workerId]; 
		int y = this.foundYStartPositions[this.workerId];
		int x = this.foundXStartPositions[this.workerId]; 
		
		if(y == -1 || x == -1)
			return; 

		
		Stack<IntTupel> stack = new Stack<Worker.IntTupel>();
		
		int areaValueForLabel = (y*this.image.length) + x; 

		
		this.image[y][x] = -1; 
		this.label[y][x] = areaValueForLabel; 
		stack.push(new IntTupel(y, x));

		while(!stack.empty())
		{
			IntTupel curField = stack.pop(); 

			this.checkForSameArea(curField.y-1, curField.x, valueToCompare, areaValueForLabel, stack); 
			this.checkForSameArea(curField.y, curField.x-1, valueToCompare, areaValueForLabel, stack); 
			this.checkForSameArea(curField.y, curField.x+1, valueToCompare, areaValueForLabel, stack); 
			this.checkForSameArea(curField.y+1, curField.x, valueToCompare, areaValueForLabel, stack); 			
		}
		
//		this.image[y][x] = -1; 
		this.foundYStartPositions[this.workerId] = -1; 
		this.foundXStartPositions[this.workerId] = -1; 
		this.foundStartValues[this.workerId] = -1; 

	}
	
	
	public static class IntTupel {
		final public int y;
		final public int x;

		public IntTupel(int y, int x) {
			this.y = y;
			this.x = x;
		}
	}
	
	public void checkForSameArea(int y, int x, int valueToCompare, int areaValueForLabel, Stack<IntTupel> stack)
	{
		if(y >= image.length || y < 0)
			return; 

		else if(x >= image[y].length || x < 0)
			return; 
		
		else if(image[y][x] != valueToCompare)
			return; 

		else
		{
			image[y][x] = -1; 
			label[y][x] = areaValueForLabel; 
			stack.push(new IntTupel(y, x)); 
		}
	}
}
