package aqms;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.*;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.json.simple.JSONObject;
import java.sql.*;
import java.text.DecimalFormat;

import org.hsqldb.jdbc.JDBCDriver;
import com.labjack.LJM;
import com.labjack.LJMException;
import com.sun.jna.ptr.DoubleByReference;
import com.sun.jna.ptr.IntByReference;

import jssc.SerialPortList;
import jssc.SerialPort; 
import jssc.SerialPortException;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.awt.event.ActionEvent;

@SuppressWarnings("unused")
public class Main {
	private JFrame frame;
	private JPanel contentPane;
	private JPanel headerPane;
	private JPanel pm10Pane;
	private JPanel pm25Pane;
	private JPanel so2Pane;
	private JPanel coPane;
	private JPanel o3Pane;
	private JPanel no2Pane;
	private JPanel hcPane;
	private JPanel chartPane;
	private JLabel lblLocation;
	private JLabel lblInternet;
	private JLabel lblServer;
	private JLabel lblTitle;
	private JLabel lblTime;
	private JLabel lblDate;
	private JLabel lblPM10;
	private JLabel lblPM25;
	private JLabel lblSO2;
	private JLabel lblCO;
	private JLabel lblO3;
	private JLabel lblNO2;
	private JLabel lblHC;
	private JLabel lblPM10flow;
	private JLabel lblPM25flow;
	private JLabel lblSO2sat;
	private JLabel lblCOsat;
	private JLabel lblO3sat;
	private JLabel lblNO2sat;
	private JLabel lblHCsat;
	private JLabel lblPM10val;
	private JLabel lblPM25val;
	private JLabel lblSO2val;
	private JLabel lblCOval;
	private JLabel lblO3val;
	private JLabel lblNO2val;
	private JLabel lblHCval;
	private JButton btnKonfigurasi;
	private JButton btnParameter;
	private JButton btnData;
	private JButton btnPompa;
	private JButton btnSatuan;
	private Font btnFont = new Font("Arial Black", Font.BOLD, 13);
	private Color red = Color.decode("#800000");
	private Color green = Color.decode("#008000");
	private Color white = Color.WHITE;
	private Color yellow = Color.YELLOW;
	private Color btnBgColor = Color.decode("#0078D7");
	private Color btnColor = Color.YELLOW;
	private Color lime = Color.decode("#00FF00");
	private LineBorder borderPane = new LineBorder(Color.DARK_GRAY, 1, true);
	private Timer timer1 = new Timer();
	
	static String id_stasiun = "";
	static String nama_stasiun = "";
	static String satuan = "ppm";
	static SerialPort serialPM10;
	static SerialPort serialPM25;
	static String portPM10;
	static String portPM25;
	static int baudPM10;
	static int baudPM25;
	static Boolean isPM10 = false;
	static Boolean isPM25 = false;
	static String resultPM10;
	static String resultPM25;
	static BigDecimal resultSO2 = null;
	static BigDecimal resultCO = null;
	static BigDecimal resultO3 = null;
	static BigDecimal resultNO2 = null;
	static BigDecimal resultHC = null;
	static Double ppmSO2 = null;
	static Double ppmCO = null;
	static Double ppmO3 = null;
	static Double ppmNO2 = null;
	static Double ppmHC = null;
	static Double ppbSO2 = null;
	static Double ppbCO = null;
	static Double ppbO3 = null;
	static Double ppbNO2 = null;
	static Double ppbHC = null;
	static Double ugSO2 = null;
	static Double ugCO = null;
	static Double ugO3 = null;
	static Double ugNO2 = null;
	static Double ugHC = null;
	static Double showSO2 = null;
	static Double showCO = null;
	static Double showO3 = null;
	static Double showNO2 = null;
	static Double showHC = null;
	static Expression expSO2 = null;
	static Expression expCO = null;
	static Expression expO3 = null;
	static Expression expNO2 = null;
	static Expression expHC = null;
	static String txtPM10 = "0";
	static String txtPM10flow = "0.0";
	static String txtPM25 = "0";
	static String txtPM25flow = "0.0";
	static String gainSO2 = "";
	static String offsetSO2 = "";
	static String massSO2 = "";
	static String txtSO2 = "";
	static String gainCO = "";
	static String offsetCO = "";
	static String massCO = "";
	static String txtCO = "";
	static String gainO3 = "";
	static String offsetO3 = "";
	static String massO3 = "";
	static String txtO3 = "";
	static String gainNO2 = "";
	static String offsetNO2 = "";
	static String massNO2 = "";
	static String txtNO2 = "";
	static String gainHC = "";
	static String offsetHC = "";
	static String massHC = "";
	static String txtHC = "";
	static int intervalCheckInternet = 0;
	static String idEndDataLogRange = "-1";
	static String idStartDataLogRange = "-1";
	
	protected String readLabjack(String name) {
		try {
            IntByReference handleRef = new IntByReference(0);
            int handle = 0;
            LJM.openS("ANY", "ANY", "ANY", handleRef);
            handle = handleRef.getValue();
            DoubleByReference valueRef = new DoubleByReference(0);
            LJM.eReadName(handle, name, valueRef);
            return new DecimalFormat("#.######").format( valueRef.getValue() );
        }
        catch (LJMException le) {
            le.printStackTrace();
        }
		return "0";
	}
	
