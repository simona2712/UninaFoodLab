package database;

import java.sql.*;

public class DBConnection {
	
	private static Connection connection = null;
	
	private DBConnection() {
		// Costruttore privato: impedisce istanziazione
    }
	
	public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Parametri di connessione
                String url = "jdbc:postgresql://localhost:5432/uninafoodlab";
                String user = "tuo_utente";
                String password = "tua_password";

                // Carica il driver
                Class.forName("org.postgresql.Driver");

                // Crea la connessione
                connection = DriverManager.getConnection(url, user, password);

            } catch (ClassNotFoundException e) {
                System.err.println("Driver JDBC non trovato");
                e.printStackTrace();
            } catch (SQLException e) {
                System.err.println("Errore nella connessione al DB");
                e.printStackTrace();
                throw e;
            }
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
