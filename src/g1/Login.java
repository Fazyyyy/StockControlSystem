package g1;
import java.awt.Component;
import java.awt.EventQueue;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.Color;


public class Login{

	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	
	private JFrame frame;
	private JTextField UserF;
	private JPasswordField PassF;
	protected Component rootPane;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
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
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.setResizable(false);
		frame.setTitle("Classic Color Enterprises");
		frame.setBounds(100, 100, 394, 311);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.getContentPane().setLayout(null);
		
		
		JLabel LoginPage = new JLabel("STOCK CONTROL SYSTEM");
		LoginPage.setHorizontalAlignment(SwingConstants.CENTER);
		LoginPage.setFont(new Font("Tahoma", Font.BOLD, 20));
		LoginPage.setBounds(35, 26, 296, 41);
		frame.getContentPane().add(LoginPage);
		
		JLabel User = new JLabel("USERNAME:");
		User.setFont(new Font("Tahoma", Font.BOLD, 14));
		User.setBounds(21, 81, 92, 30);
		frame.getContentPane().add(User);
		
		JLabel Pass = new JLabel("PASSWORD:");
		Pass.setFont(new Font("Tahoma", Font.BOLD, 14));
		Pass.setBounds(21, 122, 92, 30);
		frame.getContentPane().add(Pass);
		
		JLabel UserT = new JLabel("USER TYPE:");
		UserT.setFont(new Font("Tahoma", Font.BOLD, 14));
		UserT.setBounds(21, 163, 92, 30);
		frame.getContentPane().add(UserT);
		
		UserF = new JTextField();
		UserF.setBounds(123, 81, 211, 30);
		frame.getContentPane().add(UserF);
		UserF.setColumns(10);
		UserF.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e_) {
				UserF.transferFocus();
			}
		});
		
		PassF = new JPasswordField();
		PassF.setBounds(123, 122, 211, 30);
		PassF.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e_) {
				PassF.transferFocus();
			}
		});
		
		frame.getContentPane().add(PassF);

		final JButton LoginB = new JButton("LOGIN");
		frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0),"clickButton");

		frame.getRootPane().getActionMap().put("clickButton",new AbstractAction(){
				        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

						public void actionPerformed(ActionEvent ae) {
				    LoginB.doClick();
				    System.out.println("button clicked");
				        }
				    });
		
		final JComboBox UserType = new JComboBox();
		UserType.setFont(new Font("Tahoma", Font.PLAIN, 13));
		UserType.setEditable(true);
		UserType.setModel(new DefaultComboBoxModel(new String[] {"Select", "Admin", "Employee"}));
		UserType.setBounds(123, 165, 211, 30);
		UserType.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e_) {
				LoginB.requestFocus();
			}
		});
		
		frame.getContentPane().add(UserType);
		
		LoginB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String uname = UserF.getText();
				String pword = PassF.getText();
				String option = UserType.getSelectedItem().toString();
				
				if(uname.equals("")||pword.equals("")||option.equals("Select")) {
					JOptionPane.showMessageDialog((Component) rootPane, "Some Fields Are Empty","Error",1);
				}else {
					try{
						con = Connections.getConnection();
						pst = con.prepareStatement("select * from loginsystem where username=? and password=?");
						pst.setString(1, uname);
						pst.setString(2, pword);
					    rs = pst.executeQuery();
					if(rs.next()) {
						String s1 = rs.getString("useroptions");
						String un = rs.getString("username");
						if(option.equalsIgnoreCase("Admin")&& s1.equalsIgnoreCase("admin")) {
							AdminPage ad = new AdminPage();
							ad.setVisible(true);
							frame.dispose();
						}
							if(option.equalsIgnoreCase("Employee")&& s1.equalsIgnoreCase("Employee")) {
								EmployeePage ep = new EmployeePage ();
								ep.setVisible(true);
								frame.setVisible(true);
							}	}
						else {
							JOptionPane.showMessageDialog((Component) rootPane, "Username or Password is incorrect","Error",1);
						}
					}
					catch(Exception ex) {
						System.out.println(""+ex);
					}
					
				}
			}
		});
		LoginB.setFont(new Font("Tahoma", Font.BOLD, 14));
		LoginB.setBounds(151, 206, 154, 30);
		frame.getContentPane().add(LoginB);
		
		
	}

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}

}

