package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import entity.Ingrediente;

public class IngredienteImpl extends GenericImpl<Ingrediente> implements IngredienteDAO{

    @Override
    public void create(Ingrediente o) throws SQLException {
        if (!(o instanceof Ingrediente i)) return;

        String sql = """
            INSERT INTO ingrediente (nome, tipologia_conservazione, data_scadenza, calorie)
            VALUES (?, ?, ?, ?)
        """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, i.getNome());
            ps.setString(2, i.getTipologiaConservazione());
            ps.setDate(3, Date.valueOf(i.getDataScadenza()));
            ps.setDouble(4, i.getCalorie());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    i.setId(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public Ingrediente read(int id) throws SQLException {
        String sql = "SELECT * FROM ingrediente WHERE id_ingrediente = ?";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Ingrediente(
                        rs.getInt("id_ingrediente"),
                        rs.getString("nome"),
                        rs.getString("tipologia_conservazione"),
                        rs.getDate("data_scadenza").toLocalDate(),
                        rs.getDouble("calorie")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public void update(Ingrediente o) throws SQLException {
        if (!(o instanceof Ingrediente i)) return;

        String sql = """
            UPDATE ingrediente 
            SET nome = ?, tipologia_conservazione = ?, data_scadenza = ?, calorie = ? 
            WHERE id_ingrediente = ?
        """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, i.getNome());
            ps.setString(2, i.getTipologiaConservazione());
            ps.setDate(3, Date.valueOf(i.getDataScadenza()));
            ps.setDouble(4, i.getCalorie());
            ps.setInt(5, i.getId());

            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM ingrediente WHERE id_ingrediente = ?";
        
        Connection conn = getConnection(); 

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public List<Ingrediente> findAll() throws SQLException {
        List<Ingrediente> lista = new ArrayList<>();
        String sql = "SELECT * FROM ingrediente";

        try (PreparedStatement ps = getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Ingrediente i = new Ingrediente(
                    rs.getInt("id_ingrediente"),
                    rs.getString("nome"),
                    rs.getString("tipologia_conservazione"),
                    rs.getDate("data_scadenza").toLocalDate(),
                    rs.getDouble("calorie")
                );
                lista.add(i);
            }
        }
        return lista;
    }
    
    public List<Ingrediente> findByNome(String nome) throws SQLException {
        List<Ingrediente> lista = new ArrayList<>();

        String sql = "SELECT * FROM ingrediente WHERE nome ILIKE ? ORDER BY nome ASC";

        Connection conn = getConnection();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + nome + "%"); // Cerca ingredienti che contengono la stringa
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Ingrediente ing = new Ingrediente(
                        rs.getInt("id_ingrediente"),
                        rs.getString("nome"),
                        rs.getString("tipologia_conservazione"),
                        rs.getDate("data_scadenza").toLocalDate(),
                        rs.getDouble("calorie")
                    );
                    lista.add(ing);
                }
            }
        }
        return lista;
    }
    
    
    public List<Ingrediente> findByRicetta(int idRicetta) throws SQLException {

        List<Ingrediente> lista = new ArrayList<>();

        String sql = """
            SELECT i.*
            FROM ingrediente i
            JOIN utilizzo u ON i.id_ingrediente = u.fk_ingrediente
            WHERE u.fk_ricetta = ?
        """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {

            ps.setInt(1, idRicetta);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Ingrediente i = new Ingrediente(
                            rs.getInt("id_ingrediente"),
                            rs.getString("nome"),
                            rs.getString("tipologia_conservazione"),
                            rs.getDate("data_scadenza").toLocalDate(),
                            rs.getDouble("calorie")
                    );

                    lista.add(i);
                }
            }
        }

        return lista;
    }
}