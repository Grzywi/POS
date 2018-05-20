package POS.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.stage.Stage;

public class waiterWindowController implements Initializable {

	@FXML
	Label waitersName = new Label();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		waitersName.setText(nameKeeper.getName() + "\n" + "id: " + nameKeeper.getId());
	}

	public void handleTable(ActionEvent e) throws IOException {
		Parent createAccountParent = FXMLLoader.load(getClass().getResource("/tableView.fxml"));
		Scene createAccountScene = new Scene(createAccountParent);
		Stage appStage = (Stage) (((Node) e.getSource()).getScene().getWindow());
		appStage.setScene(createAccountScene);
		appStage.show();
		
		nameKeeper.setTableNumber(Integer.parseInt(((Labeled) e.getSource()).getText()));
	}
	
	public void handleLogOut(ActionEvent e) throws IOException {
		Parent createAccountParent = FXMLLoader.load(getClass().getResource("/FXML/POS.fxml"));
		Scene createAccountScene = new Scene(createAccountParent);
		Stage appStage = (Stage) (((Node) e.getSource()).getScene().getWindow());
		appStage.setScene(createAccountScene);
		appStage.show();
	}
}
