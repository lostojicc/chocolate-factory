package models;

import serializer.ISerializable;
import java.util.ArrayList;

public class ShoppingCart implements ISerializable, IIdentifiable {
	private int id;
	private int userId;
	private double price;
	
	public ShoppingCart() {
		super();
	}

	public ShoppingCart(int userId, double price) {
		super();
		this.userId = userId;
		this.price = price;
	}

	@Override
	public String[] ToCSV() {
		String[] values = new String[3];
		values[0] = Integer.toString(id);
		values[1] = Integer.toString(userId);
		values[2] = Double.toString(price);
		return values;
	}
	
	@Override
	public void FromCSV(String[] values) {
		this.id = Integer.parseInt(values[0]);
		this.userId = Integer.parseInt(values[1]);
		this.price = Double.parseDouble(values[2]);
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}
}
