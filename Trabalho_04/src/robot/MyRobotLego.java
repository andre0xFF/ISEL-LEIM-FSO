package robot;
import javax.swing.JTextField;
import RobotLego.RobotLego;

public class MyRobotLego {
	private JTextField l;
	private boolean liveMode;
	private RobotLego robot;
	private MemoryContainer memory;

	public MyRobotLego(JTextField l, boolean liveMode) {
		this.l = l;
		this.liveMode = liveMode;
		this.memory = new MemoryContainer();

		if(liveMode) robot = new RobotLego();
	}

	public boolean OpenNXT(String name, String hostname) {
		return OpenNXT(name);
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

	public void shutdown() { CloseNXT(); }

	public void escape(Boolean start) {
		//memory.setMODE_ESCAPE = start;
		// TODO: Start/stop run thread
	}

	public void avoid(Boolean start) {
		//memory.setMODE_AVOID = start;
		// TODO: Start/stop avoid obstacles thread
	}

	public void roam(Boolean start) {
		//memory.setMODE_ROAM = start;
		// TODO: Start/stop roaming thread
		// TODO: Disable gui commands
	}
}
