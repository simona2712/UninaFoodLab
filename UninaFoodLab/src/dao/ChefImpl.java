package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import entity.*;


public class ChefImpl extends GenericImpl<Chef> implements ChefDAO{

    @Override
    public void create(Chef o) throws SQLException {
        if (!(o instanceof Chef)) return;
        Chef c = (Chef) o;

        String sql = """
            INSERT INTO chef
            (nome, cognome, numero_telefono, email,
             specializzazione, anni_esperienza, password)
            VALUES (?,?,?,?,?,?,?)
        """;

        try (PreparedStatement ps = getConnection()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, c.getNome());
            ps.setString(2, c.getCognome());
            ps.setString(3, c.getNumeroTelefono());
            ps.setString(4, c.getEmail());
            ps.setString(5, c.getSpecializzazione());
            ps.setInt(6, c.getAnniEsperienza());
            ps.setString(7, c.getPassword());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) c.setId(rs.getInt(1));
            }
        }
    }

    @Override
    public Chef read(int id) throws SQLException {

        String sql = "SELECT * FROM chef WHERE id_chef=?";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Chef(
                        rs.getInt("id_chef"),
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("numero_telefono"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("specializzazione"),
                        rs.getInt("anni_esperienza")
                );
            }
        }

        return null;
    }

    @Override
    public void update(Chef o) throws SQLException {
        if (!(o instanceof Chef)) return;
        Chef c = (Chef) o;

        String sql = """
            UPDATE chef
            SET nome=?, cognome=?, numero_telefono=?, email=?,
                password=?, specializzazione=?, anni_esperienza=?
            WHERE id_chef=?
        """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {

            ps.setString(1, c.getNome());
            ps.setString(2, c.getCognome());
            ps.setString(3, c.getNumeroTelefono());
            ps.setString(4, c.getEmail());
            ps.setString(5, c.getPassword());
            ps.setString(6, c.getSpecializzazione());
            ps.setInt(7, c.getAnniEsperienza());
            ps.setInt(8, c.getId());

            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {

        String sql = "DELETE FROM chef WHERE id_chef=?";
        
        Connection conn = getConnection(); 

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    
    public List<Chef> findAll() throws SQLException {
        List<Chef> lista = new ArrayList<>();

        String sql = "SELECT * FROM chef";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

               while (rs.next()) {
                   Chef chef = new Chef(
                       rs.getInt("id_chef"),
                       rs.getString("nome"),
                       rs.getString("cognome"),
                       rs.getString("numero_telefono"),
                       rs.getString("email"),
                       rs.getString("password"),
                       rs.getString("specializzazione"),
                       rs.getInt("anni_esperienza")
                   );
                   lista.add(chef);
               }
           }

        return lista;
    }
    
    public Chef login(String username, String password) throws SQLException {
        String sql = "SELECT * FROM chef WHERE email = ? AND password = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                	return new Chef(
                            rs.getInt("id_chef"),
                            rs.getString("nome"),
                            rs.getString("cognome"),
                            rs.getString("numero_telefono"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("specializzazione"),
                            rs.getInt("anni_esperienza")
                        );
                }
            }
        }
        return null;
    }
}