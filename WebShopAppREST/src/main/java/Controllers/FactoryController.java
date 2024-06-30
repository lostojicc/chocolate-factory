package Controllers;

import java.nio.ReadOnlyBufferException;
import java.util.ArrayList;
import java.util.Collection;

import dao.DAO;
import dto.FactoryDTO;
import dto.FactorySearchDTO;
import models.Address;
import models.Chocholate;
import models.Factory;
import models.Location;
import models.OpenStatus;
import models.User;

public class FactoryController {
	private String contextPath;
	private DAO factoryDao;
	
	private LocationController locationController;
	private UserController userController;
	private ChocholateController chocholateController;
	private AddressController addressController;
	
	public FactoryController(String contextPath) {
		this.contextPath = contextPath;
		factoryDao = new DAO<Factory>(contextPath, Factory.class);
	}
	
	public void setDependency(LocationController locationController, UserController userController, ChocholateController chocholateController, AddressController addressController) {
		this.locationController = locationController;
		this.userController = userController;
		this.chocholateController = chocholateController;
		this.addressController = addressController;
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
	
	private boolean matchesChocolate(Factory factory, FactorySearchDTO search) {
		ArrayList<Chocholate> chocolates = chocholateController.getByFactoryId(factory.getId());

		if(chocolates.isEmpty() && search.getChocolate().isBlank())
			return true;
		
		for (Chocholate chocholate : chocolates) {
			if(chocholate.getName().contains(search.getChocolate()) || search.getChocolate().isBlank())
				return true;
		}
		
		return false;
	}
	
	private boolean matchesLocation(Factory factory, FactorySearchDTO search) {
		Location location = locationController.getById(factory.getLocationId());
		Address address = addressController.getById(location.getAddressId());
		
		return address.getCity().contains(search.getLocation()) || address.getState().contains(search.getLocation()) || search.getLocation().isBlank();
	}
	
	private boolean matchesName(Factory factory, FactorySearchDTO search) {
		return factory.getName().contains(search.getName()) || search.getName().isBlank();
	}
	
	private boolean matchesRating(Factory factory, FactorySearchDTO search) {
		return factory.getRating() >= search.getRating();
	}
	
	private boolean matchesSearch(Factory factory, FactorySearchDTO search) {
		return matchesName(factory, search) &&
				matchesChocolate(factory, search) &&
				matchesLocation(factory, search) &&
				matchesRating(factory, search);
	}
	
	public Collection<Factory> getSearched(FactorySearchDTO search){
		Collection<Factory> factories = new ArrayList<Factory>();
		
		for (Factory factory : getAll()) {
			if(matchesSearch(factory, search))
				factories.add(factory);
		}
		
		return factories;
	}
	
	public Factory getById(int id) {
		return (Factory) factoryDao.GetById(id);
	}
	
}
