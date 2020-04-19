package aqms;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.sql.ResultSet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	static JTextField txtPortKontroler;
	static JTextField txtBaudKontroler;
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
				if(conf.getString("data").contentEquals("controller")) txtPortKontroler.setText(conf.getString("content"));
				if(conf.getString("data").contentEquals("controller_baud")) txtBaudKontroler.setText(conf.getString("content"));
				if(conf.getString("data").contentEquals("pump_interval")) txtIntervalPompa.setText(conf.getString("content"));
				if(conf.getString("data").contentEquals("pump_control")) txtKontrolerPompa.setText(conf.getString("content"));
			}
		} catch (Exception e) {e.printStackTrace();}
	}

	private void initialize() {
		frame = new JFrame("KONFIGURASI");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/images/logotrusur.png")));
		frame.setBounds(100,100,650,300);
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
		JLabel lblKontrolerPompa = new JLabel("Kontroler Pompa (1=Show;0=Hide)");
		lblKontrolerPompa.setBounds(5, 230, 200, 20);
		
		JLabel lblPortPM10 = new JLabel("Port PM10");
		JLabel lblBaudPM10 = new JLabel("Baud PM10");
		JLabel lblPortPM25 = new JLabel("Port PM25");
		JLabel lblBaudPM25 = new JLabel("Baud PM25");
		lblPortPM10.setBounds(450, 5, 70, 20);
		lblBaudPM10.setBounds(450, 30, 70, 20);
		lblPortPM25.setBounds(450, 55, 70, 20);
		lblBaudPM25.setBounds(450, 80, 70, 20);
		
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
		txtPortKontroler = new JTextField(20);
		txtBaudKontroler = new JTextField(10);
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
				Main.execQuery("UPDATE configurations SET content='" + txtPortKontroler.getText() + "' WHERE data='controller'");
				Main.execQuery("UPDATE configurations SET content='" + txtBaudKontroler.getText() + "' WHERE data='controller_baud'");
				Main.execQuery("UPDATE configurations SET content='" + txtIntervalPompa.getText() + "' WHERE data='pump_interval'");
				Main.execQuery("UPDATE configurations SET content='" + txtKontrolerPompa.getText() + "' WHERE data='pump_control'");
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
		txtPortPM10.setBounds(520, 5, 60, 20);
		txtBaudPM10.setBounds(520, 30, 60, 20);
		txtPortPM25.setBounds(520, 55, 60, 20);
		txtBaudPM25.setBounds(520, 80, 60, 20);
		btnSimpan.setBounds(450, 105, 160, 50);
		
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
		contentPane.add(txtPortPM10);
		contentPane.add(txtBaudPM10);
		contentPane.add(txtPortPM25);
		contentPane.add(txtBaudPM25);
		contentPane.add(btnSimpan);
		
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 1));
		contentPane.setLayout(new BorderLayout(0, 0));
		frame.setContentPane(contentPane);
	}

}
