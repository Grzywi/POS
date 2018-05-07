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
import javafx.scene.control.TextInputControl;
import javafx.stage.Stage;

public class createAccountController {

	int pass;
	int a = 1;  // checking actual focus(nameField or pinField)
	String pin = "";
	String name = "";
	@FXML
	Label label = new Label(); // to display info if the PIN is already taken

	@FXML
	TextField nameField = new TextField();

	@FXML
	TextField pinField = new TextField();

	@FXML
	//receiving the focus
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
		if (a == 2) {
			if(pinField.getLength()<4) {
				pinField.appendText(digit);
				pin = pin.concat(digit);
			}
			else {
				
			}
		} else if (a == 1) {
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
	public void handleEnter(ActionEvent e) throws SQLException, IOException {
		ConnectionClass connectionClass = new ConnectionClass();
		Connection connection = connectionClass.getConnection();

		int finalPin = Integer.parseInt(pin);
		String finalName = name;

		//checking whether the PIN is already taken
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
			int Rrs = presStatement.executeUpdate(query2);
			
			//after successful Account creation the user is taken back to the login window
			Parent createAccountParent = FXMLLoader.load(getClass().getResource("/FXML/POS.fxml"));
			Scene createAccountScene = new Scene(createAccountParent);
			Stage appStage = (Stage) (((Node) e.getSource()).getScene().getWindow());
			appStage.setScene(createAccountScene);
			appStage.show();
		}
	}

	@FXML
	public void handleBack(ActionEvent e) throws IOException {
		Parent createAccountParent = FXMLLoader.load(getClass().getResource("/FXML/POS.fxml"));
		Scene createAccountScene = new Scene(createAccountParent);
		Stage appStage = (Stage) (((Node) e.getSource()).getScene().getWindow());
		appStage.setScene(createAccountScene);
		appStage.show();
	}

}
