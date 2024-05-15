package models;

import serializer.ISerializable;
import java.util.ArrayList;

public class ShoppingCart implements ISerializable, IIdentifiable {
	private int id;
	private ArrayList<Integer> chocholateIds;
	private int userId;
	private double price;
	
	public ShoppingCart() {
		super();
		chocholateIds = new ArrayList<Integer>();
	}

	public ShoppingCart(ArrayList<Integer> chocholateIds, int userId, double price) {
		super();
		this.chocholateIds = chocholateIds;
		this.userId = userId;
		this.price = price;
	}

	@Override
	public String[] ToCSV() {
		String[] values = new String[chocholateIds.size() + 3];
		values[0] = Integer.toString(id);
		values[1] = Integer.toString(userId);
		values[2] = Double.toString(price);
		int i = 3;
		for(int id: chocholateIds) {
			values[i++] = Integer.toString(id);
			
		}
		
		return values;
	}
	
	@Override
	public void FromCSV(String[] values) {
		this.id = Integer.parseInt(values[0]);
		this.userId = Integer.parseInt(values[1]);
		this.price = Double.parseDouble(values[2]);
		
		for(int i=3; i<values.length;i++) {
			chocholateIds.add(Integer.parseInt(values[i]));
		}
	}
	
	public ArrayList<Integer> getChocholateIds() {
		return chocholateIds;
	}

	public void setChocholateIds(ArrayList<Integer> chocholateIds) {
		this.chocholateIds = chocholateIds;
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
