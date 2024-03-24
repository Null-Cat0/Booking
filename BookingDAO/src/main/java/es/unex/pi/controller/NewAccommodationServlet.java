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
import java.util.logging.Level;
import java.util.logging.Logger;

import es.unex.pi.dao.AccommodationDAO;
import es.unex.pi.dao.JDBCAccommodationDAOImpl;
import es.unex.pi.model.Accommodation;

/**
 * Servlet implementation class NewAccommodationServlet
 */
public class NewAccommodationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(HttpServlet.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewAccommodationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		logger.info("GET request");
		
		try {
			 long idp = Long.parseLong(request.getParameter("id"));
			 request.setAttribute("idp", idp);
			 request.setAttribute("tipoInformacion", "Añadir");
			 
		} catch (Exception e) {
			e.printStackTrace();
			logger.severe("Error: " + e.getMessage());
		}
		
		
		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/NewAccommodation.jsp");
		view.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		logger.setLevel(Level.INFO);
		logger.info("POST request");
		

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		
		try {
			//Datos de la habitacion
			String name = request.getParameter("name");
			int price = Integer.parseInt(request.getParameter("price"));
			String description = request.getParameter("description");
			int nAccommodations = Integer.parseInt(request.getParameter("nAccommodations"));
			
			//Id del alojamiento
			Long idp = Long.parseLong(request.getParameter("idp"));
			
			//Crear objeto alojamiento	
			Accommodation a = new Accommodation(name, price, description, nAccommodations,idp);
			
			//Guardar objeto habitacion en la bd
			AccommodationDAO aDAO = new JDBCAccommodationDAOImpl();
			aDAO.setConnection(conn);
			aDAO.add(a);
			
			response.sendRedirect("ListPropertiesServlet.do");
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			logger.severe("Error: " + e.getMessage());
			
		}

	}

}
