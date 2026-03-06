package dao;

import java.sql.Connection;
import java.sql.SQLException;
import database.DBConnection;

public abstract class GenericImpl<T> implements GenericDAO<T>{
    
	protected Connection getConnection() throws SQLException {
        return DBConnection.getConnection();
    }
    
    public abstract void create(T o) throws SQLException;
    public abstract T read(int id) throws SQLException;
    public abstract void update(T o) throws SQLException;
    public abstract void delete(int id) throws SQLException;
}