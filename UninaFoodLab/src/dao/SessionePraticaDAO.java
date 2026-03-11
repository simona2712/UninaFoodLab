package dao;

import entity.SessionePratica;
import java.sql.SQLException;
import java.util.List;

public interface SessionePraticaDAO extends GenericDAO<SessionePratica> {
    List<SessionePratica> findAll() throws SQLException;
    void associaRicetta(int idSessione, int idRicetta) throws SQLException;
    List<SessionePratica> findFutureByCorso(int idCorso) throws SQLException;
    List<SessionePratica> findByChef(int idChef) throws SQLException;
}