package sockets.threads;

import javax.swing.JTextField;
import java.io.*;
import java.net.*;
import robot.MyRobotLego;

public class RobotLegoSockets extends MyRobotLego {
	private Socket serverSocket;
    private PrintWriter out;
    private BufferedReader in;
    private JTextField l;
    private final String serverHostname = "127.0.0.1";
    
	public RobotLegoSockets(JTextField l, boolean liveMode) {
		super(l, liveMode);
		this.l = l;
		connect();
	}
	
	private void connect() {
		try {
			Thread.sleep(500);
			serverSocket = new Socket(serverHostname, 7755);
			out = new PrintWriter(serverSocket.getOutputStream(), true);
	        in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
		} catch(IOException | InterruptedException e) {
			new Server(l).start();
			connect();
		}	
	}
	
	@Override
	public boolean OpenNXT(String name) {
		if(serverSocket.isClosed()) connect();
		
		try {
	        sendCommand("0:2:");
	        String input;
	        
			while((input = in.readLine()) == null) {
				ClientHandler.stringToArraysOfInt(input);
				Thread.sleep(1000);
			}
		} catch (IOException | InterruptedException e) {
			return false;
		}
		
		// TODO: 
		// ask the server if he can use the robot
		// server replies -- true:false
		return true;
	}
	
	@Override
	public boolean CloseNXT() {
		try {
			sendCommand("0:1:");
			in.close();
			out.close();
			serverSocket.close();
			super.CloseNXT();
			return true;
		} catch (Exception e) { return false; }
	}
	
	public void shutdown() {
		sendCommand("0:0:");
		CloseNXT();
	}
	
	public void ping() {
	}
	
	private void sendCommand(String command) {
		out.println(command);
	}
	// 1:direction:distance:radius:angle:offsetL:offsetR:
	@Override
	public void Reta(int units) { sendCommand("1:8:" + units + ":0:0:0:0:"); }
	
	@Override
	public void CurvarDireita(int radius, int angle) { sendCommand("1:6:" + radius + ":" + angle + ":0:0:0:"); }
	
	@Override
	public void CurvarEsquerda(int radius, int angle) { sendCommand("1:4:" + radius + ":" + angle + ":0:0:"); }
	
	@Override
	public void AjustarVMD(int offset) { sendCommand("1:9:0:0:0:0:" + offset + ":"); }
	
	@Override
	public void AjustarVME(int offset) { sendCommand("1:7:0:0:0:" + offset + ":0:"); }
	
	@Override
	public void Parar(boolean trueStop) { sendCommand("1:5:0:0:0:0:"); }
}