package model.dao.negozio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.dao.ConnectionFactory;
import model.dao.GenericProcedureDAO;
import model.dao.exception.DAOException;

public class DAOCountArticoli implements GenericProcedureDAO<Integer>{
    
    @Override
    public Integer execute(Object... params) throws DAOException {
        Integer username = (Integer) params[0];
        int id = 0;

        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql = "SELECT COUNT(idARTICOLI) ad number FROM articoli WHERE `idNegozio` = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, username);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                id = rs.getInt("number");
            }

            return id;
        } catch (SQLException e) {
            throw new DAOException("Logni error: " + e.getMessage());
        }
    }

}

