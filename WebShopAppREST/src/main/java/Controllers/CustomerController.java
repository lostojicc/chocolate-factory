package Controllers;

import java.util.ArrayList;

import dao.DAO;
import models.Customer;
import models.CustomerType;
import models.CustomerTypeName;
import models.Order;
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
	
	public Boolean Update(Customer customer) {
		return CustomerDAO.Update(customer);
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
	
	public CustomerType GetByUsername(String username) {
		User user = userController.GetByUsername(username);
		Customer customer = this.GetByUserId(user.getId());
		
		return this.GetById(customer.getCustomerTypeId());
	}
	
	public void AddPointsForOrder(Order order) {
		Customer customer = this.GetByUserId(order.getUserId());
		customer.setPoints(customer.getPoints() + (int) (order.getPrice() / 1000 * 133));
		
		customer.setCustomerTypeId(this.PromoteCustomer(customer.getPoints()));
		
		this.Update(customer);
	}
	
	public int PromoteCustomer(int points) {
		CustomerType bronze = (CustomerType) CustomerTypeDAO.GetById(2);
		CustomerType silver = (CustomerType) CustomerTypeDAO.GetById(3);
		CustomerType gold = (CustomerType) CustomerTypeDAO.GetById(4);
		
		if(points >= gold.getPointsNeeded()) {
			return 4;
		}
		if(points < gold.getPointsNeeded() && points >= silver.getPointsNeeded()) {
			return 3;
		}
		if(points < silver.getPointsNeeded() && points >= bronze.getPointsNeeded()) {
			return 2;
		}
		
		return 1;
	}
	
	public double GetDiscount(int userId) {
		Customer customer = this.GetByUserId(userId);
		CustomerType customerType = this.GetById(customer.getCustomerTypeId());
		
		return customerType.getDiscount();
	}
	
	public void DecreasePointsForOrder(Order order) {
		Customer customer = this.GetByUserId(order.getUserId());
		
		customer.setPoints(customer.getPoints() - (int) (order.getPrice() / 1000 * 133 * 4));
		
		customer.setCustomerTypeId(this.PromoteCustomer(customer.getPoints()));
		
		this.Update(customer);
	}
	
}
