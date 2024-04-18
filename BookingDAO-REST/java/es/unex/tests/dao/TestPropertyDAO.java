package es.unex.tests.dao;
import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import es.unex.pi.dao.PropertyDAO;
import es.unex.pi.dao.JDBCPropertyDAOImpl;
import es.unex.pi.model.Property;

import org.junit.Test;



@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestPropertyDAO {

	static DBConn dbConn;
	static PropertyDAO propertyDAO;
	static Connection conn;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dbConn = new DBConn();
		conn = dbConn.create();
	    propertyDAO = new JDBCPropertyDAOImpl();
		propertyDAO.setConnection(conn);
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
		
		Property property02 = propertyDAO.get(1);
		assertEquals(property02.getId(),1);
		assertEquals(property02.getName(),"NH Oquendo");
		
		Property property03 = propertyDAO.get(2);
		assertEquals(property03.getId(),2);
		assertEquals(property03.getName(),"Atrio Resort");
		
	}
	
	
	@Test
	public void test2Add(){
		Property property01 = new Property();
		property01.setName("newProperty");
		property01.setTelephone("777777777");
		property01.setIdu(1);
		property01.setGradesAverage(5);
		property01.setCity("Mérida");
		property01.setAvailable(1);
		property01.setPetFriendly(1);
		
		propertyDAO.add(property01);
		
		Property property02 = propertyDAO.getAllBySearchName("newProperty").iterator().next();
		assertEquals(property01.getTelephone(),property02.getTelephone());
	}
	
	@Test
	public void test3Modify(){
		Property property01 = propertyDAO.getAllBySearchName("newProperty").iterator().next();
		property01.setName("newPropertyUpdated");
		propertyDAO.update(property01);
		
		Property property02 = propertyDAO.getAllBySearchName("newPropertyUpdated").iterator().next();
		assertEquals(property01.getTelephone(),property02.getTelephone());
		
		propertyDAO.getAll();
	}
	
	@Test
	public void test4Delete(){
		
		 Property property01 = propertyDAO.getAllBySearchName("newPropertyUpdated").iterator().next();
		 long idProperty= property01.getId();
		 propertyDAO.delete(idProperty);
		 
		 Property property02 = propertyDAO.get(idProperty);
		 assertEquals(null,property02);
		 
		propertyDAO.getAll();
	}

}
