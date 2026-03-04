package dao;

import entity.Chef;
import java.sql.SQLException;
import java.util.List;

public interface ChefDAO extends UtenteDAO , GenericDAO{
	
    List<Chef> findAll() throws SQLException;
    Chef login(String username, String password) throws SQLException;
}