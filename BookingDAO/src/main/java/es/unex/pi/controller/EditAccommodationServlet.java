package es.unex.pi.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import es.unex.pi.dao.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import es.unex.pi.model.Accommodation;
import es.unex.pi.model.Property;
import es.unex.pi.model.User;

/**
 * Servlet implementation class EditAccommodationServlet
 */
public class EditAccommodationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(HttpServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditAccommodationServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		logger.info("EditAccommodationServlet: doGet");

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		AccommodationDAO accommodationDao = new JDBCAccommodationDAOImpl();
		accommodationDao.setConnection(conn);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		try {
			String id = request.getParameter("id");
			logger.info("Accommodation id (" + id + ")");
			long aid = 0;
			aid = Long.parseLong(id);
			logger.info("Accommodation id (" + id + ") and casting " + aid);
			Accommodation a = accommodationDao.get(aid);

			if (a != null) {
				PropertyDAO propertyDao = new JDBCPropertyDAOImpl();
				propertyDao.setConnection(conn);
				Property p = propertyDao.get(a.getIdp());
				if (p != null) {
					if (p.getIdu() != user.getId()) {
						logger.info("User is not the owner of the property");
						response.sendRedirect("ListCategoriesServlet.do");
					} else {

						logger.info("Accommodation is not null");
						request.setAttribute("idp", a.getIdp());
						request.setAttribute("accommodation", a);
					}

				}

			} else {
				response.sendRedirect("ListCategoriesServlet.do");
			}

			request.setAttribute("tipoInformacion", "Editar");
			RequestDispatcher rq = request.getRequestDispatcher("/WEB-INF/NewAccommodation.jsp");
			rq.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		logger.info("EditAccommodationServlet: doPost");
		
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		HttpSession session = request.getSession();
		
		AccommodationDAO accommodationDao = new JDBCAccommodationDAOImpl();
		accommodationDao.setConnection(conn);
		

		User user = (User) session.getAttribute("user");
		
		try {

			String name = request.getParameter("name");
			String description = request.getParameter("description");
			int price = Integer.parseInt(request.getParameter("price"));
			int nAccommodations = Integer.parseInt(request.getParameter("nAccommodations"));
			long id = Long.parseLong(request.getParameter("ida"));
			long idp = Long.parseLong(request.getParameter("idp"));

	
			PropertyDAO pDAO = new JDBCPropertyDAOImpl();
			pDAO.setConnection(conn);
			Property p = pDAO.get(idp);
			if (p !=null) 
			{
				if(p.getIdu() != user.getId())
				{
					logger.info("User is not the owner of the property");
					response.sendRedirect("ListPropertiesServlet.do");
				}
			} else {
				logger.info("Property is null");
				response.sendRedirect("ListPropertiesServlet.do");
			}
			
			Accommodation a = new Accommodation(id, name, price, description, nAccommodations, idp);
			accommodationDao.update(a);

			response.sendRedirect("ListPropertiesServlet.do");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
