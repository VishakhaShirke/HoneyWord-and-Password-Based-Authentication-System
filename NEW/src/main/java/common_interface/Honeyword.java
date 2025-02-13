package common_interface;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;

public class Honeyword {

    private static Connection con;
    private static final String[] characters = {"a", "s", "d", "f", "g", "h", "j", "k", "l", "q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "z", "x", "c", "v", "b", "n", "m"};

    public static void connectDatabase() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/xe";
        String username = "root";
        String password = "root";
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to Database...");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new SQLException("Database connection error: " + e.getMessage());
        }
    }

    public static void generateHoneywords(String originalPassword, String userId, HttpServletRequest request) {
        Set<String> honeyWordSet = new HashSet<>();
        Random rn = new Random();

        try (Connection connection = con;
             PreparedStatement stmt = connection.prepareStatement("INSERT INTO Honeywords (user_id, honeyword) VALUES (?, ?)")) {

            while (honeyWordSet.size() < 10) {
                StringBuilder word = new StringBuilder();
                String transformedPassword = transformPassword(originalPassword);
                honeyWordSet.add(transformedPassword);
                stmt.setString(1, getUserId(request));
                stmt.setString(2, transformedPassword);
                stmt.executeUpdate();
            }
            System.out.println("Generated and stored honeywords successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String transformPassword(String originalPassword) {
        char randomChar = getRandomCharacter();
        return originalPassword + randomChar;
    }

    private static char getRandomCharacter() {
        Random rn = new Random();
        return characters[rn.nextInt(characters.length)].charAt(0);
    }
    
    public static String getUserId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (String) session.getAttribute("userId");
    }



}
