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
import dto.SearchOrderParamsDTO;
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
	@Path("/searchForUser/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response SearchUserOrders(@PathParam("username") String username, SearchOrderParamsDTO searchParams,@HeaderParam("Authorization") String authorizationHeader) {
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
		
		for(Order o : orderController.SearchByUserId(user.getId(), searchParams)) {
			Factory fac = facContr.getById(o.getFactoryId());
			ordersDTO.add(new OrderDTO(o, fac.getName() ,user));
		}
		
		return Response.status(Status.OK).entity(ordersDTO).build();
	}
	
	@POST
	@Path("/searchForManager/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response SearchManagerOrders(@PathParam("username") String username, SearchOrderParamsDTO searchParams,@HeaderParam("Authorization") String authorizationHeader) {
		if(!JWTUtils.IsRoleCorrect(authorizationHeader, UserRole.Manager))
			return Response.status(Response.Status.UNAUTHORIZED).build();
		
		ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");
		OrderController orderController = conInjector.getController(OrderController.class);
		UserController userController = conInjector.getController(UserController.class);
		FactoryController facContr = conInjector.getController(FactoryController.class);
		
		User manager = userController.GetByUsername(username);
		
		if(manager == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("User not found").build();
		}
		
		ArrayList<OrderDTO> ordersDTO = new ArrayList<OrderDTO>();
		
		for(Order o : orderController.SearchByFactoryId(manager.getFactoryId(),searchParams)) {
			Factory fac = facContr.getById(o.getFactoryId());
			User customer = userController.getById(o.getUserId());
			ordersDTO.add(new OrderDTO(o, fac.getName() ,customer));
		}
		
		return Response.status(Status.OK).entity(ordersDTO).build();
	}
	
	
	
	
	@GET
	@Path("/getForManager/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getManagerOrders(@PathParam("username") String username,@HeaderParam("Authorization") String authorizationHeader) {
		if(!JWTUtils.IsRoleCorrect(authorizationHeader, UserRole.Manager))
			return Response.status(Response.Status.UNAUTHORIZED).build();
		
		ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");
		OrderController orderController = conInjector.getController(OrderController.class);
		UserController userController = conInjector.getController(UserController.class);
		FactoryController facContr = conInjector.getController(FactoryController.class);
		
		User manager = userController.GetByUsername(username);
		
		if(manager == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Manager not found").build();
		}
		
		ArrayList<OrderDTO> ordersDTO = new ArrayList<OrderDTO>();
		
		for(Order o : orderController.GetByFactoryId(manager.getFactoryId())) {
			Factory fac = facContr.getById(o.getFactoryId());
			User customer = userController.getById(o.getUserId());
			ordersDTO.add(new OrderDTO(o, fac.getName() ,customer));
		}
		
		return Response.status(Status.OK).entity(ordersDTO).build();
	}
	
	@POST
	@Path("/getChocholatesForOrder")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getChocholatesForOrder(OrderDTO orderDTO,@HeaderParam("Authorization") String authorizationHeader) {
		Boolean logic = false;
		if(JWTUtils.IsRoleCorrect(authorizationHeader, UserRole.Customer))
			logic = true;
			
		
		if(JWTUtils.IsRoleCorrect(authorizationHeader, UserRole.Manager))
			logic = true;
		
		if(!logic) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
		
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
	
	@POST
	@Path("/cancelOrder")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response CancelOrder(OrderDTO orderDTO, @HeaderParam("Authorization") String authorizationHeader) {
		if(!JWTUtils.IsRoleCorrect(authorizationHeader, UserRole.Customer))
			return Response.status(Response.Status.UNAUTHORIZED).build();
		
		ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");
		OrderController orderController = conInjector.getController(OrderController.class);
		
		if(!orderController.CancelOrder(orderDTO.getId())) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		return Response.status(Status.OK).build();
	}
	
	@POST
	@Path("/rejectOrAcceptOrder/{logic}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response RejectOrder(OrderDTO orderDTO, @PathParam("logic") int logic,@HeaderParam("Authorization") String authorizationHeader) {
		if(!JWTUtils.IsRoleCorrect(authorizationHeader, UserRole.Manager))
			return Response.status(Response.Status.UNAUTHORIZED).build();
		
		ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");
		OrderController orderController = conInjector.getController(OrderController.class);
		
		if(!orderController.RejectOrAcceptOrder(orderDTO.getId(), logic)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		return Response.status(Status.OK).build();
	}
	
}
