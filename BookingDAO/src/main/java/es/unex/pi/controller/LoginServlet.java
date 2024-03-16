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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.model.User;
import es.unex.pi.util.Validador;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(HttpServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/Login.jsp");
		view.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/*
		 * TODO Comprobar los datos del usuario y en caso de que no sean correctos,
		 * indicarselo al usuario.
		 */
		logger.setLevel(Level.INFO);

		// Recuperar la sesión
		HttpSession session = request.getSession();

		// Recuperar conexión a la base de datos
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);
		User user = new User();

		try {

			// Obtención de los datos del formulario
			String email = request.getParameter("email");
			String password = request.getParameter("password");

			// Errores
			Map<String, String> messages = new HashMap<String, String>();

			System.out.print(email);
			logger.info("Email: " + email);
			logger.info("Password: " + password);

			if ((user = userDAO.getUserByEmail(email)) != null) {
				logger.info("Usuario encontrado");
				if (user.getPassword().equals(password)) {
					logger.info("Contraseña correcta");

					// Añadir el usuario a la sesión
					request.getSession().setAttribute("user", user);

					// Redirigir a la página principal
					response.sendRedirect("ListCategoriesServlet.do");

				} else {
					messages.put("password", "Contraseña incorrecta");
					request.setAttribute("messages", messages);

					logger.info("Contraseña incorrecta");

					request.setAttribute("email", email);

					RequestDispatcher view = request.getRequestDispatcher("WEB-INF/Login.jsp");
					view.forward(request, response);
				}
			} else {
				messages.put("email", "Usuario no encontrado, registrese para poder acceder al sistema.");
				session.setAttribute("messages", messages);

				logger.info("Usuario no encontrado");

				RequestDispatcher view = request.getRequestDispatcher("WEB-INF/Login.jsp");
				view.forward(request, response);
			}
		} catch (Exception e) {
			logger.info("Error en el login");
			e.printStackTrace();
		}
	}
}
