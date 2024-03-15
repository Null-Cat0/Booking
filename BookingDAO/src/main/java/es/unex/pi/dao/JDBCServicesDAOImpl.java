package es.unex.pi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import es.unex.pi.model.*;

public class JDBCServicesDAOImpl implements ServicesDAO {
	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCCategoryDAOImpl.class.getName());
	
	@Override
	public Service get(long id) {
		if (conn == null) return null;
		Service service = null;		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM services WHERE id ="+id);			 
			if (!rs.next()) return null; 
			service  = new Service();	 
			fromRsToServiceObject(rs,service);
			logger.info("fetching Category by id: "+id+" -> "+service.getId()+" "+service.getName()+" ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return service;
	}
	
	@Override
	public Service get(String name) {
		if (conn == null) return null;
		
		Service service = null;		
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM services WHERE UPPER(name) LIKE '%" + name + "%'");			 
			if (!rs.next()) return null; 
			service  = new Service();	 
			fromRsToServiceObject(rs,service);			
			logger.info("fetching Category by name: "+service.getId()+" "+service.getName());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return service;
	}
	
	
	public List<Service> getAll() {

		if (conn == null) return null;
		
		ArrayList<Service> services = new ArrayList<Service>();
		try {
			Statement stmt;
			ResultSet rs;
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM services");
			while ( rs.next() ) {
				Service service = new Service();
				fromRsToServiceObject(rs,service);
				services.add(service);
				logger.info("fetching Categories: "+service.getId()+" "+service.getName());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return services;
	}
	
	@Override
	public long add(Service service) {
		long id=-1;
		long lastid=-1;
		if (conn != null){
			Statement stmt;
			try {
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM sqlite_sequence WHERE name ='services'");			 
				if (!rs.next()) return -1; 
				lastid=rs.getInt("seq");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO services (name) VALUES('"
									+service.getName()+"')");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM sqlite_sequence WHERE name ='services'");			 
				if (!rs.next()) return -1; 
				id=rs.getInt("seq");
				if (id<=lastid) return -1;
											
				logger.info("CREATING Category("+id+"): "+service.getName());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return id;
	}

	@Override
	public boolean update(Service service) {
		boolean done = false;
		if (conn != null){
			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("UPDATE services SET name='"+service.getName()
				+"'"+"' WHERE id = "+service.getId());
				logger.info("updating Category: "+service.getId()+" "+service.getName());
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
				stmt.executeUpdate("DELETE FROM services WHERE id ="+id);
				logger.info("deleting Category: "+id);
				done= true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return done;
	}

	public void fromRsToServiceObject(ResultSet rs,Service service) throws SQLException{
		service.setId(rs.getInt("id"));
		service.setName(rs.getString("name"));
	}
	
	@Override
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
}
