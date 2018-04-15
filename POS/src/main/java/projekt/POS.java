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
	
	public static void main(String[] args) throws SQLException {
		launch(args);
	
		}


	@Override
	public void start(Stage primaryStage) throws Exception {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/FXML/POS.fxml"));
		AnchorPane anchorPane = loader.load();

		Scene scene = new Scene(anchorPane);

		primaryStage.setScene(scene);
		primaryStage.setTitle("POS");
		primaryStage.show();

	}

}
