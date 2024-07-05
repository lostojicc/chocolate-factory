package models;

import serializer.ISerializable;

public class Location implements IIdentifiable, ISerializable, IsDeletable{
	private int id;
	private double latitude;
	private double longitude;
	private int addressId;
	
	public Location() {
		
	}
	
	
	
	public Location(double latitude, double longitude, int addressId) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.addressId = addressId;
	}

	public Location(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	@Override
	public String[] ToCSV() {
		String[] values = {
				Integer.toString(id),
				Double.toString(latitude),
				Double.toString(longitude),
				Integer.toString(addressId)
				
		};
		return values;
	}
	@Override
	public void FromCSV(String[] values) {
		// TODO Auto-generated method stub
		this.id = Integer.parseInt(values[0]);
		this.latitude = Double.parseDouble(values[1]);
		this.longitude = Double.parseDouble(values[2]);
		this.addressId = Integer.parseInt(values[3]);
	}


	public double getLatitude() {
		return latitude;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}



	public double getLongitude() {
		return longitude;
	}



	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}



	public int getAddressId() {
		return addressId;
	}



	public void setAddressId(int addressId) {
		this.addressId = addressId;
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
