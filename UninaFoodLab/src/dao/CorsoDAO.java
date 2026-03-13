package dao;

import entity.Chef;
import entity.Corso;
import java.sql.SQLException;
import java.util.List;

public interface CorsoDAO extends GenericDAO<Corso> {
    List<Corso> findAll() throws SQLException;
    List<Corso> findByArgomento(String argomento) throws SQLException;
    List<Corso> findCorsiByAllievo(int idAllievo) throws SQLException;
    List<Corso> findByChef(Chef chef) throws SQLException;
    int countCorsiTotali() throws SQLException;
}