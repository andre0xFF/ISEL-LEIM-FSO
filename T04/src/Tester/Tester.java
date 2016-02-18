package Tester;

import Robot.MyRobotLego;
import Robot.States.StateMachine;

public class Tester {

	public static void main(String[] args) throws InterruptedException {
		StateMachine machine = new StateMachine(new MyRobotLego());
		
		machine.waitState();
		
		Thread.sleep(5000);
		
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
