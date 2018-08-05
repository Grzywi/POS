package POS.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import connectivity.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class waiterWindowController implements Initializable {
	
	@FXML Label waitersName;

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

	public void initialize(URL arg0, ResourceBundle arg1) {
		
		waitersName.setText(nameKeeper.getName() + "\n" + "id: " + nameKeeper.getId());
		try {
			ConnectionClass connectionClass = new ConnectionClass();
			Connection connection = connectionClass.getConnection();
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
