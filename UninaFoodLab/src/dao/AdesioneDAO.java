package dao;

import entity.Adesione;
import java.sql.SQLException;
import java.util.List;

public interface AdesioneDAO extends GenericDAO{
	
	List<Adesione> findAll() throws SQLException;
	int countAdesioniBySessione(int idSessione) throws SQLException;

}
