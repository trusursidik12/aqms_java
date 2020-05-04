package aqms;

import java.awt.EventQueue;
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

public class Configuration {

	private JFrame frame;
	static JTextField txtDeviceId;
	static JTextField txtStasiunId;
	static JTextField txtNamaStasiun;
	static JTextField txtAlamat;
	static JTextField txtKota;
	static JTextField txtPropinsi;
	static JTextField txtLatitude;
	static JTextField txtLongitude;
	static JTextField txtIntervalPompa;
	static JTextField txtKontrolerPompa;
	static JTextField txtPortPM10;
	static JTextField txtBaudPM10;
	static JTextField txtPortPM25;
	static JTextField txtBaudPM25;
	static JTextField txtPortPump;
	static JTextField txtBaudPump;
	static JTextField txtPortHC;
	static JTextField txtBaudHC;
	static JTextField txtPortPwm;
	static JTextField txtBaudPwm;
	static JTextField txtPortWs;
	static JTextField txtBaudWs;
	static String valPortHC;
	static String valBaudHC;
	static String valPortPwm;
	static String valBaudPwm;
	static String valPortWs;
	static String valBaudWs;
	static JButton btnSimpan;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Configuration window = new Configuration();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Configuration() {
		initialize();
		ResultSet conf = Main.execQuery("SELECT * FROM configurations");
		try {
			while(conf.next()) {
				if(conf.getString("data").contentEquals("device_id")) txtDeviceId.setText(conf.getString("content"));
				if(conf.getString("data").contentEquals("sta_id")) txtStasiunId.setText(conf.getString("content"));
				if(conf.getString("data").contentEquals("sta_nama")) txtNamaStasiun.setText(conf.getString("content"));
				if(conf.getString("data").contentEquals("sta_alamat")) txtAlamat.setText(conf.getString("content"));
				if(conf.getString("data").contentEquals("sta_kota")) txtKota.setText(conf.getString("content"));
				if(conf.getString("data").contentEquals("sta_prov")) txtPropinsi.setText(conf.getString("content"));
				if(conf.getString("data").contentEquals("sta_lat")) txtLatitude.setText(conf.getString("content"));
				if(conf.getString("data").contentEquals("sta_lon")) txtLongitude.setText(conf.getString("content"));
				if(conf.getString("data").contentEquals("com_pm10")) txtPortPM10.setText(conf.getString("content"));
				if(conf.getString("data").contentEquals("baud_pm10")) txtBaudPM10.setText(conf.getString("content"));
				if(conf.getString("data").contentEquals("com_pm25")) txtPortPM25.setText(conf.getString("content"));
				if(conf.getString("data").contentEquals("baud_pm25")) txtBaudPM25.setText(conf.getString("content"));
				if(conf.getString("data").contentEquals("controller")) txtPortPump.setText(conf.getString("content"));
				if(conf.getString("data").contentEquals("controller_baud")) txtBaudPump.setText(conf.getString("content"));
				if(conf.getString("data").contentEquals("com_hc")) {
					txtPortHC.setText(conf.getString("content"));
					valPortHC = conf.getString("content");
				}
				if(conf.getString("data").contentEquals("baud_hc")) {
					txtBaudHC.setText(conf.getString("content"));
					valBaudHC = conf.getString("content");
				}
				if(conf.getString("data").contentEquals("com_pwm")) {
					txtPortPwm.setText(conf.getString("content"));
					valPortPwm = conf.getString("content");
				}
				if(conf.getString("data").contentEquals("baud_pwm")) {
					txtBaudPwm.setText(conf.getString("content"));
					valBaudPwm = conf.getString("content");
				}
				if(conf.getString("data").contentEquals("com_ws")) {
					txtPortWs.setText(conf.getString("content"));
					valPortWs = conf.getString("content");
				}
				if(conf.getString("data").contentEquals("baud_ws")) {
					txtBaudWs.setText(conf.getString("content"));
					valBaudWs = conf.getString("content");
				}
				if(conf.getString("data").contentEquals("pump_interval")) txtIntervalPompa.setText(conf.getString("content"));
				if(conf.getString("data").contentEquals("pump_control")) txtKontrolerPompa.setText(conf.getString("content"));
			}
		} catch (Exception e) {e.printStackTrace();}
	}

