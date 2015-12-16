package robot.behavior;
import robot.MyRobotLego;

public class Avoid extends Thread {
	private final MyRobotLego robot;
	
	public boolean alive;
	public boolean scanner;
	public boolean reactor;
	
	private final static int SCANNER_DELAY = 500;
	private final static int PORT = RobotLego.RobotLego.S_2;

	public Avoid(MyRobotLego robot) {
	    this.robot = robot;
	    this.alive = true;
	    this.scanner = true;
	    this.reactor = true;
	    //this.robot.SetSensorTouch(PORT);
	    this.start();
	}
	
	public void run() {
		while(alive) {
			robot.addBehaviour();
			synchronized(robot) {
				System.out.println("Avoid: I'm alive " + robot.getActiveBehaviours());
				sleepForAWhile(500);
				robot.notify();
			}
			robot.rmBehaviour();
		}
	}

//	public void run() {
//		while(alive) {
//			if(scan()) react();
//			sleepForAWhile(SCANNER_DELAY);
//		}
//		
//		this.interrupt();
//	}

	/**
	 * Use the touch sensor to check for collision
	 * @return if collision occurred
	 */
	public boolean scan() {
		return robot.Sensor(PORT) == 1 ? true : false;
	}
	
	/**
	 * Makes the robot avoid an obstacle
	 */
	public void react() {
		synchronized(robot) {
			robot.Parar(true);
			robot.Reta(-20);
			robot.Parar(false);
			robot.CurvarEsquerda(0, 90);
			robot.Parar(false);
		}
  	}

	  
	private void sleepForAWhile(int ms) {	  
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}