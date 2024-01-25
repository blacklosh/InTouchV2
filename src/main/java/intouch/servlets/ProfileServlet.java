package intouch.servlets;

import intouch.dto.UserDto;
import intouch.services.impl.SessionsManager;
import intouch.services.model.Session;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private SessionsManager sessionsManager;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        sessionsManager = (SessionsManager) context.getAttribute("sessionsManager");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("utf-8");
        Session session = sessionsManager.getSession(false, req, resp);
        if (session != null && session.getAttribute("user") != null) {
            UserDto user = (UserDto) session.getAttribute("user");
            req.setAttribute("user", user);
            req.getRequestDispatcher("profile.ftl").forward(req,resp);
        } else {
            resp.sendRedirect("signin");
        }
    }
}