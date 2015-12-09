package robot.behavior;
import robot.MyRobotLego;

public class Escape extends Thread {

  private Boolean alive;
  
  private static boolean active = true;
  public void setActivee(boolean escape) { this.active = escape; }
  
  private MyRobotLego robot;
  private int minDistance;
  private int maxDistance;
  private int objDistance;

  public static Boolean SCANNER;
  public static Boolean REACTOR;
  
  private final static int MIN_SPEED = 30;
  private final static int MAX_SPEED = 100;
  private final static int SCANNER_DELAY= 500;
  private final static int PORT = RobotLego.RobotLego.S_1;
  
  public Escape(MyRobotLego robot, int minDistance, int maxDistance) {
    this.alive = true;
    this.robot = robot;
    Escape.SCANNER = true;
    Escape.REACTOR = true;
    this.minDistance = minDistance;
    this.maxDistance = maxDistance;
    this.start();
  }

  public Boolean getAlive() { return this.alive; }
  public Boolean getActive() { return (Escape.SCANNER && Escape.REACTOR); }
  public int getMinDistance() { return this.minDistance; }
  public int getMaxDistance() { return this.maxDistance; }
  
  public void setAlive(Boolean alive) { this.alive = alive; }
  public void setMinDistance(int distance) { this.minDistance = distance; }
  public void setMaxDistance(int distance) { this.maxDistance = distance; }
  public void setActive(Boolean active) { 
	Escape.SCANNER = active;
	Escape.REACTOR = active;
  }

  public void run() {
	for(int j = 0; j < 20; j++) {
		System.out.println(active);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	return;
	
//	while(alive) {	  
//	  if(Escape.SCANNER && Escape.REACTOR) react(scan(), MIN_SPEED, MAX_SPEED, minDistance, maxDistance);		  
//	    sleepForAWhile(SCANNER_DELAY);
//	  }
//	  
//	this.interrupt();
  }

  /**
   * Read the scanner result
   * @return How far the object is related to the robot [0..255]
   */
  private int scan() {
	// TODO: If there is no object behind what value this returns?
	robot.SetSensorLowspeed(Escape.PORT);
    return robot.SensorUS(Escape.PORT);
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
