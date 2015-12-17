package robot.automate;

import java.util.Random;
import java.util.concurrent.Semaphore;

import robot.MyRobotLego;
import robot.Scanner;

public class Roam extends Behaviour {
	private final static int DEFAULT_DISTANCE = 10;
	private final static int DEFAULT_RADIUS = 90;
	private final static int DEFAULT_ANGLE = 0;
	private int lastOrder = 0;


	public Roam(MyRobotLego robot, Semaphore permission) {
		super(robot, permission);
		this.start();
	}

	
	@Override
	public void run() {
		while(alive) {
			action();
			Scanner.sleep(Behaviour.DEFAULT_DELAY);
		}
		
		this.interrupt();
	}

	
	@Override
	public void action() {
		try { permission.acquire(); }
		catch (InterruptedException e1) { e1.printStackTrace(); }
		
		int distance = DEFAULT_DISTANCE;
		int radius = DEFAULT_RADIUS;
		int angle = DEFAULT_ANGLE;
		
		switch(lastOrder = randomNumber(1, 3, lastOrder)) {
		case 1:
			distance = DEFAULT_DISTANCE;
			radius = 0;
			angle = 0;
			
			ROBOT.Reta(distance);
			ROBOT.Parar(false);
			break;
		case 2: {
				distance = 0;
				radius = randomNumber(5, 15, 0);
				angle = 90;
				
				ROBOT.CurvarDireita(radius, angle);
				ROBOT.Parar(false);
			}
			break;
		case 3: {
				distance = 0;
				radius = randomNumber(5, 15, 0);
				angle = 90;
				
				ROBOT.CurvarEsquerda(radius, 90);
				ROBOT.Parar(false);
			}
			break;
		}
		
		permission.release();
		System.out.printf("Roam: Distance %d Radius %d Angle %d\n", distance, radius, angle);
		MyRobotLego.sleepForAWhile(MyRobotLego.calculateDelay(distance, radius, angle));
	}
	
	public static int randomNumber(int min, int max, int previous) {
		Random r = new Random();
		int i = r.nextInt(max) + min;
		
		if(i == previous) i = randomNumber(min, max, previous);
		
		return i;
	}


	@Override
	public synchronized void pause() { }

}
