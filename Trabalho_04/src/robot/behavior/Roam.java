package robot.behavior;
import java.util.Random;

import robot.MyRobotLego;

public class Roam extends Thread {
	private boolean alive;
	
	private MyRobotLego robot;
	
	private final static int AVERAGE_SPEED = 3;
	private final static int DEFAULT_DISTANCE = 10;
	private final static int DEFAULT_DELAY = 1500;
	
	public Roam(MyRobotLego robot) {
		this.alive = true;
		this.robot = robot;
		this.start();
	}
	
	public boolean getAlive() { return this.alive; }
	
	public void setAlive(Boolean alive) { this.alive = alive; }
	
	public void run() {
		int r = 0;
		
		while(alive) {
			r = randomNumber(1, 3, r);
			switch(r) {
			case 1:
//				robot.Reta(DEFAULT_DISTANCE);
//				robot.Parar(false);
				sleepForAWhile(calculateDelay(DEFAULT_DISTANCE) - 1500);
				break;
			case 2:	
//				robot.CurvarDireita(randomNumber(5, 15, 0), 90);
//				robot.Parar(false);
				break;
			case 3:
//				robot.CurvarEsquerda(randomNumber(5, 15, 0), 90);
//				robot.Parar(false);
				break;
			}
			
			System.out.print(r);
			sleepForAWhile(DEFAULT_DELAY);			
		}
		
		this.interrupt();
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
	
	private int calculateDelay(int distance) {
		return (distance / AVERAGE_SPEED) * 1000;
	}
	
	
	
}