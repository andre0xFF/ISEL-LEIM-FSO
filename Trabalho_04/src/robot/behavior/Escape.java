package robot.behavior;
import robot.MyRobotLego;

public class Escape extends Thread {  
  private MyRobotLego robot;
  private int minDistance;
  private int maxDistance;
  private int objDistance;

  public static boolean ALIVE;
  public static boolean SCANNER;
  public static boolean REACTOR;
  
  private final static int MIN_SPEED = 30;
  private final static int MAX_SPEED = 100;
  private final static int SCANNER_DELAY = 500;
  private final static int PORT = RobotLego.RobotLego.S_1;
  
  public Escape(MyRobotLego robot, int minDistance, int maxDistance) {
	this.robot = robot;
    this.minDistance = minDistance;
    this.maxDistance = maxDistance;
	Escape.ALIVE = true;
    Escape.SCANNER = true;
    Escape.REACTOR = true;
    this.start();
  }

//  public void run() {
//	for(int j = 0; j < 20; j++) {
//		System.out.println("Escape: " + Escape.ALIVE);
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	return;
	
	public void run() {
		while(ALIVE) {	  
		  if(REACTOR) react(scan(), MIN_SPEED, MAX_SPEED, minDistance, maxDistance);		  
		    sleepForAWhile(SCANNER_DELAY);
		  }
		  
		this.interrupt();
  }

  /**
   * Read the scanner result
   * @return How far the object is related to the robot [0..255]
   */
  private int scan() {
	// TODO: If there is no object behind what value this returns?
	robot.SetSensorLowspeed(PORT);
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
  public void react(int distance, int minSpeed, int maxSpeed, int minDistance, int maxDistance) {
    if(objDistance < minDistance || objDistance > maxDistance) return;
    
    int relDistance = distance - minDistance;
    
    int speed = maxSpeed - (relDistance * 100 / maxDistance);
    
    if(speed < minSpeed) speed += minSpeed - speed;
    
    robot.Parar(true);
    robot.SetSpeed(speed);
    robot.Reta(maxDistance - objDistance);
  }
  
  protected void simulateReact(int objDistance, int minSpeed, int maxSpeed, int minDistance, int maxDistance) throws InterruptedException {		
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
