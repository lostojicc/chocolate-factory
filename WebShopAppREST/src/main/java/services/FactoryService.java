package services;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.authentication.ResponseAuthenticationException;

import Controllers.AddressController;
import Controllers.ChocholateController;
import Controllers.ControllersInjector;
import Controllers.FactoryController;
import Controllers.LocationController;
import models.Address;
import models.Chocholate;
import models.Factory;
import models.Location;

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
		return controller.getAll();
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
    public Location getLocation(@PathParam("id") int locationId) {
		ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");
		
		LocationController controller = conInjector.getController(LocationController.class);
		return controller.getById(locationId);
    }

    @GET
    @Path("/location/address/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Address getAddress(@PathParam("id") int addressId) {
    	ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");
    	
    	AddressController controller = conInjector.getController(AddressController.class);
    	return controller.getById(addressId);
    }
    
    @GET
	@Path("/chocolates/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Chocholate> getChocolates(@PathParam("id") int factoryId) {
		ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");
		
		ChocholateController controller = conInjector.getController(ChocholateController.class);
		return controller.getByFactoryId(factoryId);
	}
}
