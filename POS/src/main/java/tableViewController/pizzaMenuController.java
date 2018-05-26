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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class pizzaMenuController implements Initializable {

	int waiterId = nameKeeper.getId();
	int tableNumber = nameKeeper.getTableNumber();
	int productId;
	int productPrice;

	@FXML
	private TableView<OrderTable> orderTable;
	@FXML
	private TableColumn<OrderTable, String> columnProduct;
	@FXML
	private TableColumn<OrderTable, Integer> columnPrice;

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
				while(result.next()) {
					data.add(new OrderTable(result.getString("produkt"), res.getInt("cena")));
				}
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		columnProduct.setCellValueFactory(new PropertyValueFactory<>("product"));
		columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

		orderTable.setItems(null);
		orderTable.setItems(data);
	}

	@FXML
	public void handleBack(ActionEvent e) throws IOException {
		Parent createAccountParent = FXMLLoader.load(getClass().getResource("/tableView.fxml"));
		Scene createAccountScene = new Scene(createAccountParent);
		Stage appStage = (Stage) (((Node) e.getSource()).getScene().getWindow());
		appStage.setScene(createAccountScene);
		appStage.show();
	}

	public void handlePizza(ActionEvent e) throws SQLException {
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
			while(result.next()) {
				data.add(new OrderTable(result.getString("produkt"), res.getInt("cena")));
			}
		}

	columnProduct.setCellValueFactory(new PropertyValueFactory<>("product"));
	columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

	orderTable.setItems(null);
	orderTable.setItems(data);
	}
}