package g1;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.border.LineBorder;


public class AdminPage extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminPage frame = new AdminPage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	AdminPage() {
		setResizable(false);
		setTitle("Classic Color Enterprises");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 338);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ADMIN PAGE");
		lblNewLabel.setForeground(new Color(255, 250, 205));
		lblNewLabel.setBounds(94, 33, 217, 27);
		lblNewLabel.setFont(new Font("Segoe UI Black", Font.BOLD, 30));
		contentPane.add(lblNewLabel);
		
		JButton ManageProduct = new JButton("Manage Products");
		ManageProduct.setBackground(new Color(222, 184, 135));
		ManageProduct.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(139, 69, 19), 2), 
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		ManageProduct.setOpaque(true);
		ManageProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProductsPage prodpage = new ProductsPage();
				prodpage.setVisible(true);
				setVisible(false);
				
			}
		});
		ManageProduct.setFont(new Font("Tahoma", Font.BOLD, 12));
		ManageProduct.setBounds(78, 71, 233, 35);
		contentPane.add(ManageProduct);
		
		JButton AddEmployee = new JButton("Manage Users");
		AddEmployee.setBackground(new Color(222, 184, 135));
		AddEmployee.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(139, 69, 19), 2), 
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		AddEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UsersPage upage = new UsersPage();
				upage.setVisible(true);
				setVisible(false);
				
			}
		});
		AddEmployee.setFont(new Font("Tahoma", Font.BOLD, 12));
		AddEmployee.setBounds(78, 159, 233, 35);
		contentPane.add(AddEmployee);
		
		final JButton LogOutButton = new JButton("Log Out");
		LogOutButton.setBackground(new Color(222, 184, 135));
		LogOutButton.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(139, 69, 19), 2), 
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		LogOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int a = JOptionPane.showConfirmDialog(LogOutButton, "Are you sure?");
				if (a == JOptionPane.YES_OPTION) {
					
					Login login = new Login();
					login.setVisible(true);
					dispose();
					
				}
			}
		});
		LogOutButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		LogOutButton.setBounds(78, 251, 233, 35);
		contentPane.add(LogOutButton);
		
		
		JButton TransactionB = new JButton("Transaction Log");
		TransactionB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TransactionLog tl = new TransactionLog();
				tl.setVisible(true);
				setVisible(false);
			}
		});
		TransactionB.setFont(new Font("Tahoma", Font.BOLD, 12));
		TransactionB.setBorder(BorderFactory.createCompoundBorder(
		                new LineBorder(new Color(139, 69, 19), 2), 
		                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		TransactionB.setBackground(new Color(222, 184, 135));
		TransactionB.setBounds(78, 205, 233, 35);
		contentPane.add(TransactionB);
		
		JButton InventoryLogB = new JButton("Inventory Log");
		InventoryLogB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InventoryLog inventorylog = new InventoryLog();
				inventorylog.setVisible(true);
				setVisible(false);
			}
		});
		InventoryLogB.setOpaque(true);
		InventoryLogB.setFont(new Font("Tahoma", Font.BOLD, 12));
		InventoryLogB.setBorder(BorderFactory.createCompoundBorder(
		                new LineBorder(new Color(139, 69, 19), 2), 
		                BorderFactory.createEmptyBorder(5, 10, 5, 10)));		
		InventoryLogB.setBackground(new Color(222, 184, 135));
		InventoryLogB.setBounds(78, 113, 233, 35);
		contentPane.add(InventoryLogB);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\PC\\Pictures\\BackgroundAdminPage.png"));
		lblNewLabel_1.setBounds(0, 0, 384, 299);
		contentPane.add(lblNewLabel_1);
		
	
		
		
		
		
		
}
}
