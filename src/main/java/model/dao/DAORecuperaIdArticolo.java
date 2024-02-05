package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import model.dao.exception.DAOException;

public class DAORecuperaIdArticolo implements GenericProcedureDAO<List<Integer>>{
    
    @Override
    public List<Integer> execute(Object... params) throws DAOException {
        List<Integer> result = new ArrayList<>();
        Integer id = (Integer) params[0];

        try{
            Connection conn = ConnectionFactory.getConnection();
            String sql = "SELECT idARTICOLI as ARTICOLO FROM articoli WHERE idNegozio = ?";
            try(PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setLong(1, id);

                ResultSet rs = stmt.executeQuery();

                while(rs.next()) {
                    result.add(rs.getInt("ARTICOLO"));
                }   
            }
            return result;
        } catch (SQLException e) {
            throw new DAOException("DAORecuperaIdArticolo error: " + e.getMessage());
        }
    }

}