	private void initialize() {
		frame = new JFrame("KONFIGURASI");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/images/logotrusur.png")));
		frame.setBounds(100,100,650,400);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel contentPane = new JPanel();
		
		JLabel lblDeviceId = new JLabel("Device ID");
		lblDeviceId.setBounds(5, 5, 200, 20);
		JLabel lblStasiunId = new JLabel("Stasiun ID");
		lblStasiunId.setBounds(5, 30, 200, 20);
		JLabel lblNamaStasiun = new JLabel("Nama Stasiun");
		lblNamaStasiun.setBounds(5, 55, 200, 20);
		JLabel lblAlamat = new JLabel("Alamat");
		lblAlamat.setBounds(5, 80, 200, 20);
		JLabel lblKota = new JLabel("Kota");
		lblKota.setBounds(5, 105, 200, 20);
		JLabel lblPropinsi = new JLabel("Propinsi");
		lblPropinsi.setBounds(5, 130, 200, 20);
		JLabel lblLatitude = new JLabel("Latitude");
		lblLatitude.setBounds(5, 155, 200, 20);
		JLabel lblLongitude = new JLabel("Longitude");
		lblLongitude.setBounds(5, 180, 200, 20);
		JLabel lblIntervalPompa = new JLabel("Interval Pompa (menit)");
		lblIntervalPompa.setBounds(5, 205, 200, 20);
		JLabel lblKontrolerPompa = new JLabel("Speed Pompa (1-100)");
		lblKontrolerPompa.setBounds(5, 230, 200, 20);
		
		JLabel lblPortPM10 = new JLabel("Port PM10");
		JLabel lblBaudPM10 = new JLabel("Baud PM10");
		JLabel lblPortPM25 = new JLabel("Port PM25");
		JLabel lblBaudPM25 = new JLabel("Baud PM25");
		JLabel lblPortPump = new JLabel("Port Pump");
		JLabel lblBaudPump = new JLabel("Baud Pump");
		JLabel lblPortHC = new JLabel("Port HC");
		JLabel lblBaudHC = new JLabel("Baud HC");
		JLabel lblPortPwm = new JLabel("Port PWM");
		JLabel lblBaudPwm = new JLabel("Baud PWM");
		JLabel lblPortWs = new JLabel("Port WS");
		JLabel lblBaudWs = new JLabel("Baud WS");
		lblPortPM10.setBounds(450, 5, 70, 20);
		lblBaudPM10.setBounds(450, 30, 70, 20);
		lblPortPM25.setBounds(450, 55, 70, 20);
		lblBaudPM25.setBounds(450, 80, 70, 20);
		lblPortPump.setBounds(450, 105, 70, 20);
		lblBaudPump.setBounds(450, 130, 70, 20);
		lblPortHC.setBounds(450, 155, 70, 20);
		lblBaudHC.setBounds(450, 180, 70, 20);
		lblPortPwm.setBounds(450, 205, 70, 20);
		lblBaudPwm.setBounds(450, 230, 70, 20);
		lblPortWs.setBounds(450, 255, 70, 20);
		lblBaudWs.setBounds(450, 280, 70, 20);
		
		txtDeviceId = new JTextField(20);
		txtStasiunId = new JTextField(100);
		txtNamaStasiun = new JTextField(100);
		txtAlamat = new JTextField(200);
		txtKota = new JTextField(100);
		txtPropinsi = new JTextField(100);
		txtLatitude = new JTextField(20);
		txtLongitude = new JTextField(20);
		txtIntervalPompa = new JTextField(5);
		txtKontrolerPompa = new JTextField(5);
		txtPortPM10 = new JTextField(20);
		txtBaudPM10 = new JTextField(10);
		txtPortPM25 = new JTextField(20);
		txtBaudPM25 = new JTextField(10);
		txtPortPump = new JTextField(20);
		txtBaudPump = new JTextField(10);
		txtPortHC = new JTextField(20);
		txtBaudHC = new JTextField(10);
		txtPortPwm = new JTextField(20);
		txtBaudPwm = new JTextField(10);
		txtPortWs = new JTextField(20);
		txtBaudWs = new JTextField(10);
		btnSimpan = new JButton("Simpan");
		btnSimpan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.execQuery("UPDATE configurations SET content='" + txtDeviceId.getText() + "' WHERE data='device_id'");
				Main.execQuery("UPDATE configurations SET content='" + txtStasiunId.getText() + "' WHERE data='sta_id'");
				Main.execQuery("UPDATE configurations SET content='" + txtNamaStasiun.getText() + "' WHERE data='sta_nama'");
				Main.execQuery("UPDATE configurations SET content='" + txtAlamat.getText() + "' WHERE data='sta_alamat'");
				Main.execQuery("UPDATE configurations SET content='" + txtKota.getText() + "' WHERE data='sta_kota'");
				Main.execQuery("UPDATE configurations SET content='" + txtPropinsi.getText() + "' WHERE data='sta_prov'");
				Main.execQuery("UPDATE configurations SET content='" + txtLatitude.getText() + "' WHERE data='sta_lat'");
				Main.execQuery("UPDATE configurations SET content='" + txtLongitude.getText() + "' WHERE data='sta_lon'");
				Main.execQuery("UPDATE configurations SET content='" + txtPortPM10.getText() + "' WHERE data='com_pm10'");
				Main.execQuery("UPDATE configurations SET content='" + txtBaudPM10.getText() + "' WHERE data='baud_pm10'");
				Main.execQuery("UPDATE configurations SET content='" + txtPortPM25.getText() + "' WHERE data='com_pm25'");
				Main.execQuery("UPDATE configurations SET content='" + txtBaudPM25.getText() + "' WHERE data='baud_pm25'");
				Main.execQuery("UPDATE configurations SET content='" + txtPortPump.getText() + "' WHERE data='controller'");
				Main.execQuery("UPDATE configurations SET content='" + txtBaudPump.getText() + "' WHERE data='controller_baud'");
				if(valPortHC == null) {
					Main.execQuery("INSERT INTO configurations (data,content) VALUES ('com_hc','" + txtPortHC.getText() + "')");
				} else {
					Main.execQuery("UPDATE configurations SET content='" + txtPortHC.getText() + "' WHERE data='com_hc'");
				}
				if(valBaudHC == null) {
					Main.execQuery("INSERT INTO configurations (data,content) VALUES ('baud_hc','" + txtBaudHC.getText() + "')");
				} else {
					Main.execQuery("UPDATE configurations SET content='" + txtBaudHC.getText() + "' WHERE data='baud_hc'");
				}
				if(valPortPwm == null) {
					Main.execQuery("INSERT INTO configurations (data,content) VALUES ('com_pwm','" + txtPortPwm.getText() + "')");
				} else {
					Main.execQuery("UPDATE configurations SET content='" + txtPortPwm.getText() + "' WHERE data='com_pwm'");
				}
				if(valBaudPwm == null) {
					Main.execQuery("INSERT INTO configurations (data,content) VALUES ('baud_pwm','" + txtBaudPwm.getText() + "')");
				} else {
					Main.execQuery("UPDATE configurations SET content='" + txtBaudPwm.getText() + "' WHERE data='baud_pwm'");
				}
				if(valPortWs == null) {
					Main.execQuery("INSERT INTO configurations (data,content) VALUES ('com_ws','" + txtPortWs.getText() + "')");
				} else {
					Main.execQuery("UPDATE configurations SET content='" + txtPortWs.getText() + "' WHERE data='com_ws'");
				}
				if(valBaudWs == null) {
					Main.execQuery("INSERT INTO configurations (data,content) VALUES ('baud_ws','" + txtBaudWs.getText() + "')");
				} else {
					Main.execQuery("UPDATE configurations SET content='" + txtBaudWs.getText() + "' WHERE data='baud_ws'");
				}
				Main.execQuery("UPDATE configurations SET content='" + txtIntervalPompa.getText() + "' WHERE data='pump_interval'");
				Main.execQuery("UPDATE configurations SET content='" + txtKontrolerPompa.getText() + "' WHERE data='pump_control'");
				JOptionPane.showMessageDialog(null, "Data tersimpan");
				
				Main.initParam();
				
				try {
					Main.serialPM10.closePort();
					Main.serialPM25.closePort();
					Main.serialHC.closePort();
					Main.serialPwm.closePort();
					Main.isPM10 = false;
					Main.isPM25 = false;
					Main.isHC = false;
					Main.isPwm = false;
			    } catch (Exception ex) {}
				
				
				try {
					Main.serialPM10 = Main.OpenSerial(Main.portPM10, Main.baudPM10);
					Main.isPM10 = true;
				} catch (Exception ex) { }

				try {
					Main.serialPM25 = Main.OpenSerial(Main.portPM25, Main.baudPM25);
					Main.isPM25 = true;
				} catch (Exception ex) { }

				try {
					Main.serialHC = Main.OpenSerial(Main.portHC, Main.baudHC);
					Main.isHC = true;
				} catch (Exception ex) { }
				
				try {
					Main.serialPwm = Main.OpenSerial(Main.portPwm, Main.baudPwm);
					Main.isPwm = true;
				} catch (Exception ex) { }
				
				Main.lblLocation.setText(Main.nama_stasiun);
			}
		});
		txtDeviceId.setBounds(205, 5, 200, 20);
		txtStasiunId.setBounds(205, 30, 200, 20);
		txtNamaStasiun.setBounds(205, 55, 200, 20);
		txtAlamat.setBounds(205, 80, 200, 20);
		txtKota.setBounds(205, 105, 200, 20);
		txtPropinsi.setBounds(205, 130, 200, 20);
		txtLatitude.setBounds(205, 155, 200, 20);
		txtLongitude.setBounds(205, 180, 200, 20);
		txtIntervalPompa.setBounds(205, 205, 200, 20);
		txtKontrolerPompa.setBounds(205, 230, 200, 20);
		txtPortPM10.setBounds(520, 5, 100, 20);
		txtBaudPM10.setBounds(520, 30, 100, 20);
		txtPortPM25.setBounds(520, 55, 100, 20);
		txtBaudPM25.setBounds(520, 80, 100, 20);
		txtPortPump.setBounds(520, 105, 100, 20);
		txtBaudPump.setBounds(520, 130, 100, 20);
		txtPortHC.setBounds(520, 155, 100, 20);
		txtBaudHC.setBounds(520, 180, 100, 20);
		txtPortPwm.setBounds(520, 205, 100, 20);
		txtBaudPwm.setBounds(520, 230, 100, 20);
		txtPortWs.setBounds(520, 255, 100, 20);
		txtBaudWs.setBounds(520, 280, 100, 20);
		btnSimpan.setBounds(450, 305, 100, 50);
		
		contentPane.add(lblDeviceId);
		contentPane.add(lblStasiunId);
		contentPane.add(lblNamaStasiun);
		contentPane.add(lblAlamat);
		contentPane.add(lblKota);
		contentPane.add(lblPropinsi);
		contentPane.add(lblLatitude);
		contentPane.add(lblLongitude);
		contentPane.add(lblIntervalPompa);
		contentPane.add(lblKontrolerPompa);
		contentPane.add(txtDeviceId);
		contentPane.add(txtStasiunId);
		contentPane.add(txtNamaStasiun);
		contentPane.add(txtAlamat);
		contentPane.add(txtKota);
		contentPane.add(txtPropinsi);
		contentPane.add(txtLatitude);
		contentPane.add(txtLongitude);
		contentPane.add(txtIntervalPompa);
		contentPane.add(txtKontrolerPompa);
		contentPane.add(lblPortPM10);
		contentPane.add(lblBaudPM10);
		contentPane.add(lblPortPM25);
		contentPane.add(lblBaudPM25);
		contentPane.add(lblPortPump);
		contentPane.add(lblBaudPump);
		contentPane.add(lblPortHC);
		contentPane.add(lblBaudHC);
		contentPane.add(lblPortPwm);
		contentPane.add(lblBaudPwm);
		contentPane.add(lblPortWs);
		contentPane.add(lblBaudWs);
		contentPane.add(txtPortPM10);
		contentPane.add(txtBaudPM10);
		contentPane.add(txtPortPM25);
		contentPane.add(txtBaudPM25);
		contentPane.add(txtPortPump);
		contentPane.add(txtBaudPump);
		contentPane.add(txtPortHC);
		contentPane.add(txtBaudHC);
		contentPane.add(txtPortPwm);
		contentPane.add(txtBaudPwm);
		contentPane.add(txtPortWs);
		contentPane.add(txtBaudWs);
		contentPane.add(btnSimpan);
		
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 1));
		contentPane.setLayout(new BorderLayout(0, 0));
		frame.setContentPane(contentPane);
	}

}
