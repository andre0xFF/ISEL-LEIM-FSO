package robot.automate;

import java.util.concurrent.Semaphore;

import robot.MyRobotLego;

public abstract class Behaviour extends Thread {
	public final MyRobotLego ROBOT;
	public final Semaphore permission;
	public final static int DEFAULT_DELAY = 500;
	
	public boolean alive;
	
	public Behaviour(MyRobotLego robot, Semaphore permission) {
		this.ROBOT = robot;
		this.permission = permission;
		this.alive = true;
	}
	
	public abstract void pause();
	public abstract void run();
	public abstract void action();
}
