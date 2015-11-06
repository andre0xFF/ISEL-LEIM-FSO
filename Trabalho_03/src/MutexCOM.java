
public class MutexCOM extends ProcessCOM {
	private final String mutexName = "mutex";
	
	public MutexCOM() {
		
		// initialize mutex in memory
		if(super.receiveMessage().equals("")) {
			release();
		}
	}
	
	public Boolean acquire() throws MutexNotFound {
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
	/*
	private void increment() {
		int n = getNumberOfProcesses();
		super.sendMessage(Integer.toString(++n));
	}
	
	private void decrement() {
		int n = getNumberOfProcesses();
		super.sendMessage(Integer.toString(--n));	
	}
	
	private int getNumberOfProcesses() {
		String msg = super.receiveMessage();
		return Integer.parseInt(msg.substring((msg.length())));
	}
	*/
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
