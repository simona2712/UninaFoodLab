package dao;

import entity.Ingrediente;
import java.sql.SQLException;
import java.util.List;

public interface IngredienteDAO extends GenericDAO {
    List<Ingrediente> findAll() throws SQLException;
    List<Ingrediente> findByNome(String nome) throws SQLException;
}