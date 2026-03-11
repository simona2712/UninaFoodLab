package dao;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import database.DBConnection;
import entity.*;


public class SessionePraticaImpl extends GenericImpl<SessionePratica> implements SessionePraticaDAO{

    @Override
    public void create(SessionePratica o) throws SQLException {
        if (!(o instanceof SessionePratica)) return;
        SessionePratica s = (SessionePratica) o;

        String sql = """
            INSERT INTO sessione_pratica
            (durata, data, ora, fk_corso, laboratorio, max_partecipanti, utensili)
            VALUES (?,?,?,?,?,?,?)
        """;

        try (PreparedStatement ps = getConnection()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, s.getDurata());
            ps.setDate(2, Date.valueOf(s.getData()));
            ps.setTime(3, Time.valueOf(s.getOra()));
            ps.setInt(4, s.getCorso().getId());
            ps.setString(5, s.getLaboratorio());
            ps.setInt(6, s.getMaxPartecipanti());
            ps.setString(7, s.getUtensili());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    s.setId(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public SessionePratica read(int id) throws SQLException {

        String sql = "SELECT * FROM sessione_pratica WHERE id_sessionepratica=?";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    CorsoImpl corsoDAO = new CorsoImpl();
                    Corso corso = corsoDAO.read(rs.getInt("fk_corso"));

                    SessionePratica s = new SessionePratica(
                        rs.getInt("id_sessionepratica"),
                        rs.getInt("durata"),
                        rs.getDate("data").toLocalDate(),
                        rs.getTime("ora").toLocalTime(),
                        corso,
                        rs.getInt("max_partecipanti"),
                        rs.getString("laboratorio"),
                        rs.getString("utensili")
                    );
                    
                    RicettaImpl ricettaDAO = new RicettaImpl();
                    s.setRicette(ricettaDAO.findBySessionePratica(id));

                    return s;
                }
            }
        }

        return null;
    }

    @Override
    public void update(SessionePratica o) throws SQLException {
        if (!(o instanceof SessionePratica)) return;
        SessionePratica s = (SessionePratica) o;

        String sql = """
            UPDATE sessione_pratica
            SET durata=?, data=?, ora=?, fk_corso=?,
                laboratorio=?, max_partecipanti=?, utensili=?
            WHERE id_sessionepratica=?
        """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {

            ps.setInt(1, s.getDurata());
            ps.setDate(2, Date.valueOf(s.getData()));
            ps.setTime(3, Time.valueOf(s.getOra()));
            ps.setInt(4, s.getCorso().getId());
            ps.setString(5, s.getLaboratorio());
            ps.setInt(6, s.getMaxPartecipanti());
            ps.setString(7, s.getUtensili());
            ps.setInt(8, s.getId());

            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {

        String sql = "DELETE FROM sessione_pratica WHERE id_sessionepratica=?";
        
        Connection conn = getConnection(); 

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    
    public List<SessionePratica> findAll() throws SQLException {
        List<SessionePratica> lista = new ArrayList<>();

        String sql = "SELECT * FROM sessione_pratica";
        CorsoImpl corsoDAO = new CorsoImpl();

        try (PreparedStatement ps = getConnection().prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

               while (rs.next()) {
                   Corso corso = corsoDAO.read(rs.getInt("fk_corso"));

                   SessionePratica s = new SessionePratica(
                       rs.getInt("id_sessionepratica"),
                       rs.getInt("durata"),
                       rs.getDate("data").toLocalDate(),
                       rs.getTime("ora").toLocalTime(),
                       corso,
                       rs.getInt("max_partecipanti"),
                       rs.getString("laboratorio"),
                       rs.getString("utensili")
                   );

                   lista.add(s);
               }
           }
        
        return lista;
    }
    
    public void associaRicetta(int idSessione, int idRicetta) throws SQLException {
        String sql = "INSERT INTO svolge (fk_sessionepratica, fk_ricetta) VALUES (?, ?)";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, idSessione);
            ps.setInt(2, idRicetta);
            ps.executeUpdate();
        }
    }
    
    public List<SessionePratica> findFutureByCorso(int idCorso) throws SQLException {

        List<SessionePratica> lista = new ArrayList<>();

        String sql = """
            SELECT * 
            FROM sessione_pratica
            WHERE fk_corso = ? AND data > CURRENT_DATE
            ORDER BY data
        """;

        CorsoImpl corsoDAO = new CorsoImpl();

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {

            ps.setInt(1, idCorso);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Corso corso = corsoDAO.read(rs.getInt("fk_corso"));

                    SessionePratica s = new SessionePratica(
                            rs.getInt("id_sessionepratica"),
                            rs.getInt("durata"),
                            rs.getDate("data").toLocalDate(),
                            rs.getTime("ora").toLocalTime(),
                            corso,
                            rs.getInt("max_partecipanti"),
                            rs.getString("laboratorio"),
                            rs.getString("utensili")
                    );

                    lista.add(s);
                }
            }
        }

        return lista;
    }
    
    public List<SessionePratica> findByChef(int idChef) throws SQLException {
        List<SessionePratica> sessioni = new ArrayList<>();
        
        String sql = "SELECT sp.id, sp.durata, sp.data, sp.ora, sp.laboratorio, sp.utensili, sp.max_partecipanti, sp.id_corso " +
                     "FROM sessionepratica sp " +
                     "JOIN corso c ON sp.fk_corso = c.id_corso " +
                     "WHERE c.fk_chef = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idChef);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int durata = rs.getInt("durata");
                    LocalDate data = rs.getDate("data").toLocalDate();
                    LocalTime ora = rs.getTime("ora").toLocalTime();
                    String laboratorio = rs.getString("laboratorio");
                    String utensili = rs.getString("utensili");
                    int max = rs.getInt("max_partecipanti");
                    
                    int idCorso = rs.getInt("id_corso");
                    Corso corso = new CorsoImpl().read(idCorso);
                    
                    SessionePratica s = new SessionePratica(id, durata, data, ora, corso, max, utensili, laboratorio);
                    sessioni.add(s);
                }
            }
        }
        return sessioni;
    }
}