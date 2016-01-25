package robot.states;

import robot.MyRobotLego;

public final class Avoid extends ActiveState {
	public static final int ID = 4;
	
	public Avoid(MyRobotLego robot, FrontScanner scanner) {
		super(robot, scanner, ID);
		this.weight = 2;
	}
	
	@Override
	public void run() {
		while(scanner.scan() == 1) {
			super.run();
			action();
			
			delay = MyRobotLego.calculateMovementDelay(robot.getSpeed(), 20);
			delay += MyRobotLego.calculateMovementDelay(robot.getSpeed(), 0, 90);
			loopDelay();
		}
		
		robot.Parar(false);
	}

	@Override
	public void action() {
		robot.Reta(-20);
		robot.CurvarEsquerda(0, 90);
	}

}
