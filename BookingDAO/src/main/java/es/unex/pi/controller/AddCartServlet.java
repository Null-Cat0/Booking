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
		
		HttpSession session = request.getSession();
		Map<Accommodation, Integer> cart = (Map<Accommodation, Integer>) session.getAttribute("cart");
		request.setAttribute("cart", cart);
		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/Cart.jsp");
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

		User user = (User) session.getAttribute("user");
		Map<Accommodation, Integer> cart = (Map<Accommodation, Integer>) session.getAttribute("cart");
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");

		for (Map.Entry<Accommodation, Integer> entry : cart.entrySet()) {
			Accommodation a = entry.getKey();
			int n = entry.getValue();

			Booking b = new Booking(user.getId(), a.getPrice() * n);

			BookingDAO bookingDao = new JDBCBookingDAOImpl();
			bookingDao.setConnection(conn);
			long idb = bookingDao.add(b);

			BookingsAccommodations ba = new BookingsAccommodations(idb, a.getId(), n);

			BookingsAccommodationsDAO bookingsAccommodationsDao = new JDBCBookingsAccommodationsDAOImpl();
			bookingsAccommodationsDao.setConnection(conn);
			bookingsAccommodationsDao.add(ba);
		}

	}

}
