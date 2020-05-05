package aqms;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;

public class Voltages {

	private JFrame frame;
	static JTextField txtAIN0;
	static JTextField txtAIN1;
	static JTextField txtAIN2;
	static JTextField txtAIN3;
	static JTextField txtAIN0Less;
	static JTextField txtAIN1Less;
	static JTextField txtAIN2Less;
	static JTextField txtAIN3Less;
	private Timer timerClock = new Timer();
	static Double smallestAIN0,smallestAIN1,smallestAIN2,smallestAIN3;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Voltages window = new Voltages();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public Voltages() {
		initialize();
		timerClock();
		smallestAIN0 = Double.parseDouble(Main.vAIN0);
		smallestAIN1 = Double.parseDouble(Main.vAIN1);
		smallestAIN2 = Double.parseDouble(Main.vAIN2);
		smallestAIN3 = Double.parseDouble(Main.vAIN3);
	}
	
	private void timerClock() {
		timerClock.schedule( new TimerTask() {
			public void run() {
				txtAIN0.setText(Main.vAIN0);
				txtAIN1.setText(Main.vAIN1);
				txtAIN2.setText(Main.vAIN2);
				txtAIN3.setText(Main.vAIN3);
				if(smallestAIN0 > Double.parseDouble(Main.vAIN0)) smallestAIN0 = Double.parseDouble(Main.vAIN0);
				if(smallestAIN1 > Double.parseDouble(Main.vAIN1)) smallestAIN1 = Double.parseDouble(Main.vAIN1);
				if(smallestAIN2 > Double.parseDouble(Main.vAIN2)) smallestAIN2 = Double.parseDouble(Main.vAIN2);
				if(smallestAIN3 > Double.parseDouble(Main.vAIN3)) smallestAIN3 = Double.parseDouble(Main.vAIN3);
				txtAIN0Less.setText(smallestAIN0.toString());
				txtAIN1Less.setText(smallestAIN1.toString());
				txtAIN2Less.setText(smallestAIN2.toString());
				txtAIN3Less.setText(smallestAIN3.toString());
			}
		}, 0,1000);
	}

	private void initialize() {
		frame = new JFrame("Voltages");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/images/logotrusur.png")));
		frame.setBounds(50,50,325,250);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel contentPane = new JPanel();
		
		JLabel lblCurrent = new JLabel("Current");
		lblCurrent.setFont(new Font("Arial", Font.BOLD, 14));
		lblCurrent.setBounds(70, 5, 100, 30);	
		
		JLabel lblSmallest = new JLabel("Smallest");
		lblSmallest.setFont(new Font("Arial", Font.BOLD, 14));
		lblSmallest.setBounds(200, 5, 100, 30);	
		
		JLabel lblAIN0 = new JLabel("O3");
		lblAIN0.setFont(new Font("Arial", Font.BOLD, 14));
		lblAIN0.setBounds(5, 40, 50, 30);	
		txtAIN0 = new JTextField(20);
		txtAIN0.setFont(new Font("Arial", Font.BOLD, 14));
		txtAIN0.setBounds(50, 40, 100, 25);	
		txtAIN0Less = new JTextField(20);
		txtAIN0Less.setFont(new Font("Arial", Font.BOLD, 14));
		txtAIN0Less.setBounds(180, 40, 100, 25);

		JLabel lblAIN1 = new JLabel("NO2");
		lblAIN1.setFont(new Font("Arial", Font.BOLD, 14));
		lblAIN1.setBounds(5, 80, 50, 30);	
		txtAIN1 = new JTextField(20);
		txtAIN1.setFont(new Font("Arial", Font.BOLD, 14));
		txtAIN1.setBounds(50, 80, 100, 25);	
		txtAIN1Less = new JTextField(20);
		txtAIN1Less.setFont(new Font("Arial", Font.BOLD, 14));
		txtAIN1Less.setBounds(180, 80, 100, 25);
		
		JLabel lblAIN2 = new JLabel("CO");
		lblAIN2.setFont(new Font("Arial", Font.BOLD, 14));
		lblAIN2.setBounds(5, 120, 50, 30);	
		txtAIN2 = new JTextField(20);
		txtAIN2.setFont(new Font("Arial", Font.BOLD, 14));
		txtAIN2.setBounds(50, 120, 100, 25);	
		txtAIN2Less = new JTextField(20);
		txtAIN2Less.setFont(new Font("Arial", Font.BOLD, 14));
		txtAIN2Less.setBounds(180, 120, 100, 25);
		
		JLabel lblAIN3 = new JLabel("SO2");
		lblAIN3.setFont(new Font("Arial", Font.BOLD, 14));
		lblAIN3.setBounds(5, 160, 50, 30);	
		txtAIN3 = new JTextField(20);
		txtAIN3.setFont(new Font("Arial", Font.BOLD, 14));
		txtAIN3.setBounds(50, 160, 100, 25);	
		txtAIN3Less = new JTextField(20);
		txtAIN3Less.setFont(new Font("Arial", Font.BOLD, 14));
		txtAIN3Less.setBounds(180, 160, 100, 25);
		
		contentPane.add(lblCurrent);
		contentPane.add(lblSmallest);
		contentPane.add(lblAIN0);
		contentPane.add(txtAIN0);
		contentPane.add(txtAIN0Less);
		contentPane.add(lblAIN1);
		contentPane.add(txtAIN1);
		contentPane.add(txtAIN1Less);
		contentPane.add(lblAIN2);
		contentPane.add(txtAIN2);
		contentPane.add(txtAIN2Less);
		contentPane.add(lblAIN3);
		contentPane.add(txtAIN3);
		contentPane.add(txtAIN3Less);
		
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 1));
		contentPane.setLayout(new BorderLayout(0, 0));
		frame.setContentPane(contentPane);
	}

}
