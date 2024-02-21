package intouch.servlets;

import intouch.dto.SignUpForm;
import intouch.dto.UserDto;
import intouch.exceptions.InTouchException;
import intouch.services.AuthorizationService;
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

@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {
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
        Session session = sessionsManager.getSession(false, req, resp);
        if (session != null && session.getAttribute("user") != null) {
            resp.sendRedirect("menu");
        } else {
            req.setAttribute("err",req.getParameter("err"));
            req.getRequestDispatcher("signup.ftl").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        SignUpForm signUpForm = SignUpForm.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
        try {
            UserDto userDto = authorizationService.signUp(signUpForm);
            Session session = sessionsManager.getSession(true, req, resp);
            session.setAttribute("user", userDto);
            resp.sendRedirect("menu");
        } catch (InTouchException e) {
            resp.sendRedirect("signup?err=" + e.getMessage());
        }
    }
}