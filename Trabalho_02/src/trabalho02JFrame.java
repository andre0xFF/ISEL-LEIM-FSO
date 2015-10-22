import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

@SuppressWarnings("serial")
public class trabalho02JFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtProcess;
	private JLabel lblDebug;
	private JLabel lblProcesses;
	private ProcessManager pm = new ProcessManager();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					trabalho02JFrame frame = new trabalho02JFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public trabalho02JFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblProcess = new JLabel("Processo");
		lblProcess.setBounds(12, 12, 70, 15);
		contentPane.add(lblProcess);
		
		txtProcess = new JTextField();
		txtProcess.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					log(getProcessName(txtProcess.getText()));
				}
			}
		});
		txtProcess.setText("//home//andrew//Workspace//Trabalho_01.jar");
		txtProcess.setBounds(100, 10, 328, 53);
		contentPane.add(txtProcess);
		txtProcess.setColumns(10);
		
		JButton btnExecute = new JButton("Executar");
		btnExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guiRunProcess();
			}
		});
		btnExecute.setBounds(311, 75, 117, 25);
		contentPane.add(btnExecute);
		
		JLabel lblNumberOfActiveProcesses = new JLabel("Numero de processos activos:");
		lblNumberOfActiveProcesses.setBounds(12, 80, 220, 15);
		contentPane.add(lblNumberOfActiveProcesses);
		
		lblProcesses = new JLabel("0");
		lblProcesses.setBounds(244, 80, 28, 15);
		contentPane.add(lblProcesses);
		
		JCheckBox chckbxDebug = new JCheckBox("Debug");
		chckbxDebug.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggleDebug();
			}
		});
		chckbxDebug.setBounds(12, 103, 129, 23);
		contentPane.add(chckbxDebug);
		
		lblDebug = new JLabel("Debug enabled? false");
		lblDebug.setEnabled(false);
		lblDebug.setBounds(12, 134, 416, 128);
		contentPane.add(lblDebug);
	}

	private void guiRunProcess() {
		if(txtProcess.getText() == "")
			return;
		
		String processPath = txtProcess.getText();
		
		if(!verifyFilePath(processPath) || !pm.openProcess(processPath)) {
			log("Falha ao executar o processo " + getProcessName(processPath));
			return;
		}
		
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
            	log("");
            	String r = pm.trimDeadProcesses();
            	
            	if(r == null) {
            		return;
            	}
            	           
                log("Terminou o processo " + getProcessName(r));
                lblProcesses.setText(Integer.toString(pm.getNumberOfProcessesAlive()));
            }
        };
        
        timer.schedule(task, 0, 5000);

		updateGui();
		log("Sucesso a executar o processo " + getProcessName(txtProcess.getText()));	
	}
	
	protected boolean verifyFilePath(String processPath) {
		File fP = new File(processPath);
		return fP.exists();
	}

	private void log(String text) {
		if(!lblDebug.isEnabled())
			return;
		
		lblDebug.setText(text);
	}

	private void toggleDebug() {
		lblDebug.setEnabled(!lblDebug.isEnabled());
		lblDebug.setText("Debug enabled? " + Boolean.toString(lblDebug.isEnabled()));
	}
	
	private void updateGui() {
		lblProcesses.setText(Integer.toString(pm.getNumberOfProcesses()));
	}
	
	protected String getProcessName(String processPath) {
		return processPath.substring(
				processPath.lastIndexOf('/') + 1,
				processPath.length()
				);
	}
 }
