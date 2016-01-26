package robot.activestates;

import robot.MyRobotLego;
import robot.scanners.FrontScanner;
import robot.states.ActiveState;

public final class Avoid extends ActiveState {
	public static final int ID = 4;
	public static final int SCANNER_ID = FrontScanner.ID;
	
	public Avoid(MyRobotLego robot, FrontScanner scanner) {
		super(robot, scanner, ID);
		this.weight = 2;
		this.start();
	}
	
	@Override
	public void run() {
		synchronized(robot) {
			robot.Parar(true);
			robot.SetRelativeSpeed(90);
		}
		
		while(scanner.scan() == 1 && active) {
			super.run();
			action();
			//System.out.println("Avoid, run()");
			delay = MyRobotLego.calculateMovementDelay(robot.getRelativeSpeed(), 20);
			delay += MyRobotLego.calculateMovementDelay(robot.getRelativeSpeed(), 0, 90);
			loopDelay();
		}
		
		synchronized(robot) {
			robot.Parar(false);
			robot.SetRelativeSpeed(originalSpeed);
		}
	}

	@Override
	public void action() {
		synchronized(robot) {
			robot.Reta(-20);
			robot.CurvarEsquerda(0, 90);
		}
	}

}
