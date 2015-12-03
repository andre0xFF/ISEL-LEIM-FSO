public class Escape extends Thread {
  private RobotLego robot;
  private Boolean alive;

  private Boolean scanner;
  private Boolean reactor;

  public Escape(RobotLego robot) {
    this.robot = robot;
    this.scanner = true;
    this.reactor = true;
    start();
  }

  public Boolean getReactor() { return this.reactor; }
  public Boolean getScanner() { return this.scanner; }
  public Boolean setReactor(Boolean reactor) { this.reactor = reactor; }
  public Boolean setScanner(Boolean scanner) { this.scanner = scanner; }

  public void run() {
    while() {

    }
  }

  @Override
  public void start() { super(); this.alive = true; }
  public void stop() { this.alive = false; }

  public boolean scan() {
    if(!scanner) return false;

    int r = robot.SensorUS(MyRobotLego.SENSOR_BACK);
    if(r > 0) react(r);
  }

  public void react() {
    if(!reactor) return false;

    // take control of the robot

  }
}
