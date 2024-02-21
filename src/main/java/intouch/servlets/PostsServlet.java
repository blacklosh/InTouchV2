package intouch.servlets;

import intouch.dto.CreatePostForm;
import intouch.dto.PostDto;
import intouch.dto.SignInForm;
import intouch.dto.UserDto;
import intouch.exceptions.InTouchException;
import intouch.services.PostsService;
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
import java.util.List;

@WebServlet("/posts")
public class PostsServlet extends HttpServlet {
    private PostsService postsService;
    private SessionsManager sessionsManager;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        postsService = (PostsService) context.getAttribute("postsService");
        sessionsManager = (SessionsManager) context.getAttribute("sessionsManager");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session session = sessionsManager.getSession(false, req, resp);
        if (session != null && session.getAttribute("user") != null) {
            UserDto user = (UserDto) session.getAttribute("user");
            List<PostDto> posts = postsService.findAll();
            req.setAttribute("user", user);
            req.setAttribute("posts", posts);
            req.getRequestDispatcher("posts.ftl").forward(req,resp);
        } else {
            resp.sendRedirect("signin");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String text = req.getParameter("text");
        Session session = sessionsManager.getSession(false, req, resp);
        if (session != null && session.getAttribute("user") != null) {
            UserDto user = (UserDto) session.getAttribute("user");
            CreatePostForm form = CreatePostForm.builder()
                    .text(text)
                    .authorId(user.getId())
                    .build();
            postsService.save(form);
            resp.sendRedirect("posts");
        } else {
            resp.sendRedirect("signin");
        }
    }
}
