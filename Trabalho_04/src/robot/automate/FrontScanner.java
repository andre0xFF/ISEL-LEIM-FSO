package robot.automate;
import robot.MyRobotLego;

public class FrontScanner extends Scanner {	
	public FrontScanner(MyRobotLego robot, int port) {
		super(robot, port);
		this.start();
	}
	
	
	public void run() {
		while(alive) {
			if(scan() > 0) react();
			MyRobotLego.sleepForAWhile(super.getDelay());
		}
		this.interrupt();
	}


	@Override
	public synchronized int scan() {
		return ROBOT.Sensor(PORT);
	}


	@Override
	public synchronized void react() {
		new Avoid(ROBOT);	
	}
}
