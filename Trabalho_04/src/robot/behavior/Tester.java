package robot.behavior;

import robot.behavior.Escape;

public class Tester {

	public static void main(String[] args) throws InterruptedException {
		escape();
//		roam();
	}
	
	private static void escape() throws InterruptedException {
		Escape escaper = new Escape(null, 5, 250);
		for(int j = 0; j < 20; j++) {
			System.out.println("Tester: " + Escape.ALIVE);
			Thread.sleep(1000);
			Escape.ALIVE = !Escape.ALIVE;
		}
//		Thread thread = new Thread(escaper);
	
		System.out.println("Scanner: " + Escape.SCANNER);
		System.out.println("Scanner: " + Escape.REACTOR);

		for(int i = 0; i < 50; i++) {
			escaper.simulateReact(50 + i, 30, 100, 5, 250);
			Thread.sleep(500);
		}
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
