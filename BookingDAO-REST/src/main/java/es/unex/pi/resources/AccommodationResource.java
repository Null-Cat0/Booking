package es.unex.pi.resources;

import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Consumes;
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

import es.unex.pi.model.*;
import es.unex.pi.dao.*;
import es.unex.pi.resources.exceptions.*;

@Path("/accommodations")
public class AccommodationResource {
	Logger logger = Logger.getLogger(AccommodationResource.class.getName());

	@Context
	ServletContext sc;

	@Context
	UriInfo uriInfo;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Accommodation> getAccommodationsJSON(@Context HttpServletRequest request) {
		logger.info("getAccommodationsJSON");

		List<Accommodation> accommodations = null;
		Connection conn = (Connection) sc.getAttribute("dbConn");

		// AccommodationDAO
		AccommodationDAO aDao = new JDBCAccommodationDAOImpl();
		aDao.setConnection(conn);

		accommodations = aDao.getAll();
		return accommodations;
	}

	@GET
	@Path("/{accommodationid: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Accommodation getAccommodationJSON(@PathParam("accommodationid") long accommodationId,
			@Context HttpServletRequest request) {
		logger.info("getAccommodationJSON: " + accommodationId);
		Connection conn = (Connection) sc.getAttribute("dbConn");

		AccommodationDAO aDao = new JDBCAccommodationDAOImpl();
		aDao.setConnection(conn);

		Accommodation accommodation = aDao.get(accommodationId);

		if (accommodation != null) {

		} else {
			throw new CustomNotFoundException("Accommodations with id " + accommodationId + " not found");
		}

		return accommodation;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response post(Accommodation newAccommodation, @Context HttpServletRequest request) {

		logger.info("post: " + newAccommodation);

		Connection conn = (Connection) sc.getAttribute("dbConn");
		AccommodationDAO aDao = new JDBCAccommodationDAOImpl();
		aDao.setConnection(conn);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		// Property
		PropertyDAO pDao = new JDBCPropertyDAOImpl();
		pDao.setConnection(conn);

		Property property = pDao.get(newAccommodation.getIdp());
		long id;
		if (user.getId() == property.getIdu()) {
			if ((id = aDao.add(newAccommodation)) == -1) {
				logger.info("Accommodation " + newAccommodation + " not added");
			}

		} else {
			throw new CustomBadRequestException("Accommodation not valid");
		}

		String message = "Accommodations created";

		return Response.status(Response.Status.CREATED).entity(message)
				.contentLocation(uriInfo.getAbsolutePathBuilder().path(Long.toString(id)).build()).build();
	}

	@POST
	@Consumes("application/x-www-form-urlencoded")
	public Response post(MultivaluedMap<String, String> formParams, @Context HttpServletRequest request) {

		logger.info("post: " + formParams);
		Connection conn = (Connection) sc.getAttribute("dbConn");

		AccommodationDAO aDao = new JDBCAccommodationDAOImpl();
		aDao.setConnection(conn);
		long id = -1;
		try {
			Accommodation a = new Accommodation();
			a.setIdp(Long.valueOf(formParams.getFirst("idp")));
			a.setName(formParams.getFirst("name"));
			a.setNumAccommodations(Integer.valueOf(formParams.getFirst("numAccommodations")));
			a.setPrice(Integer.valueOf(formParams.getFirst("price")));

			id = aDao.add(a);
			if (id == -1) {
				throw new CustomBadRequestException("Accommodation not valid");
			} else {
				logger.info("Accommodation " + a + " added");
			}

		} catch (Exception e) {
			throw new CustomBadRequestException("Accommodation not valid");
		}

		String message = "Accommodation created";
		return Response.status(Response.Status.CREATED)
				.entity(message)
				.contentLocation(uriInfo.getAbsolutePathBuilder().path(Long.toString(id)).build()).build();
	}

	@PUT
	@Path("/{accommodationid: [0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response put(Accommodation accommodationUpdate, @PathParam("accommodationid") long accommodationId,
			@Context HttpServletRequest request) {
		logger.info("put: " + accommodationUpdate);
		
		Response res = null;
		Connection conn = (Connection) sc.getAttribute("dbConn");
		
		PropertyDAO pDao = new JDBCPropertyDAOImpl();
		pDao.setConnection(conn);
		
		AccommodationDAO aDao = new JDBCAccommodationDAOImpl();
		aDao.setConnection(conn);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		Accommodation accommodation = aDao.get(accommodationId);

		if (accommodation != null) {
			Property property = pDao.get(accommodation.getIdp());
			if (user.getId() == property.getIdu()) {
				aDao.update(accommodationUpdate);
				res = Response.noContent().build();
			} else {
				throw new CustomBadRequestException("Accommodation not valid");
			}
		} else {
			throw new CustomNotFoundException("Accommodation not found");
		}

		return res;
	}

}
