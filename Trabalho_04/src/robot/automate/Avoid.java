package robot.automate;

import java.util.concurrent.Semaphore;

import robot.MyRobotLego;

public class Avoid extends Behaviour {

	public Avoid(MyRobotLego robot, Semaphore permission) {
		super(robot, permission);
		this.start();
	}

	
	@Override
	public void run() {
		action();
		this.interrupt();	
	}

	
	@Override
	public synchronized void action() {
		try { permission.acquire(); }
		catch (InterruptedException e1) { e1.printStackTrace(); }
		
		System.out.println("Avoid: -20, left 90");
		ROBOT.Parar(true);
		ROBOT.Reta(-20);
		ROBOT.Parar(false);
		ROBOT.CurvarEsquerda(0, 90);
		ROBOT.Parar(false);

		permission.release();
	}

	
	@Override
	public synchronized void pause() {	}

}
