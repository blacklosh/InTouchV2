package intouch.servlets;

import intouch.dto.SignInForm;
import intouch.dto.UserDto;
import intouch.exceptions.InTouchException;
import intouch.services.AuthorizationService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(value = "/signin")
public class SignInServlet extends HttpServlet {
    private AuthorizationService authorizationService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        authorizationService = (AuthorizationService) context.getAttribute("authorizationService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        req.setAttribute("err", req.getParameter("err"));
        req.getRequestDispatcher("signin.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        SignInForm signInForm = SignInForm.builder()
                .email(email)
                .password(password)
                .build();
        try {
            UserDto userDto = authorizationService.signIn(signInForm);
            HttpSession session = req.getSession(true);
            session.setAttribute("user", userDto);
            resp.sendRedirect("profile");
        } catch (InTouchException e) {
            resp.sendRedirect("signin?err=" + e.getMessage());
        }
    }
}