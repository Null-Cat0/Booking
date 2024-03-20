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

import es.unex.pi.dao.AccommodationDAO;
import es.unex.pi.dao.JDBCAccommodationDAOImpl;
import es.unex.pi.dao.JDBCPropertiesServicesDAOImpl;
import es.unex.pi.dao.JDBCPropertyDAOImpl;
import es.unex.pi.dao.JDBCServicesDAOImpl;
import es.unex.pi.dao.PropertiesServicesDAO;
import es.unex.pi.dao.PropertyDAO;
import es.unex.pi.dao.ServicesDAO;
import es.unex.pi.model.Accommodation;
import es.unex.pi.model.PropertiesServices;
import es.unex.pi.model.Property;
import es.unex.pi.model.Service;
import es.unex.pi.model.User;

/**
 * Servlet implementation class EditPropertyServlet
 */
public class EditPropertyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(EditPropertyServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditPropertyServlet() {
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

				ServicesDAO serviceDao = new JDBCServicesDAOImpl();
				serviceDao.setConnection(conn);
				listaServicios = serviceDao.getAll();

				Map<String, Boolean> serviciosAsociados = new HashMap<String, Boolean>();

				for (Service s : listaServicios) {
					if () {
						serviciosAsociados.put(s.getName(), true);
					} else {
						serviciosAsociados.put(s.getName(), false);
					}
				}

//				//Mostrar map
//				for (Map.Entry<Long, Boolean> entry : serviciosAsociados.entrySet()) {
//                    logger.info("Key = " + entry.getKey() + ", Value = " + entry.getValue());
//               }

				logger.info("Servicios asociados a la propiedad");
				request.setAttribute("mapServices", serviciosAsociados);
				

				request.setAttribute("property", property);
				RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/NewProperty.jsp");
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		logger.info("EditPropertyServlet: Request received");
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		PropertyDAO propertyDao = new JDBCPropertyDAOImpl();
		propertyDao.setConnection(conn);
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		try {
			long id = Long.parseLong(request.getParameter("id"));
			String name = request.getParameter("nombreAlojamiento");
			String address = request.getParameter("direccion");
			String tel = request.getParameter("tel");
			String city = request.getParameter("ciudad");
			double dist = Double.parseDouble(request.getParameter("distanciaCentro"));
			double grades = Double.parseDouble(request.getParameter("valoracionMedia"));
			String description = request.getParameter("descripcion");
			int petFriendly = request.getParameter("permitenMascotas") == "Si" ? 1 : 0;

			Property property = new Property(id, name, address, tel, grades, city, dist, description, petFriendly, 1,
					(int) user.getId());
			propertyDao.update(property);

			// Servicios del hotel
			PropertiesServicesDAO propertyServiceDao = new JDBCPropertiesServicesDAOImpl();
			propertyServiceDao.setConnection(conn);
			List<PropertiesServices> serviciosPropiedad = propertyServiceDao.getAllByProperty(id);
			List<Service> listaServiciosModificados = new ArrayList<Service>();
			listaServiciosModificados = (List<Service>) request.getAttribute("listServices");

		} catch (Exception e) {

		}

	}

}
