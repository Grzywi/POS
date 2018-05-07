package POS.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class waiterWindowController implements Initializable {

	AnchorPaneController loginWindow = new AnchorPaneController();

	@FXML
	Label waitersName = new Label();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println(loginWindow.getName());
	}

}
