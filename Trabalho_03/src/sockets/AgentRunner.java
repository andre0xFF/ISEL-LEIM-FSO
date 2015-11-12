package sockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import robot.MyRobotLego;

public abstract class AgentRunner implements Runnable {	
	public static void main(String[] args) throws IOException {
		final int port = 7766;
		Agent agent;		
	
		System.out.println("$ Starting agent");
		ServerSocket serverSocket = new ServerSocket(port);
		agent = new Agent(serverSocket.accept());	
		
		//while(alive && (input = agent.receive()) != null) {
		while(true) {
			int[] procedures = Agent.procedureBuilder(agent.receive());
			
			switch(procedures[0]) {
			case 500:
				System.out.println("$ Killing agent!");
				return;
			case 300:
				agent.robot(procedures);
				break;
			}
		}
		
	}
	
	//public void run() { }
}

class Agent extends Client {
	private MyRobotLego robot;
	
	public Agent(Socket clientSocket) {
		super(clientSocket);
		robot = new MyRobotLego(new javax.swing.JTextField(), false);
	}
	
	public static int[] procedureBuilder(String order) {
		String[] orders = order.split(":");
		int[] intOrders = new int[orders.length];
		
		for(int i = 0; i < orders.length; i++) {
			intOrders[i] = Integer.parseInt(orders[i]);
		}
		
		return intOrders;
	}
	
	public void robot(int[] procedures) {
		if(procedures.length < 2) return;
		
		// 300:direction:distance:radius:angle:offsetL:offsetR:
		switch(procedures[1]) {
		case 8:
			robot.Reta(procedures[2]);
			System.out.println("Robot $ Moving forward");
			break;
		case 2:
			robot.Reta(-procedures[2]);
			System.out.println("Robot $ Moving backwards");
			break;
		case 4:
			robot.CurvarDireita(procedures[3], procedures[4]);
			//robot.AjustarVMD(procedures[6]);
			System.out.println("Robot $ Turning left");
			break;
		case 6:
			robot.CurvarDireita(procedures[3], procedures[4]);
			//robot.AjustarVMD(procedures[6]);
			System.out.println("Robot $ Turning right");
			break;
		case 5:
			robot.Parar(true);
			System.out.println("Robot $ Idling");
			break;
		case 7:
			robot.AjustarVME(procedures[5]);
			break;
		case 9:
			robot.AjustarVMD(procedures[6]);
			break;
		}
	}
}
