package model.dao.notifica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.dao.ConnectionFactory;
import model.dao.GenericProcedureDAO;
import model.dao.exception.DAOException;

public class DAORecuperaIdOrdini implements GenericProcedureDAO<List<Integer>>{
    
    @Override
    public List<Integer> execute(Object... params) throws DAOException {
        List<Integer> result = new ArrayList<>();
        Integer id = (Integer) params[0];

        try (Connection conn = ConnectionFactory.getConnection()) {

            String sql = "SELECT ID FROM ORDINI WHERE idNegozio = ? AND DATA > CURRENT_DAY AND COFERMATO = 'NO' LIMIT 1";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            stmt.close();
            
            result.add(rs.getInt("ID"));
            
            return result;

        } catch (SQLException e) {
            throw new DAOException("Logni error: " + e.getMessage());
        }
    }

}