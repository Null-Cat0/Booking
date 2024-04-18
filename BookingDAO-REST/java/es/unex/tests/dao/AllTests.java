package es.unex.tests.dao;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ TestAccommodationDAO.class, TestBookingDAO.class, TestBookingsAccommodationsDAO.class,
		TestCategoryDAO.class, TestPropertiesCategoriesDAO.class, TestPropertyDAO.class, TestReviewDAO.class,
		TestUserDAO.class })
public class AllTests {

}
