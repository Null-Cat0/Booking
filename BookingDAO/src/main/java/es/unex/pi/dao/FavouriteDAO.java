package es.unex.pi.dao;

import java.sql.Connection;
import java.util.List;

import es.unex.pi.model.Favourite;

public interface FavouriteDAO {

	/**
	 * set the database connection in this DAO.
	 * 
	 * @param conn
	 *            database connection.
	 */
	public void setConnection(Connection conn);

	/**
	 * Gets all the user's favorite properties.
	 * 
	 * @return List of all the user's favorite properties from the database.
	 */
	
	public List<Favourite> getAll();

	/**
	 *Gets all the Review that are related to a user.
	 * 
	 * @param idu
	 *            User identifier
	 * 
	 * @return List of all the user's favorite properties.
	 */
	public List<Favourite> getAllByUser(long idu);
	
	/**
	 * Adds an favourite to the database.
	 * 
	 * @param Favourite
	 *            Favourite object with the details of the relation between the property and the user.
	 * 
	 * @return property identifier or -1 in case the operation failed.
	 */
	
	public boolean add(Favourite Favourite);
	/**
	 * Remove an Review from the database.
	 * 
	 * @param Favourite
	 *            Favourite object with the details of the relation between the property and the user.
	 * 
	 * @return property identifier or -1 in case the operation failed.
	 */
	
	public boolean delete(Favourite Favourite);
}
