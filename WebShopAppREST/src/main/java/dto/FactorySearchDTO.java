package dto;

import models.ChocholateKind;
import models.ChocholateType;
import models.OpenStatus;

public class FactorySearchDTO {
	private String name;
	private String chocolate;
	private String location;
	private double rating;
	private String chocolateKind;
	private String chocolateType;
	private String status;
	
	public FactorySearchDTO() {
		
	}
	
	public FactorySearchDTO(String name, String chocolate, String location, double rating, String chocolateKind,
			String chocolateType, String status) {
		super();
		this.name = name;
		this.chocolate = chocolate;
		this.location = location;
		this.rating = rating;
		this.chocolateKind = chocolateKind;
		this.chocolateType = chocolateType;
		this.status = status;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getChocolate() {
		return chocolate;
	}
	public void setChocolate(String chocolate) {
		this.chocolate = chocolate;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public String getChocolateKind() {
		return chocolateKind;
	}
	public void setChocolateKind(String chocolateKind) {
		this.chocolateKind = chocolateKind;
	}
	public String getChocolateType() {
		return chocolateType;
	}
	public void setChocolateType(String chocolateType) {
		this.chocolateType = chocolateType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
