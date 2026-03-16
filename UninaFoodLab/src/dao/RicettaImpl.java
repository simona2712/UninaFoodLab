package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import database.DBConnection;
import entity.Ricetta;

public class RicettaImpl extends GenericImpl<Ricetta> implements RicettaDAO{

    @Override
    public void create(Ricetta o) throws SQLException {
        if (!(o instanceof Ricetta r)) return;

        String sql = """
            INSERT INTO ricetta (durata, descrizione, preparazione, allergeni)
            VALUES (?, ?, ?::tipo_prep, ?::tipo_allergeni)
        """;
        

        try (PreparedStatement ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, r.getDurata());
            ps.setString(2, r.getDescrizione());
            ps.setString(3, r.getPreparazione());
            
            if (r.getAllergeni() != null && !r.getAllergeni().isEmpty()) {
                ps.setString(4, r.getAllergeni().get(0));
            } else {
                ps.setNull(4, Types.VARCHAR);
            }

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    r.setId(rs.getInt(1));
                }
            }
        }
        UtilizzoImpl uDAO = new UtilizzoImpl();
        for (entity.Utilizzo u : r.getIngredienti()) {
            uDAO.create(r.getId(), u);
        }
    }

    @Override
    public Ricetta read(int id) throws SQLException {
        String sql = "SELECT * FROM ricetta WHERE id_ricetta = ?";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Ricetta r = new Ricetta(
                        rs.getInt("id_ricetta"),
                        rs.getInt("durata"),
                        rs.getString("descrizione"),
                        rs.getString("preparazione")
                    );

                    String allergeniStr = rs.getString("allergeni");
                    if (allergeniStr != null && !allergeniStr.equals("{}")) {
                        String pulita = allergeniStr.replace("{", "").replace("}", "");
                        List<String> listaAllergeni = Arrays.asList(pulita.split(","));
                        r.setAllergeni(listaAllergeni);
                    } else {
                        r.setAllergeni(new ArrayList<>());
                    }

                    UtilizzoImpl uDAO = new UtilizzoImpl();
                    r.setIngredienti(uDAO.findByRicetta(id));

                    return r;
                }
            }
        }
        return null;
    }

    @Override
    public void update(Ricetta o) throws SQLException {
        if (!(o instanceof Ricetta r)) return;

        String sql = """
            UPDATE ricetta 
            SET durata = ?, descrizione = ?, preparazione = ? 
            WHERE id_ricetta = ?
        """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, r.getDurata());
            ps.setString(2, r.getDescrizione());
            ps.setString(3, r.getPreparazione());
            ps.setInt(4, r.getId());

            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        
        String sql = "DELETE FROM ricetta WHERE id_ricetta = ?";
        
        Connection conn = getConnection(); 

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public List<Ricetta> findAll() throws SQLException {
        List<Ricetta> lista = new ArrayList<>();
        String sql = "SELECT * FROM ricetta";

        try (PreparedStatement ps = getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Ricetta r = new Ricetta(
                    rs.getInt("id_ricetta"),
                    rs.getInt("durata"),
                    rs.getString("descrizione"),
                    rs.getString("preparazione")
                );

                String allergeniStr = rs.getString("allergeni");
                if (allergeniStr != null && !allergeniStr.equals("{}")) {
                    String pulita = allergeniStr.replace("{", "").replace("}", "");
                    List<String> listaAllergeni = Arrays.asList(pulita.split(","));
                    r.setAllergeni(listaAllergeni);
                } else {
                    r.setAllergeni(new ArrayList<>());
                }

                lista.add(r);
            }
        }
        return lista;
    }
    
    public List<Ricetta> findBySessionePratica(int idSessione) throws SQLException {
        List<Ricetta> lista = new ArrayList<>();
        String sql = """
            SELECT r.* FROM ricetta r 
            JOIN svolge s ON r.id_ricetta = s.fk_ricetta 
            WHERE s.fk_sessionepratica = ?
        """;
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, idSessione);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Ricetta r = new Ricetta(
                        rs.getInt("id_ricetta"),
                        rs.getInt("durata"),
                        rs.getString("descrizione"),
                        rs.getString("preparazione")
                    );
                    
                    UtilizzoImpl uDAO = new UtilizzoImpl();
                    r.setIngredienti(uDAO.findByRicetta(r.getId()));
                    
                    lista.add(r);
                }
            }
        }
        return lista;
    }
    
    public void associaASessionePratica(int idRicetta, int idSessionePratica) throws SQLException {

        if (idRicetta <= 0 || idSessionePratica <= 0)
            throw new IllegalArgumentException("ID non validi");

        String checkSql = """
            SELECT 1 FROM svolge 
            WHERE fk_ricetta = ? AND fk_sessionepratica = ?
        """;

        try (PreparedStatement check = getConnection().prepareStatement(checkSql)) {

            check.setInt(1, idRicetta);
            check.setInt(2, idSessionePratica);

            try (ResultSet rs = check.executeQuery()) {
                if (rs.next()) {
                    return;
                }
            }
        }

        String insertSql = """
            INSERT INTO svolge (fk_ricetta, fk_sessionepratica)
            VALUES (?, ?)
        """;

        try (PreparedStatement ps = getConnection().prepareStatement(insertSql)) {
            ps.setInt(1, idRicetta);
            ps.setInt(2, idSessionePratica);
            ps.executeUpdate();
        }
    }
    
    public void iscriviSessioneOnline(int idAllievo, int idSessione) throws SQLException {

        String sql = """
            INSERT INTO adesione (presenza, data_adesione, fk_allievo, fk_sessioneonline)
            VALUES (?, CURRENT_DATE, ?, ?)
        """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {

            ps.setBoolean(1, false);
            ps.setInt(2, idAllievo);
            ps.setInt(3, idSessione);

            ps.executeUpdate();
        }
    }
    
    public void aggiungiIngrediente(int idRicetta, int idIngrediente, double quantita, String unita) throws SQLException {

        if (idRicetta <= 0 || idIngrediente <= 0)
            throw new IllegalArgumentException("ID non validi");

        String checkSql = """
            SELECT 1 FROM utilizzo
            WHERE fk_ricetta = ? AND fk_ingrediente = ?
        """;

        try (PreparedStatement check = getConnection().prepareStatement(checkSql)) {

            check.setInt(1, idRicetta);
            check.setInt(2, idIngrediente);

            try (ResultSet rs = check.executeQuery()) {
                if (rs.next()) {
                    return;
                }
            }
        }

        String insertSql = """
            INSERT INTO utilizzo (fk_ricetta, fk_ingrediente, quantita, unita_misura)
            VALUES (?, ?, ?, ?)
        """;

        try (PreparedStatement ps = getConnection().prepareStatement(insertSql)) {

            ps.setInt(1, idRicetta);
            ps.setInt(2, idIngrediente);
            ps.setDouble(3, quantita);
            ps.setString(4, unita);

            ps.executeUpdate();
        }
    }
    

    public List<Ricetta> findByCorso(int idCorso) throws SQLException {
        List<Ricetta> ricette = new ArrayList<>();
        
        String sql = """
            SELECT DISTINCT r.id_ricetta, r.durata, r.descrizione, r.preparazione, r.allergeni 
            FROM ricetta r 
            JOIN svolge s ON r.id_ricetta = s.fk_ricetta 
            JOIN sessionepratica sp ON s.fk_sessionepratica = sp.id_sessionepratica 
            WHERE sp.fk_corso = ?
        """;
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idCorso);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Ricetta r = new Ricetta(
                        rs.getInt("id_ricetta"),
                        rs.getInt("durata"),
                        rs.getString("descrizione"),
                        rs.getString("preparazione")
                    );
                    
                    String allergeniStr = rs.getString("allergeni"); 
                    if (allergeniStr != null && !allergeniStr.equals("{}")) {
                        String pulita = allergeniStr.replace("{", "").replace("}", "");
                        List<String> listaAllergeni = Arrays.asList(pulita.split(","));
                        r.setAllergeni(listaAllergeni);
                        System.out.println("DEBUG: Trovati allergeni per " + r.getId() + ": " + listaAllergeni);
                    } else {
                        r.setAllergeni(new ArrayList<>());
                    }
                    
                    UtilizzoImpl uDAO = new UtilizzoImpl();
                    r.setIngredienti(uDAO.findByRicetta(r.getId()));
                    
                    ricette.add(r);
                }
            }
        }
        return ricette;
    }
    
    public Map<String,Integer> getTopRicetteUsate() throws SQLException {

        Map<String,Integer> result = new HashMap<>();

        String sql = """
            SELECT r.descrizione, COUNT(*) as utilizzi
            FROM svolge s
            JOIN ricetta r ON s.fk_ricetta = r.id_ricetta
            GROUP BY r.descrizione
            ORDER BY utilizzi DESC
            LIMIT 5
        """;

        try(PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while(rs.next()) {
                String nome = rs.getString("nome");
                int count = rs.getInt("utilizzi");

                result.put(nome, count);
            }
        }

        return result;
    }
    
    public double getMediaRicettePerSessione() throws SQLException {
        String sql = """
            SELECT AVG(cnt)
            FROM (
                SELECT COUNT(*) AS cnt
                FROM Svolge
                GROUP BY FK_SessionePratica
            ) t
        """;
        try(PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            if(rs.next()) {
                return rs.getDouble(1);
            }
        }
        return 0;
    }

    public int getMaxRicettePerSessione() throws SQLException {
        String sql = """
            SELECT MAX(cnt)
            FROM (
                SELECT COUNT(*) AS cnt
                FROM Svolge
                GROUP BY FK_SessionePratica
            ) t
        """;
        try(PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            if(rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public int getMinRicettePerSessione() throws SQLException {
        String sql = """
            SELECT MIN(cnt)
            FROM (
                SELECT COUNT(*) AS cnt
                FROM Svolge
                GROUP BY FK_SessionePratica
            ) t
        """;
        try(PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            if(rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
}