package sockets;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import sun.misc.Queue;

public class Server {

	public static void main(String[] args) throws IOException, InterruptedException {	
		final int serverPort = 7755;
		final int agentPort = 7766;
		final String agentPath = "//home//andrew//Workspace//Agent.jar";
		Queue<Client> clients = new Queue<>();
		ProcessManager manager = new ProcessManager();
		
		System.out.println("Server $ starting");
		ServerSocket serverSocket = new ServerSocket(serverPort);
		
		while(true) {
			System.out.println("Server $ waiting for new connections");
			Client c = new Client(serverSocket.accept());
			System.out.println("Client $ connected");
			
			if(!c.authenticate()) break;
			
			clients.enqueue(c);
			System.out.println("Client $ authenticated and queued");
			
			if(manager.isRunning()) break;
			
			System.out.println("Agent $ starting");
			manager.start(agentPath, agentPort);
			
			Thread.sleep(500);
			clients.dequeue().accept("127.0.0.1", agentPort);		
		}
	}
}

class Client {
	final static String token = "omELIltY5u9fbkXvHxRc7CDcH4yIr7TB";
	Socket socket;
	
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
	
	public boolean authenticate() throws IOException {
		return token.equals(receive());
	}
	
	public void accept(String agentIp, int agentPort) throws IOException {
		send("200:" + agentIp + ":" + Integer.toString(agentPort) + ":");
		socket.close();
	}
}

class ProcessManager {
	private String path = new String();
	private int port;
	Process p;
	
	public void start(String path, int port) throws IOException {
		this.path = path;
		this.port = port;
		p = runJavaProcess(this.path, new String());
	}
	
	public int getPort() {
		return this.port;
	}
	
	public static Process runJavaProcess(String path, String params) throws IOException {
		return Runtime.getRuntime().exec("java -jar " + path + " " + params);
	}
	
	public boolean isRunning() {
		try {
			p.isAlive();
			return true;
		} catch (Exception e) {
			return false;
		}
		/*
	    try {
	    	p.exitValue();
	        return false;
	    } catch (Exception e) {
	        return true;
	    }
	    */
	}
	
	public void killAgent() {
		//Runtime.getRuntime().exec("kill " PID);
	}
}
