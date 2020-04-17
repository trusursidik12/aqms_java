package aqms;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
	
	static BigDecimal resultSO2 = null;
	static BigDecimal resultCO = null;
	static BigDecimal resultO3 = null;
	static BigDecimal resultNO2 = null;
	static BigDecimal resultHC = null;
	static Expression expSO2 = null;
	static Expression expCO = null;
	static Expression expO3 = null;
	static Expression expNO2 = null;
	static Expression expHC = null;
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
	
	
	protected ResultSet execQuery(String query) {
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
	
	public static void main(String[] args) {
        //System.out.println( "admin".hashCode() );		
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
	
	private void initSerial() {
		execQuery("TRUNCATE TABLE serial_ports");
		String[] portNames = SerialPortList.getPortNames();
		for(int i = 0; i < portNames.length; i++){
			execQuery("INSERT INTO serial_ports (port,description) VALUES ('" + portNames[i] +"','" + portNames[i] + "')");
		}
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
		
		
	}
	
    private void initChart() {
        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBackground(Color.white);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 1));
        chartPane.add(chartPanel);
    }

    private XYDataset createDataset() {
        TimeSeriesCollection dataset = new TimeSeriesCollection();

        TimeSeries serSO2 = new TimeSeries("SO2");
        final Minute minute = new Minute();
        serSO2.add(new Second(1, minute), 0.72);
        serSO2.add(new Second(3, minute), 0.789);
        serSO2.add(new Second(5, minute), 0.79);
        serSO2.add(new Second(7, minute), 0.4);
        serSO2.add(new Second(9, minute), 0.59);
        serSO2.add(new Second(11, minute), 0.289);
        serSO2.add(new Second(13, minute), 0.389);
        serSO2.add(new Second(15, minute), 0.89);
        dataset.addSeries(serSO2);

//        TimeSeries series2 = new TimeSeries("Series2");
//        series2.add(new Day(1, 1, 2017), 40);
//        series2.add(new Day(2, 1, 2017), 35);
//        series2.add(new Day(3, 1, 2017), 26);
//        series2.add(new Day(4, 1, 2017), 45);
//        series2.add(new Day(5, 1, 2017), 40);
//        series2.add(new Day(6, 1, 2017), 35);
//        series2.add(new Day(7, 1, 2017), 45);
//        series2.add(new Day(8, 1, 2017), 48);
//        series2.add(new Day(9, 1, 2017), 31);
//        series2.add(new Day(10, 1, 2017), 32);
//        series2.add(new Day(11, 1, 2017), 21);
//        series2.add(new Day(12, 1, 2017), 35);
//        series2.add(new Day(13, 1, 2017), 10);
//        series2.add(new Day(14, 1, 2017), 25);
//        series2.add(new Day(15, 1, 2017), 15);
//        dataset.addSeries(series2);
        

        return dataset;
      }

	
	private JFreeChart createChart(XYDataset dataset) {
		JFreeChart chart = ChartFactory.createTimeSeriesChart("","Time","Value",dataset);
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

	public Main() {
		initialize();
		initSerial();
		initParam();
		timer1();
		initChart(); 		
	}
	
	private void timer1() {
		timer1.schedule( new TimerTask() {
		    public void run() {
		    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");  
				LocalDateTime now = LocalDateTime.now();  
				lblTime.setText(dtf.format(now));
				dtf = DateTimeFormatter.ofPattern("dd MMMM yyyy");  
				now = LocalDateTime.now();  
				lblDate.setText(dtf.format(now));
				
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
				
				if(resultSO2.doubleValue() < 0) txtSO2 = "0"; else txtSO2 = new DecimalFormat("#.###").format(resultSO2);
				if(resultCO.doubleValue() < 0) txtCO = "0"; else txtCO = new DecimalFormat("#.###").format(resultCO);
				if(resultO3.doubleValue() < 0) txtO3 = "0"; else txtO3 = new DecimalFormat("#.###").format(resultO3);
				if(resultNO2.doubleValue() < 0) txtNO2 = "0"; else txtNO2 = new DecimalFormat("#.###").format(resultNO2);
				if(resultHC.doubleValue() < 0) txtHC = "0"; else txtHC = new DecimalFormat("#.###").format(resultHC);
				
				lblSO2val.setText(txtSO2);
				lblCOval.setText(txtCO);
				lblO3val.setText(txtO3);
				lblNO2val.setText(txtNO2);
				lblHCval.setText(txtHC);
				
				//System.out.println(readSerial("COM8",9600));
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
		btnKonfigurasi.setMargin( new Insets(1,1,1,1));  
		btnKonfigurasi.setFocusPainted(false);
		btnKonfigurasi.setFont(btnFont);
		btnKonfigurasi.setForeground(btnColor);
		btnKonfigurasi.setBackground(btnBgColor);
		btnKonfigurasi.setBounds(77,15,115,35);
		
		btnData = new JButton("Data");
		btnData.setMargin(new Insets(1,1,1,1));  
		btnData.setFocusPainted(false);
		btnData.setFont(btnFont);
		btnData.setForeground(btnColor);
		btnData.setBackground(btnBgColor);
		btnData.setBounds(214,15,115,35);
		
		btnPompa = new JButton("Pompa 1");
		btnPompa.setMargin(new Insets(1,1,1,1));  
		btnPompa.setFocusPainted(false);
		btnPompa.setFont(btnFont);
		btnPompa.setForeground(Color.WHITE);
		btnPompa.setBackground(green);
		btnPompa.setBounds(800,15,87,35);
		
		btnSatuan = new JButton("Satuan");
		btnSatuan.setMargin(new Insets(1,1,1,1));  
		btnSatuan.setFocusPainted(false);
		btnSatuan.setFont(btnFont);
		btnSatuan.setForeground(btnColor);
		btnSatuan.setBackground(btnBgColor);
		btnSatuan.setBounds(905,15,77,35);

		
		headerPane = new JPanel() {  
			public void paintComponent(Graphics g) {  
				Image img = Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/images/dark-textures.jpg"));  
				g.drawImage(img, 0, 0, 1920, 65, this);
				Image logo = Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/images/logotrusur.png"));
				g.drawImage(logo, 8, 8, 50, 50, this);
				
			}  
		};
		headerPane.setBounds(0,0,headerPane.getWidth(), 65);
		headerPane.add(btnKonfigurasi);
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
		lblPM10val.setText("11 ug/m3");
		lblPM10val.setFont(new Font("Arial", Font.BOLD, 28));
		lblPM10val.setForeground(lime);
		lblPM10val.setBounds(58,52,150,35);
		lblPM10flow = new JLabel();
		lblPM10flow.setText("1.8 l/mnt");
		lblPM10flow.setFont(new Font("Arial", Font.BOLD, 14));
		lblPM10flow.setForeground(yellow);
		lblPM10flow.setBounds(113,91,100,25);
		pm10Pane = new JPanel() {  
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
		lblPM25val.setText("10 ug/m3");
		lblPM25val.setFont(new Font("Arial", Font.BOLD, 28));
		lblPM25val.setForeground(lime);
		lblPM25val.setBounds(58,52,150,35);
		lblPM25flow = new JLabel();
		lblPM25flow.setText("1.8 l/mnt");
		lblPM25flow.setFont(new Font("Arial", Font.BOLD, 14));
		lblPM25flow.setForeground(yellow);
		lblPM25flow.setBounds(113,91,100,25);
		pm25Pane = new JPanel() {  
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
		lblSO2val.setText("0.017");
		lblSO2val.setFont(new Font("Arial", Font.BOLD, 24));
		lblSO2val.setForeground(lime);
		lblSO2val.setBounds(67,35,150,35);
		lblSO2sat = new JLabel();
		lblSO2sat.setText("(ppm)");
		lblSO2sat.setFont(new Font("Arial", Font.BOLD, 14));
		lblSO2sat.setForeground(white);
		lblSO2sat.setBounds(117,2,50,25);
		so2Pane = new JPanel() {  
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
		lblCOval.setText("0.017");
		lblCOval.setFont(new Font("Arial", Font.BOLD, 24));
		lblCOval.setForeground(lime);
		lblCOval.setBounds(67,35,150,35);
		lblCOsat = new JLabel();
		lblCOsat.setText("(ppm)");
		lblCOsat.setFont(new Font("Arial", Font.BOLD, 14));
		lblCOsat.setForeground(white);
		lblCOsat.setBounds(117,2,50,25);
		coPane = new JPanel() {  
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
		lblO3val.setText("0.017");
		lblO3val.setFont(new Font("Arial", Font.BOLD, 24));
		lblO3val.setForeground(lime);
		lblO3val.setBounds(67,35,150,35);
		lblO3sat = new JLabel();
		lblO3sat.setText("(ppm)");
		lblO3sat.setFont(new Font("Arial", Font.BOLD, 14));
		lblO3sat.setForeground(white);
		lblO3sat.setBounds(117,2,50,25);
		o3Pane = new JPanel() {  
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
		lblNO2val.setText("0.017");
		lblNO2val.setFont(new Font("Arial", Font.BOLD, 24));
		lblNO2val.setForeground(lime);
		lblNO2val.setBounds(67,35,150,35);
		lblNO2sat = new JLabel();
		lblNO2sat.setText("(ppm)");
		lblNO2sat.setFont(new Font("Arial", Font.BOLD, 14));
		lblNO2sat.setForeground(white);
		lblNO2sat.setBounds(117,2,50,25);
		no2Pane = new JPanel() {  
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
		lblHCval.setText("0.017");
		lblHCval.setFont(new Font("Arial", Font.BOLD, 24));
		lblHCval.setForeground(lime);
		lblHCval.setBounds(67,35,150,35);
		lblHCsat = new JLabel();
		lblHCsat.setText("(ppm)");
		lblHCsat.setFont(new Font("Arial", Font.BOLD, 14));
		lblHCsat.setForeground(white);
		lblHCsat.setBounds(117,2,50,25);
		hcPane = new JPanel() {  
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
