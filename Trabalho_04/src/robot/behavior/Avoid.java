package robot.behavior;
import robot.MyRobotLego;

public class Avoid extends Thread {
	private boolean alive;
	
	private MyRobotLego robot;
	
	private final static int SCANNER_DELAY= 500;
	private final static int PORT = RobotLego.RobotLego.S_2;
	
	public static boolean SCANNER;
	public static boolean REACTOR;

	public Avoid(MyRobotLego robot) {
	    this.alive = true;
	    this.robot = robot;
	    Escape.SCANNER = true;
	    Escape.REACTOR = true;
	    this.start();
	}

	public boolean getAlive() { return this.alive; }
	public boolean getActive() { return (Avoid.SCANNER && Avoid.REACTOR); }
	
	public void setAlive(Boolean alive) { this.alive = alive; }
	public void setActive(Boolean active) { 
		Escape.SCANNER = active;
		Escape.REACTOR = active;
	}

	public void run() {
		while(alive) {
			if(scan()) react();
			sleepForAWhile(SCANNER_DELAY);
		}
	  
		this.interrupt();
	}

	/**
	 * Use the touch sensor to check for collision
	 * @return if collision occurred
	 */
	public boolean scan() {
		robot.SetSensorTouch(Avoid.PORT);
		robot.Sensor(Avoid.PORT);
		return robot.Sensor(Avoid.PORT) == 1 ? true : false;
	}
	
	/**
	 * Makes the robot avoid an obstacle
	 */
	public void react() {
		robot.Parar(true);
		robot.Reta(-20);
		robot.Parar(false);
		robot.CurvarEsquerda(0, 90);
		robot.Parar(false);
  	}

	  
	private void sleepForAWhile(int ms) {	  
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}