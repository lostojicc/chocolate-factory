package models;

import serializer.ISerializable;

public class CustomerType implements IIdentifiable, ISerializable, IsDeletable {
	private int id;
	private CustomerTypeName typeName;
	private double discount;
	private int pointsNeeded;
	@Override
	public String[] ToCSV() {
		// TODO Auto-generated method stub
		String[] values = {
				Integer.toString(id),
				typeName.name(),
				Double.toString(discount),
				Integer.toString(pointsNeeded)
		};
		return values;
	}

	@Override
	public void FromCSV(String[] values) {
		// TODO Auto-generated method stub
		this.id = Integer.parseInt(values[0]);
		this.typeName = CustomerTypeName.valueOf(values[1]);
		this.discount = Double.parseDouble(values[2]);
		this.pointsNeeded = Integer.parseInt(values[3]);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CustomerTypeName getTypeName() {
		return typeName;
	}

	public void setTypeName(CustomerTypeName typeName) {
		this.typeName = typeName;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public int getPointsNeeded() {
		return pointsNeeded;
	}

	public void setPointsNeeded(int pointsNeeded) {
		this.pointsNeeded = pointsNeeded;
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
