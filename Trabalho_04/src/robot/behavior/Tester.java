package robot.behavior;

import robot.behavior.Escape;

public class Tester {

	public static void main(String[] args) throws InterruptedException {
		Escape escaper = new Escape(null, 5, 250);
		Thread thread = new Thread(escaper);
		
//		Thread.sleep(7000);
		
		Escape.SCANNER = false;
		Escape.REACTOR = false;
	
		System.out.println("Scanner: " + Escape.SCANNER);
		System.out.println("Scanner: " + Escape.REACTOR);

		for(int i = 0; i < 50; i++) {
			escaper.simulateReact(50 + i, 30, 100, 5, 250);	
			Thread.sleep(50);
		}
		
		
		
		System.out.println("Thread: " + escaper.getAlive());
		escaper.setAlive(false);
		thread.join();
		System.out.println("Thread: " + escaper.getAlive());
	}

}
