package dao;

import entity.SessioneOnline;
import java.sql.SQLException;
import java.util.List;

public interface SessioneOnlineDAO extends GenericDAO<SessioneOnline> {
    List<SessioneOnline> findAll() throws SQLException;
    List<SessioneOnline> findByCorso(int idCorso) throws SQLException;
    List<SessioneOnline> findFutureByCorso(int idCorso) throws SQLException;
    List<SessioneOnline> findByChef(int idChef) throws SQLException;
}