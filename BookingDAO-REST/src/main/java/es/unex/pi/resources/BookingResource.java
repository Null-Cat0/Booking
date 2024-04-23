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
import es.unex.pi.dao.*;
import es.unex.pi.model.*;
import es.unex.pi.resources.exceptions.CustomNotFoundException;
@Path("/bookings")
public class BookingResource {
	Logger logger = Logger.getLogger(BookingResource.class.getName());

	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Booking> getBookingsJSON(@Context HttpServletRequest request) {
		logger.info("getPropertiesJSON");

		List<Booking> bookings = null;
		Connection conn = (Connection) sc.getAttribute("dbConn");

		// PropertyDAO
		BookingDAO bDao = new JDBCBookingDAOImpl();
		bDao.setConnection(conn);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		if (user != null) {
			bookings = bDao.getAllByUser((int)user.getId());
		} else {
			logger.info("No user in session");
		}

		return bookings;
	}
	
	@GET
	@Path("/{bookingId: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Booking getBookingJSON (@PathParam("bookingId")long bookingId, @Context HttpServletRequest request) 
	{
		logger.info("getBookingJSON");
		
		Connection conn = (Connection) sc.getAttribute("dbConn");
		BookingDAO bDao = new JDBCBookingDAOImpl();
		bDao.setConnection(conn);
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		Booking b = bDao.get(bookingId);
		if (b.getIdu() == user.getId()) {
			return b;
		} 
		else {
			logger.info("Booking not found");
			throw new CustomNotFoundException("Booking not found");
		}
	
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response post (Booking newBooking, @Context HttpServletRequest request) 
	{
		logger.info("post: " + newBooking);
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		if (newBooking.getIdu() == user.getId()) {
			Connection conn = (Connection) sc.getAttribute("dbConn");
			BookingDAO bDao = new JDBCBookingDAOImpl();
			bDao.setConnection(conn);

			long id =bDao.add(newBooking);		
			String message = "Object created";
			return Response.status(Response.Status.CREATED) // 201
					.entity(message) // return the id of the new order
					.contentLocation(uriInfo.getAbsolutePathBuilder().path(Long.toString(id)).build()) 																	
					.build();
		} else {
			logger.info("User not allowed to add booking");
			throw new CustomNotFoundException("User not allowed to add booking");
		}


	}
	@POST
	@Consumes("application/x-www-form-urlencoded")
	public Response post (MultivaluedMap<String, String> formParams, @Context HttpServletRequest request) 
	{
		logger.info("post: " + formParams);
		
		Connection conn = (Connection) sc.getAttribute("dbConn");
		BookingDAO  bDao = new JDBCBookingDAOImpl();
		bDao.setConnection(conn);
		
				
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		Booking b = new Booking();
		b.setIdu(user.getId());
		b.setTotalPrice(Integer.parseInt(formParams.getFirst("totalPrice")));

		long id = bDao.add(b);
		b.setId(id);
		String message = "Booking added";
		return Response.status(Response.Status.CREATED).entity(message)
				.contentLocation(uriInfo.getAbsolutePathBuilder().path(Long.toString(id)).build()).build();
	
	}
	
	
	@PUT
    @Path("/{bookingId: [0-9]+}")
    @Consumes(MediaType.APPLICATION_JSON)
	public Response put(Booking booking, @PathParam("bookingId") long bookingId, @Context HttpServletRequest request) {
		logger.info("put: " + booking);

		Connection conn = (Connection) sc.getAttribute("dbConn");
		BookingDAO bDao = new JDBCBookingDAOImpl();
		bDao.setConnection(conn);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		Booking b = bDao.get(bookingId);

		if (b.getIdu() == user.getId()) {
			String message = "Object updated";
			bDao.update(booking);
			return  Response.status(Response.Status.OK).entity(message)
					.contentLocation(uriInfo.getAbsolutePathBuilder().path(Long.toString(bookingId)).build())
					.build();
		} else {
			logger.info("User not allowed to update booking");
			throw new CustomNotFoundException("User not allowed to update booking");
		}
	}
	
	@DELETE
	@Path("/{bookingId: [0-9]+}")
	public Response delete(@PathParam("bookingId") long bookingId, @Context HttpServletRequest request) {
		logger.info("delete: " + bookingId);

		Connection conn = (Connection) sc.getAttribute("dbConn");
		BookingDAO bDao = new JDBCBookingDAOImpl();
		bDao.setConnection(conn);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		Booking b = bDao.get(bookingId);

		if (b.getIdu() == user.getId()) {
			String message = "Object deleted";
			bDao.delete(bookingId);
			return Response.status(Response.Status.OK).entity(message).build();
		} else {
			logger.info("User not allowed to delete booking");
			throw new CustomNotFoundException("User not allowed to delete booking");
		}
	}
	
	
}
