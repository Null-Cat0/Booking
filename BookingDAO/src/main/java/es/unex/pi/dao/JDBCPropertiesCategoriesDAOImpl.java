package es.unex.pi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.model.PropertiesCategories;

public class JDBCPropertiesCategoriesDAOImpl implements PropertiesCategoriesDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCPropertiesCategoriesDAOImpl.class.getName());

	@Override
	public List<PropertiesCategories> getAll() {

		if (conn == null) return null;
						
		ArrayList<PropertiesCategories> propertiesCategoriesList = new ArrayList<PropertiesCategories>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM PropertiesCategories");
						
			while ( rs.next() ) {
				PropertiesCategories propertiesCategories = new PropertiesCategories();
				fromRsToPropertiesCategoriesObject(rs,propertiesCategories);
				propertiesCategoriesList.add(propertiesCategories);
				logger.info("fetching all PropertiesCategories: "+propertiesCategories.getIdp()+" "+propertiesCategories.getIdct());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return propertiesCategoriesList;
	}

	@Override
	public List<PropertiesCategories> getAllByCategory(long idct) {
		
		if (conn == null) return null;
						
		ArrayList<PropertiesCategories> propertiesCategoriesList = new ArrayList<PropertiesCategories>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM PropertiesCategories WHERE idct="+idct);

			while ( rs.next() ) {
				PropertiesCategories propertiesCategories = new PropertiesCategories();
				fromRsToPropertiesCategoriesObject(rs,propertiesCategories);
				propertiesCategoriesList.add(propertiesCategories);
				logger.info("fetching all PropertiesCategories by idp: "+propertiesCategories.getIdp()+"->"+propertiesCategories.getIdct());
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return propertiesCategoriesList;
	}
	
	@Override
	public List<PropertiesCategories> getAllByProperty(long idp) {
		
		if (conn == null) return null;
						
		ArrayList<PropertiesCategories> propertiesCategoriesList = new ArrayList<PropertiesCategories>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM PropertiesCategories WHERE idp="+idp);

			while ( rs.next() ) {
				PropertiesCategories propertiesCategories = new PropertiesCategories();
				fromRsToPropertiesCategoriesObject(rs,propertiesCategories);
				propertiesCategoriesList.add(propertiesCategories);
				logger.info("fetching all PropertiesCategories by idct: "+propertiesCategories.getIdct()+"-> "+propertiesCategories.getIdp());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return propertiesCategoriesList;
	}
	
	
	@Override
	public PropertiesCategories get(long idp,long idct) {
		if (conn == null) return null;
		
		PropertiesCategories propertiesCategories = null;		
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM PropertiesCategories WHERE idp="+idp+" AND idct="+idct);			 
			if (!rs.next()) return null;
			propertiesCategories= new PropertiesCategories();
			fromRsToPropertiesCategoriesObject(rs,propertiesCategories);
			logger.info("fetching PropertiesCategories by idp: "+propertiesCategories.getIdp()+"  and idct: "+propertiesCategories.getIdct());
		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return propertiesCategories;
	}

	@Override
	public boolean add(PropertiesCategories propertiesCategories) {
		boolean done = false;
		if (conn != null){
			
			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO PropertiesCategories (idp,idct) VALUES('"+
									propertiesCategories.getIdp()+"','"+
									propertiesCategories.getIdct()+"')");
						
				logger.info("creating PropertiesCategories:("+propertiesCategories.getIdp()+" "+propertiesCategories.getIdct());
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public boolean update(PropertiesCategories dbObject, PropertiesCategories newObject) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				
				stmt.executeUpdate("UPDATE PropertiesCategories SET idct="+newObject.getIdct()
				+" WHERE idp = "+dbObject.getIdp() + " AND idct = " + dbObject.getIdct());
				
				logger.info("updating PropertiesCategories:("+dbObject.getIdp()+" "+dbObject.getIdct());
				
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public boolean delete(long idp, long idct) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM PropertiesCategories WHERE idp ="+idp+" AND idct="+idct);
				logger.info("deleting PropertiesCategories: "+idp+" , idct="+idct);
				done= true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return done;
	}

	public void fromRsToPropertiesCategoriesObject(ResultSet rs, PropertiesCategories propertiesCategories) throws SQLException {
		propertiesCategories.setIdp(rs.getInt("idp"));
		propertiesCategories.setIdct(rs.getInt("idct"));
				

	}
	
	@Override
	public void setConnection(Connection conn) {
		// TODO Auto-generated method stub
		this.conn = conn;
	}
	
}
