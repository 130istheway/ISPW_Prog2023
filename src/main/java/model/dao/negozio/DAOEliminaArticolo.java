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

        try{
            Connection conn = ConnectionFactory.getConnection();
            
            String sql = "DELETE FROM articoli WHERE idARTICOLI = ? AND idNegozio = ?";
            try(PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setLong(1, idArticoli);
                stmt.setLong(2, idNegozio);

                int rowsInserted = stmt.executeUpdate();

                return rowsInserted > 0;
            }
        } catch (SQLException e) {
            throw new DAOException("DAOEliminaArticolo : " + e.getMessage());
        }
    }

}

