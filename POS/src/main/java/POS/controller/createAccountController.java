package POS.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class createAccountController {
		public void handleCreateAccount() {
			
		}
		public void loginNumber() {
			
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
