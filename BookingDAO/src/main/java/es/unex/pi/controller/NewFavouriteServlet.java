package es.unex.pi.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Logger;

import es.unex.pi.model.Favourite;
import es.unex.pi.model.User;
import es.unex.pi.dao.*;

/**
 * Servlet implementation class NewFavouriteServlet
 */
public class NewFavouriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(NewFavouriteServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NewFavouriteServlet() {
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
		// TODO Auto-generated method stub
		logger.info("doPost : NewFavouriteServlet");

		HttpSession session = request.getSession();
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");

		User user = (User) session.getAttribute("user");

		try {
			String id = request.getParameter("propertyId"); // id of the favourite to add
			Long idp = Long.parseLong(id);

			logger.info("User: " + user.getId() + " Property: " + idp);

			Favourite fav = new Favourite(user.getId(), idp);
			FavouriteDAO fdao = new JDBCFavouriteDAOImpl();
			fdao.setConnection(conn);

			fdao.add(fav);
			
		} catch (Exception e) {
			logger.info("Error: " + e.getMessage());
			
		}
		
		response.sendRedirect("ListFavouritesServlet.do");

	}

}
