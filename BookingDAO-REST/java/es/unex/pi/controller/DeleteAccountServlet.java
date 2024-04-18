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
import java.util.logging.Level;
import java.util.logging.Logger;

import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.model.User;

/**
 * Servlet implementation class DeleteAccountServlet
 */
public class DeleteAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(HttpServlet.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteAccountServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		logger.info("DeleteAccountServlet : doGet");
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user != null) {
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/UserConfirmationPage.jsp");
			rd.forward(request, response);
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/Login.jsp");
			rd.forward(request, response);
		}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		logger.info("DeleteAccountServlet : doGet");
		
		HttpSession session = request.getSession();
		Connection connection = (Connection) getServletContext().getAttribute("dbConn");
		
		UserDAO dao = new JDBCUserDAOImpl();
		dao.setConnection(connection);
		
		User user = (User) session.getAttribute("user");
		
		logger.info("User: " + user.toString());
		if (user != null) {
			dao.delete(user.getId());
			session.removeAttribute("user");
			user = null;
			session.invalidate();
			
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/Login.jsp");
			rd.forward(request, response);
		}
		

	}

}
