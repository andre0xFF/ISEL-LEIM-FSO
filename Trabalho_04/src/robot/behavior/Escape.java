package robot.behavior;
import robot.MyRobotLego;

public class Escape extends Thread {
  private Boolean alive;
  
  private MyRobotLego robot;
  private int minDistance;
  private int maxDistance;
  private int objDistance;

  public static Boolean SCANNER;
  public static Boolean REACTOR;
  
  private final static int MIN_SPEED = 30;
  private final static int MAX_SPEED = 100;
  private final static int SCANNER_DELAY= 500;
  private final static int PORT = MyRobotLego.S_1;
  
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
  
  public void setAlive(Boolean alive) { this.alive = alive; }
  public void setActive(Boolean active) { 
	  Escape.SCANNER = active;
	  Escape.REACTOR = active;
  }

  public void run() {
	  while(alive) {	  
		  if(Escape.SCANNER && Escape.REACTOR) react(scan(), MIN_SPEED, MAX_SPEED, minDistance, maxDistance);
		  
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
    return robot.SensorUS(Escape.PORT);
  }

  /**
   * React to the proximity of an object
   * @param distance How close the object is related to the minimum distance
   */
  public void react(int distance, int minSpeed, int maxSpeed, int minDistance, int maxDistance) {
    if(objDistance < minDistance || objDistance > maxDistance) return;
    
    robot.SetSensorLowspeed(Escape.PORT);
    
    int relDistance = distance - minDistance;
    
    int speed = maxSpeed - (relDistance * 100 / maxDistance);
    
    if(speed < minSpeed) speed += minSpeed - speed;
    
    robot.Parar(true);
    robot.SetSpeed(speed);
    robot.Reta(maxDistance - objDistance);
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
