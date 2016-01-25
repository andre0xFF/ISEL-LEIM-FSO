package robot.scanners;

import robot.activestates.Avoid;
import robot.states.RobotNervousSystem;
import robot.states.Scanner;
import robot.states.StateMachine;

public final class FrontScanner extends Scanner {

	public static final int ID = 2;
	
	public FrontScanner(StateMachine machine, int port) {
		super(machine, ID, port);
	}

	@Override
	protected void objectDetected() {
		objectDetected = true;
		for(RobotNervousSystem listener : listeners)  {
			listener.ObjectDetected(new Avoid(robot, this));	
		}
	}

	@Override
	protected void setPort(int port) { robot.SetSensorTouch(port); }

	@Override
	public int scan() { return robot.Sensor(port); }
	
	@Override
	public void run() {
		while(active) {
			super.run();
			
			if(!objectDetected && objectDistance == 1) { objectDetected(); }
			else if(objectDetected && objectDistance != 1) { objectIsGone(); }
			
			try { Thread.sleep(delay); } 
			catch (InterruptedException e) { }
		}
	}

}