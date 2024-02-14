package intouch.mapper;

import intouch.dto.CreatePostForm;
import intouch.dto.PostDto;
import intouch.model.Post;

import java.util.List;

public interface PostMapper {
    PostDto toDto(Post post);
    Post toPost(PostDto dto);
    Post toPost(CreatePostForm dto);
    List<PostDto> toDtoList(List<Post> posts);
}
