package sockets;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import robot.MyRobotLego;
import sun.misc.Queue;

public class Server {
	static final String IP = "127.0.0.1";
	static final int PORT = 7755;

	public static void main(String[] args) throws IOException, InterruptedException {			
		boolean run = true;
		int numberOfClients = 0;
		
		System.out.println("Server $ starting");
		ServerSocket serverSocket = new ServerSocket(Server.PORT);
		
		System.out.println("Agent $ starting");
		Agent manager = new Agent();
		//manager.connect(manager.getIp(), manager.getPort());
		
		Queue<Client> clients = new Queue<>();
		System.out.println("Server $ Ready!");
		
		while(run) {
			Client c = new Client(serverSocket.accept());
			
			{ // Authentication process
				int r = Client.authenticate(c.receive());
				
				if(r != Agent.ID && r != Client.ID) {
					c.send(Integer.toString(Codes.UNAUTHORIZED));
					continue;
				}
				
				if(r == Agent.ID) {
					c.send(Integer.toString(Codes.AUTHENTICATED));
					manager.toggle();
					c.disconnect();
					System.out.println("Agent $ I'm free");
					continue;
				}
				
				if(r == Client.ID) {
					c.send(Integer.toString(Codes.AUTHENTICATED));
					numberOfClients++;
				}
		
			}
			
			clients.enqueue(c);
			System.out.println("Client $ connected. Queue: " + numberOfClients);
			
			if(manager.agentIsBusy()) continue;
			
			clients.dequeue().accept();	
			manager.toggle();
			numberOfClients--;
		}
		
		serverSocket.close();
	}
}

class Client {
	public final static String TOKEN = "omELIltY5u9fbkXvHxRc7CDcH4yIr7TB";
	public final static int ID = 2;
	private Socket socket;
	
	public Client(Socket clientSocket) {
		this.socket = clientSocket;
	}
	
	public String receive() throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		return in.readLine();
	}
	
	public void send(String msg) throws IOException {
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true); 
		out.println(msg);
	}
	
	public static int authenticate(String token) throws IOException {		
		if(Client.TOKEN.equals(token)) return Client.ID;
		
		if(Agent.TOKEN.equals(token)) return Agent.ID;
		
		return 0;
	}
	
	public void accept() throws IOException {
		//send("200:" + agentIp.toString() + ":" + Integer.toString(agentPort) + ":");
		send(Integer.toString(Codes.ACCEPTED));
		socket.close();
	}
	
	public void disconnect() throws IOException {
		socket.close();
	}
}

class Agent {
	private final String PATH = "//home//andrew//Workspace//Agent.jar";
	public final static String IP = "127.0.0.1";
	public final static int PORT = 7766;
	public final static String TOKEN = "H98QQ185t5q4YAlq8MYxB571PKg6dJSi";
	public final static int ID = 1;
	private Socket socket;
	private boolean busy = true;
	private Process p;
	private MyRobotLego robot;
	
	public void toggle() { this.busy = !this.busy; }
	
	public boolean agentIsBusy() { return this.busy; }
	
	private void start() throws IOException {
		p = runJavaProcess(this.PATH, new String());
	}
	
	public static Process runJavaProcess(String path, String params) throws IOException {
		return Runtime.getRuntime().exec("java -jar " + path + " " + params);
	}

	public boolean toggleStatusOnServer() throws UnknownHostException, IOException {
		socket = new Socket(Server.IP, Server.PORT);
		
		send(Agent.TOKEN);
		
		if(Integer.parseInt(receive()) != Codes.AUTHENTICATED) return false;
		
		socket.close();
		
		return true;
	}
	
	public void connectWithRobot() { 
		robot = new MyRobotLego(new javax.swing.JTextField(), false);
		robot.OpenNXT("Amelia");
	}
	
	public void disconnectFromRobot() {
		robot.CloseNXT();
	}
	
	public void connectWithClient(Socket socket) {
		this.socket = socket;
	}
	
	public void disconnectFromClient() throws IOException {
		socket.close();
	}
	
	public void send(String msg) throws IOException {
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true); 
		out.println(msg);
	}
	
	public String receive() throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		return in.readLine();
	}

	public void robot(int[] procedures) {
		if(procedures.length < 2) return;
		
		// 300:direction:distance:radius:angle:offsetL:offsetR:
		if(procedures[2] < 0) procedures[1] = Codes.ROBOT_BACKWARDS;
		
		switch(procedures[1]) {
		case Codes.ROBOT_FORWARD:
			robot.Reta(procedures[2]);
			System.out.println("Robot $ Moving forward");
			break;
		case Codes.ROBOT_BACKWARDS:
			robot.Reta(-procedures[2]);
			System.out.println("Robot $ Moving backwards");
			break;
		case Codes.ROBOT_LEFT:
			robot.CurvarDireita(procedures[3], procedures[4]);
			//robot.AjustarVMD(procedures[6]);
			System.out.println("Robot $ Turning left");
			break;
		case Codes.ROBOT_RIGHT:
			robot.CurvarDireita(procedures[3], procedures[4]);
			//robot.AjustarVMD(procedures[6]);
			System.out.println("Robot $ Turning right");
			break;
		case Codes.ROBOT_STOP:
			robot.Parar(true);
			System.out.println("Robot $ Idling");
			break;
		case Codes.ROBOT_OFFSET_L:
			robot.AjustarVME(procedures[5]);
			break;
		case Codes.ROBOT_OFFSET_R:
			robot.AjustarVMD(procedures[6]);
			break;
		}
	}
	
	//public boolean isRunning() {
		// TODO: connect to agent and see if he is alive
		/*
		try {
			p.isAlive();
			return true;
		} catch (Exception e) {
			return false;
		}
		*/
		/*
	    try {
	    	p.exitValue();
	        return false;
	    } catch (Exception e) {
	        return true;
	    }
	    */
	//}
	
	public void killAgent() {
		//Runtime.getRuntime().exec("kill " PID);
	}
}

class Codes {
	public final static int ROBOT_ORDER = 300;
	public final static int ROBOT_FORWARD = 8;
	public final static int ROBOT_BACKWARDS = 2;
	public final static int ROBOT_LEFT = 4;
	public final static int ROBOT_RIGHT = 6;
	public final static int ROBOT_STOP = 5;
	public final static int ROBOT_OFFSET_L = 7;
	public final static int ROBOT_OFFSET_R = 9;
	public final static int AUTHENTICATED = 200;
	public final static int ACCEPTED = 202;
	public final static int UNAUTHORIZED = 401;
	public final static int DISCONNECT = 500;
	public final static String REGEX = ":";
	
	// 300:direction:distance:radius:angle:offsetL:offsetR:
	public static String intToString(int direction, int distance, int radius, int angle, int offsetL, int offsetR) {
		return Integer.toString(Codes.ROBOT_ORDER)
				+ Integer.toString(distance) + Codes.REGEX 
				+ Integer.toString(radius) + Codes.REGEX
				+ Integer.toString(angle) + Codes.REGEX
				+ Integer.toString(offsetL) + Codes.REGEX
				+ Integer.toString(offsetR) + Codes.REGEX;
	}
	
	public static int[] stringToInt(String orders) {
		String[] r1 = orders.split(Codes.REGEX);
		int[] r2 = new int[r1.length];
		
		for(int i = 0; i < r1.length; i++) {
			r2[i] = Integer.parseInt(r1[i]);
		}
		
		return r2;
	}
}