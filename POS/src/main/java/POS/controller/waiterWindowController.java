package POS.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import POS.scene.SceneManager;
import connectivity.ConnectionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class waiterWindowController implements Initializable {

	@FXML
	Label waitersName;

	String tableButtonName = "table";
	int tableNumber;
	int waiterId = nameKeeper.getId();

	@FXML
	private TableView<myBillsTV> myBills;
	@FXML
	private TableColumn<myBillsTV, Integer> columnTable;
	@FXML
	private TableColumn<myBillsTV, Integer> columnCharge;

	private ObservableList<myBillsTV> data;

	private final SceneManager sceneManager = new SceneManager();

	public void initialize(URL arg0, ResourceBundle arg1) {

		waitersName.setText(nameKeeper.getName() + "\n" + "id: " + nameKeeper.getId());
		try {

			Connection connection = ConnectionManager.getConnection();
			data = FXCollections.observableArrayList();

			String checkTable = "select * from closedOrders WHERE kelner = '" + waiterId + "'  ";
			PreparedStatement prStatement = connection.prepareStatement(checkTable);
			ResultSet res = prStatement.executeQuery(checkTable);

			while (res.next()) {
				data.add(new myBillsTV(res.getInt("stolik"), res.getInt("suma")));
			}

		} catch (Exception e) {
			System.err.println(e);
		}
		columnTable.setCellValueFactory(new PropertyValueFactory<>("tableNumber"));
		columnCharge.setCellValueFactory(new PropertyValueFactory<>("charge"));
		myBills.setItems(null);
		myBills.setItems(data);
	}

	public void handleCloseShift(final ActionEvent actionEvent) throws IOException, SQLException {

		Connection connection = ConnectionManager.getConnection();

		String closeShift = "delete from closedOrders WHERE kelner = '" + waiterId + "' ";
		PreparedStatement pstm = connection.prepareStatement(closeShift);
		pstm.executeUpdate(closeShift);

		final Scene PosScene = sceneManager.createScene("/FXML/POS.fxml");
		sceneManager.showStage(actionEvent, PosScene);

	}

	public void handleTable(final ActionEvent actionEvent) throws IOException {
		final Scene tableViewScene = sceneManager.createScene("/tableView.fxml");
		sceneManager.showStage(actionEvent, tableViewScene);

		nameKeeper.setTableNumber(Integer.parseInt(((Labeled) actionEvent.getSource()).getText()));
	}

	public void handleLogOut(final ActionEvent actionEvent) throws IOException {
		final Scene PosScene = sceneManager.createScene("/FXML/POS.fxml");
		sceneManager.showStage(actionEvent, PosScene);
	}
}
