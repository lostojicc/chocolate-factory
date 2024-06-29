package dto;

import java.util.ArrayList;
import java.util.Date;

import models.Order;
import models.OrderState;
import models.User;

public class OrderDTO {
	private int id;
	private String identification;
	private Date dateTime;
	private double price;
	private int userId;
	private int factoryId;
	private OrderState state;
	
	private String factoryName;
	private String username;
	private ArrayList<Integer> chocholateIds;
	
	public OrderDTO() {
		
	}
	
	public OrderDTO(Order order, String factoryName, User user) {
		this.id = order.getId();
		this.identification = order.getIdentification();
		this.dateTime = order.getDateTime();
		this.price = order.getPrice();
		this.userId = order.getUserId();
		this.factoryId = order.getFactoryId();
		this.state = order.getState();
		this.chocholateIds = order.getChocholateIds();
		
		this.factoryName = factoryName;
		this.username = user.getUsername();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
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

	public int getFactoryId() {
		return factoryId;
	}

	public void setFactoryId(int factoryId) {
		this.factoryId = factoryId;
	}

	public OrderState getState() {
		return state;
	}

	public void setState(OrderState state) {
		this.state = state;
	}

	public String getFactoryName() {
		return factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ArrayList<Integer> getChocholateIds() {
		return chocholateIds;
	}

	public void setChocholateIds(ArrayList<Integer> chocholateIds) {
		this.chocholateIds = chocholateIds;
	}
}
