package intouch.services.impl;

import intouch.dto.UserDto;
import intouch.exceptions.NotFoundException;
import intouch.model.FileInfo;
import intouch.repository.FileRepository;
import intouch.repository.UsersRepository;
import intouch.services.FileService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

public class FileServiceImpl implements FileService {
    private final String path;
    private final FileRepository filesRepository;
    private final UsersRepository usersRepository;

    public FileServiceImpl(String path, FileRepository filesRepository, UsersRepository usersRepository) {
        this.path = path;
        this.filesRepository = filesRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public FileInfo saveFileToStorage(UserDto user, InputStream inputStream, String originalFileName, String contentType, Long size) {
        FileInfo fileInfo = new FileInfo(
                null,
                originalFileName,
                UUID.randomUUID().toString(),
                size,
                contentType
        );
        try {
            Files.copy(inputStream, Paths.get(path + fileInfo.getStorageFileName() + "." + fileInfo.getType().split("/")[1]));
            fileInfo = filesRepository.save(fileInfo);
            usersRepository.updateAvatarForUser(user.getId(), fileInfo.getId());
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        return fileInfo;
    }

    @Override
    public void readFileFromStorage(UUID fileId, OutputStream outputStream) {
        Optional<FileInfo> optionalFileInfo = filesRepository.findById(fileId);
        FileInfo fileInfo = optionalFileInfo.orElseThrow(() -> new NotFoundException("File not found"));

        File file = new File(path + fileInfo.getStorageFileName() + "." + fileInfo.getType().split("/")[1]);
        try {
            Files.copy(file.toPath(), outputStream);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public FileInfo getFileInfo(UUID fileId) {
        return filesRepository.findById(fileId).orElseThrow(() -> new NotFoundException("File not found"));
    }
}