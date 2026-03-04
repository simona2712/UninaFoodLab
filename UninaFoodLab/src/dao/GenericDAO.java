package dao;

import java.sql.SQLException;

public interface GenericDAO {
	
	void create(Object o) throws SQLException;
	Object read(int id) throws SQLException;
	void update(Object o) throws SQLException;
	void delete(int id) throws SQLException;
}
