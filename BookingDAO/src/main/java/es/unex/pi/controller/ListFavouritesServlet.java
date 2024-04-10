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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.dao.*;
import es.unex.pi.model.Favourite;
import es.unex.pi.model.Property;
import es.unex.pi.model.User;
/**
 * Servlet implementation class ListFavouritesServlet
 */
public class ListFavouritesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(HttpServlet.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListFavouritesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		logger.info("ListFavouritesServlet: doGet");
		HttpSession session = request.getSession();
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		
		FavouriteDAO fdao = new JDBCFavouriteDAOImpl();
		fdao.setConnection(conn);
		
		User user = (User) session.getAttribute("user");
		
		List<Favourite> listFav = fdao.getAllByUser(user.getId());
		
		
		PropertyDAO pdao = new JDBCPropertyDAOImpl();
		pdao.setConnection(conn);
		
		List<Property> listProp = new ArrayList<Property>();
		for (Favourite fav : listFav) {
			System.out.println("Favourite: " + fav.getIdu() + " " + fav.getIdp());
			listProp.add(pdao.get(fav.getIdp()));
		}
		session.setAttribute("listProp", listProp);
	
		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/Favourites.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
