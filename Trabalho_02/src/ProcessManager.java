import java.util.ArrayList;

public class ProcessManager {
	private ArrayList<FSOProcess> processList = new ArrayList<>();
	
	ProcessManager() { }
	
	public int getNumberOfProcesses() {
		return this.processList.size();
	}
	
	public String trimDeadProcesses() {
		for(int i = 0; i < processList.size(); i++)
			if(!processList.get(i).getProcess().isAlive())
			{
				String processName = processList.get(i).getPath();
				processList.remove(i);
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
		FSOProcess process = new FSOProcess();
		
		if(!process.openProcess(path)) {
			return false;
		}
		
		processList.add(process);
		return true;
	}
}