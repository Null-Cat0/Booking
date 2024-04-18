package es.unex.pi.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.model.Accommodation;
import es.unex.pi.model.Property;
import es.unex.pi.model.Review;
import es.unex.pi.model.User;
import es.unex.pi.util.Entry;
import es.unex.pi.util.Ordenator;
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
		logger.setLevel(Level.INFO);
		logger.info("ListResultsServlet: doGet");

		
		//Valores con los que se ordena los resultados de la busqueda
        String[] valores = request.getParameterValues("valores");
        String[] filtros = request.getParameterValues("available");
		
		// Obtener los datos del formulario de busqueda
		String search =  request.getParameter("search");
		if (search == null || search.isEmpty()) {
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
			List<Property> listPropertiesDescription = propertyDao.getAllBySearchDescription(search);
			
			// Unir las dos listas
			listPropertiesName.addAll(listPropertiesCity);
			listPropertiesName.addAll(listPropertiesDescription);

			//Ordenar la lista de propiedades
			if (valores != null && valores.length > 0) {
				logger.info("Ordenacion por: " + valores[0]);
				Ordenator.ordenarLista(listPropertiesName, valores);
			}
			
		    //Filtrar la lista de propiedades
			if (filtros != null && filtros.length > 0) {
				logger.info("Filtros: " + Arrays.toString(filtros));
				Ordenator.filtrarLista(listPropertiesName, filtros);
			}
			
			//Mapa para incluir el numero de reviews que tiene esa propiedad y el precio de una habitacion
			Map<Property,Entry<Integer,Double>> mapaResultados = new LinkedHashMap<Property,Entry<Integer,Double>>();
			Iterator<Property> it = listPropertiesName.iterator();
			
			//Reviews
			ReviewDAO reviewDao = new JDBCReviewDAOImpl();
			reviewDao.setConnection(conn);
			
			//Habitacion
			AccommodationDAO accommodationDao = new JDBCAccommodationDAOImpl();
			accommodationDao.setConnection(conn);
			
					
			while (it.hasNext()) {
				Property p = it.next();
				List<Review> listReviews = reviewDao.getAllByProperty(p.getId());
				List<Accommodation> listAccommodations = accommodationDao.getAllByProperty((int)p.getId());
				
				//Ordenar las habitaciones por numero de disponibles
				Collections.sort(listAccommodations,Accommodation.numAccommodationsComparator.reversed());
                
				int numReviews = 0;
                double price = 0;
                
                if (listReviews != null && listReviews.size() > 0 ) {
                	numReviews = listReviews.size();
                }
				if (listAccommodations != null && listAccommodations.size() > 0 ) {
					if(listAccommodations.get(0).getNumAccommodations() > 0)
						price = listAccommodations.get(0).getPrice();
				}
				
				Entry<Integer,Double> e = new Entry<Integer,Double>(numReviews,price);
				
				mapaResultados.put(p, e);
			}

			// Añadir los elementos a la request
			request.setAttribute("mapaResultados", mapaResultados);
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

		logger.info("ListResultsServlet: doPost");
		doGet(request, response);
	}

}
