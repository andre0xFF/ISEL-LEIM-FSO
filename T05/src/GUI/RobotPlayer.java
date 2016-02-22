package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Robot.MyRobotLego;
import Robot.MyRobotLegoRecorder;

@SuppressWarnings("serial")
public class RobotPlayer extends JFrame implements Runnable {
	private JPanel contentPane;
	private T05JFrame t05;
	private MyRobotLegoRecorder robot;
	
	private JButton btnSaveConfig;
	private JButton btnLoadConfig;
	private JButton btnSavePath;
	private JButton btnLoadPath;
	private JButton btnLoadInversePath;
	private JButton btnStop;
	
	public RobotPlayer(T05JFrame t05, MyRobotLegoRecorder robot) {
		this.t05 = t05;
		this.robot = robot;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 214, 253);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnSaveConfig = new JButton("Guardar Configura\u00E7\u00E3o");
		btnSaveConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					robot.saveConfig();
					t05.log("Config saved: " + robot.getName());
				} catch (IOException e) {
					t05.log("Failed to save config: " + robot.getName());
				}

			}
		});
		btnSaveConfig.setBounds(10, 11, 175, 23);
		btnSaveConfig.setEnabled(false);
		contentPane.add(btnSaveConfig);
		
		btnLoadConfig = new JButton("Carregar Configura\u00E7\u00E3o");
		btnLoadConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					robot.loadConfig(t05.getTxtRobot());
					t05.updateGuiComponents();
					t05.log("Config loaded: " + robot.getName());
				} catch (IOException e) {
					t05.log("Failed to load config: " + robot.getName());
				}
			}
		});
		btnLoadConfig.setBounds(10, 45, 175, 23);
		btnLoadConfig.setEnabled(false);
		contentPane.add(btnLoadConfig);
		
		btnSavePath = new JButton("Gravar Trajet\u00F3ria");
		btnSavePath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					robot.toggleRecorder();
					t05.log("Recorder: " + robot.isRecording());
				} catch (IOException e) { }

			}
		});
		btnSavePath.setBounds(10, 79, 175, 23);
		btnSavePath.setEnabled(false);
		contentPane.add(btnSavePath);
		
		btnLoadPath = new JButton("Reproduzir Trajet\u00F3ria");
		btnLoadPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					robot.replay(false);
					t05.log("Playing: " + robot.isRecording());
				} catch (IOException e) {
					t05.log("Failed to replay: " + robot.isRecording());
				}

			}
		});
		btnLoadPath.setBounds(10, 113, 175, 23);
		btnLoadPath.setEnabled(false);
		contentPane.add(btnLoadPath);
		
		btnLoadInversePath = new JButton("Reproduzir Trajet\u00F3ria Inversa");
		btnLoadInversePath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					robot.replay(true);
					t05.log("Playing inverse: " + robot.isRecording());
				} catch (IOException e) {
					t05.log("Failed to replay: " + robot.isRecording());
				}
			}
		});
		btnLoadInversePath.setBounds(10, 147, 175, 23);
		btnLoadInversePath.setEnabled(false);
		contentPane.add(btnLoadInversePath);
		
		btnStop = new JButton("Parar Grava\u00E7\u00E3o/Reprodu\u00E7\u00E3o");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(robot.isPlaying()) {
					robot.togglePlayer();
				}
				
				if(robot.isRecording()) {
					try {
						robot.toggleRecorder();
					} catch (IOException e) { }
				}
				
				t05.log("All stopped");
			}
		});
		btnStop.setBounds(10, 181, 175, 23);
		btnStop.setEnabled(false);
		contentPane.add(btnStop);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void run() { }

	public void updateGuiComponents(boolean onOff) {
		btnSaveConfig.setEnabled(onOff);
		btnLoadConfig.setEnabled(onOff);
		btnSavePath.setEnabled(onOff);
		btnLoadPath.setEnabled(onOff);
		btnLoadInversePath.setEnabled(onOff);
		btnStop.setEnabled(onOff);
	}

}
