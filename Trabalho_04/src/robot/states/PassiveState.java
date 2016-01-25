package robot.states;

import robot.MyRobotLego;

/**
 * A passive state:
 * * runs when there's no active state
 * * can be paused
 * * have no priority
 * * does not need a scanner
 * @author affonseca
 *
 */
public abstract class PassiveState extends State
{
	protected boolean pause = false;
	
	public boolean isPaused() { return this.pause; }	
	public void pause() { this.pause = true; }
	
	public void unpause() {
		synchronized(robot) {
			this.pause = false;
			robot.notify();
		}
	}
	
	public PassiveState(MyRobotLego robot, int id) {
		super(robot, id);
	}
	
	public void run() {
		super.run();
		
		while(active && pause) {
			synchronized(robot) {
				try { robot.wait(); }
				catch (InterruptedException e) { }
			}
		}
	}
}
