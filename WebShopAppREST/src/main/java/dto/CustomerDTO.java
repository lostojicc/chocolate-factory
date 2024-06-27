package dto;

import java.time.LocalDate;

import models.CustomerTypeName;
import models.Gender;
import models.User;
import models.UserRole;

public class CustomerDTO {
	private int id;
	private String username;
	private String password;
	private String name;
	private String surname;
	private Gender gender;
	private LocalDate dateOfBirth;
	private UserRole role;
	private int points;
	private CustomerTypeName typeName;
	
	public CustomerDTO() {
		
	}
	
	public CustomerDTO(User user, int points, CustomerTypeName typeName) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.name = user.getName();
		this.surname = user.getSurname();
		this.gender = user.getGender();
		this.dateOfBirth = user.getDateOfBirth();
		this.role = user.getRole();
		this.points = points;
		this.typeName = typeName;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public UserRole getRole() {
		return role;
	}
	public void setRole(UserRole role) {
		this.role = role;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public CustomerTypeName getTypeName() {
		return typeName;
	}
	public void setTypeName(CustomerTypeName typeName) {
		this.typeName = typeName;
	}
}
