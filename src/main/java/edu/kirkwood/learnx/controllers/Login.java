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

@WebServlet("/login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String redirect = req.getParameter("redirect");
        if(redirect != null) {
            req.setAttribute("redirect", redirect);
        }
        req.setAttribute("pageTitle", "Log in to your account");
        req.getRequestDispatcher("WEB-INF/learnx/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("inputEmail1");
        String password = req.getParameter("inputPassword1");
        String[] rememberMe = req.getParameterValues("checkbox-1");
        String redirect = req.getParameter("redirect");

        Map<String, String> results = new HashMap<>();
        results.put("email", email);
        results.put("password1", password);
        if(rememberMe != null && rememberMe[0].equals("remember")) {
            results.put("remember", "remember");
        }

        User userFromDatabase = UserDAO.get(email);
        if(userFromDatabase == null) {
            // No user found with that email
            results.put("loginFail", "That email/password combination is not correct");
        } else {
            if(!BCrypt.checkpw(password, String.valueOf(userFromDatabase.getPassword()))) {
                // Incorrect password
                results.put("loginFail", "That email/password combination is not correct");
            } else {
                if(!userFromDatabase.getStatus().equals("active")) {
                    // User is inactive or locked
                    results.put("loginFail", "Cannot log in. Account status: " + userFromDatabase.getStatus());
                } else {
                    // SUCCESS!
                    userFromDatabase.setLast_logged_in(Instant.now().atOffset(ZoneOffset.UTC).toInstant());
                    UserDAO.update(userFromDatabase);
                    userFromDatabase.setPassword(null);
                    HttpSession session = req.getSession();
                    session.invalidate();
                    session = req.getSession();
                    
                    // Did user check the Remember me box?
                    if(rememberMe != null && rememberMe[0].equals("remember")) {
                        session.setMaxInactiveInterval(30 * 24 * 60 * 60);
                    }
                    
                    
                    session.setAttribute("activeUser", userFromDatabase);
                    session.setAttribute("flashMessageSuccess", "Welcome back!");
                    if(redirect != null) {
                        resp.sendRedirect(redirect);
                    } else {
                        resp.sendRedirect("learnx");
                    }
                    return;
                }
            }
        }

        req.setAttribute("results", results);
        req.setAttribute("pageTitle", "Log in to your account");
        req.getRequestDispatcher("WEB-INF/learnx/login.jsp").forward(req, resp);
    }
}
