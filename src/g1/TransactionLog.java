package g1;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TransactionLog {
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
					TransactionLog window = new TransactionLog();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public Object[][] getTable() {
		String query = "select * from logindb.transactionlog;";
		
		ArrayList<ArrayList<Object>> arryB = new ArrayList<ArrayList<Object>>();

		int rowCount = 1;
		try {
				con = Connections.getConnection();
				pst = con.createStatement();
				ResultSet rs = pst.executeQuery(query);
								
				while (rs.next()) {
					ArrayList<Object> arry = new ArrayList<Object>();
					
					double price_ = rs.getDouble("Price");
					DecimalFormat df = new DecimalFormat("#,##0.00");
				    String formattedPrice = df.format(price_);
				    double amount___ = rs.getDouble("Price");
					String formattedAmount = df.format(amount___);
					arry.add(rs.getDate("Date"));
					arry.add(rs.getTime("Time"));
					arry.add(rs.getString("Customer"));
					arry.add(rs.getString("Description"));
					arry.add(rs.getString("Unit"));
					arry.add(rs.getInt("Quantity"));
					arry.add(formattedPrice);
					arry.add(formattedAmount);
					arry.add(rs.getString("Status"));
					
					arryB.add(arry);
					
					rowCount++;
				}
			    
			   con.close();
			}
		catch(Exception ex) {
			ex.printStackTrace();
		}
        Object[][] mainobj = new Object[arryB.size()][9];
		
		for (int i = 0; i < arryB.size(); i++) {
			for (int j = 0; j < arryB.get(i).size(); j++) {
				mainobj[i][j] = arryB.get(i).get(j);
			}
		}
			
		return mainobj;
		}

	/**
	 * Create the application.
	 */
	public TransactionLog() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1011, 450);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
				getTable(),
				new String[] {
						"Date", "Time", "Customer's Name", "Item Description", "Unit", "Quantity", "Unit Price", " Total Amount", "Status"
				}
			));
		table.setBounds(10, 44, 975, 325);
		JScrollPane scrollPane= new  JScrollPane(table);
		scrollPane.setBounds(10, 56, 975, 344);
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
		btnBack.setBounds(952, 11, 33, 34);
		frame.getContentPane().add(btnBack);
	}
	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}
}
