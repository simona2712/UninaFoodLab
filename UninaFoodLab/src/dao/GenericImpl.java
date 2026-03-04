package dao;

import java.sql.Connection;
import java.sql.SQLException;
import database.DBConnection;

public abstract class GenericImpl {
    
	protected Connection getConnection() throws SQLException {
        return DBConnection.getConnection();
    }
    
    public abstract void create(Object o) throws SQLException;
    public abstract Object read(int id) throws SQLException;
    public abstract void update(Object o) throws SQLException;
    public abstract void delete(int id) throws SQLException;
}