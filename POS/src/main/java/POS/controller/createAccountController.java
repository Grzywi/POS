package POS.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import POS.scene.SceneManager;
import connectivity.ConnectionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;

public class createAccountController {

	private final SceneManager sceneManager = new SceneManager();

	int pass;
	int a = 2; // checking actual focus(nameField or pinField)
	int b = 1; // checking if char entered is a number
	String pin = "";
	String name = "";
	@FXML
	Label label = new Label(); // to display info if the PIN is already taken

	@FXML
	TextField nameField = new TextField();

	@FXML
	TextField pinField = new TextField();

	@FXML
	// receiving the focus
	public void pinFieldClicked() {
		a = 2;
	}

	@FXML
	public void nameFieldClicked() {
		a = 1;
	}

	@FXML
	public void loginNumber(ActionEvent e) {
		String digit = ((Labeled) e.getSource()).getText();
		if (digit.matches(".*\\d+.*")) {
			b = 1;
		} else {
			b = 2;
		}

		if (a == 2 && b == 1) {
			if (pinField.getLength() < 4) {
				pinField.appendText(digit);
				pin = pin.concat(digit);
			}
		}

		else if (a == 1) {
			nameField.appendText(digit);
			name = name.concat(digit);
		}

	}

	@FXML
	public void handleClear() {
		if (a == 2) {
			pinField.setText("");
			pin = "";
		} else if (a == 1) {
			nameField.setText("");
			name = "";
		}
	}

	@FXML
	public void handleEnter(final ActionEvent actionEvent) throws SQLException, IOException {

		Connection connection = ConnectionManager.getConnection();

		int finalPin = Integer.parseInt(pin);
		String finalName = name;

		// checking whether the PIN is already taken
		String query1 = "select * from kelnerzy WHERE PIN = '" + finalPin + "'";
		ResultSet rs;

		PreparedStatement preStatement = connection.prepareStatement(query1);
		rs = preStatement.executeQuery(query1);

		if (rs.next()) {
			nameField.setText("");
			pinField.setText("");
			name = "";
			pin = "";
			label.setText("Podany PIN jest zajêty");
		}

		else {
			String query2 = "insert INTO kelnerzy (kelner, PIN) VALUES ('" + finalName + "', '" + finalPin + "')";
			PreparedStatement presStatement = connection.prepareStatement(query2);
			presStatement.executeUpdate(query2);

			// after successful Account creation the user is taken back to the login window
			final Scene posScene = sceneManager.createScene("/FXML/POS.fxml");
			sceneManager.showStage(actionEvent, posScene);
		}
	}

	@FXML
	public void handleBack(final ActionEvent actionEvent) throws IOException {
		final Scene posScene = sceneManager.createScene("/FXML/POS.fxml");
		sceneManager.showStage(actionEvent, posScene);
	}

}
