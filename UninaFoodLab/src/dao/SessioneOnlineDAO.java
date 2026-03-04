package dao;

import entity.SessioneOnline;
import java.sql.SQLException;
import java.util.List;

public interface SessioneOnlineDAO extends SessioneDAO, GenericDAO {
    List<SessioneOnline> findAll() throws SQLException;
    List<SessioneOnline> findByCorso(int idCorso) throws SQLException;
}