package es.unex.pi.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

import es.unex.pi.util.Validador;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		/*
		 * TODO Comprobar existencia del usuario en la base de datos Si existe y los
		 * datos correctos, redirigir a la página principal Si no existe, indicarselo al
		 * usuario
		 */
		// doGet(request, response);

		// Obtención de los datos del formulario
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		System.out.println("Email: " + email);
		System.out.println("Password: " + password);

		// Comprobación de la contraseña
//		Validador pat = new Validador("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\w).{8,}");
		boolean validate = true ; // pat.esValido(password);
		if (validate) {
			System.out.println("Contraseña válida");
//			response.getWriter().println("<!DOCTYPE html><html><body>");
//			response.getWriter().println("<br> Email:" + email);
//			response.getWriter().println("<br> Password:" + password);
//			response.getWriter().println("</p></body></html>");
			response.sendRedirect("ListPropertiesServlet.do");
		} else {
			System.out.println("La contraseña no cumple con los criterios requeridos");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}
}
