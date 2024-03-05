package es.unex.giiis.pi.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import es.unex.giiis.pi.utils.Validador;

/**
 * Servlet implementation class CreateAccountServlet
 */
public class CreateAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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

		// Obtención de los datos del formulario

		String name = request.getParameter("name");
		String surname = request.getParameter("secondname");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");


		if (password.equals(password2)) {
			System.out.println("Las contraseñas no coinciden");

			Validador pat = new Validador("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\w).{8,}");
			boolean validate = pat.esValido(password);
			if (validate) {
				System.out.println("Contraseña válida");
				System.out.println("Name: " + name);
				System.out.println("Surname: " + surname);
				System.out.println("Email: " + email);
				System.out.println("Password: " + password);
				System.out.println("Password2: " + password2);
//			response.getWriter().println("<!DOCTYPE html><html><body>");
//			response.getWriter().println("<br> Email:" + email);
//			response.getWriter().println("<br> Password:" + password);
//			response.getWriter().println("</p></body></html>");
				RequestDispatcher rd = request.getRequestDispatcher("search_and_list.html");
				rd.forward(request, response);
			} else {
				System.out.println("La contraseña no cumple con los criterios requeridos");
			}
		}
		else {
			System.out.println("Las contraseñas no coinciden");
			//TODO controlar que las contraseñas no coinciden (mensaje de error + redirigir a la página de registro con los campos rellenos)
		}
	}

}
