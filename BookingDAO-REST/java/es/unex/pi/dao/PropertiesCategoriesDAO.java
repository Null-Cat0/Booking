package es.unex.pi.dao;

import java.sql.Connection;
import java.util.List;

import es.unex.pi.model.PropertiesCategories;


public interface PropertiesCategoriesDAO {

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
	
	public List<PropertiesCategories> getAll();

	/**
	 *Gets all the PropertyCategory that are related to a category.
	 * 
	 * @param idct
	 *            Category identifier
	 * 
	 * @return List of all the PropertyCategory related to a category.
	 */
	public List<PropertiesCategories> getAllByCategory(long idct);
	
	/**
	 * Gets all the PropertyCategory that contains an specific property.
	 * 
	 * @param idr
	 *            Property Identifier
	 * 
	 * @return List of all the PropertyCategory that contains an specific property
	 */
	public List<PropertiesCategories> getAllByProperty(long idr);

	/**
	 * Gets a PropertyCategory from the DB using idr and idct.
	 * 
	 * @param idr 
	 *            property identifier.
	 *            
	 * @param idct
	 *            Category Identifier
	 * 
	 * @return PropertyCategory with that idr and idct.
	 */
	
	public PropertiesCategories get(long idr,long idct);

	/**
	 * Adds an PropertyCategory to the database.
	 * 
	 * @param PropertyCategory
	 *            PropertyCategory object with the details of the relation between the property and the category.
	 * 
	 * @return property identifier or -1 in case the operation failed.
	 */
	
	public boolean add(PropertiesCategories PropertyCategory);

	/**
	 * Updates an existing PropertyCategory in the database.
	 * 
	 * @param dbObject
	 *            PropertyCategory object that is going to be updated in the database 
	 * @param newObject
	 *            PropertyCategory object with the new details of the existing relation between the property and the category. 
	 * 
	 * @return True if the operation was made and False if the operation failed.
	 */
	
	public boolean update(PropertiesCategories dbObject, PropertiesCategories newObject);

	/**
	 * Deletes an PropertyCategory from the database.
	 * 
	 * @param idr
	 *            Property identifier.
	 *            
	 * @param idct
	 *            Category Identifier
	 * 
	 * @return True if the operation was made and False if the operation failed.
	 */
	
	public boolean delete(long idr, long idct);
}