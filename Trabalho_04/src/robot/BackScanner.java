package robot;

public class BackScanner extends Scanner {
	private int minDistance = 5;
	private int maxDistance = 250;
	
	
	public BackScanner(MyRobotLego robot, int port) {
		super(robot, port);
		ROBOT.SetSensorLowspeed(port);
		this.start();
	}
	
	
	public BackScanner(MyRobotLego robot, int port, int minDistance, int maxDistance) {
		super(robot, port);
		this.minDistance = minDistance;
		this.maxDistance = maxDistance;
		ROBOT.SetSensorLowspeed(port);
		this.start();
	}
	
	
	public void setMinDistance(int min) { this.minDistance = min; }
	public void setMaxDistance(int max) { this.maxDistance = max; }
	public int getMinDistance() { return this.minDistance; }
	public int getMaxDistance() { return this.maxDistance; } 
	
	
	public void run() {
		while(alive) {
			if(scan() > 0) objectDetected(scan());
			MyRobotLego.sleepForAWhile(getDelay());	
		}
		this.interrupt();
	}

	
	@Override
	public synchronized int scan() {
		return ROBOT.SensorUS(PORT);
	}

	
	@Override
	public void objectDetected(int distance) {	
		for(ObjectListener listener : listeners) {
			listener.backObjectDetected(scan());
		}
	}
	
	
}
