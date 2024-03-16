package es.unex.pi.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		
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
					logger.info("Servicios asociados a la propiedad");
					request.setAttribute("listServices", listaServicios);
					
					// Lista de servicios que no tiene la propiedad
					List<Service> listaServiciosNoAsociados = new ArrayList<Service>();
					listaServiciosNoAsociados = serviceDao.getAllNotInProperty(pid);
					logger.info("Servicios no asociados a la propiedad");
					request.setAttribute("listServicesNotIn", listaServiciosNoAsociados);
					
					
					
				} else {
					logger.info("No hay servicios asociados a la propiedad");
				}

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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
