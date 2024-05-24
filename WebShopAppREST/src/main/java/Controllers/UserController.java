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
		User user = new User("kita", "mika", "ludajaja123", "stefan", Gender.Female , LocalDate.now(),
				UserRole.Administrator);
		UserDao.Save(user);
		
		return UserDao.GetAll();
	}
	

}
