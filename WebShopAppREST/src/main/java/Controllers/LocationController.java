package Controllers;

import dao.DAO;
import models.Address;
import models.Factory;
import models.Location;

public class LocationController {
	private String contextPath;
	private DAO locationDao;
	private AddressController addressController;
	
	public void setDependency(AddressController addressController) {
		this.addressController = addressController;
	}
	
	public LocationController(String contextPath) {
		this.contextPath = contextPath;
		locationDao = new DAO<Location>(contextPath, Location.class);
	}
	
	public Location getById(int id) {
		return (Location) locationDao.GetById(id);
	}
	
	public Location save(Location location, Address address) {
		try {
			Address savedAddress = addressController.save(address);
			location.setAddressId(savedAddress.getId());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return (Location) locationDao.Save(location);
	}
}
