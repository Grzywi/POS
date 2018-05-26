package POS.controller;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OrderTable {
	
	private final StringProperty product;
	private final IntegerProperty price;
	
	public OrderTable(String product, int price) {
		this.product = new SimpleStringProperty(product);
		this.price = new SimpleIntegerProperty(price);
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
}
