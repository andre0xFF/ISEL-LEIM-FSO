package robot.behavior;
import java.util.Random;

import robot.MyRobotLego;

public class Roam extends Thread {
	private final MyRobotLego robot;
	
	public boolean alive;
	
	private final static int AVERAGE_SPEED = 3;
	private final static int DEFAULT_DISTANCE = 10;
	private final static int DEFAULT_DELAY = 1500;
	
	public Roam(MyRobotLego robot) {
		this.robot = robot;
		this.alive = true;
		this.start();
	}
	
	public void run() {

		while(alive) {
			robot.addBehaviour();
			synchronized(robot) {
	
				while(robot.getActiveBehaviours() > 1) {
					try {
					robot.wait();
					} catch (InterruptedException e) {
					e.printStackTrace();
					}		
				}

				System.out.println("Roam: I'm alive " + robot.getActiveBehaviours());
				robot.notify();
			}
			robot.rmBehaviour();
			sleepForAWhile(1000);
		}
			
		this.interrupt();
	}
	
//	public void run() {
//		int r = 0;
//		
//		while(alive) {
//			react(randomNumber(1, 3, r));
//			sleepForAWhile(calculateDelay(0, 0, 0));
//		}
//
//		
//		this.interrupt();
//	}
	
	public void react(int order) {
		switch(order) {
		case 1:
			System.out.print("Moving forward " + DEFAULT_DISTANCE + " units");
			robot.Reta(DEFAULT_DISTANCE);
			robot.Parar(false);
			sleepForAWhile(calculateDelay(DEFAULT_DISTANCE, 0, 0));
			break;
		case 2:
			{
				int radius = randomNumber(5, 15, 0);
				System.out.print("Turning right " + radius + " radius " + 90 + " angle");
				robot.CurvarDireita(radius, 90);
				robot.Parar(false);
			}
			break;
		case 3:
			{
				int radius = randomNumber(5, 15, 0);
				System.out.print("Turning left " + radius + " radius " + 90 + " angle");
				robot.CurvarEsquerda(radius, 90);
				robot.Parar(false);
			}
			break;
		}	
	}
	
	public int randomNumber(int min, int max, int previous) {
		Random r = new Random();
		int i = r.nextInt(max) + min;
		
		if(i == previous) i = randomNumber(min, max, previous);
		
		return i;
	}
	
	private void sleepForAWhile(int ms) {	  
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private int calculateDelay(int distance, int radius, int angle) {
//		if(radius == 0 && angle == 0) return (distance / AVERAGE_SPEED) * 1000;
		if(radius == 0 && angle == 0) return (radius * angle) + 100;
		
		if(distance > 0) return (1100 * distance) / 20;
		
		return DEFAULT_DELAY;
	}
	
	
	
}