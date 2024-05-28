package Controllers;

import dao.DAO;
import models.Factory;
import models.Location;

public class LocationController {
	private String contextPath;
	private DAO locationDao;
	
	public LocationController(String contextPath) {
		this.contextPath = contextPath;
		locationDao = new DAO<Location>(contextPath, Location.class);
	}
	
	public Location getById(int id) {
		return (Location) locationDao.GetById(id);
	}
}
