package es.unex.pi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.model.Favourite;
import es.unex.pi.model.Message;

public class JDBCMessageDAOImpl implements MessageDAO {
	private Connection conn;
	Logger logger = Logger.getLogger(JDBCMessageDAOImpl.class.getName());

	@Override
	public void setConnection(Connection conn) {
		this.conn = conn;

	}

	@Override
	public List<Message> getAll() {

		if (conn == null)
			return null;

		ArrayList<Message> messages = new ArrayList<Message>();
		try {
			Statement stmt;
			ResultSet rs;
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Favourites");
			while (rs.next()) {
				Message message = new Message();
				fromRsToCategoryObject(rs, message);
				messages.add(message);
				logger.info("fetching messages: " + message.getIds() + " " + message.getIdr());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return messages;
	}

	@Override
	public long add(Message message) {
	    long id = -1;
	    if (conn != null) {
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;
	        try {
	            // Iniciar una transacción
	            conn.setAutoCommit(false);

	            // Insertar el mensaje en la tabla
	            pstmt = conn.prepareStatement("INSERT INTO messages (sender_id, receiver_id, message_text) VALUES (?, ?, ?)");
	            pstmt.setLong(1, message.getIds());
	            pstmt.setLong(2, message.getIdr());
	            pstmt.setString(3, message.getTexto());
	            int affectedRows = pstmt.executeUpdate();

	            if (affectedRows == 0) {
	                // Si no se insertó ningún registro, lanzar una excepción o manejar el error adecuadamente
	                throw new SQLException("Failed to insert message");
	            }

	            // Obtener el ID generado automáticamente
	            rs = pstmt.getGeneratedKeys();
	            if (rs.next()) {
	                id = rs.getLong(1);
	            } else {
	                // Si no se pudo obtener el ID, lanzar una excepción o manejar el error adecuadamente
	                throw new SQLException("Failed to retrieve generated message ID");
	            }

	            // Confirmar la transacción
	            conn.commit();
	        } catch (SQLException e) {
	            // Si ocurre algún error, revertir la transacción y manejar la excepción adecuadamente
	            try {
	                if (conn != null) {
	                    conn.rollback();
	                }
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	            e.printStackTrace();
	        } finally {
	            // Cerrar los recursos
	            try {
	                if (pstmt != null) pstmt.close();
	                if (rs != null) rs.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    return id;
	}

	private void fromRsToCategoryObject(ResultSet rs, Message m) throws SQLException {
		m.setId(rs.getInt("message_id"));
		m.setTexto(rs.getString("message_text"));
		m.setIdr(rs.getInt("receiver_id"));
		m.setIds(rs.getInt("sender_id"));

	}

	@Override
	public List<Message> getByR(long id) {
		if (conn == null)
			return null;

		ArrayList<Message> messages = new ArrayList<Message>();
		try {
			Statement stmt;
			ResultSet rs;
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Messages WHERE receiver_id = " + id);
			while (rs.next()) {
				Message message = new Message();
				fromRsToCategoryObject(rs, message);
				messages.add(message);
				logger.info("fetching Favourites: " + message.getIds() + " " + message.getIdr());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return messages;
	}

	@Override
	public List<Message> getBySender(long id) {
		if (conn == null)
			return null;

		ArrayList<Message> messages = new ArrayList<Message>();
		try {
			Statement stmt;
			ResultSet rs;
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Messages WHERE sender_id = " + id);
			while (rs.next()) {
				Message message = new Message();
				fromRsToCategoryObject(rs, message);
				messages.add(message);
				logger.info("fetching Favourites: " + message.getIds() + " " + message.getIdr());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return messages;
	}

}
