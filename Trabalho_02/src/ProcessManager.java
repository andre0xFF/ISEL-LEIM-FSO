import java.util.ArrayList;
import java.util.TimerTask;

public class ProcessManager {
	private ArrayList<Process> processList = new ArrayList<>();
	
	ProcessManager() { }
	
	public int getNumberOfProcesses() {
		return this.processList.size();
	}
	
	public Boolean runJavaProcess(String path) {
		Process p = null;
		
		try {
			p = Runtime.getRuntime().exec("java -jar " + path);
			
		} catch (Exception e) {
			return false;
		}
		
		this.processList.add(p);
		return true;
	}
	
	public Boolean runWinProcess(String path) {
		ProcessBuilder pb = new ProcessBuilder(path);
		Process p = null;
		
		try {
			p = pb.start();
		} catch (Exception e) {
			return false;
		}
		
		this.processList.add(p);
		return true;
	}
	
	private String terminatedProcess() {
		return null;
	}
	
	public static ArrayList<Process> trimDeadProcesses(ArrayList<Process> array) {
		for(int i = 0; i < array.size(); i++)
			if(!array.get(i).isAlive())
				array.remove(i);
		
		return array;
	}
	
	public int getNumberOfProcessesAlive() {
		int c = 0;
		
		for(int i = 0; i < processList.size(); i++)
			if(processList.get(i).isAlive())
				c++;		
		
		return c;
	}
}