package Controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.naming.ldap.ManageReferralControl;

import dao.DAO;
import dto.UserDTO;
import dto.UserSearchDTO;
import models.Customer;
import models.CustomerType;
import models.CustomerTypeName;
import models.Factory;
import models.Gender;
import models.User;
import models.UserRole;

public class UserController{
	
	private String contextPath;
	private DAO UserDao;
	private CustomerController customerController;
	
	public void setDependency(CustomerController customerController) {
		this.customerController = customerController;
	}
	
	public UserController(String context) {
		contextPath = context;
		UserDao = new DAO<User>(contextPath, User.class);
	}
	public ArrayList<User> GetAll(){
		ArrayList<User> allUsers = UserDao.GetAll();
		ArrayList<User> users = new ArrayList<User>();
		
		for (User user : allUsers) {
			if(user.getRole() == UserRole.Worker && user.isDeleted())
				continue;
			users.add(user);
		}
		
		return users;
	}
	
	private Collection<User> getManagers(){
		Collection<User> managers = new ArrayList<User>();
		
		for (User user : GetAll()) {
			if(user.getRole() == UserRole.Manager)
				managers.add(user);
		}
		
		return managers;
	}
	
	public User getManagerByFactoryId(int factoryId) {
		for (User manager : getManagers()) {
			if(manager.getFactoryId() == factoryId)
				return manager;
		}
		
		return null;
	}
	
	public User getById(int id) {
		return (User) UserDao.GetById(id);
	}
	
	public Boolean Update(User user) {
		return UserDao.Update(user);
	}
	
	public boolean delete(int userId) {
		return UserDao.Delete(getById(userId));
	}
	
	public User GetByUsername(String username) {
		for(User user: this.GetAll()) {
			if(user.getUsername().equals(username)) {
				return user;
			}
		}
		
		return null;
	}
	
	public boolean IsPasswordCorrect(User loggedUser, String password) {
		return loggedUser.getPassword().equals(password);
	}
	
	public void Save(User user) {
		UserDao.Save(user);
	}
	
	public Boolean CheckUserValid(User user) {
		if(user.getName() == null || user.getName().equals("")) {
			return false;
		}
		if(user.getPassword() == null || user.getPassword().equals("") || !user.getPassword().matches("^.{1,10}$")) {
			return false;
		}
		if(user.getUsername() == null || user.getUsername().equals("")) {
			return false;
		}
		if(user.getSurname() == null || user.getSurname().equals("")) {
			return false;
		}
		if(user.getDateOfBirth() == null) {
			return false;
		}
		
		return true;
	}
	
	private boolean matchesName(User user, UserSearchDTO search) {
		return user.getName().toLowerCase().contains(search.getName().toLowerCase()) || search.getName().isBlank();
	}
	
	private boolean matchesSurname(User user, UserSearchDTO search) {
		return user.getSurname().toLowerCase().contains(search.getSurname().toLowerCase()) || search.getSurname().isBlank();
	}
	
	private boolean matchesUsername(User user, UserSearchDTO search) {
		return user.getUsername().toLowerCase().contains(search.getUsername().toLowerCase()) || search.getUsername().isBlank();
	}
	
	private boolean matchesSus(User user, UserSearchDTO search) {
		if(!search.isSus())
			return true;
		
		if(user.getRole() == UserRole.Customer) {
			Customer customer = customerController.GetByUserId(user.getId());
			return customer.isSuspicious();
		}
		
		return false;
	}
	
	private boolean matchesType(User user, UserSearchDTO search) {
		if(search.getType().isBlank())
			return true;
		
		if(user.getRole() == UserRole.Customer) {
			Customer customer = customerController.GetByUserId(user.getId());
			return customerController.GetCustomerTypeNameByCustomer(customer).toString().equals(search.getType());
		}
		
		return false;
	}
	
	private boolean matchesSearch(User user, UserSearchDTO search) {
		return matchesName(user, search) &&
				matchesSurname(user, search) &&
				matchesUsername(user, search) &&
				matchesRole(user, search) &&
				matchesBlocked(user, search) &&
				matchesSus(user, search) &&
				matchesType(user, search);
	}
	
	private boolean matchesRole(User user, UserSearchDTO search) {
		if(search.getRole().isBlank())
			return true;
		
		return user.getRole() == UserRole.valueOf(search.getRole());
	}
	
	private boolean matchesBlocked(User user, UserSearchDTO search) {
		if(search.isBlocked())
			return user.isBlocked();
		return true;
	}
	
	public Collection<User> getWorkersForFactory(int factoryId){
		Collection<User> workers = new ArrayList<User>();
		
		for (User user : GetAll()) {
			if(user.getRole() == UserRole.Worker && user.getFactoryId() == factoryId && !user.isDeleted())
				workers.add(user);
		}
		
		return workers;
	}
	
	public Collection<UserDTO> getSearched(UserSearchDTO search){
		Collection<UserDTO> users = new ArrayList<UserDTO>();
		
		for (User user : GetAll()) {
			if(matchesSearch(user, search) && !user.isDeleted()) {
				int points = 0;
				if(user.getRole() == UserRole.Customer) {
					Customer customer = customerController.GetByUserId(user.getId());
					points = customer.getPoints();
				}
				users.add(new UserDTO(user, points));
			}	
		}
		
		return users;
	}
	
	public ArrayList<User> getFreeManagers(){
		ArrayList<User> managers = new ArrayList<>();
		
		for (User user : GetAll()) {
			if(user.getRole() == UserRole.Manager && user.getFactoryId() == 0)
				managers.add(user);
		}
		
		return managers;
	}
	
	public boolean update(User user) {
		return UserDao.Update(user);
	}
	
	public int GetFactoryIdByUsername(String username) {
		User user = this.GetByUsername(username);
		
		return user.getFactoryId();
	}
	
	public boolean blockUser(int id) {
		User user = getById(id);
		
		user.setBlocked(!user.isBlocked());
		
		return UserDao.Update(user);
	}
}
