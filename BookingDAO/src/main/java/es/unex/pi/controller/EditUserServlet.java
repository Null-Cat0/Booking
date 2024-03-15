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
 * Servlet implementation class ListUserDataServlet
 */
public class EditUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(HttpServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditUserServlet() {
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
		logger.setLevel(Level.INFO);
		logger.info("ListUserDataServlet: Request received");

		// Recuperar la sesión y cargar los datos del usuario de la sesion en la request
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		request.setAttribute("user", user);
		request.setAttribute("tipoInformacion", "Editar");
		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/CreateAccount.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);
		logger.setLevel(Level.INFO);
		logger.info("EditUserServlet: Request received");

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);

		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("user");

		try {

			// Obtención de los datos del formulario
			String name = request.getParameter("name");
			String surname = request.getParameter("secondname");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String password2 = request.getParameter("password2");
			if (password.equals(password2)) {
				logger.info("las contraseñas coinciden");

//			Validador pat = new Validador("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\w).{8,}");
				boolean validate = true;// pat.esValido(password);
				if (validate) {
					logger.info("Contraseña válida");
					logger.info("Name: " + name);
					logger.info("Surname: " + surname);
					logger.info("Email: " + email);
					logger.info("Password: " + password);
					logger.info("Password2: " + password2);

					// Crear un objeto User con los datos del formulario
					User user = new User(sessionUser.getId(), name, surname, email, password);
					userDAO.update(user);

					// Añadir el usuario a la sesión
					session.removeAttribute("user");
					session.setAttribute("user", user);

					response.sendRedirect("ListCategoriesServlet.do");
				} else {
					logger.info("Contraseña no válida");
				}
			} else {
				logger.info("Las contraseñas no coinciden");
				// TODO controlar que las contraseñas no coinciden (mensaje de error + redirigir
				// a la página de registro con los campos rellenos)
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
