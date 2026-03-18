package database;

import java.sql.*;

public class DBConnection {
    private static Connection connection = null;

    private DBConnection() {}

    public static synchronized Connection getConnection() throws SQLException {

        if (connection == null || connection.isClosed()) {
            String url = "jdbc:postgresql://localhost:5432/uninafoodlab";
            String user = "postgres";
            String password = "simona";

            try {
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                System.err.println("Errore di connessione al database!");
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
                System.out.println("Connessione al DB chiusa con successo.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}