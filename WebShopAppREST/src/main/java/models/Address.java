package models;

import serializer.ISerializable;

public class Address implements IIdentifiable, ISerializable{
	private int id;
	private String street;
	private String city;
	private String state;
	private int zip;
	
	public Address() {
		
	}
	
	
	
	public Address(String street, String city, String state, int zip) {
		super();
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}



	@Override
	public String[] ToCSV() {
		String[] values = {
				Integer.toString(id),
				street,
				city,
				state,
				Integer.toString(zip)
		};
		return values;
	}
	@Override
	public void FromCSV(String[] values) {
		// TODO Auto-generated method stub
		this.id = Integer.parseInt(values[0]);
		this.street = values[1];
		this.city = values[2];
		this.state = values[3];
		this.zip = Integer.parseInt(values[4]);
	}



	public String getStreet() {
		return street;
	}



	public void setStreet(String street) {
		this.street = street;
	}



	public String getCity() {
		return city;
	}



	public void setCity(String city) {
		this.city = city;
	}



	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
	}



	public int getZip() {
		return zip;
	}



	public void setZip(int zip) {
		this.zip = zip;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}


	
	
}
