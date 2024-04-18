package es.unex.pi.dao;

import java.sql.Connection;
import java.util.List;

import es.unex.pi.model.Review;


public interface ReviewDAO {

	/**
	 * set the database connection in this DAO.
	 * 
	 * @param conn
	 *            database connection.
	 */
	public void setConnection(Connection conn);

	/**
	 * Gets all the property and the categories related to them from the database.
	 * 
	 * @return List of all the property and the categories related to them from the database.
	 */
	
	public List<Review> getAll();

	/**
	 *Gets all the Review that are related to a user.
	 * 
	 * @param idu
	 *            User identifier
	 * 
	 * @return List of all the Review related to a user.
	 */
	public List<Review> getAllByUser(long idu);
	
	/**
	 * Gets all the Review that contains an specific property.
	 * 
	 * @param idr
	 *            Property Identifier
	 * 
	 * @return List of all the Review that contains an specific property
	 */
	public List<Review> getAllByProperty(long idr);

	/**
	 * Gets a Review from the DB using idr and idu.
	 * 
	 * @param idr 
	 *            property identifier.
	 *            
	 * @param idu
	 *            User Identifier
	 * 
	 * @return Review with that idr and idu.
	 */
	
	public Review get(long idr,long idu);

	/**
	 * Adds an Review to the database.
	 * 
	 * @param Review
	 *            Review object with the details of the relation between the property and the user.
	 * 
	 * @return property identifier or -1 in case the operation failed.
	 */
	
	public boolean add(Review Review);

	/**
	 * Updates an existing Review in the database.
	 * 
	 * @param Review
	 *            Review object with the new details of the existing relation between the property and the user. 
	 * 
	 * @return True if the operation was made and False if the operation failed.
	 */
	
	public boolean update(Review Review);

	/**
	 * Deletes an Review from the database.
	 * 
	 * @param idr
	 *            Property identifier.
	 *            
	 * @param idu
	 *            User Identifier
	 * 
	 * @return True if the operation was made and False if the operation failed.
	 */
	
	public boolean delete(long idr, long idu);
}