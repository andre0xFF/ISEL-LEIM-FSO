package robot;

public final class FrontScanner extends Scanner {
	public FrontScanner(MyRobotLego robot, int port) {
		super(robot, port);
		this.setPriority(MAX_PRIORITY);
	}
	
	@Override
	public int getDelay() { return this.delay; }
	
	@Override
	protected void setPort(int port) { robot.SetSensorTouch(port); }
	
	@Override
	public int scan() { return robot.Sensor(port); }
	
	@Override
	public void objectDetected(int distance) {
		if(objectDetected) return;
		
		objectDetected = true;
		
		for(RobotNervousSystem listener : listeners) listener.frontObjectDetected(distance);
	}
	
	@Override
	protected void objectIsGone() {
		if(!objectDetected) return;
		
		objectDetected = false;
		for(RobotNervousSystem listener : listeners) listener.frontObjectIsGone();	
	}
	
	public void run() {
		while(active) {
			super.run();
			if(!objectDetected && scan() > 0) objectDetected(scan());
			MyRobotLego.sleep(getDelay());
		}
	}
	
}