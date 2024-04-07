package es.unex.pi.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import es.unex.pi.model.Accommodation;
import es.unex.pi.model.Booking;
import es.unex.pi.model.BookingsAccommodations;
import es.unex.pi.model.Property;
import es.unex.pi.model.User;
import es.unex.pi.dao.*;
import es.unex.pi.util.Entry;

/**
 * Servlet implementation class AddCartServlet
 */
public class AddCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(AddCartServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddCartServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Htt-Servlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		logger.info("doGet : AddCartServlet");

		/*
		 * Map<String,String> messages = new HashMap<String,String>();
		 * 
		 * messages.put("noHab", "No hay suficientes habitaciones disponibles de"+
		 * accommodation.getName() + " en " + property.getName() +
		 * " para realizar la reserva."); request.setAttribute("messages", messages);
		 */
		HttpSession session = request.getSession();
		Map<Property, List<Entry<Accommodation, Integer>>> reservas = (Map<Property, List<Entry<Accommodation, Integer>>>) session
				.getAttribute("cart");
		request.setAttribute("cart", reservas);
		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/cart.jsp");
		view.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		logger.info("doPost : AddCartServlet");

		HttpSession session = request.getSession();
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");

		User user = (User) session.getAttribute("user");
		Map<Property, List<Entry<Accommodation, Integer>>> cart = (Map<Property, List<Entry<Accommodation, Integer>>>) session
				.getAttribute("cart");

		// Necesario para las reservas
		BookingDAO bookingDao = new JDBCBookingDAOImpl();
		bookingDao.setConnection(conn);

		BookingsAccommodationsDAO bookingsAccommodationsDao = new JDBCBookingsAccommodationsDAOImpl();
		bookingsAccommodationsDao.setConnection(conn);

		// Necesario para reducir el número de habitaciones disponibles
		AccommodationDAO accommodationDao = new JDBCAccommodationDAOImpl();
		accommodationDao.setConnection(conn);
		try {
			int totalPrice = Integer.parseInt(request.getParameter("totalPrice"));

			for (Property property : cart.keySet()) {

				Booking booking = new Booking();
				booking.setIdu(user.getId());
				booking.setTotalPrice(totalPrice);
				long idBooking = bookingDao.add(booking);
				booking.setId(idBooking);
				for (Entry<Accommodation, Integer> entry : cart.get(property)) {

					Accommodation accommodation = accommodationDao.get(entry.getKey().getId());

					if (accommodation.getNumAccommodations() < entry.getValue()) {
						logger.info("No hay suficientes habitaciones disponibles");
						response.sendRedirect("AddCartServlet.do");
					} else {
						// Hacer la reserva de la habitación
						BookingsAccommodations bookingsAccommodations = new BookingsAccommodations();
						bookingsAccommodations.setIdacc(entry.getKey().getId());
						bookingsAccommodations.setIdb(idBooking);
						bookingsAccommodations.setNumAccommodations(entry.getValue());
						bookingsAccommodationsDao.add(bookingsAccommodations);

						// Reducir el número de habitaciones disponibles
						accommodation.setNumAccommodations(accommodation.getNumAccommodations() - entry.getValue());
						accommodationDao.update(accommodation);

						// Vaciar el carrito
						session.setAttribute("cart", new HashMap<Property, List<Entry<Accommodation, Integer>>>());
				

					}
				}
			}
		} catch (NumberFormatException e) {
			logger.info("parameter id is not a number");
			response.sendRedirect("ListCategoriesServlet.do");
		}
		response.sendRedirect("ListCategoriesServlet.do");

	}

}
