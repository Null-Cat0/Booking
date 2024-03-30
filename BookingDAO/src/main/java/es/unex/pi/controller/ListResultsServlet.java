package es.unex.pi.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.model.Property;
import es.unex.pi.model.User;
import es.unex.pi.dao.*;

/**
 * Servlet implementation class ListResultsServlet
 */
public class ListResultsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(ListResultsServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListResultsServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Obtener datos del form de busqueda y hacer una lista con los hoteles que
		// cumplan los requisitos

		logger.setLevel(Level.INFO);

		// Obtener los datos del formulario de busqueda
		String search = request.getParameter("search");

		if (search.isEmpty()) {
			logger.info("No se ha introducido ninguna ciudad");
			// Redirigir a la pagina de resultados con un mensaje de error
			request.getRequestDispatcher("/WEB-INF/SearchResults.jsp").forward(request, response);
		} else {
			// Mostrar los datos obtenidos
			logger.info("Search:" + search);
			
			// Obtener la base de datos
			Connection conn = (Connection) getServletContext().getAttribute("dbConn");

			PropertyDAO propertyDao = new JDBCPropertyDAOImpl();
			propertyDao.setConnection(conn);
			
			// Obtener la lista de propiedades que cumplan los requisitos
			List<Property> listPropertiesName = propertyDao.getAllBySearchName(search);
			List<Property> listPropertiesCity = propertyDao.getAllBySearchCity(search);
			
			// Unir las dos listas
			listPropertiesName.addAll(listPropertiesCity);

			for (Iterator iterator = listPropertiesName.iterator(); iterator.hasNext();) {
				Property property = (Property) iterator.next();
				logger.info("Propiedad:" + property.getName());
			}

			// Añadir la lista de propiedades a la request
			request.setAttribute("listProperties", listPropertiesName);
			request.setAttribute("search", search);

			// Redirigir a la pagina de resultados
			request.getRequestDispatcher("/WEB-INF/SearchResults.jsp").forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
