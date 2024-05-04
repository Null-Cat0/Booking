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
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import es.unex.pi.dao.*;
import es.unex.pi.model.*;
import es.unex.pi.resources.exceptions.CustomBadRequestException;

@Path("/bookingsaccommodations")
public class BookingsAccommodationsResource {
	Logger logger = Logger.getLogger(BookingResource.class.getName());

	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addBookingAccommodation(BookingsAccommodations bookingAccommodation,
			@Context HttpServletRequest request) {
        
		logger.info("addBookingAccommodation");
		
        
        Connection conn = (Connection) sc.getAttribute("dbConn");
		
        AccommodationDAO aDao = new JDBCAccommodationDAOImpl();
                aDao.setConnection(conn);
        Accommodation a = aDao.get(bookingAccommodation.getIdacc());
		if (a == null)
			throw new CustomBadRequestException("Accommodation not found");
		else if (a.getNumAccommodations()< bookingAccommodation.getNumAccommodations())
			bookingAccommodation.setNumAccommodations(a.getNumAccommodations());
		
		
		a.setNumAccommodations(a.getNumAccommodations()-(int)bookingAccommodation.getNumAccommodations());
		if (!aDao.update(a))
			throw new CustomBadRequestException("Error in POST");
		
		BookingsAccommodationsDAO baDao = new JDBCBookingsAccommodationsDAOImpl();
		baDao.setConnection(conn);
		
		if (!baDao.add(bookingAccommodation))
			throw new CustomBadRequestException("Error in POST");
		
		String message = "BookingAccommodation  added";
		
		return Response.status(Response.Status.CREATED)
				.entity("{\"status\" : \"200\", \"message\" : \"" + message + "\"}")
				.contentLocation(
						uriInfo.getAbsolutePathBuilder().path(Long.toString(bookingAccommodation.getIdb())).build())
				.build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<BookingsAccommodations> getBookingsAccommodationsJSON(@Context HttpServletRequest request) {
		logger.info("getBookingsAccommodationsJSON");

		List<BookingsAccommodations> bookingsAccommodations = null;
		Connection conn = (Connection) sc.getAttribute("dbConn");

		// PropertyDAO
		BookingsAccommodationsDAO baDao = new JDBCBookingsAccommodationsDAOImpl();
		baDao.setConnection(conn);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		if (user != null) {
			bookingsAccommodations = baDao.getAllByUser((int) user.getId());
		} else {
			logger.info("No user in session");
		}

		return bookingsAccommodations;
	}
	
}
