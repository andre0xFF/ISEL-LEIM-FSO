package robot.automate;

import robot.MyRobotLego;

public class Avoid extends Behaviour {

	public Avoid(MyRobotLego robot) {
		super(robot);
		this.start();
	}

	@Override
	public void run() {
		action();	
		this.interrupt();	
	}

	@Override
	public void action() {
		ROBOT.addBehaviour();
		synchronized(ROBOT) {
			System.out.println("Avoid: -20, left 90");
			ROBOT.Parar(true);
			ROBOT.Reta(-20);
			ROBOT.Parar(false);
			ROBOT.CurvarEsquerda(0, 90);
			ROBOT.Parar(false);
			ROBOT.notify();
		}
		ROBOT.rmBehaviour();
	}

}
