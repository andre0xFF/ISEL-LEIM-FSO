package memoryMapped;

public class MutexCOM extends ProcessCOM {
	private final String mutexName = "mutex";
	
	public MutexCOM() {	
		// initialize mutex in memory
		
		// memory reset
		super.sendMessage("");
		if(super.receiveMessage().equals("")) {
			release();
		}
	}
	/*
	public boolean acquire() throws MutexNotFound {
		String str = super.receiveMessage();
		
		if(!str.contains(mutexName)) {
			throw new MutexNotFound();
		}
		
		super.sendMessage("notHere");
		
		return true;
	}
	*/
	
	public boolean acquire() throws MutexNotFound, InterruptedException {
		String str = super.receiveMessage();
		
		while(!str.contains(mutexName)) {
			Thread.sleep(1000);
			str = super.receiveMessage();
			continue;
		}
		
		super.sendMessage("notHere");
		return true;
	}
	
	public void release() { 
		super.sendMessage(mutexName);
	}
	
}

