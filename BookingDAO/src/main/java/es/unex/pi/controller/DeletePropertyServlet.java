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

/**
 * Servlet implementation class DeletePropertyServlet
 */
public class DeletePropertyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(DeletePropertyServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeletePropertyServlet() {
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
		HttpSession session = request.getSession();
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

				request.setAttribute("property", property);
				RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/ConfirmationPage.jsp");
				rd.forward(request, response);
			}

			else {
				logger.info("Property is null");
				response.sendRedirect("LisCategoriesServlet.do");
			}
		} catch (

		NumberFormatException e) {
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
		logger.info("DeletePropertyServlet: Request received");
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
				if (property.getIdu() == user.getId()) {
					propertyDao.delete(pid);
					response.sendRedirect("ListCategoriesServlet.do");
				} else {
					logger.info("User is not the owner of the property");
					response.sendRedirect("ListCategoriesServlet.do");
				}
			} else {
				logger.info("Property is null");
				response.sendRedirect("ListCategoriesServlet.do");
			}
		} catch (NumberFormatException e) {
			logger.info("parameter id is not a number");

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
