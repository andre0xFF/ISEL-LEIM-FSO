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
			agent.connectWithServer();
			System.out.println("Server $ I'm free");
			
			System.out.println("Agent $ waiting for new connections");
			agent.connectWithClient(serverSocket.accept());
			
			int r = Client.authenticate(agent.receive());
			if(r != Client.ID) continue;
			
			System.out.println("Client $ connected");
			agent.connectWithRobot();
			System.out.println("Robot $ connected");
			
			int[] c;
			while ((c = agent.receiveOrder())[0] == 300) {
				// TODO: add connection timeout?
				agent.robot(c);		
			}
			
			agent.disconnectWithClient();
			System.out.println("Client $ disconnected");
			agent.disconnectWithRobot();
			System.out.println("Robot $ disconnected");
		}
		
	}
	
	//public void run() { }
}


/*
class Agent {
	public final static String IP = "127.0.0.1";
	public final static int PORT = 7766;
	private MyRobotLego robot;
	private Socket socket;
		
	public void connectWithServer() throws UnknownHostException, IOException {
		socket = new Socket(Server.IP, Server.PORT);
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true); 
		out.println(AgentManager.TOKEN);
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
}
*/