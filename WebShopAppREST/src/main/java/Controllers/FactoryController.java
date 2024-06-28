package Controllers;

import java.nio.ReadOnlyBufferException;
import java.util.ArrayList;
import java.util.Collection;

import dao.DAO;
import dto.FactoryDTO;
import models.Factory;
import models.Location;
import models.OpenStatus;
import models.User;

public class FactoryController {
	private String contextPath;
	private DAO factoryDao;
	
	private LocationController locationController;
	private UserController userController;
	
	public FactoryController(String contextPath) {
		this.contextPath = contextPath;
		factoryDao = new DAO<Factory>(contextPath, Factory.class);
	}
	
	public void setDependency(LocationController locationController, UserController userController) {
		this.locationController = locationController;
		this.userController = userController;
	}
			
	
	public Collection<Factory> getAll(){
		return factoryDao.GetAll();
	}
	
	public Factory Save(FactoryDTO factory) {
		Location location;
		
		try {
			location = locationController.save(factory.getLocation(), factory.getAddress());
			factory.getLocation().setId(location.getId());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		Factory newFactory = (Factory) factoryDao.Save(factory.toModel());
		User manager = factory.getManager();
		manager.setFactoryId(newFactory.getId());
		
		if(userController.getById(manager.getId()) == null)
			userController.Save(manager);
		else 
			userController.update(manager);
			
		return newFactory;
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
