package g1;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


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
		lblNewLabel.setBounds(126, 42, 138, 27);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		contentPane.add(lblNewLabel);
		
		JButton ManageProduct = new JButton("Manage Products");
		ManageProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProductsPage prodpage = new ProductsPage();
				prodpage.setVisible(true);
				setVisible(false);
				
			}
		});
		ManageProduct.setFont(new Font("Tahoma", Font.BOLD, 12));
		ManageProduct.setBounds(53, 80, 277, 35);
		contentPane.add(ManageProduct);
		
		JButton AddEmployee = new JButton("Manage Users");
		AddEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UsersPage upage = new UsersPage();
				upage.setVisible(true);
				
			}
		});
		AddEmployee.setFont(new Font("Tahoma", Font.BOLD, 12));
		AddEmployee.setBounds(53, 126, 277, 35);
		contentPane.add(AddEmployee);
		
		final JButton LogOutButton = new JButton("Log Out");
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
		LogOutButton.setBounds(53, 172, 277, 35);
		contentPane.add(LogOutButton);
		
		
	}
}
