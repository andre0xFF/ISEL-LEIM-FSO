package memoryMapped;

public class MutexCOM extends ProcessCOM {
	private final String mutexName = "mutex";
	
	public MutexCOM() {
		//super.sendMessage("");	// uncomment if memory reset is needed
		if(super.receiveMessage().equals("")) {
			release();
		}
	}
	
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

