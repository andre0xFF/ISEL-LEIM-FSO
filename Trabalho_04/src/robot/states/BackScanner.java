package robot.states;

public final class BackScanner extends Scanner {

public static final int ID = 1;
	
	private int[] trigger = new int[2];

	public BackScanner(StateMachine machine, int port, int[] trigger) {
		super(machine, ID, port);
		this.trigger = trigger;
	}

	@Override
	protected void objectDetected() {
		objectDetected = true;
		for(RobotNervousSystem listener : listeners)  {
			listener.ObjectDetected(new Escape(robot, this, trigger));	
		}
	}

	@Override
	protected void setPort(int port) { robot.SetSensorLowspeed(port); }

	@Override
	public int scan() { return robot.SensorUS(port); }
	
	@Override
	public void run() { 
		while(active) {
			super.run();

			if(!objectDetected && (objectDistance > trigger[0] && objectDistance < trigger[1])) {
				objectDetected();
			}			
			else if(objectDetected && (objectDistance < trigger[0] || objectDistance > trigger[1])) {
				objectIsGone();
			}
						
			try { Thread.sleep(delay); } 
			catch (InterruptedException e) { }
		}
	}

}