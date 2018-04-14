package connectivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionClass {
	public Connection connection;
	public Connection getConnection() throws SQLException {
		
		String username = "root";
		String password = "grzywi19";
		String dbUrl = "jdbc:mysql://localhost:3306/pos?autoReconnect=true&useSSL=false";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			connection = DriverManager.getConnection(dbUrl, username, password);
			System.out.println("connected");
			PreparedStatement preStatement = connection.prepareStatement("use POS");
		} catch (Exception e) {
			System.out.println("not connected");
			e.printStackTrace();
		}
		return connection;
		
	}

}
