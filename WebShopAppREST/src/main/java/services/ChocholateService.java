package services;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import dao.DAO;
import dao.ProductDAO;
import models.Chocholate;
import models.Product;

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
		if (ctx.getAttribute("chocholateDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("chocholateDAO", new DAO<Chocholate>(contextPath,Chocholate.class));
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Chocholate> getProducts() {
		DAO dao = (DAO) ctx.getAttribute("chocholateDAO");
		return dao.GetAll();
	}
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Chocholate getChocholate(Chocholate chocholate) {
		DAO dao = (DAO) ctx.getAttribute("chocholateDAO");
		return (Chocholate) dao.Save(chocholate);
	}
		
}