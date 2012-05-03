import java.util.concurrent.atomic.AtomicBoolean;


public class AliceBob {

	AtomicBoolean alicesFlag1 = new AtomicBoolean(); 
	AtomicBoolean alicesFlag2 = new AtomicBoolean(); 

	AtomicBoolean bobsFlag1 = new AtomicBoolean(); 
	AtomicBoolean bobsFlag2 = new AtomicBoolean(); 

	AtomicBoolean runAlice = new AtomicBoolean(true); 
	AtomicBoolean runBob = new AtomicBoolean(true); 
	
	
	public void runAlice()
	{
		this.runAlice.set(true); 
		
		Alice alice = new Alice(); 
		
		Thread aliceThread = new Thread(alice); 
		aliceThread.start(); 
		
		
	}
	
	public class Alice implements Runnable
	{
		public void run()
		{
			while(runAlice.get())
			{
				// GO AROUDN TOWN
				// randomSekundenZahl zwischen 2 und 10 und für diese Zeit sleepen
				
				// ENTRY-SECTION
				alicesFlag1.set(true); 
				alicesFlag2.set(bobsFlag2.get()); 
				
				while( ! (bobsFlag1.get() == false || alicesFlag2.get() != bobsFlag2.get()) )
				{
					System.out.println("ALICE BUSY WAITING");
				}
				
				// CIRITICAL SECTION
				System.err.println("ALICES DOG IN THE YARD");
				
				
				// LEAVE CRITICAL SECTION
				alicesFlag1.set(false); 
				
			}
			
			
		}
	}
	
}
