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
		frame.setBounds(100, 100, 973, 574);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		JLabel PCode = new JLabel("Product Code");
		PCode.setBounds(27, 11, 100, 14);
		PCode.setFont(new Font("Tahoma", Font.BOLD, 14));
		frame.getContentPane().add(PCode);
		

		PcodeTF = new JTextField();
		PcodeTF.setBounds(27, 36, 105, 20);
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
		
		JLabel Description = new JLabel("Description");
		Description.setBounds(148, 11, 85, 14);
		Description.setFont(new Font("Tahoma", Font.BOLD, 14));
		frame.getContentPane().add(Description);
		
		DescTF = new JTextField();
		DescTF.setBounds(148, 36, 105, 20);
		DescTF.setColumns(10);
		frame.getContentPane().add(DescTF);
		
		JLabel QuantityL = new JLabel("Quantity");
		QuantityL.setBounds(27, 67, 71, 14);
		QuantityL.setFont(new Font("Tahoma", Font.BOLD, 14));
		frame.getContentPane().add(QuantityL);
		
		JButton AddB = new JButton("Add");
		AddB.setBounds(413, 35, 89, 23);
		AddB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				purchase();
				
		    	
			}
		});
		frame.getContentPane().add(AddB);
		
		
		QtyTF = new JTextField();
		QtyTF.setBounds(27, 92, 105, 20);
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
	
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setBounds(271, 11, 41, 14);
		lblPrice.setFont(new Font("Tahoma", Font.BOLD, 14));
		frame.getContentPane().add(lblPrice);

		PriceTF = new JTextField();
		PriceTF.setBounds(271, 36, 105, 20);
		PriceTF.setColumns(10);
		frame.getContentPane().add(PriceTF);
		
			
		JLabel lblProductSize = new JLabel("Product Size");
		lblProductSize.setBounds(148, 69, 100, 14);
		lblProductSize.setFont(new Font("Tahoma", Font.BOLD, 14));
		frame.getContentPane().add(lblProductSize);
		
		PsizeTF = new JTextField();
		PsizeTF.setBounds(148, 92, 105, 20);
		PsizeTF.setColumns(10);
		frame.getContentPane().add(PsizeTF);
		
		JLabel Total = new JLabel("Total");
		Total.setBounds(781, 23, 48, 14);
		Total.setFont(new Font("Tahoma", Font.BOLD, 14));
		frame.getContentPane().add(Total);
		
		totalTF = new JTextField();
		totalTF.setBounds(699, 49, 207, 20);
		totalTF.setColumns(10);
		frame.getContentPane().add(totalTF);
		
		JTextPane BillP = new JTextPane();
		BillP.setBounds(656, 218, 291, 276);
		BillP.setFont(new Font("Times New Roman", Font.BOLD, 11));
		frame.getContentPane().add(BillP);
	
		JButton btnBill = new JButton("Print");
		btnBill.setBounds(768, 193, 89, 23);
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
			}
			});
		frame.getContentPane().add(btnBill);
		
		
		
		JLabel Pay = new JLabel("Payment");
		Pay.setBounds(768, 81, 71, 14);
		Pay.setFont(new Font("Tahoma", Font.BOLD, 14));
		frame.getContentPane().add(Pay);
		
		PayTF = new JTextField();
		PayTF.setBounds(699, 106, 207, 20);
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
		lblChange.setBounds(768, 137, 61, 14);
		lblChange.setFont(new Font("Tahoma", Font.BOLD, 14));
		frame.getContentPane().add(lblChange);
		
		ChangeTF = new JTextField();
		ChangeTF.setBounds(699, 162, 207, 20);
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
		scrollPane.setBounds(10, 135, 623, 358);
		frame.getContentPane().add(scrollPane);
		
		voidB = new JButton("Void");
		voidB.setBounds(512, 35, 89, 23);
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
		
		
	
		
	}


	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}
	
}
