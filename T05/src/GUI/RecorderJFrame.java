package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Robot.MyRobotLego;
import Robot.Recorder;

@SuppressWarnings("serial")
public class RecorderJFrame extends JFrame implements Runnable {
	private JPanel contentPane;
	private JFrame t05;
	private Recorder recorder;
	
	public RecorderJFrame(T05JFrame t05, Recorder recorder) {
		this.recorder = recorder;
		this.t05 = t05;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 214, 253);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnSaveConfig = new JButton("Guardar Configura\u00E7\u00E3o");
		btnSaveConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				recorder.saveConfig();
				t05.log("Configuration saved!");
			}
		});
		btnSaveConfig.setBounds(10, 11, 175, 23);
		contentPane.add(btnSaveConfig);
		
		JButton btnLoadConfig = new JButton("Carregar Configura\u00E7\u00E3o");
		btnLoadConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				recorder.loadConfig();
				t05.updateGuiComponents();
			}
		});
		btnLoadConfig.setBounds(10, 45, 175, 23);
		contentPane.add(btnLoadConfig);
		
		JButton btnSavePath = new JButton("Gravar Trajet\u00F3ria");
		btnSavePath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//recording = true;
				//if(playing) playing = false;
				//main.log("Recording: " + recording);
			}
		});
		btnSavePath.setBounds(10, 79, 175, 23);
		contentPane.add(btnSavePath);
		
		JButton btnLoadPath = new JButton("Reproduzir Trajet\u00F3ria");
		btnLoadPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//if(recording) recording = false;
				//playing = true;
				//loadPathFromFile();
				//main.log("Playing: " + playing);
			}
		});
		btnLoadPath.setBounds(10, 113, 175, 23);
		contentPane.add(btnLoadPath);
		
		JButton btnLoadInversePath = new JButton("Reproduzir Trajet\u00F3ria Inversa");
		btnLoadInversePath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//inverse = true;
				//playing = true;
				//if(recording) recording = false;
				//main.log("Playing inverse: " + playing);
			}
		});
		btnLoadInversePath.setBounds(10, 147, 175, 23);
		contentPane.add(btnLoadInversePath);
		
		JButton btnStop = new JButton("Parar Grava\u00E7\u00E3o/Reprodu\u00E7\u00E3o");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//if(recording) {
				//	recording = false;
				//	savePathToFile();
				//}
				//playing = false;
				//inverse = false;
				//main.log("Stopped all");
			}
			
		});
		btnStop.setBounds(10, 181, 175, 23);
		contentPane.add(btnStop);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	@Override
	public void run() { }

}
