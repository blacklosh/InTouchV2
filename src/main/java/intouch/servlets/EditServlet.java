package intouch.servlets;

import intouch.dto.UserDto;
import intouch.services.impl.SessionsManager;
import intouch.services.model.Session;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/edit")
public class EditServlet extends HttpServlet {
    private SessionsManager sessionsManager;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        sessionsManager = (SessionsManager) context.getAttribute("sessionsManager");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session session = sessionsManager.getSession(false, req, resp);
            UserDto user = (UserDto) session.getAttribute("user");
            req.setAttribute("user", user);
            req.getRequestDispatcher("edit.ftl").forward(req,resp);
    }
}