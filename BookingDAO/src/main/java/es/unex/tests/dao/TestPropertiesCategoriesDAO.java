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

import es.unex.pi.dao.PropertiesCategoriesDAO;
import es.unex.pi.dao.JDBCPropertiesCategoriesDAOImpl;
import es.unex.pi.model.PropertiesCategories;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestPropertiesCategoriesDAO {

	static DBConn dbConn;
	static PropertiesCategoriesDAO PropertiesCategoriesDAO;
	static Connection conn;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dbConn = new DBConn();
		conn = dbConn.create();
	    PropertiesCategoriesDAO = new JDBCPropertiesCategoriesDAOImpl();
		PropertiesCategoriesDAO.setConnection(conn);
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
		
		List<PropertiesCategories> PropertiesCategoriesList = PropertiesCategoriesDAO.getAll();
		
		PropertiesCategories PropertiesCategories = PropertiesCategoriesDAO.get(1,1);
		
		assertEquals(PropertiesCategories.getIdp(),1);
		assertEquals(PropertiesCategories.getIdct(),1);
		
		assertEquals(PropertiesCategoriesList.get(0).getIdp(),PropertiesCategories.getIdp());			
			
	}
	
	@Test
	public void test2BaseDataByCategory() {
		
		List<PropertiesCategories> PropertiesCategoriesList = PropertiesCategoriesDAO.getAllByCategory(1);
		for(PropertiesCategories PropertiesCategories: PropertiesCategoriesList)			
			assertEquals(PropertiesCategories.getIdct(),1);			
	}
	
	@Test
	public void test3BaseDataByProperty() {
		
		List<PropertiesCategories> PropertiesCategoriesList = PropertiesCategoriesDAO.getAllByProperty(1);
		for(PropertiesCategories PropertiesCategories: PropertiesCategoriesList)			
			assertEquals(PropertiesCategories.getIdp(),1);			
	}
	
	@Test
	public void test4Add(){
		PropertiesCategories propertiesCategories01 = new PropertiesCategories();
		propertiesCategories01.setIdp(2);
		propertiesCategories01.setIdct(3);
		PropertiesCategoriesDAO.add(propertiesCategories01);
		
		PropertiesCategories propertiesCategories02 = PropertiesCategoriesDAO.get(2,3);
		
		assertEquals(2,propertiesCategories02.getIdp());
		assertEquals(3,propertiesCategories02.getIdct());
				
	}
	
	
	@Test
	public void test5Modify(){
		
		PropertiesCategories propertiesCategories01 = PropertiesCategoriesDAO.get(2,3);
		PropertiesCategories propertiesCategories02 = PropertiesCategoriesDAO.get(2,3);
		propertiesCategories02.setIdct(1);
		PropertiesCategoriesDAO.update(propertiesCategories01,propertiesCategories02);
		
		PropertiesCategories propertiesCategories03 = PropertiesCategoriesDAO.get(2,1);
		assertEquals(2,propertiesCategories03.getIdp());
		assertEquals(1,propertiesCategories03.getIdct());
	}
	
	@Test
	public void test6Delete(){
		 
		PropertiesCategoriesDAO.delete(2,1);
		List<PropertiesCategories> PropertiesCategoriesList = PropertiesCategoriesDAO.getAll();
		 
		 PropertiesCategories PropertiesCategories01 = new PropertiesCategories();
		 PropertiesCategories01.setIdp(2);
		 PropertiesCategories01.setIdct(1);
		 		 
		for(PropertiesCategories PropertiesCategories: PropertiesCategoriesList) {
				assertNotEquals(PropertiesCategories,PropertiesCategories01);
		}
		 
	}

}
