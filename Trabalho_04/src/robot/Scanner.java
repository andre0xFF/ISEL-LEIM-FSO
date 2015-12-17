package robot;

import java.util.*;

public abstract class Scanner extends Thread {
	public boolean alive = true;	
	public final MyRobotLego ROBOT;
	public final int PORT;
	public List<ObjectListener> listeners = new ArrayList<ObjectListener>();
	
	private int delay = 500;
	
	
	public Scanner(MyRobotLego robot, int port) {
		this.ROBOT = robot;
		this.PORT = port;
		this.addListener(robot);
	}
	
	
	public void setDelay(int delay) { this.delay = delay; }
	public int getDelay() { return this.delay; }
	
	public void addListener(ObjectListener toAdd) { this.listeners.add(toAdd); }
	
	public static void sleep(int ms) {	  
		try { Thread.sleep(ms); }
		catch (InterruptedException e) { }
	}
	
	public abstract void objectDetected(int distance);
	public abstract void run();
	public abstract int scan();
}