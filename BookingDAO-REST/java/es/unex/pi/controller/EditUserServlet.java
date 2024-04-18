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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.model.Message;
import es.unex.pi.model.User;
import es.unex.pi.dao.*;

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

		logger.setLevel(Level.INFO);
		logger.info("ListUserDataServlet: doGet");

		// Recuperar la sesión y cargar los datos del usuario de la sesion en la request
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		// Mensajes enviados
		MessageDAO messageDAO = new JDBCMessageDAOImpl();
		messageDAO.setConnection(conn);

		// Hay que hacer un mapa con los mensajes enviados y el email del receptor
		Map<String, List<Message>> mapMensajesEnviados = new LinkedHashMap<String, List<Message>>();
		List<Message> mEnviados = messageDAO.getBySender(user.getId());
		if (mEnviados == null) {
			logger.info("No hay mensajes enviados");
		} else {
			for (Message m : mEnviados) {
				UserDAO userDAO = new JDBCUserDAOImpl();
				userDAO.setConnection(conn);
				User u = userDAO.get(m.getIdr());
				String email = u.getEmail();
				if (mapMensajesEnviados.containsKey(email)) {
					mapMensajesEnviados.get(email).add(m);
				} else {
					List<Message> mensajes = new java.util.ArrayList<Message>();
					mensajes.add(m);
					mapMensajesEnviados.put(email, mensajes);
				}

			}
			request.setAttribute("mEnviados", mapMensajesEnviados);
		}

		// Mensajes recibidos

		Map<String, List<Message>> mapMensajesRecibidos = new LinkedHashMap<String, List<Message>>();
		List<Message> mRecibidos = messageDAO.getByR(user.getId());
		if (mRecibidos == null) {
			logger.info("No hay mensajes enviados");
		} else {
			for (Message m : mRecibidos) {
				UserDAO userDAO = new JDBCUserDAOImpl();
				userDAO.setConnection(conn);
				User u = userDAO.get(m.getIds());
				String email = u.getEmail();
				if (mapMensajesRecibidos.containsKey(email)) {
					logger.info("Ya existe el email");
					logger.info("Email: " + email);
					logger.info("Mensaje: " + m.getTexto());
					mapMensajesRecibidos.get(email).add(m);
				} else {
					List<Message> mensajes = new java.util.ArrayList<Message>();
					mensajes.add(m);
					mapMensajesRecibidos.put(email, mensajes);
				}
			}
			request.setAttribute("mRecibidos", mapMensajesRecibidos);
		}

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

		logger.setLevel(Level.INFO);
		logger.info("EditUserServlet: doPost");

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

//			Validador pat = new Validador("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\w).{8,}");
			boolean validate = true;// pat.esValido(password);
			if (validate) {
				logger.info("Contraseña válida");
				logger.info("Name: " + name);
				logger.info("Surname: " + surname);
				logger.info("Email: " + email);
				logger.info("Password: " + password);

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

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
