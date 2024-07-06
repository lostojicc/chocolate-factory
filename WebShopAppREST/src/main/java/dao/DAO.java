package dao;

import java.util.ArrayList;
import models.*;
import serializer.*;

public class DAO<T extends IIdentifiable & ISerializable & IsDeletable> implements IDao<T> {
	protected Serializer<T> _serializer;
	protected ArrayList<T> _items;
	
	public DAO(String contextPath, Class<T> clazz){
		_serializer = new Serializer<T>(contextPath, clazz);
		_items = (ArrayList<T>) _serializer.fromCSV();
	}
	
	@Override
	public ArrayList<T> GetAll() {
		_items = (ArrayList<T>) _serializer.fromCSV();
		return _items;
	}

	@Override
	public T Save(T item) {
		_items = (ArrayList<T>) _serializer.fromCSV();
		item.setId(this.GetNextId());
		
		_items.add(item);
		_serializer.ToCSV(_items);
		
		return item;
	}

	@Override
	public Boolean Update(T item) {
		T oldItem = this.GetById(item.getId());
		
		if(oldItem == null) {
			return false;
		}
		
		int index = _items.indexOf(oldItem);
		_items.remove(index);
		_items.add(index, item);
		_serializer.ToCSV(_items);
		return true;
	}

	@Override
	public Boolean Delete(T item) {
		T foundItem = this.GetById(item.getId());
		
		if(foundItem == null) 
			return false;
		
		foundItem.setDeleted(true);
			
		return Update(foundItem);
	}

	@Override
	public T GetById(int id) {
		_items = (ArrayList<T>) _serializer.fromCSV();
		for(T item:_items) {
			if(item.getId() == id) {
				return item;
			}
		}
		return null;
	}
	
	private int GetNextId() {
		//uradio ucitavanje vec u save
		if(_items.isEmpty()) {
			return 1;
		}
		
		int maxId = 1;
		for(T item:_items) {
			if(item.getId() > maxId) {
				maxId = item.getId();
			}
		}
		
		return maxId+1;
	}

}
