package tester;

import robot.MyRobotLego;
import robot.states.PassiveState;

public final class PassiveStateTester extends PassiveState {

	public PassiveStateTester(MyRobotLego robot, int id) {
		super(robot, id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void run() {
		while(active) {
			super.run();
			System.out.println(id);
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
