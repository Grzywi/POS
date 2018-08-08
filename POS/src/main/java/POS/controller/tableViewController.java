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
import javafx.scene.control.Button;

public class tableViewController {

	int tableNumber;
	int waiterId = nameKeeper.getId();
	String tableButtonName = "table";
	String green = "-fx-background-color: #00ff00";

	private final SceneManager sceneManager = new SceneManager();
	
	@FXML
	public void pizzaMenu(final ActionEvent actionEvent) throws IOException {
		final Scene pizzaMenuScene = sceneManager.createScene("/tableViewFXMLs/pizzaMenu.fxml");
		sceneManager.showStage(actionEvent, pizzaMenuScene);
		pizzaMenuScene.getStylesheets().add("RowColor.css");
	}

	@FXML
	public void drinksMenu(final ActionEvent actionEvent) throws IOException {
		final Scene drinkMenuScene = sceneManager.createScene("/tableViewFXMLs/DrinksMenu.fxml");
		sceneManager.showStage(actionEvent, drinkMenuScene);
		drinkMenuScene.getStylesheets().add("RowColor.css");
		
	}

	@FXML
	public void dessertsMenu(final ActionEvent actionEvent) throws IOException {
		final Scene dessertMenuScene = sceneManager.createScene("/tableViewFXMLs/DessertsMenu.fxml");
		sceneManager.showStage(actionEvent, dessertMenuScene);
		dessertMenuScene.getStylesheets().add("RowColor.css");
	}

	@FXML
	public void handleBack(final ActionEvent actionEvent) throws IOException, SQLException {
		final Scene waiterWindowScene = sceneManager.createScene("/waiterWindow.fxml");
		sceneManager.showStage(actionEvent, waiterWindowScene);

		Connection connection = ConnectionManager.getConnection();

		String checkOrders = "select * from orders";

		PreparedStatement presStatement = connection.prepareStatement(checkOrders);
		ResultSet Rrs = presStatement.executeQuery(checkOrders);

		while (Rrs.next()) {

			tableNumber = Rrs.getInt("stolikId");
			tableButtonName = tableButtonName.concat(String.valueOf(tableNumber));

			if (Rrs.getInt("waiterId") == waiterId) {
				Button but = (Button) waiterWindowScene.lookup("#" + tableButtonName);
				but.setStyle("-fx-background-color: #00ff00");
				tableButtonName = "table";
			} else {
				Button but = (Button) waiterWindowScene.lookup("#" + tableButtonName);
				
				final boolean isGreen = but.getStyle().equals(green);
				if (isGreen) {
					tableButtonName = "table";
				} else {
					but.setStyle("-fx-background-color: #f4ff31");
					tableButtonName = "table";
				}
			}
		}
	}

}
