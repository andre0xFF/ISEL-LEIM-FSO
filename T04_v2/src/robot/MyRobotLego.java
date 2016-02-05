package robot;

import java.util.Random;

import javax.swing.JTextField;

import RobotLego.RobotLego;

public class MyRobotLego {
	public final static boolean LIVE_MODE = false;
	public final static int FRONT_SCANNER_PORT = RobotLego.S_2;
	public final static int BACK_SCANNER_PORT = RobotLego.S_1;
	public final static int DEFAULT_AVERAGE_SPEED = 14;										// cm/s
	
	private int relativeSpeed;
	
	private RobotLego robot;
	
	//protected final StateMachine stateMachine = new StateMachine(this);

	public MyRobotLego() {
		if(LIVE_MODE) { robot = new RobotLego(); }
	}
	
	public MyRobotLego(JTextField txt) {
		if(LIVE_MODE) { robot = new RobotLego(); }
	}

	public boolean OpenNXT(String name) {
		if(LIVE_MODE) {	
			if(robot.OpenNXT(name)) {
				SetRelativeSpeed(50);
				return true;
			} else { return false; }
		} else { return true; }
	}

	public boolean CloseNXT() {
		//System.out.println("Connection is closed");

		if (LIVE_MODE) {
			robot.Parar(true);
			robot.CloseNXT();
		}
		
		return true;
	}

	public void Reta(int units) {
		//System.out.println("Moving forward " + units + " units");

		if (LIVE_MODE) robot.Reta(units);
	}

	public void CurvarDireita(int radius, int angle) {
		//System.out.println("Turning left " + radius + " radius " + angle + " angle");

		if (LIVE_MODE) robot.CurvarDireita(radius, angle);
	}

	public void CurvarEsquerda(int radius, int angle) {
		//System.out.println("Turning right " + radius + " radius " + angle + " angle");

		if (LIVE_MODE) robot.CurvarEsquerda(radius, angle);
	}

	public void AjustarVMD(int offset) {
		if (LIVE_MODE) robot.AjustarVMD(offset);
	}

	public void AjustarVME(int offset) {
		if (LIVE_MODE) robot.AjustarVME(offset);
	}

	public void Parar(boolean trueStop) {
		//System.out.println("Robot stop");

		if (LIVE_MODE) robot.Parar(trueStop);
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
		return LIVE_MODE ? robot.SensorUS(port) : r.nextInt(50);
	}
	
	public void SetSensorTouch(int port) {
		if (LIVE_MODE) robot.SetSensorTouch(port);
	}
	
	public int Sensor(int port) {
		Random r = new Random();		
		return LIVE_MODE ? robot.Sensor(port) : 1;

	}
	
	public void goWalk() {

	}
	
	public void goRun(int minDistance, int maxDistance) {	

	}
	
	public void goAvoid() {

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
