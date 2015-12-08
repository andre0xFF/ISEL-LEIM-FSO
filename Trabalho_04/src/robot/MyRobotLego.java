package robot;
import javax.swing.JTextField;
import RobotLego.RobotLego;

public class MyRobotLego extends RobotLego {
	private JTextField l;
	private boolean liveMode;
//	private RobotLego robot;

	public MyRobotLego(JTextField l, boolean liveMode) {
		this.l = l;
		this.liveMode = liveMode;

//		if(liveMode) robot = new RobotLego();
	}

	public boolean OpenNXT(String name, String hostname) {
		return OpenNXT(name);
	}

	public boolean OpenNXT(String name) {
		l.setText("Connection is open");

		if (liveMode) this.OpenNXT(name);

		return true;
	}

	@Override
	public void CloseNXT() {
		l.setText("Connection is closed");

		if (liveMode) this.CloseNXT();
	}

	public void Reta(int units) {
			l.setText("Moving forward " + units + " units");

			if (liveMode) this.Reta(units);
	}

	public void CurvarDireita(int radius, int angle) {
		l.setText("Turning left " + radius + " radius " + angle + " angle");

		if (liveMode) this.CurvarDireita(radius, angle);
	}

	public void CurvarEsquerda(int radius, int angle) {
		l.setText("Turning right " + radius + " radius " + angle + " angle");

		if (liveMode) this.CurvarEsquerda(radius, angle);
	}

	public void AjustarVMD(int offset) {
		if (liveMode) this.AjustarVMD(offset);
	}

	public void AjustarVME(int offset) {
		if (liveMode) this.AjustarVME(offset);
	}

	public void Parar(boolean trueStop) {
		l.setText("Robot stop");

		if (liveMode) this.Parar(trueStop);
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
