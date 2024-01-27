package model.dao;

import java.sql.SQLException;

import model.dao.exception.DAOException;

public interface GenericProcedureDAO<P> {
    P execute(Object... params) throws SQLException, DAOException;
}
