package POS.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import connectivity.ConnectionClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AnchorPaneController {
	int id;
	String name;
	String number = "";
	int pass = 0;
	String query = "select * from kelnerzy WHERE PIN = ";
	@FXML
	Label label = new Label();

	@FXML
	TextField DisplayField;

	public void loginNumber(ActionEvent e) {
		String digit = ((Labeled) e.getSource()).getText();
		number = number.concat(digit);
		if (DisplayField.getLength() < 4) {
			DisplayField.appendText("*");
		}
		pass = Integer.parseInt(number);
	}

	public void handleClear() {
		DisplayField.setText("");
		number = "";
	}

	@FXML
	public void createAccount(ActionEvent e) throws IOException {
		Parent createAccountParent = FXMLLoader.load(getClass().getResource("/createAccount.fxml"));
		Scene createAccountScene = new Scene(createAccountParent);
		Stage appStage = (Stage) (((Node) e.getSource()).getScene().getWindow());
		appStage.setScene(createAccountScene);
		appStage.show();
	}

	@FXML
	public void handleEnter(ActionEvent e) throws SQLException, IOException {
		ConnectionClass connectionClass = new ConnectionClass();
		Connection connection = connectionClass.getConnection();
		String sql = query.concat(number);

		PreparedStatement preStatement = connection.prepareStatement(sql);
		ResultSet rs = preStatement.executeQuery(sql);

		if (rs.next()) {
			id = rs.getInt("id");
			name = rs.getString("kelner");
			System.out.println("zalogowano na konto " + id + " " + name);

			// opening waiters view after successful login
			Parent createAccountParent = FXMLLoader.load(getClass().getResource("/waiterWindow.fxml"));
			Scene createAccountScene = new Scene(createAccountParent);
			Stage appStage = (Stage) (((Node) e.getSource()).getScene().getWindow());
			appStage.setScene(createAccountScene);
			appStage.show();
			
		} else {
			label.setText("Niepoprawny PIN");
		}
	}
	public String getName() {
		return name;
	}
}
