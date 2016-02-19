package GUI;

import Robot.Recorder;

@SuppressWarnings("serial")
public class T05JFrame extends T04JFrame {
	RecorderJFrame recorderFrame;
	
	public static void main(String[] args) {
		T05JFrame frame = new T05JFrame();
	}
	
	public T05JFrame() {
		recorderFrame = new RecorderJFrame(this, new Recorder(super.robotLego));
	}
}
