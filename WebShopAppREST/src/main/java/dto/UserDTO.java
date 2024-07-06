package dto;

import models.User;
import models.UserRole;

public class UserDTO {
	private int id;
	private String name;
	private String surname;
	private String username;
	private UserRole role;
	private boolean isBlocked;
	private int points;
	
	public UserDTO() {
		points = 0;
	}
	
	public UserDTO(User user, int points) {
		id = user.getId();
		name = user.getName();
		surname = user.getSurname();
		username = user.getUsername();
		role = user.getRole();
		isBlocked = user.isBlocked();
		this.points = points;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
