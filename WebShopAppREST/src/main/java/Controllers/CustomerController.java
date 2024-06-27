package Controllers;

import java.util.ArrayList;

import dao.DAO;
import models.Customer;
import models.CustomerType;
import models.CustomerTypeName;
import models.User;

public class CustomerController {
	private String contextPath;
	private DAO CustomerDAO;
	private DAO CustomerTypeDAO;
	
	private UserController userController;
	
	public void setDependency(UserController userController) {
		this.userController = userController;
	}
	
	public CustomerController(String context) {
		contextPath = context;
		CustomerDAO = new DAO<Customer>(contextPath, Customer.class);
		CustomerTypeDAO = new DAO<CustomerType>(contextPath, CustomerType.class);
	}
	
	public ArrayList<Customer> GetAll(){
		return CustomerDAO.GetAll();
	}
	
	public void Save(Customer customer) {
		CustomerDAO.Save(customer);
	}
	
	public Customer GetByUserId(int userId) {
		for(Customer c: this.GetAll()) {
			if(c.getUserId() == userId) {
				return c;
			}
		}
		
		return null;
	}
	
	public CustomerType GetById(int customerTypeId) {
		return (CustomerType) CustomerTypeDAO.GetById(customerTypeId);
	}
	
	public CustomerTypeName GetCustomerTypeNameByCustomer(Customer customer) {
		return this.GetById(customer.getCustomerTypeId()).getTypeName();
	}
	
}
