import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ButtonGroup;
import java.awt.event.MouseAdapter;

public class MainFrame implements Runnable {

	JFrame frmSiba;
	private JTable table;

	JLabel timeRunning = new JLabel("", SwingConstants.LEFT);
	JLabel lblDateRunning = new JLabel("", SwingConstants.LEFT);
	
    String currentTime;
    String dtString;

    Thread clockThread;
    Thread dateThread;
    
    private int key = 2;
    private static int orderNo = 0;
    private int qSize;
    
    private File f;
    private final ButtonGroup buttonGroup = new ButtonGroup();
    private final PriorityQueue q = new PriorityQueue();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				MainFrame window = new MainFrame();
				window.frmSiba.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
		clockThread = new Thread(this);
        dateThread = new Thread(this);
        clockThread.start();
        dateThread.start();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private synchronized void initialize() {
		frmSiba = new JFrame();
		frmSiba.setTitle("SIBAU CAFE");
		frmSiba.setBounds(350, 100, 600, 500);
		frmSiba.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSiba.getContentPane().setLayout(null);

		lblDateRunning.setSize(5,4);
        timeRunning.setSize(5,4);
        lblDateRunning.updateUI();
        timeRunning.updateUI();
		
		JLabel lblNewLabel = new JLabel("SIBAU  CAFE");
		lblNewLabel.setFont(new Font("Viner Hand ITC", Font.BOLD, 20));
		lblNewLabel.setBounds(219, 11, 145, 23);
		frmSiba.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("TOTAL PRICE : ");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2.setBounds(368, 356, 97, 14);
		frmSiba.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("0.00");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_3.setBounds(473, 356, 46, 14);
		frmSiba.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("* Please select the items from above.");
		lblNewLabel_4.setBounds(57, 355, 214, 14);
		frmSiba.getContentPane().add(lblNewLabel_4);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 77, 584, 2);
		frmSiba.getContentPane().add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 395, 584, 2);
		frmSiba.getContentPane().add(separator_1);
		
		JLabel lblNewLabel_5 = new JLabel("Order No. ");
		lblNewLabel_5.setFont(new Font("Tw Cen MT", Font.BOLD, 15));
		lblNewLabel_5.setBounds(52, 98, 77, 14);
		frmSiba.getContentPane().add(lblNewLabel_5);
		
		String order = "00" + ++orderNo;
		JLabel lblNewLabel_6 = new JLabel(order);
		lblNewLabel_6.setFont(new Font("Tw Cen MT", Font.BOLD, 15));
		lblNewLabel_6.setBounds(123, 98, 46, 14);
		frmSiba.getContentPane().add(lblNewLabel_6);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Student");
		rdbtnNewRadioButton.addActionListener(e -> {
			if(rdbtnNewRadioButton.isSelected()) {
				key = 2;
			}
		});
		buttonGroup.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.setSelected(true);
		rdbtnNewRadioButton.setBounds(52, 47, 77, 23);
		frmSiba.getContentPane().add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Staff");
		rdbtnNewRadioButton_1.addActionListener(e -> {
			if(rdbtnNewRadioButton_1.isSelected()) {
				key = 1;
			}
		});
		buttonGroup.add(rdbtnNewRadioButton_1);
		rdbtnNewRadioButton_1.setBounds(131, 47, 77, 23);
		frmSiba.getContentPane().add(rdbtnNewRadioButton_1);
		
		timeRunning.setBounds(453, 51, 121, 14);
		frmSiba.getContentPane().add(timeRunning);
		
		lblDateRunning.setBounds(429, 26, 145, 14);
		frmSiba.getContentPane().add(lblDateRunning);

		
		JButton btnNewButton = new JButton("Cancel");
		btnNewButton.addActionListener(e -> {
			int option = JOptionPane.showConfirmDialog(null, "Are You Sure?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (option == JOptionPane.YES_OPTION) {
						System.exit(0);
				}
		});
		btnNewButton.setBounds(52, 408, 89, 23);
		frmSiba.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Generate Token");
		btnNewButton_1.addActionListener(e -> {

			String curDate = dtString.replaceAll(" ", "");
			String curTime = currentTime.replaceAll(":", "");
			String fileName = curDate + "_" + curTime + "_Order" + orderNo;

			f = new File(fileName + ".txt");
			try {
				FileWriter fw = new FileWriter(f);
				BufferedWriter bw = new BufferedWriter(fw);

				bw.write("Order No. 00" + orderNo);
				bw.write("\n-------------------------------------------------------\n");
				bw.write("Date : " + dtString);
				bw.write("\nTime : " + currentTime);
				bw.write("\n-------------------------------------------------------\n");

				for(int i = 0; i < table.getRowCount(); i++) {
					//If quantity is not zero then write to file:
					if(Integer.parseInt(table.getModel().getValueAt(i, 0).toString()) != 0) {
						for(int j = 0; j < table.getColumnCount(); j++) {

							bw.write(table.getModel().getValueAt(i, j).toString() + "    ");
						}
						bw.write("\n");
					}
				}

				bw.write("\n-------------------------------------------------------");
				bw.write("\nTOTAL PRICE  :   " + lblNewLabel_3.getText());

				bw.close();
				fw.close();
				System.out.println("Data written to file...");
			}
			catch(IOException ioe) {
				ioe.printStackTrace();
			}

			//Add file to queue with key:
			q.enqueue(f, key);

			int time = 0; //in minutes
			if(key == 1) {
				time = 1;
			}
			else if(key == 2) {
				time = 2;
			}

			Timer timer = new Timer();
			TimerTask task = new TimerTask() {

				@Override
				public void run() {
					q.dequeue();
				}

			};
			timer.schedule(task, time * 60000L);

			new Receipt(f, orderNo, time).frmReceipt.setVisible(true);
			frmSiba.setVisible(false);
		});
		btnNewButton_1.setBounds(414, 408, 118, 23);
		frmSiba.getContentPane().add(btnNewButton_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(52, 123, 480, 222);
		frmSiba.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				double total = 0;
				int i = table.getSelectedRow();
				
				String quantity = JOptionPane.showInputDialog(null, "Enter the Quantity", 0);
				table.setValueAt(Objects.requireNonNullElse(quantity, 0), i, 0);
				
				double sum = Short.parseShort(table.getValueAt(i, 0).toString()) * Double.parseDouble(table.getValueAt(i, 2).toString());
				table.setValueAt(sum, i, 3);
				
				for(int r = 0; r < table.getRowCount(); r++) {
					total += Double.parseDouble(table.getValueAt(r, 3).toString());
				}
				
				lblNewLabel_3.setText(Double.toString(total));
			}
		});
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{(short) 0, "Chicken Biryani", 120, 0.0},
				{(short) 0, "Vegetable Biryani", 80, 0.0},
				{(short) 0, "Chicken Petes", 60, 0.0},
				{(short) 0, "Vegetable Petes", 40, 0.0},
				{(short) 0, "Chicken Roll", 50, 0.0},
				{(short) 0, "Vegetable Roll", 30, 0.0},
				{(short) 0, "Burger", 60, 0.0},
				{(short) 0, "Samosa", 12, 0.0},
				{(short) 0, "Pakorey", 20, 0.0},
				{(short) 0, "Banana Shake", 160, 0.0},
				{(short) 0, "Lemande", 120, 0.0},
				{(short) 0, "Fries", 40, 0.0},
			},
			new String[] {
				"Quantity", "Available Items", "Rate", "Price"
			}
		) {
			final Class[] columnTypes = new Class[] {
				Short.class, String.class, Integer.class, Double.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			final boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(193);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(3).setResizable(false);
		
	}
	
	public void run() {

        Thread thisThread = Thread.currentThread();

        while (clockThread == thisThread) {
            iterateTime();
            pause(); // for 1000 milliseconds
        }

        while (dateThread == thisThread) {
            iterateDate();
            pause(); // for 1000 milliseconds
        }
    }
	
	
	private void iterateTime() {
        // get the time and convert it to a date
        Calendar cal = Calendar.getInstance();
        java.util.Date date = cal.getTime();
        DateFormat dateFormatter = DateFormat.getTimeInstance();
        SimpleDateFormat dateFormatterH = new SimpleDateFormat("H:mm:ss");
        currentTime = dateFormatterH.format(date);
        timeRunning.setText("  Time : " + dateFormatter.format(date));
    }

    private void iterateDate() {
        // get the time and convert it to a date
        Calendar cal = Calendar.getInstance();
        java.util.Date date = cal.getTime();
        SimpleDateFormat dt = new SimpleDateFormat ("dd MMM yyyy '('EE')'");
        dtString = dt.format(date);
        lblDateRunning.setText("  Date : " + dtString);
    }
    
    private void pause() {
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }
}
