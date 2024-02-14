package intouch.services;

import intouch.dto.CreatePostForm;
import intouch.dto.PostDto;

import java.util.List;

public interface PostsService {
    PostDto save(CreatePostForm form);
    List<PostDto> findAll();
}
