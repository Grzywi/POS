package POS.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import POS.constant.ColumnName;
import POS.constant.DataBaseQuery;
import POS.scene.SceneManager;
import POS.util.NameKeeper;
import POS.connectivity.ConnectionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;

import static POS.constant.ColumnName.WAITER_NAME_COLUMN;
import static POS.constant.ColumnName.WAITER_ID_COLUMN;;

public class AnchorPaneController {

	private final SceneManager sceneManager = new SceneManager();
	private final String green = "-fx-background-color: #00ff00";
	private final String yellow = "-fx-background-color: #f4ff31";

	private int tableNumber;
	private int waiterId;

	private String name;
	private String pin = "";
	private String tableButtonName = "table";

	@FXML
	private Label wrongPinLabel;

	@FXML
	private TextField pinField;

	public void loginNumber(ActionEvent e) {
		final String digit = ((Labeled) e.getSource()).getText();
		pin = pin.concat(digit);
		if (pinField.getLength() < 4) {
			pinField.appendText("*");
		}
	}

	public void handleClear() {
		pinField.setText("");
		pin = "";
		wrongPinLabel.setText("");
	}

	@FXML
	public void createAccountScene(final ActionEvent actionEvent) {
		final Scene accountScene = sceneManager.createScene("/createAccount.fxml");
		sceneManager.showStage(actionEvent, accountScene);
	}

	@FXML
	public void handleEnter(final ActionEvent actionEvent) throws SQLException {

		NameKeeper.setPassword(Integer.parseInt(pin));

		final Connection connection = ConnectionManager.getConnection();

		final PreparedStatement preStatement = connection.prepareStatement(DataBaseQuery.GET_WAITER);
		preStatement.setString(1, pin);
		final ResultSet waiterResult = preStatement.executeQuery();

		if (!waiterResult.next()) {
			wrongPinLabel.setText("Niepoprawny PIN");
			return;
		} 
		
		name = waiterResult.getString(WAITER_NAME_COLUMN);
		waiterId = waiterResult.getInt(WAITER_ID_COLUMN);
		System.out.println("zalogowano na konto " + waiterId + " " + name);
		NameKeeper.setName(waiterResult.getString(WAITER_NAME_COLUMN));
		NameKeeper.setId(waiterResult.getInt(WAITER_ID_COLUMN));

		final ResultSet currentWaiterOrders = getWaiterResult(DataBaseQuery.GET_CURRENT_WAITER_ORDERS);
		final ResultSet otherWaitersOrders = getWaiterResult(DataBaseQuery.GET_OTHER_WAITERS_ORDERS);
		
		final Scene waiterWindowScene = sceneManager.createScene("/waiterWindow.fxml");
		sceneManager.showStage(actionEvent, waiterWindowScene);
		
		colorTable(waiterWindowScene, currentWaiterOrders, actionEvent, green);
		colorTable(waiterWindowScene, otherWaitersOrders, actionEvent, yellow);
	}

	private ResultSet getWaiterResult(final String query) throws SQLException {
		final Connection connection = ConnectionManager.getConnection();
		final PreparedStatement otherWaitersStatement = connection
				.prepareStatement(query);
		otherWaitersStatement.setInt(1, waiterId);
		final ResultSet otherWaitersOrders = otherWaitersStatement.executeQuery();
		return otherWaitersOrders;
	}

	private void colorTable(final Scene waiterWindowScene, final ResultSet orders, final ActionEvent actionEvent, final String tableColor)
			throws SQLException {

		while (orders.next()) {

			tableNumber = orders.getInt(ColumnName.TABLE_ID_COLUMN);
			tableButtonName = tableButtonName.concat(String.valueOf(tableNumber));

			final Button tableButton = (Button) waiterWindowScene.lookup("#" + tableButtonName);
			tableButton.setStyle(tableColor);
			tableButtonName = "table";
		}
	}
}
