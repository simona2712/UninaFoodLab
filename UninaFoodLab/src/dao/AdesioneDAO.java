package dao;

import entity.Adesione;
import java.sql.SQLException;
import java.util.List;

public interface AdesioneDAO extends GenericDAO<Adesione>{
	
	List<Adesione> findAll() throws SQLException;
	int countAdesioniBySessione(int idSessione) throws SQLException;
	int countAdesioniByCorso(int idCorso) throws SQLException;
	void iscriviSessioneOnline(int idAllievo, int idSessione) throws SQLException;
	int countAdesioniByAllievoAndCorso(int idAllievo, int idCorso) throws SQLException;
	int countAdesioniByAllievoAndSessioneOnline(int idAllievo, int idSessioneOnline) throws SQLException;

}
