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

import es.unex.pi.dao.JDBCPropertyDAOImpl;
import es.unex.pi.dao.PropertyDAO;
import es.unex.pi.model.Accommodation;
import es.unex.pi.model.Property;
import es.unex.pi.dao.*;
/**
 * Servlet implementation class DeleteAccommodationServlet
 */
public class DeleteAccommodationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(HttpServlet.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteAccommodationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		logger.info("DeleteAccommodationServlet : doGet");
		
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		AccommodationDAO accommodationDao = new JDBCAccommodationDAOImpl();
		accommodationDao.setConnection(conn);

		try {
			String id = request.getParameter("id");
			logger.info("Accommodation id (" + id + ")");
			long aid = 0;
			aid = Long.parseLong(id);
			logger.info("Accommodation id (" + id + ") and casting " + aid);
			Accommodation a = accommodationDao.get(aid);
			if (a != null) {

				request.setAttribute("accommodation", a);
				RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/AccommodationConfirmationPage.jsp");
				rd.forward(request, response);
			}

			else {
				logger.severe("Accommodation is null");
				request.setAttribute("error", "Error, la propiedad que está tratando de borrar no existe");
				RequestDispatcher view = request.getRequestDispatcher("WEB-INF/Error.jsp");
				view.forward(request, response);
			}
		} catch (

		NumberFormatException e) {
			logger.setLevel(Level.SEVERE);
			logger.severe("parameter id is not a number");
			logger.severe("Error en POST: DeleteAccommodationServlet " + e.getMessage());
			e.printStackTrace();

			request.setAttribute("error", "Error en la petición, el id no es un número válido");
			RequestDispatcher view = request.getRequestDispatcher("WEB-INF/Error.jsp");
			view.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		logger.info("DeleteAccommodationServlet: doPost");
		
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		AccommodationDAO accommodationDao = new JDBCAccommodationDAOImpl();
		accommodationDao.setConnection(conn);
		
		try {
			long ida = Long.parseLong(request.getParameter("id"));
			accommodationDao.delete(ida);
			response.sendRedirect("ListPropertiesServlet.do");
			
		}catch (Exception e) {
			logger.setLevel(Level.SEVERE);
			logger.severe("Error en POST: DeleteAccommodationServlet " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", "Error en la petición, el id no es un número válido");
			RequestDispatcher view = request.getRequestDispatcher("WEB-INF/Error.jsp");
			view.forward(request, response);
		}
		
		
		
	}

}
