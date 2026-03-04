package dao;

import entity.Allievo;
import java.sql.SQLException;
import java.util.List;

public interface AllievoDAO extends GenericDAO{
	
	List<Allievo> findAll() throws SQLException;
	void aggiungiAllergia(int idAllievo, int idAllergia) throws SQLException;
	void iscriviACorso(int idAllievo, int idCorso) throws SQLException;
	void disiscriviDaCorso(int idAllievo, int idCorso) throws SQLException;

}
