package edu.kirkwood.learnx.controllers;

import edu.kirkwood.learnx.data.UserDAO;
import edu.kirkwood.learnx.models.User;
import edu.kirkwood.shared.CommunicationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("pageTitle", "Sign up for an account");
        req.getRequestDispatcher("WEB-INF/learnx/signup.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("inputEmail1");
        String password1 = req.getParameter("inputPassword1");
        String password2 = req.getParameter("inputPassword2");
        String[] terms = req.getParameterValues("checkbox-1");
        if(terms == null) {
            terms = new String[]{"0"};
        }
        if(password1 == null) {
            password1 = "";
        }

        Map<String, String> results = new HashMap<>();
        results.put("email", email);
        results.put("password1", password1);
        results.put("password2", password2);
        results.put("terms", terms[0]);

        User user = new User();
        try {
            user.setEmail(email);
        } catch(IllegalArgumentException e) {
            results.put("emailError", e.getMessage());
        }
        User userFromDatabase = UserDAO.get(email);
        if(userFromDatabase != null) {
            results.put("emailError", "A user with that email address already exists.");
        }
        try {
            user.setPassword(password1.toCharArray());
        } catch(IllegalArgumentException e) {
            results.put("password1Error", e.getMessage());
        }
        if(password2.equals("")) {
            results.put("password2Error", "This input is required");
        }
        if(!password1.equals(password2)) {
            results.put("password2Error", "Passwords don't match");
        }
        if(terms == null || !terms[0].equals("agree")){
            results.put("termsOfServiceError", "You must agree to our terms of service");
        }

        if (!results.containsKey("emailError") && !results.containsKey("password1Error")
                && !results.containsKey("password2Error") && !results.containsKey("termsOfServiceError")
        ) {
            List<String> twoFactorInfo = UserDAO.add(user);
            if(!twoFactorInfo.isEmpty()) {
                // Send user an email
                String code = twoFactorInfo.get(0);
                CommunicationService.sendNewUserEmail(code, email);
                // To do: Display error if the email could not be sent
                HttpSession session = req.getSession(); // Get the existing session
                session.invalidate(); // Remove all existing session data
                session = req.getSession(); // Create new session
                session.setAttribute("code", twoFactorInfo.get(0));
                session.setAttribute("email", email);
                resp.sendRedirect("confirm");
                return;
            }
            // To do: Display error saying "Could not add user" if twoFactorInfo is empty
            
        }

        req.setAttribute("results", results);
        req.setAttribute("pageTitle", "Sign up for an account");
        req.getRequestDispatcher("WEB-INF/learnx/signup.jsp").forward(req, resp);
    }
}
