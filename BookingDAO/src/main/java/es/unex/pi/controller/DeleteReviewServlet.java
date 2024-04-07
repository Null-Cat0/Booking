package es.unex.pi.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.unex.pi.model.Property;
import es.unex.pi.model.Review;
import es.unex.pi.model.User;
import es.unex.pi.dao.*;

/**
 * Servlet implementation class DeleteReviewServlet
 */
public class DeleteReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(DeleteReviewServlet.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteReviewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		logger.setLevel(Level.INFO);
		logger.info("DeleteReviewServlet: doGet");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.setLevel(Level.INFO);
		logger.info("DeleteReviewServlet: doPost");
		
		
		HttpSession session = request.getSession();
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		
		String propertyId = request.getParameter("id");
		User user = (User) session.getAttribute("user");
		
		ReviewDAO rdao = new JDBCReviewDAOImpl();
		rdao.setConnection(conn);
		
		PropertyDAO pdao = new JDBCPropertyDAOImpl();
		pdao.setConnection(conn);
		
		try {
			ReviewDAO reviewDAO = new JDBCReviewDAOImpl();
			reviewDAO.setConnection(conn);
			long idp = Long.parseLong(propertyId);
			reviewDAO.delete(idp, user.getId());
			logger.info("Review borrada");
			
			Property p = pdao.get(idp);			
			List<Review> reviews = rdao.getAllByProperty(idp);
			if (reviews != null && !reviews.isEmpty()) {
				int acum = 0;
				for (Review r : reviews) {
					acum += r.getGrade();
				}
				double media = (double) acum / reviews.size();
				p.setGradesAverage(media);
			}
			else {
				p.setGradesAverage(0);
			}
			if (!pdao.update(p)) {
                logger.info("Error saving property in NewReviewServlet");
			}
									
		}catch(Exception e) {
            e.printStackTrace();
			logger.info("Error al borrar la review");
        }
	
	}

}
