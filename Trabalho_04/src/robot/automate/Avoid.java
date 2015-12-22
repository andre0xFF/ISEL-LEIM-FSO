package robot.automate;

import robot.FrontScanner;
import robot.MyRobotLego;

public class Avoid extends Behaviour {
	private final FrontScanner scanner;

	public Avoid(MyRobotLego robot, FrontScanner scanner) {
		super(robot);
		this.scanner = scanner;
		setPriority(MAX_PRIORITY);
	}


	@Override
	public void run() {
		while(scanner.scan() > 0) {
			synchronized(robot) {
				action();
				MyRobotLego.sleep(getDelay());
			}
		}
		
		this.interrupt();	
	}


	@Override
	public void action() {
		System.out.println("Avoid: -20, left 90");
		robot.Reta(-20);
		robot.CurvarEsquerda(0, 90);
		robot.Parar(false);
		
		MyRobotLego.sleep(calculateDelay(20, 0, 90) - getDelay());
	}

}
