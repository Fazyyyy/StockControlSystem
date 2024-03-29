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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.AbstractDocument;
import javax.swing.ImageIcon;

public class ProductsPage{

	Connection con;
	Statement pst;
	ResultSet rs;
	
	PreparedStatement prt;
	PreparedStatement prt2;
	public boolean isser = false;
	public static Integer productid_ = 0;

	public JFrame ProductsPage;
	private JTextField ProductID;
	private JTextField ProductType;
	private JTextField ProductSize;
	private JTextField Quantity;
	private JTextField Price;
	private JTable ProductTable;
	private JTextField textField;
	private JTextField updateT;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductsPage frame = new ProductsPage();
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
					double price_ = rs.getDouble("Price");
					int quantity_ = rs.getInt("Quantity");
					 DecimalFormat df = new DecimalFormat("#,##0.00");
					 DecimalFormat df1 = new DecimalFormat("#,##0");
					 DecimalFormat df2 = new DecimalFormat("00000");
				     String formattedPrice = df.format(price_);
				     String formattedqty = df1.format(quantity_);
				     String formattedid = df2.format(id__);
					arry.add(formattedid);
					maxIDD.add(id__);
					arry.add(rs.getString("Description"));
					arry.add(rs.getString("ProductSize"));
					arry.add(formattedqty);
					arry.add(formattedPrice);
					
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
			int selectedRow = ProductTable.getSelectedRow();
			String type = "Delete";
			String desc = ProductTable.getValueAt(selectedRow, 1).toString();
			String unit= ProductTable.getValueAt(selectedRow, 2).toString();
			int currentQuantity = Integer.valueOf("" + ProductTable.getValueAt(selectedRow, 3));
			String price= ProductTable.getValueAt(selectedRow, 4).toString();
			prt2 = con.prepareStatement("INSERT INTO logindb.inventorylog(Date, Time,Type,Description,Unit, Quantity, Price) VALUES(?,?,?,?,?,?,?)");
			prt2.setDate(1, new java.sql.Date(System.currentTimeMillis()));
			prt2.setTime(2, new java.sql.Time(System.currentTimeMillis()));
			prt2.setString(3, type);
			prt2.setString(4, desc);
			prt2.setString(5, unit);
			prt2.setInt(6, currentQuantity);
			prt2.setString(7, price);
			prt2.executeUpdate();
		   con.close();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		};
	}


	public ProductsPage() {
        ProductsPage = new JFrame();
        ProductsPage.setResizable(false);
		ProductsPage.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 12));
		ProductsPage.setTitle("Classic Color Enterprises");
		ProductsPage.setBounds(100, 100, 925, 578);
		ProductsPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ProductsPage.setLocationRelativeTo(null);
		ProductsPage.setVisible(true);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4, 2));
		panel.setVisible(true);
		

		JLabel Product_TypeLabel = new JLabel("DESCRIPTION");
		Product_TypeLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		Product_TypeLabel.setBounds(10, 104, 115, 30);
		panel.add(Product_TypeLabel);
		

		ProductType = new JTextField();
		ProductType.setColumns(10);
		ProductType.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					ProductSize.requestFocus();
				}
			}
		});
		ProductType.setBounds(123, 104, 186, 25);
		panel.add(ProductType);
		
		
		JLabel Product_SizeLabel = new JLabel("UNIT");
		Product_SizeLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		Product_SizeLabel.setBounds(10, 145, 115, 30);
		panel.add(Product_SizeLabel);

		ProductSize = new JTextField();
		ProductSize.setColumns(10);
		ProductSize.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					Quantity.requestFocus();
				}
			}
		});
		ProductSize.setBounds(123, 145, 186, 25);
		panel.add(ProductSize);
		
		JLabel QuantityLabel = new JLabel("QUANTITY");
		QuantityLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		QuantityLabel.setBounds(10, 186, 115, 30);
		panel.add(QuantityLabel);
		

		Quantity = new JTextField();
		Quantity.setColumns(10);
		Quantity.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				}
			}
		});
		Quantity.setBounds(123, 191, 186, 25);
		panel.add(Quantity);
		
		
		JLabel PriceLabel = new JLabel("UNIT PRICE");
		PriceLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		PriceLabel.setBounds(10, 227, 89, 50);
		panel.add(PriceLabel);
		
		Price = new JTextField();
		Price.setColumns(10);
		Price.setBounds(122, 242, 186, 25);
		panel.add(Price);
		
		JButton AddB = new JButton("");
		AddB.setIcon(new ImageIcon("C:\\Users\\PC\\Downloads\\Add.png"));
		AddB.setBounds(35, 31, 40, 57);
		AddB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddProduct(panel);
			}
		});
		ProductsPage.getContentPane().setLayout(null);
		ProductsPage.getContentPane().add(AddB);
		
		ProductTable = new JTable();
		ProductTable.setModel(new DefaultTableModel(
			getTable(),
			new String[] {
					"Product Code", "Item Description", "Unit", "Quantity", "Unit Price"
			}
		));
		ProductTable.getColumnModel().getColumn(1).setPreferredWidth(87);
		ProductTable.getColumnModel().getColumn(2).setPreferredWidth(80);
		ProductTable.setBorder(new LineBorder(new Color(0, 0, 0)));
		JScrollPane scrollPane= new  JScrollPane(ProductTable);
		scrollPane.setBounds(35, 99, 864, 429);
		ProductsPage.getContentPane().add(scrollPane);
		
		JButton DeleteB = new JButton("");
		DeleteB.setIcon(new ImageIcon("C:\\Users\\PC\\Downloads\\Delete.png"));
		DeleteB.setBounds(85, 31, 40, 57);
		ProductsPage.getContentPane().add(DeleteB);
		
		DeleteB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						DefaultTableModel TableModel = (DefaultTableModel) ProductTable.getModel();
						
						if(ProductTable.getSelectedRowCount()== 1) {
							int warning = JOptionPane.showConfirmDialog(null, "Are you sure to delete?");
							if(warning == JOptionPane.YES_OPTION) {
								
						String value_ = (String) ProductTable.getValueAt(ProductTable.getSelectedRow(), 1);
						
						deleteRow(value_);
						ProductTable.setModel(new DefaultTableModel(
								getTable(), 
								new String[] {
										"Product Code", "Item Description", "Unit", "Quantity", "Unit Price"
								}
							));}
						}else {
						if(ProductTable.getRowCount()== 0) {
					JOptionPane.showMessageDialog(null, "Table is empty.");
					
				}else {
					JOptionPane.showMessageDialog(null, "Please select one product to delete.");
				}
			}}});
		
		JButton btnBack = new JButton("");
		btnBack.setBounds(859, 16, 40, 41);
		btnBack.setForeground(new Color(255, 255, 255));
		btnBack.setIcon(new ImageIcon("C:\\Users\\PC\\Downloads\\337078064_906823977277995_5747373366250644684_n.png"));
		ProductsPage.getContentPane().add(btnBack);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProductsPage.dispose();
				
				AdminPage ap = new AdminPage();
				ap.setVisible(true);
			}
		});
		ProductsPage.getContentPane().add(btnBack);
		
		textField = new JTextField();
		textField.setBounds(744, 68, 155, 20);
		ProductsPage.getContentPane().add(textField);
		textField.setColumns(10);
		
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
		
		JLabel Product_TypeLabel_1 = new JLabel(":");
		Product_TypeLabel_1.setIcon(new ImageIcon("C:\\Users\\PC\\Downloads\\Search.png"));
		Product_TypeLabel_1.setBounds(691, 61, 53, 31);
		Product_TypeLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		ProductsPage.getContentPane().add(Product_TypeLabel_1);
		
		updateT = new JTextField();
		updateT.setColumns(10);
		updateT.setBounds(431, 68, 155, 20);
		ProductsPage.getContentPane().add(updateT);
		
		JButton UpdateB = new JButton("Update");
		UpdateB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = ProductTable.getSelectedRow();
				
				
				if(selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Please select a product.", "Warning", JOptionPane.WARNING_MESSAGE);
					return;
				}
				int currentQuantity = Integer.valueOf("" + ProductTable.getValueAt(selectedRow, 3));
				int currentID = Integer.valueOf("" + ProductTable.getValueAt(selectedRow, 0));
				
				int increaseAmount = 1;
				if(!updateT.getText().equals("")) {
					increaseAmount = Integer.valueOf(updateT.getText());
				}
				updateT.setText("");
				ProductTable.setRowSelectionInterval(selectedRow, 0);
				
				int newQuantity = currentQuantity + increaseAmount;
				
				ProductTable.setValueAt (newQuantity, selectedRow, 3);
				
				try {
					con = Connections.getConnection();
					pst = con.createStatement();
					pst.executeUpdate("UPDATE logindb.products SET Quantity = '" + newQuantity + "' WHERE ProductID = '" + currentID + "';");
					String type = "Update";
					String desc = ProductTable.getValueAt(selectedRow, 1).toString();
					String unit= ProductTable.getValueAt(selectedRow, 2).toString();
					String price= ProductTable.getValueAt(selectedRow, 4).toString();
					prt2 = con.prepareStatement("INSERT INTO logindb.inventorylog(Date, Time,Type,Description,Unit, Quantity, Price) VALUES(?,?,?,?,?,?,?)");
					prt2.setDate(1, new java.sql.Date(System.currentTimeMillis()));
					prt2.setTime(2, new java.sql.Time(System.currentTimeMillis()));
					prt2.setString(3, type);
					prt2.setString(4, desc);
					prt2.setString(5, unit);
					prt2.setInt(6, increaseAmount);
					prt2.setString(7, price);
					prt2.executeUpdate();
					con.close();
				}
				catch(Exception ex) {
					ex.printStackTrace();
				}
				ProductTable.setModel(new DefaultTableModel(
						getTable(), 
						new String[] {
								"Product Code", "Item Description", "Unit", "Quantity", "Unit Price"
						}
					));   
			}
		});
		UpdateB.setBounds(324, 57, 97, 31);
		ProductsPage.getContentPane().add(UpdateB);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\PC\\Pictures\\BackGroundProdPAge.png"));
		lblNewLabel.setBounds(0, 0, 909, 539);
		ProductsPage.getContentPane().add(lblNewLabel);
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
							"Product Code", "Item Description", "Unit", "Quantity", "Unit Price"
					}
				));
 		}
 	}
     
     public void AddProduct(JPanel panel) {
    	 String prodType = "", prodSize = "";
    	 int quantity = 0;
    	 float price = 0.0f;
			
			
			while (true) {
				if (!Quantity.getText().equals("") && !Price.getText().equals("")) {
					prodType = ProductType.getText();
				
				prodSize = ProductSize.getText();
				quantity = Integer.parseInt(Quantity.getText());
				price = Float.parseFloat(Price.getText());
				}
			
			int stat = JOptionPane.showInternalOptionDialog(null, panel, "Add Product", JOptionPane.YES_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[] {"ADD"}, "ADD");
			System.out.println(stat);
			
			System.out.println(prodType);
			System.out.println(prodSize);
			System.out.println(quantity);
			System.out.println(price);
			
			prodType = ProductType.getText();
			prodSize = ProductSize.getText();
			quantity = Integer.parseInt(Quantity.getText());
			price = Float.parseFloat(Price.getText());
			
			
			if (stat == 0) {
				if (!(prodType.equals("") && prodSize.equals("") && Quantity.getText().equals("") && Price.getText().equals(""))) {
					System.out.println("YES");	
					DecimalFormat df2 = new DecimalFormat("00000");
				     String formattedid = df2.format(++productid_);
					String query = "insert into logindb.products values ('" + formattedid + "','" +
						prodType + "','" +
						prodSize + "','" +
						quantity + "','" +
						price + "');";
                    String type = "Add";

					try {
						con = Connections.getConnection();
						prt = con.prepareStatement("insert into logindb.products values (?, ?, ?, ?, ?);");
						prt.setString(1, formattedid);
						prt.setString(2, prodType);
						prt.setString(3, prodSize);
						prt.setInt(4, quantity);
						prt.setFloat(5, price);
						prt.executeUpdate(query);
						prt2 = con.prepareStatement("INSERT INTO logindb.inventorylog(Date, Time,Type,Description,Unit, Quantity, Price) VALUES(?,?,?,?,?,?,?)");
						prt2.setDate(1, new java.sql.Date(System.currentTimeMillis()));
						prt2.setTime(2, new java.sql.Time(System.currentTimeMillis()));
						prt2.setString(3, type);
						prt2.setString(4, prodType);
						prt2.setString(5, prodSize);
						prt2.setInt(6, quantity);
						prt2.setFloat(7, price);
						prt2.executeUpdate();
					   con.close();

					}
					catch(Exception ex) {
						ex.printStackTrace();
					}
					

					JOptionPane.showMessageDialog(null, "Product Added!!!");

				    ProductType.setText("");
				    ProductSize.setText("");
				    Quantity.setText("");
				    Price.setText("");
				    
				    panel.revalidate();
				    panel.repaint();
				    
					ProductTable.setModel(new DefaultTableModel(
						getTable(),
						new String[] {
								"Product Code", "Item Description", "Unit", "Quantity", "Unit Price"
						}
					));
					break;
				}
				
				else {
					JOptionPane.showMessageDialog(null, "Invalid input");
					continue;
				}
				
			} else {
				ProductType.setText("");
			    ProductSize.setText("");
			    Quantity.setText("");
			    Price.setText("");
			    
			    panel.revalidate();
			    panel.repaint();
			    
				break;
			}
     }
     }
}