package g1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connections {
	static Connection con;
	public static Connection getConnection() {
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
	    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/logindb", "root", "Robert@17");
	    
		}catch (Exception ex) {
			System.out.println(""+ex);
		}		
	return con;
	}
	 
}