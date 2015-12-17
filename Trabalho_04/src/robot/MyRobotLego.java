package robot;
import java.util.concurrent.Semaphore;

import javax.swing.JTextField;
import RobotLego.RobotLego;
import robot.automate.*;

public class MyRobotLego implements ObjectListener {
	private final JTextField l;
	
	private final static int DEFAULT_DELAY = 1500;
	private final static int DEFAULT_AVERAGE_SPEED = 3;
	private final static int FRONT_SCANNER_PORT = RobotLego.S_2;
	private final static int BACK_SCANNER_PORT = RobotLego.S_1;
	private final boolean liveMode;
	
	private RobotLego robot;
	private FrontScanner fScanner;
	private BackScanner bScanner;
	private Roam roam;
	
	private Semaphore permission = new Semaphore(1);


	public MyRobotLego(JTextField l, boolean liveMode) {
		this.l = l;
		this.liveMode = liveMode;

		if(liveMode) robot = new RobotLego();
	}

	
	public static void sleepForAWhile(int ms) {	  
		try { Thread.sleep(ms); } 
		catch (InterruptedException e) { e.printStackTrace(); }
	}
	
	public static int calculateDelay(int distance, int radius, int angle) {
		if(radius == 0 && angle == 0) return (distance / DEFAULT_AVERAGE_SPEED) * 1000;
//		if(distance == 0 && radius > 0 && angle > 0) return (radius * angle) + 100;
		
		if(distance > 0) return (1100 * distance) / 20;
		
		return DEFAULT_DELAY;
	}

	public boolean OpenNXT(String name) {
		l.setText("Connection is open");

		if (liveMode) robot.OpenNXT(name);

		return true;
	}

	public boolean CloseNXT() {
		l.setText("Connection is closed");

		if (liveMode) robot.CloseNXT();
		
		return true;
	}

	public void Reta(int units) {
		l.setText("Moving forward " + units + " units");

		if (liveMode) robot.Reta(units);
	}

	public void CurvarDireita(int radius, int angle) {
		l.setText("Turning left " + radius + " radius " + angle + " angle");

		if (liveMode) robot.CurvarDireita(radius, angle);
	}

	public void CurvarEsquerda(int radius, int angle) {
		l.setText("Turning right " + radius + " radius " + angle + " angle");

		if (liveMode) robot.CurvarEsquerda(radius, angle);
	}

	public void AjustarVMD(int offset) {
		if (liveMode) robot.AjustarVMD(offset);
	}

	public void AjustarVME(int offset) {
		if (liveMode) robot.AjustarVME(offset);
	}

	public void Parar(boolean trueStop) {
		l.setText("Robot stop");

		if (liveMode) robot.Parar(trueStop);
	}
	
	public void SetSpeed(int speed) {
		if (liveMode) robot.SetSpeed(speed);
	}
	
	public void SetSensorLowspeed(int port) {
		if (liveMode) robot.SetSensorLowspeed(port);
	}
	
	public int SensorUS(int port) {
		if (liveMode) return robot.SensorUS(port);
		
		return 0;
	}
	
	public void SetSensorTouch(int port) {
		if (liveMode) robot.SetSensorTouch(port);
	}
	
	public int Sensor(int port) {
		if (liveMode) return robot.Sensor(port);
		
		return 0;
	}

	@Override
	public void frontObjectDetected(int distance) {
		new Avoid(this, permission);
	}

	@Override
	public void backObjectDetected(int distance) {
		new Escape(this, permission, distance, bScanner.getMinDistance(), bScanner.getMaxDistance());
		System.out.print("Robot $ Object distance: " + distance);
	}
	
	public void toggleAvoid() {
		if(fScanner == null || !fScanner.alive) fScanner = new FrontScanner(this, FRONT_SCANNER_PORT);
		else fScanner.alive = false;
	}
	
	public void toggleEscape(int minDistance, int maxDistance) {
		if(bScanner == null || !bScanner.alive) bScanner = new BackScanner(this, BACK_SCANNER_PORT, minDistance, maxDistance);
		else bScanner.alive = false;
	}
	
	public void toggleEscape() {
		if(bScanner == null || !bScanner.alive) bScanner = new BackScanner(this, BACK_SCANNER_PORT);
		else bScanner.alive = false;
	}
	
	public void toggleRoam() {
		if(roam == null || !roam.alive) roam = new Roam(this, permission);
		else roam.alive = false;
	}
}
