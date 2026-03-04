package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import entity.Ricetta;

public class RicettaDAO extends GenericDAO {

    @Override
    public void create(Object o) throws SQLException {
        if (!(o instanceof Ricetta r)) return;

        String sql = """
            INSERT INTO ricetta (durata, descrizione, preparazione, allergeni)
            VALUES (?, ?, ?, ?)
        """;
        

        try (PreparedStatement ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, r.getDurata());
            ps.setString(2, r.getDescrizione());
            ps.setString(3, r.getPreparazione());
            ps.setString(4, String.join(", ", r.getAllergeni()));

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    r.setId(rs.getInt(1));
                }
            }
        }
        UtilizzoDAO uDAO = new UtilizzoDAO();
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
                    if (allergeniStr != null && !allergeniStr.isEmpty()) {
                        List<String> listaAllergeni = Arrays.stream(allergeniStr.split(","))
                                                            .map(String::trim)
                                                            .collect(Collectors.toList());
                        r.setAllergeni(listaAllergeni);
                    }

                    UtilizzoDAO uDAO = new UtilizzoDAO();
                    r.setIngredienti(uDAO.findByRicetta(id));

                    return r;
                }
            }
        }
        return null;
    }

    @Override
    public void update(Object o) throws SQLException {
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
                    
                    UtilizzoDAO uDAO = new UtilizzoDAO();
                    r.setIngredienti(uDAO.findByRicetta(r.getId()));
                    
                    lista.add(r);
                }
            }
        }
        return lista;
    }
}