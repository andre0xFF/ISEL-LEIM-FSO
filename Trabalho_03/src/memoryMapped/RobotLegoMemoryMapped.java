package memoryMapped;

import javax.swing.JTextField;
import robot.MyRobotLego;


public class RobotLegoMemoryMapped extends MyRobotLego {
	private MutexCOM mutex;
	
	public RobotLegoMemoryMapped(JTextField l, boolean liveMode) {
		super(l, liveMode);
		mutex = new MutexCOM();
	}
	
	public boolean OpenNXT(String name) {
		try {
			mutex.acquire();
			super.OpenNXT(name);
			return true;
		}
		catch(MutexNotFound e) { return false; }
	}
	
	public boolean CloseNXT() {
		try {
			mutex.release();
			super.CloseNXT();
			return true;
		} catch (Exception e) { return false; }
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