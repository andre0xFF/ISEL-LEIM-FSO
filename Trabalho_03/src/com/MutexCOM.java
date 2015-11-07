package com;

public class MutexCOM extends ProcessCOM {
	private final String mutexName = "mutex";
	
	public MutexCOM() {
		
		// initialize mutex in memory
		if(super.receiveMessage().equals("")) {
			release();
		}
	}
	
	public boolean acquire() throws MutexNotFound {
		String str = super.receiveMessage();
		
		if(!str.contains(mutexName)) {
			throw new MutexNotFound();
		}
		
		super.sendMessage("notHere");
		
		return true;
	}
	
	public void release() { 
		super.sendMessage(mutexName);
	}
	
}


@SuppressWarnings("serial")
class MutexNotFound extends Exception {

	public MutexNotFound() { 
		super("MutexNotFound");
	}
	
	public MutexNotFound(Throwable cause) {
		super(cause);
	}
}
