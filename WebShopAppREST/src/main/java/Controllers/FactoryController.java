package Controllers;

import java.nio.ReadOnlyBufferException;
import java.util.ArrayList;
import java.util.Collection;

import dao.DAO;
import models.Factory;
import models.OpenStatus;

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
	
	public Collection<Factory> getSorted(){
		Collection<Factory> factories = new ArrayList<Factory>();
		
		for (Factory factory : getAll()) {
			if (factory.getStatus() == OpenStatus.OPEN) 
				factories.add(factory);
		}
		
		for (Factory factory : getAll()) {
			if (factory.getStatus() == OpenStatus.CLOSED) 
				factories.add(factory);
		}
		
		return factories;
	}
	
	public Factory getById(int id) {
		return (Factory) factoryDao.GetById(id);
	}
}
