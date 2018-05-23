package tableViewController;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import POS.controller.nameKeeper;
import connectivity.ConnectionClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Labeled;
import javafx.stage.Stage;

public class pizzaMenuController {
	
	int waiterId = nameKeeper.getId();
	int tableNumber = nameKeeper.getTableNumber();
	int productId;
	int productPrice;
	
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
		
		//getting id and price of a product
		if(rs.next()) {
			productId = rs.getInt("id");
			productPrice = rs.getInt("cena");
		}
		
		String insertQuery = "insert into orders (waiterId, stolikId, zamowienie, cena) values ('" + waiterId + "', '" + tableNumber + "', '" + productId + "', '" + productPrice + "')";
		
		PreparedStatement presStatement = connection.prepareStatement(insertQuery);
		int Rs = presStatement.executeUpdate(insertQuery);
		}
}
