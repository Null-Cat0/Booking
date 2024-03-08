package es.unex.pi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.model.Accommodation;


public class JDBCAccommodationDAOImpl implements AccommodationDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCAccommodationDAOImpl.class.getName());
	
	@Override
	public Accommodation get(long id) {
		if (conn == null) return null;
		
		Accommodation accommodation = null;		
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM accommodations WHERE id ="+id);			 
			if (!rs.next()) return null; 
			accommodation  = new Accommodation();	 
			fromRsToAccommodationObject(rs,accommodation);
			logger.info("fetching Accommodation by id: "+id+" -> "+accommodation.getId()+" "+accommodation.getName()+" "+accommodation.getDescription());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accommodation;
	}
	
	
	@Override
	public Accommodation get(String name) {
		if (conn == null) return null;
		
		Accommodation accommodation = null;		
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM accommodations WHERE name = '"+name+"'");			 
			if (!rs.next()) return null; 
			accommodation  = new Accommodation();	 
			fromRsToAccommodationObject(rs,accommodation);
			logger.info("fetching Accommodation by name: "+accommodation.getId()+" "+accommodation.getName()+" "+accommodation.getDescription());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accommodation;
	}
	
	
	
	public List<Accommodation> getAll() {

		if (conn == null) return null;
		
		ArrayList<Accommodation> accommodations = new ArrayList<Accommodation>();
		try {
			Statement stmt;
			ResultSet rs;
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM accommodations");
			while ( rs.next() ) {
				Accommodation accommodation = new Accommodation();
				fromRsToAccommodationObject(rs,accommodation);				
				accommodations.add(accommodation);
				logger.info("fetching Accommodations: "+accommodation.getId()+" "+accommodation.getName()+" "+accommodation.getDescription());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accommodations;
	}
	
	public List<Accommodation> getAllBySearchName(String search) {
		search = search.toUpperCase();
		if (conn == null)
			return null;

		ArrayList<Accommodation> accommodations = new ArrayList<Accommodation>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM accommodations WHERE UPPER(name) LIKE '%" + search + "%'");

			while (rs.next()) {
				Accommodation accommodation = new Accommodation();
				fromRsToAccommodationObject(rs,accommodation);
				accommodations.add(accommodation);
				logger.info("fetching accommodations by text in the name: "+search+": "+accommodation.getId()+" "+accommodation.getName()+" "+accommodation.getDescription());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accommodations;
	}
	

	@Override
	public long add(Accommodation accommodation) {
		long id=-1;
		long lastid=-1;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM sqlite_sequence WHERE name ='accommodations'");			 
				if (!rs.next()) 
					return -1; 
				lastid=rs.getInt("seq");
								
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO accommodations (name,price,description,idp,numAccommodations) VALUES('"
									+accommodation.getName()+"', " + accommodation.getPrice() +", '" + accommodation.getDescription() +"', " + accommodation.getIdp()+", " + accommodation.getNumAccommodations()+ ")");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM sqlite_sequence WHERE name ='accommodations'");			 
				if (!rs.next()) 
					return -1; 
				id=rs.getInt("seq");
				if (id<=lastid) 
					return -1;
				logger.info("CREATING Accommodation("+id+"): "+accommodation.getName());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return id;
	}

	@Override
	public boolean update(Accommodation accommodation) {
		boolean done = false;
		if (conn != null){
			
			Statement stmt;
			try {
				stmt = conn.createStatement();
				
				stmt.executeUpdate("UPDATE accommodations SET name='"+accommodation.getName()
				+"', price = " + accommodation.getPrice() +", description = '" + accommodation.getDescription() +"', idp= " + accommodation.getIdp()+", numAccommodations= " + accommodation.getNumAccommodations()+  " WHERE id = "+accommodation.getId());
				
				logger.info("updating Accommodation: "+accommodation.getId()+" "+accommodation.getName()+" "+accommodation.getDescription());
				
				done= true;
			} catch (SQLException e) {
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
				stmt.executeUpdate("DELETE FROM accommodations WHERE id ="+id);
				
				logger.info("deleting Accommodation: "+id);
				
				done= true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return done;
	}

	public void fromRsToAccommodationObject(ResultSet rs, Accommodation accommodation) throws SQLException{
		accommodation.setId(rs.getInt("id"));
		accommodation.setName(rs.getString("name"));
		accommodation.setPrice(rs.getInt("price"));
		accommodation.setDescription(rs.getString("description"));
		accommodation.setIdp(rs.getInt("idp"));
		accommodation.setNumAccommodations(rs.getInt("numAccommodations"));
	}
	
	@Override
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	
}
