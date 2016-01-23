package robot;

public final class BackScanner extends Scanner {
	private int minDistance = 5;
	private int maxDistance = 250;
	
	public int getMinDistance() { return this.minDistance; }
	public int getMaxDistance() { return this.maxDistance; }
	public void setMinDistance(int min) { this.minDistance = min; }
	public void setMaxDistance(int max) { this.maxDistance = max; }
	
	public BackScanner(MyRobotLego robot, int port) {
		super(robot, port);
		this.setPriority(MIN_PRIORITY);
	}
	
	public BackScanner(MyRobotLego robot, int port, int minDistance, int maxDistance) {
		super(robot, port);
		this.minDistance = minDistance;
		this.maxDistance = maxDistance;
	}
	
	@Override 
	public int getDelay() { return this.delay; }
	
	@Override
	protected void setPort(int port) { robot.SetSensorLowspeed(port); }
	
	@Override
	public int scan() { return robot.SensorUS(port); }
	
	@Override
	public void objectDetected(int distance) {		
		objectDetected = true;
		for(RobotNervousSystem listener : listeners)  listener.rearObjectDetected(distance);
	}
	
	@Override
	public void objectIsGone() {		
		objectDetected = false;
		for(RobotNervousSystem listener : listeners) listener.rearObjectIsGone();
	}	
	
	public void run() {
		while(active) {
			super.run();
			int scanResult = scan();
			System.out.println("BackScanner: " + scanResult);
			if(scanResult > minDistance && scanResult < maxDistance) objectDetected(scanResult);
			else if(objectDetected && (scanResult <= minDistance || scanResult >= maxDistance)) objectIsGone();
			MyRobotLego.sleep(getDelay());
		}
	}

}
