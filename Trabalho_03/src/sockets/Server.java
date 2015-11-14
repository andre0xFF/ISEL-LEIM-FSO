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
		
		System.out.println("Server $ starting");
		ServerSocket serverSocket = new ServerSocket(Server.PORT);
		
		System.out.println("Agent $ starting");
		Agent manager = new Agent();
		//manager.connect(manager.getIp(), manager.getPort());
		
		Queue<Client> clients = new Queue<>();
		
		while(run) {
			System.out.println("Server $ waiting for new connections");
			Client c = new Client(serverSocket.accept());
			
			{
				int r = Client.authenticate(c.receive());
				
				if(r == Agent.ID) { 
					manager.toggle();
					c.disconnect();
					System.out.println("Agent $ I'm free");
					continue;
				}
				
				if(r == Client.ID) { 
					manager.toggle();
					System.out.println("Agent $ busy");
				}
				
				if(r == 0) continue;
			}
			
			clients.enqueue(c);
			System.out.println("Client $ connected");
			
			if(manager.agentIsBusy()) continue;
			
			clients.dequeue().accept(manager.getIp(), manager.getPort());		
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
		if(Client.TOKEN.equals(token))
			return ID;
		
		if(Agent.TOKEN.equals(token))
			return Agent.ID;
		
		return 0;
	}
	
	public void accept(String agentIp, int agentPort) throws IOException {
		send("200:" + agentIp.toString() + ":" + Integer.toString(agentPort) + ":");
		socket.close();
	}
	
	public void disconnect() throws IOException {
		socket.close();
	}
}

class Agent {
	private final String PATH = "//home//andrew//Workspace//Agent.jar";
	private final String IP = "127.0.0.1";
	private final int PORT = 7766;
	public final static String TOKEN = "H98QQ185t5q4YAlq8MYxB571PKg6dJSi";
	public final static int ID = 2;
	private Socket socket;
	private boolean busy = false;
	private Process p;
	private MyRobotLego robot;
	
	public int getPort() { return this.PORT; }
	
	public String getIp() { return this.IP; }
	
	public void toggle() { this.busy = !this.busy; }
	
	public boolean agentIsBusy() { return this.busy; }
	
	private void start() throws IOException {
		p = runJavaProcess(this.PATH, new String());
	}
	
	public static Process runJavaProcess(String path, String params) throws IOException {
		return Runtime.getRuntime().exec("java -jar " + path + " " + params);
	}
	
	public void connect(String ip, int port) throws IOException {
		try { socket = new Socket(ip, port); }
		catch(IOException e) {
			start();
			connect(ip, port);
		}
		finally { socket.close(); }
	}
	
	public void connectWithServer() throws UnknownHostException, IOException {
		socket = new Socket(Server.IP, Server.PORT);
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true); 
		out.println(Agent.TOKEN);
		socket.close();
	}
	
	public void connectWithRobot() { 
		robot = new MyRobotLego(new javax.swing.JTextField(), false);
		robot.OpenNXT("Amelia");
	}
	
	public void disconnectWithRobot() {
		robot.CloseNXT();
	}
	
	public void connectWithClient(Socket socket) {
		this.socket = socket;
	}
	
	public void disconnectWithClient() throws IOException {
		socket.close();
	}
	
	public String receive() throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		return in.readLine();
	}
	
	public int[] receiveOrder() throws IOException {
		String[] r1 = receive().split(":");
		int[] r2 = new int[r1.length];
		
		for(int i = 0; i < r1.length; i++) {
			r2[i] = Integer.parseInt(r1[i]);
		}
		
		return r2;
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
