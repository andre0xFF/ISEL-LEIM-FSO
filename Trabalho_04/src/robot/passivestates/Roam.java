package robot.passivestates;

import java.util.Random;

import robot.MyRobotLego;
import robot.states.PassiveState;

public final class Roam extends PassiveState 
{
	public static final int ID = 3;
	private int order = 0;															// last generated order
	private int distance = 0;														// centimeters
	private int radius = 0;															// centimeters
	private int angle = 90;															// degrees
	
	public Roam(MyRobotLego robot) {
		super(robot, ID);
		
		synchronized(robot) { 
			robot.SetRelativeSpeed(45);
		}
		
		this.start();
	}
	
	@Override
	public void run() {
		while(active) {
			super.run();
			action();
			
			if(distance != 0) { delay = MyRobotLego.calculateMovementDelay(MyRobotLego.DEFAULT_AVERAGE_SPEED, distance); }
			else if(radius != 0) { delay = MyRobotLego.calculateMovementDelay(MyRobotLego.DEFAULT_AVERAGE_SPEED, radius, angle); }
			//System.out.printf("Roam, run(), distance, %d, radius: %d, speed: %d\n", distance, radius, robot.getRelativeSpeed());
			loopDelay();
		}
		
		synchronized(robot) {
			robot.Parar(false);
		}
	}

	@Override
	public void action() {
		distance = 0;
		radius = 0;
		angle = 90;
		
		order = randomNumberWithoutRepeating(1, 3, order);
		
		synchronized(robot) {
			switch(order) {
			case 1:
				distance = randomNumberWithoutRepeating(10, 50, 0);
				robot.Reta(distance);
				break;
			case 2:
				radius = randomNumberWithoutRepeating(10, 50, 0);
				robot.CurvarDireita(radius, angle);
				break;
			case 3:
				radius = randomNumberWithoutRepeating(10, 50, 0);
				robot.CurvarEsquerda(radius, angle);
				break;
			}
		}

	}
	
	public static int randomNumberWithoutRepeating(int min, int max, int previous) {
		Random r = new Random();
		int i = r.nextInt(max) + min;
		
		if(i == previous) { 
			i = randomNumberWithoutRepeating(min, max, previous); 		
		}
		
		return i;
	}

}
