package models;

import java.text.ParseException;

import javax.sql.rowset.JoinRowSet;

import serializer.ISerializable;
import utils.DateUtils;

public class Customer implements IIdentifiable, ISerializable{
	private int id;
	private int userId;
	private int points;
	private int customerTypeId;
	
	public Customer() {
		
	}
	
	public String[] ToCSV() {
		// TODO Auto-generated method stub
		String[] values = {
				Integer.toString(id),
				Integer.toString(userId),
				Integer.toString(points),
				Integer.toString(customerTypeId)
		};
		return values;
	}

	@Override
	public void FromCSV(String[] values) {
		this.id = Integer.parseInt(values[0]);
		this.userId = Integer.parseInt(values[1]);
		this.points = Integer.parseInt(values[2]);
		this.customerTypeId = Integer.parseInt(values[3]);
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getCustomerTypeId() {
		return customerTypeId;
	}

	public void setCustomerTypeId(int customerTypeId) {
		this.customerTypeId = customerTypeId;
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
