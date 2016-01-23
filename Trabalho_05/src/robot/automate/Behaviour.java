package robot.automate;

import robot.MyRobotLego;

public abstract class Behaviour extends Thread {
	public final static int DEFAULT_DELAY = 500;
	public final static int DEFAULT_AVERAGE_SPEED = 3;
	
	protected final MyRobotLego robot;
	protected boolean active = true;
	protected boolean pause = false;
	protected int delay = DEFAULT_DELAY;
	protected int speed = DEFAULT_AVERAGE_SPEED;
	
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
	
	public abstract void action();
	
	public Behaviour(MyRobotLego robot) {
		this.robot = robot;
	}
	
	public void run() {
		if(active && pause) {
			synchronized(robot) {
				try { robot.wait(); }
				catch (InterruptedException e) { }
			}
		}
		if(!active) this.interrupt();
	}
	
	public static int calculateDelay(int distance, int radius, int angle) {
		// TODO : calculate delays
//		if(radius == 0 && angle == 0) return (distance / DEFAULT_AVERAGE_SPEED) * 1000;
		if(distance == 0 && radius > 0 && angle > 0) return (radius * angle) + 100;
		
		if(distance > 0) return (1100 * distance) / 20;
		
		return DEFAULT_DELAY;
	}
	

}
