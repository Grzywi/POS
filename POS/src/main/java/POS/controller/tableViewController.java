package POS.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class tableViewController {
	
	@FXML
	public void pizzaMenu(ActionEvent e) throws IOException {
		Parent createAccountParent = FXMLLoader.load(getClass().getResource("/tableViewFXMLs/pizzaMenu.fxml"));
		Scene createAccountScene = new Scene(createAccountParent);
		Stage appStage = (Stage) (((Node) e.getSource()).getScene().getWindow());
		appStage.setScene(createAccountScene);
		appStage.show();
	}
	@FXML
	public void drinksMenu(ActionEvent e) throws IOException {
		Parent createAccountParent = FXMLLoader.load(getClass().getResource("/tableViewFXMLs/DrinksMenu.fxml"));
		Scene createAccountScene = new Scene(createAccountParent);
		Stage appStage = (Stage) (((Node) e.getSource()).getScene().getWindow());
		appStage.setScene(createAccountScene);
		appStage.show();
	}
	@FXML
	public void dessertsMenu(ActionEvent e) throws IOException {
		Parent createAccountParent = FXMLLoader.load(getClass().getResource("/tableViewFXMLs/DessertsMenu.fxml"));
		Scene createAccountScene = new Scene(createAccountParent);
		Stage appStage = (Stage) (((Node) e.getSource()).getScene().getWindow());
		appStage.setScene(createAccountScene);
		appStage.show();
	}
	@FXML
	public void handleBack(ActionEvent e) throws IOException {
		Parent createAccountParent = FXMLLoader.load(getClass().getResource("/waiterWindow.fxml"));
		Scene createAccountScene = new Scene(createAccountParent);
		Stage appStage = (Stage) (((Node) e.getSource()).getScene().getWindow());
		appStage.setScene(createAccountScene);
		appStage.show();
	}
}
