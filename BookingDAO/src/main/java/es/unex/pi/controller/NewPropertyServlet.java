package es.unex.pi.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.unex.pi.model.Property;
import es.unex.pi.model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import es.unex.pi.dao.*;
/**
 * Servlet implementation class NewPropertyServlet
 */
public class NewPropertyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(NewPropertyServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NewPropertyServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * TODO [ ] Cargar la página de creación de una nueva propiedad
		 */
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/NewProperty.jsp");
		rd.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/*
		 * TODO [ ] Recoger los datos del formulario [ ] Crear una nueva propiedad [ ]
		 * Redirigir a la página de la propiedad creada
		 * 			
			//Mover a otro servlet como checkPropertyServlet ? (Parte)
		 */

		logger.setLevel(Level.INFO);
		logger.info("NewPropertyServlet:doPost");

		try {
			// Recoger los datos del formulario
			String title = request.getParameter("nombreAlojamiento");
			String address = request.getParameter("direccion");
			String tel = request.getParameter("tel");
			double distance = Double.parseDouble(request.getParameter("distanciaCentro"));
			double gradesAverage = Double.parseDouble(request.getParameter("valoracionMedia"));
			String description = request.getParameter("descripcion");

			// Obtener usuario de la sesión.
			HttpSession session = request.getSession();

			User user = (User) session.getAttribute("user");


			// Crear una nueva propiedad
			Connection conn = (Connection) getServletContext().getAttribute("dbConn");
			PropertyDAO propertyDAO = new JDBCPropertyDAOImpl();
			propertyDAO.setConnection(conn);
			Property property = new Property(title, address, tel, gradesAverage, "Cáceres", distance, description, 1, 1, (int)user.getId());
			propertyDAO.add(property);
			logger.info("Property added: " + property.toString());
			
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/SearchAndList.jsp");
			rd.forward(request, response);
		
		
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error: ", e);
		}

	}

}
