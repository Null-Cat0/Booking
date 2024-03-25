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
	 * @see Htt-Servlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		logger.info("doGet : AddCartServlet");
		//Obtención del usuario y de la propiedad de la que son las habitaciones 
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		HttpSession session = request.getSession();
		
		PropertyDAO propertyDao = new JDBCPropertyDAOImpl();
		propertyDao.setConnection(conn);
		
		AccommodationDAO accommodationDao = new JDBCAccommodationDAOImpl();
		accommodationDao.setConnection(conn);
		
		// TODO =  Map<Hotel, List<Map.Entry<Habitacion, Integer>>> reservas = new HashMap<>();
		//Creación de la reserva y la reserva relacionada con la habitación
		try {
			
			int idp = Integer.parseInt(request.getParameter("id"));
			Property property = propertyDao.get(idp);
			
			List<Accommodation> la = accommodationDao.getAllByProperty(idp);
			Map<Accommodation, Integer> cart = (Map<Accommodation, Integer>) session.getAttribute("cart");
			if (cart == null) {
				cart = new HashMap<Accommodation, Integer>();
			}
			
			for (Accommodation a : la) {
				int n=Integer.parseInt(request.getParameter("nHabitaciones"+a.getId()));
				System.out.println(a + " Numero seleccionado: " + n);
				cart.put(a, n);
				
			}

			request.setAttribute("property", property);
			session.setAttribute("cart", cart);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/cart.jsp");
		rd.forward(request, response);                           
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		logger.info("doPost : AddCartServlet");
		
		HttpSession session = request.getSession();
		
		User user = (User) session.getAttribute("user");
		Map<Accommodation, Integer> cart = (Map<Accommodation, Integer>) session.getAttribute("cart") ;
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		
		for (Map.Entry<Accommodation, Integer> entry : cart.entrySet()) {
			Accommodation a = entry.getKey();
			int n = entry.getValue();
			
			Booking b = new Booking(user.getId(), a.getPrice()*n);
			
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
