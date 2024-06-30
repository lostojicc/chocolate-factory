package dto;

import models.ChocholateKind;
import models.ChocholateType;
import models.OpenStatus;

public class FactorySearchDTO {
	private String name;
	private String chocolate;
	private String location;
	private double rating;
	private ChocholateKind chocolateKind;
	private ChocholateType chocolateType;
	private OpenStatus status;
	
	public FactorySearchDTO() {
		
	}
	
	public FactorySearchDTO(String name, String chocolate, String location, double rating, ChocholateKind chocolateKind,
			ChocholateType chocolateType, OpenStatus status) {
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
	public ChocholateKind getChocolateKind() {
		return chocolateKind;
	}
	public void setChocolateKind(ChocholateKind chocolateKind) {
		this.chocolateKind = chocolateKind;
	}
	public ChocholateType getChocolateType() {
		return chocolateType;
	}
	public void setChocolateType(ChocholateType chocolateType) {
		this.chocolateType = chocolateType;
	}
	public OpenStatus getStatus() {
		return status;
	}
	public void setStatus(OpenStatus status) {
		this.status = status;
	}
	
	
}
