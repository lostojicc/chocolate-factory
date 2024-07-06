package models;

import java.time.LocalDateTime;
import java.time.LocalTime;

import serializer.ISerializable;

public class Factory implements IIdentifiable, ISerializable, IsDeletable{
	private int id;
	private String name;
	private LocalTime openTime;
	private LocalTime closeTime;
	private OpenStatus status;
	private int locationId;
	private String imagePath;
	private double rating;
	private boolean isDeleted;
	
	public Factory() {
		isDeleted = false;
	}
	
	

	public Factory(String name, LocalTime openTime, LocalTime closeTime, OpenStatus status, int locationId, String imagePath,
			double rating) {
		super();
		this.name = name;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.status = status;
		this.locationId = locationId;
		this.imagePath = imagePath;
		this.rating = rating;
	}

	@Override
	public String[] ToCSV() {
		// TODO Auto-generated method stub
		String[] values = {
				Integer.toString(id),
				name,
				openTime.toString(),
				closeTime.toString(),
				status.toString(),
				Integer.toString(locationId),
				imagePath,
				Double.toString(rating),
				Boolean.toString(isDeleted)
		};
		return values;
	}

	@Override
	public void FromCSV(String[] values) {
		// TODO Auto-generated method stub
		this.id = Integer.parseInt(values[0]);
		this.name = values[1];
		this.openTime = LocalTime.parse(values[2]);
		this.closeTime = LocalTime.parse(values[3]);
		this.status = OpenStatus.valueOf(values[4]);
		this.locationId = Integer.parseInt(values[5]);
		this.imagePath = values[6];
		this.rating = Double.parseDouble(values[7]);
		this.isDeleted = Boolean.parseBoolean(values[8]);
	}

	

	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}

	public LocalTime getOpenTime() {
		return openTime;
	}



	public void setOpenTime(LocalTime openTime) {
		this.openTime = openTime;
	}



	public LocalTime getCloseTime() {
		return closeTime;
	}



	public void setCloseTime(LocalTime closeTime) {
		this.closeTime = closeTime;
	}



	public OpenStatus getStatus() {
		return status;
	}



	public void setStatus(OpenStatus status) {
		this.status = status;
	}



	public String getImagePath() {
		return imagePath;
	}



	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}



	public double getRating() {
		return rating;
	}



	public void setRating(double rating) {
		this.rating = rating;
	}



	public int getLocationId() {
		return locationId;
	}



	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}



	@Override
	public Boolean isDeleted() {
		// TODO Auto-generated method stub
		return isDeleted;
	}



	@Override
	public void setDeleted(Boolean isDeleted) {
		// TODO Auto-generated method stub
		this.isDeleted = isDeleted;
	}
	
	
	
}
