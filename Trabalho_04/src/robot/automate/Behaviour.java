package robot.automate;

import robot.MyRobotLego;

public abstract class Behaviour extends Thread {
	public final MyRobotLego ROBOT;
	public boolean alive;
	
	public Behaviour(MyRobotLego robot) {
		this.ROBOT = robot;
		this.alive = true;
	}
	
	public abstract void run();
	public abstract void action();
}
