package dao;

import java.sql.SQLException;

public interface GenericDAO<T> {
	
	void create(T o) throws SQLException;
	T read(int id) throws SQLException;
	void update(T o) throws SQLException;
	void delete(int id) throws SQLException;
}
