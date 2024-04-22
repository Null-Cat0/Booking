package es.unex.pi.resources;

import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.server.PathParam;
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

public class UserResource {
	Logger logger = Logger.getLogger(AccommodationResource.class.getName());

	@Context
	ServletContext sc;

	@Context
	UriInfo uriInfo;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsersJSON(@Context HttpServletRequest request) {
        logger.info("getUsersJSON");

        List<User> users = null;
        Connection conn = (Connection) sc.getAttribute("dbConn");

        // UserDAO
        UserDAO uDao = new JDBCUserDAOImpl();
        uDao.setConnection(conn);

        users = uDao.getAll();
        return users;
    }
	
	
	@GET
	@Path("/{userid: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUserJSON(@PathParam("userid") long userId,
            @Context HttpServletRequest request) {
        logger.info("getUserJSON: " + userId);
        Connection conn = (Connection) sc.getAttribute("dbConn");

        UserDAO uDao = new JDBCUserDAOImpl();
        uDao.setConnection(conn);

        User user = uDao.get(userId);
        
       HttpSession session = request.getSession();
       User userSession = (User) session.getAttribute("user");
       
       if(userSession.getId() == userId) {
           return user;
       } else {
           throw new CustomNotFoundException("User (" + userId + ") not found");
       }
        
    }
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response post(User user, @Context HttpServletRequest request) {
        logger.info("newUser: " + user.toString());
        Connection conn = (Connection) sc.getAttribute("dbConn");

        UserDAO uDao = new JDBCUserDAOImpl();
        uDao.setConnection(conn);

        long id= uDao.add(user);

        if(id == -1) 
        	throw new CustomNotFoundException("User (" + user.toString() + ") not added");
        else {
        String message = "User added";
        
        return Response.status(Response.Status.CREATED).entity(message)
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
        
        long id = uDao.add(user);
        
        if(id == -1) {
            throw new CustomNotFoundException("User (" + user.toString() + ") not added");
        }else {
            String message = "User added";
            
            return Response.status(Response.Status.CREATED).entity(message)
                    .contentLocation(uriInfo.getAbsolutePathBuilder().path(Long.toString(user.getId())).build())
                    .build();
        }
    }
	
	@PUT
	@Path("/{userid: [0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response put(User user, @PathParam("userid") long userId, @Context HttpServletRequest request) {
        logger.info("updateUser: " + user.toString());
        Connection conn = (Connection) sc.getAttribute("dbConn");

        UserDAO uDao = new JDBCUserDAOImpl();
        uDao.setConnection(conn);

        User userSession = (User) request.getSession().getAttribute("user");
        if(userSession.getId() == userId) {
            uDao.update(user);
            String message = "User updated";
            return Response.status(Response.Status.NO_CONTENT).entity(message).build();
        } else {
            throw new CustomNotFoundException("User (" + userId + ") not found");
        }
    }
	
	@DELETE
	@Path("/{userid [0-9]+}")
	public Response deleteUser (@PathParam("userid") long userid, @Context HttpServletRequest request) 
	{
		logger.info("Delete User with id: "+userid );
		Connection conn = (Connection) sc.getAttribute("dbConn");
		
		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		
		
		if(userSession.getId() == userid) {
			UserDAO userDao = new JDBCUserDAOImpl();
			userDao.setConnection(conn);
			
			userDao.delete(userid);
			return Response.noContent().build();
		}else {
			throw new CustomNotFoundException("User not deleted");
		}
		
		
	}

}
