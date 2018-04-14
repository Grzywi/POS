package POS.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;

public class AnchorPaneController {
	String number = "";
	int pass = 0;

	@FXML
	TextField DisplayField;

	PreparedStatement myStmt = null;
	ResultSet myRs = null;

	public void loginNumber(ActionEvent e) {
		String digit = ((Labeled) e.getSource()).getText();
		number = number.concat(digit);
		DisplayField.appendText("*");
		pass = Integer.parseInt(number);
		System.out.println(pass);
	}

	public Connection getConnection() throws SQLException {
		Connection con = null;
		System.out.println("we");
		con.prepareStatement("use POS");
		System.out.println("w");
		return con;
	}
	public void handleEnter() throws SQLException {
		Connection con = null;
		con.prepareStatement("select * from kelnerzy WHERE PIN=pass");
	}
}

