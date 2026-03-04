package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import entity.Utilizzo;
import entity.Ingrediente;

public class UtilizzoImpl extends GenericImpl implements UtilizzoDAO{


    public void create(int idRicetta, Utilizzo u) throws SQLException {
        String sql = """
            INSERT INTO utilizzo (fk_ricetta, fk_ingrediente, quantita, unita_misura)
            VALUES (?, ?, ?, ?)
        """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, idRicetta);
            ps.setInt(2, u.getIngrediente().getId());
            ps.setDouble(3, u.getQuantita());
            ps.setString(4, u.getUnitaMisura());

            ps.executeUpdate();
        }
    }
    

    public List<Utilizzo> findByRicetta(int idRicetta) throws SQLException {
        List<Utilizzo> lista = new ArrayList<>();
        
        String sql = """
            SELECT u.*, i.nome, i.tipologia_conservazione, i.data_scadenza, i.calorie 
            FROM utilizzo u 
            JOIN ingrediente i ON u.fk_ingrediente = i.id_ingrediente 
            WHERE u.fk_ricetta = ?
        """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, idRicetta);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // 1. Ricostruiamo l'oggetto Ingrediente
                    Ingrediente ing = new Ingrediente(
                        rs.getInt("fk_ingrediente"),
                        rs.getString("nome"),
                        rs.getString("tipologia_conservazione"),
                        rs.getDate("data_scadenza").toLocalDate(),
                        rs.getDouble("calorie")
                    );

                    // 2. Creiamo l'oggetto Utilizzo
                    Utilizzo util = new Utilizzo(
                        ing,
                        rs.getDouble("quantita"),
                        rs.getString("unita_misura")
                    );
                    
                    lista.add(util);
                }
            }
        }
        return lista;
    }

    public void update(int idRicetta, Utilizzo u) throws SQLException {
        String sql = """
            UPDATE utilizzo 
            SET quantita = ?, unita_misura = ? 
            WHERE fk_ricetta = ? AND fk_ingrediente = ?
        """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setDouble(1, u.getQuantita());
            ps.setString(2, u.getUnitaMisura());
            ps.setInt(3, idRicetta);
            ps.setInt(4, u.getIngrediente().getId());
            ps.executeUpdate();
        }
    }
    
    
    @Override
    public void update(Object o) throws SQLException {
        throw new UnsupportedOperationException(
            "Per aggiornare un Utilizzo serve anche l'ID Ricetta. Usa update(int idRicetta, Utilizzo u)"
        );
    }
    

    public void delete(int idRicetta, int idIngrediente) throws SQLException {
        String sql = "DELETE FROM utilizzo WHERE fk_ricetta = ? AND fk_ingrediente = ?";
        
        Connection conn = getConnection(); 

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idRicetta);
            ps.setInt(2, idIngrediente);
            ps.executeUpdate();
        }
    }

 
    @Override public void create(Object o) throws SQLException { 
    	throw new UnsupportedOperationException(
                "Errore: Utilizzo non può essere creato da solo. Usa create(int idRicetta, Utilizzo u)"
            );
    }
    @Override public Object read(int id) throws SQLException { 
    	throw new UnsupportedOperationException(
                "Errore: Utilizzo non ha un ID univoco. Usa findByRicetta(int idRicetta)"
            );
    }
    @Override public void delete(int id) throws SQLException { 
    	throw new UnsupportedOperationException(
                "Errore: Per eliminare un utilizzo servono idRicetta e idIngrediente. Usa delete(int idR, int idI)"
            );
    }
}