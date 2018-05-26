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
import javafx.stage.Stage;

public class tableViewController {

	int tableNumber;
	int waiterId = nameKeeper.getId();
	String tableButtonName = "table";

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
	public void handleBack(ActionEvent e) throws IOException, SQLException {
		Parent createAccountParent = FXMLLoader.load(getClass().getResource("/waiterWindow.fxml"));
		Scene createAccountScene = new Scene(createAccountParent);
		Stage appStage = (Stage) (((Node) e.getSource()).getScene().getWindow());
		appStage.setScene(createAccountScene);
		appStage.show();

		ConnectionClass connectionClass = new ConnectionClass();
		Connection connection = connectionClass.getConnection();

		String checkOrders = "select * from orders WHERE waiterId = '" + waiterId + "'";

		PreparedStatement presStatement = connection.prepareStatement(checkOrders);
		ResultSet Rrs = presStatement.executeQuery(checkOrders);

		while (Rrs.next()) {
			tableNumber = Rrs.getInt("stolikId");
			tableButtonName = tableButtonName.concat(String.valueOf(tableNumber));

			Button but = (Button) createAccountScene.lookup("#" + tableButtonName);
			but.setStyle("-fx-background-color: #00ff00");
			tableButtonName = "table";
		}
	}
}
