package sockets;

import javax.swing.JTextField;
import java.io.*;
import java.net.*;
import robot.MyRobotLego;

public class RobotLegoSockets extends MyRobotLego {
	Socket socket;
	private final String serverHost = "127.0.0.1";
	final String agentPath = "//home//andrew//Workspace//Server.jar";
	private final int serverPort = 7755;
	
	public RobotLegoSockets(JTextField l, boolean liveMode) {
		super(l, liveMode);
	}
	
	@Override
	public boolean OpenNXT(String name) {
		if(!connect(serverHost, serverPort)) return false;
		
		String[] procedures;
		
		while((procedures = Agent.procedureBuilder((receive()))) == null) {
			/*
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			*/
		}
		
		if(!connect(procedures[1], Integer.parseInt(procedures[2]))) return false;
		
		return true;
	}
	
	public boolean connect(String serverHost, int serverPort) {
		try {
			socket = new Socket(serverHost, serverPort);
			authenticate();
			return true;
		} catch(IOException e) {
			createServer();
			connect(serverHost, serverPort);
		}
		
		return false;
	}
	
	public void createServer() { 
		try {
			AgentManager.runJavaProcess(agentPath, null);
			Thread.sleep(500);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}	
	}
	
	public void authenticate() { 
		send(Client.token);
	}
	
	@Override
	public boolean CloseNXT() { 
		shutdown();
		return true;
	}
	
	public void shutdown() { 
		send("500:0:0:0:0:0:0");
	}
	
	private void send(String msg) { 
		PrintWriter out;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println(msg);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}
	
	private String receive() {
		BufferedReader in;
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			return in.readLine();
		} catch (IOException e) {
			return null;
		}
	}
	
	// 1:direction:distance:radius:angle:offsetL:offsetR:
	@Override
	public void Reta(int units) { send("300:8:" + units + ":0:0:0:0:"); }
	
	@Override
	public void CurvarDireita(int radius, int angle) { send("300:6:" + radius + ":" + angle + ":0:0:0"); }
	
	@Override
	public void CurvarEsquerda(int radius, int angle) { send("300:4:" + radius + ":" + angle + ":0:0:0"); }
	
	@Override
	public void AjustarVMD(int offset) { send("300:9:0:0:0:" + offset + ":0"); }
	
	@Override
	public void AjustarVME(int offset) { send("300:7:0:0:0:" + offset+ ":0"); }
	
	@Override
	public void Parar(boolean trueStop) { send("300:5:0:0:0:0:0"); }
}