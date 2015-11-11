// https://www.cs.uic.edu/~troy/spring05/cs450/sockets/socket.html
// https://github.com/areong/Socket/tree/master/src/com/areong/socket
// https://docs.oracle.com/javase/tutorial/networking/sockets/index.html
// http://www.avajava.com/tutorials/lessons/how-do-i-write-a-server-socket-that-can-handle-input-and-output.html
// https://gerrydevstory.com/2014/01/28/multithreaded-server-in-java/
// http://cs.lmu.edu/~ray/notes/javanetexamples/

package sockets;

import robot.MyRobotLego;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.JTextField;

public class Server {
	public static JTextField l;
	private ArrayList<ClientHandler> clients;
	private ServerSocket serverSocket;
	private final int port = 3366;
	
	public Server(JTextField l) {
		this.l = l;
		start();
	}
	
	private void start() {
		try {
			serverSocket = new ServerSocket(port);
			l.setText("[x] Starting server");
			while(true) {
				l.setText("[x] Waiting for connections");
				Socket clientSocket = serverSocket.accept();
				clients.add(new ClientHandler(clientSocket));
			}
		} catch(IOException e) {
			l.setText("[x] Failed to accept new client");
			return; 
		}
	}
	
	private boolean killClient(InetAddress host) {
		for(int i = 0; i < clients.size(); i++) {
			if(clients.get(i).getHost() == host) {
				l.setText("[x] Killing client");
				return clients.get(i).close();
			}
		}
		
		return false;
	}
}

class ClientHandler extends Thread {
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	private MyRobotLego robot;
	
	public ClientHandler(Socket clientSocket) {
		this.clientSocket = clientSocket;
		this.robot = new MyRobotLego(Server.l, false);
		start();
		Server.l.setText("test");
	}
	
	public InetAddress getHost() {
		return this.clientSocket.getInetAddress();
	}
	
	public boolean close() { 
		try {
			out.close();
			in.close();
			clientSocket.close();
			interrupt();
			return true;
		} catch(IOException e) { return false; }
	}
	
	@Override
	public void run() {
		try {
			out = new PrintWriter(clientSocket.getOutputStream(), true); 
        	in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        	
        	while(!this.isInterrupted()) {
        		String input = in.readLine();
        		if(input == null) break;
        		//execRobot(input);
        	}
		} catch(IOException e) { close(); }
	}
}








/*

public class Server {
	//private ArrayList<RobotLegoSockets> clients;
	private ArrayList<ClientHandler> clients;
	private final String endpoint;
	private final String token = "2256Ox3dCPN2e5i3NG279Ps82f2tFoY1";
	ServerSocket server;
	ObjectInputStream in;
	ObjectOutputStream out;
	
	
	public Server(String clientEndpoint) {
		clients.add(new RobotLegoSockets());
		endpoint = client.getEndpoint();
		start();
	}
	
	public Server(String endpoint) {
		this.endpoint = endpoint;
	}
	
	public void killClient(String host) {
		for(int i = 0; i < clients.size(); i++) {
			if(parseHost(clients.get(i).getEndpoint()).equals(host)) {
				clients.remove(i);
			}
		}
	}
	

	
	public static String parseHost(String endpoint) {
		return endpoint.substring(0, endpoint.indexOf(":") - 1);
		
	}
	
	public String getHost() {
		return parseHost(endpoint);
	}
	
	public int getPort() {
		return parsePort(endpoint);
	}
	
	private void start() {
		try {
            ServerSocket serverSocket = new ServerSocket(parsePort(endpoint));
            Socket clientSocket = serverSocket.accept();
            ClientHandler client = new ClientHandler(clientSocket);
            start();
        } catch (IOException e) { }
	}
	
}

class ClientHandler extends Thread {
	private final String host;
	
	public ClientHandler(Socket clientSocket) {
		try {
	        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
	        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
	        Object o = in.readObject();
		} catch(IOException | ClassNotFoundException e) { }
	}
	
	@Override
	public void run() {
		
	}
	
	public void start() {
		
	}
	
	public void close() {
		
	}
	
	public void sendMessage() { }
	
	public void receiveMessage() { }
}


*/