package robot.automate;

import java.util.Random;

import robot.MyRobotLego;

public class Roam extends Behaviour {
	private final static int DEFAULT_DISTANCE = 10;
	private int lastOrder = 0;

	public Roam(MyRobotLego robot) {
		super(robot);
		this.start();
	}

	@Override
	public void run() {
		while(alive) {
				action();
		}
		
		this.interrupt();		
	}

	@Override
	public void action() {
		synchronized(ROBOT) {
			switch(lastOrder = randomNumber(1, 3, lastOrder)) {
			case 1:
				System.out.println("Roam: Moving forward " + DEFAULT_DISTANCE + " units");
				ROBOT.Reta(DEFAULT_DISTANCE);
				ROBOT.Parar(false);
				MyRobotLego.sleepForAWhile(MyRobotLego.calculateDelay(DEFAULT_DISTANCE, 0, 0));
				break;
			case 2: {
					int radius = randomNumber(5, 15, 0);
					System.out.println("Roam: Turning right " + radius + " radius " + 90 + " angle");
					ROBOT.CurvarDireita(radius, 90);
					ROBOT.Parar(false);
					MyRobotLego.sleepForAWhile(MyRobotLego.calculateDelay(0, radius, 90));
				}
				break;
			case 3: {
					int radius = randomNumber(5, 15, 0);
					System.out.println("Roam: Turning left " + radius + " radius " + 90 + " angle");
					ROBOT.CurvarEsquerda(radius, 90);
					ROBOT.Parar(false);
					MyRobotLego.sleepForAWhile(MyRobotLego.calculateDelay(0, radius, 90));
				}
				break;
			}
			ROBOT.notify();
		}
	}
	
	public static int randomNumber(int min, int max, int previous) {
		Random r = new Random();
		int i = r.nextInt(max) + min;
		
		if(i == previous) i = randomNumber(min, max, previous);
		
		return i;
	}

}
