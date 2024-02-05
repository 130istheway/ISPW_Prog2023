package model.dao.negozio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import carrello.articoli.Articoli;
import carrello.articoli.Factory;
import model.dao.ConnectionFactory;
import model.dao.GenericProcedureDAO;
import model.dao.exception.DAOException;
import util.ConvertiStringToArticolo;

public class DAORecuperaTuttiArticoliBase implements GenericProcedureDAO<String>{
    @Override
    public String execute(Object... params) throws DAOException {
        Integer id = (Integer) params[0];

        try{
            Connection conn = ConnectionFactory.getConnection();
            
            String sql = "SELECT * FROM Articoli WHERE `idNegozio` = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            String capo = "_";
            StringBuilder daRestituire = new StringBuilder();

            while(rs.next()){

                List<Object> listaList = new ArrayList<>();
                listaList = ConvertiStringToArticolo.convertToArticoloList( rs.getString("ARTICOblob"));
                Articoli articolo = Factory.factoryProdotto(listaList);

                daRestituire.append("ID : " + rs.getInt("idARTICOLI") + " | ARTICOLO :" + articolo.getNomeArticolo() + "  prezzo :" + articolo.getPrezzoArticolo()+ "  quantita : "  + articolo.getQuantitaArticolo() + capo);
            }
            return daRestituire.toString();
        } catch (SQLException e) {
            throw new DAOException("DAORecuperaIdOrdini : " + e.getMessage());
        }
    }
}