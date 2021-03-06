package aqms;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.sql.ResultSet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Parameter {

	private JFrame frame;
	static JTextField txtMolecularSO2;
	static JTextField txtFormulaSO2;
	static JTextField txtGainSO2;
	static JTextField txtOffsetSO2;
	static JTextField txtMolecularCO;
	static JTextField txtFormulaCO;
	static JTextField txtGainCO;
	static JTextField txtOffsetCO;
	static JTextField txtMolecularO3;
	static JTextField txtFormulaO3;
	static JTextField txtGainO3;
	static JTextField txtOffsetO3;
	static JTextField txtMolecularNO2;
	static JTextField txtFormulaNO2;
	static JTextField txtGainNO2;
	static JTextField txtOffsetNO2;
	
	static JButton btnSimpan;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Parameter window = new Parameter();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Parameter() {
		initialize();
		ResultSet conf = Main.execQuery("SELECT * FROM params");
		try {
			while(conf.next()) {
				if(conf.getString("param_id").contentEquals("so2")){
					txtMolecularSO2.setText(Double.toString(conf.getDouble("molecular_mass")));
					txtFormulaSO2.setText(conf.getString("formula"));
					txtGainSO2.setText(Double.toString(conf.getDouble("gain")));
					txtOffsetSO2.setText(Double.toString(conf.getDouble("offset")));
				}
				if(conf.getString("param_id").contentEquals("co")){
					txtMolecularCO.setText(Double.toString(conf.getDouble("molecular_mass")));
					txtFormulaCO.setText(conf.getString("formula"));
					txtGainCO.setText(Double.toString(conf.getDouble("gain")));
					txtOffsetCO.setText(Double.toString(conf.getDouble("offset")));
				}
				if(conf.getString("param_id").contentEquals("o3")){
					txtMolecularO3.setText(Double.toString(conf.getDouble("molecular_mass")));
					txtFormulaO3.setText(conf.getString("formula"));
					txtGainO3.setText(Double.toString(conf.getDouble("gain")));
					txtOffsetO3.setText(Double.toString(conf.getDouble("offset")));
				}
				if(conf.getString("param_id").contentEquals("no2")){
					txtMolecularNO2.setText(Double.toString(conf.getDouble("molecular_mass")));
					txtFormulaNO2.setText(conf.getString("formula"));
					txtGainNO2.setText(Double.toString(conf.getDouble("gain")));
					txtOffsetNO2.setText(Double.toString(conf.getDouble("offset")));
				}
			}
		} catch (Exception e) {e.printStackTrace();}
	}

	private void initialize() {
		frame = new JFrame("PARAMETER");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/images/logotrusur.png")));
		frame.setBounds(50,50,380,520);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel contentPane = new JPanel();
		
		JLabel lblSO2 = new JLabel("SO2 (0 - 1 ppm)");
		lblSO2.setFont(new Font("Arial", Font.BOLD, 14));
		lblSO2.setBounds(5, 5, 150, 30);		
		JLabel lblSO2Molecular = new JLabel("Molecular :");
		lblSO2Molecular.setBounds(20, 30, 70, 25);
		txtMolecularSO2 = new JTextField(20);
		txtMolecularSO2.setBounds(90, 30, 50, 25);		
		JLabel lblSO2Gain = new JLabel("Gain :");
		lblSO2Gain.setBounds(155, 30, 50, 25);
		txtGainSO2 = new JTextField(20);
		txtGainSO2.setBounds(190, 30, 50, 25);		
		JLabel lblSO2Offset = new JLabel("Offset :");
		lblSO2Offset.setBounds(255, 30, 60, 25);
		txtOffsetSO2 = new JTextField(20);
		txtOffsetSO2.setBounds(300, 30, 50, 25);
		JLabel lblSO2Formula = new JLabel("Formula :");
		lblSO2Formula.setBounds(20, 65, 70, 25);
		txtFormulaSO2 = new JTextField(20);
		txtFormulaSO2.setBounds(90, 65, 260, 25);

		JLabel lblCO = new JLabel("CO (0 - 1 ppm)");
		lblCO.setFont(new Font("Arial", Font.BOLD, 14));
		lblCO.setBounds(5, 100, 150, 30);		
		JLabel lblCOMolecular = new JLabel("Molecular :");
		lblCOMolecular.setBounds(20, 125, 70, 25);
		txtMolecularCO = new JTextField(20);
		txtMolecularCO.setBounds(90, 125, 50, 25);		
		JLabel lblCOGain = new JLabel("Gain :");
		lblCOGain.setBounds(155, 125, 50, 25);
		txtGainCO = new JTextField(20);
		txtGainCO.setBounds(190, 125, 50, 25);		
		JLabel lblCOOffset = new JLabel("Offset :");
		lblCOOffset.setBounds(255, 125, 60, 25);
		txtOffsetCO = new JTextField(20);
		txtOffsetCO.setBounds(300, 125, 50, 25);
		JLabel lblCOFormula = new JLabel("Formula :");
		lblCOFormula.setBounds(20, 160, 70, 25);
		txtFormulaCO = new JTextField(20);
		txtFormulaCO.setBounds(90, 160, 260, 25);

		JLabel lblO3 = new JLabel("O3 (0 - 5 ppm)");
		lblO3.setFont(new Font("Arial", Font.BOLD, 14));
		lblO3.setBounds(5, 195, 150, 30);		
		JLabel lblO3Molecular = new JLabel("Molecular :");
		lblO3Molecular.setBounds(20, 220, 70, 25);
		txtMolecularO3 = new JTextField(20);
		txtMolecularO3.setBounds(90, 220, 50, 25);		
		JLabel lblO3Gain = new JLabel("Gain :");
		lblO3Gain.setBounds(155, 220, 50, 25);
		txtGainO3 = new JTextField(20);
		txtGainO3.setBounds(190, 220, 50, 25);		
		JLabel lblO3Offset = new JLabel("Offset :");
		lblO3Offset.setBounds(255, 220, 60, 25);
		txtOffsetO3 = new JTextField(20);
		txtOffsetO3.setBounds(300, 220, 50, 25);
		JLabel lblO3Formula = new JLabel("Formula :");
		lblO3Formula.setBounds(20, 255, 70, 25);
		txtFormulaO3 = new JTextField(20);
		txtFormulaO3.setBounds(90, 255, 260, 25);

		JLabel lblNO2 = new JLabel("NO2 (0 - 1 ppm)");
		lblNO2.setFont(new Font("Arial", Font.BOLD, 14));
		lblNO2.setBounds(5, 290, 150, 30);		
		JLabel lblNO2Molecular = new JLabel("Molecular :");
		lblNO2Molecular.setBounds(20, 315, 70, 25);
		txtMolecularNO2 = new JTextField(20);
		txtMolecularNO2.setBounds(90, 315, 50, 25);		
		JLabel lblNO2Gain = new JLabel("Gain :");
		lblNO2Gain.setBounds(155, 315, 50, 25);
		txtGainNO2 = new JTextField(20);
		txtGainNO2.setBounds(190, 315, 50, 25);		
		JLabel lblNO2Offset = new JLabel("Offset :");
		lblNO2Offset.setBounds(255, 315, 60, 25);
		txtOffsetNO2 = new JTextField(20);
		txtOffsetNO2.setBounds(300, 315, 50, 25);
		JLabel lblNO2Formula = new JLabel("Formula :");
		lblNO2Formula.setBounds(20, 350, 70, 25);
		txtFormulaNO2 = new JTextField(20);
		txtFormulaNO2.setBounds(90, 350, 260, 25);
		
		JLabel lblHC = new JLabel("HC (0 - 50 ppm)");
		lblHC.setFont(new Font("Arial", Font.BOLD, 14));
		lblHC.setBounds(5, 385, 150, 30);		
		
		btnSimpan = new JButton("Simpan");
		btnSimpan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 Main.execQuery("UPDATE params SET molecular_mass='" + txtMolecularSO2.getText() + "',formula='" + txtFormulaSO2.getText() + "',gain='" + txtGainSO2.getText() + "',offset='" + txtOffsetSO2.getText() + "' WHERE param_id='so2'");
				 Main.execQuery("UPDATE params SET molecular_mass='" + txtMolecularCO.getText() + "',formula='" + txtFormulaCO.getText() + "',gain='" + txtGainCO.getText() + "',offset='" + txtOffsetCO.getText() + "' WHERE param_id='co'");
				 Main.execQuery("UPDATE params SET molecular_mass='" + txtMolecularO3.getText() + "',formula='" + txtFormulaO3.getText() + "',gain='" + txtGainO3.getText() + "',offset='" + txtOffsetO3.getText() + "' WHERE param_id='o3'");
				 Main.execQuery("UPDATE params SET molecular_mass='" + txtMolecularNO2.getText() + "',formula='" + txtFormulaNO2.getText() + "',gain='" + txtGainNO2.getText() + "',offset='" + txtOffsetNO2.getText() + "' WHERE param_id='no2'");
				 JOptionPane.showMessageDialog(null, "Data tersimpan");
				 Main.initParam();
			}
		});
		
		txtMolecularSO2.setFont(new Font("Arial", Font.BOLD, 14));
		txtFormulaSO2.setFont(new Font("Arial", Font.BOLD, 14));
		txtGainSO2.setFont(new Font("Arial", Font.BOLD, 14));
		txtOffsetSO2.setFont(new Font("Arial", Font.BOLD, 14));
		txtMolecularCO.setFont(new Font("Arial", Font.BOLD, 14));
		txtFormulaCO.setFont(new Font("Arial", Font.BOLD, 14));
		txtGainCO.setFont(new Font("Arial", Font.BOLD, 14));
		txtOffsetCO.setFont(new Font("Arial", Font.BOLD, 14));
		txtMolecularO3.setFont(new Font("Arial", Font.BOLD, 14));
		txtFormulaO3.setFont(new Font("Arial", Font.BOLD, 14));
		txtGainO3.setFont(new Font("Arial", Font.BOLD, 14));
		txtOffsetO3.setFont(new Font("Arial", Font.BOLD, 14));
		txtMolecularNO2.setFont(new Font("Arial", Font.BOLD, 14));
		txtFormulaNO2.setFont(new Font("Arial", Font.BOLD, 14));
		txtGainNO2.setFont(new Font("Arial", Font.BOLD, 14));
		txtOffsetNO2.setFont(new Font("Arial", Font.BOLD, 14));
		
		btnSimpan.setBounds(100, 420, 170, 50);
		
		contentPane.add(lblSO2);
		contentPane.add(lblSO2Molecular);
		contentPane.add(txtMolecularSO2);
		contentPane.add(lblSO2Gain);
		contentPane.add(txtGainSO2);
		contentPane.add(lblSO2Offset);
		contentPane.add(txtOffsetSO2);
		contentPane.add(lblSO2Formula);
		contentPane.add(txtFormulaSO2);
		contentPane.add(lblCO);
		contentPane.add(lblCOMolecular);
		contentPane.add(txtMolecularCO);
		contentPane.add(lblCOGain);
		contentPane.add(txtGainCO);
		contentPane.add(lblCOOffset);
		contentPane.add(txtOffsetCO);
		contentPane.add(lblCOFormula);
		contentPane.add(txtFormulaCO);
		contentPane.add(lblO3);
		contentPane.add(lblO3Molecular);
		contentPane.add(txtMolecularO3);
		contentPane.add(lblO3Gain);
		contentPane.add(txtGainO3);
		contentPane.add(lblO3Offset);
		contentPane.add(txtOffsetO3);
		contentPane.add(lblO3Formula);
		contentPane.add(txtFormulaO3);
		contentPane.add(lblNO2);
		contentPane.add(lblNO2Molecular);
		contentPane.add(txtMolecularNO2);
		contentPane.add(lblNO2Gain);
		contentPane.add(txtGainNO2);
		contentPane.add(lblNO2Offset);
		contentPane.add(txtOffsetNO2);
		contentPane.add(lblNO2Formula);
		contentPane.add(txtFormulaNO2);
		contentPane.add(lblHC);
		contentPane.add(btnSimpan);
		
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 1));
		contentPane.setLayout(new BorderLayout(0, 0));
		frame.setContentPane(contentPane);
	}

}
