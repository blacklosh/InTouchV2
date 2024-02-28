package intouch.services;

import intouch.dto.CreatePostForm;
import intouch.dto.PostDto;
import intouch.exceptions.NotFoundException;

import java.util.List;
import java.util.UUID;

public interface PostsService {
    PostDto save(CreatePostForm form);
    PostDto getById(UUID id) throws NotFoundException;
    List<PostDto> findAll();
}
