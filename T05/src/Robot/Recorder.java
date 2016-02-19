package Robot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Recorder {
	private MyRobotLego robot;
	private boolean isPlaying = false;
	private boolean isRecording = false;
	private boolean inverse = false;
	
	public Recorder(MyRobotLego robot) {
		this.robot = robot;
	}
	
	public boolean isPlaying() { 
		return this.isPlaying; 
	}
	
	public boolean isRecording() { 
		return this.isRecording; 
	}
	
	public void savePath(File file) {
		
	}
	
	public void loadPath(File file) {
		
	}
	
	public void saveConfig() {		
		int[] robotOffsets = robot.getOffsets();
		String robotName = robot.getName();
		
		try {
			File file = new File(robotName + ".txt");
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			
			file.createNewFile();

			bw.write(robotOffsets[0] + ":" + robotOffsets[1]);
			
			bw.close();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}
	}
	
	public void loadConfig() {
		File file = new File(robot.getName() + ".txt");
		
		if(file.exists()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				
				String[] config = br.readLine().split(":");
				int[] offsets = { 
						Integer.parseInt(config[0]),
						Integer.parseInt(config[1])
				};
				
				robot.AjustarVME(offsets[0]);
				robot.AjustarVMD(offsets[1]);
				
				br.close();			
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
	}
}
