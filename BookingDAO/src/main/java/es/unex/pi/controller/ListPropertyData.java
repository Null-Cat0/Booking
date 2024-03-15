package es.unex.pi.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Logger;

import es.unex.pi.model.Property;
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		PropertyDAO propertyDao = new JDBCPropertyDAOImpl();
		propertyDao.setConnection(conn);
		
		try{
			String id = request.getParameter("id");
			logger.info("Property id ("+id+")");
		    long pid = 0;
		    pid = Long.parseLong(id);
		    logger.info("Property id ("+id+") and casting "+pid);
		    Property property = propertyDao.get(pid);
		    if(property!=null) {
            	request.setAttribute("property", property);
        		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/ListPropertyData.jsp");
        		rd.forward(request, response);
            }
            else {
            	logger.info("Property is null");
            	response.sendRedirect("LisCategoriesServlet.do");
            }
		}
			catch (NumberFormatException e) {
				logger.info("parameter id is not a number");

				//TODO: Redirect to ListOrderServlet.
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
