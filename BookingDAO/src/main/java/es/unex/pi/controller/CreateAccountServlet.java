package es.unex.pi.controller;

import jakarta.servlet.RequestDispatcher;
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
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
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

		// Obtención de los datos del formulario
		String name = request.getParameter("name");
		String surname = request.getParameter("secondname");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");


		if (userDAO.getUserByEmail(email) != null) {
			logger.info("Existe un usuario con el email proporcionado");
			// TODO controlar que el usuario ya existe (mensaje de error + redirigir a la
			// página de registro con los campos rellenos)
		} else {
			logger.info("No existe un usuario con el email proporcionado");

			if (password.equals(password2)) {
				logger.info("las contraseñas coinciden");

//				Validador pat = new Validador("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\w).{8,}");
				boolean validate = true;//pat.esValido(password);
				if (validate) {
					logger.info("Contraseña válida");
					logger.info("Name: " + name);
					logger.info("Surname: " + surname);
					logger.info("Email: " + email);
					logger.info("Password: " + password);
					logger.info("Password2: " + password2);
					
					// Crear un objeto User con los datos del formulario
					User user = new User(name, surname, email, password);
					userDAO.add(user);
					
					//Añadir el usuario a la sesión
					request.getSession().setAttribute("user", user);					
					
					RequestDispatcher rd = request.getRequestDispatcher("search_and_list.html");
					rd.forward(request, response);
				} else {
					System.out.println("La contraseña no cumple con los criterios requeridos");
				}
			} else {
				System.out.println("Las contraseñas no coinciden");
				// TODO controlar que las contraseñas no coinciden (mensaje de error + redirigir
				// a la página de registro con los campos rellenos)
			}
		}
	}

}
