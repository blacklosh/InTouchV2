package intouch.servlets;

import intouch.dto.UserDto;
import intouch.model.User;
import intouch.repository.impl.UsersRepositoryImpl;
import intouch.services.UsersService;
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
import java.sql.Connection;
import java.util.UUID;

@WebServlet(value = "/delete")
public class DeleteServlet extends HttpServlet {
    private SessionsManager sessionsManager;
    private UsersService usersService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        sessionsManager = (SessionsManager) context.getAttribute("sessionsManager");
        usersService = (UsersService) context.getAttribute("usersService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session session = sessionsManager.getSession(false, req, resp);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect("signin");
            return;
        }
        UserDto userDto = (UserDto) session.getAttribute("user");
        usersService.delete(userDto.getId());
        session.removeAttribute("user");
        resp.sendRedirect("signin");
    }
}