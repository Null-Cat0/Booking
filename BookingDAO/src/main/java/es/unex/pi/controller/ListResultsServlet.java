package es.unex.pi.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.model.User;

/**
 * Servlet implementation class ListResultsServlet
 */
public class ListResultsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(ListResultsServlet.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListResultsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Obtener datos del form de busqueda y hacer una lista con los hoteles que cumplan los requisitos
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		logger.setLevel(Level.INFO);
		
		//Obtener los datos del formulario de busqueda
		String city = request.getParameter("city");
		
		//Mostrar los datos obtenidos
		logger.info("City: " + city);
		
		//Obtener la base de datos
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		
		
		
		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);
		User user = new User();

	
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
