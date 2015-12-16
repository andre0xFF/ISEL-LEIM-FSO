package robot.behavior;

import javax.swing.JTextField;

import robot.MyRobotLego;
import robot.behavior.Escape;

public class Tester {

	public static void main(String[] args) throws InterruptedException {
//		escape();
//		roam();	
		MyRobotLego locker = new MyRobotLego(new JTextField(), false);
		Roam roam = new Roam(locker);	
		
		Thread.sleep(3000);
		System.out.println("Escape starting");
		Escape escape = new Escape(locker, 5, 250);
		
		Thread.sleep(3000);
		System.out.println("Avoid starting");
		Avoid avoid = new Avoid(locker);
		
		Thread.sleep(2000);
		System.out.println("Escape shutting down");
		escape.alive = false;
		
		Thread.sleep(2000);
		System.out.println("Avoid shutting down");
		avoid.alive = false;
	}
	
	private static void escape() throws InterruptedException {
//		Escape escaper = new Escape(null, 5, 250);
//		new Escape(null, 5, 250);
//		Thread.sleep(2500);
//		escaper.alive = false;
//		System.out.println(escaper.isAlive());
//		Thread.sleep(2500);
//		System.out.println(escaper.isAlive());

//		for(int i = 0; i < 50; i++) {
//			escaper.simulateReact(50 + i, 30, 100, 5, 250);
//			Thread.sleep(500);
//		}
	}
	
	private static void roam() throws InterruptedException {
		Roam roamer = new Roam(null);
		Thread thread = new Thread(roamer);
		
		for(int i = 0; i < 20; i++) {
			System.out.println("hi");
			Thread.sleep(1000);
		}
		
	}

}
