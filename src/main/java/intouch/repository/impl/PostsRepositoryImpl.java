package intouch.repository.impl;

import intouch.model.Post;
import intouch.repository.PostsRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PostsRepositoryImpl implements PostsRepository {

    @Override
    public Optional<Post> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Post> findAll() {
        return null;
    }

    @Override
    public Post save(Post item) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}