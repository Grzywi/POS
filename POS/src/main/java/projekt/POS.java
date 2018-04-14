package projekt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class POS extends Application {
	static String username = "root";
	static String password = "grzywi19";
	static String dbUrl = "jdbc:mysql://localhost:3306/pos?autoReconnect=true&useSSL=false";
	
	static Connection conn = null;
	PreparedStatement myStmt = null;
	ResultSet myRs = null;

	public static void main(String[] args) throws SQLException {
		launch(args);
			try {
				conn = DriverManager.getConnection(dbUrl,username, password);
				System.out.println("connected");
			} catch (SQLException e) {
				System.err.println(e.getMessage());
				
			}
		}


	@Override
	public void start(Stage primaryStage) throws Exception {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/POS.fxml"));
		AnchorPane anchorPane = loader.load();

		Scene scene = new Scene(anchorPane);

		primaryStage.setScene(scene);
		primaryStage.setTitle("POS");
		primaryStage.show();

	}

}
