package services;

import java.nio.file.attribute.UserPrincipalLookupService;
import javax.ws.rs.core.Response;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
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

@Path("/user")
public class UserService {
	@Context
	ServletContext ctx;
	
	public UserService() {
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
	@Path("/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> getUsers() {
		ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");
		
		UserController userCont = conInjector.getController(UserController.class);
		
		return userCont.GetAll();
	}
	
	@POST
	@Path("/register")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response RegisterNewUser(User user) {
		ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");
		
		UserController userCont = conInjector.getController(UserController.class);
		
		if(userCont.GetByUsername(user.getUsername()) != null) {
			return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Bad Request: User with given username already exists.")
                    .build();
		}
		
		userCont.Save(user);
		
		String message = "User registered successfully.";
        return Response.ok(message).build();
	}
}
