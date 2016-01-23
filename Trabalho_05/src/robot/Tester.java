package robot;

import robot.behavior.Escape;

public class Tester {

	public static void main(String[] args) throws InterruptedException {
		Escape escaper = new Escape(null);
		Thread thread = new Thread(escaper);
		
		Thread.sleep(7000);
	
		Escape.SCANNER = true;
		Escape.REACTOR = true;
		System.out.println("Scanner: " + Escape.SCANNER);
		System.out.println("Scanner: " + Escape.REACTOR);
		Escape.SCANNER = false;
		Escape.REACTOR = false;
		System.out.println("Scanner: " + Escape.SCANNER);
		System.out.println("Scanner: " + Escape.REACTOR);
		
		System.out.println("Thread: " + escaper.getAlive());
		escaper.setAlive(false);
		thread.join();
		System.out.println("Thread: " + escaper.getAlive());
	}

}
