package es.unex.pi.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import es.unex.pi.dao.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import es.unex.pi.model.Accommodation;
import es.unex.pi.model.Property;

/**
 * Servlet implementation class EditAccommodationServlet
 */
public class EditAccommodationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(HttpServlet.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditAccommodationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		logger.info("GET request");
		
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
			if(a!=null) {
				logger.info("Accommodation is not null");
				request.setAttribute("idp", a.getIdp());
				request.setAttribute("accommodation", a);
			}else {
				logger.info("Accommodation is null");
			}
			
			
			request.setAttribute("tipoInformacion", "Editar");
			RequestDispatcher rq = request.getRequestDispatcher("/WEB-INF/NewAccommodation.jsp");
			rq.forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		AccommodationDAO accommodationDao = new JDBCAccommodationDAOImpl();
		accommodationDao.setConnection(conn);
		try {
		
			String name = request.getParameter("name");
			String description = request.getParameter("description");
			int price = Integer.parseInt(request.getParameter("price"));
			int nAccommodations = Integer.parseInt(request.getParameter("nAccommodations"));
			long id = Long.parseLong(request.getParameter("ida"));
			long idp = Long.parseLong(request.getParameter("idp"));

			Map<String, String> messages = new HashMap<String, String>();

			//TODO :Comprobar que los datos de la habitacion son correctos
			
			Accommodation a = new Accommodation(id,name,price,description,nAccommodations,idp);
			accommodationDao.update(a);
			
			
			response.sendRedirect("ListPropertiesServlet.do");
		
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
