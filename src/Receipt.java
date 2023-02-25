import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class Receipt {

	JFrame frmReceipt;
	
	private final int orderNo;
	private final int time;
	private final File f;
	

	/**
	 * Create the application.
	 */
	public Receipt(File f, int orderNo, int time) {
		this.f = f;
		this.orderNo = orderNo;
		this.time = time;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmReceipt = new JFrame();
		frmReceipt.setTitle("Receipt");
		frmReceipt.setBounds(400, 150, 500, 400);
		frmReceipt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmReceipt.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("TOKEN NO: ");
		lblNewLabel.setBounds(90, 11, 198, 53);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 30));
		frmReceipt.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("00" + orderNo);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Verdana", Font.BOLD, 30));
		lblNewLabel_1.setBounds(281, 11, 99, 53);
		frmReceipt.getContentPane().add(lblNewLabel_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(55, 76, 373, 206);
		frmReceipt.getContentPane().add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);
		
		try {
			Scanner sc = new Scanner(f);
			
			while (sc.hasNextLine())
				textArea.append(sc.nextLine() + "\n");
			
			textArea.append("\nYour Order will take approximately " + time + " minutes to complete.\nPlease wait in the Queue. Thank you! :)\n");
			sc.close();
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		JButton btnNewButton = new JButton("Cancel");
		btnNewButton.addActionListener(e -> {
			new MainFrame().frmSiba.setVisible(true);
			frmReceipt.setVisible(false);
		});
		btnNewButton.setBounds(54, 308, 89, 23);
		frmReceipt.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Print");
		btnNewButton_1.addActionListener(e -> {
			try {
				textArea.print();
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		btnNewButton_1.setBounds(338, 308, 89, 23);
		frmReceipt.getContentPane().add(btnNewButton_1);
	}
}
