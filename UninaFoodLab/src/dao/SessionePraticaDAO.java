package dao;

import entity.SessionePratica;
import java.sql.SQLException;
import java.util.List;

public interface SessionePraticaDAO extends GenericDAO {
    List<SessionePratica> findAll() throws SQLException;
    void associaRicetta(int idSessione, int idRicetta) throws SQLException;
}