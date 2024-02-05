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
        String cosa = (String) params[1];

        try{
            Connection conn = ConnectionFactory.getConnection();
            
            String sql = "UPDATE ORDINI SET CONFERMATO = ? WHERE idORDINI = ?";
            try(PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setLong(2, id);
                stmt.setString(1, cosa);

                int rowsInserted = stmt.executeUpdate();

                return rowsInserted > 0;
            }
        } catch (SQLException e) {
            throw new DAOException("DAOConfermaOrdineDalID : " + e.getMessage());
        }
    }

}