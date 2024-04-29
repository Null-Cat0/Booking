package es.unex.pi.resources;


import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import es.unex.pi.model.*;
import es.unex.pi.resources.exceptions.CustomBadRequestException;
import es.unex.pi.dao.*;

@Path("/services")
public class ServiceResource {

	Logger logger = Logger.getLogger(FavouriteResource.class.getName());

	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Service> getServicesJSON(@Context HttpServletRequest request) {
		logger.info("getServicesJSON");

		List<Service> services = null;
		Connection conn = (Connection) sc.getAttribute("dbConn");

		ServicesDAO sDao = new JDBCServicesDAOImpl();
		sDao.setConnection(conn);

		services = sDao.getAll();
		return services;
	}
	@GET
	@Path("/{propertyId: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Service> getServicesJSON(@PathParam("propertyId") long propertyId, @Context HttpServletRequest request) {
		logger.info("getServicesJSON");

		List<Service> services = null;
		Connection conn = (Connection) sc.getAttribute("dbConn");

		ServicesDAO sDao = new JDBCServicesDAOImpl();
		sDao.setConnection(conn);

		services = sDao.getAllInProperty(propertyId);
		return services;
	}
}
