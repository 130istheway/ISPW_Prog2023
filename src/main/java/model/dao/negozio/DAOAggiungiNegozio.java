package model.dao.negozio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import carrello.articoli.Articoli;
import carrello.articoli.Factory;
import model.dao.ConnectionFactory;
import model.dao.GenericProcedureDAO;
import model.dao.exception.DAOException;
import util.ConvertiStringToArticolo;

public class DAOAggiungiNegozio implements GenericProcedureDAO<Boolean>{
    
    @Override
    public Boolean execute(Object... params) throws DAOException {
        String dati = (String) params[0];
        Integer id = (Integer) params[1];
        ResultSet rs;

        try{
            Connection conn = ConnectionFactory.getConnection();
             
            String sql = "INSERT INTO articoli (ARTICOblob, idNegozio) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, dati);
            stmt.setLong(2, id);

            int rowsInserted = stmt.executeUpdate();
            rs = stmt.getGeneratedKeys();

            if (rowsInserted < 1){
                return false;
            }

            int autoId = 999;
            
            if (rs.next()) {
                autoId = rs.getInt(1);
            }

            List<Object> listaList = new ArrayList<>();
            listaList = ConvertiStringToArticolo.convertToArticoloList(dati);
            Articoli articolo = Factory.factoryProdotto(listaList);
            articolo.setId(autoId);

            sql = "UPDATE articoli SET ARTICOblob = ? WHERE idARTICOLI = ?";
            stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, articolo.toString());
            stmt.setInt(2, autoId);

            rowsInserted = stmt.executeUpdate();

            if (rowsInserted < 1) {
                return false;
            }

            return true;
        } catch (SQLException e) {
            throw new DAOException("DAOAggiungiNegozio : " + e.getMessage());
        }
    }

}
