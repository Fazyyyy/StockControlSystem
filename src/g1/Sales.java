package g1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextArea;
import javax.swing.JSpinner;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.SpinnerNumberModel;

public class Sales {

	private JFrame frame;
	private JTextField PcodeTF;
	private JTextField DescTF;
	private JTextField PriceTF;
	private JTextField AmountTF;
	private JTextField textField;
	private JTextField PayTF;
	private JTextField ChangeTF;
	private JTable table;

	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	private JTextField PsizeTF;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sales window = new Sales();
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
	public Sales() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Classic Color Enterprises");
		frame.setResizable(false);
		frame.setBounds(100, 100, 973, 574);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(139, 69, 19));
		panel.setBounds(10, 11, 623, 115);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel PCode = new JLabel("Product Code");
		PCode.setFont(new Font("Tahoma", Font.BOLD, 14));
		PCode.setBounds(27, 11, 100, 14);
		panel.add(PCode);
		
		JLabel Description = new JLabel("Description");
		Description.setFont(new Font("Tahoma", Font.BOLD, 14));
		Description.setBounds(148, 11, 85, 14);
		panel.add(Description);
		
		JLabel QuantityL = new JLabel("Quantity");
		QuantityL.setFont(new Font("Tahoma", Font.BOLD, 14));
		QuantityL.setBounds(27, 67, 71, 14);
		panel.add(QuantityL);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPrice.setBounds(271, 11, 41, 14);
		panel.add(lblPrice);
		
		JLabel lblTotal = new JLabel("Amount");
		lblTotal.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTotal.setBounds(386, 11, 100, 14);
		panel.add(lblTotal);
		
		PcodeTF = new JTextField();
		PcodeTF.setBackground(new Color(205, 133, 63));
		PcodeTF.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					String pcode = PcodeTF.getText();
					
					try {
						con = Connections.getConnection();
						pst = con.prepareStatement("SELECT * from logindb.products where ProductID = ?");
						
						pst.setString(1, pcode);
						rs = pst.executeQuery();
						
					    if(rs.next() == false) {
					    	JOptionPane.showMessageDialog(frame, "Product Code Not Found");
					    	
					    }
					    else {
					        String pname = rs.getString("Description");
					        String prodsize = rs.getString("ProductSize");
					        String price = rs.getInt("Price") + ".00" ;
					        
					        DescTF.setText(pname.trim());
					        PriceTF.setText(price.trim());
					        PsizeTF.setText(prodsize.trim());
					    }
					
					}
					catch(Exception ex) {
						ex.printStackTrace();
						
					}
					
					}
			}
		});
		PcodeTF.setBounds(27, 36, 105, 20);
		panel.add(PcodeTF);
		PcodeTF.setColumns(10);
		
		JSpinner QtyTF = new JSpinner();
		QtyTF.setModel(new SpinnerNumberModel(0, 0, 10000000, 1));
		QtyTF.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int price = Integer.parseInt(PriceTF.getText());
				int qty = Integer.parseInt(QtyTF.getValue().toString());
				
				int tot = price * qty;
				
				AmountTF.setText(String.valueOf(tot));
				
			}
		});
		QtyTF.setBounds(27, 92, 105, 20);
		panel.add(QtyTF);
		
		DescTF = new JTextField();
		DescTF.setColumns(10);
		DescTF.setBounds(148, 36, 105, 20);
		panel.add(DescTF);
		
		PriceTF = new JTextField();
		PriceTF.setColumns(10);
		PriceTF.setBounds(271, 36, 105, 20);
		panel.add(PriceTF);
		
		AmountTF = new JTextField();
		AmountTF.setColumns(10);
		AmountTF.setBounds(386, 36, 105, 20);
		
		panel.add(AmountTF);
		
		JButton AddBtn = new JButton("Add");
		AddBtn.setBounds(511, 79, 89, 23);
		panel.add(AddBtn);
		
		
		JLabel lblProductSize = new JLabel("Product Size");
		lblProductSize.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblProductSize.setBounds(148, 69, 100, 14);
		panel.add(lblProductSize);
		
		PsizeTF = new JTextField();
		PsizeTF.setColumns(10);
		PsizeTF.setBounds(148, 92, 105, 20);
		panel.add(PsizeTF);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(662, 11, 285, 215);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel Total = new JLabel("Total");
		Total.setFont(new Font("Tahoma", Font.BOLD, 14));
		Total.setBounds(117, 11, 48, 14);
		panel_1.add(Total);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(41, 36, 207, 20);
		panel_1.add(textField);
		
		JLabel Pay = new JLabel("Amount Received");
		Pay.setFont(new Font("Tahoma", Font.BOLD, 14));
		Pay.setBounds(84, 67, 121, 14);
		panel_1.add(Pay);
		
		PayTF = new JTextField();
		PayTF.setColumns(10);
		PayTF.setBounds(41, 92, 207, 20);
		panel_1.add(PayTF);
		
		JLabel lblChange = new JLabel("Change");
		lblChange.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblChange.setBounds(117, 123, 61, 14);
		panel_1.add(lblChange);
		
		ChangeTF = new JTextField();
		ChangeTF.setColumns(10);
		ChangeTF.setBounds(41, 148, 207, 20);
		panel_1.add(ChangeTF);
		
		JButton btnBill = new JButton("Bill");
		btnBill.setBounds(99, 179, 89, 23);
		panel_1.add(btnBill);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Product Code", "Description", "Product Size", "Quantity", "Price", "Amount"
			}
		));
		JScrollPane scrollPane= new  JScrollPane(table);
		scrollPane.setBounds(10, 135, 623, 358);
		frame.getContentPane().add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(662, 251, 285, 242);
		frame.getContentPane().add(textArea);
	
		
	}
}
