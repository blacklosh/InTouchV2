package intouch.servlets;

import intouch.dto.CreatePostForm;
import intouch.dto.PostDto;
import intouch.dto.UserDto;
import intouch.services.PostsService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/posts")
public class PostsServlet extends HttpServlet {
    private PostsService postsService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        postsService = (PostsService) context.getAttribute("postsService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserDto user = (UserDto) session.getAttribute("user");
        List<PostDto> posts = postsService.findAll();
        req.setAttribute("user", user);
        req.setAttribute("posts", posts);
        req.getRequestDispatcher("posts.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String text = req.getParameter("text");
        HttpSession session = req.getSession();
        UserDto user = (UserDto) session.getAttribute("user");
        CreatePostForm form = CreatePostForm.builder()
                .text(text)
                .authorId(user.getId())
                .build();
        postsService.save(form);
        resp.sendRedirect(req.getContextPath() + "/posts");
    }
}
