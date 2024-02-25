package intouch.mapper.impl;

import intouch.dto.CreatePostForm;
import intouch.dto.PostDto;
import intouch.mapper.PostMapper;
import intouch.mapper.UserMapper;
import intouch.model.Post;
import intouch.model.User;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PostMapperImpl implements PostMapper {
    private final UserMapper userMapper;

    @Override
    public PostDto toDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .author(userMapper.toDto(post.getAuthor()))
                .text(post.getText())
                .creationDate(post.getCreationDate())
                .build();
    }

    @Override
    public Post toPost(PostDto dto) {
        return Post.builder()
                .id(dto.getId())
                .author(userMapper.toUser(dto.getAuthor()))
                .text(dto.getText())
                .creationDate(dto.getCreationDate())
                .build();
    }

    @Override
    public Post toPost(CreatePostForm dto) {
        return Post.builder()
                .author(User.builder()
                        .id(dto.getAuthorId())
                        .build())
                .text(dto.getText())
                .creationDate(Instant.now())
                .build();
    }

    @Override
    public List<PostDto> toDtoList(List<Post> posts) {
        return posts.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
