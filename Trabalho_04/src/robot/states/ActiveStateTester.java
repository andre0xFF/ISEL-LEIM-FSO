package robot.states;

import robot.MyRobotLego;

public final class ActiveStateTester extends ActiveState {

	public ActiveStateTester(MyRobotLego robot, Scanner scanner, int id) {
		super(robot, scanner, id);
		this.weight = id;
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
