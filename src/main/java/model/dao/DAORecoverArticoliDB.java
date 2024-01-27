package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.dao.exception.DAOException;

public class DAORecoverArticoliDB implements GenericProcedureDAO<String>{
    
    @Override
    public String execute(Object... params) throws DAOException {
        Integer id = (Integer) params[0];
        String string = null;

        try(Connection conn = ConnectionFactory.getConnection()){
            String sql = "SELECT `ARTICOblob` as DATI FROM articoli WHERE `idARTICOLI` = ? LIMIT 1";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            stmt.close();

            if (rs.next()) {
                string = rs.getString("DATI");
            }
            
            return string;
        } catch (SQLException e) {
            throw new DAOException("Logni error: " + e.getMessage());
        }
    }

}

