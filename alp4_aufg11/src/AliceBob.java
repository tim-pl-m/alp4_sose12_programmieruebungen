import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


public class AliceBob {

	AtomicBoolean alicesFlag1 = new AtomicBoolean(); 
	AtomicBoolean alicesFlag2 = new AtomicBoolean(); 

	AtomicBoolean bobsFlag1 = new AtomicBoolean(); 
	AtomicBoolean bobsFlag2 = new AtomicBoolean(); 

	AtomicBoolean runAlice = new AtomicBoolean(true); 
	AtomicBoolean runBob = new AtomicBoolean(true); 

	AtomicInteger numberOfDogsInTheYard = new AtomicInteger(0); 

	
	public void runAlice()
	{
		this.runAlice.set(true); 
		
		Alice alice = new Alice(); 
		
		Thread aliceThread = new Thread(alice); 
		aliceThread.start(); 
		
	}
	

	
	public void runBob()
	{
		this.runBob.set(true); 
		
		Bob bob = new Bob(); 
		
		Thread bobThread = new Thread(bob); 
		bobThread.start(); 
		
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
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}
				
				// CIRITICAL SECTION
				if(numberOfDogsInTheYard.incrementAndGet() != 1)
				{
					System.err.println("ERROR!! MORE THAN ONE DOG IN THE YARD [REGOCINGED WHILE LEAVING ALICES DOG IN THE YARD");
				}
				
				System.out.println("ALICES DOG IN THE YARD");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
				numberOfDogsInTheYard.decrementAndGet(); 
				
				
				// LEAVE CRITICAL SECTION
				alicesFlag1.set(false); 
				
			}
		}
	}
	
	
	

	public class Bob implements Runnable
	{
		public void run()
		{
			while(runBob.get())
			{
				// GO AROUDN TOWN
				// randomSekundenZahl zwischen 2 und 10 und für diese Zeit sleepen
				
				// ENTRY-SECTION
				bobsFlag1.set(true); 
				bobsFlag2.set(! alicesFlag2.get()); 
				
				while( ! (alicesFlag1.get() == false || alicesFlag2.get() == bobsFlag2.get()) )
				{
					System.out.println("BOB BUSY WAITING");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					
				}
				
				// CIRITICAL SECTION
				if(numberOfDogsInTheYard.incrementAndGet() != 1)
				{
					System.err.println("ERROR!! MORE THAN ONE DOG IN THE YARD [REGOCINGED WHILE LEAVING ALICES DOG IN THE YARD");
				}
				
				
				System.out.println("BOBS DOG IN THE YARD");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				numberOfDogsInTheYard.decrementAndGet(); 
				
				// LEAVE CRITICAL SECTION
				bobsFlag1.set(false); 
				
			}
			
			
		}
	}
	
}
