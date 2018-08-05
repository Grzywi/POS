package tableViewController;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import POS.controller.OrderTable;
import POS.controller.nameKeeper;
import connectivity.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
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
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

public class DrinksMenuController implements Initializable {

	String waiterName = nameKeeper.getName();
	int waiterId = nameKeeper.getId();
	int tableNumber = nameKeeper.getTableNumber();
	int productId;
	int productPrice;
	int suma;
	int tableCharge;

	@FXML
	Label sumaTextLabel;
	@FXML
	Label sumaNumberLabel;

	@FXML
	private TableView<OrderTable> orderTable;
	@FXML
	private TableColumn<OrderTable, String> columnProduct;
	@FXML
	private TableColumn<OrderTable, Integer> columnPrice;
	@FXML
	private TableColumn<OrderTable, Integer> columnWaiterID;

	private ObservableList<OrderTable> data;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {

			ConnectionClass connectionClass = new ConnectionClass();
			Connection connection = connectionClass.getConnection();
			data = FXCollections.observableArrayList();

			// checking table orders
			String checkTable = "select * from orders WHERE stolikId = '" + tableNumber + "'";
			PreparedStatement prStatement = connection.prepareStatement(checkTable);
			ResultSet res = prStatement.executeQuery(checkTable);

			while (res.next()) {
				String getProductName = "select * from menu WHERE id = '" + res.getInt("zamowienie") + "'";
				PreparedStatement pStatement = connection.prepareStatement(getProductName);
				ResultSet result = pStatement.executeQuery(getProductName);
				while (result.next()) {

					data.add(new OrderTable(result.getString("produkt"), res.getInt("cena"), res.getInt("waiterId")));
					suma += res.getInt("cena");
					sumaNumberLabel.setText(String.valueOf(suma) + " PLN");
				}
			}
		} catch (Exception e) {
			System.err.println(e);
		}

		columnProduct.setCellValueFactory(new PropertyValueFactory<>("product"));
		columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		columnWaiterID.setCellValueFactory(new PropertyValueFactory<>("waiterID"));

		orderTable.setItems(null);
		orderTable.setItems(data);

		PseudoClass yellowPS = PseudoClass.getPseudoClass("yellow");
		PseudoClass greenPS = PseudoClass.getPseudoClass("green");

		orderTable.setRowFactory(param -> new TableRow<OrderTable>() {
			@Override
			protected void updateItem(OrderTable item, boolean empty) {
				super.updateItem(item, empty);
				pseudoClassStateChanged(greenPS, (!empty) && item.getWaiterID() == waiterId);
				pseudoClassStateChanged(yellowPS, (!empty) && item.getWaiterID() != waiterId);
			}
		});

	}

	@FXML
	public void handleUsun() throws SQLException {

		ConnectionClass connectionClass = new ConnectionClass();
		Connection connection = connectionClass.getConnection();
		data = FXCollections.observableArrayList();

		String deleteTableOrders = "delete from orders WHERE stolikId = '" + tableNumber + "'";
		PreparedStatement Pstm = connection.prepareStatement(deleteTableOrders);
		int rss = Pstm.executeUpdate(deleteTableOrders);

		orderTable.setItems(null);
		orderTable.setItems(data);
		suma = 0;
		sumaNumberLabel.setText(String.valueOf(suma) + " PLN");

	}

	@FXML
	public void handleDrukuj(ActionEvent e) throws SQLException, IOException {
		ConnectionClass connectionClass = new ConnectionClass();
		Connection connection = connectionClass.getConnection();
		data = FXCollections.observableArrayList();

		String getTableOrders = "select * from orders WHERE stolikId = '" + tableNumber + "'";
		PreparedStatement Prestm = connection.prepareStatement(getTableOrders);
		ResultSet rs = Prestm.executeQuery(getTableOrders);
		while (rs.next()) {
			tableCharge += rs.getInt("cena");
		}

		String insertIntoClosedOrders = "insert into closedOrders (kelner, stolik, suma) values ('" + waiterId + "',  '"
				+ tableNumber + "', '" + tableCharge + "')";
		PreparedStatement pstm = connection.prepareStatement(insertIntoClosedOrders);
		int resu = pstm.executeUpdate(insertIntoClosedOrders);

		String deleteTableOrders = "delete from orders WHERE stolikId = '" + tableNumber + "'";
		PreparedStatement Pstm = connection.prepareStatement(deleteTableOrders);
		int rss = Pstm.executeUpdate(deleteTableOrders);

		System.out.println("Suma: " + suma + " PLN");

		orderTable.setItems(null);
		orderTable.setItems(data);
		suma = 0;
		sumaNumberLabel.setText(String.valueOf(suma) + " PLN");
		
		Parent createAccountParent = FXMLLoader.load(getClass().getResource("/waiterWindow.fxml"));
		Scene createAccountScene = new Scene(createAccountParent);
		Stage appStage = (Stage) (((Node) e.getSource()).getScene().getWindow());
		appStage.setScene(createAccountScene);
		appStage.show();
	}

	@FXML
	public void handleBack(ActionEvent e) throws IOException {
		Parent createAccountParent = FXMLLoader.load(getClass().getResource("/tableView.fxml"));
		Scene createAccountScene = new Scene(createAccountParent);
		Stage appStage = (Stage) (((Node) e.getSource()).getScene().getWindow());
		appStage.setScene(createAccountScene);
		appStage.show();
	}

	public void handleDrink(ActionEvent e) throws SQLException {
		ConnectionClass connectionClass = new ConnectionClass();
		Connection connection = connectionClass.getConnection();

		String produkt = ((Labeled) e.getSource()).getText();
		String productIdQuery = "select * from menu WHERE produkt = '" + produkt + "'";

		PreparedStatement preStatement = connection.prepareStatement(productIdQuery);
		ResultSet rs = preStatement.executeQuery(productIdQuery);

		// getting id and price of a product
		while (rs.next()) {
			productId = rs.getInt("id");
			productPrice = rs.getInt("cena");

			suma += rs.getInt("cena");
			sumaNumberLabel.setText(String.valueOf(suma) + " PLN");
		}

		String insertQuery = "insert into orders (waiterId, stolikId, zamowienie, cena) values ('" + waiterId + "', '"
				+ tableNumber + "', '" + productId + "', '" + productPrice + "')";

		PreparedStatement presStatement = connection.prepareStatement(insertQuery);
		int Rs = presStatement.executeUpdate(insertQuery);
		data = FXCollections.observableArrayList();

		// checking table orders
		String checkTable = "select * from orders WHERE stolikId = '" + tableNumber + "'";
		PreparedStatement prStatement = connection.prepareStatement(checkTable);
		ResultSet res = prStatement.executeQuery(checkTable);

		while (res.next()) {
			String getProductName = "select * from menu WHERE id = '" + res.getInt("zamowienie") + "'";
			PreparedStatement pStatement = connection.prepareStatement(getProductName);
			ResultSet result = pStatement.executeQuery(getProductName);
			while (result.next()) {

				data.add(new OrderTable(result.getString("produkt"), res.getInt("cena"), res.getInt("waiterID")));

			}
		}

		columnProduct.setCellValueFactory(new PropertyValueFactory<>("product"));
		columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		columnWaiterID.setCellValueFactory(new PropertyValueFactory<>("waiterID"));
		orderTable.setItems(null);
		orderTable.setItems(data);
	}
}