package intouch.servlets;

import intouch.dto.SignUpForm;
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

@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {
    private AuthorizationService authorizationService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        authorizationService = (AuthorizationService) context.getAttribute("authorizationService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("err", req.getParameter("err"));
        req.getRequestDispatcher("signup.ftl").forward(req, resp);
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
            HttpSession session = req.getSession(true);
            session.setAttribute("user", userDto);
            resp.sendRedirect(req.getContextPath() + "/profile");
        } catch (InTouchException e) {
            resp.sendRedirect(req.getContextPath() + "/signup?err=" + e.getMessage());
        }
    }
}