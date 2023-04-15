package g1;
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
public class EmployeePage extends JFrame {
private JPanel contentPane;
/**
* Launch the application.
*/
public static void main(String[] args) {
EventQueue.invokeLater(new Runnable() {
public void run() {
try {
EmployeePage frame = new EmployeePage();
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
public EmployeePage() {
	setTitle("Classic Color Enterprises");
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setBounds(100, 100, 400, 338);
setLocationRelativeTo(null);

contentPane = new JPanel();
contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
setContentPane(contentPane);
contentPane.setLayout(null);

JButton btnViewProducts = new JButton("View Products");
btnViewProducts.setBackground(new Color(222, 184, 135));
btnViewProducts.setBorder(BorderFactory.createCompoundBorder(
        new LineBorder(new Color(139, 69, 19), 2), 
        BorderFactory.createEmptyBorder(5, 10, 5, 10)));
btnViewProducts.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
		ViewProduct VP = new ViewProduct();
		VP.setVisible(true);
		setVisible(false);

	}
});
btnViewProducts.setFont(new Font("Tahoma", Font.BOLD, 12));
btnViewProducts.setBounds(65, 87, 250, 35);
contentPane.add(btnViewProducts);

JLabel lblEmployeePage = new JLabel("EMPLOYEE PAGE");
lblEmployeePage.setForeground(new Color(255, 250, 205));
lblEmployeePage.setFont(new Font("Tahoma", Font.BOLD, 20));
lblEmployeePage.setBounds(96, 47, 232, 27);
contentPane.add(lblEmployeePage);

JButton btnSell = new JButton("Sales");
btnSell.setBackground(new Color(222, 184, 135));
btnSell.setBorder(BorderFactory.createCompoundBorder(
        new LineBorder(new Color(139, 69, 19), 2), 
        BorderFactory.createEmptyBorder(5, 10, 5, 10)));
btnSell.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
		Sales sales = new Sales();
		sales.setVisible(true);
		setVisible(false);
	}
});
btnSell.setFont(new Font("Tahoma", Font.BOLD, 12));
btnSell.setBounds(65, 134, 250, 35);
contentPane.add(btnSell);

JButton LogOutButton = new JButton("Log Out");
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
LogOutButton.setBounds(65, 179, 249, 35);
contentPane.add(LogOutButton);
JLabel lblNewLabel_1 = new JLabel("");
lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\PC\\Pictures\\BackgroundAdminPage.png"));
lblNewLabel_1.setBounds(-2, 1, 384, 299);
contentPane.add(lblNewLabel_1);
}
}
