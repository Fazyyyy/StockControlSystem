package g1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Font;
import javax.swing.JScrollPane;

import java.awt.GridLayout;
import java.awt.Window;

import javax.swing.DefaultComboBoxModel;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.AbstractDocument;
import javax.swing.ImageIcon;
import java.awt.BorderLayout;

public class ViewProduct{

	Connection con;
	Statement pst;
	ResultSet rs;
	
	PreparedStatement prt;
	
	public boolean isser = false;
	public static Integer productid_ = 0;

	public JFrame ViewProduct;
	private JTextField ProductID;
	private JTextField ProductType;
	private JTextField ProductSize;
	private JTextField Quantity;
	private JTextField Price;
	private JTable ProductTable;
	private JTextField textField;
	private JLabel lblNewLabel_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewProduct frame = new ViewProduct();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}




	public Object[][] getTable() {
		String query = "select * from logindb.products;";
		
		ArrayList<ArrayList<Object>> arryB = new ArrayList<ArrayList<Object>>();
		ArrayList<Integer> maxIDD = new ArrayList<>();
		maxIDD.add(productid_);

		int rowCount = 1;
		try {
				con = Connections.getConnection();
				pst = con.createStatement();
				ResultSet rs = pst.executeQuery(query);
								
				while (rs.next()) {
					ArrayList<Object> arry = new ArrayList<Object>();
					
					int id__ = rs.getInt("ProductID");
					arry.add(String.format("%06d", id__));
					maxIDD.add(id__);
					arry.add(rs.getString("Description"));
					arry.add(rs.getString("ProductSize"));
					arry.add(rs.getInt("Quantity"));
					arry.add(rs.getInt("Price") + ".00");
					
					arryB.add(arry);
					
					rowCount++;
				}
			    
			   con.close();
			}
		catch(Exception ex) {
			ex.printStackTrace();
			
		}
		
		productid_ = Collections.max(maxIDD);
		System.out.println("maxIDD=" + productid_);
			System.out.println("ID=" + productid_ + " ROWCOUNT=" + rowCount);
		Object[][] mainobj = new Object[arryB.size()][5];
		
		for (int i = 0; i < arryB.size(); i++) {
			for (int j = 0; j < arryB.get(i).size(); j++) {
				mainobj[i][j] = arryB.get(i).get(j);
			}
		}
			
		return mainobj;
	}
	
	public ArrayList<String> getTable(String column) {
		String query = "select " + column + " from logindb.products;";
		
		ArrayList<String> arryB = new ArrayList<String>();
		arryB.add("Select Product");

		try {
				con = Connections.getConnection();
				pst = con.createStatement();
				ResultSet rs = pst.executeQuery(query);
								
				while (rs.next()) {
					arryB.add(rs.getString("Description"));
				}
			    
			   con.close();
			}
			catch(Exception ex) {
				
			};
			
		return arryB;
	}
	
	public void deleteRow(String row) {
		try {
			con = Connections.getConnection();
			pst = con.createStatement();
			pst.executeUpdate("DELETE FROM logindb.products WHERE Description = '" + row + "';");

		   con.close();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		};
	}


	public ViewProduct() {
        ViewProduct = new JFrame();
        ViewProduct.setResizable(false);
		ViewProduct.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 12));
		ViewProduct.setTitle("Classic Color Enterprises");
		ViewProduct.setBounds(100, 100, 925, 578);
		ViewProduct.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ViewProduct.setLocationRelativeTo(null);
		ViewProduct.setVisible(true);
		
		
		ProductTable = new JTable();
		ProductTable.setModel(new DefaultTableModel(
			getTable(),
			new String[] {
					"Product Code", "Item Description", "Product Size", "Quantity", "Price"
			}
		));
		ProductTable.getColumnModel().getColumn(1).setPreferredWidth(87);
		ProductTable.getColumnModel().getColumn(2).setPreferredWidth(80);
		ViewProduct.getContentPane().setLayout(null);
		ProductTable.setBorder(new LineBorder(new Color(0, 0, 0)));
		JScrollPane scrollPane= new  JScrollPane(ProductTable);
		scrollPane.setBounds(35, 68, 864, 460);
		ViewProduct.getContentPane().add(scrollPane);
		
		JButton btnBack = new JButton("");
		btnBack.setBounds(859, 16, 40, 41);
		btnBack.setForeground(new Color(255, 255, 255));
		btnBack.setIcon(new ImageIcon("C:\\Users\\PC\\Downloads\\337078064_906823977277995_5747373366250644684_n.png"));
		ViewProduct.getContentPane().add(btnBack);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewProduct.dispose();
				
				EmployeePage EP = new EmployeePage();
				EP.setVisible(true);
			}
		});
		ViewProduct.getContentPane().add(btnBack);
		
		JLabel lblNewLabel = new JLabel("Search:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(594, 32, 66, 14);
		ViewProduct.getContentPane().add(lblNewLabel);
		
		
		
		textField = new JTextField();
		textField.setBounds(660, 26, 193, 31);
		ViewProduct.getContentPane().add(textField);
		textField.setColumns(10);
		
		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\PC\\Pictures\\BackGroundProdPAge.png"));
		lblNewLabel_1.setBounds(0, 0, 909, 539);
		ViewProduct.getContentPane().add(lblNewLabel_1);
		
		
		
		((AbstractDocument)textField.getDocument()).addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				String inputChar = textField.getText();
				filterRows(inputChar);
				
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				insertUpdate(e);
				
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}

     public void setVisible(boolean b) {
	// TODO Auto-generated method stub
	
     }
     
     public void filterRows(String input) {
 		if (!input.equals("")) {
 			DefaultTableModel model = (DefaultTableModel) ProductTable.getModel();
 			TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);
 			ProductTable.setRowSorter(sorter);
 			sorter.setRowFilter(RowFilter.regexFilter("(?i).*" + input + ".*", 0, 1, 2));
 		} else {
 			ProductTable.setRowSorter(null);
 			ProductTable.setModel(new DefaultTableModel(
					getTable(), 
					new String[] {
							"Product Code", "Item Description", "Product Size", "Quantity", "Price"
					}
				));
 		}
 	}
}