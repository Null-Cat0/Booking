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
	public List<Favourite> getAll() {

		if (conn == null) return null;
		
		ArrayList<Favourite> categories = new ArrayList<Favourite>();
		try {
			Statement stmt;
			ResultSet rs;
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Favourites");
			while ( rs.next() ) {
				Category category = new Category();
				fromRsToCategoryObject(rs,category);
				categories.add(category);
				logger.info("fetching Categories: "+category.getId()+" "+category.getName()+" "+category.getDescription());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categories;
	}

	@Override
	public List<Favourite> getAllByUser(long idu) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean add(Favourite Favourite) {
		// TODO Auto-generated method stub
		return false;
	}

	public void fromRsToCategoryObject(ResultSet rs,Category category) throws SQLException{
		category.setId(rs.getInt("id"));
		category.setName(rs.getString("name"));
		category.setDescription(rs.getString("description"));
	}
	
}
