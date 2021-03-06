package aqms;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileWriter;
import java.sql.ResultSet;

public class Data implements PropertyChangeListener {

	private JFrame frame;
	private JFormattedTextField txtStartDate = new JFormattedTextField(DateFormat.getDateInstance(DateFormat.LONG));
	private JFormattedTextField txtEndDate = new JFormattedTextField(DateFormat.getDateInstance(DateFormat.LONG));
	private String modeBtnCal,valStartDate,valEndDate;
	private CalendarWindow calendarWindow;
	private DefaultTableModel model = new DefaultTableModel();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Data window = new Data();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
    @Override
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getPropertyName().equals("selectedDate")) {
			java.util.Calendar cal = (java.util.Calendar)event.getNewValue();
			Date selDate =  cal.getTime();
			if(modeBtnCal.contentEquals("btnCalStart")) {
				txtStartDate.setValue(selDate);
				valStartDate = new SimpleDateFormat("yyyy-MM-dd").format(selDate);
			}
			if(modeBtnCal.contentEquals("btnCalEnd")) {
				txtEndDate.setValue(selDate);
				valEndDate = new SimpleDateFormat("yyyy-MM-dd").format(selDate);
			}
        }
		modeBtnCal = "";
	}

	public Data() {
		initialize();
	}
	
	private void export(){
        FileWriter fileWriter;
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("export_output/excel"));
        int retrival = chooser.showSaveDialog(null);
        if (retrival == JFileChooser.APPROVE_OPTION) {
            try{
                TableModel tModel = model;
                fileWriter = new FileWriter(new File(chooser.getSelectedFile() + ".xls"));
                for(int i = 0; i < tModel.getColumnCount(); i++){
	                fileWriter.write(tModel.getColumnName(i).toUpperCase() + "\t");
	            }
                fileWriter.write("\n");
                for(int i=0; i < tModel.getRowCount(); i++) {
                for(int j=0; j < tModel.getColumnCount(); j++) {
	                fileWriter.write(tModel.getValueAt(i,j).toString() + "\t");
	            }
                fileWriter.write("\n");
            }
                fileWriter.close();
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
   }

	private void initialize() {
		frame = new JFrame("DATA");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/images/logotrusur.png")));
		frame.setBounds(0,0,1024,600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel contentPane = new JPanel();
		JLabel lblFilter = new JLabel("Filter : ");
		JLabel lblTo = new JLabel("s/d");
		JButton btnCalStart = new JButton("...");
		JButton btnCalEnd = new JButton("...");
		JButton btnFilter = new JButton("Filter");
		JButton btnExport = new JButton("Export");
		JScrollPane tableScrollPane;
		JTable table = new JTable(); 

		Object[] columnNames = { "Waktu", "PM10", "PM25", "SO2", "CO", "O3", "NO2", "HC", "Tekanan", "Suhu", "Kelembaban", "Curah Hujan", "Kec. angin", "Arah Angin", "Solar Radiasi" }; 
		Object[] data = new Object[15];
        model.setColumnIdentifiers(columnNames);
        table.setModel(model);
		
		txtStartDate.setValue(new Date());
		txtEndDate.setValue(new Date());
		
		valStartDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
		valEndDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
		calendarWindow = new CalendarWindow(); 
		calendarWindow.addPropertyChangeListener(this);
		
		lblFilter.setBounds(20, 5, 60, 25);
		txtStartDate.setBounds(60,5,100,25);
		btnCalStart.setBounds(160,5,20,25);
		lblTo.setBounds(200, 5, 20, 25);
		txtEndDate.setBounds(240,5,100,25);
		btnCalEnd.setBounds(340,5,20,25);
		btnFilter.setBounds(380,5,100,25);
		btnExport.setBounds(490,5,100,25);
		
		btnCalStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				modeBtnCal = "btnCalStart";
				Date d = (Date)txtStartDate.getValue();	
				calendarWindow.resetSelection(d);				
				calendarWindow.setUndecorated(true);
				calendarWindow.setVisible(true);
			}
		});
		
		btnCalEnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				modeBtnCal = "btnCalEnd";
				Date d = (Date)txtEndDate.getValue();	
				calendarWindow.resetSelection(d);				
				calendarWindow.setUndecorated(true);
				calendarWindow.setVisible(true);
			}
		});
		
		btnFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (int i = model.getRowCount() - 1; i > -1; i--) {
					model.removeRow(i);
			    }
				
				ResultSet aqm_data = Main.execQuery("SELECT * FROM data WHERE waktu >= '" + valStartDate + " 00:00:00' AND waktu <= '" + valEndDate + " 23:59:59' ORDER BY waktu DESC LIMIT 100");
				try {
					while(aqm_data.next()) {
						data[0] = aqm_data.getString("waktu");
						data[1] = Double.toString(aqm_data.getDouble("pm10"));
						data[2] = Double.toString(aqm_data.getDouble("pm25"));
						data[3] = Double.toString(aqm_data.getDouble("so2"));
						data[4] = Double.toString(aqm_data.getDouble("co"));
						data[5] = Double.toString(aqm_data.getDouble("o3"));
						data[6] = Double.toString(aqm_data.getDouble("no2"));
						data[7] = Double.toString(aqm_data.getDouble("hc"));
						data[8] = Double.toString(aqm_data.getDouble("pressure"));
						data[9] = Double.toString(aqm_data.getDouble("temp"));
						data[10] = Double.toString(aqm_data.getDouble("humidity"));
						data[11] = Double.toString(aqm_data.getDouble("rainrate"));
						data[12] = Double.toString(aqm_data.getDouble("windspeed"));
						data[13] = Double.toString(aqm_data.getDouble("winddir"));
						data[14] = Double.toString(aqm_data.getDouble("solarrad"));
						model.addRow(data);
					}
				} catch (Exception e) {}
			}
		});
		
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				export();
			}
		});
		
		ResultSet aqm_data = Main.execQuery("SELECT * FROM data ORDER BY waktu DESC LIMIT 100");
		try {
			while(aqm_data.next()) {
				data[0] = aqm_data.getString("waktu");
				data[1] = Double.toString(aqm_data.getDouble("pm10"));
				data[2] = Double.toString(aqm_data.getDouble("pm25"));
				data[3] = Double.toString(aqm_data.getDouble("so2"));
				data[4] = Double.toString(aqm_data.getDouble("co"));
				data[5] = Double.toString(aqm_data.getDouble("o3"));
				data[6] = Double.toString(aqm_data.getDouble("no2"));
				data[7] = Double.toString(aqm_data.getDouble("hc"));
				data[8] = Double.toString(aqm_data.getDouble("pressure"));
				data[9] = Double.toString(aqm_data.getDouble("temp"));
				data[10] = Double.toString(aqm_data.getDouble("humidity"));
				data[11] = Double.toString(aqm_data.getDouble("rainrate"));
				data[12] = Double.toString(aqm_data.getDouble("windspeed"));
				data[13] = Double.toString(aqm_data.getDouble("winddir"));
				data[14] = Double.toString(aqm_data.getDouble("solarrad"));
				model.addRow(data);
			}
		} catch (Exception e) {}
   
        
        table.getColumnModel().getColumn(0).setPreferredWidth(120);
        table.setBounds(0, 0, 1000, 500); 
  
        tableScrollPane = new JScrollPane(table); 
        tableScrollPane.setBounds(5, 40, 1000, 510); 

		contentPane.add(lblFilter);
		contentPane.add(txtStartDate);
		contentPane.add(btnCalStart);
		contentPane.add(lblTo);
		contentPane.add(txtEndDate);
		contentPane.add(btnCalEnd);
		contentPane.add(btnFilter);
		contentPane.add(btnExport);
		contentPane.add(tableScrollPane);
		
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 1));
		contentPane.setLayout(new BorderLayout(0, 0));
		frame.setContentPane(contentPane);
		
	}

}
