package es.unex.pi.resources;

import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import es.unex.pi.model.*;
import es.unex.pi.resources.exceptions.CustomNotFoundException;
import es.unex.pi.dao.*;

@Path("/users")
public class UserResource {
	Logger logger = Logger.getLogger(UserResource.class.getName());

	@Context
	ServletContext sc;

	@Context
	UriInfo uriInfo;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public User getUsersJSON(@Context HttpServletRequest request) {
		logger.info("getUsersJSON");

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user != null) {
			logger.info("Returning user's session");
			return user;
		} else {
			throw new CustomNotFoundException("User not found in session");
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response post(User user, @Context HttpServletRequest request) {
		logger.info("newUser: " + user.toString());
		Connection conn = (Connection) sc.getAttribute("dbConn");

		UserDAO uDao = new JDBCUserDAOImpl();
		uDao.setConnection(conn);
		
		
		
		if (uDao.getUserByEmail(user.getEmail()) != null) {
			throw new CustomNotFoundException("User (" + user.toString() + ") already exists");
		}

		long id = uDao.add(user);

		if (id == -1)
			throw new CustomNotFoundException("User (" + user.toString() + ") not added");
		else {

			HttpSession session = request.getSession();
			session.setAttribute("user", user);

			String message = "User added";

			return Response.status(Response.Status.CREATED)
					.entity("{\"status\" : \"200\", \"message\" : \"" + message + "\" , \"id\" : \"" + id + "\"}")
					.contentLocation(uriInfo.getAbsolutePathBuilder().path(Long.toString(user.getId())).build())
					.build();
		}
	}

	@POST
	@Consumes("application/x-www-form-urlencoded")
	public Response post(MultivaluedMap<String, String> formParams, @Context HttpServletRequest request) {
		logger.info("newUser from formParams: " + formParams.toString());
		Connection conn = (Connection) sc.getAttribute("dbConn");

		User user = new User();
		user.setEmail(formParams.getFirst("email"));
		user.setPassword(formParams.getFirst("password"));
		user.setSurname(formParams.getFirst("surname"));
		user.setName(formParams.getFirst("name"));

		UserDAO uDao = new JDBCUserDAOImpl();

		uDao.setConnection(conn);
		if (uDao.getUserByEmail(user.getEmail()) != null) {
			throw new CustomNotFoundException("User (" + user.toString() + ") already exists");
		}else {
		long id = uDao.add(user);

		if (id == -1) {
			throw new CustomNotFoundException("User (" + user.toString() + ") not added");
		} else {
			String message = "User added";

			return Response.status(Response.Status.CREATED)
					.entity("{\"status\" : \"200\", \"message\" : \"" + message + "\" , \"id\" : \"" + id + "\"}")
					.contentLocation(uriInfo.getAbsolutePathBuilder().path(Long.toString(user.getId())).build())
					.build();
		}
	}}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response put(User user, @Context HttpServletRequest request) {
		logger.info("updateUser: " + user.toString());
		Connection conn = (Connection) sc.getAttribute("dbConn");

		UserDAO uDao = new JDBCUserDAOImpl();
		uDao.setConnection(conn);

		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null) {
			throw new CustomNotFoundException("User not found in session");
		}
		
		if (userSession.getId() == user.getId()) {
			uDao.update(user);
			session.removeAttribute("user");
			session.setAttribute("user", user);
			String message = "User updated";
			return Response.status(Response.Status.NO_CONTENT)
					.entity("{\"status\" : \"200\", \"message\" : \"" + message + "\"}").build();
		} else {
			throw new CustomNotFoundException("User (" + user.getId() + ") not found");
		}
	}

	@DELETE
	public Response delete(@Context HttpServletRequest request) {
		logger.info("deleteUser");
		Connection conn = (Connection) sc.getAttribute("dbConn");

		UserDAO uDao = new JDBCUserDAOImpl();
		uDao.setConnection(conn);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user != null) {
			uDao.delete(user.getId());
			session.removeAttribute("user");
			String message = "User deleted";
			return Response.status(Response.Status.NO_CONTENT).entity(message).build();
		} else {
			throw new CustomNotFoundException("User not found in session");
		}
	}
}
