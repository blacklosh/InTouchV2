package intouch.mapper.impl;

import intouch.dto.CreatePostForm;
import intouch.dto.PostDto;
import intouch.mapper.PostMapper;
import intouch.model.Post;

import java.util.List;
import java.util.stream.Collectors;

public class PostMapperImpl implements PostMapper {
    @Override
    public PostDto toDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .authorId(post.getAuthorId())
                .text(post.getText())
                .build();
    }

    @Override
    public Post toPost(PostDto dto) {
        return Post.builder()
                .id(dto.getId())
                .authorId(dto.getAuthorId())
                .text(dto.getText())
                .build();
    }

    @Override
    public Post toPost(CreatePostForm dto) {
        return Post.builder()
                .authorId(dto.getAuthorId())
                .text(dto.getText())
                .build();
    }

    @Override
    public List<PostDto> toDtoList(List<Post> posts) {
        return posts.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
