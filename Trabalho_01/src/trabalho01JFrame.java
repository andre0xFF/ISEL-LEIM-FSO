/** RobotLego info
 * reta(distancia)
 * curvarDireita(raio, angulo)
 * curvarEsquerda(raio, angulo)
 * parar(true) - Parar assincrono "imediato"
 * parar(false) - Para sincrono "depois do fim dos outros comandos"
 * ajustarVMD(offset)
 * ajustarVME(offset)
 * 
 * Boolean openNXT(String nomeRobot) inicia ligacao bluetooth
 * * true se a ligação for estabelicida
 * * false se a ligação não for estabelecida
 * 
 * void closeNXT() terminar ligacao bluetooth
 * 
 * Comandos do robot podem ser executados com algum delay
 * 
 * Robot executa até 16 comandos assincronizados, depois de atingir
 * o máximo o mais antigo é eliminado e adiciona o novo 
 * (First In First Out)
 */

//import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import RobotLego.RobotLego;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class trabalho01JFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtOffsetEsquerda;
	private JTextField txtOffsetDireita;
	private JLabel lblOffsetDireita;
	private JTextField txtLog;
	private JLabel lblRobot;
	private JTextField txtRobot;
	private JTextField txtRaio;
	private JLabel lblAngulo;
	private JTextField txtAngulo;
	private JLabel lblDistancia;
	private JTextField txtDistancia;
	private JButton btnEsquerda;
	private JButton btnParar;
	private JButton btnDireita;
	private JButton btnRectaguarda;
	private JButton btnFrente;
	
	JCheckBox chckbxDebug;
	JRadioButton rdbtnOnOff;
	
	private int offsetLeft;
	private int offsetRight;
	private String robotName;
	private String debugText;
	private int radius;
	private int distance;
	private int angle;
	private boolean onOff;
	private boolean debug;
	RobotLego robot;
	
	/**
	 * Initializes variables
	 */
	void initVariables() {
		robotName = "Desenha1";
		offsetLeft = 0;
		offsetRight = 0;
		radius = 0;
		angle = 0;
		distance = 0;
		debug = true;
		onOff = true;
		
		robot = new RobotLego();
	}
	
	/**
	 * Updates GUI components
	 */
	void updateGuiComponents() {
		txtOffsetDireita.setText(Integer.toString(offsetRight));
		txtOffsetEsquerda.setText(Integer.toString(offsetLeft));
		txtRobot.setText(robotName);
		txtAngulo.setText(Integer.toString(angle));
		txtDistancia.setText(Integer.toString(distance));
		txtRaio.setText(Integer.toString(radius));
		rdbtnOnOff.setSelected(onOff);
		chckbxDebug.setSelected(debug);
		txtLog.setText(debugText);
		
		btnFrente.setEnabled(onOff);
		btnDireita.setEnabled(onOff);
		btnEsquerda.setEnabled(onOff);
		btnRectaguarda.setEnabled(onOff);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					trabalho01JFrame frame = new trabalho01JFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public trabalho01JFrame() {	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblOffsetEsquerda = new JLabel("Offset esquerda");
		lblOffsetEsquerda.setBounds(60, 14, 125, 15);
		contentPane.add(lblOffsetEsquerda);
		
		txtOffsetEsquerda = new JTextField();
		txtOffsetEsquerda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				offsetLeft = stringToInteger(txtOffsetEsquerda.getText());
				offsetLeft = stringToInteger(txtOffsetEsquerda.getText());
				log("Offset esquerda:" + Integer.toString(offsetLeft));
			}
		});
		txtOffsetEsquerda.setBounds(12, 12, 30, 19);
		contentPane.add(txtOffsetEsquerda);
		txtOffsetEsquerda.setColumns(10);
		
		txtOffsetDireita = new JTextField();
		txtOffsetDireita.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				offsetRight = stringToInteger(txtOffsetDireita.getText());
				log("Offset direita:" + Integer.toString(offsetRight));
			}	
		});
		txtOffsetDireita.setColumns(10);
		txtOffsetDireita.setBounds(398, 12, 30, 19);
		contentPane.add(txtOffsetDireita);
		
		lblOffsetDireita = new JLabel("Offset direita");
		lblOffsetDireita.setBounds(278, 14, 102, 15);
		contentPane.add(lblOffsetDireita);
		
		chckbxDebug = new JCheckBox("Debug");
		chckbxDebug.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				debug = chckbxDebug.isSelected();
			}
		});
		chckbxDebug.setBounds(12, 243, 77, 23);
		contentPane.add(chckbxDebug);
		
		txtLog = new JTextField();
		txtLog.setBounds(143, 245, 285, 19);
		contentPane.add(txtLog);
		txtLog.setColumns(10);
		
		JLabel lblLog = new JLabel("Log");
		lblLog.setBounds(95, 247, 30, 15);
		contentPane.add(lblLog);
		
		lblRobot = new JLabel("Robot");
		lblRobot.setBounds(60, 39, 51, 15);
		contentPane.add(lblRobot);
		
		txtRobot = new JTextField();
		txtRobot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				robotName = txtRobot.getText();
				log("Robot:" + robotName);
			}
		});
		txtRobot.setColumns(10);
		txtRobot.setBounds(130, 37, 90, 19);
		contentPane.add(txtRobot);
		
		rdbtnOnOff = new JRadioButton("On/Off");
		rdbtnOnOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onOff = rdbtnOnOff.isSelected();
				robotOnOff();
			}
		});
		rdbtnOnOff.setBounds(258, 35, 70, 23);
		contentPane.add(rdbtnOnOff);
		
		JLabel lblRaiocm = new JLabel("Raio (cm)");
		lblRaiocm.setBounds(60, 66, 70, 15);
		contentPane.add(lblRaiocm);
		
		txtRaio = new JTextField();
		txtRaio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radius = stringToInteger(txtRaio.getText());
				log("Raio:" + Integer.toString(radius));
			}
		});
		txtRaio.setColumns(10);
		txtRaio.setBounds(130, 64, 30, 19);
		contentPane.add(txtRaio);
		
		lblAngulo = new JLabel("Ângulo (º)");
		lblAngulo.setBounds(178, 66, 77, 15);
		contentPane.add(lblAngulo);
		
		txtAngulo = new JTextField();
		txtAngulo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				angle = stringToInteger(txtAngulo.getText());
				log("Angulo:" + Integer.toString(angle));
			}
		});
		txtAngulo.setColumns(10);
		txtAngulo.setBounds(258, 64, 30, 19);
		contentPane.add(txtAngulo);
		
		lblDistancia = new JLabel("Distância (cm)");
		lblDistancia.setBounds(306, 66, 77, 15);
		contentPane.add(lblDistancia);
		
		txtDistancia = new JTextField();
		txtDistancia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				distance = stringToInteger(txtDistancia.getText());
				log("Distancia:" + Integer.toString(distance));
			}
		});
		txtDistancia.setColumns(10);
		txtDistancia.setBounds(398, 64, 30, 19);
		contentPane.add(txtDistancia);
		
		btnFrente = new JButton("Frente");
		btnFrente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				robotMove(stringToInteger(txtDistancia.getText()), 0, 0, btnFrente.getText(), 0, 0);
			}
		});
		btnFrente.setBackground(Color.GREEN);
		btnFrente.setBounds(178, 95, 117, 40);
		contentPane.add(btnFrente);
		
		btnEsquerda = new JButton("Esquerda");
		btnEsquerda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				robotMove(stringToInteger(
						txtDistancia.getText()),
						stringToInteger(txtRaio.getText()), 
						stringToInteger(txtDistancia.getText()), 
						btnEsquerda.getText(),
						0, 0
						);
			}
		});
		btnEsquerda.setBackground(Color.YELLOW);
		btnEsquerda.setBounds(49, 147, 117, 40);
		contentPane.add(btnEsquerda);
		
		btnParar = new JButton("Parar");
		btnParar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				robotMove(stringToInteger(
						txtDistancia.getText()), 
						0, 0,
						btnParar.getText(),
						0, 0
						);
			}
		});
		btnParar.setBackground(Color.RED);
		btnParar.setBounds(178, 147, 117, 40);
		contentPane.add(btnParar);
		
		btnDireita = new JButton("Direita");
		btnDireita.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				robotMove(stringToInteger(
						txtDistancia.getText()), 
						stringToInteger(txtRaio.getText()), 
						stringToInteger(txtDistancia.getText()), 
						btnDireita.getText(),
						0, 0
						);
			}
		});
		btnDireita.setBackground(Color.MAGENTA);
		btnDireita.setBounds(306, 146, 117, 40);
		contentPane.add(btnDireita);
		
		btnRectaguarda = new JButton("Retaguarda");
		btnRectaguarda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				robotMove(
						stringToInteger(txtDistancia.getText()), 
						0, 0, 
						btnRectaguarda.getText(),
						0, 0
						);
			}
		});
		btnRectaguarda.setBackground(Color.BLUE);
		btnRectaguarda.setBounds(178, 199, 117, 40);
		contentPane.add(btnRectaguarda);
		
		initVariables();
		updateGuiComponents();
		
		//if (txtRobot.getText() instanceof String)
		//{
			
		//}
	}
	
	protected void robotOnOff() {	
		try {
			if(!onOff) {
				robot.OpenNXT(robotName);
			} else {
				robot.CloseNXT();
			}
		} catch (Exception e) { 
			log("Erro!");
			return; 
		}
		
		
		updateGuiComponents();
		log("On/Off:" + Boolean.toString(onOff));
	}

	int stringToInteger(String s) {
		try {
			return Integer.parseInt(s);
		}
		catch (NumberFormatException e) {
			return 0;
		}
		
	}
	
	void log(String v) {
		if (!debug) {
			return;
		}
		
		txtLog.setText(v);
	}
	
	void robotMove(int distance, int radius, int angle, String direction, int offsetL, int offsetR) {
		if (!onOff) {
			return;
		}
		
		switch(direction) {
			case "Frente":
				robot.Reta(distance);
				break;
			case "Retaguarda":
				robot.Reta(-distance);
				break;
			case "Direita":
				robot.CurvarDireita(radius, angle);
				robot.AjustarVMD(offsetR);
				break;
			case "Esquerda":
				robot.CurvarEsquerda(radius, angle);
				robot.AjustarVME(offsetL);
				break;
			case "Parar":
				robot.Parar(true);
		}
		
		this.distance = distance;
		this.angle = angle;
		this.radius = radius;
		log("[" + direction + "] distancia: " + this.distance + " angulo: " + this.angle + " raio: " + this.radius);
	}
	
}
