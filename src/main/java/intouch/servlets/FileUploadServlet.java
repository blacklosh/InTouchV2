package intouch.servlets;

import intouch.dto.UserDto;
import intouch.model.FileInfo;
import intouch.services.FileService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/upload")
@MultipartConfig
public class FileUploadServlet extends HttpServlet {
    private FileService fileService;

    @Override
    public void init(ServletConfig config) {
        this.fileService = (FileService) config.getServletContext().getAttribute("fileService");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part part = request.getPart("file");
        HttpSession session = request.getSession(true);
        UserDto userDto = (UserDto) session.getAttribute("user");
        FileInfo fileInfo = fileService.saveFileToStorage(
                userDto,
                part.getInputStream(),
                part.getSubmittedFileName(),
                part.getContentType(),
                part.getSize());
        userDto.setAvatarId(fileInfo.getId());
        session.setAttribute("user", userDto);
        response.sendRedirect(request.getContextPath() + "/profile");
    }
}