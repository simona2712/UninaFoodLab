package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import entity.*;

public class AllievoImpl extends GenericImpl<Allievo> implements AllievoDAO{

    @Override
    public void create(Allievo o) throws SQLException {
        if (!(o instanceof Allievo)) return;
        Allievo a = (Allievo) o;

        String sql = """
            INSERT INTO allievo
            (nome, cognome, numero_telefono, email, livello_abilita, password)
            VALUES (?,?,?,?,?,?)
        """;

        try (PreparedStatement ps = getConnection()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, a.getNome());
            ps.setString(2, a.getCognome());
            ps.setString(3, a.getNumeroTelefono());
            ps.setString(4, a.getEmail());
            ps.setString(5, a.getLivelloAbilita());
            ps.setString(6, a.getPassword());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    a.setId(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public Allievo read(int id) throws SQLException {

        String sql = "SELECT * FROM allievo WHERE id_allievo=?";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Allievo a = new Allievo(
                            rs.getInt("id_allievo"),
                            rs.getString("nome"),
                            rs.getString("cognome"),
                            rs.getString("numero_telefono"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("livello_abilita")
                    );

                    AllergiaImpl allergiaDAO = new AllergiaImpl();
                    a.setAllergie(allergiaDAO.findByAllievo(id));

                    CorsoImpl corsoDAO = new CorsoImpl();
                    a.setCorsiSeguiti(corsoDAO.findCorsiByAllievo(id));

                    return a;
                }
            }
        }
        return null;
    }

    @Override
    public void update(Allievo o) throws SQLException {
        if (!(o instanceof Allievo)) return;
        Allievo a = (Allievo) o;

        String sql = """
            UPDATE allievo
            SET nome=?, cognome=?, numero_telefono=?, email=?,
                livello_abilita=?, password=?
            WHERE id_allievo=?
        """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {

            ps.setString(1, a.getNome());
            ps.setString(2, a.getCognome());
            ps.setString(3, a.getNumeroTelefono());
            ps.setString(4, a.getEmail());
            ps.setString(5, a.getLivelloAbilita());
            ps.setString(6, a.getPassword());
            ps.setInt(7, a.getId());

            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {

        String sql = "DELETE FROM allievo WHERE id_allievo=?";
        
        Connection conn = getConnection(); 

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    
    public List<Allievo> findAll() throws SQLException {
        List<Allievo> lista = new ArrayList<>();

        String sql = "SELECT * FROM allievo";

        try (Connection conn = getConnection(); 
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

               while (rs.next()) {
            	   lista.add(read(rs.getInt("id_allievo")));
               }
           }
        return lista;
    }
    
    public void aggiungiAllergia(int idAllievo, int idAllergia) throws SQLException {
        String sql = "INSERT INTO soffre (fk_allievo, fk_allergia) VALUES (?, ?)";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, idAllievo);
            ps.setInt(2, idAllergia);
            ps.executeUpdate();
        }
    }
    
    // Metodo per creare l'associazione nel database
    public void iscriviACorso(int idAllievo, int idCorso) throws SQLException {
        String sql = "INSERT INTO segue (fk_allievo, fk_corso) VALUES (?, ?)";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, idAllievo);
            ps.setInt(2, idCorso);
            ps.executeUpdate();
        }
    }

    // Metodo per rimuovere l'iscrizione
    public void disiscriviDaCorso(int idAllievo, int idCorso) throws SQLException {
        String sql = "DELETE FROM segue WHERE fk_allievo = ? AND fk_corso = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, idAllievo);
            ps.setInt(2, idCorso);
            ps.executeUpdate();
        }
    }
    
    public Allievo login(String username, String password) throws SQLException {
        String sql = "SELECT * FROM allievo WHERE email = ? AND password = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                	return new Allievo(
                            rs.getInt("id_allievo"),
                            rs.getString("nome"),
                            rs.getString("cognome"),
                            rs.getString("numero_telefono"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("livello_abilita")
                        );
                }
            }
        }
        return null;
    }
}