package es.unex.pi.resources;

import es.unex.pi.resources.exceptions.*;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.dao.*;
import es.unex.pi.model.Property;
import es.unex.pi.model.User;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("/properties")
public class PropertyResource {

	Logger logger = Logger.getLogger(PropertyResource.class.getName());

	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;

//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	public List<Property> getPropertiesJSON(@Context HttpServletRequest request) {
//		logger.info("getPropertiesJSON");
//
//		List<Property> properties = null;
//		Connection conn = (Connection) sc.getAttribute("dbConn");
//
//		// PropertyDAO
//		PropertyDAO pDao = new JDBCPropertyDAOImpl();
//		pDao.setConnection(conn);
//
//		properties = pDao.getAll();
//		return properties;
//	}

	@GET
	@Path("/{propertyid: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Property getPropertyJSON(@PathParam("propertyid") long propertyid, @Context HttpServletRequest request) {

		logger.info("getPropertyJSON: " + propertyid);
		Connection conn = (Connection) sc.getAttribute("dbConn");

		Property p = null;
		PropertyDAO pDao = new JDBCPropertyDAOImpl();
		pDao.setConnection(conn);

		p = pDao.get(propertyid);
		if (p != null) {
			logger.info("Property is not null:" + p);
		} else {
			System.out.println("Property with id " + propertyid + " not found");

			throw new CustomNotFoundException("Property with id " + propertyid + " not found");
		}
		return p;

	}

	@GET
	@Path("/{search: [a-zA-z]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Property> getPropertyBySearchJSON(@PathParam("search") String search,
			@Context HttpServletRequest request) {

		logger.info("getPropertyBySearchJSON: " + search);
		PropertyDAO pDao = new JDBCPropertyDAOImpl();
		Connection conn = (Connection) sc.getAttribute("dbConn");
		pDao.setConnection(conn);
		List<Property> properties = null;
		properties = pDao.getAllBySearchName(search);
		properties.addAll(pDao.getAllBySearchCity(search));
		properties.addAll(pDao.getAllBySearchDescription(search));
		return properties;

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Property> getPropertyByUserJSON(@Context HttpServletRequest request) {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		logger.info("getPropertyByUserJSON: " + user.getId());
		List<Property> properties = null;

		Connection conn = (Connection) sc.getAttribute("dbConn");
		PropertyDAO pDao = new JDBCPropertyDAOImpl();
		pDao.setConnection(conn);

		properties = pDao.getAllByUser(user.getId());
		if (properties == null) {
			throw new CustomNotFoundException("User with id " + user.getId() + " not found");
		}
		return properties;

	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response post(Property newProperty, @Context HttpServletRequest request) {

		logger.info("post: " + newProperty);
		Response res = null;
		Connection conn = (Connection) sc.getAttribute("dbConn");

		PropertyDAO pDao = new JDBCPropertyDAOImpl();
		pDao.setConnection(conn);

		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("user");

		if (user != null) {

			newProperty.setIdu((int) user.getId());
			long id = pDao.add(newProperty);
			newProperty.setId(id);
			String message = "Property added";
			res = Response.status(Response.Status.CREATED).entity("{\"status\" : \"200\", \"message\" : \"" + message + "\"}")
					.contentLocation(uriInfo.getAbsolutePathBuilder().path(Long.toString(id)).build()).build();

		} else {
			throw new CustomBadRequestException("User not allowed to add property");
		}

		return res;
	}

	// POST that receives a new property via webform
	@POST
	@Consumes("application/x-www-form-urlencoded")
	public Response post(MultivaluedMap<String, String> formParams, @Context HttpServletRequest request) {

		logger.info("post: " + formParams);

		Connection conn = (Connection) sc.getAttribute("dbConn");
		PropertyDAO pDao = new JDBCPropertyDAOImpl();
		pDao.setConnection(conn);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		Property p = new Property();
		long id;
		try {
			p.setIdu((int) user.getId());
			p.setName(formParams.getFirst("name"));
			p.setDescription(formParams.getFirst("description"));
			p.setAddress(formParams.getFirst("address"));
			p.setCity(formParams.getFirst("city"));

			id = pDao.add(p);
			if (id == -1)
				logger.info("Property not added");
		} catch (Exception e) {
			throw new CustomBadRequestException("Error in form parameters");
		}

		String message = "Property added";

		return Response.status(Response.Status.CREATED).entity(message)
				.contentLocation(uriInfo.getAbsolutePathBuilder().path(Long.toString(id)).build()).build();

	}

	@PUT
	@Path("/{propertyid: [0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response put(@PathParam("propertyid") long propertyid, Property property,
			@Context HttpServletRequest request) {
		Response res = null;
		Connection conn = (Connection) sc.getAttribute("dbConn");

		PropertyDAO pDao = new JDBCPropertyDAOImpl();
		pDao.setConnection(conn);

		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("user");

		System.out.println("Actualizando");
		if (user != null) {
			if (user.getId() == property.getIdu()) {
				pDao.update(property);
				String message = "Property updated";
				res = Response.status(Response.Status.OK)
						.entity("{\"status\" : \"200\", \"message\" : \"" + message + "\"}")
						.contentLocation(uriInfo.getAbsolutePathBuilder().path(Long.toString(propertyid)).build())
						.build();
			} else {
				throw new CustomBadRequestException("User not allowed to update property");
			}
		}

		return res;
	}

	@DELETE
	@Path("/{propertyid: [0-9]+}")
	public Response delete(@PathParam("propertyid") long propertyid, @Context HttpServletRequest request) {
		Response res = null;
		Connection conn = (Connection) sc.getAttribute("dbConn");

		PropertyDAO pDao = new JDBCPropertyDAOImpl();
		pDao.setConnection(conn);

		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("user");

		if (user != null) {
			Property p = pDao.get(propertyid);
			if (p != null) {
				if (user.getId() == p.getIdu()) {
					pDao.delete(propertyid);
					String message = "Property deleted";
					res = Response.status(Response.Status.OK).entity(message).build();
				} else {
					throw new CustomBadRequestException("User not allowed to delete property");
				}
			} else {
				throw new CustomNotFoundException("Property not found");
			}
		}

		return res;
	}
}
