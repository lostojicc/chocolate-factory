package services;

import java.io.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.io.File;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path("/file")
public class FileUploadService {
	@Context
	ServletContext ctx;
	
	public FileUploadService() {
	}
	
	@Path("/image")
	@POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadFile (@FormDataParam("file") InputStream inputStream)
	{
        try {
        	String imageIdent = "glorya " + LocalDate.now().toString() + " "  + LocalTime.now().toString().replaceAll(":", "-") + ".png";
        	String path = ctx.getRealPath("") + "images" + File.separator + imageIdent;
        	saveToFile(inputStream, path);
        	String webPath = "http://localhost:8080/WebShopAppREST/images/" + imageIdent;
            return Response.status(Response.Status.OK).entity(webPath).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
	
    private void saveToFile(InputStream uploadedInputStream, String path) throws IOException {
        try {
        	OutputStream outputStream = new FileOutputStream(new File(path));
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.flush();
            outputStream.close();
        }
        catch(Exception e) {
        	e.printStackTrace();
        };
    }  
}