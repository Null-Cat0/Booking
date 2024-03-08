package es.unex.pi.dao;

import java.sql.Connection;
import java.util.List;

import es.unex.pi.model.Accommodation;


public interface AccommodationDAO {

	/**
	 * set the database connection in this DAO.
	 * 
	 * @param conn
	 *            database connection.
	 */
	public void setConnection(Connection conn);
	
	/**
	 * Gets an accommodation from the DB using id.
	 * 
	 * @param id
	 *            Accommodation Identifier.
	 * 
	 * @return Accommodation object with that id.
	 */
	public Accommodation get(long id);

	/**
	 * Gets an accommodation from the DB using name.
	 * 
	 * @param name
	 *            Accommodation name.
	 * 
	 * @return Accommodation object with that name.
	 */
	public Accommodation get(String name);

	
	/**
	 * Gets all the accommodations from the database.
	 * 
	 * @return List of all the accommodations from the database.
	 */
	public List<Accommodation> getAll();
	
	/**
	 * Gets all the accommodations from the database that contain a text in the name.
	 * 
	 * @param search
	 *            Search string .
	 * 
	 * @return List of all the accommodations from the database that contain a text in the name.
	 */	
	public List<Accommodation> getAllBySearchName(String search);


	/**
	 * Adds a accommodation to the database.
	 * 
	 * @param accommodation
	 *            Accommodation object with the accommodation details.
	 * 
	 * @return Accommodation identifier or -1 in case the operation failed.
	 */
	
	public long add(Accommodation accommodation);

	/**
	 * Updates an existing accommodation in the database.
	 * 
	 * @param accommodation
	 *            Accommodation object with the new details of the existing accommodation.
	 * 
	 * @return True if the operation was made and False if the operation failed.
	 */
	
	
	public boolean update(Accommodation accommodation);

	/**
	 * Deletes a accommodation from the database.
	 * 
	 * @param id
	 *            Accommodation identifier.
	 * 
	 * @return True if the operation was made and False if the operation failed.
	 */
	
	public boolean delete(long id);
}
