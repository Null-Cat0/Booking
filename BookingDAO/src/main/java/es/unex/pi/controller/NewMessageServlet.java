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
import java.util.logging.Logger;

import es.unex.pi.model.Message;
import es.unex.pi.model.User;
import es.unex.pi.dao.*;

/**
 * Servlet implementation class NewMessageServlet
 */
public class NewMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(HttpServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NewMessageServlet() {
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		logger.info("NewMessageServlet : doPost");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		MessageDAO messageDAO = new JDBCMessageDAOImpl();
		messageDAO.setConnection(conn);

		// No debería llegar aquí si no está logueado
		if (user == null) {
			logger.info("NewMessageServlet : User is null");
			response.sendRedirect("login.jsp");
		}
		try {
			String message = request.getParameter("message");
			String recipient = request.getParameter("recipient");
			if (recipient == null) {
				logger.info("NewMessageServlet : recipient is null");
				logger.info("NewMessageServlet : Email is null");
				request.setAttribute("error", "Error: Usuario no encontrado");
				RequestDispatcher view = request.getRequestDispatcher("WEB-INF/Error.jsp");
				view.forward(request, response);
			} else {

				if (message == null) {
					logger.info("NewMessageServlet : message is null");
				}

				Message m = new Message();

				UserDAO userDAO = new JDBCUserDAOImpl();
				userDAO.setConnection(conn);

				System.out.println("recipient: " + recipient);
				User r = userDAO.getUserByEmail(recipient);
				if (r == null) {
					logger.info("NewMessageServlet : User not found");
					request.setAttribute("error", "Error: Usuario no encontrado");
					RequestDispatcher view = request.getRequestDispatcher("WEB-INF/Error.jsp");
					view.forward(request, response);
				} else {
					m.setTexto(message);
					m.setIdr(r.getId());
					m.setIds(user.getId());

					if (messageDAO.add(m) == -1)
						logger.severe("Error: ");
					else

						logger.info("NewMessageServlet : Message sent from " + user.getName() + " to " + r.getName()
								+ " : " + message);
					
				}
				response.sendRedirect("EditUserServlet.do");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.severe("Error: ");
			request.setAttribute("error", "Error: No has introducido un mensaje o el usuario ");
			RequestDispatcher view = request.getRequestDispatcher("WEB-INF/Error.jsp");
			view.forward(request, response);
		}

	}

}
