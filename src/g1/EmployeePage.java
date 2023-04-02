package g1;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
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
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setBounds(100, 100, 400, 338);
setLocationRelativeTo(null);

contentPane = new JPanel();
contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
setContentPane(contentPane);
contentPane.setLayout(null);
JButton btnViewProducts = new JButton("View Products");
btnViewProducts.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
		ViewProduct VP = new ViewProduct();
		VP.setVisible(true);
		setVisible(false);

	}
});
btnViewProducts.setFont(new Font("Tahoma", Font.BOLD, 12));
btnViewProducts.setBounds(49, 87, 277, 35);
contentPane.add(btnViewProducts);
JLabel lblEmployeePage = new JLabel("EMPLOYEE PAGE");
lblEmployeePage.setFont(new Font("Tahoma", Font.BOLD, 20));
lblEmployeePage.setBounds(96, 47, 232, 27);
contentPane.add(lblEmployeePage);
JButton btnSell = new JButton("Sales");
btnSell.setFont(new Font("Tahoma", Font.BOLD, 12));
btnSell.setBounds(49, 133, 277, 35);
contentPane.add(btnSell);
JButton LogOutButton = new JButton("Log Out");
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
LogOutButton.setBounds(49, 179, 277, 35);
contentPane.add(LogOutButton);
}
}
