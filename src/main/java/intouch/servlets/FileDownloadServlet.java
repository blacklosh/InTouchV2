package intouch.servlets;

import intouch.exceptions.NotFoundException;
import intouch.model.FileInfo;
import intouch.services.FileService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/download/*")
public class FileDownloadServlet extends HttpServlet {
    private FileService fileService;

    @Override
    public void init(ServletConfig config) {
        this.fileService = (FileService) config.getServletContext().getAttribute("fileService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UUID fileId;
        try {
            String fileIdString = request.getRequestURI().substring("/intouch/download/".length());
            fileId = UUID.fromString(fileIdString);
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            response.setStatus(400);
            response.getWriter().println("Wrong request");
            return;
        }

        try {
            FileInfo fileInfo = fileService.getFileInfo(fileId);
            response.setContentType(fileInfo.getType());
            response.setContentLength(fileInfo.getSize().intValue());
            response.setHeader("Content-Disposition", "filename=\"" + fileInfo.getOriginalFileName() + "\"");
            fileService.readFileFromStorage(fileId, response.getOutputStream());
            response.flushBuffer();
        } catch (NotFoundException e) {
            response.setStatus(404);
            response.getWriter().println(e.getMessage());
        }
    }
}