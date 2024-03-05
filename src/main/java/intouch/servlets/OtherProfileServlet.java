package intouch.servlets;

import intouch.dto.PostDto;
import intouch.dto.UserDto;
import intouch.exceptions.NotFoundException;
import intouch.services.PostsService;
import intouch.services.UsersService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/profile/*")
public class OtherProfileServlet extends HttpServlet {
    private PostsService postsService;
    private UsersService usersService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        postsService = (PostsService) servletContext.getAttribute("postsService");
        usersService = (UsersService) servletContext.getAttribute("usersService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID profileId;
        try {
            String profileIdString = req.getRequestURI().substring((req.getContextPath() + "/profile/").length());
            profileId = UUID.fromString(profileIdString);
        } catch (IllegalArgumentException | StringIndexOutOfBoundsException e) {
            resp.setStatus(400);
            resp.getWriter().println("Wrong request");
            return;
        }
        UserDto user = (UserDto) req.getSession().getAttribute("user");
        if(user.getId().equals(profileId)) {
            resp.sendRedirect(req.getContextPath() + "/profile");
            return;
        }
        UserDto author;
        try {
            author = usersService.getById(profileId);
        } catch (NotFoundException e) {
            resp.setStatus(404);
            resp.getWriter().println(e.getMessage());
            return;
        }
        try {
            List<PostDto> posts = postsService.findAllByAuthorId(profileId);
            req.setAttribute("user", user);
            req.setAttribute("author", author);
            req.setAttribute("posts", posts);
            req.getRequestDispatcher("/other_profile.ftl").forward(req, resp);
        } catch (NotFoundException e) {
            resp.setStatus(404);
            resp.getWriter().println(e.getMessage());
        }
    }
}