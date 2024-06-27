package Controllers;

import java.time.LocalDate;
import java.util.ArrayList;

import dao.DAO;
import models.Gender;
import models.User;
import models.UserRole;

public class UserController{
	
	private String contextPath;
	private DAO UserDao;
	
	public void setDependency() {
		
	}
	
	public UserController(String context) {
		contextPath = context;
		UserDao = new DAO<User>(contextPath, User.class);
	}
	public ArrayList<User> GetAll(){
		/*User user = new User("kita", "mika", "ludajaja123", "stefan", Gender.Female , LocalDate.now(),
				UserRole.Administrator);
		UserDao.Save(user);*/
		
		return UserDao.GetAll();
	}
	
	public User getById(int id) {
		return (User) UserDao.GetById(id);
	}
	
	public Boolean Update(User user) {
		return UserDao.Update(user);
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
	

}