	protected SerialPort OpenSerial(String Port,int BaudRate) {
		SerialPort serialPort = new SerialPort(Port);
	    try {
	        serialPort.openPort();
	        serialPort.setParams(BaudRate, 8, 1, 0);
	        return serialPort;
	    } catch (Exception ex) {System.out.println(ex);}
		return null;
	}
		
	protected String readSerial(String Port,int BaudRate) {
		SerialPort serialPort = new SerialPort(Port);
	    try {
	        serialPort.openPort();
	        serialPort.setParams(BaudRate, 8, 1, 0);
	        String response = serialPort.readString();
	        serialPort.closePort();
	        return response;
	    }
	    catch (SerialPortException ex) {
	        System.out.println(ex);
	    }
	    return "";
	}
	
	static ResultSet execQuery(String query) {
		try {
			Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/aqms","SA", "");
			Statement statement = con.createStatement();
		    ResultSet rs = statement.executeQuery(query);
		    return rs;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected ResultSet getDataLogRange(int minutes) {
		String id_end = "-1";
		String id_start = "-1";
		String lasttime;
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");  
		LocalDateTime now = LocalDateTime.now().minusMinutes(minutes);
		lasttime = dtf.format(now);
		
		ResultSet rs = execQuery("SELECT id FROM data_log WHERE waktu LIKE '" + lasttime + ":%' AND is_avg='0' ORDER BY waktu LIMIT 1");
		try {
			if(rs.next()) id_start = rs.getString("id");
			else id_start = "-1";
		} catch (Exception e) { id_start = "-1";}
		
		rs = execQuery("SELECT id FROM data_log ORDER BY waktu DESC");
		try {
			if(rs.next()) id_end = rs.getString("id");
			else id_end = "-1";
		} catch (Exception e) { id_end = "-1";}
		
		if(id_start != "-1") {
			rs = execQuery("SELECT * FROM data_log WHERE id between '" + id_start + "' AND '" + id_end + "'");
			idEndDataLogRange = id_end;
			idStartDataLogRange = id_start;
			try {
				return rs;
			} catch (Exception e) { return null; }	
		}
		return null;
	}
	
	protected String putData(JSONObject params) {
		String response = "";
		try {
			URL url = new URL("http://103.247.11.149/server_side/api/put_data.php");
			String userCredentials = "KLHK-2019:Project2016-2019";
			String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestProperty ("Authorization", basicAuth);
			con.setRequestMethod("PUT");
			con.setRequestProperty("Api-Key", "VHJ1c3VyVW5nZ3VsVGVrbnVzYV9wVA==");
			con.setRequestProperty("cache-control", "no-cache");
			con.setRequestProperty("Content-Type", "application/json");
	        con.setRequestProperty("Content-Length", String.valueOf(params.toString().getBytes("UTF-8").length));
			con.setUseCaches(false);
			con.setDoInput(true);
			con.setDoOutput(true);
			con.getOutputStream().write(params.toString().getBytes());
			Reader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
	        for (int c; (c = in.read()) >= 0;) response += (char)c;
			
	        return response;
		} catch (Exception e) {}
		return null;
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void checkInternet() {
		try {
			URL url = new URL("http://103.247.11.149/server_side/api/is_connect.php");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
			    content.append(inputLine);
			}
			in.close();
			con.disconnect();
			if(content.toString().trim().contentEquals("1")) {
				lblInternet.setText("INTERNET CONNECTED");
				lblInternet.setForeground(lime);
				lblServer.setText("SERVER CONNECTED");
				lblServer.setForeground(lime);
			} else {
				lblInternet.setText("NO INTERNET");
				lblInternet.setForeground(red);
				lblServer.setText("SERVER NOT CONNECTED");
				lblServer.setForeground(red);
			}
		} catch (Exception e) {
			lblInternet.setText("NO INTERNET");
			lblInternet.setForeground(red);
			lblServer.setText("SERVER NOT CONNECTED");
			lblServer.setForeground(red);
			e.printStackTrace();
		}
	}
	
	private void initSerial() {
		execQuery("TRUNCATE TABLE serial_ports");
		String[] portNames = SerialPortList.getPortNames();
		for(int i = 0; i < portNames.length; i++){
			execQuery("INSERT INTO serial_ports (port,description) VALUES ('" + portNames[i] +"','" + portNames[i] + "')");
		}
		
		try { 
			serialPM10 = OpenSerial(portPM10, baudPM10);
			isPM10 = true;
		} catch (Exception e) {e.printStackTrace();}

		try { 
			serialPM25 = OpenSerial(portPM25, baudPM25);
			isPM25 = true;
		} catch (Exception e) {e.printStackTrace();}
	}
	
	private void initParam() {
		ResultSet rs = null;
		try {
			rs = execQuery("SELECT molecular_mass,formula,gain,offset FROM params WHERE param_id = 'so2'");
			rs.next();
			expSO2 = new Expression(rs.getString("formula"));
			gainSO2 = rs.getString("gain");
			offsetSO2 = rs.getString("offset");
			massSO2 = rs.getString("molecular_mass");
			
			rs = execQuery("SELECT molecular_mass,formula,gain,offset FROM params WHERE param_id = 'co'");
			rs.next();
			expCO = new Expression(rs.getString("formula"));
			gainCO = rs.getString("gain");
			offsetCO = rs.getString("offset");
			massCO = rs.getString("molecular_mass");
			
			rs = execQuery("SELECT molecular_mass,formula,gain,offset FROM params WHERE param_id = 'o3'");
			rs.next();
			expO3 = new Expression(rs.getString("formula"));
			gainO3 = rs.getString("gain");
			offsetO3 = rs.getString("offset");
			massO3 = rs.getString("molecular_mass");
			
			rs = execQuery("SELECT molecular_mass,formula,gain,offset FROM params WHERE param_id = 'no2'");
			rs.next();
			expNO2 = new Expression(rs.getString("formula"));
			gainNO2 = rs.getString("gain");
			offsetNO2 = rs.getString("offset");
			massNO2 = rs.getString("molecular_mass");
			
			rs = execQuery("SELECT molecular_mass,formula,gain,offset FROM params WHERE param_id = 'hc'");
			rs.next();
			expHC = new Expression(rs.getString("formula"));
			gainHC = rs.getString("gain");
			offsetHC = rs.getString("offset");
			massHC = rs.getString("molecular_mass");
			
		} catch (Exception e) { e.printStackTrace(); }
		
		try {
			rs = execQuery("SELECT content FROM configurations WHERE data = 'sta_id'");
			rs.next();
			id_stasiun = rs.getString("content");
		} catch (Exception e) { e.printStackTrace(); }
		
		try {
			rs = execQuery("SELECT content FROM configurations WHERE data = 'sta_nama'");
			rs.next();
			nama_stasiun = rs.getString("content");
			lblLocation.setText(nama_stasiun);
		} catch (Exception e) { e.printStackTrace(); }
		
		try {
			rs = execQuery("SELECT content FROM configurations WHERE data = 'com_pm10'");
			rs.next();
			portPM10 = rs.getString("content");
		} catch (Exception e) { e.printStackTrace(); }
		
		try {
			rs = execQuery("SELECT content FROM configurations WHERE data = 'com_pm25'");
			rs.next();
			portPM25 = rs.getString("content");
		} catch (Exception e) { e.printStackTrace(); }
		
		try {
			rs = execQuery("SELECT content FROM configurations WHERE data = 'baud_pm10'");
			rs.next();
			baudPM10 = Integer.parseInt(rs.getString("content"));
		} catch (Exception e) { e.printStackTrace(); }
		
		try {
			rs = execQuery("SELECT content FROM configurations WHERE data = 'baud_pm25'");
			rs.next();
			baudPM25 = Integer.parseInt(rs.getString("content"));
		} catch (Exception e) { e.printStackTrace(); }
		
		
	}
	
    private void initChart() {
        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBackground(Color.white);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 1));
        chartPane.removeAll();
        chartPane.add(chartPanel);
    }

