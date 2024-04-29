package es.unex.pi.dao;

import java.sql.Connection;
import java.util.List;

import es.unex.pi.model.Service;

public interface ServicesDAO {
	/**
	 * set the database connection in this DAO.
	 * 
	 * @param conn
	 *            database connection.
	 */
	public void setConnection(Connection conn);
	
	/**
	 * Gets a category from the DB using id.
	 * 
	 * @param id
	 *            Service Identifier.
	 * 
	 * @return Service object with that id.
	 */
	public Service get(long id);

	/**
	 * Gets a category from the DB using name.
	 * 
	 * @param name
	 *            Service name.
	 * 
	 * @return Service object with that name.
	 */
	public Service get(String name);

	public List<Service> getAllNotInProperty(long idp);
	public List<Service> getAllInProperty(long idp);
	
	
	/**
	 * Gets all the Service from the database.
	 * 
	 * @return List of all the Services from the database.
	 */
	public List<Service> getAll();
	
	/**
	 * Adds a category to the database.
	 * 
	 * @param category
	 *            Service object with the category details.
	 * 
	 * @return Service identifier or -1 in case the operation failed.
	 */
	
	public long add(Service category);

	/**
	 * Updates an existing category in the database.
	 * 
	 * @param category
	 *            Service object with the new details of the existing Service.
	 * 
	 * @return True if the operation was made and False if the operation failed.
	 */
	
	
	public boolean update(Service category);

	/**
	 * Deletes a Service from the database.
	 * 
	 * @param id
	 *            Service identifier.
	 * 
	 * @return True if the operation was made and False if the operation failed.
	 */
	
	public boolean delete(long id);
}
