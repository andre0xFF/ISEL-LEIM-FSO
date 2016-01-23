package robot.automate;

import java.util.Random;
import robot.MyRobotLego;

public class Roam extends Behaviour {
	private final static int DEFAULT_DISTANCE = 10;
	private final static int DEFAULT_RADIUS = 90;
	private final static int DEFAULT_ANGLE = 0;
	
	private int lastOrder = 0;

	public Roam(MyRobotLego robot) {
		super(robot);
		setPriority(MIN_PRIORITY);
		this.start();
	}
	
	@Override
	public void run() {
		while(active) {
			super.run();
			action();
			MyRobotLego.sleep(getDelay());
		}
		robot.Parar(false);
		this.interrupt();

	}
	@Override
	public void action() {
		
		int distance = DEFAULT_DISTANCE;
		int radius = DEFAULT_RADIUS;
		int angle = DEFAULT_ANGLE;
		
		switch(lastOrder = randomOrder(1, 3, lastOrder)) {
		case 1:
			distance = randomOrder(5, 45, 0);
			radius = 0;
			angle = 0;
			
			robot.Reta(distance);
			break;
		case 2:
			distance = 0;
			radius = randomOrder(5, 15, 0);
			angle = 90;
			
			robot.CurvarDireita(radius, angle);
			break;
		case 3:
			distance = 0;
			radius = randomOrder(5, 15, 0);
			angle = 90;
			
			robot.CurvarEsquerda(radius, 90);
			break;
		}
		
		System.out.printf("Roam: Distance %d Radius %d Angle %d Delay %d\n", distance, radius, angle, calculateDelay(distance, radius, angle));
		MyRobotLego.sleep(calculateDelay(distance, radius, angle));
	}
	
	public static int randomOrder(int min, int max, int previous) {
		Random r = new Random();
		int i = r.nextInt(max) + min;
		
		if(i == previous) i = randomOrder(min, max, previous);
		
		return i;
	}

}
