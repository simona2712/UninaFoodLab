package dao;

import entity.Ricetta;
import java.sql.SQLException;
import java.util.List;

public interface RicettaDAO extends GenericDAO<Ricetta> {
    List<Ricetta> findAll() throws SQLException;
    List<Ricetta> findBySessionePratica(int idSessione) throws SQLException;
	void associaASessionePratica(int idRicetta, int idSessione) throws SQLException;
	void iscriviSessioneOnline(int idAllievo, int idSessione) throws SQLException;
	void aggiungiIngrediente(int idRicetta, int idIngrediente, double quantita) throws SQLException;
	List<Ricetta> findByCorso(int idCorso) throws SQLException;
	
}