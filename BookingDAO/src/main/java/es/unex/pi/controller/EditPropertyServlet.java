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
import es.unex.pi.util.Validador;
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

		logger.info("EditPropertyServlet: doGet()");

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
				logger.severe("Property is null");
				request.setAttribute("error", "Error al editar la propiedad. Por favor, inténtelo de nuevo");
				RequestDispatcher view = request.getRequestDispatcher("WEB-INF/Error.jsp");
				view.forward(request, response);
			}
		} catch (NumberFormatException e) {
			logger.severe("parameter id is not a number");
			request.setAttribute("error", "Error al editar la propiedad. Por favor, inténtelo de nuevo");
			RequestDispatcher view = request.getRequestDispatcher("WEB-INF/Error.jsp");
			view.forward(request, response);

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

			Validador pat = new Validador("");

			if (pat.esValido(name, address, tel, city, dist, description, petFriendly, available)) {

				logger.info("Updating property");
				logger.info("Property id: " + id);
				logger.info("Property name: " + name);
				logger.info("Property address: " + address);
				logger.info("Property tel: " + tel);
				logger.info("Property city: " + city);
				logger.info("Property dist: " + dist);
				logger.info("Property description: " + description);
				logger.info("Property petFriendly: " + petFriendly);
				logger.info("Property available: " + available);

				Property property = new Property(id, name, address, tel, city, dist, description, petFriendly,
						available, (int) user.getId());
				propertyDao.update(property);

				// Servicios del hotel
				PropertiesServicesDAO propertyServiceDao = new JDBCPropertiesServicesDAOImpl();
				propertyServiceDao.setConnection(conn);

				// De los servicios seleccionados hay que comprobar cuales estan asociados a la
				// propiedad y cuales ha quitado
				ServicesDAO serviceDao = new JDBCServicesDAOImpl();
				serviceDao.setConnection(conn);

				String[] services = request.getParameterValues("servicios");

				// Lista de los servicios asociados a la propiedad
				List<PropertiesServices> serviciosPropiedad = propertyServiceDao.getAllByProperty(id); 
				
				// Lista de los servicios seleccionados por el usuario
				List<Service> listaServiciosSelecionados = new ArrayList<Service>();

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
				response.sendRedirect("ListPropertiesServlet.do");
			}
			else {
				logger.info("Property data is not valid");
				request.setAttribute("error", "Error al editar la propiedad. Los datos introducidos no son válidos. Por favor, inténtelo de nuevo.");
				RequestDispatcher view = request.getRequestDispatcher("WEB-INF/Error.jsp");
				view.forward(request, response);
			}
		

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Error updating property");
			request.setAttribute("error", "Error al editar la propiedad. Por favor, inténtelo de nuevo.");
			RequestDispatcher view = request.getRequestDispatcher("WEB-INF/Error.jsp");
			view.forward(request, response);
		}

	}

}
