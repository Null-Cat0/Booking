package es.unex.pi.resources;

import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.dao.*;
import es.unex.pi.model.Property;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;

@Path("/properties")
public class PropertiesResource {

	Logger logger = Logger.getLogger(PropertiesResource.class.getName());
	
	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Property> getPropertiesJSON(@Context HttpServletRequest request) {
		logger.info("getPropertiesJSON");
		
		List<Property> properties = null;
		Connection conn = (Connection) sc.getAttribute("dbConn");
		
		//PropertyDAO
		PropertyDAO pDao = new JDBCPropertyDAOImpl();
		pDao.setConnection(conn);

		properties = pDao.getAll();		
		return properties;
	}
	
	
}
