package es.unex.pi.controller;

import java.io.IOException;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import es.unex.pi.dao.CategoryDAO;
import es.unex.pi.dao.PropertyDAO;
import es.unex.pi.dao.PropertiesCategoriesDAO;
import es.unex.pi.dao.JDBCCategoryDAOImpl;
import es.unex.pi.dao.JDBCPropertyDAOImpl;
import es.unex.pi.dao.JDBCPropertiesCategoriesDAOImpl;
import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.model.Accommodation;
import es.unex.pi.model.Category;
import es.unex.pi.model.Property;
import es.unex.pi.model.PropertiesCategories;
import es.unex.pi.model.User;
import es.unex.pi.util.Triplet;

import jakarta.servlet.RequestDispatcher;

import java.sql.Connection;
import es.unex.pi.dao.*
;

/**
 * Servlet implementation class ListPropertyServlet
 */
public class ListPropertiesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListPropertiesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		logger.info("ListPropertiesServlet: doGet");

		
		HttpServletRequest req = (HttpServletRequest) request;
		User user = (User) req.getSession().getAttribute("user");
		
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");

		PropertyDAO propertyDAO = new JDBCPropertyDAOImpl();
		propertyDAO.setConnection(conn);
		
		List<Property> properties = new ArrayList<Property>();
		properties = propertyDAO.getAllByUser(user.getId());
		
		AccommodationDAO accommodationDAO = new JDBCAccommodationDAOImpl();
		accommodationDAO.setConnection(conn);
		
		Map<Property, List<Accommodation>> propertiesAccommodations = new HashMap<Property, List<Accommodation>>();
		
		for (Property p : properties) {
			List<Accommodation> accommodations = new ArrayList<Accommodation>();
			accommodations = accommodationDAO.getAccommodationProperty(p.getId());
			propertiesAccommodations.put(p, accommodations);
		}
		
		
		request.setAttribute("propertiesAccommodations", propertiesAccommodations);
		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/ListPropertiesUser.jsp");
		view.forward(request,response);
		
	}

	
}
