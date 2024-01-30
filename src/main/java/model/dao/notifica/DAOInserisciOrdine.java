package model.dao.notifica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.dao.ConnectionFactory;
import model.dao.GenericProcedureDAO;
import model.dao.exception.DAOException;

public class DAOInserisciOrdine implements GenericProcedureDAO<Boolean>{
    
    @Override
    public Boolean execute(Object... params) throws DAOException {
        Integer idNegozio = (Integer) params[0];
        Integer idCliente = (Integer) params[1];
        Integer LISTA = (Integer) params[2];
        java.sql.Date DATA = (java.sql.Date) params[3];


        try (Connection conn = ConnectionFactory.getConnection()) {
            
            String sql = "INSERT INTO ORDINI (idNegozio, idCliente, LISTA, DATA, CONFERMATO) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, idNegozio);
            stmt.setLong(2, idCliente);
            stmt.setLong(3, LISTA);
            stmt.setDate(4, DATA);
            stmt.setBoolean(5, false);

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            throw new DAOException("Logni error: " + e.getMessage());
        }
    }
}
