package es.unex.tests.dao;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.sql.Connection;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import es.unex.pi.dao.BookingsAccommodationsDAO;
import es.unex.pi.dao.JDBCBookingsAccommodationsDAOImpl;
import es.unex.pi.model.BookingsAccommodations;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestBookingsAccommodationsDAO {

	static DBConn dbConn;
	static BookingsAccommodationsDAO BookingsAccommodationsDAO;
	static Connection conn;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dbConn = new DBConn();
		conn = dbConn.create();
	    BookingsAccommodationsDAO = new JDBCBookingsAccommodationsDAOImpl();
		BookingsAccommodationsDAO.setConnection(conn);
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
		
		List<BookingsAccommodations> BookingsAccommodationsList = BookingsAccommodationsDAO.getAll();
		
		BookingsAccommodations BookingsAccommodations = BookingsAccommodationsDAO.get(1,1);
		
		assertEquals(BookingsAccommodations.getIdb(),1);
		assertEquals(BookingsAccommodations.getIdacc(),1);
		
		assertEquals(BookingsAccommodationsList.get(0).getIdb(),BookingsAccommodations.getIdb());			
			
	}
	
	@Test
	public void test2BaseDataByAccommodation() {
		
		List<BookingsAccommodations> BookingsAccommodationsList = BookingsAccommodationsDAO.getAllByAccommodation(2);
		for(BookingsAccommodations BookingsAccommodations: BookingsAccommodationsList)			
			assertEquals(BookingsAccommodations.getIdacc(),2);			
	}
	
	@Test
	public void test3BaseDataByBooking() {
		
		List<BookingsAccommodations> BookingsAccommodationsList = BookingsAccommodationsDAO.getAllByBooking(2);
		for(BookingsAccommodations BookingsAccommodations: BookingsAccommodationsList)			
			assertEquals(BookingsAccommodations.getIdb(),2);			
	}
	
	@Test
	public void test4Add(){
		BookingsAccommodations bookingsAccommodations01 = new BookingsAccommodations();
		bookingsAccommodations01.setIdb(1);
		bookingsAccommodations01.setIdacc(2);
		BookingsAccommodationsDAO.add(bookingsAccommodations01);
		
		BookingsAccommodations bookingsAccommodations02 = BookingsAccommodationsDAO.get(1,2);
		
		assertEquals(1,bookingsAccommodations02.getIdb());
		assertEquals(2,bookingsAccommodations02.getIdacc());
				
	}
	
	
	@Test
	public void test5Modify(){
		
		BookingsAccommodations bookingsAccommodations01 = BookingsAccommodationsDAO.get(1,2);
		BookingsAccommodations bookingsAccommodations02 = BookingsAccommodationsDAO.get(1,2);
		bookingsAccommodations02.setIdacc(3);
		BookingsAccommodationsDAO.update(bookingsAccommodations01,bookingsAccommodations02);
		
		BookingsAccommodations bookingsAccommodations03 = BookingsAccommodationsDAO.get(1,3);
		assertEquals(1,bookingsAccommodations03.getIdb());
		assertEquals(3,bookingsAccommodations03.getIdacc());
	}
	
	@Test
	public void test6Delete(){
		 
		BookingsAccommodationsDAO.delete(1,3);
		List<BookingsAccommodations> BookingsAccommodationsList = BookingsAccommodationsDAO.getAll();
		 
		 BookingsAccommodations BookingsAccommodations01 = new BookingsAccommodations();
		 BookingsAccommodations01.setIdb(1);
		 BookingsAccommodations01.setIdacc(3);
		 		 
		for(BookingsAccommodations BookingsAccommodations: BookingsAccommodationsList) {
				assertNotEquals(BookingsAccommodations,BookingsAccommodations01);
		}
		 
	}

}
