package es.unex.pi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.model.Property;

public class JDBCPropertyDAOImpl implements PropertyDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCPropertyDAOImpl.class.getName());
	
	@Override
	public Property get(long id) {
		if (conn == null) return null;
		
		Property property = null;		
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM properties WHERE id ="+id);			 
			if (!rs.next()) return null; 
			property  = new Property();	 
			fromRsToPropertyObject(rs,property);
			logger.info("fetching property: "+property.getId());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return property;
	}
	
	public List<Property> getAll() {

		if (conn == null) return null;
		
		ArrayList<Property> properties = new ArrayList<Property>();
		try {
			Statement stmt;
			ResultSet rs;
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM properties");
			while ( rs.next() ) {
				Property property = new Property();
				property  = new Property();	 
				fromRsToPropertyObject(rs,property);
				properties.add(property);
				logger.info("fetching property: "+property.getId());							
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return properties;
	}
	
	public List<Property> getAllBySearchName(String search) {
		search = search.toUpperCase();
		if (conn == null)
			return null;

		ArrayList<Property> properties = new ArrayList<Property>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM properties WHERE UPPER(name) LIKE '%" + search + "%'");

			while (rs.next()) {
				Property property = new Property();
				fromRsToPropertyObject(rs,property);
				properties.add(property);
				
				logger.info("fetching property: "+property.getId());
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return properties;
	}
	

	
	public List<Property> getAllByUser(long idu) {
		
		if (conn == null)
			return null;

		ArrayList<Property> properties = new ArrayList<Property>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM properties WHERE idu = "+ idu);

			while (rs.next()) {
				Property property = new Property();
				fromRsToPropertyObject(rs,property);
				properties.add(property);
		
				logger.info("fetching properties: "+property.getId());
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return properties;
	}

	@Override
	public long add(Property property) {
		long id=-1;
		long lastid=-1;
		if (conn != null){
			logger.info("CREATING property " + property.getName());
			Statement stmt;
			try {
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM sqlite_sequence WHERE name ='properties'");			 
				if (!rs.next()) return -1; 
				lastid=rs.getInt("seq");
								
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO properties (name,address,telephone,idu,gradesAverage,city,centerDistance,description,petFriendly,available) VALUES('"
									+property.getName()+"','"+property.getAddress()+"','" + property.getTelephone() + "'," 
									+ property.getIdu() + "," + property.getGradesAverage()+",'"+ property.getCity() +"',"+ property.getCenterDistance() +",'" + property.getDescription() + "'," 
									+ property.getPetFriendly() +"," + property.getAvailable() + ")");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM sqlite_sequence WHERE name ='properties'");			 
				if (!rs.next()) return -1; 
				id=rs.getInt("seq");
				if (id<=lastid) return -1;
											
				logger.info("CREATING property("+id+"): "+property.getName());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return id;
	}

	@Override
	public boolean update(Property property) {
		boolean done = false;
		if (conn != null){
			
			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("UPDATE properties SET name='"+property.getName()
				+"', address='"+property.getAddress()
				+"', telephone='"+property.getTelephone()
				+"', idu="+property.getIdu()
				+", gradesAverage="+property.getGradesAverage()
				+", city='"+property.getCity()
				+"', centerDistance="+property.getCenterDistance()
				+", description='"+property.getDescription()				
				+"', petFriendly="+property.getPetFriendly()
				+", available="+property.getAvailable()
				+" WHERE id = "+property.getId());
				logger.info("updating property: "+property.getId());
						
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;

	}

	@Override
	public boolean delete(long id) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM properties WHERE id ="+id);
				logger.info("deleting property: "+id);
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}

	public void fromRsToPropertyObject(ResultSet rs,Property property) throws SQLException{
		property.setId(rs.getInt("id"));
		property.setName(rs.getString("name"));
		property.setAddress(rs.getString("address"));
		property.setTelephone(rs.getString("telephone"));
		property.setIdu(rs.getInt("idu"));
		property.setCity(rs.getString("city"));
		property.setGradesAverage(rs.getDouble("gradesAverage"));
		property.setCenterDistance(rs.getDouble("centerDistance"));
		property.setDescription(rs.getString("description"));
		property.setPetFriendly(rs.getInt("petFriendly"));
		property.setAvailable(rs.getInt("available"));
	}
	
	@Override
	public void setConnection(Connection conn) {
		// TODO Auto-generated method stub
		this.conn = conn;
	}

	
}
