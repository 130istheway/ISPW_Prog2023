package model.dao.negozio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.dao.ConnectionFactory;
import model.dao.GenericProcedureDAO;
import model.dao.exception.DAOException;

public class DAOIdNegozio implements GenericProcedureDAO<Integer>{
    
    @Override
    public Integer execute(Object... params) throws DAOException {
        String username = (String) params[0];
        int id = 0;

        try{
            Connection conn = ConnectionFactory.getConnection();
            
            String sql = "SELECT ID FROM login WHERE username = ? LIMIT 1";
            try(PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setString(1, username);

                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    id = rs.getInt("ID");
                }

                return id;
            }
        } catch (SQLException e) {
            throw new DAOException("DAOIdNegozio : " + e.getMessage());
        }
    }

}
