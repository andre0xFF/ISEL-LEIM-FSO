package tester;

import javax.swing.JTextField;
import robot.BackScanner;
import robot.FrontScanner;
import robot.MyRobotLego;
import robot.automate.Avoid;
import robot.automate.Roam;

public class test {

	public static void main(String[] args) throws InterruptedException {
		MyRobotLego robot = new MyRobotLego(new JTextField(), false);
		Roam r = new Roam(robot);
		Thread.sleep(3000);
		new Avoid(robot, new FrontScanner(robot, 0));
		Thread.sleep(2000);
		System.out.println("avoid");
		r.pause();
		Thread.sleep(6000);
		r.unpause();
	}
	
	public static void testSensors() throws InterruptedException {
		System.out.println("1");
		BackScanner t = new BackScanner(new MyRobotLego(new JTextField(), false), 0);
		t.pause();
		new FrontScanner(new MyRobotLego(new JTextField(), false), 0);
		Thread.sleep(3500);
		
		System.out.println("2");
		t.setDelay(250);
		t.unpause();
		Thread.sleep(3500);
		
		System.out.println("3");
		t.deactivate();
		Thread.sleep(3500);
	}

}