import java.io.IOException;
import java.util.ArrayList;

public class ProcessManager {
	private ArrayList<FSOProcess> processList = new ArrayList<>();
	
	ProcessManager() { }
	
	public int getNumberOfProcesses() {
		return this.processList.size();
	}
	
	private void addProcess(FSOProcess fsop) {
		processList.add(fsop);
	}
	
	private void rmProcess(FSOProcess fsop) {
		processList.remove(fsop);
	}
	
	public String trimDeadProcesses() {
		for(int i = 0; i < processList.size(); i++)
			if(!processList.get(i).getProcess().isAlive())
			{
				String processName = processList.get(i).getPath();
				rmProcess(processList.get(i));
				return processName;
			}
		
		return null;
	}
	
	public int getNumberOfProcessesAlive() {
		int c = 0;
		
		for(int i = 0; i < processList.size(); i++)
			if(processList.get(i).getProcess().isAlive())
				c++;
		
		return c;
	}

	public Boolean openProcess(String path) {
		if(path.endsWith(".jar"))
			return runJavaProcess(path);
		else if(path.endsWith(".exe"))
			return runWinProcess(path);
		
		return false;
	}
	
	private Boolean runWinProcess(String path) {
		ProcessBuilder pb = new ProcessBuilder(path);
		Process process = null;
		
		try {
			process = pb.start();
			FSOProcess fsop = new FSOProcess(path, process);
			addProcess(fsop);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	private Boolean runJavaProcess(String path) {
		Process process = null;
		
		try {
			process = Runtime.getRuntime().exec("java -jar " + path);
			FSOProcess fsop = new FSOProcess(path, process);
			addProcess(fsop);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
}