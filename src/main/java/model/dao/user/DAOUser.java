package model.dao.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.dao.ConnectionFactory;
import model.dao.GenericProcedureDAO;
import model.dao.exception.DAOException;

public class DAOUser implements GenericProcedureDAO<String>{
    
    @Override
    public String execute(Object... params) throws DAOException {
        Integer id = (Integer) params[0];

        try{
            Connection conn = ConnectionFactory.getConnection();
            
            String sql = "SELECT * FROM ORDINI WHERE `idUtente` = ?";
            try(PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setLong(1, id);

                ResultSet rs = stmt.executeQuery();

                String capo = "_";
                StringBuilder daRestituire = new StringBuilder();

                while(rs.next()){
                    daRestituire.append("ID : " + rs.getInt("idORDINI") + " | Negozio - " + rs.getString("idNegozio") + " | Utente - " + rs.getString("idUtente") + " | Ordine - " + rs.getString("listaOrdine") + " | Data - " + rs.getString("DATA").substring(0, 10) + " | Conferma - " + rs.getString("CONFERMATO")+ capo);
                }
                return daRestituire.toString();
            }
        } catch (SQLException e) {
            throw new DAOException("DAOUser : " + e.getMessage());
        }
    }

}
