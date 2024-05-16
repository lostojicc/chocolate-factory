package models;

import java.text.ParseException;
import java.util.Date;

import serializer.ISerializable;
import utils.DateUtils;

public class User implements IIdentifiable, ISerializable {
	protected int id;
	protected String userName;
	protected String password;
	protected String name;
	protected String surname;
	protected Gender gender;
	protected Date dateOfBirth;
	protected UserRole role;
	// protected?
	
	public User() {
		
	}

	public User(int id, String userName, String password, String name, String surname, Gender gender, Date dateOfBirth,
			UserRole role) {
		super();
		this.id = id;
		this.userName = userName;
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
				userName,
				password,
				name,
				surname,
				gender.name(),
				DateUtils.format(dateOfBirth),
				role.name()
		};
		return values;
	}

	@Override
	public void FromCSV(String[] values) {
		this.id = Integer.parseInt(values[0]);
		this.userName = values[1];
		this.password = values[2];
		this.name = values[3];
		this.surname = values[4];
		this.gender = Gender.valueOf(values[5]);
		try {
			this.dateOfBirth = DateUtils.parse(values[6]);
        } catch (ParseException e) {
            e.printStackTrace();
        }	
		this.role = UserRole.valueOf(values[7]);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}
}
