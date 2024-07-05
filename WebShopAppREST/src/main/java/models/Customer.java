package models;

import java.text.ParseException;

import javax.sql.rowset.JoinRowSet;

import serializer.ISerializable;
import utils.DateUtils;

public class Customer implements IIdentifiable, ISerializable, IsDeletable{
	private int id;
	private int userId;
	private int points;
	private int customerTypeId;
	private boolean isSuspicious;
	
	public Customer() {
		
	}
	
	public String[] ToCSV() {
		// TODO Auto-generated method stub
		String[] values = {
				Integer.toString(id),
				Integer.toString(userId),
				Integer.toString(points),
				Integer.toString(customerTypeId),
				Boolean.toString(isSuspicious)
		};
		return values;
	}

	@Override
	public void FromCSV(String[] values) {
		this.id = Integer.parseInt(values[0]);
		this.userId = Integer.parseInt(values[1]);
		this.points = Integer.parseInt(values[2]);
		this.customerTypeId = Integer.parseInt(values[3]);
		this.isSuspicious = Boolean.parseBoolean(values[4]);
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

	public boolean isSuspicious() {
		return isSuspicious;
	}

	public void setSuspicious(boolean isSuspicious) {
		this.isSuspicious = isSuspicious;
	}

	@Override
	public Boolean isDeleted() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDeleted(Boolean isDeleted) {
		// TODO Auto-generated method stub
		
	}
	
	
}
