package intouch.servlets;

import intouch.dto.SignInForm;
import intouch.dto.UserDto;
import intouch.exceptions.InTouchException;
import intouch.services.AuthorizationService;
import intouch.services.model.Session;
import intouch.services.impl.SessionsManager;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet(value = "/signin", loadOnStartup = 1)
public class SignInServlet extends HttpServlet {
    private AuthorizationService authorizationService;
    private SessionsManager sessionsManager;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        authorizationService = (AuthorizationService) context.getAttribute("authorizationService");
        sessionsManager = (SessionsManager) context.getAttribute("sessionsManager");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("utf-8");
        Session session = sessionsManager.getSession(false, req, resp);
        if (session != null && session.getAttribute("user") != null) {
            resp.sendRedirect("profile");
        } else {
            req.setAttribute("err",req.getParameter("err"));
            req.getRequestDispatcher("signin.ftl").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("utf-8");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        SignInForm signInForm = SignInForm.builder()
                .email(email)
                .password(password)
                .build();
        try {
            UserDto userDto = authorizationService.signIn(signInForm);
            Session session = sessionsManager.getSession(true, req, resp);
            session.setAttribute("user", userDto);
            resp.sendRedirect("profile");
        } catch (InTouchException e) {
            resp.sendRedirect("signin?err=" + e.getMessage());
        }
    }
}