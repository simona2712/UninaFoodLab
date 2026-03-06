package dao;

import entity.Notifica;
import java.sql.SQLException;
import java.util.List;

public interface NotificaDAO extends GenericDAO<Notifica> {
    List<Notifica> findAll() throws SQLException;
    List<Notifica> findByChef(int idChef) throws SQLException;
    List<Notifica> findByCorso(int idCorso) throws SQLException;
}