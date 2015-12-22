package robot;

import java.util.*;

public abstract class Scanner extends Thread {
	public final static int DEFAULT_DELAY = 500;
	
	protected final MyRobotLego robot;
	protected boolean active = true;
	protected boolean pause = false;
	protected int delay = DEFAULT_DELAY;
	protected final int port;
	protected boolean objectDetected = false;
	protected List<RobotNervousSystem> listeners = new ArrayList<RobotNervousSystem>();
	
	public int getPort() { return this.port; }
	
	protected void addListener(RobotNervousSystem toAdd) { this.listeners.add(toAdd); }
	
	public int getDelay() { return this.delay; }
	
	public void setDelay(int delay) { this.delay = delay; }
	
	public boolean isActive() { return this.active; }
	
	public void deactivate() { this.active = false; }
	
	public boolean isPaused() { return this.pause; }
	
	public void pause() { this.pause = true; }
	
	public void unpause() {
		synchronized(robot) {
			this.pause = false;
			robot.notify();
		}
	}
	
	protected abstract void objectDetected(int distance);
	protected abstract void objectIsGone();
	protected abstract void setPort(int port);
	public abstract int scan();
	
	public Scanner(MyRobotLego robot, int port) {
		this.robot = robot;
		setPort(port);
		this.port = port;
		this.addListener(robot);
		this.start();
	}
	
	
	public void run() {
		if(active && pause) {
			synchronized(robot) {
				try { robot.wait(); }
				catch (InterruptedException e) { }
			}
		}
			
		if(!active) this.interrupt();

		if(scan() > 0) objectDetected(scan());
	}	
}


interface RobotNervousSystem {
	void frontObjectDetected(int distance);
	void rearObjectDetected(int distance);
	void frontObjectIsGone();
	void rearObjectIsGone();
	
	void roam();
	void escape(int minDistance, int maxDistance);
	void avoid();
}