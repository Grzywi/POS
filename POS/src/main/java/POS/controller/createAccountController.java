package POS.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class createAccountController {
	
		int pass;
		
		@FXML
		TextField nameField;
		TextField pinField;
		
		
		public void loginNumber(ActionEvent e) {
			String digit = ((Labeled) e.getSource()).getText();
			nameField.appendText(digit);
		}
		
		
		public void handleClear() {
			
		}
		public void handleEnter() {
			
		}
		
		public void handleBack(ActionEvent e) throws IOException {
			Parent createAccountParent = FXMLLoader.load(getClass().getResource("/FXML/POS.fxml"));
			Scene createAccountScene = new Scene(createAccountParent);
			Stage appStage = (Stage) (((Node) e.getSource()).getScene().getWindow());
			appStage.setScene(createAccountScene);
			appStage.show();
		}
}
