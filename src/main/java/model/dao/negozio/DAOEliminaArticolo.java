package model.dao.negozio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.dao.ConnectionFactory;
import model.dao.GenericProcedureDAO;
import model.dao.exception.DAOException;

public class DAOEliminaArticolo implements GenericProcedureDAO<Boolean>{
    
    @Override
    public Boolean execute(Object... params) throws DAOException {
        Integer idArticoli = (Integer) params[0];
        Integer idNegozio = (Integer) params[1];

        try(Connection conn = ConnectionFactory.getConnection()) {
            
            String sql = "DELETE FROM articoli WHERE idARTICOLI = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, idArticoli);

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted < 1) {
                return false;
            }
             sql = "DELETE FROM articolinegozi WHERE idArticolo = ? AND idNegozio = ?;";
             stmt = conn.prepareStatement(sql);
            stmt.setLong(1, idArticoli);
            stmt.setLong(2, idNegozio);

            rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) return true;

            return false;
        } catch (SQLException e) {
            throw new DAOException("Logni error: " + e.getMessage());
        }
    }

}

