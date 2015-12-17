package robot;

public class FrontScanner extends Scanner {	
	public FrontScanner(MyRobotLego robot, int port) {
		super(robot, port);
		this.start();
	}
	
	
	public void run() {
		while(alive) {
			if(scan() > 0) objectDetected(0);
			MyRobotLego.sleepForAWhile(getDelay());
		}
		this.interrupt();
	}


	@Override
	public synchronized int scan() {
		return ROBOT.Sensor(PORT);
	}


	@Override
	public void objectDetected(int distance) {
		for(ObjectListener listener : listeners) {
			listener.frontObjectDetected(distance);
		}
		
	}
}