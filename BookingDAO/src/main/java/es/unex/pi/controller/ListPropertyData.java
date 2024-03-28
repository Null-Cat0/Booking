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

import es.unex.pi.model.*;
import es.unex.pi.util.Entry;
import es.unex.pi.dao.*;

/**
 * Servlet implementation class ListPropertyData
 */
public class ListPropertyData extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(HttpServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListPropertyData() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		PropertyDAO propertyDao = new JDBCPropertyDAOImpl();
		propertyDao.setConnection(conn);

		try {
			String id = request.getParameter("id");
			logger.info("Property id (" + id + ")");
			long pid = 0;
			pid = Long.parseLong(id);
			logger.info("Property id (" + id + ") and casting " + pid);
			Property property = propertyDao.get(pid);
			if (property != null) {
				// Servicios del hotel
				PropertiesServicesDAO propertyServiceDao = new JDBCPropertiesServicesDAOImpl();
				propertyServiceDao.setConnection(conn);

				List<PropertiesServices> serviciosPropiedad = propertyServiceDao.getAllByProperty(pid);
				List<Service> listaServicios = new ArrayList<Service>();

				if (serviciosPropiedad != null) {
					ServicesDAO serviceDao = new JDBCServicesDAOImpl();
					serviceDao.setConnection(conn);
					for (PropertiesServices ps : serviciosPropiedad) {
						Service servicio = serviceDao.get(ps.getIds());
						listaServicios.add(servicio);
					}

					request.setAttribute("listServices", listaServicios);
				} else {
					logger.info("No hay servicios asociados a la propiedad");
				}

				// Habitaciones del hotel

				AccommodationDAO accomodationDao = new JDBCAccommodationDAOImpl();
				accomodationDao.setConnection(conn);
				List<Accommodation> habitaciones = accomodationDao.getAccommodationProperty(pid);
				if (habitaciones != null) {
					request.setAttribute("listAccommodations", habitaciones);
				} else {
					logger.info("No hay habitaciones asociadas a la propiedad");
				}

				request.setAttribute("property", property);
				RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/ListPropertyData.jsp");
				rd.forward(request, response);
			} else {
				logger.info("Property is null");
				response.sendRedirect("LisCategoriesServlet.do");
			}
		} catch (NumberFormatException e) {
			logger.info("parameter id is not a number");

			// TODO: Redirect to ListOrderServlet.
			response.sendRedirect("LisCategoriesServlet.do");

		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Obtención del usuario y de la propiedad de la que son las habitaciones
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		HttpSession session = request.getSession();

		PropertyDAO propertyDao = new JDBCPropertyDAOImpl();
		propertyDao.setConnection(conn);

		AccommodationDAO accommodationDao = new JDBCAccommodationDAOImpl();
		accommodationDao.setConnection(conn);

		// Creación de la reserva y la reserva relacionada con la habitación
		try {

			String id = request.getParameter("id");
			if (id != null) {// Si se ha seleccionado una propiedad, por lo que se puede llamar en el inicio
				int idp = Integer.parseInt(id);
				Property property = propertyDao.get(idp);

				List<Accommodation> la = accommodationDao.getAllByProperty(idp);
				Map<Property, List<Entry<Accommodation, Integer>>> reservas = (Map<Property, List<Entry<Accommodation, Integer>>>) session
						.getAttribute("cart");

				if (reservas == null) {
					reservas = new HashMap<Property, List<Entry<Accommodation, Integer>>>();
				}

				List<Entry<Accommodation, Integer>> le = null;

				for (Accommodation a : la) {
					int n = Integer.parseInt(request.getParameter("nHabitaciones" + a.getId()));
					if (n > 0) {
						if (reservas.containsKey(property)) {
							logger.info("Property: " + property);
							le = reservas.get(property);
						} else {
							logger.info("No Property " + property);
							le = new ArrayList<Entry<Accommodation, Integer>>();
						}
						logger.info(a + " Numero seleccionado: " + n);
						Entry<Accommodation, Integer> e = new Entry<Accommodation, Integer>(a, n);
						le.add(e);
					}
				}

				//Si se ha seleccionado alguna habitación se pone en la sesión
				if (le != null) {
					reservas.put(property, le);
					session.setAttribute("cart", reservas);
				} else {
					session.setAttribute("cart", null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		response.sendRedirect("AddCartServlet.do");
	}

}
