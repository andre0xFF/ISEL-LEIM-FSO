package robot.activestates;

import robot.MyRobotLego;
import robot.scanners.BackScanner;
import robot.states.ActiveState;

public final class Escape extends ActiveState {
	public static final int ID = 5;
	public static final int SCANNER_ID = BackScanner.ID;
	
	/* Robot speeds */
	public final static int MIN_SPEED = 30;
	public final static int MAX_SPEED = 100;
	
	int[] range = new int[2];																			// Robot's safe distance from the detected object
	int objectDistance;
	
	public Escape(MyRobotLego robot, BackScanner scanner, int[] range) {
		super(robot, scanner, ID);
		this.objectDistance = scanner.scan();
		this.range = range;
		this.weight = 1;
		this.delay = 100;
		this.start();
	}
	
	@Override
	public void run() {
		synchronized(robot) {
			robot.Parar(true);
		}
		
		for(
				objectDistance = scanner.scan(); 
				objectDistance > range[0] && objectDistance < range[1] && active;
				objectDistance = scanner.scan()) {
			
			super.run();
			action();
			//System.out.printf("Escape, run(), oDistance, %d, speed: %d\n", objectDistance, robot.getRelativeSpeed());
			loopDelay();
		}
		
		synchronized(robot) {
			robot.Parar(false);
			robot.SetRelativeSpeed(originalSpeed);
		}
	}

	@Override
	public void action() {
		synchronized(robot) {
			int relativeDistance = objectDistance - range[0];
			
			int speed = MAX_SPEED - (relativeDistance * 100 / range[1]);
			
			if(speed < MIN_SPEED) speed += MIN_SPEED - speed;
			
			robot.SetRelativeSpeed(speed);
			robot.Reta(1);
		}
	}

}
