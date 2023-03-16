package g1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UsersPage {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTable table;

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

	/**
	 * Create the application.
	 */
	public UsersPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 735, 466);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		JLabel ManageUserLabel = new JLabel("Manage Users");
		ManageUserLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		ManageUserLabel.setBounds(28, 43, 218, 46);
		frame.getContentPane().add(ManageUserLabel);
		
		JLabel UsernameLabel = new JLabel("Username:");
		UsernameLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
		UsernameLabel.setBounds(28, 121, 110, 25);
		frame.getContentPane().add(UsernameLabel);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblPassword.setBounds(28, 173, 110, 25);
		frame.getContentPane().add(lblPassword);
		
		JLabel lblUserType = new JLabel("User Type:");
		lblUserType.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblUserType.setBounds(28, 227, 110, 25);
		frame.getContentPane().add(lblUserType);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(127, 278, 89, 41);
		frame.getContentPane().add(btnAdd);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.setBounds(226, 278, 89, 41);
		frame.getContentPane().add(btnRemove);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(127, 121, 185, 27);
		frame.getContentPane().add(textField);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(127, 178, 185, 27);
		frame.getContentPane().add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(127, 232, 185, 27);
		frame.getContentPane().add(textField_2);
		
		table = new JTable();
		table.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		table.setBounds(338, 43, 339, 344);
		frame.getContentPane().add(table);
		
		JButton btnBack = new JButton("");
		btnBack.setBounds(10, 11, 54, 41);
		btnBack.setForeground(new Color(255, 255, 255));
		btnBack.setIcon(new ImageIcon("C:\\Users\\PC\\Downloads\\backarrow-removebg-preview.png"));
		frame.getContentPane().add(btnBack);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				AdminPage ap = new AdminPage();
				ap.setVisible(true);
			}
		});
	}

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}
}
