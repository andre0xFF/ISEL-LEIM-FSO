package robot.automate;

import robot.MyRobotLego;

public class Escape extends Behaviour {
	private final int objectDistance;
	private final int minDistance;
	private final int maxDistance;
	private final int minSpeed;
	private final int maxSpeed;

	public Escape(MyRobotLego robot, int objectDistance, int minDistance, int maxDistance, int minSpeed, int maxSpeed) {
		super(robot);
		this.objectDistance = objectDistance;
		this.minDistance = minDistance;
		this.maxDistance = maxDistance;
		this.minSpeed = minSpeed;
		this.maxSpeed = maxSpeed;
		this.start();
	}

	@Override
	public void run() {
		action();
		this.interrupt();
	}

	@Override
	public void action() {
		ROBOT.addBehaviour();
		synchronized(ROBOT) {
			if(objectDistance < minDistance || objectDistance > maxDistance) return;
							
			int relDistance = objectDistance - minDistance;
			
			int speed = maxSpeed - (relDistance * 100 / maxDistance);
			
			if(speed < minSpeed) speed += minSpeed - speed;
			
			System.out.printf("Run: %d, %d\n", speed, objectDistance);
			ROBOT.Parar(true);
			ROBOT.SetSpeed(speed);
			ROBOT.Reta(maxDistance - objectDistance);
			ROBOT.notify();
		}
		ROBOT.rmBehaviour();
	}

}
