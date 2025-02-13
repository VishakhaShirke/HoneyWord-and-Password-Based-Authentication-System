package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBC {

    private static Connection con;

    public static void connectDatabase() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/xe", "root", "root");
            System.out.println("Connected to Database...");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new SQLException("Database connection error: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        if (con == null || con.isClosed()) {
            connectDatabase();
        }
        return con;
    }

    public static void registerUser(String fullname, String username, String email, String password, String phone, String securityQuestion, String securityAnswer) {
        String updateString = "INSERT INTO Honeyword_user_data (username, fullname, email, password, phone, securityQuestion, securityAnswer) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement stmt = connection.prepareStatement(updateString)) {
                stmt.setString(1, username);
                stmt.setString(2, fullname);
                stmt.setString(3, email);
                stmt.setString(4, password);
                stmt.setString(5, phone);
                stmt.setString(6, securityQuestion);
                stmt.setString(7, securityAnswer);
                stmt.executeUpdate();
                connection.commit();

                System.out.println("User registered successfully");
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
                System.out.println("Error registering user: " + e.getMessage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database connection error: " + e.getMessage());
        }
    }
}
