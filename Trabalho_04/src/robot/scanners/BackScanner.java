package robot.scanners;

import robot.activestates.Escape;
import robot.states.Scanner;
import robot.states.StateMachine;

public final class BackScanner extends Scanner {

public static final int ID = 1;
	
	private int[] trigger = new int[2];

	public BackScanner(StateMachine machine, int port, int[] trigger) {
		super(machine, ID, port);
		this.trigger = trigger;
		this.start();
	}

	@Override
	protected void setPort(int port) { 
		synchronized(robot) {
			robot.SetSensorLowspeed(port); 
		}
	}

	@Override
	public int scan() { return robot.SensorUS(port); }
	
	@Override
	public void run() { 
		while(active) {
			super.run();
			
			if(!objectDetected && (objectDistance > trigger[0] && objectDistance < trigger[1])) {
				objectDetected(new Escape(robot, this, trigger));
			}			
			else if(objectDetected && (objectDistance < trigger[0] || objectDistance > trigger[1])) {
				objectIsGone();
			}
			//System.out.printf("BackScanner, run(), detected: %s distance: %d\n", objectDetected, objectDistance);
			try { Thread.sleep(delay); } 
			catch (InterruptedException e) { }
		}
	}
}