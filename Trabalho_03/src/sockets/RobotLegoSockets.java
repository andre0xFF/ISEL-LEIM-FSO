package sockets;

import javax.swing.JTextField;
import java.io.*;
import java.net.*;
import robot.MyRobotLego;

public class RobotLegoSockets extends MyRobotLego {
	private Socket serverSocket;
    private PrintWriter out;
    private BufferedReader in;
    
	public RobotLegoSockets(JTextField l, boolean liveMode) {
		super(l, liveMode);
	}
	
	public boolean start(String serverHostname) {
		try {
			serverSocket = new Socket(serverHostname, 3366);
            out = new PrintWriter(serverSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            return true;
		} catch(IOException e) {
			return false;
		}
	}
}

/*
public class RobotLegoSockets extends MyRobotLego {
	private final String token = "2256Ox3dCPN2e5i3NG279Ps82f2tFoY1";
	private final String endpoint = "127.0.0.1:6634";
	private Server server;
	
	private Socket clientSocket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    
	public RobotLegoSockets(JTextField l, boolean liveMode) {
		super(l, liveMode);
		if(!start()) {
			server = new Server(this);
		}
	}
	
	public RobotLegoSockets(String endpoint) {
		this.endpoint = endpoint;
	}
	
	public String getEndpoint() {
		return this.endpoint;
	}
	
	public boolean start() {
		try {
				clientSocket = new Socket(server.getHost(), server.getPort());
	            out = new PrintWriter(clientSocket.getOutputStream(), true);
	            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				return true;
		} 
		catch(IOException e) { return false; }
	}
	
	public void stop() {
		try {
			out.close();
			in.close();
			clientSocket.close();
		} catch(IOException e) { }
	}
	
	private String receiveMessage() { 
		try {
			return in.readLine(); 
		} catch(IOException e) { return null; }
	}
	
	private void sendMessage(String msg) { 
		out.println(msg);
	}
	
	public boolean OpenNXT(String name) { sendMessage("OpenNXT()"); return true; }
}
*/