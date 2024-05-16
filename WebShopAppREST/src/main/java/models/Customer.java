package models;

import java.text.ParseException;

import javax.sql.rowset.JoinRowSet;

import utils.DateUtils;

public class Customer extends User{
	private int points;
	private int customerTypeId;
	// shoppingCartId?
	
	public Customer() {
		
	}
	
	public String[] ToCSV() {
		// TODO Auto-generated method stub
		String[] values = {
				Integer.toString(id),
				Integer.toString(points),
				Integer.toString(customerTypeId)
		};
		return values;
	}

	@Override
	public void FromCSV(String[] values) {
		this.id = Integer.parseInt(values[0]);
		this.points = Integer.parseInt(values[1]);
		this.customerTypeId = Integer.parseInt(values[2]);
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
}
