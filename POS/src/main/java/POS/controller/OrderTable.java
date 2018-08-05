package POS.controller;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OrderTable {
	
	private final StringProperty product;
	private final IntegerProperty price;
	private final IntegerProperty waiterID;
	
	public OrderTable(String product, int price, int waiterID) {
		this.product = new SimpleStringProperty(product);
		this.price = new SimpleIntegerProperty(price);
		this.waiterID = new SimpleIntegerProperty(waiterID);
	}

	public String getProduct() {
		return product.get();
	}
	
	public int getPrice() {
		return price.get();
	}
	
	public void setProduct(String value) {
		product.set(value);
	}
	
	public void setPrice(int value) {
		price.set(value);
	}
	
	public StringProperty productProperty() {
		return product;
	}
	
	public IntegerProperty priceProperty() {
		return price;
	}
	
	public Integer getWaiterID() {
		return waiterID.get();
	}
	
	public void setWaiterID(Integer value) {
		waiterID.set(value);
	}
	
	public IntegerProperty waiterIDProperty() {
		return waiterID;
	}
}
