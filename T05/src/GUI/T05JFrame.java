package GUI;

import Robot.MyRobotLegoRecorder;

@SuppressWarnings("serial")
public class T05JFrame extends T04JFrame {
	RobotPlayer recorderFrame;
	
	public T05JFrame(MyRobotLegoRecorder robot) {
		super(robot);
		recorderFrame = new RobotPlayer(this, robot);
		new Thread(recorderFrame).start();
	}

	public static void main(String[] args) {
		new T05JFrame(new MyRobotLegoRecorder());
	}
	
	public String getTxtRobot() {
		return txtRobot.getText();
	}
	
	@Override
	public void updateGuiComponents() {
		super.updateGuiComponents();
		
		if(recorderFrame != null) {
			recorderFrame.updateGuiComponents(onOff);
		}
	}
}
