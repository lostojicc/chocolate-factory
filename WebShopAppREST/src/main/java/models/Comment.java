package models;

import serializer.ISerializable;

public class Comment implements ISerializable, IIdentifiable, IsDeletable {
	private int id;
	private int userId;
	private int factoryId;
	private String text;
	private int grade;
	private CommentState state;
	
	public Comment() {
		super();
	}

	public Comment(int userId, int factoryId, String text, int grade, CommentState state) {
		super();
		this.userId = userId;
		this.factoryId = factoryId;
		this.text = text;
		this.grade = grade;
		this.state = state;
	}

	@Override
	public String[] ToCSV() {
		String[] values = {
			Integer.toString(id),
			Integer.toString(userId),
			Integer.toString(factoryId),
			text,
			Integer.toString(grade),
			state.name()
		};
		return values;
	}

	@Override
	public void FromCSV(String[] values) {
		// TODO Auto-generated method stub
		id = Integer.parseInt(values[0]);
		userId = Integer.parseInt(values[1]);
		factoryId = Integer.parseInt(values[2]);
		text = values[3];
		grade = Integer.parseInt(values[4]);
		state = CommentState.valueOf(values[5]);
	}
	
	public CommentState getState() {
		return state;
	}

	public void setState(CommentState state) {
		this.state = state;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getFactoryId() {
		return factoryId;
	}

	public void setFactoryId(int factoryId) {
		this.factoryId = factoryId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public Boolean isDeleted() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDeleted(Boolean isDeleted) {
		// TODO Auto-generated method stub
		
	}
}
