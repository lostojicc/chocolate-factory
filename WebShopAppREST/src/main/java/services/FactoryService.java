package services;

import java.util.ArrayList;
import java.util.Collection;

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

import org.glassfish.jersey.client.authentication.ResponseAuthenticationException;

import Controllers.AddressController;
import Controllers.ChocholateController;
import Controllers.CommentController;
import Controllers.ControllersInjector;
import Controllers.FactoryController;
import Controllers.LocationController;
import Controllers.OrderController;
import Controllers.UserController;
import dto.FactoryDTO;
import dto.FactorySearchDTO;
import models.Address;
import models.Chocholate;
import models.Comment;
import models.Factory;
import models.Location;
import models.User;
import models.UserRole;
import utils.JWTUtils;

@Path("/factory")
public class FactoryService {
	@Context
	ServletContext ctx;
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("controllers") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("controllers", new ControllersInjector(contextPath));
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Factory> getAll() {
		ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");
		
		FactoryController controller = conInjector.getController(FactoryController.class);
		return controller.getSorted();
	}
	
	@GET
	@Path("/getFactoryByUsername/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFactoryByUsername(@PathParam("username") String username) {
		ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");
		
		UserController controller = conInjector.getController(UserController.class);
		
		int factoryId = controller.GetFactoryIdByUsername(username);
		if(factoryId == 0)
			return Response.status(Response.Status.NOT_FOUND).build();	
		
		return Response.ok(factoryId).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@PathParam("id") int id) {
		ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");
		
		FactoryController controller = conInjector.getController(FactoryController.class);
		Factory factory = controller.getById(id);
		
		if(factory != null)
			return Response.ok(factory).build();
		else 
			return Response.status(Response.Status.NOT_FOUND).build();	
	}
	
	@GET
    @Path("/location/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLocation(@PathParam("id") int locationId) {
		ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");
		LocationController controller = conInjector.getController(LocationController.class);
		
		Location location = controller.getById(locationId);
		if(location != null)
			return Response.ok(location).build();
		else 
			return Response.status(Response.Status.NOT_FOUND).build();
    }
	
	@DELETE
	@Path("/delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteFactory(@PathParam("id") int factoryId) {
		ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");
		FactoryController controller = conInjector.getController(FactoryController.class);
		
		if(controller.delete(factoryId))
			return Response.ok().build();
		
		return Response.status(Response.Status.BAD_REQUEST).entity("Could not delete this factory!").build();
	}

    @GET
    @Path("/location/address/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAddress(@PathParam("id") int addressId) {
    	ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");   	
    	AddressController controller = conInjector.getController(AddressController.class);
    	
    	Address address = controller.getById(addressId);
		if(address != null)
			return Response.ok(address).build();
		else 
			return Response.status(Response.Status.NOT_FOUND).build();
    }
    
    @POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addFactory(FactoryDTO factory, @HeaderParam("Authorization") String authorizationHeader) {
		if(!JWTUtils.IsRoleCorrect(authorizationHeader, UserRole.Administrator))
			return Response.status(Response.Status.UNAUTHORIZED).build();
		
		ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");
		FactoryController factoryController = conInjector.getController(FactoryController.class);
		
		Factory savedFactory = factoryController.Save(factory);
		return Response.ok().entity(savedFactory).build();
	}
    
    @GET
	@Path("/chocolates/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Chocholate> getChocolates(@PathParam("id") int factoryId) {
		ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");
		
		ChocholateController controller = conInjector.getController(ChocholateController.class);
		return controller.getByFactoryId(factoryId);
	}
    
    @GET
    @Path("/comments/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getComments(@PathParam("id") int factoryId, @HeaderParam("Authorization") String authorizationHeader) {
    	if(!JWTUtils.IsRoleCorrect(authorizationHeader, UserRole.Manager) && !JWTUtils.IsRoleCorrect(authorizationHeader, UserRole.Administrator))
			return Response.status(Response.Status.UNAUTHORIZED).build();
    	
    	ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");   	
    	CommentController controller = conInjector.getController(CommentController.class);
    	
    	Collection<Comment> comments = controller.getByFactoryId(factoryId);
		if(comments != null)
			return Response.ok(comments).build();
		else 
			return Response.status(Response.Status.NOT_FOUND).build();
    }
    
    @GET
    @Path("/comments/acceptedComments/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAcceptedComments(@PathParam("id") int factoryId) {
    	ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");   	
    	CommentController controller = conInjector.getController(CommentController.class);
    	
    	Collection<Comment> comments = controller.GetAcceptedByFactoryId(factoryId);
		if(comments != null)
			return Response.ok(comments).build();
		else 
			return Response.status(Response.Status.NOT_FOUND).build();
    }
    
    @GET
    @Path("/comments/canUserComment/{username}/{factoryId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response CanUserComment(@PathParam("username") String username,@PathParam("factoryId") int factoryId,@HeaderParam("Authorization") String authorizationHeader) {
    	if(!JWTUtils.IsRoleCorrect(authorizationHeader, UserRole.Customer))
			return Response.status(Response.Status.UNAUTHORIZED).build();
    	
    	ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");   	
    	CommentController commentController = conInjector.getController(CommentController.class);
    	OrderController orderController = conInjector.getController(OrderController.class);
    	UserController controller = conInjector.getController(UserController.class);
    	
    	User user = controller.GetByUsername(username);
    	
    	int numOfComments = commentController.GetNumberOfCommentsOnFactory(user.getId(), factoryId);
    	int numOfOrders = orderController.GetAcceptedByUserAndFactoryId(user.getId(), factoryId).size();
    	
		if(numOfOrders > numOfComments)
			return Response.ok().build();
		else 
			return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    
    @GET
    @Path("/comments/user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") int userId) {
    	ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");   	
    	UserController controller = conInjector.getController(UserController.class);
    	
    	User user = controller.getById(userId);
		if(user != null)
			return Response.ok(user).build();
		else 
			return Response.status(Response.Status.NOT_FOUND).build();
    }
    

    @POST
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getSearched(FactorySearchDTO dto) {
    	ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");
		
		FactoryController controller = conInjector.getController(FactoryController.class);
		
		return Response.ok().entity(controller.getSearched(dto)).build();
    }

    @POST
    @Path("/comments/acceptOrReject/{logic}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response AcceptOrRejectComment(Comment comment,@PathParam("logic") int logic, @HeaderParam("Authorization") String authorizationHeader) {
    	if(!JWTUtils.IsRoleCorrect(authorizationHeader, UserRole.Manager))
			return Response.status(Response.Status.UNAUTHORIZED).build();
    	
    	ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");   	
    	CommentController commentController = conInjector.getController(CommentController.class);
    	
    	if(!commentController.AcceptOrRejectComment(comment.getId(), logic)) {
    		return Response.status(Response.Status.BAD_REQUEST).build();
    	}
    	
		return Response.ok().build();

    }
    
    @POST
    @Path("/comments/addComment/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response AcceptOrRejectComment(Comment comment,@PathParam("username") String username, @HeaderParam("Authorization") String authorizationHeader) {
    	if(!JWTUtils.IsRoleCorrect(authorizationHeader, UserRole.Customer))
			return Response.status(Response.Status.UNAUTHORIZED).build();
    	
    	ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");   	
    	CommentController commentController = conInjector.getController(CommentController.class);
    	UserController controller = conInjector.getController(UserController.class);
    	
    	User user = controller.GetByUsername(username);
    	comment.setUserId(user.getId());
    	
    	commentController.Save(comment);
    	
		return Response.ok().build();

    }
    
    
}
