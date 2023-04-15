package g1;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InventoryLog{
	
	Connection con;
	Statement pst;
	ResultSet rs;
	
	PreparedStatement prt;


	private JFrame frame;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InventoryLog window = new InventoryLog();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public Object[][] getTable() {
		String query = "select * from logindb.inventorylog;";
		
		ArrayList<ArrayList<Object>> arryB = new ArrayList<ArrayList<Object>>();

		int rowCount = 1;
		try {
				con = Connections.getConnection();
				pst = con.createStatement();
				ResultSet rs = pst.executeQuery(query);
								
				while (rs.next()) {
					ArrayList<Object> arry = new ArrayList<Object>();
					 
					arry.add(rs.getString("Date"));
					arry.add(rs.getString("Time"));
					arry.add(rs.getString("Type"));
					arry.add(rs.getString("Description"));
					arry.add(rs.getString("Unit"));
					arry.add(rs.getInt("Quantity"));
					arry.add(rs.getBigDecimal("Price") + ".00");
					arryB.add(arry);
					
					rowCount++;
				}
			    
			   con.close();
			}
		catch(Exception ex) {
			ex.printStackTrace();
		}
        Object[][] mainobj = new Object[arryB.size()][7];
		
		for (int i = 0; i < arryB.size(); i++) {
			for (int j = 0; j < arryB.get(i).size(); j++) {
				mainobj[i][j] = arryB.get(i).get(j);
			}
		}
		return mainobj;
		}
	public InventoryLog() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Classic Color Enterprises");
		frame.setVisible(true);
		frame.setBounds(100, 100, 760, 469);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
				getTable(),
				new String[] {
						"Date", "Time", "Type", "Item Description", "Unit", "Quantity", "Unit Price"
				}
			));
		table.setBounds(10, 69, 724, 350);
		JScrollPane scrollPane= new  JScrollPane(table);
		scrollPane.setBounds(22, 61, 701, 358);
		frame.getContentPane().add(scrollPane);
		
		JButton btnBack = new JButton("");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminPage ap = new AdminPage();
				ap.setVisible(true);
				frame.setVisible(false);
			}
		});
		btnBack.setForeground(Color.WHITE);
		btnBack.setBounds(683, 11, 40, 41);
		frame.getContentPane().add(btnBack);

	}
	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}
}
