package g1;
import java.awt.Component;
import java.awt.EventQueue;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Image;
import java.awt.*;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JSeparator;
import javax.swing.JLayeredPane;
import javax.swing.UIManager;


public class Login{

	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	
	private JFrame frame;
	private JTextField UserF;
	private JPasswordField PassF;
	protected Component rootPane;
	private ImageIcon login1;
	

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
		frame.getContentPane().setBackground(Color.CYAN);
		frame.setResizable(false);
		frame.setTitle("Classic Color Enterprises");
		frame.setBounds(100, 100, 396, 472);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(new Color(139, 69, 19));
		

		
		JLabel LoginPage = new JLabel("");
		LoginPage.setIcon(new ImageIcon("C:\\Users\\PC\\Pictures\\Classic Colors.png"));
		LoginPage.setHorizontalAlignment(SwingConstants.CENTER);
		LoginPage.setFont(new Font("Tahoma", Font.BOLD, 20));
		LoginPage.setBounds(35, 37, 303, 110);
		frame.getContentPane().add(LoginPage);
		
		JLabel User_1 = new JLabel("\r\n");
		User_1.setIcon(new ImageIcon("C:\\Users\\PC\\Pictures\\usericon.png"));
		User_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		User_1.setBounds(54, 204, 29, 30);
		frame.getContentPane().add(User_1);
		
		JLabel User = new JLabel("");
		User.setIcon(new ImageIcon("C:\\Users\\PC\\Downloads\\Username.png"));
		User.setFont(new Font("Tahoma", Font.BOLD, 14));
		User.setBounds(87, 206, 113, 30);
		frame.getContentPane().add(User);
		
		
		JLabel UserT = new JLabel("");
		UserT.setIcon(new ImageIcon("C:\\Users\\PC\\Downloads\\Select.png"));
		UserT.setFont(new Font("Tahoma", Font.BOLD, 14));
		UserT.setBounds(90, 312, 92, 30);
		frame.getContentPane().add(UserT);
		
		UserF = new JTextField();
		UserF.setFont(new Font("Times New Roman", Font.BOLD, 16));
		UserF.setBounds(86, 204, 212, 32);
		frame.getContentPane().add(UserF);
		UserF.setColumns(10);
		UserF.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e_) {
				UserF.transferFocus();
			}
		});
		JLabel Pass = new JLabel("");
		Pass.setIcon(new ImageIcon("C:\\Users\\PC\\Downloads\\lABEL2.png"));
		Pass.setFont(new Font("Tahoma", Font.BOLD, 14));
		Pass.setBounds(90, 265, 92, 21);
		frame.getContentPane().add(Pass);
		
		JLabel PassIcon = new JLabel("\r\n");
		PassIcon.setIcon(new ImageIcon("C:\\Users\\PC\\Pictures\\passicon.png"));
		PassIcon.setFont(new Font("Tahoma", Font.BOLD, 14));
		PassIcon.setBounds(54, 257, 23, 31);
		frame.getContentPane().add(PassIcon);
		
		PassF = new JPasswordField();
		PassF.setBounds(87, 258, 211, 32);
		PassF.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e_) {
				PassF.transferFocus();
			}
		});
		
		frame.getContentPane().add(PassF);

		final JButton LoginB = new JButton("");
		LoginB.setBackground(new Color(0,0,0,0));
		LoginB.setBorderPainted(false);
		LoginB.setForeground(new Color(255, 255, 255));
		LoginB.setIcon(new ImageIcon("C:\\Users\\PC\\Pictures\\LoginButton.png"));
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
		UserType.setFont(new Font("Times New Roman", Font.BOLD, 16));
		UserType.setModel(new DefaultComboBoxModel(new String[] {"Select", "Admin", "Employee"}));
		UserType.setBounds(87, 312, 211, 30);
		UserType.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e_) {
				LoginB.requestFocus();
			}
		});
		
		frame.getContentPane().add(UserType);
		
		LoginB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String uname = UserF.getText();
				String pword = String.valueOf(PassF.getPassword());
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
								frame.dispose();
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
		LoginB.setFont(new Font("Times New Roman", Font.BOLD, 16));
		LoginB.setBounds(130, 353, 130, 41);
		LoginB.setOpaque(false);
		frame.getContentPane().add(LoginB);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\PC\\Downloads\\Background.png"));
		lblNewLabel.setBounds(0, 0, 380, 478);
		frame.getContentPane().add(lblNewLabel);
		
	
		
		
		
	}
	

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}
}

