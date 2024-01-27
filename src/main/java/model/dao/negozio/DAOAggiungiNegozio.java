package model.dao.negozio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.dao.ConnectionFactory;
import model.dao.GenericProcedureDAO;
import model.dao.exception.DAOException;

public class DAOAggiungiNegozio implements GenericProcedureDAO<Boolean>{
    
    @Override
    public Boolean execute(Object... params) throws DAOException {
        String dati = (String) params[0];
        Integer id = (Integer) params[1];
        ResultSet rs;

        try {
            
            Connection conn = ConnectionFactory.getConnection();
            String sql = "INSERT INTO articoli (ARTICOblob, idNegozio) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, dati);
            stmt.setLong(2, id);

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                rs = stmt.getGeneratedKeys();
            }else{
                return false;
            }

            Integer artId = rs.getInt(1);

            sql = "INSERT INTO articolinegozi (idNegozio, idArticolo) VALUES (? , ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);
            stmt.setLong(2, artId);

            int rowsInserted2 = stmt.executeUpdate();

            if (rowsInserted2 > 0) return true;
            
            return false;
        } catch (SQLException e) {
            throw new DAOException("Logni error: " + e.getMessage());
        }
    }

}
