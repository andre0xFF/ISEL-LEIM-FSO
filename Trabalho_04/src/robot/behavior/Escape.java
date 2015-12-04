package robot.behavior;
import RobotLego.RobotLego;

public class Escape extends Thread {
  private Boolean alive;
  private RobotLego robot;

  public static Boolean SCANNER;
  public static Boolean REACTOR;

  public Escape(RobotLego robot) {
    this.alive = true;
    this.robot = robot;
    Escape.SCANNER = true;
    Escape.REACTOR = true;
    this.start();
  }

  public Boolean getAlive() { return this.alive; }
  public void setAlive(Boolean alive) { this.alive = alive; }

  public void run() {
	  while(alive) {
		  System.out.println("I'm alive");
		  scan();

		  try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	  }
	  
	  this.interrupt();
  }

  /**
   * Use the scanner to determine if there's an object behind
   * @return
   */
  public boolean scan() {
    if(!Escape.SCANNER) return false;
    
    // Read the sensor: robot.SensorUS()
    // Call react if there's an object behind

    return false;
  }

  /**
   * React to the proximity of an object
   * @param r How close the object is [0..250?]
   */
  public void react(int r) {
    if(!REACTOR) return;

    // Take control of the robot
    // Make the robot move. Speed is relative to the distance of the object detected
  }
}
