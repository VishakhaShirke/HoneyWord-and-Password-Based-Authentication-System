package common_interface;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import database.JDBC; 

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            Honeyword.connectDatabase();
            if ("Register".equals(action)) {
                String fullname = request.getParameter("fullname");
                String username = request.getParameter("username");
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                String phone = request.getParameter("phone");
                String securityQuestionId = request.getParameter("securityQuestion");
                String securityAnswer = request.getParameter("securityAnswer");
                String securityQuestion = getSecurityQuestionText(securityQuestionId);

                try {
                    String userId = Honeyword.getUserId(request); 
                    Honeyword.generateHoneywords(password, userId, request); 
                    JDBC.registerUser(fullname, username, email, password, phone, securityQuestion, securityAnswer);
                    response.sendRedirect("registration-success.html");
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendRedirect("registration-failure.html");
                }
            } else {
                response.sendRedirect("index.html");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("db-error.html");
        }
    }

    private String getSecurityQuestionText(String id) {
        if (id == null) {
            return "";
        }

        switch (id) {
            case "1":
                return "color";
            case "2":
                return "city_born";
            case "3":
                return "school";
            default:
                return "";
        }
    }
}
