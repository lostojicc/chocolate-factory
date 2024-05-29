package models;

import serializer.ISerializable;

public class Location implements IIdentifiable, ISerializable{
	private int id;
	private double latitude;
	private double longitute;
	private int addressId;
	
	public Location() {
		
	}
	
	
	
	public Location(double latitude, double longitute, int addressId) {
		super();
		this.latitude = latitude;
		this.longitute = longitute;
		this.addressId = addressId;
	}



	@Override
	public String[] ToCSV() {
		String[] values = {
				Integer.toString(id),
				Double.toString(latitude),
				Double.toString(longitute),
				Integer.toString(addressId)
				
		};
		return values;
	}
	@Override
	public void FromCSV(String[] values) {
		// TODO Auto-generated method stub
		this.id = Integer.parseInt(values[0]);
		this.latitude = Double.parseDouble(values[1]);
		this.longitute = Double.parseDouble(values[2]);
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



	public double getLongitute() {
		return longitute;
	}



	public void setLongitute(double longitute) {
		this.longitute = longitute;
	}



	public int getAddressId() {
		return addressId;
	}



	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
	
}
