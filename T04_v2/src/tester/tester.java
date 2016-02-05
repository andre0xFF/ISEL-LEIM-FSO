package tester;

import robot.MyRobotLego;
import robot.states.StateMachine;

public class tester {

	public static void main(String[] args) throws InterruptedException {
		StateMachine machine = new StateMachine(new MyRobotLego());
		
		machine.waitState();
		
		Thread.sleep(10000);
		
		machine.runState(10, 100);		
		Thread.sleep(3000);
		
		machine.avoidState();
		Thread.sleep(2000);
		machine.avoidState();	

		Thread.sleep(5000);
		
		machine.runState(10, 100);
		Thread.sleep(5000);
	}

}
