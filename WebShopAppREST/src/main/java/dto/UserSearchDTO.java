package dto;

import models.CustomerType;

public class UserSearchDTO {
	private String name;
	private String surname;
	private String username;
	private String role;
	private boolean blocked;
	private boolean sus;
	private String type;
	
	public UserSearchDTO() {
	}
	
	
	
	public UserSearchDTO(String name, String surname, String username, String role, boolean blocked, boolean sus) {
		super();
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.role = role;
		this.blocked = blocked;
		this.sus = sus;
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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public boolean isBlocked() {
		return blocked;
	}
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	public boolean isSus() {
		return sus;
	}
	public void setSus(boolean sus) {
		this.sus = sus;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}

}
