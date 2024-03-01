package edu.kirkwood.learnx.controllers;

import edu.kirkwood.learnx.data.UserDAO;
import edu.kirkwood.learnx.models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/password-reset")
public class PasswordReset extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("pageTitle", "Reset your password");
        req.getRequestDispatcher("WEB-INF/learnx/password-reset.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("inputEmail1");
        
        Map<String, String> results = new HashMap<>();
        results.put("email", email);

        // To do: write an if statement if email was sent 
        UserDAO.passwordReset(email, req);
        results.put("passwordResetMsg", "If there is an account found, we will send an email");

        req.setAttribute("results", results);
        req.setAttribute("pageTitle", "Reset your password");
        req.getRequestDispatcher("WEB-INF/learnx/password-reset.jsp").forward(req, resp);
    }
}