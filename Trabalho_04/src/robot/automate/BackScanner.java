package robot.automate;

import robot.MyRobotLego;

public class BackScanner extends Scanner {
	private final int minDistance;
	private final int maxDistance;
	
	public BackScanner(MyRobotLego robot, int port, int minDistance, int maxDistance) {
		super(robot, port);
		this.minDistance = minDistance;
		this.maxDistance = maxDistance;
		ROBOT.SetSensorLowspeed(port);
		this.start();
	}

	
	public void run() {
		react();
		while(alive) {
			if(scan() > 0) react();
			
			MyRobotLego.sleepForAWhile(super.getDelay());
		}
		this.interrupt();
	}

	
	@Override
	public synchronized int scan() {
		return ROBOT.SensorUS(PORT);
	}


	@Override
	public synchronized void react() {
		new Escape(ROBOT, scan(), minDistance, maxDistance, MyRobotLego.MIN_SPEED, MyRobotLego.MAX_SPEED);	
	}
}
