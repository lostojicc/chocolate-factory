package services;

import java.util.ArrayList;
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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import Controllers.ChocholateController;
import Controllers.ChocholateInstanceController;
import Controllers.ControllersInjector;
import Controllers.FactoryController;
import Controllers.OrderController;
import Controllers.UserController;
import dto.ChocholateInstanceDTO;
import dto.OrderDTO;
import models.Chocholate;
import models.ChocholateInstance;
import models.Factory;
import models.Order;
import models.User;
import models.UserRole;
import utils.JWTUtils;

@Path("/order")
public class OrderService {
	@Context
	ServletContext ctx;
	
	public OrderService() {
		
	}
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("controllers") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("controllers", new ControllersInjector(contextPath));
		}
	}
	
	@GET
	@Path("/getForUser/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserOrders(@PathParam("username") String username,@HeaderParam("Authorization") String authorizationHeader) {
		if(!JWTUtils.IsRoleCorrect(authorizationHeader, UserRole.Customer))
			return Response.status(Response.Status.UNAUTHORIZED).build();
		
		ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");
		OrderController orderController = conInjector.getController(OrderController.class);
		UserController userController = conInjector.getController(UserController.class);
		FactoryController facContr = conInjector.getController(FactoryController.class);
		
		User user = userController.GetByUsername(username);
		
		if(user == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("User not found").build();
		}
		
		ArrayList<OrderDTO> ordersDTO = new ArrayList<OrderDTO>();
		
		for(Order o : orderController.GetByUserId(user.getId())) {
			Factory fac = facContr.getById(o.getFactoryId());
			ordersDTO.add(new OrderDTO(o, fac.getName() ,user));
		}
		
		return Response.status(Status.OK).entity(ordersDTO).build();
	}
	
	@POST
	@Path("/getChocholatesForOrder")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getChocholatesForOrder(OrderDTO orderDTO,@HeaderParam("Authorization") String authorizationHeader) {
		if(!JWTUtils.IsRoleCorrect(authorizationHeader, UserRole.Customer))
			return Response.status(Response.Status.UNAUTHORIZED).build();
		
		ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");
		OrderController orderController = conInjector.getController(OrderController.class);
		ChocholateInstanceController chochoInstanceController = conInjector.getController(ChocholateInstanceController.class);
		ChocholateController chochoController = conInjector.getController(ChocholateController.class);
		
		ArrayList<ChocholateInstanceDTO> chocholates = new ArrayList<ChocholateInstanceDTO>();
		
		for(int id : orderDTO.getChocholateIds()) {
			ChocholateInstance chochoInstance = chochoInstanceController.GetById(id);
			
			Chocholate chocho = chochoController.GetById(chochoInstance.getChocholateId());
			
			chocholates.add(new ChocholateInstanceDTO(chocho, chochoInstance));
		}
		
		
		return Response.status(Status.OK).entity(chocholates).build();
	}
	
	
}
