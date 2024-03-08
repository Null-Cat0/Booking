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

import es.unex.pi.dao.ReviewDAO;
import es.unex.pi.dao.JDBCReviewDAOImpl;
import es.unex.pi.model.Review;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestReviewDAO {

	static DBConn dbConn;
	static ReviewDAO reviewsDAO;
	static Connection conn;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dbConn = new DBConn();
		conn = dbConn.create();
	    reviewsDAO = new JDBCReviewDAOImpl();
		reviewsDAO.setConnection(conn);
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
		
		List<Review> reviewsList = reviewsDAO.getAll();
		
		Review review = reviewsDAO.get(1,1);
		
		assertEquals(review.getIdp(),1);
		assertEquals(review.getIdu(),1);
		
		assertEquals(reviewsList.get(0).getIdp(),review.getIdp());			
			
	}
	
	@Test
	public void test2BaseDataByUser() {
		
		List<Review> reviewsList = reviewsDAO.getAllByUser(1);
		for(Review reviews: reviewsList)			
			assertEquals(reviews.getIdu(),1);			
	}
	
	@Test
	public void test3BaseDataByProperty() {
		
		List<Review> reviewsList = reviewsDAO.getAllByProperty(1);
		for(Review reviews: reviewsList)			
			assertEquals(reviews.getIdp(),1);			
	}
	
	@Test
	public void test4Add(){
		Review review01 = new Review();
		review01.setIdp(2);
		review01.setIdu(2);
		review01.setReview("NewReviewComment");
		review01.setGrade(4);
		
		reviewsDAO.add(review01);
		
		Review review02 = reviewsDAO.get(2,2);
		
		assertEquals(2,review02.getIdp());
		assertEquals(2,review02.getIdu());
				
	}
	
	
	@Test
	public void test5Modify(){
		
		Review review01 = reviewsDAO.get(2,2);
		String oldReview = review01.getReview();
		int oldGrade = review01.getGrade();
		
		review01.setReview("NewReview");
		review01.setGrade(5);
		reviewsDAO.update(review01);
		
		Review review02 = reviewsDAO.get(2,2);
		assertEquals("NewReview",review02.getReview());
		assertEquals(5,review02.getGrade());

		review01.setReview(oldReview);
		review01.setGrade(oldGrade);		
		reviewsDAO.update(review01);

		review02 = reviewsDAO.get(2,2);
		assertEquals(oldReview,review02.getReview());
		assertEquals(oldGrade,review02.getGrade());

	}
	
	@Test
	public void test6Delete(){
		 
		reviewsDAO.delete(2,2);
		List<Review> reviewsList = reviewsDAO.getAll();
		 
		 Review reviews01 = new Review();
		 reviews01.setIdp(2);
		 reviews01.setIdu(2);
		 		 
		for(Review reviews: reviewsList) {
				assertNotEquals(reviews,reviews01);
		}
		 
	}

}
