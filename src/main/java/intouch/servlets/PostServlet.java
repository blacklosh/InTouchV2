package intouch.servlets;

import intouch.dto.PostDto;
import intouch.dto.UserDto;
import intouch.exceptions.NotFoundException;
import intouch.services.PostsService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/post/*")
public class PostServlet extends HttpServlet {
    private PostsService postsService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        postsService = (PostsService) servletContext.getAttribute("postsService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID postId;
        try {
            String postIdString = req.getRequestURI().substring((req.getContextPath() + "/post/").length());
            postId = UUID.fromString(postIdString);
        } catch (IllegalArgumentException | StringIndexOutOfBoundsException e) {
            resp.setStatus(400);
            resp.getWriter().println("Wrong request");
            return;
        }
        try {
            UserDto user = (UserDto) req.getSession().getAttribute("user");
            PostDto post = postsService.getById(postId);
            req.setAttribute("user", user);
            req.setAttribute("post", post);
            req.getRequestDispatcher("/view_post.ftl").forward(req, resp);
        } catch (NotFoundException e) {
            resp.setStatus(404);
            resp.getWriter().println(e.getMessage());
        }
    }
}
