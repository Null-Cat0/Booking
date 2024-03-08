package es.unex.pi.dao;

import java.sql.Connection;
import java.util.List;

import es.unex.pi.model.Booking;


public interface BookingDAO {

	/**
	 * set the database connection in this DAO.
	 * 
	 * @param conn
	 *            database connection.
	 */
	public void setConnection(Connection conn);
	
	/**
	 * Gets a booking from the DB using id.
	 * 
	 * @param id
	 *            Booking Identifier.
	 * 
	 * @return Booking object with that id.
	 */
	public Booking get(long id);
	
	
	/**
	 * Gets a booking from the DB using idu and totalPrice.
	 * 
	 * @param idu
	 *            User Identifier.
	 * @param totalPrice
	 *            Booking total price.
	 * 
	 * @return Booking object with that id.
	 */
	public Booking get(long idu,int totalPrice);


	
	/**
	 * Gets all the bookinges from the database.
	 * 
	 * @return List of all the bookinges from the database.
	 */
	public List<Booking> getAll();
	

	/**
	 * Adds a booking to the database.
	 * 
	 * @param booking
	 *            Booking object with the booking details.
	 * 
	 * @return Booking identifier or -1 in case the operation failed.
	 */
	
	public long add(Booking booking);

	/**
	 * Updates an existing booking in the database.
	 * 
	 * @param booking
	 *            Booking object with the new details of the existing booking.
	 * 
	 * @return True if the operation was made and False if the operation failed.
	 */
	
	
	public boolean update(Booking booking);

	/**
	 * Deletes a booking from the database.
	 * 
	 * @param id
	 *            Booking identifier.
	 * 
	 * @return True if the operation was made and False if the operation failed.
	 */
	
	public boolean delete(long id);
}
