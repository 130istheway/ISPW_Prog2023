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

        try{
            Connection conn = ConnectionFactory.getConnection();
            
            String sql = "SELECT * FROM ORDINI WHERE `idORDINI` = ? LIMIT 1";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();
            
            rs.next();

            result.add(String.valueOf(rs.getInt("idORDINI")));
            result.add(rs.getString("idNegozio"));
            result.add(rs.getString("idUtente"));
            result.add(rs.getString("listaOrdine"));
            result.add(formatter.format(rs.getDate("DATA")));
            result.add(rs.getString("CONFERMATO"));

            return result;
        } catch (SQLException e) {
            throw new DAOException("DAORecuperaOrdiniDaID : " + e.getMessage());
        }
    }

}
