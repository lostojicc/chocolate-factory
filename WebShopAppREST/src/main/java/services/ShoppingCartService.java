package services;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import Controllers.ChocholateController;
import Controllers.ChocholateInstanceController;
import Controllers.ControllersInjector;
import Controllers.CustomerController;
import Controllers.ShoppingCartController;
import Controllers.UserController;
import dto.ChocholateInstanceDTO;
import models.Chocholate;
import models.ChocholateInstance;
import models.CustomerType;
import models.ShoppingCart;
import models.User;
import models.UserRole;
import utils.JWTUtils;

@Path("/shopping-cart")
public class ShoppingCartService {
	@Context
	ServletContext ctx;
	
	public ShoppingCartService() {
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
	@Path("/getCart/{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response GetCartByUsername(@PathParam("username") String username) {
		ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");
		ShoppingCartController shoppingCartContr = conInjector.getController(ShoppingCartController.class);
		
		ShoppingCart shoppingCart = shoppingCartContr.GetByUsername(username);
		if(shoppingCart == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		return Response.ok().entity(shoppingCart).build();
	}
	
	@GET
	@Path("/getDiscount/{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response GetDiscountByUsername(@PathParam("username") String username) {
		ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");
		CustomerController customerContr = conInjector.getController(CustomerController.class); 
		
		CustomerType customerType = customerContr.GetByUsername(username);
		if(customerType == null) {
			return Response.status(Status.BAD_REQUEST).build(); 
		}
		
		return Response.ok().entity(customerType).build();
	}
	
	@GET
	@Path("/getChocholates/{cartId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response GetChocholateInstances(@PathParam("cartId") int cartId ,@HeaderParam("Authorization") String authorizationHeader) {
		if(!JWTUtils.IsRoleCorrect(authorizationHeader, UserRole.Customer))
			return Response.status(Response.Status.UNAUTHORIZED).build();
		
		ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");
		ChocholateInstanceController chochoInstanceController = conInjector.getController(ChocholateInstanceController.class);
		ChocholateController chochoController = conInjector.getController(ChocholateController.class);
		
		ArrayList<ChocholateInstanceDTO> chocholates = new ArrayList<ChocholateInstanceDTO>();
		try {
			for(ChocholateInstance chochoInstance : chochoInstanceController.GetNotCheckedByCartId(cartId)) {
				Chocholate chocho = chochoController.GetById(chochoInstance.getChocholateId());
			
				chocholates.add(new ChocholateInstanceDTO(chocho, chochoInstance));
			}
			
			return Response.ok().entity(chocholates).build();
			
		}catch(Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createNewCart(User sentUser) {
		ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");
		ShoppingCartController shoppingCartContr = conInjector.getController(ShoppingCartController.class);
		UserController userControler = conInjector.getController(UserController.class);
		
		User user = userControler.GetByUsername(sentUser.getUsername());
		if(user.getRole() != UserRole.Customer) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		
		if(!shoppingCartContr.Save(new ShoppingCart(user.getId(), 0)))
			return Response.status(Response.Status.BAD_REQUEST).build();
		
		return Response.ok().build();
	}
	
	@POST
	@Path("/addChocholate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addChocholateToCart(ChocholateInstance chochoInstance, @HeaderParam("Authorization") String authorizationHeader) {
		if(!JWTUtils.IsRoleCorrect(authorizationHeader, UserRole.Customer))
			return Response.status(Response.Status.UNAUTHORIZED).build();

		ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");
		ShoppingCartController shoppingCartContr = conInjector.getController(ShoppingCartController.class);
		
		if(shoppingCartContr.IsChocholateFromAnotherFactory(chochoInstance)) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Please clear cart from first factory, then try again").build();
		}
		
		if(!shoppingCartContr.AddToCart(chochoInstance))
			return Response.status(Response.Status.NOT_FOUND).build();
		
		return Response.ok().build();
	}
	
	@POST
	@Path("/updateChocholate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateChocholate(ChocholateInstanceDTO chochoInstanceDTO, @HeaderParam("Authorization") String authorizationHeader) {
		if(!JWTUtils.IsRoleCorrect(authorizationHeader, UserRole.Customer))
			return Response.status(Response.Status.UNAUTHORIZED).build();

		ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");
		ShoppingCartController shoppingCartContr = conInjector.getController(ShoppingCartController.class);
		
		if(!shoppingCartContr.UpdateChocholateInstance(chochoInstanceDTO)) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		
		return Response.ok().build();
	}
	
	@DELETE
	@Path("/deleteChocholate/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateChocholate(@PathParam("id") int id, @HeaderParam("Authorization") String authorizationHeader) {
		if(!JWTUtils.IsRoleCorrect(authorizationHeader, UserRole.Customer))
			return Response.status(Response.Status.UNAUTHORIZED).build();

		ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");
		ShoppingCartController shoppingCartContr = conInjector.getController(ShoppingCartController.class);
		
		if(!shoppingCartContr.DeleteChocholateInstance(id)) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		
		return Response.ok().build();
	}
	
	@POST
	@Path("/checkout")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response CheckoutFromCart(ShoppingCart cart, @HeaderParam("Authorization") String authorizationHeader) {
		if(!JWTUtils.IsRoleCorrect(authorizationHeader, UserRole.Customer))
			return Response.status(Response.Status.UNAUTHORIZED).build();
		
		ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");
		ShoppingCartController shoppingCartContr = conInjector.getController(ShoppingCartController.class);
		
		int returnValue = shoppingCartContr.CheckoutFromCart(cart.getId());
		if(returnValue == 1) {
			return Response.status(Response.Status.NOT_FOUND).entity("Shopping cart not found!").build();
		}
		if(returnValue == 2) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Shopping cart is empty!").build();
		}
		if(returnValue == 3) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Creating an order failed").build();
		}
		
		return Response.ok().build();
	}
	
	
}
