package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import entity.Notifica;
import entity.Chef;
import entity.Corso;

public class NotificaDAO extends GenericDAO {
	private ChefDAO chefDAO = new ChefDAO();
    private CorsoDAO corsoDAO = new CorsoDAO();

    @Override
    public void create(Object o) throws SQLException {
        if (!(o instanceof Notifica n)) return;

        String sql = """
            INSERT INTO notifica (testo, data_creazione, fk_chef, fk_corso)
            VALUES (?, ?, ?, ?)
        """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, n.getTesto());
            ps.setDate(2, Date.valueOf(n.getDataCreazione()));
            ps.setInt(3, n.getChef().getId());
            
            // Gestione del Corso null (Notifica Generale)
            if (n.getCorso() != null) {
                ps.setInt(4, n.getCorso().getId());
            } else {
                ps.setNull(4, Types.INTEGER);
            }

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    n.setId(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public Notifica read(int id) throws SQLException {
        String sql = "SELECT * FROM notifica WHERE id_notifica = ?";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Recupero Chef
                    ChefDAO chefDAO = new ChefDAO();
                    Chef chef = chefDAO.read(rs.getInt("fk_chef"));

                    // Recupero Corso (se presente)
                    Corso corso = null;
                    int idCorso = rs.getInt("fk_corso");
                    if (!rs.wasNull()) {
                        CorsoDAO corsoDAO = new CorsoDAO();
                        corso = corsoDAO.read(idCorso);
                    }

                    Notifica n = new Notifica(
                        rs.getInt("id_notifica"),
                        rs.getString("testo"),
                        chef,
                        corso
                    );
                    
                    // Impostiamo la data reale salvata nel DB
                    n.setDataCreazione(rs.getDate("data_creazione").toLocalDate());
                    
                    return n;
                }
            }
        }
        return null;
    }

    @Override
    public void update(Object o) throws SQLException {
        if (!(o instanceof Notifica n)) return;

        String sql = """
            UPDATE notifica 
            SET testo = ?, data_creazione = ?, fk_chef = ?, fk_corso = ? 
            WHERE id_notifica = ?
        """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, n.getTesto());
            ps.setDate(2, Date.valueOf(n.getDataCreazione()));
            ps.setInt(3, n.getChef().getId());
            
            if (n.getCorso() != null) {
                ps.setInt(4, n.getCorso().getId());
            } else {
                ps.setNull(4, Types.INTEGER);
            }
            
            ps.setInt(5, n.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM notifica WHERE id_notifica = ?";
        
        Connection conn = getConnection(); 

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public List<Notifica> findAll() throws SQLException {
        List<Notifica> lista = new ArrayList<>();
        String sql = "SELECT * FROM notifica";

        ChefDAO chefDAO = new ChefDAO();
        CorsoDAO corsoDAO = new CorsoDAO();

        try (PreparedStatement ps = getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Chef chef = chefDAO.read(rs.getInt("fk_chef"));
                
                Corso corso = null;
                int idCorso = rs.getInt("fk_corso");
                if (!rs.wasNull()) {
                    corso = corsoDAO.read(idCorso);
                }

                Notifica n = new Notifica(
                    rs.getInt("id_notifica"),
                    rs.getString("testo"),
                    chef,
                    corso
                );
                
                n.setDataCreazione(rs.getDate("data_creazione").toLocalDate());
                lista.add(n);
            }
        }
        return lista;
    }
    
    public List<Notifica> findByChef(int idChef) throws SQLException {
        List<Notifica> lista = new ArrayList<>();
        String sql = "SELECT * FROM notifica WHERE fk_chef = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idChef);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Chef chef = chefDAO.read(rs.getInt("fk_chef")); // otteniamo oggetto Chef
                    Corso corso = null;
                    int idCorso = rs.getInt("fk_corso");
                    if (!rs.wasNull()) {
                        corso = corsoDAO.read(idCorso); // otteniamo oggetto Corso
                    }

                    lista.add(new Notifica(
                        rs.getInt("id_notifica"),
                        rs.getString("testo"),
                        chef,
                        corso
                    ));
                }
            }
        }
        return lista;
    }
    
    public List<Notifica> findByCorso(int idCorso) throws SQLException {
        List<Notifica> lista = new ArrayList<>();
        String sql = "SELECT * FROM notifica WHERE fk_corso = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCorso);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Chef chef = chefDAO.read(rs.getInt("fk_chef"));
                    Corso corso = corsoDAO.read(rs.getInt("fk_corso"));

                    lista.add(new Notifica(
                        rs.getInt("id_notifica"),
                        rs.getString("testo"),
                        chef,
                        corso
                    ));
                }
            }
        }
        return lista;
    }
}