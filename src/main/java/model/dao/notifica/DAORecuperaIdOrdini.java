package model.dao.notifica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.dao.ConnectionFactory;
import model.dao.GenericProcedureDAO;
import model.dao.exception.DAOException;

public class DAORecuperaIdOrdini implements GenericProcedureDAO<List<Integer>>{
    
    @Override
    public List<Integer> execute(Object... params) throws DAOException {
        List<Integer> result = new ArrayList<>();
        Integer id = (Integer) params[0];

        try{
            Connection conn = ConnectionFactory.getConnection();
            
            String sql = "SELECT * FROM ORDINI WHERE idNegozio = ? AND DATA > CURRENT_DAte() AND CONFERMATO = 'NI'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();
            if (!rs.wasNull()) {    
                while (rs.next()) {
                    result.add(rs.getInt("idORDINI"));   
                }
                return result;
            } else {
                return Collections.emptyList();
            }

        } catch (SQLException e) {
            throw new DAOException("DAORecuperaIdOrdini : " + e.getMessage());
        }
    }

}