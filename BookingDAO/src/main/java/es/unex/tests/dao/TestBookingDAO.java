package es.unex.tests.dao;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import es.unex.pi.model.Booking;
import es.unex.pi.dao.BookingDAO;
import es.unex.pi.dao.JDBCBookingDAOImpl;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestBookingDAO {

	static DBConn dbConn;
	static BookingDAO bookingDAO;
	static Connection conn;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dbConn = new DBConn();
		conn = dbConn.create();
	    bookingDAO = new JDBCBookingDAOImpl();
		bookingDAO.setConnection(conn);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
		dbConn.destroy(conn);
	    
	}

	@Before
	public void setUpBeforeMethod() throws Exception {
	
	}

	@Test
	public void test1BaseData() {
		
		Booking booking01 = bookingDAO.get(0);
		assertEquals(booking01.getId(),0);
		assertEquals(booking01.getTotalPrice(),100);
		
		Booking booking02 = bookingDAO.get(1);
		assertEquals(booking02.getId(),1);
		assertEquals(booking02.getTotalPrice(),200);

		Booking booking03 = bookingDAO.get(2);
		assertEquals(booking03.getId(),2);
		assertEquals(booking03.getTotalPrice(),700);

	}
	
	
	@Test
	public void test2Add(){
		Booking booking01 = new Booking();
		booking01.setTotalPrice(1000);
		booking01.setIdu(1);
		
		long value = bookingDAO.add(booking01);
		
		Booking booking02 = bookingDAO.get(booking01.getIdu(),booking01.getTotalPrice());
		assertEquals(booking01.getIdu(),booking02.getIdu());
		assertEquals(booking01.getTotalPrice(),booking02.getTotalPrice());
	}
	
	@Test
	public void test3Modify(){
		Booking booking01 = bookingDAO.get(1,1000);
		int previousPrice = booking01.getTotalPrice();
		
		booking01.setTotalPrice(2000);
		bookingDAO.update(booking01);
		
		Booking booking02 = bookingDAO.get(1,2000);		
		assertNotNull(booking02);

		booking01.setTotalPrice(previousPrice);
		bookingDAO.update(booking01);
		
		booking02 = bookingDAO.get(1,1000);		
		assertNotNull(booking02);
	
	}

	
	@Test
	public void test4Delete(){
		 Booking booking01 = bookingDAO.get(1,1000);
		 bookingDAO.delete(booking01.getId());
		 
		 Booking booking02 = bookingDAO.get(1,1000);
 		 assertEquals(null, booking02);
 		 
 		bookingDAO.getAll();
	}

}
