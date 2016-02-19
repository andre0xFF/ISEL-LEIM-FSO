package Robot;

import java.util.Random;

import javax.swing.JTextField;

import RobotLego.RobotLego;
import Robot.States.StateMachine;

public class MyRobotLego {
	public final static boolean LIVE_MODE = false;
	public final static int FRONT_SCANNER_PORT = RobotLego.S_2;
	public final static int BACK_SCANNER_PORT = RobotLego.S_1;
	public final static int DEFAULT_AVERAGE_SPEED = 14;								// cm/s
	
	private String name = "";
	private int relativeSpeed;
	
	private RobotLego robot;
	private StateMachine machine;
	private boolean walk = false;
	private int[] offsets = new int[] {0, 0};
	
	//protected final StateMachine stateMachine = new StateMachine(this);

	public MyRobotLego() {
		if(LIVE_MODE) { robot = new RobotLego(); }
	}
	
	public MyRobotLego(JTextField txt) {
		if(LIVE_MODE) { robot = new RobotLego(); }
	}
	
	public String getName() {
		return this.name;
	}
	
	public int[] getOffsets() {
		return this.offsets ;
	}

	public boolean OpenNXT(String name) {
		boolean r = true;
		this.name = name;
		
		if(LIVE_MODE) { r = robot.OpenNXT(name); }
		
		machine = new StateMachine(this);
		return r;
	}

	public boolean CloseNXT() {
		machine.deactivate();
		machine = null;
		System.gc();
		
		if (LIVE_MODE) {
			robot.Parar(true);
			robot.CloseNXT();
		}
		
		return true;
	}

	public void Reta(int units, boolean stop) {
		//System.out.println("Moving forward " + units + " units");

		if (LIVE_MODE) {
			synchronized(robot) {
				robot.Reta(units);

				if(stop) robot.Parar(false);
			}
		}
	}

	public void CurvarDireita(int radius, int angle, boolean stop) {
		//System.out.println("Turning left " + radius + " radius " + angle + " angle");

		if (LIVE_MODE) {
			synchronized(robot) {
				robot.CurvarDireita(radius, angle);
				if(stop) robot.Parar(false);
			}
		}
	}

	public void CurvarEsquerda(int radius, int angle, boolean stop) {
		//System.out.println("Turning right " + radius + " radius " + angle + " angle");

		if (LIVE_MODE) {
			synchronized(robot) {
				robot.CurvarEsquerda(radius, angle);
				if(stop) robot.Parar(false);
			}
		}
	}

	public void AjustarVMD(int offset) {
		this.offsets[1] = offset;
		
		if (LIVE_MODE) {
			synchronized(robot) {
				robot.AjustarVMD(offset);
			}
		}
	}

	public void AjustarVME(int offset) {
		this.offsets[0] = offset;
		
		if (LIVE_MODE) {
			synchronized(robot) {
				robot.AjustarVME(offset);
			}
		}
	}

	public void Parar(boolean trueStop) {
		//System.out.println("Robot stop");

		if (LIVE_MODE) {
			synchronized(robot) {
				robot.Parar(trueStop);
			}
		}
	}
	
	public int getRelativeSpeed() { 
		return this.relativeSpeed; 
	}
	
	public void SetRelativeSpeed(int percentage) {
		this.relativeSpeed = percentage;
		
		if (LIVE_MODE) robot.SetSpeed(relativeSpeed);
	}
	
	public void SetSensorLowspeed(int port) {
		if (LIVE_MODE) robot.SetSensorLowspeed(port);
	}
	
	public int SensorUS(int port) {
		Random r = new Random();
		return LIVE_MODE ? robot.SensorUS(port) : r.nextInt(151);
	}
	
	public void SetSensorTouch(int port) {
		if (LIVE_MODE) robot.SetSensorTouch(port);
	}
	
	public int Sensor(int port) {
		Random r = new Random();		
		return LIVE_MODE ? robot.Sensor(port) : r.nextInt(2);

	}
	
	public void goWalk() {
		if(!walk) { 
			machine.walkState(); 
			walk = true;
		}
		else { 
			machine.waitState();
			if (LIVE_MODE) robot.Parar(false);
			walk = false;
		}
	}
	
	public void goRun(int minDistance, int maxDistance) {	
		machine.runState(minDistance, maxDistance);
	}
	
	public void goAvoid() {
		machine.avoidState();
	}

	public static void sleep(int ms) { 
		try { Thread.sleep(ms); }
		catch (InterruptedException e) { }
	}

	public static int calculateMovementDelay(int speed, int distance) {
		return (int)(distance / speed) * 1000;
	}

	public static int calculateMovementDelay(int speed, int radius, int angle) {
		double radians = (angle * 2 * Math.PI) / 360;
		return (int)((radius * radians) / speed) * 1000;
	}
}
