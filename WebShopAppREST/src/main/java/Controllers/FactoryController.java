package Controllers;

import java.nio.ReadOnlyBufferException;
import java.util.ArrayList;
import java.util.Collection;

import dao.DAO;
import dto.FactoryDTO;
import dto.FactorySearchDTO;
import models.Address;
import models.Chocholate;
import models.ChocholateKind;
import models.ChocholateType;
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
		ArrayList<Factory> factories = factoryDao.GetAll();
		ArrayList<Factory> newFactories = new ArrayList<Factory>();
		
		for (Factory factory : factories) {
			if(!factory.isDeleted()) {
				System.out.println(factory.getId());
				newFactories.add(factory);
			}
				
		}
		
		
		return newFactories;
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
			System.out.println(factory.getId());
			if (factory.getStatus() == OpenStatus.OPEN) 
				factories.add(factory);
		}
		
		for (Factory factory : getAll()) {
			if (factory.getStatus() == OpenStatus.CLOSED) 
				factories.add(factory);
		}
		
		for (Factory factory : factories) {
			System.out.println("Too" + factory.getId());
		}
		return factories;
	}
	
	private void deleteChocolatesForFactory(int factoryId) {
		for (Chocholate chocolate : chocholateController.getByFactoryId(factoryId)) 
			chocholateController.Delete(chocolate.getId());
	}
	
	private void releaseManager(int factoryId) {
		User manager = userController.getManagerByFactoryId(factoryId);
		
		if(manager != null) {
			manager.setFactoryId(0);
			userController.update(manager);
		}
	}
	
	private void deleteFactoryWorkers(int factoryId) {
		for (User worker : userController.getWorkersForFactory(factoryId)) 
			userController.delete(worker.getId());
	}
	
	public boolean delete(int factoryId) {
		Factory factory = getById(factoryId);
		
		deleteChocolatesForFactory(factoryId);
		releaseManager(factoryId);
		deleteFactoryWorkers(factoryId);
		
		return factoryDao.Delete(factory);
	}
	
	private boolean matchesChocolate(Factory factory, FactorySearchDTO search) {
		ArrayList<Chocholate> chocolates = chocholateController.getByFactoryId(factory.getId());

		if(chocolates.isEmpty() || search.getChocolate().isBlank())
			return true;
		
		for (Chocholate chocholate : chocolates) {
			if(chocholate.getName().toLowerCase().contains(search.getChocolate().toLowerCase()))
				return true;
		}
		
		return false;
	}
	
	private boolean matchesLocation(Factory factory, FactorySearchDTO search) {
		Location location = locationController.getById(factory.getLocationId());
		Address address = addressController.getById(location.getAddressId());
		
		return address.getCity().toLowerCase().contains(search.getLocation().toLowerCase()) || address.getState().toLowerCase().contains(search.getLocation().toLowerCase()) || search.getLocation().isBlank();
	}
	
	private boolean matchesName(Factory factory, FactorySearchDTO search) {
		return factory.getName().toLowerCase().contains(search.getName().toLowerCase()) || search.getName().isBlank();
	}
	
	private boolean matchesRating(Factory factory, FactorySearchDTO search) {
		return factory.getRating() >= search.getRating();
	}
	
	private boolean matchesChocolateKind(Factory factory, FactorySearchDTO search) {
		ArrayList<Chocholate> chocolates = chocholateController.getByFactoryId(factory.getId());
		
		if(search.getChocolateKind().isBlank())
			return true;

		if(chocolates.isEmpty())
			return false;
		
		for (Chocholate chocholate : chocolates) {
			if(chocholate.getKind() == ChocholateKind.valueOf(search.getChocolateKind()))
				return true;
		}
		
		return false;
	}
	
	private boolean matchesChocolateType(Factory factory, FactorySearchDTO search) {
		ArrayList<Chocholate> chocolates = chocholateController.getByFactoryId(factory.getId());
		
		if(search.getChocolateType().isBlank())
			return true;

		if(chocolates.isEmpty())
			return false;
		
		for (Chocholate chocholate : chocolates) {
			if(chocholate.getType() == ChocholateType.valueOf(search.getChocolateType()))
				return true;
		}
		
		return false;
	}
	
	private boolean matchesStatus(Factory factory, FactorySearchDTO search) {
		if(search.getStatus().isBlank())
			return true;
		
		return factory.getStatus() == OpenStatus.valueOf(search.getStatus());
	}
	
	private boolean matchesSearch(Factory factory, FactorySearchDTO search) {
		return matchesName(factory, search) &&
				matchesChocolate(factory, search) &&
				matchesLocation(factory, search) &&
				matchesRating(factory, search) &&
				matchesChocolateKind(factory, search) &&
				matchesChocolateType(factory, search) &&
				matchesStatus(factory, search);
	}
	
	public Collection<FactoryDTO> getSearched(FactorySearchDTO search){
		Collection<FactoryDTO> factories = new ArrayList<FactoryDTO>();
		
		for (Factory factory : getAll()) {
			if(matchesSearch(factory, search)) {
				Location location = locationController.getById(factory.getLocationId());
				factories.add(new FactoryDTO(factory, location, addressController.getById(location.getAddressId())));
			}
				
		}
		
		return factories;
	}
	
	public Factory getById(int id) {
		return (Factory) factoryDao.GetById(id);
	}
	
	public Boolean Update(Factory factory) {
		return factoryDao.Update(factory);
	}
	
}
