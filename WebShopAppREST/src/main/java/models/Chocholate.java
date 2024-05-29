package models;

import serializer.ISerializable;

public class Chocholate implements IIdentifiable, ISerializable {
	private int id;
	private String name;
	private double price;
	private ChocholateKind kind;
	private int factoryId;
	//dodati entitet factory?
	private ChocholateType type;
	private double grams;
	private String description;
	private String imagePath;
	private Boolean isAvailable;
	private int quantity;
	
	public Chocholate() {
		super();
	}

	public Chocholate(String name, double price, ChocholateKind kind, int factoryId, ChocholateType type, double grams,
			String description, String imagePath, Boolean isAvailable, int quantity) {
		super();
		this.name = name;
		this.price = price;
		this.kind = kind;
		this.factoryId = factoryId;
		this.type = type;
		this.grams = grams;
		this.description = description;
		this.imagePath = imagePath;
		this.isAvailable = isAvailable;
		this.quantity = quantity;
	}

	@Override
	public String[] ToCSV() {
		// TODO Auto-generated method stub
		String[] values = {
				Integer.toString(id),
				name,
				Double.toString(price),
				kind.name(),
				Integer.toString(factoryId),
				type.name(),
				Double.toString(grams),
				description,
				imagePath,
				isAvailable.toString(),
				Integer.toString(quantity)
		};
		return values;
	}

	@Override
	public void FromCSV(String[] values) {
		this.id = Integer.parseInt(values[0]);
		this.name = values[1];
		this.price = Double.parseDouble(values[2]);
		this.kind = ChocholateKind.valueOf(values[3]);
		this.factoryId = Integer.parseInt(values[4]);
		this.type = ChocholateType.valueOf(values[5]);
		this.grams = Double.parseDouble(values[6]);
		this.description = values[7];
		this.imagePath = values[8];
		this.isAvailable = Boolean.parseBoolean(values[9]);
		this.quantity = Integer.parseInt(values[10]);
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

	public void getFactoryId(int factoryId) {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Boolean getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public void setId(int id) {
		// TODO Auto-generated method stub
		this.id = id;
	}

}
