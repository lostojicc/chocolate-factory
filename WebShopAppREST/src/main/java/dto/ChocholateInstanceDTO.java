package dto;

import models.Chocholate;
import models.ChocholateInstance;
import models.ChocholateKind;
import models.ChocholateType;

public class ChocholateInstanceDTO {
	private String name;
	private double price;
	private ChocholateKind kind;
	private int factoryId;
	private ChocholateType type;
	private double grams;
	private String imagePath;
	private int chocholateQuantity;
	private int maxQuantity;
	private double totalPrice;
	
	//instance
	private int id;
	private int cartId;
	private int chocholateId;
	private int quantity;
	
	public ChocholateInstanceDTO() {
		super();
	}
	
	public ChocholateInstanceDTO(Chocholate chocholate, ChocholateInstance chochoInstance) {
		super();
		this.name = chocholate.getName();
		this.price = chocholate.getPrice();
		this.kind = chocholate.getKind();
		this.factoryId = chocholate.getFactoryId();
		this.type = chocholate.getType();
		this.grams = chocholate.getGrams();
		this.imagePath = chocholate.getImagePath();
		this.chocholateQuantity = chocholate.getQuantity();
		
		this.id = chochoInstance.getId();
		this.cartId = chochoInstance.getCartId();
		this.chocholateId = chocholate.getId();
		this.quantity = chochoInstance.getQuantity();
		this.maxQuantity = quantity + chocholateQuantity;
		
		this.totalPrice = price * quantity;
	}
	
	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getMaxQuantity() {
		return maxQuantity;
	}

	public void setMaxQuantity(int maxQuantity) {
		this.maxQuantity = maxQuantity;
	}

	public int getChocholateQuantity() {
		return chocholateQuantity;
	}

	public void setChocholateQuantity(int chocholateQuantity) {
		this.chocholateQuantity = chocholateQuantity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public ChocholateKind getKind() {
		return kind;
	}

	public void setKind(ChocholateKind kind) {
		this.kind = kind;
	}

	public int getFactoryId() {
		return factoryId;
	}

	public void setFactoryId(int factoryId) {
		this.factoryId = factoryId;
	}

	public ChocholateType getType() {
		return type;
	}

	public void setType(ChocholateType type) {
		this.type = type;
	}

	public double getGrams() {
		return grams;
	}

	public void setGrams(double grams) {
		this.grams = grams;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public int getChocholateId() {
		return chocholateId;
	}

	public void setChocholateId(int chocholateId) {
		this.chocholateId = chocholateId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
