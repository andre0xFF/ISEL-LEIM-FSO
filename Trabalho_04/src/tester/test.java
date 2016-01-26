package tester;

import java.util.concurrent.TimeUnit;

import robot.MyRobotLego;

public class test 
{
	public static void main(String[] args) throws InterruptedException {
		MyRobotLegoTester robot = new MyRobotLegoTester();
		robot = connectWithRobot(robot);
		
		robot.escape(5, 100);
		robot.roam();
		robot.avoid();

		
		Thread.sleep(30 * 60 * 1000);
		
		robot = disconnectFromRobot(robot);
	}
	
	public static void TestStates(MyRobotLegoTester robot) {
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
		}
		
		System.out.println("GO!");
		return robot;
	}
	
	public static MyRobotLegoTester disconnectFromRobot(MyRobotLegoTester robot) throws InterruptedException {
		if(MyRobotLego.LIVE_MODE) {
			Thread.sleep(1000);
			robot.CloseNXT();
		}
		
		return robot;
	}
	
	public static void TestBackScanner(MyRobotLegoTester robot) throws InterruptedException {
		robot.SetSensorLowspeed(0);
		for(int i = 0; i < 120; i++) {
			System.out.println(i + " " + robot.SensorUS(0));
			Thread.sleep(50);
		}
	}
	
	public static void TestEscape(MyRobotLegoTester robot) throws InterruptedException {		
		robot.escape(1, 100);
		Thread.sleep(5 * 60 * 1000);
	}
	
	public static void TestRoam(MyRobotLegoTester robot) throws InterruptedException {	
		robot.roam();
		Thread.sleep(5 * 60 * 1000);
		robot.roam();
	}
	
	/**
	 * To calculate the real robot speed make sure the robot goes in a straight line
	 * adjust the offsets if needed
	 * @param robot
	 * @throws InterruptedException
	 */
	public static void CalculateSpeed(MyRobotLegoTester robot) throws InterruptedException {
		Thread.sleep(1000);
		robot.SetSensorLowspeed(MyRobotLego.BACK_SCANNER_PORT);
		int totalDistance = 100;
		int relativeSpeed = 50;
		
		int initialScan = 0;
		while(initialScan > 250) {
			initialScan = robot.SensorUS(MyRobotLego.BACK_SCANNER_PORT);
		}
		
		int finalScan = initialScan;
		long initialTime = System.nanoTime();
		
		robot.SetRelativeSpeed(relativeSpeed);
		robot.AjustarVME(1);
		robot.AjustarVMD(0);
		
		robot.Reta(totalDistance);
		robot.Parar(false);
		
		int relativeDistance = 0;
		while((finalScan - initialScan) <= totalDistance || relativeDistance != (finalScan - initialScan)) {
			relativeDistance = finalScan - initialScan;
			Thread.sleep(20);
			finalScan = robot.SensorUS(MyRobotLego.BACK_SCANNER_PORT);
			System.out.print((finalScan - initialScan) + " ");
		}
		
		long finalTime = System.nanoTime();
		
		long elapsedTime = TimeUnit.NANOSECONDS.toSeconds(finalTime - initialTime);
		int calculatedSpeed = totalDistance/(int)TimeUnit.NANOSECONDS.toSeconds((finalTime - initialTime));
		
		System.out.println("");
		System.out.println("Total distance: " + totalDistance + " cm");
		System.out.println("Elapsed time: " + elapsedTime + " s");
		System.out.println("Speed: " + calculatedSpeed + " cm/s");
		System.out.println("Relative speed: " + relativeSpeed + " %");
	}

}