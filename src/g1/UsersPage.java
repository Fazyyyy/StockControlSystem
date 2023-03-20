package g1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.AbstractDocument;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.event.ActionEvent;


public class UsersPage {

	Connection con;
	Statement pst;
	ResultSet rs;
	
	PreparedStatement prt;
	
	private JFrame frame;
	private JTextField userTF;
	private JTextField passTF;
	private JTextField userTTF;
	private JTable table;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UsersPage window = new UsersPage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Object[][] getTable() {
		String query = "select * from logindb.loginsystem;";
		
		ArrayList<ArrayList<Object>> arryB = new ArrayList<ArrayList<Object>>();

		int rowCount = 1;
		try {
				con = Connections.getConnection();
				pst = con.createStatement();
				ResultSet rs = pst.executeQuery(query);
								
				while (rs.next()) {
					ArrayList<Object> arry = new ArrayList<Object>();
					
					arry.add(rs.getString("username"));
					arry.add(rs.getString("password"));
					arry.add(rs.getString("useroptions"));
					
					arryB.add(arry);
					
					rowCount++;
				}
			    
			   con.close();
			}
		catch(Exception ex) {
			ex.printStackTrace();
		}
        Object[][] mainobj = new Object[arryB.size()][5];
		
		for (int i = 0; i < arryB.size(); i++) {
			for (int j = 0; j < arryB.get(i).size(); j++) {
				mainobj[i][j] = arryB.get(i).get(j);
			}
		}
			
		return mainobj;
		}
	public void deleteRow(String row) {
		try {
			con = Connections.getConnection();
			pst = con.createStatement();
			pst.executeUpdate("DELETE FROM logindb.loginsystem WHERE username = '" + row + "';");

		   con.close();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		};
	}
	public UsersPage() {
		frame = new JFrame();
		frame.setBounds(100, 100, 735, 466);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 2));
		panel.setVisible(true);
		
		JLabel UsernameLabel = new JLabel("Username:");
		UsernameLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
		UsernameLabel.setBounds(28, 121, 110, 25);
	    panel.add(UsernameLabel);
		
		userTF = new JTextField();
		userTF.setColumns(10);
		userTF.setBounds(127, 121, 185, 27);
		panel.add(userTF);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblPassword.setBounds(28, 173, 110, 25);
		panel.add(lblPassword);
		
		passTF = new JPasswordField();
		passTF.setColumns(10);
		passTF.setBounds(127, 178, 185, 27);
		panel.add(passTF);
		
		JLabel lblUserType = new JLabel("User Type:");
		lblUserType.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblUserType.setBounds(28, 227, 110, 25);
		panel.add(lblUserType);
		
		userTTF = new JTextField();
		userTTF.setColumns(10);
		userTTF.setBounds(127, 232, 185, 27);
		panel.add(userTTF);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddUser(panel);
			}
		});
		btnAdd.setBounds(131, 11, 89, 41);
		frame.getContentPane().add(btnAdd);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.setBounds(239, 11, 89, 41);
		frame.getContentPane().add(btnRemove);
		btnRemove.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			DefaultTableModel TableModel = (DefaultTableModel) table.getModel();
			if(table.getSelectedRowCount()== 1) {
				int warning = JOptionPane.showConfirmDialog(null, "Are you sure to remove?");
				if(warning == JOptionPane.YES_OPTION) {
					
			String value_ = (String) table.getValueAt(table.getSelectedRow(), 0);
			deleteRow(value_);
			table.setModel(new DefaultTableModel(
					getTable(), 
					new String[] {
							"Username", "Password", "User Type"
					}
				));}
			}else {
			if(table.getRowCount()== 0) {
		JOptionPane.showMessageDialog(null, "There is no user to remove.");
		
	}else {
		JOptionPane.showMessageDialog(null, "Please select only one user to remove.");
	}
}}});

		
		table = new JTable();
		table.setModel(new DefaultTableModel(
				getTable(),
				new String[] {
						"Username", "Password", "User Type"
				}
			));
		table.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		table.setBounds(10, 63, 699, 344);
		JScrollPane scrollPane= new  JScrollPane(table);
		scrollPane.setBounds(10, 58, 701, 366);
		frame.getContentPane().add(scrollPane);
		
		JButton btnBack = new JButton("");
		btnBack.setBounds(10, 11, 54, 41);
		btnBack.setForeground(new Color(255, 255, 255));
		btnBack.setIcon(new ImageIcon("C:\\Users\\PC\\Downloads\\backarrow-removebg-preview.png"));
		frame.getContentPane().add(btnBack);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(554, 21, 155, 20);
		frame.getContentPane().add(textField);

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
		
		JLabel Product_TypeLabel_1 = new JLabel("SEARCH:");
		Product_TypeLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		Product_TypeLabel_1.setBounds(477, 17, 76, 25);
		frame.getContentPane().add(Product_TypeLabel_1);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				AdminPage ap = new AdminPage();
				ap.setVisible(true);
			}
		});
	}

	public void setVisible(boolean b) {
		
	}
	  public void filterRows(String input) {
	 		if (!input.equals("")) {
	 			DefaultTableModel model = (DefaultTableModel) table.getModel();
	 			TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);
	 			table.setRowSorter(sorter);
	 			sorter.setRowFilter(RowFilter.regexFilter("(?i).*" + input + ".*", 0, 1, 2));
	 		} else {
	 			table.setRowSorter(null);
	 			table.setModel(new DefaultTableModel(
						getTable(), 
						new String[] {
								"Username", "Password", "User Type"
						}
					));
	 		}
	 	}
    public void AddUser(JPanel panel) {
			
			
			while (true) {
				if (!userTF.getText().equals("") && !passTF.getText().equals("") && !userTTF.getText().equals("")) {
					String Username = userTF.getText();
			        String Password = passTF.getText();
			        String UserType = userTTF.getText();
			        
				}
			
			int stat = JOptionPane.showInternalOptionDialog(null, panel, "Add User", JOptionPane.YES_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[] {"ADD"}, "ADD");
			System.out.println(stat);
		
		
			String Username = userTF.getText();
	        String Password = passTF.getText();
	        String UserType = userTTF.getText();
			
			
			if (stat == 0) {
				if (!Username.equals("") && !Password.equals("") && !UserType.equals("")) {
					System.out.println("YES");	
					String query = "insert into logindb.loginsystem values ('" + Username + "','" +
						Password + "','" +
						UserType + "');";
					try {
						con = Connections.getConnection();
						prt = con.prepareStatement("insert into logindb.loginsystem values (?, ?, ?);");
						prt.setString(1, Username);
						prt.setString(2, Password);
						prt.setString(3, UserType);
						prt.executeUpdate(query);
					   con.close();

					}
					catch(Exception ex) {
						ex.printStackTrace();
					}
					

					JOptionPane.showMessageDialog(null, "User Registered!");

				    userTF.setText("");
				    passTF.setText("");
				    userTTF.setText("");
				    
				    panel.revalidate();
				    panel.repaint();
				    
					table.setModel(new DefaultTableModel(
						getTable(),
						new String[] {
								"Username", "Password", "User Type"
						}
					));
					break;
				}
				
				else {
					JOptionPane.showMessageDialog(null, "Invalid input");
					continue;
				}
				
			} else {
				  userTF.setText("");
				  passTF.setText("");
				  userTTF.setText("");
			    
			    panel.revalidate();
			    panel.repaint();
			    
				break;
			}
    }
    }
}
