package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import entity.Corso;
import entity.Chef;

public class CorsoImpl extends GenericImpl implements CorsoDAO{

    @Override
    public void create(Object o) throws SQLException {
        if (!(o instanceof Corso)) return;
        Corso c = (Corso) o;

        String sql = """
                INSERT INTO corso (nome, argomento, data_inizio, data_fine, frequenza, fk_chef) 
                VALUES (?, ?, ?, ?, ?, ?)
            """;
        
        try (PreparedStatement ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, c.getNome());
            ps.setString(2, c.getArgomento());
            ps.setDate(3, Date.valueOf(c.getDataInizio()));
            ps.setDate(4, Date.valueOf(c.getDataFine()));
            ps.setString(5, c.getFrequenza());
            ps.setInt(6, c.getChef().getId());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
            	c.setId(rs.getInt(1));
            }
        } 
    }

    @Override
    public Corso read(int id) throws SQLException {
    	
        String sql = "SELECT * FROM corso WHERE id_corso=?";
        
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ChefImpl chefDAO = new ChefImpl();
                Chef chef = chefDAO.read(rs.getInt("fk_chef"));

                return new Corso(
                        rs.getInt("id_corso"),
                        rs.getString("nome"),
                        rs.getString("argomento"),
                        rs.getDate("data_inizio").toLocalDate(),
                        rs.getDate("data_fine").toLocalDate(),
                        rs.getString("frequenza"),
                        chef
                );
            }
        }
        return null;
    }

    @Override
    public void update(Object o) throws SQLException {
        if (!(o instanceof Corso)) return;
        Corso c = (Corso) o;

        String sql = """
                UPDATE corso 
                SET nome=?, argomento=?, data_inizio=?, data_fine=?, frequenza=?, fk_chef=? 
                WHERE id_corso=?
            """;
        
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, c.getNome());
            ps.setString(2, c.getArgomento());
            ps.setDate(3, Date.valueOf(c.getDataInizio()));
            ps.setDate(4, Date.valueOf(c.getDataFine()));
            ps.setString(5, c.getFrequenza());
            ps.setInt(6, c.getChef().getId());
            ps.setInt(7, c.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM corso WHERE id_corso=?";
        
        Connection conn = getConnection(); 

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public List<Corso> findAll() throws SQLException {
        List<Corso> lista = new ArrayList<>();
        String sql = "SELECT * FROM corso";
        
        ChefImpl chefDAO = new ChefImpl();
        
        try (PreparedStatement ps = getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Chef chef = chefDAO.read(rs.getInt("fk_chef"));
                
                Corso c = new Corso(
                        rs.getInt("id_corso"),
                        rs.getString("nome"),
                        rs.getString("argomento"),
                        rs.getDate("data_inizio").toLocalDate(),
                        rs.getDate("data_fine").toLocalDate(),
                        rs.getString("frequenza"),
                        chef
                );
                lista.add(c);
            }
        }
        return lista;
    }
    
    public List<Corso> findByArgomento(String argomento) throws SQLException {
        List<Corso> lista = new ArrayList<>();
        String sql = "SELECT * FROM corso WHERE argomento = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, argomento);

            try (ResultSet rs = ps.executeQuery()) {
                ChefImpl chefDAO = new ChefImpl();

                while (rs.next()) {
                    Chef chef = chefDAO.read(rs.getInt("fk_chef"));

                    lista.add(new Corso(
                            rs.getInt("id_corso"),
                            rs.getString("nome"),
                            rs.getString("argomento"),
                            rs.getDate("data_inizio").toLocalDate(),
                            rs.getDate("data_fine").toLocalDate(),
                            rs.getString("frequenza"),
                            chef
                    ));
                }
            }
        }
        return lista;
    }
    
    public List<Corso> findCorsiByAllievo(int idAllievo) throws SQLException {
        List<Corso> lista = new ArrayList<>();
        
        String sql = """
            SELECT c.* FROM corso c 
            JOIN segue s ON c.id_corso = s.fk_corso 
            WHERE s.fk_allievo = ?
        """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, idAllievo);
            try (ResultSet rs = ps.executeQuery()) {
                ChefImpl chefDAO = new ChefImpl();

                while (rs.next()) {
                    
                    int idChef = rs.getInt("fk_chef");
                    Chef chef = (Chef) chefDAO.read(idChef);

                    Corso c = new Corso(
                        rs.getInt("id_corso"),
                        rs.getString("nome"),
                        rs.getString("argomento"),
                        rs.getDate("data_inizio").toLocalDate(),
                        rs.getDate("data_fine").toLocalDate(),
                        rs.getString("frequenza"),
                        chef
                    );
                    lista.add(c);
                }
            }
        }
        return lista;
    }
    
}