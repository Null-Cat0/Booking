package es.unex.pi.controller;

import jakarta.servlet.ServletException;
import java.sql.Connection;
import java.util.logging.Logger;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import es.unex.pi.dao.*;
import es.unex.pi.model.Property;
import es.unex.pi.model.Review;
import es.unex.pi.model.User;

/**
 * Servlet implementation class NewReviewServlet
 */
public class NewReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(NewReviewServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NewReviewServlet() {
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
		logger.info("doGet : NewReviewServlet.do");
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		logger.info("doPost : NewReviewServlet.do");

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		HttpSession session = request.getSession();

		// User
		User user = (User) session.getAttribute("user");

		// Property
		PropertyDAO pdao = new JDBCPropertyDAOImpl();
		pdao.setConnection(conn);

		// Reviews
		ReviewDAO rdao = new JDBCReviewDAOImpl();
		rdao.setConnection(conn);

		try {
			String id = request.getParameter("propertyId");
			logger.info("Casting id to long in doPost : NewReviewServlet");
			long propertyId = Long.parseLong(id);

			Property p = pdao.get(propertyId);

			if (p != null) {

				if (request.getParameter("puntuacion") != null) {

					int grade = Integer.parseInt(request.getParameter("puntuacion"));
					String review = request.getParameter("valoracion");
					Review r = new Review(propertyId, user.getId(), review, grade);
					rdao.add(r);
				}
			}
			response.sendRedirect("ListPropertyData.do?id=" + propertyId);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
