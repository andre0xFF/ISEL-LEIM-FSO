package Robot.Scanners;

import Robot.MyRobotLego;
import Robot.States.StateMachine;

public class BackScanner extends Scanner {
	public static final int STATE_ID = 2;
	
	public BackScanner(MyRobotLego robot, StateMachine machine, int[] trigger) {
		super(robot, machine, STATE_ID, trigger);
		this.start();
	}

	@Override
	protected void setPort() {
		robot.SetSensorLowspeed(MyRobotLego.BACK_SCANNER_PORT);
	}

	@Override
	protected int scan() {
		return robot.SensorUS(MyRobotLego.BACK_SCANNER_PORT);
	}

	@Override
	protected boolean newObjectDetected(int[] trigger, int objectDistance) {
		return (objectDistance > trigger[0] && objectDistance < trigger[1]);
	}

	@Override
	protected boolean oldObjectGone(int[] trigger, int objectDistance) {
		return (objectDistance < trigger[0] || objectDistance > trigger[1]);
	}

	@Override
	protected void end() {
		robot.Parar(false);
	}

	@Override
	protected void log() {
		System.out.printf("BackScanner, log(), objectDetected: %s, objectDistance: %d\n", objectDetected, objectDistance);
	}
	
	@Override
	public void run() {
		while(active) {
			super.run();
		}
		
		end();
		interrupt();
	}


}

