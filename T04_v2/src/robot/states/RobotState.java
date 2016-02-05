package robot.states;

import robot.MyRobotLego;

public abstract class RobotState extends Thread {
	private static final int DEFAULT_DELAY = 500;
	protected static final boolean LOG = true;
	
	protected final MyRobotLego robot;

	protected boolean active = true;
	protected int delay = DEFAULT_DELAY;
	protected final int id;
	
	public RobotState(MyRobotLego robot, int id) {
		this.robot = robot;
		this.id = id;
	}
	
	public void deactivate() {
		if(active) {
			active = false;
		}
	}
	
	private void delay() {
		try { Thread.sleep(delay); } 
		catch (InterruptedException e) { e.printStackTrace(); }
	}
	
	@Override
	public void run() {
		while(active) {						
			action();
			if(LOG) { log(); }
			
			delay();
		}
		
		end();
		interrupt();
	}
	
	protected abstract void action();
	protected abstract void end();
	protected abstract void log();


}
