package robot;
import javax.swing.JTextField;
import RobotLego.RobotLego;
import robot.behavior.*;

public class MyRobotLego {
	private final JTextField l;
	private final boolean liveMode;
	private RobotLego robot;
	
	private Roam roam;
	private Avoid avoid;
	private Escape escape;
	
	private int nBehaviours = 0;
	
	public final static int MIN_SPEED = 30;
	public final static int MAX_SPEED = 100;
	private final static int DEFAULT_DELAY = 1500;
	private final static int DEFAULT_AVERAGE_SPEED = 3;

	public MyRobotLego(JTextField l, boolean liveMode) {
		this.l = l;
		this.liveMode = liveMode;

		if(liveMode) robot = new RobotLego();
	}
	
	public int getBehaviours() { return this.nBehaviours; }
	
	public void addBehaviour() { ++this.nBehaviours; }
	
	public void rmBehaviour() { --this.nBehaviours; }
	
	public static void sleepForAWhile(int ms) {	  
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
		
		this.notifyAll();
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

	public void shutdown() { CloseNXT(); }

	public void roam(boolean alive) {
		if(alive) roam = new Roam(this);
		else roam.alive = false;
	}
	
	public void avoid(boolean alive) {
		if(alive) avoid = new Avoid(this);
		else avoid.alive = false;
	}
	
	public void escape(boolean alive, int min, int max) {
		if(alive) escape = new Escape(this, min, max);
		else escape.alive = false;
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
}
