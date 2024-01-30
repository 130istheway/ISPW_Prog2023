package model.dao.notifica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.dao.ConnectionFactory;
import model.dao.GenericProcedureDAO;
import model.dao.exception.DAOException;

public class DAOConfermaOrdineDalID implements GenericProcedureDAO<Boolean>{
    
    @Override
    public Boolean execute(Object... params) throws DAOException {
        Integer id = (Integer) params[0];

        try (Connection conn = ConnectionFactory.getConnection()) {

            String sql = "UPDATE item SET CONFERMATO = TRUE WHERE ItemCode = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);

            int rowsInserted = stmt.executeUpdate();

            stmt.close();

            if (rowsInserted > 0) return true;

            return false;
        } catch (SQLException e) {
            throw new DAOException("Logni error: " + e.getMessage());
        }
    }

}