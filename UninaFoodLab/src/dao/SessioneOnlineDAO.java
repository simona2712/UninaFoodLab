package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import entity.*;


public class SessioneOnlineDAO extends GenericDAO {

    @Override
    public void create(Object o) throws SQLException {
        if (!(o instanceof SessioneOnline)) return;
        SessioneOnline s = (SessioneOnline) o;

        String sql = """
            INSERT INTO sessione_online
            (durata, data, ora, fk_corso, link_riunione, max_partecipanti)
            VALUES (?,?,?,?,?,?)
        """;

        try (PreparedStatement ps = getConnection()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, s.getDurata());
            ps.setDate(2, Date.valueOf(s.getData()));
            ps.setTime(3, Time.valueOf(s.getOra()));
            ps.setInt(4, s.getCorso().getId());
            ps.setString(5, s.getLinkRiunione());
            ps.setInt(6, s.getMaxPartecipanti());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    s.setId(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public SessioneOnline read(int id) throws SQLException {

        String sql = "SELECT * FROM sessione_online WHERE id_sessioneonline=?";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                CorsoDAO corsoDAO = new CorsoDAO();
                Corso corso = corsoDAO.read(rs.getInt("fk_corso"));

                return new SessioneOnline(
                        rs.getInt("id_sessioneonline"),
                        rs.getInt("durata"),
                        rs.getDate("data").toLocalDate(),
                        rs.getTime("ora").toLocalTime(),
                        corso,
                        rs.getString("link_riunione"),
                        rs.getInt("max_partecipanti")
                );
            }
        }

        return null;
    }

    @Override
    public void update(Object o) throws SQLException {
        if (!(o instanceof SessioneOnline)) return;
        SessioneOnline s = (SessioneOnline) o;

        String sql = """
            UPDATE sessione_online
            SET durata=?, data=?, ora=?, fk_corso=?,
                link_riunione=?, max_partecipanti=?
            WHERE id_sessioneonline=?
        """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {

            ps.setInt(1, s.getDurata());
            ps.setDate(2, Date.valueOf(s.getData()));
            ps.setTime(3, Time.valueOf(s.getOra()));
            ps.setInt(4, s.getCorso().getId());
            ps.setString(5, s.getLinkRiunione());
            ps.setInt(6, s.getMaxPartecipanti());
            ps.setInt(7, s.getId());

            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {

        String sql = "DELETE FROM sessione_online WHERE id_sessioneonline=?";
        
        Connection conn = getConnection(); 

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    
    public List<SessioneOnline> findAll() throws SQLException {
        List<SessioneOnline> lista = new ArrayList<>();

        String sql = "SELECT * FROM sessione_online";
        
        CorsoDAO corsoDAO = new CorsoDAO();

        try (PreparedStatement ps = getConnection().prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

               while (rs.next()) {
                   // Recupero i dati del corso per ogni riga
                   Corso corso = corsoDAO.read(rs.getInt("fk_corso"));

                   // Costruzione dell'oggetto sessione
                   SessioneOnline s = new SessioneOnline(
                       rs.getInt("id_sessioneonline"),
                       rs.getInt("durata"),
                       rs.getDate("data").toLocalDate(),
                       rs.getTime("ora").toLocalTime(),
                       corso,
                       rs.getString("link_riunione"),
                       rs.getInt("max_partecipanti")
                   );
                   
                   lista.add(s);
               }
           }
           return lista;
    	}
}