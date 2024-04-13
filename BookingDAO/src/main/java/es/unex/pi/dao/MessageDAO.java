package es.unex.pi.dao;

import java.sql.Connection;
import java.util.List;

import es.unex.pi.model.Message;

public interface MessageDAO {
	/**
	 * set the database connection in this DAO.
	 * 
	 * @param conn
	 *            database connection.
	 */
	public void setConnection(Connection conn);
	/**
	 * Gets all the messages from the database.
	 */
	public List<Message> getAll();
	/**
	 * Insert a message into the database.
	 * @param message Message to be inserted.
	 * @return The id of the message inserted.
	 */
	
	public long add(Message message);
	/**
	 * Gets a message from the database.
	 * @param id Id of the message to be retrieved.
	 * @return The message with the id given.
     */
	public List<Message> getByR(long id);
	/**
	 * Gets a message from the database.
	 * @param id Id of the user .
	 * 
	 */
	public List<Message> getBySender(long id);
	
}
