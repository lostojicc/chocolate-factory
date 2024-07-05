package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import serializer.ISerializable;

public class Order implements IIdentifiable, ISerializable, IsDeletable {
	private int id;
	private String identification;
	private ArrayList<Integer> chocholateIds;
	private int factoryId;
	private Date dateTime;
	private double price;
	private int userId;
	private OrderState state;
	
	public Order() {
		super();
		chocholateIds = new ArrayList<Integer>();
	}

	public Order(String identification, ArrayList<Integer> chocholateIds, int factoryId, Date dateTime, double price,
			int userId, OrderState state) {
		super();
		this.identification = identification;
		this.chocholateIds = chocholateIds;
		this.factoryId = factoryId;
		this.dateTime = dateTime;
		this.price = price;
		this.userId = userId;
		this.state = state;
	}

	@Override
	public String[] ToCSV() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String[] values = new String[chocholateIds.size() + 7];
		values[0] = Integer.toString(id);
		values[1] = identification;
		values[2] = Integer.toString(factoryId);
		values[3] = sdf.format(dateTime);
		values[4] = Double.toString(price);
		values[5] = Integer.toString(userId);
		values[6] = state.name();
		
		int i = 7;
		for(int id:chocholateIds) {
			values[i++] = Integer.toString(id);
		}
		
		return values;
	}

	@Override
	public void FromCSV(String[] values) {
		id = Integer.parseInt(values[0]);
		identification = values[1];
		factoryId = Integer.parseInt(values[2]);
		
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     try {
			dateTime = sdf.parse(values[3]);
		 } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
	     price = Double.parseDouble(values[4]);
	     userId = Integer.parseInt(values[5]);
	     state = OrderState.valueOf(values[6]);
	     
	     for(int i=7;i<values.length;i++) {
	    	 chocholateIds.add(Integer.parseInt(values[i]));
	     }
	}

	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	public ArrayList<Integer> getChocholateIds() {
		return chocholateIds;
	}

	public void setChocholateIds(ArrayList<Integer> chocholateIds) {
		this.chocholateIds = chocholateIds;
	}

	public int getFactoryId() {
		return factoryId;
	}

	public void setFactoryId(int factoryId) {
		this.factoryId = factoryId;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public OrderState getState() {
		return state;
	}

	public void setState(OrderState state) {
		this.state = state;
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void setId(int id) {
		// TODO Auto-generated method stub
		this.id = id;
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
