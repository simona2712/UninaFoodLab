package dao;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import database.DBConnection;
import entity.*;


public class SessioneOnlineImpl extends GenericImpl<SessioneOnline> implements SessioneOnlineDAO{

    @Override
    public void create(SessioneOnline o) throws SQLException {
        if (!(o instanceof SessioneOnline)) return;
        SessioneOnline s = (SessioneOnline) o;

        String sql = """
            INSERT INTO sessioneonline
            (durata, data, ora, fk_corso, linkriunione, max_partecipanti)
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

        String sql = "SELECT * FROM sessioneonline WHERE id_sessioneonline=?";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                CorsoImpl corsoDAO = new CorsoImpl();
                Corso corso = corsoDAO.read(rs.getInt("fk_corso"));

                return new SessioneOnline(
                        rs.getInt("id_sessioneonline"),
                        rs.getInt("durata"),
                        rs.getDate("data").toLocalDate(),
                        rs.getTime("ora").toLocalTime(),
                        corso,
                        rs.getString("linkriunione"),
                        rs.getInt("max_partecipanti")
                );
            }
        }

        return null;
    }

    @Override
    public void update(SessioneOnline o) throws SQLException {
        if (!(o instanceof SessioneOnline)) return;
        SessioneOnline s = (SessioneOnline) o;

        String sql = """
            UPDATE sessioneonline
            SET durata=?, data=?, ora=?, fk_corso=?,
                linkriunione=?, max_partecipanti=?
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

        String sql = "DELETE FROM sessioneonline WHERE id_sessioneonline=?";
        
        Connection conn = getConnection(); 

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    
    public List<SessioneOnline> findAll() throws SQLException {
        List<SessioneOnline> lista = new ArrayList<>();

        String sql = "SELECT * FROM sessioneonline";
        
        CorsoImpl corsoDAO = new CorsoImpl();

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
                       rs.getString("linkriunione"),
                       rs.getInt("max_partecipanti")
                   );
                   
                   lista.add(s);
               }
           }
           return lista;
    	}
    
    public List<SessioneOnline> findByCorso(int idCorso) throws SQLException {
        List<SessioneOnline> lista = new ArrayList<>();
        String sql = "SELECT * FROM sessioneonline WHERE fk_corso = ? ORDER BY data ASC, ora ASC";

        CorsoDAO corsoDAO = new CorsoImpl();
        Corso corso = (Corso) corsoDAO.read(idCorso);

        if (corso == null) return lista;

        Connection conn = getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCorso);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SessioneOnline s = new SessioneOnline(
                        rs.getInt("id_sessioneonline"),
                        rs.getInt("durata"),
                        rs.getDate("data").toLocalDate(),
                        rs.getTime("ora").toLocalTime(),
                        corso,
                        rs.getString("linkriunione"),
                        rs.getInt("max_partecipanti")
                    );
                    lista.add(s);
                }
            }
        }
        return lista;
    }
    
    public List<SessioneOnline> findFutureByCorso(int idCorso) throws SQLException {

        List<SessioneOnline> lista = new ArrayList<>();

        String sql = """
            SELECT * 
            FROM sessioneonline
            WHERE fk_corso = ?
            AND data >= CURRENT_DATE
            ORDER BY data ASC, ora ASC
        """;

        CorsoDAO corsoDAO = new CorsoImpl();
        Corso corso = corsoDAO.read(idCorso);

        if (corso == null) return lista;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {

            ps.setInt(1, idCorso);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    SessioneOnline s = new SessioneOnline(
                        rs.getInt("id_sessioneonline"),
                        rs.getInt("durata"),
                        rs.getDate("data").toLocalDate(),
                        rs.getTime("ora").toLocalTime(),
                        corso,
                        rs.getString("linkriunione"),
                        rs.getInt("max_partecipanti")
                    );

                    lista.add(s);
                }
            }
        }

        return lista;
    }
    
    public List<SessioneOnline> findByChef(int idChef) throws SQLException {
        List<SessioneOnline> sessioni = new ArrayList<>();
        
        String sql = "SELECT so.id_sessioneonline, so.durata, so.data, so.ora, so.linkriunione, so.max_partecipanti, so.fk_corso " +
                     "FROM sessioneonline so " +
                     "JOIN corso c ON so.fk_corso = c.id_corso " +
                     "WHERE c.fk_chef = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idChef);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id_sessioneonline");
                    int durata = rs.getInt("durata");
                    LocalDate data = rs.getDate("data").toLocalDate();
                    LocalTime ora = rs.getTime("ora").toLocalTime();
                    String link = rs.getString("linkriunione");
                    int max = rs.getInt("max_partecipanti");
                    
                    int idCorso = rs.getInt("fk_corso");
                    Corso corso = new CorsoImpl().read(idCorso);
                    
                    SessioneOnline s = new SessioneOnline(id, durata, data, ora, corso, link, max);
                    sessioni.add(s);
                }
            }
        }
        return sessioni;
    }
    
    public int countSessioniOnline() throws SQLException {

        String sql = "SELECT COUNT(*) FROM sessioneonline";
        try(PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            if(rs.next()) {
                return rs.getInt(1);
            }
        }

        return 0;
    }
}