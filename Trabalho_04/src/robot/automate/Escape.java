package robot.automate;

import robot.BackScanner;
import robot.MyRobotLego;

public class Escape extends Behaviour {
	private int objectDistance;
	private final int minDistance;
	private final int maxDistance;
	private final BackScanner scanner;
	
	public final static int MIN_SPEED = 30;
	public final static int MAX_SPEED = 100;

	
	public Escape(MyRobotLego robot, BackScanner scanner) {
		super(robot);
		this.scanner = scanner;
		this.objectDistance = scanner.scan();
		this.minDistance = scanner.getMinDistance();
		this.maxDistance = scanner.getMaxDistance();
		this.setPriority(NORM_PRIORITY);
		this.start();
	}

	
	@Override
	public void run() {
		while(objectDistance > minDistance && objectDistance < maxDistance) {
			synchronized(robot) {
				action();
				MyRobotLego.sleep(getDelay());
			}
			objectDistance = scanner.scan();
		}
		
		robot.Parar(false);
		this.interrupt();
	}

	
	@Override
	public void action() {						
		int relDistance = objectDistance - minDistance;
		
		int speed = MAX_SPEED - (relDistance * 100 / maxDistance);
		
		if(speed < MIN_SPEED) speed += MIN_SPEED - speed;
		
		System.out.printf("Run: %d, %d\n", speed, objectDistance);
		robot.SetSpeed(speed);
		robot.Reta(maxDistance - objectDistance);
		robot.Parar(false);
	}
	
}
