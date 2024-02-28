package intouch.services.impl;

import intouch.dto.CreatePostForm;
import intouch.dto.PostDto;
import intouch.exceptions.NotFoundException;
import intouch.mapper.PostMapper;
import intouch.model.Post;
import intouch.repository.PostsRepository;
import intouch.services.PostsService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class PostsServiceImpl implements PostsService {
    private final PostsRepository postsRepository;
    private final PostMapper postMapper;

    @Override
    public PostDto save(CreatePostForm form) {
        Post post = postMapper.toPost(form);
        Post saved = postsRepository.save(post);
        return postMapper.toDto(saved);
    }

    @Override
    public PostDto getById(UUID id) throws NotFoundException {
        Optional<Post> optionalPost = postsRepository.findById(id);
        if(optionalPost.isEmpty()) {
            throw new NotFoundException("POST WITH ID " + id + " NOT FOUND");
        }
        Post post = optionalPost.get();
        return postMapper.toDto(post);
    }

    @Override
    public List<PostDto> findAll() {
        return postMapper.toDtoList(postsRepository.findAll());
    }
}