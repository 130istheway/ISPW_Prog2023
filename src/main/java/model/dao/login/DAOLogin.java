package model.dao.login;

import java.sql.*;

import model.dao.ConnectionFactory;
import model.dao.GenericProcedureDAO;
import model.dao.exception.DAOException;
import model.domain.Credential;
import model.domain.Role;

public class DAOLogin implements GenericProcedureDAO<Credential>{
    
    @Override
    public Credential execute(Object... params) throws DAOException {
        String username = (String) params[0];
        String password = (String) params[1];
        String role = "NONE";

        try {
            Connection conn = ConnectionFactory.getConnection();
            
            String sql = "SELECT role FROM login WHERE username = ? AND password = ? LIMIT 1";

            try(PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, password);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        role = rs.getString("ROLE");
                        return new Credential(username, password, model.domain.Role.valueOf(role));
                    }
                } catch (SQLTimeoutException e) {
                    throw new DAOException("DAOLogin : Time out");
                }
            }
            return new Credential(null, null, Role.NONE);
        } catch (SQLException e) {
            throw new DAOException("DAOLogin : " + e.getMessage());
        }
    }

}
