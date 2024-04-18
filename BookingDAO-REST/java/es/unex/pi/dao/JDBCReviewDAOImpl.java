package es.unex.pi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.model.Review;

public class JDBCReviewDAOImpl implements ReviewDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCReviewDAOImpl.class.getName());

	@Override
	public List<Review> getAll() {

		if (conn == null) return null;
						
		ArrayList<Review> reviewList = new ArrayList<Review>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Reviews");
						
			while ( rs.next() ) {
				Review review = new Review();
				fromRsToeviewObject(rs,review);
				reviewList.add(review);
				logger.info("fetching all Review: "+review.getIdp()+" "+review.getIdu());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return reviewList;
	}

	@Override
	public List<Review> getAllByUser(long idu) {
		
		if (conn == null) return null;
						
		ArrayList<Review> reviewList = new ArrayList<Review>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Reviews WHERE idu="+idu);

			while ( rs.next() ) {
				Review review = new Review();
				fromRsToeviewObject(rs,review);
				reviewList.add(review);
				logger.info("fetching all Review by idp: "+review.getIdp()+"->"+review.getIdu());
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return reviewList;
	}
	
	@Override
	public List<Review> getAllByProperty(long idp) {
		
		if (conn == null) return null;
						
		ArrayList<Review> reviewList = new ArrayList<Review>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Reviews WHERE Idp="+idp);

			while ( rs.next() ) {
				Review review = new Review();
				fromRsToeviewObject(rs,review);
				reviewList.add(review);
				logger.info("fetching all Review by idu: "+review.getIdu()+"-> "+review.getIdp());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return reviewList;
	}
	
	
	@Override
	public Review get(long idp,long idu) {
		if (conn == null) return null;
		
		Review review = null;		
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Reviews WHERE Idp="+idp+" AND idu="+idu);			 
			if (!rs.next()) return null;
			review= new Review();
			fromRsToeviewObject(rs,review);
			logger.info("fetching Review by idp: "+review.getIdp()+"  and idu: "+review.getIdu());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return review;
	}
	
	

	@Override
	public boolean add(Review review) {
		boolean done = false;
		if (conn != null){
			
			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO Reviews (idp,idu,review,grade) VALUES("+
									review.getIdp()+","+
									review.getIdu()+",'"+
									review.getReview()+"',"+
									review.getGrade()+")");
						
				logger.info("creating Review:("+review.getIdp()+" "+review.getIdu());
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public boolean update(Review review) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				String consulta = "UPDATE Reviews SET review = '" + review.getReview() + "', grade = " + review.getGrade() 
				+" WHERE idp = "+review.getIdp() + " AND idu = " + review.getIdu();
				System.out.println(consulta);

				stmt.executeUpdate(consulta);
				
				logger.info("updating Review:("+review.getIdp()+" "+review.getIdu());
				
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public boolean delete(long idp, long idu) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM Reviews WHERE idp ="+idp+" AND idu="+idu);
				logger.info("deleting Review: "+idp+" , idu="+idu);
				done= true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return done;
	}

	public void fromRsToeviewObject(ResultSet rs,Review review) throws SQLException{
		review.setIdp(rs.getInt("idp"));
		review.setIdu(rs.getInt("idu"));
		review.setReview(rs.getString("review"));
		review.setGrade(rs.getInt("grade"));
	}
	
	@Override
	public void setConnection(Connection conn) {
		// TODO Auto-generated method stub
		this.conn = conn;
	}
	
}
