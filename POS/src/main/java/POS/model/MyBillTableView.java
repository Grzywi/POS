package POS.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class MyBillTableView {

	private final IntegerProperty charge;
	private final IntegerProperty tableNumber;	
	
	public MyBillTableView(int tableNumber, int charge) {
		this.charge = new SimpleIntegerProperty(charge);
		this.tableNumber = new SimpleIntegerProperty(tableNumber);
	}
	
	public int getCharge() {
		return charge.get();
	}
	
	public void setCharge(int value) {
		charge.set(value);
	}
	
	public IntegerProperty chargeProperty() {
		return charge;
	}
	
	public int getTableNumber() {
		return tableNumber.get();
	}
	
	public void setTableNumber(int value) {
		tableNumber.set(value);
	}
	
	public IntegerProperty tableNumberProperty() {
		return tableNumber;
	}
}
