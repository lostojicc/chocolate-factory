package services;

import java.nio.file.attribute.UserPrincipalLookupService;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import Controllers.ControllersInjector;
import Controllers.CustomerController;
import Controllers.ShoppingCartController;
import Controllers.UserController;
import dao.DAO;
import dto.CustomerDTO;
import models.Chocholate;
import models.Customer;
import models.CustomerTypeName;
import models.ShoppingCart;
import models.User;
import models.UserRole;
import utils.JWTUtils;

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
	
	@GET
	@Path("/getFreeManagers")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> getFreeManagers(){
		ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");
		
		UserController userCont = conInjector.getController(UserController.class);
		
		return userCont.getFreeManagers();
	}
	
	@POST
	@Path("/register")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response RegisterNewUser(User user) {
		ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");
		
		UserController userCont = conInjector.getController(UserController.class);
		CustomerController customerController = conInjector.getController(CustomerController.class);
		
		if(userCont.GetByUsername(user.getUsername()) != null) {
			return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Username")
                    .build();
		}
		
		if(!userCont.CheckUserValid(user)) {
			return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Bad Request: failed to register user")
                    .build();
		}
		
		userCont.Save(user);
		
		Customer newCustomer = new Customer();
		newCustomer.setPoints(0);
		newCustomer.setUserId(user.getId());
		newCustomer.setCustomerTypeId(1); //postaje none
		customerController.Save(newCustomer);
		
		String message = "User registered successfully.";
        return Response.ok(message).build();
	}
	
	@GET
	@Path("/get/{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response GetCartByUsername(@PathParam("username") String username, @HeaderParam("Authorization") String authorizationHeader) {
		if(!JWTUtils.IsRoleCorrect(authorizationHeader, UserRole.Customer))
			return Response.status(Response.Status.UNAUTHORIZED).build();
		
		ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");
		UserController userController = conInjector.getController(UserController.class);
		CustomerController customerController = conInjector.getController(CustomerController.class);
		
		User user = userController.GetByUsername(username);
		
		if(user == null) {
			return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Bad Request: failed to get user")
                    .build();
		}
		
		Customer customer = customerController.GetByUserId(user.getId());
		if(customer == null) {
			return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Bad Request: failed to get customer")
                    .build();
		}
		
		CustomerTypeName customerType = customerController.GetCustomerTypeNameByCustomer(customer);
		
		CustomerDTO customerDTO = new CustomerDTO(user, customer.getPoints(), customerType);
		
		return Response.ok().entity(customerDTO).build();
	}
	
	@POST
	@Path("/updateName/{data}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response UpdateName(User sentUser, @PathParam("data") int data) {
		ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");
		
		UserController userCont = conInjector.getController(UserController.class);
		User user = userCont.getById(sentUser.getId());
		
		if(user == null) {
			return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Bad Request: failed to find user")
                    .build();
		}
		
		if(data == 1)
			user.setName(sentUser.getName());
		if(data == 2)
			user.setSurname(sentUser.getSurname());
		if(data == 3)
			user.setDateOfBirth(sentUser.getDateOfBirth());
		if(data == 4)
			user.setGender(sentUser.getGender());
		if(data == 5)
			user.setUsername(sentUser.getUsername());
		if(data == 6)
			user.setPassword(sentUser.getPassword());
		
		if(!userCont.CheckUserValid(user)) {
			return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Bad Request: name not valid")
                    .build();
		}
		
		if(!userCont.Update(user)) {
			return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Bad Request: failed to update user")
                    .build();
		}
		
		String message = "Users Name updated successfully.";
        return Response.ok(message).build();
	}
	
	
	
}
