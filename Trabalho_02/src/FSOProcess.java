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
	/*
	public Boolean openProcess(String path) {
		if(path.endsWith(".jar"))
			return runJavaProcess(path);
		else if(path.endsWith(".exe"))
			return runWinProcess(path);
		
		return false;
	}

	private Boolean runWinProcess(String path) {
		ProcessBuilder pb = new ProcessBuilder(path);
		process = null;
		
		try {
			process = pb.start();
			this.path = path;
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public Boolean runJavaProcess(String path) {
		process = null;
		
		try {
			process = Runtime.getRuntime().exec("java -jar " + path);
			this.path = path;
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	*/
}
