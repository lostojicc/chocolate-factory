package Controllers;

import dao.DAO;
import models.Address;
import models.Factory;

public class AddressController {
	private String contextPath;
	private DAO addressDao;
	
	public AddressController(String contextPath) {
		this.contextPath = contextPath;
		addressDao = new DAO<Address>(contextPath, Address.class);
	}
	
	public Address getById(int id) {
		return (Address) addressDao.GetById(id);
	}
}
