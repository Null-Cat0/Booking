package es.unex.pi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.model.BookingsAccommodations;

public class JDBCBookingsAccommodationsDAOImpl implements BookingsAccommodationsDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCBookingsAccommodationsDAOImpl.class.getName());

	@Override
	public List<BookingsAccommodations> getAll() {

		if (conn == null) return null;
						
		ArrayList<BookingsAccommodations> BookingsAccommodationsList = new ArrayList<BookingsAccommodations>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM BookingsAccommodations");
						
			while ( rs.next() ) {
				BookingsAccommodations bookingsAccommodations = new BookingsAccommodations();
				fromRsToBookingsAccommodationsObject(rs,bookingsAccommodations);		
				BookingsAccommodationsList.add(bookingsAccommodations);
				logger.info("fetching all BookingsAccommodations: "+bookingsAccommodations.getIdb()+" "+bookingsAccommodations.getIdacc());
					
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return BookingsAccommodationsList;
	}

	@Override
	public List<BookingsAccommodations> getAllByAccommodation(long idacc) {
		
		if (conn == null) return null;
						
		ArrayList<BookingsAccommodations> BookingsAccommodationsList = new ArrayList<BookingsAccommodations>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM BookingsAccommodations WHERE idacc="+idacc);

			while ( rs.next() ) {
				BookingsAccommodations bookingsAccommodations = new BookingsAccommodations();
				fromRsToBookingsAccommodationsObject(rs,bookingsAccommodations);		
				BookingsAccommodationsList.add(bookingsAccommodations);
				logger.info("fetching all BookingsAccommodations by idb: "+bookingsAccommodations.getIdb()+"->"+bookingsAccommodations.getIdacc());
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return BookingsAccommodationsList;
	}
	
	@Override
	public List<BookingsAccommodations> getAllByBooking(long idb) {
		
		if (conn == null) return null;
						
		ArrayList<BookingsAccommodations> BookingsAccommodationsList = new ArrayList<BookingsAccommodations>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM BookingsAccommodations WHERE idb="+idb);

			while ( rs.next() ) {
				BookingsAccommodations bookingsAccommodations = new BookingsAccommodations();
				fromRsToBookingsAccommodationsObject(rs,bookingsAccommodations);		
				BookingsAccommodationsList.add(bookingsAccommodations);
				logger.info("fetching all BookingsAccommodations by idacc: "+bookingsAccommodations.getIdacc()+"-> "+bookingsAccommodations.getIdb());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return BookingsAccommodationsList;
	}
	
	
	@Override
	public BookingsAccommodations get(long idb,long idacc) {
		if (conn == null) return null;
		
		BookingsAccommodations bookingsAccommodations = null;		
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM BookingsAccommodations WHERE idb="+idb+" AND idacc="+idacc);			 
			if (!rs.next()) return null;
			bookingsAccommodations= new BookingsAccommodations();
			fromRsToBookingsAccommodationsObject(rs,bookingsAccommodations);		
			logger.info("fetching BookingsAccommodations by idb: "+bookingsAccommodations.getIdb()+"  and idacc: "+bookingsAccommodations.getIdacc());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return bookingsAccommodations;
	}
	
	

	@Override
	public boolean add(BookingsAccommodations bookingsAccommodations) {
		boolean done = false;
		if (conn != null){
			
			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO BookingsAccommodations (idb,idacc,numAccommodations) VALUES("+
									bookingsAccommodations.getIdb()+","+
									bookingsAccommodations.getIdacc()+","+
									bookingsAccommodations.getNumAccommodations()+")");
						
				logger.info("creating BookingsAccommodations:("+bookingsAccommodations.getIdb()+" "+bookingsAccommodations.getIdacc());
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public boolean update(BookingsAccommodations dbBookingsAccommodations, BookingsAccommodations newBookingsAccommodations) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				
				stmt.executeUpdate("UPDATE bookingsAccommodations SET idb = " + newBookingsAccommodations.getIdb() + ", idacc= "+newBookingsAccommodations.getIdacc() + ", numAccommodations= "+newBookingsAccommodations.getNumAccommodations()
				+" WHERE idb = "+dbBookingsAccommodations.getIdb() + " AND idacc = " + dbBookingsAccommodations.getIdacc());
				
				logger.info("updating BookingsAccommodations:("+dbBookingsAccommodations.getIdb()+" "+dbBookingsAccommodations.getIdacc());
				
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public boolean delete(long idb, long idacc) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM BookingsAccommodations WHERE idb ="+idb+" AND idacc="+idacc);
				logger.info("deleting BookingsAccommodations: "+idb+" , idacc="+idacc);
				done= true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return done;
	}

	public void fromRsToBookingsAccommodationsObject(ResultSet rs,BookingsAccommodations bookingsAccommodations) throws SQLException {
		bookingsAccommodations.setIdb(rs.getInt("idb"));
		bookingsAccommodations.setIdacc(rs.getInt("idacc"));
		bookingsAccommodations.setNumAccommodations(rs.getInt("numAccommodations"));
	}
	
	@Override
	public void setConnection(Connection conn) {
		// TODO Auto-generated method stub
		this.conn = conn;
	}
	
}
