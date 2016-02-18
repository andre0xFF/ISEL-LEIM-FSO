package Robot.Scanners;
import java.util.ArrayList;
import java.util.List;

import Robot.MyRobotLego;
import Robot.States.StateMachine;
import Robot.States.StateTrigger;

public abstract class Scanner extends Thread {
	private static final int DEFAULT_DELAY = 100;
	protected static final boolean LOG = true;
	
	protected final MyRobotLego robot;
	
	protected boolean active = true;
	protected int delay = DEFAULT_DELAY;
	public final int id;
	protected final int[] trigger;
	
	/* Who will listen to scanner's events */
	protected List<StateTrigger> listeners = new ArrayList<StateTrigger>();
	
	/* Scanner's event triggers */
	protected volatile int objectDistance = -1;
	protected volatile boolean objectDetected = false;
	
	protected void addListener(StateTrigger toAdd) { 
		this.listeners.add(toAdd); 
	}
	
	public int findObjectDistance() {
		objectDistance = scan();
		return objectDistance;
	}
	
	/* What each scanner will report */
	protected void objectDetected(int stateID) {
		objectDetected = true;
		
		for(StateTrigger listener : listeners)  {
			listener.ObjectDetected(stateID);	
		}
	}
	
	// Event triggered when an object is not longer detected
	public void objectIsGone(int stateID) {
		objectDetected = false;
		
		for(StateTrigger listener : listeners)  {
			listener.ObjectIsGone(stateID);	
		}
	}
	
	public int[] getTrigger() {
		return this.trigger;
	}
	
	public Scanner(MyRobotLego robot, StateMachine machine, int id, int[] trigger) {
		this.robot = robot;
		this.id = id;
		addListener(machine);
		this.trigger = trigger;
		setPort();
	}
	
	public void deactivate() {
		if(active) { active = false; }
	}
	
	private void scanDelay() {
		try { Thread.sleep(delay); } 
		catch (InterruptedException e) { e.printStackTrace(); }
	}
	
	@Override
	public void run() {
		findObjectDistance();

		if(!objectDetected && newObjectDetected(trigger, objectDistance)) {
			objectDetected(id);
			if(LOG) { log(); }
		} else if(objectDetected && oldObjectGone(trigger, objectDistance)) {
			objectIsGone(id);
			if(LOG) { log(); }
		}
		
		scanDelay();
	}
	
	protected abstract void setPort();
	protected abstract int scan();
	protected abstract boolean newObjectDetected(int[] trigger, int objectDistance);
	protected abstract boolean oldObjectGone(int[] trigger, int objectDistance);
	protected abstract void end();
	protected abstract void log();
}
