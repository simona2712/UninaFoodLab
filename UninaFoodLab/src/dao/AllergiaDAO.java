package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import entity.Allergia;


public class AllergiaDAO extends GenericDAO {

    @Override
    public void create(Object o) throws SQLException {
        if (!(o instanceof Allergia)) return;
        Allergia a = (Allergia) o;
        String sql = "INSERT INTO allergia(nome) VALUES(?)";
        try (PreparedStatement ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, a.getNome());
            ps.executeUpdate();
            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) a.setId(rs.getInt(1));
            }
        }
    }

    @Override
    public Allergia read(int id) throws SQLException {
        String sql = "SELECT * FROM allergia WHERE id_allergia=?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Allergia(rs.getInt("id_allergia"), rs.getString("nome"));
                }
            }
        }
        return null;
    }

    @Override
    public void update(Object o) throws SQLException {
        if (!(o instanceof Allergia)) return;
        Allergia a = (Allergia) o;
        String sql = "UPDATE allergia SET nome=? WHERE id_allergia=?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, a.getNome());
            ps.setInt(2, a.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM allergia WHERE id_allergia=?";
        
        Connection conn = getConnection(); 

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public List<Allergia> findAll() throws SQLException {
        List<Allergia> lista = new ArrayList<>();
        String sql = "SELECT * FROM allergia";
        
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                lista.add(new Allergia(rs.getInt("id_allergia"), rs.getString("nome")));
            }
        }
        return lista;
    }
    
    public List<Allergia> findByAllievo(int idAllievo) throws SQLException {
        List<Allergia> lista = new ArrayList<>();
        String sql = """
            SELECT a.* FROM allergia a 
            JOIN soffre s ON a.id_allergia = s.fk_allergia 
            WHERE s.fk_allievo = ?
        """;
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, idAllievo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Allergia(
                        rs.getInt("id_allergia"), 
                        rs.getString("nome")
                    ));
                }
            }
        }
        return lista;
    }
}