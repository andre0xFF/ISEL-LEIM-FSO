package robot.scanners;

import robot.MyRobotLego;
import robot.states.StateMachine;

public class FrontScanner extends Scanner {
	private final static int[] TRIGGER = new int[] { 1 };
	public static final int STATE_ID = 3;
	
	public FrontScanner(MyRobotLego robot, StateMachine machine) {
		super(robot, machine, STATE_ID, TRIGGER);
		this.start();
	}

	@Override
	protected int scan() {
		return robot.Sensor(MyRobotLego.FRONT_SCANNER_PORT); 
	}

	@Override
	protected void end() { }

	@Override
	protected void log() { 
		System.out.printf("FrontScanner, log(), objectDetected: %s, objectDistance: %d\n", objectDetected, objectDistance);
	}

	@Override
	protected void setPort() {
		robot.SetSensorTouch(MyRobotLego.FRONT_SCANNER_PORT);	
	}

	@Override
	protected boolean newObjectDetected(int[] trigger, int objectDistance) {
		return (objectDistance == trigger[0]);
	}

	@Override
	protected boolean oldObjectGone(int[] trigger, int objectDistance) {
		return (objectDistance != trigger[0]);
	}

}
