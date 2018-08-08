package POS.connectivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

	private volatile static Connection connection;

	private ConnectionManager() {
	}

	public static Connection getConnection() throws SQLException {
		if (connection == null) {
			synchronized (ConnectionManager.class) {
				if (connection == null) {
					return createConnection("root", "grzywi19");
				}
			}
		}
		return connection;
	}

	private static Connection createConnection(final String username, final String password) {
		final String dbUrl = "jdbc:mysql://localhost:3306/pos?autoReconnect=true&useSSL=false";

		try {
			Class.forName("com.mysql.jdbc.Driver");

			connection = DriverManager.getConnection(dbUrl, username, password);
			System.out.println("connected");
			connection.prepareStatement("use POS");
		} catch (final Exception e) {
			System.err.println(e);
		}
		return connection;

	}
}
