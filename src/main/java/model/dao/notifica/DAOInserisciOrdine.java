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
        String lista = (String) params[2];
        java.sql.Date data = (java.sql.Date) params[3];

        try{
            Connection conn = ConnectionFactory.getConnection();
            
            String sql = "INSERT INTO ORDINI (idNegozio, idUtente, listaOrdine, DATA, CONFERMATO) VALUES (?, ?, ?, ?, ?)";
            try(PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setLong(1, idNegozio);
                stmt.setLong(2, idCliente);
                stmt.setString(3, lista);
                stmt.setDate(4, data);
                stmt.setString(5, "NI");

                int rowsInserted = stmt.executeUpdate();

                return rowsInserted > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("DAOInserisciOrdine : " + e.getMessage());
        }
    }
}
