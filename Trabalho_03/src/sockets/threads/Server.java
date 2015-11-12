// https://www.cs.uic.edu/~troy/spring05/cs450/sockets/socket.html
// https://github.com/areong/Socket/tree/master/src/com/areong/socket
// https://docs.oracle.com/javase/tutorial/networking/sockets/index.html
// http://www.avajava.com/tutorials/lessons/how-do-i-write-a-server-socket-that-can-handle-input-and-output.html
// https://gerrydevstory.com/2014/01/28/multithreaded-server-in-java/
// http://cs.lmu.edu/~ray/notes/javanetexamples/

package sockets.threads;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import javax.swing.JTextField;

public class Server extends Thread {
	private JTextField l;
	private ArrayList<ClientHandler> clients = new ArrayList<>();
	private ServerSocket serverSocket;
	private final int port = 7755;
	private Semaphore mutex;
	
	public Server(JTextField l) {
		this.l = l;
	}
	
	@Override
	public void run() {
		try {
			System.out.println("[x] Starting server");
			mutex = new Semaphore(1);
			
			serverSocket = new ServerSocket(port);		
			
			while(true) {				
				Socket clientSocket = serverSocket.accept();			
				clients.add(new ClientHandler(clientSocket, l, mutex));	
				trimClients();
				System.out.println("[x] Clients connected: " + clients.size());
			}
		} catch(IOException e) {
			System.out.println("[x] Failed to accept new client");
			return;
		} finally { 
			// TODO 
		}
	}

	private ClientHandler findClient(String hostname) {
		for(int i = 0; i < clients.size(); i++) {
			if(clients.get(i).getHost().toString().equals(hostname)) {
				return clients.get(i);
			}
		}
		
		return null;
	}
	
	private boolean killClient(String hostname) {
		return findClient(hostname).close();
	}
	
	private void killClient(ClientHandler client) {
		clients.remove(client);
	}
	
	private void trimClients() {
		for(int i = 0; i < clients.size(); i++) {
			if(!clients.get(i).isAlive()) clients.remove(i);
		}
	}
}

class ClientHandler extends Thread {
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	private robot.MyRobotLego robot;
	private JTextField l;
	private boolean isAlive;
	private Semaphore mutex;
	
	public ClientHandler(Socket clientSocket, JTextField l, Semaphore mutex) {
		this.clientSocket = clientSocket;
		this.l = l;
		this.mutex = mutex;
		this.isAlive = true;
		start();
	}
	
	public String getHost() {
		return this.clientSocket.getInetAddress().toString();
	}
	
	@Override
	public void run() {
		try {
			if(!isAlive) return;
			
			out = new PrintWriter(clientSocket.getOutputStream(), true); 
        	in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        	robot = new robot.MyRobotLego(l, false);
        	
        	String input;
        	while((input = in.readLine()) != null) {
        		int[] c = stringToArraysOfInt(input);	
        		if(c[0] == 0) processCommand(c);
        		if(c[0] == 1) processOrder(c);
        	}
        	
		} catch(IOException | InterruptedException e) { }
		//finally { close(); }
	}
	
	public boolean close() { 
		try {
			// TODO: tell the server to remove this client
			System.out.print("im here");
			mutex.release();
			out.close();
			in.close();
			clientSocket.close();
			return true;
		} catch(IOException e) { return false; }
	}
	
	public void shutdown() {
		close();
		isAlive = false;
		interrupt();
	}
	
	private boolean processOrder(int[] order) throws InterruptedException {
		// 1:direction:distance:radius:angle:offsetL:offsetR:
		if (order.length == 0) return false;
		if(order[0] != 1) return false;
		
		switch(order[1]) {
		case 8:
			robot.Reta(order[1]);
			System.out.println("[x] Robot: Moving forward");
			break;
		case 2:
			robot.Reta(-order[1]);
			System.out.println("[x] Robot: Moving backwards");
			break;
		case 6:
			robot.CurvarDireita(order[3], order[4]);
			robot.AjustarVMD(order[6]);
			System.out.println("[x] Robot: Turning left");
			break;
		case 4:
			robot.CurvarEsquerda(order[3], order[4]);
			robot.AjustarVME(order[5]);
			System.out.println("[x] Robot: Turning right");
			break;
		case 5:
			robot.Parar(true);
			System.out.println("[x] Robot stop");
			break;
		case 7:
			robot.AjustarVME(order[5]);
			break;
		case 9:
			robot.AjustarVMD(order[6]);
			break;
		}
		return true;
	}
	
	private void sendCommand(String cmd) {
		out.println(cmd);
		out.flush();
	}
	
	private boolean processCommand(int[] order) throws InterruptedException {
		// 0:0: -- shutdown
		// 0:1: -- close
		// 0:2: -- start
		// 0:3: -- ping
		// 0:4: -- pong
		// 0:5: -- kill
		if (order.length == 0) return false;
		if(order[0] != 0) return false;
		
		switch(order[1]) {
		case 0:
			shutdown();
			break;
		case 1:
			close();
			break;
		case 2:
			mutex.acquire();
			sendCommand("0:2:");
			break;
		case 3:
			System.out.println("Client: ping!");
			sendCommand("0:4:");
			// send pong
			break;
		
		}
		
		return false;
	}
	
	public static int[] stringToArraysOfInt(String order) {
		String[] orders = order.split(":");
		int[] intOrders = new int[orders.length];
		
		for(int i = 0; i < orders.length; i++) {
			intOrders[i] = Integer.parseInt(orders[i]);
		}
		
		return intOrders;
	}
}