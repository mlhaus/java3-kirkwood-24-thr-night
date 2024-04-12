package edu.kirkwood.learnx.controllers;

import edu.kirkwood.learnx.data.CourseDAO;
import edu.kirkwood.learnx.models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/enroll")
public class EnrollmentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User activeUser = (User)session.getAttribute("activeUser");
        if(activeUser == null || !activeUser.getPrivileges().equals("student")) {
            resp.sendRedirect("courses");
            return;
        }
        int courseId = Integer.parseInt(req.getParameter("course"));
        if(CourseDAO.enroll(activeUser.getId(), courseId)) {
            session.setAttribute("flashMessageSuccess", "You're enrolled!");
        } else {
            session.setAttribute("flashMessageDanger", "Enrollment not successful");
        }
        resp.sendRedirect("student");
    }
}