package g1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JTextArea;
import javax.swing.JSpinner;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ChangeEvent;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Sales {

	private JFrame frame;
	private JTextField PcodeTF;
	private JTextField DescTF;
	private JTextField PriceTF;
	private JTextField totalTF;
	private JTextField PayTF;
	private JTextField ChangeTF;
	private JTable table;
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	DefaultTableModel df;
	private JTextField PsizeTF;
	private JTextField QtyTF;
	private JButton voidB;

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

	
	public void purchase(){
		
		 int quantity = Integer.parseInt(QtyTF.getText());
	        double unitPrice = Double.parseDouble(PriceTF.getText());

	        // Calculate the total price and set it in the total price field
	        double totalPrice = quantity * unitPrice;
	        String formattedTotalPrice = String.format("%.2f", totalPrice);
	        
		df = (DefaultTableModel)table.getModel();
		df.addRow(new Object[] {
				
				PcodeTF.getText(),
				DescTF.getText(),
		        PsizeTF.getText(),
		        QtyTF.getText(),
		        PriceTF.getText(),
		        formattedTotalPrice
		        
		});

		double sum1 = 0;
	    for (int i = 0; i < table.getRowCount(); i++) {
	    	String amountStr = (String) table.getValueAt(i, 5);
	    	double amount = Double.parseDouble(amountStr);
	       sum1 += amount;
	    }
	    System.out.println("sum1=" + sum1);
	    String formattedSum1 = String.format("%.2f", sum1);
	    totalTF.setText(formattedSum1);
		
		PcodeTF.setText("");
		DescTF.setText("");
        PsizeTF.setText("");
        QtyTF.setText("");
        PriceTF.setText("");
        

}

	public void change() {
		double total = Double.parseDouble(totalTF.getText());
		int pay = Integer.parseInt(PayTF.getText());
		
		double bal = pay - total;
		String bal1 = String.format("%.2f", bal);
		
		ChangeTF.setText(bal1);
	}
	public void savetransaction() {
		df = (DefaultTableModel)table.getModel();
		List<String> allStr = new ArrayList<String>();
		List<Integer> allQuan = new ArrayList<Integer>();
		List<Integer> allID = new ArrayList<Integer>();
		float total = 0.00f;
		
	     for (int i = 0; i < df.getRowCount(); i++) {
	    	 int pcode = Integer.parseInt("" + df.getValueAt(i, 0));
	    	 allID.add(pcode);
	    	 int quantity = Integer.parseInt("" + df.getValueAt(i, 3));
	    	 allQuan.add(quantity);
	    	 total += Float.parseFloat("" + df.getValueAt(i, 5));
	    	 
	    	 allStr.add("UPDATE logindb.products SET Quantity = %d WHERE ProductID = %d;");
	    	 
	     }
	     try {
	    	 con = Connections.getConnection();
			 Statement pst = con.createStatement();
			 
			 for(int j = 0; j< allQuan.size(); j++) {
				 ResultSet rs = pst.executeQuery("SELECT * FROM logindb.products WHERE ProductID = " + allID.get(j)+";");
				 rs.next();
				 int oldQuan = rs.getInt("Quantity");
				 System.out.println(oldQuan);
				 
				 String query = String.format(allStr.get(j), oldQuan - allQuan.get(j), rs.getInt("ProductID"));
				 System.out.println(allQuan.get(j));
				 System.out.println(query);
				 pst.executeUpdate(query);
			 }
	    	 
	     }catch (SQLException e) {
	 		// TODO Auto-generated catch block
	 		e.printStackTrace();
	 	}
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
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setBounds(100, 100, 1013, 764);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JFrame billframe = new JFrame();
		billframe.setTitle("Classic Color Enterprises");
		billframe.setResizable(false);
		billframe.setBounds(100, 100, 320, 764);
		billframe.setLocationRelativeTo(null);
		billframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		billframe.getContentPane().setLayout(null);
		
		JLabel PCode = new JLabel("Product Code:");
		PCode.setForeground(new Color(204, 204, 0));
		PCode.setBounds(22, 50, 111, 14);
		PCode.setFont(new Font("Tahoma", Font.BOLD, 14));
		frame.getContentPane().add(PCode);
		

		PcodeTF = new JTextField();
		PcodeTF.setBounds(143, 49, 207, 20);
		PcodeTF.setBackground(new Color(255, 255, 255));
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
					        QtyTF.requestFocus();
					       
					    }
					
					}
					catch(Exception ex) {
						ex.printStackTrace();
						
					}
					
					}
			}
		});
		frame.getContentPane().add(PcodeTF);
		PcodeTF.setColumns(10);
		
		JLabel Description = new JLabel("Item \r\nDescription:");
		Description.setForeground(new Color(204, 204, 0));
		Description.setBounds(22, 93, 139, 14);
		Description.setFont(new Font("Tahoma", Font.BOLD, 14));
		frame.getContentPane().add(Description);
		
		DescTF = new JTextField();
		DescTF.setBounds(143, 92, 207, 20);
		DescTF.setColumns(10);
		frame.getContentPane().add(DescTF);
		
		JLabel QuantityL = new JLabel("Quantity:");
		QuantityL.setForeground(new Color(204, 204, 0));
		QuantityL.setBounds(22, 138, 71, 14);
		QuantityL.setFont(new Font("Tahoma", Font.BOLD, 14));
		frame.getContentPane().add(QuantityL);
		
		JButton AddB = new JButton("Add");
		AddB.setBounds(152, 410, 89, 23);
		AddB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				purchase();
				
		    	
			}
		});
		frame.getContentPane().add(AddB);
		
		
		QtyTF = new JTextField();
		QtyTF.setBounds(143, 137, 207, 20);
		QtyTF.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					AddB.doClick();
					PcodeTF.requestFocus();
				}
			}
		});
		frame.getContentPane().add(QtyTF);
		QtyTF.setColumns(10);
	
		JLabel lblPrice = new JLabel("Unit Price:");
		lblPrice.setForeground(new Color(204, 204, 0));
		lblPrice.setBounds(22, 220, 111, 14);
		lblPrice.setFont(new Font("Tahoma", Font.BOLD, 14));
		frame.getContentPane().add(lblPrice);

		PriceTF = new JTextField();
		PriceTF.setBounds(143, 219, 207, 20);
		PriceTF.setColumns(10);
		frame.getContentPane().add(PriceTF);
		
			
		JLabel lblProductSize = new JLabel("Unit:");
		lblProductSize.setForeground(new Color(204, 204, 0));
		lblProductSize.setBounds(22, 180, 100, 14);
		lblProductSize.setFont(new Font("Tahoma", Font.BOLD, 14));
		frame.getContentPane().add(lblProductSize);
		
		PsizeTF = new JTextField();
		PsizeTF.setBounds(143, 179, 207, 20);
		PsizeTF.setColumns(10);
		frame.getContentPane().add(PsizeTF);
		
		JLabel Total = new JLabel("Total");
		Total.setForeground(new Color(204, 204, 0));
		Total.setBounds(22, 281, 48, 14);
		Total.setFont(new Font("Tahoma", Font.BOLD, 14));
		frame.getContentPane().add(Total);
		
		totalTF = new JTextField();
		totalTF.setBounds(143, 280, 207, 20);
		totalTF.setColumns(10);
		frame.getContentPane().add(totalTF);
		
		JTextPane BillP = new JTextPane();
		BillP.setBounds(7, 11, 291, 764);
		BillP.setFont(new Font("Times New Roman", Font.BOLD, 11));
		billframe.getContentPane().add(BillP);
	
		JButton btnBill = new JButton("Print");
		btnBill.setBounds(120, 468, 89, 23);
		btnBill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				change();
				savetransaction();
				BillP.setText("*************************************************\n");
				BillP.setText(BillP.getText() + "                       Classic Color Enterprises\n");
				BillP.setText(BillP.getText() + "        A.S Fortuna St., Banilad, Mandaue City 6014\n");
				BillP.setText(BillP.getText() + "                            Telefax: 238-3899\n");
				BillP.setText(BillP.getText() + "                  Gerry Glen Y. Saw - Proprietor\n");
				BillP.setText(BillP.getText() + "************************************************\n");
				BillP.setText(BillP.getText() + "Description\tSize\tQuantity\tPrice\n");
				
				df = (DefaultTableModel)table.getModel();
				for (int i = 0; i < table.getRowCount(); i++) {
				
				String desc = df.getValueAt(i, 1).toString(); 
				String size = df.getValueAt(i, 2).toString(); 
				String qty = df.getValueAt(i, 3).toString(); 
				String price = df.getValueAt(i, 4).toString();
				
				
				BillP.setText(BillP.getText() + desc + "\t" + size + "\t"+ qty +"\t"+ price + "\n");
				}
				BillP.setText(BillP.getText() + "************************************************\n");
				BillP.setText(BillP.getText() + "Total:\t\t\t" + totalTF.getText() + "\n");
				BillP.setText(BillP.getText() + "Payment Received:\t\t\t" + PayTF.getText() + ".00\n");
				BillP.setText(BillP.getText() + "Balance:\t\t\t" + ChangeTF.getText() + "\n");
				billframe.setVisible(true);
			}
			});
		frame.getContentPane().add(btnBill);
		
		
		
		JLabel Pay = new JLabel("Cash");
		Pay.setForeground(new Color(204, 204, 0));
		Pay.setBounds(22, 313, 71, 14);
		Pay.setFont(new Font("Tahoma", Font.BOLD, 14));
		frame.getContentPane().add(Pay);
		
		PayTF = new JTextField();
		PayTF.setBounds(143, 311, 207, 20);
		PayTF.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				 btnBill.doClick();
				}
			}
		});
		PayTF.setColumns(10);
		frame.getContentPane().add(PayTF);
		
		
		JLabel lblChange = new JLabel("Change");
		lblChange.setForeground(new Color(204, 204, 0));
		lblChange.setBounds(22, 347, 61, 14);
		lblChange.setFont(new Font("Tahoma", Font.BOLD, 14));
		frame.getContentPane().add(lblChange);
		
		ChangeTF = new JTextField();
		ChangeTF.setBounds(143, 347, 207, 20);
		ChangeTF.setColumns(10);
		frame.getContentPane().add(ChangeTF);
		
		
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Product Code", "Description", "Product Size", "Quantity", "Price", "Amount"
			}
		));
		JScrollPane scrollPane= new  JScrollPane(table);
		scrollPane.setBounds(364, 11, 623, 703);
		frame.getContentPane().add(scrollPane);
		
		voidB = new JButton("Void");
		voidB.setBounds(22, 389, 89, 23);
		voidB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				df = (DefaultTableModel)table.getModel();
				int selectedRow = table.getSelectedRow();
		        if (selectedRow != -1) {
		        	int warning = JOptionPane.showConfirmDialog(null, "Are you sure to delete?");
					if(warning == JOptionPane.YES_OPTION) {
		            df.removeRow(selectedRow);
		            double sum1 = 0;
		    	    for (int i = 0; i < table.getRowCount(); i++) {
		    	    	String amountStr = (String) table.getValueAt(i, 5);
		    	    	double amount = Double.parseDouble(amountStr);
		    	       sum1 += amount;
		    	    }
		    	    System.out.println("sum1=" + sum1);
		    	    String formattedSum1 = String.format("%.2f", sum1);
		    	    totalTF.setText(formattedSum1);
					}
		        }
			}
		});
		frame.getContentPane().add(voidB);
		
		JButton btnNew = new JButton("New");
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				df = (DefaultTableModel) table.getModel();
		        df.setRowCount(0);
			}
		});
		btnNew.setBounds(261, 376, 89, 23);
		frame.getContentPane().add(btnNew);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setForeground(new Color(204, 204, 0));
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\PC\\Pictures\\BackGroundProdPAge.png"));
		lblNewLabel_1.setBounds(-3, 0, 960, 539);
		frame.getContentPane().add(lblNewLabel_1);
		
		
	
		
	}


	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}
}
