package services;

import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import Controllers.ControllersInjector;
import Controllers.UserController;
import dao.DAO;
import models.Chocholate;
import models.User;

@Path("/register")
public class RegisterService {
	@Context
	ServletContext ctx;
	
	public RegisterService() {
	}
	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira vise puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("controllers") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("controllers", new ControllersInjector(contextPath));
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> getUsers(User user) {
		ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");
		
		UserController userCont = conInjector.getController(UserController.class);
		
		return userCont.GetAll();
	}
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean getChocholate(User user) {
		ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");
		
		UserController userCont = conInjector.getController(UserController.class);
		
		return true;
	}
}
