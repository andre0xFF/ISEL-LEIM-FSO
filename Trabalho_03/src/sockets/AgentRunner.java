package sockets;

import java.io.IOException;
import java.net.ServerSocket;


public abstract class AgentRunner implements Runnable {
	static final int PORT = 7766;
	
	public static void main(String[] args) throws IOException {
		System.out.println("Agent $ starting");
		ServerSocket serverSocket = new ServerSocket(AgentRunner.PORT);
		Agent agent = new Agent();
		
		while(true) {	
			if(!agent.toggleStatusOnServer()) continue;
			System.out.println("Server $ I'm free");
			
			System.out.println("Agent $ waiting for new connections");
			agent.connectWithClient(serverSocket.accept());
			
			{
				int r = Client.authenticate(agent.receive());
				if(r != Client.ID) continue;
				
				System.out.println("Client $ connected");
				agent.connectWithRobot();
				System.out.println("Robot $ connected");
			}
			
			String r;
			while((r = agent.receive()) != null) {
				// TODO: add connection timeout?
				int[] c;
				if((c = Codes.stringToInt(r))[0] != Codes.ROBOT_ORDER) break;
				
				agent.robot(c);	
			}
			
			agent.disconnectFromClient();
			System.out.println("Client $ disconnected");
			agent.disconnectFromRobot();
			System.out.println("Robot $ disconnected");
		}
		
	}
	
	//public void run() { }
}