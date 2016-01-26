package robot.scanners;

import robot.activestates.Avoid;
import robot.states.Scanner;
import robot.states.StateMachine;

public final class FrontScanner extends Scanner {

	public static final int ID = 2;
	
	public FrontScanner(StateMachine machine, int port) {
		super(machine, ID, port);
		this.start();
	}

	@Override
	protected void setPort(int port) { 
		synchronized(robot) {
			robot.SetSensorTouch(port); 
		}
	}

	@Override
	public int scan() {
		synchronized(robot) {
			return robot.Sensor(port); 
		}
	}
	
	@Override
	public void run() {
		while(active) {
			super.run();
			
			if(!objectDetected && objectDistance == 1) { 
				objectDetected(new Avoid(robot, this)); 
			}
			else if(objectDetected && objectDistance != 1) { 
				objectIsGone(); 
			}
			
			try { Thread.sleep(delay); } 
			catch (InterruptedException e) { }
		}
	}

}