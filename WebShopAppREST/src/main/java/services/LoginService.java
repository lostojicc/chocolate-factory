package services;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.nimbusds.jose.JOSEException;

import Controllers.ChocholateController;
import Controllers.ControllersInjector;
import Controllers.UserController;
import models.User;
import utils.JWTUtils;

@Path("")
public class LoginService {
	@Context
	ServletContext ctx;
	
	public LoginService() {
		
	}
	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		if (ctx.getAttribute("controllers") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("controllers", new ControllersInjector(contextPath));
		}
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(User user) {
		ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");
		
		UserController usCo = conInjector.getController(UserController.class);
		
		User loggedUser = usCo.GetByUsername(user.getUsername());
		
		if(loggedUser == null) 
			return Response.status(Response.Status.NOT_FOUND).entity("Username doesn't exist").build();
		
		if(!usCo.IsPasswordCorrect(loggedUser, user.getPassword()))
			return Response.status(Response.Status.UNAUTHORIZED).entity("Incorrect password!").build();
		
		if(loggedUser.isBlocked())
			return Response.status(Response.Status.UNAUTHORIZED).entity("This account has been blocked.").build();
		
		try {
            String token = JWTUtils.generateToken(loggedUser.getUsername(), loggedUser.getRole());
            
            return Response.status(Response.Status.OK)
                           .entity(token)
                           .build();
        } catch (JOSEException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Failed to generate JWT token")
                           .build();
        }
	}
	
	
	@POST
	@Path("/logout")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void logout(@Context HttpServletRequest request) {
		request.getSession().invalidate();
	}
	
	@GET
	@Path("/currentUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User login(@Context HttpServletRequest request) {
		return (User) request.getSession().getAttribute("user");
	}
}
