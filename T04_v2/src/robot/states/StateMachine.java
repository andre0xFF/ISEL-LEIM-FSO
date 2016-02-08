package robot.states;

import java.util.Random;
import robot.states.RobotState;
import robot.MyRobotLego;
import robot.scanners.*;

public class StateMachine implements StateTrigger {
	private final static int TOTAL_STATES = 4;
	private final static int STATE_ON = 1;
	private final static int STATE_OFF = 0;
	
	private final MyRobotLego robot;	
	private static int DEFAULT_STATE;
	
	private RobotState currentState;
	private int[] states = new int[TOTAL_STATES];
	
	private BackScanner bScanner;
	private FrontScanner fScanner;
	
	public StateMachine(MyRobotLego robot) {
		this.robot = robot;
		setDefaultState(WaitState.ID);
	}
	
	public void waitState() {
		setDefaultState(WaitState.ID);
	}
	
	public void walkState() {
		setDefaultState(WalkState.ID);
	}
	
	public boolean runState(int minDistance, int maxDistance) {
		if(bScanner == null) {
			this.bScanner = new BackScanner(robot, this, new int[] { minDistance, maxDistance });
			return true;
		} else {
			bScanner.deactivate();
			states[RunState.ID] = STATE_OFF;
			bScanner = null;
			nextState();
			return false;
		}
	}
	
	public boolean avoidState() {
		if(fScanner == null) {
			this.fScanner = new FrontScanner(robot, this);
			return true;
		} else {
			fScanner.deactivate();
			states[AvoidState.ID] = STATE_OFF;
			fScanner = null;
			nextState();
			return false;
		}
	}
	
	private void setDefaultState(int id) {
		states[DEFAULT_STATE] = STATE_OFF;
		states[id] = STATE_ON;
		DEFAULT_STATE = id;
		nextState();
	}

	public void nextState() {		
		int i = TOTAL_STATES;
		while(--i > 0) {
			if(states[i] == STATE_ON) { break; }
		}
		
		if(currentState != null && i == currentState.id) { return; }
		
		switch(i) {
		case WaitState.ID:
			setNewState(new WaitState(robot));
			break;
		case WalkState.ID:
			setNewState(new WalkState(robot));
			break;
		case RunState.ID:
			setNewState(new RunState(robot, bScanner));
			break;
		case AvoidState.ID:
			setNewState(new AvoidState(robot));
			break;
		}
	}
	
	private void setNewState(RobotState newState) {
		if(currentState != null) {
			currentState.deactivate();
		}
		
		currentState = newState;
		currentState.start();
	}

	@Override
	public void ObjectDetected(int stateID) {		
		states[stateID] = STATE_ON;
		nextState();
	}

	@Override
	public void ObjectIsGone(int stateID) {
		states[stateID] = STATE_OFF;
		nextState();
	}

	public void deactivate() {
		if(fScanner != null) {
			fScanner.deactivate();
		}
		
		if(bScanner != null) {
			bScanner.deactivate();
		}
		
		if(currentState != null) {
			currentState.deactivate();
		}
		
		for(int i = 0; i < states.length; i++) {
			states[i] = STATE_OFF;
		}
	}
}

final class WaitState extends RobotState {
	public final static int ID = 0;
	private final static String LOG = "I'm just waiting for something to happen";
	
	public WaitState(MyRobotLego robot) {
		super(robot, ID);
		super.delay = 1000;
	}

	@Override
	protected void action() { }

	@Override
	protected void end() { }

	@Override
	protected void log() { System.out.println(LOG); }
}

final class WalkState extends RobotState {
	public final static int ID = 1;
	
	private int distance;
	private int radius;
	private int angle;
	private int order;
	
	private int originalSpeed = super.robot.getRelativeSpeed();
	
	public WalkState(MyRobotLego robot) {
		super(robot, ID);
		robot.SetRelativeSpeed(50);
	}

	@Override
	protected void action() {
		distance = 0;
		radius = 0;
		angle = 90;
		
		switch(order = nextMovement(order)) {
		case 1:
			super.robot.Reta(distance);
			break;
		case 2:
			super.robot.CurvarDireita(radius, angle);
			break;
		case 3:
			super.robot.CurvarEsquerda(radius, angle);
			break;
		}
		
		if(distance != 0) { 
			super.delay = MyRobotLego.calculateMovementDelay(MyRobotLego.DEFAULT_AVERAGE_SPEED, distance); 
		} else if(radius != 0) { 
			super.delay = MyRobotLego.calculateMovementDelay(MyRobotLego.DEFAULT_AVERAGE_SPEED, radius, angle);
		}
	}
	
	private int nextMovement(int previous) { 
		Random rand = new Random();
		int next;
		
		do {
			next = rand.nextInt(3) + 1;
		} while(previous == next);
		
		if(next > 1) {
			distance = 0;
			radius = rand.nextInt(50) + 5;
			angle = 90;
		} else {
			distance = rand.nextInt(50) + 5;
			radius = 0;
			angle = 0;
		}
		
		return next;
	}

	@Override
	protected void end() {	super.robot.SetRelativeSpeed(originalSpeed); }

	@Override
	protected void log() {
		System.out.printf("Walking distance: %d radius: %d angle: %d delay: %d\n", distance, radius, angle, super.delay);
	}
	
}

final class RunState extends RobotState {
	public final static int ID = 2;
	public final static int MIN_SPEED = 30;
	public final static int MAX_SPEED = 100;
	
	private final Scanner scanner;
	private final int originalSpeed = robot.getRelativeSpeed();
	private final int[] range;
	private int objectDistance = 10;
	private int speed;
	
	public RunState(MyRobotLego robot, BackScanner scanner) {
		super(robot, ID);
		this.scanner = scanner;
		this.range = scanner.getTrigger();
		this.objectDistance = scanner.findObjectDistance();
		super.delay = 100;
		super.robot.Parar(true);
	}

	@Override
	protected void action() {
		if(objectDistance < range[0] || objectDistance > range[1]) { 
			super.active = false;
			return; 
		}
		
		int relativeDistance = objectDistance - range[0];
		
		speed = MAX_SPEED - (relativeDistance * 100 / range[1]);
		
		if(speed < MIN_SPEED) speed += MIN_SPEED - speed;
		
		robot.SetRelativeSpeed(speed);
		robot.Reta(1);
		
		objectDistance = scanner.findObjectDistance();
	}

	@Override
	protected void end() {
		robot.Parar(false);
		robot.SetRelativeSpeed(originalSpeed);
	}

	@Override
	protected void log() {
		System.out.printf("Running objectDistance: %d, speed: %d\n", objectDistance, speed);
	}
}

final class AvoidState extends RobotState {
	public final static int ID = 3;
	
	private final int originalSpeed = super.robot.getRelativeSpeed();
	
	public AvoidState(MyRobotLego robot) {
		super(robot, ID);
		robot.Parar(true);
		robot.SetRelativeSpeed(90);
	}

	@Override
	protected void action() {
		robot.Reta(-20);
		robot.CurvarEsquerda(0, 90);
	}

	@Override
	protected void end() {
		robot.Parar(false);
		robot.SetRelativeSpeed(originalSpeed);	
	}

	@Override
	protected void log() {
		System.out.printf("Avoiding! delay: %d\n", super.delay);
		
	}
	
}
