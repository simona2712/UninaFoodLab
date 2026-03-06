package dao;

import entity.Utilizzo;
import java.sql.SQLException;
import java.util.List;

public interface UtilizzoDAO extends GenericDAO<Utilizzo> {
    void create(int idRicetta, Utilizzo u) throws SQLException;
    List<Utilizzo> findByRicetta(int idRicetta) throws SQLException;
    void update(int idRicetta, Utilizzo u) throws SQLException;
    void delete(int idRicetta, int idIngrediente) throws SQLException;
}