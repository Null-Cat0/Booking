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
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.model.User;
import es.unex.pi.util.Validador;

/**
 * Servlet implementation class CreateAccountServlet
 */
public class CreateAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(HttpServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateAccountServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Redirigir a la página de registro
		request.setAttribute("tipoInformacion", "Crear");

		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/CreateAccount.jsp");
		view.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * TODO Comprobar existencia del usuario en la base de datos Si existe,
		 * indicarselo al usuario Si no existe, redirigir a la página principal
		 */
		logger.setLevel(Level.INFO);
		// Recuperar conexión a la base de datos y crear un UserDAO
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);

		try {

			// Obtención de los datos del formulario
			String name = request.getParameter("name");
			String surname = request.getParameter("secondname");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String password2 = request.getParameter("password2");
			
			// Crear un objeto User con los datos del formulario
			User user = new User(name, surname, email, password);

			// Errores
			Map<String, String> messages = new HashMap<String, String>();

			if (userDAO.getUserByEmail(email) != null) {
				logger.info("Existe un usuario con el email proporcionado");
				
				messages.put("email", "El email ya está en uso");
				request.setAttribute("messages", messages);
				request.setAttribute("user", user);
				
				RequestDispatcher view = request.getRequestDispatcher("WEB-INF/CreateAccount.jsp");
				view.forward(request, response);
			} else {
				logger.info("No existe un usuario con el email proporcionado");

				if (password != null & password.equals(password2)) {
					logger.info("las contraseñas coinciden");

//				Validador pat = new Validador("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\w).{8,}");
					boolean validate = true;// pat.esValido(password);
					if (validate) {
						logger.info("Contraseña válida");
						logger.info("Name: " + name);
						logger.info("Surname: " + surname);
						logger.info("Email: " + email);
						logger.info("Password: " + password);
						logger.info("Password2: " + password2);

						// Crear un objeto User con los datos del formulario
						long id = userDAO.add(user);

						// Añadir el usuario a la sesión
						user.setId(id);
						HttpSession session = request.getSession();
						session.setAttribute("user", user);
						
						response.sendRedirect("ListCategoriesServlet.do");
					} else {
						logger.info("Contraseña no válida");
						
						messages.put("password", "La contraseña no es válida");
						request.setAttribute("messages", messages);
						request.setAttribute("user", user);
						

						RequestDispatcher view = request.getRequestDispatcher("WEB-INF/CreateAccount.jsp");
						view.forward(request, response);
					}
				} else {
					logger.info("Las contraseñas no coinciden");
					
					messages.put("password", "Las contraseñas no coinciden");
					request.setAttribute("messages", messages);
					request.setAttribute("user", user);
					

					RequestDispatcher view = request.getRequestDispatcher("WEB-INF/CreateAccount.jsp");
					view.forward(request, response);
				}
			}
		} catch (Exception e) {
			logger.info("Error al crear el usuario");
		}
	}

}
