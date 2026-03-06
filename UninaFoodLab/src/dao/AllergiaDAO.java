package dao;

import entity.Allergia;
import java.sql.SQLException;
import java.util.List;

public interface AllergiaDAO extends GenericDAO<Allergia> {
    List<Allergia> findAll() throws SQLException;
    List<Allergia> findByAllievo(int idAllievo) throws SQLException;
}