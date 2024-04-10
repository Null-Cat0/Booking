package es.unex.pi.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.model.Accommodation;
import es.unex.pi.model.Booking;
import es.unex.pi.model.BookingsAccommodations;
import es.unex.pi.model.Property;
import es.unex.pi.model.User;
import es.unex.pi.util.Entry;
import es.unex.pi.dao.*;
import java.sql.Connection;

/**
 * Servlet implementation class ListBookingsRecordServlet
 */
public class ListBookingsRecordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(HttpServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListBookingsRecordServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		logger.info("ListBookingsRecordServlet: doGet");
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		// Reservas de un usuario
		BookingDAO bookingDao = new JDBCBookingDAOImpl();
		bookingDao.setConnection(conn);

		List<Booking> bookings = bookingDao.getAllByUser(user.getId());

		// Datos de la reserva
		BookingsAccommodationsDAO bookingsAccommodationsDao = new JDBCBookingsAccommodationsDAOImpl();
		bookingsAccommodationsDao.setConnection(conn);

		// Habitacion
		AccommodationDAO accommodationDao = new JDBCAccommodationDAOImpl();
		accommodationDao.setConnection(conn);

		// Hotel
		PropertyDAO propertyDao = new JDBCPropertyDAOImpl();
		propertyDao.setConnection(conn);

		List<Entry<Property, Integer>> entryList = new ArrayList<Entry<Property, Integer>>();
		for (Booking b : bookings) {
			List<BookingsAccommodations> bookingsAccommodations = bookingsAccommodationsDao.getAllByBooking(b.getId());
			// for (BookingsAccommodations ba : bookingsAccommodations) {
			// Solamente se tiene en cuenta la primera para cada bookingAcc
			if (!bookingsAccommodations.isEmpty()) {
				BookingsAccommodations ba = bookingsAccommodations.get(0);
				Accommodation accommodation = accommodationDao.get(ba.getIdacc());
				Property property = propertyDao.get(accommodation.getIdp());
				entryList.add(new Entry<Property, Integer>(property, b.getTotalPrice()));
			}
			// }
		}

		request.setAttribute("historico", entryList);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/BookingsRecord.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			doGet(request, response);
	}

}
