package models;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;

import serializer.ISerializable;
import utils.DateUtils;

public class User implements IIdentifiable, ISerializable, IsDeletable {
	protected int id;
	protected String username;
	protected String password;
	protected String name;
	protected String surname;
	protected Gender gender;
	protected LocalDate dateOfBirth;
	protected UserRole role;
	protected int factoryId;
	protected boolean isBlocked;
	protected Boolean isDeleted;
	
	public User() {
		isDeleted = false;
	}

	public User(String username, String password, String name, String surname, Gender gender, LocalDate dateOfBirth,
			UserRole role) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.role = role;
	}

	@Override
	public String[] ToCSV() {
		// TODO Auto-generated method stub
		String[] values = {
				Integer.toString(id),
				username,
				password,
				name,
				surname,
				gender.name(),
				dateOfBirth.toString(),
				role.name(),
				Integer.toString(factoryId),
				Boolean.toString(isBlocked),
				Boolean.toString(isDeleted)
		};
		return values;
	}

	@Override
	public void FromCSV(String[] values) {
		this.id = Integer.parseInt(values[0]);
		this.username = values[1];
		this.password = values[2];
		this.name = values[3];
		this.surname = values[4];
		this.gender = Gender.valueOf(values[5]);
		this.dateOfBirth = LocalDate.parse(values[6]);
		this.role = UserRole.valueOf(values[7]);
		this.factoryId = Integer.parseInt(values[8]);
		this.isBlocked = Boolean.parseBoolean(values[9]);
		this.isDeleted = Boolean.parseBoolean(values[10]);
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

	public int getFactoryId() {
		return factoryId;
	}

	public void setFactoryId(int factoryId) {
		this.factoryId = factoryId;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
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
