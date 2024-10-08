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

import es.unex.pi.model.Favourite;
import es.unex.pi.model.User;
import es.unex.pi.dao.*;
/**
 * Servlet implementation class DeleteFavouriteServlet
 */
public class DeleteFavouriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    Logger logger = Logger.getLogger(DeleteFavouriteServlet.class.getName());
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteFavouriteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		logger.info("DeleteFavouriteServlet: doGet");
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		logger.info("DeleteFavouriteServlet: doPost");
		
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			
			String id = request.getParameter("propertyId"); // id of the favourite to delete
			Long idp = Long.parseLong(id);
			logger.info("DeleteFavouriteServlet: idp = " + idp); 
			
			Connection conn = (Connection) getServletContext().getAttribute("dbConn");
			FavouriteDAO fdao = new JDBCFavouriteDAOImpl();
			fdao.setConnection(conn);
			
			//Si el favorito existe, lo borramos
			if(fdao.get(user.getId(),idp)!=null)		
				fdao.delete(new Favourite(user.getId(),idp));
			
			response.sendRedirect("ListFavouritesServlet.do");
			
		}
		catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Error al borrar el favorito. Por favor, inténtelo de nuevo.");
			RequestDispatcher view = request.getRequestDispatcher("WEB-INF/Error.jsp");
			view.forward(request, response);
		}
	
	}

}
