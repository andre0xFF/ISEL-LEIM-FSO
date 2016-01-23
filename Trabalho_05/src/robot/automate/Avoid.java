package robot.automate;

import robot.FrontScanner;
import robot.MyRobotLego;

public class Avoid extends Behaviour {
	private final FrontScanner scanner;

	public Avoid(MyRobotLego robot, FrontScanner scanner) {
		super(robot);
		this.scanner = scanner;
		setPriority(MAX_PRIORITY);
		this.start();
	}


	@Override
	public void run() {
		super.run();
		action();
		this.interrupt();

	}


	@Override
	public void action() {
		System.out.println("Avoid: -20, left 90");
		robot.SetSpeed(50);
		robot.Parar(true);
		robot.Reta(-20);
		robot.CurvarEsquerda(0, 90);
		robot.Parar(false);
		MyRobotLego.sleep(calculateDelay(20, 0, 90) - getDelay());

	}

}
