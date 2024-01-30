package model.dao.login;

import java.sql.*;

import model.dao.ConnectionFactory;
import model.dao.GenericProcedureDAO;
import model.dao.exception.DAOException;
import model.domain.Credential;

public class DAOLogin implements GenericProcedureDAO<Credential>{
    
    @Override
    public Credential execute(Object... params) throws DAOException {
        String username = (String) params[0];
        String password = (String) params[1];
        String role = "NONE";

        try (Connection conn = ConnectionFactory.getConnection()) {
            
            String sql = "SELECT role FROM login WHERE username = ? AND password = ? LIMIT 1";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            stmt.close();

            if (rs.next()) {
                role = rs.getString("ROLE");
            }

            return new Credential(username, password, model.domain.Role.valueOf(role));
        } catch (SQLException e) {
            throw new DAOException("Logni error: " + e.getMessage());
        }
    }

}
