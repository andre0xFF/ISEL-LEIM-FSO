package robot.automate;

import java.util.concurrent.Semaphore;

import robot.MyRobotLego;

public class Escape extends Behaviour {
	private final int objectDistance;
	private final int minDistance;
	private final int maxDistance;
	
	public final static int MIN_SPEED = 30;
	public final static int MAX_SPEED = 100;

	
	public Escape(MyRobotLego robot, Semaphore permission, int objectDistance, int minDistance, int maxDistance) {
		super(robot, permission);
		this.objectDistance = objectDistance;
		this.minDistance = minDistance;
		this.maxDistance = maxDistance;
		this.start();
	}

	
	@Override
	public void run() {
		action();
		this.interrupt();
	}

	
	@Override
	public synchronized void action() {
		try { permission.acquire(); }
		catch (InterruptedException e1) { e1.printStackTrace(); }
		
		if(objectDistance < minDistance || objectDistance > maxDistance) return;
						
		int relDistance = objectDistance - minDistance;
		
		int speed = MAX_SPEED - (relDistance * 100 / maxDistance);
		
		if(speed < MIN_SPEED) speed += MIN_SPEED - speed;
		
		System.out.printf("Run: %d, %d\n", speed, objectDistance);
		ROBOT.Parar(true);
		ROBOT.SetSpeed(speed);
		ROBOT.Reta(maxDistance - objectDistance);
		
		permission.release();
	}


	@Override
	public synchronized void pause() {	}

	
}
