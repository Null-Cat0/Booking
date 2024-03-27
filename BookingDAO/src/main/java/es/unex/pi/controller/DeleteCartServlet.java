package es.unex.pi.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import es.unex.pi.model.Accommodation;
import es.unex.pi.model.Property;
import es.unex.pi.util.Entry;

/**
 * Servlet implementation class DeleteCartServlet
 */
public class DeleteCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(HttpServlet.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteCartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		HttpSession session = request.getSession();
		Map<Property, List<Entry<Accommodation, Integer>>> reservas = (Map<Property, List<Entry<Accommodation, Integer>>>) session
				.getAttribute("cart");
		String id = request.getParameter("idP");
		logger.info("Property id (" + id + ")");
		long pid = 0;
		pid = Long.parseLong(id);
		logger.info("Property id (" + id + ") and casting " + pid);
		Property property = new Property();
		for (Property p : reservas.keySet()) {
			if (p.getId() == pid) {
				property = p;
			}
		}
		reservas.remove(property);
		session.setAttribute("cart", reservas);
		
		response.sendRedirect("AddCartServlet.do");
		
	}

}
