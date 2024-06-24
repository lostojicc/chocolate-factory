package services;

import java.io.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.io.File;

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
import Controllers.ControllersInjector;
import models.Chocholate;
import models.UserRole;
import utils.JWTUtils;

import org.glassfish.jersey.media.multipart.MultiPartFeature;

import com.nimbusds.jwt.JWTClaimsSet;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path("/chocholate")
public class ChocholateService {
	@Context
	ServletContext ctx;
	
	public ChocholateService() {
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
	public Collection<Chocholate> getProducts() {
		ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");
		
		ChocholateController chochoContr = conInjector.getController(ChocholateController.class);
		return chochoContr.GetAll();
	}
	
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addChocoalate(Chocholate chocolate, @HeaderParam("Authorization") String authorizationHeader) {
		if(!JWTUtils.IsRoleCorrect(authorizationHeader, UserRole.Manager))
			return Response.status(Response.Status.UNAUTHORIZED).build();
		
		ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");
		
		ChocholateController chochoContr = conInjector.getController(ChocholateController.class);
		
		if(!chochoContr.ValidateChocolate(chocolate)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		chochoContr.Save(chocolate);
		return Response.ok().build();
	}
	
	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateChocoalate(Chocholate chocolate) {
		ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");
		
		ChocholateController chochoContr = conInjector.getController(ChocholateController.class);
		
		Chocholate oldChocholate =  chochoContr.GetById(chocolate.getId());
		if(oldChocholate == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		if(!chochoContr.ValidateChocolate(chocolate)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		if(!chocolate.getImagePath().equals(oldChocholate.getImagePath())) {
			String filePath = ctx.getRealPath("") + "images" + oldChocholate.getImagePath().split("images")[1];
	        File file = new File(filePath);
	        file.delete();
		}
		chochoContr.Update(chocolate);
		
		return Response.ok().build();	
	}
	
	@DELETE
	@Path("/delete/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteChocolate(@PathParam("id") int id) {
		ControllersInjector conInjector = (ControllersInjector) ctx.getAttribute("controllers");
		
		ChocholateController chochoContr = conInjector.getController(ChocholateController.class);
		if(chochoContr.Delete(id))
			return Response.ok().build();
		else 
			return Response.status(Status.BAD_REQUEST).build();
	}
}