package dao;

import java.util.ArrayList;

public interface IDao<T> {

	ArrayList<T> GetAll();
	
	T Save(T item);
	
	Boolean Update(T item);
	
	Boolean Delete(T item);
	
	T GetById(int id);
}
