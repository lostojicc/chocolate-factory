package Controllers;

import java.nio.ReadOnlyBufferException;
import java.util.Collection;

import dao.DAO;
import models.Factory;

public class FactoryController {
	private String contextPath;
	private DAO factoryDao;
	
	public FactoryController(String contextPath) {
		this.contextPath = contextPath;
		factoryDao = new DAO<Factory>(contextPath, Factory.class);
	}
	
	public Collection<Factory> getAll(){
		return factoryDao.GetAll();
	}
	
	public Factory getById(int id) {
		return (Factory) factoryDao.GetById(id);
	}
}
