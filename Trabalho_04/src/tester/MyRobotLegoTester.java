package tester;

import robot.MyRobotLego;

public final class MyRobotLegoTester extends MyRobotLego {

	public MyRobotLegoTester() {
		super();
	}
	
	public void TestActiveState(int weight) {
		stateMachine.setActiveState(new tester.ActiveStateTester(this, null, weight));
	}
	
	public void TestPassiveState() {
		stateMachine.setPassiveState(new tester.PassiveStateTester(this, 0));
	}

}
