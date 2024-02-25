package intouch.services;

import intouch.dto.UserDto;
import intouch.model.FileInfo;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public interface FileService {
    FileInfo saveFileToStorage(UserDto user, InputStream file, String originalFileName, String contentType, Long size);
    void readFileFromStorage(UUID fileId, OutputStream outputStream);
    FileInfo getFileInfo(UUID fileId);
}