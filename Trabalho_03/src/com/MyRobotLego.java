package com;
import javax.swing.JTextField;
import RobotLego.RobotLego;

public abstract class MyRobotLego {
	private JTextField l;
	private boolean liveMode;
	private RobotLego robot;
	
	public MyRobotLego(JTextField l, boolean liveMode) {
		this.l = l;
		this.liveMode = liveMode;
		
		
		if(liveMode) {
			robot = new RobotLego();
		}
	}
	
	public boolean OpenNXT(String name) {			
			l.setText("Connection is open");
		
			if (liveMode) {
				robot.OpenNXT(name);
			}
			
			return true;
	}
	
	public boolean CloseNXT() {
		l.setText("Connection is closed");
		
		if (liveMode) {
			robot.CloseNXT();
		}
		
		return true;
	}
	
	public void Reta(int units) {
			l.setText("Moving forward " + units + " units");
			
			if (liveMode) {
				robot.Reta(units);
			}
	}
	
	public void CurvarDireita(int radius, int angle) {
		l.setText("Turning left" + radius + " radius " + angle + " angle");
		
		if (liveMode) {
			robot.CurvarDireita(radius, angle);
		}		
	}
	
	public void CurvarEsquerda(int radius, int angle) {
		l.setText("Turning right" + radius + " radius " + angle + " angle");
		
		if (liveMode) {
			robot.CurvarEsquerda(radius, angle);
		}		
	}
	
	public void AjustarVMD(int offset) {
		l.setText("Right Offset " + offset + " units");
		
		if (liveMode) {
			robot.AjustarVMD(offset);
		}
	}
	
	public void AjustarVME(int offset) {
		l.setText("Left Offset " + offset + " units");
		
		if (liveMode) {
			robot.AjustarVME(offset);
		}
	}

	public void Parar(boolean trueStop) {
		l.setText("Robot stop");
		
		if (liveMode) {
			robot.Parar(trueStop);
		}
	}

}
