package robot.states;

import robot.MyRobotLego;

/**
 * An active state:
 * * is never paused
 * * runs while a condition is true
 * * have priorities (weight)
 * * needs a scanner to support the desired action
 * @author affonseca
 *
 */
public abstract class ActiveState extends State 
{
	protected final Scanner scanner;

	protected int weight = 0;
	protected boolean execute = false;
	
	public int getWeight() { return this.weight; }

	public ActiveState(MyRobotLego robot, Scanner scanner, int id) {
		super(robot, id);
		this.scanner = scanner;
	}
	
	public void run() {
		synchronized(robot) {
			while(!execute) {
				try { robot.wait(); }
				catch (InterruptedException e) { }
			}
		}
			
		super.run();
	}
	
	@Override
	public void deactivate() {
		if(scanner != null) {
			scanner.deactivate();
		}
		
		super.deactivate();
	}

	public void execute() {
		this.execute = true;
	}

}
