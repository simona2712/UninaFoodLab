package dao;

import entity.Ricetta;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface RicettaDAO extends GenericDAO<Ricetta> {
    List<Ricetta> findAll() throws SQLException;
    List<Ricetta> findBySessionePratica(int idSessione) throws SQLException;
	void associaASessionePratica(int idRicetta, int idSessione) throws SQLException;
	void iscriviSessioneOnline(int idAllievo, int idSessione) throws SQLException;
	void aggiungiIngrediente(int idRicetta, int idIngrediente, double quantita) throws SQLException;
	List<Ricetta> findByCorso(int idCorso) throws SQLException;
	Map<String,Integer> getTopRicetteUsate() throws SQLException;
	double getMediaRicettePerSessione() throws SQLException;
	int getMaxRicettePerSessione() throws SQLException;
	int getMinRicettePerSessione() throws SQLException;
	
}