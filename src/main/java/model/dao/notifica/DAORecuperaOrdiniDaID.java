package model.dao.notifica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.dao.ConnectionFactory;
import model.dao.GenericProcedureDAO;
import model.dao.exception.DAOException;

public class DAORecuperaOrdiniDaID implements GenericProcedureDAO<List<String>>{

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    
    @Override
    public List<String> execute(Object... params) throws DAOException {
        List<String> result = new ArrayList<>();
        Integer id = (Integer) params[0];

        try (Connection conn = ConnectionFactory.getConnection()) {

            String sql = "SELECT * FROM ORDINI WHERE ID = ? LIMIT 1";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            stmt.close();

            result.add(String.valueOf(rs.getInt("ID")));
            result.add(rs.getString("NEGOZIO"));
            result.add(rs.getString("CLIENTE"));
            result.add(rs.getString("LISTA"));
            result.add(formatter.format(rs.getDate("DATA")));
            result.add(rs.getString("CONFERMATO"));
            
            return result;
        } catch (SQLException e) {
            throw new DAOException("Logni error: " + e.getMessage());
        }
    }

}
