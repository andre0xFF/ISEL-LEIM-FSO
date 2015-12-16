package robot.behavior;
import robot.MyRobotLego;

public class Escape extends Thread {
	private final MyRobotLego robot;
	private final int minDistance;
	private final int maxDistance;
	private int objDistance;
	
	public boolean alive;
	public boolean scanner;
	public boolean reactor;
	
	private final static int MIN_SPEED = 30;
	private final static int MAX_SPEED = 100;
	private final static int SCANNER_DELAY = 500;
	private final static int PORT = RobotLego.RobotLego.S_1;
  
  public Escape(MyRobotLego robot, int minDistance, int maxDistance) {
	this.robot = robot;
    this.minDistance = minDistance;
    this.maxDistance = maxDistance;
	this.alive = true;
    this.scanner = true;
    this.reactor = true;
    //this.robot.SetSensorLowspeed(PORT);
    this.start();
  }

  public void run() {

	while(alive) {
		robot.addBehaviour();
		synchronized(robot) {
			
			while(robot.getActiveBehaviours() > 2) {
				try {
				robot.wait();
				} catch (InterruptedException e) {
				e.printStackTrace();
				}
			}
			
			System.out.println("Escape: I'm alive " + robot.getActiveBehaviours());
			sleepForAWhile(500);
			robot.notify();
		}
		robot.rmBehaviour();

	}
	

	this.interrupt();

  }
	
//	public void run() {
//		while(alive) {			
//			if(scan() > 0 && reactor) react(scan(), MIN_SPEED, MAX_SPEED, minDistance, maxDistance);	  
//			  
//			sleepForAWhile(SCANNER_DELAY);
//		}
//		  
//		this.interrupt();
//	}

  /**
   * Read the scanner result
   * @return How far the object is related to the robot [0..255]
   */
  private int scan() {
	// TODO: If there is no object behind what value this returns?	
    return robot.SensorUS(PORT);
  }

  /**
   * React to the proximity of an object
   * @param distance Object distance
   * @param minSpeed Minimum robot speed
   * @param maxSpeed Maximum robot speed
   * @param minDistance Safe zone minimum distance
   * @param maxDistance Safe zone maximum allowed distance
   */
  public void react(int objectDistance, int minSpeed, int maxSpeed, int minDistance, int maxDistance) {
	synchronized(robot) {
		if(objectDistance < minDistance || objectDistance > maxDistance) return;
		
		int relDistance = objectDistance - minDistance;
		
		int speed = maxSpeed - (relDistance * 100 / maxDistance);
		
		if(speed < minSpeed) speed += minSpeed - speed;
		
		robot.Parar(true);
		robot.SetSpeed(speed);
		robot.Reta(maxDistance - objectDistance);
	}
  }
  
  protected void simulateReact(int objDistance, int minSpeed, int maxSpeed, int minDistance, int maxDistance) {		
    if(objDistance < minDistance || objDistance > maxDistance) return;
    
    int relDistance = objDistance - minDistance;
    
    int speed = maxSpeed - (relDistance * 100 / maxDistance);
    
    if(speed < minSpeed) speed += minSpeed - speed;
    
    System.out.printf("Speed: %d\n", speed);
    System.out.printf("Moving forward: %d\n", maxDistance - objDistance);
  }
  
  private void sleepForAWhile(int ms) {	  
	try {
		Thread.sleep(ms);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
  }
}
