package es.unex.pi.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import es.unex.pi.dao.JDBCPropertiesServicesDAOImpl;
import es.unex.pi.dao.JDBCPropertyDAOImpl;
import es.unex.pi.dao.JDBCServicesDAOImpl;
import es.unex.pi.dao.PropertiesServicesDAO;
import es.unex.pi.dao.PropertyDAO;
import es.unex.pi.dao.ServicesDAO;
import es.unex.pi.model.PropertiesServices;
import es.unex.pi.model.Property;
import es.unex.pi.model.Service;
import es.unex.pi.model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		try {
			String id = request.getParameter("id");
			logger.info("Property id (" + id + ")");
			long pid = 0;
			pid = Long.parseLong(id);
			logger.info("Property id (" + id + ") and casting " + pid);
			Property property = propertyDao.get(pid);
			if (property != null) {
				if (property.getIdu() != user.getId()) {
					logger.info("User is not the owner of the property");
					response.sendRedirect("ListCategoriesServlet.do");
				} else {
					// Servicios del hotel
					PropertiesServicesDAO propertyServiceDao = new JDBCPropertiesServicesDAOImpl();
					propertyServiceDao.setConnection(conn);

					List<PropertiesServices> serviciosPropiedad = propertyServiceDao.getAllByProperty(pid);
					List<Service> listaServicios = new ArrayList<Service>();

					ServicesDAO serviceDao = new JDBCServicesDAOImpl();
					serviceDao.setConnection(conn);
					listaServicios = serviceDao.getAll();

					Map<String, Boolean> serviciosAsociados = new HashMap<String, Boolean>();

					int j = 0;
					for (int i = 0; i < listaServicios.size(); i++) {
						Service s = listaServicios.get(i);

						if (j < serviciosPropiedad.size()
								&& listaServicios.get(i).getId() == serviciosPropiedad.get(j).getIds()) {
							serviciosAsociados.put(s.getName(), true);
							j++;
						} else {
							serviciosAsociados.put(s.getName(), false);
						}
					}

					logger.info("Servicios asociados a la propiedad");
					request.setAttribute("mapServices", serviciosAsociados);

					request.setAttribute("property", property);
					request.setAttribute("tipoInformacion", "Editar");
					RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/NewProperty.jsp");
					rd.forward(request, response);
				}
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
		logger.info("EditPropertyServlet: Request received");
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		PropertyDAO propertyDao = new JDBCPropertyDAOImpl();
		propertyDao.setConnection(conn);
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		// TODO :Comprobar que los datos de la habitacion son correctos
		try {

			long id = Long.parseLong(request.getParameter("id"));
			String name = request.getParameter("nombreAlojamiento");
			String address = request.getParameter("direccion");
			String tel = request.getParameter("tel");
			String city = request.getParameter("ciudad");
			double dist = Double.parseDouble(request.getParameter("distanciaCentro"));
			String description = request.getParameter("descripcion");
			int petFriendly = request.getParameter("permitenMascotas").equals("Si") ? 1 : 0;
			int available = request.getParameter("disponibilidad").equals("Si") ? 1 : 0;

			System.out.println("Pet friendly: " + petFriendly);
			System.out.println("Available: " + available);
			Property property = new Property(id, name, address, tel, city, dist, description, petFriendly, available,
					(int) user.getId());
			propertyDao.update(property);

			// Servicios del hotel
			PropertiesServicesDAO propertyServiceDao = new JDBCPropertiesServicesDAOImpl();
			propertyServiceDao.setConnection(conn);

			// De los servicios seleccionados hay que comprobar cuales estan asociados a la
			// propiedad y cuales ha quitado
			ServicesDAO serviceDao = new JDBCServicesDAOImpl();
			serviceDao.setConnection(conn);

			String[] services = request.getParameterValues("servicios");

			List<PropertiesServices> serviciosPropiedad = propertyServiceDao.getAllByProperty(id); // Lista de los
																									// servicios
																									// asociados a la
																									// propiedad
			List<Service> listaServiciosSelecionados = new ArrayList<Service>(); // Lista de los servicios seleccionados
																					// por el usuario

			if (services != null) {

				for (String s : services) {
					Service service = serviceDao.get(s);
					logger.info("Servicio seleccionado: " + s);
					listaServiciosSelecionados.add(service);
				}
			} else {
				logger.info("No hay servicios seleccionados");
				listaServiciosSelecionados = null;
			}

			// Eliminamos los servicios que no esten seleccionados
			for (int i = 0; i < serviciosPropiedad.size(); i++) {
				if (listaServiciosSelecionados == null) {
					logger.info("Servicio no seleccionado: " + serviciosPropiedad.get(i).getIds());
					propertyServiceDao.delete(id, serviciosPropiedad.get(i).getIds());
				} else if (!listaServiciosSelecionados.contains(serviciosPropiedad.get(i))) {
					logger.info("Servicio no seleccionado: " + serviciosPropiedad.get(i).getIds());
					propertyServiceDao.delete(id, serviciosPropiedad.get(i).getIds());
				}

			}

			if (listaServiciosSelecionados != null) {
				// Añadimos los servicios seleccionados
				for (Service j : listaServiciosSelecionados) {
					if (!serviciosPropiedad.contains(new PropertiesServices(id, j.getId()))) {
						logger.info("Añadiendo servicio seleccionado: " + j.getId());
						propertyServiceDao.add(new PropertiesServices(id, j.getId()));
					}
				}
			}

			response.sendRedirect("ListCategoriesServlet.do");

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Error updating property");
			response.sendRedirect("ListCategoriesServlet.do");
		}

	}

}
