package POS.controller;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OrderTable {
	
	private final StringProperty product;
	private final IntegerProperty price;
	private final StringProperty waiter;
	
	public OrderTable(String product, int price, String waiter) {
		this.product = new SimpleStringProperty(product);
		this.price = new SimpleIntegerProperty(price);
		this.waiter = new SimpleStringProperty(waiter);
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
	
	public String getWaiter() {
		return waiter.get();
	}
	
	public void setWaiter(String value) {
		waiter.set(value);
	}
	
	public StringProperty waiterProperty() {
		return waiter;
	}
}
