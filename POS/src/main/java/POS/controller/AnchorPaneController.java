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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AnchorPaneController {
	int id;
	int tableNumber;
	int waiterId;
	int password;
	
	String isGreen = "-fx-background-color: #00ff00";
	String name;
	String PIN = "";
	String tableButtonName = "table";


	@FXML
	Label label = new Label();

	@FXML
	TextField DisplayField;

	public void loginNumber(ActionEvent e) {
		String digit = ((Labeled) e.getSource()).getText();
		PIN = PIN.concat(digit);
		if (DisplayField.getLength() < 4) {
			DisplayField.appendText("*");
		}
	}

	public void handleClear() {
		DisplayField.setText("");
		PIN = "";
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

		password = Integer.parseInt(PIN);
		nameKeeper.setPassword(password);

		String checkWaiter = "select * from kelnerzy WHERE PIN = '" + password + "'";

		ConnectionClass connectionClass = new ConnectionClass();
		Connection connection = connectionClass.getConnection();

		PreparedStatement preStatement = connection.prepareStatement(checkWaiter);
		ResultSet rs = preStatement.executeQuery(checkWaiter);

		if (rs.next()) {
			name = rs.getString("kelner");
			waiterId = rs.getInt("id");
			System.out.println("zalogowano na konto " + waiterId + " " + name);
			nameKeeper.setName(rs.getString("kelner"));
			nameKeeper.setId(rs.getInt("id"));

			// opening waiters view after successful login
			Parent createAccountParent = FXMLLoader.load(getClass().getResource("/waiterWindow.fxml"));
			Scene createAccountScene = new Scene(createAccountParent);
			Stage appStage = (Stage) (((Node) e.getSource()).getScene().getWindow());
			appStage.setScene(createAccountScene);
			appStage.show();

			String checkOrders = "select * from orders";

			PreparedStatement presStatement = connection.prepareStatement(checkOrders);
			ResultSet Rrs = presStatement.executeQuery(checkOrders);

			while (Rrs.next()) {
				tableNumber = Rrs.getInt("stolikId");
				tableButtonName = tableButtonName.concat(String.valueOf(tableNumber));
				
				if (Rrs.getInt("waiterId") == waiterId) {
					Button but = (Button) createAccountScene.lookup("#" + tableButtonName);
					but.setStyle("-fx-background-color: #00ff00");
					tableButtonName = "table";
				} else {
					Button but = (Button) createAccountScene.lookup("#" + tableButtonName);
					if(but.getStyle().equals(isGreen)) {
						tableButtonName = "table";
					}
					else{
						but.setStyle("-fx-background-color: #f4ff31");
						tableButtonName = "table";
					}
				}
			}
		} else {
			label.setText("Niepoprawny PIN");
		}
	}
}
