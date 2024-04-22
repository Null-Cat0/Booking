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

@Path("/favourites")
public class FavouriteResource {
	Logger logger = Logger.getLogger(FavouriteResource.class.getName());

	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Favourite> getPropertiesJSON(@Context HttpServletRequest request) {
		logger.info("getPropertiesJSON");

		List<Favourite> favourites = null;
		Connection conn = (Connection) sc.getAttribute("dbConn");

		// PropertyDAO
		FavouriteDAO fDao = new JDBCFavouriteDAOImpl();
		fDao.setConnection(conn);
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		
		if(user != null) {
            favourites = fDao.getAllByUser(user.getId());
		}
		else {
			logger.info("No user in session");
		}
		
		return favourites;
	}
	
	@GET
	@Path("/{favouriteid: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Favourite getPropertyJSON(@PathParam("propertyId") long propertyid,@Context HttpServletRequest request) {
		logger.info("getPropertyJSON");
		
		Connection conn = (Connection) sc.getAttribute("dbConn");
		FavouriteDAO fDao = new JDBCFavouriteDAOImpl();
        fDao.setConnection(conn);
        
        List<Favourite> favourites = null;
        HttpSession session = request.getSession();
        
        Favourite favourite = null;
        User user = (User) session.getAttribute("user");
        
		if (user != null) {
			favourites = fDao.getAllByUser(user.getId());
			for (Favourite f : favourites) {
				if (f.getIdp() == propertyid && f.getIdu() == user.getId()) {
					favourite = f;
					break;
				}
			}
		} else {
			logger.info("No user in session");
		}
       return favourite;
        
	}
	
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response post(Favourite newFavourite, @Context HttpServletRequest request) {
		logger.info("post: " + newFavourite);
		Response res = null;
		Connection conn = (Connection) sc.getAttribute("dbConn");

		FavouriteDAO fDao = new JDBCFavouriteDAOImpl();
		fDao.setConnection(conn);

		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("user");

		if (user != null) {
			if (user.getId() == newFavourite.getIdu()) {
				fDao.add(newFavourite);
				String message = "Favourite added";
				res = Response.status(Response.Status.CREATED).entity(message)
						.contentLocation(uriInfo.getAbsolutePathBuilder().path(Long.toString(newFavourite.getIdp())).build()).build();
			} else {
				logger.info("User not allowed to add favourite");
				throw new CustomBadRequestException("User not allowed to add favourite");
			}
		} else {
			logger.info("No user in session");
			throw new CustomBadRequestException("No user in session");
		}
		return res;
	}
	
	@DELETE
	@Path("/{favouriteid: [0-9]+}")
	public Response deleteFavourite(@PathParam("propertyId") long propertyid, @Context HttpServletRequest request) {
		logger.info("deleteFavourite");
		Response res = null;
		Connection conn = (Connection) sc.getAttribute("dbConn");

		FavouriteDAO fDao = new JDBCFavouriteDAOImpl();
		fDao.setConnection(conn);

		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("user");

		if (user != null) {
			Favourite f = fDao.get(user.getId(),propertyid);
			if (f.getIdu() == user.getId()) {
				fDao.delete(f);
				String message = "Favourite deleted";
				res = Response.status(Response.Status.OK).entity(message)
						.contentLocation(uriInfo.getAbsolutePathBuilder().path(Long.toString(propertyid)).build())
						.build();
			} else {
				logger.info("User not allowed to delete favourite");
				throw new CustomBadRequestException("User not allowed to delete favourite");
			}
		} else {
			logger.info("No user in session");
			throw new CustomBadRequestException("No user in session");
		}
		return res;
	}

}
