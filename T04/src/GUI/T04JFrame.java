package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Robot.MyRobotLego;

@SuppressWarnings("serial")
public class T04JFrame extends JFrame implements Runnable {
	private static final String DEFAULT_ROBOT_NAME = "FSO1";
	
	private JPanel contentPane;
	private JTextField txtOffsetEsquerda;
	private JTextField txtOffsetDireita;
	private JLabel lblOffsetDireita;
	private JLabel lblOffsetEsquerda;
	private JLabel lblLog;
	private JTextField txtLog;
	private JLabel lblRobot;
	protected JTextField txtRobot;
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
	private JCheckBox chckbxDebug;
	private JCheckBox chckbxVaguear;
	private JCheckBox chckbxEvitarObstaculo;
	private JCheckBox chckbxFugir;
	private JRadioButton rdbtnOnOff;
	
	private int offsetLeft;
	private int offsetRight;
	private String debugText;
	private int radius;
	private int distance;
	private int angle;
	protected boolean onOff = false;
	private boolean debug;
	
	private JTextField textDistMinFugir;
	private JTextField textDistMaxFugir;
	
	private boolean walk;
	private boolean avoid;
	private boolean run;
	
	protected MyRobotLego robotLego;
	
	void initVariables(MyRobotLego robot) {
		offsetLeft = 0;
		offsetRight = 2;
		radius = 0;
		angle = 0;
		distance = 0;
		debug = true;
		onOff = false;
		walk = false;
		avoid = false;
		run = false;
		
		this.robotLego = robot;
	}
	
	public void updateGuiComponents() {
		txtOffsetEsquerda.setEnabled(onOff);
		txtOffsetDireita.setEnabled(onOff);
		txtAngulo.setEnabled(onOff);
		txtAngulo.setText(Integer.toString(angle));
		txtDistancia.setEnabled(onOff);
		txtDistancia.setText(Integer.toString(distance));
		txtRaio.setEnabled(onOff);
		txtRaio.setText(Integer.toString(radius));
		rdbtnOnOff.setSelected(onOff);
		chckbxDebug.setSelected(debug);	
		chckbxVaguear.setSelected(walk);
		chckbxEvitarObstaculo.setSelected(avoid);
		chckbxFugir.setSelected(run);
		txtLog.setText(debugText);
		btnFrente.setEnabled(onOff);
		btnDireita.setEnabled(onOff);
		btnEsquerda.setEnabled(onOff);
		btnRectaguarda.setEnabled(onOff);
		btnParar.setEnabled(onOff);
		textDistMaxFugir.setEnabled(onOff);
		textDistMinFugir.setEnabled(onOff);
		chckbxEvitarObstaculo.setEnabled(onOff);
		chckbxFugir.setEnabled(onOff);
		chckbxVaguear.setEnabled(onOff);
		
		if(robotLego.getName() != "") {
			txtRobot.setText(robotLego.getName());
			txtOffsetEsquerda.setText(Integer.toString(robotLego.getOffsets()[0]));
			txtOffsetDireita.setText(Integer.toString(robotLego.getOffsets()[1]));
		}
	}

	public static void main(String[] args) {
		T04JFrame frame = new T04JFrame(new MyRobotLego());
		frame.setVisible(true);
	}

