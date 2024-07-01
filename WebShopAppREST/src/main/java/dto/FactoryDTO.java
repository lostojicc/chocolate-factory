package dto;

import java.time.LocalTime;

import Controllers.UserController;
import models.Address;
import models.Factory;
import models.Location;
import models.OpenStatus;
import models.User;

public class FactoryDTO {
	private int id;
	private String name;
	private LocalTime openTime;
	private LocalTime closeTime;
	private OpenStatus status;
	private String imagePath;
	private double rating;
	private Location location;
	private Address address;
	private User manager;
	
	public FactoryDTO() {
		
	}
	
	public FactoryDTO(int id, String name, LocalTime openTime, LocalTime closeTime, OpenStatus status, String imagePath,
			double rating, Location location, Address address) {
		super();
		this.id = id;
		this.name = name;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.status = status;
		this.imagePath = imagePath;
		this.rating = rating;
		this.location = location;
		this.address = address;
	}
	
	public FactoryDTO(Factory factory, Location location, Address address) {
		this.id = factory.getId();
		this.name = factory.getName();
		this.openTime = factory.getOpenTime();
		this.closeTime = factory.getCloseTime();
		this.status = factory.getStatus();
		this.imagePath = factory.getImagePath();
		this.rating = factory.getRating();
		this.location = location;
		this.address = address;
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

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}

	public Factory toModel() {
		return new Factory(name, openTime, closeTime, status, location.getId(), imagePath, rating);
	}
}
