package es.unex.pi.resources;

import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.model.Property;
import es.unex.pi.model.Review;
import es.unex.pi.model.User;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import es.unex.pi.dao.*;

@Path("/reviews")
public class ReviewResource {
	Logger logger = Logger.getLogger(BookingResource.class.getName());

	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;

	@GET
	@Path("/property/{propertyid: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Review> getReviewsJSON(@PathParam("propertyid") long propertyid, @Context HttpServletRequest request) {
		logger.info("getReviewsJSON");

		List<Review> reviews = null;
		Connection conn = (Connection) sc.getAttribute("dbConn");

		// ReviewDAO
		ReviewDAO rDao = new JDBCReviewDAOImpl();
		rDao.setConnection(conn);
		reviews = rDao.getAllByProperty(propertyid);
		for (Review review : reviews) {
			System.out.println(review.toString());
		}

		if (reviews == null) {
			logger.info("No reviews found");
		}
		return reviews;
	}

	@GET
	@Path("/{reviewid: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserNameReviewJSON(@PathParam("reviewid") long reviewid, @Context HttpServletRequest request) {
		logger.info("getReviewsJSON");

		Connection conn = (Connection) sc.getAttribute("dbConn");

		Review review = null;
		ReviewDAO rDao = new JDBCReviewDAOImpl();
		rDao.setConnection(conn);
		review = rDao.get(reviewid);

		if (review == null) {
			logger.info("No review found");
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		UserDAO uDao = new JDBCUserDAOImpl();
		uDao.setConnection(conn);
		User user = uDao.get(review.getIdu());

		if (user == null) {
			// Devolver un error 500 si no se ha podido obtener el usuario
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		// Construir el objeto JSON manualmente
		JsonObject responseJson = Json.createObjectBuilder().add("name", user.getName()).build();

		// Devolver el objeto JSON como respuesta
		return Response.ok().entity(responseJson).build();

	}

	@POST
	public Response addReview(Review review, @Context HttpServletRequest request) {
		logger.info("addReview");
		Connection conn = (Connection) sc.getAttribute("dbConn");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			logger.info("No user logged in");
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
		review.setIdu(user.getId());
		ReviewDAO rDao = new JDBCReviewDAOImpl();
		rDao.setConnection(conn);
		long id = rDao.add(review);
		if (id == -1) {
			logger.info("Error adding review");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}

		// Como se ha añadido correctamente, es necesario actualizar el grades de la
		// property
		PropertyDAO pDao = new JDBCPropertyDAOImpl();
		pDao.setConnection(conn);
		Property property = pDao.get(review.getIdp());
		if (property == null) {
			logger.info("Property not found");
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		List<Review> reviews = rDao.getAllByProperty(review.getIdp());
		double grade = 0;
		for (Review r : reviews) {
			grade += r.getGrade();
		}
		grade /= reviews.size();
		property.setGradesAverage(grade);

		if (!pDao.update(property)) {
			logger.info("Error updating property grades");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}

		return Response.ok().build();
	}

	@DELETE
	@Path("/{reviewid: [0-9]+}")
	public Response deleteReview(@PathParam("reviewid") long reviewid, @Context HttpServletRequest request) {
		logger.info("deleteReview");
		Connection conn = (Connection) sc.getAttribute("dbConn");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			logger.info("No user logged in");
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
		ReviewDAO rDao = new JDBCReviewDAOImpl();
		rDao.setConnection(conn);
		Review review = rDao.get(reviewid);
		if (review == null) {
			logger.info("Review not found");
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		if (user.getId() != review.getIdu()) {
			logger.info("User not allowed to delete review");
			return Response.status(Response.Status.FORBIDDEN).build();
		}
		if (!rDao.delete(reviewid)) {
			logger.info("Error deleting review");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}

		// Como se ha eliminado correctamente, es necesario actualizar el grades de la
		// property
		PropertyDAO pDao = new JDBCPropertyDAOImpl();
		pDao.setConnection(conn);
		Property property = pDao.get(review.getIdp());
		if (property == null) {
			logger.info("Property not found");
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		List<Review> reviews = rDao.getAllByProperty(review.getIdp());
		double grade = 0;
		for (Review r : reviews) {
			System.out.println("Review: " + r.toString());
			grade += r.getGrade();
		}
		System.out.println("Grade1: " + grade);
		if (reviews.size() == 0) {
			grade = 0;
		} else {
			grade = grade / reviews.size();
		}

		property.setGradesAverage(grade);
		pDao.update(property);

		return Response.ok().build();
	}
}
