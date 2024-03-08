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
import es.unex.pi.model.Category;
import es.unex.pi.model.Property;
import es.unex.pi.model.PropertiesCategories;
import es.unex.pi.model.User;
import es.unex.pi.util.Triplet;

import jakarta.servlet.RequestDispatcher;

import java.sql.Connection;



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
		
		
		logger.info("Atendiendo GET");
		
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);

		PropertyDAO PropertyDAO = new JDBCPropertyDAOImpl();
		PropertyDAO.setConnection(conn);
		
		CategoryDAO categoryDAO = new JDBCCategoryDAOImpl();
		categoryDAO.setConnection(conn);
		
		PropertiesCategoriesDAO propertiesCategoriesDAO = new JDBCPropertiesCategoriesDAOImpl();
		propertiesCategoriesDAO.setConnection(conn);
		
		List<Property> propertiesList = PropertyDAO.getAll();
		
		Iterator<Property> itPropertyList = propertiesList.iterator();

		List<Triplet<Property, User, List<PropertiesCategories>>> propertiesUserList = new ArrayList<Triplet<Property, User, List<PropertiesCategories>>>();

		while(itPropertyList.hasNext()) {
			Property property = (Property) itPropertyList.next();
			User user = userDAO.get(property.getIdu());
			List<PropertiesCategories> propertiesCategories = propertiesCategoriesDAO.getAllByProperty(property.getId());
			
			logger.info("User " + user.getName());

			propertiesUserList.add(new Triplet<Property, User, List<PropertiesCategories>>(property,user,propertiesCategories));
		}
		
		
		List<User> listUser = new ArrayList<User>();
		listUser = userDAO.getAll();
		Iterator<User> itUser = listUser.iterator();
		Map<User,List<Property>> userPropertiesMap = new HashMap<User,List<Property>>();
		
		while(itUser.hasNext()) {
			User user = itUser.next();
			propertiesList = PropertyDAO.getAllByUser(user.getId());
			userPropertiesMap.put(user, propertiesList);
		}
		
		request.setAttribute("propertiesList",propertiesUserList);
		request.setAttribute("usersMap", userPropertiesMap);
		
		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/ListPropertiesUser.jsp");
		view.forward(request,response);
		
	}

	
}
