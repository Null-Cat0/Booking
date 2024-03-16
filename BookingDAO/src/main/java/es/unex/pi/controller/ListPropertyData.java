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

import es.unex.pi.model.*;
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
				if(habitaciones!=null) {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
