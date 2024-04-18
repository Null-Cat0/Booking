package es.unex.pi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.model.Category;
import es.unex.pi.model.Favourite;

public class JDBCFavouriteDAOImpl implements FavouriteDAO{
	private Connection conn = null;
	private static final Logger logger = Logger.getLogger(JDBCFavouriteDAOImpl.class.getName());
	
	@Override
	public void setConnection(Connection conn) {

		this.conn = conn;
		
	}
	
	@Override
	public Favourite get(long idu, long idp) {

		if (conn == null) return null;
		
		Favourite favourite = null;
		try {
			Statement stmt;
			ResultSet rs;
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Favourites WHERE idu="+idu+" AND idp="+idp);
			while ( rs.next() ) {
				
				fromRsToCategoryObject(rs,favourite);
				if(favourite!=null)
					logger.info("fetching Favourites: "+favourite.getIdu()+" "+favourite.getIdp());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return favourite;
	}
	

	@Override
	public List<Favourite> getAll() {

		if (conn == null) return null;
		
		ArrayList<Favourite> favourites = new ArrayList<Favourite>();
		try {
			Statement stmt;
			ResultSet rs;
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Favourites");
			while ( rs.next() ) {
				Favourite favourite = new Favourite();
				fromRsToCategoryObject(rs,favourite);
				favourites.add(favourite);
				logger.info("fetching Favourites: "+favourite.getIdu()+" "+favourite.getIdp());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return favourites;
	}

	@Override
	public List<Favourite> getAllByUser(long idu) {

		if (conn == null) return null;
		
		ArrayList<Favourite> favourites = new ArrayList<Favourite>();
		try {
			Statement stmt;
			ResultSet rs;
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Favourites WHERE idu="+idu);
			while ( rs.next() ) {
				Favourite favourite = new Favourite();
				fromRsToCategoryObject(rs,favourite);
				favourites.add(favourite);
				logger.info("fetching Favourites: "+favourite.getIdu()+" "+favourite.getIdp());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return favourites;
	}

	@Override
	public boolean add(Favourite Favourite) {
		boolean result = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO Favourites (idu,idp) VALUES(" + Favourite.getIdu() +", " + Favourite.getIdp()+ ")");
				result = true;
				logger.info("CREATING Favourites: ");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	@Override
	public boolean delete(Favourite Favourite) {
		boolean result = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM Favourites WHERE idu="+Favourite.getIdu()+" AND idp="+Favourite.getIdp());
				result = true;
				logger.info("Delete Favourites: ");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	
	

	public void fromRsToCategoryObject(ResultSet rs,Favourite favourite) throws SQLException{
		favourite.setIdu(rs.getInt("idu"));
		favourite.setIdp(rs.getInt("idp"));
	}
	
}
