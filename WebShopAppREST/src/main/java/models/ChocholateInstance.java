package models;

import serializer.ISerializable;

public class ChocholateInstance implements IIdentifiable, ISerializable {
	private int id;
	private int cartId;
	private int chocholateId;
	private int quantity;
	private Boolean checkedOut;
	
	public ChocholateInstance() {
		super();
		this.checkedOut = false;
		this.quantity = 0;
	}
	
	public ChocholateInstance(int CartId, int ChocoId, int quant, Boolean checkedOut) {
		super();
		this.cartId = CartId;
		this.chocholateId = ChocoId;
		this.quantity = quant;
		this.checkedOut = checkedOut;
	}
	
	@Override
	public String[] ToCSV() {
		// TODO Auto-generated method stub
		String[] values = {
				Integer.toString(id),
				Integer.toString(cartId),
				Integer.toString(chocholateId),
				Integer.toString(quantity),
				Boolean.toString(checkedOut)
		};
		return values;
	}

	@Override
	public void FromCSV(String[] values) {
		this.id = Integer.parseInt(values[0]);
		this.cartId = Integer.parseInt(values[1]);
		this.chocholateId = Integer.parseInt(values[2]);
		this.quantity = Integer.parseInt(values[3]);
		this.checkedOut = Boolean.valueOf(values[4]);
	}

	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public int getChocholateId() {
		return chocholateId;
	}

	public void setChocholateId(int chocholateId) {
		this.chocholateId = chocholateId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public Boolean getCheckedOut() {
		return checkedOut;
	}

	public void setCheckedOut(Boolean checkedOut) {
		this.checkedOut = checkedOut;
	}
}
