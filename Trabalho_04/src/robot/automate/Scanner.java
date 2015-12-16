package robot.automate;

import robot.MyRobotLego;

public abstract class Scanner extends Thread {
	public boolean alive = true;	
	public final MyRobotLego ROBOT;
	public final int PORT;
	
	private int delay;
	
	public Scanner(MyRobotLego robot, int port) {
		this.ROBOT = robot;
		this.PORT = port;
	}
	
	public void setDelay(int delay) { this.delay = delay; }
	public int getDelay() { return this.delay; }
	
	public abstract void run();
	public abstract int scan();
	public abstract void react();
}
