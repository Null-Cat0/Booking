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
import java.util.logging.Level;
import java.util.logging.Logger;

import es.unex.pi.dao.AccommodationDAO;
import es.unex.pi.dao.JDBCAccommodationDAOImpl;
import es.unex.pi.model.Accommodation;
import es.unex.pi.model.Property;
import es.unex.pi.model.User;
import es.unex.pi.dao.*;

/**
 * Servlet implementation class NewAccommodationServlet
 */
public class NewAccommodationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(HttpServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NewAccommodationServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		logger.setLevel(Level.INFO);
		logger.info("NewAccommodationServlet: doGet");

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("user");
		PropertyDAO pDAO = new JDBCPropertyDAOImpl();
		pDAO.setConnection(conn);

		try {
			long idp = Long.parseLong(request.getParameter("id"));

			Property p = pDAO.get(idp);
			if (p.getIdu() != user.getId()) {
				logger.info("No tienes permisos para añadir alojamientos a esta propiedad");
				response.sendRedirect("ListPropertiesServlet.do");
			} else {

				request.setAttribute("idp", idp);
				request.setAttribute("tipoInformacion", "Añadir");
				RequestDispatcher view = request.getRequestDispatcher("WEB-INF/NewAccommodation.jsp");
				view.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.severe("Error: " + e.getMessage());
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		logger.info("NewAccommodationServlet: doPost");
		logger.setLevel(Level.INFO);

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		PropertyDAO pdao = new JDBCPropertyDAOImpl();
		pdao.setConnection(conn);
		try {
			// Datos de la habitacion
			String name = request.getParameter("name");
			int price = Integer.parseInt(request.getParameter("price"));
			String description = request.getParameter("description");
			int nAccommodations = Integer.parseInt(request.getParameter("nAccommodations"));

			// Id del alojamiento
			Long idp = Long.parseLong(request.getParameter("idp"));
			Property p = pdao.get(idp);
			if (p.getIdu() != user.getId()) {
				logger.info("No tienes permisos para añadir alojamientos a esta propiedad");
				response.sendRedirect("ListPropertiesServlet.do");
			} else {

				// Crear objeto alojamiento
				Accommodation a = new Accommodation(name, price, description, nAccommodations, idp);

				// Guardar objeto habitacion en la bd
				AccommodationDAO aDAO = new JDBCAccommodationDAOImpl();
				aDAO.setConnection(conn);
				aDAO.add(a);

				response.sendRedirect("ListPropertiesServlet.do");
			}
		} catch (Exception e) {

			e.printStackTrace();
			logger.severe("Error: " + e.getMessage());

		}

	}

}
