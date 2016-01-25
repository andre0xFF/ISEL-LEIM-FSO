package tester;

import java.util.concurrent.TimeUnit;

import robot.MyRobotLego;

public class test 
{
	public static void main(String[] args) throws InterruptedException {
		MyRobotLegoTester robot = new MyRobotLegoTester();
		robot = connectWithRobot(robot);
		
		testStates(robot);
		
		robot = disconnectFromRobot(robot);
	}
	
	public static void testStates(MyRobotLegoTester robot) {
		robot.TestPassiveState();
		
		try { Thread.sleep(2000); } 
		catch (InterruptedException e) { }
		
		robot.TestActiveState(2);
		
		try { Thread.sleep(2000); } 
		catch (InterruptedException e) { }
		
		robot.TestActiveState(1);
		
		try { Thread.sleep(2000); } 
		catch (InterruptedException e) { }
		
		robot.TestActiveState(3);
	}
	
	public static MyRobotLegoTester connectWithRobot(MyRobotLegoTester robot) throws InterruptedException {
		if(MyRobotLego.LIVE_MODE) { 
			robot.OpenNXT("FSO1");
			Thread.sleep(10000); 
		}
		
		System.out.println("GO!");
		return robot;
	}
	
	public static MyRobotLegoTester disconnectFromRobot(MyRobotLegoTester robot) throws InterruptedException {
		Thread.sleep(1000);
		
		if(MyRobotLego.LIVE_MODE) { 
			robot.CloseNXT();
		}
		
		return robot;
	}
	
	public static void backScannerTest(MyRobotLegoTester robot) throws InterruptedException {
		robot.SetSensorLowspeed(0);
		for(int i = 0; i < 60; i++) {
			System.out.println(i + " " + robot.SensorUS(0));
			Thread.sleep(50);
		}
	}
	
	public static void escapeTest(MyRobotLegoTester robot) throws InterruptedException {		
		robot.escape(1, 100);		
		Thread.sleep(10 * 60 * 1000);
	}
	
	public static void roamTest(MyRobotLegoTester robot) throws InterruptedException {	
		robot.roam();
		Thread.sleep(10000);
		robot.roam();
	}
	
	public static void calculateSpeed(MyRobotLegoTester robot) throws InterruptedException {
		robot.SetSensorLowspeed(MyRobotLego.BACK_SCANNER_PORT);
		int initialDistance = robot.SensorUS(MyRobotLego.BACK_SCANNER_PORT);
		int finalDistance = initialDistance;
		long initialTime = System.nanoTime();
			
		robot.SetSpeed(50);
		robot.AjustarVME(4);
		robot.AjustarVMD(0);
		
		robot.Reta(100);
		robot.Parar(false);
		
		while((finalDistance - initialDistance) <= 100) {
			Thread.sleep(50);
			finalDistance = robot.SensorUS(MyRobotLego.BACK_SCANNER_PORT);
			System.out.print((finalDistance - initialDistance) + " ");
		}
		
		long finalTime = System.nanoTime();
		
		System.out.println("");
		System.out.println("Travelled distance: " + (finalDistance - initialDistance));
		System.out.println("Elapsed time: " + TimeUnit.NANOSECONDS.toSeconds((finalTime - initialTime)));
	}

}