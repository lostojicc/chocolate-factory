package models;

import java.time.LocalDateTime;

import serializer.ISerializable;

public class Factory implements IIdentifiable, ISerializable{
	private int id;
	private String name;
	private LocalDateTime openTime;
	private LocalDateTime closeTime;
	private OpenStatus status;
	private String imagePath;
	private double rating;
	
	public Factory() {
		
	}
	
	

	public Factory(String name, LocalDateTime openTime, LocalDateTime closeTime, OpenStatus status, String imagePath,
			double rating) {
		super();
		this.name = name;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.status = status;
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
				imagePath,
				Double.toString(rating)
		};
		return values;
	}

	@Override
	public void FromCSV(String[] values) {
		// TODO Auto-generated method stub
		this.id = Integer.parseInt(values[0]);
		this.name = values[1];
		this.openTime = LocalDateTime.parse(values[2]);
		this.closeTime = LocalDateTime.parse(values[3]);
		this.status = OpenStatus.valueOf(values[4]);
		this.imagePath = values[5];
		this.rating = Double.parseDouble(values[6]);
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

	public LocalDateTime getOpenTime() {
		return openTime;
	}



	public void setOpenTime(LocalDateTime openTime) {
		this.openTime = openTime;
	}



	public LocalDateTime getCloseTime() {
		return closeTime;
	}



	public void setCloseTime(LocalDateTime closeTime) {
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
	
	
	
}