	public T04JFrame(MyRobotLego robot) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 587, 327);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblOffsetEsquerda = new JLabel("Offset esquerda");
		lblOffsetEsquerda.setBounds(60, 14, 125, 15);
		contentPane.add(lblOffsetEsquerda);
		
		txtOffsetEsquerda = new JTextField();
		txtOffsetEsquerda.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				offsetLeft = stringToInteger(txtOffsetEsquerda.getText());
				robot.AjustarVME(offsetLeft);
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
				robot.AjustarVMD(offsetRight);
				log("Offset direita:" + Integer.toString(offsetRight));
			}	
		});
		txtOffsetDireita.setColumns(10);
		txtOffsetDireita.setBounds(398, 12, 30, 19);
		contentPane.add(txtOffsetDireita);
		
		lblOffsetDireita = new JLabel("Offset direita");
		lblOffsetDireita.setBounds(286, 14, 102, 15);
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
		
		lblLog = new JLabel("Log");
		lblLog.setBounds(95, 247, 30, 15);
		contentPane.add(lblLog);
		
		lblRobot = new JLabel("Robot");
		lblRobot.setBounds(134, 39, 51, 15);
		contentPane.add(lblRobot);
		
		txtRobot = new JTextField();
		txtRobot.setText(DEFAULT_ROBOT_NAME);
		txtRobot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				log("Robot:" + txtRobot.getText());
			}
		});
		txtRobot.setColumns(10);
		txtRobot.setBounds(198, 36, 90, 19);
		contentPane.add(txtRobot);
		
		rdbtnOnOff = new JRadioButton("On/Off");
		rdbtnOnOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onOff = rdbtnOnOff.isSelected();
				robotOnOff(!onOff);
			}
		});
		rdbtnOnOff.setBounds(456, 10, 70, 23);
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
				robotMove(
						stringToInteger(txtDistancia.getText()),
						0, 0,
						8,
						offsetLeft, offsetRight);
			}
		});
		btnFrente.setBackground(Color.GREEN);
		btnFrente.setBounds(178, 95, 117, 40);
		contentPane.add(btnFrente);
		
		btnEsquerda = new JButton("Esquerda");
		btnEsquerda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				robotMove(
						stringToInteger(txtDistancia.getText()),
						stringToInteger(txtRaio.getText()), 
						stringToInteger(txtAngulo.getText()), 
						4,
						offsetLeft, offsetRight
						);
			}
		});
		btnEsquerda.setBackground(Color.YELLOW);
		btnEsquerda.setBounds(49, 147, 117, 40);
		contentPane.add(btnEsquerda);
		
		btnParar = new JButton("Parar");
		btnParar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				robotMove(
						stringToInteger(txtDistancia.getText()), 
						0, 0,
						5,
						offsetLeft, offsetRight
						);
			}
		});
		btnParar.setBackground(Color.RED);
		btnParar.setBounds(178, 147, 117, 40);
		contentPane.add(btnParar);
		
		btnDireita = new JButton("Direita");
		btnDireita.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				robotMove(
						stringToInteger(txtDistancia.getText()), 
						stringToInteger(txtRaio.getText()), 
						stringToInteger(txtAngulo.getText()), 
						6,
						offsetLeft, offsetRight
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
						2,
						offsetLeft, offsetRight
						);
			}
		});
		btnRectaguarda.setBackground(Color.BLUE);
		btnRectaguarda.setBounds(178, 199, 117, 40);
		contentPane.add(btnRectaguarda);
		
		chckbxVaguear = new JCheckBox("Vaguear");
		chckbxVaguear.setBounds(443, 92, 97, 23);
		chckbxVaguear.addActionListener(new ActionListener() {		
			public void actionPerformed(ActionEvent e) {
				robotLego.goWalk();
				walk = !walk;
			}
			
		});
		contentPane.add(chckbxVaguear);
		
		chckbxEvitarObstaculo = new JCheckBox("Evitar obstaculo");
		chckbxEvitarObstaculo.setBounds(443, 118, 122, 23);
		chckbxEvitarObstaculo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				robotLego.goAvoid();
				avoid = !avoid;
			}
		});
		contentPane.add(chckbxEvitarObstaculo);
		
		chckbxFugir = new JCheckBox("Fugir");
		chckbxFugir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				robotLego.goRun(Integer.parseInt(textDistMinFugir.getText()), Integer.parseInt(textDistMaxFugir.getText()));
				run = !run;
			}
		});
		chckbxFugir.setBounds(443, 147, 97, 23);
		contentPane.add(chckbxFugir);
		
		textDistMinFugir = new JTextField();
		textDistMinFugir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(stringToInteger(textDistMaxFugir.getText()) < 0) textDistMinFugir.setText("0");
			}
		});
		textDistMinFugir.setText("0");
		textDistMinFugir.setBounds(443, 210, 30, 20);
		contentPane.add(textDistMinFugir);
		textDistMinFugir.setColumns(10);
		
		textDistMaxFugir = new JTextField();
		textDistMaxFugir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(stringToInteger(textDistMaxFugir.getText()) > 255) textDistMaxFugir.setText("250");
			}
		});
		textDistMaxFugir.setText("250");
		textDistMaxFugir.setBounds(485, 210, 30, 20);
		contentPane.add(textDistMaxFugir);
		textDistMaxFugir.setColumns(10);
		
		JLabel lblDistancia_1 = new JLabel("Distancia");
		lblDistancia_1.setBounds(441, 183, 70, 15);
		contentPane.add(lblDistancia_1);
		
		this.setVisible(true);
		initVariables(robot);
		updateGuiComponents();
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if(onOff) robotLego.CloseNXT();
			}
		});
	}
	
	protected void robotOnOff(Boolean status) {
		onOff = (status == true) ? !robotLego.CloseNXT() : robotLego.OpenNXT(txtRobot.getText());
		updateGuiComponents();
		log("On/Off: " + Boolean.toString(onOff));
	}

	private int stringToInteger(String s) {
		try {
			return Integer.parseInt(s);
		}
		catch (NumberFormatException e) {
			return 0;
		}
		
	}
	
	public void log(String v) {
		if (!debug) { return; }
		
		txtLog.setText(v);
	}
	
	public void robotMove(int distance, int radius, int angle, int direction, int offsetL, int offsetR) {
		if (!onOff) return;

		switch(direction) {
			case 8:
				robotLego.Reta(distance, true);
				break;
			case 2:
				robotLego.Reta(-distance, true);
				break;
			case 6:
				robotLego.CurvarDireita(radius, angle, true);
				robotLego.AjustarVMD(offsetR);
				break;
			case 4:
				robotLego.CurvarEsquerda(radius, angle, true);
				robotLego.AjustarVME(offsetL);
				break;
			case 5:
				robotLego.Parar(true);
		}
		
		this.distance = distance;
		this.angle = angle;
		this.radius = radius;
		log("[" + direction + "] distancia: " + this.distance + " angulo: " + this.angle + " raio: " + this.radius);
	}

	@Override
	public void run() { }
}
