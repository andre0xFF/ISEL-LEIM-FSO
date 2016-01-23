public class FSOProcess {
	private String path;
	private Process process;
	
	public String getPath() {
		return this.path;
	}
	
	public Process getProcess() {
		return this.process;
	}
	
	public void setProcess(String path, Process process) {
		this.path = path;
		this.process = process;		
	}
	
	public FSOProcess(String path, Process process) {
		setProcess(path, process);
	}
	
	public FSOProcess() { }
}
