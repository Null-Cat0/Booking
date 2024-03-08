package es.unex.pi.dao;

import java.sql.Connection;
import java.util.List;

import es.unex.pi.model.BookingsAccommodations;


public interface BookingsAccommodationsDAO {

	/**
	 * set the database connection in this DAO.
	 * 
	 * @param conn
	 *            database connection.
	 */
	public void setConnection(Connection conn);

	/**
	 * Gets all the booking and the accommodations related to them from the database.
	 * 
	 * @return List of all the booking and the accommodations related to them from the database.
	 */
	
	public List<BookingsAccommodations> getAll();

	/**
	 *Gets all the BookingAccommodation that are related to a accommodation.
	 * 
	 * @param idacc
	 *            Accommodation identifier
	 * 
	 * @return List of all the BookingAccommodation related to a accommodation.
	 */
	public List<BookingsAccommodations> getAllByAccommodation(long idacc);
	
	/**
	 * Gets all the BookingAccommodation that contains an specific booking.
	 * 
	 * @param idb
	 *            Booking Identifier
	 * 
	 * @return List of all the BookingAccommodation that contains an specific booking
	 */
	public List<BookingsAccommodations> getAllByBooking(long idb);

	/**
	 * Gets a BookingAccommodation from the DB using idb and idacc.
	 * 
	 * @param idb 
	 *            booking identifier.
	 *            
	 * @param idacc
	 *            Accommodation Identifier
	 * 
	 * @return BookingAccommodation with that idb and idacc.
	 */
	
	public BookingsAccommodations get(long idb,long idacc);

	/**
	 * Adds an BookingAccommodation to the database.
	 * 
	 * @param BookingAccommodation
	 *            BookingAccommodation object with the details of the relation between the booking and the accommodation.
	 * 
	 * @return booking identifier or -1 in case the operation failed.
	 */
	
	public boolean add(BookingsAccommodations BookingAccommodation);

	/**
	 * Updates an existing BookingAccommodation in the database.
	 * 
	 * @param dbBookingAccommodation
	 *            BookingAccommodation object existing in the database 
	 *
	 * @param newBookingAccommodation
	 *            BookingAccommodation object with the new details of the existing relation between the booking and the accommodation. 
	 * 
	 * @return True if the operation was made and False if the operation failed.
	 */
	
	public boolean update(BookingsAccommodations dbBookingsAccommodations, BookingsAccommodations newBookingsAccommodations);

	/**
	 * Deletes an BookingAccommodation from the database.
	 * 
	 * @param idb
	 *            Booking identifier.
	 *            
	 * @param idacc
	 *            Accommodation Identifier
	 * 
	 * @return True if the operation was made and False if the operation failed.
	 */
	
	public boolean delete(long idb, long idacc);
}