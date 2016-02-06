package robot;

import RobotLego.RobotLego;

public class RobotSimulation extends RobotLego {
	private void log(String msg) {
		System.out.println(msg);
	}
	
	protected boolean OpenNXT() {
		log("Connection is open");
		return true;
	}
}
