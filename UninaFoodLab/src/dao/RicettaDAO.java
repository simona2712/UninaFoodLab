package dao;

import entity.Ricetta;
import java.sql.SQLException;
import java.util.List;

public interface RicettaDAO extends GenericDAO {
    List<Ricetta> findAll() throws SQLException;
    List<Ricetta> findBySessionePratica(int idSessione) throws SQLException;
}