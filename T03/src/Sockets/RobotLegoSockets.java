package Sockets;

import javax.swing.JTextField;
import java.io.*;
import java.net.*;
import Robot.MyRobotLego;

public class RobotLegoSockets extends MyRobotLego {
	Socket socket;
	
	public RobotLegoSockets(JTextField l, boolean liveMode) {
		super(l, liveMode);
	}
	
	public void send(String msg) throws IOException {
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true); 
		out.println(msg);
	}
	
	public String receive() throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		return in.readLine();
	}
	
	public boolean connect() throws UnknownHostException, IOException { 
		socket = new Socket(Server.IP, Server.PORT);
		send(Client.TOKEN);
		
		if(Integer.parseInt(receive()) != Codes.AUTHENTICATED) return false;
		
		String r;
		while((r = receive()) != null) { 
			if(Integer.parseInt(r) != Codes.ACCEPTED) return false;
		}
		
		disconnect();
		
		socket = new Socket(Agent.IP, Agent.PORT);
		send(Client.TOKEN);
				
		return true;
	}
	
	public void disconnect() throws IOException {
		send(Integer.toString(Codes.DISCONNECT));
		socket.close();
	}
	
	@Override
	public boolean OpenNXT(String name) {
		try {
			if(!connect()) return false;
			
			return true;
		} catch (IOException e) {
			return false;
		}		
		//while((procedures = Agent.procedureBuilder((receive()))) == null) {
		//}		
	}
	/*
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
	*/
	
	@Override
	public boolean CloseNXT() { 
		try {
			send(Integer.toString(Codes.DISCONNECT));
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	/*
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
	*/

	// 1:direction:distance:radius:angle:offsetL:offsetR:
	@Override
	public void Reta(int units) { 
		//send("300:8:" + units + ":0:0:0:0:");
		try {
			send(
					Codes.ROBOT_ORDER + Codes.REGEX
					+ Codes.ROBOT_FORWARD + Codes.REGEX
					+ units + Codes.REGEX
					+ "0" + Codes.REGEX
					+ "0" + Codes.REGEX
					+ "0" + Codes.REGEX
					+ "0" + Codes.REGEX
					);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void CurvarDireita(int radius, int angle) { 
		//send("300:6:" + radius + ":" + angle + ":0:0:0");
		try {
			send(
					Codes.ROBOT_ORDER + Codes.REGEX
					+ Codes.ROBOT_RIGHT + Codes.REGEX
					+ "0" + Codes.REGEX
					+ radius + Codes.REGEX
					+ angle + Codes.REGEX
					+ "0" + Codes.REGEX
					+ "0" + Codes.REGEX
					);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void CurvarEsquerda(int radius, int angle) { 
		try {
			send(
					Codes.ROBOT_ORDER + Codes.REGEX
					+ Codes.ROBOT_LEFT + Codes.REGEX
					+ "0" + Codes.REGEX
					+ radius + Codes.REGEX
					+ angle + Codes.REGEX
					+ "0" + Codes.REGEX
					+ "0" + Codes.REGEX
					);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	@Override
	public void AjustarVMD(int offset) { 
		try {
			send(
					Codes.ROBOT_ORDER + Codes.REGEX
					+ Codes.ROBOT_OFFSET_L + Codes.REGEX
					+ "0" + Codes.REGEX
					+ "0" + Codes.REGEX
					+ "0" + Codes.REGEX
					+ offset + Codes.REGEX
					+ "0" + Codes.REGEX
					);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	@Override
	public void AjustarVME(int offset) { 
		try {
			send(
					Codes.ROBOT_ORDER + Codes.REGEX
					+ Codes.ROBOT_OFFSET_R + Codes.REGEX
					+ "0" + Codes.REGEX
					+ "0" + Codes.REGEX
					+ "0" + Codes.REGEX
					+ "0" + Codes.REGEX
					+ offset + Codes.REGEX
					);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	@Override
	public void Parar(boolean trueStop) { 
		try {
			send(
					Codes.ROBOT_ORDER + Codes.REGEX
					+ Codes.ROBOT_STOP + Codes.REGEX
					+ "0" + Codes.REGEX
					+ "0" + Codes.REGEX
					+ "0" + Codes.REGEX
					+ "0" + Codes.REGEX
					+ "0" + Codes.REGEX
					);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
