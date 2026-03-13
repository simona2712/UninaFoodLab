package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import entity.Adesione;
import entity.Allievo;
import entity.SessionePratica;

public class AdesioneImpl extends GenericImpl<Adesione> implements AdesioneDAO{

    @Override
    public void create(Adesione o) throws SQLException {
        if (!(o instanceof Adesione ade)) return;

        String sql = """
            INSERT INTO adesione (presenza, data_adesione, fk_allievo, fk_sessionepratica)
            VALUES (?, ?, ?, ?)
        """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setBoolean(1, ade.isPresenza());
            ps.setDate(2, Date.valueOf(ade.getDataAdesione()));
            ps.setInt(3, ade.getAllievo().getId());
            ps.setInt(4, ade.getSessione().getId());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    ade.setId(rs.getInt(1)); 
                }
            }
        }
    }

    @Override
    public Adesione read(int id) throws SQLException {
        String sql = "SELECT * FROM adesione WHERE id_adesione = ?";
        AllievoImpl allievoDAO = new AllievoImpl();

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    
                    Allievo allievo = allievoDAO.read(rs.getInt("fk_allievo"));

                    SessionePraticaImpl sessioneDAO = new SessionePraticaImpl();
                    SessionePratica sessione = sessioneDAO.read(rs.getInt("fk_sessionepratica"));

                    Adesione ade = new Adesione(
                        rs.getInt("id_adesione"),
                        allievo,
                        sessione
                    );
                    
                    ade.setDataAdesione(rs.getDate("data_adesione").toLocalDate());
                    
                    if (rs.getBoolean("presenza")) {
                        ade.confermaPresenza();
                    }
                    
                    return ade;
                }
            }
        }
        return null;
    }

    @Override
    public void update(Adesione o) throws SQLException {
        if (!(o instanceof Adesione ade)) return;

        String sql = "UPDATE adesione SET presenza = ?, data_adesione = ? WHERE id_adesione = ?";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
        	ps.setBoolean(1, ade.isPresenza());
            ps.setDate(2, Date.valueOf(ade.getDataAdesione()));
            ps.setInt(3, ade.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM adesione WHERE id_adesione = ?";
        Connection conn = getConnection(); 
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public List<Adesione> findAll() throws SQLException {
        List<Adesione> lista = new ArrayList<>();
        String sql = "SELECT * FROM adesione";

        AllievoImpl allievoDAO = new AllievoImpl();
        SessionePraticaImpl sessioneDAO = new SessionePraticaImpl();

        try (PreparedStatement ps = getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Allievo allievo = allievoDAO.read(rs.getInt("fk_allievo"));
                SessionePratica sessione = sessioneDAO.read(rs.getInt("fk_sessionepratica"));

                Adesione ade = new Adesione(
                    rs.getInt("id_adesione"),
                    allievo,
                    sessione
                );

                if (rs.getBoolean("presenza")) {
                    ade.confermaPresenza();
                }

                lista.add(ade);
            }
        }
        return lista;
    }
    
    public int countAdesioniBySessione(int idSessione) throws SQLException {
        String sql = "SELECT COUNT(*) FROM adesione WHERE fk_sessionepratica = ?";

        Connection conn = getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idSessione);

            try (ResultSet rs = ps.executeQuery()) {
            	if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
    
    public int countAdesioniByCorso(int idCorso) throws SQLException {

        String sql = """
            SELECT COUNT(*)
            FROM adesione a
            JOIN sessionepratica sp 
            ON a.fk_sessionepratica = sp.id_sessionepratica
            WHERE sp.fk_corso = ?
        """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {

            ps.setInt(1, idCorso);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return 0;
    }
    
    public void iscriviSessioneOnline(int idAllievo, int idSessione) throws SQLException {

        String sql = """
            INSERT INTO adesione (presenza, data_adesione, fk_allievo, fk_sessionepratica)
            VALUES (?, CURRENT_DATE, ?, ?)
        """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {

            ps.setBoolean(1, false);
            ps.setInt(2, idAllievo);
            ps.setInt(3, idSessione);

            ps.executeUpdate();
        }
    }
    

    public int countAdesioniByAllievoAndCorso(int idAllievo, int idCorso) throws SQLException {
        String sql = """
            SELECT COUNT(*) FROM adesione a
            JOIN sessionepratica sp ON a.fk_sessionepratica = sp.id_sessionepratica
            WHERE a.fk_allievo = ? AND sp.fk_corso = ?
        """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, idAllievo);
            ps.setInt(2, idCorso);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
    
    
    public int countAdesioniByAllievoAndSessioneOnline(int idAllievo, int idSessioneOnline) throws SQLException {
        String sql = "SELECT COUNT(*) FROM adesione WHERE fk_allievo = ? AND fk_sessioneonline = ?";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, idAllievo);
            ps.setInt(2, idSessioneOnline);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
    
    public int countPresenti() throws SQLException {

        String sql = "SELECT COUNT(*) FROM adesione WHERE presenza = true";
        int count = 0;

        try (PreparedStatement ps = getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        }
        return count;
    }
    
    public int countAssenti() throws SQLException {

        String sql = "SELECT COUNT(*) FROM adesione WHERE presenza = false";
        int count = 0;

        try (PreparedStatement ps = getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        }
        return count;
    }
    
    public int countBySessione(int idSessione) throws SQLException {

        String sql = "SELECT COUNT(*) FROM adesione WHERE fk_sessionepratica = ?";
        int count = 0;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, idSessione);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        }
        return count;
    }
}