    private XYDataset createDataset() {
        TimeSeriesCollection dataset = new TimeSeriesCollection();

        Hour hour;
        Minute mnt;
        Second sec;
        int h,i,s;
        String waktu;
        Double logSO2;
        Double logCO;
        //Double logO3;
        Double logNO2;
        Double logHC;
        
        TimeSeries serSO2 = new TimeSeries("SO2");
        TimeSeries serCO = new TimeSeries("CO");
        //TimeSeries serO3 = new TimeSeries("O3");
        TimeSeries serNO2 = new TimeSeries("NO2");
        TimeSeries serHC = new TimeSeries("HC");
        
        ResultSet datalog = execQuery("SELECT * FROM data_log ORDER BY waktu DESC LIMIT 20");
        try {
        	while(datalog.next()) {
        		waktu = datalog.getString("waktu");
        		logSO2 = datalog.getDouble("so2");
        		logCO = datalog.getDouble("co");
        		//logO3 = datalog.getDouble("o3");
        		logNO2 = datalog.getDouble("no2");
        		logHC = datalog.getDouble("hc");
        		
        		h = Integer.parseInt(waktu.substring(11,13));
        		i = Integer.parseInt(waktu.substring(14,16));
        		s = Integer.parseInt(waktu.substring(17,19));
        		
        		hour = new Hour(h,new Day());
        		mnt = new Minute(i,hour);
        		sec = new Second(s, mnt);
        		
        		serSO2.add(sec, logSO2);
        		serCO.add(sec, logCO);
        		//serO3.add(sec, logO3);
        		serNO2.add(sec, logNO2);
        		serHC.add(sec, logHC);
        		
        	}
        } catch (Exception e) {}
        
        dataset.addSeries(serSO2);
        dataset.addSeries(serCO);
        //dataset.addSeries(serO3);
        dataset.addSeries(serNO2);
        dataset.addSeries(serHC);
        
        return dataset;
      }

	
	private JFreeChart createChart(XYDataset dataset) {
		JFreeChart chart = ChartFactory.createTimeSeriesChart("","Time","ug/m3",dataset);
        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);
        chart.setTitle(new TextTitle("",new Font("Serif", java.awt.Font.BOLD, 18)));
        return chart;
    }
	
	private void popupLogin(String mode) {
		JTextField username = new JTextField(20);
		JTextField password = new JPasswordField(20);

		JPanel myPanel = new JPanel();
		myPanel.add(new JLabel("Username :"));
		myPanel.add(username);
		myPanel.add(Box.createHorizontalStrut(15));
		myPanel.add(new JLabel("Password :"));
		myPanel.add(password);

		int result = JOptionPane.showConfirmDialog(null, myPanel, "Login", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			ResultSet users = execQuery("SELECT password FROM users WHERE username = '" + username.getText() + "'");
			try {
				if(users.next()) {
					if(Integer.parseInt(users.getString("password")) == password.getText().hashCode()) {
						if (mode == "Konfigurasi") new Configuration();
						if (mode == "Parameter") new Parameter();
						if (mode == "Data") new Data();
					} else {
						JOptionPane.showMessageDialog(null, "Username atau password salah");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Username atau password salah");
				}
			} catch (Exception e) { JOptionPane.showMessageDialog(null, "Ada kesalahan, silakan hubungi technical support!"); }
		}
	}

	public Main() {
		initialize();
		initParam();
		initSerial();
		timer1();
		initChart();
	}
	
	private void timer1() {
		timer1.schedule( new TimerTask() {
		    @SuppressWarnings("unchecked")
			public void run() {
		    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");  
				LocalDateTime now = LocalDateTime.now();  
				lblTime.setText(dtf.format(now));
				dtf = DateTimeFormatter.ofPattern("dd MMMM yyyy");  
				now = LocalDateTime.now();  
				lblDate.setText(dtf.format(now));
				
				if(intervalCheckInternet > 30) intervalCheckInternet = 0;
				if(intervalCheckInternet == 0) checkInternet();
				intervalCheckInternet++;
				
		    	try {
					String ain0 = readLabjack("AIN0");
					String ain1 = readLabjack("AIN1");
					String ain2 = readLabjack("AIN2");
					String ain3 = readLabjack("AIN3");
					String ain4 = readLabjack("AIN4");
					
					resultSO2 = expSO2.with("AIN0",ain0).and("AIN1",ain1).and("AIN2",ain2).and("AIN3",ain3).and("AIN4",ain4).and("gain",gainSO2).and("offset",offsetSO2).eval();
					resultCO = expCO.with("AIN1",ain1).and("AIN1",ain1).and("AIN2",ain2).and("AIN3",ain3).and("AIN4",ain4).and("gain",gainCO).and("offset",offsetCO).eval();
					resultO3 = expO3.with("AIN2",ain2).and("AIN1",ain1).and("AIN2",ain2).and("AIN3",ain3).and("AIN4",ain4).and("gain",gainO3).and("offset",offsetO3).eval();
					resultNO2 = expNO2.with("AIN3",ain3).and("AIN1",ain1).and("AIN2",ain2).and("AIN3",ain3).and("AIN4",ain4).and("gain",gainNO2).and("offset",offsetNO2).eval();
					resultHC = expHC.with("AIN4",ain4).and("AIN1",ain1).and("AIN2",ain2).and("AIN3",ain3).and("AIN4",ain4).and("gain",gainHC).and("offset",offsetHC).eval();
					
					ppmSO2 = resultSO2.doubleValue();
					ppmCO = resultCO.doubleValue();
					ppmO3 = resultO3.doubleValue();
					ppmNO2 = resultNO2.doubleValue();
					ppmHC = resultHC.doubleValue();
					
					if(ppmSO2 < 0) ppmSO2 = new Double("0");
					if(ppmCO < 0) ppmCO = new Double("0");
					if(ppmO3 < 0) ppmO3 = new Double("0");
					if(ppmNO2 < 0) ppmNO2 = new Double("0");
					if(ppmHC < 0) ppmHC = new Double("0");
					
					ppbSO2 = ppmSO2 * 1000;
					ppbCO = ppmCO * 1000;
					ppbO3 = ppmO3 * 1000;
					ppbNO2 = ppmNO2 * 1000;
					ppbHC = ppmHC * 1000;
					
					ugSO2 = ppbSO2 * Double.parseDouble(massSO2) / 24.45;
					ugCO = ppbCO * Double.parseDouble(massCO) / 24.45;
					ugO3 = ppbO3 * Double.parseDouble(massO3) / 24.45;
					ugNO2 = ppbNO2 * Double.parseDouble(massNO2) / 24.45;
					ugHC = ppbHC * Double.parseDouble(massHC) / 24.45;
					
					if(satuan == "ppm") {
						showSO2 = ppmSO2;
						showCO = ppmCO;
						showO3 = ppmO3;
						showNO2 = ppmNO2;
						showHC = ppmHC;
					} else if(satuan == "ppb") {
						showSO2 = ppbSO2;
						showCO = ppbCO;
						showO3 = ppbO3;
						showNO2 = ppbNO2;
						showHC = ppbHC;
					} else if(satuan == "ug") {
						showSO2 = ugSO2;
						showCO = ugCO;
						showO3 = ugO3;
						showNO2 = ugNO2;
						showHC = ugHC;
					}
					
					txtSO2 = new DecimalFormat("#.###").format(showSO2);
					txtCO = new DecimalFormat("#.###").format(showCO);
					txtO3 = new DecimalFormat("#.###").format(showO3);
					txtNO2 = new DecimalFormat("#.###").format(showNO2);
					txtHC = new DecimalFormat("#.###").format(showHC);
				} catch (Exception e) { 
					ugSO2 = new Double("0");
					ugCO = new Double("0");
					ugO3 = new Double("0");
					ugNO2 = new Double("0");
					ugHC = new Double("0");
					txtSO2 = "0";
					txtCO = "0";
					txtO3 = "0";
					txtNO2 = "0";
					txtHC = "0";
				}
		    	try {
		    		if(isPM10) {
						resultPM10 = serialPM10.readString();
						txtPM10 = new DecimalFormat("#").format((Double.parseDouble(resultPM10.split(",")[0]) * 1000));
						txtPM10flow = resultPM10.split(",")[1];
		    		}
		    	} catch (Exception e) {;
		    		txtPM10 = "0";
		    		txtPM10flow = "0.0";
		    	}
		    	
		    	try {
		    		if(isPM25) {
						resultPM25 = serialPM25.readString();
						txtPM25 = new DecimalFormat("#").format((Double.parseDouble(resultPM25.split(",")[0]) * 1000));
						txtPM25flow = resultPM25.split(",")[1];
		    		}
		    	} catch (Exception e) {
		    		txtPM25 = "0";
		    		txtPM25flow = "0.0";
		    	}
		    	
		    	lblPM10val.setText(txtPM10 + " ug/m3");
		    	lblPM10flow.setText(txtPM10flow + " l/mnt");
		    	lblPM25val.setText(txtPM25 + " ug/m3");
		    	lblPM25flow.setText(txtPM25flow + " l/mnt");
				lblSO2val.setText(txtSO2);
				lblCOval.setText(txtCO);
				lblO3val.setText(txtO3);
				lblNO2val.setText(txtNO2);
				lblHCval.setText(txtHC);

				execQuery("INSERT INTO data_log (waktu,pm10,pm25,pm10flow,pm25flow,so2,co,o3,no2,hc,is_avg) VALUES (NOW(),'" + txtPM10 + "','" + txtPM25 + "','" + txtPM10flow + "','" + txtPM25flow + "','" + ugSO2.toString() + "','" + ugCO.toString() + "','" + ugO3.toString() + "','" + ugNO2.toString() + "','" + ugHC.toString() + "','0')");
				
				ResultSet dataLog = getDataLogRange(30);
				try {
					double totPM10 = 0,totPM25 = 0,totPM10flow = 0,totPM25flow = 0,totSO2 = 0,totCO = 0,totO3 = 0,totNO2 = 0,totHC = 0;
					double avgPM10 = 0,avgPM25 = 0,avgPM10flow = 0,avgPM25flow = 0,avgSO2 = 0,avgCO = 0,avgO3 = 0,avgNO2 = 0,avgHC = 0;
					int numrow = 0;
					while(dataLog.next()) {
						numrow++;
						totPM10 += dataLog.getDouble("pm10");
						totPM25 += dataLog.getDouble("pm25");
						totPM10flow += dataLog.getDouble("pm10flow");
						totPM25flow += dataLog.getDouble("pm25flow");
						totSO2 += dataLog.getDouble("so2");
						totCO += dataLog.getDouble("co");
						totO3 += dataLog.getDouble("o3");
						totNO2 += dataLog.getDouble("no2");
						totHC += dataLog.getDouble("hc");
					}
					if(numrow > 0) {
						avgPM10 = totPM10 / numrow;
						avgPM25 = totPM25 / numrow;
						avgPM10flow = totPM10flow / numrow;
						avgPM25flow = totPM25flow / numrow;
						avgSO2 = totSO2 / numrow;
						avgCO = totCO / numrow;
						avgO3 = totO3 / numrow;
						avgNO2 = totNO2 / numrow;
						avgHC = totHC / numrow;
						
						execQuery("UPDATE data_log SET is_avg='1',avg_at=NOW() WHERE id BETWEEN '" + idStartDataLogRange + "' AND '" + idEndDataLogRange + "'");
						idStartDataLogRange = "-1";
						idEndDataLogRange = "-1";
						execQuery("INSERT INTO data (id_stasiun,waktu,pm10,pm25,pm10flow,pm25flow,so2,co,o3,no2,hc,is_sent,is_sent2) VALUES ('" + id_stasiun + "',NOW(),'" + Math.round(avgPM10) + "','" + Math.round(avgPM25) + "','" + Math.round(avgPM10flow) + "','" + Math.round(avgPM25flow) + "','" + Math.round(avgSO2) + "','" + Math.round(avgCO) + "','" + Math.round(avgO3) + "','" + Math.round(avgNO2) + "','" + Math.round(avgHC) + "','0','0')");
					}
				} catch (Exception e) {}
				
				
				ResultSet aqmData = execQuery("SELECT * FROM data WHERE is_sent = 0 ORDER BY id LIMIT 1");
				try {
					if(aqmData.next()) {
						JSONObject params = new JSONObject();
						params.put("id_stasiun", aqmData.getString("id_stasiun"));
						params.put("waktu", aqmData.getString("waktu"));
						params.put("pm10", aqmData.getDouble("pm10"));
						params.put("pm25", aqmData.getDouble("pm25"));
						params.put("so2", aqmData.getDouble("so2"));
						params.put("co", aqmData.getDouble("co"));
						params.put("o3", aqmData.getDouble("o3"));
						params.put("no2", aqmData.getDouble("no2"));
						params.put("hc", aqmData.getDouble("hc"));
						if(putData(params).trim().contentEquals("success:1")){
							execQuery("UPDATE data SET is_sent=1,sent_at=NOW() WHERE id = '" + aqmData.getDouble("id") + "'");
						}
					}
				} catch (Exception e) { }
				
				initChart();
				
		    }
		}, 0,1000);
	}
	
	private void initialize() {
		frame = new JFrame("AQMS");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/images/logotrusur.png")));
		frame.setResizable(false);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.setBounds(100, 100, 1024, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel() {  
			private static final long serialVersionUID = 1L;
			
			public void paintComponent(Graphics g) {  
				Image img = Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/images/black_metal_texture.png"));  
				g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);  
				Image location = Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/images/location.png"));
				g.drawImage(location, 12, 75, 25, 25, this);  
				Image globe = Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/images/globe.png"));
				g.drawImage(globe, 825, 75, 25, 25, this);
				Image network = Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/images/network.png"));
				g.drawImage(network, 825, 105, 25, 25, this);
			}  
		};
		
		lblLocation = new JLabel();
		lblLocation.setText("KLHK");
		lblLocation.setFont(new Font("Arial Black", Font.BOLD, 14));
		lblLocation.setForeground(lime);
		lblLocation.setBounds(40,75,300,25);
		
		lblInternet = new JLabel();
		lblInternet.setText("NO INTERNET");
		lblInternet.setFont(new Font("Arial", Font.BOLD, 12));
		lblInternet.setForeground(red);
		lblInternet.setBounds(855,75,150,25);
		
		lblServer = new JLabel();
		lblServer.setText("SERVER NOT CONNECTED");
		lblServer.setFont(new Font("Arial", Font.BOLD, 12));
		lblServer.setForeground(red);
		lblServer.setBounds(855,105,150,25);

		lblTitle = new JLabel();
		lblTitle.setText("Partikulat dan Gas");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 28));
		lblTitle.setForeground(lime);
		lblTitle.setBounds(390,120,300,25);

		lblTime = new JLabel();
		lblTime.setText("12:12:12");
		lblTime.setFont(new Font("Arial", Font.BOLD, 36));
		lblTime.setForeground(white);
		lblTime.setBounds(840,160,200,50);

		lblDate = new JLabel();
		lblDate.setText("16 April 2020");
		lblDate.setFont(new Font("Arial", Font.BOLD, 24));
		lblDate.setForeground(white);
		lblDate.setBounds(840,190,200,50);
		
		contentPane.add(lblLocation);
		contentPane.add(lblInternet);
		contentPane.add(lblServer);
		contentPane.add(lblTitle);
		contentPane.add(lblTime);
		contentPane.add(lblDate);
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 1));
		contentPane.setLayout(new BorderLayout(0, 0));
		
		btnKonfigurasi = new JButton("Konfigurasi");
		btnKonfigurasi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				popupLogin("Konfigurasi");
			}
		});
		btnKonfigurasi.setMargin( new Insets(1,1,1,1));  
		btnKonfigurasi.setFocusPainted(false);
		btnKonfigurasi.setFont(btnFont);
		btnKonfigurasi.setForeground(btnColor);
		btnKonfigurasi.setBackground(btnBgColor);
		btnKonfigurasi.setBounds(77,15,115,35);
		
		btnParameter = new JButton("Parameter");
		btnParameter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				popupLogin("Parameter");
			}
		});
		btnParameter.setMargin( new Insets(1,1,1,1));  
		btnParameter.setFocusPainted(false);
		btnParameter.setFont(btnFont);
		btnParameter.setForeground(btnColor);
		btnParameter.setBackground(btnBgColor);
		btnParameter.setBounds(214,15,115,35);
		
		btnData = new JButton("Data");
		btnData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				popupLogin("Data");
			}
		});
		btnData.setMargin(new Insets(1,1,1,1));  
		btnData.setFocusPainted(false);
		btnData.setFont(btnFont);
		btnData.setForeground(btnColor);
		btnData.setBackground(btnBgColor);
		btnData.setBounds(351,15,115,35);
		
		btnPompa = new JButton("Pompa 1");
		btnPompa.setMargin(new Insets(1,1,1,1));  
		btnPompa.setFocusPainted(false);
		btnPompa.setFont(btnFont);
		btnPompa.setForeground(Color.WHITE);
		btnPompa.setBackground(green);
		btnPompa.setBounds(800,15,87,35);
		
		btnSatuan = new JButton("Satuan");
		btnSatuan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(satuan == "ppm") { 
					satuan = "ppb";
					lblSO2sat.setText("(ppb)");
					lblCOsat.setText("(ppb)");
					lblO3sat.setText("(ppb)");
					lblNO2sat.setText("(ppb)");
					lblHCsat.setText("(ppb)");
				} else if(satuan == "ppb") {
					satuan = "ug";
					lblSO2sat.setText("(ug/m3)");
					lblCOsat.setText("(ug/m3)");
					lblO3sat.setText("(ug/m3)");
					lblNO2sat.setText("(ug/m3)");
					lblHCsat.setText("(ug/m3)");
				}else if(satuan == "ug") {
					satuan = "ppm";
					lblSO2sat.setText("(ppm)");
					lblCOsat.setText("(ppm)");
					lblO3sat.setText("(ppm)");
					lblNO2sat.setText("(ppm)");
					lblHCsat.setText("(ppm)");
				}
			}
		});
		btnSatuan.setMargin(new Insets(1,1,1,1));  
		btnSatuan.setFocusPainted(false);
		btnSatuan.setFont(btnFont);
		btnSatuan.setForeground(btnColor);
		btnSatuan.setBackground(btnBgColor);
		btnSatuan.setBounds(905,15,77,35);

		
		headerPane = new JPanel() {  
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {  
				Image img = Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/images/dark-textures.jpg"));  
				g.drawImage(img, 0, 0, 1920, 65, this);
				Image logo = Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/images/logotrusur.png"));
				g.drawImage(logo, 8, 8, 50, 50, this);
				
			}  
		};
		headerPane.setBounds(0,0,headerPane.getWidth(), 65);
		headerPane.add(btnKonfigurasi);
		headerPane.add(btnParameter);
		headerPane.add(btnData);
		headerPane.add(btnPompa);
		headerPane.add(btnSatuan);
		headerPane.setBorder(borderPane);
		headerPane.setLayout(new BorderLayout(0, 0));

		/*PANEL PM10 ========================================================================================*/
		lblPM10 = new JLabel();
		lblPM10.setText("PM10");
		lblPM10.setFont(new Font("Arial", Font.BOLD, 18));
		lblPM10.setForeground(white);
		lblPM10.setBounds(5,11,50,25);
		lblPM10val = new JLabel();
		lblPM10val.setText("0 ug/m3");
		lblPM10val.setFont(new Font("Arial", Font.BOLD, 28));
		lblPM10val.setForeground(lime);
		lblPM10val.setBounds(58,52,150,35);
		lblPM10flow = new JLabel();
		lblPM10flow.setText("0.0 l/mnt");
		lblPM10flow.setFont(new Font("Arial", Font.BOLD, 14));
		lblPM10flow.setForeground(yellow);
		lblPM10flow.setBounds(113,91,100,25);
		pm10Pane = new JPanel() {  
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
				g.drawImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/images/dark-textures.jpg")), 0, 0, 1920, 1080, this);
			}  
		};
		pm10Pane.setBounds(12,170,190,130);
		pm10Pane.add(lblPM10);
		pm10Pane.add(lblPM10val);
		pm10Pane.add(lblPM10flow);
		pm10Pane.setBorder(borderPane);
		pm10Pane.setLayout(new BorderLayout(0, 0));  
		contentPane.add(pm10Pane, BorderLayout.CENTER);
		/*END PANEL PM10 ========================================================================================*/
		
		/*PANEL PM25 ========================================================================================*/
		lblPM25 = new JLabel();
		lblPM25.setText("PM25");
		lblPM25.setFont(new Font("Arial", Font.BOLD, 18));
		lblPM25.setForeground(white);
		lblPM25.setBounds(5,11,50,25);
		lblPM25val = new JLabel();
		lblPM25val.setText("0 ug/m3");
		lblPM25val.setFont(new Font("Arial", Font.BOLD, 28));
		lblPM25val.setForeground(lime);
		lblPM25val.setBounds(58,52,150,35);
		lblPM25flow = new JLabel();
		lblPM25flow.setText("0.0 l/mnt");
		lblPM25flow.setFont(new Font("Arial", Font.BOLD, 14));
		lblPM25flow.setForeground(yellow);
		lblPM25flow.setBounds(113,91,100,25);
		pm25Pane = new JPanel() {  
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
				g.drawImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/images/dark-textures.jpg")), 0, 0, 1920, 1080, this);
			}  
		};
		pm25Pane.setBounds(217,170,190,130);
		pm25Pane.add(lblPM25);
		pm25Pane.add(lblPM25val);
		pm25Pane.add(lblPM25flow);
		pm25Pane.setBorder(borderPane);
		pm25Pane.setLayout(new BorderLayout(0, 0));  
		contentPane.add(pm25Pane, BorderLayout.CENTER);
		/*END PANEL PM25 ========================================================================================*/
		
		/*PANEL SO2 ========================================================================================*/
		lblSO2 = new JLabel();
		lblSO2.setText("SO2");
		lblSO2.setFont(new Font("Arial", Font.BOLD, 18));
		lblSO2.setForeground(white);
		lblSO2.setBounds(5,2,50,25);
		lblSO2val = new JLabel();
		lblSO2val.setText("0.0");
		lblSO2val.setFont(new Font("Arial", Font.BOLD, 24));
		lblSO2val.setForeground(lime);
		lblSO2val.setBounds(60,35,150,35);
		lblSO2sat = new JLabel();
		lblSO2sat.setText("(ppm)");
		lblSO2sat.setFont(new Font("Arial", Font.BOLD, 14));
		lblSO2sat.setForeground(white);
		lblSO2sat.setBounds(110,2,70,25);
		so2Pane = new JPanel() { 
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
				g.drawImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/images/dark-textures.jpg")), 0, 0, 1920, 1080, this);
			}  
		};
		so2Pane.setBounds(448,170,170,65);
		so2Pane.add(lblSO2);
		so2Pane.add(lblSO2val);
		so2Pane.add(lblSO2sat);
		so2Pane.setBorder(borderPane);
		so2Pane.setLayout(new BorderLayout(0, 0));  
		contentPane.add(so2Pane, BorderLayout.CENTER);
		/*END PANEL SO2 ========================================================================================*/	
		
		/*PANEL CO ========================================================================================*/
		lblCO = new JLabel();
		lblCO.setText("CO");
		lblCO.setFont(new Font("Arial", Font.BOLD, 18));
		lblCO.setForeground(white);
		lblCO.setBounds(5,2,50,25);
		lblCOval = new JLabel();
		lblCOval.setText("0.0");
		lblCOval.setFont(new Font("Arial", Font.BOLD, 24));
		lblCOval.setForeground(lime);
		lblCOval.setBounds(60,35,150,35);
		lblCOsat = new JLabel();
		lblCOsat.setText("(ppm)");
		lblCOsat.setFont(new Font("Arial", Font.BOLD, 14));
		lblCOsat.setForeground(white);
		lblCOsat.setBounds(110,2,70,25);
		coPane = new JPanel() { 
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
				g.drawImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/images/dark-textures.jpg")), 0, 0, 1920, 1080, this);
			}  
		};
		coPane.setBounds(645,170,170,65);
		coPane.add(lblCO);
		coPane.add(lblCOval);
		coPane.add(lblCOsat);
		coPane.setBorder(borderPane);
		coPane.setLayout(new BorderLayout(0, 0));  
		contentPane.add(coPane, BorderLayout.CENTER);
		/*END PANEL CO ========================================================================================*/
		
		/*PANEL O3 ========================================================================================*/
		lblO3 = new JLabel();
		lblO3.setText("O3");
		lblO3.setFont(new Font("Arial", Font.BOLD, 18));
		lblO3.setForeground(white);
		lblO3.setBounds(5,2,50,25);
		lblO3val = new JLabel();
		lblO3val.setText("0.0");
		lblO3val.setFont(new Font("Arial", Font.BOLD, 24));
		lblO3val.setForeground(lime);
		lblO3val.setBounds(60,35,150,35);
		lblO3sat = new JLabel();
		lblO3sat.setText("(ppm)");
		lblO3sat.setFont(new Font("Arial", Font.BOLD, 14));
		lblO3sat.setForeground(white);
		lblO3sat.setBounds(110,2,70,25);
		o3Pane = new JPanel() {
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
				g.drawImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/images/dark-textures.jpg")), 0, 0, 1920, 1080, this);
			}  
		};
		o3Pane.setBounds(448,242,170,65);
		o3Pane.add(lblO3);
		o3Pane.add(lblO3val);
		o3Pane.add(lblO3sat);
		o3Pane.setBorder(borderPane);
		o3Pane.setLayout(new BorderLayout(0, 0));  
		contentPane.add(o3Pane, BorderLayout.CENTER);
		/*END PANEL O3 ========================================================================================*/
		
		/*PANEL NO2 ========================================================================================*/
		lblNO2 = new JLabel();
		lblNO2.setText("NO2");
		lblNO2.setFont(new Font("Arial", Font.BOLD, 18));
		lblNO2.setForeground(white);
		lblNO2.setBounds(5,2,50,25);
		lblNO2val = new JLabel();
		lblNO2val.setText("0.0");
		lblNO2val.setFont(new Font("Arial", Font.BOLD, 24));
		lblNO2val.setForeground(lime);
		lblNO2val.setBounds(60,35,150,35);
		lblNO2sat = new JLabel();
		lblNO2sat.setText("(ppm)");
		lblNO2sat.setFont(new Font("Arial", Font.BOLD, 14));
		lblNO2sat.setForeground(white);
		lblNO2sat.setBounds(110,2,70,25);
		no2Pane = new JPanel() {
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
				g.drawImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/images/dark-textures.jpg")), 0, 0, 1920, 1080, this);
			}  
		};
		no2Pane.setBounds(645,242,170,65);
		no2Pane.add(lblNO2);
		no2Pane.add(lblNO2val);
		no2Pane.add(lblNO2sat);
		no2Pane.setBorder(borderPane);
		no2Pane.setLayout(new BorderLayout(0, 0));  
		contentPane.add(no2Pane, BorderLayout.CENTER);
		/*END PANEL NO2 ========================================================================================*/
		
		/*PANEL HC ========================================================================================*/
		lblHC = new JLabel();
		lblHC.setText("HC");
		lblHC.setFont(new Font("Arial", Font.BOLD, 18));
		lblHC.setForeground(white);
		lblHC.setBounds(5,2,50,25);
		lblHCval = new JLabel();
		lblHCval.setText("0.0");
		lblHCval.setFont(new Font("Arial", Font.BOLD, 24));
		lblHCval.setForeground(lime);
		lblHCval.setBounds(60,35,150,35);
		lblHCsat = new JLabel();
		lblHCsat.setText("(ppm)");
		lblHCsat.setFont(new Font("Arial", Font.BOLD, 14));
		lblHCsat.setForeground(white);
		lblHCsat.setBounds(110,2,70,25);
		hcPane = new JPanel() { 
			private static final long serialVersionUID = 1L;
			
			public void paintComponent(Graphics g) {
				g.drawImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/images/dark-textures.jpg")), 0, 0, 1920, 1080, this);
			}  
		};
		hcPane.setBounds(842,242,170,65);
		hcPane.add(lblHC);
		hcPane.add(lblHCval);
		hcPane.add(lblHCsat);
		hcPane.setBorder(borderPane);
		hcPane.setLayout(new BorderLayout(0, 0));  
		contentPane.add(hcPane, BorderLayout.CENTER);
		/*END PANEL HC ========================================================================================*/
		

		/*PANEL CHART========================================================================================*/
		chartPane = new JPanel() {
			private static final long serialVersionUID = 1L;
			
			public void paintComponent(Graphics g) {
				g.drawImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/images/dark-textures.jpg")), 0, 0, 1920, 1080, this);
			}  
		};
		
		chartPane.setBounds(12,316,998,272);
        chartPane.setBorder(borderPane);
		chartPane.setLayout(new BorderLayout(0, 0));  
		contentPane.add(chartPane, BorderLayout.CENTER);
		/*END PANEL CHART ========================================================================================*/
		
		frame.setContentPane(contentPane);
		frame.getContentPane().add(headerPane);
	}

}
